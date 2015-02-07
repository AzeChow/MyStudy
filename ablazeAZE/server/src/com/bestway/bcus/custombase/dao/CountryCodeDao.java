/*
 * Created on 2004-6-9
 * 
 * // Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.dao;

import java.util.List;

import com.bestway.common.BaseDao;

//import com.borland.primetime.ui.re;

public class CountryCodeDao extends BaseDao {

	/**
	 * 国家地区
	 */
	public List findCountry(String sFields, String sValue) {
		return findCustoms("Country", sFields, sValue);
	}
	
	/**
	 * 海关关区
	 */
	public List findCustoms(String sFields, String sValue) {
		return findCustoms("Customs", sFields, sValue);
	}

	/**
	 * 国内进出口口岸
	 */
	public List findPortInternal(String sFields, String sValue) {
		return findCustoms("PortInternal", sFields, sValue);
	}

	/**
	 * 国际进出口港口
	 */
	public List findPortLin(String sFields, String sValue) {
		return findCustoms("PortLin", sFields, sValue);
	}

	/**
	 * 国内进出口码头
	 */
	public List findPreDock(String sFields, String sValue) {
		return findCustoms("PreDock", sFields, sValue);

	}
   /**
    * 地区代码
    * */
	public List findDistrict(String sFields,String sValue){
		return findCustoms("District",sFields,sValue);
	}
	/**
	 * 报关行
	 */
	public List findCustomsBroker(String sFields,String sValue){
		return findCustoms("CustomsBroker",sFields,sValue);
	}
}

