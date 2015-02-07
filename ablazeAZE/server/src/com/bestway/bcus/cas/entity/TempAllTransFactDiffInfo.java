package com.bestway.bcus.cas.entity;

public class TempAllTransFactDiffInfo implements java.io.Serializable,
		java.lang.Comparable {
	private String complexCode;

	private String commName;

	private String commUnit;

	private String scmCocName;

	private String beginDate;

	private Double sendAmount;

	private Double sendWeight;

	private Double backAmount;

	private Double backWeight;

	/**
	 * 转厂数量
	 */
	private Double transAmount;

	/**
	 * 转厂重量
	 */
	private Double transWeight;

	private Double waitTransAmount;

	private Double waitTransWeight;

	private Double exceedTransAmount;

	private Double exceedTransWeight;

	private Double envelopAmount;

	private Double envelopWeight;

	private Double envelopRemainAmount;

	private Double envelopRemainWeight;

	private Double diffAmount;

	private Double diffWeight;
	
	private Double plusAmount;
	
	private Double plusWeight;
	
	private Double minusAmount;
	
	private Double minusWeight;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommUnit() {
		return commUnit;
	}

	public void setCommUnit(String commUnit) {
		this.commUnit = commUnit;
	}

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public Double getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}

	public Double getDiffWeight() {
		return diffWeight;
	}

	public void setDiffWeight(Double diffWeight) {
		this.diffWeight = diffWeight;
	}

	public Double getEnvelopRemainAmount() {
		return envelopRemainAmount;
	}

	public void setEnvelopRemainAmount(Double envelopRemainAmount) {
		this.envelopRemainAmount = envelopRemainAmount;
	}

	public Double getEnvelopRemainWeight() {
		return envelopRemainWeight;
	}

	public void setEnvelopRemainWeight(Double envelopRemainWeight) {
		this.envelopRemainWeight = envelopRemainWeight;
	}

	public Double getExceedTransAmount() {
		return exceedTransAmount;
	}

	public void setExceedTransAmount(Double exceedTransAmount) {
		this.exceedTransAmount = exceedTransAmount;
	}

	public Double getExceedTransWeight() {
		return exceedTransWeight;
	}

	public void setExceedTransWeight(Double exceedTransWeight) {
		this.exceedTransWeight = exceedTransWeight;
	}

	public String getScmCocName() {
		return scmCocName;
	}

	public void setScmCocName(String scmCocName) {
		this.scmCocName = scmCocName;
	}

	public Double getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(Double sendAmount) {
		this.sendAmount = sendAmount;
	}

	public Double getSendWeight() {
		return sendWeight;
	}

	public void setSendWeight(Double sendWeight) {
		this.sendWeight = sendWeight;
	}

	/**
	 * 获取转厂数量
	 * @return
	 */
	public Double getTransAmount() {
		return transAmount;
	}

	/**
	 * 设置转厂数量
	 * @param transAmount
	 */
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}

	/**
	 * 获取转厂重量
	 * @return
	 */
	public Double getTransWeight() {
		return transWeight;
	}

	/**
	 * 设置转厂重量
	 * @param transWeight
	 */
	public void setTransWeight(Double transWeight) {
		this.transWeight = transWeight;
	}

	public Double getWaitTransAmount() {
		return waitTransAmount;
	}

	public void setWaitTransAmount(Double waitTransAmount) {
		this.waitTransAmount = waitTransAmount;
	}

	public Double getWaitTransWeight() {
		return waitTransWeight;
	}

	public void setWaitTransWeight(Double waitTransWeight) {
		this.waitTransWeight = waitTransWeight;
	}

	public Double getBackAmount() {
		return backAmount;
	}

	public void setBackAmount(Double backAmount) {
		this.backAmount = backAmount;
	}

	public Double getBackWeight() {
		return backWeight;
	}

	public void setBackWeight(Double backWeight) {
		this.backWeight = backWeight;
	}

	public Double getEnvelopAmount() {
		return envelopAmount;
	}

	public void setEnvelopAmount(Double envelopAmount) {
		this.envelopAmount = envelopAmount;
	}

	public Double getEnvelopWeight() {
		return envelopWeight;
	}

	public void setEnvelopWeight(Double envelopWeight) {
		this.envelopWeight = envelopWeight;
	}

	public String getKey() {
		return ((this.complexCode == null ? "" : this.complexCode.trim()) + "/"
				+ (this.commName == null ? "" : this.commName.trim()) + "/"
				+ (this.commUnit == null ? "" : this.commUnit.trim()) + "/" + (this.scmCocName == null ? ""
				: this.scmCocName.trim()));
	}

	public int compareTo(Object o) {
		return this.getKey().compareTo(((TempAllTransFactDiffInfo) o).getKey());
	}

	/**
	 * @return the plusAmount
	 */
	public Double getPlusAmount() {
		return plusAmount;
	}

	/**
	 * @param plusAmount the plusAmount to set
	 */
	public void setPlusAmount(Double plusAmount) {
		this.plusAmount = plusAmount;
	}

	/**
	 * @return the plusWeight
	 */
	public Double getPlusWeight() {
		return plusWeight;
	}

	/**
	 * @param plusWeight the plusWeight to set
	 */
	public void setPlusWeight(Double plusWeight) {
		this.plusWeight = plusWeight;
	}

	/**
	 * @return the minusAmount
	 */
	public Double getMinusAmount() {
		return minusAmount;
	}

	/**
	 * @param minusAmount the minusAmount to set
	 */
	public void setMinusAmount(Double minusAmount) {
		this.minusAmount = minusAmount;
	}

	/**
	 * @return the minusWeight
	 */
	public Double getMinusWeight() {
		return minusWeight;
	}

	/**
	 * @param minusWeight the minusWeight to set
	 */
	public void setMinusWeight(Double minusWeight) {
		this.minusWeight = minusWeight;
	}
}
