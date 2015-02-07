package com.bestway.dzsc.checkcancel.entity;

public class TempDzscCheckImgDetil {

	/**
	 * 料件序号
	 */
	private String ptNum;

	/**
	 * 数量类型
	 */
	private String qtyType;

	/**
	 * 数量
	 */
	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getQtyType() {
		return qtyType;
	}

	public void setQtyType(String qtyType) {
		this.qtyType = qtyType;
	}

	public String getPtNum() {
		return ptNum;
	}

	public void setPtNum(String ptNum) {
		this.ptNum = ptNum;
	}

	

	

}
