package com.bestway.fixasset.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class TempGroupImpCommInfo implements Serializable {
	/**
	 * 主序号
	 */
	private Integer mainNo;
	/**
	 * 海关编码
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
	 * 单价
	 */
	private Double unitPrice;

	/**
	 * 单位
	 */
	private Unit unit;

	/**
	 * 原产国 or 最终目的产国
	 */
	private Country country;

	/**
	 * 已分组的数量
	 */
	private Double allotAmout = 0.0;

	/**
	 * 已分组的金额
	 */
	private Double allotTotalPrice;

	public Integer getMainNo() {
		return mainNo;
	}

	public void setMainNo(Integer mainNo) {
		this.mainNo = mainNo;
	}

	public Double getAllotAmout() {
		return allotAmout;
	}

	public void setAllotAmout(Double allotAmout) {
		this.allotAmout = allotAmout;
	}

	public Double getAllotTotalPrice() {
		return allotTotalPrice;
	}

	public void setAllotTotalPrice(Double allotTotalPrice) {
		this.allotTotalPrice = allotTotalPrice;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
