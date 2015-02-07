package com.bestway.common.aptitudereport.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.aptitudereport.entity.FiltCondition;
import com.bestway.common.aptitudereport.entity.GroupValue;
import com.bestway.common.aptitudereport.entity.ReportField;
import com.bestway.common.aptitudereport.entity.SelectCondition;
import com.bestway.common.aptitudereport.entity.StatTypeValue;
import com.bestway.common.aptitudereport.entity.TempReport;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;

public class AptitudeReportDao extends BaseDao {

	public void saveSelectCondition(SelectCondition selectCondition)
			throws DataAccessException {

		this.saveOrUpdate(selectCondition);

	}

	public void saveGroupValue(GroupValue groupValue)
			throws DataAccessException {

		this.saveOrUpdate(groupValue);

	}

	public void saveReportField(ReportField reportField)
			throws DataAccessException {

		this.saveOrUpdate(reportField);

	}

	public void saveFiltCondition(FiltCondition filtCondition)
			throws DataAccessException {
		this.saveOrUpdate(filtCondition);
	}

	public void deleteSelectCondition(SelectCondition selectCondition)
			throws DataAccessException {
		this.delectFiltConditionBySelectCondition(selectCondition);
		this.delectGroupValueBySelectCondition(selectCondition);
		this.deleteReportFieldBySelectCondition(selectCondition);
		this.delectStatTypeValueBySelectCondition(selectCondition);
		this.delete(selectCondition);
	}

	public void deleteFiltCondition(FiltCondition filtCondition)
			throws DataAccessException {
		this.delete(filtCondition);
	}

	public List findFiltCondition() {
		return this.find("select a from FiltCondition a");
	}

