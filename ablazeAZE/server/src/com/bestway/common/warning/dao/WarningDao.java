/*
 * Created on 2004-9-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.warning.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.warning.entity.PlanTask;
import com.bestway.common.warning.entity.WarningItem;
import com.bestway.common.warning.entity.WarningThread;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class WarningDao extends BaseDao {

	/**
	 * type = 0 or 1
	 * 
	 * @return
	 */
	public WarningThread findWarningThread(int type) {
		List list = this
				.find(
						"select a from WarningThread a where a.company.id= ? and a.type = ? ",
						new Object[] { CommonUtils.getCompany().getId(), type });
		if (list.size() > 0) {
			return (WarningThread) list.get(0);
		}
		return null;
	}

	/**
	 * type = 0 or 1
	 * 
	 * @return
	 */
	public List<WarningThread> findWarningThreadAll(int type) {
		List list = this.find(
				"select a from WarningThread a where a.type = ? ",
				new Object[] { type });
		return list;
	}

	public void saveWarningThread(WarningThread w) {
		super.saveOrUpdate(w);
	}

	public void deleteWarningThread(WarningThread w) {
		super.delete(w);
	}

	public List<PlanTask> findPlanTask(Company company) {
		List list = this.find(
				"select a from PlanTask a where a.company.id= ?  ",
				new Object[] { company.getId() });
		return list;
	}

	public void savePlanTask(PlanTask p) {
		super.saveOrUpdate(p);
	}

	public void deletePlanTask(PlanTask p) {
		super.delete(p);
	}

	public List<WarningItem> findWarningItem(Company company) {
		if (company == null) {
			//wss:2010.05.06
			System.out.println("hhhhhhhhhhhhhhhhhhhhh");
			return new ArrayList();
		}
		List list = this.find(
				"select a from WarningItem a where a.company.id= ?  ",
				new Object[] { company.getId() });
		return list;
	}

	public WarningItem findWarningItem(Company company, Integer index) {
		List list = this
				.find(
						"select a from WarningItem a where a.company.id= ? and a.warningItemFlag=? ",
						new Object[] { company.getId(), index });
		if (list.size() > 0) {
			return (WarningItem) list.get(0);
		}
		return null;
	}

	public void saveWarningItem(WarningItem w) {
		super.saveOrUpdate(w);
	}

	public void deleteWarningItem(WarningItem w) {
		super.delete(w);
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
	public List commonSearch(String selects, String className, List conditions,
			String groupBy, String leftOuter, Company company) {
		if (leftOuter == null) {
			leftOuter = "";
		}
		String sql = "";
		sql = " from " + className + " a " + leftOuter + " where a.company =? ";
		if (selects != null && !selects.equals("")) {
			sql = selects + sql;
		} else {// selects == null
			sql = " select a from " + className + " a " + leftOuter
					+ " where a.company =? ";
		}
		List<Object> params = new ArrayList<Object>();
		params.add(company);
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
		if (groupBy != null && !groupBy.equals("")) {
			sql = sql + " " + groupBy;
		}
//		System.out.println("===sql=="+sql);
		return this.find(sql, params.toArray());

//		return result;
	}

}
