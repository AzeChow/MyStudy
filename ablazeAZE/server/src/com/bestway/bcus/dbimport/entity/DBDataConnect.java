/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 数据库连接
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DBDataConnect extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 连接字符串
	 */
    private String url;         
    /**
     * 用户名
     */
	private String userName;  
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 服务器名
	 */
	private String serverName;
	/**
	 * 数据库名称
	 */
	private String dataName; 
	/**
	 * 标识
	 */
	private String flat;
	/**
	 * 驱动器名
	 */
	private String driverClassName;

	/**
	 * @return Returns the driverClassName.
	 */
	public String getDriverClassName() {
		return driverClassName;
	}
	/**
	 * @param driverClassName The driverClassName to set.
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the flat.
	 */
	public String getFlat() {
		return flat;
	}
	/**
	 * @param flat The flat to set.
	 */
	public void setFlat(String flat) {
		this.flat = flat;
	}
	/**
	 * @return Returns the dataName.
	 */
	public String getDataName() {
		return dataName;
	}
	/**
	 * @param dataName The dataName to set.
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	/**
	 * @return Returns the serverName.
	 */
	public String getServerName() {
		return serverName;
	}
	/**
	 * @param serverName The serverName to set.
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
