/*
 * Created on 2005-6-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractanalyse.action;

import java.util.Date;
import java.util.List;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractanalyse.entity.TempContractImg;
import com.bestway.bcs.contractanalyse.entity.TempFinishProduct;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.bcs.contractanalyse.action.ContractAnalyseAction 报关分析Action
 * 
 * @author Administrator
 */
public interface ContractAnalyseAction {

	/**
	 * 查询已报关的商品
	 * 
	 * @param request 请求控制
	 * @param isImport 判断是否进口类型，true为进口
	 * @param seqNum 商品序号
	 * @param customer 客户
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param contract 合同备案表头
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	List findCommInfoImpExpAnalyse(Request request, boolean isImport,
			Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, Contract contract);

	/**
	 * 查询已报关的商品
	 * 
	 * @param request 请求控制
	 * @param isImport 判断是否进口类型，true为进口
	 * @param seqNum 商品序号
	 * @param customer 客户
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param lsContract 是List型，合同备案表头
	 * @param state 状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	List findCommInfoImpExpAnalyse(Request request, boolean isImport,
			String code, String name, String spec,String unitName,Integer seqNo,String customer, String impExpType, Date beginDate,
			Date endDate, List lsContract, int state);

	/**
	 * 进口包装统计
	 * 
	 * @param request 请求控制
	 * @param contracts 合同备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param wrapCode 包装种类编码
	 * @param state 状态类型
	 * @return List 是WrapStat型，进口包装统计
	 */
	List findImportWrapStat(Request request, List contracts, Date beginDate,
			Date endDate, String wrapCode, int state);

	/**
	 * 进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * 
	 * @param request 请求控制
	 * @param isImport 判断是否料件，true为料件
	 * @param seqNum 商品序号
	 * @param customer 客户
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param contract 合同备案表头
	 * @param state 生效类型
	 * @return List 是ImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 */
	List findImpExpCommodityStatus(Request request, boolean isImport,
			Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, Contract contract, int state,int impExpFlag);

	/**
	 * 报关单预录入库查询
	 * 
	 * @param request 请求控制
	 * @param contracts 合同备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param state 状态类型
	 * @return List 是BcsCustomsDeclaration型，报关单表头
	 */
	List findPreCustomsDeclaration(Request request, List contracts,
			Date beginDate, Date endDate, int state);

	/**
	 * 库存统计 横向显示
	 * 
	 * @param request 请求控制
	 * @param contracts 合同备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param materielType 物料类型
	 * @param state 状态类型
	 * @return List 是StorageStat型，库存统计 横向显示
	 */
	List findStorageStat(Request request, List contracts, Date beginDate,
			Date endDate, String materielType, int state);

	/**
	 * 获得所有的合同料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @param request 请求控制
	 * @return List 是TempContractImg型，存放合同料件的临时资料 
	 */
	List findTempContractImg(Request request);

	/**
	 * 查找料件执行情况
	 * 
	 * @param request 请求控制
	 * @param contracts 合同备案表头
	 * @param tempContractImg 存放合同料件的临时资料
	 * @param state 状态类型
	 * @return List 是ImpMaterielExeState型，存放报关分析－－料件执行情况分析－－料件执行情况
	 */
	List findImpMaterielExeState(Request request, List contracts,
			TempContractImg tempContractImg,int state);

	/**
	 * 查找料件执行明细情况 来自[进口料件,料件转厂,退料出口],
	 * 
	 * @param request 请求控制
	 * @param contractImg 合同备案料件
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param state 状态类型
	 * @return List 是ImpMaterielExeDetail型，存放报关分析－－料件执行情况分析－－料件执行明细资料
	 */
	List findImpMaterielExeDetail(Request request, ContractImg contractImg,
			int impExpType, Date beginDate, Date endDate,int state);

	/**
	 * 查找进出口料件统计
	 * 
	 * @param request 请求控制
	 * @param contracts 合同备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param searchType 设置要查找的属性
	 * @param state 状态类型
	 * @return List 是ImpMaterielExeStat型，存放报关分析－－料件执行情况分析－－进口料件统计
	 */
	List findImpMaterielExeStat(Request request, List contracts,
			Date beginDate, Date endDate, int searchType,int state);

	/**
	 * 查找成品执行明细情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param request 请求控制
	 * @param contractExg 合同备案成品
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param state 状态类型
	 * @return List 是ExpFinishProductProgressDetail型，出口成品执行进度明细资料
	 */
	List findExpProductExeDetail(Request request, ContractExg contractExg,
			int impExpType, Date beginDate, Date endDate,int state);

	/**
	 * 查找成品执行总表情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param request 请求控制
	 * @param contract 合同备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param state 状态类型
	 * @return List 是ExpFinishProductProgressTotal型，出口成品执行进度总表资料
	 */
	List findExpProductExeTotal(Request request, Contract contract,
			Date beginDate, Date endDate,int state);

	/**
	 * 查找出口成品统计 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param request 请求控制
	 * @param contracts 合同备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param searchType 设置要查找的属性
	 * @param state 状态类型
	 * @return List 是ExpFinishProductStat型，存放报关分析－－成品执行情况分析－－出口成品统计资料
	 */
	List findExpFinishProductStat(Request request, List contracts,
			Date beginDate, Date endDate, int searchType,int state);

	/**
	 * 各合同执行状况表
	 * 
	 * @param request 请求控制
	 * @param state 状态类型
	 * @return List 是ContractStat型，存放报关分析－－各合同执行状况表资料
	 */
	List findContractAnalyseStat(Request request, int state);

	/**
	 * 根据合同、和临时的合同料件资料 查找合同成品资料
	 * 
	 * @param request 请求控制
	 * @param contractImgList 存放合同料件的临时资料
	 * @param contractList 是List<Contract>型，合同备案表头
	 * @return List<TempFinishProduct> 存放临时的成品资料
	 */
	public List<TempFinishProduct> findContractBomByContractImg(
			Request request, List<TempContractImg> contractImgList,
			List<Contract> contractList);

	/**
	 * 库存统计 纵向显示
	 * 
	 * @param request 请求控制
	 * @param contracts 合同备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param materielType 物料类型
	 * @param state 状态类型
	 * @return List 是StorageContractStat型，库存统计 纵向显示
	 */
	List findStorageContractStat(Request request, List contracts,
			Date beginDate, Date endDate, String materielType, int state);

	/**
	 * 进出口报关单查询
	 * 
	 * @param request 请求控制
	 * @param lsContract 是List型，合同备案表头
	 * @param impExpFlag 进出口标志
	 * @param impExpType 进出口类型
	 * @param customsDeclarationCode 预录入号或报关单号或入库报关单号
	 * @param fecavBillCode 批准文号(外汇核销单号)
	 * @param containerCode 集装箱号码
	 * @param trade 贸易方式
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param state 状态类型
	 * @return List 是ImpExpCustomsDeclarationList型，存放报关分析－－进、出口报关单列表资料
	 */
	List findImpExpCustomsDeclarationList(Request request, List lsContract,
			Integer impExpFlag, Integer impExpType,
			String customsDeclarationCode, String fecavBillCode,
			String containerCode, Trade trade, Date beginDate, Date endDate,
			int state);
	
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
	List findFinishedProductExportAmount(Request request, List lsMateriel,
			List<Contract> contractList, Date beginDate, Date endDate);

	void checkAuthority(Request request);
}
