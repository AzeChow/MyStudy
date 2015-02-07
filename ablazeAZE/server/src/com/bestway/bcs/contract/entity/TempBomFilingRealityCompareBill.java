package com.bestway.bcs.contract.entity;

import java.io.Serializable;
/**
 * 存放合同国内购料清单的临时实体类
 * @author hw
 *
 */
public class TempBomFilingRealityCompareBill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 料件序号
	 */
	private Integer serialNumber;
	/**
	 * 料件名称
	 */
	private String imgName;
	/**
	 * 单位名称
	 */
	private String unit;
	/**
	 * 进口数（进料+转厂进口）
	 */
	private Double importAmount;
	/**
	 * 备案总耗
	 */
	private Double filingConsumption;
	/**
	 * 实际总耗
	 */
	private Double realityConsumption;
	/**
	 * 备案总耗与实际总耗的差额
	 */
	private Double difference;
    /**
     * 说明原因
     * @return
     */
	private String explain;
	
	/**
	 * 国内购买数量
	 */
	private Double domesticPurchase = null;
	
	/**
	 * 净耗重量（KG）
	 */
	private Double netLossWeight = null;
	
	/**
	 * 料件调出数量
	 */
	private Double calloutAmount = null;
	
	/**
	 * 损耗率
	 */
	private Double waste = null;
	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Double getImportAmount() {
		return importAmount;
	}

	public void setImportAmount(Double importAmount) {
		this.importAmount = importAmount;
	}

	public Double getFilingConsumption() {
		return filingConsumption;
	}

	public void setFilingConsumption(Double filingConsumption) {
		this.filingConsumption = filingConsumption;
	}

	public Double getRealityConsumption() {
		return realityConsumption;
	}

	public void setRealityConsumption(Double realityConsumption) {
		this.realityConsumption = realityConsumption;
	}

	public Double getDifference() {
		return difference;
	}

	public void setDifference(Double difference) {
		this.difference = difference;
	}
	
	/**
	 * 国内购买数量
	 */
	public Double getDomesticPurchase() {
		return domesticPurchase;
	}

	/**
	 * 国内购买数量
	 */
	public void setDomesticPurchase(Double domesticPurchase) {
		this.domesticPurchase = domesticPurchase;
	}

	/**
	 * 净耗重量（KG）
	 */
	public Double getNetLossWeight() {
		return netLossWeight;
	}

	/**
	 * 净耗重量（KG）
	 */
	public void setNetLossWeight(Double netLossWeight) {
		this.netLossWeight = netLossWeight;
	}

	/**
	 * 料件调出数量
	 */
	public Double getCalloutAmount() {
		return calloutAmount;
	}

	/**
	 * 料件调出数量
	 */
	public void setCalloutAmount(Double calloutAmount) {
		this.calloutAmount = calloutAmount;
	}

	/**
	 * 损耗率
	 */
	public Double getWaste() {
		return waste;
	}

	/**
	 * 损耗率
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}
}
