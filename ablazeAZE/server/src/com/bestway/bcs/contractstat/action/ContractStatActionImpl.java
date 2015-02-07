/*
 * Created on 2005-5-18
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractstat.action;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.entity.ExpProductStat;
import com.bestway.bcs.contractstat.entity.ExpProductStatResult;
import com.bestway.bcs.contractstat.entity.ImpMaterialStat;
import com.bestway.bcs.contractstat.entity.ImpMaterialStatResult;
import com.bestway.bcs.contractstat.logic.ContractStatLogic;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.TempCustomsInfo;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * com.bestway.bcs.contractstat.action.ContractStatActionImpl
 * 
 * @author Administrator
 */
@AuthorityClassAnnotation(caption = "电子化手册", index = 5)
public class ContractStatActionImpl extends BaseActionImpl implements
		ContractStatAction {
	private ContractStatLogic contractStatLogic;

	/**
	 * 获取contractStatLogic
	 * 
	 * @return Returns the contractqryLogic.
	 */
	public ContractStatLogic getContractStatLogic() {
		return contractStatLogic;
	}

	/**
	 * 设置contractStatLogic
	 * 
	 * @param contractqryLogic
	 *            The contractqryLogic to set.
	 */
	public void setContractStatLogic(ContractStatLogic contractqryLogic) {
		this.contractStatLogic = contractqryLogic;
	}

	/**
	 * 计算进口料件报关总值
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param list
	 *            是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 * @return ImpMaterialStatResult 存放统计报表中的进口料件报关情况表－－统计数据资料
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public ImpMaterialStatResult impMaterialStat(Request reqeust,
			List<ImpMaterialStat> list) {
		return this.contractStatLogic.impMaterialStat(list);
	}

	// /**
	// * 进口料件情况统计表
	// *
	// * @param reqeust
	// * 请求控制
	// * @param contract
	// * 合同备案表头
	// * @param beginDate
	// * 开始日期
	// * @param endDate
	// * 结束日期
	// * @param state
	// * 生效类型
	// * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	// */
	// @AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	// public List<ImpMaterialStat> findImpMaterialStat(Request reqeust,
	// Contract contract, Date beginDate, Date endDate, int state) {
	// return this.contractStatLogic.findImpMaterialStat(contract, beginDate,
	// endDate, state);
	// }

	/**
	 * 进口料件情况统计表
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @param isDetachCompute
	 *            是否分开统计
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List<ImpMaterialStat> findImpMaterialStatByContracts(
			Request reqeust, List contracts, Date beginDate, Date endDate,
			int state, boolean isCountInvoice, boolean isDetachCompute) {
		return this.contractStatLogic.findImpMaterialStatByContracts(contracts,
				beginDate, endDate, state,isCountInvoice,isDetachCompute);
	}

	/**
	 * 进口料件情况统计表
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List<ImpMaterialStat> findImpMaterialSchedule(Request reqeust,
			Contract contract, Date beginDate, Date endDate, boolean isCountInvoice) {
		return this.contractStatLogic.findImpMaterialStat(contract, beginDate,
				endDate,isCountInvoice);
	}

	/**
	 * 计算出口成品总值
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param list
	 *            是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 * @return List 是ExpProductStatResult型，存放统计报表中的出口成品报关情况表－－统计数据资料
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public ExpProductStatResult expProductStat(Request reqeust,
			List<ExpProductStat> list) {
		return this.contractStatLogic.expProductStat(list);
	}
	
	public List<ImpMaterialStat> findImpMaterialStat(Contract contract,
			Date beginDate, Date endDate, int state, boolean isCountInvoice){
		
		return this.contractStatLogic.findImpMaterialStat(contract, beginDate, endDate, state, isCountInvoice);
	}
	
	public List<ImpMaterialStat> findImpMaterialStatForOne(Contract contract,
			Date beginDate, Date endDate, int state, boolean isCountInvoice){
		return this.contractStatLogic.findImpMaterialStatForOne(contract, beginDate, endDate, state, isCountInvoice);
	}
			

	// /**
	// * 出口成品情况统计表
	// *
	// * @param reqeust
	// * 请求控制
	// * @param contract
	// * 合同备案表头
	// * @param beginDate
	// * 开始日期
	// * @param endDate
	// * 结束日期
	// * @param state
	// * 生效类型
	// * @return List 是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	// */
	// @AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	// public List<ExpProductStat> findExpProductStat(Request reqeust,
	// Contract contract, Date beginDate, Date endDate, int state) {
	// return this.contractStatLogic.findExpProductStat(contract, beginDate,
	// endDate, state);
	// }

	/**
	 * 出口成品情况统计表
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List<ExpProductStat> findExpProductStatByContracts(Request reqeust,
			List contracts, Date beginDate, Date endDate, int state, boolean isDetachCompute) {
		return this.contractStatLogic.findExpProductStatByContracts(contracts,
				beginDate, endDate, state, isDetachCompute);
	}

	/**
	 * 出口成品情况统计表
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List<ExpProductStat> findExpProductSchedule(Request reqeust,
			Contract contract, Date beginDate, Date endDate) {
		return this.contractStatLogic.findExpProductStat(contract, beginDate,
				endDate);
	}

	/**
	 * 进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * 
	 * @param reqeust
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
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List findMaterialImportListByContracts(Request reqeust,
			boolean isImport, Integer seqNum, String code, String name,
			String customer, String impExpType, Date beginDate, Date endDate,
			List contracts, int state,int impExpFlag) {
		return	this.contractStatLogic.findImpExpCommInfoList(
				isImport, seqNum, code, name, customer, impExpType,
				beginDate, endDate, contracts, state, impExpFlag);
//		List resultList = new ArrayList();
//		for (int i = 0; i < contracts.size(); i++) {
//			Contract contract = (Contract) contracts.get(i);
//			resultList.addAll(this.contractStatLogic.findImpExpCommInfoList(
//					isImport, seqNum, code, name, customer, impExpType,
//					beginDate, endDate, contracts, state, impExpFlag));
//		}
//		return resultList;
	}

	/**
	 * 查询申请单转报关单
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public List findBillToCustomsList(Request reqeust,Date beginDate, Date endDate, int state) {
		return contractStatLogic.findBillToCustomsList( beginDate,  endDate,  state);
	}
	

	/**
	 * 查询进出口申请单报表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public List findBillList(Request reqeust,Date beginDate, Date endDate,String billType, int state) {
		return contractStatLogic.findBillList( beginDate,  endDate,  billType, state);
		
	}
	/**
	 *  查询进出口报关单总值（寮步外经办用）
	 */
	public ImpMaterialStat findIoMoneyValue(Request reqeust,Date beginDate,Date endDate){
		return this.contractStatLogic.findIoMoneyValue(beginDate, endDate);
	}
	/**
	 * 查询已报关的商品
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param isImport
	 *            判断是否料件，true为料件
	 * @param contract
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List findCustomsDeclarationCommInfo(Request reqeust,
			boolean isImport, Contract contract, int state) {
		return this.contractStatLogic.findCustomsDeclarationCommInfo(isImport,
				contract, state);
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param isImport
	 *            判断是否料件，true为料件
	 * @param lsContract
	 *            是Contract型，合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List findCustomsDeclarationCommInfo(Request reqeust,
			boolean isImport, List lsContract, int state) {
		return this.contractStatLogic.findCustomsDeclarationCommInfo(isImport,
				lsContract, state);
	}

	public List findCustomsDeclarationCommInfoDistance(Request reqeust,
			boolean isImport, List lsContract, int state) {
		return this.contractStatLogic.findCustomsDeclarationCommInfo(isImport,
				lsContract, state);
	}
	
	public List findSpecialCustomsDeclarationCommInfoDistance(Request reqeust,Integer impExpFlag,int state){
		return this.contractStatLogic.findSpecialCustomsDeclarationCommInfo(impExpFlag,state);
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param isImport
	 *            进口判断，true为进口
	 * @param contract
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List findCustomsDeclarationCustomer(Request reqeust,
			boolean isImport, Contract contract, int state) {
		return this.contractStatLogic.findCustomsDeclarationCustomer(isImport,
				contract, state);
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param isImport
	 *            进口判断，true为进口
	 * @param lsContract
	 *            是Contract型，合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List findCustomsDeclarationCustomer(Request reqeust,
			boolean isImport, List lsContract, int state) {
		return this.contractStatLogic.findCustomsDeclarationCustomer(isImport,
				lsContract, state);
	}

	/**
	 * 料件，成品执行进度明细
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param isImport
	 *            进口判断，true为进口
	 * @param impExpType
	 *            进出口类型
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 料件时是ImpScheduleDetail型，存放统计报表中的料件执行进度明细资料
	 *         成品时是ExpScheduleDetail型，存放统计报表中的成品执行进度明细资料
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List findImpExpScheduleDetail(Request reqeust, boolean isImport,
			String impExpType, Contract contract, Date beginDate, Date endDate) {
		return this.contractStatLogic.findImpExpScheduleDetail(isImport,
				impExpType, contract, beginDate, endDate);
	}

	/**
	 * 核销单登记表
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是CancelAfterVerification型，存放核销单登记表资料
	 */
	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public List findCancelAfterVerificationList(Request reqeust, List contract,
			Date beginDate, Date endDate) {
		return this.contractStatLogic.findCancelAfterVerificationList(contract,
				beginDate, endDate);
	}

	public List findSpecialImpExpType(Request reqeust, Integer impExpFlag,Integer state) {
	
		return this.contractStatLogic.findSpecialImpExpType(impExpFlag,state);
	}
	
	public List findDZSCSpecialImpExpType(Request reqeust, Integer impExpFlag,Integer state) {
		
		return this.contractStatLogic.findDZSCSpecialImpExpType(impExpFlag,state);
	}

	public List findSpecialCustomsDeclarationCommInfo(Request reqeust,
			Integer seqNum, String code, String name, String customer,
			Date beginDate, Date endDate,List contracts,  int state, int impExpFlag) {

		return this.contractStatLogic.findSpecialCustomsDeclarationCommInfo(
				seqNum, code, name, customer,beginDate, endDate,contracts, state, impExpFlag);
	}
	
	public List findSpecialDzscCustomsDeclarationCommInfo(Request reqeust,
			Integer seqNum, String code, String name, String customer,
			Date beginDate, Date endDate,List contracts,  int state, int impExpFlag) {

		return this.contractStatLogic.findSpecialDzscCustomsDeclarationCommInfo(
				seqNum, code, name, customer,beginDate, endDate,contracts, state, impExpFlag);
	}


	public List findSpecialCustomsDeclarationCustomer(Request reqeust, Integer impExpFlag, int state) {
		
		return this.contractStatLogic.findSpecialCustomsDeclarationCustomer(impExpFlag,state);
	}

	@AuthorityFunctionAnnotation(caption = "统计报表--浏览", index = 15)
	public void checkAuthority(Request request) {
		
	}
	/**
	 * 根据报关单商品资料查询申请单商品对应的料号
	 * @param reqeust
	 * @param isImport
	 * @param lsContract
	 * @param state
	 * @return
	 */
	public List findMaterielByImpExpCommodityInfoToCustoms(Request reqeust,
			boolean isImport, List lsContract, int state){
		return this.contractStatLogic
				.findMaterielByImpExpCommodityInfoToCustoms(isImport,
						lsContract, state);
	}
	/**
	 * 查找报关单中的料号（针对直接新增的报关单，而不通过申请单转报关单）
	 * @param reqeust
	 * @param isImport
	 * @param lsContract
	 * @param state
	 * @return
	 */
	public List findMaterielByImpExpCommodityInfo(Request reqeust,
			boolean isImport, List lsContract, int state){
		return this.contractStatLogic
		.findMaterielByImpExpCommodityInfo(isImport,
				lsContract, state);
	}
	/**
	 * 根据报关单通过中间表查找申请单
	 * 
	 * @param reqeust 请求控制
	 *            
	 * @param isImport 判断是否料件，true为料件
	 *            
	 * @param seqNum商品序号
	 *            
	 * @param commCode 商品编码
	 * 
	 * @param  name 商品名称
	 *            
	 * @param customer 客户
	 *           
	 * @param impExpType 能是多个进出口类型例如："1,2,3,4"
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contract
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * @param ptno 料号
	 * @return List进出口报关登记表
	 */
	public List findImpExpCommodityInfoToCustoms(Request reqeust, boolean isImport,
			Integer seqNum, String code, String name, String customer,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, int impExpFlag,String ptno){
		return this.contractStatLogic.findImpExpCommodityInfoToCustoms(
				isImport, seqNum, code, name, customer, impExpType, beginDate,
				endDate, contracts, state, impExpFlag, ptno);
	}

	public List findStockStatistic(Request reqeust, boolean isMaterial, List contracts, int state, String ptNo,
			Date begin, Date end, boolean IsGroupEms) {
		if(isMaterial) {
			return contractStatLogic.findMaterialStockStatistic(contracts, state, ptNo, begin, end, IsGroupEms);
		} else {
			return contractStatLogic.findProduceStockStatistic(contracts, state, ptNo, begin, end, IsGroupEms);
		}
		
	}
	/**
	 * 计算料件耗用明细
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param isImport
	 *            判断是否料件，true为料件
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
	 * @param ptno 料号
	 * @param isMerger 是否合并显示
	 * @return List 是ImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 */
	public List findMaterialDetail(Request reqeust, boolean isImport,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, String ptno,boolean isMerger) {
		return contractStatLogic.findMaterialDetail(isImport, impExpType,
				beginDate, endDate, contracts, state, ptno,isMerger);
	}
	/**
	 * 查询报关行申报登记表
	 * @param reqeust
	 *            请求控制
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	public List findCustomsBrokerList(Request request , Date beginDate ,Date endDate,String customsbrokerName){
		return contractStatLogic.findCustomsBrokerList(beginDate,endDate,customsbrokerName);
	}
	/**
	 * 取得进口料件报关登记表或出口成品报关登记表资料的总金额, 报关单份数
	 * 
	 * @param list
	 *            报关登记表
	 * @return 总金额, 报关单份数
	 */
	public List getTotal(Request reqeust,List list) {
		return contractStatLogic.getTotal(list);
	}

	@Override
	public Boolean ischeckImgExg(List contracts,String materialType) {
		return contractStatLogic.ischeckImgExg(contracts,materialType);
	}
}
