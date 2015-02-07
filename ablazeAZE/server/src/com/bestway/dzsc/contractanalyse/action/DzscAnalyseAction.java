/*
 * Created on 2005-6-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.action;

import java.util.Date;
import java.util.List;

import com.bestway.common.Request;
import com.bestway.dzsc.contractanalyse.entity.TempDzscContractImg;
import com.bestway.dzsc.contractanalyse.entity.TempDzscFinishProduct;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.dzsc.contractanalyse.action.DzscAnalyseAction
 * 
 * @author Administrator
 */
public interface DzscAnalyseAction {

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
	List findCommInfoImpExpAnalyse(Request request, boolean isImport,
			Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, DzscEmsPorHead contract);

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
	List findImportWrapStat(Request request, List contracts, Date beginDate,
			Date endDate, String wrapCode);

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
	List findImpExpCommodityStatus(Request request, boolean isImport,
			Integer seqNum, String customer, String impExpType, Date beginDate,
			Date endDate, DzscEmsPorHead contract);

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
	List findPreCustomsDeclaration(Request request, List contracts,
			Date beginDate, Date endDate, boolean isEffective);

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
	List findStorageStat(Request request, List contracts, Date beginDate,
			Date endDate, String materielType);

	/**
	 * 获得所有的合同料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是TempDzscContractImg型，存放合同料件的临时资料
	 */
	List findTempContractImg(Request request);

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
	List findImpMaterielExeState(Request request, List contracts,
			TempDzscContractImg tempContractImg);

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
	List findImpMaterielExeDetail(Request request, DzscEmsImgBill contractImg,
			int impExpType, Date beginDate, Date endDate);

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
	List findImpMaterielExeStat(Request request, List contracts,
			Date beginDate, Date endDate, int searchType);

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
	List findExpProductExeDetail(Request request,
			DzscEmsExgBill DzscEmsExgBill, int impExpType, Date beginDate,
			Date endDate);

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
	List findExpProductExeTotal(Request request, DzscEmsPorHead contract,
			Date beginDate, Date endDate);

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
	public List findExpFinishProductStat(Request request, List contracts,
			Date beginDate, Date endDate, int searchType);

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
	List<TempDzscFinishProduct> findContractBomByContractImg(Request request,
			List<TempDzscContractImg> contractImgList,
			List<DzscEmsPorHead> contractList);

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
			List<DzscEmsPorHead> contractList, Date beginDate, Date endDate);

	/**
	 * 进口料件备案统计表
	 * 
	 * @param head
	 * @return
	 */
	List findImpPORImgStatInfo(Request request, DzscEmsPorHead head);
	
	/**
	 * 合同月结报表
	 * @param request
	 * @param date 截止日期  
	 * @return
	 * @author 石小凯
	 */
//	List findDzscContractBalance(Request request,Date date);
}
