package com.bestway.common.fpt.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.FptCancelBill;
import com.bestway.common.fpt.entity.FptDownData;
import com.bestway.common.fpt.entity.FptEmail;
import com.bestway.common.fpt.entity.FptEmailParamver;
import com.bestway.common.fpt.entity.FptInitBill;
import com.bestway.common.fpt.entity.FptInitBillItem;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.fpt.entity.TempCasBillTOFptTOCustomsReport;
import com.bestway.common.fpt.entity.TempFptAppParam;
import com.bestway.common.fpt.entity.TempFptAppSpareAnalyes;
import com.bestway.common.fpt.entity.TempFptAppSpareAnalyesDetail;
import com.bestway.common.fpt.entity.TempFptAppheadAndOrder;
import com.bestway.common.fpt.entity.TempFptApplySurplus;
import com.bestway.common.fpt.entity.TempFptBillExeInfo;
import com.bestway.common.fpt.entity.TempFptBillHeadImportFromExcel;
import com.bestway.common.fpt.entity.TempFptEmail;
import com.bestway.common.fpt.entity.TempFptExeInfo;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

@SuppressWarnings("unchecked")
public interface FptManageAction {
	
	/**
	 * 关封浏览权限，空方法
	 */
	void permissionCheckAppHeadGlance(Request request);
	
	/**
	 * 关封新增权限
	 */
	void permissionCheckAppHeadAdd(Request request);

	/**
	 * 关封修改权限
	 */
	void permissionCheckAppHeadEdit(Request request);
	
	/**
	 * 关封删除权限
	 */
	void permissionCheckAppHeadDel(Request request);
	
	/**
	 * 关封变更权限
	 */
	void permissionCheckAppHeadChange(Request request);
	
	/**
	 * 关封结案权限
	 */
	void permissionCheckAppHeadEndCase(Request request);
	
	/**
	 * 关封撤销结案权限
	 */
	void permissionCheckAppHeadCancelEndCase(Request request);
	
	
	/**
	 * 海关申报权限
	 */
	void permissionCheckApply(Request request);
	
	/**
	 * 补发报文权限
	 */
	void permissionCheckResend(Request request);
	
	/**
	 * 关封处理回执权限
	 */
	void permissionCheckProcess(Request request);
	
	/**
	 * 关封转抄权限
	 */
	void permissionCheckCopy(Request request);
	
	/**
	 * 成品发货单浏览权限
	 */
	void permissionCheckOutBillGlance(Request request);
	
	/**
	 * 成品发货单新增权限
	 */
	void permissionCheckOutBillAdd(Request request);
	
    /**
     * 成品发货单修改权限
     */
	void permissionCheckOutBillEdit(Request request);
	
	/**
	 * 成品发货单删除权限
	 */
	void permissionCheckOutBillDel(Request request);
	
	/**
	 * 成品退货单浏览权限
	 */
	void permissionCheckInBackBillGlance(Request request);
	
	/**
	 * 成品退货单新增权限
	 */
	void permissionCheckInBackBillAdd(Request request);
	
    /**
     * 成品退货单修改权限
     */
	void permissionCheckInBackBillEdit(Request request);
	
	/**
	 * 成品退货单删除权限
	 */
	void permissionCheckInBackBillDel(Request request);
	
	/**
	 * 其他功能权限
	 */
	void permissionCheckOther(Request request);
	
	/**
	 * 料件收货单浏览权限
	 */
	void permissionCheckInBillGlance(Request request);
	
	/**
	 * 料件收货单新增权限
	 */
	void permissionCheckInBillAdd(Request request);
	
	/**
	 * 料件收货单修改权限
	 */
	void permissionCheckInBillEdit(Request request);
	
	/**
	 * 料件收货单删除权限
	 */
	void permissionCheckInBillDel(Request request);
	
	/**
	 * 料件退货单浏览权限
	 */
	void permissionCheckOutBackBillGlance(Request request);
	
	/**
	 * 料件退货单新增权限
	 */
	void permissionCheckOutBackBillAdd(Request request);
	
	/**
	 * 料件退货单修改权限
	 */
	void permissionCheckOutBackBillEdit(Request request);
	
	/**
	 * 料件退货单删除权限
	 */
	void permissionCheckOutBackBillDel(Request request);
	
	/**
	 * 单据撤销权限
	 */
	void permissionCheckBillCancel(Request request);
	
	/**
	 * 统计报表浏览权限
	 */
	void permissionCheckGetGuide(Request request);
	
	/**
	 * 结转单据对应表权限
	 */
	void permissionCheckCorresponding(Request request);
	
	/**
	 * 报文查询权限
	 */
	void permissionCheckMessageQuery(Request request);
	
	/**
	 * 深加工结转平台下载权限
	 */
	void permissionCheckBtplsDownData(Request request);

	
	
	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	FptParameterSet findTransParameterSet(Request request);

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	FptParameterSet saveTransParameterSet(Request request,
			FptParameterSet parameterSet);

	/**
	 * 获得关封申请单所有数据
	 */

	List findFptAppHead(Request request);

//	/**
//	 * 获得关封申请单所有数据来自进出货标志
//	 */
//	List findFptAppHeadByInOutFlag(Request request, String inOutFlag);

	/**
	 * 获得关封申请单所有数据来自进出货标志
	 */
	public List findFptAppHeadByInOutFlag(Request request, String fptInOutFlag,
			String declareState, String emsDeclareState);

	/**
	 * 获得关封申请单数据来自客户或供应商Id
	 */
	List findCustomsEnvelopRequestBillByScmCocId(Request request,
			String scmCocId);

	/**
	 * 保存关封申请单
	 */
	public FptAppHead saveFptAppHead(Request request, FptAppHead fptAppHead);
	public void saveFptAppHeads(Request request, List fptAppHeads);

	FptAppItem saveFptAppItem(Request request, FptAppItem data);

	/**
	 * 删除关封申请单
	 */
	void deleteFptAppHead(Request request, FptAppHead fptAppHead);

	/**
	 * 删除关封申请单商品信息数据
	 */
	void deleteFptAppItem(Request request, List list);

	/**
	 * 保存关封申请单商品信息数据
	 */
	List saveFptAppItem(Request request, List list);

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	List findFptAppItems(Request request, String parentId);

	/** 获得最大的数值 */
	Integer findMaxValueByFptAppItem(Request request, String parentId,
			String inOutFlag);

	/** 获得 FptAppItem 来自序号 */
	FptAppItem findFptAppItemByListNo(Request request, String parentId,
			Integer listNo, String inOutFlag);

	/**
	 * 获得最大的单据号来自进出货标志
	 */
	long getMaxBillNoByImpExpGoodsFlag(Request request, boolean impExpGoodsFlag);

