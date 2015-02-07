/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common.fpt.btplsinput.entity;

import java.util.Date;

/**
 * 收发货实体类表体
 *
 * @author lzj
 */
public class KHIndentureTemp {

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
     * 收到货的时间
     */
    private Date recieveDate;                   //收到货的时间
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
    /**
     * 收货数量
     */
    private Double compIndentureQuantity;
    /**
     * 报关数量
     */
    private Double compBgQuantity;
    /**
     * 转入单价
     */
    private Double khPrice;
    /**
     * 转入金额
     */
    private Double khTotalMoney;
    /**
     * JBCUS是否下载过
     */
    private Boolean isJBCUSDown;

    public Double getCompBgQuantity() {
        return compBgQuantity;
    }

    public void setCompBgQuantity(Double khQuantity) {
        this.compBgQuantity = khQuantity;
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

    public Date getRecieveDate() {
        return recieveDate;
    }

    public void setRecieveDate(Date recieveDate) {
        this.recieveDate = recieveDate;
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

    public Double getKhPrice() {
        return khPrice;
    }

    public void setKhPrice(Double khPrice) {
        this.khPrice = khPrice;
    }

    public Double getKhTotalMoney() {
        return khTotalMoney;
    }

    public void setKhTotalMoney(Double khTotalMoney) {
        this.khTotalMoney = khTotalMoney;
    }

    public Double getCompIndentureQuantity() {
        return compIndentureQuantity;
    }

    public void setCompIndentureQuantity(Double compIndentureQuantity) {
        this.compIndentureQuantity = compIndentureQuantity;
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
