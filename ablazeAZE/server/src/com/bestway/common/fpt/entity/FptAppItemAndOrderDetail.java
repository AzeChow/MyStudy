package com.bestway.common.fpt.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;

public class FptAppItemAndOrderDetail extends BaseScmEntity {
	/**
	 * 转厂申请单表体ID
	 */
	private String fptAppItemId = null;
	/**
	 * 订单表体ID
	 */
	private String orderDetail = null;
	/**
	 * 转出数量
	 */
	private Double amount=0.0;
	/**
	 * 转出日期
	 */
	private Date  tradeData=null;
	public String getFptAppItemId() {
		return fptAppItemId;
	}
	public void setFptAppItemId(String fptAppItemId) {
		this.fptAppItemId = fptAppItemId;
	}
	public String getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getTradeData() {
		return tradeData;
	}
	public void setTradeData(Date tradeData) {
		this.tradeData = tradeData;
	}
}