	/**
	 * 获得关封申请单商品信息记录来自数据是否正确的检验
	 */
	List findCustomsEnvelopRequestCommodityInfoByCheck(Request request,
			String parentId);

	// /**
	// * 获得关封单据所有数据
	// */
	// List findCustomsEnvelopBill(Request request);
	//
	// /**
	// * 获得关封单据所有数据
	// */
	// List findCustomsEnvelopBill(Request request, boolean impExpGoodsFlag,
	// boolean isAvailability);

	// /**
	// * 获得关封单据所有数据
	// */
	// List findCustomsEnvelopBill(Request request, boolean isImport,
	// boolean isAvailability, ScmCoc scmCoc);

	/**
	 * 获得关封单据所有数据
	 */
	List findFptAppItemByCustomsDeclaration(Request request, int impExpType,
			String emsNo, ScmCoc scmCoc);

	// /**
	// * 获得关封单据所有数据来自进出货标志
	// */
	// List findCustomsEnvelopBill(Request request, boolean impExpGoodsFlag,
	// String billNo, ScmCoc sc, Date beginDate, Date endDate,
	// Boolean isEndCase);

	/**
	 * 查找结转单据商品的可待转数量还有多少
	 */
	double findTransferFactoryCommodityInfoLeft(Request request,
			FptBillItem commInfo);

	// /**
	// * 获得关封单据数据来自客户或供应商Id
	// */
	// List findCustomsEnvelopBillByScmCocId(Request request, String scmCocId);

	// /**
	// * 保存关封单据
	// */
	// CustomsEnvelopBill saveCustomsEnvelopBill(Request request,
	// CustomsEnvelopBill customsEnvelopBill);

	// /**
	// * 删除关封单据
	// */
	// void deleteCustomsEnvelopBill(Request request,
	// CustomsEnvelopBill customsEnvelopBill);

	// /**
	// * 删除关封单据商品信息数据
	// */
	// List deleteCustomsEnvelopCommodityInfo(Request request, List list);

	// /**
	// * 保存关封单据商品信息数据
	// */
	// List saveCustomsEnvelopCommodityInfo(Request request, List list);

	// /**
	// * 获得关封单据商品信息记录来自数据是否正确的检验
	// */
	// List findCustomsEnvelopCommodityInfoByCheck(Request request, String
	// parentId);

	/**
	 * 来自电子帐册料件--并通过封商品信息进行筛选
	 */
	List findEmsMateriel(Request request, EmsHeadH2k emsH2k, String parentId);

	/**
	 * 来自电子帐册成品--并通过封商品信息进行筛选
	 */
	List findEmsFinishedProduct(Request request, EmsHeadH2k emsH2k,
			String parentId);

	/**
	 * * ---------------------------------------------------- 结转单据用到的方法
	 * -------------------------------------------------------
	 */
	/**
	 * 新增结转单据
	 */
	FptBillHead newFptBillHead(Request request, String fptInOutFlag, String fptBusinessType);

	/**
	 * 根据系统类型抓取正在执行的账册号或手册号
	 */
	List findEmsHeadByProjectType(Request request, Integer projectType);

	// /**
	// * 根据系统类型，账册号或手册号，物料和成品分类抓取转厂的物料信息
	// *
	// * @param projectType
	// * @return
	// */
	// List findTempCustomsEnvelopRequestCommInfo(Request request,
	// Integer projectType, String emsNo,
	// CustomsEnvelopBill customsEnvelopBill, boolean isMaterial);

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是FptBillHead型，单据表头
	 */
	FptBillHead findFptBillHeadById(Request request, String id);

	/**
	 * 转厂海关申报
	 */
	DeclareFileInfo applyFptBill(Request request, FptBillHead head);

	/**
	 * 转厂单回执处理
	 * 
	 * @param head
	 * @param exingHead
	 * @return
	 */
	String processFptBillHead(Request request, final FptBillHead head,
			List lsReturnFile);

	/**
	 * 查找正在执行的收发货编号
	 * 
	 * @param request
	 * @param billNo
	 * @return
	 */
	FptBillHead findExingFptBillHeadByEmsNo(Request request, String copBillNo,
			String inOutFlag);

	/**
	 * 变更单据
	 */
	FptBillHead changingFptBill(Request request, FptBillHead fptBillHead);

	/**
	 * 转抄数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Contract型，合同表头
	 */
	List<FptBillHead> copyFptBillHead(Request request, List<FptBillHead> list,
			Integer inorOut, Integer billType);

	/**
	 * 获得最大的单据号表体序号
	 * 
	 * @param head
	 * @return
	 */
	List addBillListNo(FptBillHead head);

	/**
	 * 取结转单转报关单时判断手册号是否是所选择的手册号
	 */
	boolean checkmakeFptToBgdEmsH2k(String cm, List listcomminfo);

	/**
	 * 取结转单转报关单时判断手册号是否是所选择的手册号
	 */
	boolean checkmakeFptToBgdEmsH2kBill(String cm, List listcomminfo);

	/**
	 * 把查询出来的临时商品信息,保存为结转单据的商品信息
	 * 
	 * @param request
	 * @param lsResult
	 * @return
	 */
	List saveFptAppItemCommInfoToFptBillItemCommInfo(Request request,
			List lsResult, FptBillHead head);

	/**
	 * 把查询出来的归并关系商品信息,保存为结转单据的商品信息
	 * 
	 * @param request
	 * @param lsResult
	 * @return
	 */
	List saveTempCustomsEnvelopCommInfoToFptBillItemCommInfo(Request request,
			List lsResult, FptBillHead head);

	/**
	 * 转抄明细
	 * 
	 * @param request
	 * @param exgFrom
	 * @param exgTo
	 */
	List<FptBillItem> copyFptBillExpImpDetail(Request request,
			List<FptBillItem> list, FptBillHead head);

	// /**
	// * 把查询出来的临时商品信息,保存为关封的商品信息
	// *
	// * @param list
	// * @param envelopBill
	// * @return
	// */
	// List saveCustomsEnvelopRequestCommInfo(Request request,
	// List<TempCustomsEnvelopCommInfo> list,
	// CustomsEnvelopBill envelopBill);

	/**
	 * 拆分结转单据，将结转单据没有报关的数量，生成一个新的结转单据
	 * 
	 * @param transferFactoryBill
	 * @return
	 */
	FptBillHead splitTransferFactoryBill(Request request,
			FptBillHead transferFactoryBill);

	//
	// /**
	// * 获得结转所有单据
	// */
	// List findTransferFactoryBill(Request request);

	/**
	 * 查找所有结转单扰
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param customer
	 *            客户/供应商
	 * @return 符合上面指定的条件的进出口申请单
	 */
	public List findInOutFptBillHeadByType(Request request, String fptInOutFlag, String fptBusinessType,
			Date beginDate, Date endDate, String tradeCode,
			String state, String appNo,  ScmCoc scmcoc);
	
