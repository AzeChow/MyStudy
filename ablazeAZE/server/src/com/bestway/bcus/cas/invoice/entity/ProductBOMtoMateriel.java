package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;
/**
 * 成品转厂折料类
 * @author黄创亮
 *
 */
public class ProductBOMtoMateriel implements Serializable{
	/**
	 * 客户名称
	 */
	private String customerName=null;
	/**
	 * 成品名称
	 */
	private String productName=null;

	/**
	 * 成品规格
	 */
	private String productSpec=null;
	
	/**
	 * 成品单位
	 */
	private String productUnitName=null;
	/**
	 * 成品折料数量
	 */
	private Double productAmount=null;
	/**
	 * 料件名称
	 */
	private String materialName=null;

	/**
	 * 料件规格
	 */
	private String materialSpec=null;
	
	/**
	 * 料件单位
	 */
	private String materialUnitName=null;
	/**
	 * 单耗
	 */
	private Double unitWaste=null;

	/**
	 * 损耗率
	 */
	private Double waste=null;
	/**
	 * 单项用量 (总耗) 
	 */
	private Double unitDosage = null;
	/**
	 * 总用量
	 */
	private Double unitAmout = null;
	/**
	 *合同号 
	 */
	private String contractNo = null;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductSpec() {
		return productSpec;
	}
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}
	public String getProductUnitName() {
		return productUnitName;
	}
	public void setProductUnitName(String productUnitName) {
		this.productUnitName = productUnitName;
	}
	public Double getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialSpec() {
		return materialSpec;
	}
	public void setMaterialSpec(String materialSpec) {
		this.materialSpec = materialSpec;
	}
	public String getMaterialUnitName() {
		return materialUnitName;
	}
	public void setMaterialUnitName(String materialUnitName) {
		this.materialUnitName = materialUnitName;
	}
	public Double getUnitWaste() {
		return unitWaste;
	}
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}
	public Double getWaste() {
		return waste;
	}
	public void setWaste(Double waste) {
		this.waste = waste;
	}
	public Double getUnitDosage() {
		return unitDosage;
	}
	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Double getUnitAmout() {
		return unitAmout;
	}
	public void setUnitAmout(Double unitAmout) {
		this.unitAmout = unitAmout;
	}
	
	
	

}
