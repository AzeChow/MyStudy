package com.bestway.dzsc.dzscmanage.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;

public class TempDzscCustomsFactoryUnitWasteDiff implements
		java.io.Serializable {

	/**
	 * 备案序号
	 */
	private Integer imgSeqNum = null;

	/**
	 * 海关编码
	 */
	private Complex complex = null;

	/**
	 * 商品名称
	 */
	private String name = null;

	/**
	 * 型号规格
	 */
	private String spec = null;

	/**
	 * 海关单耗
	 */
	private Double customsUnitWare;

	/**
	 * 海关损耗
	 */
	private Double customsWare;

	/**
	 * 工厂单耗
	 */
	private Double factoryUnitWare;

	/**
	 * 工厂损耗
	 */
	private Double factoryWare;

	/**
	 * 单耗差异
	 */
	private Double unitWareDiff;

	/**
	 * 损耗差异
	 */
	private Double wareDiff;

	public Integer getImgSeqNum() {
		return imgSeqNum;
	}

	public void setImgSeqNum(Integer imgSeqNum) {
		this.imgSeqNum = imgSeqNum;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Double getCustomsUnitWare() {
		return customsUnitWare;
	}

	public void setCustomsUnitWare(Double customsUnitWare) {
		this.customsUnitWare = customsUnitWare;
	}

	public Double getCustomsWare() {
		return customsWare;
	}

	public void setCustomsWare(Double customsWare) {
		this.customsWare = customsWare;
	}

	public Double getFactoryUnitWare() {
		return factoryUnitWare;
	}

	public void setFactoryUnitWare(Double factoryUnitWare) {
		this.factoryUnitWare = factoryUnitWare;
	}

	public Double getFactoryWare() {
		return factoryWare;
	}

	public void setFactoryWare(Double factoryWare) {
		this.factoryWare = factoryWare;
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

	public Double getUnitWareDiff() {
		return unitWareDiff;
	}

	public void setUnitWareDiff(Double unitWareDiff) {
		this.unitWareDiff = unitWareDiff;
	}

	public Double getWareDiff() {
		return wareDiff;
	}

	public void setWareDiff(Double wareDiff) {
		this.wareDiff = wareDiff;
	}
}
