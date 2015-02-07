package com.bestway.common.materialbase.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.manualdeclare.entity.CommdityForbid;
import com.bestway.bcus.manualdeclare.entity.RestirictCommodity;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
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
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * 料件管理操作接口
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public interface MaterialManageAction {

	/**
	 * 保存物流实体
	 * 
	 * @param request
	 * @param obj
	 * @return
	 */
	ParaSet saveParaSet(Request request, ParaSet obj);

	/**
	 * 删除仓库设置
	 * 
	 * @param request
	 * @param ware
	 */
	void deleteWare(Request request, WareSet ware);

	/**
	 * 删除司机资料
	 * 
	 * @param request
	 * @param motor
	 */
	void deleteMotorCode(Request request, MotorCode motor);

	/**
	 * 删除计量单位
	 * 
	 * @param request
	 * @param unit
	 */
	void deleteUnit(Request request, CalUnit unit);

	/**
	 * 删除币制
	 * 
	 * @param request
	 * @param curr
	 */
	void deleteCurr(Request request, CurrCode curr);

	/**
	 * 删除税制代码资料
	 * 
	 * @param request
	 * @param share
	 */
	void deleteShare(Request request, ShareCode share);

	/**
	 * 删除物料主档
	 * 
	 * @param request
	 * @param materiel
	 */
	void deleteMateriel(Request request, Materiel materiel);

	/**
	 * 检查料件使用情况
	 * @param request
	 * @param materiel
	 * @return
	 */
	String checkMaterielUsingCondition(Request request,Materiel materiel);
	/**
	 * 显示汇率所有数据
	 * 
	 * @param request
	 * @return
	 */
	List findCurrRate(Request request);

	/**
	 * 取得汇率
	 * 
	 * @param sourCurr
	 *            本地币别
	 * @param destCurr
	 *            外地币别
	 * @param createDate
	 *            创建日期
	 * @return 汇率
	 */
	double findExchangeRate(Request request, Curr sourCurr, Curr destCurr,
			Date createDate);

	Date findExchangeRateData(Request request, Curr sourCurr, Curr destCurr,
			Date createDate);

	// 返回司机资料设置
	MotorCode findMotorCodeByCode(Request request, String homeCard);

	/**
	 * 通过海关编码返回司机资料设置
	 * 
	 * @param request
	 * @param complex
	 *            海关编码
	 * @return
	 */
	MotorCode findMotorCodeComplex(Request request, String complex);

	/**
	 * 返回司机资料设置
	 * 
	 * @param request
	 * @return
	 */
	List findMotorCodeComplex(Request request);

	/**
	 * 根据帐册序号返回限制商品
	 * 
	 * @param request
	 * @param ImpExpType
	 * @param seqNum
	 * @return
	 */
	RestirictCommodity findRerictCommodity(Request request, Boolean ImpExpType,
			String seqNum);
	/**
	 * 查找商品版本是否禁用
	 * 
	 * @param type
	 *            物料类别
	 * @return
	 */
	public CommdityForbid findCommdityForbid(Request request,String type, String seqNum, String version);
	/**
	 * 取得版本号
	 * 
	 * @param id
	 *            电子帐册成品id
	 * @return 没有被禁止的与指定的id匹配的电子帐册成品的版本号
	 */
	public List findVersionNo(Request request,Integer seqNum, Integer version);
	/**
	 * 返回报关单物料信息
	 * 
	 * @param request
	 * @param ImpExpType
	 * @param seqNum
	 * @param CustomsDeclarationCommInfo
	 * @return
	 */
	Double findCustomsDeclarationCommInfo(Request request, Boolean ImpExpType,
			String seqNum,
			BaseCustomsDeclarationCommInfo CustomsDeclarationCommInfo);

	/**
	 * 返回报关单物料信息
	 * 
	 * @param request
	 * @param ImpExpType
	 * @param seqNum
	 * @param CustomsDeclarationCommInfo
	 * @return
	 */
	List findCustomsDeclarationCommInfos(Request request, Boolean isMaterial,
			List<Integer> seqNums,
			BaseCustomsDeclarationCommInfo CustomsDeclarationCommInfo);
	
	/**
	 * 返回报关单归并前信息
	 * 
	 * @param request
	 * @param ImpExpType
	 * @param seqNum
	 * @param types
	 * @return
	 */
	Double findCustomsBeforeComInfo(Request request, Boolean ImpExpType,
			String seqNum, String types);

	/**
	 * 保存事业部
	 * 
	 * @param request
	 * @param obj
	 * @return
	 */
	public ProjectDept saveProjectDept(Request request, ProjectDept obj);

	/**
	 * 查找本地币制所对应的汇率设置
	 * 
	 * @param currCode
	 * @return
	 */
	List findCurrRate(Request request, String currCode);

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bestway.common.factorymerge.logicommon.action.LogiCommonAction#
	 * findUserByLoginName(java.lang.String)
	 */
	WareSet findWarerByCode(Request request, String code);

	WareSet findWarerByName(Request request, String name);

	CalUnit findUnitByCode(Request request, String code);

	CurrRate saveCurrRate(Request request, CurrRate currRate);

	MotorCode saveMotorCode(Request request, MotorCode motor);

	void deleteCurrRate(Request request, CurrRate currRate);

	CalUnit findUnitByName(Request request, String name);

	CurrCode findCurrByCode(Request request, String code);

	CurrCode findCurrByName(Request request, String name);

	ShareCode findTaxaTionByCode(Request request, String code);

	ShareCode findTaxaTionByName(Request request, String name);

	Materiel findMaterielByPtNo(Request request, String ptNo);

	List findWareSet(Request request);

	List findCalUnit(Request request);

	List findCurrCode(Request request);

	List findTaxaTion(Request request);

	List findMateriel(Request request, String materielType, int index,
			int length, String property, Object value, Boolean isLike);

	WareSet saveWare(Request request, WareSet ware);

	CalUnit saveUnit(Request request, CalUnit unit);

	CurrCode saveCurr(Request request, CurrCode curr);

	ShareCode saveShare(Request request, ShareCode share);

	Materiel saveMateriel(Request request, Materiel materiel);
	
	/**
	 * 批量修改报关、工厂物料的单位折算与单重
	 * @param list
	 * @param unitConver
	 * @param ptNetWight
	 */
	public List updateMateriel(Request request,List list,Double unitConver,Double ptNetWight);

	List findScmManufacturer(Request request);

	List findScmCocsfacturerByXiaok(Request request);

	List findScmCocscustomerByXiaok(Request request);

	void findMaterielforControlEdit(Request request);

	void findMaterielforControlDel(Request request);

	void findMaterielforControlAdd(Request request);

	/*
	 * 返回所有国家资料
	 */
	List findSysAreas(Request request);

	SysArea findSysAreaById(Request request, String id);

	SysArea findSysAreaByCode(Request request, String code);

	SysArea saveSysArea(Request request, SysArea area);

	void deleteSysArea(Request request, SysArea area);

	/*
	 * 返回所有物料类别
	 */
	List findScmCois(Request request);

	ScmCoi findScmCoiById(Request request, String id);

	ScmCoi findScmCoiByCode(Request request, String code);

	ScmCoi saveScmCoi(Request request, ScmCoi coi);

	void deleteScmCoi(Request request, ScmCoi coi);

	/**
	 * 返回所有客户/供应商/合作伙伴资料
	 */
	List findScmCocs(Request request);

	List findScmManuFactoryOut(Request request);

	List findScmManuFactoryIn(Request request);

	List findScmManucustomer(Request request);

	List findMotorCode(Request request);

	List findMotorCode(Request request, int index, int length);

	ScmCoc findScmCocById(Request request, String id);

	ScmCoc findScmCocByCode(Request request, String code);

	ScmCoc findScmCocByname(Request request, String name);

	/**
	 * 保存客户供应商资料
	 * 
	 * @param request
	 * @param coc
	 * @return
	 */
	ScmCoc saveScmCoc(Request request, ScmCoc coc);

	void deleteScmCoc(Request request, ScmCoc coc);

	/**
	 * 查找物料主档来自类别
	 */
	List findMaterielByType(Request request, String materielType);

	// /* 查找成品或半成品
	// *
	// */
	// public List findProductOrHalf(Request request, Boolean isHalf) {
	// return this.getCommonBaseCodeDao().findProductOrHalf(isHalf);
	// }
	List findMaterielByMaterielType(Request request, String materielType);

	UnitWaste saveUnitWaste(Request request, UnitWaste object);

	List findUnitWaste(Request request, String exgNo);

	UnitWaste saveBomImgDetail(Request request, UnitWaste object);

	void deleteUnitWaste(Request request, UnitWaste object);

	void deleteBomImgDetail(Request request, UnitWaste object);

	void deleteAllBom(Request request, String exgNo);

	List findCustomsUser(Request request);

	void deleteCustomsUser(Request request, CustomsUser motor);

	CustomsUser saveCustomsUser(Request request, CustomsUser obj);

	String findCustomsUserTel(Request request, String name);

	List findParaSet(Request request);

	void deleteParaSet(Request request, ParaSet obj);

	String findParaSetBytype(Request request, int type, String beginValue);

	// /**
	// * 返回产品结构管理
	// */
	// public List findMaterBomManage(Request request, String materielType) {
	// return materialManageDao.findMaterBomManage(materielType);
	// }
	/**
	 * 查找不在工厂BOM管理父件、料件里的工厂物料主档资料
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	List findEnterpriseMaterialNotInMaster(Request request);

	/**
	 * 查找不在工厂BOM管理父件、料件里的工厂物料主档资料(料件)
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	@AuthorityFunctionAnnotation(caption = "工厂BOM管理-浏览", index = 3.0)
	public List findEnterpriseMaterialNotInMasterMateriel(Request request);

	/**
	 * 查找 工厂物料主档 资料，但是不在 工厂BOM管理 里由给定的父级料号和父件版本号所确定的子件的子级料号里（不是指定半成品的子件），
	 * 但要去掉循环调用的。
	 * 
	 * @param request
	 *            请求控制
	 * @param parentNo
	 *            父件料号
	 * @param version
	 *            父件版本号
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	List findEnterpriseMaterialNotInDetail(Request request, String parentNo,
			Integer version);

	/**
	 * 构造工厂BOM管理父件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是TempEntBomMaster型，临时的工厂BOM管理父件资料
	 */
	List findEnterpriseBomMaster(Request request, int index, int length,
			String property, Object value, Boolean isLike);
	
	
	/**
	 * 构造工厂BOM管理父件资料(震兴)
	 * 
	 * @param request
	 *            请求控制
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是TempEntBomMaster型，临时的工厂BOM管理父件资料
	 */
	List findEnterpriseBomMasterZX(Request request, int index, int length,
			String property, Object value, Boolean isLike);

	/**
	 * 返回报关常用工厂BOM版本
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	List findExistMaterialBomVersion(Request request);

	/**
	 * 返回报关常用工厂BOM版本里的数目
	 * 
	 * @param request
	 *            请求控制
	 * @return int 报关常用工厂BOM版本里的数目
	 */
	int findMaxMaterialBomVersion(Request request);

	/**
	 * 构造工厂BOM管理版本
	 * 
	 * @param request
	 *            请求控制
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return List 是TempEntBomVersion型，临时的工厂BOM管理版本资料
	 */
	List findEnterpriseBomVersion(Request request, String parentNo,
			int bomStructureType);
	
	
	/**
	 * 根据料号查找报关常用工厂BOM版本
	 * 
	 * @param request
	 *            请求控制
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return List 是TempEntBomVersion型，临时的工厂BOM管理版本资料
	 */
	List findMaterialBomVersion(Request request, String parentNo, int bomStructureType);
	
	
	/**
	 * 根据料号查找报关常用工厂BOM版本时间
	 * 
	 * @param request
	 *            请求控制
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return List 是TempEntBomVersion型，临时的工厂BOM管理版本资料
	 */
	List findMaterialBomVersionDate(Request request, String parentNo);
	
	
	/**
	 * 构造工厂BOM管理版本(震兴)
	 * 
	 * @param request
	 *            请求控制
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return List 是TempEntBomVersion型，临时的工厂BOM管理版本资料
	 */
	List findEnterpriseBomVersionZX(Request request, String parentNo,
			int bomStructureType);

	/**
	 * 构造工厂BOM管理子件
	 * 
	 * @param request
	 *            请求控制
	 * @param parentNo
	 *            父件料号
	 * @param bomStructureType
	 *            版本类型
	 * @return HashMap<String, List<Object>> key为版本号＋有效日期＋失效日期，value为工厂BOM管理子件资料
	 */
	HashMap<String, List<Object>> findEnterpriseBomDetail(Request request,
			String parentNo, int bomStructureType);

	/**
	 * 构造工厂BOM管理子件
	 * 
	 * @param request
	 *            请求控制
	 * @param parentNo
	 *            父件料号
	 */
	List findEnterpriseBomDetail(Request request, String parentNo);

//	/**
//	 * 工厂所有BOM
//	 * @param request
//	 * @return
//	 */
//	List findAllEnterpriseBomDetail(Request request);
	
	/**
	 * 从工厂物料主档资料里增加工厂BOM管理父料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 * @return List 是TempEntBomMaster型，临时的工厂BOM管理父件资料
	 */
	List addEnterpriseBomMaster(Request request, List<EnterpriseMaterial> list);

	/**
	 * 增加工厂BOM管理版本
	 * 
	 * @param request
	 *            请求控制
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 */
	void addEnterpriseBomVersion(Request request, TempEntBomMaster tempMaster,
			TempEntBomVersion tempVersion);

	/**
	 * 增加工厂BOM管理子件
	 * 
	 * @param request
	 *            请求控制
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 * @return List 是EnterpriseBomManage型，工厂BOM管理子件资料
	 */
	List addEnterpriseBomDetail(Request request, TempEntBomMaster tempMaster,
			TempEntBomVersion tempVersion, List<EnterpriseMaterial> list);

	/**
	 * 删除工厂BOM管理父料
	 * 
	 * @param request
	 *            请求控制
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 */
	void deleteEnterpriseBomMaster(Request request, TempEntBomMaster tempMaster);

	/**
	 * 删除工厂BOM管理版本
	 * 
	 * @param request
	 *            请求控制
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 */
	void deleteEnterpriseBomVersion(Request request,
			TempEntBomMaster tempMaster, TempEntBomVersion tempVersion);

	/**
	 * 增加工厂BOM管理子件
	 * 
	 * @param request
	 *            请求控制
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 * @param list
	 *            是EnterpriseBomManage型，工厂BOM管理子件资料
	 */
	void deleteEnterpriseBomDetail(Request request,
			TempEntBomMaster tempMaster, TempEntBomVersion tempVersion,
			List<EnterpriseBomManage> list);

	/**
	 * 增加工厂BOM管理版本
	 * 
	 * @param request
	 *            请求控制
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 */
	void saveEnterpriseBomVersion(Request request, TempEntBomMaster tempMaster,
			TempEntBomVersion tempVersion);

	/**
	 * 增加工厂BOM管理版本(震兴)
	 * 
	 * @param request
	 *            请求控制
	 * @param tempMaster
	 *            临时的工厂BOM管理父件资料
	 * @param tempVersion
	 *            临时的工厂BOM管理版本资料
	 */
	void saveEnterpriseBomVersionZX(Request request,
			TempEntBomMaster tempMaster, TempEntBomVersion tempVersion);
	/**
	 * 保存工厂BOM管理子件
	 * 
	 * @param request
	 *            请求控制
	 * @param detail
	 *            工厂BOM管理子件
	 */
	EnterpriseBomManage saveEnterpriseBomManage(Request request,
			EnterpriseBomManage detail);
	
	/**
	 * 保存工厂BOM管理子件(震兴)
	 * 修改净重
	 * @param request
	 *            请求控制
	 * @param detail
	 *            工厂BOM管理子件
	 */
	EnterpriseBomManage saveEnterpriseBomManageZX(Request request,
			EnterpriseBomManage detail);
	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 * @param version
	 *            父件版本号
	 */
	void transferBomFromEnterpriseNoVersionNoDate(Request request, List list,
			Integer version);
	
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
	void transferBomFromEnterpriseNoVersionNoDate(Request request, List list,
			Integer version, boolean isMerge);

	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 */
	void transferBomFromEnterpriseByDate(Request request, List list);

	/**
	 * 从工厂BOM管理中自动导入到报关常用工厂BOM中
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 */
	void transferBomFromEnterpriseByVersion(Request request, List list);

	/**
	 * 根据物料类型抓取工厂物料主档资料
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	List findEnterpriseMaterial(Request request);

	EnterpriseMaterial findEnterpriseMaterial(Request request, String ptNo);

	/**
	 * 工厂物料主档显示所有数据来自工厂物料主档的类别List
	 * 
	 * @param request
	 *            请求控制
	 * @param materielTypeList
	 *            物料类型List
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	List findEnterpriseMaterial(Request request, List materielTypeList);

	/**
	 * 根据条件抓取工厂物料主档资料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	List findEnterpriseMaterial(Request request, String materielType,
			int index, int length, String property, Object value, Boolean isLike);

	/**
	 * 抓取所有工厂BOM
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	List findAllEnterpriseBomDetail(Request request, int index,
			int length, String property, Object value, Boolean isLike);
	
	/**
	 * 分页查询查询来自类型的总数
	 */
	public Long findEnterpriseMaterialCount(Request request, String materielType,
			int index, int length, String property, Object value, Boolean isLike);

	/**
	 *分页查询工厂BOM的总数
	 */
	public Long findAllEnterpriseBomDetailCount(Request request, int index,
			int length, String property, Object value, Boolean isLike);
	/**
	 * 根据id来查询工厂物料主档资料
	 * 
	 * @param request
	 *            请求控制
	 * @param id
	 *            工厂物料主档Id
	 * @return EnterpriseMaterial 工厂物料主档资料
	 */
	EnterpriseMaterial findEnterpriseMaterialById(Request request, String id);

	/**
	 * 保存工厂物料主档资料
	 * 
	 * @param request
	 *            请求控制
	 * @param materiel
	 *            工厂物料主档资料
	 */
	EnterpriseMaterial saveEnterpriseMaterial(Request request,
			EnterpriseMaterial materiel);

	/**
	 * 删除工厂物料主档资料
	 * 
	 * @param request
	 *            请求控制
	 * @param materiel
	 *            工厂物料主档
	 */
	void deleteEnterpriseMaterial(Request request, EnterpriseMaterial materiel);

	/**
	 * 根据料号查找工厂物料主档资料
	 * 
	 * @param request
	 *            请求控制
	 * @param ptNo
	 *            料号
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	EnterpriseMaterial findEnterpriseMaterialByPtNo(Request request, String ptNo);

	/**
	 * 查找工厂物料主档里成品或是委外的半成品，但是不在报关常用工厂物料里
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是EnterpriseMaterial型，工厂物料主档里成品或是委外的半成品
	 */
	List findEnterpriseMaterialForMaterial(Request request, String materielType);

	/**
	 * 根据给定的工厂物料主档资料，在报关常用工厂物料里新增资料
	 * 
	 * @param request
	 *            请求控制
	 * @param lsMateriel
	 *            是EnterpriseMaterial型，工厂物料主档资料
	 * @return List 是Materiel型，报关常用工厂物料
	 */
	List addCustomMaterial(Request request, List lsMateriel);

	/**
	 * 在工厂物料主档里查找成品或是委外的半成品，但是不在报关常用工厂BOM成品里存在
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	List findTopProductBomManage(Request request);

	/**
	 * 根据版本号在工厂物料主档里查找成品或是委外的半成品，但是不在报关常用工厂BOM成品里存在
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	List findTopProductBomManage(Request request, Integer version);

	/**
	 * 在工厂物料主档里查找成品或是委外的半成品，但是要在工厂BOM管理父件、报关常用工厂BOM成品里不同时存在的
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是EnterpriseMaterial型，工厂物料主档里成品或是委外的半成品
	 */
	List findTopProductBomManageForVersion(Request request);

	/**
	 * 根据条件查找报关常用工厂BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	List findMaterielBomMaster(Request request, int index, int length,
			String property, Object value, Boolean isLike);

	/**
	 * 根据报关常用工厂BOM版本查找报关常用工厂BOM里的料件
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	List findMaterielBomDetail(Request request, MaterialBomVersion version);

	/**
	 * 保存报关常用工厂BOM料件
	 * 
	 * @param request
	 *            请求控制
	 * @param materielBomDetail
	 *            报关常用工厂BOM料件
	 */
	MaterialBomDetail saveMaterielBomDetail(Request request,
			MaterialBomDetail materielBomDetail);

	/**
	 * 删除报关常用工厂BOM料件
	 * 
	 * @param request
	 *            请求控制
	 * @param materielBomDetail
	 *            报关常用工厂BOM料件
	 */
	void deleteMaterielBomDetail(Request request,
			MaterialBomDetail materielBomDetail);

	/**
	 * 根据报关常用工厂BOM成品查找报关常用工厂BOM版本
	 * 
	 * @param request
	 *            请求控制
	 * @param master
	 *            报关常用工厂BOM成品
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	List findMaterielBomVersion(Request request, MaterialBomMaster master);

	/**
	 * 保存报关常用工厂BOM版本
	 * 
	 * @param request
	 *            请求控制
	 * @param materielBomVersion
	 *            报关常用工厂BOM版本
	 */
	MaterialBomVersion saveMaterielBomVersion(Request request,
			MaterialBomVersion materielBomVersion);

	/**
	 * 根据版本号、物料号码查找报关常用工厂BOM料件，但是要它的父件来自报关常用工厂物料
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @param ptPart
	 *            料号
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	List findMaterielBomDetail(Request request, Integer version, String ptPart);

	/**
	 * 根据版本号查找报关常用工厂BOM里的料件
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @return List 是MaterialBomDetail型，报关常用工厂BOM料件
	 */
	List findMaterielBomDetail(Request request, Integer version);

	/**
	 * 查找报关常用工厂物料里的成品资料，但是不在报关常用工厂BOM成品里查找
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Materiel型，报关常用工厂物料
	 */
	List findNotInBomMasterMaterial(Request request);

	List findNotInBomMasterMaterial2(Request request);

	/**
	 * 根据版本号查找报关常用工厂物料里的料件资料，但是不在报关常用工厂BOM料件里查找
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @return List 是报关常用工厂物料型，Materiel
	 */
	List findNotInBomDetailMaterial(Request request, MaterialBomVersion version);

	/**
	 * 根据报关常用工厂物料保存报关常用工厂BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param lsCustomMaterial
	 *            是Materiel型，报关常用工厂物料
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	List saveMaterialBomMaster(Request request, List lsCustomMaterial);

	/**
	 * 根据报关常用工厂BOM版本、报关常用工厂物料保存报关常用工厂BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @param lsCustomDetail
	 *            是Materiel型，报关常用工厂物料
	 * @return List 是型，
	 */
	List saveMaterialBomDetail(Request request, MaterialBomVersion version,
			List lsCustomDetail);

	/**
	 * 保存报关常用工厂BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @param lsCustomDetail
	 *            是Materiel型，报关常用工厂物料
	 * @return List 是型，
	 */
	MaterialBomDetail saveMaterialBomDetail(Request request,
			MaterialBomDetail detail);

	/**
	 * 保存报关常用工厂BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param master
	 *            报关常用工厂BOM成品资料
	 */
	void deleteMaterielBomMaster(Request request, MaterialBomMaster master);

	/**
	 * 根据报关常用工厂BOM版本删除报关常用工厂BOM成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 */
	void deleteMaterielBomVersion(Request request, MaterialBomVersion version);

	/**
	 * 根据父级料号、父件版本号查找工厂BOM管理版本
	 * 
	 * @param request
	 *            请求控制
	 * @param parentNo
	 *            父级料号
	 * @param version
	 *            父件版本号
	 * @return List 是EnterpriseBomVersion型，工厂BOM管理版本
	 */
	List findEnterpriseBomVersionByVersion(Request request, String parentNo,
			Integer version);

	
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
			Integer version);
	
	/**
	 * 根据报关常用工厂BOM成品、版本号查找报关常用工厂BOM版本
	 * 
	 * @param request
	 *            请求控制
	 * @param master
	 *            报关常用工厂BOM成品
	 * @param version
	 *            版本号
	 * @return List 是型，报关常用工厂BOM版本
	 */
	List findMaterielBomVersionByVersion(Request request,
			MaterialBomMaster master, Integer version);
	
	
	/**
	 * 根据料号查找报关常用工厂BOM成品资料列表
	 * 
	 * @param ptNos
	 *            料号
	 * @return List 是MaterialBomMaster型，报关常用工厂BOM成品
	 */
	public List<MaterialBomMaster> findMaterielBomMasterByPtNos(Request request, String... ptNos);

	/**
	 * 根据版本号抓取不重复的报关常用工厂BOM版本id
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            父件版本号
	 * @return List 是MaterialBomVersion型，报关常用工厂BOM版本
	 */
	List findMaterialBomVersionId(Request request, Integer version);

	/**
	 * 批量更新报关常用工厂BOM料件的损耗率
	 * 
	 * @param request
	 *            请求控制
	 * @param versionNo
	 *            版本号
	 * @param pFourCode
	 *            成品四码
	 * @param mFourCode
	 *            料件四码
	 * @param waste
	 *            损耗率
	 * @param isByUnitUsed
	 *            true为更新单耗，false为更新单项用量
	 */
	void updateMaterialBomWaste(Request request, Integer versionNo,
			String pFourCode, String mFourCode, Double waste,
			boolean isByUnitUsed);

	/**
	 * 根据物料类型查找内部归并的四码
	 * 
	 * @param request
	 *            请求控制
	 * @param materialType
	 *            物料类型
	 * @return List 内部归并的四码
	 */
	List findInnerMergeFourCodeByMaterialType(Request request,
			String materialType);

	/**
	 * 根据物料类型自动初始化报关常用工厂物料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param number
	 *            要初始化的个数
	 */
	void initMateriel(Request request, String materielType, int number);

	/**
	 * 自动初始化报关常用工厂BOM料件资料
	 */
	void initBom(Request request);

	/**
	 * 根据料号查找重复的报关常用工厂物料资料
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempRepeatMateriel型，临时重复的报关常用工厂物料资料
	 */
	List findRepeatMaterial(Request request);

	/**
	 * 删除重复的报关常用工厂物料资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是TempRepeatMateriel型，临时重复的报关常用工厂物料资料
	 */
	void deleteRepeatMateriel(Request request, List list);

	/**
	 * 判断 工厂BOM管理 里是否完全没有版本，ture为是没有，false为存在有
	 * 
	 * @param request
	 *            请求控制
	 * @return boolean ture为是没有，false为存在有
	 */
	boolean checkEnterpriseBomVersionCount(Request request);

	/**
	 * 保存系统参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @param parameterSet
	 *            系统参数设置资料
	 */
	void saveValues(Request request, ParameterSet parameterSet);

	void checkEnterpriseMaterielByBrowser(Request request);

	void checkEnterpriseMaterielByUpdate(Request request);

	void checkEnterpriseMaterielByDelete(Request request);

	void checkEnterpriseMaterielByAdd(Request request);

	List findProjectDept(Request request);

	void deleteProjectDept(Request request, ProjectDept obj);

	void accountUnitDosage(Request request);

	List findUnitCollate(Request request);

	void deleteUnitCollate(Request request, UnitCollate obj);

	UnitCollate saveUnitCollate(Request request, UnitCollate unit);

	void updateCustomsCurrRate(Request request, Date beginDate, Date endDate,
			Boolean exchangeRateIsNull);

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
	List findMaterialBomMateriel(Request request, int index, int length,
			String property, Object value, Boolean isLike);

	/**
	 * 根据了件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	List findMaterialBomByDetail(Request request, Materiel materiel);

	public MotorCode saveMotorCodeToCustoms(Request request, MotorCode motor);

	public String[] getCommNameSpec(Request request,
			BaseCustomsDeclarationCommInfo commInfo);

	public Double findCurrRateByMG();

	/**
	 * BOM导入
	 * 
	 * @param temp
	 * @return
	 */
	public void executeBomImport(Request request, List headList);
	
	
	/**
	 * BOM导入（震兴）
	 * 
	 * @param temp
	 * @return
	 */
	public void executeBomImportZX(Request request, List headList);
	
	
	public List checkBomImportFileData(Request request, List list,
			boolean isrePlace);

	/**
	 * 根据父级料号、子件料号、版本号判断导入的数据中是否已经存在工厂BOM管理里
	 * 
	 * @param list
	 *            导入的数据
	 * @return
	 */
	public List findNotExistedBomManageFileData(Request request, List list,
			boolean isrePlace);

	/**
	 * 保存客户供应商资料 request ls
	 */
	public void saveInputScmcoc(Request request, List ls, boolean isOverwrite);

	/**
	 * 保存客户供应商栏位控制参数
	 * 
	 * @param request
	 * @param scmCocControl
	 */
	public void saveScmCocControl(Request request, ScmCocControl scmCocControl);

	/**
	 * 查找客户供应商栏位控制参数
	 * 
	 * @param request
	 * @return
	 */
	public List findScmCocControl(Request request);

	/**
	 * 保存外部倒入的工厂司机资料
	 */
	public void saveImportDriverInfo(Request request, boolean isOverwrite,
			List ls2);

	/**
	 * 查询BOM料件备案记录
	 * 
	 * @param versionApply
	 *            BOM版本备案记录
	 * @return List 是MaterialBomDetailApply，BOM料件备案记录
	 */
	List findMaterialBomDetailApply(Request request, String ptNo,
			Integer versionNo);

	/**
	 * 根据成品查询BOM中的料件
	 * 
	 * @param ptno
	 * @return
	 */
	List findMaterialBomByDetailByptno(Request request, String ptno,
			Integer version);

	public List findMateriel(Request request);

	/**
	 * 新增BOM或者从BOM中插入BOM料件;
	 * 
	 * @param list
	 *            要插入的料件
	 * 
	 */
	Map addMaterialBomVersionOrInsertMaterialBomDetail(Request request,
			MaterialBomVersion mersion, Integer version);

	/**
	 * 复制bom，工厂BOM管理
	 * 
	 * @param list
	 *            要插入的料件
	 * 
	 */
	Map addMaterialBomVersionOrInsertMaterialBomDetail(Request request,
			List<EnterpriseBomManage> details, Integer version);

	/**
	 * 根据参数查找来料、三资设备是否存在
	 * 
	 * @param request
	 * @param emsNo
	 * @return
	 */
	public Boolean findAllFixByNo(Request request, String emsNo, Integer fixType);

	public List findUnit(Request request);

	/**
	 * 获取是供应商
	 */
	public List findScmCocsIsWork(Request request);

	/**
	 * 根据加工公司名称查找海关注册公司
	 */
	public Brief findBrief(Request request, String name);

	/**
	 * 查找报关常用物料里面的料号
	 */
	public List findPtNo(Request request);

	/**
	 * 返回所有的工厂BOM管理
	 * 
	 * @return List 工厂BOM管理
	 */
	public List findAllEnterpriseBomManage(Request request);

	/**
	 * 根据物料类型抓取工厂物料主档资料
	 * 
	 * @return List 是EnterpriseMaterial型，工厂物料主档资料
	 */
	public List findEnterpriseMaterialByType(List names);

	/**
	 * 获取电子帐册归并版本
	 * 
	 * @param request
	 * @return
	 */
	public List findVersion(Request request,
			AtcMergeBeforeComInfo beforeCommInfo);

	/**
	 * 统计工厂料件被使用的成品
	 * 
	 * @param request
	 * @param materiel
	 * @return
	 */
	public List findEnterpriseBomManageByDetail(Request request,
			EnterpriseMaterial materiel);

	/**
	 * 查询料件和半成品
	 * 
	 * @return
	 */
	public List findEnterpriseMaterialMaterielAndProduct(Request request,
			int index, int length, String property, Object value, boolean isLike);

	/**
	 * 通过id 查找工厂BOM子件
	 * 
	 * @param id
	 * @return
	 */
	public List findEnterpriseBomManagerById(String id);

	List checkEnterpriseBomManageIsRight();

	/**
	 * 根据表名查找所有
	 * 
	 * @param tableName
	 * @return
	 */
	public List findList(String tableName);

	/**
	 * 查找所有物料主档料号
	 * 
	 * @param tableName
	 * @return
	 */
	public List findAllPtNo();

	/**
	 * 保存List
	 */
	void saveList(List list);

	List findMaterialVersion(Request request, String ptNo);
	/**
	 * 检查电子帐册归并关系是否禁用
	 * @param ptNo
	 * @return
	 */
	public List findInnerMergeDataByptNoIsForbid(Request request, String ptNo);
	void checkBomStructureTypeAuthority(Request request);

	/**
	 * 根据企业版本得出海关版本
	 * 
	 * @param ptNo
	 * @param version
	 * @return
	 */
	public Integer getVersion(Request request,String ptNo, String cmpVersion);
	void checkCommonBaseCodeAuthority(Request request);

	void checkBomBrowseAuthority(Request request);

	void checkCustomBomManageAuthority(Request request);
	
	List<ScmCoc> findScmCocIsCustomer(Request request);
	List<ScmCoc> findScmCocIsManufacturer(Request request);
	
	public void updateMaterialInfo(Request request,List materielList);
	
	public List findEnterpriseMaterials(Request request);
	
	public List findScmCoi(Request request);
	
	/**
	 * 检查国内车牌是否存在
	 * @param homeCard
	 * @return
	 */
	public Boolean checkMotorCodeHomeCard(Request request,MotorCode motorCode);
}