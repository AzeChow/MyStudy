package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.ScmCoc;

public class TempSumInvoiceInfo implements Serializable {

	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = -679704071893023803L;
	
	/**
	 * 供应商
	 */
	private String customer = null;
	/**
	 * 报关商品名称
	 */
	private String cuName = null;
	/**
	 * 报关商品单位
	 */
	private String unit = null;
	/**
	 * 总计数量
	 */
	private Double sumAmount = null;
	/**
	 * 总计金额
	 */
	private Double sumMoney = null;
	/**
	 * 总计重量
	 */
	private Double sumWeight = null;
	/**
	 * 
	 * @return
	 */
	public String getCuName() {
		return cuName;
	}
	/**
	 * 
	 * @param cuName
	 */
	public void setCuName(String cuName) {
		this.cuName = cuName;
	}
	/**
	 * 
	 * @return
	 */
	public Double getSumAmount() {
		return sumAmount;
	}
	/**
	 * 
	 * @param sumAmount
	 */
	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}
	/**
	 * 
	 * @return
	 */
	public Double getSumMoney() {
		return sumMoney;
	}
	/**
	 * 
	 * @param sumMoney
	 */
	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}
	/**
	 * 
	 * @return
	 */
	public Double getSumWeight() {
		return sumWeight;
	}
	/**
	 * 
	 * @param sumWeight
	 */
	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
