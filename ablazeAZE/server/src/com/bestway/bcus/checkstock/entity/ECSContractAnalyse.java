package com.bestway.bcus.checkstock.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 账册合同分析
 * 
 * @author chl
 * @version 1.0
 * @createDate 2013-9-16
 */
public class ECSContractAnalyse extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 序号
	 */
	private Integer serialNumber;
	
	/**
	 * 盘点核查批次
	 */
	private ECSSection section;
	
	/**
	 * 帐册编号
	 */
	private String emsNo;
	
	/**
	 * 备案序号
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
	 * 计量单位
	 */
	private String unit;
	
	/**
	 * 进口数量
	 */
	private Double importAmount;
	
	/**
	 * 成品耗用
	 */
	private Double wasteAmount;
	
	/**
	 * 	结余数量
	 */
	private Double resultAmount;

	public ECSSection getSection() {
		return section;
	}

	public void setSection(ECSSection section) {
		this.section = section;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getImportAmount() {
		return importAmount;
	}

	public void setImportAmount(Double importAmount) {
		this.importAmount = importAmount;
	}

	public Double getWasteAmount() {
		return wasteAmount;
	}

	public void setWasteAmount(Double wasteAmount) {
		this.wasteAmount = wasteAmount;
	}

	public Double getResultAmount() {
		return resultAmount;
	}

	public void setResultAmount(Double resultAmount) {
		this.resultAmount = resultAmount;
	}
	public void init() {
		this.importAmount = 0d;
		this.resultAmount = 0d;
		this.wasteAmount = 0d;
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
	
}
