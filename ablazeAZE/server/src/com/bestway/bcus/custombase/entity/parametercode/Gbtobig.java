/*
 * Created on 2005-3-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.parametercode;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放基础代码－－繁简体对照表资料
 */
public class Gbtobig extends BaseEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 简体
	 */
	private String name;
	/**
	 * 繁体
	 */
	private String 	bigname; 
	
	public Gbtobig() {
		super();
	}

	/**
	 * 获取繁体
	 * 
	 * @return bigname 繁体
	 */
	public String getBigname() {
		return bigname;
	}
	
	/**
	 * 设置繁体
	 * 
	 * @param bigname 繁体
	 */
	public void setBigname(String bigname) {
		this.bigname = bigname;
	}
	
	/**
	 * 获取简体
	 * 
	 * @return name 简体
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置简体
	 * 
	 * @param name 简体
	 */
	public void setName(String name) {
		this.name = name;
	}
}
