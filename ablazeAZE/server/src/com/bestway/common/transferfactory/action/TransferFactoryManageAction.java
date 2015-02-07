package com.bestway.common.transferfactory.action;

import java.util.Date;
import java.util.List;

import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopRequestBill;
import com.bestway.common.transferfactory.entity.TempCustomsEnvelopCommInfo;
import com.bestway.common.transferfactory.entity.TransParameterSet;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.common.transferfactory.entity.TransferFactoryCommodityInfo;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBill;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBillCommodityInfo;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

public interface TransferFactoryManageAction {

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	TransParameterSet findTransParameterSet(Request request);

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	TransParameterSet saveTransParameterSet(Request request,
			TransParameterSet parameterSet);

	/**
	 * 获得关封申请单所有数据
	 */
	List findCustomsEnvelopRequestBill(Request request);
	
	/**
	 * 获得关封所有数据（进口）
	 */
	List getEnvelopBillDetail(Request request,List<Condition> conditions,List<Condition> conditions1);

	/**
	 * 获得关封所有数据（出口）
	 */
	List getEnvelopBillDetailout(Request request,List<Condition> conditions,List<Condition> conditions1);
	/**
	 * 获得关封申请单所有数据来自进出货标志
	 */
	List findCustomsEnvelopRequestBillByImpExpGoodsFlag(Request request,
			boolean impExpGoodsFlag);

	/**
	 * 获得关封申请单数据来自客户或供应商Id
	 */
	List findCustomsEnvelopRequestBillByScmCocId(Request request,
			String scmCocId);

	/**
	 * 保存关封申请单
	 */
	CustomsEnvelopRequestBill saveCustomsEnvelopRequestBill(Request request,
			CustomsEnvelopRequestBill customsEnvelopRequestBill);

	/**
	 * 删除关封申请单
	 */
	void deleteCustomsEnvelopRequestBill(Request request,
			CustomsEnvelopRequestBill customsEnvelopRequestBill);

	/**
	 * 删除关封申请单商品信息数据
	 */
	void deleteCustomsEnvelopRequestCommodityInfo(Request request, List list);

	/**
	 * 保存关封申请单商品信息数据
	 */
	List saveCustomsEnvelopRequestCommodityInfo(Request request, List list);

//	Double findCECI(Request request,String emsNo,Integer seqNum,boolean isImpExpGoods);
	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	List findCustomsEnvelopRequestCommodityInfo(Request request, String parentId);

	/**
	 * 获得最大的单据号来自进出货标志
	 */
	long getMaxBillNoByImpExpGoodsFlag(Request request, boolean impExpGoodsFlag);

	/**
	 * 获得关封申请单商品信息记录来自数据是否正确的检验
	 */
	List findCustomsEnvelopRequestCommodityInfoByCheck(Request request,
			String parentId);

	/**
	 * 获得关封单据所有数据
	 */
	List findCustomsEnvelopBill(Request request);

	/**
	 * 获得关封单据所有数据
	 */
	List findCustomsEnvelopBill(Request request, boolean impExpGoodsFlag,
			boolean isAvailability);

	/**
	 * 获得关封单据所有数据
	 */
	List findCustomsEnvelopBill(Request request, boolean isImport,
			boolean isAvailability, ScmCoc scmCoc);

	/**
	 * 获得关封单据所有数据
	 */
	List findCustomsEnvelopCommodityInfo(Request request, boolean isImport,
			boolean isAvailability, String emsNo, ScmCoc scmCoc);

	/**
	 * 获得关封单据所有数据来自进出货标志
	 */
	List findCustomsEnvelopBill(Request request, boolean impExpGoodsFlag,
			String billNo, ScmCoc sc, Date beginDate, Date endDate,
			Boolean isEndCase);

	/**
	 * 查找结转单据商品的可待转数量还有多少
	 */
	double findTransferFactoryCommodityInfoLeft(Request request,
			TransferFactoryCommodityInfo commInfo);

	/**
	 * 获得关封单据数据来自客户或供应商Id
	 */
	List findCustomsEnvelopBillByScmCocId(Request request, String scmCocId);

	/**
	 * 保存关封单据
	 */
	CustomsEnvelopBill saveCustomsEnvelopBill(Request request,
			CustomsEnvelopBill customsEnvelopBill);

	/**
	 * 删除关封单据
	 */
	void deleteCustomsEnvelopBill(Request request,
			CustomsEnvelopBill customsEnvelopBill);

	/**
	 * 删除关封单据商品信息数据
	 */
	List deleteCustomsEnvelopCommodityInfo(Request request, List list);

