/***

 * 浏览进出货转厂期初单 1
 保存进出货转厂期初单 2
 删除进出货转厂期初单 3

 
 浏览结转单据 4
 保存结转单据 5
 删除结转单据 6

 浏览转厂申请单 7
 保存转厂申请单 8
 删除转厂申请单 9


 浏览关封 10
 保存关封 11
 删除关封 12
 关封余量分析 13
 转厂统计分析 14

 */

package com.bestway.common.fpt.action;

import java.io.File;
import java.util.ArrayList;
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
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.dao.FptManageDao;
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
import com.bestway.common.fpt.logic.FptManageLogic;
import com.bestway.common.fpt.logic.FptQpLogic;
import com.bestway.common.fpt.logic.FptQueryLogic;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

@AuthorityClassAnnotation(caption = "深加工结转", index = 11)
public class FptManageActionImpl extends BaseActionImpl implements FptManageAction {
	private FptManageDao fptManageDao = null;

	private FptManageLogic fptManageLogic = null;

	private FptQueryLogic fptQueryLogic = null;

	private FptQpLogic fptQpLogic = null;

	public FptManageDao getFptManageDao() {
		return fptManageDao;
	}

	/**
	 * @return the fptQpLogic
	 */
	public FptQpLogic getFptQpLogic() {
		return fptQpLogic;
	}

	/**
	 * @param fptQpLogic
	 *            the fptQpLogic to set
	 */
	public void setFptQpLogic(FptQpLogic fptQpLogic) {
		this.fptQpLogic = fptQpLogic;
	}

	public void setFptManageDao(FptManageDao transferFactoryManageDao) {
		this.fptManageDao = transferFactoryManageDao;
	}

	public FptManageLogic getFptManageLogic() {
		return fptManageLogic;
	}

	public void setFptManageLogic(FptManageLogic transferFactoryManageLogic) {
		this.fptManageLogic = transferFactoryManageLogic;
	}

	public FptManageActionImpl() {
		// this.setModuleName("Authority");
	}

	/**
	 * '权限检查'空方法
	 */
	@AuthorityFunctionAnnotation(caption = "结转申请表（关封）-浏览", index = 0)
	public void permissionCheckAppHeadGlance(Request request) {
	}

	@AuthorityFunctionAnnotation(caption = "结转申请表（关封）-新增", index = 0.1)
	public void permissionCheckAppHeadAdd(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "结转申请表（关封）-修改", index = 0.2)
	public void permissionCheckAppHeadEdit(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "结转申请表（关封）-删除", index = 0.3)
	public void permissionCheckAppHeadDel(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "结转申请表（关封）-变更", index = 0.4)
	public void permissionCheckAppHeadChange(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "结转申请表（关封）-结案", index = 0.5)
	public void permissionCheckAppHeadEndCase(Request request) {

	}
	
	@AuthorityFunctionAnnotation(caption = "结转申请表（关封）-作废", index = 0.6)
	public void permissionCheckAppHeadIsCAN(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "结转申请表（关封）-撤销结案", index = 0.7)
	public void permissionCheckAppHeadCancelEndCase(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "成品发货单-浏览", index = 0.8)
	public void permissionCheckOutBillGlance(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "成品发货单-新增", index = 0.9)
	public void permissionCheckOutBillAdd(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "成品发货单-修改", index = 1.0)
	public void permissionCheckOutBillEdit(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "成品发货单-删除", index = 1.1)
	public void permissionCheckOutBillDel(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "成品退货单----浏览", index = 1.2)
	public void permissionCheckInBackBillGlance(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "成品退货单----新增", index = 1.3)
	public void permissionCheckInBackBillAdd(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "成品退货单----修改", index = 1.4)
	public void permissionCheckInBackBillEdit(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "成品退货单----删除", index = 1.5)
	public void permissionCheckInBackBillDel(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "料件收货单-浏览", index = 1.6)
	public void permissionCheckInBillGlance(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "料件收货单-新增", index = 1.7)
	public void permissionCheckInBillAdd(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "料件收货单-修改", index = 1.8)
	public void permissionCheckInBillEdit(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "料件收货单-删除", index = 1.9)
	public void permissionCheckInBillDel(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "料件退货单----浏览", index = 2.0)
	public void permissionCheckOutBackBillGlance(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "料件退货单----新增", index = 2.1)
	public void permissionCheckOutBackBillAdd(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "料件退货单----修改", index = 2.2)
	public void permissionCheckOutBackBillEdit(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "料件退货单----删除", index = 2.3)
	public void permissionCheckOutBackBillDel(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "深加工结转-其他功能", index = 2.4)
	public void permissionCheckOther(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "深加工结转-海关申报", index = 2.5)
	public void permissionCheckApply(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "深加工结转-补发报文", index = 2.6)
	public void permissionCheckResend(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "深加工结转-处理回执", index = 2.7)
	public void permissionCheckProcess(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "深加工结转-转抄", index = 2.8)
	public void permissionCheckCopy(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "单据撤销-浏览", index = 2.9)
	public void permissionCheckBillCancel(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "统计报表-浏览", index = 3.0)
	public void permissionCheckGetGuide(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "结转单据对应表-浏览", index = 3.1)
	public void permissionCheckCorresponding(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "报文查询-浏览", index = 3.2)
	public void permissionCheckMessageQuery(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "深加工结转平台下载-浏览", index = 3.3)
	public void permissionCheckBtplsDownData(Request request) {

	}

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	public FptParameterSet findTransParameterSet(Request request) {
		return this.fptManageDao.findFptParameterSet();
	}

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return
	 */
	public FptParameterSet saveTransParameterSet(Request request, FptParameterSet parameterSet) {
		this.fptManageDao.saveOrUpdate(parameterSet);
		// 检查创建文件目录
		if (CommonUtils.notEmpty(parameterSet.getSendDir())) {
			File file = new File(parameterSet.getSendDir());
			if (!file.exists()) {
				if (!file.mkdirs()) {
					throw new RuntimeException("创建回执目录" + parameterSet.getSendDir() + "失败！，请手动创建。");
				}
			}
		}
		if (CommonUtils.notEmpty(parameterSet.getRecvDir())) {
			File file = new File(parameterSet.getRecvDir());
			if (!file.exists()) {
				if (!file.mkdirs()) {
					throw new RuntimeException("创建回执目录" + parameterSet.getRecvDir() + "失败！，请手动创建。");
				}
			}
		}
		if (CommonUtils.notEmpty(parameterSet.getFinallyDir())) {
			File file = new File(parameterSet.getFinallyDir());
			if (!file.exists()) {
				if (!file.mkdirs()) {
					throw new RuntimeException("创建回执目录" + parameterSet.getFinallyDir()
							+ "失败！，请手动创建。");
				}
			}
		}
		if (CommonUtils.notEmpty(parameterSet.getLogDir())) {
			File file = new File(parameterSet.getLogDir());
			if (!file.exists()) {
				if (!file.mkdirs()) {
					throw new RuntimeException("创建回执目录" + parameterSet.getLogDir() + "失败！，请手动创建。");
				}
			}
		}
		return parameterSet;
	}

	/**
	 * 获得转厂申请单所有数据
	 */

	public List findFptAppHead(Request request) {
		return fptManageDao.findFptAppHead();
	}

//	/**
//	 * 获得转厂申请单所有数据来自进出货标志
//	 */
//	public List findFptAppHeadByInOutFlag(Request request, String fptInOutFlag) {
//		return fptManageDao.findFptAppHeadByInOutFlag(fptInOutFlag);
//	}

	/**
	 * 获得关封申请单所有数据来自进出货标志
	 */
	public List findFptAppHeadByInOutFlag(Request request, String fptInOutFlag, String fptDeclareState ,String emsDeclareState) {
		return fptManageDao.findFptAppHeadByInOutFlag(fptInOutFlag, fptDeclareState, emsDeclareState);
	}

	/**
	 * 获得转厂申请单数据来自客户或供应商Id
	 */
	public List findCustomsEnvelopRequestBillByScmCocId(Request request, String scmCocId) {
		return fptManageDao.findCustomsEnvelopRequestBillByScmCocId(scmCocId);
	}

	/**
	 * 保存转厂申请单
	 */
	public FptAppHead saveFptAppHead(Request request, FptAppHead fptAppHead) {
		fptManageDao.saveFptAppHead(fptAppHead);
		return fptAppHead;

	}
	
	public void saveFptAppHeads(Request request, List fptAppHeads){
		fptManageDao.batchSaveOrUpdate(fptAppHeads);
	}

	/**
	 * 删除转厂申请单
	 */
	public void deleteFptAppHead(Request request, FptAppHead fptAppHead) {
		fptManageLogic.deleteFptAppHead(fptAppHead);
	}

	/**
	 * 删除转厂申请单商品信息数据
	 */
	public void deleteFptAppItem(Request request, List list) {
		fptManageLogic.deleteFptAppItem(list);
	}

	/**
	 * 保存转厂申请单商品信息数据
	 */
	public List saveFptAppItem(Request request, List list) {
		fptManageDao.saveFptAppItem(list);
		return list;
	}

	/**
	 * 保存转厂申请单商品信息数据
	 */
	// @AuthorityFunctionAnnotation(caption = "结转申请表（关封）-保存", index = 0.4)
	public FptAppItem saveFptAppItem(Request request, FptAppItem data) {
		fptManageDao.saveFptAppItem(data);
		return data;
	}

	/**
	 * 获得转厂申请单信息加载子表的记录
	 */
	public List findFptAppItems(Request request, String parentId) {
		return fptManageDao.findFptAppItems(parentId);
	}

	/**
	 * 查找结转单据商品的可待转数量还有多少
	 */
	public double findTransferFactoryCommodityInfoLeft(Request request, FptBillItem commInfo) {
		return fptManageLogic.findTransferFactoryCommodityInfoLeft(commInfo);
	}

	/** 获得最大的数值 */
	public Integer findMaxValueByFptAppItem(Request request, String parentId, String inOutFlag) {
		return this.fptManageDao.findMaxValueByFptAppItem(parentId, inOutFlag);
	}

	/** 获得 FptAppItem 来自序号 */
	public FptAppItem findFptAppItemByListNo(Request request, String parentId, Integer listNo,
			String inOutFlag) {
		return this.fptManageDao.findFptAppItemByListNo(parentId, listNo, inOutFlag);

	}

