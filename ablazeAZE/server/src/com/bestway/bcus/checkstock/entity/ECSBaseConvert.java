package com.bestway.bcus.checkstock.entity;


/**
 * 基础折算表
 * 
 * @author chl
 * @version 1.0
 * @createDate 2013-9-16
 */
public class ECSBaseConvert extends ECSBaseStockItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 料号
	 */
	private String ptNo; 
	/**
	 * 工厂名称
	 */
	private String ptName;
	/**
	 * 工厂规格
	 */
	private String ptSpec;
	/**
	 * 工厂单位
	 */
	private String ptUnit;
	/**
	 * 库存数量
	 */
	private Double ptAmount;
	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 折算系数
	 */
	private Double unitConvert;
	/**
	 * @return the ptNo
	 */
	public String getPtNo() {
		return ptNo;
	}
	/**
	 * @param ptNo the ptNo to set
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	/**
	 * @return the ptName
	 */
	public String getPtName() {
		return ptName;
	}
	/**
	 * @param ptName the ptName to set
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	/**
	 * @return the ptSpec
	 */
	public String getPtSpec() {
		return ptSpec;
	}
	/**
	 * @param ptSpec the ptSpec to set
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * @return the ptUnit
	 */
	public String getPtUnit() {
		return ptUnit;
	}
	/**
	 * @param ptUnit the ptUnit to set
	 */
	public void setPtUnit(String ptUnit) {
		this.ptUnit = ptUnit;
	}
	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * @return the ptAmount
	 */
	public Double getPtAmount() {
		return ptAmount;
	}
	/**
	 * @param ptAmount the ptAmount to set
	 */
	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}
	/**
	 * @return the unitConvert
	 */
	public Double getUnitConvert() {
		return unitConvert;
	}
	/**
	 * @param unitConvert the unitConvert to set
	 */
	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}
}