	/**
	 * 保存关封单据商品信息数据
	 */
	List saveCustomsEnvelopCommodityInfo(Request request, List list);
	
	/**
	 * 通过合同号查找合同料件
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	Double findContractImgByConNoAndEmsNo(Request request, String emsNo,Integer seqNum);
	
	Double findContractExgByConNoAndEmsNo(Request request, String emsNo,Integer seqNum);
	/**
	 * 查找报关单该料件的合同使用量
	 * @param request
	 * @param commSerialNo 商品序号
	 * @param impExpFlag 是否进出
	 * @param emsNo 手册编号
	 * @return
	 */
	double findCommInfoTotalAmount(Request request,Integer commSerialNo,
			Integer impExpFlag,Integer[] impExpType,String emsNo);
	/**
	 * 获得关封单据商品信息记录来自数据是否正确的检验
	 */
	List findCustomsEnvelopCommodityInfoByCheck(Request request, String parentId);

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
	 * 根据系统类型抓取正在执行的账册号或手册号
	 */
	List findEmsHeadByProjectType(Request request, Integer projectType);

	/**
	 * 根据系统类型，账册号或手册号，物料和成品分类抓取转厂的物料信息
	 * 
	 * @param projectType
	 * @return
	 */
	List findTempCustomsEnvelopRequestCommInfo(Request request,
			Integer projectType, String emsNo,
			CustomsEnvelopBill customsEnvelopBill, boolean isMaterial);

	/**
	 * 把查询出来的临时商品信息,保存为关封的商品信息
	 * 
	 * @param list
	 * @param envelopBill
	 * @return
	 */
	List saveCustomsEnvelopRequestCommInfo(Request request,
			List<TempCustomsEnvelopCommInfo> list,
			CustomsEnvelopBill envelopBill);

	/**
	 * 拆分结转单据，将结转单据没有报关的数量，生成一个新的结转单据
	 * 
	 * @param transferFactoryBill
	 * @return
	 */
	TransferFactoryBill splitTransferFactoryBill(Request request,
			TransferFactoryBill transferFactoryBill);

	/**
	 * 获得结转所有单据
	 */
	List findTransferFactoryBill(Request request);

	/**
	 * 获得结转所有单据来自进出货标志
	 */
	List findTransferFactoryBillByImpExpGoodsFlag(Request request,
			boolean impExpGoodsFlag);

	/**
	 * 获得结转所有单据来自客户或供应商Id
	 */
	List findTransferFactoryBillByScmCocId(Request request, String scmCocId,
			boolean impExpGoods);

	/**
	 * 保存结转单据
	 */
	TransferFactoryBill saveTransferFactoryBill(Request request,
			TransferFactoryBill transferFactoryBill);

	/**
	 * 删除结转单据
	 */
	void deleteTransferFactoryBill(Request request,
			TransferFactoryBill TransferFactoryBill);

	/**
	 * 删除结转单据商品信息数据
	 */
	void deleteTransferFactoryCommodityInfo(Request request, List list);

	/**
	 * 保存结转单据商品信息数据
	 */
	TransferFactoryCommodityInfo saveTransferFactoryCommodityInfo(
			Request request, TransferFactoryCommodityInfo commInfo);

	/**
	 * 保存结转单据商品信息数据
	 */
	List saveTransferFactoryCommodityInfo(Request request,
			TransferFactoryBill transferFactoryBill, List list);

	/**
	 * 获得结转单据记录来自数据是否正确的检验
	 */
	List findTransferFactoryCommodityInfoByCheck(Request request,
			String parentId);

	/**
	 * 查询结转单据中是否有商品信息
	 * @param request
	 * @param parentId
	 * @return
	 */
	List findImpExpCommodityInfo(Request request, String parentId);
	/**
	 * 获得最大的结转单据号来自进出货标志
	 */
	long getMaxTransferFactoryBillNoByImpExpGoodsFlag(Request request,
			boolean impExpGoodsFlag);

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

	/**
	 * 关封申请单-->返回已生成关封单据的关封申请单列表
	 */
	List makeCustomsEnvelopBill(Request request,
			CustomsEnvelopBill customsEnvelopBill, List dataSource);

	/**
	 * 修改关封申请单的关封单据字段,和生成关封单据字段为true
	 */
	List updateCustomsEnvelopRequestBill(Request requst,
			String customsEnvelopBillId, List dataSource);

	/**
	 * 保存转厂初始化单据
	 */
	TransferFactoryInitBill saveTransferFactoryInitBill(Request requst,
			TransferFactoryInitBill transferFactoryInitBill);

