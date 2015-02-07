package com.bestway.bls.entity;

import java.io.Serializable;

public class TempQueryBill implements Serializable{

	/**
	 * 企业编码
	 */
	private String tradeCo=null;
	
	/**
	 * 仓单号
	 */
	private String billNo=null;
	/**
	 * 获取企业编码
	 * @return
	 */
	public String getTradeCo() {
		return tradeCo;
	}
	/**
	 * 设置企业编码
	 * @param tradeCo
	 */
	public void setTradeCo(String tradeCo) {
		this.tradeCo = tradeCo;
	}
	/**
	 * 获取仓单号
	 * @return
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * 设置仓单号
	 * @param billNo
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
}
