/*
 * Created on 2004-6-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.bestway.common.CommonUtils;

/**
 * 海关基础表的父类
 */
public class CustomBaseEntity implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 是否作废
	 */
	private String isOut;

	/**
	 * 获取编码
	 * 
	 * @return code 编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置编码
	 * 
	 * @param dmodeCode 编码
	 */
	public void setCode(String dmodeCode) {
		this.code = dmodeCode;
	}

	/**
	 * 获取名称
	 * 
	 * @return dmodeName 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param dmodeName 名称
	 */
	public void setName(String dmodeName) {
		this.name = dmodeName;
	}

	/**
	 * 覆盖equals方法，当code相同时为相等
	 */
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other.getClass().equals(this.getClass())))
			return false;
		CustomBaseEntity castOther = (CustomBaseEntity) other;
		return new EqualsBuilder().append(this.getCode(), castOther.getCode())
				.isEquals();
	}

	/**
	 * 获取是否作废
	 * 
	 * @return isOut 是否作废
	 */
	public String getIsOut() {
		return isOut;
	}

	/**
	 * 设置是否作废
	 * 
	 * @param isOut 是否作废
	 */
	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}
	
	
	public static String getName(CustomBaseEntity e) {
		if(e == null) {
			return null;
		} else {
			return e.name;
		}
	}
	
	public static String getCode(CustomBaseEntity e) {
		if(e == null) {
			return null;
		} else {
			return e.code;
		}
	}
}