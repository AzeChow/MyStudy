/*
 * Created on 2004-6-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物流通用代码－－税制代码资料
 * 
 * @author Administrator
 */
public class ShareCode extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 标识符
	 */
	private String tag; 
	
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
	 * @param code 编码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	
	/**
	 * 获取名称
	 * 
	 * @return name 名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置名称
	 * 
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取标识符（例：税制代码）
	 * 
	 * @return tag 标识符（例：税制代码）
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * 设置标识符（例：税制代码）
	 * 
	 * @param tag 标识符（例：税制代码）
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
}
