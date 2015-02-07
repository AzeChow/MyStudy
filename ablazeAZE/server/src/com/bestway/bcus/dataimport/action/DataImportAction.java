/*
 * Created on 2004-9-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.action;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.DBDataExecuSql;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dataimport.entity.DBFormat;
import com.bestway.bcus.dataimport.entity.DBFormatSetup;
import com.bestway.bcus.dataimport.entity.DBQuerySql;
import com.bestway.bcus.dataimport.entity.DBTaskEx;
import com.bestway.bcus.dataimport.entity.DBTaskSel;
import com.bestway.bcus.dataimport.entity.DBView;
import com.bestway.bcus.dataimport.entity.DriverList;
import com.bestway.bcus.dataimport.entity.FieldList;
import com.bestway.bcus.dataimport.entity.ParameterList;
import com.bestway.bcus.dataimport.entity.SuperClassList;
import com.bestway.bcus.dataimport.entity.ThreadList;
import com.bestway.bcus.dataimport.entity.TxtFormat;
import com.bestway.bcus.dataimport.entity.TxtLog;
import com.bestway.bcus.dataimport.entity.TxtTask;
import com.bestway.bcus.dataimport.entity.TxtTaskEx;
import com.bestway.bcus.dataimport.entity.TxtTaskSel;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.entity.BaseCompany;

/**
 * @author lin
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface DataImportAction {
	/**
	 * 显示本导入格式
	 * 
	 * @param request
	 * @param txtTask
	 * @return
	 */
	List findTxtTask(Request request);

	public void exportBackUp(Request request, String fileName, String tableName)
			throws SQLException;

	public void execuFileSql(Request request, String sql) throws SQLException;

	/**
	 * 保存文本导入格式
	 * 
	 * @param request
	 * @param txtTask
	 * @return
	 */
	TxtTask saveTxtTask(Request request, TxtTask txtTask);

	/**
	 * 删除文本导入格式
	 * 
	 * @param request
	 * @param txtTask
	 * @return
	 */
	void deleteTxtTask(Request request, TxtTask txtTask);

	List findTxtFormat(Request request, TxtTask txtTask);

	TxtFormat saveTxtFormat(Request request, TxtFormat txtFormat);

	void deleteTxtFormat(Request request, TxtFormat txtFormat);

	List findTxtTaskEx(Request request);

	TxtTaskEx saveTxtTaskEx(Request request, TxtTaskEx txtTaskEx);

	void deleteTxtTaskEx(Request request, TxtTaskEx txtTaskEx);

	List findTxtTaskSel(Request request, TxtTaskEx fistTxtTaskEx);

	TxtTaskSel saveTxtTaskSel(Request request, TxtTaskSel txtTaskSel);

	void deleteTxtTaskSel(Request request, TxtTaskSel txtTaskSel);

	List findTxtTaskSelTaskTaskEx(Request request, TxtTaskEx txtTaskEx);

	List findTxtTaskTaskTaskSel(Request request, TxtTaskEx txtTaskEx);

	public List findClassList(Request request);

	public List findFieldList(Request request, ClassList classList);

	public ClassList saveClassList(Request request, ClassList classList);

	public FieldList saveFieldList(Request request, FieldList FieldList);

	public String getNum(Request request, String className, ClassList classList);

	public String getFieldFormatNum(Request request, String className,
			TxtTask txtTask);

	public void deleteAllFormat(Request request, TxtTask txtTask);

	public void deleteAllTxtTaskSel(Request request, TxtTaskEx txtTaskEx);

	public List findTxtTaskFromTxtTask(Request request, TxtTaskEx txtTaskEx);

	public TxtTask findTxtTaskBytaskname(Request request, String taskname);

	public List findFieldListFromTxtFormat(Request request,
			ClassList classList, TxtTask txtTask);

	public void saveTxtInput(Request request, Object obj);

	public List findRepeatList(Request request, String obj, String key,
			String value, Boolean isCompanyId);

	public void deleteTxtInput(Request request, Object obj);

	public List findObjList(Request request, String obj, String field,
			String value, Boolean isCompanyId);

	public List findTxtLog(Request request);

	public TxtLog saveTxtLog(Request request, TxtLog txtLog);

	public void deleteTxtLog(Request request);

	public void changeIsTitle(Request request, Boolean isTitleRow);

	public List findThreadList(Request request, String type);

	public void saveThreadList(Request request, ThreadList obj);

	public void deleteThread(Request request, String type);

	public List findDBDataRoot(Request request);

	public DBDataRoot saveDBDataRoot(Request request, DBDataRoot obj);

	public void deleteDBDataRoot(Request request, DBDataRoot obj);

	public List findDBView(Request request, DBDataRoot db);

	public DBView saveDBView(Request request, DBView obj);

	public List findDBTaskEx(Request request);

	public List findDBTaskSel(Request request, DBTaskEx dbTaskEx);

	public DBTaskEx saveDBTaskEx(Request request, DBTaskEx dbTaskEx);

	public void deleteAllDBTaskSel(Request request, DBTaskEx dbTaskEx);

	public void deleteDBTaskEx(Request request, DBTaskEx dbTaskEx);

	public void deleteDBTaskSel(Request request, DBTaskSel dbTaskSel);

	public List findDBFormatSetup(Request request, DBFormat dbFormat);

	public List intoData(Request request, DBFormat dbFormat,
			BaseCompany company, String deal, String inputSelect, List list,
			Hashtable hs);

	public List findView(Request request);

	public List findDBForByDBView(Request request, DBView dbView);

	public DBFormat saveDBFormat(Request request, DBFormat obj);

	public DBFormatSetup saveDBFormatSetup(Request request, DBFormatSetup obj);

	public void deleteDBFormatSetup(Request request, DBFormatSetup obj);

	public void deleteAllSetup(Request request, DBFormat dbFormat);

	public void deleteDBFormat(Request request, DBFormat obj);

	public void deleteFieldList(Request request, FieldList obj);

	public void deleteClassList(Request request, ClassList obj);

	public void deleteAllField(Request request, ClassList obj);

	public void deleteDBView(Request request, DBView obj);

	public List findDBTaskFromDBTask(Request request, DBTaskEx dbTaskEx);

	public DBTaskSel saveDBTaskSel(Request request, DBTaskSel dbTaskSel);

	public ClassList findbyid(Request request, String id);

	public void saveObject(Request request, Object obj);

	public String isConnect(Request request, String driverClassName,
			String url, String userName, String password);

	public Vector isNulltable(Request request, DBDataRoot db)
			throws SQLException;

	public String textSql(Request request, DBDataRoot db, String sql);

	public String checkDB(Request request, DBDataRoot db);

	public Vector getFieldName2(Request request,DBDataRoot dbroot, String sql, int columnCount) throws SQLException;

	public Vector getTableColumn(Request request, DBDataRoot db, String sql,
			int columnCount) throws SQLException;

	public Vector getFName(Request request, DBDataRoot db, String tableName)
			throws SQLException;

	public int getFieldsPart(Request request, DBDataRoot dbroot, String sql)
			throws SQLException;

	public List getSourceFields(Request request, DBFormat dbFormat);

	public Integer getNumToDriverList(Request request, String className,
			String seqNum);

	public DriverList saveDriverList(Request request, DriverList driverList);

	public List findDriverList(Request request);

	public DriverList findDriverListObj(Request request, Integer sortNo);

	/**
	 * 检查DB导入管理权限
	 * 
	 * @param request
	 */

