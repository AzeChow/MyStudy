/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestway.common.fpt.btplsinput.entity;
/**
 * 同步到JBCUS的申请表体的临时对象
 *
 * @author lzj
 */
public class MessageCustomsCoverBodyTemp {

    /**
     * 申请表表头
     */
    private MessageCustomsCoverHeadTemp messageCustomsCoverHeadTemp = null;
    /**
     * 修改标记 非空 0：未修改，1：已修改，2：已删除，3：新增
     */
    private String applModifyMarkState = null;
    /**
     * 转出商品备案序号
     */
    private Integer applGoodsBackNO;
    /**
     * 转出商品项号
     */
    private Integer applItemNo;
    /**
     * 转出商品编码
     */
    private String applGoodsNO;
    /**
     * 转出商品编码
     */
    private String applGoodsNO1;
    /**
     * 转出商品名称
     */
    private String applGoodsName;
    /**
     * 转出商品规格类型
     */
    private String applGoodsType;
    /**
     * 计量单位(转出)
     */
    private String applGoodsUnit;
    /**
     * 法定单位(转出)
     */
    private String applGoodsUnit1;
    /**
     * 转出商品结转申请数量
     */
    private Double applGoodsQuantity;
    /**
     * 转出商品结转申请数量
     */
    private Double applGoodsQuantity1;
    /**
     * 转出备注
     */
    private String applNote;
//    ---------------------------------------------
    /**
     * 转入修改标记 非空 0：未修改，1：已修改，2：已删除，3：新增
     */
    private String compModifyMarkState = null;
    /**
     * 转出序号
     */
    private Integer outNO;
    /**
     * 转入备案序号
     */
    private Integer compGoodsBackNO;
    /**
     * 转入商品项号
     */
    private Integer compItemNo;
    /**
     * 转入商品编码
     */
    private String compGoodsNO;
    /**
     * 转入商品编码
     */
    private String compGoodsNO1;
    /**
     * 转入商品名称
     */
    private String compGoodsName;
    /**
     * 转入商品规格类型
     */
    private String compGoodsType;
    /**
     * 计量单位(转入)
     */
    private String compGoodsUnit;
    /**
     * 法定单位(转入)
     */
    private String compGoodsUnit1;
    /**
     * 转入商品结转申请数量
     */
    private Double compGoodsQuantity;
    /**
     * 转入商品结转申请数量
     */
    private Double compGoodsQuantity1;
    /**
     * 转入商品手册号
     */
    private String compHandBookNO;
    /**
     * 转出商品手册号(转出方填写)
     */
    private String applHandBookNO;
    /**
     * 转出备注
     */
    private String compNote;

    public Integer getApplGoodsBackNO() {
        return applGoodsBackNO;
    }

    public void setApplGoodsBackNO(Integer applGoodsBackNO) {
        this.applGoodsBackNO = applGoodsBackNO;
    }

    public String getApplGoodsNO() {
        return applGoodsNO;
    }

    public void setApplGoodsNO(String applGoodsNO) {
        this.applGoodsNO = applGoodsNO;
    }

    public String getApplGoodsName() {
        return applGoodsName;
    }

    public void setApplGoodsName(String applGoodsName) {
        this.applGoodsName = applGoodsName;
    }

    public Double getApplGoodsQuantity() {
        return applGoodsQuantity;
    }

    public void setApplGoodsQuantity(Double applGoodsQuantity) {
        this.applGoodsQuantity = applGoodsQuantity;
    }

    public Double getApplGoodsQuantity1() {
        return applGoodsQuantity1;
    }

    public void setApplGoodsQuantity1(Double applGoodsQuantity1) {
        this.applGoodsQuantity1 = applGoodsQuantity1;
    }

    public String getApplGoodsType() {
        return applGoodsType;
    }

    public void setApplGoodsType(String applGoodsType) {
        this.applGoodsType = applGoodsType;
    }

