package com.bestway.common.erpbill.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TempCustomOrder implements Serializable {

	/**
	 * 商品编码
	 */
	private String code = null;
	/**
	 * 商品名称
	 */
	private String name = null;
	/**
	 * 规格型号
	 */
	private String spec = null;
	/**
	 * 计量单位
	 */
	private String unit = null;
	/**
	 * 送货日期
	 */
	private Date salesdate = null;
	/**
	 * 料号
	 */
	private String ptNo = null;
	/**
	 * 客户代码
	 */
	private String customer = null;
	/**
	 * 单价
	 */
	private Double unitPrice = 0.0;
	/**
	 * 数量
	 */
	private Double amount  = 0.0;
	/**
	 * 已转厂数量
	 */
	private Double transNum = 0.0;
	/**
	 * 已转合同数量
	 */
	private Double contractNum = 0.0;
	/**
	 * 未转厂数量
	 */
	private Double notTransNum = 0.0;
	/**
	 * 未转合同数量
	 */
	private Double notContractNum = 0.0;
	/**
	 * 总金额
	 */
	private Double totalPrice = 0.0;
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getPtNo() {
		return ptNo;
	}
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	public Date getSalesdate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (salesdate != null) {
			return java.sql.Date.valueOf(dateFormat.format(salesdate));
		}

		return salesdate;

	}
	public void setSalesdate(Date salesdate) {
		this.salesdate = salesdate;
	}
	public Double getContractNum() {
		return contractNum;
	}
	public void setContractNum(Double contractNum) {
		this.contractNum = contractNum;
	}
	public Double getNotContractNum() {
		return notContractNum;
	}
	public void setNotContractNum(Double notContractNum) {
		this.notContractNum = notContractNum;
	}
	public Double getNotTransNum() {
		return notTransNum;
	}
	public void setNotTransNum(Double notTransNum) {
		this.notTransNum = notTransNum;
	}
	public Double getTransNum() {
		return transNum;
	}
	public void setTransNum(Double transNum) {
		this.transNum = transNum;
	}

}
