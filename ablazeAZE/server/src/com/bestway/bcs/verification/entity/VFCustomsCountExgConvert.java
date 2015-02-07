package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;
/**
 * 成品报关数据统计折料表
 * @author chl
 */
public class VFCustomsCountExgConvert extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 序号
	 */
	private Integer serialNumber;
	/**
	 * 成品统计
	 */
	private VFCustomsCountExg vfCustomsCountExg;
	/**
	 * 备案料件序号
	 */
	private Integer seqNum;
	/**
	 * 备案料件名称
	 */
	private String commName;
	/**
	 * 备案料件规格
	 */
	private String commSpec;
	
	/**
	 * 备案料件单位
	 */
	private String unit;
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
	 * 成品耗用量
	 */
	private Double wasteAmount;	
	/**
	 * 手册号
	 */
	private String emsNo;
	/**
	 * 归并序号
	 */
	private Integer mergerNo;
	
	/**
	 * 批次号
	 */
	private VFSection section;
	
	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * @param emsNo the emsNo to set
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	public Integer getMergerNo() {
		return mergerNo;
	}
	public void setMergerNo(Integer mergerNo) {
		this.mergerNo = mergerNo;
	}
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
	}
	/**
	 * @return the credenceNo
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	/**
	 * @param seqNum the credenceNo to set
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	/**
	 * @return the commName
	 */
	public String getCommName() {
		return commName;
	}
	/**
	 * @param commName the commName to set
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}
	/**
	 * @return the commSpec
	 */
	public String getCommSpec() {
		return commSpec;
	}
	/**
	 * @param commSpec the commSpec to set
	 */
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the wasteAmount
	 */
	public Double getWasteAmount() {
		return wasteAmount;
	}
	/**
	 * @param wasteAmount the wasteAmount to set
	 */
	public void setWasteAmount(Double wasteAmount) {
		this.wasteAmount = wasteAmount;
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
	 * @return the vfCustomsCountExg
	 */
	public VFCustomsCountExg getVfCustomsCountExg() {
		return vfCustomsCountExg;
	}
	/**
	 * @param vfCustomsCountExg the vfCustomsCountExg to set
	 */
	public void setVfCustomsCountExg(VFCustomsCountExg vfCustomsCountExg) {
		this.vfCustomsCountExg = vfCustomsCountExg;
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
}
