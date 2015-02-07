/*
 * Created on 2005-4-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.checkcancel.action;

import java.util.List;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.dzsc.checkcancel.dao.DzscContractCavDao;
import com.bestway.dzsc.checkcancel.entity.DzscCheckHead;
import com.bestway.dzsc.checkcancel.entity.DzscCheckImg;
import com.bestway.dzsc.checkcancel.entity.DzscContractBomCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractCavTotalValue;
import com.bestway.dzsc.checkcancel.entity.DzscContractExgCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractImgCav;
import com.bestway.dzsc.checkcancel.entity.DzscCustomsDeclarationCav;
import com.bestway.dzsc.checkcancel.entity.TempExpQPCavMsgSelectParam;
import com.bestway.dzsc.checkcancel.logic.DzscContractCavLogic;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.entity.DzscParameterSet;

/**
 * com.bestway.dzsc.checkcancel.action.DzscContractCavActionImpl
 * 
 * @author Administrator
 */
//手册核查核销
@AuthorityClassAnnotation(caption = "电子手册", index = 7)
public class DzscContractCavActionImpl extends BaseActionImpl implements
		DzscContractCavAction {
	private DzscContractCavDao contractCavDao;

	private DzscContractCavLogic contractCavLogic;

	/**
	 * 获取contractCavLogic
	 * 
	 * @return Returns the contractCavLogic.
	 */
	public DzscContractCavLogic getContractCavLogic() {
		return contractCavLogic;
	}

	/**
	 * 设置contractCavLogic
	 * 
	 * @param contractCavLogic
	 */
	public void setContractCavLogic(DzscContractCavLogic contractCavLogic) {
		this.contractCavLogic = contractCavLogic;
	}

	/**
	 * 获取contractCavDao
	 * 
	 * @return Returns the contractCavDao.
	 */
	public DzscContractCavDao getContractCavDao() {
		return contractCavDao;
	}

	/**
	 * 设置contractCavDao
	 * 
	 * @param contractCavDao
	 * 
	 */
	public void setContractCavDao(DzscContractCavDao contractCavDao) {
		this.contractCavDao = contractCavDao;
	}

	/**
	 * 抓取合同核销单头
	 * 
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是DzscContractCav类型，电子帐册合同核销单头
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--数据报核-核销计算", index = 9.1)
	public List findContractCav(Request reqeust, boolean isCustoms) {
		return this.contractCavDao.findContractCav(isCustoms);
	}

	/**
	 * 抓取合同核销单头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是DzscContractCav类型，电子帐册合同核销单头
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--数据报核-核销计算", index = 9.1)
	public List findContractCav(Request reqeust, String emsNo, boolean isCustoms) {
		return this.contractCavDao.findContractCav(emsNo, isCustoms);
	}

	/**
	 * 保存合同核销单头
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return contractCav 电子帐册合同核销单头
	 */
	public DzscContractCav saveContractCav(Request reqeust,
			DzscContractCav contractCav) {
		contractCavDao.saveContractCav(contractCav);
		return contractCav;
	}

	/**
	 * 删除合同核销单头
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 */
	public void deleteContractCav(Request reqeust, DzscContractCav contractCav) {
		this.contractCavDao.deleteContractCav(contractCav);
	}

	/**
	 * 抓取合同核销报关单
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 * @return List 是DzscCustomsDeclarationCav类型，电子帐册合同核销报关单
	 */
	public List findCustomsDeclarationCav(Request reqeust,
			DzscContractCav contractCav) {
		return this.contractCavDao.findCustomsDeclarationCav(contractCav);
	}

	/**
	 * 保存合同核销报关单
	 * 
	 * @param customsDeclarationCav
	 *            电子手册合同核销报关单
	 * @return customsDeclarationCav 电子手册合同核销报关单
	 */
	public DzscCustomsDeclarationCav saveCustomsDeclarationCav(Request reqeust,
			DzscCustomsDeclarationCav customsDeclarationCav) {
		this.contractCavDao.saveCustomsDeclarationCav(customsDeclarationCav);
		return customsDeclarationCav;
	}

	/**
	 * 删除合同核销报关单
	 * 
	 * @param customsDeclarationCav
	 *            电子手帐册合同核销报关单
	 */
	public void deleteCustomsDeclarationCav(Request reqeust,
			DzscCustomsDeclarationCav customsDeclarationCav) {
		this.contractCavDao.deleteCustomsDeclarationCav(customsDeclarationCav);
	}

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return List 是DzscContractExgCav型，电子手册核销成品资料
	 */
	public List findContractExgCav(Request reqeust, DzscContractCav contractCav) {
		return this.contractCavLogic.findContractExgCav(contractCav);
	}

	/**
	 * 保存合同核销成品资料
	 * 
	 * @param contractExgCav
	 *            电子帐册合同核销成品资料
	 * @return contractExgCav 电子帐册合同核销成品资料
	 */
	public DzscContractExgCav saveContractExgCav(Request reqeust,
			DzscContractExgCav contractExgCav) {
		this.contractCavDao.saveContractExgCav(contractExgCav);
		return contractExgCav;
	}

	/**
	 * 删除合同核销成品资料
	 * 
	 * @param contractExgCav
	 *            电子帐册合同核销成品资料
	 */
	public void deleteContractExgCav(Request reqeust,
			DzscContractExgCav contractExgCav) {
		this.contractCavDao.deleteContractExgCav(contractExgCav);
	}

	/**
	 * 抓取合同核销料件资料
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return List 是DzscContractImgCav型，电子手册核销料件资料
	 */
	public List findContractImgCav(Request reqeust, DzscContractCav contractCav) {
		return this.contractCavLogic.findContractImgCav(contractCav);
	}

	/**
	 * 保存合同核销料件资料
	 * 
	 * @param contractImgCav
	 *            电子手册合同核销料件资料
	 * @return contractImgCav 电子手册合同核销料件资料
	 */
	public DzscContractImgCav saveContractImgCav(Request reqeust,
			DzscContractImgCav contractImgCav) {
		this.contractCavDao.saveContractImgCav(contractImgCav);
		return contractImgCav;
	}

	/**
	 * 删除合同核销料件资料
	 * 
	 * @param contractImgCav
	 *            电子手册合同核销料件资料
	 */
	public void deleteContractImgCav(Request reqeust,
			DzscContractImgCav contractImgCav) {
		this.contractCavDao.deleteContractImgCav(contractImgCav);
	}

	/**
	 * 抓取合同核销BOM资料
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return List 是DzscContractBomCav类型，电子帐册合同核销BOM资料
	 */
	public List findContractBomCav(Request reqeust, DzscContractCav contractCav) {
		
		//获取电子手册的小数位数设置
		DzscParameterSet dzscParameterSet = this.contractCavDao.findDzscParameterSet();
		Integer unitWasteLen = 4;//单耗长度
		Integer wasteLen = 2;//损耗长度
		Integer totalAmountLen = 2;//总用量长度
		
		if(dzscParameterSet!=null){
			if(dzscParameterSet.getUnitWaste()!=null){
				unitWasteLen = dzscParameterSet.getUnitWaste();
			}
			
			if(dzscParameterSet.getWaste()!=null){
				wasteLen = dzscParameterSet.getWaste();
			}
			
			if(dzscParameterSet.getTotalAmount()!=null){
				totalAmountLen = dzscParameterSet.getTotalAmount();
			}
		}
		List<DzscContractBomCav> list = this.contractCavDao.findContractBomCav(contractCav);
		for (int i = 0; i < list.size(); i++) {
			DzscContractBomCav bom = list.get(i);
			bom.setUnitWaste(CommonUtils.getDoubleByDigit(bom.getUnitWaste(),unitWasteLen));
			bom.setWasteAmount(CommonUtils.getDoubleByDigit(bom.getWasteAmount(),wasteLen));
			bom.setTotalAmount(CommonUtils.getDoubleByDigit(bom.getTotalAmount(),totalAmountLen));
		}
		return list;
	}

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param contractBomCav
	 *            合同核销BOM资料
	 * @return contractBomCav 合同核销BOM资料
	 */
	public DzscContractBomCav saveContractBomCav(Request reqeust,
			DzscContractBomCav contractBomCav) {
		this.contractCavDao.saveContractBomCav(contractBomCav);
		return contractBomCav;
	}

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param contractBomCav
	 *            合同核销BOM资料
	 * @return contractBomCav 合同核销BOM资料
	 */
	public void saveContractBomCavs(Request reqeust, List contractBomCavs) {
		for (int i = 0; i < contractBomCavs.size(); i++) {
			DzscContractBomCav contractBomCav = (DzscContractBomCav) contractBomCavs
					.get(i);
			this.contractCavLogic.saveContractBomCav(contractBomCav);
		}

	}

	/**
	 * 删除合同核销Bom资料
	 * 
	 * @param contractBomCav
	 *            合同核销BOM资料
	 */
	public void deleteContractBomCav(Request reqeust,
			DzscContractBomCav contractBomCav) {
		this.contractCavDao.deleteContractBomCav(contractBomCav);
	}

	/**
	 * 核销计算(包含自用和海关)
	 * 
	 * @param contract
	 *            电子手册手册表头
	 */
	public void cavContract(Request reqeust, String emsNo) {
		this.contractCavLogic.cavContract(emsNo, reqeust.getTaskId());
	}

	/**
	 * 核销计算自用核销表
	 * 
	 * @param contract
	 *            电子手册手册表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--数据报核-核销计算", index = 9.1)
	public void reMakeSelfuseContractCav(Request reqeust, String emsNo) {
		this.contractCavLogic.reMakeSelfuseContractCav(emsNo, reqeust
				.getTaskId());
	}

	/**
	 * 重新获取海关核销表
	 * 
	 * @param contract
	 *            电子手册手册表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--数据报核-核销计算", index = 9.1)
	public void reGetCustomsContractCav(Request reqeust, String emsNo) {
		this.contractCavLogic.reGetCustomsContractCav(emsNo, reqeust
				.getTaskId());
	}

	// /**
	// * 重新核销计算表头
	// *
	// * @param contract
	// */
	// public DzscContractCav reCavContractHead(Request reqeust,DzscEmsPorHead
	// contract, DzscContractCav contractCav) {
	// this.contractCavLogic.reCavContractHead(contract, contractCav);
	// return contractCav;
	// }
	// /**
	// * 核销计算报关单
	// *
	// * @param contractCav
	// */
	// public void cavCustomsDeclaration(Request reqeust,DzscContractCav
	// contractCav) {
	// this.contractCavLogic.cavCustomsDeclaration(contractCav);
	// }
	// /**
	// * 核销计算合同料件资料
	// *
	// * @param contractCav
	// */
	// public void cavContractImg(Request reqeust,DzscContractCav contractCav) {
	// this.contractCavLogic.cavContractImg(contractCav);
	// }
	//
	// /**
	// * 核销计算合同成品资料
	// *
	// * @param contractCav
	// */
	// public void cavContractExg(Request reqeust,DzscContractCav contractCav) {
	// this.contractCavLogic.cavContractExg(contractCav);
	// }
	//
	// /**
	// * 核销计算合同BOM资料
	// *
	// * @param contractCav
	// */
	// public void cavContractBom(Request reqeust,DzscContractCav contractCav) {
	// this.contractCavLogic.cavContractBom(contractCav);
	// }

	/**
	 * 重新计算边角料
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 */
	public void recalRemainMaterialAmount(Request reqeust,
			DzscContractCav contractCav) {
		this.contractCavLogic.recalRemainMaterialAmount(contractCav);
	}

	/**
	 * 核销表总计算
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return contractCavTotalValue 存放了核销的各种总计
	 */
	public DzscContractCavTotalValue findCavTotalValue(Request reqeust,
			DzscContractCav contractCav) {
		return this.contractCavLogic.findCavTotalValue(contractCav);
	}

	/**
	 * 查询中期核查表头
	 * 
	 * @return List 是DzscCheckHead类型，中期核查表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--中期核查-浏览", index = 9.2)
	public List findDzscCheckHead(Request reqeust) {
		return this.contractCavDao.findDzscCheckHead();
	}

	/**
	 * 保存中期核查表头
	 * 
	 * @param head
	 *            中期核查表头
	 * @return DzscCheckHead 中期核查表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--中期核查-保存", index = 9.2)
	public DzscCheckHead saveDzscCheckHead(Request reqeust, DzscCheckHead head) {
		this.contractCavDao.saveDzscCheckHead(head);
		return head;
	}

	/**
	 * 删除中期核查表头
	 * 
	 * @param head
	 *            中期核查表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--中期核查-删除", index = 9.2)
	public void deleteDzscCheckHead(Request reqeust, DzscCheckHead head) {
		this.contractCavLogic.deleteDzscCheckHead(head);
	}

	/**
	 * 查询中期核查料件表
	 * 
	 * @param head
	 *            中期核查表头
	 * @return List 是DzscCheckImg类型，中期核查料件资料
	 */

	public List findDzscCheckImg(Request reqeust, DzscCheckHead head) {
		return this.contractCavDao.findDzscCheckImg(head);
	}

	/**
	 * 保存中期核查料件表
	 * 
	 * @param img
	 *            中期核查料件表
	 * @return img 中期核查料件表
	 */
	public DzscCheckImg saveDzscCheckImg(Request reqeust, DzscCheckImg img) {
		this.contractCavDao.saveDzscCheckImg(img);
		return img;
	}

	/**
	 * 删除中期核查料件表
	 * 
	 * @param img
	 *            中期核查料件表
	 */
	public void deleteDzscCheckImg(Request reqeust, DzscCheckImg img) {
		this.contractCavDao.deleteDzscCheckImg(img);
	}

	/**
	 * 中期核查--添加料件--来自内部归并关系
	 * 
	 * @param dzscEmsPorHead
	 *            电子手册手册表头
	 * @param dzscCheckHead
	 *            中期核查料件表
	 * @return List 是DzscInnerMergeData类型，电子手册内部归并资料
	 */
	public List findImgFromInnerMerge(Request reqeust,
			DzscEmsPorHead dzscEmsPorHead, DzscCheckHead dzscCheckHead) {
		return this.contractCavDao.findImgFromInnerMerge(dzscEmsPorHead,
				dzscCheckHead);
	}

	/**
	 * 核销单数量取整
	 * 
	 * @param contractCav
	 *            电子手册合同核销表头
	 * @param isTotalAmount
	 *            true:修改总用量 false:修改总耗量
	 * @param modifyTotalAmountNotWriteBack
	 *            判断是否修改总用量后反写，true为修改
	 * @param modifyWasteAmountNotWriteBack
	 *            判断是否修改耗用量后反写，true为修改
	 */
	public void convertWasteAmountToInteger(Request reqeust,
			DzscContractCav contractCav, boolean isTotalAmount,
			boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack) {
		this.contractCavLogic.convertWasteAmountToInteger(contractCav,
				isTotalAmount, modifyTotalAmountNotWriteBack,
				modifyWasteAmountNotWriteBack);
	}

	/**
	 * 保存电子手册核销BOM资料
	 * 
	 * @param reqeust
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
	 */
	public DzscContractBomCav saveContractBomCav(Request reqeust,
			DzscContractBomCav bomCav, boolean modifyUnitWasteNotWriteBack,
			boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack) {
		this.contractCavLogic.saveContractBomCav(bomCav,
				modifyUnitWasteNotWriteBack, modifyTotalAmountNotWriteBack,
				modifyWasteAmountNotWriteBack);
		return bomCav;
	}

	/**
	 * 保存电子手册核销料件资料
	 * 
	 * @param imgCav
	 *            电子手册核销料件资料
	 * @param list
	 *            要是DzscContractBomCav类型，电子手册核销BOM资料
	 * @param modifyProductUsedAmountWriteBack
	 *            判断是否修改成品耗用量后反写，true为修改
	 * @return imgCav 电子手册核销料件资料
	 */
	public DzscContractImgCav saveContractImgCav(Request reqeust,
			DzscContractImgCav imgCav, List list,
			boolean modifyProductUsedAmountWriteBack) {
		this.contractCavLogic.saveContractImgCav(imgCav, list,
				modifyProductUsedAmountWriteBack);
		return imgCav;
	}

	/**
	 * 查找电子帐册合同核销BOM资料 来自 电子帐册合同核销表头ID和料件序号
	 * 
	 * @param contractCavId
	 *            电子帐册合同核销单头ID
	 * @param imgSeqNum
	 *            料件序号
	 * @return List 是DzscContractBomCav类型，电子帐册合同核销BOM资料
	 */
	public List findContractBomCavByImgSeqNum(Request request,
			String contractCavId, String imgSeqNum) {
		return this.contractCavDao.findContractBomCavByImgSeqNum(contractCavId,
				imgSeqNum);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list<ContractExgCav>(0) 成品（集合）
	 * list<ContractUnitWasteCav>(1) 单耗记录（集合）
	 * 
	 * @param parentId
	 *            电子手册合同核销单头ID
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据长度
	 * @return List list<ContractExgCav>(0) 成品（集合） list<ContractUnitWasteCav>(1)
	 *         单耗
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--数据报核-核销计算", index = 9.1)
	public List<List> findDzscContractUnitWasteCav(Request rquest,
			DzscContractCav dzscContractCav, int index, int length) {
		return this.contractCavLogic.findDzscContractUnitWasteCav(
				dzscContractCav, index, length);
	}

	/**
	 * 查找电子手册合同核销成品数量 来自 电子手册合同核销单头ID
	 * 
	 * @param contractCavId
	 *            电子手册合同核销单头ID
	 * @return int 电子手册合同核销成品数量
	 */
	public int findDzscContractExgCavCount(Request request, String contractCavId) {
		return this.contractCavDao.findDzscContractExgCavCount(contractCavId);
	}

	/**
	 * 合同核销平衡检查
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return boolean true表示核销平衡
	 */
	public boolean checkDzscContractCavData(Request request,
			DzscContractCav contractCav) {
		return this.contractCavLogic.checkDzscContractCavData(contractCav);
	}

	/**
	 * 抓取电子帐册合同核销单头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是DzscContractCav类型，电子帐册合同核销单头
	 */
	public DzscContractCav findDzscContractCavById(Request request, String Id) {
		return this.contractCavDao.findDzscContractCavById(Id);
	}

	/**
	 * 数据报核申报
	 * 
	 * @param dzscContractCav
	 */
	public DeclareFileInfo applyDzscContractCav(Request request,
			DzscContractCav dzscContractCav) {
		return this.contractCavLogic.applyDzscContractCav(dzscContractCav,
				request.getTaskId());
	}

	/**
	 * 数据报核处理回执
	 * 
	 * @param dzscContractCav
	 */
	public String processDzscContractCav(Request request,
			DzscContractCav dzscContractCav, List lsReturnFile) {
		return this.contractCavLogic.processDzscContractCav(dzscContractCav,
				lsReturnFile);
	}

	/**
	 * 中期核查申报
	 * 
	 * @param dzscCheckHead
	 *            中期核查 表头
	 * @return 生成报文文件名
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--中期核查-申报", index = 9.2)
	public DeclareFileInfo applyDzscCheck(Request request,
			DzscCheckHead dzscCheckHead) {
		return this.contractCavLogic.applyDzscCheck(dzscCheckHead, request
				.getTaskId());
	}

	/**
	 * 处理回执中期核查表头
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscCheckHead
	 *            中期核查表头
	 */
	@AuthorityFunctionAnnotation(caption = "手册核查核销--中期核查-处理回执", index = 9.2)
	public String processDzscCheckHead(Request request,
			DzscCheckHead dzscCheckHead, List lsReturnFile) {
		return this.contractCavLogic.processDzscCheckHead(dzscCheckHead,
				lsReturnFile);
	}

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPCavMessage(Request request, String messageFileName,
			TempExpQPCavMsgSelectParam param) {
		this.contractCavLogic.exportQPCavMessage(request.getTaskId(),
				messageFileName, param);
	}

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPDzscCheckHeadMessage(Request request,
			String messageFileName, List list) {
		this.contractCavLogic.exportQPDzscCheckHeadMessage(request.getTaskId(),
				messageFileName, list);
	}
	
	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	@AuthorityFunctionAnnotation(caption = "手册通关备案--改变修改标志", index = 4)
	public void changeContractImgModifyMark(Request request, List list,
			String modifyMark) {
		this.contractCavLogic.changeContractImgModifyMark(list, modifyMark);
	}
}
