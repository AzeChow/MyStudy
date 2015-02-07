package com.bestway.bcs.contractcav.entity;

import java.io.Serializable;

public class TempContractCavDomesticPurchaseBill implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 料件序号
	 */
	private Integer serialNumber;
	/**
	 * 料件名称
	 */
	private String imgName;
	/**
	 * 单位名称
	 */
	private String unit;
	/**
	 * 用在成品上的国内购料数量
	 */
	private Double amountForDomesticPurchase;
	/**
	 * 用在成品上的国内购料重量
	 */
	private Double wightForExgDomesticPurchase;
	/**
	 * 申请国内购料重量
	 */
	private Double imgWight;
	/**
	 * 总价
	 */
	private Double totalPrice;

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getAmountForDomesticPurchase() {
		return amountForDomesticPurchase;
	}

	public void setAmountForDomesticPurchase(Double amountForDomesticPurchase) {
		this.amountForDomesticPurchase = amountForDomesticPurchase;
	}

	public Double getImgWight() {
		return imgWight;
	}

	public void setImgWight(Double imgWight) {
		this.imgWight = imgWight;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getWightForExgDomesticPurchase() {
		return wightForExgDomesticPurchase;
	}

	public void setWightForExgDomesticPurchase(Double wightForExgDomesticPurchase) {
		this.wightForExgDomesticPurchase = wightForExgDomesticPurchase;
	} 
}
