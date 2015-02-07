package com.bestway.bcus.cas.entity;

import java.io.Serializable;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * @author fhz
 * 
 * 保税设备报表
 */
public class TempFixtureNotInTaxationReport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 流水号
	 */
	private Integer serialNumber;
	
	/**
	 *特殊报关单体
	 */
	private BaseCustomsDeclarationCommInfo baseCustomsCommInfo=null;
	
	/**
	 * 设备表体
	 */
	private BillDetail billDetail=null;
	
	/**
	 * 状态名称，未解除监管、已解除监管
	 */
	private String stateName=null;

	public BaseCustomsDeclarationCommInfo getBaseCustomsCommInfo() {
		return baseCustomsCommInfo;
	}

	public void setBaseCustomsCommInfo(
			BaseCustomsDeclarationCommInfo baseCustomsCommInfo) {
		this.baseCustomsCommInfo = baseCustomsCommInfo;
	}

	public BillDetail getBillDetail() {
		return billDetail;
	}

	public void setBillDetail(BillDetail billDetail) {
		this.billDetail = billDetail;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	
	
	
}
