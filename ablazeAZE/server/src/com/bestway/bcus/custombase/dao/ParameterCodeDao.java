/*
 * Created on 2004-6-9
 * 
 * // Window -
 * Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PortInternal;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.pis.constant.EspMainBusinessType;

public class ParameterCodeDao extends BaseDao {

	/**
	 * 贸易方式
	 */
	public List findTrade(String sFields, String sValue) {
		return findCustoms("Trade", sFields, sValue);
	}

	/**
	 * 计量单位
	 */
	public List findUnit(String sFields, String sValue) {
		return findCustoms("Unit", sFields, sValue);
	}

	/**
	 * 工厂计量单位
	 */
	public List findCalUnit(){
		String sql = "select c from CalUnit c";
		return this.find(sql);
	}
	public Unit findUnitByName(String sValue) {
		if (sValue != null) {
			String hsql = "select a from Unit a where a.name=? and (a.isOut <> '1' or a.isOut is null)";
			List list = find(hsql, new Object[] { sValue });
			if (list != null && list.size() > 0) {
				return (Unit) list.get(0);
			}
		}
		return null;
	}

	/**
	 * 货币代码
	 */
	public List findCurr(String sFields, String sValue) {
		return findCustoms("Curr", sFields, sValue);

	}

	/**
	 * 证件代码
	 */
	public List findLicensedocu(String sFields, String sValue) {
		// return findCustoms("LicenseDocu", sFields, sValue);
		String tableName = "LicenseDocu";
		String hsql = null;
		List list = null;
		if (sFields == null || sFields.equals("")) {
			hsql = "select a from " + tableName
					+ " a where (a.isOut <> '1' or a.isOut is null) "
					+ " order by a.code";
			list = this.getHibernateTemplate().find(hsql);
		} else {
			hsql = "select a from " + tableName
					+ " a where (a.isOut <> '1' or a.isOut is null) and a."
					+ sFields + " like ? " + " order by a.code ";
			list = this.getHibernateTemplate().find(hsql,
					new Object[] { "%" + sValue + "%" });
		}
		return list;
	}

	/**
	 * 证件代码名称
	 * 
	 * @param sFields
	 */
	public String getLicenseDouName(String sValue) {
		if (sValue != null && !"".equals(sValue.trim())) {
			String sql = "select a from LicenseDocu a where a.code = ? and (a.isOut <> '1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(sql,
					new Object[] { sValue });
			if (list != null && list.size() > 0 && list.get(0) != null) {
				return ((LicenseDocu) list.get(0)).getName();
			}
		}
		return null;
	}

	/*
	 * 合同类型
	 */
	public List findBargainType(String sFields, String sValue) {
		return findCustoms("BargainType", sFields, sValue);
	}

	/**
	 * 运输方式
	 */
	public List findTransf(String sFields, String sValue) {
		return findCustoms("Transf", sFields, sValue);

	}

	/**
	 * 成交方式
	 */
	public List findTransac(String sFields, String sValue) {
		return findCustoms("Transac", sFields, sValue);

	}

	/**
	 * 保税方式
	 */
	public List findPayMode(String sFields, String sValue) {
		return findCustoms("PayMode", sFields, sValue);
	}

	/**
	 * 包装种类
	 */
	public List findWrap(String sFields, String sValue) {
		return findCustoms("Wrap", sFields, sValue);

	}
	public List findCustomsBroker(String sFields, String sValue) {
		return findCustoms("CustomsBroker", sFields, sValue);

	}
	public List findBrokerCorp(String sFields, String sValue) {
		return this
				.find(" select distinct a.espAuthorityBrokerCorp.brokerCorp "
						+ " from EspAuthorityBrokerCorpItem a "
						+ " where a.espAuthorityBrokerCorp.brokerCorp.company.id = ? "
						+ " and (a.mainBusiness=? or a.mainBusiness=?) ", new Object[] {
						CommonUtils.getCompany().getId(), 
						EspMainBusinessType.BUSINESS_TYPE_IMP,
						EspMainBusinessType.BUSINESS_TYPE_EXP });
	}

	/**
	 * 用途代码
	 */
	public List findUses(String sFields, String sValue) {
		return findCustoms("Uses", sFields, sValue);

	}

	/**
	 * 集装箱代码
	 */
	public List findSrtJzx(String sFields, String sValue) {
		return findCustoms("SrtJzx", sFields, sValue);

	}

	/**
	 * 集装箱规格
	 */
	public List findContaModel(String sFields, String sValue) {
		return findCustoms("ContaModel", sFields, sValue);

	}

	/**
	 * 集装箱尺寸
	 */
	public List findContaSize(String sFields, String sValue) {
		return findCustoms("ContaSize", sFields, sValue);

	}

	/**
	 * 集装箱托架种类
	 */
	public List findSrtTj(String sFields, String sValue) {
		return findCustoms("SrtTj", sFields, sValue);

	}

	/**
	 * 付款类型
	 */
	public List findPayType(String sFields, String sValue) {
		return findCustoms("PayType", sFields, sValue);

	}

	/**
	 * 付款者类型
	 */
	public List findPayerType(String sFields, String sValue) {
		return findCustoms("PayerType", sFields, sValue);

	}

	/**
	 * 征免方式
	 */
	public List findLevyMode(String sFields, String sValue) {
		return findCustoms("LevyMode", sFields, sValue);

	}

	/**
	 * 征免性质
	 */
	public List findLevyKind(String sFields, String sValue) {
		return findCustoms("LevyKind", sFields, sValue);

	}

	/**
	 * 结汇方式
	 */
	public List findBalanceMode(String sFields, String sValue) {
		return findCustoms("BalanceMode", sFields, sValue);
	}

	/**
	 * 引进方式
	 */
	public List findFetchInMode(String sFields, String sValue) {
		return findCustoms("FetchInMode", sFields, sValue);
	}

	/**
	 * 核扣方式
	 */
	public List findDeduc(String sFields, String sValue) {
		return findCustoms("Deduc", sFields, sValue);
	}

	// 返回加工种类
	public List findWrap() {
		return this
				.find("select a from Wrap a where (isOut <> '1' or isOut is null)");
	}
	/**
	 * 返回报关行
	 */
	public List findCustomsBroker(){
		return this.
				find("select a from CustomsBroker a ");
	}
	/**
	 * 保存加工种类
	 */
	public void SaveWrap(Wrap obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存计量单位
	 * 
	 * @param obj
	 */

	public void SaveUnit(Unit obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 删除加工种类
	 */
	public void DeleteWrap(Wrap obj) {
		this.delete(obj);
	}

	/**
	 * 保存加工种类
	 */
	public void SaveGbtobig(Gbtobig obj) {
		this.saveOrUpdate(obj);
	}

	// 判断繁转简繁体是否重复
	public boolean isReGbtobig(String code, String scode) {
		List list = this.find(
				" select a from Gbtobig a where a.bigname =? and a.bigname<>?",
				new Object[] { code, scode });
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
    //判断注册公司代码是否重复
	public boolean isReDgBriefCode(String code) {
		List list = this.find(
				" select a from Brief a where a.code =?",
				new Object[] { code});
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	
	//判断注册公司名称是否重复
	public boolean isReDgBriefName(String name) {
		List list = this.find(
				" select a from Brief a where a.name =?",
				new Object[] { name});
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	
	// 判断加工种类编号是否重复
	public boolean isReMerger(String code, String scode) {
		List list = this.find(
				" select a from Wrap a where a.code =? and a.code<>?",
				new Object[] { code, scode });
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public Transf findTransf() {
		List list = this.find("select a from Transf a where a.name = '公路运输'");
		if (list != null && list.size() > 0) {
			return (Transf) list.get(0);
		}
		return null;
	}

	public List findAllCustomsDeclaretion() {
		List listBcs = this
				.find(
						" select a from  BcsCustomsDeclaration  a where  a.company.id=? ",
						new Object[] { CommonUtils.getCompany().getId() });
		List listBcus = this.find(
				" select a from  CustomsDeclaration  a where  a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		List listDzsc = this
				.find(
						" select a from  DzscCustomsDeclaration  a where  a.company.id=? ",
						new Object[] { CommonUtils.getCompany().getId() });
		List<BaseCustomsDeclaration> rlist = new ArrayList();
		rlist.addAll(listBcs);
		rlist.addAll(listBcus);
		rlist.addAll(listDzsc);
		return rlist;
	}

	public void SaveBrief(Brief brief) {
		this.saveOrUpdate(brief);
	}

	public void SaveCustom(Customs custom) {
		this.saveOrUpdate(custom);
		
	}

	public boolean isReDgCustomCode(String code) {
		List list = this.find(
				" select a from Customs a where a.code =?",
				new Object[] { code});
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean isReDgCustomName(String name) {
		List list = this.find(
				" select a from Customs a where a.name =?",
				new Object[] { name});
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean isReDgDgPortInternalName(String name) {
		List list = this.find(
				" select a from PortInternal a where a.name =?",
				new Object[] { name});
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean isReDgPortInternalCode(String code) {
		List list = this.find(
				" select a from PortInternal a where a.code =?",
				new Object[] { code});
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	public boolean isReDgOpenPortcode(String code) {
		List list = this.find(
				" select a from PortLin a where a.code =?",
				new Object[] { code});
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	public void SavePortInternal(PortInternal portInternal) {
		this.saveOrUpdate(portInternal);
		
	}
	public void SaveportLin(PortLin portLin) {
		this.saveOrUpdate(portLin);
		
	}
	public void SavePreDock(PreDock preDock) {
		this.saveOrUpdate(preDock);
		
	}

	public boolean isReDgPreDockCode(String code) {
		List list = this.find(
				" select a from PreDock a where a.code =?",
				new Object[] { code});
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean isReDgPreDockName(String name) {
		List list = this.find(
				" select a from PreDock a where a.name =?",
				new Object[] { name});
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 查询系统参数设置
	 * @return
	 */
	public CompanyOther findCompanyOther(){
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from CompanyOther a where a.company.id= ? ";
		parameters.add(CommonUtils.getCompany().getId());
		List<CompanyOther> list = this.find(hql,parameters.toArray());
		if(list.size()>0){
			return (CompanyOther)list.get(0);
		}else{
			CompanyOther companyOther=new CompanyOther();
			this.saveOrUpdate(companyOther);
			return companyOther;
		}
	}
}
