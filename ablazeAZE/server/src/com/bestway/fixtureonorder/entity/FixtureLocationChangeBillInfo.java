package com.bestway.fixtureonorder.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.BaseScmEntity;
/**
 * 最终异动单据资料
 * @author fhz
 *
 */
public class FixtureLocationChangeBillInfo extends BaseScmEntity {
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode;

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
	 * 设备报关单数量
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
	 * 手册号
	 */
	private String emsNo;

	/**
	 * 报关单流水号
	 */
	private String customsBillSeqNo;
	
	/**
	 * 报关单项号
	 */
	private String customsDeclaItemNo;

	/**
	 * 旧位置
	 */
	private FixtureLocation oldLocation;

	/**
	 * 新位置
	 */
	private FixtureLocation newLocation;

	/**
	 * 单据号码
	 */
	private String billCode;

	/**
	 * 经手人
	 */
	private String handMan;

	/**
	 * 异动日期
	 */
	private Date changeDate;
	
	/**
	 * 异动类型
	 * CUSTOMS_IN_FACT = 0;//设备报关进厂
	 * FACT_CHANGE_LOCATION = 1;//设备厂内位移
	 * FACT_ADD = 2;//设备厂内增加
	 * FACT_SUBTRACT = 3;设备厂内减少
	 */
	private Integer changeType;
	
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

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
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

	public String getHandMan() {
		return handMan;
	}

	public void setHandMan(String handMan) {
		this.handMan = handMan;
	}

	public Date getImpExpDate() {
		return impExpDate;
	}

	public void setImpExpDate(Date impExpDate) {
		this.impExpDate = impExpDate;
	}

	public FixtureLocation getNewLocation() {
		return newLocation;
	}

	public void setNewLocation(FixtureLocation newLocation) {
		this.newLocation = newLocation;
	}

	public FixtureLocation getOldLocation() {
		return oldLocation;
	}

	public void setOldLocation(FixtureLocation oldLocation) {
		this.oldLocation = oldLocation;
	}

	public String getCustomsDeclaItemNo() {
		return customsDeclaItemNo;
	}

	public void setCustomsDeclaItemNo(String customsDeclaItemNo) {
		this.customsDeclaItemNo = customsDeclaItemNo;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * @param emsNo the emsNo to set
	 */
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
