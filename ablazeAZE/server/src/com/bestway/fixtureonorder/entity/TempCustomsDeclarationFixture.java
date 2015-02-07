package com.bestway.fixtureonorder.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;

/**
 * @author fhz
 *
 */
public class TempCustomsDeclarationFixture implements java.io.Serializable{
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode;
	
	/**
	 * 手册号
	 */
	private String emsNo;
	
	/**
	 * 报关单项号
	 */
	private String customsDeclaItemNo;
	/**
	 * 进出口日期
	 */
	private Date impExpDate;
	
	/**
	 * 商品编码
	 */
	private Complex complex;
	
	/**
	 * 设备名称
	 */
	private String commName;
	
	/**
	 * 设备规格
	 */
	private String commSpec;
	
	/**
	 * 设备数量
	 */
	private Double amount;
	
	/**
	 * 设备位置数量
	 */
	private Double locationAmount;
	
	/**
	 * 协议号
	 */
	private String agreementNo;
	
	/**
	 * 报关单流水号
	 */
	private String customsBillSeqNo;
	
	/**
     *  设备类型 0.为不作价设备，1.为借用设备
     */
    private Integer      fixKind;

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public String getCustomsBillSeqNo() {
		return customsBillSeqNo;
	}

	public void setCustomsBillSeqNo(String customsBillSeqNo) {
		this.customsBillSeqNo = customsBillSeqNo;
	}

	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	public Date getImpExpDate() {
		return impExpDate;
	}

	public void setImpExpDate(Date impExpDate) {
		this.impExpDate = impExpDate;
	}

	public String getCustomsDeclaItemNo() {
		return customsDeclaItemNo;
	}

	public void setCustomsDeclaItemNo(String customsDeclaItemNo) {
		this.customsDeclaItemNo = customsDeclaItemNo;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * @return the locationAmount
	 */
	public Double getLocationAmount() {
		return locationAmount;
	}

	/**
	 * @param locationAmount the locationAmount to set
	 */
	public void setLocationAmount(Double locationAmount) {
		this.locationAmount = locationAmount;
	}

	/**
	 * @return the fixKind
	 */
	public Integer getFixKind() {
		return fixKind;
	}

	/**
	 * @param fixKind the fixKind to set
	 */
	public void setFixKind(Integer fixKind) {
		this.fixKind = fixKind;
	}
}