	public List findFptBillHeadSerialNumber(Request request,String fptInOutFlag);

	/**
	 * 获得结转所有单据来自进出货
	 */
	List findFptBillHead(Request request, String sysType, String inOutFlag,
			Date beginDate, Date endDate, String declareState, String appNo,
			ScmCoc scmcoc);

	/**
	 * 保存结转单据
	 */
	FptBillHead saveFptBillHead(Request request, FptBillHead transferFactoryBill);

//	/**
//	 * 判断归并后的资料是否是按申请表序号与交易单位来进行合并
//	 * 
//	 * @author ower
//	 * 
//	 */
//	boolean isMergerCheckFptBillDictItem(Request request, String inOutFlag,
//			String parentId ,String sysType);

	/**
	 * 判断内部编号号是否重复
	 * 
	 * @param head
	 * 
	 * @return boolean 手册号是否重复，true为重复
	 */
	boolean checkFptBillEmsBillNoDuple(Request request,
			String fptInOutFlag, String fptBusinessType, FptBillHead head);

	/**
	 * 判断申请单编号是否存在
	 */
	boolean checkAppNoInFptAppHead(Request request, String fptInOutFlag,
			String appNo);

	/**
	 * 删除结转单据
	 */
	void deleteFptBillHead(Request request, FptBillHead FptBillHead);

	/**
	 * 删除结转单据商品信息数据
	 */
	Map<Integer, List<FptBillItem>> deleteFptBillItem(Request request, List list);

	/**
	 * 保存结转单据商品信息数据
	 */
	FptBillItem saveFptBillItem(Request request, FptBillItem commInfo);

	/**
	 * 保存结转单据商品信息数据
	 */
	List saveFptBillItem(Request request, List list);

	/**
	 * 获得结转单据记录来自数据是否正确的检验
	 */
	List findTransferFactoryCommodityInfoByCheck(Request request,
			String parentId);

	/**
	 * 查询结转单据中是否有商品信息
	 * 
	 * @param request
	 * @param parentId
	 * @return
	 */
	List findFptBillItemCommodityInfo(Request request, String parentId,
			String inOutFlag);

	/**
	 * 查询结转单据中归并后的商品信息
	 * 
	 * @param request
	 * @param parentId
	 * @return
	 */
	List findFptBillDictItemCommodityInfo(Request request, String parentId,
			String inOutFlag ,String sysType);

	// /**
	// * 获得最大的结转单据号来自进出货标志
	// */
	// long getMaxTransferFactoryBillNoByImpExpGoodsFlag(Request request,
	// boolean impExpGoodsFlag);

	/**
	 * 获得关封申请单据来自选定用客户，且生效、未转关封的单据
	 */
	List findTempCustomsEnvelopRequestBillByScmCoc(Request request,
			String scmCocId);

	/**
	 * 获得关封申请单据商品信息来自父对象List
	 */
	List findTempCustomsEnvelopRequestCommodityInfoByParent(Request request,
			List parentList);

	/**
	 * 关封申请单转关封时商品信息的检查--返回没有在电子帐册中备案的数据(成品)
	 */
	List checkTempCustomsEnvelopRequestCommodityInfoByFinishProduct(
			Request request, List list, EmsHeadH2k emsH2k,
			EmsEdiMergerHead emsEdiMergerHead);

	/**
	 * 关封申请单转关封时商品信息的检查--返回没有在电子帐册中备案的数据(料件)
	 */
	List checkTempCustomsEnvelopRequestCommodityInfoByMateriel(Request request,
			List list, EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead);

	// /**
	// * 关封申请单-->返回已生成关封单据的关封申请单列表
	// */
	// List makeCustomsEnvelopBill(Request request,
	// CustomsEnvelopBill customsEnvelopBill, List dataSource);

	/**
	 * 修改关封申请单的关封单据字段,和生成关封单据字段为true
	 */
	List updateCustomsEnvelopRequestBill(Request requst,
			String customsEnvelopBillId, List dataSource);

	/**
	 * 保存转厂初始化单据
	 */
	FptInitBill saveTransferFactoryInitBill(Request requst,
			FptInitBill transferFactoryInitBill);

	/**
	 * 保存转厂初始化单据商品信息
	 */

	FptInitBill saveTransferFactoryInitBillCommodityInfo(Request request,
			FptInitBill initBill, List list);

	/**
	 * 保存转厂初始化单据商品信息
	 */
	FptInitBillItem saveTransferFactoryInitBillCommodityInfo(Request requst,
			FptInitBillItem transferFactoryInitBillCommodityInfo);

	/**
	 * 删除转厂初始化单据
	 */
	void deleteTransferFactoryInitBill(Request requst,
			FptInitBill transferFactoryInitBill);

	/**
	 * 删除转厂初始化单据商品信息
	 */

	FptInitBill deleteTransferFactoryInitBillCommodityInfo(Request requst,
			FptInitBill initBill, List list);

	/**
	 * 获得结转所有单据
	 */
	List findTransferFactoryInitBill(Request requst);

	/**
	 * 获得结转所有单据
	 */

	List findTransferFactoryInitBill(Request requst, boolean isImpExpFlag);

	/**
	 * 取得最大转厂初始化单据号码+1
	 * 
	 * @return
	 */
	String getTransferFactoryInitBillMaxCode(Request requst);

	/**
	 * 获得转厂初始化单据信息加载子表的记录
	 */
	List findTransferFactoryInitCommodityInfo(Request requst, String parentId);

	/**
	 * 获得转厂单据来自选定用客户，且生效、未转关封的单据
	 */
	List findTempTransferFactoryBillByScmCocNotCER(Request request,
			String scmCocId);

	/**
	 * 修改结转单据的关封申请单字段,和生成关封申请单据字段为true
	 */
	List updateTransferFactoryBillByCER(Request request,
			String customsEnvelopRequestBillId, List dataSource);

	/**
	 * 修改结转单据的报关清单字段,和生成报关清单据字段为true -->来自报关清单Id
	 */
	List updateTransFactBillByCustomsBillListId(Request request,
			String applyToCustomsBillId, List dataSource);

	/**
	 * 转厂进出货明细表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param billType
	 *            单据类型
	 * @param billCode
	 *            单据号码
	 * @param materielCode
	 *            料号
	 * @param materielName
	 *            名称
	 * @param emsNo
	 *            帐册序号
	 * @param companyName
	 *            公司名称
	 * @return
	 */
	List findTransferFactoryImpExpGoodsList(Request request, Date beginDate,
			Date endDate, String fptInOutFlag, String declareState,
			String fptBusinessType, String code, String name, Integer seqNum,
			String scmcoc);

