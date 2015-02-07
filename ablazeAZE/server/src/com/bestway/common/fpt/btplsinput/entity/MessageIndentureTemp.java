/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common.fpt.btplsinput.entity;

/**
 * 收发货表体实体同步至JBCUS
 *
 * @author lzj
 */
public class MessageIndentureTemp {

    /**
     * 表头
     */
    private MessageIndentureHeadTemp messageIndentureHeadTemp = null;
    /**
     * 序号 按自然数顺序编号（1，2，3…………）
     */
    private Integer listNo = null;
    /**
     * 发货序号 发货方不填，收货方必填
     */
    private Integer outNo = null;
    /**
     * 转入手册/账册号 发货方（收退货时的收货方）不填，收货（收退货时的发货方）方必填
     */
    private String inEmsNo = null;
    /**
     * 料号
     */
    private String copGNo = null;
    /**
     * 归并前商品名称
     */
    private String copGName = null;
    /**
     * 归并前规格型号
     */
    private String copGModel = null;
    /**
     * 申请表序号
     */
    private Integer appGNo = null; // 对应申请表中表体的序号
    /**
     * 项号
     */
    private Integer trGno = null; // 对应申请表中表体的备案序号
    /**
     * 海关商品编码
     */
    private String complex = null;
    /**
     * 商品名称
     */
    private String commName = null;
    /**
     * 商品规格
     */
    private String commSpec = null;
    /**
     * 交易单位
     */
    private String tradeUnit = null;
    /**
     * 交易数量
     */
    private Double tradeQty = null;
    /**
     * 计量单位
     */
    private String unit = null;
    /**
     * 申报数量
     */
    private Double qty = null;
    /**
     * 备注
     */
    private String note = null;

    public Integer getAppGNo() {
        return appGNo;
    }

    public void setAppGNo(Integer appGNo) {
        this.appGNo = appGNo;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getCommSpec() {
        return commSpec;
    }

    public void setCommSpec(String commSpec) {
        this.commSpec = commSpec;
    }

    public String getComplex() {
        return complex;
    }

    public void setComplex(String complex) {
        this.complex = complex;
    }

    public String getCopGModel() {
        return copGModel;
    }

    public void setCopGModel(String copGModel) {
        this.copGModel = copGModel;
    }

    public String getCopGName() {
        return copGName;
    }

    public void setCopGName(String copGName) {
        this.copGName = copGName;
    }

    public String getCopGNo() {
        return copGNo;
    }

    public void setCopGNo(String copGNo) {
        this.copGNo = copGNo;
    }

    public String getInEmsNo() {
        return inEmsNo;
    }

    public void setInEmsNo(String inEmsNo) {
        this.inEmsNo = inEmsNo;
    }

    public Integer getListNo() {
        return listNo;
    }

    public void setListNo(Integer listNo) {
        this.listNo = listNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getOutNo() {
        return outNo;
    }

    public void setOutNo(Integer outNo) {
        this.outNo = outNo;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Integer getTrGno() {
        return trGno;
    }

    public void setTrGno(Integer trGno) {
        this.trGno = trGno;
    }

    public Double getTradeQty() {
        return tradeQty;
    }

    public void setTradeQty(Double tradeQty) {
        this.tradeQty = tradeQty;
    }

    public String getTradeUnit() {
        return tradeUnit;
    }

    public void setTradeUnit(String tradeUnit) {
        this.tradeUnit = tradeUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public MessageIndentureHeadTemp getMessageIndentureHeadTemp() {
        return messageIndentureHeadTemp;
    }

    public void setMessageIndentureHeadTemp(MessageIndentureHeadTemp messageIndentureHeadTemp) {
        this.messageIndentureHeadTemp = messageIndentureHeadTemp;
    }
}
