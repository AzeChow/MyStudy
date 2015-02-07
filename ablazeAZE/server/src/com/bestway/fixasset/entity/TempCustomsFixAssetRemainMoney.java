package com.bestway.fixasset.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class TempCustomsFixAssetRemainMoney implements java.io.Serializable{
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
	 * 已办证免税金额
	 */
	private Double dutyCertedMoney;

	/**
	 * 证免税申请中金额
	 */
	private Double dutyCertingMoney;

	/**
	 * 海关金额
	 */
	private Double customsMoney;

	/**
	 * 工厂金额
	 */
	private Double factoryMoney;

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

	public Double getCustomsMoney() {
		return customsMoney;
	}

	public void setCustomsMoney(Double customsMoney) {
		this.customsMoney = customsMoney;
	}

	public Double getDutyCertedMoney() {
		return dutyCertedMoney;
	}

	public void setDutyCertedMoney(Double dutyCertedMoney) {
		this.dutyCertedMoney = dutyCertedMoney;
	}

	public Double getDutyCertingMoney() {
		return dutyCertingMoney;
	}

	public void setDutyCertingMoney(Double dutyCertingMoney) {
		this.dutyCertingMoney = dutyCertingMoney;
	}

	public Double getFactoryMoney() {
		return factoryMoney;
	}

	public void setFactoryMoney(Double factoryMoney) {
		this.factoryMoney = factoryMoney;
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
