/*
 * Created on 2004-12-28
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
import com.bestway.bcus.dbimport.entity.DBDataConnect;
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
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface DbimportAction {
	/*
	 * 通用删除数据方法
	 */
	 public abstract void DelTable(List list);
	 public SrtJzx getSrtJzx(String code);
	 public SrtTj getSrtTj(String code);
	/*
	 * 通用查询数据方法
	 */
	public abstract List FindTable(String module);
	public Unit Findbynameunit(String sValue);
	public void onceDelTable(String module);
	public void SaveLevyMode(LevyMode levyMode);
	public void SaveLevyKind(LevyKind levyKind);
	public void deleteAllCompany();
	public ClassList SaveClassList(ClassList classList);
	public ClassList findClassListById(String id);
	public FieldList SaveFieldList(FieldList fieldList);
	public String FindBriefCodeByName(String sValue);
	/*
	 * 根据编码查询计量方式
	 */public abstract Unit Findbyidunit(String sValue);

	 public void saveDistrict(District district);
	/*
	 * 根据系统登录公司名称查询公司//客户端程序要加一个窗体用来选择公司
	 */public abstract Company Findbynamecompany(String sValue);

	/*
	 *查询得到所有的公司 
	 */public abstract List Findallcompany();

	/*
	 * 根据Delphi版系统中提供的关务海关注册公司的编号查询得到brief实体对象
	 */public abstract Brief Findbycodebrief(String sValue);

	/*
	 * 根据Delphi版系统中提供的关务海关注册公司的名称查询得到brief实体对象
	 */public Brief Findbynamebrief(String sValue);
	 
	/*
	 * 根据物料类别编号查询[物流]物料类别ScmCoi
	 */public abstract ScmCoi Findbycodescmcoi(String sValue);

	/*
	 * 根据编号查询商品类别Complex
	 */public abstract Complex Findbycodecomplex(String sValue);

	/*
	 * 根据编号查询计量单位CalUnit
	 */public abstract CalUnit Findbycodecalunit(String sValue);

	/*
	 * 根据名称查询客户供应商资料ScmCoc
	 */public abstract ScmCoc Findbynamescmcoc(String sValue);
	 
	 /*
	  * 根据代号查询客户供应商资料ScmCoc
	  */public ScmCoc Findbycodescmcoc(String sValue);
	 	
	 
	/*
	 * 根据编号查询国家代码SysArea
	 */public abstract SysArea Findbycodesysarea(String sValue);
	 
	/*
	 * 根据编号查询国家地区Country
	 */public Country Findbycodecountry(String sValue);

	/*
	 * 根据名称查询国家地区Country
	 */public Country Findbynamecountry(String sValue);
	 
	 
	/*
	 * 根据编号查询税制代码ShareCode
	 */public abstract ShareCode Findbycodesharecode(String sValue);

	/*
	 *根据料号查询物料主档Materiel 
	 */public Materiel Findbyptnomateriel(String sValue);
	 
	 
	/*
	 * 根据代号查询得到仓库设置WareSet
	 */public WareSet Findbycodewareset(String sValue);

	 
	/*
	 * 根据代码查询货币代码Curr
	 */public Curr Findbycodecurr(String sValue);
	 
	/*
	 * 根据EMSNO得到EmsHeadH2k
	 */public EmsHeadH2k FindbyemsnoEmsHeadH2k(String sValue);
	 
	/*
	 *	根据loginname得到AclUser 
	 */public AclUser FindbyloginnameAclUser(String sValue);
	 
	 /*
	  * 根据code得到Customs
	  */public Customs Findbycodecustoms(String sValue);
	  
	/*
	 * 根据了解name得到Customs
	 */public Customs Findbynamecustoms(String sValue);
	  
	  
	/*
	 * 根据code得到Transf
	 */public Transf Findbycodetransf(String sValue);  
	 
	/*
	 * 根据name得到Transf
	 */public Transf Findbynametransf(String sValue);
	 
	 
	/*
	 * 根据Code得到Trade
	 */public Trade Findbycodetrade(String sValue);
	 
	/*
	 * 根据name得到Trade
	 */public Trade Findbynametrade(String sValue);
	 
	 
	/*
	 * 根据Code得到计量方式 Unit
	 */public Unit Findbycodeunit(String sValue);
 
	 
	/*
	 * 	根据Code与Name 得到LevyMode
	 */public LevyMode FindLevyMode(String sCode,String sName);
	 
	/*
	 * 根据Code与Name 得到District
	 */public District FindDistrict(String sCode,String sName);
	 
		
	/*
	 * 根据Code与Name 得到PortLin
	 */public PortLin FindPortLin(String sCode,String sName);
	 
	/*
	 * 根据Code与Name 得到Transac
	 */public Transac FindTransac(String sCode,String sName);
		
	/*
	 *  根据Code与Name 得到Wrap
	 */public Wrap FindWrap(String sCode,String sName);
	 
	/*
	 *  根据Code与Name 得到Uses
	 */public Uses FindUses(String sCode,String sName);
	 
	/*
	 * 根据名称(中文或符号)得到Curr
	 */public Curr Findbych_encurr(String sValue1,String sValue2);
		
 
	/*
	 * 根据Code,Name 得到PreDock
	 */public PreDock FindPreDock(String sCode,String sName);
	 
	/*
	 *  根据Code,Name 得到BalanceMode
	 */public BalanceMode FindBalanceMode(String sCode,String sName);
	 
	/*
	 * 查询得到CustomsDeclaration
	 */public CustomsDeclaration FindCustomsDeclaration(String emsHeadH2k,Integer serialNumber,Integer impExpFlag);
	 
	/*
	 * 查询得到ApplyToCustomsBillList
	 */public  ApplyToCustomsBillList FindApplyToCustomsBillList(String listno);
			 
	 
	/************************************************公共方法结束************************/
	 
	public abstract List FindCotype();

	public abstract void DelCotype(List list);

	public abstract void SaveCotype(CoType Cotype);

	/*
	 *  投资类型invclass->INV_CLASS
	 */public abstract List FindInvclass();

	public abstract void DelInvclass(List list);

	public abstract void SaveInvclass(InvClass invClass);

	/*
	 * 
	 * @author Administrator
	 *投资方式investmode->INVEST_MODE
	 */public abstract List FindInvestmode();

	public abstract void DelInvestmode(List list);

	public abstract void SaveInvestmode(InvestMode investMode);

	/*
	 * 
	 * @author Administrator
	 *
	 * 加工种类MachiningType->hd_jgzl
	 */public abstract List FindMachiningType();

	public abstract void DelMachiningType(List list);

	public abstract void SaveMachiningType(MachiningType machiningType);

	/*
	 * 
	 * @author Administrator
	 *
	 * 申报报关方式applicationMode->D_MODE
	 */public abstract List FindapplicationMode();

	public abstract void DelapplicationMode(List list);

	public abstract void SaveApplicationMode(ApplicationMode applicationMode);

	/*
	 * 
	 * @author Administrator
	 *
	 * 海关注册公司brief->brief
	 * 
	 */public abstract List Findbrief();

	public abstract void Delbrief(List list);

	public abstract void Savebrief(Brief brief);

	/*
	 * 
	 * @author Administrator
	 *税务部门TaxCode->TAX_CODE
	 */public abstract List FindtaxCode();

	public abstract void DeltaxCode(List list);

	public abstract void SavetaxCode(TaxCode taxCode);

	/*
	 * 
	 * @author Administrator
	 *
	 *工商行政SaicCode->SAIC_CODE
	 */public abstract List FindsaicCode();

	public abstract void DelsaicCode(List list);

	public abstract void SavesaicCode(SaicCode saicCode);

	/*
	 * 
	 * @author Administrator
	 *
	 *技术监督StsCode->STS_CODE
	 */public abstract List FindstsCode();

	public abstract void DelstsCode(List list);

	public abstract void SavestsCode(StsCode stsCode);

	/*
	 * 
	 * @author Administrator
	 *
	 *外经贸部门RedDep->RED_DEP
	 */public abstract List FindredDep();

	public abstract void DelredDep(List list);

	public abstract void SaveredDep(RedDep redDep);

	/*----------------------------------------------------------------------------------
	 * 
	 * @author Administrator
	 *国家地区
	 */public abstract List Findcountry();

	public abstract void Delcountry(List list);

	public abstract void Savecountry(Country country);

	/*
	 * 
	 * @author Administrator
	 *
	 * 海关关区Customs->Customs
	 */public abstract List Findcustoms();

	public abstract void Delcustoms(List list);

	public abstract void Savecustoms(Customs customs);

	/*
	 * 
	 * @author Administrator
	 *
	 *国内进出口口岸PortInternal->Port_internal
	 */public abstract List FindportInternal();

	public abstract void DelportInternal(List list);

	public abstract void SaveportInternal(PortInternal portInternal);

	/*
	 * 
	 * @author Administrator
	 *
	 * 国际进出口港口PortLin->Port_lin
	 */public abstract List FindportLin();

	public abstract void DelportLin(List list);

	public abstract void SaveportLin(PortLin portLin);

	/*
	 * 
	 * @author Administrator
	 *
	 *国内进出口码头PreDock->Pre_dock
	 */public abstract List FindpreDock();

	public abstract void DelpreDock(List list);

	public abstract void SavepreDock(PreDock preDock);

	/*----------------------------------------------------------------------------------
	 * 
	 * @author Administrator
	 * 报关参数
	 */public abstract List Findtrade();

	public abstract void Savetrade(Trade trade);

	/*
	 *对Unit操作 
	 */public abstract List FindUnit();

	public abstract void SaveUnit(Unit unit);

	/*
	 *货币代码 Curr->curr 
	 */public abstract List Findcurr();

	public abstract void Savecurr(Curr curr);

	/*
	 * 证件代码LicenseDocu->LICENSEDOCU
	 */public abstract List FindlicenseDocu();

	public abstract void SavelicenseDocu(LicenseDocu licenseDocu);

	/*
	 * 运输方式Transf->TRANSF
	 */public abstract List Findtransf();

	public abstract void Savetransf(Transf transf);

	/*
	 *成交方式Transac->TRANSAC 
	 */public abstract List Findtransac();

	public abstract void Savetransac(Transac transac);

	/*
	 *保税方式PayMode->PAY_MODE 
	 */public abstract List FindpayMode();

	public abstract void SavepayMode(PayMode payMode);

	/*
	 *包装种类Wrap->hd_baozhuang
	 */public abstract List Findwrap();

	public abstract void Savewrap(Wrap wrap);

	/*
	 * 用途代码Uses->USE_TO
	 */public abstract void Saveuses(Uses uses);

	/*
	 * 集装箱代码SrtJzx->SRT_JZX
	 */public abstract void SavesrtJzx(SrtJzx srtJzx);

	/*
	 * 集装箱规格ContaModel->CONTA_MODEL
	 */public abstract void SavecontaModel(ContaModel contaModel);

	/*
	 *集装箱尺寸ContaSize->CONTA_SIZE
	 */public abstract void SavecontaSize(ContaSize contaSize);

	/*
	 * 集装箱托架种类SrtTj->SRT_TJ
	 */public abstract void SavesrtTj(SrtTj srtTj);

	/*
	 * 付款类型PayType->PAY_TYPE
	 */public abstract void SavepayType(PayType payType);

	/*
	 * 付款者类型PayerType-> PAYER_TYPE
	 */public abstract void SavepayerType(PayerType payerType);

	/*
	 *商品类别Complex->complex 
	 */public abstract void Savecomplex(Complex complex);

	/*
	 * 领证商品备注表LicenseNote->LicenseNote
	 */public abstract void SavelicenseNote(LicenseNote licenseNote);

	/*
	 * 导入公司数据Company->sys_cmp
	 */public abstract void Savecompany(Company company);

	/*
	 * 仓库设置WareSet->scm_whs
	 */public abstract void SavewareSet(WareSet wareSet);

	/*
	 *计量单位CalUnit->select * from sys_ccd where ccd_field='LSA_UM' 
	 */public abstract void SavecalUnit(CalUnit calUnit);

	/*
	 * 币别设置CurrCode->sys_curr
	 */public abstract void SavecurrCode(CurrCode currCode);

	/*
	 *国家代码SysArea->select * from sys_zone where zone_level=2 
	 */public abstract void SavesysArea(SysArea sysArea);

	/*
	 * 客户供应商资料ScmCoc->scm_cut
	 */public abstract void SavescmCoc(ScmCoc scmCoc);

	/*
	 *物料类别ScmCoi->scm_coi
	 */public abstract void SavescmCoi(ScmCoi scmCoi);

	/*
	 *税制代码ShareCode->sys_tax //加一个税制代码taxation
	 */public abstract void SaveshareCode(ShareCode shareCode);

	/*
	 *物料主档Materiel->scm_ptm
	 */public abstract void Savemateriel(Materiel materiel);
	 
	/*
	 *内部归并  InnerMergeData->bcp_imr
	 */public void Saveinnermergedata(InnerMergeData innerMergeData);
	 
	 
	/*
	 *进出口申请单主表 ImpExpRequestBill->Invoice_Head
	 */public void SaveimpExpRequestBill(ImpExpRequestBill impExpRequestBill);
	
	/*
	 *进出口申请单从表 ImpExpCommodityInfo->invoice_body
	 */public void SaveimpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo);
		
	
	/*
	 *报关清单维护ApplyToCustomsBillList->EDI_CL_HEAD 
	 */public void SaveapplyToCustomsBillList(ApplyToCustomsBillList applyToCustomsBillList);
	
 
	/*
	 * 归并后商品信息AtcMergeAfterComInfo->EDI_CL_LIST
	 */public void SaveatcMergeAfterComInfo(AtcMergeAfterComInfo atcMergeAfterComInfo);

	 
	/*
	 * 归并前商品信息(可修改)  AtcMergeBeforeComInfo->EDI_CL_LIST
	 */public void SaveAtcMergeBeforeComInfo(AtcMergeBeforeComInfo atcMergeBeforeComInfo);

		 
	/*
	 * 进口报关单CustomsDeclaration->hg_import_dd
	 */public CustomsDeclaration SaveCustomsDeclaration(CustomsDeclaration customsDeclaration);
	
	/*
	 * 进口报关单(明细表)CustomsDeclarationCommInfo->hg_import_dddetails
	 */public void SaveCustomsDeclarationCommInfo(CustomsDeclarationCommInfo customsDeclarationCommInfo);
	

	public abstract List findDbConnect();
	public abstract void saveDBDataConnect(DBDataConnect db);
	public EmsEdiTrHead saveEmsEdiTrHead(EmsEdiTrHead head) throws DataAccessException;
	public EmsEdiMergerHead saveEmsMergerHead(EmsEdiMergerHead head) throws DataAccessException;
	public EmsEdiMergerImgAfter saveEmsMergerImgAfter(EmsEdiMergerImgAfter imgAfter) throws DataAccessException;
	public EmsEdiMergerExgAfter saveEmsMergerExgAfter(EmsEdiMergerExgAfter exgAfter) throws DataAccessException;
	public EmsEdiMergerExgBefore saveEmsMergerExgBefore(EmsEdiMergerExgBefore exgBefore) throws DataAccessException;
	public EmsHeadH2k saveEmsHead(EmsHeadH2k head) throws DataAccessException;
	public EmsHeadH2kExg saveEmsHeadExg(EmsHeadH2kExg exg) throws DataAccessException;
	public EmsHeadH2kVersion saveEmsHeadH2kVersion(EmsHeadH2kVersion version) throws DataAccessException;
	public EmsHeadH2kFas saveEmsHeadH2kFas(EmsHeadH2kFas head) throws DataAccessException;
	public CheckHead saveCheckHead(CheckHead head) throws DataAccessException;
	public FptAppHead saveCustomsEnvelopRequestBill(FptAppHead head) throws DataAccessException;
