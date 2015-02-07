/*
 * Created on 2004-12-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.license;

import java.util.List;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.license.action.LicenseAction;
import com.bestway.bcus.license.entity.ClientInfo;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class LicenseClient implements Runnable {
	private static LicenseClient licenseClient = null;

	private static LicenseAction licenseAction = null;

	private static ClientInfo clientInfo = null;

	private int interval = 300000;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// 
		while (true) {
			licenseAction.refreshLoginedClientInfo(clientInfo);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static LicenseClient getInstance(String companyName) {
		if (licenseClient == null) {
			licenseClient = new LicenseClient();
			licenseAction = (LicenseAction) CommonVars.getApplicationContext()
					.getBean("licenseAction");
		}
		if (clientInfo == null) {
			// InetAddress inetAddress = null;
			// try {
			// inetAddress = InetAddress.getLocalHost();
			// } catch (UnknownHostException e) {
			// e.printStackTrace();
			// }
			clientInfo = new ClientInfo();
			// clientInfo.setClientIP(inetAddress.getHostAddress());
			// clientInfo.setClientName(inetAddress.getHostName());
		}
		if (companyName != null) {
			clientInfo.setCompanyName(companyName.trim());
		} else {
			clientInfo.setCompanyName("");
		}
		return licenseClient;
	}

	private void loginClientInfo() {
		clientInfo = licenseAction.loginClientInfo(clientInfo);
	}
	/**
	 * 判断公司是否有license文件
	 * @return
	 */
	public boolean checkHasLicense() {
		return licenseAction.checkHasLicense(clientInfo);
	}

	public int checkLicense() {
		int result = licenseAction.checkLicense(clientInfo);
		if (result > -1) {
			this.loginClientInfo();
		}
		return result;
	}

	public void removeLoginedClientInfo() {
		licenseAction.removeLoginedClientInfo(clientInfo);
//		System.out.print("-------------------removeLoginedClientInfo");
	}

	/**
	 * 有给客户授权的模块
	 * 
	 * @return
	 */
	public List getHasModules() {
		return licenseAction.getHasModules(clientInfo.getCompanyName());
	}

	/**
	 * 检查是否有权利使用深加工结转的内部管理功能
	 * 
	 * @return
	 */
	public boolean checkFptManagePermisson() {
		return licenseAction.checkFptManagePermisson(clientInfo
				.getCompanyName());
	}

	/**
	 * 检查是否有权利使用DB导入接口
	 * 
	 * @return
	 */
	public boolean checkDBImportPermisson() {
		return licenseAction
				.checkDBImportPermisson(clientInfo.getCompanyName());
	}

	/**
	 * 检查是否有权利使用文本导入接口
	 * 
	 * @return
	 */
	public boolean checkFileImportPermisson() {
		return licenseAction.checkFileImportPermisson(clientInfo
				.getCompanyName());
	}

}