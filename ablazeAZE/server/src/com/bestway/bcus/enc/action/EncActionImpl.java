package com.bestway.bcus.enc.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.cas.entity.BillTemp1;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.BcusContractExeInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.CustomsFromMateriel;
import com.bestway.bcus.enc.entity.ExpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ImpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.bcus.enc.entity.InputInExRequestBillOrder;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.enc.entity.XinpuReport;
import com.bestway.bcus.enc.logic.EncBcusLogic;
import com.bestway.bcus.enc.logic.EncLogic;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.bcus.system.entity.BcusParameterSet;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ProjectDept;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.action.BaseEncActionImpl;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsFromMateriel;

/**
 * @author EncAction 接口的实现类 chcked by kcb at 2008/12/1
 */
//通关--申请单与清单
@AuthorityClassAnnotation(caption = "电子帐册", index = 6)
@SuppressWarnings("unchecked")
public class EncActionImpl extends BaseEncActionImpl implements EncAction {
	/**
	 * @return 帐册通关logic层
	 */
	public EncLogic getEncLogic() {
		return ((EncLogic) getBaseEncLogic());
	}

	/**
	 * 设置权限名称
	 */
	public EncActionImpl() {
		setModuleName("Authority");
	}

	/**
	 * 帐册通关logic层
	 */
	private EncBcusLogic encBcusLogic = null;

	/**
	 * 查询BCUS参数
	 * 
	 * @param parameter
	 */
	public BcusParameterSet findBcusParameterSet(Request request) {
		return ((EncDao) getBaseEncDao()).findBcusParameterSet();
	}

	/**
	 * 保存BCUS参数
	 * 
	 * @param parameter
	 */
	public BcusParameterSet saveBcusParameterSet(Request request,
			BcusParameterSet bcusParameterSet) {
		((EncDao) this.getBaseEncDao()).saveOrUpdate(bcusParameterSet);
		return bcusParameterSet;
	}

	/**
	 * 取得成品单耗来自序号汇总
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            序号
	 * @return 与指定序号匹配的成品序号
	 */
	public List findExgUnitWearBySeqNumIsTotal(Request request, BillTemp1 bill,
			Date beginDate, Date endDate, String deptId) {
		return encBcusLogic.findExgUnitWearBySeqNumIsTotal(bill, beginDate,
				endDate, deptId);
	}

	/**
	 * 取得成品单耗来自序号单个
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            序号
	 * @return 与指定序号匹配的成品序号
	 */
	public List findExgUnitWearBySeqNumNoTotal(Request request, BillTemp1 bill,
			Date beginDate, Date endDate, String deptId, Integer impExpType) {
		return encBcusLogic.findExgUnitWearBySeqNumNoTotal(bill, beginDate,
				endDate, deptId, impExpType);
	}

	/**
	 * 查询所有料件的耗用
	 * 
	 * @param request
	 * @param imgSeqNum
	 * @return
	 */
	public List findBomBySeqNum(Request request, Integer[] imgSeqNum) {
		return ((EncDao) getBaseEncDao()).findBomBySeqNum(imgSeqNum);

	}

	/**
	 * 查找所有进出口申请单
	 * 
	 * @param request
	 *            请求控制
	 * @return 进出口申请单
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-浏览", index = 3.1)
	public List findImpExpRequestBill(Request request) {
		return ((EncDao) getBaseEncDao()).findImpExpRequestBill();
	}

	public List findImpExpRequestBill1(Request request) {
		return ((EncDao) getBaseEncDao()).findImpExpRequestBill();
	}
	
	/**
	 * 查找所有进出口申请单来自进出口申请单类型
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @param isCustomsBill
	 *            已全转报关清单或全转已转报关单（当全部转完为True）
	 * @param billNo
	 *            单据号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param customer
	 *            客户/供应商
	 * @return 符合上面指定的条件的进出口申请单
	 */
//	@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-浏览", index = 3.1)
	public List findImpExpRequestBillByType(Request request, int type,
			Boolean isCustomsBill, String billNo, Date beginDate, Date endDate,
			String customer) {
		return ((EncDao) getBaseEncDao()).findImpExpRequestBillByType(type,
				isCustomsBill, billNo, beginDate, endDate, customer);
	}

	/**
	 * 查找进出口申请单
	 */
	public List findImpExpRequestBillByBillNo(Request request, String billNo,
			Integer billType) {
		return ((EncDao) getBaseEncDao()).findImpExpRequestBillByBillNo(billNo,
				billType);
	}

	/**
	 * 查找所有进出口申请单
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @return 进出口申请单
	 */
	//@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-浏览", index = 3.1)
	public List findAllImpExpRequestBillByType(Request request, int type) {
		return ((EncDao) getBaseEncDao()).findAllImpExpRequestBillByType(type);
	}

	/**
	 * 删除进出口申请单数据
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBill
	 *            进出口申请单据
	 */
	//@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-删除", index = 3.1)
	public void deleteImpExpRequestBill(Request request,
			ImpExpRequestBill impExpRequestBill) {
		((EncLogic) getBaseEncLogic())
				.deleteImpExpRequestBill(impExpRequestBill);
	}

	/**
	 * 保存进出口申请单数据
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBill
	 *            进出口申请单据
	 * @return 进出口申请单
	 */
	//@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-保存", index = 3.1)
	public ImpExpRequestBill saveImpExpRequestBill(Request request,
			ImpExpRequestBill impExpRequestBill) {
		((EncLogic) getBaseEncLogic()).saveImpExpRequestBill(impExpRequestBill);
		return impExpRequestBill;
	}
    /**
     * 保存申请单表头项数
     * @param request
     * @param impExpRequestBill
     * @return
     */
	public ImpExpRequestBill saveImpExpRequestBillItemCount(Request request,
			ImpExpRequestBill impExpRequestBill) {
		((EncLogic) getBaseEncLogic()).saveImpExpRequestBillItemCount(impExpRequestBill);
		return impExpRequestBill;
	}
	
	/**
	 * 查找进出口申请单来自id
	 * 
	 * @param id
	 */
	public ImpExpRequestBill findImpExpRequestBillById(Request request, String id) {
		return ((EncDao)getBaseEncDao()).findImpExpRequestBillById(id);
	}

	/**
	 * 获得最大的单据号来自进出口申请单表
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @return 最大的编号或者是0001
	 */
	public String getMaxBillNoByType(Request request, int type) {
		return ((EncLogic) getBaseEncLogic()).getMaxBillNoByType(type);
	}

	/**
	 * 删除进出口商品信息数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            进出口商品信息
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-删除", index = 3.1)
	public void deleteImpExpCommodityInfo(){}
	
	public void deleteImpExpCommodityInfo(Request request, List list) {
		((EncLogic) getBaseEncLogic()).deleteImpExpCommodityInfo(list);
	}

	/**
	 * 保存进出口商品信息数据
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param list
	 *            进出口商品信息
	 * @return 进出口商品信息数据
	 */
	//@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-保存", index = 3.1)
	public List saveImpExpCommodityInfo(Request reqeust, List list) {
		((EncDao) getBaseEncDao()).saveImpExpCommodityInfo(list);
		return list;
	}

	/**
	 * 获得商品信息加载子表的记录
	 * 
	 * @param request
	 *            请求控制
	 * @return 商品信息加载子表的记录
	 */
	//@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-浏览", index = 3.1)
	public List findImpExpCommodityInfo(Request request) {
		return ((EncDao) getBaseEncDao()).findImpExpCommodityInfo();
	}

	/**
	 * 获得当前进出口的商品信息的个数
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            进出口申请单id
	 * @return 进出口商品信息的个数
	 * 
	 */
	public int findImpExpCommodityInfoCount(Request request, String parentId) {
		return ((EncDao) getBaseEncDao())
				.findImpExpCommodityInfoCount(parentId);
	}

	/**
	 * 查找所有进出报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @return 进出口报关清单
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-浏览", index = 3.2)
	public List findApplyToCustomsBillList(Request request) {
		return ((EncLogic) getBaseEncLogic()).findApplyToCustomsBillList();
	}

	/**
	 * 根据清单类型查找报关清单.
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return 报关清单
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-浏览", index = 3.2)
	public List findApplyToCustomsBillListByType(Request request,
			int impExpFlag, Date beginDate, Date endDate) {
		return ((EncLogic) getBaseEncLogic()).findApplyToCustomsBillListByType(
				impExpFlag, beginDate, endDate);
	}

	public List findApplyToCustomsBillListByTypeBoToCustoms(Request request,
			int impExpFlag) {
		return ((EncDao) getBaseEncDao())
				.findApplyToCustomsBillListByTypeBoToCustoms(impExpFlag);
	}

	/**
	 * 根据清单类型查找未转报关单的报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return 未转报关单的报关清单
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-浏览", index = 3.2)
	public List findBillListNoMakeCustomsDeclaration(Request request,
			int impExpFlag) {
		return ((EncDao) getBaseEncDao())
				.findBillListNoMakeCustomsDeclaration(impExpFlag);
	}

	/**
	 * 删除报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-删除", index = 3.2)
	public void deleteApplyToCustomsBillList(Request request,
			ApplyToCustomsBillList applyToCustomsBillList) {
		this.encBcusLogic.deleteApplyToCustomsBillList(applyToCustomsBillList);
	}

	/**
	 * 保存报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @return 报关清单
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-保存", index = 3.2)
	public ApplyToCustomsBillList saveApplyToCustomsBillList(Request request,
			ApplyToCustomsBillList applyToCustomsBillList) {
		((EncLogic) getBaseEncLogic())
				.saveApplyToCustomsBillList(applyToCustomsBillList);
		return applyToCustomsBillList;
	}

	/**
	 * 根据归并后商品信息查询归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeAfterComInfo
	 *            归并后商品信息
	 * @return 与指定的归并后商品信息id匹配的归并前商品信息
	 */
	public List findAtcMergeBeforeComInfoByAfterID(Request request,
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		return ((EncLogic) getBaseEncLogic())
				.findAtcMergeBeforeComInfoByAfterID(atcMergeAfterComInfo);
	}