//	public CustomsEnvelopBill saveCustomsEnvelopBill(CustomsEnvelopBill head) throws DataAccessException;
	public FptBillHead saveTransferFactoryBill(FptBillHead head) throws DataAccessException;
	public FptInitBill saveTransferFactoryInitBill(FptInitBill head) throws DataAccessException;
	public AclGroup saveAclGroup(AclGroup head) throws DataAccessException;
	public AclUser getAclUser(String loginName,Company company);
	public void saveObject(Object obj);
	public District getDistrict(String code);
	public Curr getCurr(String code);
	public CoType getCoType(String code);
	public CoType getCoTypeByName(String name);
	public Customs getCustoms(String code);
	public RedDep getRedDep(String code);
	public LevyMode getLevyMode(String code);
	public PayMode getPayMode(String code);
	public MachiningType getMachiningType(String code);
	public ApplicationMode getApplicationMode(String code);
	public Customs getCustomsByName(String name);
	public WareSet getWareSet(String code);
	public Country getCountry(String code);
	public void deleteDbAll();
	public void deleteObjectAll(String className);
	public void editVersion(String className,String version);
	public LevyKind getLevyKind(String code);
	public LevyKind FindLevyKind(String sCode,String sName);
	public void SaveBom(EnterpriseBomManage bom);
	public String Findbynamecomplex(String sValue);

	
	
	
	
	

	/*
	 * 保存海关帐单据类型
	 */
	public void saveBillType(BillType billType);

	/*
	 * 保存海关帐单据类型
	 */
	public void saveBillType(List billTypeList);
	
	/*
	 * 保存海关帐单据
	 */	
	void saveBillMaster(BillMaster billMaster);
	
	
	
	/*
	 * 保存海关帐单据
	 */	
	void saveBillMaster(List billMasterList);
	
	
	/*
	 * 保存海关帐单据明细
	 */	
	void saveBillDetail(BillDetail billDetail);
		
	
	
	/*
	 * 保存海关帐单据明细
	 */	
	void saveBillDetail(List billDetailList);
	public List FindAllComplex();
	
	
	/**
	 * 获得电子帐册物料对象来自海关商品编码
	 * @param complexCode
	 * @return
	 */
	EmsHeadH2kImg findEmsHeadH2kImgByComplexCode(String complexCode);
	
	

	/**
	 * 获得电子帐册成品对象来自海关商品编码
	 * @param complexCode
	 * @return
	 */
	EmsHeadH2kExg findEmsHeadH2kExgByComplexCode(String complexCode);
	
	public BalanceMode FindByName(String sValue);
	
	
}