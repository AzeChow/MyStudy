package com.bestway.common.fpt.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 商品单价维护
 * 
 * @author Administrator
 * 
 */
public class FptEmailParamver extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * pop服务器
	 */
	private String popServer = null;
	/**
	 * smtp服务器
	 */
	private String smptServer = null;
	/**
	 * 用户名
	 */
	private String userName = null;
	/**
	 * 用户密码
	 */
	private String password = null;
	/**
	 * 用户地址
	 */
	private String myEmailAdress = null;
	/**
	 * 发送邮件服务端口号
	 */
	private String smtpport = null;
	/**
	 * 
	 * 接收邮件服务端口号
	 */

	/**
	 * 身份验证
	 * 
	 */
	private Boolean isAuthenticator = false;
	
	
	private String poppport = null;
	/**
	 * 服务器类型pop3 IMAP HTTP
	 */
	public String type = null;
	public static final String pop3type = "0";
	public static final String IMAPtype = "1";
	public static final String HTTPtype = "2";
	/**
	 * 显示的中文或英文名
	 */
	private String showName = null;

	public String getPopServer() {
		return popServer;
	}

	public void setPopServer(String popServer) {
		this.popServer = popServer;
	}

	public String getSmptServer() {
		return smptServer;
	}

	public void setSmptServer(String smptServer) {
		this.smptServer = smptServer;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMyEmailAdress() {
		return myEmailAdress;
	}

	public void setMyEmailAdress(String myEmailAdress) {
		this.myEmailAdress = myEmailAdress;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getSmtpport() {
		return smtpport;
	}

	public void setSmtpport(String smtpport) {
		this.smtpport = smtpport;
	}

	public String getPoppport() {
		return poppport;
	}

	public void setPoppport(String poppport) {
		this.poppport = poppport;
	}

	/**
	 * @return the isAuthenticator
	 */
	public Boolean getIsAuthenticator() {
		return isAuthenticator;
	}

	/**
	 * @param isAuthenticator the isAuthenticator to set
	 */
	public void setIsAuthenticator(Boolean isAuthenticator) {
		this.isAuthenticator = isAuthenticator;
	}


}