	/**
	 * 根据清单编号查询归并后商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @return 与指定的清单编号匹配的归并后商品信息
	 */
	public List findAtcMergeAfterComInfoByListID(Request request,
			ApplyToCustomsBillList applyToCustomsBillList) {
		return ((EncLogic) getBaseEncLogic())
				.findAtcMergeAfterComInfoByListID(applyToCustomsBillList);
	}
	
	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeBeforeComInfo
	 *            归并前商品信息
	 * @return 删除后报关清单的信息
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-删除", index = 3.2)
	public ApplyToCustomsBillList deleteAtcMergeBeforeComInfo(Request request,
			AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
		return ((EncLogic) getBaseEncLogic())
				.deleteAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
	}

	/**
	 * 删除报关清单归并前商品信息(多笔)
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关清单归并前商品信息
	 * @return 删除归并前商品信息后报关清单的信息
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-删除", index = 3.2)
	public ApplyToCustomsBillList deleteAtcMergeBeforeComInfo(Request request,
			List list) {
		return ((EncLogic) getBaseEncLogic()).deleteAtcMergeBeforeComInfo(list);
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeBeforeComInfo
	 *            归并前商品信息
	 * @return 报关清单归并前商品信息
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-保存", index = 3.2)
	public AtcMergeBeforeComInfo saveAtcMergeBeforeComInfo(Request request,
			AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
		((EncLogic) getBaseEncLogic())
				.saveAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
		return atcMergeBeforeComInfo;
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param declaredDataList
	 *            临时单据商品信息
	 * @param billList
	 *            报关清单
	 * @return 报关清单归并前商品信息
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-保存", index = 3.2)
	public ApplyToCustomsBillList saveAtcMergeBeforeComInfo(Request request,
			List declaredDataList, ApplyToCustomsBillList billList) {
		((EncLogic) getBaseEncLogic()).saveAtcMergeBeforeComInfo(
				declaredDataList, billList);
		return billList;
	}

	/**
	 * 获取报关清单的最大清单编号
	 * 
	 * @param request
	 *            请求控制
	 * @return 报关单的最大编号
	 */
	public String getApplyToCustomsBillListMaxNo(Request request) {
		return ((EncDao) getBaseEncDao()).getApplyToCustomsBillListMaxNo();
	}
	
	/**
	 * 查询申请单
	 * 
	 * @param billNo
	 *            清单状态
	 * @param flat
	 *            进出口标志
	 * @return
	 */
	public ApplyToCustomsBillList getApplyBillList(Request request, String billNo, int flag) {
		return ((EncDao) getBaseEncDao()).getApplyBillList(billNo, flag);
	}

	/**
	 * 取得临时申报商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            报关清单
	 * @param materielProductFlag
	 *            物料成品标志
	 * @return 临时申报商品信息
	 */
	public List getTempDeclaredCommInfo(Request request,
			ApplyToCustomsBillList billList, Integer materielProductFlag) {
		return ((EncLogic) getBaseEncLogic()).getTempDeclaredCommInfo(billList,
				materielProductFlag);
	}

	/**
	 * 获得商品信息记录来自数据是否正确的检验
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            父对象的id
	 * @return 检验后的商品信息记录
	 */
	public List findImpExpCommodityInfoByCheck(Request request, String parentId) {
		return ((EncLogic) getBaseEncLogic())
				.findImpExpCommodityInfoByCheck(parentId);
	}

	/**
	 * 查找所有可以生成报关清单的数据来自进出口申请单(ATC)
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            单据类型
	 * @return 有效且已全转报关清单或全转已转报关单
	 */
	public List findImpExpRequestBillToATCByType(Request request, int type) {
		return ((EncDao) getBaseEncDao())
				.findImpExpRequestBillToATCByType(type);
	}

	/**
	 * 进出口申请单--->报关清单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isImportGoods
	 * 是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单
	 * @param dataSource
	 *            数据源
	 * @param emsEdiMergerHead
	 *            电子帐册归并表头
	 * @param isImportGoods
	 *            是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * @param isNewRecord
	 *            是代表生成新的清单还是追加到原有的清单
	 * @return list.get(0)==清单单头 list.get(1)==申请单头（修改后）
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-保存", index = 3.2)
	public List makeApplyToCustomsRequestBillList(Request request,
			List dataSource, boolean isImportGoods,
			MakeCusomsDeclarationParam param) {
		return ((EncLogic) getBaseEncLogic())
				.makeApplyToCustomsRequestBillList(dataSource, isImportGoods,
						param);
	}

	/**
	 * 在根据报关清单自动生成报关单的时候，检查报关清单的数据是否符合条件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关清单内容
	 * @return 如果成功返回0；
	 */
	public int checkBillListForMakeCustomsDeclaration(Request request, List list) {
		return ((EncLogic) getBaseEncLogic())
				.checkBillListForMakeCustomsDeclaration(list);
	}

	/**
	 * 清单转报关单 从报关清单自动转报关单及其商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param billLists
	 *            报关清单
	 * @param param
	 *            报关单参数
	 * @param customs
	 *            报关单信息
	 * @return 与指定的报关清单内容匹配的报关单信息
	 */
	// @AuthorityFunctionAnnotation(caption = "保存报关单", index = 8)
	public List makeCusomsDeclarationFromBillList(Request request,
			List impexpbills, List afterinfoLists,
			MakeCusomsDeclarationParam param, CustomsDeclaration customs) {
		return ((EncLogic) getBaseEncLogic())
				.makeCusomsDeclarationFromBillList(impexpbills, afterinfoLists,
						param, customs);
	}

	/**
	 * 作废报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclaration
	 *            报关单信息
	 * @return 作废的报关单
	 */
	// @AuthorityFunctionAnnotation(caption = "删除报关单", index = 9)
	public CustomsDeclaration cancelCustomsDeclaration(Request request,
			CustomsDeclaration customsDeclaration) {
		((EncLogic) getBaseEncLogic())
				.cancelCustomsDeclaration(customsDeclaration);
		return customsDeclaration;
	}

	/**
	 * 查找已转报关单的报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @return 已转报关单的报关清单
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-大小清单差异分析", index = 3.2)
	public List findBillListMakedCustomsDeclaration(Request request) {
		return ((EncDao) getBaseEncDao()).findBillListMakedCustomsDeclaration();
	}

	/**
	 * 根据报关清单取得报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            报关清单
	 * @return 与指定的报关清单匹配的报关单信息
	 */
	public List findCustomsDeclarationByBillList(Request request,
			ApplyToCustomsBillList billList) {
		return ((EncDao) getBaseEncDao())
				.findCustomsDeclarationByBillList(billList);
	}

	/**
	 * 根据报关清单取得清单和报关单商品信息的差异
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            报关清单
	 * @return 报关清单和报关单内容
	 */
	public List findDiffrenceAnalyseCommInfo(Request request,
			ApplyToCustomsBillList billList) {
		return ((EncLogic) getBaseEncLogic())
				.findDiffrenceAnalyseCommInfo(billList);
	}

	/**
	 * 根据报关单ID取得报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationID
	 *            报关单id
	 * @return 与指定报关单id匹配的报关单商品信息
	 */
	public List findCommInfoByCustomsDeclarationID(Request request,
			String customsDeclarationID) {
		return ((EncDao) getBaseEncDao())
				.findCommInfoByCustomsDeclarationID(customsDeclarationID);
	}

	/**
	 * 查询报关单信息来自预录入号
	 * 
	 * @param request
	 *            请求控制
	 * @param preCustomsDeclarationCode
	 *            预录入号
	 * @param projectType
	 *            模块信息
	 * @return 与指定的预录入号匹配的报关单信息
	 */
	public List findCustomsDeclarationbyYlrh(Request request,
			String preCustomsDeclarationCode, int projectType) {
		return ((EncDao) getBaseEncDao()).findCustomsDeclarationbyYlrh(
				preCustomsDeclarationCode, projectType);
	}

