package com.bestway.pis.entity;

import java.io.Serializable;
import java.util.Date;

public class AclUserTemp implements Serializable, Cloneable {

	private String id;
	
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
     * 最后登录日期
     */
    private Date lastlogin = null;
    
    private Date createDate = null;

    /**
     * 为"S"时为超级用户
     */
    private String userFlag = null;

    /**
     * 风格 OCEAN_THEME = 0; // 海兰色 STEEL_THEME = 1; // 铁色 AQUA_THEME = 2; // 海蓝宝石
     * SANDSTONE_THEME = 3; // 沙岩色 EMERALD_THEME = 4; // 翠绿色
     */
    private Integer style = null;

    /**
     * 菜单导航类型
     */
    private Integer menuNavigationType ;

    /**
     * 是否禁用
     */
    private Boolean isForbid = false;
    /**
     * 是否显示Logo
     */
    private Boolean isShowLogoArea = true;

    /**
     * 公司编码
     */
    private String companyCode = null; //
    /**
     * 电子邮箱
     */
    private String email = null;

    public AclUserTemp() {
    }

    public AclUserTemp(java.lang.String loginName) {
        this.loginName = loginName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return Returns the lastlogin.
     */
    public Date getLastlogin() {
        return lastlogin;
    }

    /**
     * @param lastlogin The lastlogin to set.
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
     * @param loginName The loginName to set.
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * @param password The password to set.
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
     * @param userFlag The superFlag to set.
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

    public Integer getMenuNavigationType() {
        return menuNavigationType;
    }

    public void setMenuNavigationType(Integer menuNavigationType) {
        this.menuNavigationType = menuNavigationType;
    }

    public Boolean getIsForbid() {
        return isForbid;
    }

    public void setIsForbid(Boolean isForbid) {
        this.isForbid = isForbid;
    }

    public Boolean getIsShowLogoArea() {
        return isShowLogoArea;
    }

    public void setIsShowLogoArea(Boolean isShowLogoArea) {
        this.isShowLogoArea = isShowLogoArea;
    }

    public String getUserFlagCN() {
        if ((userFlag != null) && userFlag.trim().equals("S")) {
            return "超级用户";
        } else {
            return "普通用户";
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}
