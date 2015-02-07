/*
 * Created on 2004-6-9
 * 
 * // Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.dao;

import java.util.List;

import com.bestway.common.BaseDao;

public class DepCodeDao extends BaseDao
{

	/**
	 * 税务代码
	 */
	public List findTaxCode(String sFields, String sValue)
	{
		return this.findCustoms("TaxCode", sFields, sValue);
	}



	/**
	 * 工商行政
	 */
	public List findSaicCode(String sFields, String sValue)
	{
		return this.findCustoms("SaicCode", sFields, sValue);
	}



	/**
	 * 技术监督
	 */
	public List findStsCode(String sFields, String sValue)
	{
		return this.findCustoms("StsCode", sFields, sValue);
	}



	/**
	 * 外经贸部门
	 */
	public List findRedDep(String sFields, String sValue)
	{
		return this.findCustoms("RedDep", sFields, sValue);
	}
}