	/**
	 * 根据清单类型和清单状态 查找报关清单.
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * @param billState
	 *            单据状态
	 * @return 与指定的单据类型和状态匹配的报关清单
	 */
	public List findApplyToCustomsBillListByTypeAndState(Request request,
			int impExpType, int billState) {
		return ((EncDao) getBaseEncDao())
				.findApplyToCustomsBillListByTypeAndState(impExpType, billState);
	}

	/**
	 * 查找进口类型退港申请书来自进出口单据Id
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBillId
	 *            进出口申请单id
	 * @return 与指定的进出口申请单据匹配的进口类型的退港申请书
	 */
	public ImpBackPortRequestBook findImpBackPortRequestBookById(
			Request request, String impExpRequestBillId) {
		return ((EncDao) getBaseEncDao())
				.findImpBackPortRequestBookById(impExpRequestBillId);
	}

	/**
	 * 查找出口类型退港申请书来自进出口单据Id
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpRequestBillId
	 *            进出口申请单id
	 * @return 与指定的进出口申请单id匹配的出口类型的退港申请书
	 */
	public ExpBackPortRequestBook findExpBackPortRequestBookById(
			Request request, String impExpRequestBillId) {
		return ((EncDao) getBaseEncDao())
				.findExpBackPortRequestBookById(impExpRequestBillId);
	}

	/**
	 * 保存进口类型的退港申请书
	 * 
	 * @param request
	 *            请求控制
	 * @param impBackPortRequestBook
	 *            进口类型的退港申请书
	 * @return 进口类型的退港申请书
	 */
	public ImpBackPortRequestBook saveImpBackPortRequestBook(Request request,
			ImpBackPortRequestBook impBackPortRequestBook) {
		((EncDao) getBaseEncDao())
				.saveImpBackPortRequestBook(impBackPortRequestBook);
		return impBackPortRequestBook;
	}

	/**
	 * 保存出口类型的退港申请书
	 * 
	 * @param request
	 *            请求控制
	 * @param expBackPortRequestBook
	 *            出口类型的退港申请书
	 * @return 出口类型的退港申请书
	 */
	public ExpBackPortRequestBook saveExpBackPortRequestBook(Request request,
			ExpBackPortRequestBook expBackPortRequestBook) {
		((EncDao) getBaseEncDao())
				.saveExpBackPortRequestBook(expBackPortRequestBook);
		return expBackPortRequestBook;
	}

