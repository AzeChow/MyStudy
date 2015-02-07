/*
 * Created on 2005-4-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.checkcancel.action;

import java.util.List;

import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.dzsc.checkcancel.entity.DzscCheckHead;
import com.bestway.dzsc.checkcancel.entity.DzscCheckImg;
import com.bestway.dzsc.checkcancel.entity.DzscContractBomCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractCavTotalValue;
import com.bestway.dzsc.checkcancel.entity.DzscContractExgCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractImgCav;
import com.bestway.dzsc.checkcancel.entity.DzscCustomsDeclarationCav;
import com.bestway.dzsc.checkcancel.entity.TempExpQPCavMsgSelectParam;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.dzsc.checkcancel.action.DzscContractCavAction
 * 
 * @author Administrator
 */
public interface DzscContractCavAction {
	/**
	 * 抓取合同核销单头
	 * 
	 * @param request
	 *            请求控制
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是DzscContractCav类型，电子帐册合同核销单头
	 */
	List findContractCav(Request request, boolean isCustoms);

	/**
	 * 抓取合同核销单头
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册编号
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是DzscContractCav类型，电子帐册合同核销单头
	 */
	List findContractCav(Request request, String emsNo, boolean isCustoms);

	/**
	 * 保存合同核销单头
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return contractCav 电子帐册合同核销单头
	 */
	DzscContractCav saveContractCav(Request request, DzscContractCav contractCav);

	/**
	 * 删除合同核销单头
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子帐册合同核销单头
	 */
	void deleteContractCav(Request request, DzscContractCav contractCav);

	/**
	 * 抓取合同核销报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscCustomsDeclarationCav类型，电子帐册合同核销报关单
	 */
	List findCustomsDeclarationCav(Request request, DzscContractCav contractCav);

	/**
	 * 保存合同核销报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationCav
	 *            电子手册合同核销报关单
	 * @return customsDeclarationCav 电子手册合同核销报关单
	 */
	DzscCustomsDeclarationCav saveCustomsDeclarationCav(Request request,
			DzscCustomsDeclarationCav customsDeclarationCav);

	/**
	 * 删除合同核销报关单
	 * 
	 * @param request
	 *            请求控制
	 * @param customsDeclarationCav
	 *            电子手帐册合同核销报关单
	 */
	void deleteCustomsDeclarationCav(Request request,
			DzscCustomsDeclarationCav customsDeclarationCav);

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return List 是DzscContractExgCav型，电子手册核销成品资料
	 */
	List findContractExgCav(Request request, DzscContractCav contractCav);

	/**
	 * 保存合同核销成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgCav
	 *            电子帐册合同核销成品资料
	 * @return contractExgCav 电子帐册合同核销成品资料
	 */
	DzscContractExgCav saveContractExgCav(Request request,
			DzscContractExgCav contractExgCav);

	/**
	 * 删除合同核销成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgCav
	 *            电子帐册合同核销成品资料
	 */
	void deleteContractExgCav(Request request, DzscContractExgCav contractExgCav);

	/**
	 * 抓取合同核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return List 是DzscContractImgCav型，电子手册核销料件资料
	 */
	List findContractImgCav(Request request, DzscContractCav contractCav);

	/**
	 * 保存合同核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImgCav
	 *            电子手册合同核销料件资料
	 * @return contractImgCav 电子手册合同核销料件资料
	 */
	DzscContractImgCav saveContractImgCav(Request request,
			DzscContractImgCav contractImgCav);

	/**
	 * 删除合同核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImgCav
	 *            电子手册合同核销料件资料
	 */
	void deleteContractImgCav(Request request, DzscContractImgCav contractImgCav);

	/**
	 * 抓取合同核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return List 是DzscContractBomCav类型，电子帐册合同核销BOM资料
	 */
	List findContractBomCav(Request request, DzscContractCav contractCav);

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractBomCav
	 *            合同核销BOM资料
	 * @return contractBomCav 合同核销BOM资料
	 */
	DzscContractBomCav saveContractBomCav(Request request,
			DzscContractBomCav contractBomCav);

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractBomCav
	 *            合同核销BOM资料
	 * @return contractBomCav 合同核销BOM资料
	 */
	void saveContractBomCavs(Request reqeust, List contractBomCavs);

