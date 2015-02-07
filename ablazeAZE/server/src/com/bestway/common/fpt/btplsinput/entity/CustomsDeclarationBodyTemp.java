package com.bestway.common.fpt.btplsinput.entity;

/**
 * 导入JBCUS报关单表体
 *
 * @author lzj
 */
public class CustomsDeclarationBodyTemp {

    /**
     * 报关单表头ID
     */
    private CustomsDeclarationHeadTemp customsDeclarationHeadTemp;
    /**
     * 备案序号
     */
    private Integer commSerialNo;
    /**
     * 商品流水号
     */
    private Integer seqNo;
    /**
     * 商品编码
     */
    private String complex;
    /**
     * 商品名称
     */
    private String commName;
    /**
     * 商品规格
     */
    private String commSpec;
    /**
     * 商品数量
     */
    private Double commAmount;
    /**
     * 商品单价
     */
    private Double commUnitPrice;
    /**
     * 商品总价
     */
    private Double commTotalPrice;
    /**
     * 单位
     */
    private String unit;
    /**
     * 法定单位
     */
    private String legalUnit;
    /**
     * 第二法定单位
     */
    private String secondLegalUnit;
    /**
     * 第一法定数量
     */
    private Double firstAmount;
    /**
     * 第二法定数量
     */
    private Double secondAmount;
    /**
     * 原产国
     */
    private String country;
    /**
     * 用途
     */
    private String uses;
    /**
     * 减免方式
     */
    private String levyMode;
    /**
     * 毛重
     */
    private Double grossWeight;
    /**
     * 净重
     */
    private Double netWeight;

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public Double getCommAmount() {
        return commAmount;
    }

    public void setCommAmount(Double commAmount) {
        this.commAmount = commAmount;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public Integer getCommSerialNo() {
        return commSerialNo;
    }

    public void setCommSerialNo(Integer commSerialNo) {
        this.commSerialNo = commSerialNo;
    }

    public String getCommSpec() {
        return commSpec;
    }

    public void setCommSpec(String commSpec) {
        this.commSpec = commSpec;
    }

    public Double getCommTotalPrice() {
        return commTotalPrice;
    }

    public void setCommTotalPrice(Double commTotalPrice) {
        this.commTotalPrice = commTotalPrice;
    }

    public Double getCommUnitPrice() {
        return commUnitPrice;
    }

    public void setCommUnitPrice(Double commUnitPrice) {
        this.commUnitPrice = commUnitPrice;
    }

    public String getComplex() {
        return complex;
    }

    public void setComplex(String complex) {
        this.complex = complex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(Double firstAmount) {
        this.firstAmount = firstAmount;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getSecondAmount() {
        return secondAmount;
    }

    public void setSecondAmount(Double secondAmount) {
        this.secondAmount = secondAmount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLegalUnit() {
        return legalUnit;
    }

    public void setLegalUnit(String legalUnit) {
        this.legalUnit = legalUnit;
    }

    public String getSecondLegalUnit() {
        return secondLegalUnit;
    }

    public void setSecondLegalUnit(String secondLegalUnit) {
        this.secondLegalUnit = secondLegalUnit;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public String getLevyMode() {
        return levyMode;
    }

    public void setLevyMode(String levyMode) {
        this.levyMode = levyMode;
    }

    public CustomsDeclarationHeadTemp getCustomsDeclarationHeadTemp() {
        return customsDeclarationHeadTemp;
    }

    public void setCustomsDeclarationHeadTemp(CustomsDeclarationHeadTemp customsDeclarationHeadTemp) {
        this.customsDeclarationHeadTemp = customsDeclarationHeadTemp;
    }
}
