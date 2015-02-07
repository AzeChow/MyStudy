package com.bestway.fixasset.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class TempFactoryFixAssetRemainMoney implements java.io.Serializable {
	/**
	 * 序号
	 */
	private Integer mainNo;

	/**
	 * 商品编码
	 */
	private Complex complex;

	/**
	 * 商品名称
	 */
	private String commName;

	/**
	 * 商品规格
	 */
	private String commSpec;

	/**
	 * 单位
	 */
	private Unit unit;

	/**
	 * 备案数量
	 */
	private Double porAmount;

	/**
	 * 备案金额
	 */
	private Double porMoney;

	/**
	 * 已变更数量
	 */
	private Double changedAmount;

	/**
	 * 已变更金额
	 */
	private Double changedMoney;

	/**
	 * 变更中数量
	 */
	private Double changingAmount;

	/**
	 * 变更中金额
	 */
	private Double changingMoney;

	/**
	 * 清单数量
	 */
	private Double listAmount;

	/**
	 * 工厂金额
	 */
	private Double factoryMoney;

	public Double getChangedAmount() {
		return changedAmount;
	}

	public void setChangedAmount(Double changedAmount) {
		this.changedAmount = changedAmount;
	}

	public Double getChangedMoney() {
		return changedMoney;
	}

	public void setChangedMoney(Double changedMoney) {
		this.changedMoney = changedMoney;
	}

	public Double getChangingAmount() {
		return changingAmount;
	}

	public void setChangingAmount(Double changingAmount) {
		this.changingAmount = changingAmount;
	}

	public Double getChangingMoney() {
		return changingMoney;
	}

	public void setChangingMoney(Double changingMoney) {
		this.changingMoney = changingMoney;
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

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Double getFactoryMoney() {
		return factoryMoney;
	}

	public void setFactoryMoney(Double factoryMoney) {
		this.factoryMoney = factoryMoney;
	}

	public Double getListAmount() {
		return listAmount;
	}

	public void setListAmount(Double listAmount) {
		this.listAmount = listAmount;
	}

	public Integer getMainNo() {
		return mainNo;
	}

	public void setMainNo(Integer mainNo) {
		this.mainNo = mainNo;
	}

	public Double getPorAmount() {
		return porAmount;
	}

	public void setPorAmount(Double porAmount) {
		this.porAmount = porAmount;
	}

	public Double getPorMoney() {
		return porMoney;
	}

	public void setPorMoney(Double porMoney) {
		this.porMoney = porMoney;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
