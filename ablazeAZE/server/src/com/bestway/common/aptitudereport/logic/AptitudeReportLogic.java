package com.bestway.common.aptitudereport.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.entity.TempBillDetail;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.common.Request;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.aptitudereport.dao.AptitudeReportDao;
import com.bestway.common.aptitudereport.entity.FiltCondition;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.aptitudereport.entity.ReportField;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.StatTypeValue;
import com.bestway.common.aptitudereport.entity.TempReport;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;

public class AptitudeReportLogic {

	private AptitudeReportDao aptitudeReportDao = null;

	public AptitudeReportDao getAptitudeReportDao() {
		return aptitudeReportDao;
	}

	public void setAptitudeReportDao(AptitudeReportDao aptitudeReportDao) {
		this.aptitudeReportDao = aptitudeReportDao;
	}

	public List findSelectCondition(Integer reportType) {
		return aptitudeReportDao.findSelectCondition(reportType);

	}

	//
	// 根据状态查询 报表资料
	//
	public List findSelectConditionByState(Integer reportType) {
		return aptitudeReportDao.findSelectConditionByState(reportType);

	}

	//
	// 保存报表资料
	//	
	public void saveSelectCondition(SelectCondition selectCondition,
			List<GroupValue> groupList, List<StatTypeValue> statTypeValueList,
			List<GroupValue> groupValueByStatTypeList,
			List<FiltCondition> addFiltConditionList)
			throws DataAccessException {
		String groupingColumnValue = "";
		String sign = "/";
		for (int i = 0; i < groupList.size(); i++) {
			if (i == groupList.size() - 1) {
				sign = "";
			}
			groupingColumnValue = groupingColumnValue
					+ groupList.get(i).getName() + sign;

		}
		selectCondition.setGroupingColumnValue(groupingColumnValue);
		this.aptitudeReportDao.saveSelectCondition(selectCondition);
		for (int i = 0; i < groupList.size(); i++) {
			GroupValue groupValue = new GroupValue();
			groupValue.setCode(groupList.get(i).getCode());
			groupValue.setName(groupList.get(i).getName());
			groupValue.setGroupType(0);
			groupValue.setSelectCondition(selectCondition);
			this.aptitudeReportDao.saveGroupValue(groupValue);

		}
		for (int i = 0; i < statTypeValueList.size(); i++) {
			StatTypeValue statTypeValue = new StatTypeValue();

			statTypeValue.setCode(statTypeValueList.get(i).getCode());
			statTypeValue.setName(statTypeValueList.get(i).getName());
			statTypeValue.setIsSelect(statTypeValueList.get(i).getIsSelect());
			statTypeValue.setSelectCondition(selectCondition);
			this.aptitudeReportDao.saveStatTypeValue(statTypeValue);

		}

		for (int i = 0; i < groupValueByStatTypeList.size(); i++) {
			GroupValue groupValue = new GroupValue();
			groupValue.setCode(groupValueByStatTypeList.get(i).getCode());
			groupValue.setName(groupValueByStatTypeList.get(i).getName());
			groupValue.setGroupType(1);
			groupValue.setSelectCondition(selectCondition);
			this.aptitudeReportDao.saveGroupValue(groupValue);
		}

		for (int i = 0; i < addFiltConditionList.size(); i++) {
			FiltCondition filtCondition = new FiltCondition();
			filtCondition.setBeginBracket(addFiltConditionList.get(i)
					.getBeginBracket());
			filtCondition.setEndBracket(addFiltConditionList.get(i)
					.getEndBracket());
			filtCondition.setEnFieldName(addFiltConditionList.get(i)
					.getEnFieldName());
			filtCondition.setEnSqlSentence(addFiltConditionList.get(i)
					.getEnSqlSentence());
			filtCondition.setFieldName(addFiltConditionList.get(i)
					.getFieldName());
			filtCondition.setLogic(addFiltConditionList.get(i).getLogic());
			filtCondition
					.setOperator(addFiltConditionList.get(i).getOperator());
			filtCondition.setSelectCondition(selectCondition);
			filtCondition.setSqlSentence(addFiltConditionList.get(i)
					.getSqlSentence());
			filtCondition.setValue(addFiltConditionList.get(i).getValue());
			filtCondition.setConditionType(addFiltConditionList.get(i)
					.getConditionType());
			filtCondition
					.setDataType(addFiltConditionList.get(i).getDataType());
			this.aptitudeReportDao.saveFiltCondition(filtCondition);

		}

	}

