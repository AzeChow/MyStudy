package com.bestway.bcus.financial.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

public class ProductConsumption  extends BaseScmEntity {
	
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 报表日期
	 */
	private String accountDate;
	/**
	 * 成品商品名称
	 */
	private String hsName;

	/**
	 * 成品商品规格型号
	 */
	private String hsSpec;
	/**
	 * 成品单位
	 */
	private Unit hsUnit;
	
	/**
	 * 料件商品名称
	 */
	private String ljName;

	/**
	 * 料件商品规格型号
	 */
	private String ljSpec;
	/**
	 * 料件单位
	 */
	private Unit ljUnit;
	
	/**
	 * 单耗/净耗
	 */
	private Double unitWaste;

	/**
	 * 损耗率
	 */
	private Double waste;
	
	/**
	 * 上下浮率
	 */
	private Double alterRate;
	
	/**
	 * 调后单项用量
	 */
	private Double changeUnitWaste;
	
	/**
	 * 本月产量
	 */
	private Double thisProduction;
	
	/**
	 * 本月需料量
	 */
	private Double ljNeedQuantity;
	
	/**
	 * 料件单价
	 */
	private Double ljPrice;

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
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

	public Unit getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	public String getLjName() {
		return ljName;
	}

	public void setLjName(String ljName) {
		this.ljName = ljName;
	}

	public String getLjSpec() {
		return ljSpec;
	}

	public void setLjSpec(String ljSpec) {
		this.ljSpec = ljSpec;
	}

	public Unit getLjUnit() {
		return ljUnit;
	}

	public void setLjUnit(Unit ljUnit) {
		this.ljUnit = ljUnit;
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

	public Double getAlterRate() {
		return alterRate;
	}

	public void setAlterRate(Double alterRate) {
		this.alterRate = alterRate;
	}

	public Double getChangeUnitWaste() {
		return changeUnitWaste;
	}

	public void setChangeUnitWaste(Double changeUnitWaste) {
		this.changeUnitWaste = changeUnitWaste;
	}

	public Double getThisProduction() {
		return thisProduction;
	}

	public void setThisProduction(Double thisProduction) {
		this.thisProduction = thisProduction;
	}

	public Double getLjNeedQuantity() {
		return ljNeedQuantity;
	}

	public void setLjNeedQuantity(Double ljNeedQuantity) {
		this.ljNeedQuantity = ljNeedQuantity;
	}

	public Double getLjPrice() {
		return ljPrice;
	}

	public void setLjPrice(Double ljPrice) {
		this.ljPrice = ljPrice;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
