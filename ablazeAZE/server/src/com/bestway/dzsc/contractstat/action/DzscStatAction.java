/*
 * Created on 2005-5-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractstat.action;

import java.util.Date;
import java.util.List;

import com.bestway.common.Request;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractstat.entity.DzscBillListExpProductStat;
import com.bestway.dzsc.contractstat.entity.DzscBillListImpMaterialStat;
import com.bestway.dzsc.contractstat.entity.DzscExpProductStat;
import com.bestway.dzsc.contractstat.entity.DzscExpProductStatResult;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStat;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStatResult;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.dzsc.contractstat.action.DzscStatAction
 * 
 * @author Administrator
 */
public interface DzscStatAction {
	/**
	 * 计算进口料件报关总值
	 * 
	 * @param reqeust 请求控制
	 * @param list 是DzscImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 * @return DzscImpMaterialStatResult 存放统计报表中的进口料件报关情况表－－统计数据资料
	 */
	DzscImpMaterialStatResult impMaterialStat(Request reqeust,
			List<DzscImpMaterialStat> list);

	/**
	 * 进口料件报关情况统计表
	 * 
	 * @param reqeust 请求控制
	 * @param contract 手册通关备案表头
	 * @return List 是DzscImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	List<DzscImpMaterialStat> findImpMaterialStat(Request reqeust,
			DzscEmsPorHead contract);

	/**
	 * 计算出口成品总值
	 * 
	 * @param reqeust 请求控制
	 * @param list 是DzscExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 * @return DzscExpProductStatResult 存放统计报表中的出口成品报关情况表－－统计数据资料
	 */
	DzscExpProductStatResult expProductStat(Request reqeust,
			List<DzscExpProductStat> list);

	/**
	 * 出口成品报关情况统计表
	 * 
	 * @param reqeust 请求控制
	 * @param contract 手册通关备案表头
	 * @return List 是DzscExpProductStat型， 存放统计报表中的出口成品报关情况表资料
	 */
	List<DzscExpProductStat> findExpProductStat(Request reqeust,
			DzscEmsPorHead contract);
	/**
	 * 出口成品情况统计表
	 * 
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
	public List<DzscExpProductStat> findExpProductStatByDzscEmsPorHeads(Request reqeust,List<DzscEmsPorHead> heads,
			Date beginDate, Date endDate, int state, boolean isDetachCompute);

	/**
	 * 进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * 
	 * @param reqeust 请求控制
	 * @param isImport 判断是否料件，true为料件
	 * @param seqNum 商品序号
	 * @param customer 客户
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param contract 手册通关备案表头
	 * @return List 是DzscImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 */
	List findMaterialImportList(Request reqeust, boolean isImport,
			Integer seqNum, String customer, String impExpType,
			Date beginDate, Date endDate, DzscEmsPorHead contract);
	
	/**最新修改后的方法
	 * 进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * 
	 * @param reqeust 请求控制
	 * @param isImport 判断是否料件，true为料件
	 * @param seqNum 商品序号
	 * @param customer 客户
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param billState 报关单状态
	 * @param contracts 手册通关备案表头集合
	 * @return List 是DzscImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 * @author 石小凯
	 */
	List findMaterialImportListNew(Request reqeust, boolean isImport,
			Integer seqNum,String ptName, String customer, String impExpType,
			Date beginDate, Date endDate, List<DzscEmsPorHead> contracts, String billState);

	/**
	 * 查询已报关的商品
	 * 
	 * @param reqeust 请求控制
	 * @param isImport 判断是否料件，true为料件
	 * @param contract 手册通关备案表头
	 * @return List 是TempDzscCustomsDeclarCommInfo型，
	 */
	List findCustomsDeclarationCommInfo(Request reqeust, boolean isImport,
			DzscEmsPorHead contract);

