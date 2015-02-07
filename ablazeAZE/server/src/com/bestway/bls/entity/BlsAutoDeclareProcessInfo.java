package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;

/**
 * 自动海关申报及回执处理记录
 * @author Administrator
 *
 */
public class BlsAutoDeclareProcessInfo  extends BaseScmEntity{
	/**
	 * 类型BlsServiceType
	 */
	private String serviceType=null;
	
	/**
	 * 自动申报：true 自动回执处理：false
	 */
	private Boolean isAutoDeclare=true;
	
	/**
	 * 进仓 "I" 出仓 "O"
	 */
	private String inOut=null;
	
	/**
	 * 车次号或仓单号
	 */
	private String billCode=null;
	/**
	 * 申报日期
	 */
	private Date declareProcessDate=null;
	/**
	 * 申报结果 0:退单 1:通过 2:正在审批
	 */
	private String declareProcessResult;
	/**
	 * 备注
	 */
	private String memo;
	
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
	public Date getDeclareProcessDate() {
		return declareProcessDate;
	}
	public void setDeclareProcessDate(Date declareDate) {
		this.declareProcessDate = declareDate;
	}
	public String getDeclareProcessResult() {
		return declareProcessResult;
	}
	public void setDeclareProcessResult(String declareResult) {
		this.declareProcessResult = declareResult;
	}
	public Boolean getIsAutoDeclare() {
		return isAutoDeclare;
	}
	public void setIsAutoDeclare(Boolean isAutoDeclare) {
		this.isAutoDeclare = isAutoDeclare;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = this.getFieldValueByLen(memo,255);
	}	
}
