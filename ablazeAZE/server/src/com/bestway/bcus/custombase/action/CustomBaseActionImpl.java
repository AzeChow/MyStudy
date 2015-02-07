/*
 * Created on 2004-6-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.action;

import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.custombase.dao.BaseCodeDao;
import com.bestway.bcus.custombase.dao.CountryCodeDao;
import com.bestway.bcus.custombase.dao.CustomBaseVersionDao;
import com.bestway.bcus.custombase.dao.DepCodeDao;
import com.bestway.bcus.custombase.dao.HsCodeDao;
import com.bestway.bcus.custombase.dao.ParameterCodeDao;
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
import com.bestway.bcus.custombase.logic.CustomBaseLogic;
import com.bestway.bcus.custombase.logic.HandUpdateCustomsLogic;
import com.bestway.bcus.custombase.logic.UpdateBase;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
@AuthorityClassAnnotation(caption = "海关基础资料", index = 1.1)
public class CustomBaseActionImpl extends BaseActionImpl implements
		CustomBaseAction {
	private BaseCodeDao baseCodeDao = null;

	private CountryCodeDao countryCodeDao = null;

	private DepCodeDao depCodeDao = null;

	private HsCodeDao hsCodeDao = null;

	private ParameterCodeDao parameterCodeDao = null;

	private CustomBaseVersionDao customBaseVersionDao = null;

	private UpdateBase updateBase = null;

	private CustomBaseLogic customBaseLogic = null;

	private HandUpdateCustomsLogic handUpdateCustomsLogic = null;

	public HandUpdateCustomsLogic getHandUpdateCustomsLogic() {
		return handUpdateCustomsLogic;
	}

	public void setHandUpdateCustomsLogic(
			HandUpdateCustomsLogic handUpdateCustomsLogic) {
		this.handUpdateCustomsLogic = handUpdateCustomsLogic;
	}

	public CustomBaseVersionDao getCustomBaseVersionDao() {
		return customBaseVersionDao;
	}

	public void setCustomBaseVersionDao(
			CustomBaseVersionDao customBaseVersionDao) {
		this.customBaseVersionDao = customBaseVersionDao;
	}

	/**
	 * 根据实体类型和ID 抓出实体对象
	 * 
	 * @param cls
	 * @param id
	 * @return
	 */
	public Object load(Class entityClass, String id) {
		return this.baseCodeDao.load(entityClass, id);
	}

	/**
	 * 更新海关基本资料
	 * 
	 * @param db
	 */
	public void updateCustoms(Request request, DBDataRoot db) {
		this.updateBase.updateCustoms(db);
	}

	/*
	 * 简繁体对照表
	 */

	public List findGbtobig(String sFields, String sValue) {
		return baseCodeDao.findGbtobig(sFields, sValue);
	}

	/**
	 * 投资方式
	 */

	public List findInvestMode(String fieldName, String fieldValue) {
		return baseCodeDao.findInvestMode(fieldName, fieldValue);
	}

	/**
	 * 地区代码
	 */
	public List findDistrict(String fieldName, String fieldValue) {
		return countryCodeDao.findDistrict(fieldName, fieldValue);
	}

	/**
	 * 企业性质
	 */
	public List findCoType(String fieldName, String fieldValue) {
		return baseCodeDao.findCoType(fieldName, fieldValue);
	}

	/**
	 * 投资类型
	 */
	public List findInvClass(String fieldName, String fieldValue) {
		return baseCodeDao.findInvClass(fieldName, fieldValue);
	}

	/**
	 * 加工种类
	 */

	public List findMachiningType(String fieldName, String fieldValue) {
		return baseCodeDao.findMachiningType(fieldName, fieldValue);
	}

	/**
	 * 申报/报关方式
	 */
	public List findDMode(String fieldName, String fieldValue) {
		return baseCodeDao.getDMode(fieldName, fieldValue);
	}

	/**
	 * 海关注册公司
	 */
	public List findBrief(String fieldName, String fieldValue) {
		return baseCodeDao.getBrief(fieldName, fieldValue);
	}

	/**
	 * 海关注册公司分页
	 */
	@Override
	public List<Object> findPageBriefList(Request request, int index,
			int length, String property, Object value, boolean isLike) {
		System.out.println("CustomBaseAction - Dao");
		return baseCodeDao
				.findPageBrief(index, length, property, value, isLike);
	}

	/**
	 * 获得总条数
	 */
	@Override
	public Long findPageBriefCount(String property, Object value, boolean isLike) {
		System.out.println("CustomBaseAction - Dao");
		return baseCodeDao.findPageBriefCount(property, value, isLike);
	}

	/**
	 * 征免方式
	 */
	public List findLevyMode(String sFields, String sValue) {
		return parameterCodeDao.findLevyMode(sFields, sValue);
	}

	/**
	 * 征免性质
	 */
	public List findLevyKind(String sFields, String sValue) {
		return parameterCodeDao.findLevyKind(sFields, sValue);
	}

	/**
	 * 核扣方式
	 */
	public List findDeduc(String sFields, String sValue) {
		return parameterCodeDao.findDeduc(sFields, sValue);
	}

	public List findCountry(String sfields, String svalue) {
		return countryCodeDao.findCountry(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.countrycode.action.Action#getcustoms(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findCustoms(String sfields, String svalue) {
		return countryCodeDao.findCustoms(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.countrycode.action.Action#getport_internal
	 * (int, int, java.lang.String, java.lang.String)
	 */
	public List findPortInternal(String sfields, String svalue) {
		return countryCodeDao.findPortInternal(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.countrycode.action.Action#getport_lin(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findPortLin(String sfields, String svalue) {
		return countryCodeDao.findPortLin(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.countrycode.action.Action#getpre_dock(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findPreDock(String sfields, String svalue) {
		return countryCodeDao.findPreDock(sfields, svalue);
	}

	public List findRedDep(String sfields, String svalue) {
		return depCodeDao.findRedDep(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.depcode.action.Action#getSAIC_CODE(int,
	 * int)
	 */
	public List findSaicCode(String sfields, String svalue) {
		return depCodeDao.findSaicCode(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.depcode.action.Action#getBaseCodeSTS_CODE
	 * (int, int)
	 */
	public List findStsCode(String sfields, String svalue) {
		return depCodeDao.findStsCode(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.custombase.depcode.action.Action#getTAX_CODE(int,
	 * int)
	 */
	public List findTaxCode(String sfields, String svalue) {
		return depCodeDao.findTaxCode(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.custombase.hscode.action.Action#getComplex(int,
	 * int, java.lang.String, java.lang.String)
	 */

	public List findComplex(String sfields, String svalue) {
		return hsCodeDao.findComplex(sfields, svalue);
	}

	/**
	 * 查询商品编码
	 * 
	 * @return
	 */

	public List findCustomsComplexNotInComplex() {
		return hsCodeDao.findCustomsComplexNotInComplex();
	}

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

	public List findCustomsComplexNotInComplex(int index, int length,
			String property, Object value, boolean isLike) {
		return hsCodeDao.findCustomsComplexNotInComplex(index, length,
				property, value, isLike);
	}

	public List findCustomsComplex(int index, int length, String property,
			Object value, boolean isLike) {
		return hsCodeDao.findCustomsComplex(index, length, property, value,
				isLike);

	}

	/**
	 * 保存商品编码
	 * 
	 * @param complex
	 */
	// @AuthorityFunctionAnnotation(caption = "保存商品编码", index = 7)
	public List saveComplex(List list) {
		return this.customBaseLogic.saveComplex(list);
	}

	/**
	 * 保存商品编码
	 * 
	 * @param complex
	 */
	// @AuthorityFunctionAnnotation(caption = "保存商品编码", index = 7)
	public Complex saveComplex(Complex complex) {
		this.hsCodeDao.saveComplex(complex);
		return complex;
	}

	/**
	 * 删除商品编码
	 * 
	 * @param complex
	 */
	// @AuthorityFunctionAnnotation(caption = "删除商品编码", index = 8)
	public void deleteComplex(Complex complex) {
		this.customBaseLogic.deleteComplex(complex);
	}

	/**
	 * 删除商品编码
	 * 
	 * @param complex
	 */
	// @AuthorityFunctionAnnotation(caption = "删除商品编码", index = 8)
	public void deleteComplex(List list) {
		this.customBaseLogic.deleteComplex(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.hscode.action.Action#getLicensenote(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findLicensenote(String sfields, String svalue) {
		return hsCodeDao.findLicensenote(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getConta_model
	 * (int, int, java.lang.String, java.lang.String)
	 */
	public List findContaModel(String sfields, String svalue) {
		return parameterCodeDao.findContaModel(sfields, svalue);
	}

	public List findBargainType(String sFields, String sValue) {
		return parameterCodeDao.findBargainType(sFields, sValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getConta_size
	 * (int, int, java.lang.String, java.lang.String)
	 */
	public List findContaSize(String sfields, String svalue) {
		return parameterCodeDao.findContaSize(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getCurr(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findCurr(String sfields, String svalue) {
		return parameterCodeDao.findCurr(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getLicensedocu
	 * (int, int, java.lang.String, java.lang.String)
	 */
	public List findLicensedocu(String sfields, String svalue) {
		return parameterCodeDao.findLicensedocu(sfields, svalue);
	}

	/**
	 * 证件代码名称
	 * 
	 * @param sFields
	 */
	public String getLicenseDouName(String sValue) {
		return this.parameterCodeDao.getLicenseDouName(sValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getPay_mode
	 * (int, int, java.lang.String, java.lang.String)
	 */
	public List findPayMode(String sfields, String svalue) {
		return parameterCodeDao.findPayMode(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getPay_type
	 * (int, int, java.lang.String, java.lang.String)
	 */
	public List findPayType(String sfields, String svalue) {
		return parameterCodeDao.findPayType(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getPayer_type
	 * (int, int, java.lang.String, java.lang.String)
	 */
	public List findPayerType(String sfields, String svalue) {
		return parameterCodeDao.findPayerType(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getSrt_jzx(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findSrtJzx(String sfields, String svalue) {
		return parameterCodeDao.findSrtJzx(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getSrt_tj(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findSrtTj(String sfields, String svalue) {
		return parameterCodeDao.findSrtTj(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getTrade(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findTrade(String sfields, String svalue) {
		return parameterCodeDao.findTrade(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getTransac(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findTransac(String sfields, String svalue) {
		return parameterCodeDao.findTransac(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getTransf(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findTransf(String sfields, String svalue) {
		return parameterCodeDao.findTransf(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getUnit(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findUnit(String sfields, String svalue) {
		return parameterCodeDao.findUnit(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getUses(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findUses(String sfields, String svalue) {
		return parameterCodeDao.findUses(sfields, svalue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.custombase.parametercode.action.Action#getWrap(int,
	 * int, java.lang.String, java.lang.String)
	 */
	public List findWrap(String sfields, String svalue) {
		return parameterCodeDao.findWrap(sfields, svalue);
	}

	public List findCustomsBroker(String sfields, String svalue) {
		return parameterCodeDao.findCustomsBroker(sfields, svalue);
	}

	public List findBrokerCorp(String sfields, String svalue) {
		return parameterCodeDao.findBrokerCorp(sfields, svalue);
	}

	/**
	 * 结汇方式
	 */
	public List findBalanceMode(String sFields, String sValue) {
		return parameterCodeDao.findBalanceMode(sFields, sValue);
	}

	/**
	 * 引进方式
	 */
	public List findFetchInMode(String sFields, String sValue) {
		return parameterCodeDao.findFetchInMode(sFields, sValue);
	}

	/**
	 * @return Returns the baseCodeDao.
	 */
	public BaseCodeDao getBaseCodeDao() {
		return baseCodeDao;
	}

	/**
	 * @param baseCodeDao
	 *            The baseCodeDao to set.
	 */
	public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
		this.baseCodeDao = baseCodeDao;
	}

	/**
	 * @return Returns the countryCodeDao.
	 */
	public CountryCodeDao getCountryCodeDao() {
		return countryCodeDao;
	}

	/**
	 * @param countryCodeDao
	 *            The countryCodeDao to set.
	 */
	public void setCountryCodeDao(CountryCodeDao countryCodeDao) {
		this.countryCodeDao = countryCodeDao;
	}

	/**
	 * @return Returns the depCodeDao.
	 */
	public DepCodeDao getDepCodeDao() {
		return depCodeDao;
	}

	/**
	 * @param depCodeDao
	 *            The depCodeDao to set.
	 */
	public void setDepCodeDao(DepCodeDao depCodeDao) {
		this.depCodeDao = depCodeDao;
	}

	/**
	 * @return Returns the hsCodeDao.
	 */
	public HsCodeDao getHsCodeDao() {
		return hsCodeDao;
	}

	/**
	 * @param hsCodeDao
	 *            The hsCodeDao to set.
	 */
	public void setHsCodeDao(HsCodeDao hsCodeDao) {
		this.hsCodeDao = hsCodeDao;
	}

	/**
	 * @return Returns the parameterCodeDao.
	 */
	public ParameterCodeDao getParameterCodeDao() {
		return parameterCodeDao;
	}

	/**
	 * @param parameterCodeDao
	 *            The parameterCodeDao to set.
	 */
	public void setParameterCodeDao(ParameterCodeDao parameterCodeDao) {
		this.parameterCodeDao = parameterCodeDao;
	}

	public Complex findComplexByCode(String sValue) {
		return this.hsCodeDao.findComplexByCode(sValue);
	}

	/**
	 * 检查浏览基础代码权限
	 * 
	 * @param request
	 */
	@AuthorityFunctionAnnotation(caption = "浏览基础代码", index = 1)
	public void checkBaseCodeAuthority(Request request) {

	}

	/**
	 * 检查浏览部门代码权限
	 * 
	 * @param request
	 */
	@AuthorityFunctionAnnotation(caption = "浏览部门代码", index = 2)
	public void checkBaseCodeDepAuthority(Request request) {

	}

	/**
	 * 检查浏览国家地区权限
	 * 
	 * @param request
	 */
	@AuthorityFunctionAnnotation(caption = "浏览国家地区", index = 3)
	public void checkBaseCodeCountryAuthority(Request request) {

	}

	/**
	 * 检查浏览报关参数权限
	 * 
	 * @param request
	 */
	@AuthorityFunctionAnnotation(caption = "浏览报关参数", index = 4)
	public void checkBaseCodeParameterAuthority(Request request) {

	}

	/**
	 * 检查浏览商品编码权限
	 * 
	 * @author Administrator
	 * @param request
	 */
	@AuthorityFunctionAnnotation(caption = "浏览商品编码", index = 5)
	public void checkBaseCodeHsAuthority(Request request) {

	}

	/**
	 * 检查浏览商品编码权限
	 * 
	 * @author Administrator
	 * @param request
	 */
	@AuthorityFunctionAnnotation(caption = "浏览商品编码及疫区", index = 6)
	public void checkCheckupComplexAuthority(Request request) {

	}

	/**
	 * @return Returns the updateBase.
	 */
	public UpdateBase getUpdateBase() {
		return updateBase;
	}

	/**
	 * @param updateBase
	 *            The updateBase to set.
	 */
	public void setUpdateBase(UpdateBase updateBase) {
		this.updateBase = updateBase;
	}

	public CustomBaseLogic getCustomBaseLogic() {
		return customBaseLogic;
	}

	public void setCustomBaseLogic(CustomBaseLogic customBaseLogic) {
		this.customBaseLogic = customBaseLogic;
	}

	public List findComplexByType(String type) {
		return this.hsCodeDao.findComplexByType(type);
	}

	/**
	 * 返回所有Complex
	 * 
	 * @return
	 */
	public List findCustomsComplex() {
		return this.hsCodeDao.findCustomsComplex();
	}

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
	public List findComplex(Request request, int index, int length,
			String property, Object value, boolean isLike) {
		return this.hsCodeDao.findComplex(index, length, property, value,
				isLike, false);
	}

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
	public List findComplexAll(Request request, int index, int length,
			String property, Object value, boolean isLike) {
		return this.hsCodeDao.findComplex(index, length, property, value,
				isLike, true);
	}

	/**
	 * 是否有四码存在
	 * 
	 * @param fourCode
	 * @return
	 */
	public long findComplexLikeFourCodeCount(String fourCode) {
		return this.hsCodeDao.findComplexLikeFourCodeCount(fourCode);
	}

	public List findCustomsDocuName(String code) {
		return hsCodeDao.findCustomsDocuName(code);
	}

	public List findCustomsComplexNotInComplex1() {
		return hsCodeDao.findCustomsComplexNotInComplex1();
	}

	/**
	 * 查询商品自用编码
	 * 
	 * @return
	 */
	public List findComplexNotInCustomsComplex() {
		return this.customBaseLogic.findComplexNotInCustomsComplex();
		// return hsCodeDao.findComplexNotInCustomsComplex();
	}

	public List findComplexNotInCustomsComplex(int index, int length,
			String property, Object value, boolean isLike) {
		return hsCodeDao.findComplexNotInCustomsComplex(index, length,
				property, value, isLike);
	}

	// 更新商品编码表
	public List findChangedComplex(Request request) {
		return this.customBaseLogic.findChangedComplex(request.getTaskId());
	}

	// 返回加工种类
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.bcus.innermerge.action.aaaaaaaaa#findWrap()
	 */
	public List findWrap() {
		return this.parameterCodeDao.findWrap();
	}

	/**
	 * 返回报关行
	 */
	public List findCustomsBroker() {
		return this.parameterCodeDao.findCustomsBroker();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#SaveWrap(com.bestway.bcus
	 * .custombase.entity.parametercode.Wrap)
	 */
	public Wrap SaveWrap(Wrap obj) {
		this.parameterCodeDao.SaveWrap(obj);
		return obj;
	}

	/*
	 * 保存计量单位 (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#Saveunit(com.bestway.bcus
	 * .custombase.entity.parametercode.Unit)
	 */
	public Unit SaveUnit(Unit obj) {
		this.parameterCodeDao.SaveUnit(obj);
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#SaveGbtobig(com.bestway.
	 * bcus.custombase.entity.parametercode.Gbtobig)
	 */
	public Gbtobig SaveGbtobig(Gbtobig obj) {
		this.parameterCodeDao.SaveGbtobig(obj);
		return obj;
	}

	public PreDock SavePreDock(PreDock preDock) {
		this.parameterCodeDao.SavePreDock(preDock);
		return preDock;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#DeleteWrap(com.bestway.bcus
	 * .custombase.entity.parametercode.Wrap)
	 */
	public void DeleteWrap(Wrap obj) {
		this.parameterCodeDao.DeleteWrap(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#isReGbtobig(java.lang.String
	 * , java.lang.String)
	 */
	public boolean isReGbtobig(String code, String scode) {
		return this.parameterCodeDao.isReGbtobig(code, scode);
	}

	// 判断加工种类编号是否重复
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.innermerge.action.aaaaaaaaa#isReMerger(java.lang.String,
	 * java.lang.String)
	 */
	public boolean isReMerger(String code, String scode) {
		return this.parameterCodeDao.isReMerger(code, scode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.bcus.innermerge.action.aaaaaaaaa#findTransf()
	 */
	public Transf findTransf() {
		return this.parameterCodeDao.findTransf();
	}

	/**
	 * 检察是否有重复的自用商品编码
	 * 
	 * @param code
	 *            商品编码
	 * @return 如果有重复则返回是，否则反回否
	 */
	public boolean checkComplexCode(String code) {
		return this.customBaseLogic.checkComplexCode(code);
	}

	public List updateComplex(Request request, List list) {
		return this.customBaseLogic.updateComplex(request.getTaskId(), list);
	}

	/**
	 * 查找海关商编与自用商编的差异信息
	 */
	public List findUpdateComplex(Request request) {
		return this.customBaseLogic.findUpdateComplex();
	}

	// --------------------------------------------------------------------
	public ScmCoc findScmCocByName(Request request, String name,
			Boolean isCustomer, Boolean isManufacturer) {
		return this.baseCodeDao.findScmCocByName(name, isCustomer,
				isManufacturer);
	}

	public ScmCoc findScmCocByCode(Request request, String code,
			Boolean isCustomer, Boolean isManufacturer) {
		return this.baseCodeDao.findScmCocByCode(code, isCustomer,
				isManufacturer);
	}

	// ------------------------------------------------------------------
	public Country findCountryByName(Request request, String name) {
		return this.baseCodeDao.findCountryByName(name);
	}

	public List findCountry(Request request) {
		return this.baseCodeDao.findCountry();
	}

	/**
	 * 找出所有的仓库编码类型
	 * 
	 * @param request
	 * @return
	 */
	public List findWareSet(Request request) {
		return this.baseCodeDao.findWareSet();
	}

	public Country findCountryByCode(Request request, String code) {
		return this.baseCodeDao.findCountryByCode(code);
	}

	// ------------------------------------------------------------------
	public Curr findCurrByName(Request request, String name) {
		return this.baseCodeDao.findCurrByName(name);
	}

	public Curr findCurrByCode(Request request, String code) {
		return this.baseCodeDao.findCurrByCode(code);
	}

	public List findCurr(Request request) {
		return this.baseCodeDao.findCurr();
	}

	public List findScmCoc(Request request, Boolean isCustomer,
			Boolean isManufacturer) {
		return this.baseCodeDao.findScmCoc(isCustomer, isManufacturer);
	}

	public List findComplexAll(Request request) {
		return this.hsCodeDao.findComplexAll();
	}

	public List findBytesLength(Request request) {
		return this.baseCodeDao.findBytesLength();
	}

	public List findIsControlLength(Request request) {
		return this.baseCodeDao.findIsControlLength();
	}

	public List saveCoutryAsChecked(Request request, List list, boolean ischeck) {
		return customBaseLogic.saveCoutryAsChecked(list, ischeck);
	}

	public List addCheckupComplex(Request request, List list, Integer flag) {
		return customBaseLogic.addCheckupComplex(list, flag);
	}

	public List findComplexForCheckupNotIn(Request request, Integer impexpfalg) {
		return hsCodeDao.findComplexForCheckupNotIn(impexpfalg);
	}

	public List findComplexByFlag(Request request, Integer impexpfalg) {
		return hsCodeDao.findComplexByFlag(impexpfalg);
	}

	public void deleteObjects(Request request, List list) {
		for (int i = 0; i < list.size(); i++) {
			hsCodeDao.delete(list.get(i));
		}
	}

	/**
	 * 分页条件查询Bcs报关商品资料
	 * 
	 */

	public List findComplexBcsTenInnerMerge(Request request, int index,
			int length, String property, Object value, boolean isLike,
			boolean isImport) {
		return customBaseLogic.findComplexBcsTenInnerMerge(index, length,
				property, value, isLike, isImport);
	}

	public List findAllCustomsDeclaretion(Request request) {
		return this.parameterCodeDao.findAllCustomsDeclaretion();
	}

	public void saveOrUpdateObject(Request request, Object obj) {
		this.parameterCodeDao.saveOrUpdate(obj);
	}

	/**
	 * 根据海关商品编码生成自用商品编码
	 * 
	 * @param code
	 * @return
	 */
	public Complex makeComplexFromCustomsComplex(Request request, String code) {
		return this.customBaseLogic.makeComplexFromCustomsComplex(code);
	}

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
			String property, Object value, boolean isLike) {
		return this.hsCodeDao.findMachinebyFactoryAndFactualCustomsRalation(
				index, length, property, value, isLike);
	}

	public List findTransferMode() {
		return customBaseLogic.findTransferMode();
	}

	/**
	 * 查找其他参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是CompanyOther型，其他参数设置资料
	 */
	public List findCompanyOther(Request request) {
		return this.baseCodeDao.findCompanyOther();
	}

	/**
	 * 查询BCS参数
	 * 
	 * @param parameter
	 */
	public BcsParameterSet findBcsParameterSet(Request request) {
		return this.baseCodeDao.findBcsParameterSet();
	}

	/**
	 * 保存商检商品
	 * 
	 * @param complex
	 */
	public void saveCheckupComplex(Request request, Complex complex) {
		this.hsCodeDao.updateCustomsComplexByCode(complex.getCcontrol(),
				complex.getCode());
		this.baseCodeDao.saveOrUpdate(complex);

	}

	public List disableComplex(List list) {
		return customBaseLogic.disableComplex(list);
	}

	public List enableComplex(List list) {
		return customBaseLogic.enableComplex(list);
	}

	public Brief SaveBrief(Brief brief) {
		this.parameterCodeDao.SaveBrief(brief);
		return brief;
	}

	public Customs SaveCustom(Customs custom) {
		this.parameterCodeDao.SaveCustom(custom);
		return custom;
	}

	public PortInternal SavePortInternal(PortInternal portInternal) {
		this.parameterCodeDao.SavePortInternal(portInternal);
		return portInternal;
	}

	public PortLin SaveportLin(PortLin portLin) {
		this.parameterCodeDao.SaveportLin(portLin);
		return portLin;
	}

	public boolean isReDgBriefCode(String data) {
		return this.parameterCodeDao.isReDgBriefCode(data);
	}

	public boolean isReDgBriefName(String name) {
		return this.parameterCodeDao.isReDgBriefName(name);
	}

	public boolean isReDgCustomCode(String data) {
		return this.parameterCodeDao.isReDgCustomCode(data);
	}

	public boolean isReDgCustomName(String name) {
		return this.parameterCodeDao.isReDgCustomName(name);
	}

	public boolean isReDgDgPortInternalName(String name) {
		return this.parameterCodeDao.isReDgDgPortInternalName(name);
	}

	public boolean isReDgPortInternalCode(String data) {
		return this.parameterCodeDao.isReDgPortInternalCode(data);
	}

	public boolean isReDgOpenPortcode(String data) {
		return this.parameterCodeDao.isReDgOpenPortcode(data);
	}

	public boolean isReDgPreDockCode(String data) {
		return this.parameterCodeDao.isReDgPreDockCode(data);
	}

	public boolean isReDgPreDockName(String name) {
		return this.parameterCodeDao.isReDgPreDockName(name);
	}

	public void upgradeCustomsBase() {
		this.handUpdateCustomsLogic.upgradeCustomsBase();
	}

	public String[] upgradeCustomsBase(Request request, String fileName) {

		return handUpdateCustomsLogic.upgradeCustomsBase(request, fileName);

	}

	public CompanyOther findCompanyOther() {
		return parameterCodeDao.findCompanyOther();
	}

	public List findCustomBaseVersion() {
		return this.customBaseVersionDao.findCustomBaseVersion();
	}

	public List getMustUpgradeFileName(Map<String, String[]> map) {
		return this.handUpdateCustomsLogic.getMustUpgradeFileName(map);
	}

	/**
	 * 从客户端上传海关基础编码文件到服务器端
	 * 
	 * @param fileContent
	 */
	public void uploadCustomsBaseFileToServer(byte[] fileContent,
			String fileName) {
		this.handUpdateCustomsLogic.uploadCustomsBaseFileToServer(fileContent,
				fileName);
	}

	/**
	 * 从运维平台下载报关行
	 * 
	 * @return
	 */
	public List<CustomsBroker> downCustomsBrokerFormbgcs() {
		return this.customBaseLogic.downCustomsBrokerFormbgcs();
	}

	/**
	 * 保存报关行
	 * 
	 * @param CustomsBroker
	 */
	public List saveCustomsBroker(List list) {
		return this.customBaseLogic.saveCustomsBroker(list);
	}

	/**
	 * 删除报关行
	 * 
	 */
	public void deleteCustomsBroker(CustomsBroker customsBroker) {
		this.customBaseLogic.deleteCustomsBroker(customsBroker);
	}

	/**
	 * 删除报关行
	 * 
	 */
	public void deleteCustomsBroker(List list) {
		this.customBaseLogic.deleteCustomsBroker(list);
	}

	@Override
	public void SaveBriefs(List<Brief> list) {
		this.customBaseLogic.SaveBriefs(list);
	}

}