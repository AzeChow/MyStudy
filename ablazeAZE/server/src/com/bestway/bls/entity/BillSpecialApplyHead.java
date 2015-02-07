package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;

/**
 * 仓单特殊申请单表头
 * @author Administrator
 *
 */
public class BillSpecialApplyHead extends BaseScmEntity{

	/**
	 * 企业编码
	 */
	private String tradeCo;
	
	/**
	 * 企业名称
	 */
	private String tradeName;
	
	/**
	 * 仓单号
	 */
	private String billNo;
	
	/**
	 * 特殊申请类型
	 */
	private String applyType;
	
	/**
	 * 申报时间 
	 */
	private Date applyDate;
	
	/**
	 * 申报人
	 */
	private String applyPerson;
	
	/**
	 * 录入时间
	 */
	private Date writeDate;
	
	/**
	 * 录入人
	 */
	private String writePerson;
	
	/** 申报状态	初始状态、等待审批、正在执行、退单 */
	private String declareState;
	
	/**
	 * 申请原因
	 */
	private String applyReason;
	
	/**
	 * 备注
	 */
	private String note;

	/**
	 * 企业编码
	 */
	public String getTradeCo() {
		return tradeCo;
	}

	/**
	 * 企业编码
	 */
	public void setTradeCo(String tradeCo) {
		this.tradeCo = tradeCo;
	}

	/**
	 * 仓单号
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 仓单号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 特殊申请类型
	 */
	public String getApplyType() {
		return applyType;
	}

	/**
	 * 特殊申请类型
	 */
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	/**
	 * 申请原因
	 */
	public String getApplyReason() {
		return applyReason;
	}

	/**
	 * 申请原因
	 */
	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	/**
	 * 企业编码
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 企业编码
	 */
	public void setNote(String note) {
		this.note = note;
	}

	public String getTradeName() {
		return tradeName;
	}

	public String getDeclareState() {
		return declareState;
	}

	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyPerson() {
		return applyPerson;
	}

	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public String getWritePerson() {
		return writePerson;
	}

	public void setWritePerson(String writePerson) {
		this.writePerson = writePerson;
	}	
	
}
