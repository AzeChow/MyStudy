/*
 * Created on 2005-6-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;

import com.bestway.bcs.contract.entity.Contract;

/**
 * 临时实体类，存放报关分析－－库存分析资料、成品
 * 
 * @author ls
 */
public class TempExpContractStat implements Serializable{

	/**
	 * 合同定量
	 */
    private Double contractRation = null; 
	
	/**
	 * 总出口量
	 */
    private Double totalExpAmount = null;  
	
	/**
	 * 返工数量
	 */
    private Double returnAmount   = null;  
	
	/**
	 * 大单出口量
	 */
    private Double orderExpAmount = null;  
	
	/**
	 * 可以出口数量
	 */
    private Double canExpRemain   = null; 
	
	/**
	 * 现在出口数量
	 */
    private Double nowExpAmount   = null;  
	
	/**
	 * 合同
	 */
    private String contractId = null;

    
    
    
    /**
     * 获取大单出口量
     * 
     * @return orderExpAmount 大单出口量
     */
    public Double getOrderExpAmount() {
        return orderExpAmount;
    }
    
     /**
     * 设置大单出口量
     * 
     * @param orderExpAmount 大单出口量
     */
    public void setOrderExpAmount(Double orderExpAmount) {
        this.orderExpAmount = orderExpAmount;
    }
    
     /**
     * 获取可以出口数量
     * 
     * @return canExpRemain 可以出口数量
     */
    public Double getCanExpRemain() {
        return canExpRemain;
    }

    /**
     * 获取合同定量
     * 
     * @return contractRation 合同定量
     */
    public Double getContractRation() {
        return contractRation;
    }

    /**
     * 获取现在出口数量
     * 
     * @return nowExpAmount 现在出口数量
     */
    public Double getNowExpAmount() {
        return nowExpAmount;
    }

    /**
     * 获取返工数量
     * 
     * @return returnAmount 返工数量
     */
    public Double getReturnAmount() {
        return returnAmount;
    }

    /**
     * 获取总出口量
     * 
     * @return totalExpAmount 总出口量
     */
    public Double getTotalExpAmount() {
        return totalExpAmount;
    }

     /**
     * 设置可以出口数量
     * 
     * @param canExpRemain 可以出口数量
     */
    public void setCanExpRemain(Double canExpRemain) {
        this.canExpRemain = canExpRemain;
    }

     /**
     * 设置合同定量
     * 
     * @param contractRation 合同定量
     */
    public void setContractRation(Double contractRation) {
        this.contractRation = contractRation;
    }

     /**
     * 设置现在出口数量
     * 
     * @param nowExpAmount 现在出口数量
     */
    public void setNowExpAmount(Double nowExpAmount) {
        this.nowExpAmount = nowExpAmount;
    }

     /**
     * 设置返工数量
     * 
     * @param returnAmount 返工数量
     */
    public void setReturnAmount(Double returnAmount) {
        this.returnAmount = returnAmount;
    }

     /**
     * 设置总出口量
     * 
     * @param totalExpAmount 总出口量
     */
    public void setTotalExpAmount(Double totalExpAmount) {
        this.totalExpAmount = totalExpAmount;
    }
    
     /**
     * 获取合同
     * 
     * @return contractId 合同
     */
    public String getContractId() {
        return contractId;
    }
    
     /**
     * 设置合同
     * 
     * @param contractId 合同
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
