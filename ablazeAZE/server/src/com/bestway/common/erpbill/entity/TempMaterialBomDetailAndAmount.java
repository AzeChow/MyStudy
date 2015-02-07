package com.bestway.common.erpbill.entity;

import java.io.Serializable;

import com.bestway.common.materialbase.entity.MaterialBomDetail;

/**
 * @author fhz
 *
 */
public class TempMaterialBomDetailAndAmount implements Serializable {
	
	/**
	 * 报关常用工厂BOM料件
	 */
	private MaterialBomDetail materialBomDetail;
	
	/**
	 * 客户订单明细
	 */
	private CustomOrderDetail customOrderDetail;
	
	
	/**
	 * 当两个materialBomDetail相同时就把他们的数量加起来
	 */
	private Double grossAmount;
	

	/**
	 * @return the grossAmount
	 */
	public Double getGrossAmount() {
		return grossAmount;
	}

	/**
	 * @param grossAmount the grossAmount to set
	 */
	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
	}

	/**
	 * @return the materialBomDetail
	 */
	public MaterialBomDetail getMaterialBomDetail() {
		return materialBomDetail;
	}

	/**
	 * @param materialBomDetail the materialBomDetail to set
	 */
	public void setMaterialBomDetail(MaterialBomDetail materialBomDetail) {
		this.materialBomDetail = materialBomDetail;
	}

	/**
	 * @return the customOrderDetail
	 */
	public CustomOrderDetail getCustomOrderDetail() {
		return customOrderDetail;
	}

	/**
	 * @param customOrderDetail the customOrderDetail to set
	 */
	public void setCustomOrderDetail(CustomOrderDetail customOrderDetail) {
		this.customOrderDetail = customOrderDetail;
	}
}
