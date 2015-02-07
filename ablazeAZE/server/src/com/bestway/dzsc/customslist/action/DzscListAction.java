package com.bestway.dzsc.customslist.action;

import java.util.Date;
import java.util.List;

import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.customslist.entity.DzscBillListAfterCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.dzsc.customslist.action.DzscListAction
 * 
 * @author refdom
 */

public interface DzscListAction {

	/**
	 * 根据清单ID查找报关清单表头.
	 * 
	 * @param impExpType
	 *            清单类型
	 * @param billState
	 *            清单状态
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	DzscCustomsBillList findDzscCustomsBillListById(Request request, String id);

	/**
	 * 查找所有归并前的商品
	 */
	public List findAllAtcMergerBeforeComInfo(Request request,
			DzscCustomsBillList dzscCustomsBillList);
	
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
	List findDzscCustomsBillListByTypeAndState(Request request, int impExpType,
			int billState);

	/**
	 * 根据清单进出口标志和手册号查找报关清单表头.
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHead
	 *            手册表头
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	List findDzscCustomsBillList(Request request, int impExpFlag,
			BaseEmsHead emsHead, String listNo);

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
	List checkRequestBillForBillList(Request request, List list,
			DzscEmsPorHead dzscEmsPorHead);

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
	 * @param isImportGoods
	 *            是进货还是出货(出口还是进口)，true表示进口
	 * @param isNewRecord
	 *            是代表生成新的清单还是追加到原有的清单，true表示新增
	 * @return list.get(0)==清单单头 list.get(1)==申请单头（修改后）
	 */
	List makeApplyToCustomsRequestBillList(Request request,
			DzscCustomsBillList applyToCustomsBillList, List dataSource,
			boolean isImg, boolean isNewRecord, boolean isMergeData);

	/**
	 * 查找所有进出报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscCustomsBillList型，报关清单表头
	 */
	List findApplyToCustomsBillList(Request request);

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
	List findApplyToCustomsBillListByType(Request request, int impExpFlag,
			BaseEmsHead emsHead, Date beginDate, Date endDate);

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
	List findBillListNoMakeCustomsDeclaration(Request request, String emsNo,
			Integer impExpFlag);

	/**
	 * 删除报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 */
	void deleteApplyToCustomsBillList(Request request,
			DzscCustomsBillList applyToCustomsBillList);

	/**
	 * 保存报关清单
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 */
	DzscCustomsBillList saveApplyToCustomsBillList(Request request,
			DzscCustomsBillList applyToCustomsBillList);

	/**
	 * 申报保管清单
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscCustomsBillList
	 *            报关清单表头
	 */
	DeclareFileInfo applyDzscCustomsBillList(Request request,
			DzscCustomsBillList dzscCustomsBillList);

	/**
	 * 报关清单回执处理
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscCustomsBillList
	 *            报关清单表头
	 */
	String processDzscCustomsBillList(Request request,
			DzscCustomsBillList dzscCustomsBillList, List lsReturnFile);

	/**
	 * 根据归并后商品信息查询归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeAfterComInfo
	 *            清单归并后商品信息
	 * @return List 是DzscBillListBeforeCommInfo型，清单归并前商品信息
	 */
	List findAtcMergeBeforeComInfoByAfterID(Request request,
			DzscBillListAfterCommInfo atcMergeAfterComInfo);

	/**
	 * 根据清单编号查询归并后商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param applyToCustomsBillList
	 *            报关清单表头
	 * @return List 是DzscBillListAfterCommInfo型，清单归并后商品信息
	 */
	List findAtcMergeAfterComInfoByListID(Request request,
			DzscCustomsBillList applyToCustomsBillList);

	/**
	 * 删除报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeBeforeComInfo
	 *            清单归并前商品信息
	 * @return DzscCustomsBillList 报关清单表头
	 */
	DzscCustomsBillList deleteAtcMergeBeforeComInfo(Request request,
			DzscBillListBeforeCommInfo atcMergeBeforeComInfo);

	/**
	 * 删除报关清单归并前商品信息(多笔)
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscBillListBeforeCommInfo型，报关清单归并前商品信息
	 * @return DzscCustomsBillList 报关清单表头
	 */
	DzscCustomsBillList deleteAtcMergeBeforeComInfo(Request request, List list);

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param request
	 *            请求控制
	 * @param atcMergeBeforeComInfo
	 *            报关清单归并前商品信息
	 * @return atcMergeBeforeComInfo 报关清单归并前商品信息
	 */
	DzscBillListBeforeCommInfo saveAtcMergeBeforeComInfo(Request request,
			DzscBillListBeforeCommInfo atcMergeBeforeComInfo);

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
	DzscCustomsBillList saveAtcMergeBeforeComInfo(Request request,
			List declaredDataList, DzscCustomsBillList billList);

	/**
	 * 获取当天的最大清单号码
	 * 
	 * @param request
	 *            请求控制
	 * @return String 当天的最大清单号码
	 */
	String getApplyToCustomsBillListMaxNo(Request request);

	/**
	 * 在根据报关清单自动生成报关单的时候，检查报关清单的数据是否符合条件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 * @return 如果成功返回0；
	 */
	int checkBillListForMakeCustomsDeclaration(Request request, List list);

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
	List makeCusomsDeclarationFromBillList(Request request, List billLists,
			MakeCusomsDeclarationParam param);

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
	List getTempDeclaredCommInfo(Request request, DzscCustomsBillList billList,
			Integer materielProductFlag);

	/**
	 * 根据报关清单取得报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param listNo
	 *            报关清单号码
	 * @return List 是DzscCustomsDeclaration型，报关单头
	 */
	List findCustomsDeclarationByBillList(Request request, String listNo);

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	void exportQPBillListMessage(Request request, String messageFileName,
			DzscCustomsBillList dzscCustomsBillList);
}
