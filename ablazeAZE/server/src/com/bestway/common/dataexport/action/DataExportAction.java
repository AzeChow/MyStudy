/*
 * Created on 2004-9-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.action;

import java.util.List;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.dataexport.entity.DBToTxtRegion;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.JDBCParameter;
import com.bestway.common.dataexport.entity.JDBCRegion;
import com.bestway.common.dataexport.entity.JDBCRegionSetup;
import com.bestway.common.dataexport.entity.JDBCSqlEvent;
import com.bestway.common.dataexport.entity.JDBCTask;
import com.bestway.common.dataexport.entity.JDBCTaskDetail;
import com.bestway.common.dataexport.entity.JDBCView;
import com.bestway.common.dataexport.entity.TempJDBCColumn;
import com.bestway.common.dataexport.entity.ThreadData;
import com.bestway.common.dataexport.entity.TxtDBTask;
import com.bestway.common.dataexport.entity.TxtDBTaskDetail;
import com.bestway.common.dataexport.entity.TxtToDBRegion;
import com.bestway.common.dataexport.entity.TxtToDBRegionSetup;
import com.bestway.common.dataexport.logic.DataExportUtils;
import com.bestway.common.tools.entity.TempResultSet;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface DataExportAction {

	/** 保存 JDBCDataSource */
	JDBCDataSource saveJDBCDataSource(Request request,
			JDBCDataSource jdbcDataSource);

	/** 删除 JDBCDataSource */
	void deleteJDBCDataSource(Request request, JDBCDataSource jdbcDataSource);

	/** 查找 JDBCDataSource */
	List<JDBCDataSource> findJDBCDataSource(Request request);

	/** 保存 JDBCParameter */
	JDBCParameter saveJDBCParameter(Request request, JDBCParameter jdbcParameter);

	/** 删除 JDBCParameter */
	void deleteJDBCParameter(Request request, JDBCParameter jdbcParameter);

	/** 查找 JDBCParameter */
	List<JDBCParameter> findJDBCParameter(Request request);

	/** 保存 JDBCRegion */
	JDBCRegion saveJDBCRegion(Request request, JDBCRegion jdbcRegion);

	/** 删除 JDBCRegion */
	void deleteJDBCRegion(Request request, JDBCRegion jdbcRegion);

	/** 查找 JDBCRegion */
	List<JDBCRegion> findJDBCRegion(Request request);

	/** 保存 JDBCRegionSetup */
	JDBCRegionSetup saveJDBCRegionSetup(Request request,
			JDBCRegionSetup jdbcRegionSetup);

	/** 删除 JDBCRegionSetup */
	void deleteJDBCRegionSetup(Request request, JDBCRegionSetup jdbcRegionSetup);

	/** 查找 JDBCRegionSetup */
	List<JDBCRegionSetup> findJDBCRegionSetup(Request request);

	/** 保存 JDBCSqlEvent */
	JDBCSqlEvent saveJDBCSqlEvent(Request request, JDBCSqlEvent jdbcSqlEvent);

	/** 删除 JDBCSqlEvent */
	void deleteJDBCSqlEvent(Request request, JDBCSqlEvent jdbcSqlEvent);

	/** 查找 JDBCSqlEvent */
	List<JDBCSqlEvent> findJDBCSqlEvent(Request request);

	/** 查找 JDBCSqlEvent */
	List<JDBCSqlEvent> findJDBCSqlEvent(Request request,
			JDBCDataSource jdbcDataSource);

	/** 保存 JDBCTask */
	JDBCTask saveJDBCTask(Request request, JDBCTask jdbcTask);

	/** 删除 JDBCTask */
	void deleteJDBCTask(Request request, JDBCTask jdbcTask);

	/** 查找 JDBCTask */
	List<JDBCTask> findJDBCTask(Request request);

	/** 保存 JDBCTaskDetail */
	JDBCTaskDetail saveJDBCTaskDetail(Request request,
			JDBCTaskDetail jdbcTaskDetail);

	/** 删除 JDBCTaskDetail */
	void deleteJDBCTaskDetail(Request request, JDBCTaskDetail jdbcTaskDetail);

	/** 查找 JDBCTaskDetail */
	List<JDBCTaskDetail> findJDBCTaskDetail(Request request);

	/** 保存 JDBCView */
	JDBCView saveJDBCView(Request request, JDBCView jdbcView);

	/** 删除 JDBCView */
	void deleteJDBCView(Request request, JDBCView jdbcView);

	/** 查找 JDBCView */
	List<JDBCView> findJDBCView(Request request);

	/** 保存 ThreadData */
	ThreadData saveThreadData(Request request, ThreadData threadData);

	/** 删除 ThreadData */
	void deleteThreadData(Request request, ThreadData threadData);

	/** 查找 ThreadData */
	List<ThreadData> findThreadData(Request request);

	/**
	 * 连接数据库
	 * 
	 * @param db
	 * @return
	 */
	boolean isConnect(String driverClassName, String url, String userName,
			String password);

	/** 执行 sql event */
	void execuJDBCSqlEvent(Request request, JDBCSqlEvent sqlEvent);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 * @return
	 */
	TempResultSet getTempResultSet(Request request,
			JDBCDataSource jdbcDataSource, String sql);

	/**获得只有列的数据集*/
	TempResultSet getNoDataTempResultSet(Request request,JDBCDataSource jdbcDataSource,
			String sql) ;
	
	/**
	 * connection 由连接池管理 没有执行,只是测试
	 * 
	 * @param sql
	 * @return
	 */
	int executeUpdateSql(Request request, JDBCDataSource jdbcDataSource,
			final String sql);

	/**
	 * create drop .. DDL connection 由连接池管理 没有执行,只是测试
	 * 
	 * @param sql
	 * @return
	 */
	boolean executeSql(Request request, JDBCDataSource jdbcDataSource,
			final String sql);

	/** 获得连接数据库表名 */
	List<String> getTableNames(Request request, JDBCDataSource jdbcDataSource);

	/** 获得连接数据库列名 */
	List<String> getColumnNames(Request request, JDBCDataSource jdbcDataSource,
			String tableName);

	/** 查找 JDBCView */
	List<JDBCView> findJDBCView(Request request, JDBCDataSource jdbcDataSource);

	/** 查找 JDBCRegion */
	List<JDBCRegion> findJDBCRegion(Request request, JDBCView jdbcView);

	/** 转抄 JDBCRegion */
	JDBCRegion copyJDBCRegion(Request request, JDBCRegion srcJDBCRegion);

	/** 查找 JDBCRegionSetup */
	List<JDBCRegionSetup> findJDBCRegionSetup(Request request,
			String jdbcRegionId);

	/** 获得连接数据库表列名 */
	List<TempJDBCColumn> getDestTempJDBCColumn(Request request,
			JDBCDataSource jdbcDataSource, String tableName);

	/** 获得连接数据库视图列名 */
	List<TempJDBCColumn> getSrcTempJDBCColumn(Request request,
			JDBCDataSource jdbcDataSource, String viewSql);

	/** 查找 JDBCTaskDetail */
	List<JDBCTaskDetail> findJDBCTaskDetail(Request request, JDBCTask jdbcTask);

	/** 查找 JDBCTask */
	boolean isExecute(Request request, JDBCTask jdbcTask);

	/** 查找 JDBCRegion 不存任务明细中的数据 */
	List<JDBCRegion> findJDBCRegionNotInJDBCTask(Request request,
			JDBCTask jdbcTask);

	/** 查找 JDBCSqlEvent 不存任务明细中的数据 */
	List<JDBCSqlEvent> findJDBCSqlEventNotInJDBCTask(Request request,
			JDBCTask jdbcTask);

	/** 查找 JDBCRegion */
	int findJDBCTaskDetailMaxSeqNum(Request request, JDBCTask jdbcTask);

	/** 查找 JDBCTask 的子任务 */
	List<JDBCTask> findSubJDBCTaskNotInJDBCTask(Request request,
			JDBCTask jdbcTask);

	/** 获得测试SQL */
	String getTestSql(Request request, String sql, String repateExpress);

	/** 获得测试SQL */
	void executeImportExportData(Request request, JDBCTask jdbcTask);

	/** 查找 ThreadData */
	ThreadData findThreadData(Request request, int type);

	/**
	 * 结束导入线程
	 * 
	 */
	void shutDownImportExportThread(Request request);

	/**
	 * 起动自动导入
	 */
	void startImportExportThread(Request request);

	// //////////////
	// 文本导入导出
	// //////////////
	/**
     * 检查数据源操作权限
     * 
     * @param request
     */
	public void checkDataSourceExportAuthority(Request request);
	/**
     * 检查事件设置权限
     * 
     * @param request
     */
	public void checkEventExportAuthority(Request request);
	/**
     * 检查SQL参数设置权限
     * 
     * @param request
     */
	public void checkSqlParameterExportAuthority(Request request);
	/**
     * 检查视图操作权限
     * 
     * @param request
     */
	public void checkViewExportAuthority(Request request);
	/**
     * 检查DB域定义管理权限
     * 
     * @param request
     */
	public void checkDBRegionExportAuthority(Request request);
	/**
     * 检查DB任务计划设置权限
     * 
     * @param request
     */
	public void checkDBTaskExportAuthority(Request request);
	/**
     * 检查执行DB导入导出权限
     * 
     * @param request
     */
	public void checkDBTaskExeExportAuthority(Request request);
	
	/**
     * 检查数据源操作权限
     * 
     * @param request
     */
	public void checkDataSourceOperateExportAuthority(Request request);
	/**
     * 检查事件设置权限
     * 
     * @param request
     */
	public void checkEventSetExportAuthority(Request request);
	/**
     * 检查SQL参数设置权限
     * 
     * @param request
     */
	public void checkSqlParameterSetExportAuthority(Request request);
	/**
     * 检查视图操作权限
     * 
     * @param request
     */
	public void checkViewOperateExportAuthority(Request request);
	/**
     * 检查文本域定义管理权限
     * 
     * @param request
     */
	public void checkTextFieldExportAuthority(Request request);
	/**
     * 检查文本任务计划设置权限
     * 
     * @param request
     */
	public void checkTextRegionExportAuthority(Request request);
	/**
     * 检查执行文本导入导出权限
     * 
     * @param request
     */
	public void checkTextTaskExeExportAuthority(Request request);

	/** 保存 DBToTxtRegion */
	DBToTxtRegion saveDBToTxtRegion(Request request, DBToTxtRegion dbToTxtRegion);

	/** 删除 DBToTxtRegion */
	void deleteDBToTxtRegion(Request request, DBToTxtRegion dbToTxtRegion);

	/** 查找 DBToTxtRegion */
	List<DBToTxtRegion> findDBToTxtRegion(Request request);
	
	/** 查找 DBToTxtRegion */
	List<DBToTxtRegion> findDBToTxtRegion(Request request,JDBCView jdbcView);

	/** 保存 TxtToDBRegion */
	TxtToDBRegion saveTxtToDBRegion(Request request, TxtToDBRegion txtToDBRegion);

	/** 删除 TxtToDBRegion */
	void deleteTxtToDBRegion(Request request, TxtToDBRegion txtToDBRegion);

	/** 查找 TxtToDBRegion */
	List<TxtToDBRegion> findTxtToDBRegion(Request request);

	/** 保存 TxtToDBRegionSetup */
	TxtToDBRegionSetup saveTxtToDBRegionSetup(Request request,
			TxtToDBRegionSetup txtToDBRegionSetup);

	/** 删除 TxtToDBRegionSetup */
	void deleteTxtToDBRegionSetup(Request request,
			TxtToDBRegionSetup txtToDBRegionSetup);

	/** 查找 TxtToDBRegionSetup */
	List<TxtToDBRegionSetup> findTxtToDBRegionSetup(Request request);

	/** 查找 TxtToDBRegionSetup */
	List<TxtToDBRegionSetup> findTxtToDBRegionSetup(Request request,
			String txtToDBRegionId);

	/** 保存 TxtTask */
	TxtDBTask saveTxtDBTask(Request request, TxtDBTask txtTask);

	/** 查找 TxtTask */
	List<TxtDBTask> findTxtDBTask(Request request);

	/** 保存 TxtTaskDetail */
	TxtDBTaskDetail saveTxtDBTaskDetail(Request request, TxtDBTaskDetail txtDBTaskDetail);

	/** 删除 TxtTaskDetail */
	void deleteTxtDBTaskDetail(Request request, TxtDBTaskDetail txtDBTaskDetail);

	/** 查找 TxtTaskDetail */
	List<TxtDBTaskDetail> findTxtDBTaskDetail(Request request);

	/** 查找 TxtTaskDetail */
