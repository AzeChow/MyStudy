package com.bestway.owp.entity;

import com.bestway.common.message.entity.CspSignInfo;

public class OwpSignInfo extends CspSignInfo {
	/**
	 * 电子口岸统一编号
	 */
	private String seqNo;


	/**
	 * 转入转出标志
	 */
	private String inOutFlag;

	/**
	 * 申请表编号
	 */
	private String appNo;
	
	/**
	 * 转出企业内部编号
	 */
	private String copBillNo;

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getCopBillNo() {
		return copBillNo;
	}

	public void setCopBillNo(String copBillNo) {
		this.copBillNo = copBillNo;
	}
	
	
}
