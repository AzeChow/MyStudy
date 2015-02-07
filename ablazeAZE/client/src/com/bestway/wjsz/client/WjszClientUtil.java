package com.bestway.wjsz.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bestway.client.windows.form.FmMain;
import com.bestway.client.windows.form.LogoutActionListener;
import com.bestway.jptds.acluser.action.AclUserAction;
import com.bestway.jptds.acluser.entity.CheckAuthrityResult;
import com.bestway.jptds.acluser.entity.CorpAclUser;
import com.bestway.jptds.acluser.entity.LrdAclUser;
import com.bestway.jptds.client.common.CommonVars;
import com.bestway.jptds.client.common.RegistryUtil;
import com.bestway.jptds.system.action.SystemAction;
import com.bestway.jptds.system.entity.Company;
import com.bestway.waijing.action.WjszAction;
import com.bestway.waijing.entity.WjszParaSet;

public class WjszClientUtil {
	/**
	 * 是否登录
	 */
	private static boolean loginStatus = false;

	public static boolean isLoginStatus() {
		return loginStatus;
	}

	public static void login() {
		if (loginStatus) {
			return;
		}
		WjszAction wjszAction = (WjszAction) com.bestway.bcus.client.common.CommonVars
				.getApplicationContext().getBean("wjszAction");
		WjszParaSet wjszParaSet = (WjszParaSet) wjszAction
				.findWjszParaSet(new com.bestway.common.Request(
						com.bestway.bcus.client.common.CommonVars.getCurrUser()));
		Map map = RegistryUtil.getRegistUser();
		if (map == null) {
			return;
		}
		String userId = (map.get("userid") == null || "".equals(map
				.get("userid"))) ? "" : map.get("userid").toString();
		String password = wjszParaSet.getPassword();
		if (userId == null || "".equals(userId.trim()) || password == null
				|| "".equals(password.trim())) {
			return;
		}
		login(userId, password);
	}

	public static void login(String userId, String password) {
		WjszAction wjszAction = (WjszAction) com.bestway.bcus.client.common.CommonVars
				.getApplicationContext().getBean("wjszAction");
		WjszParaSet wjszParaSet = (WjszParaSet) wjszAction
				.findWjszParaSet(new com.bestway.common.Request(
						com.bestway.bcus.client.common.CommonVars.getCurrUser()));
		if (wjszParaSet.getServerName() == null
				|| "".equals(wjszParaSet.getServerName().trim())) {
			throw new RuntimeException("外经服务器地址不能为空！");
		}
		if (wjszParaSet.getServerPort() == null
				|| "".equals(wjszParaSet.getServerPort().trim())) {
			throw new RuntimeException("外经服务器端口不能为空！");
		}
		System
				.setProperty("wjszServerName", wjszParaSet.getServerName()
						.trim());
		System.setProperty("wjszHttpPort", wjszParaSet.getServerPort().trim());
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"com/bestway/wjsz/client/WjszHttpClientContext.xml");
		CommonVars.setApplicationContext(applicationContext);
		final AclUserAction authorityAction = (AclUserAction) com.bestway.jptds.client.common.CommonVars
				.getApplicationContext().getBean("aclUserAction");
		boolean isAdminUser = authorityAction.checkIsAdminUser(userId);
		SystemAction systemAction = (SystemAction) CommonVars
				.getApplicationContext().getBean("systemAction");
		if (isAdminUser) {
			throw new RuntimeException("用户" + userId + "是超级管理人员，不能在这里登录！");
		}
		Map map = RegistryUtil.getRegistUser();
		if (map == null) {
			JOptionPane.showMessageDialog(null, "请先注册用户！");
			return;
		}
		String key = ((map.get("key") == null || "".equals(map.get("key"))) ? ""
				: map.get("key").toString());
		String type = ((map.get("type") == null || "".equals(map.get("type"))) ? ""
				: map.get("type").toString());
		if (!vaildPassword(type, userId, password, key)) {
			return;
		}
		if (CommonVars.getUser() instanceof LrdAclUser) {
			throw new RuntimeException("用户" + userId + "是录入点用户，不是企业端用户");
		}
		CorpAclUser user = (CorpAclUser) CommonVars.getUser();
		Company company = systemAction.findCompanies(CommonVars.getRequest(),
				user.getCompanyCode());
		if (company == null) {
			JOptionPane.showMessageDialog(null, "企业编码" + user.getCompanyCode()
					+ "不存在，请与外经联系！");
			return;
		}
		CommonVars.setCompany(company);
		FmMain fmMain = FmMain.getInstance();
		fmMain.addLogoutActionListener(new LogoutActionListener() {

			public void actionPerformed(ActionEvent e) {
				authorityAction.getRemoveOnlineCount(CommonVars.getUser()
						.getUserId());
				CommonVars.setCompany(null);
				CommonVars.setUser(null);
				CommonVars.setSessionId(null);
			}
		});

		fmMain.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowClosing(java.awt.event.WindowEvent evt) {
				if (JOptionPane.showConfirmDialog(FmMain.getInstance(),
						"你确认要退出系统吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					FmMain.getInstance().setDefaultCloseOperation(
							JFrame.DO_NOTHING_ON_CLOSE);
				} else {
					try {
						authorityAction.getRemoveOnlineCount(CommonVars
								.getUser().getUserId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					FmMain.getInstance().setDefaultCloseOperation(
							JFrame.EXIT_ON_CLOSE);
				}

			}
		});
		// fmMain.addBackgroundProcess(InformationRimdInfoUtil
		// .getInformationRimdInfoUtil(fmMain));
		// fmMain.startBackgroundProcess();
//		JOptionPane.showMessageDialog(FmMain.getInstance(), "登录外径三资申报系统成功！");
		loginStatus = true;
	}

	private static boolean vaildPassword(String type, String userId,
			String password, String key) {
		AclUserAction authorityAction = (AclUserAction) CommonVars
				.getApplicationContext().getBean("aclUserAction");
		CheckAuthrityResult result = null;
		String sessionId = "";
		try {
			result = authorityAction.vaildPassword(type, userId, password);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "用户验证失败！" + e.getMessage());
			return false;
		}
		if (result == null) {
			return false;
		}
		try {
			sessionId = CommonVars.decryptStringByPubliceKey(result
					.getSessionId(), key);
			CommonVars.setSessionId(sessionId);
			CommonVars.setUser(result.getUser());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "解密失败！");
			return false;
		}
		return true;
	}

}
