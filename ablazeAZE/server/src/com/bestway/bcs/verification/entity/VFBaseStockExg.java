package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 库存成品基本实体
 * @author chl
 *
 */
public class VFBaseStockExg extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 序号 
     */
    private Integer serialNumber; 

	/**
	 * 成品料号
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
	 * 成品库存数量
	 */
	private Double storeAmount;
	
	/**
	 * 批次号
	 */
	private VFSection section;
	/**
	 * 成品BOM版本
	 */
	private Integer version;
	
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
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
	}
	/**
	 * @return the serialNumber
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
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
