package com.bestway.common.erpbill.entity;

import java.io.Serializable;

/**
 * @author fhz
 *
 */
public class TempCustomOrderDetailAndAmount implements Serializable {
	
	/**
	 * 客户订单明细
	 */
	private CustomOrderDetail customOrderDetail;

	/**
	 * 是否被选中
	 */
	private Boolean isSelect = false;

	/**
	 * 出口数量
	 */
	private Double exportAmount;

	/**
	 * 转厂数量
	 */
	private Double TransferAmount;

	/**
	 * 已转合同数量
	 */
	private Double contractAmount;
	
	/**
	 * 出口数量+转厂数量
	 */
	private Double grossAmount;

	/**
	 * @return the customOrderDetail
	 */
	public CustomOrderDetail getCustomOrderDetail() {
		return customOrderDetail;
	}

	/**
	 * @param customOrderDetail
	 *            the customOrderDetail to set
	 */
	public void setCustomOrderDetail(CustomOrderDetail customOrderDetail) {
		this.customOrderDetail = customOrderDetail;
	}

	/**
	 * @return the exportAmount
	 */
	public Double getExportAmount() {
		return exportAmount;
	}

	/**
	 * @param exportAmount
	 *            the exportAmount to set
	 */
	public void setExportAmount(Double exportAmount) {
		this.exportAmount = exportAmount;
	}

	/**
	 * @return the isSelect
	 */
	public Boolean getIsSelect() {
		return isSelect;
	}

	/**
	 * @param isSelect
	 *            the isSelect to set
	 */
	public void setIsSelect(Boolean isSelect) {
		this.isSelect = isSelect;
	}

	/**
	 * @return the transmitAmount
	 */
	public Double getTransferAmount() {
		return TransferAmount;
	}

	/**
	 * @param transmitAmount
	 *            the transmitAmount to set
	 */
	public void setTransferAmount(Double transmitAmount) {
		this.TransferAmount = transmitAmount;
	}

	/**
	 * @return the contractAmount
	 */
	public Double getContractAmount() {
		return contractAmount;
	}

	/**
	 * @param contractAmount
	 *            the contractAmount to set
	 */
	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

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
}
