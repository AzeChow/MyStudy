/*
 * Created on 2004-6-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物料通用代码－－计量单位资料
 * 
 * @author Administrator
 */
public class UnitCollate extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 单位
	 */
	private String unitName; 
	/**
	 * 单位1
	 */
	private String unitName1;
	/**
	 * 比例因子
	 */
	private Double unitRate;  
	// unitRate = unitName1 / unitName 
	// unitName:个 unitName1:千个  unitRate: 0.001
	
	
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitName1() {
		return unitName1;
	}
	public void setUnitName1(String unitName1) {
		this.unitName1 = unitName1;
	}
	public Double getUnitRate() {
		return unitRate;
	}
	public void setUnitRate(Double unitRate) {
		this.unitRate = unitRate;
	}
	
	
	
	

}
