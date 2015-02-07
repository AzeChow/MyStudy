/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 字段列表
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SuperFieldList extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 关联表头
     */
    private SuperClassList superClassList;
	/**
	 * 序号
	 */
    private Integer seqNum;
    /**
     * 类字段
     */
    private String fieldname;
    /**
     * 字段备注
     */    
    private String name;
    /**
     * 字段类型
     */
    private String fieldtype;
    /**
     * 是否为空
     */
    private Boolean isNullValue;
    /**
     * 关联对象类名
     */
    private String childClassName;
    /**
     * 关联字段
     */
    private String childFieldName;
    /**
     * 是否标识唯一值
     */
    private Boolean isOnlyValue;
    
    
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public SuperClassList getSuperClassList() {
		return superClassList;
	}
	public void setSuperClassList(SuperClassList superClassList) {
		this.superClassList = superClassList;
	}
	public String getFieldtype() {
		return fieldtype;
	}
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}
	public Boolean getIsNullValue() {
		return isNullValue;
	}
	public void setIsNullValue(Boolean isNullValue) {
		this.isNullValue = isNullValue;
	}
	public String getChildClassName() {
		return childClassName;
	}
	public void setChildClassName(String childClassName) {
		this.childClassName = childClassName;
	}
	public String getChildFieldName() {
		return childFieldName;
	}
	public void setChildFieldName(String childFieldName) {
		this.childFieldName = childFieldName;
	}
	public Boolean getIsOnlyValue() {
		return isOnlyValue;
	}
	public void setIsOnlyValue(Boolean isOnlyValue) {
		this.isOnlyValue = isOnlyValue;
	}
}
