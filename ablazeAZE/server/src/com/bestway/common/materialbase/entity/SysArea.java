/*
 * Created on 2004-6-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物流通用代码－－国家代码资料
 * 
 * @author Administrator
 */
public class SysArea extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 国家区域编码
	 */
	private String code;
	
	/**
	 * 国家区域名称
	 */
	private String name;
	
	/**
	 * 关务国家名称
	 */
	private Country country;
	
	/**
	 * 获取国家区域编码
	 * 
	 * @return code 国家区域编码
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 设置国家区域编码
	 * 
	 * @param code 国家区域编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	
	/**
	 * 获取国家区域名称
	 * 
	 * @return name 国家区域名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置国家区域名称
	 * 
	 * @param name 国家区域名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * 获取关务国家名称
	 * 
	 * @return country 关务国家名称
	 */
	public Country getCountry() {
		return country;
	}
	
	/**
	 * 设置关务国家名称
	 * 
	 * @param country 关务国家名称
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

}
