package com.bestway.dzsc.contractanalyse.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

public class TempImpPORImgStatInfo implements Serializable {
	private Integer seqNum;

	private Complex complex;

	private String commName;

	private String commSpec;

	private Unit unit;
	
	private Double unitPrice;

	private Double imgAmount;

	private Double exgUsedAmount;

	private Double exceedAmount;

	private Double totalImpAmount;

	private Double imgBackExport;

	private Double canUseAmount;

	public Double getCanUseAmount() {
		return canUseAmount;
	}

	public void setCanUseAmount(Double canUseAmount) {
		this.canUseAmount = canUseAmount;
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

	public Double getExceedAmount() {
		return exceedAmount;
	}

	public void setExceedAmount(Double exceedAmount) {
		this.exceedAmount = exceedAmount;
	}

	public Double getExgUsedAmount() {
		return exgUsedAmount;
	}

	public void setExgUsedAmount(Double exgUsedAmount) {
		this.exgUsedAmount = exgUsedAmount;
	}

	public Double getImgAmount() {
		return imgAmount;
	}

	public void setImgAmount(Double imgAmount) {
		this.imgAmount = imgAmount;
	}

	public Double getImgBackExport() {
		return imgBackExport;
	}

	public void setImgBackExport(Double imgBackExport) {
		this.imgBackExport = imgBackExport;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Double getTotalImpAmount() {
		return totalImpAmount;
	}

	public void setTotalImpAmount(Double totalImpAmount) {
		this.totalImpAmount = totalImpAmount;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
