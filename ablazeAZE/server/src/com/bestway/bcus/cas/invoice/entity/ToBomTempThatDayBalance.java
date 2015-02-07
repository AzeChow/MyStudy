package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.CalUnit;

public class ToBomTempThatDayBalance implements Serializable  {
	/**
	 * 成品信息
	 */
	private TempThatDayBalance product;
	
	/**
	 * 工厂料号
	 */
	private String ptPart;
	/**
	 * 工厂单位
	 */
	private CalUnit ptUnit;
	/**
	 * 损耗
	 */
	private Double waste;
	/**
	 * 单耗
	 */
	private Double unitWaste;  
	/**
	 * 单项用量
	 */
	private Double unitUsed;
	/**
	 * 海关商品名称
	 */
	private String hsName;
	/**
	 * 海关商品规格型号
	 */
	private String hsSpec;

	/**
	 * 折算报关数量
	 */
	private Double hsAmount;
	/**
	 * 累加报关数量
	 */
	private Double hsAllAmount;
	/**
	 * 库存数量
	 */
	private Double wareAmount;
	
	
	/**
	 * 折算报关单位比率
	 */

	private Double unitConvert = null;

	/**
	 * 海关单位
	 */
	private Unit hsUnit;

	
	
	public Double getHsAllAmount() {
		return hsAllAmount;
	}

	public void setHsAllAmount(Double hsAllAmount) {
		this.hsAllAmount = hsAllAmount;
	}

	public Double getUnitWaste() {
		return unitWaste;
	}

	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	public TempThatDayBalance getProduct() {
		return product;
	}

	public void setProduct(TempThatDayBalance product) {
		this.product = product;
	}

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public CalUnit getPtUnit() {
		return ptUnit;
	}

	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}

	public Double getWaste() {
		return waste;
	}

	public void setWaste(Double waste) {
		this.waste = waste;
	}

	public Double getUnitUsed() {
		return unitUsed;
	}

	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
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

	public Double getHsAmount() {
		return hsAmount;
	}

	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}

	public Double getWareAmount() {
		return wareAmount;
	}

	public void setWareAmount(Double wareAmount) {
		this.wareAmount = wareAmount;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public Unit getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}
	
	
	
}