	public List findSelectCondition(Integer reportType) {
		SelectCondition selectCondition = new SelectCondition();
		String tableName = "SelectCondition";
		String hsql = " select a from " + tableName
				+ " as a  where  a.company =? and a.reportType =?";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				reportType });
	}

	public List findSelectConditionByState(Integer reportType) {
		SelectCondition selectCondition = new SelectCondition();
		String tableName = "SelectCondition";
		String hsql = " select a from " + tableName
				+ " as a  where  a.company =? and a.reportType =?";
		return this.find(hsql, new Object[] { CommonUtils.getCompany(),
				reportType });
	}

	public List<FiltCondition> findFiltCondition(String selectConditionId,
			String conditionType) {

		String tableName = "FiltCondition";
		String hsql = " select a from " + tableName
				+ " as a  where  a.selectCondition.id =? and a.conditionType=?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(selectConditionId);
		parameters.add(conditionType);
		return this.find(hsql, parameters.toArray());

	}

	public List<GroupValue> findGroupValue(String selectConditionId,
			Integer groupType) {

		String tableName = "GroupValue";
		String hsql = " select a from " + tableName
				+ " as a  where  a.selectCondition.id =? and a.groupType =?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(selectConditionId);
		parameters.add(groupType);
		return this.find(hsql, parameters.toArray());

	}

	public List<GroupValue> findGroupValue(String selectConditionId) {

		String tableName = "GroupValue";
		String hsql = " select a from " + tableName
				+ " as a  where  a.selectCondition.id =? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(selectConditionId);
		return this.find(hsql, parameters.toArray());

	}

	public List<ReportField> findReportField(String selectConditionId) {

		String tableName = "ReportField";
		String hsql = " select a from " + tableName
				+ " as a  where  a.selectCondition.id =?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(selectConditionId);
		return this.find(hsql, parameters.toArray());

	}

	public List<StatTypeValue> findStatTypeValue(String selectConditionId) {

		String tableName = "StatTypeValue";
		String hsql = " select a from " + tableName
				+ " as a  where  a.selectCondition.id =? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(selectConditionId);
		return this.find(hsql, parameters.toArray());

	}

	public void deleteFiltCondition(List<FiltCondition> listFiltCondition) {

		this.deleteAll(listFiltCondition);
	}

	public void deleteGroupValue(List<GroupValue> groupList) {

		this.deleteAll(groupList);
	}

	public void deleteGroupValue(GroupValue groupValue) {

		this.delete(groupValue);
	}

	public void deleteStatTypeValue(StatTypeValue statTypeValue) {
		this.delete(statTypeValue);

	}

	public void deleteReportField(ReportField reportField) {

		this.delete(reportField);
	}

	public void delectFiltConditionBySelectCondition(
			SelectCondition selectCondition) {
		String tableName = "FiltCondition";
		this.batchUpdateOrDelete(" delete from " + tableName
				+ " a where a.selectCondition = ? ", selectCondition);

	}

	public void deleteReportFieldBySelectCondition(
			SelectCondition selectCondition) {
		String tableName = "ReportField";
		this.batchUpdateOrDelete(" delete from " + tableName
				+ " a where a.selectCondition  = ? ", selectCondition);

	}

	public void delectStatTypeValueBySelectCondition(
			SelectCondition selectCondition) {
		String tableName = "StatTypeValue";
		this.batchUpdateOrDelete(" delete from " + tableName
				+ " a where a.selectCondition = ? ", selectCondition);

	}

	public void delectGroupValueBySelectCondition(
			SelectCondition selectCondition) {
		String tableName = "GroupValue";
		this.batchUpdateOrDelete(" delete from " + tableName
				+ " a where a.selectCondition = ? ", selectCondition);

	}

	/**
	 * 组织HQL报表条件公共查询
	 * 
	 * @param selects
	 *            选择内容
	 * @param className
	 *            类名
	 * @param conditions
	 *            查询条件
	 * @param groupBy
	 *            分组
	 * @param leftOuter
	 *            左连接内容
	 * @return 查询结果
	 */
	public List<TempReport> commonSearch(String selects, String className,
			List<Condition> conditions, String groupByColumn,
			List<Condition> conditionsToStat, String groupBy) {

		String sql = "";
		sql = " from " + className + " a " + " where a.company =? ";

		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null

			sql = " select " + groupByColumn + " from " + className + " a "
					+ " where a.company =? ";

		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {

				if (conditions.get(i).getLogic() != null) {
					sql += " " + conditions.get(i).getLogic() + "  ";
				}
				if (conditions.get(i).getBeginBracket() != null) {
					sql += conditions.get(i).getBeginBracket();
				}
				if (conditions.get(i).getFieldName() != null) {
					sql += "a." + conditions.get(i).getFieldName();
				}
				if (conditions.get(i).getOperator() != null) {
					sql += conditions.get(i).getOperator();
				}
				if (conditions.get(i).getValue() != null) {

					sql += "? ";
					params.add(conditions.get(i).getValue());
				}
				if (conditions.get(i).getEndBracket() != null)
					sql += conditions.get(i).getEndBracket();
			}

		}
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " group by " + groupBy;
		}

		if (conditionsToStat != null) {
			for (int i = 0; i < conditionsToStat.size(); i++) {

				if (conditionsToStat.get(i).getLogic() != null) {
					sql += " " + conditionsToStat.get(i).getLogic() + "  ";
				}
				if (conditionsToStat.get(i).getFieldName() != null) {
					sql += conditionsToStat.get(i).getFieldName();
				}
				if (conditionsToStat.get(i).getOperator() != null) {
					sql += conditionsToStat.get(i).getOperator();
				}
				if (conditionsToStat.get(i).getValue() != null) {

					sql += "? ";
					params.add(conditionsToStat.get(i).getValue());
				}
			}

		}

		return this.find(sql, params.toArray());
	}

	public List<AtcMergeBeforeComInfo> findReportDetailToAtcMerge(
			String selects, String className, List<Condition> conditions) {
		String sql = "";
		sql = " from " + className + " a " + " where a.company =? ";

		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null

			sql = " select a" + " from " + className + " a "
					+ " where a.company =? ";

		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = conditions.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null) {

					sql += "? ";
					params.add(condition.getValue());
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}

		}

		return this.find(sql, params.toArray());

	}

	public List<BillDetail> findReportDetailToBillDetail(String selects,
			String className, List<Condition> conditions) {
		String sql = "";
		sql = " from " + className + " a " + " where a.company =? ";

		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null

			sql = " select a" + " from " + className + " a "
					+ " where a.company =? ";

		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {

				if (conditions.get(i).getLogic() != null) {
					sql += " " + conditions.get(i).getLogic() + "  ";
				}
				if (conditions.get(i).getBeginBracket() != null) {
					sql += conditions.get(i).getBeginBracket();
				}
				if (conditions.get(i).getFieldName() != null) {
					sql += "a." + conditions.get(i).getFieldName();
				}
				if (conditions.get(i).getOperator() != null) {
					sql += " " + conditions.get(i).getOperator();
				}
				if (conditions.get(i).getValue() != null) {

					sql += " ? ";
					params.add(conditions.get(i).getValue());
	
				}
				if (conditions.get(i).getEndBracket() != null)
					sql += conditions.get(i).getEndBracket();
			}

		}

		return this.find(sql, params.toArray());

	}

	public List<BaseCustomsDeclarationCommInfo> findReportDetailToCustoms(
			String selects, String className, List<Condition> conditions) {

		String sql = "";
		sql = " from " + className + " a " + " where a.company =? ";

		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null

			sql = " select a" + " from " + className + " a "
					+ " where a.company =? ";

		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				if (conditions.get(i).getLogic() != null) {
					sql += " " + conditions.get(i).getLogic() + "  ";
				}
				if (conditions.get(i).getBeginBracket() != null) {
					sql += conditions.get(i).getBeginBracket();
				}
				if (conditions.get(i).getFieldName() != null) {
					sql += "a." + conditions.get(i).getFieldName();
				}
				if (conditions.get(i).getOperator() != null) {
					sql += conditions.get(i).getOperator();
				}
				if (conditions.get(i).getValue() != null) {

					sql += "? ";
					params.add(conditions.get(i).getValue());
				}
				if (conditions.get(i).getEndBracket() != null)
					sql += conditions.get(i).getEndBracket();
			}

		}

		return this.find(sql, params.toArray());

	}

	public List<DzscBillListBeforeCommInfo> findReportDetailToDzscBill(
			String selects, String className, List<Condition> conditions) {
		String sql = "";
		sql = " from " + className + " a " + " where a.company =? ";

		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null

			sql = " select a" + " from " + className + " a "
					+ " where a.company =? ";

		}
		List<Object> params = new ArrayList<Object>();
		params.add(CommonUtils.getCompany());
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				Condition condition = (Condition) conditions.get(i);
				if (condition.getLogic() != null) {
					sql += " " + condition.getLogic() + "  ";
				}
				if (condition.getBeginBracket() != null) {
					sql += condition.getBeginBracket();
				}
				if (condition.getFieldName() != null) {
					sql += "a." + condition.getFieldName();
				}
				if (condition.getOperator() != null) {
					sql += condition.getOperator();
				}
				if (condition.getValue() != null) {

					sql += "? ";
					params.add(condition.getValue());
				}
				if (condition.getEndBracket() != null)
					sql += condition.getEndBracket();
			}

		}

		return this.find(sql, params.toArray());

	}

	public void saveStatTypeValue(StatTypeValue statTypeValue)
			throws DataAccessException {
		this.saveOrUpdate(statTypeValue);

	}

}
