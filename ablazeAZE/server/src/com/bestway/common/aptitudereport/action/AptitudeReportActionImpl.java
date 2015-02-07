package com.bestway.common.aptitudereport.action;

import java.util.List;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.aptitudereport.dao.AptitudeReportDao;
import com.bestway.common.aptitudereport.entity.FiltCondition;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.aptitudereport.entity.ReportField;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.StatTypeValue;
import com.bestway.common.aptitudereport.entity.TempReport;
import com.bestway.common.aptitudereport.logic.AptitudeReportLogic;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;

public class AptitudeReportActionImpl implements AptitudeReportAction {

	private AptitudeReportDao aptitudeReportDao = null;

	private AptitudeReportLogic aptitudeReportLogic = null;

	public AptitudeReportDao getAptitudereportDao() {
		return aptitudeReportDao;
	}


	public void setAptitudeReportDao(AptitudeReportDao aptitudeReportDao) {
		this.aptitudeReportDao = aptitudeReportDao;
	}

	public AptitudeReportLogic getAptitudeReportLogic() {
		return aptitudeReportLogic;
	}


	public void setAptitudeReportLogic(AptitudeReportLogic aptitudeReportLogic) {
		this.aptitudeReportLogic = aptitudeReportLogic;
	}


	public List findFiltCondition(Request request) {

		return null;
	}

	/**
	 * 查询报表
	 */
	public List findSelectCondition(Request request, Integer reportType) {

		return aptitudeReportLogic.findSelectCondition(reportType);
	}

	/**
	 * 根据报表类型查询报表
	 */
	public List findSelectConditionByState(Request request, Integer reportType) {

		return aptitudeReportLogic.findSelectConditionByState(reportType);
	}



	
	/**
	 * 保存报表表头
	 */
	public SelectCondition saveSelectCondition(Request request,
			SelectCondition selectCondition, List<GroupValue> groupList,
			List<StatTypeValue> statTypeValueList,
			List<GroupValue> groupValueByStatTypeList,
			List<FiltCondition> addFiltConditionList) {

		aptitudeReportLogic.saveSelectCondition(selectCondition, groupList,
				statTypeValueList,groupValueByStatTypeList,addFiltConditionList);
		return selectCondition;
	}


	
	/**保存分组条件
	 * 
	 */
	public GroupValue saveGroupValue(Request request, GroupValue groupValue) {
		aptitudeReportLogic.saveGroupValue(groupValue);
		return groupValue;
	}

	
	/**
	 * 删除报表表头
	 */
	public void deleteSelectCondition(Request request,
			SelectCondition selectCondition) {

		aptitudeReportLogic.deleteSelectCondition(selectCondition);
	}

	
	/**
	 * 删除筛选条件
	 */
	public void deleteFiltCondition(Request request, FiltCondition filtCondition) {

		aptitudeReportDao.deleteFiltCondition(filtCondition);

	}

	
	/**
	 * 查询筛选条件
	 */
	public List<FiltCondition> findFiltCondition(Request request,
			String selectConditionId,String conditionType) {

		return this.aptitudeReportDao.findFiltCondition(selectConditionId,conditionType);
	}

	
	/**
	 * 删除筛选条件
	 */
	public void deleteFiltCondition(Request request,
			List<FiltCondition> listFiltCondition) {

		aptitudeReportDao.deleteFiltCondition(listFiltCondition);

	}

	/**
	 * 组织公共查询（带select group by）
	 * 
	 * @param request
	 *            请求控制
	 * @param selects
	 *            选择内容或范围 不为空是selects 为空是select a
	 * @param className
	 *            表名
	 * @param conditions
	 *            查询条件("and"或"or""(""值"")"等等)
	 * @param groupBy
	 *            分组
	 * @return 带 selects groupby的公共查询
	 */

