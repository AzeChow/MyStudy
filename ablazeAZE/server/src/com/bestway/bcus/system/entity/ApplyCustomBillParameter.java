/*
 * Created on 2004-7-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放系统参数设置－其他参数设置资料
 * 
 * @author 001
 */
public class ApplyCustomBillParameter extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();

	/**
	 *  常用币制
	 */
	private Curr				curr;

	/**
	 *  征免方式
	 */
	private LevyMode			levyMode;

	/**
	 *  贸易国别
	 */
	private Country				country;

	/**
	 *  用途代码
	 */
	private Uses				uses;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	

	public Uses getUses() {
		return uses;
	}

	public void setUses(Uses uses) {
		this.uses = uses;
	}

	public LevyMode getLevyMode() {
		return levyMode;
	}

	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

}
