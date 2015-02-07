/*
 * Created on 2004-6-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物流通用代码－－参数对应设置资料
 * 
 * @author Administrator
 */
public class ParaSet extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 栏位
	 */
	private int type;
	
	/**
	 * 当前值
	 */
	private String beginValue;  
	
	/**
	 * 对应后值
	 */
	private String afterValue; 

	/**
	 * 获取对应后值
	 * 
	 * @return afterValue 对应后值
	 */
	public String getAfterValue() {
		return afterValue;
	}

	/**
	 * 设置对应后值
	 * 
	 * @param afterValue 对应后值
	 */
	public void setAfterValue(String afterValue) {
		this.afterValue = afterValue;
	}

	/**
	 * 获取当前值
	 * 
	 * @return beginValue 当前值
	 */
	public String getBeginValue() {
		return beginValue;
	}

	/**
	 * 设置当前值
	 * 
	 * @param beginValue 当前值
	 */
	public void setBeginValue(String beginValue) {
		this.beginValue = beginValue;
	}

	/**
	 * 获取栏位
	 * 
	 * @return type 栏位
	 */
	public int getType() {
		return type;
	}

	/**
	 * 设置栏位
	 * 
	 * @param type 栏位
	 */
	public void setType(int type) {
		this.type = type;
	}

}