	/**
	 * 转厂进出货明细表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param billType
	 *            单据类型
	 * @param billCode
	 *            单据号码
	 * @param materielCode
	 *            料号
	 * @param materielName
	 *            名称
	 * @param emsNo
	 *            帐册序号
	 * @param companyName
	 *            公司名称
	 * @return
	 */
	List findTransferImpExpCustomsList(Request request, Date beginDate,
			Date endDate, Integer impexpType, String emsNo, String scmcoc,
			int projectType, Boolean effec, String code, String name,
			Integer seqNum);

	/**
	 * 转厂进出货状况表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param impExpFlag
	 * @param billCode
	 * @param materielCode
	 * @param materielName
	 * @param emsNo
	 * @param companyName
	 * @return
	 */
	List findStatisticTotalTransferStatusQuantity(Request request,
			Date beginDate, Date endDate, String fptInOutFlag,
			String declareState, String fptBusinessType, String code,
			String name, Integer seqNum, Integer projectType, String scmcocName);

	// /**
	// * 获得转厂单据商品信息来自父对象List 用于转换报关清单过程中
	// */
	// List findTempTransferFactoryCommodityInfoByParentToACT(Request request,
	// List parentList);

	// /**
	// * 获得没有结案,没有过期,生效的关封单据
	// */
	// List findCustomsEnvelopBillByAvailability(Request request, String
	// scmCocId);

	// /**
	// * 根据关封单据id获得其计算后的detail
	// */
	// List findCustomsEnvelopCommodityInfoByTempTransferFactoryCommodityInfo(
	// Request request, String parentId, boolean isImportGoods,
	// List tempTransferFactoryCommodityInfoList);

	/**
	 * 生成结转单据
	 */
	FptBillHead makeFptBillFromCasBill(Request request, List billDetailList,
			String casBillCode, FptAppHead fptAppHead, String emsNo,
			boolean isComplex, boolean isNewFptBillHead,
			FptBillHead oldFptBillHead);

	/**
	 * 获取查询list
	 * 
	 * @param commodityCode
	 *            商品编码
	 * @param commodityName
	 *            商品名称
	 * @param seqNum
	 *            帐册序号
	 * @param oppoisteEnterpriseName
	 *            对方企事业名称
	 * @param isImportGoods
	 *            转入/转出
	 * @param customsEnvelopBillNo
	 *            关封号
	 * @return
	 */
	List findCustomsEnvelopSpareAnalyes(Request request, String commodityCode,
			String commodityName, String seqNum, String oppoisteEnterpriseName,
			boolean isImportGoods, String customsEnvelopBillNo);

	// /**
	// * 有数据转报关清单在关封中
	// *
	// * @param c
	// * @return
	// */
	// boolean hasDataTransFactoryBillByEnvelopId(Request request,
	// CustomsEnvelopBill c);

	// /**
	// * 获得当前转厂进出口的商品信息的个数
	// */
	// int findTransferFactoryCommodityInfoCount(Request request, String
	// parentId);

	// /**
	// * 获得当前关封单据的商品信息的个数
	// */
	// int findCustomsEnvelopCommodityInfoCount(Request request, String
	// parentId);

	/**
	 * 获得当前关封申请单的商品信息的个数
	 */
	int findCustomsEnvelopRequestCommodityInfoCount(Request request,
			String parentId);

	/**
	 * 获得结转单据信息加载子表的记录
	 */
	List findTransferFactoryCommodityInfo(Request request, String parentId);

	/**
	 * 有数据已转关封在关封申请单中
	 * 
	 * @param c
	 * @return
	 */
	boolean hasDataTransferCustomsEnvelopByCustomsEnvelopRequest(
			Request request, FptAppHead c);

	/**
	 * 有数据已转关封申请单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	boolean hasDataCustomsDeclarationByTransFactBill(Request request,
			FptBillHead t);

	/**
	 * 有数据已报关清单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	boolean hasDataTransferApplyToCustomsBillByTransferFactoryBill(
			Request request, FptBillHead t);

	// /**
	// * 查找结转单据生成报关清单时的中间表信息来自关封相关的数据项
	// *
	// * @param c
	// * @return
	// */
	// List findMakeApplyToCustomsInfoByCustomsEnvelopBill(Request request,
	// CustomsEnvelopBill c);

	// /**
	// * 获得关封申请单信息加载子表的记录
	// */
	// List findCustomsEnvelopCommodityInfo(Request request);

	/**
	 * 转厂分析查询权限控制
	 */
	void checkTransferStatisticAnalyse(Request request);

	/**
	 * 关封余量分析权限控制
	 */

	void checkCustomsEnvelopSpareAnalyse(Request request);

	// /**
	// * 抓取报关单资料
	// *
	// * @param projectType
	// * @return
	// */
	// List findExportCustomsDeclaration(Request request,
	// CustomsEnvelopBill customsEnvelopBill);

	// /**
	// * 抓取报关单资料
	// *
	// * @param projectType
	// * @return
	// */
	// List findImportCustomsDeclaration(Request request,
	// CustomsEnvelopBill customsEnvelopBill);

	List findTransferFactoryInitCommodityInfoForCustoms(Request request,
			String parentId);

	List findPutRecord(Request request);

	List findTransferPlan(Request request);

	/**
	 * 查询转厂中关封的客户和厂商
	 * 
	 * @return
	 */
	List findCustomsEnvelopScmCoc(Request request, boolean isImport);

	/**
	 * 查询转厂中关封的商品信息
	 * 
	 * @return
	 */
	List findCustomsEnvelopComplex(Request request, boolean isImport,
			boolean isSeqNum);

	// 关封明细报表
	List findCustomsEnvelopList(Request request, String envelopCode,
			Integer seqNum, String complexCode, String scmCoc,
			boolean isImport, int state, Date beginDate, Date endDate);

	// /**
	// * 根据单据号查询关封商品明细
	// *
	// * @param customsEnvelopBillCode
	// * @return
	// */
	// List findTempTransferFactoryCommInfo(Request request,
	// String customsEnvelopBillCode);

	// /**
	// * 查询不在转厂起初单的商品编码
	// *
	// * @param initBillId
	// * @return
	// */
	// List findComplexNotInInitBill(Request request, String initBillId,
	// String customsEnvelopBillCode);

	/**
	 * 结转单据自动转报关单及其商品信息
	 * 
	 * @param lsBills
	 * @param param
	 * @return
	 */
	List makeCusomsDeclarationFromTransferBill(Request request,
			MakeCusomsDeclarationParam param, List billItem,
			FptAppHead fptAppHead, BaseCustomsDeclaration cm);

