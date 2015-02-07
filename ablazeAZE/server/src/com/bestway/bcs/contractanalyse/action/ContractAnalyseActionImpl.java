/*
 * Created on 2005-6-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractanalyse.action;

import java.security.acl.Acl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractanalyse.entity.ContractStat;
import com.bestway.bcs.contractanalyse.entity.ExpFinishProductProgressTotal;
import com.bestway.bcs.contractanalyse.entity.ImpMaterielExeStat;
import com.bestway.bcs.contractanalyse.entity.TempContractImg;
import com.bestway.bcs.contractanalyse.entity.TempFinishProduct;
import com.bestway.bcs.contractanalyse.logic.ContractAnalyseLogic;
import com.bestway.bcs.contractstat.logic.ContractStatLogic;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.bcs.contractanalyse.action.ContractAnalyseActionImpl
 * 报关分析ActionImpl
 * 
 * 
 * @author Administrator
 */
@AuthorityClassAnnotation(caption = "电子化手册", index = 5)
public class ContractAnalyseActionImpl extends BaseActionImpl implements
		ContractAnalyseAction {
	ContractAnalyseLogic contractAnalyseLogic = null;

	ContractStatLogic contractStatLogic = null;

	/**
	 * 获取contractStatLogic
	 * 
	 * @return Returns the contractStatLogic.
	 */
	public ContractStatLogic getContractStatLogic() {
		return contractStatLogic;
	}

	/**
	 * 设置contractStatLogic
	 * 
	 * @param contractStatLogic
	 *            The contractStatLogic to set.
	 */
	public void setContractStatLogic(ContractStatLogic contractStatLogic) {
		this.contractStatLogic = contractStatLogic;
	}

	/**
	 * 获取contractAnalyseLogic
	 * 
	 * @return Returns the contractAnalyseLogic.
	 */
	public ContractAnalyseLogic getContractAnalyseLogic() {
		return contractAnalyseLogic;
	}

	/**
	 * 设置contractAnalyseLogic
	 * 
	 * @param contractAnalyseLogic
	 *            The contractAnalyseLogic to set.
	 */
	public void setContractAnalyseLogic(
			ContractAnalyseLogic contractAnalyseLogic) {
		this.contractAnalyseLogic = contractAnalyseLogic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.BaseAction#getAcl()
	 */
	public Acl getAcl() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.BaseAction#getModuleName()
	 */
	public String getModuleName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.BaseAction#setModuleName(java.lang.String)
	 */
	public void setModuleName(String moduleName) {
		// TODO Auto-generated method stub

	}

	/**
	 * 进口包装统计
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param wrapCode
	 *            包装种类编码
	 * @param state
	 *            状态类型
	 * @return List 是WrapStat型，进口包装统计
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findImportWrapStat(Request request, List contracts,
			Date beginDate, Date endDate, String wrapCode, int state) {
		return this.contractAnalyseLogic.findImportWrapStat(contracts,
				beginDate, endDate, wrapCode, state);
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
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是ImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findImpExpCommodityStatus(Request request, boolean isImport,
			Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, Contract contract, int state,int impExpFlag) {
		List   contracts=new ArrayList();
		contracts.add(contract);
		return this.contractStatLogic.findImpExpCommInfoList(isImport, seqNum,
				null, null, customer, impExpType, beginDate, endDate, contracts,
				state,impExpFlag);// CustomsDeclarationState.EFFECTIVED
	}

	/**
	 * 报关单预录入库查询
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclaration型，报关单表头
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findPreCustomsDeclaration(Request request, List contracts,
			Date beginDate, Date endDate, int state) {
		return this.contractAnalyseLogic.findPreCustomsDeclaration(contracts,
				beginDate, endDate, state);
	}

	/**
	 * 库存统计 横向显示
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param materielType
	 *            物料类型
	 * @param state
	 *            状态类型
	 * @return List 是StorageStat型，库存统计 横向显示
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findStorageStat(Request request, List contracts,
			Date beginDate, Date endDate, String materielType, int state) {
		return this.contractAnalyseLogic.findStorageStat(contracts, beginDate,
				endDate, materielType, state);
	}

	/**
	 * 库存统计 纵向显示
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param materielType
	 *            物料类型
	 * @param state
	 *            状态类型
	 * @return List 是StorageContractStat型，库存统计 纵向显示
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findStorageContractStat(Request request, List contracts,
			Date beginDate, Date endDate, String materielType, int state) {
		return this.contractAnalyseLogic.findStorageContractStat(contracts,
				beginDate, endDate, materielType, state);
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            判断是否进口类型，true为进口
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
	 *            合同备案表头
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findCommInfoImpExpAnalyse(Request request, boolean isImport,
			Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, Contract contract) {
		return this.contractAnalyseLogic.findCommInfoImpExpList(isImport,
				seqNum, customer, impExpType, beginDate, endDate, contract);
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param request
	 *            请求控制
	 * @param isImport
	 *            判断是否进口类型，true为进口
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
	 * @param lsContract
	 *            是List型，合同备案表头
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findCommInfoImpExpAnalyse(Request request, boolean isImport,
			String code, String name,String spec,String unitName, Integer seqNo, String customer,
			String impExpType, Date beginDate, Date endDate, List lsContract,
			int state) {
		return this.contractAnalyseLogic.findCommInfoImpExpList(isImport, code,
				name, spec, unitName, seqNo, customer, impExpType, beginDate, endDate,
				lsContract, state);
	}

	/**
	 * 获得所有的合同料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempContractImg型，存放合同料件的临时资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findTempContractImg(Request request) {
		return this.contractAnalyseLogic.findTempContractImg();
	}

	/**
	 * 查找料件执行情况
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            合同备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @param state
	 *            状态类型
	 * @return List 是ImpMaterielExeState型，存放报关分析－－料件执行情况分析－－料件执行情况
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findImpMaterielExeState(Request request, List contracts,
			TempContractImg tempContractImg, int state) {
		return this.contractAnalyseLogic.findImpMaterielExeState(contracts,
				tempContractImg, state);
	}

	/**
	 * 查找料件执行明细情况 来自[进口料件,料件转厂,退料出口],
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImg
	 *            合同备案料件
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ImpMaterielExeDetail型，存放报关分析－－料件执行情况分析－－料件执行明细资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findImpMaterielExeDetail(Request request,
			ContractImg contractImg, int impExpType, Date beginDate,
			Date endDate, int state) {
		return this.contractAnalyseLogic.findImpMaterielExeDetail(contractImg,
				impExpType, beginDate, endDate, state);
	}

	/**
	 * 查找进出口料件统计
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param searchType
	 *            设置要查找的属性
	 * @param state
	 *            状态类型
	 * @return List 是ImpMaterielExeStat型，存放报关分析－－料件执行情况分析－－进口料件统计
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findImpMaterielExeStat(Request request, List contracts,
			Date beginDate, Date endDate, int searchType, int state) {
		return this.contractAnalyseLogic.findImpMaterielExeStat(contracts,
				beginDate, endDate, searchType, state);
	}

	/**
	 * 查找成品执行明细情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExg
	 *            合同备案成品
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductProgressDetail型，出口成品执行进度明细资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findExpProductExeDetail(Request request,
			ContractExg contractExg, int impExpType, Date beginDate,
			Date endDate, int state) {
		return this.contractAnalyseLogic.findExpProductExeDetail(contractExg,
				impExpType, beginDate, endDate, state);
	}

	/**
	 * 查找成品执行总表情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductProgressTotal型，出口成品执行进度总表资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findExpProductExeTotal(Request request, Contract contract,
			Date beginDate, Date endDate, int state) {
		return this.contractAnalyseLogic.findExpProductExeTotal(contract,
				beginDate, endDate, state);
	}

	/**
	 * 查找出口成品统计 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param request
	 *            请求控制
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param searchType
	 *            设置要查找的属性
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductStat型，存放报关分析－－成品执行情况分析－－出口成品统计资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findExpFinishProductStat(Request request, List contracts,
			Date beginDate, Date endDate, int searchType, int state) {
		return this.contractAnalyseLogic.findExpFinishProductStat(contracts,
				beginDate, endDate, searchType, state);

	}

	/**
	 * 各合同执行状况表
	 * 
	 * @param request
	 *            请求控制
	 * @param state
	 *            状态类型
	 * @return List 是ContractStat型，存放报关分析－－各合同执行状况表资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public List findContractAnalyseStat(Request request, int state) {
		return this.contractAnalyseLogic.findContractAnalyseStat(state);
	}

	/**
	 * 根据合同、和临时的合同料件资料 查找合同成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImgList
	 *            存放合同料件的临时资料
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @return List<TempFinishProduct> 存放临时的成品资料
	 */
	public List<TempFinishProduct> findContractBomByContractImg(
			Request request, List<TempContractImg> contractImgList,
			List<Contract> contractList) {
		return this.contractAnalyseLogic.findContractBomByContractImg(
				contractImgList, contractList);
	}

	/**
	 * 进出口报关单查询
	 * 
	 * @param request
	 *            请求控制
	 * @param lsContract
	 *            是List型，合同备案表头
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param customsDeclarationCode
	 *            预录入号或报关单号或入库报关单号
	 * @param fecavBillCode
	 *            批准文号(外汇核销单号)
	 * @param containerCode
	 *            集装箱号码
	 * @param trade
	 *            贸易方式
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ImpExpCustomsDeclarationList型，存放报关分析－－进、出口报关单列表资料
	 */
	//@AuthorityFunctionAnnotation(caption = "浏览统计报表", index = 15)
	public List findImpExpCustomsDeclarationList(Request request,
			List lsContract, Integer impExpFlag, Integer impExpType,
			String customsDeclarationCode, String fecavBillCode,
			String containerCode, Trade trade, Date beginDate, Date endDate,
			int state) {
		return this.contractAnalyseLogic.findImpExpCustomsDeclarationList(
				lsContract, impExpFlag, impExpType, customsDeclarationCode,
				fecavBillCode, containerCode, trade, beginDate, endDate, state);
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
			List lsMateriel, List<Contract> contractList, Date beginDate,
			Date endDate) {
		return this.contractAnalyseLogic.findFinishedProductExportAmount(
				lsMateriel, contractList, beginDate, endDate);
	}
	@AuthorityFunctionAnnotation(caption = "报关分析--浏览", index = 16)
	public void checkAuthority(Request request) {
		// TODO Auto-generated method stub
		
	}

}
