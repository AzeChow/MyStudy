/*
 * Created on 2004-6-25
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.authority.entity;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 公司基础资料
 * @author 001
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BaseCompany extends BaseEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 加工单位名称
	 */
	private String name; 
	/**
	 * 加工单位名称 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isValid(){
		if(this.name == null || name.equals("")) return false;
		return true;
	}
}