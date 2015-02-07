package com.bestway.bcus.enc.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;

public class TempExgUsedImgAmount implements java.io.Serializable {
	private Integer exgSeqNum;

	private String exgComplex;

	private String exgName;

	private String exgSpec;

	private String exgUnit;

	private Integer version;

	private Double exgAmount;

	private Integer imgSeqNum;

	private String imgComplex;

	private String imgName;

	private String imgSpec;

	private String imgUnit;

	private Double unitUsed;

	private Double waste;

	private Double totalUsed;
	
	private Double unitPrice;
	
	private Double totalMoney;
	
	private Double wasteNew;
	
	private Double unitWearNew;

	public Double getExgAmount() {
		return exgAmount;
	}

	public void setExgAmount(Double exgAmount) {
		this.exgAmount = exgAmount;
	}

	public String getExgComplex() {
		return exgComplex;
	}

	public void setExgComplex(String exgComplex) {
		this.exgComplex = exgComplex;
	}

	public String getExgName() {
		return exgName;
	}

	public void setExgName(String exgName) {
		this.exgName = exgName;
	}

	public Integer getExgSeqNum() {
		return exgSeqNum;
	}

	public void setExgSeqNum(Integer exgSeqNum) {
		this.exgSeqNum = exgSeqNum;
	}

	public String getExgSpec() {
		return exgSpec;
	}

	public void setExgSpec(String exgSpec) {
		this.exgSpec = exgSpec;
	}

	public String getExgUnit() {
		return exgUnit;
	}

	public void setExgUnit(String exgUnit) {
		this.exgUnit = exgUnit;
	}

	public String getImgComplex() {
		return imgComplex;
	}

	public void setImgComplex(String imgComplex) {
		this.imgComplex = imgComplex;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Integer getImgSeqNum() {
		return imgSeqNum;
	}

	public void setImgSeqNum(Integer imgSeqNum) {
		this.imgSeqNum = imgSeqNum;
	}

	public String getImgSpec() {
		return imgSpec;
	}

	public void setImgSpec(String imgSpec) {
		this.imgSpec = imgSpec;
	}

	public String getImgUnit() {
		return imgUnit;
	}

	public void setImgUnit(String imgUnit) {
		this.imgUnit = imgUnit;
	}

	public Double getTotalUsed() {
		return totalUsed;
	}

	public void setTotalUsed(Double totalUsed) {
		this.totalUsed = totalUsed;
	}

	public Double getUnitUsed() {
		return unitUsed;
	}

	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}

	public Double getWaste() {
		return waste;
	}

	public void setWaste(Double waste) {
		this.waste = waste;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getExgNameSpec() {
		return (this.exgName == null ? "" : this.exgName) + "/"
				+ (this.exgSpec == null ? "" : this.exgSpec);
	}

	public String getImgNameSpec() {
		return (this.imgName == null ? "" : this.imgName) + "/"
				+ (this.imgSpec == null ? "" : this.imgSpec);
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getUnitWearNew() {
		return unitWearNew;
	}

	public void setUnitWearNew(Double unitWearNew) {
		this.unitWearNew = unitWearNew;
	}

	public Double getWasteNew() {
		return wasteNew;
	}

	public void setWasteNew(Double wasteNew) {
		this.wasteNew = wasteNew;
	}
}
