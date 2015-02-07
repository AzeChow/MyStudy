package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;

/**
 * 自动海关申报退单记录
 * 
 * @author Administrator
 * 
 */
public class BlsAutoBackBillInfo extends BaseScmEntity {
	/**
	 * 类型BlsServiceType
	 */
	private String serviceType = null;
	/**
	 * 相关联id
	 */
	private String relateId = null;

	/**
	 * 进仓 "I" 出仓 "O"
	 */
	private String inOut = null;

	/**
	 * 车次号或仓单号
	 */
	private String billCode = null;

	/**
	 * 申报次数
	 */
	private Integer declareTimes;
	/**
	 * 申报日期，时间
	 */
	private String declareDates;

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getInOut() {
		return inOut;
	}

	public void setInOut(String inOut) {
		this.inOut = inOut;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getRelateId() {
		return relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}

	public Integer getDeclareTimes() {
		return declareTimes;
	}

	public void setDeclareTimes(Integer declareTimes) {
		this.declareTimes = declareTimes;
	}

	public String getDeclareDates() {
		return declareDates;
	}

	public void setDeclareDates(String declareDates) {
		this.declareDates = declareDates;
	}

}
