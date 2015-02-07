/*
 * Created on 2005-5-18
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractstat.action;

import java.util.Date;
import java.util.List;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.entity.ExpProductStat;
import com.bestway.bcs.contractstat.entity.ExpProductStatResult;
import com.bestway.bcs.contractstat.entity.ImpMaterialStat;
import com.bestway.bcs.contractstat.entity.ImpMaterialStatResult;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * checked by kcb 2008/10/10
 * 
 * @author Administrator
 */
public interface ContractStatAction {

	/**
	 * 计算进口料件报关总值
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param list
	 *            是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 * @return ImpMaterialStatResult 存放统计报表中的进口料件报关情况表－－统计数据资料
	 */
	ImpMaterialStatResult impMaterialStat(Request reqeust,
			List<ImpMaterialStat> list);

	/**
	 * 查询申请单转报关单
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public List findBillToCustomsList(Request reqeust,Date beginDate, Date endDate, int state);
	/**
	 * 查询进出口申请单报表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public List findBillList(Request reqeust,Date beginDate, Date endDate, String billType,int state);
	
	/**
	 * 查询进出口报关单总值（寮步外经办用）
	 */
	ImpMaterialStat findIoMoneyValue(Request reqeust,Date beginDate,Date endDate);

	
	
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
	// List<ImpMaterialStat> findImpMaterialStat(Request reqeust,
	// Contract contract, Date beginDate, Date endDate, int state);

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
	List<ImpMaterialStat> findImpMaterialStatByContracts(Request reqeust,
			List contracts, Date beginDate, Date endDate, int state,
			boolean isCountInvoice, boolean isDetachCompute);

	/**
	 * 计算出口成品总值
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param list
	 *            是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 * @return List 是ExpProductStatResult型，存放统计报表中的出口成品报关情况表－－统计数据资料
	 */
	ExpProductStatResult expProductStat(Request reqeust,
			List<ExpProductStat> list);
	
	/**
	 * 进口料件情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	List<ImpMaterialStat> findImpMaterialStat(Contract contract,
			Date beginDate, Date endDate, int state, boolean isCountInvoice);

	
	List<ImpMaterialStat> findImpMaterialStatForOne(Contract contract,
			Date beginDate, Date endDate, int state, boolean isCountInvoice);
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
	 * @param isDetachCompute
	 *            是否分开统计
	 * @return List 是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 */
	List<ExpProductStat> findExpProductStatByContracts(Request reqeust,
			List contracts, Date beginDate, Date endDate, int state,boolean isDetachCompute);

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
	List findMaterialImportListByContracts(Request reqeust, boolean isImport,
			Integer seqNum, String code, String name, String customer,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, int impExpFlag);

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
	List findCustomsDeclarationCommInfo(Request reqeust, boolean isImport,
			Contract contract, int state);

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
	List findCustomsDeclarationCommInfo(Request reqeust, boolean isImport,
			List lsContract, int state);

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
	List findCustomsDeclarationCommInfoDistance(Request reqeust,
			boolean isImport, List lsContract, int state);

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
	List findCustomsDeclarationCustomer(Request reqeust, boolean isImport,
			Contract contract, int state);

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
	List findCustomsDeclarationCustomer(Request reqeust, boolean isImport,
			List lsContract, int state);

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
	List<ImpMaterialStat> findImpMaterialSchedule(Request reqeust,
			Contract contract, Date beginDate, Date endDate,
			boolean isCountInvoice);

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
	List<ExpProductStat> findExpProductSchedule(Request reqeust,
			Contract contract, Date beginDate, Date endDate);

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
	List findImpExpScheduleDetail(Request reqeust, boolean isImport,
			String impExpType, Contract contract, Date beginDate, Date endDate);

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
	List findCancelAfterVerificationList(Request reqeust, List contract,
			Date beginDate, Date endDate);

	/**
	 * 查询特殊报关单中的进出口类型
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return
	 */
	List findSpecialImpExpType(Request reqeust, Integer impExpFlag,Integer state);
	
