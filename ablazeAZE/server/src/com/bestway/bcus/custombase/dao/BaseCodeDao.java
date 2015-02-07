/*
 * Created on 2004-6-9
 * 
 * // Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.warning.entity.WarningItemAnnotation;

@SuppressWarnings("unchecked")
public class BaseCodeDao extends BaseDao
{

	public List findGbtobig(String sFields, String sValue)
	{
		return find("Gbtobig", sFields, sValue);
	}

	/**
	 * 投资方式
	 */
	public List findInvestMode(String sFields, String sValue)
	{
		return findCustoms("InvestMode", sFields, sValue);
	}

	/**
	 * 企业性质
	 */
	public List findCoType(String sFields, String sValue)
	{
		return findCustoms("CoType", sFields, sValue);
	}

	/**
	 * 投资类型
	 */
	public List findInvClass(String sFields, String sValue)
	{
		return findCustoms("InvClass", sFields, sValue);
	}

	/**
	 * 加工种类
	 */
	public List findMachiningType(String sFields, String sValue)
	{
		return findCustoms("MachiningType", sFields, sValue);

	}

	/**
	 * 申报/报关方式
	 */
	public List getDMode(String sFields, String sValue)
	{
		return findCustoms("ApplicationMode", sFields, sValue);

	}

	/**
	 * 海关注册公司
	 */
	public List getBrief(String sFields, String sValue)
	{
		String hsql = null;
		List list = null;
			hsql = "select a from Brief  " 
					+ " a where (a.isOut <> '1' or a.isOut is null) and a."
					+ sFields + " = ? ";
			list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue });
		return list;
