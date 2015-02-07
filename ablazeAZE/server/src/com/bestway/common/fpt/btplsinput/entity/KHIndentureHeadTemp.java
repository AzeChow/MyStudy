/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common.fpt.btplsinput.entity;

import java.util.Date;

/**
 * 导入JBCUS发货数据
 *
 * @author lzj
 */
public class KHIndentureHeadTemp {

    /**
     * 供应商代码
     */
    private String companyCode;
    /**
     * 供应商名称
     */
    private String companyName;
    /**
     * 客户代码
     */
    private String khCode;
    /**
     * 客户名称
     */
    private String khName;
    /**
     * 单据号
     */
    private String indentureNO;                 //单据号
    /**
     * 订单号
     */
    private String orderNO;                     //订单号
    /**
     * 关封号
     */
    private String coverNO;                     //关封号
    /**
     * JBCUS是否下载过
     */
    private Boolean isJBCUSDown;

    public String getIndentureNO() {
        return indentureNO;
    }

    public void setIndentureNO(String indentureNO) {
        this.indentureNO = indentureNO;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getCoverNO() {
        return coverNO;
    }

    public void setCoverNO(String coverNO) {
        this.coverNO = coverNO;
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
    
    
    
}
