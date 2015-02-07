/*
 * Created on 2005-6-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.dzsc.checkcancel.logic.DzscContractCavLogic;
import com.bestway.dzsc.contractanalyse.entity.TempDzscContractImg;
import com.bestway.dzsc.contractanalyse.entity.TempDzscFinishProduct;
import com.bestway.dzsc.contractanalyse.entity.TempFinishedProductExportAmount;
import com.bestway.dzsc.contractanalyse.entity.TempImpPORImgStatInfo;
import com.bestway.dzsc.contractanalyse.logic.DzscAnalyseLogic;
import com.bestway.dzsc.contractstat.logic.DzscStatLogic;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * com.bestway.dzsc.contractanalyse.action.DzscAnalyseActionImpl
 * 
 * @author Administrator
 */
//手册报关分析查看
@AuthorityClassAnnotation(caption = "电子手册", index = 7)
public class DzscAnalyseActionImpl extends BaseActionImpl implements
		DzscAnalyseAction {
	DzscAnalyseLogic dzscAnalyseLogic = null; 

	DzscStatLogic dzscStatLogic = null;

	/**
	 * 获取dzscStatLogic
	 * 
	 * @return Returns the contractStatLogic.
	 */
	public DzscStatLogic getDzscStatLogic() {
		return dzscStatLogic;
	}

	/**
	 * 设置dzscStatLogic
	 * 
	 * @param contractStatLogic
	 *            The contractStatLogic to set.
	 */
	public void setDzscStatLogic(DzscStatLogic contractStatLogic) {
		this.dzscStatLogic = contractStatLogic;
	}

	/**
	 * 获取dzscAnalyseLogic
	 * 
	 * @return Returns the contractAnalyseLogic.
	 */
	public DzscAnalyseLogic getDzscAnalyseLogic() {
		return dzscAnalyseLogic;
	}

	/**
	 * 设置dzscAnalyseLogic
	 * 
	 * @param contractAnalyseLogic
	 *            The contractAnalyseLogic to set.
	 */
	public void setDzscAnalyseLogic(DzscAnalyseLogic contractAnalyseLogic) {
		this.dzscAnalyseLogic = contractAnalyseLogic;
	}

	/**
	 * 进口包装统计
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param wrapCode
	 *            包装种类编码
	 * @return List 是DzscWrapStat型，存放进口包装统计资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findImportWrapStat(Request request, List contracts,
			Date beginDate, Date endDate, String wrapCode) {
		return this.dzscAnalyseLogic.findImportWrapStat(contracts, beginDate,
				endDate, wrapCode);
	}

	/**
	 * 进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            判断是否料件，true为料件
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contract
	 *            手册通关备案表头
	 * @return List 是DzscImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findImpExpCommodityStatus(Request request, boolean isImport,
			Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, DzscEmsPorHead contract) {
		return this.dzscStatLogic.findImpExpCommInfoList(isImport, seqNum,
				customer, impExpType, beginDate, endDate, contract);
	}

	/**
	 * 报关单预录入库查询
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isEffective
	 *            是否生效，true为生效
	 * @return List 是DzscCustomsDeclaration型，报关单表头
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findPreCustomsDeclaration(Request request, List contracts,
			Date beginDate, Date endDate, boolean isEffective) {
		return this.dzscAnalyseLogic.findPreCustomsDeclaration(contracts,
				beginDate, endDate, isEffective);
	}

	/**
	 * 库存统计
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param materielType
	 *            物料类型
	 * @return List 库存分析资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findStorageStat(Request request, List contracts,
			Date beginDate, Date endDate, String materielType) {
		return this.dzscAnalyseLogic.findStorageStat(contracts, beginDate,
				endDate, materielType);
	}

	/**
	 * 料件成品分析
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            判断是否进口类型，true为进口
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户Id
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contract
	 *            手册通关备案表头
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findCommInfoImpExpAnalyse(Request request, boolean isImport,
			Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, DzscEmsPorHead contract) {
		return this.dzscAnalyseLogic.findCommInfoImpExpList(isImport, seqNum,
				customer, impExpType, beginDate, endDate, contract);
	}

	/**
	 * 获得所有的合同料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempDzscContractImg型，存放合同料件的临时资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findTempContractImg(Request request) {
		return this.dzscAnalyseLogic.findTempContractImg();
	}

	/**
	 * 查找料件执行情况
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            手册通关备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return List 料件执行情况资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findImpMaterielExeState(Request request, List contracts,
			TempDzscContractImg tempContractImg) {
		return this.dzscAnalyseLogic.findImpMaterielExeState(contracts,
				tempContractImg);
	}

	/**
	 * 查找料件执行明细情况 来自[进口料件,料件转厂,退料出口],
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImg
	 *            手册通关备案料件
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 料件执行明细资料资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findImpMaterielExeDetail(Request request,
			DzscEmsImgBill contractImg, int impExpType, Date beginDate,
			Date endDate) {
		return this.dzscAnalyseLogic.findImpMaterielExeDetail(contractImg,
				impExpType, beginDate, endDate);
	}

	/**
	 * 查找进出口料件统计
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param searchType
	 *            设置要查找的属性
	 * @return List 进口料件统计资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findImpMaterielExeStat(Request request, List contracts,
			Date beginDate, Date endDate, int searchType) {
		return this.dzscAnalyseLogic.findImpMaterielExeStat(contracts,
				beginDate, endDate, searchType);
	}

	/**
	 * 查找成品执行明细情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param request
	 *            请求控制
	 * @param DzscEmsExgBill
	 *            手册通关备案表头
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 出口成品执行进度明细资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findExpProductExeDetail(Request request,
			DzscEmsExgBill DzscEmsExgBill, int impExpType, Date beginDate,
			Date endDate) {
		return this.dzscAnalyseLogic.findExpProductExeDetail(DzscEmsExgBill,
				impExpType, beginDate, endDate);
	}

	/**
	 * 查找成品执行总表情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscExpFinishProductProgressTotal型，出口成品执行进度总表资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findExpProductExeTotal(Request request,
			DzscEmsPorHead contract, Date beginDate, Date endDate) {
		return this.dzscAnalyseLogic.findExpProductExeTotal(contract,
				beginDate, endDate);
	}

	/**
	 * 查找出口成品统计 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param searchType
	 *            设置要查找的属性
	 * @return List 是DzscExpFinishProductStat型，出口成品统计资料
	 */
//	@AuthorityFunctionAnnotation(caption = "报关分析", index = 11)
	public List findExpFinishProductStat(Request request, List contracts,
			Date beginDate, Date endDate, int searchType) {
		return this.dzscAnalyseLogic.findExpFinishProductStat(contracts,
				beginDate, endDate, searchType);

	}

	/**
	 * 查找 用于报表
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImgList
	 *            存放合同料件的临时资料
	 * @param contractList
	 *            手册通关备案表头
	 * @return List 是TempDzscFinishProduct型，存放临时的成品资料
	 */
	public List<TempDzscFinishProduct> findContractBomByContractImg(
			Request request, List<TempDzscContractImg> contractImgList,
			List<DzscEmsPorHead> contractList) {
		return this.dzscAnalyseLogic.findContractBomByContractImg(
				contractImgList, contractList);
	}

	/**
	 * 查询成品物料的出口数量
	 * 
	 * @param ptNo
	 * @param versionNo
	 * @param contractList
	 * @param beginDate
	 * @param endDate
	 * @param impExpTypes
	 * @return
	 */
	public List findFinishedProductExportAmount(Request request,
			List lsMateriel, List<DzscEmsPorHead> contractList, Date beginDate,
			Date endDate) {
		return this.dzscAnalyseLogic.findFinishedProductExportAmount(
				lsMateriel, contractList, beginDate, endDate);
	}

	/**
	 * 进口料件备案统计表
	 * 
	 * @param head
	 * @return
	 */
	public List findImpPORImgStatInfo(Request request, DzscEmsPorHead head) {
		return this.dzscAnalyseLogic.findImpPORImgStatInfo(head);
	}
//	/**
//	 * 合同月结报表
//	 * @param request
//	 * @param date 截止日期  
//	 * @return
//	 * @author 石小凯
//	 */
//	public List findDzscContractBalance(Request request,Date date){
//		return this.dzscAnalyseLogic.findDzscContractBalance(date);
//	}
}
