package com.bestway.bcus.client.authority;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.client.AutoUpgrade;
import com.bestway.bcus.client.JarDataStoreImpl;
import com.bestway.bcus.client.LoadPluginImpl;
import com.bestway.bcus.client.LoadPluginReportImpl;
import com.bestway.bcus.client.RenderDataColumnImpl;
import com.bestway.bcus.client.cas.parameter.InitCasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.license.LicenseClient;
import com.bestway.bcus.license.action.LicenseAction;
import com.bestway.bcus.license.entity.LicenseInfo;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.windows.form.FmMain;
import com.bestway.client.windows.form.FormThemeControl;
import com.bestway.client.windows.form.LogoutActionListener;
import com.bestway.client.windows.form.NavigationListener;
import com.bestway.client.windows.form.ThemeListener;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.client.erpbillcenter.parameter.InitErpBillParameterCommonVars;
import com.bestway.common.client.warning.Warn;
import com.bestway.common.constant.ParameterType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.BomStructureType;
import com.bestway.docs.LogInfoAdapter;
import com.bestway.ui.winuicontrol.JFrameBase;

public class FmLogin extends JFrameBase {

	JPanel jContentPane = null;

	private JTextField tfUserId = null;

	private JPasswordField tfPassword = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private AuthorityAction authorityAction = null;

	private SystemAction systemAction = null;

	private LicenseAction licenseAction = null;

	int iTryTimes = 0;

	private JComboBox cbCompanies = null;

	private JPanel jPanel = null;

	private JLabel jLabel6 = null;

	private JTextField tfUserName = null;

	private String serverIP = null;

	private JMenuItem jbcusHelpItem = null;

	private JButton btnRegister = null;

	private JButton btnNameEdit = null;

	private Company selectedCompany = null;

	private AclUser aclUser = null; // @jve:decl-index=0:

	private int errorCommit = 0;

	public FmLogin() {

		super();

		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");

		// "外商投资企业关务物流一体化管理系统"
		CommonVariables.mainTitle = getApplicationName();

		initialize();

		licenseAction = (LicenseAction) CommonVars.getApplicationContext()
				.getBean("licenseAction");

		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");
	}

