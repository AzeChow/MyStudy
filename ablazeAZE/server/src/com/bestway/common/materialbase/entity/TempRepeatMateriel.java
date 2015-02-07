package com.bestway.common.materialbase.entity;

import java.io.Serializable;

/**
 * 临时实体类，存放临时重复的报关常用工厂物料资料
 * 
 * @author administrator
 *
 */
public class TempRepeatMateriel implements Serializable{
	
	/**
	 * 料号
	 */
	private String ptNo;
	
	/**
	 * 判断是否选中，true为选中
	 */
	private Boolean isSelected=false;
	
	/**
	 * 获取判断是否选中，true为选中
	 * 
	 * @return isSelected 判断是否选中，true为选中
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}
	
	/**
	 * 设置判断是否选中，true为选中
	 * 
	 * @param isSelected 判断是否选中，true为选中
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
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
}
