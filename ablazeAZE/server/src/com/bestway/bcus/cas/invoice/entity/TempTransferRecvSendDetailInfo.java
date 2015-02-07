package com.bestway.bcus.cas.invoice.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.CalUnit;

public class TempTransferRecvSendDetailInfo implements java.io.Serializable {
	
	private static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 单据号码
	 */
	private String billNo;

	/**
	 * 采购/业务订单号
	 */
	private String poSoBillNo;

	/**
	 * 生效日期
	 */
	private Date validDate;

	/**
	 * 工厂料号
	 */
	private String ptPart;

	/**
	 * 商品名称
	 */
	private String ptName;

	/**
	 * 规格型号
	 */
	private String ptSpec;

	/**
	 * 单位
	 */
	private CalUnit ptUnit;

	/**
	 * 收货数量
	 */
	private Double ptRecvAmount;

	/**
	 * 退货数量
	 */
	private Double ptBackAmount;

	/**
	 * 海关商品编码
	 */

	private Complex complex;

	/**
	 * 海关商品名称
	 */
	private String hsName;

	/**
	 * 海关商品规格型号
	 */
	private String hsSpec;

	/**
	 * 序号
	 */
	private Integer seqNum;

	/**
	 * 折算海关收货数量
	 */
	private Double hsRecvAmount;

	/**
	 * 折算海关退货数量
	 */
	private Double hsBackAmount;

	/**
	 * 海关单位
	 */
	private Unit hsUnit;
	
	
	

	/*
	 * 关封号
	 */
	private String envelopNo;
	
	/*
	 * 对应报单号
	 */
	private String customNo;
	

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Double getHsRecvAmount() {
		return hsRecvAmount;
	}

	public void setHsRecvAmount(Double hsAmount) {
		this.hsRecvAmount = hsAmount;
	}

	public String getHsName() {
		return hsName;
	}

	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	public String getHsSpec() {
		return hsSpec;
	}

	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	public Unit getHsUnit() {
		return hsUnit;
	}

	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public CalUnit getPtUnit() {
		return ptUnit;
	}

	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getPoSoBillNo() {
		return poSoBillNo;
	}

	public void setPoSoBillNo(String poSoBillNo) {
		this.poSoBillNo = poSoBillNo;
	}

	public Double getPtBackAmount() {
		return ptBackAmount;
	}

	public void setPtBackAmount(Double ptBackAmount) {
		this.ptBackAmount = ptBackAmount;
	}

	public Double getPtRecvAmount() {
		return ptRecvAmount;
	}

	public void setPtRecvAmount(Double ptRecvAmount) {
		this.ptRecvAmount = ptRecvAmount;
	}

	public Double getHsBackAmount() {
		return hsBackAmount;
	}

	public void setHsBackAmount(Double hsBackAmount) {
		this.hsBackAmount = hsBackAmount;
	}
	
	
	
	
	public String getEnvelopNo() {
		return envelopNo;
	}

	public void setEnvelopNo(String envelopNo) {
		this.envelopNo = envelopNo;
	}

	public String getCustomNo() {
		return customNo;
	}

	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}

	public String getBillYear(){
		if(this.validDate==null){
			return "";
		}else{
			return dateFormat.format(this.validDate).substring(0, 4);
		}
	}
	
	public String getBillMonth(){
		if(this.validDate==null){
			return "";
		}else{
			return dateFormat.format(this.validDate).substring(5, 7);
		}
	}
	
	public String getBillDay(){
		if(this.validDate==null){
			return "";
		}else{
			return dateFormat.format(this.validDate).substring(8);
		}
	}
}
