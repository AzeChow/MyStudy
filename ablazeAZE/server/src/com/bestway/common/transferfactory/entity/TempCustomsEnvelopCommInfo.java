package com.bestway.common.transferfactory.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class TempCustomsEnvelopCommInfo implements Serializable{
	private static final long serialVersionUID = 1L;

//	private Integer projectType = null;// 系统类型（0:联网监管；1：纸质手册；3：电子手册）
//
	private String emsNo = null;// 账册号（联网监管）；手册号（纸质手册和电子手册）

	private Integer seqNum = null;// 帐册序号

	private String complex = null;// 海关商品编码

	private String ptName = null; // 商品名称

	private String ptSpec = null; // 商品规格

	private Unit unit = null; // 单位

	private Curr curr = null;// 币制
	
	private Double price = null; //单价

	public String getComplex() {
		return complex;
	}

	public void setComplex(String complex) {
		this.complex = complex;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

//	public Integer getProjectType() {
//		return projectType;
//	}
//
//	public void setProjectType(Integer projectType) {
//		this.projectType = projectType;
//	}

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

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	/**
	 * @return Returns the price.
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
}
