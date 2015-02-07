/*
 * Created on 2004-6-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物流通用代码－－物料类别资料
 * 
 * @author Administrator
 */
public class ScmCoi extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 类别编码
	 */
	private String code;
	
	/**
	 * 类别名称
	 */
	private String name;
	
	/**
	 * 类别属性
	 */
	private String coiProperty;
	
	/**
	 * 获取类别编码
	 * 
	 * @return coiCode 类别编码
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 设置类别编码
	 * 
	 * @param coiCode 类别编码
	 */
	public void setCode(String coiCode) {
		this.code = coiCode;
	}
	
	
	/**
	 * 获取类别名称
	 * 
	 * @return coiName 类别名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置类别名称
	 * 
	 * @param coiName 类别名称
	 */
	public void setName(String coiName) {
		this.name = coiName;
	}
	
	/**
	 * 获取类别属性
	 * 
	 * @return coiProperty 类别属性
	 */
	public String getCoiProperty() {
		return coiProperty;
	}
	
	/**
	 * 设置类别属性
	 * 
	 * @param coiProperty 类别属性
	 */
	public void setCoiProperty(String coiProperty) {
		this.coiProperty = coiProperty;
	}
}
