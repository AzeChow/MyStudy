/*
 * Created on 2005-6-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 存放报关分析－－成品执行情况分析－－出口成品统计资料
 * 
 * @author ls
 */
public class DzscExpFinishProductStat implements Serializable{
	
	/**
	 * 商品编码
	 */
    private String complexCode            = null;
	
	/**
	 * 品名规格
	 */
    private String nameSpec               = null; 
	
	/**
	 * 单位
	 */
    private Unit   unit                   = null; 
	
	/**
	 * 合同总订量
	 */
    private Double contractTotalAmount    = null;
	
	/**
	 * 总进口量
	 */
    private Double totalExpAmount         = null;
	
	/**
	 * 成品出口总量
	 */
    private Double finishProductExpAmount = null; 
	
	/**
	 * 转厂出口总量
	 */
    private Double transferExpAmount      = null;
	
	/**
	 * 退厂返工总量
	 */
    private Double backFactoryRework      = null;
	
	/**
	 * 返工复出总量
	 */
    private Double reworkExpAmount        = null;
	
	/**
	 * 可进口总量
	 */
    private Double canExpAmount           = null;
	
    /**
     * 获取退厂返工总量
     * 
     * @return backFactoryRework 退厂返工总量
     */
    public Double getBackFactoryRework() {
		return backFactoryRework;
	}
	
	/**
	 * 设置退厂返工总量
	 * 
	 * @param backFactoryRework 退厂返工总量
	 */
	public void setBackFactoryRework(Double backFactoryRework) {
		this.backFactoryRework = backFactoryRework;
	}
	
    /**
     * 获取可进口总量
     * 
     * @return canExpAmount 可进口总量
     */
	public Double getCanExpAmount() {
		return canExpAmount;
	}
	
	/**
	 * 设置可进口总量
	 * 
	 * @param canExpAmount 可进口总量
	 */
	public void setCanExpAmount(Double canExpAmount) {
		this.canExpAmount = canExpAmount;
	}
	
    /**
     * 获取商品编码
     * 
     * @return complexCode 商品编码
     */
	public String getComplexCode() {
		return complexCode;
	}
	
	/**
	 * 设置商品编码
	 * 
	 * @param complexCode 商品编码
	 */
	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}
	
    /**
     * 获取合同总订量
     * 
     * @return contractTotalAmount 合同总订量
     */
	public Double getContractTotalAmount() {
		return contractTotalAmount;
	}
	
	/**
	 * 设置合同总订量
	 * 
	 * @param contractTotalAmount 合同总订量
	 */
	public void setContractTotalAmount(Double contractTotalAmount) {
		this.contractTotalAmount = contractTotalAmount;
	}
	
    /**
     * 获取成品出口总量
     * 
     * @return finishProductExpAmount 成品出口总量
     */
	public Double getFinishProductExpAmount() {
		return finishProductExpAmount;
	}
	
	/**
	 * 设置成品出口总量
	 * 
	 * @param finishProductExpAmount 成品出口总量
	 */
	public void setFinishProductExpAmount(Double finishProductExpAmount) {
		this.finishProductExpAmount = finishProductExpAmount;
	}
	
    /**
     * 获取品名规格
     * 
     * @return nameSpec 品名规格
     */
	public String getNameSpec() {
		return nameSpec;
	}
	
	/**
	 * 设置品名规格
	 * 
	 * @param nameSpec 品名规格
	 */
	public void setNameSpec(String nameSpec) {
		this.nameSpec = nameSpec;
	}
	
    /**
     * 获取返工复出总量
     * 
     * @return reworkExpAmount 返工复出总量
     */
	public Double getReworkExpAmount() {
		return reworkExpAmount;
	}
	
	/**
	 * 设置返工复出总量
	 * 
	 * @param reworkExpAmount 返工复出总量
	 */
	public void setReworkExpAmount(Double reworkExpAmount) {
		this.reworkExpAmount = reworkExpAmount;
	}
	
    /**
     * 获取总进口量
     * 
     * @return totalExpAmount 总进口量
     */
	public Double getTotalExpAmount() {
		return totalExpAmount;
	}
	
	/**
	 * 设置总进口量
	 * 
	 * @param totalExpAmount 总进口量
	 */
	public void setTotalExpAmount(Double totalExpAmount) {
		this.totalExpAmount = totalExpAmount;
	}
	
    /**
     * 获取转厂出口总量
     *  
     * @return transferExpAmount 转厂出口总量
     */
	public Double getTransferExpAmount() {
		return transferExpAmount;
	}
	
	/**
	 * 设置转厂出口总量
	 * 
	 * @param transferExpAmount 转厂出口总量
	 */
	public void setTransferExpAmount(Double transferExpAmount) {
		this.transferExpAmount = transferExpAmount;
	}
	
    /**
     * 获取单位
     *  
     * @return unit 单位
     */ 
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * 设置单位
	 * 
	 * @param unit 单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
    
    
    
    
}
