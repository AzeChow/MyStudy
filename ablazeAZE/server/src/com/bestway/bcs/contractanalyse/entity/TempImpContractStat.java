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
 * 临时实体类，存放报关分析－－库存分析资料、料件
 * 
 * @author ls
 */
public class TempImpContractStat implements Serializable {

	/**
	 * 合同定量
	 */
    private Double contractRation = null; 
	/**
	 * 总进口量
	 */
    private Double totalImpAmount            = null;  
    
	/**
	 * 大单进口量
	 */
    private Double orderImpAmount            = null;  
    
	/**
	 * 退料出口量
	 */
    private Double backMaterielExpAmount     = null; 
    
	/**
	 * 成品使用量
	 */
    private Double finishProductDosageAmount = null; 
    
	/**
	 * 余料库存
	 */
    private Double remainStorageAmount       = null;
    
	/**
	 * 现进口量
	 */
    private Double nowImpAmount              = null; 
    
	/**
	 * 合同
	 */
    private String contractId                = null; 

    /**
     * 获取退料出口量
     * 
     * @return backMaterielExpAmount 退料出口量
     */
    public Double getBackMaterielExpAmount() {
        return backMaterielExpAmount;
    }

    /**
     * 获取成品使用量
     * 
     * @return finishProductDosageAmount 成品使用量
     */
    public Double getFinishProductDosageAmount() {
        return finishProductDosageAmount;
    }

    /**
     * 获取现进口量
     * 
     * @return Returns the nowImpAmount.
     */
    public Double getNowImpAmount() {
        return nowImpAmount;
    }

    /**
     * 获取大单进口量
     * 
     * @return orderImpAmount 大单进口量
     */
    public Double getOrderImpAmount() {
        return orderImpAmount;
    }

    /**
     * 获取余料库存
     * 
     * @return remainStorageAmount 余料库存
     */
    public Double getRemainStorageAmount() {
        return remainStorageAmount;
    }

    /**
     * 获取总进口量
     * 
     * @return totalImpAmount 总进口量
     */
    public Double getTotalImpAmount() {
        return totalImpAmount;
    }

    /**
     * 设置退料出口量
     * 
     * @param backMaterielExpAmount 退料出口量
     */
    public void setBackMaterielExpAmount(Double backMaterielExpAmount) {
        this.backMaterielExpAmount = backMaterielExpAmount;
    }

    /**
     * 设置成品使用量
     * 
     * @param finishProductDosageAmount 成品使用量
     */
    public void setFinishProductDosageAmount(Double finishProductDosageAmount) {
        this.finishProductDosageAmount = finishProductDosageAmount;
    }

    /**
     * 设置现进口量
     * 
     * @param nowImpAmount 现进口量
     */
    public void setNowImpAmount(Double nowImpAmount) {
        this.nowImpAmount = nowImpAmount;
    }

    /**
     * 设置
     * 
     * @param orderImpAmount
     *            The orderImpAmount to set.
     */
    public void setOrderImpAmount(Double orderImpAmount) {
        this.orderImpAmount = orderImpAmount;
    }

    /**
     * 设置余料库存
     * 
     * @param remainStorageAmount 余料库存
     */
    public void setRemainStorageAmount(Double remainStorageAmount) {
        this.remainStorageAmount = remainStorageAmount;
    }

    /**
     * 设置总进口量
     * 
     * @param totalImpAmount 总进口量
     */
    public void setTotalImpAmount(Double totalImpAmount) {
        this.totalImpAmount = totalImpAmount;
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

	public Double getContractRation() {
		return contractRation;
	}

	public void setContractRation(Double contractRation) {
		this.contractRation = contractRation;
	}
}
