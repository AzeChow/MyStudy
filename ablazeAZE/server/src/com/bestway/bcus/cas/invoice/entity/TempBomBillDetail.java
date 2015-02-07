package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.common.materialbase.entity.CalUnit;

public class TempBomBillDetail implements   Serializable{
	/**
	 * 单据
	 */
	private BillDetail bill;
	/**
	 * 成品-料号
	 */
	private String 	ptPart;
	/**
	 * 成品-名称
	 */
	private String ptName;
	/**
	 * 成品-规格型号
	 */
	private String ptSpec;
	/**
	 * 成品-单位
	 */
	private CalUnit ptUnit;

	/**
	 * 成品-数量
	 */
	private Double ptAmount;
	/**
	 * 成品-折算报关单位比率
	 */
	private Double unitConvert = null;
	/**
	 * 单项用量
	 */
	private Double unitUsed;
	/**
	 * 单耗
	 */
	private Double unitWaste;
	/**
	 * 损耗
	 */
	private Double waste;
	/**
	 * 库存量
	 */
	private Double wareAmount;
	/**
	 * 物料类型
	 * @return
	 */
	private String materielType;
	/**
	 * 以相同的报关名称+规格+单位；总报关数量
	 */
	private Double hsAllAmount;
	
	public BillDetail getBill() {
		return bill;
	}
	public void setBill(BillDetail bill) {
		this.bill = bill;
	}
	public String getPtPart() {
		return ptPart;
	}
	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
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
	public CalUnit getPtUnit() {
		return ptUnit;
	}
	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}
	public Double getPtAmount() {
		return ptAmount;
	}
	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}
	public Double getUnitConvert() {
		return unitConvert;
	}
	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}
	public Double getUnitUsed() {
		return unitUsed;
	}
	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
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
	public Double getWareAmount() {
		return wareAmount;
	}
	public void setWareAmount(Double wareAmount) {
		this.wareAmount = wareAmount;
	}
	public String getMaterielType() {
		return materielType;
	}
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
	public Double getHsAllAmount() {
		return hsAllAmount;
	}
	public void setHsAllAmount(Double hsAllAmount) {
		this.hsAllAmount = hsAllAmount;
	}
	
	
}
