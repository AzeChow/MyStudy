/*
 * Created on 2005-5-26
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractstat.entity;

import java.io.Serializable;

/**
 * 存放核销单登记表资料
 */
public class CancelAfterVerification implements Serializable{
	
	/**
	 * 报关单号
	 */
	private String customsDeclarationNo;
	
	/**
	 * 核销单号
	 */
	private String cavNo;
	
	/**
	 * 总值
	 */
	private Double totalPrice;
	
	/**
	 * 加工费
	 */
	private Double processPrice;
	
	/**
	 * 料费
	 */
	private Double materialPrice;
	
	/**
	 * 获取核销单号
	 * 
	 * @return cavNo 核销单号
	 */
	public String getCavNo() {
		return cavNo;
	}
	/**
	 * 获取报关单号
	 * 
	 * @return customsDeclarationNo 报关单号
	 */
	public String getCustomsDeclarationNo() {
		return customsDeclarationNo;
	}
	/**
	 * 获取料费
	 * 
	 * @return materialPrice 料费
	 */
	public Double getMaterialPrice() {
		return materialPrice;
	}
	/**
	 * 获取加工费
	 * 
	 * @return processPrice 加工费
	 */
	public Double getProcessPrice() {
		return processPrice;
	}
	/**
	 * 获取总值
	 * 
	 * @return totalPrice 总值
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}
	
	/**
	 * 设置核销单号
	 * 
	 * @param cavNo 核销单号
	 */
	public void setCavNo(String cavNo) {
		this.cavNo = cavNo;
	}
	
	/**
	 * 设置报关单号
	 * 
	 * @param customsDeclarationNo 报关单号
	 */
	public void setCustomsDeclarationNo(String customsDeclarationNo) {
		this.customsDeclarationNo = customsDeclarationNo;
	}
	
	/**
	 * 设置料费
	 * 
	 * @param materialPrice 料费
	 */
	public void setMaterialPrice(Double materialPrice) {
		this.materialPrice = materialPrice;
	}
	
	/**
	 * 设置加工费
	 * 
	 * @param processPrice 加工费
	 */
	public void setProcessPrice(Double processPrice) {
		this.processPrice = processPrice;
	}
	
	/**
	 * 设置总值
	 * 
	 * @param totalPrice 总值
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/** 
	 * 重写equals方法，当两个的核销单号相同就是相等，否则不等
	 * 
	 * @param arg0 要不较的
	 * @return boolean 当两个的核销单号相同就是相等返回true，否则不等返回false
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object arg0) {
		if(arg0==null){
			return false;
		}
		if(!arg0.getClass().equals(this.getClass())){
			return false;
		}
		CancelAfterVerification cav=(CancelAfterVerification)arg0;
		if(cav.getCavNo().equals(this.getCavNo())){
			return true;
		}
		return false;
	}
}
