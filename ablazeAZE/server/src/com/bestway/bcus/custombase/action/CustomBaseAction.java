/*
 * Created on 2004-6-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.action;

import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PortInternal;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.CustomsBroker;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 海关基础资料服务器端接口
 * 
 * @author 001 // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public interface CustomBaseAction {
	/**
	 * 根据实体类型和ID 抓出实体对象
	 * 
	 * @param cls
	 * @param id
	 * @return
	 */
	Object load(Class entityClass, String id);

	/**
	 * 删除加工种类
	 */
	void DeleteWrap(Wrap obj);

	// 返回加工种类
	List findWrap();

	/**
	 * 返回报关行
	 */
	List findCustomsBroker();

	/**
	 * 保存加工种类
	 */
	Wrap SaveWrap(Wrap obj);

	/**
	 * 保存计量单位
	 */
	Unit SaveUnit(Unit obj);

	/**
	 * 保存繁转简
	 * 
	 * @param obj
	 * @return
	 */
	Gbtobig SaveGbtobig(Gbtobig obj);

	/*
	 * 简繁体对照表
	 */

	public List findGbtobig(String sFields, String sValue);

	boolean isReGbtobig(String code, String scode);

	Transf findTransf();

	// 判断加工种类编号是否重复
	boolean isReMerger(String code, String scode);

	/**
	 * 投资方式
	 */
	List findInvestMode(String fieldName, String fieldValue);

	/**
	 * 企业性质
	 */
	List findCoType(String fieldName, String fieldValue);

	/**
	 * 地区代码
	 */
	List findDistrict(String fieldName, String fieldValue);

	/**
	 * 投资类型
	 */
	List findInvClass(String fieldName, String fieldValue);

	/**
	 * 加工种类
	 */
	List findMachiningType(String fieldName, String fieldValue);

	/**
	 * 申报/报关方式
	 */
	List findDMode(String fieldName, String fieldValue);

	/**
	 * 海关注册公司
	 */
	List findBrief(String fieldName, String fieldValue);

	List<Object> findPageBriefList(Request request, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 获得总条数
	 */
	Long findPageBriefCount(String property, Object value, boolean isLike);

	/**
	 * 征免方式
	 */
	List findLevyMode(String sFields, String sValue);

	/**
	 * 征免性质
	 */
	List findLevyKind(String sFields, String sValue);

	/**
	 * 找到国家
	 * 
	 * @param sfields
	 * @param svalue
	 * @return list
	 */
	List findCountry(String sfields, String svalue);

	List findDeduc(String sFields, String sValue);

	/**
	 * 报关行
	 */
	List findCustomsBroker(String sFields, String sValue);

	List findBrokerCorp(String sFields, String sValue);

	/**
	 * 找出合同类型
	 */
	public List findBargainType(String sFields, String sValue);

	/**
	 * 找出客户 (non-Javadoc)
	 * 
	 * @see com.bestway.common.custombase.countrycode.action.Action#getcustoms(int,
	 *      int, java.lang.String, java.lang.String)
	 */
	List findCustoms(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.countrycode.action.Action#getport_internal
	 * (int, int, java.lang.String, java.lang.String)
	 */
	List findPortInternal(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.countrycode.action.Action#getport_lin(int,
	 * int, java.lang.String, java.lang.String)
	 */
	List findPortLin(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.countrycode.action.Action#getpre_dock(int,
	 * int, java.lang.String, java.lang.String)
	 */List findPreDock(String sfields, String svalue);

	List findRedDep(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.depcode.action.Action#getSAIC_CODE(int,
	 * int)
	 */
	List findSaicCode(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.depcode.action.Action#getBaseCodeSTS_CODE
	 * (int, int)
	 */
	List findStsCode(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.custombase.depcode.action.Action#getTAX_CODE(int,
	 * int)
	 */
	List findTaxCode(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.custombase.hscode.action.Action#getComplex(int,
	 * int, java.lang.String, java.lang.String)
	 */
	List findComplex(String sfields, String svalue);

	/**
	 * 查询商品编码
	 * 
	 * @return
	 */
	List findCustomsComplexNotInComplex();

	/**
	 * 分页条件查询商品编码
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	List findCustomsComplexNotInComplex(int index, int length, String property,
			Object value, boolean isLike);

	List findCustomsComplex(int index, int length, String property,
			Object value, boolean isLike);

	/**
	 * 保存商品编码
	 * 
	 * @param complex
	 */
	List saveComplex(List list);

	Complex saveComplex(Complex complex);

	/**
	 * 检察是否有重复的自用商品编码
	 * 
	 * @param code
	 *            商品编码
	 * @return 如果有重复则返回是，否则反回否
	 */
	boolean checkComplexCode(String code);

	/**
	 * 删除商品编码
	 * 
	 * @param complex
	 */
	void deleteComplex(Complex complex);

	/**
	 * 禁用商品编码
	 * 
	 * @param complex
	 */
	List disableComplex(List list);

	/**
	 * 启用商品编码
	 * 
	 * @param complex
	 */
	List enableComplex(List list);

	/**
	 * 删除商品编码
	 * 
	 * @param complex
	 */
	void deleteComplex(List list);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.hscode.action.Action#getLicensenote(int,
	 * int, java.lang.String, java.lang.String)
	 */
	List findLicensenote(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getConta_model
	 * (int, int, java.lang.String, java.lang.String)
	 */
	List findContaModel(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getConta_size
	 * (int, int, java.lang.String, java.lang.String)
	 */
	List findContaSize(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getCurr(int,
	 * int, java.lang.String, java.lang.String)
	 */
	List findCurr(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getLicensedocu
	 * (int, int, java.lang.String, java.lang.String)
	 */
	List findLicensedocu(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getPay_mode
	 * (int, int, java.lang.String, java.lang.String)
	 */List findPayMode(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getPay_type
	 * (int, int, java.lang.String, java.lang.String)
	 */List findPayType(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getPayer_type
	 * (int, int, java.lang.String, java.lang.String)
	 */List findPayerType(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getSrt_jzx(int,
	 * int, java.lang.String, java.lang.String)
	 */List findSrtJzx(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getSrt_tj(int,
	 * int, java.lang.String, java.lang.String)
	 */List findSrtTj(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getTrade(int,
	 * int, java.lang.String, java.lang.String)
	 */List findTrade(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getTransac(int,
	 * int, java.lang.String, java.lang.String)
	 */List findTransac(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getTransf(int,
	 * int, java.lang.String, java.lang.String)
	 */List findTransf(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getUnit(int,
	 * int, java.lang.String, java.lang.String)
	 */List findUnit(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getUses(int,
	 * int, java.lang.String, java.lang.String)
	 */List findUses(String sfields, String svalue);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getWrap(int,
	 * int, java.lang.String, java.lang.String)
	 */List findWrap(String sfields, String svalue);

	/**
	 * 结汇方式
	 */
	List findBalanceMode(String sFields, String sValue);

	/**
	 * 引进方式
	 */
	List findFetchInMode(String sFields, String sValue);

	public Complex findComplexByCode(String sValue);

	/**
	 * 检查浏览基础代码权限
	 * 
	 * @param request
	 */

	void checkBaseCodeAuthority(Request request);

	/**
	 * 检查浏览部门代码权限
	 * 
	 * @param request
	 */
	void checkBaseCodeDepAuthority(Request request);

	/**
	 * 检查浏览国家地区权限
	 * 
	 * @param request
	 */
	void checkBaseCodeCountryAuthority(Request request);

	/**
	 * 检查浏览报关参数权限
	 * 
	 * @param request
	 */
	void checkBaseCodeParameterAuthority(Request request);

	/**
	 * 检查浏览商品编码权限
	 * 
	 * @param request
	 */
	void checkBaseCodeHsAuthority(Request request);

	public void updateCustoms(Request request, DBDataRoot db);

	public String getLicenseDouName(String sValue);

	public List findComplexByType(String type);

	public List findCustomsComplex();

	/**
	 * 分页条件查询商品编码(不包括禁用记录)
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findComplex(Request request, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 分页条件查询商品编码(全部记录)
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findComplexAll(Request request, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 分页条件查询对应关系中的设备
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List getMachine(Request request, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 分页条件查询Bcs报关商品资料
	 * 
	 */
	public List findComplexBcsTenInnerMerge(Request request, int index,
			int length, String property, Object value, boolean isLike,
			boolean isImport);

	/**
	 * 是否有四码存在
	 * 
	 * @param fourCode
	 * @return
	 */
	long findComplexLikeFourCodeCount(String fourCode);

	public List findCustomsDocuName(String code);

	/**
	 * 查询商品自用编码
	 * 
	 * @return
	 */
	List findCustomsComplexNotInComplex1();

	// List findUpdateComplex(List list1);

	List findComplexNotInCustomsComplex();

	List findComplexNotInCustomsComplex(int index, int length, String property,
			Object value, boolean isLike);

	// 更新商品编码表
	public List findChangedComplex(Request request);

	public List updateComplex(Request request, List list);

	public Country findCountryByName(Request request, String name);

	public List findCountry(Request request);

	public Country findCountryByCode(Request request, String code);

	public ScmCoc findScmCocByCode(Request request, String code,
			Boolean isCustomer, Boolean isManufacturer);

	public ScmCoc findScmCocByName(Request request, String name,
			Boolean isCustomer, Boolean isManufacturer);

	public List findScmCoc(Request request, Boolean isCustomer,
			Boolean isManufacturer);

	public Curr findCurrByName(Request request, String name);

	public Curr findCurrByCode(Request request, String code);

	public List findCurr(Request request);

	/**
	 * 找到所有的商品编码
	 * 
	 * @param request
	 *            请求控制
	 * @return list
	 */
	public List findComplexAll(Request request);

	/**
	 * 找到名称、规格限定长度
	 * 
	 * @param request
	 * @return
	 */
	public List findBytesLength(Request request);

	/**
	 * 找到是否名称、规格限定长度
	 * 
	 * @param request
	 * @return
	 */
	public List findIsControlLength(Request request);

	public List saveCoutryAsChecked(Request request, List list, boolean isCheck);

	public List addCheckupComplex(Request request, List list, Integer flag);

	public List findComplexForCheckupNotIn(Request request, Integer impexpfalg);

	public List findComplexByFlag(Request request, Integer impexpfalg);

	public void deleteObjects(Request request, List list);

	public List findUpdateComplex(Request request);

	public List findAllCustomsDeclaretion(Request request);

	public void saveOrUpdateObject(Request request, Object obj);

	/**
	 * 根据海关商品编码生成自用商品编码
	 * 
	 * @param code
	 * @return
	 */
	public Complex makeComplexFromCustomsComplex(Request request, String code);

	/**
	 * 找出所有的仓库编码类型
	 * 
	 * @param request
	 * @return
	 */
	public List findWareSet(Request request);

	public List findTransferMode();

	/**
	 * 查找其他参数设置
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CompanyOther型，其他参数设置资料
	 * @author sxk
	 */
	List findCompanyOther(Request request);

	/**
	 * 查询BCS参数
	 * 
	 * @param parameter
	 */
	public BcsParameterSet findBcsParameterSet(Request request);

	/**
	 * 保存商检商品
	 * 
	 * @param complex
	 */
	public void saveCheckupComplex(Request request, Complex complex);

	boolean isReDgBriefCode(String data);

	boolean isReDgBriefName(String name);

	Brief SaveBrief(Brief brief);

	void SaveBriefs(List<Brief> list);

	Customs SaveCustom(Customs custom);

	boolean isReDgCustomCode(String data);

	boolean isReDgCustomName(String name);

	PortInternal SavePortInternal(PortInternal portInternal);

	PortLin SaveportLin(PortLin portLin);

	boolean isReDgPortInternalCode(String data);

	boolean isReDgOpenPortcode(String data);

	boolean isReDgDgPortInternalName(String name);

	void checkCheckupComplexAuthority(Request request);

	/**
	 * @author Administrator date : Mar 27, 2012 10:38:15 AM
	 * @param preDock
	 * @return
	 */
	PreDock SavePreDock(PreDock preDock);

	boolean isReDgPreDockCode(String data);

	boolean isReDgPreDockName(String name);

	void upgradeCustomsBase();

	String[] upgradeCustomsBase(Request request, String fileName);

	/**
	 * 查询系统参数设置
	 * 
	 * @return
	 */
	CompanyOther findCompanyOther();

	List getMustUpgradeFileName(Map<String, String[]> map);

	/**
	 * 从客户端上传海关基础编码文件到服务器端
	 * 
	 * @param fileContent
	 */
	void uploadCustomsBaseFileToServer(byte[] fileContent, String fileName);

	/**
	 * 从运维平台下载报关行
	 * 
	 * @returns
	 */
	public List<CustomsBroker> downCustomsBrokerFormbgcs();

	/**
	 * 保存报关行
	 * 
	 * @param complex
	 */
	List saveCustomsBroker(List list);

	/**
	 * 删除报关行
	 * 
	 */
	public void deleteCustomsBroker(List list);

	/**
	 * 删除报关行
	 * 
	 */
	public void deleteCustomsBroker(CustomsBroker customsBroker);
}