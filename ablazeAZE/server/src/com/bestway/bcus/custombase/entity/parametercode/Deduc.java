/*
 * Created on 2004-6-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.entity.parametercode;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.common.CommonUtils;

/**
 *核扣方式资料
 */
public class Deduc extends CustomBaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 贸易方式代码
	 */
	private String tradeCode; 

	/**
	 * 核扣方法
	 */
	private String deducMark; 

	/**
	 * 进出口类别
	 */
	private String inOutType; 

	/**
	 * 核扣方式说明
	 */
	private String deducNote; 

	/**
	 * 获取核扣方法
	 * 
	 * @return deducMark 核扣方法
	 */
	public String getDeducMark() {
		return deducMark;
	}

	/**
	 * 设置核扣方法
	 * 
	 * @param deducMark 核扣方法
	 */
	public void setDeducMark(String deducMark) {
		this.deducMark = deducMark;
	}

	/**
	 * 获取核扣方式说明
	 * 
	 * @return deducNote 核扣方式说明
	 */
	public String getDeducNote() {
		return deducNote;
	}

	/**
	 * 设置核扣方式说明
	 * 
	 * @param deducNote 核扣方式说明
	 */
	public void setDeducNote(String deducNote) {
		this.deducNote = deducNote;
	}

	/**
	 * 获取进出口类别
	 * 
	 * @return inOutType 进出口类别
	 */
	public String getInOutType() {
		return inOutType;
	}

	/**
	 * 设置进出口类别
	 * 
	 * @param inOutType 进出口类别
	 */
	public void setInOutType(String inOutType) {
		this.inOutType = inOutType;
	}

	/**
	 * 获取贸易方式代码
	 * 
	 * @return tradeCode 贸易方式代码
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * 设置贸易方式代码
	 * 
	 * @param tradeCode 贸易方式代码
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
}