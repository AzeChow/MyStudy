/*
 * Created on 2004-6-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

import com.bestway.common.authority.entity.BaseCompany;

/**
 * 
 * 物流基本实体类，
 * 里面主要包含有子公司的资料。将来抓取物流资料时以公司来区分。
 * 
 */
public class BaseScmEntity extends BaseEntity {
	/**
	 * 公司对象
	 */
	private BaseCompany company;

	/**
	 * @return Returns the company.
	 */
	public BaseCompany getCompany() {
		return company;
	}
   
	/**
	 * @param company
	 *            The company to set.
	 */
	public void setCompany(BaseCompany company) {
		this.company = company;
	}
}