/*
 * Created on 2005-3-22
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.qp.entity;

import com.bestway.common.CommonUtils;

/**
 * 存放合同备案BOM资料
 */
public class BcsQPContractBom implements java.io.Serializable {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 成品序号
	 */
	private Integer exgSeqNum;

	/**
	 * 单耗
	 */
	private Double unitWare = null;

	/**
	 * 损耗
	 */
	private Double ware = null;
	/**
	 * 非保税料件比例%
	 */
	private Double nonMnlRatio;

	/**
	 * 料件总表序号
	 */
	private Integer imgSeqNum = null;
	/**
	 * 获取非保税料件比例%
	 */
	public Double getNonMnlRatio() {
		return nonMnlRatio;
	}
	/**
	 * 设置非保税料件比例%
	 */
	public void setNonMnlRatio(Double nonMnlRatio) {
		this.nonMnlRatio = nonMnlRatio;
	}

	public Integer getExgSeqNum() {
		return exgSeqNum;
	}

	public void setExgSeqNum(Integer exgSeqNum) {
		this.exgSeqNum = exgSeqNum;
	}

	public Double getUnitWare() {
		return unitWare;
	}

	public void setUnitWare(Double unitWare) {
		this.unitWare = unitWare;
	}

	public Double getWare() {
		return ware;
	}

	public void setWare(Double ware) {
		this.ware = ware;
	}

	public Integer getImgSeqNum() {
		return imgSeqNum;
	}

	public void setImgSeqNum(Integer imgSeqNum) {
		this.imgSeqNum = imgSeqNum;
	}

}