	/**
	 * 删除合同核销Bom资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractBomCav
	 *            合同核销BOM资料
	 */
	void deleteContractBomCav(Request request, DzscContractBomCav contractBomCav);

	/**
	 * 核销计算(包含自用和海关)
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            电子手册手册表头
	 */
	void cavContract(Request request, String emsNo);

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list<ContractExgCav>(0) 成品（集合） list<ContractUnitWasteCav>(1)
	 * 单耗记录（集合）
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            电子手册合同核销单头ID
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据长度
	 * @return List list<ContractExgCav>(0) 成品（集合） list<ContractUnitWasteCav>(1)
	 *         单耗
	 */
	List<List> findDzscContractUnitWasteCav(Request request,
			DzscContractCav dzscContractCav, int index, int length);

	/**
	 * 查找电子手册合同核销成品数量 来自 电子手册合同核销单头ID
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCavId
	 *            电子手册合同核销单头ID
	 * @return int 电子手册合同核销成品数量
	 */
	int findDzscContractExgCavCount(Request request, String contractCavId);

	/**
	 * 核销计算自用核销表
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            电子手册手册表头
	 */
	void reMakeSelfuseContractCav(Request request, String emsNo);

	/**
	 * 重新获取海关核销表
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            电子手册手册表头
	 */
	void reGetCustomsContractCav(Request request, String emsNo);

	// /**
	// * 重新核销计算表头
	// *
	// * @param contract
	// */
	// DzscContractCav reCavContractHead(Request request,DzscEmsPorHead
	// contract, DzscContractCav contractCav);
	// /**
	// * 核销计算报关单
	// *
	// * @param contractCav
	// */
	// void cavCustomsDeclaration(Request request,DzscContractCav contractCav) ;
	// /**
	// * 核销计算合同料件资料
	// *
	// * @param contractCav
	// */
	// void cavContractImg(Request request,DzscContractCav contractCav) ;
	//
	// /**
	// * 核销计算合同成品资料
	// *
	// * @param contractCav
	// */
	// void cavContractExg(Request request,DzscContractCav contractCav);
	//
	// /**
	// * 核销计算合同BOM资料
	// *
	// * @param contractCav
	// */
	// void cavContractBom(Request request,DzscContractCav contractCav) ;

	/**
	 * 重新计算边角料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子手册合同核销单头
	 */
	void recalRemainMaterialAmount(Request request, DzscContractCav contractCav);

	/**
	 * 核销表总计算
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return contractCavTotalValue 存放了核销的各种总计
	 */
	DzscContractCavTotalValue findCavTotalValue(Request request,
			DzscContractCav contractCav);

	/**
	 * 查询中期核查表头
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscCheckHead类型，中期核查表头
	 */
	List findDzscCheckHead(Request request);

	/**
	 * 处理回执中期核查表头
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscCheckHead
	 *            中期核查表头
	 */
	String processDzscCheckHead(Request request, DzscCheckHead dzscCheckHead,
			List lsReturnFile);

	/**
	 * 保存中期核查表头
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            中期核查表头
	 * @return DzscCheckHead 中期核查表头
	 */
	DzscCheckHead saveDzscCheckHead(Request request, DzscCheckHead head);

	/**
	 * 删除中期核查表头
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            中期核查表头
	 */
	void deleteDzscCheckHead(Request request, DzscCheckHead head);

	/**
	 * 查询中期核查料件表
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            中期核查表头
	 * @return List 是DzscCheckImg类型，中期核查料件资料
	 */
	List findDzscCheckImg(Request request, DzscCheckHead head);

	/**
	 * 保存中期核查料件表
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            中期核查料件表
	 * @return img 中期核查料件表
	 */
	DzscCheckImg saveDzscCheckImg(Request request, DzscCheckImg img);