	public void saveSelectCondition(SelectCondition selectCondition,
			List<ReportField> reportFieldDestList,
			List<FiltCondition> addFiltConditionList)
			throws DataAccessException {

		this.aptitudeReportDao.saveSelectCondition(selectCondition);
		for (int i = 0; i < reportFieldDestList.size(); i++) {
			ReportField reportField = new ReportField();
			reportField.setCnName(reportFieldDestList.get(i).getCnName());
			reportField.setEnName(reportFieldDestList.get(i).getEnName());
			reportField.setFieldType(1);
			reportField.setClassname(reportFieldDestList.get(i).getClassname());
			reportField.setSelectCondition(selectCondition);
			reportField.setClassname(reportFieldDestList.get(i).getClassname());
			this.aptitudeReportDao.saveReportField(reportField);

		}
		for (int i = 0; i < addFiltConditionList.size(); i++) {
			FiltCondition filtCondition = new FiltCondition();
			filtCondition.setBeginBracket(addFiltConditionList.get(i)
					.getBeginBracket());
			filtCondition
					.setDataType(addFiltConditionList.get(i).getDataType());
			filtCondition.setConditionType("1");
			filtCondition.setEndBracket(addFiltConditionList.get(i)
					.getEndBracket());
			filtCondition.setFieldName(addFiltConditionList.get(i)
					.getFieldName());
			filtCondition.setEnFieldName(addFiltConditionList.get(i)
					.getEnFieldName());
			filtCondition.setLogic(addFiltConditionList.get(i).getLogic());
			filtCondition.setValue(addFiltConditionList.get(i).getValue());
			filtCondition
					.setOperator(addFiltConditionList.get(i).getOperator());
			filtCondition.setSelectCondition(selectCondition);
			filtCondition.setEnSqlSentence(addFiltConditionList.get(i)
					.getEnSqlSentence());
			filtCondition.setSqlSentence(addFiltConditionList.get(i)
					.getSqlSentence());
			this.aptitudeReportDao.saveFiltCondition(filtCondition);

		}

	}

	public void saveStatTypeValue(List<StatTypeValue> statTypeValueList,
			SelectCondition selectCondition) throws DataAccessException {

	}

	//
	// 保存
	//
	public void saveGroupValue(GroupValue groupValue)
			throws DataAccessException {
		this.aptitudeReportDao.saveGroupValue(groupValue);
	}

	//
	// 保存分组栏位值
	//

	public void deleteSelectCondition(SelectCondition selectCondition) {
		this.aptitudeReportDao.deleteSelectCondition(selectCondition);

	}

	/**
	 * 查询报表明细
	 * 
	 * @param list
	 *            临时报表明细
	 * @return 根据已选报表名称获得其报表明细信息
	 */

	public List<TempReport> commonSearch(String selects, String className,
			List conditions, String groupByColumn, List conditionsToStat,
			String groupBy) {
		List<TempReport> reportList = this.aptitudeReportDao.commonSearch(
				selects, className, conditions, groupByColumn,
				conditionsToStat, groupBy);

		return reportList;
	}

	// 在显示报表中取得分组条件
	public String getGroupByList(List<GroupValue> groupByList) {
		String groupBy = "";
		String comma = ",";
		if (groupByList.size() > 0) {
			for (int i = 0; i < groupByList.size(); i++) {
				if (i == groupByList.size() - 1) {
					comma = "";
				}
				groupBy += groupByList.get(i).getCode() + comma;

			}
		}
		return groupBy;

	}

	// 获得过滤条件的逻辑表达式
	public List<Condition> getConditions(List<FiltCondition> filtList) {
		List<Condition> conditions = new ArrayList<Condition>();
		if (filtList.size() > 0) {
			for (int i = 0; i < filtList.size(); i++) {

				String logic = filtList.get(i).getLogic();
				String beginBracket = filtList.get(i).getBeginBracket();
				String fieldName = filtList.get(i).getEnFieldName();
				String operator = filtList.get(i).getOperator();
				String endBracket = filtList.get(i).getEndBracket();
				if (filtList.get(i).getDataType().trim().equalsIgnoreCase(
						"java.lang.Double")) {
					Object value = Double.valueOf(filtList.get(i).getValue());
					conditions.add(new Condition(logic, beginBracket,
							fieldName, operator, value, endBracket));

				}else if (filtList.get(i).getDataType().equalsIgnoreCase(
						"java.util.Date")) {


					try {
						SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd");
						java.util.Date value1 = formatter
								.parse(filtList.get(i).getValue());
						java.sql.Date value = new java.sql.Date(value1.getTime());
//						Object value = java.sql.Date.valueOf(filtList.get(i).getValue());
						conditions.add(new Condition(logic, beginBracket,
								fieldName, operator, value, endBracket));
					} catch (ParseException e) {
						e.printStackTrace();
					}

					
				}else {

					Object value = filtList.get(i).getValue();
					conditions.add(new Condition(logic, beginBracket,
							fieldName, operator, value, endBracket));
				}

			}

		}
		return conditions;
	}

	// 获得统计条件的逻辑表达式
	public List<Condition> getConditionsToStat(
			List<FiltCondition> statConditionList) {
		List<Condition> conditionsToStat = new ArrayList<Condition>();
		if (statConditionList.size() > 0) {
			for (int i = 0; i < statConditionList.size(); i++) {
				String logic = statConditionList.get(i).getLogic();
				String beginBracket = statConditionList.get(i)
						.getBeginBracket();
				String fieldName = statConditionList.get(i).getEnFieldName();
				String operator = statConditionList.get(i).getOperator();
				String endBracket = statConditionList.get(i).getEndBracket();
				Object value = Double.valueOf(statConditionList.get(i)
						.getValue());
				conditionsToStat.add(new Condition(logic, beginBracket,
						fieldName, operator, value, endBracket));

			}

		}
		return conditionsToStat;
	}

}
