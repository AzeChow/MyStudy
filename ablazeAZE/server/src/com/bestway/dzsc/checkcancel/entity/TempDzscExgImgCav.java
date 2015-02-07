package com.bestway.dzsc.checkcancel.entity;

public class TempDzscExgImgCav {
	/**
	 * 备案序号
	 */
	private Integer seqNum;
	
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

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	
}