//	void checkDBImportAuthority(Request request);
	/**
     * 检查视图参数设置权限
     * 
     * @param request
     */
    public void checkParameterListAuthority(Request request);
    /**
     * 检查数据源操作权限
     * 
     * @param request
     */
    public void checkDataRootAuthority(Request request); 
    /**
     * 检查事件设置权限
     * 
     * @param request
     */
    public void checkDataExecuSqlAuthority(Request request);
    /**
     * 检查视图操作权限
     * 
     * @param request
     */
    public void checkViewAuthority(Request request);
    /**
     * 检查域定义管理权限
     * 
     * @param request
     */
    public void checkFormatAuthority(Request request);
    /**
     * 检查任务计划设置权限
     * 
     * @param request
     */
    public void checkDBTaskAuthority(Request request);
    /**
     * 检查执行DB导入权限
     * 
     * @param request
     */
    public void checkDBImportAuthority(Request request);

	/**
	 * 检查文本导入管理权限
	 * 
	 * @param request
	 */

	//void checkTxtImportAuthority(Request request);
    
    /**
     * 检查系统字段对照表权限
     * 
     * @param request
     */
    public void checkFieldCollateAuthority(Request request);
    /**
     * 检查导入路径设置权限
     * 
     * @param request
     */
    public void checkBcusPathSetAuthority(Request request);
    /**
     * 检查导入格式设置权限
     * 
     * @param request
     */
    public void checkTxtFormatFieldSetupAuthority(Request request);
    /**
     * 检查导入任务设置权限
     * 
     * @param request
     */
    public void checkTxtTaskAuthority(Request request);
    /**
     * 检查执行文本导入权限
     * 
     * @param request
     */
    public void checkDataToolsAuthority(Request request);

	public ClassList emsEdiChange(Request request, ClassList obj);

	public void insertTable(Request request, String fileName);

	public DBFormat formatChange(Request request, DBFormat obj);

	public DBFormatSetup getisExistAimname(Request request, DBFormat dbFormat,
			String aimName);

	public void saveTxt(Request request, Object obj);

	public DBDataExecuSql saveDBDataExecuSql(Request request, DBDataExecuSql db);

	public List findDbDataExecuSql(Request request);

	public String execuSql(Request request, DBDataExecuSql obj)
			throws SQLException;

	public void deleteDBDataExecuSql(Request request, DBDataExecuSql obj);

	public Integer getNumToTaskSel(Request request, String className,
			String seqNum, DBTaskEx dbtaskEx);

	public Integer getNumToTxtTaskSell(Request request, String className,
			String seqNum, TxtTaskEx txtTaskEx);

	public void importData(Request request, List list, String serverName,
			String dbName, String userName, String password, String hccode,
			String emsNo);

	public void copyComplex(Request request);

	public List findParameterList(Request request);

	public ParameterList saveParameterList(Request request, ParameterList obj);

	public String formatSql(Request request, String sql);

	public void deleteParameterList(Request request, ParameterList obj);

	public void startThread(Request request);

	/**
	 * 停止当前导入线程
	 * 
	 * @param request
	 */
	void shutDownThread(Request request);

	public void tempChange(Request request);

	public void saveTxtList(Request request, List list);

	public DBQuerySql saveDBQuerySql(Request request, DBQuerySql db);

	public List findDBQuerySql(Request request);
	public SuperClassList saveSuperFieldList(Request request,List fieldList,SuperClassList obj,boolean isAdd);
	public List findSuperClassList(Request request);
	public void deleteSuperClassList(Request request,List ls);
	public List findSuperFieldListByClass(Request request,SuperClassList obj);
	public List importData(Request request,List<List> lines, SuperClassList classList);
	public void saveImportData(Request request,List objList);
	
	public void deleteDBQuerySql(Request request,DBQuerySql db)  ;
}