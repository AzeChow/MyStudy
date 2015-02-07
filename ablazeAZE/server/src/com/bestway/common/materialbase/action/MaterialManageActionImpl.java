package com.bestway.common.materialbase.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.dao.DataAccessException;

import com.bestway.bcus.cas.entity.TempEnterpriseMaterial;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.materialbase.dao.MaterialManageDao;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.CurrCode;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.common.materialbase.entity.CustomsUser;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.common.materialbase.entity.ParaSet;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCocControl;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.common.materialbase.entity.TempEntBomMaster;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.common.materialbase.entity.UnitCollate;
import com.bestway.common.materialbase.entity.UnitWaste;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.common.materialbase.logic.MaterialManageLogic;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
@SuppressWarnings("unchecked")
@AuthorityClassAnnotation(caption = "物流基础资料", index = 3)
public class MaterialManageActionImpl extends BaseActionImpl implements
		MaterialManageAction {

	private MaterialManageDao materialManageDao;

	private MaterialManageLogic materialManageLogic;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#getMaterialManageDao()
	 */
	public MaterialManageDao getMaterialManageDao() {
		return materialManageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#setMaterialManageDao(com
	 * .bestway.common.materialbase.dao.MaterialManageDao)
	 */
	public void setMaterialManageDao(MaterialManageDao materialManageDao) {
		this.materialManageDao = materialManageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#getMaterialManageLogic()
	 */
	public MaterialManageLogic getMaterialManageLogic() {
		return materialManageLogic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#setMaterialManageLogic
	 * (com.bestway.common.materialbase.logic.MaterialManageLogic)
	 */
	public void setMaterialManageLogic(MaterialManageLogic materialManageLogic) {
		this.materialManageLogic = materialManageLogic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveParaSet(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.ParaSet) 保存物流实体
	 */
	public ParaSet saveParaSet(Request request, ParaSet obj) {
		this.materialManageDao.saveParaSet(obj);
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteWare(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.WareSet) 删除仓库设置
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-删除", index = 0.2)
	public void deleteWare(Request request, WareSet ware) {
		materialManageDao.deleteWare(ware); // 仓库设置删除
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteMotorCode(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.MotorCode) 删除司机资料
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-删除", index = 0.2)
	public void deleteMotorCode(Request request, MotorCode motor) { // 司机资料设置删除
		materialManageDao.deleteMotorCode(motor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteUnit(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.CalUnit) 删除计量单位
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-删除", index = 0.2)
	public void deleteUnit(Request request, CalUnit unit) {
		materialManageDao.deleteUnit(unit); // 计量单位删除
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteCurr(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.CurrCode) 删除币制
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-删除", index = 0.2)
	public void deleteCurr(Request request, CurrCode curr) {
		materialManageDao.deleteCurr(curr); // 币别删除
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteShare(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.ShareCode)
	 * 删除税制代码资料
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-删除", index = 0.2)
	public void deleteShare(Request request, ShareCode share) {
		materialManageDao.deleteShare(share); // 公共代码删除
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteMateriel(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.Materiel) 删除物料主档
	 */

	public void deleteMateriel(Request request, Materiel materiel) {
		materialManageDao.deleteMateriel(materiel); // 物料主档删除
	}

	public String checkMaterielUsingCondition(Request request,Materiel materiel){
		return materialManageLogic.checkMaterielUsingCondition(materiel);
	}
	/*
	 * (non-Javadoc) 显示汇率所有数据
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findCurrRate(com.bestway
	 * .common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findCurrRate(Request request) {// 显示汇率所有数据
		return materialManageDao.findCurrRate();
	}

	/**
	 * 通过国内车牌返回司机资料设置
	 */
	public MotorCode findMotorCodeByCode(Request request, String homeCard) {
		return materialManageDao.findMotorCodeByCode(homeCard);
	}

	/**
	 * 通过海关编码返回司机资料设置
	 */
	public MotorCode findMotorCodeComplex(Request request, String complex) {
		return materialManageDao.findMotorCodeComplex(complex);
	}

	/**
	 * 返回司机资料设置
	 */
	public List findMotorCodeComplex(Request request) {
		return materialManageDao.findMotorCodeComplex();
	}

	/**
	 * 显示汇率所有数据
	 */
	public List findCurrRate(Request request, String currCode) {
		return materialManageDao.findCurrRate(currCode);
	}

	/**
	 * 取得汇率
	 * 
	 * @param sourCurr
	 *            本地汇率
	 * @param destCurr
	 *            外地汇率
	 * @param createDate
	 *            创建日期
	 * @return 汇率
	 */
	public double findExchangeRate(Request request, Curr sourCurr,
			Curr destCurr, Date createDate) {
		return this.materialManageLogic.findExchangeRate(sourCurr, destCurr,
				createDate);
	}

	public Date findExchangeRateData(Request request, Curr sourCurr,
			Curr destCurr, Date createDate) {
		return this.materialManageDao.findExchangeRateData(sourCurr, destCurr,
				createDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.factorymerge.logicommon.action.LogiCommonAction#
	 * findUserByLoginName(java.lang.String)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findWarerByCode(com.bestway
	 * .common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public WareSet findWarerByCode(Request request, String code) {
		return materialManageDao.findWarerByCode(code); // 检查仓库设置重复
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findWarerByName(com.bestway
	 * .common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public WareSet findWarerByName(Request request, String name) {
		return materialManageDao.findWarerByName(name); // 检查仓库设置重复
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findUnitByCode(com.bestway
	 * .common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public CalUnit findUnitByCode(Request request, String code) {
		return materialManageDao.findUnitByCode(code); // 检查计量单位重复
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveCurrRate(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.CurrRate)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-编辑", index = 0.4)
	public CurrRate saveCurrRate(Request request, CurrRate currRate) {
		materialManageDao.saveCurrRate(currRate); // 保存汇率
		return currRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveMotorCode(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.MotorCode)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-编辑", index = 0.4)
	public MotorCode saveMotorCode(Request request, MotorCode motor) {
		materialManageDao.saveMotorCode(motor);
		return motor;
	}

	public MotorCode saveMotorCodeToCustoms(Request request, MotorCode motor) {
		materialManageDao.saveMotorCode(motor);
		return motor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteCurrRate(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.CurrRate)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-删除", index = 0.2)
	public void deleteCurrRate(Request request, CurrRate currRate) {
		materialManageDao.deleteCurrRate(currRate); // 删除汇率
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findUnitByName(com.bestway
	 * .common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public CalUnit findUnitByName(Request request, String name) {
		return materialManageDao.findUnitByName(name); // 检查计量单位重复
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findCurrByCode(com.bestway
	 * .common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public CurrCode findCurrByCode(Request request, String code) {
		return materialManageDao.findCurrByCode(code); // 检查币别重复
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findCurrByName(com.bestway
	 * .common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public CurrCode findCurrByName(Request request, String name) {
		return materialManageDao.findCurrByName(name); // 检查币别重复
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findTaxaTionByCode(com
	 * .bestway.common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public ShareCode findTaxaTionByCode(Request request, String code) {
		return materialManageDao.findTaxaTionByCode(code); // 检查税制代码重复
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findTaxaTionByName(com
	 * .bestway.common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public ShareCode findTaxaTionByName(Request request, String name) {
		return materialManageDao.findTaxaTionByName(name); // 检查税制代码重复
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaterielByPtNo(com
	 * .bestway.common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public Materiel findMaterielByPtNo(Request request, String ptNo) {
		return materialManageDao.findMaterielByPtNo(ptNo); // 物料主档检查料号是否重复
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findWareSet(com.bestway
	 * .common.Request)
	 */
	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findWareSet(Request request) {
		return materialManageDao.findWareSet(); // 显示仓库设置所有数据
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findCalUnit(com.bestway
	 * .common.Request)
	 */
	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findCalUnit(Request request) {
		return materialManageDao.findCalUnit(); // 显示计量单位所有数据
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findCurrCode(com.bestway
	 * .common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findCurrCode(Request request) {
		return materialManageDao.findCurrCode(); // 显示币别所有数据
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findTaxaTion(com.bestway
	 * .common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findTaxaTion(Request request) {
		return materialManageDao.findTaxaTion(); // 显示税制代码所有数据
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMateriel(com.bestway
	 * .common.Request, java.lang.String, int, int, java.lang.String,
	 * java.lang.Object, java.lang.Boolean)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂物料-浏览", index = 5.0)
	public List findMateriel(Request request, String materielType, int index,
			int length, String property, Object value, Boolean isLike) {
		return materialManageDao.findMateriel(materielType, index, length,
				property, value, isLike);
	}

	@AuthorityFunctionAnnotation(caption = "报关常用工厂物料-修改", index = 5.02)
	public void findMaterielforControlEdit(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "报关常用工厂物料-删除", index = 5.03)
	public void findMaterielforControlDel(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "报关常用工厂物料-新增", index = 5.01)
	public void findMaterielforControlAdd(Request request) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveWare(com.bestway.common
	 * .Request, com.bestway.common.materialbase.entity.WareSet)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-编辑", index = 0.4)
	public WareSet saveWare(Request request, WareSet ware)
			throws DataAccessException {
		materialManageDao.saveWare(ware);
		return ware; // 仓库设置保存
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveUnit(com.bestway.common
	 * .Request, com.bestway.common.materialbase.entity.CalUnit)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-编辑", index = 0.4)
	public CalUnit saveUnit(Request request, CalUnit unit)
			throws DataAccessException {
		materialManageDao.saveUnit(unit);
		return unit; // 计量单位保存
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveCurr(com.bestway.common
	 * .Request, com.bestway.common.materialbase.entity.CurrCode)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-编辑", index = 0.4)
	public CurrCode saveCurr(Request request, CurrCode curr)
			throws DataAccessException {
		materialManageDao.saveCurr(curr);
		return curr; // 计量单位保存
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveShare(com.bestway.
	 * common.Request, com.bestway.common.materialbase.entity.ShareCode)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-编辑", index = 0.4)
	public ShareCode saveShare(Request request, ShareCode share)
			throws DataAccessException {
		materialManageDao.saveShare(share);
		return share; // 公共代码保存
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveMateriel(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.Materiel)
	 */

	public Materiel saveMateriel(Request request, Materiel materiel)
			throws DataAccessException {
		return materialManageLogic.saveMateriel(materiel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findScmManufacturer(com
	 * .bestway.common.Request)
	 */
	public List findScmManufacturer(Request request) { // 显示供应商
		return materialManageDao.findScmManufacturer();
	}

	public List findScmCocsfacturerByXiaok(Request request) { // 显示供应商
		return materialManageDao.findScmCocsfacturerByXiaok();
	}

	public List findScmCocscustomerByXiaok(Request request) { // 显示客户
		return materialManageDao.findScmCocscustomerByXiaok();
	}

	/*
	 * 返回所有国家资料
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findSysAreas(com.bestway
	 * .common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findSysAreas(Request request) {
		return materialManageDao.findSysAreas();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findSysAreaById(com.bestway
	 * .common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public SysArea findSysAreaById(Request request, String id) {
		return materialManageDao.findSysAreaById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findSysAreaByCode(com.
	 * bestway.common.Request, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public SysArea findSysAreaByCode(Request request, String code) {
		return materialManageDao.findSysAreaByCode(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveSysArea(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.SysArea)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-编辑", index = 0.4)
	public SysArea saveSysArea(Request request, SysArea area) {
		materialManageDao.saveSysArea(area);
		return area;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteSysArea(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.SysArea)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-删除", index = 0.2)
	public void deleteSysArea(Request request, SysArea area) {
		materialManageDao.deleteSysArea(area);
	}

	/*
	 * 返回所有物料类别
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findScmCois(com.bestway
	 * .common.Request)
	 */
	// @AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findScmCois(Request request) {
		return materialManageDao.findScmCois();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findScmCoiById(com.bestway
	 * .common.Request, java.lang.String)
	 */
	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public ScmCoi findScmCoiById(Request request, String id) {
		return materialManageDao.findScmCoiById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findScmCoiByCode(com.bestway
	 * .common.Request, java.lang.String)
	 */
	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public ScmCoi findScmCoiByCode(Request request, String code) {
		return materialManageDao.findScmCoiByCode(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveScmCoi(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.ScmCoi)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-编辑", index = 0.4)
	public ScmCoi saveScmCoi(Request request, ScmCoi coi) {
		materialManageDao.saveScmCoi(coi);
		return coi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteScmCoi(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.ScmCoi)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-删除", index = 0.2)
	public void deleteScmCoi(Request request, ScmCoi coi) {
		materialManageDao.deleteScmCoi(coi);
	}

	/*
	 * 返回所有客户/供应商/合作伙伴资料
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findScmCocs(com.bestway
	 * .common.Request)
	 */
	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findScmCocs(Request request) {
		return materialManageDao.findScmCocs();
	}

	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findScmManuFactoryOut(Request request) {
		return materialManageDao.findScmManuFactoryOut();
	}

	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findScmManucustomer(Request request) {
		return materialManageDao.findScmManucustomer();
	}

	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findScmManuFactoryIn(Request request) {
		return materialManageDao.findScmManuFactoryIn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMotorCode(com.bestway
	 * .common.Request)
	 */
	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public List findMotorCode(Request request, int index, int length) { // 显示司机资料分页
		return materialManageDao.findMotorCode(index, length);
	}

	public List findMotorCode(Request request) {// 显示司机设置所有资料
		// 
		return materialManageDao.findMotorCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findScmCocById(com.bestway
	 * .common.Request, java.lang.String)
	 */
	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public ScmCoc findScmCocById(Request request, String id) {
		return materialManageDao.findScmCocById(id);
	}
	
	/**
	 * @author Administrator
	 * 主权限控制
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public void checkCommonBaseCodeAuthority(Request request) {
		// TODO Auto-generated method stub
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findScmCocByCode(com.bestway
	 * .common.Request, java.lang.String)
	 */
	//@AuthorityFunctionAnnotation(caption = "工厂通用代码-浏览", index = 0.1)
	public ScmCoc findScmCocByCode(Request request, String code) {
		return materialManageDao.findScmCocByCode(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveScmCoc(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.ScmCoc)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-保存", index = 0.3)
	public ScmCoc saveScmCoc(Request request, ScmCoc coc) {
		materialManageLogic.saveScmCoc(coc);
		return coc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteScmCoc(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.ScmCoc)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂通用代码-删除", index = 0.2)
	public void deleteScmCoc(Request request, ScmCoc coc) {
		materialManageLogic.deleteScmCoc(coc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaterielByType(com
	 * .bestway.common.Request, java.lang.String)
	 */
	public List findMaterielByType(Request request, String materielType) {
		return this.materialManageDao.findMaterielByType(materielType);
	}

	// /* 查找成品或半成品
	// *
	// */
	// public List findProductOrHalf(Request request, Boolean isHalf) {
	// return this.getCommonBaseCodeDao().findProductOrHalf(isHalf);
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaterielByMaterielType
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public List findMaterielByMaterielType(Request request, String materielType) {
		return this.materialManageDao.findMaterielByMaterielType(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveUnitWaste(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.UnitWaste)
	 */
	public UnitWaste saveUnitWaste(Request request, UnitWaste object) {
		this.materialManageDao.saveUnitWaste(object);
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findUnitWaste(com.bestway
	 * .common.Request, java.lang.String)
	 */
	public List findUnitWaste(Request request, String exgNo) {
		return materialManageDao.findUnitWaste(exgNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveBomImgDetail(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.UnitWaste)
	 */
	public UnitWaste saveBomImgDetail(Request request, UnitWaste object) {
		materialManageDao.saveBomImgDetail(object);
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteUnitWaste(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.UnitWaste)
	 */
	public void deleteUnitWaste(Request request, UnitWaste object) {
		materialManageDao.deleteUnitWaste(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteBomImgDetail(com
	 * .bestway.common.Request,
	 * com.bestway.common.materialbase.entity.UnitWaste)
	 */
	public void deleteBomImgDetail(Request request, UnitWaste object) {
		materialManageDao.deleteBomImgDetail(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteAllBom(com.bestway
	 * .common.Request, java.lang.String)
	 */
	public void deleteAllBom(Request request, String exgNo) {
		materialManageDao.deleteAllBom(exgNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findCustomsUser(com.bestway
	 * .common.Request)
	 */
	public List findCustomsUser(Request request) { // 显示报关员设置所有资料
		return this.materialManageDao.findCustomsUser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteCustomsUser(com.
	 * bestway.common.Request,
	 * com.bestway.common.materialbase.entity.CustomsUser)
	 */
	public void deleteCustomsUser(Request request, CustomsUser motor) { // 报关员设置删除
		this.materialManageDao.deleteCustomsUser(motor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveCustomsUser(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.CustomsUser)
	 */
	public CustomsUser saveCustomsUser(Request request, CustomsUser obj)
			throws DataAccessException {
		this.materialManageDao.saveCustomsUser(obj);
		return obj;
	}

	/**
	 * 保存事业部
	 */
	public ProjectDept saveProjectDept(Request request, ProjectDept obj)
			throws DataAccessException {
		this.materialManageDao.saveProjectDept(obj);
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findCustomsUserTel(com
	 * .bestway.common.Request, java.lang.String)
	 */
	public String findCustomsUserTel(Request request, String name) {
		return this.materialManageDao.findCustomsUserTel(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findParaSet(com.bestway
	 * .common.Request)
	 */
	public List findParaSet(Request request) {
		return this.materialManageDao.findParaSet();
	}

	public List findProjectDept(Request request) {
		return this.materialManageDao.findProjectDept();
	}

	public void deleteProjectDept(Request request, ProjectDept obj) {
		this.materialManageDao.deleteProjectDept(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteParaSet(com.bestway
	 * .common.Request, com.bestway.common.materialbase.entity.ParaSet)
	 */
	public void deleteParaSet(Request request, ParaSet obj) {
		this.materialManageDao.deleteParaSet(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findParaSetBytype(com.
	 * bestway.common.Request, int, java.lang.String)
	 */
	public String findParaSetBytype(Request request, int type, String beginValue) {
		return this.materialManageDao.findParaSetBytype(type, beginValue);
	}

	// /**
	// * 返回产品结构管理
	// */
	// public List findMaterBomManage(Request request, String materielType) {
	// return materialManageDao.findMaterBomManage(materielType);
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * findEnterpriseMaterialNotInMaster(com.bestway.common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public List findEnterpriseMaterialNotInMaster(Request request) {
		return this.materialManageDao.findEnterpriseMaterialNotInMaster();
	}
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public void checkBomBrowseAuthority(Request request) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 查找不在工厂BOM管理父件、料件里的工厂物料主档资料(料件)
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public List findEnterpriseMaterialNotInMasterMateriel(Request request) {
		return this.materialManageDao
				.findEnterpriseMaterialNotInMasterMateriel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * findEnterpriseMaterialNotInDetail(com.bestway.common.Request,
	 * java.lang.String, java.lang.Integer)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public List findEnterpriseMaterialNotInDetail(Request request,
			String parentNo, Integer version) {
		return this.materialManageLogic.findEnterpriseMaterialNotInDetail(
				parentNo, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseBomMaster
	 * (com.bestway.common.Request, int, int, java.lang.String,
	 * java.lang.Object, java.lang.Boolean)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public List findEnterpriseBomMaster(Request request, int index, int length,
			String property, Object value, Boolean isLike) {
		return this.materialManageLogic.findEnterpriseBomMaster(index, length,
				property, value, isLike);
	}

	
	public List findEnterpriseBomMasterZX(Request request, int index, int length,
			String property, Object value, Boolean isLike) {
		return this.materialManageLogic.findEnterpriseBomMasterZX(index, length,
				property, value, isLike);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findExistMaterialBomVersion
	 * (com.bestway.common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public List findExistMaterialBomVersion(Request request) {
		return this.materialManageDao.findExistMaterialBomVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaxMaterialBomVersion
	 * (com.bestway.common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public int findMaxMaterialBomVersion(Request request) {
		return this.materialManageDao.findMaxMaterialBomVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseBomVersion
	 * (com.bestway.common.Request, java.lang.String, int)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public List findEnterpriseBomVersion(Request request, String parentNo,
			int bomStructureType) {
		return this.materialManageLogic.findEnterpriseBomVersion(parentNo,
				bomStructureType);
	}
	
	
	public List findMaterialBomVersion(Request request, String parentNo,
			int bomStructureType) {
		return this.materialManageLogic.findMaterialBomVersion(parentNo, bomStructureType);
	}
	public List findMaterialBomVersionDate(Request request, String parentNo) {
		return this.materialManageLogic.findMaterialBomVersionDate(parentNo);
	}
	
	public List findEnterpriseBomVersionZX(Request request, String parentNo,
			int bomStructureType) {
		return this.materialManageLogic.findEnterpriseBomVersionZX(parentNo,
				bomStructureType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseBomDetail
	 * (com.bestway.common.Request, java.lang.String, int)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public HashMap<String, List<Object>> findEnterpriseBomDetail(
			Request request, String parentNo, int bomStructureType) {
		return this.materialManageLogic.findEnterpriseBomDetail(parentNo,
				bomStructureType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseBomDetail
	 * (com.bestway.common.Request, java.lang.String, int)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public List findEnterpriseBomDetail(Request request, String parentNo) {
		return this.materialManageLogic.findEnterpriseBomDetail(parentNo);
	}

//	public List findAllEnterpriseBomDetail(Request request) {
//		return this.materialManageLogic.findAllEnterpriseBomDetail();
//	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#addEnterpriseBomMaster
	 * (com.bestway.common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "新增工厂BOM管理父件", index = 3.11)
	public List addEnterpriseBomMaster(Request request,
			List<EnterpriseMaterial> list) {
		return this.materialManageLogic.addEnterpriseBomMaster(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#addEnterpriseBomVersion
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.TempEntBomMaster,
	 * com.bestway.common.materialbase.entity.TempEntBomVersion)
	 */
	@AuthorityFunctionAnnotation(caption = "编辑工厂BOM管理版本号", index = 3.21)
	public void addEnterpriseBomVersion(Request request,
			TempEntBomMaster tempMaster, TempEntBomVersion tempVersion) {
		this.materialManageLogic.addEnterpriseBomVersion(tempMaster,
				tempVersion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#addEnterpriseBomDetail
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.TempEntBomMaster,
	 * com.bestway.common.materialbase.entity.TempEntBomVersion, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "编辑工厂BOM管理子件", index = 3.31)
	public List addEnterpriseBomDetail(Request request,
			TempEntBomMaster tempMaster, TempEntBomVersion tempVersion,
			List<EnterpriseMaterial> list) {
		return this.materialManageLogic.addEnterpriseBomDetail(tempMaster,
				tempVersion, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteEnterpriseBomMaster
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.TempEntBomMaster)
	 */
	@AuthorityFunctionAnnotation(caption = "删除工厂BOM管理父件", index = 3.12)
	public void deleteEnterpriseBomMaster(Request request,
			TempEntBomMaster tempMaster) {
		this.materialManageLogic.deleteEnterpriseBomMaster(tempMaster);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteEnterpriseBomVersion
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.TempEntBomMaster,
	 * com.bestway.common.materialbase.entity.TempEntBomVersion)
	 */
	@AuthorityFunctionAnnotation(caption = "删除工厂BOM管理版本号", index = 3.23)
	public void deleteEnterpriseBomVersion(Request request,
			TempEntBomMaster tempMaster, TempEntBomVersion tempVersion) {
		this.materialManageLogic.deleteEnterpriseBomVersion(tempMaster,
				tempVersion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteEnterpriseBomDetail
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.TempEntBomMaster,
	 * com.bestway.common.materialbase.entity.TempEntBomVersion, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "删除工厂BOM管理子件", index = 3.33)
	public void deleteEnterpriseBomDetail(Request request,
			TempEntBomMaster tempMaster, TempEntBomVersion tempVersion,
			List<EnterpriseBomManage> list) {
		this.materialManageLogic.deleteEnterpriseBomDetail(tempMaster,
				tempVersion, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveEnterpriseBomVersion
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.TempEntBomMaster,
	 * com.bestway.common.materialbase.entity.TempEntBomVersion)
	 */
	@AuthorityFunctionAnnotation(caption = "编辑工厂BOM管理版本号", index = 3.21)
	public void saveEnterpriseBomVersion(Request request,
			TempEntBomMaster tempMaster, TempEntBomVersion tempVersion) {
		this.materialManageLogic.saveEnterpriseBomVersion(tempMaster,
				tempVersion);
	}
	
	public void saveEnterpriseBomVersionZX(Request request,
			TempEntBomMaster tempMaster, TempEntBomVersion tempVersion) {
		this.materialManageLogic.saveEnterpriseBomVersionZX(tempMaster,
				tempVersion);
	}
	
	public EnterpriseBomManage saveEnterpriseBomManageZX(Request request,
			EnterpriseBomManage detail) {
		this.materialManageLogic.saveEnterpriseBomManageZX(detail);
		return detail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveEnterpriseBomManage
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.EnterpriseBomManage)
	 */
	@AuthorityFunctionAnnotation(caption = "编辑工厂BOM管理子件", index = 3.31)
	public EnterpriseBomManage saveEnterpriseBomManage(Request request,
			EnterpriseBomManage detail) {
		this.materialManageDao.saveEnterpriseBomManage(detail);
		return detail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * transferBomFromEnterpriseNoVersionNoDate(com.bestway.common.Request,
	 * java.util.List, java.lang.Integer)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public void transferBomFromEnterpriseNoVersionNoDate(Request request,
			List list, Integer version) {
		this.materialManageLogic.transferBomFromEnterpriseNoVersionNoDate(list,
				version, false);
	}
	
	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中(振兴)
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 * @param version
	 *            父件版本号
	 * @param isMerge
	 *            是否合并主辅料标志相同的料件
	 */
	public void transferBomFromEnterpriseNoVersionNoDate(Request request,
			List list, Integer version, boolean isMerge) {
		this.materialManageLogic.transferBomFromEnterpriseNoVersionNoDate(list,
				version, isMerge);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * transferBomFromEnterpriseByDate(com.bestway.common.Request,
	 * java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public void transferBomFromEnterpriseByDate(Request request, List list) {
		this.materialManageLogic.transferBomFromEnterpriseByDate(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * transferBomFromEnterpriseByVersion(com.bestway.common.Request,
	 * java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public void transferBomFromEnterpriseByVersion(Request request, List list) {
		this.materialManageLogic.transferBomFromEnterpriseByVersion(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseMaterial
	 * (com.bestway.common.Request)
	 */
	public List findEnterpriseMaterial(Request request) { // 物料主档显示所有数据
		return this.materialManageDao.findEnterpriseMaterial();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.MaterialManageAction1#
	 * findEnterpriseMaterial(com.bestway.common.Request, java.lang.String)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseMaterial
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public EnterpriseMaterial findEnterpriseMaterial(Request request,
			String ptNo) {
		return this.materialManageDao.findEnterpriseMaterial(ptNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseMaterial
	 * (com.bestway.common.Request, java.util.List)
	 */
	public List findEnterpriseMaterial(Request request, List materielTypeList) {
		return this.materialManageDao.findEnterpriseMaterial(materielTypeList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseMaterial
	 * (com.bestway.common.Request, java.lang.String, int, int,
	 * java.lang.String, java.lang.Object, java.lang.Boolean)
	 */
	public List findEnterpriseMaterial(Request request, String materielType,
			int index, int length, String property, Object value, Boolean isLike) {
		return this.materialManageDao.findEnterpriseMaterial(materielType,
				index, length, property, value, isLike);
	}

	public List findAllEnterpriseBomDetail(Request request, int index,
			int length, String property, Object value, Boolean isLike) {
		return this.materialManageLogic.findAllEnterpriseBomDetailLs(index, length, property, value, isLike);
	}
	
	/**
	 * 分页查询查询来自类型的总数
	 */
	public Long findEnterpriseMaterialCount(Request request,
			String materielType, int index, int length, String property,
			Object value, Boolean isLike) {
		return this.materialManageDao.findEnterpriseMaterialCount(materielType,
				index, length, property, value, isLike);
	}
	
	/**
	 * 分页查询工厂BOM总数
	 */
	public Long findAllEnterpriseBomDetailCount(Request request, int index,
			int length, String property, Object value, Boolean isLike) {
		return this.materialManageDao.findAllEnterpriseBomDetailCount(index, length, property, value, isLike);
	}

	/**
	 * 查询料件和半成品
	 * 
	 * @return
	 */
	public List findEnterpriseMaterialMaterielAndProduct(Request request,
			int index, int length, String property, Object value, boolean isLike) {
		return this.materialManageDao.findEnterpriseMaterialMaterielAndProduct(
				index, length, property, value, isLike);
	}

	// /**
	// * 查找物料主档来自类别-->进出口报关申请单
	// */
	// public List findEnterpriseMaterialByType(Request request, String
	// materielType) {
	// return this.materialManageDao.findEnterpriseMaterialByType(materielType);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseMaterialById
	 * (com.bestway.common.Request, java.lang.String)
	 */
	public EnterpriseMaterial findEnterpriseMaterialById(Request request,
			String id) { // 物料主档显示所有数据
		return this.materialManageDao.findEnterpriseMaterialById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveEnterpriseMaterial
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.EnterpriseMaterial)
	 */

	public EnterpriseMaterial saveEnterpriseMaterial(Request request,
			EnterpriseMaterial materiel) {
		this.materialManageLogic.saveEnterpriseMaterial(materiel); // 物料主档保存
		return materiel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteEnterpriseMaterial
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.EnterpriseMaterial)
	 */

	public void deleteEnterpriseMaterial(Request request,
			EnterpriseMaterial materiel) { // 物料主档删除
		this.materialManageDao.deleteEnterpriseMaterial(materiel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findEnterpriseMaterialByPtNo
	 * (com.bestway.common.Request, java.lang.String)
	 */

	public EnterpriseMaterial findEnterpriseMaterialByPtNo(Request request,
			String ptNo) { // 物料主档查找是否重复
		return this.materialManageDao.findEnterpriseMaterialByPtNo(ptNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * findEnterpriseMaterialForMaterial(com.bestway.common.Request,
	 * java.lang.String)
	 */

	public List findEnterpriseMaterialForMaterial(Request request,
			String materielType) {
		// return this.materialManageDao
		// .findEnterpriseMaterialForMaterial(materielType);
		return this.materialManageLogic
				.findEnterpriseMaterialForMaterial(materielType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#addCustomMaterial(com.
	 * bestway.common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂BOM-新增成品", index = 5.11)
	public List addCustomMaterial(Request request, List lsMateriel) {
		return this.materialManageLogic.addCustomMaterial(lsMateriel);
	}

	//
	// /**
	// * 变更物料主档
	// *
	// * @param material
	// */
	// public EnterpriseMaterial changeCustomMaterial(Request request,
	// EnterpriseMaterial material) {
	// this.materialManageLogic.changeCustomMaterial(material);
	// return material;
	// }
	//
	// /**
	// * 生效物料主档
	// *
	// * @param material
	// */
	// public void applyCustomMaterial(Request request, String scmCoiCode) {
	// this.materialManageLogic.applyCustomMaterial(scmCoiCode);
	// }
	//
	// /**
	// * 生效物料主档
	// *
	// * @param list
	// */
	// public void applyCustomMaterial(Request request, List list) {
	// this.materialManageLogic.applyCustomMaterial(list);
	// }
	//
	// /**
	// * 生效物料主档
	// *
	// * @param material
	// */
	// public void effectiveCustomMaterial(Request request, String scmCoiCode) {
	// this.materialManageLogic.effectiveCustomMaterial(scmCoiCode);
	// }
	//
	// /**
	// * 生效物料主档
	// *
	// * @param list
	// */
	// public void effectiveCustomMaterial(Request request, List list) {
	// this.materialManageLogic.effectiveCustomMaterial(list);
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findTopProductBomManage
	 * (com.bestway.common.Request)
	 */
	public List findTopProductBomManage(Request request) {
		return this.materialManageLogic.findTopProductBomManage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findTopProductBomManage
	 * (com.bestway.common.Request, java.lang.Integer)
	 */
	public List findTopProductBomManage(Request request, Integer version) {
		return this.materialManageLogic.findTopProductBomManage(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * findTopProductBomManageForVersion(com.bestway.common.Request)
	 */
	public List findTopProductBomManageForVersion(Request request) {
		return this.materialManageLogic.findTopProductBomManageForVersion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaterielBomMaster(
	 * com.bestway.common.Request, int, int, java.lang.String, java.lang.Object,
	 * java.lang.Boolean)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public List findMaterielBomMaster(Request request, int index, int length,
			String property, Object value, Boolean isLike) {
		return this.getMaterialManageDao().findMaterielBomMaster(index, length,
				property, value, isLike);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaterielBomDetail(
	 * com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomVersion)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public List findMaterielBomDetail(Request request,
			MaterialBomVersion version) {
		return this.getMaterialManageDao().findMaterielBomDetail(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveMaterielBomDetail(
	 * com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomDetail)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂BOM-编辑料件", index = 5.31)
	public MaterialBomDetail saveMaterielBomDetail(Request request,
			MaterialBomDetail materielBomDetail) {
		this.getMaterialManageDao().saveMaterielBomDetail(materielBomDetail);
		return materielBomDetail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteMaterielBomDetail
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomDetail)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂BOM料件-删除", index = 5.33)
	public void deleteMaterielBomDetail(Request request,
			MaterialBomDetail materielBomDetail) {
		this.getMaterialManageDao().deleteMaterielBomDetail(materielBomDetail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaterielBomVersion
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomMaster)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public List findMaterielBomVersion(Request request, MaterialBomMaster master) {
		return getMaterialManageDao().findMaterielBomVersion(master);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveMaterielBomVersion
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomVersion)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂BOM版本号-编辑", index = 5.21)
	public MaterialBomVersion saveMaterielBomVersion(Request request,
			MaterialBomVersion materielBomVersion) {
		getMaterialManageDao().saveMaterielBomVersion(materielBomVersion);
		return materielBomVersion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaterielBomDetail(
	 * com.bestway.common.Request, java.lang.Integer, java.lang.String)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public List findMaterielBomDetail(Request request, Integer version,
			String ptPart) {
		return getMaterialManageDao().findMaterielBomDetail(version, ptPart);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaterielBomDetail(
	 * com.bestway.common.Request, java.lang.Integer)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public List findMaterielBomDetail(Request request, Integer version) {
		return getMaterialManageDao().findMaterielBomDetail(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findNotInBomMasterMaterial
	 * (com.bestway.common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public List findNotInBomMasterMaterial(Request request) {
		return this.materialManageDao.findNotInBomMasterMaterial();
	}

	/**
	 * 查找报关常用工厂物料里的成品资料，但是不在报关常用工厂BOM成品里查找
	 * 
	 * @return List 是Materiel型，报关常用工厂物料
	 */
	
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public List findNotInBomMasterMaterial2(Request request) {
		return this.materialManageDao.findNotInBomMasterMaterial2();
	}

	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public void checkCustomBomManageAuthority(Request request){
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findNotInBomDetailMaterial
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomVersion)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public List findNotInBomDetailMaterial(Request request,
			MaterialBomVersion version) {
		return this.materialManageDao.findNotInBomDetailMaterial(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveMaterialBomMaster(
	 * com.bestway.common.Request, java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂BOM成品-新增", index = 5.11)
	public List saveMaterialBomMaster(Request request, List lsCustomMaterial) {
		return this.materialManageLogic.saveMaterialBomMaster(lsCustomMaterial);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveMaterialBomDetail(
	 * com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomVersion,
	 * java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂BOM料件-编辑", index = 5.31)
	public List saveMaterialBomDetail(Request request,
			MaterialBomVersion version, List lsCustomDetail) {
		return this.materialManageLogic.saveMaterialBomDetail(version,
				lsCustomDetail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#saveMaterialBomDetail(
	 * com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomVersion,
	 * java.util.List)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂BOM料件-编辑", index = 5.31)
	public MaterialBomDetail saveMaterialBomDetail(Request request,
			MaterialBomDetail detail) {
		this.materialManageDao.saveMaterielBomDetail(detail);
		return detail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteMaterielBomMaster
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomMaster)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂BOM成品-删除", index = 5.13)
	public void deleteMaterielBomMaster(Request request,
			MaterialBomMaster master) {
		this.materialManageLogic.deleteMaterielBomMaster(master);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteMaterielBomVersion
	 * (com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomVersion)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用工厂BOM版本号-删除", index = 5.23)
	public void deleteMaterielBomVersion(Request request,
			MaterialBomVersion version) {
		this.materialManageLogic.deleteMaterielBomVersion(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * findEnterpriseBomVersionByVersion(com.bestway.common.Request,
	 * java.lang.String, java.lang.Integer)
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public List findEnterpriseBomVersionByVersion(Request request,
			String parentNo, Integer version) {
		return this.materialManageDao.findEnterpriseBomVersionByVersion(
				parentNo, version);
	}
	
	
	/**
	 * 根据报关常用工厂BOM成品料号、版本号查找报关常用工厂BOM版本
	 * 
	 * @param parentNo
	 *            父级料号
	 * @param version
	 *            父件版本号
	 * @return List 是EnterpriseBomVersion型，工厂BOM管理版本
	 */
	public List findMaterielBomVersionByVersion(Request request, String parentNo,
			Integer version) {
		return this.materialManageDao.findMaterielBomVersionByVersion(parentNo, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * findMaterielBomVersionByVersion(com.bestway.common.Request,
	 * com.bestway.common.materialbase.entity.MaterialBomMaster,
	 * java.lang.Integer)
	 */
	@AuthorityFunctionAnnotation(caption = "报关常用厂BOM-浏览", index = 5)
	public List findMaterielBomVersionByVersion(Request request,
			MaterialBomMaster master, Integer version) {
		return this.materialManageDao.findMaterielBomVersionByVersion(master,
				version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findMaterialBomVersionId
	 * (com.bestway.common.Request, java.lang.Integer)
	 */
	public List findMaterialBomVersionId(Request request, Integer version) {
		return this.materialManageDao.findMaterialBomVersionId(version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#updateMaterialBomWaste
	 * (com.bestway.common.Request, java.lang.Integer, java.lang.String,
	 * java.lang.String, java.lang.Double, boolean)
	 */
	public void updateMaterialBomWaste(Request request, Integer versionNo,
			String pFourCode, String mFourCode, Double waste,
			boolean isByUnitUsed) {
		this.materialManageLogic.updateMaterialBomWaste(request.getTaskId(),
				versionNo, pFourCode, mFourCode, waste, isByUnitUsed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.materialbase.action.aaaaaaa#
	 * findInnerMergeFourCodeByMaterialType(com.bestway.common.Request,
	 * java.lang.String)
	 */
	public List findInnerMergeFourCodeByMaterialType(Request request,
			String materialType) {
		return this.materialManageDao
				.findInnerMergeFourCodeByMaterialType(materialType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#initMateriel(com.bestway
	 * .common.Request, java.lang.String, int)
	 */
	public void initMateriel(Request request, String materielType, int number) {
		this.materialManageLogic.initMateriel(materielType, number);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#initBom(com.bestway.common
	 * .Request)
	 */
	public void initBom(Request request) {
		this.materialManageLogic.initBom();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#findRepeatMaterial(com
	 * .bestway.common.Request)
	 */
	public List findRepeatMaterial(Request request) { // 物料主档查找是否重复
		return this.materialManageLogic.findRepeatMaterial();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#deleteRepeatMateriel(com
	 * .bestway.common.Request, java.util.List)
	 */
	public void deleteRepeatMateriel(Request request, List list) {
		this.materialManageLogic.deleteRepeatMateriel(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.common.materialbase.action.aaaaaaa#checkEnterpriseBomVersionCount
	 * (com.bestway.common.Request)
	 */
	public boolean checkEnterpriseBomVersionCount(Request request) {
		return this.materialManageDao.checkEnterpriseBomVersionCount();
	}

	/**
	 * 保存系统参数设置资料
	 * 
	 * @param parameterSet
	 *            系统参数设置资料
	 */
	@AuthorityFunctionAnnotation(caption = "BOM管理方式设定-浏览", index = 0.01)
	public void checkBomStructureTypeAuthority(Request request) {
		// TODO Auto-generated method stub
		
	}

	@AuthorityFunctionAnnotation(caption = "BOM管理方式设定-修改", index = 0.02)
	public void saveValues(Request request, ParameterSet parameterSet) {
		this.materialManageDao.saveValues(parameterSet);
	}

	@AuthorityFunctionAnnotation(caption = "工厂物料主档-浏览", index = 2.31)
	public void checkEnterpriseMaterielByBrowser(Request request) {
	}

	@AuthorityFunctionAnnotation(caption = "工厂物料主档-修改", index = 2.35)
	public void checkEnterpriseMaterielByUpdate(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "工厂物料主档-删除", index = 2.36)
	public void checkEnterpriseMaterielByDelete(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "工厂物料主档-新增", index = 2.32)
	public void checkEnterpriseMaterielByAdd(Request request) {

	}

	// 单位用量计算
	public void accountUnitDosage(Request request) {
		this.materialManageLogic.accountUnitDosage();
	}

	public List findUnitCollate(Request request) {
		return this.materialManageDao.findUnitCollate();
	}

	public void deleteUnitCollate(Request request, UnitCollate obj) {
		this.materialManageDao.deleteUnitCollate(obj);
	}

	public UnitCollate saveUnitCollate(Request request, UnitCollate unit)
			throws DataAccessException {
		this.materialManageDao.saveUnitCollate(unit);
		return unit;
	}

	public void updateCustomsCurrRate(Request request, Date beginDate,
			Date endDate, Boolean exchangeRateIsNull) {
		this.materialManageLogic.updateCustomsCurrRates(beginDate, endDate,
				exchangeRateIsNull);
	}

	/**
	 * 查询BOM中的料件
	 * 
	 * @param materielType
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findMaterialBomMateriel(Request request, int index, int length,
			String property, Object value, Boolean isLike) {
		return this.materialManageDao.findMaterialBomMateriel(index, length,
				property, value, isLike);
	}

	/**
	 * 根据了件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	public List findMaterialBomByDetail(Request request, Materiel materiel) {
		return this.materialManageDao.findMaterialBomByDetail(materiel);
	}

	public String[] getCommNameSpec(Request request,
			BaseCustomsDeclarationCommInfo commInfo) {
		return materialManageLogic.getCommNameSpec(commInfo);
	}

	public Double findCurrRateByMG() { // 显示本币为美元的汇率表
		return materialManageDao.findCurrRateByMG();
	}

	public void executeBomImport(Request request, List headList) {
		this.materialManageLogic.executeBomImport(headList);
	}
	
	public void executeBomImportZX(Request request, List headList) {
		this.materialManageLogic.executeBomImportZX(headList);
	}

	public List checkBomImportFileData(Request request, List list,
			boolean isrePlace) {
		return materialManageLogic.checkBomImportFileData(list, isrePlace);
	}

	/**
	 * 根据父级料号、子件料号、版本号判断导入的数据中是否已经存在工厂BOM管理里
	 * 
	 * @param list
	 *            导入的数据
	 * @return
	 */
	public List findNotExistedBomManageFileData(Request request, List list,
			boolean isrePlace) {
		return materialManageLogic.findNotExistedBomManageFileData(list,
				isrePlace);
	}

	/**
	 * 保存客户供应商信息
	 */
	public void saveInputScmcoc(Request request, List ls, boolean isOverwrite) {
		this.materialManageLogic.saveInputScmcoc(ls, isOverwrite);
	}

	/**
	 * 保存客户供应商栏位控制参数
	 * 
	 * @param request
	 * @param scmCocControl
	 */
	public void saveScmCocControl(Request request, ScmCocControl scmCocControl) {
		this.materialManageDao.saveScmCocControl(scmCocControl);
	}

	/**
	 * 保存工厂司机信息
	 */
	public void saveImportDriverInfo(Request request, boolean isOverwrite,
			List ls2) {
		this.materialManageLogic.saveImportDriverInfo(ls2, isOverwrite);
	}

	/*
	 * 查询BOM料件备案记录
	 * 
	 * @param versionApply BOM版本备案记录
	 * 
	 * @return List 是MaterialBomDetailApply，BOM料件备案记录
	 */
	public List findMaterialBomDetailApply(Request request, String ptNo,
			Integer versionNo) {
		return materialManageDao.findMaterialBomDetailApply(ptNo, versionNo);
	}

	/**
	 * 根据成品查询BOM中的料件
	 * 
	 * @param ptno
	 * @return
	 */
	public List findMaterialBomByDetailByptno(Request request, String ptno,
			Integer version) {
		return this.materialManageDao.findMaterialBomByDetailByptno(ptno,
				version);
	}

	public ScmCoc findScmCocByname(Request request, String name) {
		return this.materialManageDao.findScmCocByname(name);
	}

	public List findMateriel(Request request) { // 物料主档显示所有数据
		return this.materialManageDao.findMateriel();
	}
	
	/**
	 * 新增BOM或者从BOM中插入BOM料件;
	 * 
	 * @param list
	 *            要插入的料件
	 * 
	 */
	public Map addMaterialBomVersionOrInsertMaterialBomDetail(Request request,
			MaterialBomVersion mersion, Integer version) {
		return this.materialManageLogic
				.addMaterialBomVersionOrInsertMaterialBomDetail(mersion,
						version);
	}

	/**
	 * 复制bom，工厂BOM管理
	 * 
	 * @param list
	 *            要插入的料件
	 * 
	 */
	public Map addMaterialBomVersionOrInsertMaterialBomDetail(Request request,
			List<EnterpriseBomManage> details, Integer version) {
		return this.materialManageLogic
				.addMaterialBomVersionOrInsertMaterialBomDetail(details,
						version);
	}

	/**
	 * 根据参数查找来料、三资设备是否存在
	 * 
	 * @param request
	 * @param emsNo
	 * @return
	 */
	public Boolean findAllFixByNo(Request request, String emsNo, Integer fixType) {
		return this.materialManageDao.findAllFixByNo(emsNo, fixType);
	}

	public List findUnit(Request request) {
		return this.materialManageDao.findUnit();
	}

	/*
	 * 获取是供应商
	 */
	public List findScmCocsIsWork(Request request) {
		return this.materialManageDao.findScmCocsIsWork();
	}

	/*
	 * 根据加工公司名称查找海关注册公司
	 */
	public Brief findBrief(Request request, String name) {
		return this.materialManageDao.findBrief(name);
	}

	/**
	 * 查找报关常用物料里面的料号
	 */
	public List findPtNo(Request request) {
		return this.materialManageDao.findPtNo();
	}

	/**
	 * 根据帐册序号返回限制商品
	 */
	public RestirictCommodity findRerictCommodity(Request request,
			Boolean ImpExpType, String seqNum) {
		return this.materialManageDao.findRerictCommodity(ImpExpType, seqNum);
	}
	/**
	 * 查找商品版本是否禁用
	 * 
	 * @param type
	 *            物料类别
	 * @return
	 */
	public CommdityForbid findCommdityForbid(Request request,String type, String seqNum, String version){
		return materialManageDao.findCommdityForbid(type, seqNum, version);
		
	}
	/**
	 * 取得版本号
	 * 
	 * @param id
	 *            电子帐册成品id
	 * @return 没有被禁止的与指定的id匹配的电子帐册成品的版本号
	 */
	public List findVersionNo(Request request,Integer seqNum, Integer version){
		return materialManageDao.findVersionNo(seqNum,version);
	}
	
	/**
	 * 返回报关单物料信息
	 */
	public Double findCustomsDeclarationCommInfo(Request request,
			Boolean ImpExpType, String seqNum,
			BaseCustomsDeclarationCommInfo CustomsDeclarationCommInfo) {
		return this.materialManageLogic.findCustomsDeclarationCommInfo(
				ImpExpType, seqNum, CustomsDeclarationCommInfo);
	}
	
	public List findCustomsDeclarationCommInfos(Request request, Boolean isMaterial,
			List<Integer> seqNums,
			BaseCustomsDeclarationCommInfo CustomsDeclarationCommInfo){
		return this.materialManageLogic.findCustomsDeclarationCommInfos(
				isMaterial, seqNums, CustomsDeclarationCommInfo);
	}

	/**
	 * 返回报关单归并前信息
	 */
	public Double findCustomsBeforeComInfo(Request request, Boolean ImpExpType,
			String seqNum, String types) {
		return this.materialManageDao.findCustomsBeforeComInfo(ImpExpType,
				seqNum, types);
	}

	/**
	 * 返回所有的工厂BOM管理
	 * 
	 * @return List 工厂BOM管理
	 */
	public List findAllEnterpriseBomManage(Request request) {
		return this.materialManageDao.findAllEnterpriseBomManage();
	}

	/**
	 * 根据物料类型抓取工厂物料主档资料
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialByType(List names) {
		return this.materialManageDao.findEnterpriseMaterialByType(names, null);
	}

	/**
	 * 获取电子帐册归并版本
	 * 
	 * @param request
	 * @return
	 */
	public List findVersion(Request request,
			AtcMergeBeforeComInfo beforeCommInfo) {
		return this.materialManageDao.findVersion(beforeCommInfo);
	}

	/**
	 * 统计工厂料件被使用的成品
	 * 
	 * @param request
	 * @param materiel
	 * @return
	 */
	public List findEnterpriseBomManageByDetail(Request request,
			EnterpriseMaterial materiel) {
		return materialManageLogic.findEnterpriseBomManageByDetail(materiel);
	}

	/**
	 * 通过id 查找工厂BOM子件
	 * 
	 * @param id
	 * @return
	 */
	public List findEnterpriseBomManagerById(String id) {
		return materialManageLogic.findEnterpriseBomManagerById(id);
	}

	public List checkEnterpriseBomManageIsRight() {
		return materialManageLogic.checkEnterpriseBomManageIsRight();
	}

	/**
	 * 查找客户供应商栏位控制参数
	 * 
	 * @param request
	 * @return
	 */
	public List findScmCocControl(Request request) {
		return materialManageDao.findScmCocControl();
	}

	/**
	 * 根据表明查找所有
	 * 
	 * @param tableName
	 * @return
	 */
	public List findList(String tableName) {
		return materialManageDao.findlistByName(tableName);
	}

	/**
	 * 批量修改报关、工厂物料的单位折算与单重
	 * 
	 * @param list
	 * @param unitConver
	 * @param ptNetWight
	 */
	public List updateMateriel(Request request, List list, Double unitConver,
			Double ptNetWight) {
		List listResult=new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			InnerMergeData data = (InnerMergeData) list.get(i);
			Materiel materiel = data.getMateriel();
			materiel.setUnitConvert(unitConver);
			materiel.setPtNetWeight(ptNetWight);
			materiel = materialManageLogic.saveMateriel(materiel);
			listResult.add(data);
		}
		return listResult;
	}
	
	/**
	 * 根据料号查找报关常用工厂BOM成品资料列表
	 * 
	 * @param ptNos
	 *            料号
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	public List<MaterialBomMaster> findMaterielBomMasterByPtNos(Request request, String... ptNos){
		return materialManageLogic.findMaterielBomMasterByPtNos(ptNos);
	}

	/**
	 * 查找所有物料主档料号
	 */
	public List findAllPtNo() {
		return materialManageDao.findAllPtNo();
	}

	public void saveList(List list) {
		for (int i = 0; i < list.size(); i++) {
			EnterpriseMaterial e = ((TempEnterpriseMaterial) list.get(i))
					.getEbm();
			e.setCompany(CommonUtils.getCompany());
			materialManageDao.saveOrUpdate(e);
			List mlist = materialManageDao.findMaterialByptNo(e.getPtNo());
			if (mlist.size() > 0) {
				Materiel materiel = (Materiel) mlist.get(0);
				String id = materiel.getId();
				try {
					PropertyUtils.copyProperties(materiel, e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				materiel.setId(id);
				materialManageLogic.saveMateriel(materiel);
			}
		}

	}

	public List findMaterialVersion(Request request, String ptNo) {
		List list = materialManageDao.findMaterialBomVersionByPtNo(ptNo);
		return list;
	}
	/**
	 * 检查电子帐册归并关系是否禁用
	 * @param ptNo
	 * @return
	 */
	public List findInnerMergeDataByptNoIsForbid(Request request, String ptNo) {
		return materialManageDao.findInnerMergeDataByptNoIsForbid(ptNo);
	}
	/**
	 * 根据企业版本得出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getVersion(Request request,String ptNo, String cmpVersion){
		return materialManageDao.getVersion(ptNo, cmpVersion);
		
	}

	@Override
	public List<ScmCoc> findScmCocIsCustomer(Request request) {
		List list = this.materialManageDao.findScmCocIsCustomer();
		List customers = new ArrayList<ScmCoc>();
		for (int i = 0; i <list.size(); i++) {
			ScmCoc sc = new ScmCoc();
			Object[]  obj = (Object[]) list.get(i);
			sc.setId(obj[0].toString());
			sc.setCode(obj[1].toString());
			sc.setName(obj[2]==null?null:obj[2].toString());
			customers.add(sc);
		}
		return customers;
	}

	@Override
	public List<ScmCoc> findScmCocIsManufacturer(Request request) {
		List list = this.materialManageDao.findScmCocIsManufacturer();
		List manufacturers = new ArrayList<ScmCoc>();
		for (int i = 0; i <list.size(); i++) {
			ScmCoc sc = new ScmCoc();
			Object[]  obj = (Object[]) list.get(i);
			sc.setId(obj[0].toString());
			sc.setCode(obj[1].toString());
			sc.setName(obj[2].toString());
			manufacturers.add(sc);
		}
		return manufacturers;
	}
	
	public void updateMaterialInfo(Request request,List materielList){
		materialManageDao.updateMeterialInfo(materielList); 
	}
	
	public List findEnterpriseMaterials(Request request) { // 物料主档显示所有数据
		return this.materialManageDao.findEnterpriseMaterials();
	}
	
	public List findScmCoi(Request request) { // 物料主档显示所有数据
		return this.materialManageDao.findScmCoi();
	}
	
	/**
	 * 检查国内车牌是否存在
	 * @param homeCard
	 * @return
	 */
	public Boolean checkMotorCodeHomeCard(Request request,MotorCode motorCode){
		return this.materialManageDao.checkMotorCodeHomeCard(motorCode);
	}
}