	/**
	 * 查询未转报关单的结转单据
	 * 
	 * @param isImport
	 * @return
	 */
	List findFptBillMakeCustomsDeclaration(Request request,
			String inOutFlag, String appNo, Date beginDate,
			Date endDate);

	/**
	 * 查询追加报关单结转单生成报关单时所有报关单
	 * 
	 * @param request
	 * @param flag
	 * @return
	 */

	public List findCustomsDeclarationByImpExpFlag(Request request, int flag,
			Integer project);

	/**
	 * 获得进出口商品信息来自父对象
	 * 
	 * @param request
	 *            请求控制
	 * @param parentList
	 *            父对象
	 * @return 进出口商品信息
	 */
	List findMakeSummaryFptBillCommodityInfoByParent(Request request,
			List parentList, String inOutFlag);
	/**
	 * 根据归并后料件的【收退货：sysType/申请编号/是否转入标志】进行查询数据
	 * @param request
	 * @param parentList
	 * @param inOutFlag
	 * @return
	 */
	List findFptBillCommodityInfoByHeadItem(Request request,
			List parentList, String sysType,String inOutFlag);

	/**
	 * 根据手册号码抓取正在执行的电子手册
	 * 
	 * @param emsNo
	 * @return
	 */
	DzscEmsPorHead findDzscEmsPorHeadExcu(Request request, String emsNo);

	/**
	 * 根据账册册号码抓取正在执行的电子账册
	 * 
	 * @param emsNo
	 * @return
	 */
	EmsHeadH2k findEmsHeadH2kInExecuting(Request request, String emsNo);

	// /**
	// * 根据客户供应商查找转厂商品
	// *
	// * @param scmcoc
	// * 客户供应商
	// * @return 转厂商品
	// */
	// List findCustomsEnvelopCommodityInfoByScmCoc(Request request,
	// ScmCoc scmcoc, Boolean isCustomer);

	/**
	 * 保存商品单价维护实体
	 * 
	 * @param list
	 *            商品单价维护实体
	 */
	void saveBillPriceMaintenance(Request request, List list);

	/**
	 * 删除商品单价维护实体
	 * 
	 * @param list
	 *            商品单价维护实体
	 */
	void delBillPriceMaintenance(Request request, List list);

	/**
	 * 根据客户供应商查找商品单价维护实体
	 * 
	 * @param scmCoc
	 *            客户供应商
	 * @return 商品单价维护实体
	 */
	List findBillPriceMaintenanceByScmCoc(Request request, ScmCoc scmCoc,
			Boolean isCustomer);

	/**
	 * 修改单价
	 * 
	 * @param scmCoc
	 *            客户供应商
	 */
	void editBillPriceMaintenancePrice(Request request, ScmCoc scmCoc,
			Integer seqNum, String code, Double price, int projectType,
			Boolean isCustomer);

	void checkEditBillPriceMaintenancePrice(Request request);

	List addBillPriceMaintence(Request request, List list, ScmCoc scmCoc,
			Boolean isCustomer);

	List findTransferClienCollate(Request request, Integer projectType,
			String emsNo, String scmcocCode, Date beginDate, Date endDate,
			Integer impExpType);

	List traCustomsCollate(Request request, String year, boolean isImpType);

	/**
	 * 根据关封号抓取关封里所有的手册号
	 * 
	 * @param envelopNo
	 * @return
	 */
	List findCustomsEnvelopBillEmsNo(Request request, String envelopNo);

	/**
	 * 查询结转申请单
	 * 
	 * @param inOutFlag
	 * @param declareState
	 * @param tradeCode
	 * @param isCollated 是否结案
	 * @return
	 */
	List findFptAppHeadByScmCoc(Request request, String inOutFlag,
			String declareState, ScmCoc scmCoc,Boolean isCollated);

	/**
	 * 获取企业需要下载备案资料（RecordationDataDownLoad）
	 */
	List findRecordationDataDownLoad(Request request,String downLoadState,String fptInOutFlag);

	/**
	 * 获取企业需要撤消的资料（RecordationDataDownLoad）
	 */
	List findFptCancelBill(Request request,String sysType,String billSort);

	/**
	 * 存储企业需要下载备案资料（RecordationDataDownLoad）
	 */
	FptDownData saveFptDownData(Request request, FptDownData list);

	/** 新增转厂申请单 */
	FptAppHead newFptAppHead(Request request, String fptInOutFlag);

	/**
	 * 获得关封申请单
	 */
	public FptAppHead findFptAppHeadById(Request request, Object id);

	/**
	 * // 获取明细的最大序号
	 * 
	 * @param request
	 * @param fptBillHead
	 * @return
	 */
	Integer addBillListNoInteger(Request request, FptBillHead fptBillHead);

	/** 是否已存在该申请单 */
	public boolean isExistFptAppHeadByOutCopAppNo(Request request,
			FptAppHead fptAppHead);

	/**
	 * 存储进出货转厂撤消资料
	 */
	FptCancelBill saveFptCancelBill(Request request, FptCancelBill list);

	/** 新增转厂申请单 */

	/**
	 * 备案申请单数据
	 * 
	 * @param contract
	 *            申请单表头
	 */
	public FptAppHead putOnRecordFptAppHead(Request request,
			FptAppHead fptAppHead);

	/**
	 * 获得转厂申请单信息加载子表的记录
	 */

	public List findFptBillItems(Request request,
			String parentId, String inOutFlag);

	/**
	 * 进出货单据导出excel
	 */

	TempResultSet exportFptBillItemToExcel(Request request, String parentId,
			String in);

	/**
	 * 判断申请单是否可以备案
	 * 
	 * @param contract
	 *            申请单表头
	 */
	public String checkFptAppHeadForPutOnRecord(Request request,
			FptAppHead fptAppHead);

//	/** 获得最大的数值除掉新增的值 */
//	public Integer findMaxValueByFptBillItemExceptAdd(Request request,
//			String parentId, String inOutFlag);

	/**
	 * 转抄明细
	 */
	public FptAppHead copyFptAppItem(Request request, FptAppHead head,
			List<FptAppItem> list);

	/**
	 * 转抄明细到另一个表头
	 */
	public List copyFptAppItemToHead(Request request, FptAppHead head,
			List<FptAppItem> list);

	/**
	 * 转抄数据
	 * 
	 * @param list
	 * 
	 */
	public List copyFptAppHeadAll(Request request, List<FptAppHead> list);

	/**
	 * 取得结转进口与结转出口
	 */
	public List findScmManuFactory(Request request, String paramScoId);

	/**
	 * 转抄数据
	 * 
	 * @param list
	 * 
	 */

	public List copyFptAppHead(Request request, List<FptAppHead> list);

	/**
	 * 删除下载的备案资料
	 */
	void deleteRecordationDataDownLoad(Request request,
			FptDownData recordationDataDownLoad);