	/**
	 * 获得最大的单据号来自进出货标志
	 */

	public long getMaxBillNoByImpExpGoodsFlag(Request request, boolean impExpGoodsFlag) {
		return fptManageLogic.getMaxBillNoByImpExpGoodsFlag(impExpGoodsFlag);
	}

	/**
	 * 获得转厂申请单商品信息记录来自数据是否正确的检验
	 */
	public List findCustomsEnvelopRequestCommodityInfoByCheck(Request request, String parentId) {
		return fptManageLogic.findCustomsEnvelopRequestCommodityInfoByCheck(parentId);
	}

	/**
	 * ---------------------------------------------------- 关封单据用到的方法
	 * -------------------------------------------------------
	 */
	/**
	 * 根据系统类型抓取正在执行的账册号或手册号
	 */
	public List findEmsHeadByProjectType(Request request, Integer projectType) {
		return this.fptManageLogic.findEmsHeadByProjectType(projectType);
	}

	// /**
	// * 根据系统类型，账册号或手册号，物料和成品分类抓取转厂的物料信息
	// *
	// * @param projectType
	// * @return
	// */
	// public List findTempCustomsEnvelopRequestCommInfo(Request request,
	// Integer projectType, String emsNo,
	// CustomsEnvelopBill customsEnvelopBill, boolean isMaterial) {
	// return this.transferFactoryManageLogic
	// .findTempCustomsEnvelopRequestCommInfo(projectType, emsNo,
	// customsEnvelopBill, isMaterial);
	// }

	// /**
	// * 把查询出来的临时商品信息,保存为关封的商品信息
	// *
	// * @param list
	// * @param envelopBill
	// * @return
	// */
	// public List saveCustomsEnvelopRequestCommInfo(Request request,
	// List<TempCustomsEnvelopCommInfo> list,
	// CustomsEnvelopBill envelopBill) {
	// return this.transferFactoryManageLogic
	// .saveCustomsEnvelopRequestCommInfo(list, envelopBill);
	// }

	// /**
	// * 获得关封单据所有数据
	// */
	// @AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0)
	// public List findCustomsEnvelopBill(Request request) {
	// return transferFactoryManageDao.findCustomsEnvelopBill();
	// }

	// /**
	// * 获得关封单据所有数据
	// */
	// @AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0)
	// public List findCustomsEnvelopBill(Request request,
	// boolean impExpGoodsFlag, boolean isAvailability) {
	// return transferFactoryManageDao.findCustomsEnvelopBill(impExpGoodsFlag,
	// isAvailability);
	// }
	//
	// /**
	// * 获得关封单据所有数据
	// */
	// @AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0)
	// public List findCustomsEnvelopBill(Request request, boolean isImport,
	// boolean isAvailability, ScmCoc scmCoc) {
	// return this.transferFactoryManageDao.findCustomsEnvelopBill(isImport,
	// isAvailability, scmCoc);
	// }

	/**
	 * 获得关封单据所有数据
	 */
	public List findFptAppItemByCustomsDeclaration(Request request, int impExpType, String emsNo,
			ScmCoc scmCoc) {
		return this.fptManageDao.findFptAppItemByCustomsDeclaration(impExpType, emsNo, scmCoc);
	}

	// /**
	// * 获得关封单据所有数据来自进出货标志
	// */
	// @AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0)
	// public List findCustomsEnvelopBill(Request request,
	// boolean impExpGoodsFlag, String billNo, ScmCoc sc, Date beginDate,
	// Date endDate, Boolean isEndCase) {
	// return transferFactoryManageDao.findCustomsEnvelopBill(impExpGoodsFlag,
	// billNo, sc, beginDate, endDate, isEndCase);
	// }

	// /**
	// * 获得关封单据数据来自客户或供应商Id
	// */
	// @AuthorityFunctionAnnotation(caption = "关封管理-浏览", index = 0)
	// public List findCustomsEnvelopBillByScmCocId(Request request,
	// String scmCocId) {
	// return transferFactoryManageDao
	// .findCustomsEnvelopBillByScmCocId(scmCocId);
	// }

	// /**
	// * 保存关封单据
	// */
	// @AuthorityFunctionAnnotation(caption = "关封管理-保存", index = 0.1)
	// public CustomsEnvelopBill saveCustomsEnvelopBill(Request request,
	// CustomsEnvelopBill customsEnvelopBill) {
	// transferFactoryManageLogic.saveCustomsEnvelopBill(customsEnvelopBill);
	// return customsEnvelopBill;
	// }

	// /**
	// * 删除关封单据
	// */
	// @AuthorityFunctionAnnotation(caption = "关封管理-删除", index = 0.21)
	// public void deleteCustomsEnvelopBill(Request request,
	// CustomsEnvelopBill customsEnvelopBill) {
	// transferFactoryManageLogic.deleteCustomsEnvelopBill(customsEnvelopBill);
	// }

	// /**
	// * 删除关封单据商品信息数据
	// */
	// @AuthorityFunctionAnnotation(caption = "关封管理-删除", index = 0.21)
	// public List deleteCustomsEnvelopCommodityInfo(Request request, List list)
	// {
	// this.transferFactoryManageLogic.deleteCustomsEnvelopCommodityInfo(list);
	// return list;
	// }

	// /**
	// * 保存关封单据商品信息数据
	// */
	// @AuthorityFunctionAnnotation(caption = "关封管理-保存", index = 0.1)
	// public List saveCustomsEnvelopCommodityInfo(Request request, List list) {
	// transferFactoryManageDao.saveCustomsEnvelopCommodityInfo(list);
	// return list;
	// }

	// /**
	// * 获得关封单据信息加载子表的记录
	// */
	// public List findCustomsEnvelopCommodityInfo(Request request, String
	// parentId) {
	// // return this.transferFactoryManageDao
	// // .findCustomsEnvelopCommodityInfo(parentId);
	// return this.transferFactoryManageLogic
	// .findCustomsEnvelopCommodityInfo(parentId);
	// }

	// /**
	// * 获得关封单据商品信息记录来自数据是否正确的检验
	// */
	// public List findCustomsEnvelopCommodityInfoByCheck(Request request,
	// String parentId) {
	// return this.transferFactoryManageLogic
	// .findCustomsEnvelopCommodityInfoByCheck(parentId);
	// }

	/**
	 * 来自电子帐册料件--并通过封商品信息进行筛选
	 */
	public List findEmsMateriel(Request request, EmsHeadH2k emsH2k, String parentId) {
		return this.fptManageDao.findEmsMateriel(emsH2k, parentId);
	}

	/**
	 * 来自电子帐册成品--并通过封商品信息进行筛选
	 */
	public List findEmsFinishedProduct(Request request, EmsHeadH2k emsH2k, String parentId) {
		return this.fptManageDao.findEmsFinishedProduct(emsH2k, parentId);
	}

	/**
	 * * ---------------------------------------------------- 结转单据用到的方法
	 * -------------------------------------------------------
	 */
	/**
	 * 新增结转单据表头
	 */
	public FptBillHead newFptBillHead(Request request, String fptInOutFlag, String fptBusinessType) {
		return this.fptManageLogic.newFptBillHead(fptInOutFlag, fptBusinessType);
	}

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是FptBillHead型，单据表头
	 */
	public FptBillHead findFptBillHeadById(Request request, String id) {
		return this.fptManageDao.findFptBillHeadById(id);
	}

	/**
	 * 拆分结转单据，将结转单据没有报关的数量，生成一个新的结转单据
	 * 
	 * @param transferFactoryBill
	 * @return
	 */
	public FptBillHead splitTransferFactoryBill(Request request, FptBillHead transferFactoryBill) {
		return this.fptManageLogic.splitTransferFactoryBill(transferFactoryBill);
	}

	/**
	 * 转厂海关申报
	 * 
	 * @param head
	 *            通关备案表头
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public DeclareFileInfo applyFptBill(Request request, FptBillHead head) {
		return this.fptManageLogic.applyFptBill(head, request.getTaskId());
	}

	/**
	 * 变更结转单据
	 */
	public FptBillHead changingFptBill(Request request, FptBillHead fptBillHead) {
		return this.fptManageLogic.changingFptBill(fptBillHead);
	}

	/**
	 * 转抄明细
	 */
	public List<FptBillItem> copyFptBillExpImpDetail(Request request, List<FptBillItem> list,
			FptBillHead head) {
		return this.fptManageLogic.copyFptBillExpImpDetail(list, head);
	}

	/**
	 * 转抄数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Contract型，合同表头
	 */
	public List<FptBillHead> copyFptBillHead(Request request, List<FptBillHead> list,
			Integer inorOut, Integer billType) {
		return this.fptManageLogic.copyFptBillHead(list, inorOut, billType);
	}

	/**
	 * 获得结转所有单据来自进出货标志
	 */
	public List findInOutFptBillHeadByType(Request request, String fptInOutFlag,
			String fptBusinessType, Date beginDate, Date endDate, String tradeCode, String state,
			String appNo, ScmCoc scmcoc) {
		return fptManageDao.findInOutFptBillHeadByType(fptInOutFlag, fptBusinessType, beginDate,
				endDate, tradeCode, state, appNo, scmcoc);
	}

	/**
	 * 查询结转单据流水号
	 * @return
	 */
	public List findFptBillHeadSerialNumber(Request request,String fptInOutFlag){
		return this.fptManageLogic.findFptBillHeadSerialNumber(fptInOutFlag);
	}
	/**
	 * 获得结转所有单据来自进出货
	 */
	public List findFptBillHead(Request request, String sysType, String inOutFlag, Date beginDate,
			Date endDate, String declareState, String appNo, ScmCoc scmcoc) {
		return this.fptManageDao.findFptBillHead(sysType, inOutFlag, beginDate, endDate,
				declareState, appNo, scmcoc);
	}

	/**
	 * 保存结转单据
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂单-保存", index = 1.4)
	public FptBillHead saveFptBillHead(Request request, FptBillHead transferFactoryBill) {
		fptManageLogic.saveFptBillHead(transferFactoryBill);
		return transferFactoryBill;
	}

	/**
	 * 删除结转单据
	 */
	public void deleteFptBillHead(Request request, FptBillHead FptBillHead) {
		fptManageLogic.deleteFptBillHead(FptBillHead);
	}

