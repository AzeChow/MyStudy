package com.bestway.common.materialbase.entity;

import java.io.Serializable;

/**
 * 料件被使用的成品对应表
 * @author chensir
 *
 */
public class EmptyProductMaterial implements Serializable{

	/**
	 * 子级料号
	 */
	private String compentNo = null;

	/**
	 * 子级名称
	 */
	private String compentName = null;

	/**
	 * 子级规格
	 */
	private String compentSpec = null;
	
	/**
	 * 单位用量
	 */
	private Double unitDosage = null;

	/**
	 * 单耗
	 */
	private Double unitWare = null;

	/**
	 * 损耗
	 */
	private Double ware = null;
	
	/**
	 * 父件版本号
	 */
	private String versionNo = null;
	
	/**
	 * 料别
	 */
	private String scmCoiName;
	
	/**
	 * 父料号码
	 */
	private String ptNo;
	
	/**
	 * 父料名称
	 */
	private String ptName;
	
	/**
	 * 父料规格
	 */
	private String ptSpec;
	
	/**
	 * 工厂单位
	 */
	private String calUnitName;
	
	/**
	 * 对应的BOMManager id号
	 * wss2010.04.13新加
	 */
	private String BomManagerId;
	

	public String getCompentNo() {
		return compentNo;
	}

	public void setCompentNo(String compentNo) {
		this.compentNo = compentNo;
	}

	public String getCompentName() {
		return compentName;
	}

	public void setCompentName(String compentName) {
		this.compentName = compentName;
	}

	public String getCompentSpec() {
		return compentSpec;
	}

	public void setCompentSpec(String compentSpec) {
		this.compentSpec = compentSpec;
	}

	public Double getUnitDosage() {
		return unitDosage;
	}

	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}

	public Double getUnitWare() {
		return unitWare;
	}

	public void setUnitWare(Double unitWare) {
		this.unitWare = unitWare;
	}

	public Double getWare() {
		return ware;
	}

	public void setWare(Double ware) {
		this.ware = ware;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getScmCoiName() {
		return scmCoiName;
	}

	public void setScmCoiName(String scmCoiName) {
		this.scmCoiName = scmCoiName;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public String getCalUnitName() {
		return calUnitName;
	}

	public void setCalUnitName(String calUnitName) {
		this.calUnitName = calUnitName;
	}

	public String getBomManagerId() {
		return BomManagerId;
	}

	public void setBomManagerId(String managerId) {
		BomManagerId = managerId;
	}
	
	
	
	
	
}
