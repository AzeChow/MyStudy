package com.bestway.bcus.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.CalUnit;

/**
 * 成品折算料件
 */
public class FactoryStoryProduct extends BaseScmEntity {
	/**
	 * 料号
	 */
	private String ptNo = null;

	/**
	 * 品名
	 */
	private String ptName = null;

	/**
	 * 规格
	 */
	private String ptSpec = null;

	/**
	 * 数量
	 */
	private Double quantity = null;

	/**
	 * 计量单位
	 */
	private String units = null;
	

	/**
	 * 单价
	 */
	private Double unitPrice = null;

	/**
	 * 总金额
	 */
	private Double amountPrice = null;

	/**
	 * 毛重
	 */
	private Double grossWeight = null;

	/**
	 * 净重
	 */
	private Double netWeight = null;
	/**
	 * 是否归并
	 */
	private Boolean isInnerMeger = false;


	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public Double getAmountPrice() {
		return amountPrice;
	}

	public void setAmountPrice(Double amountPrice) {
		this.amountPrice = amountPrice;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Boolean getIsInnerMeger() {
		return isInnerMeger;
	}

	public void setIsInnerMeger(Boolean isInnerMeger) {
		this.isInnerMeger = isInnerMeger;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

}
