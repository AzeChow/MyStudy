package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;

/**
 * 临时实体类
 * 
 * @author ls
 *
 */
public class TempStorageStatByMateriel implements Serializable {

    /**
     * 合同号
     */
    private String  constractNo               = null; 
    
    /**
     * 单位
     */
    private String  unit                      = null; 
    
    /**
     * 合同定量
     */
    private String  contactAmount             = null; 
    
    /**
     * 总进口量
     */
    private String  totalImpAmount            = null; 
    
    /**
     * 大单进口量
     */
    private String  orderImpAmount            = null; 
    
    /**
     * 退料出口量
     */
    private String  backMaterielExpAmount     = null; 
    
    /**
     * 成品使用量
     */
    private String  finishProductDosageAmount = null; 
    
    /**
     * 余料库存
     */
    private String  remainStorageAmount       = null; 
    
    /**
     * 现进口量
     */
    private String  nowImpAmount              = null; 
    
    /**
     * 显示分组统计
     */
    private Boolean isShowTotal               = false; 
    
    /**
     * 显示分组标题
     */
    private Boolean isShowTitle               = false; 
    
    /**
     * 获取退料出口量
     *  
     * @return backMaterielExpAmount 退料出口量
     */
    public String getBackMaterielExpAmount() {
        return backMaterielExpAmount;
    }
    
    /**
     * 设置退料出口量
     * 
     * @param backMaterielExpAmount 退料出口量
     */
    public void setBackMaterielExpAmount(String backMaterielExpAmount) {
        this.backMaterielExpAmount = backMaterielExpAmount;
    }
    
    /**
     * 获取合同号
     * 
     * @return constractNo 合同号
     */
    public String getConstractNo() {
        return constractNo;
    }
    
    /**
     * 设置合同号
     * 
     * @param constractNo 合同号
     */
    public void setConstractNo(String constractNo) {
        this.constractNo = constractNo;
    }
    
    /**
     * 获取合同定量
     * 
     * @return contactAmount 合同定量
     */
    public String getContactAmount() {
        return contactAmount;
    }
    
    /**
     * 设置合同定量
     * 
     * @param contactAmount 合同定量
     */ 
    public void setContactAmount(String contactAmount) {
        this.contactAmount = contactAmount;
    }
    
    /**
     * 获取成品使用量
     * 
     * @return finishProductDosageAmount 成品使用量
     */
    public String getFinishProductDosageAmount() {
        return finishProductDosageAmount;
    }
    
    /**
     * 设置成品使用量
     * 
     * @param finishProductDosageAmount 成品使用量
     */
    public void setFinishProductDosageAmount(String finishProductDosageAmount) {
        this.finishProductDosageAmount = finishProductDosageAmount;
    }
    
    /**
     * 获取显示分组标题
     * 
     * @return isShowTitle 显示分组标题
     */
    public Boolean getIsShowTitle() {
        return isShowTitle;
    }
    
    /**
     * 设置显示分组标题
     * 
     * @param isShowTitle 显示分组标题
     */
    public void setIsShowTitle(Boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
    }
    
    /**
     * 获取显示分组统计
     * 
     * @return isShowTotal 显示分组统计
     */
    public Boolean getIsShowTotal() {
        return isShowTotal;
    }
    
    /**
     * 设置显示分组统计
     * 
     * @param isShowTotal 显示分组统计
     */
    public void setIsShowTotal(Boolean isShowTotal) {
        this.isShowTotal = isShowTotal;
    }
    
    /**
     * 获取现进口量
     * 
     * @return nowImpAmount 现进口量
     */
    public String getNowImpAmount() {
        return nowImpAmount;
    }
    
    /**
     * 设置现进口量
     * 
     * @param nowImpAmount 现进口量
     */
    public void setNowImpAmount(String nowImpAmount) {
        this.nowImpAmount = nowImpAmount;
    }
    
    /**
     * 获取大单进口量
     * 
     * @return orderImpAmount 大单进口量
     */
    public String getOrderImpAmount() {
        return orderImpAmount;
    }
    
    /**
     * 设置大单进口量
     * 
     * @param orderImpAmount 大单进口量
     */
    public void setOrderImpAmount(String orderImpAmount) {
        this.orderImpAmount = orderImpAmount;
    }
    
    /**
     * 获取余料库存
     * 
     * @return remainStorageAmount 余料库存
     */
    public String getRemainStorageAmount() {
        return remainStorageAmount;
    }
    
    /**
     * 设置余料库存
     * 
     * @param remainStorageAmount 余料库存
     */
    public void setRemainStorageAmount(String remainStorageAmount) {
        this.remainStorageAmount = remainStorageAmount;
    }
    
    /**
     * 获取总进口量
     * 
     * @return totalImpAmount 总进口量
     */
    public String getTotalImpAmount() {
        return totalImpAmount;
    }
    
    /**
     * 设置总进口量
     * 
     * @param totalImpAmount 总进口量
     */
    public void setTotalImpAmount(String totalImpAmount) {
        this.totalImpAmount = totalImpAmount;
    }
    
    /**
     * 获取单位
     * 
     * @return unit 单位
     */
    public String getUnit() {
        return unit;
    }
    
    /**
     * 设置单位
     * 
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
