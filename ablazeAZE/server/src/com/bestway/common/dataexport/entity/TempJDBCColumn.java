/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempJDBCColumn implements Serializable, Comparable {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	private String				fieldName			= null;						// 目的表字段名称
	private Integer				fieldType			= null;						// 目的表字段类型
	private String				fieldTypeDesc		= null;						// 目的表字段
	private Boolean				isPrimaryKey		= false;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldTypeDesc() {
		return fieldTypeDesc;
	}

	public void setFieldTypeDesc(String fieldTypeDesc) {
		this.fieldTypeDesc = fieldTypeDesc;
	}

	public int compareTo(Object o) {
		TempJDBCColumn a = (TempJDBCColumn) o;
		if (a == null) {
			return 1;
		}
		String tempName = fieldName.toLowerCase();
		//
		// ASC
		//
		return tempName.compareTo(a.getFieldName().toLowerCase());
		// return 0;
	}

	public Boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

}
