/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 数据源
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JDBCDataSource extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
											.getSerialVersionUID();
	/**
	 * 数据源名称
	 */			
	private String				name;												// 数据源名称
	/**
	 * 连接字符串
	 */
	private String				url;												// 连接字符串
	/**
	 * 用户名
	 */
	private String				userName;
	/**
	 * 密码
	 */
	private String				password;
	/**
	 * 驱动器名
	 */
	private String				driverClassName;

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the driverClassName.
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * @param driverClassName
	 *            The driverClassName to set.
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
	 * @param password
	 *            The password to set.
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
	 * @param url
	 *            The url to set.
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
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
