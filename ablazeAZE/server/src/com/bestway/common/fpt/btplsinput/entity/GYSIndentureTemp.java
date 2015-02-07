/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common.fpt.btplsinput.entity;

import java.util.Date;

/**
 * 导入JBCUS发货数据表体
 *
 * @author lzj
 */
public class GYSIndentureTemp {

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
     * 发货日期
     */
    private Date indentureDate;                 //发货时间
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
    //===================================以下为转入(收货)资料============================
    /**
     * 转入料号
     */
    private String compMatNO;
    /**
     * 转入物料名称
     */
    private String compMatName;
    /**
     * 转入物料规格型号
     */
    private String compMatType;
    /**
     * 转入物料单位
     */
    private String compMatUnit;
    /**
     * 转入商品备案序号
     */
    private Integer compGoodsBackNO;
    /**
     * 转入商品编码
     */
    private String compGoodsNO;
    /**
     * 转入商品名称
     */
    private String compGoodsName;
    /**
     * 转入商品规格型号
     */
    private String compGoodsType;
    /**
     * 转入商品单位编码
     */
    private String compGoodsUnitCode;
    /**
     * 转入商品单位名称
     */
    private String compGoodsUnitName;
    /**
     * 转入商品手册号
     */
    private String compHandBookNO;
    /**
     * 转入商品单重
     */
    private Double compWeight;
    /**
     * 转入商品与物料折算比
     */
    private Double compCorrected;
    //==================================以下为转出(发货)资料==================
    /**
     * 转出料号
     */
    private String applMatNO;
    /**
     * 转出物料名称
     */
    private String applMatName;
    /**
     * 转出物料规格型号
     */
    private String applMatType;
    /**
     * 转出物料单位
     */
    private String applMatUnit;
    /**
     * 转出商品备案序号
     */
    private Integer applGoodsBackNO;
    /**
     * 转出商品编码
     */
    private String applGoodsNO;
    /**
     * 转出商品名称
     */
    private String applGoodsName;
    /**
     * 转出商品规格型号
     */
    private String applGoodsType;
    /**
     * 转出商品单位编码
     */
    private String applGoodsUnitCode;
    /**
     * 转出商品单位名称
     */
    private String applGoodsUnitName;
    /**
     * 转出商品单位名称
     */
    private String applGoodsUnitNameCode;
    /**
     * 转出商品手册号
     */
    private String applHandBookNO;
    /**
     * 转出商品单重
     */
    private Double applWeight;
    /**
     * 转出商品与物料折算比
     */
    private Double applCorrected;
    /**
     * 转出方与转入方工厂单位折算比
     */
    private Double applCalCorrected;
    /**
     * 发货数量
     */
    private Double applIndentureQuantity;
    /**
     * 报关数量
     */
    private Double applBgQuantity;
    /**
     * 转出单价
     */
    private Double gysPrice;
    /**
     * 转出金额
     */
    private Double gysTotalMoney;
    /**
     * JBCUS是否下载过
     */
    private Boolean isJBCUSDown;

    public Double getApplCalCorrected() {
        return applCalCorrected;
    }

    public void setApplCalCorrected(Double applCalCorrected) {
        this.applCalCorrected = applCalCorrected;
    }

    public Double getApplBgQuantity() {
        return applBgQuantity;
    }

    public void setApplBgQuantity(Double gysQuantity) {
        this.applBgQuantity = gysQuantity;
    }

    public Date getIndentureDate() {
        return indentureDate;
    }

    public void setIndentureDate(Date indentureDate) {
        this.indentureDate = indentureDate;
    }

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

    public String getCompMatNO() {
        return compMatNO;
    }

    public void setCompMatNO(String compMatNO) {
        this.compMatNO = compMatNO;
    }

    public String getCompMatName() {
        return compMatName;
    }

    public void setCompMatName(String compMatName) {
        this.compMatName = compMatName;
    }

    public String getCompMatType() {
        return compMatType;
    }

    public void setCompMatType(String compMatType) {
        this.compMatType = compMatType;
    }

    public String getCompMatUnit() {
        return compMatUnit;
    }

    public void setCompMatUnit(String compMatUnit) {
        this.compMatUnit = compMatUnit;
    }

    public Integer getCompGoodsBackNO() {
        return compGoodsBackNO;
    }

    public void setCompGoodsBackNO(Integer compGoodsBackNO) {
        this.compGoodsBackNO = compGoodsBackNO;
    }

    public String getCompGoodsNO() {
        return compGoodsNO;
    }

    public void setCompGoodsNO(String compGoodsNO) {
        this.compGoodsNO = compGoodsNO;
    }

    public String getCompGoodsName() {
        return compGoodsName;
    }