	private void initialize() {

		// 不允许改变大小
		setResizable(false);

		setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		setTitle(CommonVariables.mainTitle);

		setSize(367, 287);

		setContentPane(getJContentPane());

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				// 已启用的公司
				List companies = systemAction.findEnableCompanies();

				boolean flag = true;

				for (int i = 0; i < companies.size(); i++) {

					Company company = (Company) companies.get(i);

					if (company.getIsEnable() == null || company.getIsEnable()) {

						cbCompanies.addItem(company);

					}

					if (flag
							&& company.getIsCurrCompany() != null
							&& company.getIsCurrCompany().equals(
									new Boolean(true))) {

						cbCompanies.setSelectedItem(company);

						flag = false;

					}
				}

				// 渲染下拉列表的显示方式
				cbCompanies.setRenderer(new DefaultListCellRenderer() {

					public Component getListCellRendererComponent(JList list,
							Object value, int index, boolean isSelected,
							boolean cellHasFocus) {

						super.getListCellRendererComponent(list, value, index,
								isSelected, cellHasFocus);

						super.setText(((Company) value).getName());

						return this;
					}
				});
			}
		});

	}

	/**
	 * 取得应用程序名字
	 * 
	 * @return
	 */
	private String getApplicationName() {

		SAXBuilder sb = new SAXBuilder();

		Document doc = null;

		try {

			doc = sb.build(this.getClass().getClassLoader()
					.getResourceAsStream("ClientModuleTree.xml"));

			return doc.getRootElement().getAttribute("caption").getValue();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

		}
		return "";
	}

	/**
	 * @return Returns the jContentPane.
	 */
	public JPanel getJContentPane() {

		if (jContentPane == null) {

			javax.swing.JLabel loginIcon = new JLabel();

			jContentPane = new javax.swing.JPanel();

			// 自由布局
			jContentPane.setLayout(null);

			loginIcon.setBounds(0, -1, 385, 94);

			loginIcon.setText("JLabel");

			loginIcon.setIcon(CommonVars.getIconSource().getIcon("login.icon"));

			jContentPane.add(loginIcon, null);

			jContentPane.add(getJPanel(), null);

		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes tfUserId
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfUserId() {

		if (tfUserId == null) {

			tfUserId = new JTextField();

			tfUserId.setBounds(123, 35, 180, 22);

			tfUserId.addFocusListener(new java.awt.event.FocusAdapter() {

				// 焦点丢失
				public void focusLost(java.awt.event.FocusEvent e) {

					// 没有任何被选中 将抛出对话框提示用户
					if (cbCompanies.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(FmLogin.this, "请选择公司！",
								"提示信息", 1);
						return;
					}

					BaseCompany selectedCompany = (BaseCompany) cbCompanies
							.getSelectedItem();

					List userName = authorityAction.getUserNameByLoginName(
							selectedCompany, tfUserId.getText());

					if (userName != null && userName.size() > 0) {

						tfUserName.setText(((AclUser) userName.get(0))
								.getUserName());

					}
				}
			});

		}
		return tfUserId;
	}

	/**
	 * 初始化密码框
	 * 
	 * @return
	 */
	private JPasswordField getTfPassword() {

		if (tfPassword == null) {

			tfPassword = new JPasswordField();

			tfPassword.setBounds(123, 62, 180, 22);

			tfPassword.addKeyListener(new java.awt.event.KeyAdapter() {

				public void keyPressed(java.awt.event.KeyEvent e) {

					String sKey = e.getKeyText(e.getKeyCode());

					if (sKey.equalsIgnoreCase("Enter")) {

						enter();

					}
				}
			});
		}
		return tfPassword;
	}

	/**
	 * 初始化取消按钮
	 * 
	 * @return
	 */
	private JButton getBtnCancel() {

		if (btnCancel == null) {

			btnCancel = new JButton();

			btnCancel.setText("取消");

			btnCancel.setBounds(243, 125, 60, 23);

			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					System.exit(0);

				}
			});

		}
		return btnCancel;
	}

	/**
	 * @return Returns the btnOk.
	 */
	public JButton getBtnOk() {

		if (btnOk == null) {

			btnOk = new JButton();

			btnOk.setText("确定");

			btnOk.setBounds(56, 126, 60, 22);

			btnOk.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					enter();

				}
			});

		}
		return btnOk;
	}

	private void enter() {

		// 判断 防止空提交
		if (cbCompanies.getSelectedItem() == null) {
			return;
		}
		selectedCompany = (Company) cbCompanies.getSelectedItem();

		final String companyName = selectedCompany.getName().trim();// 加工单位

		if (companyName.indexOf("缺省公司") >= 0) {// 如果当前为缺省公司

			btnNameEdit.setVisible(true);

		}

		boolean isBestWay = false;

		LicenseInfo licenseInfo = licenseAction.getLicenseInfo();

		if (licenseInfo != null && licenseInfo.getCompanies().size() > 0) {

			for (int i = 0; i < licenseInfo.getCompanies().size(); i++) {

				if (licenseInfo.getCompanies().get(i).toString().indexOf("百思维") >= 0) {
					isBestWay = true;
					break;
				}
			}
		}
		if (!isBestWay) {
			if (!LicenseClient.getInstance(companyName).checkHasLicense()) {
				JOptionPane.showMessageDialog(FmLogin.this,
						"此公司没有注册license文件,请先进行注册，然后再登录！", "提示信息",
						JOptionPane.INFORMATION_MESSAGE);
				btnRegister.setBounds(56, 126, 60, 22);
				btnRegister.setVisible(true);
				btnOk.setVisible(false);
				btnRegister.doClick();
				return;
			}
			switch (LicenseClient.getInstance(companyName).checkLicense()) {
			case LicenseInfo.NO_LICENSE:
				JOptionPane.showMessageDialog(FmLogin.this,
						"此公司没有注册license文件！", "提示信息",// ,系统默认只有一个用户
						JOptionPane.INFORMATION_MESSAGE);

				return;
			case LicenseInfo.DATE_OUT:
				JOptionPane.showMessageDialog(FmLogin.this,
						"软件的试用期已过，不能继续试用，请重新注册。", "提示信息",
						JOptionPane.INFORMATION_MESSAGE);
				btnRegister.setBounds(56, 126, 60, 22);
				btnRegister.setVisible(true);
				btnOk.setVisible(false);
				btnRegister.doClick();

				return;
			case LicenseInfo.CLINT_NUM_OUT:
				JOptionPane.showMessageDialog(FmLogin.this,
						"运行客户端的数量已经超出License所允许的数量！", "提示信息",
						JOptionPane.INFORMATION_MESSAGE);

				return;
			}
		}

		AclUser currUser = null;

		int style = 0;

		boolean isMenuNavigation = false;

		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");

		// 帐号对应的名称
		String type = authorityAction.findDigestType(tfUserId.getText(),
				selectedCompany);

		System.out.println("type:" + type);

		String pw = CommonVars.messageDigest(type,
				String.valueOf(tfPassword.getPassword()));

		Hashtable response = null;

		System.out.println("Password :  >>>" + pw);

		response = authorityAction.checkUser(tfUserId.getText(), pw,
				selectedCompany);

		if (response != null) {

			currUser = (AclUser) response.get("user");

			CommonVars.setCurrUser(currUser);

			style = currUser.getStyle() == null ? FormThemeControl.OCEAN_THEME
					: currUser.getStyle().intValue();

			isMenuNavigation = (currUser.getIsMenuNavigation() == null ? false
					: currUser.getIsMenuNavigation());

			aclUser = new AclUser();

		} else {

			System.out.println("tfUserId.getText()=" + tfUserId.getText());

			CommonVars.setCurrUser(null);

			aclUser = authorityAction.findUsersByLoginName(new Request(
					CommonVars.getCurrUser()), tfUserId.getText());

			System.out.println("tfUserId.getText()=" + tfUserId.getText());

		}
		if (null == aclUser) {

			JOptionPane.showMessageDialog(FmLogin.this, "用户名输入不正确，请重新输入",
					"提示信息", 1);

			return;

		} else {

			String loginname = aclUser.getLoginName();

			if (aclUser.getIsForbid() != null && aclUser.getIsForbid()) {
				JOptionPane.showMessageDialog(FmLogin.this,
						"此用户已被禁用，请用另一用户重新登录", "提示信息", 1);
				System.exit(0);
			}

			if (CommonVars.getCurrUser() == null) {

				JOptionPane.showMessageDialog(FmLogin.this,
						"用户名或密码输入不正确，请重新输入", "提示信息", 1);

				LicenseClient.getInstance(companyName)
						.removeLoginedClientInfo();

				errorCommit++;

				if (errorCommit > 2) {

					aclUser.setIsForbid(true);

					authorityAction.saveUser(
							new Request(CommonVars.getCurrUser()), aclUser);

					errorCommit = 0;

					JOptionPane.showMessageDialog(FmLogin.this,
							"此用户已被禁用，请用另一用户重新登录", "提示信息", 1);

					System.exit(0);

				}
				System.out.println("errorCommit=" + errorCommit);
				return;
			}

		}

		if (CommonVars.getCurrUser().getIsForbid() != null
				&& CommonVars.getCurrUser().getIsForbid()) {
			JOptionPane.showMessageDialog(FmLogin.this, "此用户已被禁用，请用另一用户重新登录",
					"提示信息", 1);
			return;
		}

		systemAction.createDir(new Request(CommonVars.getCurrUser()), true,
				"sendDir", "recvDir", "finallyDir", "logDir", "recvBillDir");

		getCommonVarDir();// 读取报文路径

		getEmsFrom();// 读取电子帐册选项

		getContractFrom();// 读取合同备案选项

		getTxtDir(); // 读取文本路径

		initCompanyOther();// 初始化系统参数

		getIsBigToGBK(); // 输出报文是否

		System.out.println(CommonVars.getServerName() + "  port    ");

		System.out.println(CommonVars.getIpAddress() + "  port    "
				+ CommonVars.getPort());

		// 初始化模块
		List hsModules = LicenseClient.getInstance(companyName).getHasModules();

		// for (int i = 0; i < hsModules.size(); i++) {
		// System.out.println("Has Modules size:" + hsModules.get(i));
		if (!isBestWay) {
			if (hsModules.contains("ZhiZhiShouCeGuanLi")) {
				CommonVars.setProjectType(ProjectType.BCS);
			}
			if (hsModules.contains("DianZiShouCeGuanLi")) {
				CommonVars.setProjectType(ProjectType.BCUS);
			}
			if (hsModules.contains("LianWangJianGuan")) {
				CommonVars.setProjectType(ProjectType.DZSC);
			}
		}
		// }
		final FmMain fmMain = FmMain.getInstance(hsModules, style,
				isMenuNavigation);

		// 设置程序日志适配
		fmMain.getDgExplain().setLogInfoAdapter(new LogInfoAdapter() {

			public String getLogInfo() {

				return systemAction.getLogInfo();

			}
		});

		//
		// load into 客户端插件
		//
		fmMain.setLoadPlugin(new LoadPluginImpl());
		/**
		 * 增加用户自定义的注销事件处理程序
		 */
		fmMain.addLogoutActionListener(new LogoutActionListener() {

			public void actionPerformed(ActionEvent e) {
				FmLogin fmLogin = new FmLogin();
				fmLogin.setVisible(true);
			}

		});

		fmMain.setVisible(true);

		fmMain.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				int a = JOptionPane.showConfirmDialog(fmMain, "您确定要关闭整个程序吗？",
						"温馨提示", JOptionPane.YES_NO_OPTION);
				if (a == JOptionPane.NO_OPTION) {
					return;
				}
				System.out.println("关闭..........");
				removeLoginedClientInfo(companyName);
				System.exit(0);// 比fmMain关闭后执行
			}
		});
		fmMain.addLogoutActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// System.out.println("注销..........");
				removeLoginedClientInfo(companyName);
			}
		});
		fmMain.addExitActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// System.out.println("退出..........");
				removeLoginedClientInfo(companyName);
			}
		});

		fmMain.addRegisterActionListener(new RegisterActionListener());

		fmMain.addThemeListener(new ThemeListener() {
			public void actionPerformed(int style) {
				AclUser obj = CommonVars.getCurrUser();
				obj.setStyle(Integer.valueOf(style));
				systemAction.saveAclUser(new Request(CommonVars.getCurrUser()),
						obj);
			}
		});

		fmMain.setNavigationListener(new NavigationListener() {
			public void actionPerformed(boolean isMenuNavi) {
				AclUser obj = CommonVars.getCurrUser();
				obj.setIsMenuNavigation(isMenuNavi);
				systemAction.saveAclUser(new Request(CommonVars.getCurrUser()),
						obj);
			}
		});

		fmMain.addSetupJAVAIMEListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupJAVAIME();
			}
		});
		//
		// 自运升级
		//
		fmMain.setUpgrade(new AutoUpgrade());
		// 显示登陆的公司名称
		fmMain.setStateInfo2("当前公司：" + selectedCompany.getName());
		// 显示登陆的用户名称
		fmMain.setStateInfo1("登录用户：" + currUser.getUserName());
		//
		// 设置所有表格调用的方法,是否呈现列
		//
		CommonVariables
				.setRenderDataColumnInterface(new RenderDataColumnImpl());
		//
		// 设置所有表格数据存储调用方法
		//
		CommonVariables.setJarDataStoreInterface(new JarDataStoreImpl());
		//
		// 设置客户端报表插件
		//
		CommonVariables.setWindowOpenedEvent(new LoadPluginReportImpl());
		//
		// 运行预警
		//
		Warn.RunWarning();

		FmLogin.this.dispose();

		ExecutorService service = Executors.newFixedThreadPool(3);

		service.execute(LicenseClient.getInstance(companyName));

		service.execute(InitCasCommonVars.getInstance());

		service.execute(InitErpBillParameterCommonVars.getInstance());

		service.shutdown();

		// Thread thread = new Thread(LicenseClient.getInstance(companyName));
		// thread.start();
		// Thread initCasCommonVarsThread = new Thread(
		// InitCasCommonVars.getInstance());
		// initCasCommonVarsThread.start();
		// Thread initErpBillParameterThread = new Thread(
		// InitErpBillParameterCommonVars.getInstance());
		// initErpBillParameterThread.start();
		// /**
		// * 自动启动发送接收邮件
		// */
		// new Thread(new AutoStartDxpMail()).start();

		if (CommonVariables.isFileExist()) {
			CommonVariables.getEveryList();
		}
		this.dispose();
	}

	private void getTxtDir() {
		systemAction
				.sysBeginSaveToCommon(new Request(CommonVars.getCurrUser()));
	}

	// 初始化系统其他参数
	private void initCompanyOther() {

		CompanyOther other = systemAction.saveCommonUtilsOther(new Request(
				CommonVars.getCurrUser()));// 保存到服务变量中

		CommonVars.setOther(other); // 保存到变量中

	}

	/**
	 * 读取电子帐册选项
	 */
	private void getEmsFrom() {

		List list = null;

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.emsFrom);

		if (list != null && !list.isEmpty()) {

			CommonVars.setEmsFrom(((ParameterSet) list.get(0)).getNameValues());

		} else {

			ParameterSet ems = new ParameterSet();

			ems.setNameValues("1");

			ems.setType(ParameterType.emsFrom);

			ems.setCompany(CommonVars.getCurrUser().getCompany());

			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);

			CommonVars.setEmsFrom("1");

		}

		// 设置帐册报核出口耗料金额单价来源
		list = systemAction.findBcusnameValues(
				new Request(CommonVars.getCurrUser()),

				BcusParameter.Ems_CancelCus_Price);

		if (list == null || list.isEmpty()) {

			systemAction.saveBcusValues(new Request(CommonVars.getCurrUser()),
					BcusParameter.Ems_CancelCus_Price, "1");

		}
	}

	private void getContractFrom() {
		List list = null;
		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.contractFrom);
		if (list != null && !list.isEmpty()) {
			CommonVars.setContractFrom(((ParameterSet) list.get(0))
					.getNameValues());
		} else {
			ParameterSet ems = new ParameterSet();
			ems.setNameValues("1");
			ems.setType(ParameterType.contractFrom);
			ems.setCompany(CommonVars.getCurrUser().getCompany());
			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);
			CommonVars.setContractFrom("1");
		}

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.unitWasteWriteBackExg);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = ((ParameterSet) list.get(0));
			CommonVars
					.setUnitWasteWriteBackExg("1".equals(para.getNameValues()) ? true
							: false);
		} else {
			ParameterSet ems = new ParameterSet();
			ems.setNameValues("1");
			ems.setType(ParameterType.unitWasteWriteBackExg);
			ems.setCompany(CommonVars.getCurrUser().getCompany());
			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);
			CommonVars.setUnitWasteWriteBackExg(true);
		}

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.unitWasteWriteBackImg);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = ((ParameterSet) list.get(0));
			CommonVars
					.setUnitWasteWriteBackImg("1".equals(para.getNameValues()) ? true
							: false);
		} else {
			ParameterSet ems = new ParameterSet();
			ems.setNameValues("1");
			ems.setType(ParameterType.unitWasteWriteBackImg);
			ems.setCompany(CommonVars.getCurrUser().getCompany());
			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);
			CommonVars.setUnitWasteWriteBackImg(true);
		}

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.isVailCustomsOther);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = ((ParameterSet) list.get(0));
			if (para.getNameValues() != null
					&& para.getNameValues().equals("1")) {
				CommonVars.setIsVailCustomsOther(true);
			} else {
				CommonVars.setIsVailCustomsOther(false);
			}
		} else {
			ParameterSet ems = new ParameterSet();
			ems.setNameValues("1");
			ems.setType(ParameterType.isVailCustomsOther);
			ems.setCompany(CommonVars.getCurrUser().getCompany());
			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);
			CommonVars.setIsVailCustomsOther(true);
		}

		// 纸质手册进出口报关单的单价是否可以修改
		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.isCanModifyUnitPrice);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = ((ParameterSet) list.get(0));
			if (para.getNameValues() != null
					&& para.getNameValues().equals("1")) {
				CommonVars.setIsCanModifyUnitPrice(true);
			} else {
				CommonVars.setIsCanModifyUnitPrice(false);
			}
		} else {
			ParameterSet ems = new ParameterSet();
			ems.setNameValues("1");
			ems.setType(ParameterType.isCanModifyUnitPrice);
			ems.setCompany(CommonVars.getCurrUser().getCompany());
			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);
			CommonVars.setIsVailCustomsOther(true);
		}

		list = systemAction
				.findnameValues(new Request(CommonVars.getCurrUser()),
						ParameterType.isPrintAll);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = ((ParameterSet) list.get(0));
			if (para.getNameValues() != null
					&& para.getNameValues().equals("1")) {
				CommonVars.setIsPrintAll(true);
			} else {
				CommonVars.setIsPrintAll(false);
			}
		} else {
			ParameterSet ems = new ParameterSet();
			ems.setNameValues("0");
			ems.setType(ParameterType.isPrintAll);
			ems.setCompany(CommonVars.getCurrUser().getCompany());
			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);
			CommonVars.setIsPrintAll(false);
		}

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.bomStructureType);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			String value = para.getNameValues();
			if (value != null && !"".equals(value)) {
				CommonVars.setBomStructureType(Integer.valueOf(value));
			} else {
				CommonVars
						.setBomStructureType(BomStructureType.NO_VERSION_NO_DATE);
			}
		} else {
			CommonVars.setBomStructureType(BomStructureType.NO_VERSION_NO_DATE);
		}

		// 报关单要允许输入超量的数据
		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.CUSTOM_AMOUNT_OUT);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = (ParameterSet) list.get(0);
			String value = para.getNameValues();
			if (value != null && !"".equals(value)) {
				CommonVars.setIsCustomAmountOut(Boolean.valueOf(value));
			} else {
				CommonVars.setIsCustomAmountOut(false);
			}
		} else {
			CommonVars.setIsCustomAmountOut(false);
		}

		// 特殊报关单来源
		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.SpecialCustoms);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = ((ParameterSet) list.get(0));
			CommonVars.setSpecialCustoms(((ParameterSet) list.get(0))
					.getNameValues());
		} else {
			ParameterSet ems = new ParameterSet();
			ems.setNameValues("1");
			ems.setType(ParameterType.SpecialCustoms);
			ems.setCompany(CommonVars.getCurrUser().getCompany());
			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);
			CommonVars.setSpecialCustoms("1");
		}

		// 修改经营范围与归并关系的备案序号
		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.h2kMergerModifyNo);
		if (list != null && !list.isEmpty()) {
			ParameterSet para = ((ParameterSet) list.get(0));
			if (para.getNameValues() != null
					&& para.getNameValues().equals("1")) {
				CommonVars.setH2kMergerModifyNo(true);
			} else {
				CommonVars.setH2kMergerModifyNo(false);
			}
		} else {
			ParameterSet ems = new ParameterSet();
			ems.setNameValues("0");
			ems.setType(ParameterType.h2kMergerModifyNo);
			ems.setCompany(CommonVars.getCurrUser().getCompany());
			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);
			CommonVars.setH2kMergerModifyNo(false);
		}

		// 电子化手册通关与备案资料名称、规格限定长度
		BcsParameterSet parameterSet = systemAction
				.findBcsParameterSet(new Request(CommonVars.getCurrUser(), true));
		if (parameterSet == null) {
			parameterSet = new BcsParameterSet();
		}
		parameterSet.setIsControlLength(true);
		systemAction.saveBcsParameterSet(new Request(CommonVars.getCurrUser(),
				true), parameterSet);
	}

	private void getIsBigToGBK() {
		// 输出报文是否繁转简
		List list = systemAction
				.findnameValues(new Request(CommonVars.getCurrUser()),
						ParameterType.isBigToGBK);

		String value = "";

		if (list != null && !list.isEmpty()) {

			ParameterSet para = (ParameterSet) list.get(0);

			value = para.getNameValues();

			if (value != null && !"".equals(value)) {

				CommonVars.setIsBigToGBK(Integer.valueOf(value));

			} else {

				CommonVars.setIsBigToGBK(Integer.valueOf(0));

			}

		} else {

			ParameterSet ems = new ParameterSet();

			ems.setNameValues("0");

			ems.setType(ParameterType.isBigToGBK);

			ems.setCompany(CommonVars.getCurrUser().getCompany());

			systemAction.saveValues(new Request(CommonVars.getCurrUser()), ems);

			CommonVars.setIsBigToGBK(Integer.valueOf(0));

		}

		systemAction.saveisBigToGbk(new Request(CommonVars.getCurrUser()),
				value);

	}

	/**
	 * 读取报文路径
	 */
	private void getCommonVarDir() {

		List list = null;

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.sendDir);

		ParameterSet paraSend = (ParameterSet) list.get(0);

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.recvDir);

		ParameterSet paraRec = (ParameterSet) list.get(0);

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()),
				ParameterType.recvBillDir);

		ParameterSet paraBillRec = (ParameterSet) list.get(0);

		list = systemAction
				.findnameValues(new Request(CommonVars.getCurrUser()),
						ParameterType.finallyDir);

		ParameterSet paraFinally = (ParameterSet) list.get(0);

		list = systemAction.findnameValues(
				new Request(CommonVars.getCurrUser()), ParameterType.logDir);

		ParameterSet paraLog = (ParameterSet) list.get(0);

		systemAction.saveDir(new Request(CommonVars.getCurrUser()),
				paraSend.getNameValues(), paraRec.getNameValues(),
				paraFinally.getNameValues(), paraLog.getNameValues(),
				paraBillRec.getNameValues());

	}

	/**
	 * 
	 * This method initializes cbCompanies
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbCompanies() {
		if (cbCompanies == null) {
			cbCompanies = new JComboBox();
			cbCompanies.setBounds(123, 7, 180, 21);

			cbCompanies.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {

					selectedCompany = (Company) cbCompanies.getModel()
							.getSelectedItem();
					final String companyName = selectedCompany.getName().trim();// 加工单位
					if (companyName.indexOf("缺省公司") >= 0) {// 如果当前为缺省公司
						// System.out.println("当前缺省");
						btnNameEdit.setVisible(true);
					} else {
						// System.out.println("当前缺省个毛");
						btnNameEdit.setVisible(false);
					}
				}
			});
		}
		return cbCompanies;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(55, 90, 47, 22));
			jLabel6.setText("录入人 ");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			// 自由布局
			jPanel.setLayout(null);

			jPanel.setBounds(1, 93, 363, 170);

			jPanel.setBackground(java.awt.Color.white);

			jLabel.setBounds(55, 35, 46, 19);

			jLabel.setText("登录名");

			jLabel4.setBounds(55, 62, 46, 21);

			jLabel4.setText("密    码");

			jLabel5.setBounds(55, 7, 60, 22);

			jLabel5.setText("公司名称");

			jPanel.add(getTfUserId(), null);

			jPanel.add(getTfPassword(), null);

			jPanel.add(getCbCompanies(), null);

			jPanel.add(getBtnOk(), null);

			jPanel.add(getBtnCancel(), null);

			jPanel.add(jLabel, null);

			jPanel.add(jLabel4, null);

			jPanel.add(jLabel5, null);

			jPanel.add(jLabel6, null);

			jPanel.add(getTfUserName(), null);

			jPanel.add(getBtnRegister(), null);

			jPanel.add(getBtnNameEdit(), null);

			jPanel.registerKeyboardAction(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {

					DgCompanyNameEdit nameEdit = new DgCompanyNameEdit(
							selectedCompany);
					// nameEdit.setBounds(FmLogin.this.getX()+btnNameEdit.getX(),
					// FmLogin.this.getY()+btnNameEdit.getY()+btnNameEdit.getHeight(),
					// nameEdit.getWidth(), nameEdit.getHeight());
					nameEdit.setVisible(true);
					cbCompanies.repaint();
					btnRegister.setVisible(false);
					btnOk.setVisible(true);
					btnNameEdit.setVisible(false);
				}

			},
					KeyStroke.getKeyStroke(KeyEvent.VK_E,
							InputEvent.CTRL_DOWN_MASK),
					JComponent.WHEN_IN_FOCUSED_WINDOW);
		}
		return jPanel;
	}

	class RegisterActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());
			jfc.setFileFilter(new BackupFileFilter("lns"));
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				try {
					byte[] bytes = getBytesFromFile(file);
					licenseAction.uploadLicenseFileToServer(bytes);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(FmMain.getInstance(),
							"License文件注册失败！");
					e2.printStackTrace();
					return;
				} finally {
				}
				JOptionPane.showMessageDialog(FmMain.getInstance(),
						"License文件注册成功！");
				FmMain.getInstance().dispose();
				FmLogin fmLogin = new FmLogin();
				fmLogin.setVisible(true);
			}
		}
	}

	private byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			throw new IOException("File " + file.getName() + " size " + length
					+ " is too big!");
		}

		byte[] bytes = new byte[(int) length];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		is.close();
		return bytes;
	}

	class BackupFileFilter extends FileFilter {
		private List list = new ArrayList();

		BackupFileFilter(String suffix) {
			this.addExtension(suffix);
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (isAcceptSuffix(suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			String description = "*.";
			for (int i = 0; i < list.size(); i++) {
				description += list.get(i).toString() + " & *.";
			}
			return description.substring(0, description.length() - 5);
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}

		private boolean isAcceptSuffix(String suffix) {
			boolean isAccept = false;
			for (int i = 0; i < list.size(); i++) {
				if (suffix.equals(list.get(i).toString())) {
					isAccept = true;
					break;
				}
			}
			return isAccept;
		}

		public void addExtension(String extensionName) {
			if (extensionName.equals("")) {
				return;
			}
			list.add(extensionName.toLowerCase().trim());
		}
	}

	/**
	 * This method initializes jPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JTextField getTfUserName() {
		if (tfUserName == null) {
			tfUserName = new JTextField();
			tfUserName.setBounds(new java.awt.Rectangle(122, 90, 180, 22));
			tfUserName.setEditable(false);
		}
		return tfUserName;
	}

	/** setup java chinese ime */
	private void setupJAVAIME() {
		String info = "安装【JAVA 中文繁简输入法】\n确定要继续吗？？";
		if (JOptionPane.showConfirmDialog(FmMain.getInstance(), info, "提示!!!",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
			new DownloadJAVAIMEThread().start();
		}
	}

	private void removeLoginedClientInfo(final String companyName) {
		try {
			LicenseClient.getInstance(companyName).removeLoginedClientInfo();
		} catch (Exception ex) {
			if (ex instanceof java.net.ConnectException) {
				JOptionPane.showMessageDialog(FmMain.getInstance(),
						"服务器由于关闭(人工或者程序原因)，因此不能连接服务器", "提示", 0);
				System.exit(0);
			}
		}
	}

	class DownloadJAVAIMEThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(flag, FmMain.getInstance(),
						false, null, 0);
				CommonProgress.setMessage(flag, "系统正在下载 JAVA输入法 安装文件，请稍后...");

				byte[] bytes = systemAction.downloadJAVAIME(new Request(
						CommonVars.getCurrUser()));
				if (System.getProperty("java.ext.dirs") == null) {
					CommonProgress.closeProgressDialog(flag);
					JOptionPane.showMessageDialog(FmMain.getInstance(),
							"java.ext.dirs没有值", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String[] extDirs = System.getProperty("java.ext.dirs").split(
						";");
				if (extDirs == null || extDirs.length <= 0) {
					CommonProgress.closeProgressDialog(flag);
					JOptionPane.showMessageDialog(FmMain.getInstance(),
							"java.ext.dirs没有值", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String filePath = extDirs[0] + "/javachineseime.jar";
				CommonProgress.setMessage(flag, "系统正在在安装JAVA输入法，请稍后...");
				CommonUtils.saveFileByBytes(filePath, bytes);
				CommonProgress.closeProgressDialog(flag);

				String info = "安装JAVA输入法任务完成,重启客户端程序才能生效.\n共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(FmMain.getInstance(), info, "提示",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRegister() {
		if (btnRegister == null) {
			btnRegister = new JButton();
			btnRegister.setBounds(new Rectangle(55, 128, 60, 22));
			btnRegister.setText("注册");
			btnRegister
					.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			btnRegister.setForeground(Color.red);
			btnRegister.setVisible(false);
			btnRegister.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser();
					jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());
					jfc.setFileFilter(new BackupFileFilter("lns"));
					if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = jfc.getSelectedFile();
						try {
							byte[] bytes = getBytesFromFile(file);
							licenseAction.uploadLicenseFileToServer(bytes);
							btnRegister.setVisible(false);
							btnOk.setVisible(true);
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(FmLogin.this,
									"License文件注册失败！");
							e2.printStackTrace();
							return;
						} finally {
						}
						JOptionPane.showMessageDialog(FmLogin.this,
								"License文件注册成功！");
					}
				}
			});
		}
		return btnRegister;
	}

	/**
	 * This method initializes btnNameEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNameEdit() {
		if (btnNameEdit == null) {
			btnNameEdit = new JButton();
			btnNameEdit.setBounds(new Rectangle(120, 125, 118, 23));
			btnNameEdit.setText("修改公司名称");

			btnNameEdit.setVisible(false);

			btnNameEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCompanyNameEdit nameEdit = new DgCompanyNameEdit(
							selectedCompany);
					// nameEdit.setBounds(FmLogin.this.getX()+btnNameEdit.getX(),
					// FmLogin.this.getY()+btnNameEdit.getY()+btnNameEdit.getHeight(),
					// nameEdit.getWidth(), nameEdit.getHeight());
					nameEdit.setVisible(true);
					cbCompanies.repaint();
					btnRegister.setVisible(false);
					btnOk.setVisible(true);
					btnNameEdit.setVisible(false);
				}
			});
		}
		return btnNameEdit;
	}

}
