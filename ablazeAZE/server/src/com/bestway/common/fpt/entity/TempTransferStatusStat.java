package com.bestway.common.fpt.entity;

import java.io.Serializable;

public class TempTransferStatusStat implements Serializable {
	/**
	 * 手册号
	 */
	private String emsNo = null;
	/**
	 * 手册号
	 */
	private Integer emsSeq = null;
	/**
	 * 客户供应商
	 */
	private String scmCoc = null;
	/**
	 * 商品名称
	 */
	private String name = null;
	/**
	 * 商品编码
	 */
	private String code = null;
	/**
	 * 商品规格
	 */
	private String spec = null;
	/**
	 * 单位
	 */
	private String unit = null;
	/**
	 * 申请单编号
	 */
	private String billNo = null;
	/**
	 * 前期进出货累计
	 */
	private Double beTransAmount = null;
	/**
	 * 前期转厂累计
	 */
	private Double beYesTrans = null;
	/**
	 * 前期未转厂累计
	 */
	private Double beNoTrans = null;
	/**
	 * 本期进出货累计
	 */
	private Double thisTransAmount = null;
	/**
	 * 本期转厂累计
	 */
	private Double thYesTrans = null;
	/**
	 * 本期未转厂累计
	 */
	private Double thNoTrans = null;
	/**
	 * 总计未转厂累计
	 */
	private Double noTransAmount = null;

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public String getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(String scmCoc) {
		this.scmCoc = scmCoc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Double getBeTransAmount() {
		return beTransAmount;
	}

	public void setBeTransAmount(Double beTransAmount) {
		this.beTransAmount = beTransAmount;
	}

	public Double getBeYesTrans() {
		return beYesTrans;
	}

	public void setBeYesTrans(Double beYesTrans) {
		this.beYesTrans = beYesTrans;
	}

	public Double getBeNoTrans() {
		return beNoTrans;
	}

	public void setBeNoTrans(Double beNoTrans) {
		this.beNoTrans = beNoTrans;
	}

	public Double getThisTransAmount() {
		return thisTransAmount;
	}

	public void setThisTransAmount(Double thisTransAmount) {
		this.thisTransAmount = thisTransAmount;
	}

	public Double getThYesTrans() {
		return thYesTrans;
	}

	public void setThYesTrans(Double thYesTrans) {
		this.thYesTrans = thYesTrans;
	}

	public Double getThNoTrans() {
		return thNoTrans;
	}

	public void setThNoTrans(Double thNoTrans) {
		this.thNoTrans = thNoTrans;
	}

	public Double getNoTransAmount() {
		return noTransAmount;
	}

	public void setNoTransAmount(Double noTransAmount) {
		this.noTransAmount = noTransAmount;
	}

	public Integer getEmsSeq() {
		return emsSeq;
	}

	public void setEmsSeq(Integer emsSeq) {
		this.emsSeq = emsSeq;
	}
}
