package com.bestway.fixtureonorder.entity;

import java.io.Serializable;

/**
 * @author fhz
 *
 */
public class TempOthersBillInfo implements Serializable{
	/**
	 * 单据号码
	 */
	private String billCode;
	/**
	 * 经手人
	 */
	private String handMan;
	
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getHandMan() {
		return handMan;
	}
	public void setHandMan(String handMan) {
		this.handMan = handMan;
	}
}
