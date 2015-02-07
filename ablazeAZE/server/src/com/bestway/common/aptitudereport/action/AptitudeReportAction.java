package com.bestway.common.aptitudereport.action;

import java.util.List;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.aptitudereport.entity.FiltCondition;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.aptitudereport.entity.ReportField;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.StatTypeValue;
import com.bestway.common.aptitudereport.entity.TempField;
import com.bestway.common.aptitudereport.entity.TempReport;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;

public interface AptitudeReportAction {

	List findFiltCondition(Request request);
/**
 * 
 * @param request
 * @param selectConditionId  报表ID
 * @param conditionType 	条件类型: 1:筛选条件,2:统计条件
 * @return	条件集
 */
	List<FiltCondition> findFiltCondition(Request request,
			String selectConditionId,String conditionType);
	
	/**
	 * @param request
	 * @param selectConditionId 报表ID
	 * @return	分组条件集
	 */

	List<GroupValue> findGroupValue(Request request, String selectConditionId
			,Integer groupType);
	
	List<GroupValue> findGroupValue(Request request, String selectConditionId);
	
	/**
	 * @param request
	 * @param selectConditionId   报表ID
	 * @return	报表显示字段集
	 */
	
	List<ReportField> findReportField(Request request, String selectConditionId);
	
	/**
	 * @param request
	 * @param selects
	 * @param className		类名　单据
	 * @param conditions	条件集
	 * @return				查询集
	 */

	List<BillDetail> findReportDetailToBillDetail(Request request,
			String selects, String className, List<Condition> conditions);

	/**
	 * @param request
	 * @param selects
	 * @param className	报关单
	 * @param conditions
	 * @return
	 */
	List<BaseCustomsDeclarationCommInfo> findReportDetailToCustoms(
			Request request, String selects, String className, List conditions);

	/**
	 * @param request
	 * @param selects
	 * @param className		报关清单归并前商品信息
	 * @param conditions
	 * @return
	 */
	List<AtcMergeBeforeComInfo> findReportDetailToAtcMerge(Request request,
			String selects, String className, List conditions);

	/**
	 * 
	 * @param request
	 * @param selects
	 * @param className		ＤＺＳＣ报关清单归并前商品信息
	 * @param conditions
	 * @return
	 */
	List<DzscBillListBeforeCommInfo> findReportDetailToDzscBill(
			Request request, String selects, String className, List conditions);
	
	/**
	 * 
	 * @param request
	 * @param selectConditionId	
	 * @return		统计方式集
	 */
	List<StatTypeValue> findStatTypeValue(Request request,String selectConditionId);

	/**
	 * 查询自定义报表
	 * 
	 * @param request
	 */
	List findSelectCondition(Request request, Integer reportType);

	/**
	 * 
	 * @param request
	 * @param state  报表类型
	 * @return		报表表头
	 */
	List findSelectConditionByState(Request request, Integer reportType);

	/**
	 * 
	 * @param request
	 * @param filtCondition			查询条件
	 * @return
	 */

	
	void saveFiltCondition(Request request,FiltCondition filtConditionList);

	/**
	 * 
	 * @param request
	 * @param selectCondition	
	 * @param groupList
	 * @return
	 */
	SelectCondition saveSelectCondition(Request request,
			SelectCondition selectCondition, List<GroupValue> groupList,
			List<StatTypeValue> statTypeValueList,
			List<GroupValue> groupValueByStatTypeList,
			List<FiltCondition> addFiltConditionList);
	
	SelectCondition saveSelectCondition(Request request,
			SelectCondition selectCondition, List<ReportField> reportFieldDestList,
			List<FiltCondition> addFiltConditionList);
	
	/**
	 * 
	 * @param request
	 * @param selectCondition
	 * @return
	 */
	SelectCondition saveSelectCondition(Request request,
			SelectCondition selectCondition);

	/**
	 * 
	 * @param request
	 * @param groupValue
	 * @return
	 */
	GroupValue saveGroupValue(Request request, GroupValue groupValue);
	
	
	/**
	 * 
	 * @param request
	 * @param statTypeValue
	 * @return
	 */
	StatTypeValue saveStatTypeValue(Request request, StatTypeValue statTypeValue);
	
	/**
	 * 
	 * @param request
	 * @param reportField
	 * @return
	 */
	ReportField saveReportField(Request request,ReportField reportField);

	/**
	 * 删除查询条件
	 * 
	 * @param request
	 *            发送请求
	 * @param selectCondition
	 *            查询条件
	 */

	void deleteSelectCondition(Request request, SelectCondition selectCondition);

	/**
	 * 
	 * @param request
	 * @param listFiltCondition
	 */
	void deleteFiltCondition(Request request,
			List<FiltCondition> listFiltCondition);

	/**
	 * 
	 * @param request
	 * @param groupList
	 */
	void deleteGroupValue(Request request, List<GroupValue> groupList);

	/**
	 * 
	 * @param request
	 * @param groupValue
	 */
	void deleteGroupValue(Request request, GroupValue groupValue);
	
	/**
	 * 
	 * @param request
	 * @param reportField
	 */
	void deleteReportField(Request request, ReportField reportField);

	/**
	 * 
	 * @param request
	 * @param filtCondition
	 */
	void deleteFiltCondition(Request request, FiltCondition filtCondition);

	
	void deleteStatTypeValue(Request request, StatTypeValue statTypeValue);
	
	/**
	 * 组织公共查询（带select group by）
	 * 
	 * @param request
	 *            发送请求
	 * @param selects
	 *            选择内容或范围 不为空是selects 为空是select a
	 * @param className
	 *            表名
	 * @param conditions
	 *            查询条件("and"或"or""(""值"")"等等)
	 * @param groupBy
	 *            分组
	 * @param orderBy
	 *            统计
	 * @param having
	 *            过滤
	 * @return 带 selects groupby的公共查询
	 */
	List<TempReport> commonCount(Request request, String selects,
			String className, List conditions, String groupByColumn,
			List conditionsToStat, String groupBy);
	
	// 获取分组条件
	/**
	 * 
	 */
	String getGroupByList(Request request, List<GroupValue> groupByList);
	

	// 获得过滤条件 逻辑表达式
	/**
	 * 
	 */
	List<Condition> getConditions(Request request, List<FiltCondition> filtList);
	
	
	List<Condition> getConditionsTOStat(Request request, List<FiltCondition> statConditionList);
	


}
