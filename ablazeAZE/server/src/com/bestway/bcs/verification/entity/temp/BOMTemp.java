package com.bestway.bcs.verification.entity.temp;

import com.bestway.common.materialbase.entity.CalUnit;


public class BOMTemp implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 成品料号
	 */
	private String exgPtNo;
	
	/**
	 * 成品版本
	 */
	private Integer version; 
	
	/**
	 * 物料
	 */
	private String ptNo; 
	
	/**
	 * 单耗
	 */
	private Double unitWaste;  
	
	/**
	 * 损耗
	 */
	private Double waste;  
	
	/**
	 * 单项用量
	 */
	private Double unitUsed;
	
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
	 * @return the exgPtNo
	 */
	public String getExgPtNo() {
		return exgPtNo;
	}

	/**
	 * @param exgPtNo the exgPtNo to set
	 */
	public void setExgPtNo(String exgPtNo) {
		this.exgPtNo = exgPtNo;
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
	 * @return the unitWaste
	 */
	public Double getUnitWaste() {
		return unitWaste;
	}

	/**
	 * @param unitWaste the unitWaste to set
	 */
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	/**
	 * @return the waste
	 */
	public Double getWaste() {
		return waste;
	}

	/**
	 * @param waste the waste to set
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}

	/**
	 * @return the unitUsed
	 */
	public Double getUnitUsed() {
		return unitUsed;
	}

	/**
	 * @param unitUsed the unitUsed to set
	 */
	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
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
	
	

	public BOMTemp(String exgPtNo, Integer version, String ptNo,
			Double unitWaste, Double waste, Double unitUsed, String ptName,
			String ptSpec, String ptUnit) {
		super();
		this.exgPtNo = exgPtNo;
		this.version = version;
		this.ptNo = ptNo;
		this.unitWaste = unitWaste;
		this.waste = waste;
		this.unitUsed = unitUsed;
		this.ptName = ptName;
		this.ptSpec = ptSpec;
		this.ptUnit = ptUnit;
	}

	public BOMTemp() {
		super();
	}
}
