package com.bestway.common.authority.entity;

import java.security.Principal;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 用户信息
 * 
 * @author Administrator
 * 
 */
public class AclUser extends BaseScmEntity implements Principal {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 登陆名称
	 */
	private String loginName = null;

	/**
	 * 密码
	 */
	private String password = null;

	/**
	 * 用户名称
	 */
	private String userName = null;

	/**
	 * Email
	 */
	private String email = null;

	/**
	 * 修改日期
	 */
	private Date modified = null;

	/**
	 * 创建日期
	 */
	private Date createDate = null;

	/**
	 * 最后登陆日期
	 */
	private Date lastlogin = null;

	/**
	 * 为"S"时为超级用户
	 */
	private String userFlag = null;

	/**
	 * 风格
	 * OCEAN_THEME = 0; // 海兰色 STEEL_THEME = 1; // 铁色 AQUA_THEME = 2; // 海蓝宝石
	 * SANDSTONE_THEME = 3; // 沙岩色 EMERALD_THEME = 4; // 翠绿色
	 */
	private Integer style = null;

	/**
	 * 是否是菜单导航
	 */
	private Boolean isMenuNavigation = false;

	/**
	 * 是否禁用
	 */
	private Boolean isForbid = false;

	/**
	 * 当前ＩＰ
	 */
	private String currIP = null;

	// private BaseCompany company = null; //公司

	public AclUser() {
	}

	public AclUser(java.lang.String loginName) {
		this.loginName = loginName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the lastlogin.
	 */
	public Date getLastlogin() {
		return lastlogin;
	}

	/**
	 * @param lastlogin
	 *            The lastlogin to set.
	 */
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	/**
	 * @return Returns the loginName.
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            The loginName to set.
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return Returns the modified.
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * @param modified
	 *            The modified to set.
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	//
	// public BaseCompany getCompany() {
	// return company;
	// }
	// public void setCompany(BaseCompany company) {
	// this.company = company;
	// }
	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * getName 是实现的Principal接口的方法
	 * 
	 * @return String
	 */
	public String getName() {
		return this.getId().toString();
	}

	/**
	 * @return Returns the userFlag.
	 */
	public String getUserFlag() {
		return userFlag;
	}

	/**
	 * @param userFlag
	 *            The superFlag to set.
	 */
	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	/**
	 * @return Returns the version.
	 */
	/**
	 * @return Returns the version.
	 */

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public String getCurrIP() {
		return currIP;
	}

	public void setCurrIP(String currIP) {
		this.currIP = currIP;
	}

	public Boolean getIsMenuNavigation() {
		return isMenuNavigation;
	}

	public void setIsMenuNavigation(Boolean isMenuNavigation) {
		this.isMenuNavigation = isMenuNavigation;
	}

	public Boolean getIsForbid() {
		return isForbid;
	}

	public void setIsForbid(Boolean isForbid) {
		this.isForbid = isForbid;
	}
}