	/**
	 * 查询特殊报关单中的进出口类型
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @return
	 */
	List findDZSCSpecialImpExpType(Request reqeust, Integer impExpFlag,Integer state);

	/**
	 * 查找特殊报关单表体商品
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param state
	 *            状态
	 * @return
	 */
	List findSpecialCustomsDeclarationCommInfoDistance(Request reqeust,
			Integer impExpFlag, int state);

	/**
	 * 查找特殊报关单表体商品
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param seqNum
	 *            商品序号
	 * @param code
	 *            商品编码
	 * @param name
	 *            商品名称
	 * @param customer
	 *            客户/供应商
	 * @param beginDate
	 *            开始有效果日期
	 * @param endDate
	 *            结束有效日期
	 * @param contracts
	 *            所属合同
	 * @param state
	 *            报关单状态
	 * @param impExpFlag
	 *            进出口标志
	 * @return
	 */
	List findSpecialCustomsDeclarationCommInfo(Request reqeust, Integer seqNum,
			String code, String name, String customer, Date beginDate,
			Date endDate, List contracts, int state, int impExpFlag);
	

	/**
	 * 查找特殊报关单表体商品
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param seqNum
	 *            商品序号
	 * @param code
	 *            商品编码
	 * @param name
	 *            商品名称
	 * @param customer
	 *            客户/供应商
	 * @param beginDate
	 *            开始有效果日期
	 * @param endDate
	 *            结束有效日期
	 * @param contracts
	 *            所属合同
	 * @param state
	 *            报关单状态
	 * @param impExpFlag
	 *            进出口标志
	 * @return
	 */
	List findSpecialDzscCustomsDeclarationCommInfo(Request reqeust, Integer seqNum,
			String code, String name, String customer, Date beginDate,
			Date endDate, List contracts, int state, int impExpFlag);

	/**
	 * 查找特殊报关单客户
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param impExpFlag
	 *            进出口标志
	 * @param state
	 *            报关单状态
	 * @return
	 */
	List findSpecialCustomsDeclarationCustomer(Request reqeust,
			Integer impExpFlag, int state);

	void checkAuthority(Request request);
	/**
	 * 根据报关单商品资料查询申请单中对应的料号
	 * @param reqeust
	 * @param isImport
	 * @param lsContract
	 * @param state
	 * @return
	 */
	List findMaterielByImpExpCommodityInfoToCustoms(Request reqeust,
			boolean isImport, List lsContract, int state);
	/**
	 * 查找报关单中的料号（针对直接新增的报关单，而不通过申请单转报关单）
	 * @param reqeust
	 * @param isImport
	 * @param lsContract
	 * @param state
	 * @return
	 */
	List findMaterielByImpExpCommodityInfo(Request reqeust,
			boolean isImport, List lsContract, int state);
	
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
	List findImpExpCommodityInfoToCustoms(Request reqeust, boolean isImport,
			Integer seqNum, String code, String name, String customer,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, int impExpFlag,String ptno);
	
	
	/**
	 * 查询已报关的商品库存情况。
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param isMaterial
	 *            判断是否料件，true为料件
	 * @param contracts
	 *            是Contract型，合同备案表头
	 * @param state
	 *            生效类型
	 * @param isGroupEms
	 *            是否分手册统计
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 */
	List findStockStatistic(Request reqeust, boolean isMaterial, List contracts, int state, String ptNo, Date begin, Date end, boolean isGroupEms);
	
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
	 * @return List 是型，进出口报关登记表
	 */
	List findMaterialDetail(Request reqeust, boolean isImport,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state,String ptno,boolean isMerger);
	/**
	 * 取得进口料件报关登记表或出口成品报关登记表资料的总金额, 报关单份数
	 * 
	 * @param list
	 *            报关登记表
	 * @return 总金额, 报关单份数
	 */
	public List getTotal(Request reqeust,List list);
	/**
	 * 查询报关行申报登记表
	 */
	 List findCustomsBrokerList(Request request,Date begin , Date end,String customsbrokerName );
	
	 /**
		 * 判断手册料件是否在备案资料库料件表中存在
		 */
	 Boolean ischeckImgExg(List contracts,String materialType );
		
}
