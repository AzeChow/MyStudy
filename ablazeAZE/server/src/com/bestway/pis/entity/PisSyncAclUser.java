package com.bestway.pis.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 同步账号esp
 *
 */
public class PisSyncAclUser extends BaseScmEntity {

    /**
     * 同步错误信息
     */
    private String info;

    /**
     * 同步状态
     */
    private String syncState;

    /**
     * 登陆名称
     */
    private String loginName = null;

    /**
     * 用户id
     */
    private String userId = null;

    /**
     * 密码
     */
    private String password = null;

    /**
     * 用户名称
     */
    private String userName = null;

    /**
     * 为"S"时为超级用户
     */
    private String userFlag = null;

    /**
     * 公司编码
     */
    private String companyCode = null; //
    /**
     * 电子邮箱
     */
    private String email = null;

    /**
     * 是否禁用
     */
    private Boolean isForbid = false;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSyncState() {
        return syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFlagCN() {
        if ((userFlag != null) && userFlag.trim().equals("S")) {
            return "超级用户";
        } else {
            return "普通用户";
        }
    }

    public Boolean isIsForbid() {
        return isForbid;
    }

    public void setIsForbid(Boolean isForbid) {
        this.isForbid = isForbid;
    }

}
