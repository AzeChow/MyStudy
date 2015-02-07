/*
 * Created on 2004-12-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.license.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.license.LicenseInfoCache;
import com.bestway.bcus.license.entity.ClientInfo;
import com.bestway.bcus.license.entity.LicenseDetailInfo;
import com.bestway.bcus.license.entity.LicenseInfo;
import com.bestway.bcus.license.logic.LicenseLogic;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LicenseActionImpl implements LicenseAction {
	private LicenseLogic licenseLogic = null;

	/**
	 * 请求刷新在服务器端注册的客户端信息(在客户端登陆时)
	 * 
	 * @param clientInfo
	 */
	public ClientInfo refreshLoginedClientInfo(ClientInfo clientInfo) {
		this.licenseLogic.refreshLoginedClientInfo(clientInfo);
		return clientInfo;
	}

	/**
	 * 请求通过licese验证
	 * 
	 * @param clientInfo
	 * @return
	 */
	public ClientInfo loginClientInfo(ClientInfo clientInfo) {
		return this.licenseLogic.loginClientInfo(clientInfo);
	}
	/**
	 * 判断是否有Llicense文件
	 * 
	 * @param clientInfo
	 * @return
	 */
	public boolean checkHasLicense(ClientInfo clientInfo) {
		return this.licenseLogic.checkHasLicense(clientInfo);
	}
	/**
	 * 请求通过licese验证(在客户端登陆时)
	 * 
	 * @param clientInfo
	 * @return
	 */
	public int checkLicense(ClientInfo clientInfo) {
		return this.licenseLogic.checkLicense(clientInfo);
	}

	/**
	 * 请求删除在服务器端注册的客户端信息(在客户端退出时)
	 * 
	 * @param clientInfo
	 */
	public void removeLoginedClientInfo(ClientInfo clientInfo) {
		this.licenseLogic.removeLoginedClientInfo(clientInfo);
	}

	/**
	 * @return Returns the licenseLogic.
	 */
	public LicenseLogic getLicenseLogic() {
		return licenseLogic;
	}

	/**
	 * @param licenseLogic
	 *            The licenseLogic to set.
	 */
	public void setLicenseLogic(LicenseLogic licenseLogic) {
		this.licenseLogic = licenseLogic;
	}

	/**
	 * 取得license信息
	 * 
	 * @return
	 */
	public LicenseInfo getLicenseInfo() {
		return this.licenseLogic.getLicenseInfo();
	}

	/**
	 * 没有给客户授权的模块
	 * 
	 * @return
	 */
	public List getHasModules(String companyName) {
		return this.licenseLogic.getHasModules(companyName);
	}

	/**
	 * 检查是否有权利使用深加工结转的内部管理功能
	 * 
	 * @return
	 */
	public boolean checkFptManagePermisson(String companyName) {
		return this.licenseLogic.checkFptManagePermisson(companyName);
	}
	/**
	 * 检查是否有权利使用DB导入接口
	 * 
	 * @return
	 */
	public boolean checkDBImportPermisson(String companyName) {
		return this.licenseLogic.checkDBImportPermisson(companyName);
	}

	/**
	 * 检查是否有权利使用文本导入接口
	 * 
	 * @return
	 */
	public boolean checkFileImportPermisson(String companyName) {
		return this.licenseLogic.checkFileImportPermisson(companyName);
	}
	/**
	 * 从客户端上传license文件到服务器端
	 * 
	 * @param fileContent
	 */
	public void uploadLicenseFileToServer(byte[] fileContent) {
		this.licenseLogic.uploadLicenseFileToServer(fileContent);
	}

	/**
	 * 获取注册的license详细信息
	 * 
	 * @return
	 */
	public List getLicenseInfoString() {
		return this.licenseLogic.getLicenseInfoString();
	}
}