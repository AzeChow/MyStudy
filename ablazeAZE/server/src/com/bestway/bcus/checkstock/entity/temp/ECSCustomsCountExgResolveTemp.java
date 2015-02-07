package com.bestway.bcus.checkstock.entity.temp;

import com.bestway.common.BaseScmEntity;

/**
 * 临时成品统计折料表
 * 
 * @author lyh
 * @version 1.0
 * @createDate 2013-9-16
 */
public class ECSCustomsCountExgResolveTemp  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 帐册编号
	 */
	private String emsNo;

	/**
	 * 成品备案序号
	 */
	private Integer hsSeqNum;
	/**
	 * 成品名称
	 */
	private String hsName;
	
	/**
	 * 型号规格
	 */
	private String hsSpec;
	/**
	 * 版本号
	 */
	private Integer version;
	
	/**
	 * 料件备案序号
	 */
	private Integer ptSeqNum;

	/**
	 * 料件名称
	 */
	private String ptName;

	/**
	 * 型号规格
	 */
	private String ptSpec;
	/**
	 * 单耗
	 */
	private Double unitWaste;

	/**
	 * 损耗
	 */
	private Double waste;
	/**
	 * 耗用量
	 */
	private Double wasteAmount;
	/**
	 * 计量单位
	 */
	private String unit;
	/**
	 * 总出口
	 */
	private Double totalExportAmount;

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	

	public Integer getHsSeqNum() {
		return hsSeqNum;
	}

	public void setHsSeqNum(Integer hsSeqNum) {
		this.hsSeqNum = hsSeqNum;
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

	public Integer getPtSeqNum() {
		return ptSeqNum;
	}

	public void setPtSeqNum(Integer ptSeqNum) {
		this.ptSeqNum = ptSeqNum;
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

	public Double getWasteAmount() {
		return wasteAmount;
	}

	public void setWasteAmount(Double wasteAmount) {
		this.wasteAmount = wasteAmount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getTotalExportAmount() {
		return totalExportAmount;
	}

	public void setTotalExportAmount(Double totalExportAmount) {
		this.totalExportAmount = totalExportAmount;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void init() {
		this.unitWaste = 0d;
		this.waste = 0d;
		this.wasteAmount = 0d;
		this.totalExportAmount = 0d;
	}
}