//	List<TxtDBTaskDetail> findTxtDBTaskDetail(Request request, String txtTaskId);

	/** 转抄 TxtToDBRegion */
	TxtToDBRegion copyTxtToDBRegion(Request request,
			TxtToDBRegion srcTxtToDBRegion);

	/** 转抄 DBToTxtRegion */
	DBToTxtRegion copyDBToTxtRegion(Request request,
			DBToTxtRegion srcDBToTxtRegion);

	/** 删除 TxtTask */
	void deleteTxtDBTask(Request request, TxtDBTask jdbcTask);
	
	/** 查找 TxtDBTask */
	boolean isExecute(Request request, TxtDBTask txtDBTask);
	
	/** 查找 JDBCRegion */
	int findTxtDBTaskDetailMaxSeqNum(Request request,TxtDBTask txtDBTask);
	
	
	/** 查找 DBToTxtRegion */
	List<DBToTxtRegion> findDBToTxtRegionNotInTxtDBTask(Request request,TxtDBTask txtDBTask);
	
	
	/** 查找 TxtToDBRegion */
	List<TxtToDBRegion> findTxtToDBRegionNotInTxtDBTask(Request request,TxtDBTask txtDBTask);	
	

	/** 查找 TxtDBTask 的子任务 */
	List<TxtDBTask> findSubTxtDBTaskNotInTxtDBTask(Request request,TxtDBTask txtDBTask);
	
	/** 查找 TxtDBTaskDetail */
	List<TxtDBTaskDetail> findTxtDBTaskDetail(Request request,TxtDBTask txtDBTask);
	
	/** 执行事件 */
	void txtDBTaskEventExecute(Request request,Company company,
			TxtDBTaskDetail txtDBTaskDetail, int seqNum);
	
	/** 获得执行DB导出到文本的数据源 */
	TempResultSet getDBViewData(Request request,TxtDBTaskDetail txtDBTaskDetail);
	
	/** 执行文本导出到DB */
	void executeTxtToDBTask(Request request,TxtToDBRegion txtToDBRegion,
			TempResultSet tempResultSet);

}