	/**
	 * 删除中期核查料件表
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            中期核查料件表
	 */
	void deleteDzscCheckImg(Request request, DzscCheckImg img);

	/**
	 * 中期核查--添加料件--来自内部归并关系
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscEmsPorHead
	 *            电子手册手册表头
	 * @param dzscCheckHead
	 *            中期核查料件表
	 * @return List 是DzscInnerMergeData类型，电子手册内部归并资料
	 */
	List findImgFromInnerMerge(Request request, DzscEmsPorHead dzscEmsPorHead,
			DzscCheckHead dzscCheckHead);

	/**
	 * 核销单数量取整
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子手册合同核销表头
	 * @param isTotalAmount
	 *            true:修改总用量 false:修改总耗量
	 * @param modifyTotalAmountNotWriteBack
	 *            判断是否修改总用量后反写，true为修改
	 * @param modifyWasteAmountNotWriteBack
	 *            判断是否修改耗用量后反写，true为修改
	 */
	void convertWasteAmountToInteger(Request request,
			DzscContractCav contractCav, boolean isTotalAmount,
			boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack);

	/**
	 * 保存电子手册核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bomCav
	 *            电子手册核销BOM资料
	 * @param modifyUnitWasteNotWriteBack
	 *            判断是否修改单耗后反写，true为修改
	 * @param modifyTotalAmountNotWriteBack
	 *            判断是否修改总用量后反写，true为修改
	 * @param modifyWasteAmountNotWriteBack
	 *            判断是否修改耗用量后反写，true为修改
	 * @return bomCav 电子手册核销BOM资料
	 * 
	 */
	DzscContractBomCav saveContractBomCav(Request request,
			DzscContractBomCav bomCav, boolean modifyUnitWasteNotWriteBack,
			boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack);

	/**
	 * 保存电子手册核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param imgCav
	 *            电子手册核销料件资料
	 * @param list
	 *            要是DzscContractBomCav类型，电子手册核销BOM资料
	 * @param modifyProductUsedAmountWriteBack
	 *            判断是否修改成品耗用量后反写，true为修改
	 * @return imgCav 电子手册核销料件资料
	 */
	DzscContractImgCav saveContractImgCav(Request request,
			DzscContractImgCav imgCav, List list,
			boolean modifyProductUsedAmountWriteBack);

	/**
	 * 查找电子帐册合同核销BOM资料 来自 电子帐册合同核销表头ID和料件序号
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCavId
	 *            电子帐册合同核销单头ID
	 * @param imgSeqNum
	 *            料件序号
	 * @return List 是DzscContractBomCav类型，电子帐册合同核销BOM资料
	 */
	List findContractBomCavByImgSeqNum(Request request, String contractCavId,
			String imgSeqNum);

	/**
	 * 合同核销平衡检查
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return boolean true表示核销平衡
	 */
	boolean checkDzscContractCavData(Request request,
			DzscContractCav contractCav);

	/**
	 * 抓取电子帐册合同核销单头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是DzscContractCav类型，电子帐册合同核销单头
	 */
	DzscContractCav findDzscContractCavById(Request request, String Id);

	/**
	 * 数据报核申报
	 * 
	 * @param dzscContractCav
	 */
	DeclareFileInfo applyDzscContractCav(Request request,
			DzscContractCav dzscContractCav);

	/**
	 * 数据报核处理回执
	 * 
	 * @param dzscContractCav
	 */
	String processDzscContractCav(Request request,
			DzscContractCav dzscContractCav, List lsReturnFile);

	/**
	 * 中期核查申报
	 * 
	 * @param dzscCheckHead
	 *            中期核查 表头
	 * @return 生成报文文件名
	 */
	DeclareFileInfo applyDzscCheck(Request request, DzscCheckHead dzscCheckHead);

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	void exportQPCavMessage(Request request, String messageFileName,
			TempExpQPCavMsgSelectParam param);

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	void exportQPDzscCheckHeadMessage(Request request, String messageFileName,
			List list);
	
	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	void changeContractImgModifyMark(Request request, List list,
			String modifyMark);
}
