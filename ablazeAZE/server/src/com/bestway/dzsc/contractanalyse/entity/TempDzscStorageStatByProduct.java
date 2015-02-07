package com.bestway.dzsc.contractanalyse.entity;

import java.io.Serializable;

/**
 * 临时实体类
 * 
 * @author yp
 */
public class TempDzscStorageStatByProduct implements Serializable {
    
    /**
     * 合同号
     */
    private String  constractNo    = null; 
    
    /**
     * 单位
     */
    private String  unit           = null; 
    
    /**
     * 合同定量
     */
    private String  contractRation = null; 
    
    /**
     * 总出口量
     */
    private String  totalExpAmount = null; 
    
    /**
     * 返工数量
     */
    private String  returnAmount   = null; 
    
    /**
     * 大单出口量
     */
    private String  orderExpAmount = null; 
    
    /**
     * 可以出口数量
     */
    private String  canExpRemain   = null; 
    
    /**
     * 现在出口数量
     */
    private String  nowExpAmount   = null; 
    
    /**
     * 显示分组统计
     */
    private Boolean isShowTotal    = false; 
    
    /**
     * 显示分组标题
     */
    private Boolean isShowTitle    = false; 
        
    /**
     * 获取可以出口数量
     * 
     * @return canExpRemain 可以出口数量
     */
    public String getCanExpRemain() {
        return canExpRemain;
    }
    
    /**
     * 设置可以出口数量
     * 
     * @param canExpRemain 可以出口数量
     */
    public void setCanExpRemain(String canExpRemain) {
        this.canExpRemain = canExpRemain;
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
     * @return contractRation 合同定量
     */
    public String getContractRation() {
        return contractRation;
    }
    
    /**
     * 设置合同定量
     * 
     * @param contractRation 合同定量
     */
    public void setContractRation(String contractRation) {
        this.contractRation = contractRation;
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
     * 获取现在出口数量
     * 
     * @return nowExpAmount 现在出口数量
     */
    public String getNowExpAmount() {
        return nowExpAmount;
    }
    
    /**
     * 设置现在出口数量
     * 
     * @param nowExpAmount 现在出口数量
     */
    public void setNowExpAmount(String nowExpAmount) {
        this.nowExpAmount = nowExpAmount;
    }
        
    /**
     * 获取大单出口量
     * 
     * @return orderExpAmount 大单出口量
     */
    public String getOrderExpAmount() {
        return orderExpAmount;
    }
    
    /**
     * 设置大单出口量
     * 
     * @param orderExpAmount 大单出口量
     */
    public void setOrderExpAmount(String orderExpAmount) {
        this.orderExpAmount = orderExpAmount;
    }
        
    /**
     * 获取返工数量
     * 
     * @return returnAmount 返工数量
     */
    public String getReturnAmount() {
        return returnAmount;
    }
    
    /**
     * 设置返工数量
     * 
     * @param returnAmount 返工数量
     */
    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }
        
    /**
     * 获取总出口量
     * 
     * @return totalExpAmount 总出口量
     */
    public String getTotalExpAmount() {
        return totalExpAmount;
    }
    
    /**
     * 设置总出口量
     *  
     * @param totalExpAmount 总出口量
     */
    public void setTotalExpAmount(String totalExpAmount) {
        this.totalExpAmount = totalExpAmount;
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
