/*
 * Created on 2004-6-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import com.bestway.common.BaseScmEntity;


/**
 * 载货清单成品列表
 * @author 001
 *table="makelistexglist"
 */
public class MakeListExgList extends BaseScmEntity{
	
	/**
	 * 货物名称
	 */
	private String name;
	
	/**
	 * 取得货物名称
	 * @return 货物名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置货物名称
	 * @param name 货物名称
	 */
	public void setName(String name) {
		this.name = name;
	}
}
