/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 给核销中的单耗表头继承
 */
public class CancelUnitWear extends BaseScmEntity implements Cloneable{
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
		
    /**
	 * 核销表头
	 */
	private CancelHead cancelHead;
	/**
	 * 成品序号
	 */
	private Integer cpSeqNum;
	
	/**
	 * 版本号
	 */
	private Integer version;
	
	/**
	 * 料件序号
	 */
	private Integer ljSeqNum;
	
	/**
	 * 单耗
	 */
	private Double unitWear;
	
	/**
	 * 损耗
	 */
	private Double wear;
	
	/**
	 * 耗用情况
	 */
	private Double unitUse;
	
	/**
	 * 成品耗用情况
	 */
	private Double cpUse;
	
	/**
	 * 料件耗用情况
	 */
	private Double ljUse;
	
	
	
	public CancelHead getCancelHead() {
		return cancelHead;
	}
	public void setCancelHead(CancelHead cancelHead) {
		this.cancelHead = cancelHead;
	}
	public Integer getCpSeqNum() {
		return cpSeqNum;
	}
	public void setCpSeqNum(Integer cpSeqNum) {
		this.cpSeqNum = cpSeqNum;
	}
	public Integer getLjSeqNum() {
		return ljSeqNum;
	}
	public void setLjSeqNum(Integer ljSeqNum) {
		this.ljSeqNum = ljSeqNum;
	}
	public Double getLjUse() {
		return ljUse;
	}
	public void setLjUse(Double ljUse) {
		this.ljUse = ljUse;
	}
	public Double getUnitUse() {
		return unitUse;
	}
	public void setUnitUse(Double unitUse) {
		this.unitUse = unitUse;
	}
	public Double getUnitWear() {
		return unitWear;
	}
	public void setUnitWear(Double unitWear) {
		this.unitWear = unitWear;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Double getWear() {
		return wear;
	}
	public void setWear(Double wear) {
		this.wear = wear;
	}
	public Double getCpUse() {
		return cpUse;
	}
	public void setCpUse(Double cpUse) {
		this.cpUse = cpUse;
	}
	
		
}