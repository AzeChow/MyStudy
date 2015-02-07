/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 
 */
public class TempImpExpRequestBillForInput implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 进出口申请单表头
	 */
	private ImpExpRequestBill billHead = null;

	/**
	 * 进出口申请单表体
	 */
	private ImpExpCommodityInfo commInfo = null;

	/**
	 * 错误原因
	 */
	private String errinfo = null;

	public ImpExpRequestBill getBillHead() {
		return billHead;
	}

	public void setBillHead(ImpExpRequestBill billHead) {
		this.billHead = billHead;
	}

	public ImpExpCommodityInfo getCommInfo() {
		return commInfo;
	}

	public void setCommInfo(ImpExpCommodityInfo commInfo) {
		this.commInfo = commInfo;
	}

	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

}