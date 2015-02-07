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
 * 存放物料通用代码－－报关员设置
 * 
 * @author Administrator
 */
public class CustomsUser extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 报关员证号
	 */
	private String code;
	
	/**
	 * 报关员名称
	 */
	private String name; 
	
	/**
	 * 报关员电话
	 */
	private String tel;  



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取报关员名称
	 * 
	 * @return name 报关员名称
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置报关员名称
	 * 
	 * @param name 报关员名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取报关员电话
	 * 
	 * @return tel 报关员电话
	 */
	public String getTel() {
		return tel;
	}
	
	/**
	 * 设置报关员电话
	 * 
	 * @param tel 报关员电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
}
