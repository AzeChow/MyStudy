package com.bestway.common.tools.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * FTP配置信息
 * @author Administrator
 *
 */
public class FtpConfig extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 * FTP 服务名称
	 */
	private String				server				= null;
	/**
	 * FTP 用户密码
	 */
	private String				password			= null;
	/**
	 * FTP 用户名
	 */
	private String				userName			= null;
	/**
	 * 远程目录
	 */
	private String				remoteDir			= null;
	/**
	 * 报表文件前缀名
	 */
	private String				prefixReportName	= null;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemoteDir() {
		return remoteDir;
	}
	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPrefixReportName() {
		return prefixReportName;
	}
	public void setPrefixReportName(String prefixReportName) {
		this.prefixReportName = prefixReportName;
	}
}