	/**
	 * 删除进出货转厂撤消资料
	 */
	void deleteFptCancelBill(Request request, FptCancelBill fptCanelBill);

	/**
	 * 判断交易数量是否超量
	 */
	TempFptBillExeInfo findFptBillExeInfoByFpt(Request request, FptBillItem item);

	/**
	 * 变更申请单 如果返回null就不能变量 否则 就变更一条新的记录
	 * 
	 * @param fptAppHead
	 *            申请单表头
	 * @return Contract 申请单表头
	 */
	public FptAppHead changingFptAppHead(Request request, FptAppHead fptAppHead);

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public List findFptAppItems(Request request, String parentId,
			String inOutFlag);

	/** 获得临时的 */
	public List findTempFptAppItemByParentId(Request request, FptAppHead head);

//	/**
//	 * 获取进出货转厂单据中申报状态为正在执行的数据
//	 * 
//	 * @param request
//	 * @param appState
//	 * @return
//	 */
//	List findFptBillHeadForCancel(Request request);
	
	/**
	 * 获取进出货转厂单据中申报状态为 正在执行、未撤销的单据 
	 * 
	 * @param request
	 * @param fptInOutFlag 转入、转出
	 * @param fptBusinessType 收发货单、退货单
	 * @return
	 */
	List findFptBillHeadForCancel(Request request, String fptBusinessType, String fptInOutFlag);

	/**
	 * 申请单料件数据取整
	 * 
	 * @param list
	 *            是FptAppItem型，申请单料件
	 */
	public List<FptAppItem> saveFptAppItemAmountInteger(Request request,
			List<FptAppItem> list);

	List findMaterielByFptAppHeadType(Request request, String isImportGoods);

	/**
	 * 根据申请单编号获取申请单
	 * @param request
	 * @param isImportGoods
	 * @param list
	 * @return
	 */
	List findFptAppHead(Request request, String isImportGoods,List<String> list);
	/**
	 * 保存转厂进出货单表头和表体
	 * @param list
	 */
	void saveFptBillHeadsAndFptBillItems(Request request, List list,String fptBusinessType);

	List findFptAppItemToFptBillItem(Request request, String isImportGoods,
			String appNO);
	
	List findFptAppItemToFptBillItem(Request request, String isImportGoods,List list);

	List findUnit(Request request);
	/**
	 * 结转单据来源三种模式的料号级
	 * 
	 * @param request
	 * @param isImportGoods
	 * @param EmsNo
	 * @return
	 */
	List findFptEmsNoCopBillNoToFptBillItemCopBillNo(Request request,
			int projectType, String isImportGoods, String appNo);

	/** --------------------------------------------结转单据对应报表查询----------------------------- */
	/**
	 * 单据号码
	 */
	public List findBillNoByPara(Request request, boolean impExpFlagCode);

	/**
	 * BOM
	 */
	public List<TempObject> findBomNoByPara(Request request,
			boolean impExpFlagCode, String billTypeCode);

	/**
	 * 取得客户/供应商
	 */
	public List findScmCocsByPara(Request request, String impExpFlagCode,
			String billTypeCode);

	/**
	 * 回写结转单报关单号
	 */
	public void reciveCustomsDeclarationCode(Request request,
			List<TempCasBillTOFptTOCustomsReport> listC);

	/**
	 * 取消结转单报关单号
	 */
	public void cancelCustomsDeclarationCode(Request request,
			List<TempCasBillTOFptTOCustomsReport> list);

	/**
	 * 查询结转对应报表
	 */
	public List getMakeFptBillFromCasBill(Request request, boolean inOutFlag,
			ScmCoc scmCoc, Date beginDate, Date endDate, String billNo,
			String bomNo);

	public List getMakeFptBillCustomsDeclaration(Request request,
			boolean inOutFlag, String appNo, Date beginDate, Date endDate,
			ScmCoc scmCoc, String emsNo);

	/** --------------------------------------------结转单据转入方下载备案资料------------------------ */

	/**
	 * 备案资料海关申报
	 */
	DeclareFileInfo applyFptDownData(Request request, FptDownData head);

	/**
	 * 下载数据处理回执
	 * 
	 */
	String processFptDownData(Request request, FptDownData head,
			List lsReturnFile);

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是RecordationDataDownLoad型，单据表头
	 */
	FptDownData findFptDownDataById(Request request, String id);

	/**
	 * 根据ID查找撤消数据
	 * 
	 */
	FptCancelBill findFptCancelBillById(Request request, String id);

	/** 获得转出 FptAppItem 来自序号集合 */
	public List<Integer> findFptAppItemListNoByOut(Request request,
			String parentId);

	/**
	 * 获得申请单所有数据
	 */
	public List<FptAppHead> findFptAppHeadByNotExceute(Request request,
			FptAppHead outHead);

	/** 新增转厂申请单明细 */
	public FptAppItem newFptAppItem(Request request, FptAppHead head);

	/**
	 * 
	 * 转厂申请单报文生成
	 * 
	 * @param head
	 * 
	 * @return FptBillHead
	 */
	public DeclareFileInfo applyFptAppHead(Request request, FptAppHead head,
			String taskId);

	/**
	 * 查找 FptAppHead 来自 outCopAppNo
	 */
	public FptAppHead findFptAppHeadByOutCopAppNo(Request request,
			String outCopAppNo);

	/**
	 * 查找 FptAppHead 来自 inCopAppNo
	 */
	public FptAppHead findFptAppHeadByInCopAppNo(Request request,
			String inCopAppNo);

	/**
	 * 转厂申请单回执处理
	 * 
	 * @param fptAppHead
	 * @param existFptAppHead
	 * @return
	 */
	public String processFptAppHead(Request request, FptAppHead fptAppHead,
			FptAppHead existFptAppHead, List lsReturnFile);

	/**
	 * 获得正在执行的合同的料件或成品 (bcs)
	 * 
	 * @param inOutFlag
	 *            FptInOutFlag class
	 * @param emsNo
	 * @param inOutFlag
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findBcsContractDetailByProcessExe(Request request,
			String parentId, String emsNo, String inOutFlag, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 获得正在执行的电子手册通关备案里的料件或成品 (dzsc)
	 * 
	 * @param inOutFlag
	 *            FptInOutFlag class
	 * @param emsNo
	 * @param inOutFlag
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findDzscEmsPorHeadDetailByProcessExe(Request request,
			String parentId, String emsNo, String inOutFlag, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 获得正在执行的电子帐册里的料件或成品 (Bcus)
	 * 
	 * @param inOutFlag
	 *            FptInOutFlag class 进口是 is materiel
	 * @param emsNo
	 * @param inOutFlag
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findBcusEms2kDetailByProcessExe(Request request,
			String parentId, String emsNo, String inOutFlag, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 查询订单（条件过滤）
	 */
	public List findCustomOrderByIfok(Request request);

