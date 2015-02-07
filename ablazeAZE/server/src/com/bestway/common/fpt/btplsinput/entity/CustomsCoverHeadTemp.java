/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common.fpt.btplsinput.entity;

import java.util.Date;

/**
 * 导入JBCUS结转申请表头数据
 *
 * @author Administrator
 */
public class CustomsCoverHeadTemp {

    /**
     * 表头Id
     */
    private String id;
    /**
     * 供应商代码 -- 转出
     */
    private String companyCode;
    /**
     * 供应商名称
     */
    private String companyName;
    /**
     * 客户代码 --转入
     */
    private String khCode;
    /**
     * 客户名称
     */
    private String khName;
    /**
     * 结转申请关封号
     */
    private String coverNO;
    /**
     * 结转进口企业名称
     */
    private String rollInCompany;
    /**
     * 结转出口企业名称
     */
    private String rollOutCompany;
    /**
     * 转入海关
     */
    private String rollInCustoms;
    /**
     * 转出海关
     */
    private String rollOutCustoms;
    /**
     * 结转申请表有效开始时间
     */
    private Date startDate;
    /**
     * 结转申请表有效结束时间
     */
    private Date expiryDate;
    /**
     * 结转申请表申报状态
     */
    private String declareState = null;

    /**
     * JBCUS是否下载过
     */
    private Boolean isJBCUSDown;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoverNO() {
        return coverNO;
    }

    public void setCoverNO(String coverNO) {
        this.coverNO = coverNO;
    }

    public String getRollInCompany() {
        return rollInCompany;
    }

    public void setRollInCompany(String rollInCompany) {
        this.rollInCompany = rollInCompany;
    }

    public String getRollOutCompany() {
        return rollOutCompany;
    }

    public void setRollOutCompany(String rollOutCompany) {
        this.rollOutCompany = rollOutCompany;
    }

    public String getRollInCustoms() {
        return rollInCustoms;
    }

    public void setRollInCustoms(String rollInCustoms) {
        this.rollInCustoms = rollInCustoms;
    }

    public String getRollOutCustoms() {
        return rollOutCustoms;
    }

    public void setRollOutCustoms(String rollOutCustoms) {
        this.rollOutCustoms = rollOutCustoms;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getIsJBCUSDown() {
        return isJBCUSDown;
    }

    public void setIsJBCUSDown(Boolean isJBCUSDown) {
        this.isJBCUSDown = isJBCUSDown;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getKhCode() {
        return khCode;
    }

    public void setKhCode(String khCode) {
        this.khCode = khCode;
    }

    public String getKhName() {
        return khName;
    }

    public void setKhName(String khName) {
        this.khName = khName;
    }
    /**
     * 结转申请表申报状态
     */
    public String getDeclareState() {
		return declareState;
	}
    /**
     * 结转申请表申报状态
     */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CustomsCoverHeadTemp other = (CustomsCoverHeadTemp) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
