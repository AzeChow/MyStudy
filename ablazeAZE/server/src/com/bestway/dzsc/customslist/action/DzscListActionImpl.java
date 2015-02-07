package com.bestway.dzsc.customslist.action;

import java.util.Date;
import java.util.List;

import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.customslist.dao.DzscListDao;
import com.bestway.dzsc.customslist.entity.DzscBillListAfterCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.customslist.logic.DzscListLogic;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.dzsc.customslist.action.DzscListActionImpl
 * 
 * @author refdom
 */
//通关--报关清单
@AuthorityClassAnnotation(caption = "电子手册", index = 7)
public class DzscListActionImpl extends BaseActionImpl implements
		DzscListAction {

	private DzscListDao dzscListDao = null;

	private DzscListLogic dzscListLogic = null;

	/**
	 * 获取dzscListLogic
	 * 
	 * @return dzscListLogic
	 */
	public DzscListLogic getDzscListLogic() {
		return dzscListLogic;
	}

	/**
	 * 设置dzscListLogic
	 * 
	 * @param dzscListLogic
	 */
	public void setDzscListLogic(DzscListLogic dzscListLogic) {
		this.dzscListLogic = dzscListLogic;
	}

	/**
	 * 获取
	 * 
	 * @return dzscListDao
	 */
	public DzscListDao getDzscListDao() {
		return dzscListDao;
	}

	/**
	 * 设置dzscListDao
	 * 
	 * @param dzscListDao
	 */
	public void setDzscListDao(DzscListDao dzscListDao) {
		this.dzscListDao = dzscListDao;
	}

	/**
	 * 报关申请单转报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是TempImpExpCommodityInfo型，存放临时的申请单物料资料
	 * @param dzscEmsPorHead
	 *            通关备案表头
	 * @return List 是TempImpExpCommodityInfo型，在商品归并里没变更或在通关备案里存在的物料资料
	 */
	public List checkRequestBillForBillList(Request request, List list,
			DzscEmsPorHead dzscEmsPorHead) {
		return this.getDzscListLogic().checkRequestBillForBillList(list,
				dzscEmsPorHead);
	}

	/**
	 * 进出口申请单--->报关清单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isImportGoods
	 * 是进货还是出货(出口还是进口) 将申请单转清单数据汇总后
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @param dataSource
	 *            临时的申请单物料资料
	 * @param isImg
	 *            是进货还是出货(出口还是进口)，true表示进口
	 * @param isNewRecord
	 *            是代表生成新的清单还是追加到原有的清单，true表示新增
	 * @return list.get(0)==清单单头 list.get(1)==申请单头（修改后）
	 */
	public List makeApplyToCustomsRequestBillList(Request request,
			DzscCustomsBillList applyToCustomsBillList, List dataSource,
			boolean isImg, boolean isNewRecord, boolean isMergeData) {
		return this.getDzscListLogic().makeApplyToCustomsRequestBillList(
				applyToCustomsBillList, dataSource, isImg, isNewRecord,
				isMergeData);
	}

	/**
	 * 根据清单ID查找报关清单表头.
	 * 
	 * @param impExpType
	 *            清单类型
	 * @param billState
	 *            清单状态
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public DzscCustomsBillList findDzscCustomsBillListById(Request request,
			String id) {
		return this.dzscListDao.findDzscCustomsBillListById(id);
	}

	/**
	 * 根据清单类型和清单状态 查找报关清单表头.
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpType
	 *            清单类型
	 * @param billState
	 *            清单状态
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findDzscCustomsBillListByTypeAndState(Request request,
			int impExpType, int billState) {
		return this.dzscListDao.findDzscCustomsBillListByTypeAndState(
				impExpType, billState);
	}

	/**
	 * 查找所有进出报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	//@AuthorityFunctionAnnotation(caption = "手册报关清单--浏览", index = 7)
	public List findApplyToCustomsBillList(Request request) {
		return getDzscListLogic().findApplyToCustomsBillList();
	}

	/**
	 * 根据清单类型查找报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHead
	 *            手册表头
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	//@AuthorityFunctionAnnotation(caption = "手册报关清单--浏览", index = 7)
	public List findApplyToCustomsBillListByType(Request request,
			int impExpFlag, BaseEmsHead emsHead, Date beginDate, Date endDate) {
		return getDzscListLogic().findApplyToCustomsBillListByType(impExpFlag,
				emsHead, beginDate, endDate);
	}

	/**
	 * 根据清单进出口标志和手册号查找报关清单表头.
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHead
	 *            手册表头
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	public List findDzscCustomsBillList(Request request, int impExpFlag,
			BaseEmsHead emsHead, String listNo) {
		return this.dzscListDao.findDzscCustomsBillList(impExpFlag, emsHead,
				listNo);
	}

	/**
	 * 查找未转报关单的报关清单.
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	//@AuthorityFunctionAnnotation(caption = "手册报关清单--浏览", index = 7)
	public List findBillListNoMakeCustomsDeclaration(Request request,
			String emsNo, Integer impExpFlag) {
		return getDzscListDao().findBillListNoMakeCustomsDeclaration(emsNo,
				impExpFlag);
	}

	/**
	 * 删除报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册报关清单--删除", index = 7)
	public void deleteApplyToCustomsBillList(Request request,
			DzscCustomsBillList applyToCustomsBillList) {
		getDzscListLogic().deleteApplyToCustomsBillList(applyToCustomsBillList);
	}

	/**
	 * 保存报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册报关清单--保存", index = 7)
	public DzscCustomsBillList saveApplyToCustomsBillList(Request request,
			DzscCustomsBillList applyToCustomsBillList) {
		this.dzscListLogic.saveApplyToCustomsBillList(applyToCustomsBillList);
		return applyToCustomsBillList;
	}

	/**
	 * 申报保管清单
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscCustomsBillList
	 *            报关清单表头
	 */
	public DeclareFileInfo applyDzscCustomsBillList(Request request,
			DzscCustomsBillList dzscCustomsBillList) {
		return this.dzscListLogic.applyDzscCustomsBillList(dzscCustomsBillList,
				request.getTaskId());
	}

	/**
	 * 报关清单回执处理
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscCustomsBillList
	 *            报关清单表头
	 */
	public String processDzscCustomsBillList(Request request,
			DzscCustomsBillList dzscCustomsBillList, List lsReturnFile) {
		return this.dzscListLogic
				.processDzscCustomsBillList(dzscCustomsBillList,lsReturnFile);
	}

	/**
	 * 根据归并后商品信息查询归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeAfterComInfo
	 *            清单归并后商品信息
	 * @return List 是DzscBillListBeforeCommInfo型，清单归并前商品信息
	 */
	public List findAtcMergeBeforeComInfoByAfterID(Request request,
			DzscBillListAfterCommInfo atcMergeAfterComInfo) {
		return getDzscListLogic().findAtcMergeBeforeComInfoByAfterID(
				atcMergeAfterComInfo);
	}

	/**
	 * 根据清单编号查询归并后商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @return List 是DzscBillListAfterCommInfo型，清单归并后商品信息
	 */
	public List findAtcMergeAfterComInfoByListID(Request request,
			DzscCustomsBillList applyToCustomsBillList) {
		return getDzscListLogic().findAtcMergeAfterComInfoByListID(
				applyToCustomsBillList);
	}

	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeBeforeComInfo
	 *            清单归并前商品信息
	 * @return DzscCustomsBillList 报关清单表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册报关清单--删除", index = 7)
	public DzscCustomsBillList deleteAtcMergeBeforeComInfo(Request request,
			DzscBillListBeforeCommInfo atcMergeBeforeComInfo) {
		return getDzscListLogic().deleteAtcMergeBeforeComInfo(
				atcMergeBeforeComInfo);
	}

	/**
	 * 删除报关清单归并前商品信息(多笔)
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscBillListBeforeCommInfo型，报关清单归并前商品信息
	 * @return DzscCustomsBillList 报关清单表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册报关清单--删除", index = 7)
	public DzscCustomsBillList deleteAtcMergeBeforeComInfo(Request request,
			List list) {
		return getDzscListLogic().deleteAtcMergeBeforeComInfo(list);
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeBeforeComInfo
	 *            报关清单归并前商品信息
	 * @return atcMergeBeforeComInfo 报关清单归并前商品信息
	 */
	@AuthorityFunctionAnnotation(caption = "手册报关清单--保存", index = 7)
	public DzscBillListBeforeCommInfo saveAtcMergeBeforeComInfo(
			Request request, DzscBillListBeforeCommInfo atcMergeBeforeComInfo) {
		getDzscListLogic().saveAtcMergeBeforeComInfo(atcMergeBeforeComInfo);
		return atcMergeBeforeComInfo;
	}

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param declaredDataList
	 *            是TempBillListCommInfo型，存放临时的报关清单商品信息
	 * @param billList
	 *            报关清单表头
	 * @return billList 报关清单表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册报关清单--保存", index = 7)
	public DzscCustomsBillList saveAtcMergeBeforeComInfo(Request request,
			List declaredDataList, DzscCustomsBillList billList) {
		getDzscListLogic()
				.saveAtcMergeBeforeComInfo(declaredDataList, billList);
		return billList;
	}

	/**
	 * 获取当天的最大清单号码
	 * 
	 * @param request
	 *            请求控制
	 * @return String 当天的最大清单号码
	 */
	public String getApplyToCustomsBillListMaxNo(Request request) {
		return getDzscListLogic().getApplyToCustomsBillListMaxNo();
	}

	/**
	 * 在根据报关清单自动生成报关单的时候，检查报关清单的数据是否符合条件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 * @return int 如果成功返回0
	 */
	public int checkBillListForMakeCustomsDeclaration(Request request, List list) {
		return getDzscListLogic().checkBillListForMakeCustomsDeclaration(list);
	}

	/**
	 * 报关清单自动转报关单及其商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param billLists
	 *            是DzscCustomsBillList型，报关清单表头
	 * @param param
	 *            报关清单自动生成报关单时的参数
	 * @return List 存放了报关单的流水号
	 */
	@AuthorityFunctionAnnotation(caption = "手册报关清单--保存报关单", index = 7)
	public List makeCusomsDeclarationFromBillList(Request request,
			List billLists, MakeCusomsDeclarationParam param) {
		return getDzscListLogic().makeCusomsDeclarationFromBillList(billLists,
				param);
	}

	/**
	 * 取得临时申报商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param billList
	 *            报关清单表头
	 * @param materielProductFlag
	 *            物料标识
	 * @return List 存储了TempBillListCommInfo数据
	 */
	public List getTempDeclaredCommInfo(Request request,
			DzscCustomsBillList billList, Integer materielProductFlag) {
		return getDzscListLogic().getTempDeclaredCommInfo(billList,
				materielProductFlag);
	}

	/**
	 * 根据报关清单取得报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param listNo
	 *            报关清单号码
	 * @return List 是DzscCustomsDeclaration型，报关单头
	 */
	public List findCustomsDeclarationByBillList(Request request, String listNo) {
		return this.dzscListDao.findCustomsDeclarationByBillList(listNo);
	}

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPBillListMessage(Request request,
			String messageFileName, DzscCustomsBillList dzscCustomsBillList) {
		this.dzscListLogic.exportQPBillListMessage(request.getTaskId(),
				messageFileName, dzscCustomsBillList);
	}

	public List findAllAtcMergerBeforeComInfo(Request request,
			DzscCustomsBillList dzscCustomsBillList) {
		return this.dzscListLogic.findAllAtcMergerBeforeComInfo(dzscCustomsBillList);
	}
}