/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.action;

import java.util.List;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.dataexport.dao.DBExportDao;
import com.bestway.common.dataexport.dao.TextExportDao;
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
import com.bestway.common.dataexport.logic.DBAutoExportLogic;
import com.bestway.common.dataexport.logic.DBExportLogic;
import com.bestway.common.dataexport.logic.DBImportExportLogic;
import com.bestway.common.dataexport.logic.DataExportUtils;
import com.bestway.common.dataexport.logic.TextExportLogic;
import com.bestway.common.dataexport.logic.TextImportExportLogic;
import com.bestway.common.tools.entity.TempResultSet;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@AuthorityClassAnnotation(caption = "数据导出接口", index = 23)
public class DataExportActionImpl extends BaseActionImpl implements
		DataExportAction {
	private DBExportDao				dbExportDao				= null;
	private TextExportDao			textExportDao			= null;
	private DBExportLogic			dbExportLogic			= null;
	private TextExportLogic			textExportLogic			= null;
	private DBAutoExportLogic		dbAutoExportLogic		= null;
	private DBImportExportLogic		dbImportExportLogic		= null;
	private TextImportExportLogic	textImportExportLogic	= null;

	
	public TextImportExportLogic getTextImportExportLogic() {
		return textImportExportLogic;
	}

	public void setTextImportExportLogic(TextImportExportLogic textImportExportLogic) {
		this.textImportExportLogic = textImportExportLogic;
	}

	public DBImportExportLogic getDbImportExportLogic() {
		return dbImportExportLogic;
	}

	public void setDbImportExportLogic(DBImportExportLogic dbImportExportLogic) {
		this.dbImportExportLogic = dbImportExportLogic;
	}

	public DBAutoExportLogic getDbAutoExportLogic() {
		return dbAutoExportLogic;
	}

	public void setDbAutoExportLogic(DBAutoExportLogic dbAutoExportLogic) {
		this.dbAutoExportLogic = dbAutoExportLogic;
	}

	public DBExportDao getDbExportDao() {
		return dbExportDao;
	}

	public void setDbExportDao(DBExportDao dbExportDao) {
		this.dbExportDao = dbExportDao;
	}

	public DBExportLogic getDbExportLogic() {
		return dbExportLogic;
	}

	public void setDbExportLogic(DBExportLogic dbExportLogic) {
		this.dbExportLogic = dbExportLogic;
	}

	public TextExportDao getTextExportDao() {
		return textExportDao;
	}

	public void setTextExportDao(TextExportDao textExportDao) {
		this.textExportDao = textExportDao;
	}

	public TextExportLogic getTextExportLogic() {
		return textExportLogic;
	}

	public void setTextExportLogic(TextExportLogic textExportLogic) {
		this.textExportLogic = textExportLogic;
	}

	// /////////////////////////////
	// method
	// /////////////////////////////

	/** 保存 JDBCDataSource */
	public JDBCDataSource saveJDBCDataSource(Request request,
			JDBCDataSource jdbcDataSource) {
		return this.dbExportDao.saveJDBCDataSource(jdbcDataSource);
	}

	/** 删除 JDBCDataSource */
	public void deleteJDBCDataSource(Request request,
			JDBCDataSource jdbcDataSource) {
		this.dbExportDao.deleteJDBCDataSource(jdbcDataSource);
	}

	/** 查找 JDBCDataSource */
	public List<JDBCDataSource> findJDBCDataSource(Request request) {
		return this.dbExportDao.findJDBCDataSource();
	}

	/** 保存 JDBCParameter */
	public JDBCParameter saveJDBCParameter(Request request,
			JDBCParameter jdbcParameter) {
		return this.dbExportDao.saveJDBCParameter(jdbcParameter);
	}

	/** 删除 JDBCParameter */
	public void deleteJDBCParameter(Request request, JDBCParameter jdbcParameter) {
		this.dbExportDao.deleteJDBCParameter(jdbcParameter);
	}

	/** 查找 JDBCParameter */
	public List<JDBCParameter> findJDBCParameter(Request request) {
		return this.dbExportDao.findJDBCParameter();
	}

	/** 保存 JDBCRegion */
	public JDBCRegion saveJDBCRegion(Request request, JDBCRegion jdbcRegion) {
		return this.dbExportDao.saveJDBCRegion(jdbcRegion);
	}

	/** 删除 JDBCRegion */
	public void deleteJDBCRegion(Request request, JDBCRegion jdbcRegion) {
		this.dbExportDao.deleteJDBCRegion(jdbcRegion);
	}

	/** 查找 JDBCRegion */
	public List<JDBCRegion> findJDBCRegion(Request request) {
		return this.dbExportDao.findJDBCRegion();
	}

	/** 保存 JDBCRegionSetup */
	public JDBCRegionSetup saveJDBCRegionSetup(Request request,
			JDBCRegionSetup jdbcRegionSetup) {
		return this.dbExportDao.saveJDBCRegionSetup(jdbcRegionSetup);
	}

	/** 删除 JDBCRegionSetup */
	public void deleteJDBCRegionSetup(Request request,
			JDBCRegionSetup jdbcRegionSetup) {
		this.dbExportDao.deleteJDBCRegionSetup(jdbcRegionSetup);
	}

	/** 查找 JDBCRegionSetup */
	public List<JDBCRegionSetup> findJDBCRegionSetup(Request request) {
		return this.dbExportDao.findJDBCRegionSetup();
	}

	/** 保存 JDBCSqlEvent */
	public JDBCSqlEvent saveJDBCSqlEvent(Request request,
			JDBCSqlEvent jdbcSqlEvent) {
		dbExportDao.saveJDBCSqlEvent(jdbcSqlEvent);
		return jdbcSqlEvent;
	}

	/** 删除 JDBCSqlEvent */
	public void deleteJDBCSqlEvent(Request request, JDBCSqlEvent jdbcSqlEvent) {
		this.dbExportDao.deleteJDBCSqlEvent(jdbcSqlEvent);
	}

	/** 查找 JDBCSqlEvent */
	public List<JDBCSqlEvent> findJDBCSqlEvent(Request request) {
		return this.dbExportDao.findJDBCSqlEvent();
	}

	/** 查找 JDBCSqlEvent */
	public List<JDBCSqlEvent> findJDBCSqlEvent(Request request,
			JDBCDataSource jdbcDataSource) {
		return this.dbExportDao.findJDBCSqlEvent(jdbcDataSource);
	}

	/** 保存 JDBCTask */
	public JDBCTask saveJDBCTask(Request request, JDBCTask jdbcTask) {
		return this.dbExportDao.saveJDBCTask(jdbcTask);
	}

	/** 删除 JDBCTask */
	public void deleteJDBCTask(Request request, JDBCTask jdbcTask) {
		this.dbExportLogic.deleteJDBCTask(jdbcTask);
	}

	/** 查找 JDBCTask */
	public List<JDBCTask> findJDBCTask(Request request) {
		return this.dbExportDao.findJDBCTask();
	}

	/** 保存 JDBCTaskDetail */
	public JDBCTaskDetail saveJDBCTaskDetail(Request request,
			JDBCTaskDetail jdbcTaskDetail) {
		return this.dbExportDao.saveJDBCTaskDetail(jdbcTaskDetail);
	}

	/** 删除 JDBCTaskDetail */
	public void deleteJDBCTaskDetail(Request request,
			JDBCTaskDetail jdbcTaskDetail) {
		this.dbExportDao.deleteJDBCTaskDetail(jdbcTaskDetail);
	}

	/** 查找 JDBCTaskDetail */
	public List<JDBCTaskDetail> findJDBCTaskDetail(Request request) {
		return this.dbExportDao.findJDBCTaskDetail();
	}

	/** 保存 JDBCView */
	public JDBCView saveJDBCView(Request request, JDBCView jdbcView) {
		return this.dbExportDao.saveJDBCView(jdbcView);
	}

	/** 删除 JDBCView */
	public void deleteJDBCView(Request request, JDBCView jdbcView) {
		this.dbExportDao.deleteJDBCView(jdbcView);
	}

	/** 查找 JDBCView */
	public List<JDBCView> findJDBCView(Request request) {
		return this.dbExportDao.findJDBCView();
	}

	/** 保存 ThreadData */
	public ThreadData saveThreadData(Request request, ThreadData threadData) {
		return this.dbExportDao.saveThreadData(threadData);
	}

	/** 删除 ThreadData */
	public void deleteThreadData(Request request, ThreadData threadData) {
		this.dbExportDao.deleteThreadData(threadData);
	}

	/** 查找 ThreadData */
	public List<ThreadData> findThreadData(Request request) {
		return this.dbExportDao.findThreadData();
	}

	/**
	 * 连接数据库
	 * 
	 * @param db
	 * @return
	 */
	public boolean isConnect(String driverClassName, String url,
			String userName, String password) {
		return dbExportLogic
				.isConnect(driverClassName, url, userName, password);
	}

	/** 执行 sql event */
	public void execuJDBCSqlEvent(Request request, JDBCSqlEvent sqlEvent) {
		dbExportLogic.execuJDBCSqlEvent(sqlEvent);
	}

	/** 获得只有列的数据集 */
	public TempResultSet getNoDataTempResultSet(Request request,
			JDBCDataSource jdbcDataSource, String sql) {
		return this.dbExportLogic.getNoDataTempResultSet(jdbcDataSource, sql);
	}

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 * @return
	 */
	public TempResultSet getTempResultSet(Request request,
			JDBCDataSource jdbcDataSource, String sql) {
		return this.dbExportLogic.getTempResultSet(jdbcDataSource, sql);
	}

	/**
	 * connection 由连接池管理 没有执行,只是测试
	 * 
	 * @param sql
	 * @return
	 */
	public int executeUpdateSql(Request request, JDBCDataSource jdbcDataSource,
			final String sql) {
		return this.dbExportLogic.executeUpdateSql(jdbcDataSource, sql);
	}

	/**
	 * create drop .. DDL connection 由连接池管理 没有执行,只是测试
	 * 
	 * @param sql
	 * @return
	 */
	public boolean executeSql(Request request, JDBCDataSource jdbcDataSource,
			final String sql) {
		return this.dbExportLogic.executeSql(jdbcDataSource, sql);
	}

	/** 获得连接数据库表名 */
	public List<String> getTableNames(Request request,
			JDBCDataSource jdbcDataSource) {
		return this.dbExportLogic.getTableNames(jdbcDataSource);
	}

	/** 获得连接数据库列名 */
	public List<String> getColumnNames(Request request,
			JDBCDataSource jdbcDataSource, String tableName) {
		return this.dbExportLogic.getColumnNames(jdbcDataSource, tableName);
	}

	/** 查找 JDBCView */
	public List<JDBCView> findJDBCView(Request request,
			JDBCDataSource jdbcDataSource) {
		return this.dbExportDao.findJDBCView(jdbcDataSource);
	}

	/** 查找 JDBCRegion */
	public List<JDBCRegion> findJDBCRegion(Request request, JDBCView jdbcView) {
		return this.dbExportDao.findJDBCRegion(jdbcView);
	}

	/** 转抄 JDBCRegion */
	public JDBCRegion copyJDBCRegion(Request request, JDBCRegion srcJDBCRegion) {
		return this.dbExportLogic.copyJDBCRegion(srcJDBCRegion);
	}

	/** 查找 JDBCRegionSetup */
	public List<JDBCRegionSetup> findJDBCRegionSetup(Request request,
			String jdbcRegionId) {
		return this.dbExportDao.findJDBCRegionSetup(jdbcRegionId);
	}

	/** 获得连接数据库表列名 */
	public List<TempJDBCColumn> getDestTempJDBCColumn(Request request,
			JDBCDataSource jdbcDataSource, String tableName) {
		return this.dbExportLogic.getDestTempJDBCColumn(jdbcDataSource,
				tableName);
	}

	/** 获得连接数据库视图列名 */
	public List<TempJDBCColumn> getSrcTempJDBCColumn(Request request,
			JDBCDataSource jdbcDataSource, String viewSql) {
		return this.dbExportLogic.getSrcTempJDBCColumn(jdbcDataSource, viewSql);
	}

	/** 查找 JDBCTaskDetail */
	public List<JDBCTaskDetail> findJDBCTaskDetail(Request request,
			JDBCTask jdbcTask) {
		return this.dbExportDao.findJDBCTaskDetail(jdbcTask);
	}

	/** 查找 JDBCTask */
	public boolean isExecute(Request request, JDBCTask jdbcTask) {
		return this.dbExportDao.isExecute(jdbcTask);
	}

	/** 查找 JDBCRegion 不存任务明细中的数据 */
	public List<JDBCRegion> findJDBCRegionNotInJDBCTask(Request request,
			JDBCTask jdbcTask) {
		return this.dbExportDao.findJDBCRegionNotInJDBCTask(jdbcTask);
	}

	/** 查找 JDBCSqlEvent 不存任务明细中的数据 */
	public List<JDBCSqlEvent> findJDBCSqlEventNotInJDBCTask(Request request,
			JDBCTask jdbcTask) {
		return this.dbExportDao.findJDBCSqlEventNotInJDBCTask(jdbcTask);
	}

	/** 查找 JDBCRegion */
	public int findJDBCTaskDetailMaxSeqNum(Request request, JDBCTask jdbcTask) {
		return this.dbExportDao.findJDBCTaskDetailMaxSeqNum(jdbcTask);
	}

	/** 查找 JDBCTask 的子任务 */
	public List<JDBCTask> findSubJDBCTaskNotInJDBCTask(Request request,
			JDBCTask jdbcTask) {
		return this.dbExportDao.findSubJDBCTaskNotInJDBCTask(jdbcTask);
	}

	/** 获得测试SQL */
	public String getTestSql(Request request, String sql, String repateExpress) {
		return DataExportUtils.getTestSql(sql, repateExpress);
	}

	/** 获得测试SQL */
	public void executeImportExportData(Request request, JDBCTask jdbcTask) {
		this.dbImportExportLogic.executeImportExportData(jdbcTask,
				(Company) CommonUtils.getCompany());
	}

	/** 查找 ThreadData */
	public ThreadData findThreadData(Request request, int type) {
		return this.dbExportDao.findThreadData(type);
	}

	/**
	 * 结束导入线程
	 * 
	 */
	public void shutDownImportExportThread(Request request) {
		this.dbAutoExportLogic.shutDownThread();
	}

	/**
	 * 起动自动导入
	 */
	public void startImportExportThread(Request request) { // 服务启动
		this.dbAutoExportLogic.startThread();
	}

	// //////////////
	// 文本导入导出
	// //////////////
	/**
     * 检查数据源操作权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "DB导出管理--数据源操作", index = 1)
	public void checkDataSourceExportAuthority(Request request){
		
	}
	/**
     * 检查事件设置权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "DB导出管理--事件设置", index = 2)
	public void checkEventExportAuthority(Request request){
		
	}
	/**
     * 检查SQL参数设置权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "DB导出管理--SQL参数设置", index = 3)
	public void checkSqlParameterExportAuthority(Request request){
		
	}
	/**
     * 检查视图操作权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "DB导出管理--视图操作", index = 4)
	public void checkViewExportAuthority(Request request){
		
	}
	/**
     * 检查DB域定义管理权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "DB导出管理--DB域定义管理", index = 5)
	public void checkDBRegionExportAuthority(Request request){
		
	}
	/**
     * 检查DB任务计划设置权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "DB导出管理--DB任务计划设置", index = 6)
	public void checkDBTaskExportAuthority(Request request){
		
	}
	/**
     * 检查执行DB导入导出权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "DB导出管理--执行DB导入导出", index = 7)
	public void checkDBTaskExeExportAuthority(Request request){
		
	}
	
	/**
     * 检查数据源操作权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "文本导出管理--数据源操作", index = 8)
	public void checkDataSourceOperateExportAuthority(Request request){
		
	}
	/**
     * 检查事件设置权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "文本导出管理--事件设置", index = 9)
	public void checkEventSetExportAuthority(Request request){
		
	}
	/**
     * 检查SQL参数设置权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "文本导出管理--SQL参数设置", index = 10)
	public void checkSqlParameterSetExportAuthority(Request request){
		
	}
	/**
     * 检查视图操作权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "文本导出管理--视图操作", index = 11)
	public void checkViewOperateExportAuthority(Request request){
		
	}
	/**
     * 检查文本域定义管理权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "文本导出管理--文本域定义管理", index = 12)
	public void checkTextFieldExportAuthority(Request request){
		
	}
	/**
     * 检查文本任务计划设置权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "文本导出管理--文本任务计划设置", index = 13)
	public void checkTextRegionExportAuthority(Request request){
		
	}
	/**
     * 检查执行文本导入导出权限
     * 
     * @param request
     */
	@AuthorityFunctionAnnotation(caption = "文本导出管理--执行文本导入导出", index = 14)
	public void checkTextTaskExeExportAuthority(Request request){
		
	}

	/** 保存 DBToTxtRegion */
	public DBToTxtRegion saveDBToTxtRegion(Request request,
			DBToTxtRegion dbToTxtRegion) {
		this.textExportDao.saveDBToTxtRegion(dbToTxtRegion);
		return dbToTxtRegion;
	}

	/** 删除 DBToTxtRegion */
	public void deleteDBToTxtRegion(Request request, DBToTxtRegion dbToTxtRegion) {
		this.textExportDao.deleteDBToTxtRegion(dbToTxtRegion);
	}

	/** 查找 DBToTxtRegion */
	public List<DBToTxtRegion> findDBToTxtRegion(Request request) {
		return this.textExportDao.findDBToTxtRegion();
	}

	/** 保存 TxtToDBRegion */
	public TxtToDBRegion saveTxtToDBRegion(Request request,
			TxtToDBRegion txtToDBRegion) {
		this.textExportDao.saveTxtToDBRegion(txtToDBRegion);
		return txtToDBRegion;
	}

	/** 删除 TxtToDBRegion */
	public void deleteTxtToDBRegion(Request request, TxtToDBRegion txtToDBRegion) {
		this.textExportDao.deleteTxtToDBRegion(txtToDBRegion);
	}

	/** 查找 TxtToDBRegion */
	public List<TxtToDBRegion> findTxtToDBRegion(Request request) {
		return this.textExportDao.findTxtToDBRegion();
	}

	/** 保存 TxtToDBRegionSetup */
	public TxtToDBRegionSetup saveTxtToDBRegionSetup(Request request,
			TxtToDBRegionSetup txtToDBRegionSetup) {
		this.textExportDao.saveTxtToDBRegionSetup(txtToDBRegionSetup);
		return txtToDBRegionSetup;
	}

	/** 删除 TxtToDBRegionSetup */
	public void deleteTxtToDBRegionSetup(Request request,
			TxtToDBRegionSetup txtToDBRegionSetup) {
		this.textExportDao.deleteTxtToDBRegionSetup(txtToDBRegionSetup);
	}

	/** 查找 TxtToDBRegionSetup */
	public List<TxtToDBRegionSetup> findTxtToDBRegionSetup(Request request) {
		return this.textExportDao.findTxtToDBRegionSetup();
	}

	/** 查找 TxtToDBRegionSetup */
	public List<TxtToDBRegionSetup> findTxtToDBRegionSetup(Request request,
			String txtToDBRegionId) {
		return this.textExportDao.findTxtToDBRegionSetup(txtToDBRegionId);
	}

	/** 保存 TxtTask */
	public TxtDBTask saveTxtDBTask(Request request, TxtDBTask txtTask) {
		this.textExportDao.saveTxtDBTask(txtTask);
		return txtTask;
	}

	/** 查找 TxtTask */
	public List<TxtDBTask> findTxtDBTask(Request request) {
		return this.textExportDao.findTxtDBTask();
	}

	/** 保存 TxtTaskDetail */
	public TxtDBTaskDetail saveTxtDBTaskDetail(Request request,
			TxtDBTaskDetail txtDBTaskDetail) {
		this.textExportDao.saveTxtDBTaskDetail(txtDBTaskDetail);
		return txtDBTaskDetail;
	}

	/** 删除 TxtTaskDetail */
	public void deleteTxtDBTaskDetail(Request request,
			TxtDBTaskDetail txtDBTaskDetail) {
		this.textExportDao.deleteTxtDBTaskDetail(txtDBTaskDetail);
	}

	/** 查找 TxtTaskDetail */
	public List<TxtDBTaskDetail> findTxtDBTaskDetail(Request request) {
		return this.textExportDao.findTxtDBTaskDetail();
	}

	/** 查找 TxtTaskDetail */
	// public List<TxtDBTaskDetail> findTxtDBTaskDetail(Request request,
	// String txtTaskId) {
	// return this.textExportDao.findTxtDBTaskDetail(txtTaskId);
	// }
	/** 转抄 TxtToDBRegion */
	public TxtToDBRegion copyTxtToDBRegion(Request request,
			TxtToDBRegion srcTxtToDBRegion) {
		return this.textExportLogic.copyTxtToDBRegion(srcTxtToDBRegion);
	}

	/** 转抄 DBToTxtRegion */
	public DBToTxtRegion copyDBToTxtRegion(Request request,
			DBToTxtRegion srcDBToTxtRegion) {
		return this.textExportLogic.copyDBToTxtRegion(srcDBToTxtRegion);
	}

	/** 删除 TxtTask */
	public void deleteTxtDBTask(Request request, TxtDBTask jdbcTask) {
		this.textExportLogic.deleteTxtDBTask(jdbcTask);
	}

	/** 查找 DBToTxtRegion */
	public List<DBToTxtRegion> findDBToTxtRegion(Request request,
			JDBCView jdbcView) {
		return this.textExportDao.findDBToTxtRegion(jdbcView);
	}

	/** 查找 TxtDBTask */
	public boolean isExecute(Request request, TxtDBTask txtDBTask) {
		return this.textExportDao.isExecute(txtDBTask);
	}

	/** 查找 JDBCRegion */
	public int findTxtDBTaskDetailMaxSeqNum(Request request, TxtDBTask txtDBTask) {
		return this.textExportDao.findTxtDBTaskDetailMaxSeqNum(txtDBTask);
	}

	/** 查找 DBToTxtRegion */
	public List<DBToTxtRegion> findDBToTxtRegionNotInTxtDBTask(Request request,
			TxtDBTask txtDBTask) {
		return this.textExportDao.findDBToTxtRegionNotInTxtDBTask(txtDBTask);
	}

	/** 查找 TxtToDBRegion */
	public List<TxtToDBRegion> findTxtToDBRegionNotInTxtDBTask(Request request,
			TxtDBTask txtDBTask) {
		return this.textExportDao.findTxtToDBRegionNotInTxtDBTask(txtDBTask);
	}

	/** 查找 TxtDBTask 的子任务 */
	public List<TxtDBTask> findSubTxtDBTaskNotInTxtDBTask(Request request,
			TxtDBTask txtDBTask) {
		return this.textExportDao.findSubTxtDBTaskNotInTxtDBTask(txtDBTask);
	}

	/** 查找 TxtDBTaskDetail */
	public List<TxtDBTaskDetail> findTxtDBTaskDetail(Request request,
			TxtDBTask txtDBTask) {
		return this.textExportDao.findTxtDBTaskDetail(txtDBTask);
	}

	
	/** 执行事件 */
	public void txtDBTaskEventExecute(Request request,Company company,
			TxtDBTaskDetail txtDBTaskDetail, int seqNum) {
		this.textImportExportLogic.txtDBTaskEventExecute(company,txtDBTaskDetail,seqNum);
	}
	
	
	/** 获得执行DB导出到文本的数据源 */
	public TempResultSet getDBViewData(Request request,TxtDBTaskDetail txtDBTaskDetail) {
		return this.textImportExportLogic.getDBViewData(txtDBTaskDetail);
	}
	
	/** 执行文本导出到DB */
	public void executeTxtToDBTask(Request request,TxtToDBRegion txtToDBRegion,
			TempResultSet tempResultSet) {
		this.textImportExportLogic.executeTxtToDBTask(txtToDBRegion,tempResultSet);
	}
}