	/**
	 * 保存转厂初始化单据商品信息
	 */

	TransferFactoryInitBill saveTransferFactoryInitBillCommodityInfo(
			Request request, TransferFactoryInitBill initBill, List list);

	/**
	 * 保存转厂初始化单据商品信息
	 */
	TransferFactoryInitBillCommodityInfo saveTransferFactoryInitBillCommodityInfo(
			Request requst,
			TransferFactoryInitBillCommodityInfo transferFactoryInitBillCommodityInfo);

	/**
	 * 删除转厂初始化单据
	 */
	void deleteTransferFactoryInitBill(Request requst,
			TransferFactoryInitBill transferFactoryInitBill);

	/**
	 * 删除转厂初始化单据商品信息
	 */

	TransferFactoryInitBill deleteTransferFactoryInitBillCommodityInfo(
			Request requst, TransferFactoryInitBill initBill, List list);

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
	 * 获得转厂单据来自选定用客户，且生效、未转报关清单的单据
	 */
	List findTempTransferFactoryBillByScmCocNotATC(Request request,
			String scmCocId, String emsNo);

	/**
	 * 获得转厂单据商品信息来自父对象List
	 */
	List findTempTransferFactoryCommodityInfoByParent(Request request,
			List parentList);

	/**
	 * 
	 * 结转单据--->返回已生成关封申请单据的结转单据列表
	 * 
	 */
	List makeCustomsEnvelopRequestBill(Request request,
			CustomsEnvelopRequestBill customsEnvelopRequestBill, List dataSource);

	/**
	 * 修改结转单据的关封申请单字段,和生成关封申请单据字段为true
	 */
	List updateTransferFactoryBillByCER(Request request,
			String customsEnvelopRequestBillId, List dataSource);

	/**
	 * 结转单转报关清单时商品信息的检查--返回没有在归并关系中备案的数据(成品)
	 */
	List checkTempTransferFactoryCommodityInfoByFinishProduct(Request request,
			List list, EmsEdiMergerHead emsEdiMergerHead);

	/**
	 * 结转单转报关清单时商品信息的检查--返回没有在归并关系中备案的数据(料件)
	 */
	List checkTempTransferFactoryCommodityInfoByMateriel(Request request,
			List list, EmsEdiMergerHead emsEdiMergerHead);

	/**
	 * 结转单据--->报关清单返回报关清单 id isNewRecord 是代表生成新的报关单还是追加到原有的报关单 isImportGoods
	 * 是进货还是出货(出口还是进口)
	 */
	List makeDzscCustomsBillList(Request request,
			DzscCustomsBillList customsBillList, List lsTransFactCommInfo);

	/**
	 * 结转单据--->报关清单返回报关清单 id isNewRecord 是代表生成新的报关单还是追加到原有的报关单 isImportGoods
	 * 是进货还是出货(出口还是进口)
	 */
	List makeApplyToCustomsBill(Request request,
			ApplyToCustomsBillList customsBillList, List lsTransFactCommInfo,
			EmsEdiMergerHead emsEdiMergerHead);

	/**
	 * 修改结转单据的报关清单字段,和生成报关清单据字段为true -->来自报关清单Id
	 */
	List updateTransFactBillByCustomsBillListId(Request request,
			String applyToCustomsBillId, List dataSource);

