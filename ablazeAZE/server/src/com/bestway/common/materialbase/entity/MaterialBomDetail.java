/*
 * Created on 2004-10-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放报关常用工厂BOM料件资料
 * 
 * @author administrator
 */
public class MaterialBomDetail extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 成品版本
	 */
	private MaterialBomVersion version; 
	
	/**
	 * 物料
	 */
	private Materiel materiel; 
	
	/**
	 * 单耗
	 */
	private Double unitWaste;  
	
	/**
	 * 损耗
	 */
	private Double waste;  
	
	/**
	 * 单项用量
	 */
	private Double unitUsed;
	
	/**
	 * 备注
	 */
	private String note; 
	
//	/**
//	 * 料号
//	 */
//	private String ptNo;
//	
//	/**
//	 * 版本号
//	 */
//	private Integer versionNo;
	
	
	/**
	 * 获取物料
	 * 
	 * @return materiel 物料
	 */
	public Materiel getMateriel() {
		return materiel;
	}
	
	/**
	 * 设置物料
	 * 
	 * @param materiel 物料
	 */
	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}
		
	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}
		
	/**
	 * 设置备注
	 * 
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
		
	/**
	 * 获取单耗
	 * 
	 * @return unitWaste 单耗
	 */
	public Double getUnitWaste() {
		return unitWaste;
	}
		
	/**
	 * 设置单耗
	 * 
	 * @param unitWaste 单耗
	 */
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

		
	/**
	 * 获取损耗
	 * 
	 * @return waste 损耗
	 */
	public Double getWaste() {
		return waste;
	}
		
	/**
	 * 设置损耗
	 * 
	 * @param waste 损耗
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}

		
	/**
	 * 获取成品版本
	 * 
	 * @return version 成品版本
	 */
	public MaterialBomVersion getVersion() {
		return version;
	}
		
	/**
	 * 设置成品版本
	 * 
	 * @param version 成品版本
	 */
	public void setVersion(MaterialBomVersion version) {
		this.version = version;
	}
		
	/**
	 * 获取单项用量
	 * 
	 * @return unitUsed 单项用量
	 */
	public Double getUnitUsed() {
		return unitUsed;
	}
		
	/**
	 * 设置单项用量
	 * 
	 * @param unitUsed 单项用量
	 */
	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}
		
//	/**
//	 * 获取料号
//	 * 
//	 * @return ptNo 料号
//	 */
//	public String getPtNo() {
//		return ptNo;
//	}
//		
//	/**
//	 * 设置料号
//	 * 
//	 * @param ptNo 料号
//	 */
//	public void setPtNo(String ptNo) {
//		this.ptNo = ptNo;
//	}
//		
//	/**
//	 * 获取版本号
//	 * 
//	 * @return versionNo 版本号
//	 */
//	public Integer getVersionNo() {
//		return versionNo;
//	}
//		
//	/**
//	 * 设置版本号
//	 * 
//	 * @param versionNo 版本号
//	 */
//	public void setVersionNo(Integer versionNo) {
//		this.versionNo = versionNo;
//	}
}