	/**
	 * 查找进出口商品信息来自是否备案并以父类ID过滤
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            进出口申请单据id
	 * @return 与进出口申请单匹配且备案的进出口商品信息
	 */
	public List findImpExpCommodityInfoByPutOnRecord(Request request,
			String parentId) {
		return ((EncDao) getBaseEncDao())
				.findImpExpCommodityInfoByPutOnRecord(parentId);
	}

	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            进出口类型
	 * @return 取得临时的进出口申请单据信息
	 */
	// @AuthorityFunctionAnnotation(caption = "申请单转报关单", index = 5.1)
	public List findTempImpExpRequestBillByImpExpTypeToATC(Request request,
			int impExpType) {
		return ((EncLogic) getBaseEncLogic())
				.findTempImpExpRequestBillByImpExpTypeToATC(impExpType);
	}

	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 取得临时的进出口申请单据信息
	 */
	public List findTempImpExpRequestBillByImpExpTypeToATC(Request request,
			Date beginAvailabilityDate, Date endAvailabilityDate,
			String billNo, int impExpType) {
		return ((EncLogic) getBaseEncLogic())
				.findTempImpExpRequestBillByImpExpTypeToATC(
						beginAvailabilityDate, endAvailabilityDate, billNo,
						impExpType);
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
	public List findTempImpExpCommodityInfoByParent(Request request,
			List parentList) {
		return ((EncLogic) getBaseEncLogic())
				.findTempImpExpCommodityInfoByParent(parentList);
	}

	/**
	 * 获得商品信息加载子表的记录
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            进出口申请单据id
	 * @return 与指定的单据id匹配的进出口商品信息
	 */
	public List findImpExpCommodityInfo(Request request, String parentId) {
		return ((EncDao) getBaseEncDao()).findImpExpCommodityInfo(parentId);
	}

	/**
	 * 有数据转报关清单在进出口申请单中
	 * 
	 * @param request
	 *            请求控制
	 * @param c
	 *            进出口申请单据
	 * @return 有数据转报关清单的个数
	 */
	public boolean hasDataTransferApplyToCustomsBillByImpExpRequestBill(
			Request request, ImpExpRequestBill c) {
		return ((EncDao) getBaseEncDao())
				.hasDataTransferApplyToCustomsBillByImpExpRequestBill(c);
	}

	/**
	 * 生成进出口申请单据
	 * 
	 * @param request
	 *            请求控制
	 * @param tempBillMasterList
	 *            临时单据表头
	 * @param tempBillDetailList
	 *            临时单据明细
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-保存", index = 3.1)
	public void makeImpExpRequestBill(Request request, List tempBillMasterList,
			List tempBillDetailList) {
		((EncLogic) getBaseEncLogic()).makeImpExpRequestBill(
				tempBillMasterList, tempBillDetailList);
	}

	/**
	 * 进出口申请单转报关清单的检查--返回没有在电子帐册中备案的数据(成品)
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时进出口商品信息
	 * @param emsEdiMergerHead
	 *            经营范围归并表头
	 * @param type
	 *            物料类型
	 * @return 没有在电子帐册中备案的成品数据
	 */
	public List checkTempImpExpCommodityInfoByFinishProduct(Request request,
			List list, EmsEdiMergerHead emsEdiMergerHead, String type) {
		return ((EncLogic) getBaseEncLogic())
				.checkTempImpExpCommodityInfoByFinishProduct(list,
						emsEdiMergerHead, type);
	}

	/**
	 * 进出口申请单转报关清单的检查--返回没有在电子帐册中备案的数据(料件)
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时进出口商品信息
	 * @param emsH2k
	 *            电子帐册表头
	 * @param emsEdiMergerHead
	 *            经营范围归并关系表头
	 * @return 没有在电子帐册中备案的料件数据
	 */
	public List checkTempImpExpCommodityInfoByMateriel(Request request,
			List list, EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead) {
		return ((EncLogic) getBaseEncLogic())
				.checkTempImpExpCommodityInfoByMateriel(list, emsH2k,
						emsEdiMergerHead);
	}

	/**
	 * 判断单据号是否重复
	 * 
	 * @param request
	 *            请求控制
	 * @param data
	 *            进出口申请单
	 * @return 单据号如果重复返回true 否则返回false
	 */
	public boolean isReMerger(Request request, ImpExpRequestBill data) {
		return ((EncDao) getBaseEncDao()).isReMerger(data);
	}

	/**
	 * 获得最大的单据号来自进出口申请单表
	 * 
	 * @param company
	 *            公司名称
	 * @return 最大的单据号或0001
	 */

	public String getMaxBillNoByCompany(Company company) {
		return ((EncLogic) getBaseEncLogic()).getMaxBillNoByCompany(company);
	}

	/**
	 * 返回载货清单成品列表
	 * 
	 * @param request
	 *            请求控制
	 * @return 载货清单成品列表
	 */
	public List findMakeListExgList(Request request) {
		return ((EncDao) getBaseEncDao()).findMakeListExgList();
	}

	/**
	 * 得到当前物料不在电子帐册中备案的记录
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            临时进出口商品信息
	 * @param emsH2k
	 *            电子帐册表头
	 * @param emsEdiMergerHead
	 *            经营范围归并信息表头
	 * @param type
	 *            物料类型
	 * @return 没有在电子帐册中备案的物料记录
	 */
	public List checkTempImpExpCommodityInfoByFinishProduct2(Request request,
			List list, EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead,
			String type, boolean isbeian, String emsFrom) {
		return ((EncLogic) getBaseEncLogic())
				.checkTempImpExpCommodityInfoByFinishProduct2(list, emsH2k,
						emsEdiMergerHead, type, isbeian, emsFrom);
	}

	/**
	 * 申请单转清单时归并关系的来源选择
	 * 
	 * @param emsH2k
	 * @param emsEdiMergerHead
	 * @param type
	 * @param isbeian
	 * @param emsFrom
	 * @return
	 */
	public List findInnerMergeDataByPtNo(Request request, EmsHeadH2k emsH2k,
			EmsEdiMergerHead emsEdiMergerHead, String type, boolean isbeian,
			String emsFrom) {
		return ((EncLogic) getBaseEncLogic()).findInnerMergeDataByPtNo(emsH2k,
				emsEdiMergerHead, type, isbeian, emsFrom);
	}

	/**
	 * 进出口报关商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @param seqNum
	 *            序号
	 * @param customer
	 *            客户供应商
	 * @param ImpExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内符合条件的进出口报关商品信息
	 */
	public List findImpExpCommInfoList(Request request, boolean isImport,
			Integer seqNum, String customer, String ImpExpType, Date beginDate,
			Date endDate,String version, boolean isDeclaration, boolean isdept, List deptCode,
			int isEffect) {
		return ((EncLogic) getBaseEncLogic()).findImpExpCommInfoList(isImport,
				seqNum, customer, ImpExpType, beginDate, endDate,version,
				isDeclaration, isdept, deptCode, isEffect);
	}

	/**
	 * 特殊报关商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @param seqNum
	 *            序号
	 * @param customer
	 *            客户供应商
	 * @param ImpExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内符合条件的进出口报关商品信息
	 */
	public List findSpecialImpExpCommInfoList(Request request,
			boolean isImport, Integer seqNum, String customer,
			String ImpExpType, Date beginDate, Date endDate,
			boolean isDeclaration, boolean isdept, List deptCode, int isEffect) {
		return ((EncLogic) getBaseEncLogic()).findSpecialImpExpCommInfoList(
				isImport, seqNum, customer, ImpExpType, beginDate, endDate,
				isDeclaration, isdept, deptCode, isEffect);
	}

	/**
	 * 查询已报关的商品名称
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 已报关的商品名称
	 */
	public List findCustomsDeclarationCommInfo(Request request, boolean isImport) {
		return ((EncLogic) getBaseEncLogic())
				.findCustomsDeclarationCommInfo(isImport);
	}

	/**
	 * 查询已报关的商品名称
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 已报关的商品名称
	 */
	public List findCustomsDeclarationCommInfoNoSpec(Request request,
			boolean isImport) {
		return ((EncLogic) getBaseEncLogic())
				.findCustomsDeclarationCommInfoNoSpec(isImport);
	}

	/**
	 * 根据类型得到明细
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param iseffective
	 *            是否生效
	 * @return 在有效期内与指定的类型匹配生效的进出口商品明细
	 */
	public List findImpExpCommInfoPriceByType(Request request,
			boolean isProduct, Date beginDate, Date endDate, Boolean iseffective) {
		return ((EncLogic) getBaseEncLogic()).findImpExpCommInfoPriceByType(
				isProduct, beginDate, endDate, iseffective);
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * @return 以报关的客户
	 */
	public List findCustomsDeclarationCustomer(Request request, boolean isImport) {
		return ((EncLogic) getBaseEncLogic())
				.findCustomsDeclarationCustomer(isImport);
	}

	/**
	 * 查询所有申请单
	 * 
	 * @param request
	 *            请求控制
	 * @param state
	 *            清单状态
	 * @param type
	 *            进出口类型
	 * @param flat
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 与条件匹配的申请单
	 */
	public List findApplyBillList(Request request, Integer state, Integer type,
			Integer flat, Date beginDate, Date endDate) {
		return ((EncDao) getBaseEncDao()).findApplyBillList(state, type, flat,
				beginDate, endDate);
	}

	/**
	 * 进口料件使用情况
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            是否进口
	 * @param seqNum
	 *            料件序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内符合条件的进口料件情况
	 */
	public List findImpExpCommInfoUseForDept(Request request, boolean isImport,
			Integer seqNum, String customer, String IEType, Date beginDate,
			Date endDate, boolean isDeclaration, List deptCode, int isEffect) {
		return ((EncLogic) getBaseEncLogic()).findImpExpCommInfoUseForDept(
				isImport, seqNum, customer, IEType, beginDate, endDate,
				isDeclaration, deptCode, isEffect);
	}

	/**
	 * 不分事业部 进口料件情况统计表
	 */
	public List findImpExpCommInfoUseNoDept(Request request, boolean isImport,
			Integer seqNum, String customer, String IEType, Date beginDate,
			Date endDate, boolean isDeclaration, int isEffect,
			boolean isCountStoreNum) {
		return ((EncLogic) getBaseEncLogic()).findImpExpCommInfoUseNoDept(
				isImport, seqNum, customer, IEType, beginDate, endDate,
				isDeclaration, isEffect, isCountStoreNum);
	}

	/**
	 * 出口成品使用情况表
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            是否进口
	 * @param seqNum
	 *            成品序号
	 * @param customer
	 *            客户供应商
	 * @param IEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内符合条件的成品使用情况
	 */
	public List findImpExpCommInfoUseForExgForDept(Request request,
			boolean isImport, Integer seqNum, String customer, String IEType,
			Date beginDate, Date endDate, boolean isDeclaration,
			boolean isdept, List deptCode, int isEffect) {
		return ((EncLogic) getBaseEncLogic())
				.findImpExpCommInfoUseForExgForDept(isImport, seqNum, customer,
						IEType, beginDate, endDate, isDeclaration, isdept,
						deptCode, isEffect);
	}

	/**
	 * 不分事业部出口成品情况统计表
	 */
	public List findImpExpCommInfoUseForExgNoDept(Request request,
			boolean isImport, Integer seqNum, String customer, String IEType,
			Date beginDate, Date endDate, boolean isDeclaration, int isEffect) {
		return ((EncLogic) getBaseEncLogic())
				.findImpExpCommInfoUseForExgNoDept(isImport, seqNum, customer,
						IEType, beginDate, endDate, isDeclaration, isEffect);
	}

	/**
	 * 查询物料总量
	 * 
	 * @param request
	 *            请求控制
	 * @param tenSeqNum
	 *            十位备案序号
	 * @return 与指定的十位备案序号匹配的物料总量
	 */
	public List findMaterielAmountBySeqNum(Request request, Integer tenSeqNum) {
		return ((EncLogic) getBaseEncLogic())
				.findMaterielAmountBySeqNum(tenSeqNum);
	}

	/**
	 * 内部商品新增报关单表体
	 * 
	 * @param request
	 *            请求控制
	 * @param commInfo
	 *            报关单商品信息
	 * @param customsDeclaration
	 *            报关单内容
	 * @param exgbill
	 *            电子帐册成品表
	 * @param imgbill
	 *            电子帐册料件表
	 * @return 报关单表体内容
	 */
	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill(
			Request request, BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, EmsHeadH2kExg exgbill,
			EmsHeadH2kImg imgbill) {
		return ((EncLogic) getBaseEncLogic()).saveCustomsinfoFromBill(commInfo,
				customsDeclaration, exgbill, imgbill);
	}

	/**
	 * 保存报关商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            报关商品信息
	 */
	public void SaveCustomsFromMateriel(Request request, CustomsFromMateriel obj) {
		((EncDao) getBaseEncDao()).SaveCustomsFromMateriel(obj);
	}

	/**
	 * 取得成品版本号来自成品序号
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            成品序号
	 * @return 与序号匹配的成品的版本号
	 */
	public List getExgVersion(Request request, String seqNum) {
		return ((EncDao) getBaseEncDao()).getExgVersion(seqNum);
	}

	/**
	 * 保存报关清单归并后商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            归并后商品信息
	 */
	public AtcMergeAfterComInfo saveAtcMergeAfterComInfo(Request request,
			AtcMergeAfterComInfo obj) {
		((EncDao) getBaseEncDao()).saveAtcMergeAfterComInfo(obj);
		return obj;
	}

	/**
	 * 根据序号查找帐册料件
	 */
	public EmsHeadH2kImg getEmsHeadImg(Request request, Integer seqNum) {
		return ((EncDao) getBaseEncDao()).getEmsHeadImg(seqNum);
	}

	/**
	 * 根据序号查找帐册成品
	 */
	public EmsHeadH2kExg getEmsHeadExg(Request request, Integer seqNum) {
		return ((EncDao) getBaseEncDao()).getEmsHeadExg(seqNum);
	}

	/**
	 * 查询进出口报关单商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户供应商
	 * @param iEType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isProduct
	 *            是否是成品
	 * @param iseffective
	 *            是否生效
	 * @param commName
	 *            商品名称
	 * @param tradeMode
	 *            贸易方式
	 * @param customsNo
	 *            报关单号
	 * @return 符合指定条件的进出口报关单商品信息
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List findImpExpCommInfo(Request request, Integer seqNum,
			String customer, String iEType, Date beginDate, Date endDate,
			Date impexpBeginDate, Date impexpEndDate, boolean isProduct,
			Boolean iseffective, String commName, Trade tradeMode,
			String customsNo, String projectDeptName) {
		return this.encBcusLogic.findImpExpCommInfo(seqNum, customer, iEType,
				beginDate, endDate, impexpBeginDate, impexpEndDate, isProduct,
				iseffective, commName, tradeMode, customsNo, projectDeptName);
	}

	/**
	 * 帐册总体进出金额状况统计表
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内帐册总体进出金额
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List jisuanEmsImpExpMoneyTotal(Request request, Date beginDate,
			Date endDate, int isEffect) {
		return ((EncLogic) getBaseEncLogic()).jisuanEmsImpExpMoneyTotal(
				beginDate, endDate, isEffect);
	}

	/**
	 * 帐册进出口量平横状况
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内帐册进出口情况
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List jisuanEmsImpExpBalance(Request request, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration) {
		return ((EncLogic) getBaseEncLogic()).jisuanEmsImpExpBalance(beginDate,
				endDate, isEffect, isDeclaration);
	}

	/**
	 * 帐册出口成品状况表--------------------申报日期
	 * 
	 * @param request
	 *            请求控制
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 指定的日期内帐册出口成品状况
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List jisuanEmsExpExg(Request request, Date beginDate, Date endDate,
			Boolean isEffect) {
		return ((EncLogic) getBaseEncLogic()).jisuanEmsExpExg(beginDate,
				endDate, isEffect);
	}

	/**
	 * 料件进出平衡状况汇总表--------------------进出口日期
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param isEffect
	 *            是否生效
	 * @return 有效期内料件进出状况汇总
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List jisuanImgBalanceTotal(Request request, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration,boolean isJisuan) {
		return ((EncLogic) getBaseEncLogic()).calculateImgBalanceTotal(beginDate,
				endDate, isEffect, isDeclaration,isJisuan);
	}

	/**
	 * 更新清单
	 * 
	 * @param request
	 *            请求控制
	 * @param flat
	 *            进出口标志
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public void changeApplyBillState(Request request, Integer flat) {
		((EncLogic) getBaseEncLogic()).changeApplyBillState(flat);
	}

	/**
	 * 出口报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内出口报关清单的情况
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List jisuanExpCustomsBill(Request request, Date beginDate,
			Date endDate) {
		return ((EncLogic) getBaseEncLogic()).jisuanExpCustomsBill(beginDate,
				endDate);
	}

	/**
	 * 进口报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内进口报关清单的情况
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List jisuanImpCustomsBill(Request request, Date beginDate,
			Date endDate) {
		return ((EncLogic) getBaseEncLogic()).jisuanImpCustomsBill(beginDate,
				endDate);
	}

	/**
	 * 应退换成品
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内应退换成品的情况
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List jisuanExchangeExp(Request request, Integer impExpFlag,
			Date beginDate, Date endDate) {
		return ((EncLogic) getBaseEncLogic()).jisuanExchangeExp(impExpFlag,
				beginDate, endDate);
	}

	/**
	 * 应退换料件
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内与指定的进出口标志匹配的应退换料件的情况
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List jisuanExchangeImp(Request request, Integer impExpFlag,
			Date beginDate, Date endDate) {
		return ((EncLogic) getBaseEncLogic()).jisuanExchangeImp(impExpFlag,
				beginDate, endDate);
	}

	/**
	 * 查询物料内容来自集装箱
	 * 
	 * @param request
	 *            请求控制
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 生效的进出口申请单
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List findMaterielFromContainer(Request request, int index,
			int length, String property, Object value, Boolean isLike) {
		return ((EncDao) getBaseEncDao()).findMaterielFromContainer(index,
				length, property, value, isLike);
	}

	/**
	 * 进口料件单重差异稽核表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            进出口申请单
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内进口料件单重差异
	 */
	// @AuthorityFunctionAnnotation(caption = "电子帐册统计报表-报表查询打印查看", index = 20)
	public List jisuanImpUnitWeightCheck(Request request, List list,
			Date beginDate, Date endDate) {
		return ((EncLogic) getBaseEncLogic()).jisuanImpUnitWeightCheck(list,
				beginDate, endDate);
	}

	/**
	 * 取得帐册通关Logic层内容
	 * 
	 * @return 帐册通关Logic
	 */
	public EncBcusLogic getEncBcusLogic() {
		return encBcusLogic;
	}

	/**
	 * 设计帐册通关Logic内容
	 * 
	 * @param encBcusLogic
	 *            帐册通关Logic
	 */
	public void setEncBcusLogic(EncBcusLogic encBcusLogic) {
		this.encBcusLogic = encBcusLogic;
	}

	/**
	 * 通过帐册序号返回帐册信息
	 * 
	 * @param seqNum
	 *            序号
	 * @param type
	 *            物料类型
	 * @param emsNo
	 *            手册号
	 * @return 与指定的序号手册号匹配的帐册信息
	 */
	public Map<Integer, String> findImgExgBillBySeqNum(Request request,
			Integer seqNum, String materielType, String emsNo) {
		return ((EncLogic) getBaseEncLogic()).findImgExgBillBySeqNum(seqNum,
				materielType, emsNo);
	}

	/**
	 * 取得报关单的总金额 净重 毛重
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            报关单
	 * @return 报关单的总金额 净重 毛重
	 */
	public List getTotal(Request request, List list) {
		return this.encBcusLogic.getTotal(list);
	}

	/**
	 * 表头是否完全转报关单及所转清单
	 */
	public void changeImpexpbillByBillNo(Request request, List list,
			String ListNo) {
		((EncLogic) getBaseEncLogic()).changeImpexpbillByBillNo(list, ListNo);
	}

	/**
	 * 保存报关单
	 */
	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill2(
			Request request, BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, EmsHeadH2kExg exgbill,
			EmsHeadH2kImg imgbill) {
		return ((EncLogic) getBaseEncLogic()).saveCustomsinfoFromBill2(
				commInfo, customsDeclaration, exgbill, imgbill);
	}

	/**
	 * 保存报关单伪装箱号
	 */
	public List findCustomsDeclarationContainer(Request request,
			Date beginDate, Date endDate, int index, int length,
			String property, Object value, Boolean isLike, Hashtable billhs) {
		return encBcusLogic.findCustomsDeclarationContainer(beginDate, endDate,
				index, length, property, value, isLike, billhs);
	}

	/**
	 * 进口料件单重差异稽核表
	 */
	public List accountImpUnitWeightCheck(Request request, List list) {
		return this.encBcusLogic.accountImpUnitWeightCheck(list);
	}

	/**
	 * 内部商品转报关单删除
	 */
	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill3(
			Request request, BaseCustomsFromMateriel fromMateriel,
			BaseCustomsDeclaration customsDeclaration, EmsHeadH2kExg exgbill,
			EmsHeadH2kImg imgbill) {
		return this.encBcusLogic.saveCustomsinfoFromBill3(fromMateriel,
				customsDeclaration, exgbill, imgbill);
	}

	/**
	 * 删除清单归并后
	 */
	public void deleteAtcMergeAfterComInfo(Request request,
			AtcMergeAfterComInfo afterInfo) {
		this.encBcusLogic.deleteAtcMergeAfterComInfo(afterInfo);
	}

	/**
	 * 查找没有报关的归并后商品信息来自申请单
	 * 
	 * @param billList
	 *            申请单
	 * @return 已转报关单的归并后商品信息
	 */
	public List findAtcMergeAfterComInfoNotToCustoms(Request request,
			ApplyToCustomsBillList billList, Boolean isTransferCustomsBill) {
		return ((EncDao) getBaseEncDao()).findAtcMergeAfterComInfoNotToCustoms(
				billList, isTransferCustomsBill);
	}

	/**
	 * 申请单转报关单查看--删除
	 */
	public void deleteImpExpCommodityInfo(Request request,
			BaseCustomsDeclarationCommInfo info, ImpExpCommodityInfo t,
			boolean isDelete) {
		this.encBcusLogic.deleteImpExpCommodityInfo(info, t, isDelete);
	}

	/**
	 * 保存进出口申请单
	 */
	public ImpExpCommodityInfo saveImpExpComInfo(Request request,
			ImpExpCommodityInfo info) {
		((EncDao) getBaseEncDao()).saveImpExpComInfo(info);
		return info;
	}

	/**
	 * 根据成品序号及版本号，查找版本
	 */
	public EmsHeadH2kVersion findEmsHeadH2kVersion(Request request,
			Integer seqNum, Integer version) {
		return ((EncDao) getBaseEncDao())
				.findEmsHeadH2kVersion(seqNum, version);
	}

	/**
	 * 权限控制
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关单删单查询", index = 3.6)
	public void brownDeleteCustoms(Request request) {
	}

	/**
	 * 根据报送单信息查询归并前商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            归并后商品信息
	 * @return 与指定的归并后商品信息id匹配的归并前商品信息
	 */
	public List findAllAtcMergerBeforeComInfo(Request request,
			ApplyToCustomsBillList billList) {
		return ((EncLogic) getBaseEncLogic()).findAllAtcMergerBeforeComInfo(
				request, billList);
	}

	/**
	 * 计量单折算比例位资料
	 * 
	 * @param request
	 * @return
	 */
	public Map findAllUnitRateMap(Request request) {
		return ((EncLogic) getBaseEncLogic()).getUnitRateMap();
	}

	/**
	 * 新增清单归并前
	 * 
	 * @return
	 */
	public List newBillBefore(Request request, EmsHeadH2kImg img,
			EmsHeadH2kExg exg, Materiel mt, ApplyToCustomsBillList head) {
		return encBcusLogic.newBillBefore(img, exg, mt, head);
	}

	/**
	 * 料号级 料件耗用明细表
	 */
	public List getLjUseDetail(Request request, List billlist, Date beginDate,
			Date endDate, EmsEdiMergerHead head, String version,
			boolean isEmsOrBillList, String deptId) {
		return encBcusLogic.getLjUseDetail(billlist, beginDate, endDate, head,
				version, isEmsOrBillList, deptId);
	}

	//
	// /**
	// * 转清单
	// */
	// public void makeBillList(Request request, ApplyToCustomsBillList
	// billList,
	// List impexpbills, List afterinfoLists, int billListcount) {
	// ((EncLogic) getBaseEncLogic()).makeBillList(billList, impexpbills,
	// afterinfoLists, billListcount);
	// }

	/**
	 * 查找 报关清单
	 */
	public List findApplyToCustomsBillListByFlatAndState(Request request,
			int impExpFlag, int billState) {
		return ((EncDao) getBaseEncDao())
				.findApplyToCustomsBillListByFlatAndState(impExpFlag, billState);
	}

	/**
	 * 查找 报关清单表体－归并后
	 */
	public List findAtcMergeAfterComInfoByBillNo(Request request, String listNo) {
		return ((EncDao) getBaseEncDao())
				.findAtcMergeAfterComInfoByBillNo(listNo);
	}

	/**
	 * 查找 报关清单表体－归并前
	 */
	public List findAllAtcMergerBeforeComInfo(Request request, String billNo) {
		return ((EncDao) getBaseEncDao()).findAllAtcMergerBeforeComInfo(billNo);
	}

	/**
	 * 重新组合
	 */
	public void addBillToBill(Request request, AtcMergeAfterComInfo after,
			String billNo) {
		encBcusLogic.addBillToBill(after, billNo);
	}

	/**
	 * 重新排序
	 */
	public void billSort(Request request, String billNo) {
		encBcusLogic.billSort(billNo);
	}

	/**
	 * 保存进出口申请单
	 */
	public void saveImpExpRequestBills(Request request, List impExpRequestBills) {
		this.encBcusLogic.saveImpExpRequestBills(impExpRequestBills);
	}

	/**
	 * 根据料号查找进出口申请单
	 */
	public List findImpExpCommodityInfoByMaterielptNo(Request request,
			int billType, String billNo, String ptNo) {
		return ((EncDao) getBaseEncDao())
				.findImpExpCommodityInfoByMaterielptNo(billType, billNo, ptNo);
	}

	/**
	 * 检查是否为空
	 */
	public List checkIsNull(Request request, ApplyToCustomsBillList head) {
		return ((EncLogic) getBaseEncLogic()).checkIsNull(head);
	}

	/**
	 * 保存进出口申请单导入的顺序
	 */
	public void saveInputInExRequestBillOrder(Request request,
			InputInExRequestBillOrder order) {
		// ((EncDao) getBaseEncDao()).deleteInputInExRequestBillOrder();
		((EncDao) getBaseEncDao()).saveOrUpdate(order);

	}

	/**
	 * 查找进出口申请单导入的顺序
	 */
	public List findInputInExRequestBillOrder(Request request) {
		return ((EncDao) getBaseEncDao()).findInputInExRequestBillOrder();
	}

	/**
	 * 分事业部统计－总
	 */
	public List jisuanImgBalanceTotalForDept(Request request, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration,
			List<String> deptCode) {
		return ((EncLogic) getBaseEncLogic()).jisuanImgBalanceTotalForDept(
				beginDate, endDate, isEffect, isDeclaration, deptCode);
	}
//	/**
//	 * 统计报关单商品信息DJ
//	 */
//	public List findImpExpComminfoDj(Request request,
//			Integer type ,Trade tradeMode,Integer commName, Date beginDate, Date endDate ,boolean iseffective
//			, Map paraMap){
//		return ((EncLogic) getBaseEncLogic()).findImpExpComminfoDj(type ,tradeMode,commName,beginDate, endDate,iseffective,
//				paraMap);
//				
//	}
	/**
	 * 分事业部统计
	 */
	public List jisuanEmsImpExpBalanceForDept(Request request, Date beginDate,
			Date endDate, int isEffect, boolean isDeclaration, String deptCode) {
		return ((EncLogic) getBaseEncLogic()).jisuanEmsImpExpBalanceForDept(
				beginDate, endDate, isEffect, isDeclaration, deptCode);
	}

	/**
	 * 分事业部处理
	 */
	public List jisuanEmsExpExgForDept(Request request, Date beginDate,
			Date endDate, Boolean isEffect, String projectDept) {
		return ((EncLogic) getBaseEncLogic()).jisuanEmsExpExgForDept(beginDate,
				endDate, isEffect, projectDept);
	}

	/**
	 * 新增清单归并前来自内部商品
	 */
	public List newBillBeforeFromMateriel(Request request,
			AtcMergeBeforeComInfo before, EmsHeadH2kImg img, EmsHeadH2kExg exg,
			ApplyToCustomsBillList head) {
		return encBcusLogic.newBillBeforeFromMateriel(before, img, exg, head);
	}

	/**
	 * 保存申请单表头及表体,文本导入时使用.
	 */
	public List saveImpExpRequestBillAndImpExpCommodityInfo(Request request,
			List list, Boolean isMergeCommInfo,Boolean isSumMoney) {
		return encBcusLogic.saveImpExpRequestBillAndImpExpCommodityInfo(list,
				isMergeCommInfo,isSumMoney);
	}

	/**
	 * 出口成品耗用料件统计表
	 * 
	 * @param emsHeadH2k
	 * @return
	 */
	public List findExgUsedImgAmountInfo(Request request,
			EmsHeadH2k emsHeadH2k, Integer[] exgSeqNums, Date beginDate,
			Date endDate, Integer impExpType,boolean isPrintZeroTotalUsed,Boolean isEffective) {
		return this.encBcusLogic.findExgUsedImgAmountInfo(emsHeadH2k,
				exgSeqNums, beginDate, endDate, impExpType,isPrintZeroTotalUsed,isEffective);
	}

	/**
	 * 找出帐册最早的月结期间
	 * 
	 * @param emsHeadH2k
	 */
	public String findInitCustomsMonthTerm(Request request,
			EmsHeadH2k emsHeadH2k) {
		return this.encBcusLogic.findInitCustomsMonthTerm(emsHeadH2k);
	}

	/**
	 * 进口料件月结
	 * 
	 * @param emsHeadH2k
	 * @param preTerm
	 * @param currentTerm
	 */
	public void statCustomsInfoByMonth(Request request, EmsHeadH2k emsHeadH2k,
			String preTerm, String beginTerm, String endTerm) {
		this.encBcusLogic.statCustomsInfoByMonth(emsHeadH2k, preTerm,
				beginTerm, endTerm);
	}

	/**
	 * 查询帐册月结信息
	 * 
	 * @param term
	 * @return
	 */
	public List findCustomsMonthStatInfoByTerm(Request request, String emsNo,
			String term) {
		return ((EncDao) getBaseEncDao()).findCustomsMonthStatInfoByTerm(emsNo,
				term);
	}

	/**
	 * 查询帐册月结信息
	 * 
	 * @param term
	 * @return
	 */
	public List findCustomsMonthStatTerm(Request request, String emsNo) {
		return ((EncDao) getBaseEncDao()).findCustomsMonthStatTerm(emsNo);
	}

	/**
	 * 出口成品耗用料件统计月报表
	 * 
	 * @param emsHeadH2k
	 * @return
	 */
	public List findExgUsedImgMonthAmountInfo(Request request,
			EmsHeadH2k emsHeadH2k, Integer[] exgSeqNums, String term,
			Integer impExpType) {
		return this.encBcusLogic.findExgUsedImgMonthAmountInfo(emsHeadH2k,
				exgSeqNums, term, impExpType);
	}

	/**
	 * 获得当前所有申请单的单据号
	 * 
	 * @param request
	 *            请求控制
	 */
	public List findBillNoOfImpExpRequestBill(Request request) {
		return ((EncDao) getBaseEncDao()).findBillNoOfImpExpRequestBill();
	}

	/**
	 * 保存拆分后的清单
	 * 
	 * @param request
	 *            请求控制
	 * @param oldBill
	 *            被拆分清单
	 * @param list
	 *            拆分后的清单
	 */
	public boolean saveSplitedImpExpRequestBill(Request request,
			ImpExpRequestBill oldBill, Set set, List list) {
		return ((EncLogic) getBaseEncLogic()).saveSplitedImpExpRequestBill(
				oldBill, set, list);
	}

	/**
	 * 更新电子帐册中的申请单物料的版本号，为最大的版本号
	 * 
	 * @param request
	 */
	public void updateMaxVersion(Request request) {
		((EncLogic) getBaseEncLogic()).updateMaxVersion();
	}

	/**
	 * 获得报关清单导入的属性设置
	 * 
	 * @param request
	 *            请求控制
	 */
	public List findImportApplyToCustomsBillProperty(Request request) {
		return ((EncDao) getBaseEncDao()).findImportApplyCustomsProperty();
	}

	/**
	 * 保存报关清单导入的属性设置
	 * 
	 * @param request
	 *            请求控制
	 */
	public ImportApplyCustomsProperty saveImportApplyToCustomsBillProperty(
			Request request, ImportApplyCustomsProperty importApplyProperty) {
		((EncDao) getBaseEncDao())
				.saveImportApplyCustomsProperty(importApplyProperty);
		return importApplyProperty;
	}

	/**
	 * 检查导入到清单的数据
	 * 
	 * @param list
	 *            在文件获取数据后的List
	 * @param importApplyProperty
	 *            参数和列顺序
	 * @return
	 */
	public List checkImportFileData(Request request, List list,
			ImportApplyCustomsProperty importApplyProperty) {
		return encBcusLogic.checkImportFileData(list, importApplyProperty);
	}

	/**
	 * 导入文件来自文件
	 * 
	 * @param list
	 */
	public void importDataFromFile(Request request, List list,
			ImportApplyCustomsProperty importApplyProperty,
			boolean cbIsOverwrite) {
		encBcusLogic.importDataFromFile(list, importApplyProperty,
				cbIsOverwrite);
	}

	/**
	 * 导入前先检查数据是否合法
	 */
	public List tempDataCheck(Request request,
			ImportApplyCustomsProperty importApplyProperty, List list) {
		return encBcusLogic.tempDataCheck(importApplyProperty, list);
	}

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的料件
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 * @author sxk
	 */
	public List findEmsHeadH2kByPtNoFromMerger(Request request, String ptNo) {
		return ((EncDao) getBaseEncDao()).findEmsHeadH2kByPtNoFromMerger(ptNo);
	}

	/**
	 * 通过料号从电子账册管理中查找是否存在未修改的成品
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 * @author sxk
	 */
	public List findEmsHeadH2kExgByPtNoFromMerger(Request request, String ptNo) {
		return ((EncDao) getBaseEncDao())
				.findEmsHeadH2kExgByPtNoFromMerger(ptNo);
	}

	/**
	 * 通过料号从电子账册管理中查找是否存在BOM未修改的成品
	 * 
	 * @param request
	 * @param ptNo
	 * @return
	 * @author sxk
	 */
	public List findEmsHeadH2kBomByPtNoFromMerger(Request request, String ptNo,
			Integer version) {
		System.out.println("1DAOlimian");
		return ((EncDao) getBaseEncDao()).findEmsHeadH2kBomByPtNoFromMerger(
				ptNo, version);
	}

	/**
	 * 断判导入资料中清单编号是否重复
	 */
	public boolean checkBillListNoOverlap(Request request, String value) {
		return encBcusLogic.checkBillListNoOverlap(value);
	}

	/**
	 * 根据进出类型、单据类型,查找已转清单的申请单头资料
	 * 
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @return
	 */
	public List findImpExpRequestBillByPara(Request request,
			String impExpFlagCode, String billTypeCode) {
		return ((EncDao) getBaseEncDao()).findImpExpRequestBillByPara(
				impExpFlagCode, billTypeCode);
	}

	/**
	 * 根据进出类型、单据类型,查找客户/供应商/合作伙伴资料
	 * 
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @return
	 */
	public List findScmCocsByPara(Request request, String impExpFlagCode,
			String billTypeCode) {
		return ((EncDao) getBaseEncDao()).findScmCocsByPara(impExpFlagCode,
				billTypeCode);
	}

	/**
	 * 根据进出类型、单据类型,查找申请单表体
	 * 
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @param impExpRequestBill
	 *            申请单单据号
	 * @param bomNo
	 *            工厂料号
	 * @param scmCoc
	 *            客户供应商
	 * @param beginDate
	 *            开始生效日期
	 * @param endDate
	 *            结束生效日期
	 * @return
	 */
	public List findImpExpCommodityInfoBySomePara(Request request,
			String impExpFlagCode, String billTypeCode,
			ImpExpRequestBill impExpRequestBill, String bomNo, ScmCoc scmCoc,
			Date beginDate, Date endDate) {
		return ((EncDao) getBaseEncDao()).findImpExpCommodityInfoBySomePara(
				impExpFlagCode, billTypeCode, impExpRequestBill, bomNo, scmCoc,
				beginDate, endDate);
	}

	/**
	 * 根据进出类型、单据类型,查找表体名称、表体料号的申请单表体
	 * 
	 * @param impExpFlagCode
	 *            进出类型 0为出口、2为进口
	 * @param billTypeCode
	 *            单据类型
	 * @param impExpRequestBill
	 *            申请单单据号
	 * @param bomNo
	 *            工厂料号
	 * @param scmCoc
	 *            客户供应商
	 * @param beginDate
	 *            开始生效日期
	 * @param endDate
	 *            结束生效日期
	 * @return
	 */
	public List getDistinctImpExpCommodityInfoByName(Request request,
			String impExpFlagCode, String billTypeCode,
			ImpExpRequestBill impExpRequestBill, String bomNo, ScmCoc scmCoc,
			Date beginDate, Date endDate) {
		return ((EncDao) getBaseEncDao()).getDistinctImpExpCommodityInfoByName(
				impExpFlagCode, billTypeCode, impExpRequestBill, bomNo, scmCoc,
				beginDate, endDate);
	}

	/**
	 * 根据条件获取大小清单资料
	 * 
	 * @param request
	 * @param impExpFlagCode
	 *            进出类型
	 * @param billTypeCode
	 *            单据类型
	 * @param impExpRequestBill
	 *            单据号
	 * @param bomNo
	 *            工厂料号
	 * @param scmCoc
	 *            客户供应商
	 * @param beginDate
	 *            生效开始日期
	 * @param endDate
	 *            生效结束日期
	 * @return
	 */
	public List getRequestTOApplyTOCustomsReport(Request request,
			String impExpFlagCode, String billTypeCode,
			ImpExpRequestBill impExpRequestBill, String bomNo, ScmCoc scmCoc,
			Date beginDate, Date endDate, boolean IsListUnitNo, String customDeclareCode, String commSerial) {
		return encBcusLogic.getRequestTOApplyTOCustomsReport(impExpFlagCode,
				billTypeCode, impExpRequestBill, bomNo, scmCoc, beginDate,
				endDate, IsListUnitNo,customDeclareCode,commSerial);
	}

	/**
	 * 检查清单
	 * 
	 * @param request
	 * @param list
	 *            清单表头list
	 * @return
	 */
	public List applyToCustomsCheckup(Request request, List list) {
		return encBcusLogic.applyToCustomsCheckup(list);
	}

	/**
	 * 申请单转报关单
	 */
	public CustomsDeclaration makeBilllistsToCustomDeclaretions(
			Request request, ApplyToCustomsBillList billList,
			MakeCusomsDeclarationParam para, CustomsDeclaration customs,
			List billDetail, boolean arrayType) {
		return ((EncLogic) getBaseEncLogic())
				.makeBilllistsToCustomDeclaretions(billList, customs, para,
						billDetail, arrayType);
	}

	/**
	 * 根据清单表头查找表体
	 */
	public List findAtcMergeAfterComInfoByParents(Request request, List listNo) {
		return ((EncDao) getBaseEncDao())
				.findAtcMergeAfterComInfoByParents(listNo);
	}

	/**
	 * 根据单据类型、录入日期查找申请单
	 * 
	 * @param type
	 *            单据类型
	 * @param beginImputDate
	 *            开始录入日期
	 * @param endImputDate
	 *            结束录入日期
	 * @return 与条件匹配的所有进出口申请单
	 */
	//@AuthorityFunctionAnnotation(caption = "电子帐册通关--进出口申请单-浏览", index = 3.1)
	public List findImpExpRequestBillByTypeAndImputDate(Request request,
			int type, Date beginImputDate, Date endImputDate) {
		return ((EncDao) getBaseEncDao())
				.findImpExpRequestBillByTypeAndImputDate(type, beginImputDate,
						endImputDate);
	}

	/**
	 * 转抄申请单资料
	 * 
	 * @param request
	 * @param impExpRequestBill
	 *            要转抄的申请单表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @param billNo
	 *            单据号
	 * @return 新的申请单表头
	 */
	public ImpExpRequestBill copyImpExpRequestBillAndCommodityInfo(
			Request request, ImpExpRequestBill impExpRequestBill,
			Boolean copyInfo, String billNo) {
		return encBcusLogic.copyImpExpRequestBillAndCommodityInfo(
				impExpRequestBill, copyInfo, billNo);
	}

	/**
	 * 转抄清单资料
	 * 
	 * @param request
	 * @param applyToCustomsBillList
	 *            要转抄的清单表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @return 新的清单表头
	 */
	public ApplyToCustomsBillList copyApplyToCustomsBillListAndCommInfo(
			Request request, ApplyToCustomsBillList applyToCustomsBillList,
			Boolean copyInfo) {
		return encBcusLogic.copyApplyToCustomsBillListAndCommInfo(
				applyToCustomsBillList, copyInfo);
	}

	/**
	 * 获得各个模块的归并资料
	 * 
	 * @param projectType
	 *            模块信息
	 * @return 查询归并中的设备资料
	 */
	public List getInnerDateForSpeFix(Request request, int projectType,
			int index, int length, String property, Object value,
			boolean isLike, boolean isImport) {
		return ((EncLogic) getBaseEncLogic()).getInnerDateForSpeFix(
				projectType, index, length, property, value, isLike, isImport);
	}

	/**
	 * 进出口包装统计
	 * 
	 * @param request
	 * @param wrap
	 *            包装种类
	 * @param impExpFlag
	 *            进出口标志
	 * @param effective
	 *            是否生效
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public List findWrapStat(Request request, Wrap wrap, Integer impExpFlag,
			Boolean effective, Date beginDate, Date endDate) {
		return encBcusLogic.findWrapStat(wrap, impExpFlag, effective,
				beginDate, endDate);
	}

	/**
	 * 查找清单商品
	 */
	public AtcMergeAfterComInfo findMakeListToCustomsBycustomsInfo(
			Request request, CustomsDeclarationCommInfo customsInfo) {
		return ((EncLogic) getBaseEncLogic())
				.findMakeListToCustomsBycustomsInfo(customsInfo);
	}

	/**
	 * 查找清单商品总项数
	 */
	public Integer findMakeBcsCustomsDeclarationCountByListId(Request request,
			List list) {
		return ((EncDao) getBaseEncDao())
				.findMakeBcsCustomsDeclarationCountByListId(list);
	}

	/**
	 * 查找归并关系所有版本号
	 */
	public List findEmsEdiExgBomVersion(Request request) {
		return ((EncDao) getBaseEncDao()).findEmsEdiExgBomVersion();
	}

	/**
	 * 查找清单
	 */
	public List getApplyReportTOCustomsReport(Request request,
			String impExpFlagCode, String billTypeCode, Date beginDate,
			Date endDate, boolean IsListUnitNo) {
		return encBcusLogic.getApplyReportTOCustomsReport(impExpFlagCode,
				billTypeCode, beginDate, endDate, IsListUnitNo);
	}

	public XinpuReport getXinpuReport() {
		return ((EncDao) getBaseEncDao()).getXinpuReport();
	}

	public void saveXinpuReport(XinpuReport xinpuReport) {
		((EncDao) getBaseEncDao()).saveXinpuReport(xinpuReport);
	}

	public BcusContractExeInfo findBcusEmsHeadH2kExeInfo(Request request,
			boolean isMaterial, int impExpType, String tradeCode,
			EmsHeadH2k emsHead, String seqNum) {
		return this.getEncLogic().findEmsHeadH2kExeInfo(isMaterial, impExpType,
				tradeCode, emsHead, Integer.valueOf(seqNum));
	}

	/**
	 * 统计电子帐册财务报表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param mark
	 * @return
	 */
	public List calcCustomsDeclarationCommInfoAsFinancial(Request request,
			Date beginDate, Date endDate, String mark) {
		return this.getEncLogic().calcCustomsDeclarationCommInfoAsFinancial(
				beginDate, endDate, mark);
	}

	/**
	 * 取得大批量修改商品编的备案资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List getBatchUpdateComplex(Request request, String emsType,
			Boolean isMaterial) {
		return encBcusLogic.getBatchUpdateComplex(emsType, isMaterial);
	}

	/**
	 * 更新电子帐册的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllEmsComplex(Request request, Boolean isMaterial,
			Complex complex, Integer seqNum, boolean isSendData) {
		this.encBcusLogic.updateAllEmsComplex(isMaterial, complex, seqNum,
				isSendData);
	}

	/**
	 * 更新所有归并关系的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllEdiMergerAfterComplex(Request request,
			Boolean isMaterial, Complex complex, Complex old, Integer seqNum,
			boolean isSendData) {
		encBcusLogic.updateAllEdiMergerAfterComplex(isMaterial, complex, old,
				seqNum, isSendData);
	}

	/**
	 * 更新所有内部归并的商品编码
	 * 
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllInnerMergeDataComplex(Request request,
			Boolean isMaterial, Complex complex, Integer seqNum) {
		encBcusLogic
				.updateAllInnerMergeDataComplex(isMaterial, complex, seqNum);
	}

	public ImpExpRequestBill getImpExpRequestBill(Request request, String billNo) {
		return ((EncDao) this.getBaseEncDao()).getImpExpRequestBill(billNo);
	}
	

	/**
	 * 海关申报
	 * 
	 * @param request
	 *            请求控制
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册通关--报关清单-海关申报", index = 3.2)
	public void customDeclare(Request request) {
		
	}

	/**
	 * 根据已转清单号码查找进出口申请单
	 */
	public List findImpExpRequestBillByAllBillNo(Request request, String allBillNo,
			Integer billType){
		return  ((EncDao) this.getBaseEncDao()).findImpExpRequestBillByAllBillNo(allBillNo,
				billType);
	}
	/**
	 * 根据报关单表头ImpExpRequestBill查找清单中商品信息
	 * @return
	 */
	public List findTempImpExpCommodityInfoByBillListId(Request request,ImpExpRequestBill impExpRequestBill,
			Integer billType){
		return  ((EncDao) this.getBaseEncDao()).findTempImpExpCommodityInfoByBillListId(impExpRequestBill,
				billType);
	}

	/**
	 * 根据报关单物料信息获取进出口申请单表体资料
	 * @param request
	 * @param customsDeclarationCommInfo
	 * @param billType 单据类型
	 * @return
	 */
	public List findTempImpExpCommodityInfoBycustomsInfo(Request request,
			 CustomsDeclarationCommInfo customsDeclarationCommInfo,Integer billType) {
		return encBcusLogic.findTempImpExpCommodityInfoBycustomsInfo(customsDeclarationCommInfo,billType);
		
	}
	
	/**
	 * 根据报关单物料信息获取进出口申请单表体资料
	 * @param request
	 * @param customsDeclarationCommInfo
	 * @param billType 单据类型
	 * @return
	 */
	public List findTempImpExpCommodityInfoBycustomsInfo(Request request,
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo,Integer billType) {
		return encBcusLogic.findTempImpExpCommodityInfoBycustomsInfo(bcsCustomsDeclarationCommInfo,billType);
		
	}
	/**
	 * 根据报关单物料信息获得归并前商品信息
	 * 
	 * @param request
	 * @param customsDeclarationCommInfo
	 * @param billType
	 *            单据类型
	 * @return
	 */
	public List findAtcMergeBeforeComInfoByCustomsDeclarationCommInfo(
			Request request,
			CustomsDeclarationCommInfo customsDeclarationCommInfo,
			Integer billType) {
		return encBcusLogic
				.findAtcMergeBeforeComInfoByCustomsDeclarationCommInfo(
						customsDeclarationCommInfo, billType);
	}

	
	/**
	 * 根据条件（进出口类型，账册号，开始时间，结束时间）取得报关单BY 申报日期
	 * @param request
	 * 
	 * @param impExpFlag
	 *            进出口标志，进口为true，出口为false。
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @param property
	 *            自选条件名
	 * @param value
	 *            自选条件值
	 * @return 在有效期内与指定的手册号匹配的进口报关单
	 */
	public List findCustomsDeclaration(Request request, boolean impExpFlag, String emsNo,
			Date beginDate, Date endDate, String property, Object value) {
		return ((EncDao) getBaseEncDao()).findCustomsDeclaration(impExpFlag, emsNo, beginDate, endDate, property, value);
	}
	/**
	 * 更新电子帐册中的报关单商品的版本号，为最大的版本号,如果没有最大版本号，默认为0
	 *  @param baseCustomsDeclaration 报关单表头
	 */
	public void updateMaxVersionCustomsDeclarationCommInfo(Request request,BaseCustomsDeclaration customsDeclaration){
		((EncLogic) getBaseEncLogic()).updateMaxVersionCustomsDeclarationCommInfo(customsDeclaration);
	}
	
	/**
	 * 生成报关清单(广达订制)
	 * 
	 * @param request
	 * @param dataSource
	 * @param isMaterial
	 * @param param
	 * @return
	 */
	public List makeApplyToCustomsRequestBillListForGUANGDA(Request request,
			List dataSource, boolean isMaterial,
			MakeCusomsDeclarationParam param) {

		return ((EncLogic) getBaseEncLogic())
				.makeApplyToCustomsRequestBillListForGUANGDA(dataSource,
						isMaterial, param);
	}
	
	/**
	 * 申清单---清单--生成报关单 生成清单(广达订制) 
	 * 
	 * @param request
	 * @param impexpbills
	 * @param afterinfoLists
	 * @param param
	 * @param customs
	 *            报关单表头
	 * @return
	 */
	public List makeCusomsDeclarationFromBillListGUANGDA(Request request,List impexpbills,
			List afterinfoLists, MakeCusomsDeclarationParam param,
			CustomsDeclaration customs) {
		return ((EncLogic) getBaseEncLogic()).makeCusomsDeclarationFromBillListGUANGDA(impexpbills, afterinfoLists, param, customs);
	}
	
	/**
	 * 根据提运单号获取司机表中的信息
	 * @param billOfLading
	 * @return
	 */
	public List findMotorCode(String billOfLading){
		return ((EncDao) getBaseEncDao()).findMotorCode(billOfLading);
	}
	
	public List findImgConsumption(Request request,Date beginDate,Date endDate,ProjectDept projectDept,Integer[] imgSeqNums,Integer impExpType,Boolean isTotal){
		return encBcusLogic.findImgConsumption(beginDate,endDate,projectDept,imgSeqNums,impExpType,isTotal);
	}
}
