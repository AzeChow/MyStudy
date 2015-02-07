package com.bestway.common.tools.entity;

import java.io.Serializable;

/**
 * 服务器插件初始化参数名
 * 
 * @author Administrator
 * 
 */
public class PluginParameter implements Serializable {
	private static final long	serialVersionUID	= 1L;
	/**
	 *  参数关键字
	 */
	private String				key					= null;
	/**
	 *  参数值
	 */
	private String				value				= null;
	/**
	 *  参数中文名
	 */
	private String				cnName				= null;

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
