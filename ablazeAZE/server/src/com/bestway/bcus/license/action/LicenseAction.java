/*
 * Created on 2004-12-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.license.action;

import java.util.List;

import com.bestway.bcus.license.entity.ClientInfo;
import com.bestway.bcus.license.entity.LicenseInfo;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface LicenseAction {
	/**
	 * 请求刷新在服务器端注册的客户端信息(在客户端登陆时)
	 * @param clientInfo
	 */
	ClientInfo refreshLoginedClientInfo(ClientInfo clientInfo);
	/**
	 * 请求通过licese验证
	 * @param clientInfo
	 * @return
	 */
	ClientInfo loginClientInfo(ClientInfo clientInfo);
	/**
	 * 判断是否有Llicense文件
	 * 
	 * @param clientInfo
	 * @return
	 */
	boolean checkHasLicense(ClientInfo clientInfo) ;
	/**
	 * 请求通过licese验证(在客户端登陆时)
	 * @param clientInfo
	 * @return
	 */
	int checkLicense(ClientInfo clientInfo) ;
	/**
	 * 请求删除在服务器端注册的客户端信息(在客户端退出时)
	 * @param clientInfo
	 */
	void removeLoginedClientInfo(ClientInfo clientInfo);
	/**
	 * 取得license信息
	 * @return
	 */
	LicenseInfo getLicenseInfo();
	
	/**
	 * 没有给客户授权的模块
	 * @return
	 */
	List getHasModules(String companyName) ;
	/**
	 * 检查是否有权利使用深加工结转的内部管理功能
	 * 
	 * @return
	 */
	boolean checkFptManagePermisson(String companyName);
	/**
	 * 检查是否有权利使用DB导入接口
	 * 
	 * @return
	 */
	boolean checkDBImportPermisson(String companyName) ;

	/**
	 * 检查是否有权利使用文本导入接口
	 * 
	 * @return
	 */
	boolean checkFileImportPermisson(String companyName) ;
	/**
	 * 从客户端上传license文件到服务器端
	 * 
	 * @param fileContent
	 */
	void uploadLicenseFileToServer(byte[] fileContent) ;
	
	/**
	 * 获取注册的license详细信息
	 * @return
	 */
	List getLicenseInfoString() ;
}
