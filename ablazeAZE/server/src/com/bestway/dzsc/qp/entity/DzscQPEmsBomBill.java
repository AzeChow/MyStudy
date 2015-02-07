/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.qp.entity;

import com.bestway.common.CommonUtils;

/**
 * 存放手册通过备案的BOM资料
 * 
 * @author yp
 */
public class DzscQPEmsBomBill implements java.io.Serializable {
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
	 * 料件总表序号
	 */
	private Integer imgSeqNum = null;

	/**
	 * 获取单耗
	 * 
	 * @return unitWare 单耗
	 */
	public Double getUnitWare() {
		return unitWare;
	}

	/**
	 * 设置单耗
	 * 
	 * @param unitWare
	 *            单耗
	 */
	public void setUnitWare(Double unitWare) {
		this.unitWare = unitWare;
	}

	/**
	 * 获取损耗
	 * 
	 * @return ware 损耗
	 */
	public Double getWare() {
		return ware;
	}

	/**
	 * 设置损耗
	 * 
	 * @param ware
	 *            损耗
	 */
	public void setWare(Double ware) {
		this.ware = ware;
	}


	/**
	 * 获取料件总表序号
	 * 
	 * @return imgSeqNum 料件总表序号
	 */
	public Integer getImgSeqNum() {
		return imgSeqNum;
	}

	/**
	 * 设置料件总表序号
	 * 
	 * @param imgSeqNum
	 *            料件总表序号
	 */
	public void setImgSeqNum(Integer imgSeqNum) {
		this.imgSeqNum = imgSeqNum;
	}

	public Integer getBomVersion() {
		return 0;
	}

	public Integer getExgSeqNum() {
		return exgSeqNum;
	}

	public void setExgSeqNum(Integer exgBillSeqNum) {
		this.exgSeqNum = exgBillSeqNum;
	}
}