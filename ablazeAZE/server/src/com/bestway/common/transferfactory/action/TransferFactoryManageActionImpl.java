/***
 * 浏览进出货转厂期初单 1
 保存进出货转厂期初单 2
 删除进出货转厂期初单 3

 
 浏览结转单据 4
 保存结转单据 5
 删除结转单据 6

 浏览关封申请单 7
 保存关封申请单 8
 删除关封申请单 9


 浏览关封 10
 保存关封 11
 删除关封 12
 关封余量分析 13
 转厂统计分析 14

 */

package com.bestway.common.transferfactory.action;

import java.util.Date;
import java.util.List;

import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.dao.TransferFactoryManageDao;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopRequestBill;
import com.bestway.common.transferfactory.entity.TempCustomsEnvelopCommInfo;
import com.bestway.common.transferfactory.entity.TransParameterSet;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.common.transferfactory.entity.TransferFactoryCommodityInfo;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBill;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBillCommodityInfo;
import com.bestway.common.transferfactory.logic.TransferFactoryManageLogic;
import com.bestway.common.transferfactory.logic.TransferQueryLogic;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

@AuthorityClassAnnotation(caption = "转厂管理", index = 12)
@SuppressWarnings("unchecked")
public class TransferFactoryManageActionImpl extends BaseActionImpl implements
		TransferFactoryManageAction {
	private TransferFactoryManageDao transferFactoryManageDao = null;

	private TransferFactoryManageLogic transferFactoryManageLogic = null;

	private TransferQueryLogic transferQueryLogic = null;

	public TransferFactoryManageDao getTransferFactoryManageDao() {
		return transferFactoryManageDao;
	}

	public void setTransferFactoryManageDao(
			TransferFactoryManageDao transferFactoryManageDao) {
		this.transferFactoryManageDao = transferFactoryManageDao;
	}

	public TransferFactoryManageLogic getTransferFactoryManageLogic() {
		return transferFactoryManageLogic;
	}

	public void setTransferFactoryManageLogic(
			TransferFactoryManageLogic transferFactoryManageLogic) {
		this.transferFactoryManageLogic = transferFactoryManageLogic;
	}

	public TransferFactoryManageActionImpl() {
		// this.setModuleName("Authority");
	}

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	public TransParameterSet findTransParameterSet(Request request) {
		return this.transferFactoryManageDao.findTransParameterSet();
	}

	/**
	 * 通过合同号查找合同料件
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public Double findContractImgByConNoAndEmsNo(Request request, String emsNo,
			Integer seqNum) {
		return this.transferFactoryManageDao.findContractImgByConNoAndEmsNo(
				emsNo, seqNum);
	}

	public Double findContractExgByConNoAndEmsNo(Request request, String emsNo,
			Integer seqNum) {
		return this.transferFactoryManageDao.findContractExgByConNoAndEmsNo(
				emsNo, seqNum);
	}

	public double findCommInfoTotalAmount(Request request,
			Integer commSerialNo, Integer impExpFlag, Integer[] impExpType,
			String emsNo) {
		return this.transferFactoryManageDao.findCommInfoTotalAmount(
				commSerialNo, impExpFlag, impExpType, emsNo);
	}

	// public Double findCECI(Request request, String emsNo, Integer seqNum,
	// boolean isImpExpGoods) {
	// return this.transferFactoryManageDao.findCECI(emsNo, seqNum,
	// isImpExpGoods);
	// }

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	public TransParameterSet saveTransParameterSet(Request request,
			TransParameterSet parameterSet) {
		this.transferFactoryManageDao.saveOrUpdate(parameterSet);
		return parameterSet;
	}

	public List getEnvelopBillDetail(Request request,
			List<Condition> conditions, List<Condition> conditions1) {
		return this.transferFactoryManageLogic.getEnvelopBillDetail(conditions,
				conditions1);
	}

	public List getEnvelopBillDetailout(Request request,
			List<Condition> conditions, List<Condition> conditions1) {
		return this.transferFactoryManageLogic.getEnvelopBillDetailout(
				conditions, conditions1);
	}

	/**
	 * 获得关封申请单所有数据
	 */

	@AuthorityFunctionAnnotation(caption = "关封申请单-浏览", index = 0.45)
	public List findCustomsEnvelopRequestBill(Request request) {
		return transferFactoryManageDao.findCustomsEnvelopRequestBill();
	}

	/**
	 * 获得关封申请单所有数据来自进出货标志
	 */
	@AuthorityFunctionAnnotation(caption = "关封申请单-浏览", index = 0.45)
	public List findCustomsEnvelopRequestBillByImpExpGoodsFlag(Request request,
			boolean impExpGoodsFlag) {
		return transferFactoryManageDao
				.findCustomsEnvelopRequestBillByImpExpGoodsFlag(impExpGoodsFlag);
	}

	/**
	 * 获得关封申请单数据来自客户或供应商Id
	 */
	@AuthorityFunctionAnnotation(caption = "关封申请单-浏览", index = 0.45)
	public List findCustomsEnvelopRequestBillByScmCocId(Request request,
			String scmCocId) {
		return transferFactoryManageDao
				.findCustomsEnvelopRequestBillByScmCocId(scmCocId);
	}

	/**
	 * 保存关封申请单
	 */
	@AuthorityFunctionAnnotation(caption = "关封申请单-保存", index = 0.454)
	public CustomsEnvelopRequestBill saveCustomsEnvelopRequestBill(
			Request request, CustomsEnvelopRequestBill customsEnvelopRequestBill) {
		transferFactoryManageDao
				.saveCustomsEnvelopRequestBill(customsEnvelopRequestBill);
		return customsEnvelopRequestBill;

	}

	/**
	 * 删除关封申请单
	 */
	@AuthorityFunctionAnnotation(caption = "关封申请单-删除", index = 0.452)
	public void deleteCustomsEnvelopRequestBill(Request request,
			CustomsEnvelopRequestBill customsEnvelopRequestBill) {
		transferFactoryManageLogic
				.deleteCustomsEnvelopRequestBill(customsEnvelopRequestBill);
	}

	/**
	 * 删除关封申请单商品信息数据
	 */
	@AuthorityFunctionAnnotation(caption = "关封申请单-删除", index = 0.452)
	public void deleteCustomsEnvelopRequestCommodityInfo(Request request,
			List list) {
		transferFactoryManageLogic
				.deleteCustomsEnvelopRequestCommodityInfo(list);
	}

	/**
	 * 保存关封申请单商品信息数据
	 */
	@AuthorityFunctionAnnotation(caption = "关封申请单-保存", index = 0.454)
	public List saveCustomsEnvelopRequestCommodityInfo(Request request,
			List list) {
		transferFactoryManageDao.saveCustomsEnvelopRequestCommodityInfo(list);
		return list;
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	@AuthorityFunctionAnnotation(caption = "关封申请单-浏览", index = 0.45)
	public List findCustomsEnvelopRequestCommodityInfo(Request request,
			String parentId) {
		return transferFactoryManageDao
				.findCustomsEnvelopRequestCommodityInfo(parentId);
	}

	/**
	 * 查找结转单据商品的可待转数量还有多少
	 */
	public double findTransferFactoryCommodityInfoLeft(Request request,
			TransferFactoryCommodityInfo commInfo) {
		return transferFactoryManageLogic
				.findTransferFactoryCommodityInfoLeft(commInfo);
	}

	/**
	 * 获得最大的单据号来自进出货标志
	 */

	public long getMaxBillNoByImpExpGoodsFlag(Request request,
			boolean impExpGoodsFlag) {
		return transferFactoryManageLogic
				.getMaxBillNoByImpExpGoodsFlag(impExpGoodsFlag);
	}

	/**
	 * 获得关封申请单商品信息记录来自数据是否正确的检验
	 */
	public List findCustomsEnvelopRequestCommodityInfoByCheck(Request request,
			String parentId) {
		return transferFactoryManageLogic
				.findCustomsEnvelopRequestCommodityInfoByCheck(parentId);
	}

	/**
	 * ---------------------------------------------------- 关封单据用到的方法
	 * -------------------------------------------------------
	 */
	/**
	 * 根据系统类型抓取正在执行的账册号或手册号
	 */
	public List findEmsHeadByProjectType(Request request, Integer projectType) {
		return this.transferFactoryManageLogic
				.findEmsHeadByProjectType(projectType);
	}

	/**
	 * 根据系统类型，账册号或手册号，物料和成品分类抓取转厂的物料信息
	 * 
	 * @param projectType
	 * @return
	 */
	public List findTempCustomsEnvelopRequestCommInfo(Request request,
			Integer projectType, String emsNo,
			CustomsEnvelopBill customsEnvelopBill, boolean isMaterial) {
		return this.transferFactoryManageLogic
				.findTempCustomsEnvelopRequestCommInfo(projectType, emsNo,
						customsEnvelopBill, isMaterial);
	}

	/**
	 * 把查询出来的临时商品信息,保存为关封的商品信息
	 * 
	 * @param list
	 * @param envelopBill
	 * @return
	 */
	public List saveCustomsEnvelopRequestCommInfo(Request request,
			List<TempCustomsEnvelopCommInfo> list,
			CustomsEnvelopBill envelopBill) {
		return this.transferFactoryManageLogic
				.saveCustomsEnvelopRequestCommInfo(list, envelopBill);
	}

	/**
	 * 获得关封单据所有数据
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0.01)
	public List findCustomsEnvelopBill(Request request) {
		return transferFactoryManageDao.findCustomsEnvelopBill();
	}

	/**
	 * 获得关封单据所有数据
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0.01)
	public List findCustomsEnvelopBill(Request request,
			boolean impExpGoodsFlag, boolean isAvailability) {
		return transferFactoryManageDao.findCustomsEnvelopBill(impExpGoodsFlag,
				isAvailability);
	}

	/**
	 * 获得关封单据所有数据
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0.01)
	public List findCustomsEnvelopBill(Request request, boolean isImport,
			boolean isAvailability, ScmCoc scmCoc) {
		return this.transferFactoryManageDao.findCustomsEnvelopBill(isImport,
				isAvailability, scmCoc);
	}

	/**
	 * 获得关封单据所有数据
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0.01)
	public List findCustomsEnvelopCommodityInfo(Request request,
			boolean isImport, boolean isAvailability, String emsNo,
			ScmCoc scmCoc) {
		return this.transferFactoryManageDao.findCustomsEnvelopCommodityInfo(
				isImport, isAvailability, emsNo, scmCoc);
	}

	/**
	 * 获得关封单据所有数据来自进出货标志
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0.01)
	public List findCustomsEnvelopBill(Request request,
			boolean impExpGoodsFlag, String billNo, ScmCoc sc, Date beginDate,
			Date endDate, Boolean isEndCase) {
		return transferFactoryManageDao.findCustomsEnvelopBill(impExpGoodsFlag,
				billNo, sc, beginDate, endDate, isEndCase);
	}

	/**
	 * 获得关封单据数据来自客户或供应商Id
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0.01)
	public List findCustomsEnvelopBillByScmCocId(Request request,
			String scmCocId) {
		return transferFactoryManageDao
				.findCustomsEnvelopBillByScmCocId(scmCocId);
	}

	/**
	 * 保存关封单据
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-保存", index = 0.1)
	public CustomsEnvelopBill saveCustomsEnvelopBill(Request request,
			CustomsEnvelopBill customsEnvelopBill) {
		transferFactoryManageLogic.saveCustomsEnvelopBill(customsEnvelopBill);
		return customsEnvelopBill;
	}

	/**
	 * 删除关封单据
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-删除", index = 0.21)
	public void deleteCustomsEnvelopBill(Request request,
			CustomsEnvelopBill customsEnvelopBill) {
		transferFactoryManageLogic.deleteCustomsEnvelopBill(customsEnvelopBill);
	}

	/**
	 * 删除关封单据商品信息数据
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-删除", index = 0.21)
	public List deleteCustomsEnvelopCommodityInfo(Request request, List list) {
		this.transferFactoryManageLogic.deleteCustomsEnvelopCommodityInfo(list);
		return list;
	}

	/**
	 * 保存关封单据商品信息数据
	 */
	@AuthorityFunctionAnnotation(caption = "关封管理-保存", index = 0.1)
	public List saveCustomsEnvelopCommodityInfo(Request request, List list) {
		transferFactoryManageDao.saveCustomsEnvelopCommodityInfo(list);
		return list;
	}

	/**
	 * 获得关封单据信息加载子表的记录
	 */
	public List findCustomsEnvelopCommodityInfo(Request request, String parentId) {
		// return this.transferFactoryManageDao
		// .findCustomsEnvelopCommodityInfo(parentId);
		return this.transferFactoryManageLogic
				.findCustomsEnvelopCommodityInfo(parentId);
	}

	/**
	 * 获得关封单据商品信息记录来自数据是否正确的检验
	 */
	public List findCustomsEnvelopCommodityInfoByCheck(Request request,
			String parentId) {
		return this.transferFactoryManageLogic
				.findCustomsEnvelopCommodityInfoByCheck(parentId);
	}

	/**
	 * 来自电子帐册料件--并通过封商品信息进行筛选
	 */
	public List findEmsMateriel(Request request, EmsHeadH2k emsH2k,
			String parentId) {
		return this.transferFactoryManageDao.findEmsMateriel(emsH2k, parentId);
	}

	/**
	 * 来自电子帐册成品--并通过封商品信息进行筛选
	 */
	public List findEmsFinishedProduct(Request request, EmsHeadH2k emsH2k,
			String parentId) {
		return this.transferFactoryManageDao.findEmsFinishedProduct(emsH2k,
				parentId);
	}

	/**
	 * * ---------------------------------------------------- 结转单据用到的方法
	 * -------------------------------------------------------
	 */
	/**
	 * 拆分结转单据，将结转单据没有报关的数量，生成一个新的结转单据
	 * 
	 * @param transferFactoryBill
	 * @return
	 */
	public TransferFactoryBill splitTransferFactoryBill(Request request,
			TransferFactoryBill transferFactoryBill) {
		return this.transferFactoryManageLogic
				.splitTransferFactoryBill(transferFactoryBill);
	}

	/**
	 * 获得结转所有单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-浏览", index = 2)
	public List findTransferFactoryBill(Request request) {
		return transferFactoryManageDao.findTransferFactoryBill();
	}

	/**
	 * 获得结转所有单据来自进出货标志
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-浏览", index = 2)
	public List findTransferFactoryBillByImpExpGoodsFlag(Request request,
			boolean impExpGoodsFlag) {
		return transferFactoryManageDao
				.findTransferFactoryBillByImpExpGoodsFlag(impExpGoodsFlag);
	}

	/**
	 * 获得结转所有单据来自客户或供应商Id
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-浏览", index = 2)
	public List findTransferFactoryBillByScmCocId(Request request,
			String scmCocId, boolean impExpGoods) {
		return transferFactoryManageDao.findTransferFactoryBillByScmCocId(
				scmCocId, impExpGoods);
	}

	/**
	 * 保存结转单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public TransferFactoryBill saveTransferFactoryBill(Request request,
			TransferFactoryBill transferFactoryBill) {
		transferFactoryManageLogic.saveTransferFactoryBill(transferFactoryBill);
		return transferFactoryBill;
	}

	/**
	 * 删除结转单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-删除", index = 2.3)
	public void deleteTransferFactoryBill(Request request,
			TransferFactoryBill TransferFactoryBill) {
		transferFactoryManageLogic
				.deleteTransferFactoryBill(TransferFactoryBill);
	}

	/**
	 * 删除结转单据商品信息数据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-删除", index = 2.3)
	public void deleteTransferFactoryCommodityInfo(Request request, List list) {
		transferFactoryManageLogic.deleteTransferFactoryCommodityInfo(list);
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public TransferFactoryCommodityInfo saveTransferFactoryCommodityInfo(
			Request request, TransferFactoryCommodityInfo commInfo) {
		this.transferFactoryManageLogic
				.saveTransferFactoryCommodityInfo(commInfo);
		return commInfo;
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public List saveTransferFactoryCommodityInfo(Request request,
			TransferFactoryBill transferFactoryBill, List list) {
		return transferFactoryManageLogic.saveTransferFactoryCommodityInfo(
				transferFactoryBill, list);
	}

	/**
	 * 获得当前转厂进出口的商品信息的个数
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-浏览", index = 2)
	public int findTransferFactoryCommodityInfoCount(Request request,
			String parentId) {
		return transferFactoryManageDao
				.findTransferFactoryCommodityInfoCount(parentId);
	}

	/**
	 * 获得结转单据记录来自数据是否正确的检验
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-浏览", index = 2)
	public List findTransferFactoryCommodityInfoByCheck(Request request,
			String parentId) {
		return this.transferFactoryManageLogic
				.findTransferFactoryCommodityInfoByCheck(parentId);
	}

	/**
	 * 获得最大的结转单据号来自进出货标志
	 */
	public long getMaxTransferFactoryBillNoByImpExpGoodsFlag(Request request,
			boolean impExpGoodsFlag) {
		return this.transferFactoryManageLogic
				.getMaxTransferFactoryBillNoByImpExpGoodsFlag(impExpGoodsFlag);
	}

	/**
	 * 获得关封申请单据来自选定用客户，且生效、未转关封的单据
	 */
	public List findTempCustomsEnvelopRequestBillByScmCoc(Request request,
			String scmCocId) {
		return this.transferFactoryManageLogic
				.findTempCustomsEnvelopRequestBillByScmCoc(scmCocId);
	}

	/**
	 * 获得关封申请单据商品信息来自父对象List
	 */
	public List findTempCustomsEnvelopRequestCommodityInfoByParent(
			Request request, List parentList) {
		return this.transferFactoryManageLogic
				.findTempCustomsEnvelopRequestCommodityInfoByParent(parentList);
	}

	/**
	 * 关封申请单转关封时商品信息的检查--返回没有在电子帐册中备案的数据(成品)
	 */
	public List checkTempCustomsEnvelopRequestCommodityInfoByFinishProduct(
			Request request, List list, EmsHeadH2k emsH2k,
			EmsEdiMergerHead emsEdiMergerHead) {
		return this.transferFactoryManageLogic
				.checkTempCustomsEnvelopRequestCommodityInfoByFinishProduct(
						list, emsH2k, emsEdiMergerHead);
	}

	/**
	 * 关封申请单转关封时商品信息的检查--返回没有在电子帐册中备案的数据(料件)
	 */
	public List checkTempCustomsEnvelopRequestCommodityInfoByMateriel(
			Request request, List list, EmsHeadH2k emsH2k,
			EmsEdiMergerHead emsEdiMergerHead) {
		return this.transferFactoryManageLogic
				.checkTempCustomsEnvelopRequestCommodityInfoByMateriel(list,
						emsH2k, emsEdiMergerHead);
	}

	/**
	 * 关封申请单-->返回已生成关封单据的关封申请单列表
	 */
	@AuthorityFunctionAnnotation(caption = "关封申请单-保存", index = 0.454)
	public List makeCustomsEnvelopBill(Request request,
			CustomsEnvelopBill customsEnvelopBill, List dataSource) {
		return this.transferFactoryManageLogic.makeCustomsEnvelopBill(
				customsEnvelopBill, dataSource);
	}

	/**
	 * 修改关封申请单的关封单据字段,和生成关封单据字段为true
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public List updateCustomsEnvelopRequestBill(Request requst,
			String customsEnvelopBillId, List dataSource) {
		return this.transferFactoryManageLogic.updateCustomsEnvelopRequestBill(
				customsEnvelopBillId, dataSource);
	}

	/**
	 * 保存转厂初始化单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂期初单-保存", index = 1.2)
	public TransferFactoryInitBill saveTransferFactoryInitBill(Request requst,
			TransferFactoryInitBill transferFactoryInitBill) {
		this.transferFactoryManageLogic
				.saveTransferFactoryInitBill(transferFactoryInitBill);
		return transferFactoryInitBill;
	}

	/**
	 * 保存转厂初始化单据商品信息
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂期初单-保存", index = 1.2)
	public TransferFactoryInitBill saveTransferFactoryInitBillCommodityInfo(
			Request requst, TransferFactoryInitBill initBill, List list) {
		return this.transferFactoryManageLogic
				.saveTransferFactoryInitBillCommodityInfo(initBill, list);
	}

	/**
	 * 保存转厂初始化单据商品信息
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂期初单-保存", index = 1.2)
	public TransferFactoryInitBillCommodityInfo saveTransferFactoryInitBillCommodityInfo(
			Request requst,
			TransferFactoryInitBillCommodityInfo transferFactoryInitBillCommodityInfo) {
		this.transferFactoryManageLogic
				.saveTransferFactoryInitBillCommodityInfo(transferFactoryInitBillCommodityInfo);
		return transferFactoryInitBillCommodityInfo;
	}

	/**
	 * 删除转厂初始化单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂期初单-删除", index = 1.3)
	public void deleteTransferFactoryInitBill(Request requst,
			TransferFactoryInitBill transferFactoryInitBill) {
		this.transferFactoryManageLogic
				.deleteTransferFactoryInitBill(transferFactoryInitBill);
	}

	/**
	 * 删除转厂初始化单据商品信息
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂期初单-删除", index = 1.3)
	public TransferFactoryInitBill deleteTransferFactoryInitBillCommodityInfo(
			Request requst, TransferFactoryInitBill initBill, List list) {
		this.transferFactoryManageLogic
				.deleteTransferFactoryInitBillCommodityInfo(initBill, list);
		return initBill;
	}

	/**
	 * 获得结转所有单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂期初单-浏览", index = 1)
	public List findTransferFactoryInitBill(Request requst) {
		return this.transferFactoryManageLogic.findTransferFactoryInitBill();
	}

	/**
	 * 获得结转所有单据
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂期初单-浏览", index = 1)
	public List findTransferFactoryInitBill(Request requst, boolean isImpExpFlag) {
		return this.transferFactoryManageLogic
				.findTransferFactoryInitBill(isImpExpFlag);
	}

	/**
	 * 取得最大转厂初始化单据号码+1
	 * 
	 * @return
	 */
	public String getTransferFactoryInitBillMaxCode(Request requst) {
		return transferFactoryManageLogic.getTransferFactoryInitBillMaxCode();
	}

	/**
	 * 获得转厂初始化单据信息加载子表的记录
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂期初单-浏览", index = 1)
	public List findTransferFactoryInitCommodityInfo(Request requst,
			String parentId) {
		return this.transferFactoryManageLogic
				.findTransferFactoryInitCommodityInfo(parentId);
	}

	/**
	 * 获得转厂单据来自选定用客户，且生效、未转关封的单据
	 */
	public List findTempTransferFactoryBillByScmCocNotCER(Request request,
			String scmCocId) {
		return this.transferFactoryManageLogic
				.findTempTransferFactoryBillByScmCocNotCER(scmCocId);
	}

	/**
	 * 获得转厂单据来自选定用客户，且生效、未转报关清单的单据
	 */
	public List findTempTransferFactoryBillByScmCocNotATC(Request request,
			String scmCocId, String emsNo) {
		return this.transferFactoryManageLogic
				.findTempTransferFactoryBillByScmCocNotATC(scmCocId, emsNo);
	}

	/**
	 * 获得转厂单据商品信息来自父对象List
	 */
	public List findTempTransferFactoryCommodityInfoByParent(Request request,
			List parentList) {
		return this.transferFactoryManageLogic
				.findTempTransferFactoryCommodityInfoByParent(parentList);
	}

	/**
	 * 
	 * 结转单据--->返回已生成关封申请单据的结转单据列表
	 * 
	 */
	@AuthorityFunctionAnnotation(caption = "关封申请单-保存", index = 0.454)
	public List makeCustomsEnvelopRequestBill(Request request,
			CustomsEnvelopRequestBill customsEnvelopRequestBill, List dataSource) {
		return this.transferFactoryManageLogic.makeCustomsEnvelopRequestBill(
				customsEnvelopRequestBill, dataSource);
	}

	/**
	 * 修改结转单据的关封申请单字段,和生成关封申请单据字段为true
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public List updateTransferFactoryBillByCER(Request request,
			String customsEnvelopRequestBillId, List dataSource) {
		return this.transferFactoryManageLogic.updateTransferFactoryBillByCER(
				customsEnvelopRequestBillId, dataSource);
	}

	/**
	 * 结转单转报关清单时商品信息的检查--返回没有在归并关系中备案的数据(成品)
	 */
	public List checkTempTransferFactoryCommodityInfoByFinishProduct(
			Request request, List list, EmsEdiMergerHead emsEdiMergerHead) {
		return this.transferFactoryManageLogic
				.checkTempTransferFactoryCommodityInfoByFinishProduct(list,
						emsEdiMergerHead);
	}

	/**
	 * 结转单转报关清单时商品信息的检查--返回没有在归并关系中备案的数据(料件)
	 */
	public List checkTempTransferFactoryCommodityInfoByMateriel(
			Request request, List list, EmsEdiMergerHead emsEdiMergerHead) {
		return this.transferFactoryManageLogic
				.checkTempTransferFactoryCommodityInfoByMateriel(list,
						emsEdiMergerHead);
	}

	/**
	 * 结转单据--->报关清单返回报关清单 id isNewRecord 是代表生成新的报关单还是追加到原有的报关单 isImportGoods
	 * 是进货还是出货(出口还是进口)
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public List makeDzscCustomsBillList(Request request,
			DzscCustomsBillList customsBillList, List lsTransFactCommInfo) {
		return this.transferFactoryManageLogic.makeDzscCustomsBillList(
				customsBillList, lsTransFactCommInfo);
	}

	/**
	 * 结转单据--->报关清单返回报关清单 id isNewRecord 是代表生成新的报关单还是追加到原有的报关单 isImportGoods
	 * 是进货还是出货(出口还是进口)
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public List makeApplyToCustomsBill(Request request,
			ApplyToCustomsBillList customsBillList, List lsTransFactCommInfo,
			EmsEdiMergerHead emsEdiMergerHead) {
		return this.transferFactoryManageLogic.makeApplyToCustomsBill(
				customsBillList, lsTransFactCommInfo, emsEdiMergerHead);
		// return new ArrayList();
	}

	/**
	 * 修改结转单据的报关清单字段,和生成报关清单据字段为true -->来自报关清单Id
	 */
	@AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public List updateTransFactBillByCustomsBillListId(Request request,
			String applyToCustomsBillId, List dataSource) {
		return this.transferFactoryManageLogic
				.updateTransFactBillByCustomsBillListId(applyToCustomsBillId,
						dataSource);
	}

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
	 * @param seqNum
	 *            帐册序号
	 * @param companyName
	 *            公司名称
	 * @return
	 */

	public List findTransferFactoryImpExpGoodsList(Request request,
			Date beginDate, Date endDate, Integer billType, String billCode,
			String materielCode, String materielName, String seqNum,
			String companyName) {
		return this.transferFactoryManageLogic
				.findTransferFactoryImpExpGoodsList(beginDate, endDate,
						billType, billCode, materielCode, materielName, seqNum,
						companyName);

	}

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
	 * @param seqNum
	 *            帐册序号
	 * @param companyName
	 *            公司名称
	 * @return
	 * @AuthorityFunctionAnnotation(caption = "关封余量分析", index = 13)
	 */

	public List findTransferImpExpCustomsList(Request request, Date beginDate,
			Date endDate, Integer billType, String billCode,
			String materielCode, String materielName, String seqNum,
			String companyName) {
		return this.transferFactoryManageLogic.findTransferImpExpCustomsList(
				beginDate, endDate, billType, billCode, materielCode,
				materielName, seqNum, companyName);

	}

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

	public List findStatisticTotalTransferStatusQuantity(Request request,
			Date beginDate, Date endDate, Integer impExpFlag, String billCode,
			String materielCode, String materielName, String materielSpec,
			String companyName, String scmcode) {
		return this.transferFactoryManageLogic
				.findStatisticTotalTransferStatusQuantity(beginDate, endDate,
						impExpFlag, billCode, materielCode, materielName,
						materielSpec, companyName, scmcode);
	}

	/**
	 * 获得转厂单据商品信息来自父对象List 用于转换报关清单过程中
	 */
	public List findTempTransferFactoryCommodityInfoByParentToACT(
			Request request, List parentList) {
		return this.transferFactoryManageLogic
				.findTempTransferFactoryCommodityInfoByParentToACT(parentList);
	}

	/**
	 * 获得没有结案,没有过期,生效的关封单据
	 */
	public List findCustomsEnvelopBillByAvailability(Request request,
			String scmCocId) {
		return this.transferFactoryManageDao
				.findCustomsEnvelopBillByAvailability(scmCocId);
	}

	/**
	 * 根据关封单据id获得其计算后的detail
	 */
	public List findCustomsEnvelopCommodityInfoByTempTransferFactoryCommodityInfo(
			Request request, String parentId, boolean isImportGoods,
			List tempTransferFactoryCommodityInfoList) {
		return this.transferFactoryManageLogic
				.findCustomsEnvelopCommodityInfoByTempTransferFactoryCommodityInfo(
						parentId, isImportGoods,
						tempTransferFactoryCommodityInfoList);
	}

	/**
	 * 生成结转单据
	 */
	public void makeTransferFactoryBill(Request request, List billMasterList,
			String envelopBillCode, String emsNo) {
		this.transferFactoryManageLogic.makeTransferFactoryBill(billMasterList,
				envelopBillCode, emsNo);
	}

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
	public List findCustomsEnvelopSpareAnalyes(Request request, Date beginDate,
			Date endDate, String commodityCode, String commodityName,
			String seqNum, String oppoisteEnterpriseName,
			boolean isImportGoods, String customsEnvelopBillNo, boolean isshow) {
		return this.transferFactoryManageLogic.findCustomsEnvelopSpareAnalyes(
				beginDate, endDate, commodityCode, commodityName, seqNum,
				oppoisteEnterpriseName, isImportGoods, customsEnvelopBillNo,
				isshow);
	}

	/**
	 * 有数据转报关清单在关封中
	 * 
	 * @param c
	 * @return
	 */
	public boolean hasDataTransFactoryBillByEnvelopId(Request request,
			CustomsEnvelopBill c) {
		return this.transferFactoryManageDao
				.hasDataTransFactoryBillByEnvelopId(c);
	}

	/**
	 * 获得当前关封单据的商品信息的个数
	 */
	public int findCustomsEnvelopCommodityInfoCount(Request request,
			String parentId) {
		return this.transferFactoryManageDao
				.findCustomsEnvelopCommodityInfoCount(parentId);
	}

	/**
	 * 获得当前关封申请单的商品信息的个数
	 */
	public int findCustomsEnvelopRequestCommodityInfoCount(Request request,
			String parentId) {
		return this.transferFactoryManageDao
				.findCustomsEnvelopRequestCommodityInfoCount(parentId);
	}

	/**
	 * 获得结转单据信息加载子表的记录
	 */
	public List findTransferFactoryCommodityInfo(Request request,
			String parentId) {
		return this.transferFactoryManageDao
				.findTransferFactoryCommodityInfo(parentId);
	}

	/**
	 * 有数据已转关封在关封申请单中
	 * 
	 * @param c
	 * @return
	 */
	public boolean hasDataTransferCustomsEnvelopByCustomsEnvelopRequest(
			Request request, CustomsEnvelopRequestBill c) {
		return this.transferFactoryManageDao
				.hasDataTransferCustomsEnvelopByCustomsEnvelopRequest(c);
	}

	/**
	 * 有数据已转关封申请单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	public boolean hasDataCustomsDeclarationByTransFactBill(Request request,
			TransferFactoryBill t) {
		return this.transferFactoryManageDao
				.hasDataCustomsDeclarationByTransFactBill(t);
	}

	/**
	 * 有数据已报关清单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	public boolean hasDataTransferApplyToCustomsBillByTransferFactoryBill(
			Request request, TransferFactoryBill t) {
		return this.transferFactoryManageDao
				.hasDataTransferApplyToCustomsBillByTransferFactoryBill(t);
	}

	/**
	 * 查找结转单据生成报关清单时的中间表信息来自关封相关的数据项
	 * 
	 * @param c
	 * @return
	 */
	public List findMakeApplyToCustomsInfoByCustomsEnvelopBill(Request request,
			CustomsEnvelopBill c) {
		return this.transferFactoryManageDao
				.findMakeApplyToCustomsInfoByCustomsEnvelopBill(c);
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public List findCustomsEnvelopCommodityInfo(Request request) {
		return this.transferFactoryManageDao.findCustomsEnvelopCommodityInfo();
	}

	/**
	 * 转厂分析查询权限控制
	 */
	@AuthorityFunctionAnnotation(caption = "转厂统计分析-浏览", index = 4)
	public void checkTransferStatisticAnalyse(Request request) {

	}

	/**
	 * 关封余量分析权限控制
	 */
	@AuthorityFunctionAnnotation(caption = "关封余量分析-浏览", index = 3)
	public void checkCustomsEnvelopSpareAnalyse(Request request) {

	}

	public List findExportCustomsDeclaration(Request request,
			CustomsEnvelopBill customsEnvelopBill) {
		return this.transferFactoryManageLogic
				.findExportCustomsDeclaration(customsEnvelopBill);
	}

	public List findImportCustomsDeclaration(Request request,
			CustomsEnvelopBill customsEnvelopBill) {
		return this.transferFactoryManageLogic
				.findImportCustomsDeclaration(customsEnvelopBill);
	}

	/**
	 * 获得转厂初始化单据信息加载子表的记录for 报关
	 */
	public List findTransferFactoryInitCommodityInfoForCustoms(Request request,
			String parentId) {
		return this.transferFactoryManageDao
				.findTransferFactoryInitCommodityInfoForCustoms(parentId);
	}

	/**
	 * @return Returns the transferQueryLogic.
	 */
	public TransferQueryLogic getTransferQueryLogic() {
		return transferQueryLogic;
	}

	/**
	 * @param transferQueryLogic
	 *            The transferQueryLogic to set.
	 */
	public void setTransferQueryLogic(TransferQueryLogic transferQueryLogic) {
		this.transferQueryLogic = transferQueryLogic;
	}

	// 备案周报表
	@AuthorityFunctionAnnotation(caption = "转厂其他报表-浏览", index = 5)
	public List findPutRecord(Request request) {
		return this.transferQueryLogic.findPutRecord();
	}

	// 转厂安排周报表
	@AuthorityFunctionAnnotation(caption = "转厂其他报表-浏览", index = 5)
	public List findTransferPlan(Request request) {
		return this.transferQueryLogic.findTransferPlan();
	}

	/**
	 * 查询转厂中关封的客户和厂商
	 * 
	 * @return
	 */
	public List findCustomsEnvelopScmCoc(Request request, boolean isImport) {
		return this.transferFactoryManageDao.findCustomsEnvelopScmCoc(isImport);
	}

	/**
	 * 查询转厂中关封的商品信息
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "转厂其他报表-浏览", index = 5)
	public List findCustomsEnvelopComplex(Request request, boolean isImport,
			boolean isSeqNum) {
		return this.transferQueryLogic.findCustomsEnvelopComplex(isImport,
				isSeqNum);
	}

	// 关封明细报表
	public List findCustomsEnvelopList(Request request, String envelopCode,
			Integer seqNum, String complexCode, String scmCoc,
			boolean isImport, int state, Date beginDate, Date endDate) {
		return this.transferQueryLogic.findCustomsEnvelopList(envelopCode,
				seqNum, complexCode, scmCoc, isImport, state, beginDate,
				endDate);
	}

	/**
	 * 根据单据号查询关封商品明细
	 * 
	 * @param customsEnvelopBillCode
	 * @return
	 */
	public List findTempTransferFactoryCommInfo(Request request,
			boolean isImport, ScmCoc scmCoc) {
		return transferFactoryManageLogic.findTempTransferFactoryCommInfo(
				isImport, scmCoc);
	}

	/**
	 * 查询不在转厂起初单的商品编码
	 * 
	 * @param initBillId
	 * @return
	 */
	public List findComplexNotInInitBill(Request request, String initBillId,
			String customsEnvelopBillCode) {
		return this.transferFactoryManageLogic.findComplexNotInInitBill(
				initBillId, customsEnvelopBillCode);
	}

	/**
	 * 结转单据自动转报关单及其商品信息
	 * 
	 * @param lsBills
	 * @param param
	 * @return
	 */
	public List makeCusomsDeclarationFromTransferBill(Request request,
			List lsBills, MakeCusomsDeclarationParam param, boolean isMergeOne) {
		return this.transferFactoryManageLogic
				.makeCusomsDeclarationFromTransferBill(lsBills, param,
						isMergeOne);
	}

	/**
	 * 查询未转报关单的结转单据
	 * 
	 * @param isImport
	 * @return
	 */
	public List findTransFactBillMakeCustomsDeclaration(Request request,
			boolean isImport, ScmCoc scmCoc) {
		return this.transferFactoryManageDao
				.findTransFactBillMakeCustomsDeclaration(isImport, scmCoc);
	}

	/**
	 * 根据手册号码抓取正在执行的电子手册
	 * 
	 * @param emsNo
	 * @return
	 */
	public DzscEmsPorHead findDzscEmsPorHeadExcu(Request request, String emsNo) {
		return this.transferFactoryManageLogic.findDzscEmsPorHeadExcu(emsNo);
	}

	/**
	 * 根据账册册号码抓取正在执行的电子账册
	 * 
	 * @param emsNo
	 * @return
	 */
	public EmsHeadH2k findEmsHeadH2kInExecuting(Request request, String emsNo) {
		return this.transferFactoryManageLogic.findEmsHeadH2kInExecuting(emsNo);
	}

	/**
	 * 根据客户供应商查找转厂商品
	 * 
	 * @param scmcoc
	 *            客户供应商
	 * @return 转厂商品
	 */
	public List findCustomsEnvelopCommodityInfoByScmCoc(Request request,
			ScmCoc scmcoc, Boolean isCustomer) {
		return transferFactoryManageLogic
				.findCustomsEnvelopCommodityInfoByScmCoc(request, scmcoc,
						isCustomer);
	}

	/**
	 * 保存商品单价维护实体
	 * 
	 * @param list
	 *            商品单价维护实体
	 * @return 商品单价维护实体
	 */
	public void saveBillPriceMaintenance(Request request, List list) {
		this.transferFactoryManageDao.batchSaveOrUpdate(list);
	}

	/**
	 * 根据客户供应商查找商品单价维护实体
	 * 
	 * @param scmCoc
	 *            客户供应商
	 * @return 商品单价维护实体
	 */
	@AuthorityFunctionAnnotation(caption = "参数设置", index = 0)
	public void getParaPurview(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "商品单价维护-浏览", index = 0.51)
	public List findBillPriceMaintenanceByScmCoc(Request request,
			ScmCoc scmCoc, Boolean isCustomer) {
		return this.transferFactoryManageDao.findBillPriceMaintenanceByScmCoc(
				scmCoc, isCustomer);
	}

	/**
	 * 删除商品单价维护实体
	 * 
	 * @param list
	 *            商品单价维护实体
	 */
	@AuthorityFunctionAnnotation(caption = "商品单价维护-删除", index = 0.53)
	public void delBillPriceMaintenance(Request request, List list) {
		this.transferFactoryManageLogic.delBillPriceMaintenance(list);
	}

	// @AuthorityFunctionAnnotation(caption = "商品单价维护-修改", index = 0.52)
	public void editBillPriceMaintenancePrice(Request request, ScmCoc scmCoc,
			Integer seqNum, String code, Double price, int projectType,
			Boolean isCustomer) {
		this.transferFactoryManageLogic.editBillPriceMaintenancePrice(scmCoc,
				seqNum, code, price, projectType, isCustomer);

	}

	@AuthorityFunctionAnnotation(caption = "商品单价维护-修改", index = 0.52)
	public void checkEditBillPriceMaintenancePrice(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "商品单价维护-新增", index = 0.54)
	public List addBillPriceMaintence(Request request, List list,
			ScmCoc scmCoc, Boolean isCustomer) {
		return this.transferFactoryManageLogic.addBillPriceMaintence(list,
				scmCoc, isCustomer);
	}

	public List findTransferClienCollate(Request request, Integer projectType,
			String emsNo, String scmcocCode, Date beginDate, Date endDate,
			Integer impExpType) {
		return this.transferQueryLogic.findTransferClienCollate(projectType,
				emsNo, scmcocCode, beginDate, endDate, null, null, impExpType);
	}

	public List traCustomsCollate(Request request, String year,
			boolean isImpType) {
		return this.transferQueryLogic.traCustomsCollate(year, isImpType);
	}

	/**
	 * 根据关封号抓取关封里所有的手册号
	 * 
	 * @param envelopNo
	 * @return
	 */
	public List findCustomsEnvelopBillEmsNo(Request request, String envelopNo) {
		return this.transferFactoryManageDao
				.findCustomsEnvelopBillEmsNo(envelopNo);
	}

	public List findImpExpCommodityInfo(Request request, String parentId) {
		// TODO Auto-generated method stub
		return this.transferFactoryManageDao.findImpExpCommodityInfo(parentId);
	}

	/**
	 * 判断单据号是否已经存在
	 * 
	 * @param request
	 * @param no
	 * @return
	 */
	public boolean isExistsNo(Request request, String no) {
		return this.transferFactoryManageDao.isExistsNo(no);
	}

	public List findTransferClienCollate(Request request, Integer projectType,
			String emsNo, String scmcocCode, Date beginDate, Date endDate,
			String spec, String name, Integer impExpType) {
		// TODO Auto-generated method stub
		return this.transferQueryLogic.findTransferClienCollate(projectType,
				emsNo, scmcocCode, beginDate, endDate, spec, name, impExpType);
	}

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
	public CustomsEnvelopBill copyCustomsEnvelopBillAndCommodityInfo(
			Request request, CustomsEnvelopBill customsEnvelopBill,
			Boolean copyInfo, String billNo) {
		return this.transferFactoryManageLogic
				.copyCustomsEnvelopBillAndCommodityInfo(customsEnvelopBill,
						copyInfo, billNo);
	}
}