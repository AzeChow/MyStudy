package com.bestway.bcus.checkcancel.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class TempEmsHeadH2kBom  implements Serializable{
	/**
	 * 料件序号
	 */
	private Integer seqNum;

	/**
	 * 商品编码
	 */
	private Complex complex;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 规格型号
	 */
	private String spec;

	/**
	 * 计量单位
	 */
	private Unit unit;

	/**
	 * 单耗
	 */
	private Double unitWear;

	/**
	 * 损耗
	 */
	private Double wear;

	/**
	 * 总用量
	 */
	private Double totalUse;

	/**
	 * 单价
	 */
	private Double price;

	/**
	 * 备注
	 */
	private String note;

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Double getTotalUse() {
		return totalUse;
	}

	public void setTotalUse(Double totalUse) {
		this.totalUse = totalUse;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Double getUnitWear() {
		return unitWear;
	}

	public void setUnitWear(Double unitWear) {
		this.unitWear = unitWear;
	}

	public Double getWear() {
		return wear;
	}

	public void setWear(Double wear) {
		this.wear = wear;
	}
}
