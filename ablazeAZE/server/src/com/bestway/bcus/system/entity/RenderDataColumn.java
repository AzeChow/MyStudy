/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.entity;

import java.util.HashMap;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 控制数据对象列的显示情况
 * 
 */
public class RenderDataColumn extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();

	/**
	 * 简单的类型名
	 */
	private String				className			= null;
	/**
	 * 标识是那个报表的关键字
	 */
	private String				key					= null;
	/**
	 * 前端JTable中 显示的列名
	 */
	private String				name				= null;	
	/**
	 * 是否在前端JTable中显示
	 */
	private Boolean				isShow				= null;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {		
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
