/*
 * Created on 2004-12-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.dao;

import java.util.List;
import java.util.Vector;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.cas.bill.entity.BillMasterMateriel;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.basecode.CoType;
import com.bestway.bcus.custombase.entity.basecode.InvClass;
import com.bestway.bcus.custombase.entity.basecode.InvestMode;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortInternal;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.depcode.SaicCode;
import com.bestway.bcus.custombase.entity.depcode.StsCode;
import com.bestway.bcus.custombase.entity.depcode.TaxCode;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.LicenseNote;
import com.bestway.bcus.custombase.entity.parametercode.BalanceMode;
import com.bestway.bcus.custombase.entity.parametercode.ContaModel;
import com.bestway.bcus.custombase.entity.parametercode.ContaSize;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.PayType;
import com.bestway.bcus.custombase.entity.parametercode.PayerType;
import com.bestway.bcus.custombase.entity.parametercode.SrtJzx;
import com.bestway.bcus.custombase.entity.parametercode.SrtTj;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.FieldList;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.CurrCode;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class DbimportDao extends HibernateDaoSupport {

	/**
	 * 
	 */
	public DbimportDao() {
		super();
	}

	protected void initDao() throws Exception {
		super.initDao();
		this.getHibernateTemplate().setCacheQueries(true);
	}

	/** **********************************************公共方法开始*********************** */

	/*
	 * 通用删除数据方法
	 */
	public void DelTable(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	/*
	 * 通用查询数据方法
	 */
	public List FindTable(String module) {
		if (module != null && !module.equals("")){
		     return this.getHibernateTemplate().find("from  " + module);
		} 
		return new Vector();
	}

	public void onceDelTable(String module) {
		List list = FindTable(module);
		DelTable(list);
	}

	/*
	 * 根据系统登录公司名称查询公司//客户端程序要加一个窗体用来选择公司
	 */

	public Company Findbynamecompany(String sValue) {
		if (sValue != null){
		   String hsql = "select a from Company a where a.name=?";
		   List list = this.getHibernateTemplate().find(hsql,
				   new Object[] { sValue.trim() });
		   if (list != null && list.size() > 0)
			   return (Company) list.get(0);
		}
		return null;
	}

	// 删除所有的公司
	public void deleteAllCompany() {
		this.getHibernateTemplate().deleteAll(
				this.getHibernateTemplate().find("select a from AclUser a"));
		this.getHibernateTemplate().deleteAll(
				this.getHibernateTemplate().find("select a from Company a"));
	}

	public Unit Findbynameunit(String sValue) {
		if (sValue != null){
			String hsql = "select a from Unit a where a.name=?  and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Unit) list.get(0);
		}
		return null;
	}
    public BalanceMode FindByName(String sValue){
    	if (sValue != null){
			String hsql = "select a from BalanceMode a where a.name=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[]{ sValue.trim()});
			if (list != null && list.size()>0){
				return (BalanceMode) list.get(0);
			}
    	}
        return null;
    }
	/*
	 * 查询得到所有的公司
	 */

	public List Findallcompany() {
		String hsql = "select a from Company a where a.name<>'缺省公司'";
		List list = this.getHibernateTemplate().find(hsql);
		return list;
	}

	/*
	 * 根据Delphi版系统中提供的关务海关注册公司的编号查询得到brief实体对象
	 */
	public Brief Findbycodebrief(String sValue) {
		if (sValue != null) {
			String hsql = "select a from Brief a where a.code=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (Brief) list.get(0);
			}
		}
		return null;
	}

	/*
	 * 根据Delphi版系统中提供的关务海关注册公司的名称查询得到brief实体对象
	 */
	public Brief Findbynamebrief(String sValue) {
		if (sValue != null){
			String hsql = "select a from Brief a where a.name=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Brief) list.get(0);
		}
	    return null;
	}

	/*
	 * 根据物料类别编号查询[物流]物料类别ScmCoi
	 */

	public ScmCoi Findbycodescmcoi(String sValue) {
		if (sValue != null) {
			String hsql = "select a from ScmCoi a where a.code=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (ScmCoi) list.get(0);
			}
		}
		return null;
	}
	/**
	 * 由名称找到代码
	 * @param sValue
	 */
	public String FindBriefCodeByName(String sValue){
		if (sValue != null){
			String hsql = "select a from Brief a where a.name = ? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] {sValue.trim()});
			if (list != null && list.size()>0){
				return ((Brief) list.get(0)).getCode();
			}
		}
		return null;
	}

	/*
	 * 根据编号查询商品类别Complex
	 */

	public Complex Findbycodecomplex(String sValue) {
		if (sValue != null) {
			String hsql = "select a from Complex a where a.code=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list.size() > 0) {
				return (Complex) list.get(0);
			}
		}
		return null;
	}
	

	/**
	 * find all complex
	 * 
	 * @param sValue
	 * @return
	 */
	public List FindAllComplex() {
		return this.getHibernateTemplate().find("select a from Complex a where (a.isOut <>'1' or a.isOut is null)");
	}

	/*
	 * 根据名称查询商品类别Complex
	 */

	public String Findbynamecomplex(String sValue) {
		if (sValue != null) {
			String hsql = "select a from Complex a where a.name=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return ((Complex) list.get(0)).getCode();
			}
		}
		return null;
	}

	/*
	 * 根据编号查询计量单位CalUnit
	 */

	public CalUnit Findbycodecalunit(String sValue) {
		if (sValue != null) {
			String hsql = "select a from CalUnit a where a.code=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (CalUnit) list.get(0);
			}
		}
		return null;
	}
	
	/*
	 * 根据名称查询计量单位CalUnit
	 */

	public CalUnit Findbynamecalunit(String sValue) {
		if (sValue != null) {
			String hsql = "select a from CalUnit a where a.name=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (CalUnit) list.get(0);
			}
		}
		return null;
	}

	/*
	 * 根据名称查询客户供应商资料ScmCoc
	 */

	public ScmCoc Findbynamescmcoc(String sValue) {
		if (sValue != null) {
			String hsql = "select a from ScmCoc a where a.fname=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (ScmCoc) list.get(0);
			}
		}
		return null;
	}
	
	/*
	 * 根据名称查询单据类型
	 */

	public BillType Findbynamebilltype(String sValue) {
		if (sValue != null) {
			String hsql = "select a from BillType a where a.name=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (BillType) list.get(0);
			}
		}
		return null;
	}
	
	/*
	 * 根据单据号查询单据类型
	 */

	public BillMasterMateriel FindbynamebillMaster(String sValue) {
		if (sValue != null) {
			String hsql = "select a from BillMasterMateriel a where a.tempNo=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (BillMasterMateriel) list.get(0);
			}
		}
		return null;
	}

	/*
	 * 根据代号查询客户供应商资料ScmCoc
	 */

	public ScmCoc Findbycodescmcoc(String sValue) {
		if (sValue != null){
			String hsql = "select a from ScmCoc a where a.code=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (ScmCoc) list.get(0);
		}
		return null;
	}

	/*
	 * 根据编号查询国家代码SysArea
	 */

	public SysArea Findbycodesysarea(String sValue) {
		if (sValue != null) {
			String hsql = "select a from SysArea a where a.code=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (SysArea) list.get(0);
			}
		}
		return null;
	}

	/*
	 * 根据编号查询国家地区Country
	 */

	public Country Findbycodecountry(String sValue) {
		if (sValue != null){
			String hsql = "select a from Country a where a.code=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Country) list.get(0);
		}
		return null;
	}

	/*
	 * 根据名称查询国家地区Country
	 */

	public Country Findbynamecountry(String sValue) {
		if (sValue != null){
			String hsql = "select a from Country a where a.name=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Country) list.get(0);
		}
		return null;
	}

	/*
	 * 根据编号查询税制代码ShareCode
	 */

	public ShareCode Findbycodesharecode(String sValue) {
		if (sValue != null) {
			String hsql = "select a from ShareCode a where a.code=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (ShareCode) list.get(0);
			}
		}
		return null;
	}

	/*
	 * 根据料号查询物料主档Materiel
	 */
	public Materiel Findbyptnomateriel(String sValue) {
		if (sValue != null) {
			String hsql = "select a from Materiel a where a.ptNo=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0) {
				return (Materiel) list.get(0);
			}
		}
		return null;
	}

	/*
	 * 根据代号查询得到仓库设置WareSet
	 */

	public WareSet Findbycodewareset(String sValue) {
		if (sValue != null){
			String hsql = "select a from WareSet a where a.code=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (WareSet) list.get(0);
		}
		return null;
	}
	
	/*
	 * 根据名称查询得到仓库设置WareSet
	 */

	public WareSet Findbynamewareset(String sValue) {
		if (sValue != null){
			String hsql = "select a from WareSet a where a.name=? ";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (WareSet) list.get(0);
		}
		return null;
	}

	/*
	 * 根据代码查询货币代码Curr
	 */

	public Curr Findbycodecurr(String sValue) {
		if (sValue != null){
			String hsql = "select a from Curr a where a.code=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Curr) list.get(0);
		}
		return null;
	}

	public Curr FindbycodecurrSymb(String sValue) {
		if (sValue != null){
			String hsql = "select a from Curr a where a.currSymb=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Curr) list.get(0);
		}
		return null;
	}
	
	/*
	 * 根据EMSNO得到EmsHeadH2k
	 */

	public EmsHeadH2k FindbyemsnoEmsHeadH2k(String sValue) {
		if (sValue != null){
			String hsql = "select a from  EmsHeadH2k a.where a.emsno=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (EmsHeadH2k) list.get(0);
		}
		return null;
	}

	/*
	 * 根据loginname得到AclUser
	 */
	public AclUser FindbyloginnameAclUser(String sValue) {
		if (sValue != null){
			String hsql = "select a from  AclUser a where a.loginName=?";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (AclUser) list.get(0);
		}
		return null;
	}

	/*
	 * 根据code得到Customs
	 */

	public Customs Findbycodecustoms(String sValue) {
		if (sValue != null){
			String hsql = "select a from  Customs a where a.code=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Customs) list.get(0);
			}
		return null;
	}

	/*
	 * 根据了解name得到Customs
	 */
	public Customs Findbynamecustoms(String sValue) {
		if (sValue != null){
			String hsql = "select a from  Customs a where a.name=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Customs) list.get(0);
		}
		return null;
	}

	/*
	 * 根据code得到Transf
	 */
	public Transf Findbycodetransf(String sValue) {
		if (sValue != null){
			String hsql = "select a from  Transf a where a.code=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Transf) list.get(0);
		}
		return null;
	}

	/*
	 * 根据name得到Transf
	 */
	public Transf Findbynametransf(String sValue) {
		if (sValue != null){
			String hsql = "select a from  Transf a where a.name=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Transf) list.get(0);
		}
		return null;
	}

	/*
	 * 根据Code得到Trade
	 */
	public Trade Findbycodetrade(String sValue) {
		if (sValue != null){
			String hsql = "select a from  Trade a where a.code=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Trade) list.get(0);
		}
		return null;
	}

	/*
	 * 根据name得到Trade
	 */
	public Trade Findbynametrade(String sValue) {
		if (sValue != null ){
			String hsql = "select a from  Trade a where a.name=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Trade) list.get(0);
		}
		return null;
	}

	/*
	 * 计量方式 Unit->unit
	 */
	public Unit Findbycodeunit(String sValue) {
		if (sValue != null){
			String hsql = "select a from  Unit a where a.code=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { sValue.trim() });
			if (list != null && list.size() > 0)
				return (Unit) list.get(0);
		}
		return null;
	}

	/*
	 * 根据Code与Name 得到LevyMode
	 */
	public LevyMode FindLevyMode(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from LevyMode a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=?  and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (LevyMode) list.get(0);
		}
		return null;
	}

	/*
	 * 根据Code与Name 得到LevyKind
	 */
	public LevyKind FindLevyKind(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from LevyKind a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=?  and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (LevyKind) list.get(0);
		}
		return null;
	}

	/*
	 * 根据Code与Name 得到District
	 */

	public District FindDistrict(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from  District a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (District) list.get(0);
		}
		return null;
	}

	/*
	 * 根据Code与Name 得到PortLin
	 */

	public PortLin FindPortLin(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from  PortLin a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (PortLin) list.get(0);
		}
		return null;
	}

	/*
	 * 根据Code与Name 得到Transac
	 */

	public Transac FindTransac(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from  Transac a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=?  and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (Transac) list.get(0);
		}
		return null;
	}

	/*
	 * 根据Code与Name 得到Wrap
	 */
	public Wrap FindWrap(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from  Wrap a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (Wrap) list.get(0);
		}
		return null;
	}

	/*
	 * 根据Code与Name 得到Uses
	 */
	public Uses FindUses(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from  Uses a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (Uses) list.get(0);
		}
		return null;
	}

	/*
	 * 根据名称(中文或符号)得到Curr
	 */

	public Curr Findbych_encurr(String sValue1, String sValue2) {
		if (sValue1 != null && sValue2 != null){
			String hsql = "select a from  Curr a where "
					+ (!sValue1.trim().equals("") ? "a.name" : "a.currSymb") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate()
					.find(
							hsql,
							new Object[] { (!sValue1.trim().equals("") ? sValue1.trim()
									: sValue2.trim()) });
			if (list != null && list.size() > 0)
				return (Curr) list.get(0);
		}
		return null;
	}

	
	
	public BalanceMode FindbyBalanceMode(String sValue1, String sValue2) {
		if (sValue1 != null && sValue2 != null){
			String hsql = "select a from  BalanceMode a where "
					+ (!sValue1.trim().equals("") ? "a.code" : "a.name") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate()
					.find(
							hsql,
							new Object[] { (!sValue1.trim().equals("") ? sValue1.trim()
									: sValue2.trim()) });
			if (list != null && list.size() > 0)
				return (BalanceMode) list.get(0);
		}
		return null;
	}

	
	
	
	/*
	 * 根据Code,Name 得到PreDock
	 */

	public PreDock FindPreDock(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from  PreDock a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (PreDock) list.get(0);
		}
		return null;
	}

	
	public MachiningType FindMachiningType(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from  MachiningType a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (MachiningType) list.get(0);
		}
		return null;
	}
	
	
	/*
	 * 根据Code,Name 得到BalanceMode
	 */

	public BalanceMode FindBalanceMode(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from  BalanceMode a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (BalanceMode) list.get(0);
		}
		return null;
	}

	/*
	 * 查询得到CustomsDeclaration
	 */

	public CustomsDeclaration FindCustomsDeclaration(String emsHeadH2k,
			Integer serialNumber, Integer impExpFlag) {
		String hsql = " select a from CustomsDeclaration a where a.emsHeadH2k=? and a.serialNumber=? and a.impExpFlag=?";
		List list = this.getHibernateTemplate().find(hsql,
				new Object[] { emsHeadH2k, serialNumber, impExpFlag });
		if (list.size() > 0)
			return (CustomsDeclaration) list.get(0);
		else
			return null;
	}

	/*
	 * 查询得到ApplyToCustomsBillList
	 */

	public ApplyToCustomsBillList FindApplyToCustomsBillList(String listno) {
		String hsql = " select a from ApplyToCustomsBillList a where a.listno=?";
		List list = this.getHibernateTemplate().find(hsql,
				new Object[] { listno });
		if (list.size() > 0)
			return (ApplyToCustomsBillList) list.get(0);
		else
			return null;
	}

	public ClassList findClassListById(String id) {
		List list = this.getHibernateTemplate().find(
				"select a from ClassList a where id='" + id + "'");
		if (list.size() > 0)
			return (ClassList) list.get(0);
		else
			return null;
	}

	/** **********************************************公共方法结束*********************** */

	/*
	 * 
	 * @author Administrator
	 * 
	 * 企业性质CoType->BUST_TYPE
	 */

	public List FindCotype() {
		return this.getHibernateTemplate().find("select a from CoType a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelCotype(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SaveCotype(CoType Cotype) {
		this.getHibernateTemplate().save(Cotype);
	}

	public PayMode FindPayMode(String sCode, String sName) {
		if (sCode != null && sName != null){
			String hsql = "select a from  PayMode a where "
					+ (!sCode.trim().equals("") ? "a.code" : "a.name") + "=? and (a.isOut <>'1' or a.isOut is null)";
			List list = this.getHibernateTemplate().find(hsql,
					new Object[] { (!sCode.trim().equals("") ? sCode.trim() : sName.trim()) });
			if (list != null && list.size() > 0)
				return (PayMode) list.get(0);
		}
		return null;
	}
	
	/*
	 * 
	 * @author Administrator
	 * 
	 * 投资类型invclass->INV_CLASS
	 */

	public List FindInvclass() {
		return this.getHibernateTemplate().find("select a from InvClass a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelInvclass(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SaveInvclass(InvClass invClass) {
		this.getHibernateTemplate().save(invClass);
	}

	public void SaveFieldList(FieldList fieldList) {
		this.getHibernateTemplate().save(fieldList);
	}

	/*
	 * 
	 * @author Administrator 投资方式investmode->INVEST_MODE
	 */
	public List FindInvestmode() {
		return this.getHibernateTemplate().find("select a from InvestMode a where  (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelInvestmode(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SaveInvestmode(InvestMode investMode) {
		this.getHibernateTemplate().save(investMode);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 加工种类MachiningType->hd_jgzl
	 */

	public List FindMachiningType() {
		return this.getHibernateTemplate().find("select a from MachiningType a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelMachiningType(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SaveMachiningType(MachiningType machiningType) {
		this.getHibernateTemplate().save(machiningType);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 申报报关方式applicationMode->D_MODE
	 */
	// ApplicationMode
	public List FindapplicationMode() {
		return this.getHibernateTemplate().find("select a from ApplicationMode a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelapplicationMode(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SaveApplicationMode(ApplicationMode applicationMode) {
		this.getHibernateTemplate().save(applicationMode);
	}

	public void saveDistrict(District district) {
		this.getHibernateTemplate().save(district);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 海关注册公司brief->brief
	 * 
	 */

	public List Findbrief() {
		return this.getHibernateTemplate().find("select a from Brief a  where (a.isOut <>'1' or a.isOut is null)");
	}

	public void Delbrief(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void Savebrief(Brief brief) {
		this.getHibernateTemplate().save(brief);
	}

	public void SaveClassList(ClassList classList) {
		this.getHibernateTemplate().save(classList);
	}

	/*----------------------------------------------------------------------------------
	 * 
	 * @author Administrator
	 *部门资料
	 */
	/*
	 * 
	 * @author Administrator 税务部门TaxCode->TAX_CODE
	 */

	public List FindtaxCode() {
		return this.getHibernateTemplate().find("selcet a from TaxCode a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DeltaxCode(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SavetaxCode(TaxCode taxCode) {
		this.getHibernateTemplate().save(taxCode);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 工商行政SaicCode->SAIC_CODE
	 */
	public List FindsaicCode() {
		return this.getHibernateTemplate().find("select a from SaicCode a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelsaicCode(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SavesaicCode(SaicCode saicCode) {
		// this.getHibernateTemplate().save(saicCode);
		this.getHibernateTemplate().save(saicCode);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 技术监督StsCode->STS_CODE
	 */
	public List FindstsCode() {
		return this.getHibernateTemplate().find("select a from StsCode a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelstsCode(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SavestsCode(StsCode stsCode) {
		this.getHibernateTemplate().save(stsCode);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 外经贸部门RedDep->RED_DEP
	 */
	public List FindredDep() {
		return this.getHibernateTemplate().find(" select a from RedDep a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelredDep(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SaveredDep(RedDep redDep) {
		this.getHibernateTemplate().save(redDep);
	}

	/*----------------------------------------------------------------------------------
	 * 
	 * @author Administrator
	 *国家地区
	 */
	/*
	 * 
	 * @author Administrator
	 * 
	 * 国家地区Country->COUNTRY
	 */
	public List Findcountry() {
		return this.getHibernateTemplate().find("select a from Country a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void Delcountry(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void Savecountry(Country country) {
		this.getHibernateTemplate().save(country);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 海关关区Customs->Customs
	 */
	public List Findcustoms() {
		return this.getHibernateTemplate().find("select a from Customs a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void Delcustoms(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void Savecustoms(Customs customs) {
		this.getHibernateTemplate().save(customs);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 国内进出口口岸PortInternal->Port_internal
	 */
	public List FindportInternal() {
		return this.getHibernateTemplate().find("select a from PortInternal a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelportInternal(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SaveportInternal(PortInternal portInternal) {
		this.getHibernateTemplate().save(portInternal);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 国际进出口港口PortLin->Port_lin
	 */

	public List FindportLin() {
		return this.getHibernateTemplate().find("select a from PortLin a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelportLin(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SaveportLin(PortLin portLin) {
		this.getHibernateTemplate().save(portLin);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 国内进出口码头PreDock->Pre_dock
	 */
	public List FindpreDock() {
		return this.getHibernateTemplate().find("select a from PreDock a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void DelpreDock(List list) {
		this.getHibernateTemplate().deleteAll(list);
	}

	public void SavepreDock(PreDock preDock) {
		this.getHibernateTemplate().save(preDock);
	}

	/*----------------------------------------------------------------------------------
	 * 
	 * @author Administrator
	 * 报关参数
	 */

	/*
	 * 贸易方式 Trade->trade
	 */

	public List Findtrade() {
		return this.getHibernateTemplate().find("select a from Trade a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void Savetrade(Trade trade) {
		this.getHibernateTemplate().save(trade);
	}

	/*
	 * 计量方式 Unit->unit
	 */

	public List FindUnit() {
		return this.getHibernateTemplate().find("select a from Unit a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void SaveUnit(Unit unit) {
		this.getHibernateTemplate().save(unit);
	}

	/*
	 * 货币代码 Curr->curr
	 */
	public List Findcurr() {
		return this.getHibernateTemplate().find("select a from Curr a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void Savecurr(Curr curr) {
		this.getHibernateTemplate().save(curr);
	}

	// 征免方式
	public void SaveLevyMode(LevyMode levyMode) {
		this.getHibernateTemplate().save(levyMode);
	}

	// 征免性质
	public void SaveLevyKind(LevyKind levyKind) {
		this.getHibernateTemplate().save(levyKind);
	}

	/*
	 * 证件代码LicenseDocu->LICENSEDOCU
	 */

	public List FindlicenseDocu() {
		return this.getHibernateTemplate().find("select a from LicenseDocu a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void SavelicenseDocu(LicenseDocu licenseDocu) {
		this.getHibernateTemplate().save(licenseDocu);
	}

	/*
	 * 运输方式Transf->TRANSF
	 */
	public List Findtransf() {
		return this.getHibernateTemplate().find("select a from Transf a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void Savetransf(Transf transf) {
		this.getHibernateTemplate().save(transf);
	}

	/*
	 * 成交方式Transac->TRANSAC
	 */
	public List Findtransac() {
		return this.getHibernateTemplate().find("select a from Transac a  where (a.isOut <>'1' or a.isOut is null)");
	}

	public void Savetransac(Transac transac) {
		this.getHibernateTemplate().save(transac);
	}

	/*
	 * 保税方式PayMode->PAY_MODE
	 */

	public List FindpayMode() {
		return this.getHibernateTemplate().find("select a from PayMode a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void SavepayMode(PayMode payMode) {
		this.getHibernateTemplate().save(payMode);
	}

	/*
	 * 包装种类Wrap->hd_baozhuang
	 */

	public List Findwrap() {
		return this.getHibernateTemplate().find("select a from Wrap a where (a.isOut <>'1' or a.isOut is null)");
	}

	public void Savewrap(Wrap wrap) {
		this.getHibernateTemplate().save(wrap);
	}

	/*
	 * 用途代码Uses->USE_TO
	 */

	public void Saveuses(Uses uses) {
		this.getHibernateTemplate().save(uses);
	}

	/*
	 * 集装箱代码SrtJzx->SRT_JZX
	 */

	public void SavesrtJzx(SrtJzx srtJzx) {
		this.getHibernateTemplate().save(srtJzx);
	}

	/*
	 * 集装箱规格ContaModel->CONTA_MODEL
	 */
	public void SavecontaModel(ContaModel contaModel) {
		this.getHibernateTemplate().save(contaModel);
	}

	/*
	 * 集装箱尺寸ContaSize->CONTA_SIZE
	 */
	public void SavecontaSize(ContaSize contaSize) {
		this.getHibernateTemplate().save(contaSize);
	}

	/*
	 * 集装箱托架种类SrtTj->SRT_TJ
	 */
	public void SavesrtTj(SrtTj srtTj) {
		this.getHibernateTemplate().save(srtTj);
	}

	/*
	 * 付款类型PayType->PAY_TYPE
	 */

	public void SavepayType(PayType payType) {
		this.getHibernateTemplate().save(payType);
	}

	/*
	 * 付款者类型PayerType-> PAYER_TYPE
	 */
	public void SavepayerType(PayerType payerType) {
		this.getHibernateTemplate().save(payerType);
	}

	/*
	 * 商品类别Complex->complex
	 */

	public void Savecomplex(Complex complex) {
		// this.getHibernateTemplate().save(complex);
		this.getHibernateTemplate().save(complex);
	}

	/*
	 * 领证商品备注表LicenseNote->LicenseNote
	 */
	public void SavelicenseNote(LicenseNote licenseNote) {
		this.getHibernateTemplate().save(licenseNote);
	}

	/*
	 * 导入公司数据Company->sys_cmp
	 */
	public void Savecompany(Company company) {
		this.getHibernateTemplate().save(company);
	}

	/*
	 * 企业内部归并*****************************************************************
	 */

	/*
	 * 仓库设置WareSet->scm_whs
	 */
	public void SavewareSet(WareSet wareSet) {
		this.getHibernateTemplate().save(wareSet);
	}

	/*
	 * 计量单位CalUnit->select * from sys_ccd where ccd_field='LSA_UM'
	 */

	public void SavecalUnit(CalUnit calUnit) {
		this.getHibernateTemplate().save(calUnit);
	}

	/*
	 * 币别设置CurrCode->sys_curr
	 */
	public void SavecurrCode(CurrCode currCode) {
		this.getHibernateTemplate().save(currCode);
	}

	/*
	 * 国家代码SysArea->select * from sys_zone where zone_level=2
	 */

	public void SavesysArea(SysArea sysArea) {
		this.getHibernateTemplate().save(sysArea);
	}

	/*
	 * 客户供应商资料ScmCoc->scm_cut
	 */

	public void SavescmCoc(ScmCoc scmCoc) {
		this.getHibernateTemplate().save(scmCoc);
	}

	/*
	 * 物料类别ScmCoi->scm_coi
	 */

	public void SavescmCoi(ScmCoi scmCoi) {
		this.getHibernateTemplate().save(scmCoi);
	}

	/*
	 * 税制代码ShareCode->sys_tax //加一个税制代码taxation
	 */

	public void SaveshareCode(ShareCode shareCode) {
		this.getHibernateTemplate().save(shareCode);
	}

	/*
	 * 物料主档Materiel->scm_ptm
	 */

	public void Savemateriel(Materiel materiel) {
		this.getHibernateTemplate().save(materiel);
	}

	/**
	 * 产品结构BOM
	 * 
	 * @param innerMergeData
	 */
	public void SaveBom(EnterpriseBomManage bom) {
		this.getHibernateTemplate().save(bom);
	}

	/*
	 * 内部归并 InnerMergeData->bcp_imr
	 */

	public void Saveinnermergedata(InnerMergeData innerMergeData) {
		this.getHibernateTemplate().save(innerMergeData);
	}

	/*
	 * 进出口申请单主表 ImpExpRequestBill->Invoice_Head
	 */
	public void SaveimpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.getHibernateTemplate().save(impExpRequestBill);
	}

	/*
	 * 进出口申请单从表 ImpExpCommodityInfo->invoice_body
	 */
	public void SaveimpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
		this.getHibernateTemplate().save(impExpCommodityInfo);
	}

	/*
	 * 报关清单维护ApplyToCustomsBillList->EDI_CL_HEAD
	 */
	public void SaveapplyToCustomsBillList(
			ApplyToCustomsBillList applyToCustomsBillList) {
		this.getHibernateTemplate().save(applyToCustomsBillList);
	}

	/*
	 * 归并后商品信息AtcMergeAfterComInfo->EDI_CL_LIST
	 */

	public void SaveatcMergeAfterComInfo(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		this.getHibernateTemplate().save(atcMergeAfterComInfo);
	}

	/*
	 * 归并前商品信息(可修改) AtcMergeBeforeComInfo->EDI_CL_LIST
	 */
	public void SaveAtcMergeBeforeComInfo(
			AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
		this.getHibernateTemplate().save(atcMergeBeforeComInfo);
	}

	/*
	 * 进口报关单CustomsDeclaration->hg_import_dd
	 */

	public CustomsDeclaration SaveCustomsDeclaration(CustomsDeclaration customsDeclaration) {
		this.getHibernateTemplate().save(customsDeclaration);
		return customsDeclaration;
	}
	
	public BcsCustomsDeclaration SaveCustomsDeclaration(BcsCustomsDeclaration customsDeclaration) {
		this.getHibernateTemplate().save(customsDeclaration);
		return customsDeclaration;
	}
	
	public DzscCustomsDeclaration SaveCustomsDeclaration(DzscCustomsDeclaration customsDeclaration) {
		this.getHibernateTemplate().save(customsDeclaration);
		return customsDeclaration;
	}
	

	/*
	 * 进口报关单(明细表)CustomsDeclarationCommInfo->hg_import_dddetails
	 */
	public void SaveCustomsDeclarationCommInfo(
			CustomsDeclarationCommInfo customsDeclarationCommInfo) {
		this.getHibernateTemplate().save(customsDeclarationCommInfo);
	}

	
	/**
	 * 获得电子帐册物料对象来自海关商品编码
	 * @param complexCode
	 * @return
	 */
	public EmsHeadH2kImg findEmsHeadH2kImgByComplexCode(String complexCode) {
		List list = this.getHibernateTemplate().find(
				"select img  from EmsHeadH2kImg img left join fetch img.complex b"
						+ " where b.code = ?", complexCode);
		if(list.isEmpty()){
			return null;
		}
		return (EmsHeadH2kImg)list.get(0);
	}
	
	

	/**
	 * 获得电子帐册成品对象来自海关商品编码
	 * @param complexCode
	 * @return
	 */
	public EmsHeadH2kExg findEmsHeadH2kExgByComplexCode(String complexCode) {
		List list = this.getHibernateTemplate().find(
				"select img from EmsHeadH2kExg img left join fetch img.complex b"
						+ " where b.code = ?", complexCode);
		if(list.isEmpty()){
			return null;
		}
		return (EmsHeadH2kExg)list.get(0);
	}

	public void SaveObject(Object obj){
		this.getHibernateTemplate().save(obj);
	}
	
	public Contract SaveContract(Contract obj){
		this.getHibernateTemplate().save(obj);
		return obj;
	}
	public ContractExg SaveContractExg(ContractExg obj){
		this.getHibernateTemplate().save(obj);
		return obj;
	}
	public SrtJzx getSrtJzx(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from SrtJzx a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code.trim()});
			if (list != null && list.size()>0){
				return (SrtJzx) list.get(0);
			}
		}
		return null;
	}
	public SrtTj getSrtTj(String code){
		if (code != null){
			List list = this.getHibernateTemplate().find("select a from SrtTj a where a.code=? and (a.isOut <>'1' or a.isOut is null)",
					new Object[]{code.trim()});
			if (list != null && list.size()>0){
				return (SrtTj) list.get(0);
			}
		}
		return null;
	}
}
