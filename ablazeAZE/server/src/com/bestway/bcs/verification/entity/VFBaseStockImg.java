package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;
/**
 *  料件基本实体
 * @author chl
 */
public class VFBaseStockImg extends BaseScmEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 序号 
     */
    private Integer serialNumber; 
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
	private Double storeAmount;
	/**
	 * 归并序号
	 */
	private Integer mergerNo;
	
	/**
	 * 报关商品名称
	 */
	private String hsName;
	/**
	 * 报关商品规格
	 */
	private String hsSpec;
	/**
	 * 报关单位
	 */
	private String hsUnit;
	/**
	 * 折算系数
	 */
	private Double unitConvert;
	/**
	 * 折算报关数量
	 */
	private Double hsAmount;
	
	/**
	 * 批次号
	 */
	private VFSection section;
	
	/**
	 * 仓库
	 */
	private String warehouse;
	
	/**
	 * 备注
	 */
	private String memo;
	
	public String getPtNo() {
		return ptNo;
	}
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	public String getPtName() {
		return ptName;
	}
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	public String getPtSpec() {
		return ptSpec;
	}
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	public String getPtUnit() {
		return ptUnit;
	}
	public void setPtUnit(String ptUnit) {
		this.ptUnit = ptUnit;
	}
	public Double getStoreAmount() {
		return storeAmount;
	}
	public void setStoreAmount(Double storeAmount) {
		this.storeAmount = storeAmount;
	}
	public Integer getMergerNo() {
		return mergerNo;
	}
	public void setMergerNo(Integer mergerNo) {
		this.mergerNo = mergerNo;
	}
	public String getHsName() {
		return hsName;
	}
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}
	public String getHsSpec() {
		return hsSpec;
	}
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}
	public String getHsUnit() {
		return hsUnit;
	}
	public void setHsUnit(String hsUnit) {
		this.hsUnit = hsUnit;
	}
	public Double getHsAmount() {
		return hsAmount;
	}
	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
	}
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Double getUnitConvert() {
		return unitConvert;
	}
	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	
}
