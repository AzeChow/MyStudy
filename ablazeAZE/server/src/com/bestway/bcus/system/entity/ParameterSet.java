/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放系统参数设置资料
 * 
 * @author 001
 */
public class ParameterSet extends BaseScmEntity implements Cloneable{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 各个选项
	 */
	private int type;
	
	/**
	 * type对应的值
	 */
	private String nameValues;

	/**
	 * 获取type对应的值
	 * 
	 * @return nameValues type对应的值
	 */
	public String getNameValues() {
		return nameValues;
	}
	/**
	 * 设置type对应的值
	 * 
	 * @param nameValues  type对应的值
	 */
	public void setNameValues(String nameValues) {
		this.nameValues = nameValues;
	}
	/**
	 * 获取各个选项
	 * 
	 * @return type 各个选项
	 */
	public int getType() {
		return type;
	}
	/**
	 * 设置各个选项
	 * 
	 * @param type 各个选项
	 */
	public void setType(int type) {
		this.type = type;
	}
}
