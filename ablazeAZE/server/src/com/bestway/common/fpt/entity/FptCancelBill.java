package com.bestway.common.fpt.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 撤消申请表
 * 
 * @author sanvi
 * 
 */
public class FptCancelBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 申请表编号
	 */
	private String appNo;

	/**
	 * 转出企业内部编号
	 */
	private String copNo;

	/**
	 * 电子口岸统一编号
	 */
	private String seqNo = null;

	/**
	 * 企业编号
	 */
	private String tradeCode = null;

	/**
	 * 撤消类型（0为撤消发货单，1为撤消退货单）
	 */
	private String canelSort = null;

	/**
	 * 申报状态
	 */

	private String declareState = null;

	/**
	 * 备注 X(128) 非空 撤消原因
	 */
	private String note = null;
	
	/**
	 * 单据类型标志 收发货单 2 退货单 3
	 */
	private String sysType = null;;

	/**
	 * 进出口标志中的进口标志 0 出口1 进口
	 * 
	 * 备注 :发货与收退货是0 ;收货与发退货是1
	 */
	private String billSort = null;
	
	/**
	 * 收发货单号
	 */
	private String billNo =null;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getCopNo() {
		return copNo;
	}

	public void setCopNo(String outCopNo) {
		this.copNo = outCopNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getCanelSort() {
		return canelSort;
	}

	public void setCanelSort(String canelSort) {
		this.canelSort = canelSort;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDeclareState() {
		return declareState;
	}

	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getBillSort() {
		return billSort;
	}

	public void setBillSort(String billSort) {
		this.billSort = billSort;
	}
	
}
