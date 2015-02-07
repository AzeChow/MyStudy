package com.bestway.bcs.contract.entity;

import java.io.Serializable;

public class TempImgAndExgUsedDiffAmount implements Serializable {
	private Integer seqNum;

	private String commNameSpec;

	private Double imgAmount;

	private Double exgUsedAmount;

	private Double diffAmount;

	private Double diffRate;

	public String getCommNameSpec() {
		return commNameSpec;
	}

	public void setCommNameSpec(String commNameSpec) {
		this.commNameSpec = commNameSpec;
	}

	public Double getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}

	public Double getDiffRate() {
		return diffRate;
	}

	public void setDiffRate(Double diffRate) {
		this.diffRate = diffRate;
	}

	public Double getExgUsedAmount() {
		return exgUsedAmount;
	}

	public void setExgUsedAmount(Double exgUsedAmount) {
		this.exgUsedAmount = exgUsedAmount;
	}

	public Double getImgAmount() {
		return imgAmount;
	}

	public void setImgAmount(Double imgAmount) {
		this.imgAmount = imgAmount;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
}
