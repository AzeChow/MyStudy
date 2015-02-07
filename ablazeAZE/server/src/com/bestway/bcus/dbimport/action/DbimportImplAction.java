/*
 * Created on 2004-12-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.action;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.checkcancel.entity.CheckHead;
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
import com.bestway.bcus.dbimport.dao.DbCasDao;
import com.bestway.bcus.dbimport.dao.DbCustomDao;
import com.bestway.bcus.dbimport.dao.DbManualDeclareDao;
import com.bestway.bcus.dbimport.dao.DbconnectDao;
import com.bestway.bcus.dbimport.dao.DbimportDao;
import com.bestway.bcus.dbimport.entity.DBDataConnect;
import com.bestway.bcus.dbimport.logic.DbBcsLogic;
import com.bestway.bcus.dbimport.logic.DbCasLogic;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptInitBill;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.CurrCode;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class DbimportImplAction implements DbimportAction {
	DbimportDao dbimportDao = null;

	DbconnectDao dbconnectDao = null;

	DbManualDeclareDao dbmanualDeclareDao = null;

	DbCustomDao dbCustomDao = null;

	private DbCasDao dbCasDao = null;
	private DbCasLogic dbCasLogic = null;
	private DbBcsLogic dbBcsLogic = null;

	public DbCasDao getDbCasDao() {
		return dbCasDao;
	}

	/**
	 * @return Returns the dbCasLogic.
	 */
	public DbCasLogic getDbCasLogic() {
		return dbCasLogic;
	}
	/**
	 * @param dbCasLogic The dbCasLogic to set.
	 */
	public void setDbCasLogic(DbCasLogic dbCasLogic) {
		this.dbCasLogic = dbCasLogic;
	}
	public void setDbCasDao(DbCasDao dbCasDao) {
		this.dbCasDao = dbCasDao;
	}

	/**
	 * @return Returns the dbimportdao.
	 */
	public DbimportDao getDbimportDao() {
		return dbimportDao;
	}

	public void deleteAllCompany() {
		this.dbimportDao.deleteAllCompany();
	}

	public SrtJzx getSrtJzx(String code) {
		return dbCustomDao.getSrtJzx(code);
	}

	public SrtTj getSrtTj(String code) {
		return dbCustomDao.getSrtTj(code);
	}

	/**
	 * @param dbimportdao
	 *            The dbimportdao to set.
	 */
	public void setDbimportDao(DbimportDao dbimportDao) {
		this.dbimportDao = dbimportDao;
	}

	public DbimportImplAction() {
		super();
	}
	/**
	 * 由名称找到代码
	 * @param sValue
	 */
	public String FindBriefCodeByName(String sValue){
		return this.dbimportDao.FindBriefCodeByName(sValue);
	}
	public Unit Findbynameunit(String sValue) {
		return this.dbimportDao.Findbynameunit(sValue);
	}
	public BalanceMode FindByName(String sValue){
		return this.dbimportDao.FindByName(sValue);
	}
	public Company Findbynamecompany(String sValue){
		return dbimportDao.Findbynamecompany(sValue);
	}
	/*
	 * 通用删除数据方法
	 */

	public void DelTable(List list) {
		dbimportDao.DelTable(list);
	}

	public void SaveLevyMode(LevyMode levyMode) {
		dbimportDao.SaveLevyMode(levyMode);
	}

	/*
	 * 通用查询数据方法
	 */

	public List FindTable(String module) {
		return dbimportDao.FindTable(module);
	}

	public void onceDelTable(String module) {
		dbimportDao.onceDelTable(module);
	}

	/*
	 * 根据编码查询计量方式
	 */
	public Unit Findbyidunit(String sValue) {
		return this.dbCustomDao.Findbyidunit(sValue);
	}
	/*
	 * 查询得到所有的公司
	 */

	public List Findallcompany() {
		return dbimportDao.Findallcompany();
	}

	/*
	 * 根据Delphi版系统中提供的关务海关注册公司的编号查询得到brief实体对象
	 */

	public Brief Findbycodebrief(String sValue) {
		return dbimportDao.Findbycodebrief(sValue);
	}

	/*
	 * 根据Delphi版系统中提供的关务海关注册公司的名称查询得到brief实体对象
	 */
	public Brief Findbynamebrief(String sValue) {
		return dbimportDao.Findbynamebrief(sValue);
	}

	/*
	 * 根据物料类别编号查询[物流]物料类别ScmCoi
	 */

	public ScmCoi Findbycodescmcoi(String sValue) {
		return dbimportDao.Findbycodescmcoi(sValue);
	}

	/*
	 * 根据编号查询商品类别Complex
	 */

	public Complex Findbycodecomplex(String sValue) {
		return dbimportDao.Findbycodecomplex(sValue);
	}
	/*
	 * 根据名称查询商品类别Complex
	 */
	
	public String Findbynamecomplex(String sValue){
		return dbimportDao.Findbynamecomplex(sValue);
	}

	/*
	 * 根据编号查询计量单位CalUnit
	 */

	public CalUnit Findbycodecalunit(String sValue) {
		return dbimportDao.Findbycodecalunit(sValue);
	}

	/*
	 * 根据名称查询客户供应商资料ScmCoc
	 */

	public ScmCoc Findbynamescmcoc(String sValue) {
		return dbimportDao.Findbynamescmcoc(sValue);
	}

	/*
	 * 根据代号查询客户供应商资料ScmCoc
	 */

	public ScmCoc Findbycodescmcoc(String sValue) {
		return dbimportDao.Findbycodescmcoc(sValue);
	}

	/*
	 * 根据编号查询国家代码SysArea
	 */

	public SysArea Findbycodesysarea(String sValue) {
		return dbimportDao.Findbycodesysarea(sValue);
	}

	/*
	 * 根据编号查询国家地区Country
	 */

	public Country Findbycodecountry(String sValue) {
		return dbimportDao.Findbycodecountry(sValue);
	}

	/*
	 * 根据名称查询国家地区Country
	 */

	public Country Findbynamecountry(String sValue) {
		return dbimportDao.Findbynamecountry(sValue);
	}

	/*
	 * 根据编号查询税制代码ShareCode
	 */

	public ShareCode Findbycodesharecode(String sValue) {
		return dbimportDao.Findbycodesharecode(sValue);
	}

	/*
	 * 根据料号查询物料主档Materiel
	 */
	public Materiel Findbyptnomateriel(String sValue) {
		return dbimportDao.Findbyptnomateriel(sValue);
	}

	/*
	 * 根据代号查询得到仓库设置WareSet
	 */

	public WareSet Findbycodewareset(String sValue) {
		return dbimportDao.Findbycodewareset(sValue);
	}

	/*
	 * 根据代码查询货币代码Curr
	 */

	public Curr Findbycodecurr(String sValue) {
		return dbimportDao.Findbycodecurr(sValue);
	}

	/*
	 * 根据EMSNO得到EmsHeadH2k
	 */

	public EmsHeadH2k FindbyemsnoEmsHeadH2k(String sValue) {
		return dbimportDao.FindbyemsnoEmsHeadH2k(sValue);
	}

	/*
	 * 根据loginname得到AclUser
	 */
	public AclUser FindbyloginnameAclUser(String sValue) {
		return dbimportDao.FindbyloginnameAclUser(sValue);
	}

	/*
	 * 根据code得到Customs
	 */

	public Customs Findbycodecustoms(String sValue) {
		return dbimportDao.Findbycodecustoms(sValue);
	}

	/*
	 * 根据了解name得到Customs
	 */
	public Customs Findbynamecustoms(String sValue) {
		return dbimportDao.Findbynamecustoms(sValue);
	}

	/*
	 * 根据code得到Transf
	 */
	public Transf Findbycodetransf(String sValue) {
		return dbimportDao.Findbycodetransf(sValue);
	}

	/*
	 * 根据name得到Transf
	 */
	public Transf Findbynametransf(String sValue) {
		return dbimportDao.Findbynametransf(sValue);
	}

	/*
	 * 根据Code得到Trade
	 */
	public Trade Findbycodetrade(String sValue) {
		return dbimportDao.Findbycodetrade(sValue);
	}

	/*
	 * 根据name得到Trade
	 */
	public Trade Findbynametrade(String sValue) {
		return dbimportDao.Findbynametrade(sValue);
	}

	/*
	 * 根据Code得到计量方式 Unit->unit
	 */
	public Unit Findbycodeunit(String sValue) {
		return dbimportDao.Findbycodeunit(sValue);
	}

	/*
	 * 根据Code与Name 得到LevyMode
	 */
	public LevyMode FindLevyMode(String sCode, String sName) {
		return dbimportDao.FindLevyMode(sCode, sName);
	}

	/*
	 * 根据Code与Name 得到District
	 */

	public District FindDistrict(String sCode, String sName) {
		return dbimportDao.FindDistrict(sCode, sName);
	}

	/*
	 * 根据Code与Name 得到PortLin
	 */

	public PortLin FindPortLin(String sCode, String sName) {
		return dbimportDao.FindPortLin(sCode, sName);
	}

	/*
	 * 根据Code与Name 得到Transac
	 */

	public Transac FindTransac(String sCode, String sName) {
		return dbimportDao.FindTransac(sCode, sName);
	}

	/*
	 * 根据Code与Name 得到Wrap
	 */
	public Wrap FindWrap(String sCode, String sName) {
		return dbimportDao.FindWrap(sCode, sName);
	}

	/*
	 * 根据Code与Name 得到Uses
	 */
	public Uses FindUses(String sCode, String sName) {
		return dbimportDao.FindUses(sCode, sName);
	}

	/*
	 * 根据名称(中文或符号)得到Curr
	 */

	public Curr Findbych_encurr(String sValue1, String sValue2) {
		return dbimportDao.Findbych_encurr(sValue1, sValue2);
	}

	/*
	 * 根据Code,Name 得到PreDock
	 */

	public PreDock FindPreDock(String sCode, String sName) {
		return dbimportDao.FindPreDock(sCode, sName);
	}

	/*
	 * 根据Code,Name 得到BalanceMode
	 */

	public BalanceMode FindBalanceMode(String sCode, String sName) {
		return dbimportDao.FindBalanceMode(sCode, sName);
	}

	/*
	 * 查询得到CustomsDeclaration
	 */

	public CustomsDeclaration FindCustomsDeclaration(String emsHeadH2k,
			Integer serialNumber, Integer impExpFlag) {
		return dbimportDao.FindCustomsDeclaration(emsHeadH2k, serialNumber,
				impExpFlag);
	}

	/*
	 * 查询得到ApplyToCustomsBillList
	 */

	public ApplyToCustomsBillList FindApplyToCustomsBillList(String listno) {
		return dbimportDao.FindApplyToCustomsBillList(listno);
	}

	/** **********************************************公共方法结束*********************** */

	/*
	 * 
	 * @author Administrator
	 * 
	 * 对企业性质CoType
	 */

	public List FindCotype() {
		return dbimportDao.FindCotype();
	}

	public void DelCotype(List list) {
		dbimportDao.DelCotype(list);
	}

	public void SaveCotype(CoType Cotype) {
		dbimportDao.SaveCotype(Cotype);
	}

	/*
	 * 投资类型invclass->INV_CLASS
	 */
	public List FindInvclass() {
		return dbimportDao.FindInvclass();
	}

	public void DelInvclass(List list) {
		dbimportDao.DelInvclass(list);
	}

	public void SaveInvclass(InvClass invClass) {
		dbimportDao.SaveInvclass(invClass);
	}

	/*
	 * 
	 * @author Administrator 投资方式investmode->INVEST_MODE
	 */

	public List FindInvestmode() {
		return dbimportDao.FindInvestmode();
	}

	public void DelInvestmode(List list) {
		dbimportDao.DelInvestmode(list);
	}

	public void SaveInvestmode(InvestMode investMode) {
		dbimportDao.SaveInvestmode(investMode);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 加工种类MachiningType->hd_jgzl
	 */

	public List FindMachiningType() {
		return dbimportDao.FindMachiningType();
	}

	public void DelMachiningType(List list) {
		dbimportDao.DelMachiningType(list);
	}

	public void SaveMachiningType(MachiningType machiningType) {
		dbimportDao.SaveMachiningType(machiningType);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 申报报关方式applicationMode->D_MODE
	 */
	// ApplicationMode
	public List FindapplicationMode() {
		return dbimportDao.FindapplicationMode();
	}

	public void DelapplicationMode(List list) {
		dbimportDao.DelapplicationMode(list);
	}

	public void SaveApplicationMode(ApplicationMode applicationMode) {
		dbimportDao.SaveApplicationMode(applicationMode);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 海关注册公司brief->brief
	 * 
	 */

	public List Findbrief() {
		return dbimportDao.Findbrief();
	}

	public void Delbrief(List list) {
		dbimportDao.Delbrief(list);
	}

	public void SaveBom(EnterpriseBomManage bom) {
		dbimportDao.SaveBom(bom);
	}

	public void Savebrief(Brief brief) {
		dbimportDao.Savebrief(brief);
	}

	public ClassList SaveClassList(ClassList classList) {
		dbimportDao.SaveClassList(classList);
		return classList;
	}

	/*
	 * 
	 * @author Administrator 税务部门TaxCode->TAX_CODE
	 */

	public List FindtaxCode() {
		return dbimportDao.FindtaxCode();
	}

	public void DeltaxCode(List list) {
		dbimportDao.DeltaxCode(list);
	}

	public void SavetaxCode(TaxCode taxCode) {
		dbimportDao.SavetaxCode(taxCode);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 工商行政SaicCode->SAIC_CODE
	 */
	public List FindsaicCode() {
		return dbimportDao.FindsaicCode();
	}

	public void DelsaicCode(List list) {
		dbimportDao.DelsaicCode(list);
	}

	public void SavesaicCode(SaicCode saicCode) {
		dbimportDao.SavesaicCode(saicCode);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 技术监督StsCode->STS_CODE
	 */
	public List FindstsCode() {
		return dbimportDao.FindstsCode();
	}

	public void DelstsCode(List list) {
		dbimportDao.DelstsCode(list);
	}

	public void SavestsCode(StsCode stsCode) {
		dbimportDao.SavestsCode(stsCode);
	}

	public FieldList SaveFieldList(FieldList fieldList) {
		dbimportDao.SaveFieldList(fieldList);
		return fieldList;
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 外经贸部门RedDep->RED_DEP
	 */
	public List FindredDep() {
		return dbimportDao.FindredDep();
	}

	public void DelredDep(List list) {
		dbimportDao.DelredDep(list);
	}

	public void SaveredDep(RedDep redDep) {
		dbimportDao.SaveredDep(redDep);
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
		return dbimportDao.Findcountry();
	}

	public void Delcountry(List list) {
		dbimportDao.Delcountry(list);
	}

	public void Savecountry(Country country) {
		dbimportDao.Savecountry(country);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 海关关区Customs->Customs
	 */
	public List Findcustoms() {
		return dbimportDao.Findcustoms();

	}

	public void Delcustoms(List list) {
		dbimportDao.Delcustoms(list);
	}

	public void Savecustoms(Customs customs) {
		dbimportDao.Savecustoms(customs);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 国内进出口口岸PortInternal->Port_internal
	 */
	public List FindportInternal() {
		return dbimportDao.FindportInternal();
	}

	public void DelportInternal(List list) {
		dbimportDao.DelportInternal(list);
	}

	public void SaveportInternal(PortInternal portInternal) {
		dbimportDao.SaveportInternal(portInternal);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 国际进出口港口PortLin->Port_lin
	 */

	public List FindportLin() {
		return dbimportDao.FindportLin();
	}

	public void DelportLin(List list) {
		dbimportDao.DelportLin(list);
	}

	public void SaveportLin(PortLin portLin) {
		dbimportDao.SaveportLin(portLin);
	}

	/*
	 * 
	 * @author Administrator
	 * 
	 * 国内进出口码头PreDock->Pre_dock
	 */
	public List FindpreDock() {
		return dbimportDao.FindpreDock();
	}

	public void DelpreDock(List list) {
		dbimportDao.DelpreDock(list);
	}

	public void SavepreDock(PreDock preDock) {
		dbimportDao.SavepreDock(preDock);
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
		return dbimportDao.Findtrade();
	}

	public void Savetrade(Trade trade) {
		dbimportDao.Savetrade(trade);
	}

	/*
	 * 对Unit操作
	 */
	public List FindUnit() {
		return dbimportDao.FindUnit();
	}

	public void SaveUnit(Unit unit) {
		dbimportDao.SaveUnit(unit);
	}

	/*
	 * 货币代码 Curr->curr
	 */
	public List Findcurr() {
		return dbimportDao.Findcurr();
	}

	public void Savecurr(Curr curr) {
		dbimportDao.Savecurr(curr);
	}

	/*
	 * 证件代码LicenseDocu->LICENSEDOCU
	 */

	public List FindlicenseDocu() {
		return dbimportDao.FindlicenseDocu();
	}

	public void SavelicenseDocu(LicenseDocu licenseDocu) {
		dbimportDao.SavelicenseDocu(licenseDocu);
	}

	/*
	 * 运输方式Transf->TRANSF
	 */
	public List Findtransf() {
		return dbimportDao.Findtransf();
	}

	public void Savetransf(Transf transf) {
		dbimportDao.Savetransf(transf);
	}

	/*
	 * 成交方式Transac->TRANSAC
	 */
	public List Findtransac() {
		return dbimportDao.Findtransac();
	}

	public void Savetransac(Transac transac) {
		dbimportDao.Savetransac(transac);
	}

	/*
	 * 保税方式PayMode->PAY_MODE
	 */

	public List FindpayMode() {
		return dbimportDao.FindpayMode();
	}

	public void SavepayMode(PayMode payMode) {
		dbimportDao.SavepayMode(payMode);
	}

	/*
	 * 包装种类Wrap->hd_baozhuang
	 */

	public List Findwrap() {
		return dbimportDao.Findwrap();
	}

	public void Savewrap(Wrap wrap) {
		dbimportDao.Savewrap(wrap);
	}

	/*
	 * 用途代码Uses->USE_TO
	 */

	public void Saveuses(Uses uses) {
		dbimportDao.Saveuses(uses);
	}

	/*
	 * 集装箱代码SrtJzx->SRT_JZX
	 */

	public void SavesrtJzx(SrtJzx srtJzx) {
		dbimportDao.SavesrtJzx(srtJzx);
	}

	/*
	 * 集装箱规格ContaModel->CONTA_MODEL
	 */
	public void SavecontaModel(ContaModel contaModel) {
		dbimportDao.SavecontaModel(contaModel);
	}

	/*
	 * 集装箱尺寸ContaSize->CONTA_SIZE
	 */
	public void SavecontaSize(ContaSize contaSize) {
		dbimportDao.SavecontaSize(contaSize);
	}

	/*
	 * 集装箱托架种类SrtTj->SRT_TJ
	 */
	public void SavesrtTj(SrtTj srtTj) {
		dbimportDao.SavesrtTj(srtTj);
	}

	/*
	 * 付款类型PayType->PAY_TYPE
	 */

	public void SavepayType(PayType payType) {
		dbimportDao.SavepayType(payType);
	}

	/*
	 * 付款者类型PayerType-> PAYER_TYPE
	 */
	public void SavepayerType(PayerType payerType) {
		dbimportDao.SavepayerType(payerType);
	}

	/*
	 * 商品类别Complex->complex
	 */

	public void Savecomplex(Complex complex) {
		dbimportDao.Savecomplex(complex);
	}

	/*
	 * 领证商品备注表LicenseNote->LicenseNote
	 */
	public void SavelicenseNote(LicenseNote licenseNote) {
		dbimportDao.SavelicenseNote(licenseNote);
	}

	/*
	 * 导入公司数据Company->sys_cmp
	 */
	public void Savecompany(Company company) {
		dbimportDao.Savecompany(company);
	}

	/*
	 * 仓库设置WareSet->scm_whs
	 */
	public void SavewareSet(WareSet wareSet) {
		dbimportDao.SavewareSet(wareSet);
	}

	/*
	 * 计量单位CalUnit->select * from sys_ccd where ccd_field='LSA_UM'
	 */

	public void SavecalUnit(CalUnit calUnit) {
		dbimportDao.SavecalUnit(calUnit);
	}

	/*
	 * 币别设置CurrCode->sys_curr
	 */
	public void SavecurrCode(CurrCode currCode) {
		dbimportDao.SavecurrCode(currCode);
	}

	/*
	 * 国家代码SysArea->select * from sys_zone where zone_level=2
	 */

	public void SavesysArea(SysArea sysArea) {
		dbimportDao.SavesysArea(sysArea);
	}

	/*
	 * 客户供应商资料ScmCoc->scm_cut
	 */

	public void SavescmCoc(ScmCoc scmCoc) {
		dbimportDao.SavescmCoc(scmCoc);
	}

	/*
	 * 物料类别ScmCoi->scm_coi
	 */

	public void SavescmCoi(ScmCoi scmCoi) {
		dbimportDao.SavescmCoi(scmCoi);
	}

	/**
	 * 地区代码
	 * 
	 * @param district
	 */
	public void saveDistrict(District district) {
		dbimportDao.saveDistrict(district);
	}

	/*
	 * 税制代码ShareCode->sys_tax //加一个税制代码taxation
	 */

	public void SaveshareCode(ShareCode shareCode) {
		dbimportDao.SaveshareCode(shareCode);
	}

	/*
	 * 物料主档Materiel->scm_ptm
	 */

	public void Savemateriel(Materiel materiel) {
		dbimportDao.Savemateriel(materiel);
	}

	/*
	 * 内部归并 InnerMergeData->bcp_imr
	 */

	public void Saveinnermergedata(InnerMergeData innerMergeData) {
		dbimportDao.Saveinnermergedata(innerMergeData);
	}

	/*
	 * 进出口申请单主表 ImpExpRequestBill->Invoice_Head
	 */
	public void SaveimpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		dbimportDao.SaveimpExpRequestBill(impExpRequestBill);
	}

	/*
	 * 进出口申请单从表 ImpExpCommodityInfo->invoice_body
	 */
	public void SaveimpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
		dbimportDao.SaveimpExpCommodityInfo(impExpCommodityInfo);
	}

	/*
	 * 报关清单维护ApplyToCustomsBillList->EDI_CL_HEAD
	 */
	public void SaveapplyToCustomsBillList(
			ApplyToCustomsBillList applyToCustomsBillList) {
		dbimportDao.SaveapplyToCustomsBillList(applyToCustomsBillList);
	}

	/*
	 * 归并后商品信息AtcMergeAfterComInfo->EDI_CL_LIST
	 */

	public void SaveatcMergeAfterComInfo(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		dbimportDao.SaveatcMergeAfterComInfo(atcMergeAfterComInfo);
	}

	/*
	 * 归并前商品信息(可修改) AtcMergeBeforeComInfo->EDI_CL_LIST
	 */
	public void SaveAtcMergeBeforeComInfo(
			AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
		dbimportDao.SaveAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
	}

	/*
	 * 进口报关单CustomsDeclaration->hg_import_dd
	 */

	public CustomsDeclaration SaveCustomsDeclaration(
			CustomsDeclaration customsDeclaration) {
		dbimportDao.SaveCustomsDeclaration(customsDeclaration);
		return customsDeclaration;
	}

	/*
	 * 进口报关单(明细表)CustomsDeclarationCommInfo->hg_import_dddetails
	 */
	public void SaveCustomsDeclarationCommInfo(
			CustomsDeclarationCommInfo customsDeclarationCommInfo) {
		dbimportDao.SaveCustomsDeclarationCommInfo(customsDeclarationCommInfo);
	}

	/**
	 * @return Returns the dbconnectDao.
	 */
	public DbconnectDao getDbconnectDao() {
		return dbconnectDao;
	}

	/**
	 * @param dbconnectDao
	 *            The dbconnectDao to set.
	 */
	public void setDbconnectDao(DbconnectDao dbconnectDao) {
		this.dbconnectDao = dbconnectDao;
	}

	public List findDbConnect() {
		return dbconnectDao.findDbConnect();
	}

	public void saveDBDataConnect(DBDataConnect db) {
		dbconnectDao.saveDBDataConnect(db);
	}

	/**
	 * @return Returns the dbmanualDeclareDao.
	 */
	public DbManualDeclareDao getDbmanualDeclareDao() {
		return dbmanualDeclareDao;
	}

	/**
	 * @param dbmanualDeclareDao
	 *            The dbmanualDeclareDao to set.
	 */
	public void setDbmanualDeclareDao(DbManualDeclareDao dbmanualDeclareDao) {
		this.dbmanualDeclareDao = dbmanualDeclareDao;
	}

	public EmsEdiTrHead saveEmsEdiTrHead(EmsEdiTrHead head)
			throws DataAccessException {
		this.dbmanualDeclareDao.saveEmsEdiTrHead(head);
		return head;
	}

	public EmsEdiMergerHead saveEmsMergerHead(EmsEdiMergerHead head)
			throws DataAccessException {
		this.dbmanualDeclareDao.saveEmsMergerHead(head);
		return head;
	}

	public EmsEdiMergerImgAfter saveEmsMergerImgAfter(
			EmsEdiMergerImgAfter imgAfter) throws DataAccessException {
		this.dbmanualDeclareDao.saveEmsMergerImgAfter(imgAfter);
		return imgAfter;
	}

	public EmsEdiMergerExgAfter saveEmsMergerExgAfter(
			EmsEdiMergerExgAfter exgAfter) throws DataAccessException {
		this.dbmanualDeclareDao.saveEmsMergerExgAfter(exgAfter);
		return exgAfter;
	}

	public EmsEdiMergerExgBefore saveEmsMergerExgBefore(
			EmsEdiMergerExgBefore exgBefore) throws DataAccessException {
		this.dbmanualDeclareDao.saveEmsMergerExgBefore(exgBefore);
		return exgBefore;
	}

	public EmsHeadH2k saveEmsHead(EmsHeadH2k head) throws DataAccessException {
		this.dbmanualDeclareDao.saveEmsHead(head);
		return head;
	}

	public EmsHeadH2kVersion saveEmsHeadH2kVersion(EmsHeadH2kVersion version)
			throws DataAccessException {
		this.dbmanualDeclareDao.saveEmsHeadH2kVersion(version);
		return version;
	}

	public EmsHeadH2kExg saveEmsHeadExg(EmsHeadH2kExg exg)
			throws DataAccessException {
		this.dbmanualDeclareDao.saveEmsHeadExg(exg);
		return exg;
	}

	public EmsHeadH2kFas saveEmsHeadH2kFas(EmsHeadH2kFas head)
			throws DataAccessException {
		this.dbmanualDeclareDao.saveEmsHeadH2kFas(head);
		return head;
	}

	public CheckHead saveCheckHead(CheckHead head) throws DataAccessException {
		this.dbmanualDeclareDao.saveCheckHead(head);
		return head;
	}

	public FptAppHead saveCustomsEnvelopRequestBill(
			FptAppHead head) throws DataAccessException {
		this.dbmanualDeclareDao.saveCustomsEnvelopRequestBill(head);
		return head;
	}

//	public CustomsEnvelopBill saveCustomsEnvelopBill(CustomsEnvelopBill head)
//			throws DataAccessException {
//		this.dbmanualDeclareDao.saveCustomsEnvelopBill(head);
//		return head;
//	}

	public FptBillHead saveTransferFactoryBill(FptBillHead head)
			throws DataAccessException {
		this.dbmanualDeclareDao.saveTransferFactoryBill(head);
		return head;
	}

	public FptInitBill saveTransferFactoryInitBill(
			FptInitBill head) throws DataAccessException {
		this.dbmanualDeclareDao.saveTransferFactoryInitBill(head);
		return head;
	}

	public AclGroup saveAclGroup(AclGroup head) throws DataAccessException {
		this.dbmanualDeclareDao.saveAclGroup(head);
		return head;
	}

	public AclUser getAclUser(String loginName, Company company) {
		return this.dbmanualDeclareDao.getAclUser(loginName, company);
	}

	public void saveObject(Object obj) {
		this.dbmanualDeclareDao.saveObject(obj);
	}

	/**
	 * @return Returns the dbCustomDao.
	 */
	public DbCustomDao getDbCustomDao() {
		return dbCustomDao;
	}

	/**
	 * @param dbCustomDao
	 *            The dbCustomDao to set.
	 */
	public void setDbCustomDao(DbCustomDao dbCustomDao) {
		this.dbCustomDao = dbCustomDao;
	}

	public District getDistrict(String code) {
		return this.dbCustomDao.getDistrict(code);
	}

	public Curr getCurr(String code) {
		return this.dbCustomDao.getCurr(code);
	}

	public CoType getCoType(String code) {
		return this.dbCustomDao.getCoType(code);
	}

	public Customs getCustoms(String code) {
		return this.dbCustomDao.getCustoms(code);
	}

	public RedDep getRedDep(String code) {
		return this.dbCustomDao.getRedDep(code);
	}

	public MachiningType getMachiningType(String code) {
		return this.dbCustomDao.getMachiningType(code);
	}

	public PayMode getPayMode(String code) {
		return this.dbCustomDao.getPayMode(code);
	}

	public LevyMode getLevyMode(String code) {
		return this.dbCustomDao.getLevyMode(code);
	}

	public LevyKind getLevyKind(String code) {
		return this.dbCustomDao.getLevyKind(code);
	}

	public ApplicationMode getApplicationMode(String code) {
		return this.dbCustomDao.getApplicationMode(code);
	}

	public CoType getCoTypeByName(String name) {
		return this.dbCustomDao.getCoTypeByName(name);
	}

	public Customs getCustomsByName(String name) {
		return this.dbCustomDao.getCustomsByName(name);
	}

	public WareSet getWareSet(String code) {
		return this.dbCustomDao.getWareSet(code);
	}

	public Country getCountry(String code) {
		return this.dbCustomDao.getCountry(code);
	}

	public void deleteDbAll() {
		this.dbmanualDeclareDao.deleteDbAll();
	}

	public void deleteObjectAll(String className) {
		this.dbmanualDeclareDao.deleteObjectAll(className);
	}

	public void editVersion(String className,String version) {
		this.dbmanualDeclareDao.editVersion(className,version);
	}

	// 征免性质
	public void SaveLevyKind(LevyKind levyKind) {
		this.dbimportDao.SaveLevyKind(levyKind);
	}

	public LevyKind FindLevyKind(String sCode, String sName) {
		return this.dbimportDao.FindLevyKind(sCode, sName);
	}

	public ClassList findClassListById(String id) {
		return this.dbimportDao.findClassListById(id);
	}

	
	
	
	
	/*
	 * 保存海关帐单据类型
	 */
	public void saveBillType(BillType billType) {
		this.dbCasDao.saveBillType(billType);
	}

	/*
	 * 保存海关帐单据类型
	 */
	public void saveBillType(List billTypeList) {
		this.dbCasDao.saveBillType(billTypeList);
	}
	
	
	/*
	 * 保存海关帐单据
	 */	
	public void saveBillMaster(BillMaster billMaster){
		this.dbCasLogic.saveBillMaster(billMaster);
	}	
	
	
	
	
	/*
	 * 保存海关帐单据
	 */	
	public void saveBillMaster(List billMasterList){
		this.dbCasLogic.saveBillMaster(billMasterList);		
	}
	
	
	
	/*
	 * 保存海关帐单据明细
	 */	
	public void saveBillDetail(BillDetail billDetail){
		this.dbCasDao.saveBillDetail(billDetail);
	}
		
	
	
	/*
	 * 保存海关帐单据明细
	 */	
	public void saveBillDetail(List billDetailList){
		this.dbCasDao.saveBillDetail(billDetailList);	
	}
	/**
	 * find all complex
	 * @param sValue
	 * @return
	 */
	public List FindAllComplex(){
		return this.dbimportDao.FindAllComplex();
	}
	
	
	/**
	 * 获得电子帐册物料对象来自海关商品编码
	 * @param complexCode
	 * @return
	 */
	public EmsHeadH2kImg findEmsHeadH2kImgByComplexCode(String complexCode) {
		return this.dbimportDao.findEmsHeadH2kImgByComplexCode(complexCode);
	}
	
	

	/**
	 * 获得电子帐册成品对象来自海关商品编码
	 * @param complexCode
	 * @return
	 */
	public EmsHeadH2kExg findEmsHeadH2kExgByComplexCode(String complexCode) {
		return this.dbimportDao.findEmsHeadH2kExgByComplexCode(complexCode);
	}
	

	/**
	 * @return Returns the dbBcsLogic.
	 */
	public DbBcsLogic getDbBcsLogic() {
		return dbBcsLogic;
	}
	/**
	 * @param dbBcsLogic The dbBcsLogic to set.
	 */
	public void setDbBcsLogic(DbBcsLogic dbBcsLogic) {
		this.dbBcsLogic = dbBcsLogic;
	}

}
