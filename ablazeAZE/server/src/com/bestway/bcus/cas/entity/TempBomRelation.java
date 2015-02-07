package com.bestway.bcus.cas.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.CalUnit;

public class TempBomRelation {
	/**
	 * 工厂料号
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
	private CalUnit ptUnit;
	/**
	 * 商品编码
	 */
	private Complex complex;
	/**
	 * 报关名称
	 */
	private String hsName;
	/**
	 * 报关规格
	 */
	private String hsSpec;
	/**
	 *报关单位
	 */
	private Unit hsUnit;
	/**
	 * 单项用量
	 */
	private Double unitUsed;
	/**
	 * 物料与实际报关的折算系数
	 */
	private Double unitConvert;
	/**
	 * 单耗
	 */
	private Double unitWaste;  
	/**
	 * 损耗
	 */
	private Double waste;
	
//	private Double exchangeRate;

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

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
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

	public CalUnit getPtUnit() {
		return ptUnit;
	}

	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}

	public Unit getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	public Double getUnitUsed() {
		return unitUsed;
	}

	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
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

//	public Double getExchangeRate() {
//		return exchangeRate;
//	}
//
//	public void setExchangeRate(Double exchangeRate) {
//		this.exchangeRate = exchangeRate;
//	}
//	
	

}
