/*
 * Created on 2004-7-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放系统参数设置－公共检查选项
 * 
 * @author 001
 */
public class CommonCheckNull extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 类名
	 */
	private String className;
	
	/**
	 * 类备注
	 */
	private String classMemo;
	
	/**
	 * 字段名
	 */
	private String fieldName;
	
	/**
	 * 字段备注
	 */
	private String fieldMemo;
	
	/**
	 * 字段类型
	 */
	private String fieldType;
	
	
	public String getClassMemo() {
		return classMemo;
	}
	public void setClassMemo(String classMemo) {
		this.classMemo = classMemo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getFieldMemo() {
		return fieldMemo;
	}
	public void setFieldMemo(String fieldMemo) {
		this.fieldMemo = fieldMemo;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	
}
