package com.bestway.bcs.contract.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class TempChangedContractImgExg implements java.io.Serializable {
	private Integer groupNo;

	private Integer seqNum;

	private Complex complex;

	private String name;

	private String spec;

	/**
	 * 原产地
	 */
	private Country country;

	private Double amount;

	private Unit unit;

	private Double unitPrice;

	private Double totalPrice;

	/**
	 * 征减免税方式
	 */
	private LevyMode levyMode = null;

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public Integer getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(Integer groupNo) {
		this.groupNo = groupNo;
	}

	public LevyMode getLevyMode() {
		return levyMode;
	}

	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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

	public String getNameSpec() {
		if (this.name != null && !"".equals(this.name.trim())
				&& this.spec != null && !"".equals(this.spec.trim())) {
			return this.name + "/" + this.spec;
		} else if (this.name != null && !"".equals(this.name.trim())) {
			return this.name;
		} else if (this.spec != null && !"".equals(this.spec.trim())) {
			return this.spec;
		} else {
			return "";
		}
	}
}
