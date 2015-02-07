package com.bestway.common.message.entity;

import java.util.Date;

public class CspSignInfo {
	/**
	 * 企业编码
	 */
	private String tradeCode;

	/**
	 * 企业内部编码
	 */
	private String copNo;

	/**
	 * 业务类型 例如备案资料库，通关手册等(注：在转厂单据里是送收货单0-收发货单1-收退货单)
	 */
	private String sysType;

	/**
	 * 申报类型
	 */
	private String declareType;

	/**
	 * 核查报核次数
	 */
	private Integer colDcrTime;

	/**
	 * 证书号
	 */
	private String certSeqNo;

	/**
	 * 签名人IC卡ID
	 */
	private String idCard;

	/**
	 * 签名者姓名#
	 */
	private String icCardName;

	/**
	 * 签名者单位统一编号#
	 */
	private String icCardUnitCode;

	/**
	 * 签名者单位名称#
	 */
	private String icCardUnitName;

	/**
	 * 签名时间
	 */
	private Date signDate;

	/**
	 * 数字签名
	 */
	private String sign;

	/**
	 * 备用字段
	 */
	private String note;

	/**
	 * 备用字段1
	 */
	private String note1;

	/**
	 * 备用代码1
	 */
	private String noteCode1;

	/**
	 * 备用代码2
	 */
	private String noteCode2;

	public String getCertSeqNo() {
		return certSeqNo;
	}

	public void setCertSeqNo(String certSeqNo) {
		this.certSeqNo = certSeqNo;
	}

	public Integer getColDcrTime() {
		return colDcrTime;
	}

	public void setColDcrTime(Integer colDcrTime) {
		this.colDcrTime = colDcrTime;
	}

	public String getCopNo() {
		return copNo;
	}

	public void setCopNo(String copNo) {
		this.copNo = copNo;
	}

	public String getDeclareType() {
		return declareType;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote1() {
		return note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNoteCode1() {
		return noteCode1;
	}

	public void setNoteCode1(String noteCode1) {
		this.noteCode1 = noteCode1;
	}

	public String getNoteCode2() {
		return noteCode2;
	}

	public void setNoteCode2(String noteCode2) {
		this.noteCode2 = noteCode2;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getIcCardName() {
		return icCardName;
	}

	public void setIcCardName(String icCardName) {
		this.icCardName = icCardName;
	}

	public String getIcCardUnitCode() {
		return icCardUnitCode;
	}

	public void setIcCardUnitCode(String icCardUnitCode) {
		this.icCardUnitCode = icCardUnitCode;
	}

	public String getIcCardUnitName() {
		return icCardUnitName;
	}

	public void setIcCardUnitName(String icCardUnitName) {
		this.icCardUnitName = icCardUnitName;
	}

}
