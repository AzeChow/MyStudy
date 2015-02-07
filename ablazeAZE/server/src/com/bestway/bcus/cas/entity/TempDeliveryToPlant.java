package com.bestway.bcus.cas.entity;

import java.io.Serializable;

/**
 * 转厂送货耗料情况表临时实体类
 * 
 * @author 贺巍
 * 
 */
public class TempDeliveryToPlant implements Serializable {
	
	/**
	 *原材料名称
	 */
	private String ptName;

	/**
	 *原材料规格
	 */
	private String ptSpec;
	
	/**
	 *单位
	 */
	private String ptUnitName;
//	
	/**
	 * 客户供应商
	 */
	private String customer;


	/**
	 *结转出口报关单数量
	 */
	private Double carryOverExportDeclarationsAmount = 0.0;
	
	/**
	 *结转出口单据数量
	 */
	private Double carriedOverExportBillsAmount= 0.0;
	
	/**
	 *结转送货折原料差异
	 */
	private Double carryOverF7Difference  = 0.0;
	/**
	 *结转已转厂
	 */
	private Double carryOverF8Difference  = 0.0;
	
	/**
	 *合同单耗
	 */
	private Double contractConsumption  = 0.0;
	
	/**
	 * 合同损耗
	 */
	private Double contracLoss=0.0;
	
	/**
	 * 使用单耗的手册号
	 */
	private String conrtact=null;


	
	/**
	 * 获取原料名称
	 * @return
	 */
	public String getPtName() {
		return ptName;
	}

	/**
	 * 设置原料名称
	 * @param ptName
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	/**
	 * 获取原材料规格
	 * @return
	 */
	public String getPtSpec() {
		return ptSpec;
	}

	/**
	 * 设置原材料规格
	 * @param ptSpec
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * 获取单位
	 * @return
	 */
	public String getPtUnitName() {
		return ptUnitName;
	}

	/**
	 * 设置单位
	 * @param ptUnitName
	 */
	public void setPtUnitName(String ptUnitName) {
		this.ptUnitName = ptUnitName;
	}
	
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * 获取结转出口报关单数量
	 * @return
	 */
	public Double getCarryOverExportDeclarationsAmount() {
		return carryOverExportDeclarationsAmount;
	}
	
	/**
	 * 设置结转出口报关单数量
	 * @param carryOverExportDeclarationsAmount
	 */
	public void setCarryOverExportDeclarationsAmount(
			Double carryOverExportDeclarationsAmount) {
		this.carryOverExportDeclarationsAmount = carryOverExportDeclarationsAmount;
	}
	
	
	/**
	 * 获取结转出口单据数量
	 * @return
	 */
	public Double getCarriedOverExportBillsAmount() {
		return carriedOverExportBillsAmount;
	}
	
	/**
	 * 设置结转出口单据数量
	 * @param carriedOverExportBillsAmount
	 */
	public void setCarriedOverExportBillsAmount(Double carriedOverExportBillsAmount) {
		this.carriedOverExportBillsAmount = carriedOverExportBillsAmount;
	}
	

	/**
	 * 获取合同单耗
	 * @return
	 */
	public Double getContractConsumption() {
		return contractConsumption;
	}
	
	public Double getCarryOverF7Difference() {
		return carryOverF7Difference;
	}

	public void setCarryOverF7Difference(Double carryOverF7Difference) {
		this.carryOverF7Difference = carryOverF7Difference;
	}

	public Double getCarryOverF8Difference() {
		return carryOverF8Difference;
	}

	public void setCarryOverF8Difference(Double carryOverF8Difference) {
		this.carryOverF8Difference = carryOverF8Difference;
	}

	/**
	 * 设置合同单耗
	 * @param contractConsumption
	 */
	public void setContractConsumption(Double contractConsumption) {
		this.contractConsumption = contractConsumption;
	}
	
	/**
	 * 获取合同损耗
	 * @return
	 */
	public Double getContracLoss() {
		return contracLoss;
	}
	
	/**
	 * 设置合同损耗
	 * @param contracLoss
	 */
	public void setContracLoss(Double contracLoss) {
		this.contracLoss = contracLoss;
	}
	
	/**
	 * 获取使用单耗的手册号
	 * @return
	 */
	public String getConrtact() {
		return conrtact;
	}
	
	/**
	 * 设置使用单耗的手册号
	 * @param conrtact
	 */
	public void setConrtact(String conrtact) {
		this.conrtact = conrtact;
	}
}
