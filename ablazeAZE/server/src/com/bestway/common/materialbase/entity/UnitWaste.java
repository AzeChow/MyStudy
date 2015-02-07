/*
 * Created on 2004-6-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放单损耗资料
 * 
 * @author Administrator
 */
public class UnitWaste extends BaseScmEntity { 
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 对应成品号
	 */
	private String exgNo;
	
	/**
	 * 料号
	 */
	private String ptNo;          
	
	/**
	 * 单耗
	 */
	private Double unitWaste;    
	
	/**
	 * 损耗率
	 */
	private Double waste;
	
	/**
	 * 是否主料
	 */
	private Boolean isMainMateriel=false; 
	
	/**
	 * 材料来源
	 */
	private String meterSource;   
	
	/**
	 * 单位用量
	 */
	private Double unitDosage;    
	
	/**
	 * 获取对应成品号
	 * 
	 * @return exgNo 对应成品号
	 */
	public String getExgNo() {
		return exgNo;
	}
	
	/**
	 * 设置对应成品号
	 * 
	 * @param exgNo 对应成品号
	 */
	public void setExgNo(String exgNo) {
		this.exgNo = exgNo;
	}
	
	/**
	 * 获取是否主料
	 * 
	 * @return isMainMateriel 是否主料
	 */
	public Boolean getIsMainMateriel() {
		return isMainMateriel;
	}
	
	/**
	 * 设置是否主料
	 * 
	 * @param isMainMateriel 是否主料
	 */
	public void setIsMainMateriel(Boolean isMainMateriel) {
		this.isMainMateriel = isMainMateriel;
	}
	
	/**
	 * 获取材料来源
	 * 
	 * @return meterSource 材料来源
	 */
	public String getMeterSource() {
		return meterSource;
	}
	
	/**
	 * 设置材料来源
	 * 
	 * @param meterSource 材料来源
	 */
	public void setMeterSource(String meterSource) {
		this.meterSource = meterSource;
	}
	
	/**
	 * 获取料号
	 * 
	 * @return ptNo 料号
	 */
	public String getPtNo() {
		return ptNo;
	}
	
	/**
	 * 设置料号
	 * 
	 * @param ptNo 料号
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
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
	 * 获取损耗率
	 * 
	 * @return waste 损耗率
	 */
	public Double getWaste() {
		return waste;
	}
	
	/**
	 * 设置损耗率
	 * 
	 * @param waste 损耗率
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}
	
	/**
	 * 获取单位用量
	 * 
	 * @return unitDosage 单位用量
	 */
	public Double getUnitDosage() {
		return unitDosage;
	}
	
	/**
	 * 设置单位用量
	 * 
	 * @param unitDosage 单位用量
	 */
	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}
}
