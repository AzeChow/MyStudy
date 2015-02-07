/*
 * Created on 2005-3-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgCommonQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.DBToTxtRegion;
import com.bestway.common.dataexport.entity.JDBCRegion;
import com.bestway.common.dataexport.entity.JDBCSqlEvent;
import com.bestway.common.dataexport.entity.JDBCTask;
import com.bestway.common.dataexport.entity.JDBCTaskDetail;
import com.bestway.common.dataexport.entity.TxtDBTask;
import com.bestway.common.dataexport.entity.TxtDBTaskDetail;
import com.bestway.common.dataexport.entity.TxtToDBRegion;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class JDBCCommonQuery {
	private static JDBCCommonQuery	bcsQuery	= null;

	public synchronized static JDBCCommonQuery getInstance() {
		if (bcsQuery == null) {
			bcsQuery = new JDBCCommonQuery();
		}
		return bcsQuery;
	}

	/**
	 * 获得域设置
	 * 
	 * @param single
	 * @return
	 */
	public Object getJDBCTask(boolean single, JDBCTask jdbcTask) {
		DataExportAction dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List<JDBCTaskDetail> jdbcTaskDetailList = new ArrayList<JDBCTaskDetail>();

		// 域设
		if (jdbcTask.getIsParentTask() == null || !jdbcTask.getIsParentTask()) {
			List<JDBCRegion> dataSource = dataExportAction
					.findJDBCRegionNotInJDBCTask(new Request(CommonVars
							.getCurrUser(), true), jdbcTask);
			for (JDBCRegion jdbcRegion : dataSource) {
				JDBCTaskDetail jdbcTaskDetail = new JDBCTaskDetail();
				jdbcTaskDetail.setTaskType(JDBCTaskDetail.JDBC_REGION_TYPE);
				jdbcTaskDetail.setJdbcRegion(jdbcRegion);
				jdbcTaskDetailList.add(jdbcTaskDetail);
			}
		} else { // 子任务
			List<JDBCTask> dataSource = dataExportAction
					.findSubJDBCTaskNotInJDBCTask(new Request(CommonVars
							.getCurrUser(), true), jdbcTask);
			for (JDBCTask subJDBCTask : dataSource) {
				JDBCTaskDetail jdbcTaskDetail = new JDBCTaskDetail();
				jdbcTaskDetail.setTaskType(JDBCTaskDetail.SUB_JDBC_TASK_TYPE);
				jdbcTaskDetail.setSubJDBCTask(subJDBCTask);
				jdbcTaskDetailList.add(jdbcTaskDetail);
			}
		}

		dgCommonQuery.setDataSource(jdbcTaskDetailList);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		if (jdbcTask.getIsParentTask() == null || !jdbcTask.getIsParentTask()) {
			tableColumns.add(new JTableListColumn("DB导入导出域名称",
					"jdbcRegion.regionName", 180));
			tableColumns.add(new JTableListColumn("目标表",
					"jdbcRegion.destTableName", 100));
			tableColumns.add(new JTableListColumn("目标数据源",
					"jdbcRegion.destJDBCDataSource.name", 80));
			tableColumns.add(new JTableListColumn("视图名",
					"jdbcRegion.srcJDBCView.name", 100));
			tableColumns.add(new JTableListColumn("视图数据源",
					"jdbcRegion.srcJDBCView.jdbcDataSource.name", 80));
		} else {
			tableColumns.add(new JTableListColumn("子任务名称",
					"subJDBCTask.taskname", 180));
		}
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(single);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			if (single) {
				return dgCommonQuery.getReturnValue();
			} else {
				return dgCommonQuery.getReturnList();
			}
		}
		return null;
	}

	/**
	 * 新增事件
	 * 
	 * @param impExpGoodsFlag
	 * @return
	 */
	public List getJDBCSqlEvent() {
		DataExportAction dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");

		List<JDBCSqlEvent> dataSource = dataExportAction
				.findJDBCSqlEvent(new Request(CommonVars.getCurrUser(), true));
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("事件名称", "name", 300));
		list.add(new JTableListColumn("执行语名", "sqlStr", 300));

		DgCommonQuery.setTableColumns(list);
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		dgCommonQuery.setDataSource(dataSource);
		dgCommonQuery.setTitle("选择执行事件");
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	
	/** 获得文本导出到DB的域 */
	public List getTxtToDBRegion(TxtDBTask txtDBTask) {	
		DataExportAction dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List<TxtDBTaskDetail> txtDBTaskDetailList = new ArrayList<TxtDBTaskDetail>();

		List<TxtToDBRegion> dataSource = dataExportAction
				.findTxtToDBRegionNotInTxtDBTask(new Request(CommonVars
						.getCurrUser(), true), txtDBTask);

		for (TxtToDBRegion txtToDBRegion : dataSource) {
			TxtDBTaskDetail txtDBTaskDetail = new TxtDBTaskDetail();
			txtDBTaskDetail.setTaskType(TxtDBTaskDetail.TXT_TO_DB_REGION_TYPE);
			txtDBTaskDetail.setTxtToDBRegion(txtToDBRegion);
			txtDBTaskDetailList.add(txtDBTaskDetail);
		}
		dgCommonQuery.setDataSource(txtDBTaskDetailList);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("文本导出到DB域名称", "txtToDBRegion.regionName",
				180));
		tableColumns.add(new JTableListColumn("源文件路径",
				"txtToDBRegion.srcFilePath", 100));
		tableColumns.add(new JTableListColumn("目的表",
				"txtToDBRegion.destTableName", 100));
		tableColumns.add(new JTableListColumn("目的数据源",
				"txtToDBRegion.destJDBCDataSource.name", 80));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/** 获得文本导出的子任务 */
	public List getSubTask(TxtDBTask txtDBTask) {

		DataExportAction dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List<TxtDBTaskDetail> txtDBTaskDetailList = new ArrayList<TxtDBTaskDetail>();

		List<TxtDBTask> dataSource = dataExportAction
				.findSubTxtDBTaskNotInTxtDBTask(new Request(CommonVars
						.getCurrUser(), true), txtDBTask);

		for (TxtDBTask subTxtDBTask : dataSource) {
			TxtDBTaskDetail txtDBTaskDetail = new TxtDBTaskDetail();
			txtDBTaskDetail.setTaskType(TxtDBTaskDetail.SUB_JDBC_TASK_TYPE);
			txtDBTaskDetail.setSubTxtDBTask(subTxtDBTask);
			txtDBTaskDetailList.add(txtDBTaskDetail);
		}

		dgCommonQuery.setDataSource(txtDBTaskDetailList);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("子任务名称", "subTxtDBTask.taskname",
				180));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

	/** 获得DB导出到文本的域 */
	public List getDBToTxtRegion(TxtDBTask txtDBTask) {
		DataExportAction dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		DgCommonQuery dgCommonQuery = new DgCommonQuery();
		List<TxtDBTaskDetail> txtDBTaskDetailList = new ArrayList<TxtDBTaskDetail>();

		List<DBToTxtRegion> dataSource = dataExportAction
				.findDBToTxtRegionNotInTxtDBTask(new Request(CommonVars
						.getCurrUser(), true), txtDBTask);

		for (DBToTxtRegion dbToTxtRegion : dataSource) {
			TxtDBTaskDetail txtDBTaskDetail = new TxtDBTaskDetail();
			txtDBTaskDetail.setTaskType(TxtDBTaskDetail.DB_TO_TXT_REGION_TYPE);
			txtDBTaskDetail.setDbToTxtRegion(dbToTxtRegion);
			txtDBTaskDetailList.add(txtDBTaskDetail);
		}

		dgCommonQuery.setDataSource(txtDBTaskDetailList);
		List<JTableListColumn> tableColumns = new Vector<JTableListColumn>();
		tableColumns.add(new JTableListColumn("DB导出到文本域名称",
				"dbToTxtRegion.regionName", 180));
		tableColumns.add(new JTableListColumn("目的文件路径",
				"dbToTxtRegion.destFilePath", 100));
		tableColumns.add(new JTableListColumn("视图名",
				"dbToTxtRegion.srcJDBCView.name", 100));
		tableColumns.add(new JTableListColumn("视图数据源",
				"dbToTxtRegion.srcJDBCView.jdbcDataSource.name", 80));
		DgCommonQuery.setTableColumns(tableColumns);
		DgCommonQuery.setSingleResult(false);
		dgCommonQuery.setVisible(true);
		if (dgCommonQuery.isOk()) {
			return dgCommonQuery.getReturnList();
		}
		return null;
	}

}
