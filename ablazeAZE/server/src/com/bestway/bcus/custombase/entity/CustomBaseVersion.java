/*
 * Created on 2004-6-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity;

import java.io.Serializable;


/**
 * 存放海关基础资料里各种表的名称和更新时间
 */
public class CustomBaseVersion implements Serializable{
	
	/**
	 * 海关基础表的名称
	 */
	private String tableName;
	
	/**
	 * 更新时间
	 */
	private String version;	
	
	/**
	 * 获取海关基础表的名称
	 * 
	 * @return tableName 海关基础表的名称
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * 设置海关基础表的名称
	 * 
	 * @param tableName 海关基础表的名称
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * 获取更新时间
	 * 
	 * @return version 更新时间
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * 设置更新时间
	 * 
	 * @param version 更新时间
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