	/** 新增转厂申请单明细 来自 bcs 合同成品 */
	public List<FptAppItem> newFptAppItemByContractExg(Request request,
			FptAppHead head, List<ContractExg> contractExgs);

	/** 新增转厂申请单明细 来自 bcs 合同料件 */
	public List<FptAppItem> newFptAppItemByContractImg(Request request,
			FptAppHead head, List<ContractImg> contractImgs);

	/** 新增转厂申请单明细 来自 DZSC 正在执行的电子手册通关备案里的料件 */
	public List<FptAppItem> newFptAppItemByDzscEmsImgBill(Request request,
			FptAppHead head, List<DzscEmsImgBill> dzscEmsImgBills);

	/** 新增转厂申请单明细 来自 DZSC 正在执行的电子手册通关备案里的成品 */
	public List<FptAppItem> newFptAppItemByDzscEmsExgBill(Request request,
			FptAppHead head, List<DzscEmsExgBill> dzscEmsExgBills);

	/** 新增转厂申请单明细 来自 Bcus 正在执行的电子帐册里的成品 */
	public List<FptAppItem> newFptAppItemByEmsHeadH2kExg(Request request,
			FptAppHead head, List<EmsHeadH2kExg> emsHeadH2kExgs);

	/** 新增转厂申请单明细 来自 Bcus 正在执行的电子帐册里的料件 */
	public List<FptAppItem> newFptAppItemByEmsHeadH2kImg(Request request,
			FptAppHead head, List<EmsHeadH2kImg> emsHeadH2kImgs);

	/**
	 * 获得转厂申请单信息加载子表的记录
	 */
	public List findFptAppItemsByModifyMarkState(Request request,
			String parentId, String inOutFlag, String modifyMarkState);

	/** 获得最大的数值除掉新增的值 */
	public Integer findMaxValueByFptAppItemExceptAdd(Request request,
			String parentId, String inOutFlag);

	/**
	 * 各报关模式查找所有正在执行手册
	 * 
	 * @param projectType
	 * @return
	 */
	public List<BaseEmsHead> findAllEmsExe(Request request, int projectType);

	/**
	 * 查找 FptAppHead 来自 统一编号
	 */
	public FptAppHead findFptAppHeadAppNo(Request request, String fptInOutFlag,String seqNo);

	/**
	 * 申报撤消单数据
	 * 
	 * @param head
	 * @param taskId
	 * @return
	 */
	DeclareFileInfo applyFptCancelBill(Request request, FptCancelBill head);

	/**
	 * 撤消数据处理回执
	 * 
	 */
	String processFptCancelBill(Request request, FptCancelBill head,
			List lsReturnFile);

	/**
	 * 获得申请单余量分析报表
	 */
	public List<TempFptAppSpareAnalyes> findTempFptAppSpareAnalyes(
			Request request, TempFptAppParam param);

	
	/**
	 * Hyq
	 * 深加工结转申请表收发货余量分析
	 */
	public List<TempFptApplySurplus> findTempFptApplySurplus(
			Request request, TempFptAppParam param);
	/**
	 * 正在执行状态下深加工结转申请表收发货余量分析
	 * @param request
	 * @param param
	 * @return
	 */
	public List<TempFptApplySurplus> findTempFptApplySurplus0(
			Request request, TempFptAppParam param);
	
	/**
	 * Hyq
	 * 深加工结转手册余量分析
	 */
	public List<TempFptApplySurplus> findTempFptApplyDifference(
			Request request, TempFptAppParam param);
	
	
	/**
	 * 通过多个订单表头，查询表体！
	 * 
	 * @param customOrders
	 * @return
	 */
	public List findCustomOrderForToFptAppHead(Request request,
			List customOrders);

	/**
	 * 订单转转厂申请单
	 * 
	 * @param list
	 */
	public List makeCustomsOrderToFptAppHead(Request request, FptAppHead head,
			List<TempFptAppheadAndOrder> list, int projectType,
			String materielType, String emsNo);

	/**
	 * 查询单据某一属性不重复值
	 * 
	 * @param prop
	 * @return
	 */
	public List findDistinctProperiesFromFptBillItem(Request request,
			String prop);

	/**
	 * 查询单据中心单据转结转单据的信息
	 * 
	 * @param isMaterial
	 * @param casBillNo
	 * @param ptNo
	 * @param fptCopNo
	 * @param beginCasDate
	 * @param endCasDate
	 * @param beginFptDate
	 * @param endFptDate
	 * @return
	 */
	List findMakeFptBillFromCasBillInfo(Request request, String emsNo,
			String casBillNo, String ptNo, String fptCopNo, String complexCode,
			Date beginCasDate, Date endCasDate, Date beginFptDate,
			Date endFptDate);

	/**
	 * 获得申请单余量分析报表(明细)
	 */
	public List<TempFptAppSpareAnalyesDetail> findTempFptAppSpareAnalyesDetail(
			Request request, TempFptAppParam param);

	/**
	 * 获得关封申请单所有数据来客户供应商
	 */
	public List findFptAppHeadByScmCoc(Request request, ScmCoc scmCoc);

	/**
	 * 获得转厂余量数据信息
	 * 
	 * @param projectType
	 * @param inOutFlag
	 * @param emsNo
	 * @param seqNum
	 * @param fptAppItem
	 * @return
	 */
	public TempFptExeInfo findContractExeInfoByFpt(Request request,
			int projectType, String inOutFlag, String emsNo, int seqNum,
			FptAppItem fptAppItem);

	/**
	 * 获得转厂报关单不重复的某一属性
	 */
	public List findDistinctTranceCustomsDeclaration(Request request,
			int projectType, String prop);

	/**
	 * 抓取结转申请表里面的手册/账册号
	 * 
	 * @param fptAppHead
	 * @param inOutFlag
	 * @return
	 */
	List findEmsNoFromFptApp(Request request, FptAppHead fptAppHead,
			String inOutFlag);

	/**
	 * 收送货单据导入对方的资料
	 * 
	 * @param listHead
	 * @param listItem
	 */
	void saveFptBillItemFormOtherSide(Request request, List listHead,
			List listItem, boolean isOverwrite);

	/** ---------------------------------------------邮件----------------* */
	void saveFptEmailParamver(Request request, FptEmailParamver email);

	void saveFptEmail(Request request, FptEmail email);

	FptEmailParamver findFptEmailParamver(Request request);

	List findAppBillToFptEmail(Request request, String sysType, String state,
			String inoutFlag, String makeinout);

	List FindFptEmail(Request request, String mailIRType);