	/**
	 * 查询已报关的商品
	 * 
	 * @param reqeust 请求控制
	 * @param isImport 判断是否料件，true为料件
	 * @param contract 手册通关备案表头
	 * @return List 是TempDzscCustomsDeclarCommInfo型，
	 * @author 石小凯
	 */
	List findCustomsDeclarationCommInfoNew(Request reqeust, boolean isImport,
			DzscEmsPorHead contract);

	/**
	 * 查询已报关的客户
	 * 
	 * @param reqeust 请求控制
	 * @param isImport 判断是否进口，true时为进口
	 * @param contract 手册通关备案表头
	 * @return List 存放了已报关的客户
	 */
	List findCustomsDeclarationCustomer(Request reqeust, boolean isImport,
			DzscEmsPorHead contract);

	/**
	 * 进口料件报关情况统计表
	 * 
	 * @param reqeust 请求控制
	 * @param contract 手册通关备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return List 是DzscImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	List<DzscImpMaterialStat> findImpMaterialSchedule(Request reqeust,
			DzscEmsPorHead contract, Date beginDate, Date endDate);
	
	/**
	 * 出口成品情况统计表
	 * 
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
	public List<DzscImpMaterialStat> findImpMaterialStatByDzscEmsPorHeads(Request reqeust,List<DzscEmsPorHead> heads,
			Date beginDate, Date endDate, int state, boolean isDetachCompute);

	/**
	 * 出口成品报关情况统计表
	 * 
	 * @param reqeust 请求控制
	 * @param contract 手册通关备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return List 是DzscExpProductStat型， 存放统计报表中的出口成品报关情况表资料
	 */
	List<DzscExpProductStat> findExpProductSchedule(Request reqeust,
			DzscEmsPorHead contract, Date beginDate, Date endDate);

	/**
	 * 料件，成品执行进度明细
	 * 
	 * @param reqeust 请求控制
	 * @param isImport 判断是否料件，true时为料件
	 * @param impExpType 物料类别
	 * @param contract 手册通关备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return List 料件时是DzscImpScheduleDetail型，存放统计报表中的料件执行进度明细资料
	 * 				成品时是DzscExpScheduleDetail型，存放统计报表中的成品执行进度明细资料
	 */
	List findImpExpScheduleDetail(Request reqeust, boolean isImport,
			String impExpType, DzscEmsPorHead contract, Date beginDate,
			Date endDate);

	/**
	 * 核销单登记表
	 * 
	 * @param reqeust 请求控制
	 * @param contract 手册通关备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return List 是DzscCancelAfterVerification型，存放统计报表中的核销单登记表资料
	 */
	List findCancelAfterVerificationList(Request reqeust,
			List contract, Date beginDate, Date endDate);

	/**
	 * 成品料件进出口报关清单明细
	 * 
	 * @param reqeust 请求控制
	 * @param isMaterial 判断是否料件，true为料件
	 * @param emsNo 手册编号
	 * @param beginDate 开始日期 
	 * @param endDate 结束日期
	 * @return List 是DzscBillListDeclaration型，存放统计报表中的进口料件、出口成品清单明细表的资料
	 */
	List findDzscBillListDeclaration(Request reqeust, boolean isMaterial,
			String emsNo, Date beginDate, Date endDate);

	/**
	 * 报关清单进口料件情况统计表
	 * 
	 * @param reqeust 请求控制
	 * @param contract 手册通关备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return List 是DzscBillListImpMaterialStat型，存放统计报表中的进口料件清单情况统计表资料
	 */
	List<DzscBillListImpMaterialStat> findBillListImpMaterialStat(
			Request reqeust, DzscEmsPorHead contract, Date beginDate,
			Date endDate);

	/**
	 * 报关清单出口成品情况统计表
	 * 
	 * @param reqeust 请求控制
	 * @param contract 手册通关备案表头
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return List 是DzscBillListExpProductStat型，存放统计报表中的出口成品清单情况统计表
	 */
	List<DzscBillListExpProductStat> findBillListExpProductStat(
			Request reqeust, DzscEmsPorHead contract, Date beginDate,
			Date endDate);
}
