package com.bestway.fixasset.entity;

public class TempImportAgreementCommInfo implements java.io.Serializable{
	/**
	 * 主序号
	 */
	private String mainNo;
	/**
	 * 次序号
	 */
	private String secNo;
	/**
	 * 商品编号
	 */
	private String commCode;
	
	/**
	 * 商品名称
	 */
	private String commName;
	/**
	 * 商品规格
	 */
	private String commSpec;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 数量
	 */
	private String amount;
	/**
	 * 单价
	 */
	private String unitPrice;
	/**
	 * 总价
	 */
	private String totalPrice;
	/**
	 * 原产国
	 */
	private String country;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCommCode() {
		return commCode;
	}
	public void setCommCode(String commCode) {
		this.commCode = commCode;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMainNo() {
		return mainNo;
	}
	public void setMainNo(String mainNo) {
		this.mainNo = mainNo;
	}

	public String getSecNo() {
		return secNo;
	}
	public void setSecNo(String secNo) {
		this.secNo = secNo;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
}
