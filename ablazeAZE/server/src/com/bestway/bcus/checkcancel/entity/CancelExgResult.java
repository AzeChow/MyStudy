/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 给别成品的核算结果继承
 */
public class CancelExgResult extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 核销表头
     */
    private CancelHead cancelHead;  
	    
    /**
     * 帐册序号
     */
    private Integer emsSeqNum;    
	    
    /**
     * 成品名称
     */
    private String name;       
	    
    /**
     * 规格型号
     */
    private String spec;      
	    
    /**
     * 单位
     */
    private String unit;       
	
	    
    /**
     * 消耗--总数量
     */
    private Double useSumNum;    
	    
    /**
     * 总价值
     */
    private Double useSumPrice;  
	    
    /**
     * 消耗--总重量
     */
    private Double useSumWeight;   
	
	    
    /**
     * 内销--总数量
     */
    private Double innerUseSumNum; 
	    
    /**
     * 内销--总价值
     */
    private Double innerUseSumPrice;

	    
    /**
     * 残次品--数量
     */
    private Double leftOverImgNum; 
	    
    /**
     * 残次品--总价值
     */
    private Double leftOverImgSumPrice;
	    
    /**
     * 残次品--总重量
     */
    private Double leftOverImgSumWeight;

	
	    
    /**
     * 实际剩余--数量
     */
    private Double factLeaveNum;   
	    
    /**
     * 实际剩余--总价值
     */
    private Double factLeaveSumPrice;
	    
    /**
     * 实际剩余--总重量
     */
    private Double factLeaveSumWeight;
	
	    
    /**
     * 结余数量
     */
    private Double resultNum; 
	
	    
    /**
     * 获取消耗--总价值
     */
    private String note;       
     

	/**
	 * 获取核销表头
	 * 
	 * @return cancelHead 核销表头
	 */
	public CancelHead getCancelHead() {
		return cancelHead;
	}
		
	/**
	 * 设置核销表头
	 * 
	 * @param cancelHead 核销表头
	 */
	public void setCancelHead(CancelHead cancelHead) {
		this.cancelHead = cancelHead;
	}
	
	/**
	 * 获取帐册序号
	 * 
	 * @return   emsSeqNum 帐册序号
	 */
	public Integer getEmsSeqNum() {
		return emsSeqNum;
	}
		
	/**
	 * 设置帐册序号
	 * 
	 * @param emsSeqNum 帐册序号
	 */
	public void setEmsSeqNum(Integer emsSeqNum) {
		this.emsSeqNum = emsSeqNum;
	}
	
	/**
	 * 获取际剩余--数量
	 * 
	 * @return factLeaveNum 际剩余--数量
	 */
	public Double getFactLeaveNum() {
		return factLeaveNum;
	}
		
	/**
	 * 设置际剩余--数量
	 * 
	 * @param factLeaveNum 际剩余--数量
	 */
	public void setFactLeaveNum(Double factLeaveNum) {
		this.factLeaveNum = factLeaveNum;
	}
	
	/**
	 * 获取实际剩余--总价值
	 * 
	 * @return factLeaveSumPrice 实际剩余--总价值
	 */
	public Double getFactLeaveSumPrice() {
		return factLeaveSumPrice;
	}
		
	/**
	 * 设置实际剩余--总价值
	 * 
	 * @param factLeaveSumPrice 实际剩余--总价值
	 */
	public void setFactLeaveSumPrice(Double factLeaveSumPrice) {
		this.factLeaveSumPrice = factLeaveSumPrice;
	}
	
	/**
	 * 获取实际剩余--总重量
	 * 
	 * @return factLeaveSumWeight 实际剩余--总重量
	 */
	public Double getFactLeaveSumWeight() {
		return factLeaveSumWeight;
	}
		
	/**
	 * 设置实际剩余--总重量
	 * 
	 * @param factLeaveSumWeight 实际剩余--总重量
	 */
	public void setFactLeaveSumWeight(Double factLeaveSumWeight) {
		this.factLeaveSumWeight = factLeaveSumWeight;
	}
	
	/**
	 * 获取内销--总数量
	 * 
	 * @return innerUseSumNum 内销--总数量
	 */
	public Double getInnerUseSumNum() {
		return innerUseSumNum;
	}
		
	/**
	 * 设置内销--总数量
	 * 
	 * @param innerUseSumNum 内销--总数量
	 */
	public void setInnerUseSumNum(Double innerUseSumNum) {
		this.innerUseSumNum = innerUseSumNum;
	}
	
	/**
	 * 获取内销--总价值
	 * 
	 * @return innerUseSumPrice 内销--总价值
	 */
	public Double getInnerUseSumPrice() {
		return innerUseSumPrice;
	}
		
	/**
	 * 设置内销--总价值
	 * 
	 * @param innerUseSumPrice 内销--总价值
	 */
	public void setInnerUseSumPrice(Double innerUseSumPrice) {
		this.innerUseSumPrice = innerUseSumPrice;
	}
	
	/**
	 * 获取残次品--数量
	 * 
	 * @return leftOverImgNum 残次品--数量
	 */
	public Double getLeftOverImgNum() {
		return leftOverImgNum;
	}
		
	/**
	 * 设置残次品--数量
	 * 
	 * @param leftOverImgNum 残次品--数量
	 */
	public void setLeftOverImgNum(Double leftOverImgNum) {
		this.leftOverImgNum = leftOverImgNum;
	}
	
	/**
	 * 获取残次品--总价值
	 * 
	 * @return leftOverImgSumPrice 残次品--总价值
	 */
	public Double getLeftOverImgSumPrice() {
		return leftOverImgSumPrice;
	}
		
	/**
	 * 设置残次品--总价值
	 * 
	 * @param leftOverImgSumPrice 残次品--总价值
	 */
	public void setLeftOverImgSumPrice(Double leftOverImgSumPrice) {
		this.leftOverImgSumPrice = leftOverImgSumPrice;
	}
	
	/**
	 * 获取残次品--总重量
	 * 
	 * @return leftOverImgSumWeight 残次品--总重量
	 */
	public Double getLeftOverImgSumWeight() {
		return leftOverImgSumWeight;
	}
		
	/**
	 * 设置残次品--总重量
	 * 
	 * @param leftOverImgSumWeight 残次品--总重量
	 */
	public void setLeftOverImgSumWeight(Double leftOverImgSumWeight) {
		this.leftOverImgSumWeight = leftOverImgSumWeight;
	}
	
	/**
	 * 获取成品名称
	 * 
	 * @return name 成品名称
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * 设置成品名称
	 * 
	 * @param name 成品名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}
		
	/**
	 * 设置备注
	 * 
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * 获取规格型号
	 * 
	 * @return spec 规格型号
	 */
	public String getSpec() {
		return spec;
	}
		
	/**
	 * 设置规格型号
	 * 
	 * @param spec 规格型号
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	/**
	 * 获取消耗--总数量
	 * 
	 * @return useSumNum 消耗--总数量
	 */
	public Double getUseSumNum() {
		return useSumNum;
	}
		
	/**
	 * 设置消耗--总数量
	 * 
	 * @param useSumNum 消耗--总数量
	 */
	public void setUseSumNum(Double useSumNum) {
		this.useSumNum = useSumNum;
	}
	
	/**
	 * 获取消耗--总价值
	 * 
	 * @return useSumPrice 消耗--总价值
	 */
	public Double getUseSumPrice() {
		return useSumPrice;
	}
		
	/**
	 * 设置消耗--总价值
	 * 
	 * @param useSumPrice 消耗--总价值
	 */
	public void setUseSumPrice(Double useSumPrice) {
		this.useSumPrice = useSumPrice;
	}
	
	/**
	 * 获取消耗--总重量
	 * 
	 * @return useSumWeight 消耗--总重量
	 */
	public Double getUseSumWeight() {
		return useSumWeight;
	}
		
	/**
	 * 设置消耗--总重量
	 * 
	 * @param useSumWeight 消耗--总重量
	 */
	public void setUseSumWeight(Double useSumWeight) {
		this.useSumWeight = useSumWeight;
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
	
	/**
	 * 获取结余数量
	 * 
	 * @return resultNum 结余数量
	 */
	public Double getResultNum() {
		return resultNum;
	}
	
	/**
	 * 设置结余数量
	 * 
	 * @param resultNum 结余数量
	 */
	public void setResultNum(Double resultNum) {
		this.resultNum = resultNum;
	}
}