	/**
	 * 删除结转单据商品信息数据
	 */
	public Map<Integer, List<FptBillItem>> deleteFptBillItem(Request request, List list) {
		return this.fptManageLogic.deleteFptBillItem(list);
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	public FptBillItem saveFptBillItem(Request request, FptBillItem commInfo) {
		this.fptManageLogic.saveFptBillItem(commInfo);
		return commInfo;
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	public List saveFptBillItem(Request request, List list) {
		this.fptManageDao.saveFptBillItem(list);
		return list;
	}

	// /**
	// * 获得当前转厂进出口的商品信息的个数
	// */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂单据-浏览", index = 2)
	// public int findTransferFactoryCommodityInfoCount(Request request,
	// String parentId) {
	// return transferFactoryManageDao
	// .findTransferFactoryCommodityInfoCount(parentId);
	// }

	/**
	 * 获得结转单据记录来自数据是否正确的检验
	 */
	public List findTransferFactoryCommodityInfoByCheck(Request request, String parentId) {
		return this.fptManageLogic.findTransferFactoryCommodityInfoByCheck(parentId);
	}

	/**
	 * 判断内部编号号是否重复
	 * 
	 * @param head
	 * 
	 * @return boolean 手册号是否重复，true为重复
	 */
	public boolean checkFptBillEmsBillNoDuple(Request request, String fptInOutFlag,
			String fptBusinessType, FptBillHead head) {
		return this.fptManageLogic.checkFptBillEmsBillNoDuple(fptInOutFlag, fptBusinessType, head);
	}

	/**
	 * 判断申请单编号是否存在
	 */
	public boolean checkAppNoInFptAppHead(Request request, String fptInOutFlag, String appNo) {
		return this.fptManageLogic.checkAppNoInFptAppHead(fptInOutFlag, appNo);
	}

	//
	// /**
	// * 获得最大的结转单据号来自进出货标志
	// */
	// public long getMaxTransferFactoryBillNoByImpExpGoodsFlag(Request request,
	// boolean impExpGoodsFlag) {
	// return this.transferFactoryManageLogic
	// .getMaxTransferFactoryBillNoByImpExpGoodsFlag(impExpGoodsFlag);
	// }

	/**
	 * 获得转厂申请单据来自选定用客户，且生效、未转关封的单据
	 */
	public List findTempCustomsEnvelopRequestBillByScmCoc(Request request, String scmCocId) {
		return this.fptManageLogic.findTempCustomsEnvelopRequestBillByScmCoc(scmCocId);
	}

	/**
	 * 获得转厂申请单据商品信息来自父对象List
	 */
	public List findTempCustomsEnvelopRequestCommodityInfoByParent(Request request, List parentList) {
		return this.fptManageLogic.findTempCustomsEnvelopRequestCommodityInfoByParent(parentList);
	}

	/**
	 * 转厂申请单转关封时商品信息的检查--返回没有在电子帐册中备案的数据(成品)
	 */
	public List checkTempCustomsEnvelopRequestCommodityInfoByFinishProduct(Request request,
			List list, EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead) {
		return this.fptManageLogic.checkTempCustomsEnvelopRequestCommodityInfoByFinishProduct(list,
				emsH2k, emsEdiMergerHead);
	}

	/**
	 * 转厂申请单转关封时商品信息的检查--返回没有在电子帐册中备案的数据(料件)
	 */
	public List checkTempCustomsEnvelopRequestCommodityInfoByMateriel(Request request, List list,
			EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead) {
		return this.fptManageLogic.checkTempCustomsEnvelopRequestCommodityInfoByMateriel(list,
				emsH2k, emsEdiMergerHead);
	}

	// /**
	// * 转厂申请单-->返回已生成关封单据的转厂申请单列表
	// */
	// @AuthorityFunctionAnnotation(caption = "转厂申请单-保存", index = 0.454)
	// public List makeCustomsEnvelopBill(Request request,
	// CustomsEnvelopBill customsEnvelopBill, List dataSource) {
	// return this.transferFactoryManageLogic.makeCustomsEnvelopBill(
	// customsEnvelopBill, dataSource);
	// }

	/**
	 * 修改转厂申请单的关封单据字段,和生成关封单据字段为true
	 */
	public List updateCustomsEnvelopRequestBill(Request requst, String customsEnvelopBillId,
			List dataSource) {
		return this.fptManageLogic
				.updateCustomsEnvelopRequestBill(customsEnvelopBillId, dataSource);
	}

	/**
	 * 保存转厂初始化单据
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂期初单-保存", index = 1.2)
	public FptInitBill saveTransferFactoryInitBill(Request requst,
			FptInitBill transferFactoryInitBill) {
		this.fptManageLogic.saveTransferFactoryInitBill(transferFactoryInitBill);
		return transferFactoryInitBill;
	}

	/**
	 * 保存转厂初始化单据商品信息
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂期初单-保存", index = 1.2)
	public FptInitBill saveTransferFactoryInitBillCommodityInfo(Request requst,
			FptInitBill initBill, List list) {
		return this.fptManageLogic.saveTransferFactoryInitBillCommodityInfo(initBill, list);
	}

	/**
	 * 保存转厂初始化单据商品信息
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂期初单-保存", index = 1.2)
	public FptInitBillItem saveTransferFactoryInitBillCommodityInfo(Request requst,
			FptInitBillItem transferFactoryInitBillCommodityInfo) {
		this.fptManageLogic
				.saveTransferFactoryInitBillCommodityInfo(transferFactoryInitBillCommodityInfo);
		return transferFactoryInitBillCommodityInfo;
	}

	/**
	 * 删除转厂初始化单据
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂期初单-删除", index = 1.3)
	public void deleteTransferFactoryInitBill(Request requst, FptInitBill transferFactoryInitBill) {
		this.fptManageLogic.deleteTransferFactoryInitBill(transferFactoryInitBill);
	}

	/**
	 * 删除转厂初始化单据商品信息
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂期初单-删除", index = 1.3)
	public FptInitBill deleteTransferFactoryInitBillCommodityInfo(Request requst,
			FptInitBill initBill, List list) {
		this.fptManageLogic.deleteTransferFactoryInitBillCommodityInfo(initBill, list);
		return initBill;
	}

	/**
	 * 获得结转所有单据
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂期初单-浏览", index = 1)
	public List findTransferFactoryInitBill(Request requst) {
		return this.fptManageLogic.findTransferFactoryInitBill();
	}

	/**
	 * 获得结转所有单据
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂期初单-浏览", index = 1)
	public List findTransferFactoryInitBill(Request requst, boolean isImpExpFlag) {
		return this.fptManageLogic.findTransferFactoryInitBill(isImpExpFlag);
	}

	/**
	 * 进出货单据导出excel
	 */
	public TempResultSet exportFptBillItemToExcel(Request request, String parentId, String in) {
		return this.fptManageLogic.exportFptBillItemToExcel(parentId, in);
	}

	/**
	 * 取得最大转厂初始化单据号码+1
	 * 
	 * @return
	 */
	public String getTransferFactoryInitBillMaxCode(Request requst) {
		return fptManageLogic.getTransferFactoryInitBillMaxCode();
	}

	/**
	 * 获得转厂初始化单据信息加载子表的记录
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂期初单-浏览", index = 1)
	public List findTransferFactoryInitCommodityInfo(Request requst, String parentId) {
		return this.fptManageLogic.findTransferFactoryInitCommodityInfo(parentId);
	}

	//
	// /**
	// * 获得转厂单据来自选定用客户，且生效、未转报关清单的单据
	// */
	// public List findTempTransferFactoryBillByScmCocNotATC(Request request,
	// String scmCocId, String emsNo) {
	// return this.transferFactoryManageLogic
	// .findTempTransferFactoryBillByScmCocNotATC(scmCocId, emsNo);
	// }

	// /**
	// * 获得转厂单据商品信息来自父对象List
	// */
	// public List findTempTransferFactoryCommodityInfoByParent(Request request,
	// List parentList) {
	// return this.transferFactoryManageLogic
	// .findTempTransferFactoryCommodityInfoByParent(parentList);
	// }

	// /**
	// *
	// * 结转单据--->返回已生成转厂申请单据的结转单据列表
	// *
	// */
	// @AuthorityFunctionAnnotation(caption = "转厂申请单-保存", index = 0.454)
	// public List makeCustomsEnvelopRequestBill(Request request,
	// FptAppHead fptAppHead, List dataSource) {
	// return this.transferFactoryManageLogic.makeCustomsEnvelopRequestBill(
	// fptAppHead, dataSource);
	// }

	/**
	 * 修改结转单据的转厂申请单字段,和生成转厂申请单据字段为true
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public List updateTransferFactoryBillByCER(Request request, String customsEnvelopRequestBillId,
			List dataSource) {
		return this.fptManageLogic.updateTransferFactoryBillByCER(customsEnvelopRequestBillId,
				dataSource);
	}

	/**
	 * 修改结转单据的报关清单字段,和生成报关清单据字段为true -->来自报关清单Id
	 */
	// @AuthorityFunctionAnnotation(caption = "进出货转厂单据-保存", index = 2.4)
	public List updateTransFactBillByCustomsBillListId(Request request,
			String applyToCustomsBillId, List dataSource) {
		return this.fptManageLogic.updateTransFactBillByCustomsBillListId(applyToCustomsBillId,
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
	public List findTransferFactoryImpExpGoodsList(Request request, Date beginDate, Date endDate,
			String fptInOutFlag, String declareState, String fptBusinessType, String code,
			String name, Integer seqNum, String scmcoc) {
		return this.fptManageLogic.findTransferFactoryImpExpGoodsList(beginDate, endDate,
				fptInOutFlag, declareState, fptBusinessType, code, name, seqNum, scmcoc);

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
	public List findTransferImpExpCustomsList(Request request, Date beginDate, Date endDate,
			Integer impexpType, String emsNo, String scmcoc, int projectType, Boolean effec,
			String code, String name, Integer seqNum) {
		return this.fptManageDao.findTransferImpExpCustomsList(beginDate, endDate, impexpType,
				emsNo, scmcoc, projectType, effec, code, name, seqNum);

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
	public List findStatisticTotalTransferStatusQuantity(Request request, Date beginDate,
			Date endDate, String fptInOutFlag, String declareState, String fptBusinessType,
			String code, String name, Integer seqNum, Integer projectType, String scmcocName) {
		return this.fptManageLogic.findStatisticTotalTransferStatusQuantity(beginDate, endDate,
				fptInOutFlag, declareState, fptBusinessType, code, name, seqNum, projectType,
				scmcocName);
	}

	//
	// /**
	// * 获得转厂单据商品信息来自父对象List 用于转换报关清单过程中
	// */
	// public List findTempTransferFactoryCommodityInfoByParentToACT(
	// Request request, List parentList) {
	// return this.transferFactoryManageLogic
	// .findTempTransferFactoryCommodityInfoByParentToACT(parentList);
	// }
	//
	// /**
	// * 获得没有结案,没有过期,生效的关封单据
	// */
	// public List findCustomsEnvelopBillByAvailability(Request request,
	// String scmCocId) {
	// return this.transferFactoryManageDao
	// .findCustomsEnvelopBillByAvailability(scmCocId);
	// }

	// /**
	// * 根据关封单据id获得其计算后的detail
	// */
	// public List
	// findCustomsEnvelopCommodityInfoByTempTransferFactoryCommodityInfo(
	// Request request, String parentId, boolean isImportGoods,
	// List tempTransferFactoryCommodityInfoList) {
	// return this.transferFactoryManageLogic
	// .findCustomsEnvelopCommodityInfoByTempTransferFactoryCommodityInfo(
	// parentId, isImportGoods,
	// tempTransferFactoryCommodityInfoList);
	// }

	/**
	 * 生成结转单据
	 */
	public FptBillHead makeFptBillFromCasBill(Request request, List billDetailList,
			String casBillCode, FptAppHead fptAppHead, String emsNo, boolean isComplex,
			boolean isNewFptBillHead, FptBillHead oldFptBillHead) {
		return this.fptManageLogic.makeFptBillFromCasBill(billDetailList, casBillCode, fptAppHead,
				emsNo, isComplex, isNewFptBillHead, oldFptBillHead);
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
	public List findCustomsEnvelopSpareAnalyes(Request request, String commodityCode,
			String commodityName, String seqNum, String oppoisteEnterpriseName,
			boolean isImportGoods, String customsEnvelopBillNo) {
		return this.fptManageLogic.findCustomsEnvelopSpareAnalyes(commodityCode, commodityName,
				seqNum, oppoisteEnterpriseName, isImportGoods, customsEnvelopBillNo);
	}

	// /**
	// * 有数据转报关清单在关封中
	// *
	// * @param c
	// * @return
	// */
	// public boolean hasDataTransFactoryBillByEnvelopId(Request request,
	// CustomsEnvelopBill c) {
	// return this.transferFactoryManageDao
	// .hasDataTransFactoryBillByEnvelopId(c);
	// }

	// /**
	// * 获得当前关封单据的商品信息的个数
	// */
	// public int findCustomsEnvelopCommodityInfoCount(Request request,
	// String parentId) {
	// return this.transferFactoryManageDao
	// .findCustomsEnvelopCommodityInfoCount(parentId);
	// }

	/**
	 * 获得当前转厂申请单的商品信息的个数
	 */
	public int findCustomsEnvelopRequestCommodityInfoCount(Request request, String parentId) {
		return this.fptManageDao.findCustomsEnvelopRequestCommodityInfoCount(parentId);
	}

	/**
	 * 获得结转单据信息加载子表的记录
	 */
	public List findTransferFactoryCommodityInfo(Request request, String parentId) {
		return this.fptManageDao.findFptBillItemByParentId(parentId);
	}

	/**
	 * 有数据已转关封在转厂申请单中
	 * 
	 * @param c
	 * @return
	 */
	public boolean hasDataTransferCustomsEnvelopByCustomsEnvelopRequest(Request request,
			FptAppHead c) {
		return this.fptManageDao.hasDataTransferCustomsEnvelopByCustomsEnvelopRequest(c);
	}

	/**
	 * 有数据已转转厂申请单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	public boolean hasDataCustomsDeclarationByTransFactBill(Request request, FptBillHead t) {
		return this.fptManageDao.hasDataCustomsDeclarationByTransFactBill(t);
	}

	/**
	 * 有数据已报关清单在转厂单据中
	 * 
	 * @param t
	 * @return
	 */
	public boolean hasDataTransferApplyToCustomsBillByTransferFactoryBill(Request request,
			FptBillHead t) {
		return this.fptManageDao.hasDataTransferApplyToCustomsBillByTransferFactoryBill(t);
	}

	/**
	 * 获得进出口商品信息来自父对象
	 * 
	 * @param request
	 *            请求控制
	 * @param parentList
	 *            父对象
	 * @return 进出口商品信息
	 */
	public List findMakeSummaryFptBillCommodityInfoByParent(Request request, List parentList,
			String inOutFlag) {
		return fptManageLogic.findMakeSummaryFptBillCommodityInfoByParent(parentList, inOutFlag);
	}

	// /**
	// * 查找结转单据生成报关清单时的中间表信息来自关封相关的数据项
	// *
	// * @param c
	// * @return
	// */
	// public List findMakeApplyToCustomsInfoByCustomsEnvelopBill(Request
	// request,
	// CustomsEnvelopBill c) {
	// return this.transferFactoryManageDao
	// .findMakeApplyToCustomsInfoByCustomsEnvelopBill(c);
	// }
	//
	// /**
	// * 获得转厂申请单信息加载子表的记录
	// */
	// public List findCustomsEnvelopCommodityInfo(Request request) {
	// return this.transferFactoryManageDao.findCustomsEnvelopCommodityInfo();
	// }

	/**
	 * 根据申请编号、收退货标志查询数据
	 * 
	 * @param request
	 * @param parentList
	 * @param inOutFlag
	 * @return
	 */
	public List findFptBillCommodityInfoByHeadItem(Request request, List parentList,
			String sysType, String inOutFlag) {
		return fptManageLogic.findFptBillCommodityInfoByHeadItem(parentList, sysType, inOutFlag);
	}

	/**
	 * 转厂分析查询权限控制
	 */
	// @AuthorityFunctionAnnotation(caption = "转厂统计分析-浏览", index = 4)
	public void checkTransferStatisticAnalyse(Request request) {

	}

	/**
	 * 关封余量分析权限控制
	 */
	public void checkCustomsEnvelopSpareAnalyse(Request request) {

	}

	// public List findExportCustomsDeclaration(Request request,
	// CustomsEnvelopBill customsEnvelopBill) {
	// return this.transferFactoryManageLogic
	// .findExportCustomsDeclaration(customsEnvelopBill);
	// }

	// public List findImportCustomsDeclaration(Request request,
	// CustomsEnvelopBill customsEnvelopBill) {
	// return this.transferFactoryManageLogic
	// .findImportCustomsDeclaration(customsEnvelopBill);
	// }

	/**
	 * 获得转厂初始化单据信息加载子表的记录for 报关
	 */
	public List findTransferFactoryInitCommodityInfoForCustoms(Request request, String parentId) {
		return this.fptManageDao.findTransferFactoryInitCommodityInfoForCustoms(parentId);
	}

	/**
	 * @return Returns the transferQueryLogic.
	 */
	public FptQueryLogic getFptQueryLogic() {
		return fptQueryLogic;
	}

	/**
	 * @param transferQueryLogic
	 *            The transferQueryLogic to set.
	 */
	public void setFptQueryLogic(FptQueryLogic transferQueryLogic) {
		this.fptQueryLogic = transferQueryLogic;
	}

	// 备案周报表
	// @AuthorityFunctionAnnotation(caption = "转厂其他报表-浏览", index = 5)
	public List findPutRecord(Request request) {
		return this.fptQueryLogic.findPutRecord();
	}

	// 转厂安排周报表
	// @AuthorityFunctionAnnotation(caption = "转厂其他报表-浏览", index = 5)
	public List findTransferPlan(Request request) {
		return this.fptQueryLogic.findTransferPlan();
	}

	/**
	 * 查询转厂中关封的客户和厂商
	 * 
	 * @return
	 */
	public List findCustomsEnvelopScmCoc(Request request, boolean isImport) {
		return this.fptManageDao.findCustomsEnvelopScmCoc(isImport);
	}

	/**
	 * 查询转厂中关封的商品信息
	 * 
	 * @return
	 */
	// @AuthorityFunctionAnnotation(caption = "转厂其他报表-浏览", index = 5)
	public List findCustomsEnvelopComplex(Request request, boolean isImport, boolean isSeqNum) {
		return this.fptQueryLogic.findCustomsEnvelopComplex(isImport, isSeqNum);
	}

	// 关封明细报表
	public List findCustomsEnvelopList(Request request, String envelopCode, Integer seqNum,
			String complexCode, String scmCoc, boolean isImport, int state, Date beginDate,
			Date endDate) {
		return this.fptQueryLogic.findCustomsEnvelopList(envelopCode, seqNum, complexCode, scmCoc,
				isImport, state, beginDate, endDate);
	}

	// /**
	// * 根据单据号查询关封商品明细
	// *
	// * @param customsEnvelopBillCode
	// * @return
	// */
	// public List findTempTransferFactoryCommInfo(Request request,
	// String customsEnvelopBillCode) {
	// return this.transferFactoryManageDao
	// .findTempTransferFactoryCommInfo(customsEnvelopBillCode);
	// }

	// /**
	// * 查询不在转厂起初单的商品编码
	// *
	// * @param initBillId
	// * @return
	// */
	// public List findComplexNotInInitBill(Request request, String initBillId,
	// String customsEnvelopBillCode) {
	// return this.transferFactoryManageLogic.findComplexNotInInitBill(
	// initBillId, customsEnvelopBillCode);
	// }

	/**
	 * 结转单据自动转报关单及其商品信息
	 * 
	 * @param lsBills
	 * @param param
	 * @return
	 */
	public List makeCusomsDeclarationFromTransferBill(Request request,
			MakeCusomsDeclarationParam param, List billItem, FptAppHead fptAppHead,
			BaseCustomsDeclaration cm) {
		return this.fptManageLogic.makeCusomsDeclarationFromTransferBill(param, billItem,
				fptAppHead, cm);
	}

	/**
	 * 查询未转报关单的结转单据
	 * 
	 * @param isImport
	 * @return
	 */
	public List findFptBillMakeCustomsDeclaration(Request request, String inOutFlag, String appNo,
			Date beginDate, Date endDate) {
		return this.fptManageLogic.findFptBillMakeCustomsDeclaration(inOutFlag, appNo, beginDate,
				endDate);
	}

	/**
	 * 查询追加报关单结转单生成报关单时所有报关单
	 * 
	 * @param request
	 * @param flag
	 * @return
	 */
	public List findCustomsDeclarationByImpExpFlag(Request request, int flag, Integer project) {
		return this.fptManageDao.findCustomsDeclarationByImpExpFlag(flag, project);
	}

	/**
	 * 根据手册号码抓取正在执行的电子手册
	 * 
	 * @param emsNo
	 * @return
	 */
	public DzscEmsPorHead findDzscEmsPorHeadExcu(Request request, String emsNo) {
		return this.fptManageLogic.findDzscEmsPorHeadExcu(emsNo);
	}

	/**
	 * 根据账册册号码抓取正在执行的电子账册
	 * 
	 * @param emsNo
	 * @return
	 */
	public EmsHeadH2k findEmsHeadH2kInExecuting(Request request, String emsNo) {
		return this.fptManageLogic.findEmsHeadH2kInExecuting(emsNo);
	}

	//
	// /**
	// * 根据客户供应商查找转厂商品
	// *
	// * @param scmcoc
	// * 客户供应商
	// * @return 转厂商品
	// */
	// public List findCustomsEnvelopCommodityInfoByScmCoc(Request request,
	// ScmCoc scmcoc, Boolean isCustomer) {
	// return transferFactoryManageLogic
	// .findCustomsEnvelopCommodityInfoByScmCoc(request, scmcoc,
	// isCustomer);
	// }
	/**
	 * 取得结转进口与结转出口
	 */
	public List findScmManuFactory(Request request, String paramScoId) {
		// TODO Auto-generated method stub
		return fptManageDao.findScmManuFactory(paramScoId);
	}

	/**
	 * 保存商品单价维护实体
	 * 
	 * @param list
	 *            商品单价维护实体
	 * @return 商品单价维护实体
	 */
	public void saveBillPriceMaintenance(Request request, List list) {
		this.fptManageDao.batchSaveOrUpdate(list);
	}

	/**
	 * 根据客户供应商查找商品单价维护实体
	 * 
	 * @param scmCoc
	 *            客户供应商
	 * @return 商品单价维护实体
	 */
	// @AuthorityFunctionAnnotation(caption = "商品单价维护-浏览", index = 0.51)
	public List findBillPriceMaintenanceByScmCoc(Request request, ScmCoc scmCoc, Boolean isCustomer) {
		return this.fptManageDao.findBillPriceMaintenanceByScmCoc(scmCoc, isCustomer);
	}

	/**
	 * 删除商品单价维护实体
	 * 
	 * @param list
	 *            商品单价维护实体
	 */
	// @AuthorityFunctionAnnotation(caption = "商品单价维护-删除", index = 0.53)
	public void delBillPriceMaintenance(Request request, List list) {
		this.fptManageLogic.delBillPriceMaintenance(list);
	}

	// @AuthorityFunctionAnnotation(caption = "商品单价维护-修改", index = 0.52)
	public void editBillPriceMaintenancePrice(Request request, ScmCoc scmCoc, Integer seqNum,
			String code, Double price, int projectType, Boolean isCustomer) {
		this.fptManageLogic.editBillPriceMaintenancePrice(scmCoc, seqNum, code, price, projectType,
				isCustomer);

	}

	// @AuthorityFunctionAnnotation(caption = "商品单价维护-修改", index = 0.52)
	public void checkEditBillPriceMaintenancePrice(Request request) {

	}

	// @AuthorityFunctionAnnotation(caption = "商品单价维护-新增", index = 0.54)
	public List addBillPriceMaintence(Request request, List list, ScmCoc scmCoc, Boolean isCustomer) {
		return this.fptManageLogic.addBillPriceMaintence(list, scmCoc, isCustomer);
	}

	public List findTransferClienCollate(Request request, Integer projectType, String emsNo,
			String scmcocCode, Date beginDate, Date endDate, Integer impExpType) {
		return this.fptQueryLogic.findTransferClienCollate(projectType, emsNo, scmcocCode,
				beginDate, endDate, impExpType);
	}

	public List traCustomsCollate(Request request, String year, boolean isImpType) {
		return this.fptQueryLogic.traCustomsCollate(year, isImpType);
	}

	/**
	 * 根据关封号抓取关封里所有的手册号
	 * 
	 * @param envelopNo
	 * @return
	 */
	public List findCustomsEnvelopBillEmsNo(Request request, String envelopNo) {
		return this.fptManageDao.findFptAppEmsNo(envelopNo);
	}

	public List findFptBillItemCommodityInfo(Request request, String parentId, String inOutFlag) {
		// TODO Auto-generated method stub
		return this.fptManageDao.findFptBillItemCommodityInfo(parentId, inOutFlag);
	}

	/**
	 * 查询结转申请单
	 * 
	 * @param inOutFlag
	 * @param declareState
	 * @param scmCocTradeCode
	 * @param isCollated
	 * @return
	 */
	public List findFptAppHeadByScmCoc(Request request, String inOutFlag, String declareState,
			ScmCoc scmCoc, Boolean isCollated) {
		return this.fptManageDao
				.findFptAppHeadByScmCoc(inOutFlag, declareState, scmCoc, isCollated);
	}

	/**
	 * 获取企业需要下载备案资料（RecordationDataDownLoad）
	 */
	public List findRecordationDataDownLoad(Request request, String downLoadState,
			String fptInOutFlag) {
		return this.fptManageDao.findRecordationDataDownLoad(downLoadState, fptInOutFlag);
	}

	// /**
	// * 获取企业需要下载备案资料
	// */
	// public List findCanelInletOutletTransferFactoryBill(Request request) {
	// return this.fptManageDao.findFptCancelBill();
	// }

	public List findFptCancelBill(Request request, String sysType, String billSort) {
		return this.fptManageDao.findFptCancelBill(sysType, billSort);
	}

	/** 新增转厂申请单 */
	public FptAppHead newFptAppHead(Request request, String fptInOutFlag) {
		return this.fptManageLogic.newFptAppHead(fptInOutFlag);
	}

	/**
	 * 存储企业需要下载备案资料（RecordationDataDownLoad）
	 */
	public FptDownData saveRecordationDataDownLoad(Request request,
			FptDownData recordationDataDownLoad) {
		this.fptManageDao.saveFptDownData(recordationDataDownLoad);
		return recordationDataDownLoad;
	}

	public FptDownData saveFptDownData(Request request, FptDownData fptDownData) {
		this.fptManageDao.saveFptDownData(fptDownData);
		return fptDownData;
	}

	public FptCancelBill saveFptCancelBill(Request request, FptCancelBill fptCanelBill) {
		this.fptManageDao.saveFptCancelBill(fptCanelBill);
		return fptCanelBill;
	}

	/**
	 * 获得关封申请单
	 */
	public FptAppHead findFptAppHeadById(Request request, Object id) {
		return this.fptManageDao.findFptAppHeadById(id);
	}

	/**
	 * 删除企业需要下载备案资料
	 */
	public void deleteRecordationDataDownLoad(Request request, FptDownData recordationDataDownLoad) {
		fptManageLogic.deleteRecordationDataDownLoad(recordationDataDownLoad);
	}

	/**
	 * 删除进出货转厂撤消资料
	 */
	public void deleteFptCancelBill(Request request, FptCancelBill fptCanelBill) {
		fptManageLogic.deleteCanelInletOutletTransferFactoryBill(fptCanelBill);
	}

	/**
	 * 查找正在执行的单据
	 */
	public FptBillHead findExingFptBillHeadByEmsNo(Request request, String copBillNo,
			String inOutFlag) {
		return this.fptManageDao.findExingFptBillHeadByEmsNo(copBillNo, inOutFlag);
	}

	/**
	 * 查询结转单据中归并后的商品信息
	 * 
	 * @param request
	 * @param parentId
	 * @return
	 */
	public List findFptBillDictItemCommodityInfo(Request request, String parentId,
			String inOutFlag, String sysType) {
		// TODO Auto-generated method stub
		return this.fptManageLogic.findFptBillDictItemCommodityInfo(parentId, inOutFlag, sysType);
	}

	/**
	 * 转厂单回执处理
	 * 
	 * @param head
	 * @param exingHead
	 * @return
	 */
	public String processFptBillHead(Request request, FptBillHead head, List lsReturnFile) {
		return this.fptManageLogic.processFptBillHead(head, lsReturnFile);
	}

	public Integer addBillListNoInteger(Request request, FptBillHead fptBillHead) {
		return this.fptManageLogic.addBillListNoInteger(fptBillHead);
	}

	/** 是否已存在该申请单 */
	public boolean isExistFptAppHeadByOutCopAppNo(Request request, FptAppHead fptAppHead) {
		return this.fptManageDao.isExistFptAppHeadByOutCopAppNo(fptAppHead);
	}

	/**
	 * 备案申请单数据
	 * 
	 * @param contract
	 *            申请单表头
	 */
	public FptAppHead putOnRecordFptAppHead(Request request, FptAppHead fptAppHead) {
		this.fptManageLogic.putOnRecordFptAppHead(fptAppHead);
		return fptAppHead;
	}

	/**
	 * 判断申请单是否可以备案
	 * 
	 * @param contract
	 *            申请单表头
	 */
	public String checkFptAppHeadForPutOnRecord(Request request, FptAppHead fptAppHead) {
		return this.fptManageLogic.checkFptAppHeadForPutOnRecord(fptAppHead);
	}

	/**
	 * 转抄明细
	 */
	public FptAppHead copyFptAppItem(Request request, FptAppHead head, List<FptAppItem> list) {
		return this.fptManageLogic.copyFptAppItem(head, list);
	}

	/**
	 * 增加单据表体自然序号
	 */

	public List addBillListNo(FptBillHead head) {
		return this.fptManageLogic.addBillListNo(head);
	}

	/**
	 * 把查询出来的临时商品信息,保存为结转单据的商品信息
	 */
	public List saveFptAppItemCommInfoToFptBillItemCommInfo(Request request, List lsResult,
			FptBillHead head) {
		return this.fptManageLogic.saveFptAppItemCommInfoToFptBillItemCommInfo(lsResult, head);
	}

	/**
	 * 把查询出来的归并关系商品信息,保存为结转单据的商品信息
	 */
	public List saveTempCustomsEnvelopCommInfoToFptBillItemCommInfo(Request request, List lsResult,
			FptBillHead head) {
		return this.fptManageLogic.saveTempCustomsEnvelopCommInfoToFptBillItemCommInfo(lsResult,
				head);
	}

	/**
	 * 转抄明细到另一个表头
	 */
	public List copyFptAppItemToHead(Request request, FptAppHead head, List<FptAppItem> list) {
		return this.fptManageLogic.copyFptAppItemToHead(head, list);
	}

	/**
	 * 获得转厂申请单信息加载子表的记录
	 */
	public List findFptBillItems(Request request, String parentId, String inOutFlag) {
		return this.fptManageDao.findFptBillItems(parentId, inOutFlag);
	}

	/**
	 * 转抄数据
	 * 
	 * @param list
	 * 
	 */
	public List copyFptAppHeadAll(Request request, List<FptAppHead> list) {
		return this.fptManageLogic.copyFptAppHeadAll(list);
	}

	/**
	 * 取结转单转报关单时判断手册号是否是所选择的手册号
	 */
	public boolean checkmakeFptToBgdEmsH2k(String cm, List listcomminfo) {
		return this.fptManageLogic.checkmakeFptToBgdEmsH2k(cm, listcomminfo);
	}

	// /** 获得最大的数值除掉新增的值 */
	// public Integer findMaxValueByFptBillItemExceptAdd(Request request,
	// String parentId, String inOutFlag) {
	// return this.fptManageDao.findMaxValueByFptBillItemExceptAdd(parentId,
	// inOutFlag);
	// }

	// /**
	// * 判断归并后的资料是否是按申请表序号与交易单位来进行合并
	// *
	// * @author ower
	// *
	// */
	// public boolean isMergerCheckFptBillDictItem(Request request,
	// String inOutFlag, String parentId ,String sysType) {
	// return this.fptManageLogic.isMergerCheckFptBillDictItem(inOutFlag,
	// parentId ,sysType);
	// }

	/**
	 * 取结转单转报关单时判断手册号是否是所选择的手册号
	 */
	public boolean checkmakeFptToBgdEmsH2kBill(String cm, List listcomminfo) {
		return this.fptManageLogic.checkmakeFptToBgdEmsH2kBill(cm, listcomminfo);
	}

	/**
	 * 转抄数据
	 * 
	 * @param list
	 * 
	 */
	public List copyFptAppHead(Request request, List<FptAppHead> list) {
		return this.fptManageLogic.copyFptAppHead(list);
	}

	/**
	 * 变更申请单 如果返回null就不能变量 否则 就变更一条新的记录
	 * 
	 * @param fptAppHead
	 *            申请单表头
	 * @return Contract 申请单表头
	 */
	public FptAppHead changingFptAppHead(Request request, FptAppHead fptAppHead) {
		return this.fptManageLogic.changingFptAppHead(fptAppHead);
	}

	/**
	 * 判断交易数量是否超量
	 */
	public TempFptBillExeInfo findFptBillExeInfoByFpt(Request request, FptBillItem item) {
		return this.fptManageLogic.findFptBillExeInfoByFpt(item);
	}

	/**
	 * 获得关封申请单信息加载子表的记录
	 */
	public List findFptAppItems(Request request, String parentId, String inOutFlag) {
		return this.fptManageDao.findFptAppItems(parentId, inOutFlag);
	}

	/** 获得临时的 */
	public List findTempFptAppItemByParentId(Request request, FptAppHead head) {
		return this.fptManageLogic.findTempFptAppItemByParentId(head);
	}

	// /**
	// * 获取进出货转厂单据中申报状态为正在执行的数据
	// *
	// * @param parentId
	// * @return
	// */
	//
	// public List findFptBillHeadForCancel(Request request) {
	// return this.fptManageDao.findFptBillHeadForCancel();
	// }

	/**
	 * 获取进出货转厂单据中申报状态为 正在执行、未撤销的单据
	 * 
	 * @param parentId
	 * @return
	 */
	public List findFptBillHeadForCancel(Request request, String fptBusinessType,
			String fptInOutFlag) {
		return this.fptManageDao.findFptBillHeadForCancel(fptBusinessType, fptInOutFlag);
	}

	/**
	 * 申请单料件数据取整
	 * 
	 * @param list
	 *            是FptAppItem型，申请单料件
	 */
	public List<FptAppItem> saveFptAppItemAmountInteger(Request request, List<FptAppItem> list) {
		this.fptManageLogic.saveFptAppItemAmountInteger(list);
		return list;
	}

	public List findMaterielByFptAppHeadType(Request request, String isImportGoods) {
		return this.fptManageDao.findMaterielByFptAppHeadType(isImportGoods);
	}
	
	/**
	 * 根据申请单编号获取申请单
	 * @param request
	 * @param isImportGoods
	 * @param list
	 * @return
	 */
	public List findFptAppHead(Request request, String isImportGoods,List<String> list){
		return this.fptManageLogic.findFptAppHead(isImportGoods,list);
	}
	
	/**
	 * 保存转厂进出货单表头和表体
	 * @param list
	 */
	public void saveFptBillHeadsAndFptBillItems(Request request, List list,String fptBusinessType){
		this.fptManageLogic.saveFptBillHeadsAndFptBillItems(list,fptBusinessType);
	}

	public List findFptAppItemToFptBillItem(Request request, String isImportGoods, String appNO) {
		return this.fptManageDao.findMaterielByFptAppItemType(isImportGoods, appNO);
	}
	
	public List findFptAppItemToFptBillItem(Request request, String isImportGoods,List list) {
		return this.fptManageDao.findMaterielByFptAppItemType(isImportGoods,list);
	}

	public List findUnit(Request request){
		return this.fptManageDao.findUnitList();
	}
	
	public List findFptEmsNoCopBillNoToFptBillItemCopBillNo(Request request, int projectType,
			String isImportGoods, String appNo) {
		return this.fptManageLogic.findFptEmsNoCopBillNoToFptBillItemCopBillNo(projectType,
				isImportGoods, appNo);
	}

	/**
	 * --------------------------------------------结转单据对应报表查询------------------
	 * -----------
	 */
	/**
	 * 取得客户/供应商
	 */
	public List findScmCocsByPara(Request request, String impExpFlagCode, String billTypeCode) {
		return fptManageDao.findScmCocsByPara(impExpFlagCode, billTypeCode);
	}

	/**
	 * 单据号码
	 */
	public List findBillNoByPara(Request request, boolean impExpFlagCode) {
		return fptManageDao.findBillNoByPara(impExpFlagCode);
	}

	/**
	 * 单据号码
	 */
	public List<TempObject> findBomNoByPara(Request request, boolean impExpFlagCode,
			String billTypeCode) {
		return fptManageLogic.findBomNoByPara(impExpFlagCode, billTypeCode);
	}

	/**
	 * 回写结转单报关单号
	 */
	public void reciveCustomsDeclarationCode(Request request,
			List<TempCasBillTOFptTOCustomsReport> listC) {
		fptManageLogic.reciveCustomsDeclarationCode(listC);
	}

	/**
	 * 取消结转单报关单号
	 */
	public void cancelCustomsDeclarationCode(Request request,
			List<TempCasBillTOFptTOCustomsReport> list) {
		fptManageLogic.cancelCustomsDeclarationCode(list);
	}

	/**
	 * 查询结转对应报表
	 */
	public List getMakeFptBillFromCasBill(Request request, boolean inOutFlag, ScmCoc scmCoc,
			Date beginDate, Date endDate, String billNo, String bomNo) {
		return fptManageLogic.getMakeFptBillFromCasBill(inOutFlag, scmCoc, beginDate, endDate,
				billNo, bomNo);
	}

	public List getMakeFptBillCustomsDeclaration(Request request, boolean inOutFlag, String appNo,
			Date beginDate, Date endDate, ScmCoc scmCoc, String emsNo) {
		return fptManageLogic.getMakeFptBillCustomsDeclaration(inOutFlag, appNo, beginDate,
				endDate, scmCoc, emsNo);
	}

	/**
	 * --------------------------------------------结转单据转入方下载备案资料----------------
	 * --------
	 */
	/**
	 * 转厂海关申报
	 * 
	 * @param head
	 *            通关备案表头
	 * @return DzscEmsPorHead 通关备案表头
	 */
	public DeclareFileInfo applyFptDownData(Request request, FptDownData head) {
		return this.fptManageLogic.applyFptDownData(head, request.getTaskId());
	}

	/**
	 * 下载数据处理回执
	 * 
	 */
	public String processFptDownData(Request request, FptDownData head, List lsReturnFile) {
		return this.fptManageLogic.processFptDownData(head, lsReturnFile);
	}

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是RecordationDataDownLoad型，单据表头
	 */
	public FptDownData findFptDownDataById(Request request, String id) {
		return this.fptManageDao.findFptDownDataById(id);
	}

	/**
	 * 根据ID查找撤消数据
	 * 
	 * @return List 是RecordationDataDownLoad型，单据表头
	 */
	public FptCancelBill findFptCancelBillById(Request request, String id) {
		return (FptCancelBill) this.fptManageDao.load(FptCancelBill.class, id);
	}

	/** 获得转出 FptAppItem 来自序号集合 */
	public List<Integer> findFptAppItemListNoByOut(Request request, String parentId) {
		return this.fptManageDao.findFptAppItemListNoByOut(parentId);
	}

	/**
	 * 获得申请单所有数据
	 */
	public List<FptAppHead> findFptAppHeadByNotExceute(Request request, FptAppHead outHead) {
		return this.fptManageDao.findFptAppHeadByNotExceute(outHead);
	}

	/** 新增转厂申请单明细 */
	public FptAppItem newFptAppItem(Request request, FptAppHead head) {
		return this.fptManageLogic.newFptAppItem(head);
	}

	/**
	 * 
	 * 转厂申请单报文生成
	 * 
	 * @param head
	 * 
	 * @return FptBillHead
	 */
	public DeclareFileInfo applyFptAppHead(Request request, FptAppHead head, String taskId) {
		return this.fptManageLogic.applyFptAppHead(head, taskId);
	}

	/**
	 * 查找 FptAppHead 来自 outCopAppNo
	 */
	public FptAppHead findFptAppHeadByOutCopAppNo(Request request, String outCopAppNo) {
		return fptManageDao.findFptAppHeadByOutCopAppNo(outCopAppNo);
	}

	/**
	 * 查找 FptAppHead 来自 统一编号
	 */
	public FptAppHead findFptAppHeadAppNo(Request request, String fptInOutFlag, String seqNo) {
		List list = fptManageDao.findFptAppHeadAppNo(fptInOutFlag, seqNo);
		if (list != null && list.size() > 0) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/**
	 * 查找 FptAppHead 来自 inCopAppNo
	 */
	public FptAppHead findFptAppHeadByInCopAppNo(Request request, String inCopAppNo) {
		return fptManageDao.findFptAppHeadByInCopAppNo(inCopAppNo);
	}

	/**
	 * 转厂申请单回执处理
	 * 
	 * @param fptAppHead
	 * @param existFptAppHead
	 * @return
	 */
	public String processFptAppHead(Request request, FptAppHead fptAppHead,
			FptAppHead existFptAppHead, List lsReturnFile) {
		return fptManageLogic.processFptAppHead(fptAppHead, existFptAppHead, lsReturnFile);
	}

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
	public List findBcsContractDetailByProcessExe(Request request, String parentId, String emsNo,
			String inOutFlag, int index, int length, String property, Object value, boolean isLike) {
		return this.fptManageDao.findBcsContractDetailByProcessExe(parentId, emsNo, inOutFlag,
				index, length, property, value, isLike);
	}

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
	public List findDzscEmsPorHeadDetailByProcessExe(Request request, String parentId,
			String emsNo, String inOutFlag, int index, int length, String property, Object value,
			boolean isLike) {
		return this.fptManageDao.findDzscEmsPorHeadDetailByProcessExe(parentId, emsNo, inOutFlag,
				index, length, property, value, isLike);
	}

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
	public List findBcusEms2kDetailByProcessExe(Request request, String parentId, String emsNo,
			String inOutFlag, int index, int length, String property, Object value, boolean isLike) {
		return this.fptManageDao.findBcusEms2kDetailByProcessExe(parentId, emsNo, inOutFlag, index,
				length, property, value, isLike);
	}

	public List findCustomOrderByIfok(Request request) {
		return this.fptManageDao.findCustomOrderByIfok();
	}

	/** 新增转厂申请单明细 来自 bcs 合同成品 */
	public List<FptAppItem> newFptAppItemByContractExg(Request request, FptAppHead head,
			List<ContractExg> contractExgs) {
		return this.fptManageLogic.newFptAppItemByContractExg(head, contractExgs);
	}

	/** 新增转厂申请单明细 来自 bcs 合同料件 */
	public List<FptAppItem> newFptAppItemByContractImg(Request request, FptAppHead head,
			List<ContractImg> contractImgs) {
		return this.fptManageLogic.newFptAppItemByContractImg(head, contractImgs);
	}

	/** 新增转厂申请单明细 来自 DZSC 正在执行的电子手册通关备案里的料件 */
	public List<FptAppItem> newFptAppItemByDzscEmsImgBill(Request request, FptAppHead head,
			List<DzscEmsImgBill> dzscEmsImgBills) {
		return this.fptManageLogic.newFptAppItemByDzscEmsImgBill(head, dzscEmsImgBills);
	}

	/** 新增转厂申请单明细 来自 DZSC 正在执行的电子手册通关备案里的成品 */
	public List<FptAppItem> newFptAppItemByDzscEmsExgBill(Request request, FptAppHead head,
			List<DzscEmsExgBill> dzscEmsExgBills) {
		return this.fptManageLogic.newFptAppItemByDzscEmsExgBill(head, dzscEmsExgBills);
	}

	/** 新增转厂申请单明细 来自 Bcus 正在执行的电子帐册里的成品 */
	public List<FptAppItem> newFptAppItemByEmsHeadH2kExg(Request request, FptAppHead head,
			List<EmsHeadH2kExg> emsHeadH2kExgs) {
		return this.fptManageLogic.newFptAppItemByEmsHeadH2kExg(head, emsHeadH2kExgs);
	}

	/** 新增转厂申请单明细 来自 Bcus 正在执行的电子帐册里的料件 */
	public List<FptAppItem> newFptAppItemByEmsHeadH2kImg(Request request, FptAppHead head,
			List<EmsHeadH2kImg> emsHeadH2kImgs) {
		return this.fptManageLogic.newFptAppItemByEmsHeadH2kImg(head, emsHeadH2kImgs);
	}

	public List findTempTransferFactoryBillByScmCocNotCER(Request request, String scmCocId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获得转厂申请单信息加载子表的记录
	 */
	public List findFptAppItemsByModifyMarkState(Request request, String parentId,
			String inOutFlag, String modifyMarkState) {
		return this.fptManageDao.findFptAppItemsByModifyMarkState(parentId, inOutFlag,
				modifyMarkState);
	}

	/** 获得最大的数值除掉新增的值 */
	public Integer findMaxValueByFptAppItemExceptAdd(Request request, String parentId,
			String inOutFlag) {
		return this.fptManageDao.findMaxValueByFptAppItemExceptAdd(parentId, inOutFlag);
	}

	/**
	 * 各报关模式查找所有正在执行手册
	 * 
	 * @param projectType
	 * @return
	 */
	public List<BaseEmsHead> findAllEmsExe(Request request, int projectType) {
		return this.fptManageDao.findAllEmsExe(projectType);
	}

	/**
	 * 申报撤消单数据
	 * 
	 * @param head
	 * @param taskId
	 * @return
	 */
	public DeclareFileInfo applyFptCancelBill(Request request, FptCancelBill head) {
		String taskId = request.getTaskId();
		return this.fptManageLogic.applyFptCancelBill(head, taskId);
	}

	/**
	 * 撤消数据处理回执
	 * 
	 */
	public String processFptCancelBill(Request request, FptCancelBill head, List lsReturnFile) {
		return this.fptManageLogic.processFptCancelBill(head, lsReturnFile);
	}

	/**
	 * 获得申请单余量分析报表
	 */
	public List<TempFptAppSpareAnalyes> findTempFptAppSpareAnalyes(Request request,
			TempFptAppParam param) {
		return this.fptManageLogic.findTempFptAppSpareAnalyes(param);
	}
	
	
	/**
	 * HYQ
	 * 深加工结转申请表收发货余量分析
	 */
	public List<TempFptApplySurplus> findTempFptApplySurplus(Request request,
			TempFptAppParam param) {
		return this.fptManageLogic.findTempFptApplySurplus(param);
	}
	
	public List<TempFptApplySurplus> findTempFptApplySurplus0(Request request,
			TempFptAppParam param) {
		return this.fptManageLogic.findTempFptApplySurplus0(param);
	}
	
	/**
	 * HYQ
	 * 深加工结转手册余量分析
	 */
	public List<TempFptApplySurplus> findTempFptApplyDifference(Request request,
			TempFptAppParam param) {
		return this.fptManageLogic.findTempFptApplyDifference(param);
	}

	/**
	 * 通过多个订单表头，查询表体！
	 * 
	 * @param customOrders
	 * @return
	 */
	public List findCustomOrderForToFptAppHead(Request request, List customOrders) {
		return this.fptManageLogic.findCustomOrderForToFptAppHead(customOrders);
	}

	/**
	 * 订单转转厂申请单
	 * 
	 * @param list
	 */
	public List makeCustomsOrderToFptAppHead(Request request, FptAppHead head,
			List<TempFptAppheadAndOrder> list, int projectType, String materielType, String emsNo) {
		return this.fptManageLogic.makeCustomsOrderToFptAppHead(head, list, projectType,
				materielType, emsNo);
	}

	/**
	 * 查询单据某一属性不重复值
	 * 
	 * @param prop
	 * @return
	 */
	public List findDistinctProperiesFromFptBillItem(Request request, String prop) {
		return this.fptManageDao.findDistinctProperiesFromFptBillItem(prop);
	}

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
	public List findMakeFptBillFromCasBillInfo(Request request, String emsNo, String casBillNo,
			String ptNo, String fptCopNo, String complexCode, Date beginCasDate, Date endCasDate,
			Date beginFptDate, Date endFptDate) {
		return this.fptQueryLogic.findMakeFptBillFromCasBillInfo(emsNo, casBillNo, ptNo, fptCopNo,
				complexCode, beginCasDate, endCasDate, beginFptDate, endFptDate);
	}

	/**
	 * 获得申请单余量分析报表(明细)
	 */
	public List<TempFptAppSpareAnalyesDetail> findTempFptAppSpareAnalyesDetail(Request request,
			TempFptAppParam param) {
		return this.fptManageLogic.findTempFptAppSpareAnalyesDetail(param);
	}

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
	public TempFptExeInfo findContractExeInfoByFpt(Request request, int projectType,
			String inOutFlag, String emsNo, int seqNum, FptAppItem fptAppItem) {
		return this.fptManageLogic.findContractExeInfoByFpt(projectType, inOutFlag, emsNo, seqNum,
				fptAppItem);
	}

	/**
	 * 获得转厂报关单不重复的某一属性
	 */
	public List findDistinctTranceCustomsDeclaration(Request request, int projectType, String prop) {
		return this.fptManageDao.findDistinctTranceCustomsDeclaration(projectType, prop);
	}

	/**
	 * 抓取结转申请表里面的手册/账册号
	 * 
	 * @param fptAppHead
	 * @param inOutFlag
	 * @return
	 */
	public List findEmsNoFromFptApp(Request request, FptAppHead fptAppHead, String inOutFlag) {
		return this.fptManageDao.findEmsNoFromFptApp(fptAppHead, inOutFlag);
	}

	/**
	 * 收送货单据导入对方的资料
	 * 
	 * @param listHead
	 * @param listItem
	 */
	public void saveFptBillItemFormOtherSide(Request request, List listHead, List listItem,
			boolean isOverwrite) {
		this.fptManageLogic.saveFptBillItemFormOtherSide(listHead, listItem, isOverwrite);
	}

	/**
	 * ----------------------------邮件------------------/ /** 保存邮件
	 */
	// @AuthorityFunctionAnnotation(caption = "收发邮件参数-保存", index = 0.454)
	public void saveFptEmailParamver(Request request, FptEmailParamver email) {
		fptManageDao.saveFptEmailParamver(email);
	}

	// @AuthorityFunctionAnnotation(caption = "收发邮件-保存", index = 0.454)
	public void saveFptEmail(Request request, FptEmail email) {
		fptManageDao.saveFptEmail(email);
	}

	// @AuthorityFunctionAnnotation(caption = "收发邮件-删除", index = 0.454)
	public void deleteFptEmail(Request request, FptEmail fptEmail) {
		fptManageDao.deleteFptEmail(fptEmail);

	}

	// @AuthorityFunctionAnnotation(caption = "收发邮件-删除", index = 0.454)
	public void deleteFptEmailState(Request request, FptEmail fptEmail) {
		fptManageDao.deleteFptEmailState(fptEmail);

	}

	/**
	 * 查询邮件设
	 */
	public FptEmailParamver findFptEmailParamver(Request request) {
		return this.fptManageDao.FindFptEmailParamver();
	}

	/**
	 * 查询邮件
	 */
	public List FindFptEmail(Request request, String mailIRType) {
		return this.fptManageDao.FindFptEmail(mailIRType);
	}

	/**
	 * 查出所邮件中所需要的资料
	 */
	public List findAppBillToFptEmail(Request request, String sysType, String state,
			String inoutFlag, String makeinout) {
		return this.fptManageLogic.findAppBillToFptEmail(sysType, state, inoutFlag, makeinout);
	}

	public List FindFptEmailToAress(Request request) {
		return this.fptManageDao.FindFptEmailToAress();
	}

	/**
	 * 处理邮件
	 */
	public void excEmail(Request request, FptEmail email, TempFptEmail fp) {
		fptManageLogic.excEmail(email, fp);
	}

	/**
	 * 获得关封申请单所有数据来客户供应商
	 */
	public List findFptAppHeadByScmCoc(Request request, ScmCoc scmCoc) {
		return this.fptManageDao.findFptAppHeadByScmCoc(scmCoc);
	}

	/**
	 * 根据客户供应商查找FptComplex
	 */
	public List findFptComplex(Request request, ScmCoc scmcoc) {
		return this.fptManageDao.findFptComplex(scmcoc);
	}

	/**
	 * 根据客户供应商查找海关商品编码（经过过滤）
	 */
	public List findCustomsComplex(Request request, ScmCoc scmcoc) {
		return this.fptManageDao.findCustomsComplex(scmcoc);
	}

	/**
	 * 删除多个对像
	 */
	public void deleteObjects(Request request, List list) {
		this.fptManageLogic.deleteObjects(list);
	}

	/**
	 * 保存多个对像
	 */
	public List saveObjects(Request request, List list) {
		return this.fptManageLogic.saveObjects(list);
	}

	/**
	 * 保存深加工结转-进出货转厂单据Excel导入信息
	 */

	public void saveFptBillHeadFromImport(Request request, List<TempFptBillHeadImportFromExcel> ls,
			String inOutFlag, boolean isOverwrite) {
		this.fptManageLogic.saveFptBillHeadFromImport(ls, isOverwrite, inOutFlag);
	}

	/**
	 * 获得申请单
	 */
	public FptAppHead findFptAppHeadByOutCopAppNo(Request request, String outCopAppNo,
			String declareState) {
		return this.fptManageDao.findFptAppHeadByOutCopAppNo(outCopAppNo, declareState);
	}

	/**
	 * 获得申请单
	 */
	public FptAppHead findFptAppHeadByInCopAppNo(Request request, String inCopAppNo,
			String declareState) {
		return this.fptManageDao.findFptAppHeadByInCopAppNo(inCopAppNo, declareState);
	}

	/** 获得申请单 */
	public FptAppHead findFptAppHeadByAppNo(Request request, String appNo, String declareState) {
		return this.fptManageDao.findFptAppHeadByAppNo(appNo, declareState);
	}

	/**
	 * 下载出口明细
	 * 
	 * 在转厂申请表模块 [转入方] 加入单笔从QP系统导入转出方的资料, 1.用户选择转入申请表 2.点击 [下载转出方申请表数据] 导入按钮
	 * 3.以[申请表编号]为条件新增导入转出方申请表数据, 4.以[申请表编号]为条件覆盖导入转出方申请表数据 ( 先删除以前所有数据,导入最新数据 )
	 * 
	 * @param fptAppHead
	 * @return result = List<FptAppItem> 比QP的代码本人认为要更为高明 用进程任务相对于反回信息,要更高明一些
	 */
	public List<FptAppItem> downloadFptAppItemsOutByQp(Request request, String taskId,
			FptAppHead fptAppHead) {
		return fptQpLogic.downloadFptAppItemsOutByQp(taskId, fptAppHead);
	}

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
	public List<FptAppItem> downloadFptAppItemsInByQp(Request request, String taskId,
			FptAppHead fptAppHead) {
		return fptQpLogic.downloadFptAppItemsInByQp(taskId, fptAppHead);
	}

	/**
	 * 获得 正在处理 的转出申请表 来自海关编码没有明细 在转厂申请表模块 [转入方] 加入按
	 * [对方公司海关编码]从QP系统查询已审核通的的转厂申请表, 用户可以选取,批量从QP导入,不提供覆盖导入
	 * 
	 * @param outTradeCode
	 * @return
	 */
	public List<FptAppHead> findFptAppHeadByQp(Request request, String outTradeCode) {
		return fptQpLogic.findFptAppHeadByQp(outTradeCode);
	}

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
	public List<FptAppHead> downloadFptAppHeadsOutByQp(Request request, String taskId,
			String outTradeCode, List<FptAppHead> fptAppHeads) {
		return fptQpLogic.downloadFptAppHeadsOutByQp(taskId, outTradeCode, fptAppHeads);
	}

	/**/// //////////////////////以下是转厂收退货单//////////////////////////**/

	public List downloadFptBillItemsOutByQp(Request request, String taskId, FptBillHead fptBillHead) {
		return fptQpLogic.downloadFptBillItemsOutByQp(taskId, fptBillHead);
	}

	public List downloadFptBillItemsInByQp(Request request, String taskId, FptBillHead fptBillHead) {
		return fptQpLogic.downloadFptBillItemsInByQp(taskId, fptBillHead);
	}

	/**
	 * 根据核销关封状态获得关封申请单所有数据
	 * 
	 * @param request
	 * @param scmCoc
	 *            客户/供应商
	 * @param fptInOutFlag
	 *            转厂标志
	 * @param isCollated
	 *            核销关封状态
	 * @return
	 */
	public List findFptAppHeadByIsCollatedAndScmCoc(Request request, ScmCoc scmCoc, Date strDate,
			Date endDate, String fptInOutFlag, Boolean isCollated, Boolean is_can) {
		return fptManageDao.findFptAppHeadByIsCollatedAndScmCoc(scmCoc, strDate, endDate,
				fptInOutFlag, isCollated, is_can);
	}

	@Override
	public String getTomcatDir() {
		return System.getProperty("user.dir");
	}

	@Override
	public List<Object[]> importFptApp(Request request, byte[] excelFileContent, String taskId,
			String FptInOutFlag, List<FptAppHead> list , Date endDate) {
		// fptManageLogic.importFptApp(excelFileContent,
		// taskId);
		return fptManageLogic.importFptApp(excelFileContent, taskId, FptInOutFlag, list , endDate );
	}

	public List<FptAppHead> importFptAppisExists(Request request, byte[] excelFileContent,
			String ioFlag) {
		return fptManageLogic.importFptAppisExists(excelFileContent, ioFlag);
	}

	/**
	 * 查询是否存在
	 * 
	 * @param seqNo
	 *            电子口岸统一编号
	 * @param AppNo
	 *            申请表编号
	 * @param fptInOutFlag
	 * @return
	 */
	public boolean findExistsSeqNoOrAppNo(Request request, String seqNo, String appNo,
			String fptInOutFlag) {
		return fptManageLogic.findExistsSeqNoOrAppNo(seqNo, appNo, fptInOutFlag);
	}

	/**
	 * 判断收货单中商品明细“发货序号是否存在”
	 * 
	 * @param request
	 * @param head
	 * @param outNo
	 * @param fptInOutFlag
	 * @return
	 */
	public boolean isExistFptBillItemByOutNo(Request request, FptBillHead head, Integer outNo,
			String fptInOutFlag) {
		return fptManageDao.isExistFptBillItemByOutNo(head, outNo, fptInOutFlag);
	}

	
	/**
	 * 查找是否 被收发货单据使用
	 */
	public boolean isExistFptFptBillHeadByAppNo(Request request, String appNo, String fptInOutFlag) {
		return fptManageDao.isExistFptFptBillHeadByAppNo(appNo, fptInOutFlag);
	}
	
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
	public List findComplexByContract(Request request,int index, int length, int projectType,String emsNo, boolean isMaterial,
			String property,Object value, boolean isLike,Boolean isAll){
		if(isMaterial){
			return fptManageDao.findImgComplexByContract(index,length,projectType,emsNo,property,value,isLike,isAll);
		}else{
			return fptManageDao.findExgComplexByContract(index,length,projectType,emsNo,property,value,isLike,isAll);
		}
	}

	/**
	 * 查询关封申请单明细成品
	 * @param request
	 * @param emsNo
	 * @param seqNum
	 * @param projectType
	 * @return
	 */
	public List findFptAppItemExgBySeqNum(Request request,String emsNo, Integer seqNum,Integer projectType){
		List list = new ArrayList();
		if(ProjectType.BCS==projectType){
			list = fptManageDao.findContractExgBySeqNum(emsNo, seqNum);
		}else if(ProjectType.BCUS==projectType){
			list = fptManageDao.findEmsHeadH2kExgBySeqNum(emsNo, seqNum);
		}else if(ProjectType.DZSC==projectType){
			list = fptManageDao.findDzscEmsExgBillBySeqNum(emsNo, seqNum);
		}
		return list;
	}
}