//		return findCustoms("Brief", sFields, sValue);
	}

	/**
	 * 海关注册公司分页
	 */
	public List<Object> findPageBrief(int index, int length, String property,
			Object value, boolean isLike)
	{
		String hsql = null;
		List<Object> parameters = new ArrayList<Object>();
		hsql = "select a from Brief a where (a.isOut <> '1' or a.isOut is null) ";
		
		if ((value != null) && (property != null))
		{
			System.out.println(">>>>>>"+value+"<<<<<<"+property);
			hsql += " and a." +(isLike == true ? property + " Like ? " : property + " = ? ");
			parameters.add(isLike == true ? ("%" + value.toString() + "%" ) :  value.toString() );
		}
		
		List list = super.findPageList(hsql, (value == null || property == null) == true ? null : parameters.toArray(), index, length);

		logger.info("海关注册公司分页>>>>>>"
				+ (property == null ? "property null " : property) + ">>>>>>"
				+ (value == null ? "value null " : value.toString())+" \nHQL:>>>>>>"+hsql);
		return list;
	}

	/**
	 * 获得海关注册公司总条数
	 */
	public Long findPageBriefCount(String property, Object value, boolean isLike)
	{
		List list = null;
		long count = 0L;
		String hsql = "select count(*) from Brief a where (a.isOut <> '1' or a.isOut is null)";
		
		if (property != null && value != null )
		{
			hsql += " and a."+ property + " like ? ";
			list = this.getHibernateTemplate().find(hsql,
					new Object[] { "%" + value + "%" });
		}
		else
		{
			list = this.getHibernateTemplate().find(hsql);
		}
		
		Iterator<Object> it = list.iterator();
		while (it.hasNext())
		{
			Object o = it.next();
			count = Long.parseLong(o.toString());
		}
		
		logger.info("海关注册公司总条数>>>>>>"
				+ (property == null ? "property null values " : property) + ">>>>>>"
				+ (value == null ? "value null values " : value.toString())+"  >>>>>>count:" + count);
		return count;
	}

	public ScmCoc findScmCocByName(String name, Boolean isCustomer,
			Boolean isManufacturer)
	{
		ScmCoc data = null;
		List properies = new ArrayList();
		String hsql = " select a from  ScmCoc  a  "
				+ "where a.company.id=? and a.name=? ";
		properies.add(CommonUtils.getCompany().getId());
		properies.add(name);
		if (isCustomer != null && isCustomer)
		{
			hsql += "and a.isCustomer=? ";
			properies.add(isCustomer);
		}
		if (isManufacturer != null && isManufacturer)
		{
			hsql += "and a.isManufacturer=? ";
			properies.add(isManufacturer);
		}
		List list = this.find(hsql, properies.toArray());
		if (list.size() > 0)
		{
			data = (ScmCoc) list.get(0);
		}
		return data;
	}

	public List findScmCoc(Boolean isCustomer, Boolean isManufacturer)
	{
		ScmCoc data = null;
		List properies = new ArrayList();
		String hsql = " select a from  ScmCoc  a  " + "where a.company.id=? ";
		properies.add(CommonUtils.getCompany().getId());
		if (isCustomer != null && isCustomer)
		{
			hsql += "and a.isCustomer=? ";
			properies.add(isCustomer);
		}
		if (isManufacturer != null && isManufacturer)
		{
			hsql += "and a.isManufacturer=? ";
			properies.add(isManufacturer);
		}

		return this.find(hsql, properies.toArray());
	}

	public List findBytesLength()
	{
		String hsql = "select a.bytesLength from BcsParameterSet a";
		return this.find(hsql);
	}
	
	public List findIsControlLength()
	{
		String hsql = "select a.isControlLength from BcsParameterSet a";
		return this.find(hsql);
	}
	
	public Country findCountryByName(String name)
	{
		Country data = null;
		List list = this.find(" select a from Country a  where a.name=? ",
				new Object[] { name });
		if (list.size() > 0)
		{
			data = (Country) list.get(0);
		}
		return data;
	}

	public List findCountry()
	{
		List list = this.find(" select a from Country a   ");
		return list;
	}

	public Country findCountryByCode(String code)
	{
		Country data = null;
		List list = this.find(" select a from Country a  where a.code=? ",
				new Object[] { code });
		if (list.size() > 0)
		{
			data = (Country) list.get(0);
		}
		return data;
	}

	public Curr findCurrByName(String name)
	{
		Curr data = null;
		List list = this.find(" select a from Curr a  where a.name=? ",
				new Object[] { name });
		if (list.size() > 0)
		{
			data = (Curr) list.get(0);
		}
		return data;
	}

	public ScmCoc findScmCocByCode(String code, Boolean isCustomer,
			Boolean isManufacturer)
	{
		ScmCoc data = null;
		List properies = new ArrayList();
		String hsql = " select a from  ScmCoc  a  "
				+ "where a.company.id=? and a.code=? ";
		properies.add(CommonUtils.getCompany().getId());
		properies.add(code);
		if (isCustomer != null && isCustomer)
		{
			hsql += "and a.isCustomer=? ";
			properies.add(isCustomer);
		}
		if (isManufacturer != null && isManufacturer)
		{
			hsql += "and a.isManufacturer=? ";
			properies.add(isManufacturer);
		}
		List list = this.find(hsql, properies.toArray());
		if (list.size() > 0)
		{
			data = (ScmCoc) list.get(0);
		}
		return data;
	}

	public Curr findCurrByCode(String code)
	{
		Curr data = null;
		List list = this.find(" select a from Curr a  where a.code=? ",
				new Object[] { code });
		if (list.size() > 0)
		{
			data = (Curr) list.get(0);
		}
		return data;
	}

	public List findCurr()
	{
		List list = this.find(" select a from Curr a  ");
		return list;
	}

	/**
	 * 找出所有的仓库编码类型
	 * 
	 * @return
	 */
	public List findWareSet()
	{
		List list = this.find(" select a from WareSet a   ");
		return list;
	}

	/**
	 * 查询BCS参数
	 * 
	 * @param parameter
	 */
	public BcsParameterSet findBcsParameterSet()
	{
		List list = this.find(
				"select a from BcsParameterSet a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null)
		{
			return (BcsParameterSet) list.get(0);
		}
		else
		{
			BcsParameterSet parameterSet = new BcsParameterSet();
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	/**
	 * 查找其他参数设置资料
	 * 
	 * @return List 是CompanyOther型，其他参数设置资料
	 */
	public List findCompanyOther()
	{
		List list = this.find("select a from CompanyOther a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		while (list.size() <= 0)
		{
			CompanyOther companyOther = new CompanyOther();
			companyOther.setCompany(CommonUtils.getCompany());
			this.getHibernateTemplate().saveOrUpdate(companyOther);
			System.out.println("--------------1:" + companyOther.getOptLock());
			list = this.find("select a from CompanyOther a where a.company.id=?",
					new Object[] { CommonUtils.getCompany().getId() });
		}
		return list;
	}
}
