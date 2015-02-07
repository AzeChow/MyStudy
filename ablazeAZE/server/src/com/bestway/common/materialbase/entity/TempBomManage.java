package com.bestway.common.materialbase.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 临时实体类，存放临时工厂BOM管理子件
 * 
 * @author administrator
 */
public class TempBomManage implements Serializable{
	
	/**
	 * 料号
	 */
	private String ptNo;

    /**
     * 单耗
     */
    private Double unitWare; 
   
    /**
     * 损耗
     */
    private Double ware ;   
	
    /**
	 * 单项用量
	 */
	private Double unitDosage = null; 

    /**
     * 生效日期
     */
    private Date effectDate ; 
    
    /**
     * 失效日期
     */
    private Date endDate ;  
    
    /**
     * 备注
     */
    private String notes;
    /**
     * 主辅料标志
     */
    private Boolean isMainImg;
    

	/**
	 * 获取生效日期
	 * 
	 * @return effectDate 生效日期
	 */
	public Date getEffectDate() {
		return effectDate;
	}
	
	/**
	 * 获取生效日期
	 * 
	 * @param effectDate 生效日期
	 */
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	
	/**
	 * 获取失效日期
	 * 
	 * @return endDate 失效日期
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * 获取失效日期
	 * 
	 * @param endDate 失效日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	 * 获取料号
	 * 
	 * @param ptNo 料号
	 */
	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
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
	 * 获取单耗
	 * 
	 * @param unitWare 单耗
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
	 * 获取损耗
	 * 
	 * @param ware 损耗
	 */
	public void setWare(Double ware) {
		this.ware = ware;
	}
	
	
	/**
	 * 获取单项用量
	 * 
	 * @return unitDosage 单项用量
	 */
	public Double getUnitDosage() {
		return unitDosage;
	}
	
	/**
	 * 获取单项用量
	 * 
	 * @param unitDosage 单项用量
	 */
	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 获取EffectEndDate
	 * 
	 * @return String
	 */
	public String getEffectEndDate(){
		return (effectDate == null ? "[]"
				: DateFormatUtils.format(effectDate, "yyyy-MM-dd"))
				+ (endDate == null ? "[]" : DateFormatUtils.format(
						endDate, "yyyy-MM-dd"));
	}

	public Boolean getIsMainImg() {
		return isMainImg;
	}

	public void setIsMainImg(Boolean isMainImg) {
		this.isMainImg = isMainImg;
	}
}