	public List<TempReport> commonCount(Request request, String selects,
			String className, List conditions, String groupByColumn,
			List conditionsToStat, String groupBy) {

		return this.aptitudeReportLogic.commonSearch(selects, className,
				conditions, groupByColumn, conditionsToStat,groupBy);
	}

	
	/**
	 * 
	 */
	public void deleteGroupValue(Request request, List<GroupValue> groupList) {

		aptitudeReportDao.deleteGroupValue(groupList);
	}

	
	/**
	 * 
	 */
	public void deleteGroupValue(Request request, GroupValue groupValue) {

		aptitudeReportDao.deleteGroupValue(groupValue);
	}

	
	/**
	 * 
	 */
	public List<GroupValue> findGroupValue(Request request, String selectConditionId
			,Integer groupType) {
		return this.aptitudeReportDao.findGroupValue(selectConditionId,groupType);
	}
	
	
	public List<GroupValue> findGroupValue(Request request, String selectConditionId) {
		return this.aptitudeReportDao.findGroupValue(selectConditionId);
	}

	
	/**
	 * 
	 */
	public List<AtcMergeBeforeComInfo> findReportDetailToAtcMerge(
			Request request, String selects, String className, List conditions) {

		return this.aptitudeReportDao.findReportDetailToAtcMerge(selects,
				className, conditions);
	}

	
	/**
	 * 
	 */
	public List<BillDetail> findReportDetailToBillDetail(Request request,
			String selects, String className, List<Condition> conditions) {

		return this.aptitudeReportDao.findReportDetailToBillDetail(selects,
				className, conditions);
	}

	/**
	 * 
	 */
	public List<BaseCustomsDeclarationCommInfo> findReportDetailToCustoms(
			Request request, String selects, String className, List conditions) {

		return this.aptitudeReportDao.findReportDetailToCustoms(selects,
				className, conditions);

	}

	
	/**
	 * 
	 */
	public List<Condition> getConditions(Request request, List<FiltCondition> filtList) {

		return this.aptitudeReportLogic.getConditions(filtList);
	}
	
	public List<Condition> getConditionsTOStat(Request request, List<FiltCondition> statConditionList) {

		return this.aptitudeReportLogic.getConditionsToStat(statConditionList);
	}

	
	/**
	 * 获得分组条件
	 */
	public String getGroupByList(Request request, List<GroupValue> groupByList) {

		return this.aptitudeReportLogic.getGroupByList(groupByList);
	}

	
	/**
	 * ＤＺＳＣ清单查询
	 */
	public List<DzscBillListBeforeCommInfo> findReportDetailToDzscBill(
			Request request, String selects, String className, List conditions) {

		return this.aptitudeReportDao.findReportDetailToDzscBill(selects,
				className, conditions);
	}

	
	/**
	 * 删除显示字段
	 */
	public void deleteReportField(Request request, ReportField reportField) {

		aptitudeReportDao.deleteReportField(reportField);
		
	}

	
	/**
	 * 保存要显示的字段
	 */
	public ReportField saveReportField(Request request, ReportField reportField) {

		aptitudeReportDao.saveReportField(reportField);
		
		return reportField;
	}



	/**
	 * 查询显示字段
	 */
	public List<ReportField> findReportField(Request request, String selectConditionId) {
		
		return this.aptitudeReportDao.findReportField(selectConditionId);
	}

	/**
	 * 保存统计方式
	 */
	public StatTypeValue saveStatTypeValue(Request request, StatTypeValue statTypeValue) {

		aptitudeReportDao.saveStatTypeValue(statTypeValue);
		
		return statTypeValue;
	}

	
	/**
	 * 根据报表ＩＤ查询统计方式
	 */
	public List<StatTypeValue> findStatTypeValue(Request request, String selectConditionId) {
		// 
		return aptitudeReportDao.findStatTypeValue(selectConditionId);
	}

	
	/**
	 * 报表保存
	 */
	public SelectCondition saveSelectCondition(Request request, SelectCondition selectCondition) {

		aptitudeReportDao.saveSelectCondition(selectCondition);
		return selectCondition;
	}



	public void saveFiltCondition(Request request,FiltCondition filtCondition) {
		// 
		aptitudeReportDao.saveFiltCondition(filtCondition);
	}


	public void deleteStatTypeValue(Request request, StatTypeValue statTypeValue) {
		// 
		aptitudeReportDao.deleteStatTypeValue(statTypeValue);
		
	}


	public SelectCondition saveSelectCondition(Request request, SelectCondition selectCondition,
			List<ReportField> reportFieldDestList, List<FiltCondition> addFiltConditionList) {

		aptitudeReportLogic.saveSelectCondition(selectCondition, reportFieldDestList,
				addFiltConditionList);
		return selectCondition;
	}




	





}