	void deleteFptEmail(Request request, FptEmail email);

	void deleteFptEmailState(Request request, FptEmail fe);

	List FindFptEmailToAress(Request request);

	void excEmail(Request request, FptEmail email, TempFptEmail fp);

	List findFptComplex(Request request, ScmCoc scmcoc);

	List findCustomsComplex(Request request, ScmCoc scmcoc);

	void deleteObjects(Request request, List list);

	List saveObjects(Request request, List list);

	/**
	 * 保存深加工结转-进出货转厂单据Excel导入信息
	 */

	public void saveFptBillHeadFromImport(Request request,
			List<TempFptBillHeadImportFromExcel> ls, String inOutFlag,
			boolean isOverwrite);

	/** 获得申请单 */
	public FptAppHead findFptAppHeadByOutCopAppNo(Request request,
			String outCopAppNo, String declareState);

	/** 获得申请单 */
	public FptAppHead findFptAppHeadByInCopAppNo(Request request,
			String inCopAppNo, String declareState);

	/** 获得申请单 */
	public FptAppHead findFptAppHeadByAppNo(Request request, String appNo,
			String declareState);

	/**
	 * 下载出口明细
	 * 
	 * 在转厂申请表模块 [转入方] 加入单笔从QP系统导入转出方的资料, 1.用户选择转入申请表 2.点击 [下载转出方申请表数据] 导入按钮
	 * 3.以[申请表编号]为条件新增导入转出方申请表数据, 4.以[申请表编号]为条件覆盖导入转出方申请表数据 ( 先删除以前所有数据,导入最新数据 )
	 * 
	 * @param fptAppHead
	 * @return result = List<FptAppItem> 比QP的代码本人认为要更为高明 用进程任务相对于反回信息,要更高明一些
	 */
	public List<FptAppItem> downloadFptAppItemsOutByQp(Request request,
			String taskId, FptAppHead fptAppHead);

	/**
	 * 下载进口明细
	 * 
	 * .在转厂申请表模块 [转出方] 加入单笔从QP系统导入转入方的资料 1.用户选择已审核通过的转出申请表 2.点击 [下载转入方申请表数据]
	 * 导入按钮, 3.以[申请表编号]为条件新增导入转入方申请表数据, 4.以[申请表编号]为条件覆盖导入转入方申请表数据 (
	 * 先删除以前所有数据,导入最新数据 )
	 * 
	 * @param fptAppHead
	 * @return result = List<FptAppItem> 比QP的代码本人认为要更为高明 用进程任务相对于反回信息,要更高明一些
	 */
	public List<FptAppItem> downloadFptAppItemsInByQp(Request request,
			String taskId, FptAppHead fptAppHead);

	/**
	 * 获得 正在处理 的转出申请表 来自海关编码没有明细 在转厂申请表模块 [转入方] 加入按
	 * [对方公司海关编码]从QP系统查询已审核通的的转厂申请表, 用户可以选取,批量从QP导入,不提供覆盖导入
	 * 
	 * @param outTradeCode
	 * @return
	 */
	public List<FptAppHead> findFptAppHeadByQp(Request request,
			String outTradeCode);

	/**
	 * 获得 正在处理 的转出申请表 来自海关编码没有明细 在转厂申请表模块 [转入方] 加入按
	 * [对方公司海关编码]从QP系统查询已审核通的的转厂申请表, 用户可以选取,批量从QP导入,不提供覆盖导入
	 * 
	 * save 转出申请表 来自海关编码 有明细
	 * 
	 * @param outTradeCode
	 * @param outCopAppNos
	 * @return
	 */

	public List<FptAppHead> downloadFptAppHeadsOutByQp(Request request,
			String taskId, String outTradeCode, List<FptAppHead> fptAppHeads);

	/**/// //////////////////////以下是转厂收退货单//////////////////////////**/
	/**
	 * 
	 * 转厂收货单出口
	 * 
	 */
	public List downloadFptBillItemsOutByQp(Request request, String taskId,
			FptBillHead fptBillHead);

	/**
	 * 转厂收货单进口
	 * 
	 * @param request
	 * @param taskId
	 * @param fptBillHead
	 * @return
	 */
	public List downloadFptBillItemsInByQp(Request request, String taskId,
			FptBillHead fptBillHead);
	
	
	/**
	 * 根据核销关封状态获得关封申请单所有数据
	 * @param request
	 * @param scmCoc 客户/供应商
	 * @param fptInOutFlag 转厂标志
	 * @param isCollated 核销关封状态
	 * @return
	 */
	public List findFptAppHeadByIsCollatedAndScmCoc(Request request,ScmCoc scmCoc,Date strDate,Date endDate,String fptInOutFlag,Boolean isCollated,Boolean is_can);
	
	
	String getTomcatDir();
	
	/**
	 * 
	 * @param request
	 * @param excelFileContent
	 * @param taskId
	 * @param FptInOutFlag
	 * @param list 			查找出来的申请表Id  数组
	 * @return
	 */
    public List<Object[]> importFptApp(Request request,byte[] excelFileContent, String taskId,String FptInOutFlag,List<FptAppHead> list,Date endDate);
    
    List<FptAppHead>  importFptAppisExists(Request request,byte[] excelFileContent,String ioFlag);
    /**
     * 判断收发货单中商品明细“收发货序号是否存在”
     * @param request
     * @param head
     * @param outNo
     * @param fptInOutFlag
     * @return
     */
    public boolean   isExistFptBillItemByOutNo(Request request,FptBillHead head,Integer outNo,String fptInOutFlag);
    /**
     * 查询是否存在
     * @param seqNo 电子口岸统一编号
     * @param AppNo 申请表编号
     * @param fptInOutFlag
     * @return
     */
    boolean findExistsSeqNoOrAppNo(Request request,String seqNo, String appNo, String fptInOutFlag);
    
    /**
     * 
     * @param request
     * @param seqNo
     * @param appNo
     * @param fptInOutFlag
     * @return
     */
    boolean isExistFptFptBillHeadByAppNo(Request request,String appNo, String fptInOutFlag);
	
	/**
	 * 分页条件查询合同成品或料件的商品编码
	 * 
	 * @param index
	 * @param length
	 * @param projectType
	 * @param emsNo
	 * @param isMaterial
	 * @return
	 */
	public List findComplexByContract(Request request,int index, int length, int projectType,String emsNo, 
			boolean isMaterial,String property,Object value, boolean isLike,Boolean isAll);

	/**
	 * 查询关封申请单明细成品
	 * @param request
	 * @param emsNo
	 * @param seqNum
	 * @param projectType
	 * @return
	 */
	public List findFptAppItemExgBySeqNum(Request request,String emsNo, Integer seqNum,Integer projectType);
}
