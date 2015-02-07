/*
 * Created on 2004-6-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.owp.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放工厂BOM管理子件资料
 * 
 * @author Administrator
 */
public class OwpBomDetail extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 版本
	 */
	private OwpBomVersion version = null;

	/**
	 * 料号
	 */
	private String compentNo = null;
	/**
	 * 名称
	 */
	private String compentName = null;
	/**
	 * 规格
	 */
	private String compentSpec = null;

	/**
	 * 单项用量
	 */
	private Double unitUsed = null;

	/**
	 * 单耗
	 */
	private Double unitWare = null;

	/**
	 * 损耗
	 */
	private Double ware = null;
	/**
	 * 备注
	 */
	private String notes = null;

	/**
	 * 获取料号
	 * 
	 * @return compentNo 料号
	 */
	public String getCompentNo() {
		return compentNo;
	}

	/**
	 * 设置料号
	 * 
	 * @param compentNo
	 *            料号
	 */
	public void setCompentNo(String compentNo) {
		this.compentNo = compentNo;
	}


	/**
	 * 获取单项用量
	 * 
	 * @return unitDosage 单项用量
	 */
	public Double getUnitUsed() {
		return unitUsed;
	}

	/**
	 * 设置单项用量
	 * 
	 * @param unitDosage
	 *            单项用量
	 */
	public void setUnitUsed(Double unitDosage) {
		this.unitUsed = unitDosage;
	}

	/**
	 * 获取单耗
	 * 
	 * @return unitWare 单耗
	 */
	public Double getUnitWare() {
		return unitWare;
	}

	/**
	 * 设置单耗
	 * 
	 * @param unitWare
	 *            单耗
	 */
	public void setUnitWare(Double unitWare) {
		this.unitWare = unitWare;
	}

	/**
	 * 获取损耗
	 * 
	 * @return ware 损耗
	 */
	public Double getWare() {
		return ware;
	}

	/**
	 * 设置损耗
	 * 
	 * @param ware
	 *            损耗
	 */
	public void setWare(Double ware) {
		this.ware = ware;
	}

	/**
	 * 获取版本
	 * 
	 * @return versionNo 版本
	 */
	public OwpBomVersion getVersion() {
		return version;
	}

	/**
	 * 设置版本
	 * 
	 * @param versionNo
	 *            父件版本号
	 */
	public void setVersion(OwpBomVersion versionNo) {
			this.version = versionNo;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
	
	
}
