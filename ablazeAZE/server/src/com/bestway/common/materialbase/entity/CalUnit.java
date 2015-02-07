/*
 * Created on 2004-6-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物料通用代码－－计量单位资料
 * 
 * @author Administrator
 */
public class CalUnit extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 计量单位代码
	 */
	private String code;  
	
	/**
	 * 计量单位名称
	 */
	private String name; 
	
	/**
	 * 海关单位
	 */
	private Unit unit;  
	
	/**
	 * 单位对照比例因子
	 */
	private Double scale;


	
	/**
	 * 获取计量单位代码
	 * 
	 * @return code 计量单位代码
	 */
	public String getCode() {
		return code;
	}
		
	/**
	 * 设置计量单位代码
	 * 
	 * @param code 计量单位代码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 获取计量单位名称
	 * 
	 * @return name 计量单位名称
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * 设置计量单位名称
	 * 
	 * @param name 计量单位名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取海关单位
	 * 
	 * @return unit 海关单位
	 */
	public Unit getUnit() {
		return unit;
	}
		
	/**
	 * 设置海关单位
	 * 
	 * @param unit 海关单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/**
	 * 获取单位对照比例因子
	 * 
	 * @return scale 单位对照比例因子
	 */
	public Double getScale() {
		return scale;
	}
	
	/**
	 * 设置单位对照比例因子
	 * 
	 * @param scale 单位对照比例因子
	 */
	public void setScale(Double scale) {
		this.scale = scale;
	}

}