    public void setCompGoodsName(String compGoodsName) {
        this.compGoodsName = compGoodsName;
    }

    public String getCompGoodsType() {
        return compGoodsType;
    }

    public void setCompGoodsType(String compGoodsType) {
        this.compGoodsType = compGoodsType;
    }

    public String getCompGoodsUnitCode() {
        return compGoodsUnitCode;
    }

    public void setCompGoodsUnitCode(String compGoodsUnitCode) {
        this.compGoodsUnitCode = compGoodsUnitCode;
    }

    public String getCompGoodsUnitName() {
        return compGoodsUnitName;
    }

    public void setCompGoodsUnitName(String compGoodsUnitName) {
        this.compGoodsUnitName = compGoodsUnitName;
    }

    public String getCompHandBookNO() {
        return compHandBookNO;
    }

    public void setCompHandBookNO(String compHandBookNO) {
        this.compHandBookNO = compHandBookNO;
    }

    public String getApplMatNO() {
        return applMatNO;
    }

    public void setApplMatNO(String applMatNO) {
        this.applMatNO = applMatNO;
    }

    public String getApplMatName() {
        return applMatName;
    }

    public void setApplMatName(String applMatName) {
        this.applMatName = applMatName;
    }

    public String getApplMatType() {
        return applMatType;
    }

    public void setApplMatType(String applMatType) {
        this.applMatType = applMatType;
    }

    public String getApplMatUnit() {
        return applMatUnit;
    }

    public void setApplMatUnit(String applMatUnit) {
        this.applMatUnit = applMatUnit;
    }

    /**
     * 转出商品备案序号
     */
    public Integer getApplGoodsBackNO() {
        return applGoodsBackNO;
    }

    /**
     * 转出商品备案序号
     */
    public void setApplGoodsBackNO(Integer applGoodsBackNO) {
        this.applGoodsBackNO = applGoodsBackNO;
    }

    /**
     * 转出商品编码
     */
    public String getApplGoodsNO() {
        return applGoodsNO;
    }

    /**
     * 转出商品编码
     */
    public void setApplGoodsNO(String applGoodsNO) {
        this.applGoodsNO = applGoodsNO;
    }

    public String getApplGoodsName() {
        return applGoodsName;
    }

    public void setApplGoodsName(String applGoodsName) {
        this.applGoodsName = applGoodsName;
    }

    public String getApplGoodsType() {
        return applGoodsType;
    }

    public void setApplGoodsType(String applGoodsType) {
        this.applGoodsType = applGoodsType;
    }

    public String getApplGoodsUnitCode() {
        return applGoodsUnitCode;
    }

    public void setApplGoodsUnitCode(String applGoodsUnitCode) {
        this.applGoodsUnitCode = applGoodsUnitCode;
    }

    public String getApplGoodsUnitName() {
        return applGoodsUnitName;
    }

    public void setApplGoodsUnitName(String applGoodsUnitName) {
        this.applGoodsUnitName = applGoodsUnitName;
    }

    public String getApplHandBookNO() {
        return applHandBookNO;
    }

    public void setApplHandBookNO(String applHandBookNO) {
        this.applHandBookNO = applHandBookNO;
    }

    public Double getApplWeight() {
        return applWeight;
    }

    public void setApplWeight(Double applWeight) {
        this.applWeight = applWeight;
    }

    public Double getApplCorrected() {
        return applCorrected;
    }

    public void setApplCorrected(Double applCorrected) {
        this.applCorrected = applCorrected;
    }

    public Double getGysPrice() {
        return gysPrice;
    }

    public void setGysPrice(Double gysPrice) {
        this.gysPrice = gysPrice;
    }

    public Double getGysTotalMoney() {
        return gysTotalMoney;
    }

    public void setGysTotalMoney(Double gysTotalMoney) {
        this.gysTotalMoney = gysTotalMoney;
    }

    public Double getApplIndentureQuantity() {
        return applIndentureQuantity;
    }

    public void setApplIndentureQuantity(Double applIndentureQuantity) {
        this.applIndentureQuantity = applIndentureQuantity;
    }

    public Double getCompCorrected() {
        return compCorrected;
    }

    public void setCompCorrected(Double compCorrected) {
        this.compCorrected = compCorrected;
    }

    public Double getCompWeight() {
        return compWeight;
    }

    public void setCompWeight(Double compWeight) {
        this.compWeight = compWeight;
    }

    public String getApplGoodsUnitNameCode() {
        return applGoodsUnitNameCode;
    }

    public void setApplGoodsUnitNameCode(String applGoodsUnitNameCode) {
        this.applGoodsUnitNameCode = applGoodsUnitNameCode;
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