    public String getApplGoodsUnit() {
        return applGoodsUnit;
    }

    public void setApplGoodsUnit(String applGoodsUnit) {
        this.applGoodsUnit = applGoodsUnit;
    }

    public String getApplGoodsUnit1() {
        return applGoodsUnit1;
    }

    public void setApplGoodsUnit1(String applGoodsUnit1) {
        this.applGoodsUnit1 = applGoodsUnit1;
    }

    public String getApplHandBookNO() {
        return applHandBookNO;
    }

    public void setApplHandBookNO(String applHandBookNO) {
        this.applHandBookNO = applHandBookNO;
    }

    public String getApplModifyMarkState() {
        return applModifyMarkState;
    }

    public void setApplModifyMarkState(String applModifyMarkState) {
        this.applModifyMarkState = applModifyMarkState;
    }

    public String getApplNote() {
        return applNote;
    }

    public void setApplNote(String applNote) {
        this.applNote = applNote;
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

    public Double getCompGoodsQuantity() {
        return compGoodsQuantity;
    }

    public void setCompGoodsQuantity(Double compGoodsQuantity) {
        this.compGoodsQuantity = compGoodsQuantity;
    }

    public Double getCompGoodsQuantity1() {
        return compGoodsQuantity1;
    }

    public void setCompGoodsQuantity1(Double compGoodsQuantity1) {
        this.compGoodsQuantity1 = compGoodsQuantity1;
    }

    public String getCompGoodsType() {
        return compGoodsType;
    }

    public void setCompGoodsType(String compGoodsType) {
        this.compGoodsType = compGoodsType;
    }

    public String getCompGoodsUnit() {
        return compGoodsUnit;
    }

    public void setCompGoodsUnit(String compGoodsUnit) {
        this.compGoodsUnit = compGoodsUnit;
    }

    public String getCompGoodsUnit1() {
        return compGoodsUnit1;
    }

    public void setCompGoodsUnit1(String compGoodsUnit1) {
        this.compGoodsUnit1 = compGoodsUnit1;
    }

    public String getCompHandBookNO() {
        return compHandBookNO;
    }

    public void setCompHandBookNO(String compHandBookNO) {
        this.compHandBookNO = compHandBookNO;
    }

    public String getCompModifyMarkState() {
        return compModifyMarkState;
    }

    public void setCompModifyMarkState(String compModifyMarkState) {
        this.compModifyMarkState = compModifyMarkState;
    }

    public String getCompNote() {
        return compNote;
    }

    public void setCompNote(String compNote) {
        this.compNote = compNote;
    }

    public String getApplGoodsNO1() {
        return applGoodsNO1;
    }

    public void setApplGoodsNO1(String applGoodsNO1) {
        this.applGoodsNO1 = applGoodsNO1;
    }

    public Integer getApplItemNo() {
        return applItemNo;
    }

    public void setApplItemNo(Integer applItemNo) {
        this.applItemNo = applItemNo;
    }

    public String getCompGoodsNO1() {
        return compGoodsNO1;
    }

    public void setCompGoodsNO1(String compGoodsNO1) {
        this.compGoodsNO1 = compGoodsNO1;
    }

    public Integer getCompItemNo() {
        return compItemNo;
    }

    public void setCompItemNo(Integer compItemNo) {
        this.compItemNo = compItemNo;
    }

    public Integer getOutNO() {
        return outNO;
    }

    public void setOutNO(Integer outNO) {
        this.outNO = outNO;
    }

    public MessageCustomsCoverHeadTemp getMessageCustomsCoverHeadTemp() {
        return messageCustomsCoverHeadTemp;
    }

    public void setMessageCustomsCoverHeadTemp(MessageCustomsCoverHeadTemp messageCustomsCoverHeadTemp) {
        this.messageCustomsCoverHeadTemp = messageCustomsCoverHeadTemp;
    }
}