	/**
	 * 获得关封单据信息加载子表的记录
	 */
	List findCustomsEnvelopCommodityInfo(Request request, String parentId);

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
			Date endDate, Integer billType, String billCode,
			String materielCode, String materielName, String seqNum,
			String companyName);

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
			Date endDate, Integer billType, String billCode,
			String materielCode, String materielName, String seqNum,
			String companyName);

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
			Date beginDate, Date endDate, Integer impExpFlag, String billCode,
			String materielCode, String materielName, String materielSpec,
			String companyName, String scmcode);

	/**
	 * 获得转厂单据商品信息来自父对象List 用于转换报关清单过程中
	 */
	List findTempTransferFactoryCommodityInfoByParentToACT(Request request,
			List parentList);

	/**
	 * 获得没有结案,没有过期,生效的关封单据
	 */
	List findCustomsEnvelopBillByAvailability(Request request, String scmCocId);

	/**
	 * 根据关封单据id获得其计算后的detail
	 */
	List findCustomsEnvelopCommodityInfoByTempTransferFactoryCommodityInfo(
			Request request, String parentId, boolean isImportGoods,
			List tempTransferFactoryCommodityInfoList);

	/**
	 * 生成结转单据
	 */
	void makeTransferFactoryBill(Request request, List billMasterList,
			String envelopBillCode, String emsNo);

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
	List findCustomsEnvelopSpareAnalyes(Request request, Date beginDate, Date endDate,String commodityCode,
			String commodityName, String seqNum, String oppoisteEnterpriseName,
			boolean isImportGoods, String customsEnvelopBillNo,boolean isshow);

	/**
	 * 有数据转报关清单在关封中
	 * 
	 * @param c
	 * @return
	 */
	boolean hasDataTransFactoryBillByEnvelopId(Request request,
			CustomsEnvelopBill c);

	/**
	 * 获得当前转厂进出口的商品信息的个数
	 */
	int findTransferFactoryCommodityInfoCount(Request request, String parentId);

	/**
	 * 获得当前关封单据的商品信息的个数
	 */
	int findCustomsEnvelopCommodityInfoCount(Request request, String parentId);

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
			Request request, CustomsEnvelopRequestBill c);

	/**
	 * 有数据已转关封申请单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	boolean hasDataCustomsDeclarationByTransFactBill(Request request,
			TransferFactoryBill t);

	/**
	 * 有数据已报关清单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	boolean hasDataTransferApplyToCustomsBillByTransferFactoryBill(
			Request request, TransferFactoryBill t);

	/**
	 * 查找结转单据生成报关清单时的中间表信息来自关封相关的数据项
	 * 
	 * @param c
	 * @return
	 */
	List findMakeApplyToCustomsInfoByCustomsEnvelopBill(Request request,
			CustomsEnvelopBill c);

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	List findCustomsEnvelopCommodityInfo(Request request);

	/**
	 * 转厂分析查询权限控制
	 */
	void checkTransferStatisticAnalyse(Request request);

	/**
	 * 关封余量分析权限控制
	 */

	void checkCustomsEnvelopSpareAnalyse(Request request);

	/**
	 * 抓取报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	List findExportCustomsDeclaration(Request request,
			CustomsEnvelopBill customsEnvelopBill);

	/**
	 * 抓取报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	List findImportCustomsDeclaration(Request request,
			CustomsEnvelopBill customsEnvelopBill);

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

	/**
	 * 根据单据号查询关封商品明细
	 * 
	 * @param customsEnvelopBillCode
	 * @return
	 */
	List findTempTransferFactoryCommInfo(Request request,
			boolean isImport, ScmCoc scmCoc);

	/**
	 * 查询不在转厂起初单的商品编码
	 * 
	 * @param initBillId
	 * @return
	 */
	List findComplexNotInInitBill(Request request, String initBillId,
			String customsEnvelopBillCode);

	/**
	 * 结转单据自动转报关单及其商品信息
	 * 
	 * @param lsBills
	 * @param param
	 * @return
	 */
	List makeCusomsDeclarationFromTransferBill(Request request, List lsBills,
			MakeCusomsDeclarationParam param, boolean isMergeOne);

	/**
	 * 查询未转报关单的结转单据
	 * 
	 * @param isImport
	 * @return
	 */
	List findTransFactBillMakeCustomsDeclaration(Request request,
			boolean isImport, ScmCoc scmCoc);

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

	/**
	 * 根据客户供应商查找转厂商品
	 * 
	 * @param scmcoc
	 *            客户供应商
	 * @return 转厂商品
	 */
	List findCustomsEnvelopCommodityInfoByScmCoc(Request request,
			ScmCoc scmcoc, Boolean isCustomer);

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
	
	// 2011-6-29
	List findTransferClienCollate(Request request, Integer projectType,
			String emsNo, String scmcocCode, Date beginDate, Date endDate,
			String spec, String name, Integer impExpType);

	List traCustomsCollate(Request request, String year, boolean isImpType);

	/**
	 * 根据关封号抓取关封里所有的手册号
	 * 
	 * @param envelopNo
	 * @return
	 */
	List findCustomsEnvelopBillEmsNo(Request request, String envelopNo);

	/**
	 * 判断单据号是否已经存在
	 * @param request
	 * @param no
	 * @return
	 */
	public boolean isExistsNo(Request request, String no);

	void getParaPurview(Request request);
	/**
	 * 转抄关封资料
	 * 
	 * @param customsEnvelopBill
	 *            要转抄的关封表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @param billNo
	 *            关封号
	 * @return 新的申请单表头
	 */
	 CustomsEnvelopBill copyCustomsEnvelopBillAndCommodityInfo(Request request,
			CustomsEnvelopBill customsEnvelopBill, Boolean copyInfo, String billNo);
}
