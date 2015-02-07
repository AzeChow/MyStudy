package com.bestway.bcus.checkstock.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 成品统计折料表
 * 
 * @author chl
 * @version 1.0
 * @createDate 2013-9-16
 */
public class ECSCustomsCountExgResolve extends BaseScmEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 盘点核查批次
	 */
	private ECSSection section;

	/**
	 * 帐册编号
	 */
	private String emsNo;

	/**
	 * 成品情况统计
	 */
	private ECSCustomsCountExg ecsCustomsCountExg;
	/**
	 * 料件备案序号
	 */
	private Integer seqNum;

	/**
	 * 料件名称
	 */
	private String commName;

	/**
	 * 型号规格
	 */
	private String commSpec;
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

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}


	public ECSCustomsCountExg getEcsCustomsCountExg() {
		return ecsCustomsCountExg;
	}

	public void setEcsCustomsCountExg(ECSCustomsCountExg ecsCustomsCountExg) {
		this.ecsCustomsCountExg = ecsCustomsCountExg;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
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

	public ECSSection getSection() {
		return section;
	}

	public void setSection(ECSSection section) {
		this.section = section;
	}

	public void init() {
		this.unitWaste = 0d;
		this.waste = 0d;
		this.wasteAmount = 0d;
	}
}
