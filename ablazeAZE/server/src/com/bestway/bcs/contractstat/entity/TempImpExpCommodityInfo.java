package com.bestway.bcs.contractstat.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;

public class TempImpExpCommodityInfo extends ImpExpCommodityInfo implements
		Serializable, Comparable {
	/**
	 * 数量累计 
	 */
	private Double commAddUpAmount;
	/**
	 * 手册号码
	 */
	private String emsHeadH2k;
	/**
	 * 报关单流水号
	 */
	private Integer serialNumber;
	
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode;
	/**
	 * 是否生效
	 */
	private Boolean effective = false;
	
	/**
	 * 备注
	 */
	private String detailNote;
	
	/**
	 * 申报日期
	 */
	private Date declarationDate;
	/**
	 * 贸易方式
	 */
	private Trade tradeMode;
	/**
	 * 数量累计 
	 */
	public Double getCommAddUpAmount() {
		return commAddUpAmount;
	}
	/**
	 * 数量累计 
	 */
	public void setCommAddUpAmount(Double commAddUpAmount) {
		this.commAddUpAmount = commAddUpAmount;
	}
	/**
	 * 手册号码
	 */
	public String getEmsHeadH2k() {
		return emsHeadH2k;
	}
	/**
	 * 手册号码
	 */
	public void setEmsHeadH2k(String emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}
	
	/**
	 * 报关单流水号
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}
	/**
	 * 报关单流水号
	 */
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * 报关单号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}
	/**
	 * 报关单号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}
	/**
	 * 是否生效
	 */
	public Boolean getEffective() {
		return effective;
	}
	/**
	 * 是否生效
	 */
	public void setEffective(Boolean effective) {
		this.effective = effective;
	}
	/**
	 *备注
	 */
	public String getDetailNote() {
		return detailNote;
	}
	/**
	 * 备注
	 */
	public void setDetailNote(String detailNote) {
		this.detailNote = detailNote;
	}
	/**
	 * 报关日期
	 */
	public Date getDeclarationDate() {
		return declarationDate;
	}
	/**
	 * 报关日期
	 */
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
	public Trade getTradeMode() {
		return tradeMode;
	}
	public void setTradeMode(Trade tradeMode) {
		this.tradeMode = tradeMode;
	}
	public int compareTo(Object obj) {
		TempImpExpCommodityInfo info = (TempImpExpCommodityInfo)obj;
		if(info.getMateriel().getPtNo()==null){
			return -1;
		}
		if(this.getMateriel().getPtNo().compareTo(info.getMateriel().getPtNo())>0){
			return 1;
		}else if(this.getMateriel().getPtNo().compareTo(info.getMateriel().getPtNo())<=0){
			return -1;
		}else{
			return 0;
		}
	}
}
