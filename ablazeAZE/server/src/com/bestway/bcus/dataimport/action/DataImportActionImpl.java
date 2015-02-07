/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.action;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.dataimport.dao.DataImportDao;
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
import com.bestway.bcus.dataimport.logic.DataBackupLogic;
import com.bestway.bcus.dataimport.logic.DataDBAutoImportLogic;
import com.bestway.bcus.dataimport.logic.DataImportLogic;
import com.bestway.bcus.dataimport.logic.DataSuperImportLogic;
import com.bestway.bcus.dbimport.logic.DbBcsLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.entity.BaseCompany;
/**
 * @author lin
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@AuthorityClassAnnotation(caption = "数据导入接口", index = 22)
public class DataImportActionImpl extends BaseActionImpl implements
        DataImportAction {
    private DataImportDao   dataImportDao   = null;
    private DataImportLogic dataImportLogic = null;
    private DataBackupLogic dataBackupLogic = null;
    private DbBcsLogic      dbBcsLogic      = null;
    private DataDBAutoImportLogic dataDBAutoImportLogic = null;
    private DataSuperImportLogic dataSuperImportLogic = null; 

    public DataDBAutoImportLogic getDataDBAutoImportLogic() {
		return dataDBAutoImportLogic;
	}

	public void setDataDBAutoImportLogic(DataDBAutoImportLogic dataDBAutoImportLogic) {
		this.dataDBAutoImportLogic = dataDBAutoImportLogic;
	}

	/**
     * 显示本导入格式
     * 
     * @param request
     * @param txtTask
     * @return
     */

    public List findTxtTask(Request request) {
        return dataImportDao.findTxtTask();
    }

    /**
     * 保存文本导入格式
     * 
     * @param request
     * @param txtTask
     * @return
     */

    public TxtTask saveTxtTask(Request request, TxtTask txtTask)
            throws DataAccessException {
        dataImportDao.saveTxtTask(txtTask);
        return txtTask;

    }

    /**
     * 删除文本导入格式
     * 
     * @param request
     * @param txtTask
     * @return
     */

    public void deleteTxtTask(Request request, TxtTask txtTask) {
        dataImportDao.deleteTxtTask(txtTask);
    }

    public List findTxtFormat(Request request, TxtTask txtTask) {
        return dataImportDao.findTxtFormat(txtTask);

    }

    /**
     * 保存文本导入格式字段对照设置
     */

    public TxtFormat saveTxtFormat(Request request, TxtFormat txtFormat)
            throws DataAccessException {
        dataImportDao.saveTxtFormat(txtFormat);
        return txtFormat;
    }

    /**
     * 删除文本导入格式字段对照设置
     */

    public void deleteTxtFormat(Request request, TxtFormat txtFormat) {
        dataImportDao.deleteTxtFormat(txtFormat);
    }

    /**
     * 显示文本导入任务主表
     */

    public List findTxtTaskEx(Request request) {
        return dataImportDao.findTxtTaskEx();

    }

    /**
     * 保存文本导入任务主表
     */

    public TxtTaskEx saveTxtTaskEx(Request request, TxtTaskEx txtTaskEx)
            throws DataAccessException {
    	if(CommonUtils.isImportDataLock()) {
    		throw new RuntimeException("有用户正在导入数据库！请稍后在试。。。");
    	} else {
    		CommonUtils.setImportDataLock(true);
    	}
    	try {
    		 dataImportDao.saveTxtTaskEx(txtTaskEx);
    		 return txtTaskEx;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			CommonUtils.setImportDataLock(false);
		}
       
    }

    /**
     * 删除文本导入任务主表
     */

    public void deleteTxtTaskEx(Request request, TxtTaskEx txtTaskEx) {
        dataImportDao.deleteTxTaskEx(txtTaskEx);
    }

    /**
     * 显示文本导入任务明细
     */
    public List findTxtTaskSel(Request request, TxtTaskEx txtTaskEx) {
    	if(CommonUtils.isImportDataLock()) {
    		throw new RuntimeException("有用户正在导入数据库！请稍后在试。。。");
    	} else {
    		CommonUtils.setImportDataLock(true);
    	}
    	try {
    		return dataImportDao.findTxtTaskSel(txtTaskEx);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			CommonUtils.setImportDataLock(false);
		}
    }

    /**
     * 保存文本导入任务明细
     */

    public TxtTaskSel saveTxtTaskSel(Request request, TxtTaskSel txtTaskSel)
            throws DataAccessException {
        dataImportDao.saveTxtTaskSel(txtTaskSel);
        return txtTaskSel;
    }

    public void deleteTxtTaskSel(Request request, TxtTaskSel txtTaskSel) {
        dataImportDao.deleteTxtTaskSel(txtTaskSel);
    }

    /**
     * @return Returns the dataImportDao.
     */
    public DataImportDao getDataImportDao() {
        return dataImportDao;
    }

    /**
     * @param dataImportDao
     *            The dataImportDao to set.
     */
    public void setDataImportDao(DataImportDao dataImportDao) {
        this.dataImportDao = dataImportDao;
    }

    /**
     * @return Returns the dataImportLogic.
     */
    public DataImportLogic getDataImportLogic() {
        return dataImportLogic;
    }

    /**
     * @param dataImportLogic
     *            The dataImportLogic to set.
     */
    public void setDataImportLogic(DataImportLogic dataImportLogic) {
        this.dataImportLogic = dataImportLogic;
    }

    /**
     * 查找任务明细通过任务主表过滤
     */
    public List findTxtTaskSelTaskTaskEx(Request request, TxtTaskEx txtTaskEx) {
        return dataImportDao.findTxtTaskSelTaskTaskEx(txtTaskEx);

    }

    /**
     * 显示文本导入任务明细
     */
    public List findTxtTaskTaskTaskSel(Request request, TxtTaskEx txtTaskEx) {
        return dataImportDao.findTxtTaskTaskTaskSel();

    }

    /**
     * 显示字段对照表对照
     */

    public List findClassList(Request request) {
        return dataImportDao.findClassList();
    }

    /**
     * 显示字段对照字段对照
     */

    public List findFieldList(Request request, ClassList classList) {
        return dataImportDao.findFieldList(classList);
    }

    /**
     * 保存字段对照表对照
     */

    public ClassList saveClassList(Request request, ClassList classList)
            throws DataAccessException {
        dataImportDao.saveClassList(classList);
        return classList;
    }

    /**
     * 保存字段对照字段对照
     */

    public FieldList saveFieldList(Request request, FieldList FieldList)
            throws DataAccessException {
        dataImportDao.saveFieldList(FieldList);
        return FieldList;
    }

    /**
     * 返回序号
     */
    public String getNum(Request request, String className, ClassList classList) {
        return dataImportDao.getNum(className, classList);
    }

    /**
     * 字段设置得到序号
     */
    public String getFieldFormatNum(Request request, String className,
            TxtTask txtTask) {
        return dataImportDao.getFieldFormatNum(className, txtTask);
    }

    /**
     * 删除所有格式设置
     */

    public void deleteAllFormat(Request request, TxtTask txtTask) {
        dataImportDao.deleteAllFormat(txtTask);
    }

    /**
     * 删除所有任务明细
     */

    public void deleteAllTxtTaskSel(Request request, TxtTaskEx txtTaskEx) {
        dataImportDao.deleteAllTxtTaskSel(txtTaskEx);
    }

    /**
     * 显示任务明细通过任务主表
     */
    public List findTxtTaskFromTxtTask(Request request, TxtTaskEx txtTaskEx) {
        return dataImportDao.findTxtTaskFromTxtTask(txtTaskEx);
    }

    /**
     * 检查是否重复
     */
    public TxtTask findTxtTaskBytaskname(Request request, String taskname) {
        return dataImportDao.findTxtTaskBytaskname(taskname);
    }

    public List findFieldListFromTxtFormat(Request request,
            ClassList classList, TxtTask txtTask) {
        return dataImportDao.findFieldListFromTxtFormat(classList, txtTask);
    }

    /**
     * 保存文本
     */
    public void saveTxtInput(Request request, Object obj)
            throws DataAccessException {
        dataImportDao.saveTxtInput(obj);
    }

    public void saveTxt(Request request, Object obj) throws DataAccessException {
        dataImportDao.saveTxt(obj);
    }

    public List findRepeatList(Request request, String obj, String key,
            String value, Boolean isCompanyId) {
        return dataImportDao.findRepeatList(obj, key, value, isCompanyId);
    }

    public void deleteTxtInput(Request request, Object obj) {
        dataImportDao.deleteTxtInput(obj);
    }

    public List findObjList(Request request, String obj, String field,
            String value, Boolean isCompanyId) {
        return dataImportDao.findObjList(obj, field, value, isCompanyId);
    }

    public List findTxtLog(Request request) {
        return dataImportDao.findTxtLog();
    }

    public TxtLog saveTxtLog(Request request, TxtLog txtLog)
            throws DataAccessException {
        dataImportDao.saveTxtLog(txtLog);
        return txtLog;
    }

    public void deleteTxtLog(Request request) {
        dataImportDao.deleteTxtLog();
    }

    public void changeIsTitle(Request request, Boolean isTitleRow) {
        dataImportLogic.changeIsTitle(isTitleRow);
    }

    public List findThreadList(Request request, String type) {
        return dataImportDao.findThreadList(type);
    }

    public void saveThreadList(Request request, ThreadList obj)
            throws DataAccessException {
        dataImportDao.saveThreadList(obj);
    }

    public void deleteThread(Request request, String type) {
        dataImportDao.deleteThread(type);
    }

    // 返回数据源

    public List findDBDataRoot(Request request) {
        return dataImportDao.findDBDataRoot();
    }

    /**
     * 视图参数设置
     * 
     * @param obj
     * @throws DataAccessException
     */
    public List findDbDataExecuSql(Request request) {
        return dataImportDao.findDbDataExecuSql();
    }

    /**
     * 保存数据源
     */

    public DBDataRoot saveDBDataRoot(Request request, DBDataRoot obj)
            throws DataAccessException {
        dataImportDao.saveDBDataRoot(obj);
        return obj;
    }

    public void deleteDBDataRoot(Request request, DBDataRoot obj) {
        dataImportDao.deleteDBDataRoot(obj);
    }

    // 返回视图

    public List findDBView(Request request, DBDataRoot db) {
        return dataImportDao.findDBView(db);
    }

    public DBView saveDBView(Request request, DBView obj)
            throws DataAccessException {
        dataImportDao.saveDBView(obj);
        return obj;
    }

    // DB任务

    public List findDBTaskEx(Request request) {
        return dataImportDao.findDBTaskEx();
    }

    public List findDBTaskSel(Request request, DBTaskEx dbTaskEx) {
    	if(CommonUtils.isImportDataLock()) {
    		throw new RuntimeException("有用户正在导入数据库！请稍后在试。。。");
    	} else {
    		CommonUtils.setImportDataLock(true);
    	}
    	try {
    		return dataImportDao.findDBTaskSel(dbTaskEx);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			CommonUtils.setImportDataLock(false);
		}
    }

    /**
     * 保存DB导入任务
     */

    public DBTaskEx saveDBTaskEx(Request request, DBTaskEx dbTaskEx)
            throws DataAccessException {
    	if(CommonUtils.isImportDataLock()) {
    		throw new RuntimeException("有用户正在导入数据库！请稍后在试。。。");
    	} else {
    		CommonUtils.setImportDataLock(true);
    	}
    	try {
    		dataImportDao.saveDBTaskEx(dbTaskEx);
            return dbTaskEx;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			CommonUtils.setImportDataLock(false);
		}
        
    }

    /**
     * 删除DB导入所有任务明细
     */

    public void deleteAllDBTaskSel(Request request, DBTaskEx dbTaskEx) {
        dataImportDao.deleteAllDBTaskSel(dbTaskEx);
    }

    /**
     * 删除DB导入任务
     */

    public void deleteDBTaskEx(Request request, DBTaskEx dbTaskEx) {
        dataImportDao.deleteDBTaskEx(dbTaskEx);
    }

    /**
     * 删除DB导入任务明细
     */

    public void deleteDBTaskSel(Request request, DBTaskSel dbTaskSel) {
        dataImportDao.deleteDBTaskSel(dbTaskSel);
    }

    /**
     * 返回DB导入字段设置
     */

    public List findDBFormatSetup(Request request, DBFormat dbFormat) {
        return dataImportDao.findDBFormatSetup(dbFormat);
    }

    /**
     * DB导入
     */

    public List intoData(Request request, DBFormat dbFormat,
            BaseCompany company, String deal, String inputSelect, List list, Hashtable hs) {
    	if(CommonUtils.isImportDataLock()) {
    		throw new RuntimeException("有用户正在导入数据库！请稍后在试。。。");
    	} else {
    		CommonUtils.setImportDataLock(true);
    	}
    	try {
    		return dataImportLogic.intoData(dbFormat, company, deal, inputSelect, list, hs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			CommonUtils.setImportDataLock(false);
		}
    }

    /**
     * 返回视图
     */

    public List findView(Request request) {
        return dataImportDao.findView();
    }

    /**
     * 通过视图返回域
     */

    public List findDBForByDBView(Request request, DBView dbView) {
        return dataImportDao.findDBForByDBView(dbView);
    }

    /**
     * 保存域定义
     */

    public DBFormat saveDBFormat(Request request, DBFormat obj)
            throws DataAccessException {
        dataImportDao.saveDBFormat(obj);
        return obj;
    }

    /**
     * 保存DB导入字段设置
     */

    public DBFormatSetup saveDBFormatSetup(Request request, DBFormatSetup obj)
            throws DataAccessException {
        dataImportDao.saveDBFormatSetup(obj);
        return obj;
    }

    /**
     * 删除DB导入字段设置
     */

    public void deleteDBFormatSetup(Request request, DBFormatSetup obj) {
        dataImportDao.deleteDBFormatSetup(obj);
    }

    /**
     * 删除域设置
     */

    public void deleteDBFormat(Request request, DBFormat obj) {
        dataImportDao.deleteDBFormat(obj);
    }

    /**
     * 删除所有域设置
     */

    public void deleteAllSetup(Request request, DBFormat dbFormat) {
        dataImportDao.deleteAllSetup(dbFormat);
    }

    /**
     * 删除字段对照表字段设置
     */

    public void deleteFieldList(Request request, FieldList obj) {
        dataImportDao.deleteFieldList(obj);
    }

    /**
     * 删除字段对照表表设置
     */

    public void deleteClassList(Request request, ClassList obj) {
        dataImportDao.deleteClassList(obj);
    }

    /**
     * 删除所有字段对照表字段设置
     */

    public void deleteAllField(Request request, ClassList obj) {
        dataImportDao.deleteAllField(obj);
    }

    /**
     * 删除视图
     */

    public void deleteDBView(Request request, DBView obj) {
        dataImportDao.deleteDBView(obj);
    }

    public List findDBTaskFromDBTask(Request request, DBTaskEx dbTaskEx) {
        return dataImportDao.findDBTaskFromDBTask(dbTaskEx);
    }

    /**
     * 保存DB导入任务明细
     */
    public DBTaskSel saveDBTaskSel(Request request, DBTaskSel dbTaskSel)
            throws DataAccessException {
        dataImportDao.saveDBTaskSel(dbTaskSel);
        return dbTaskSel;
    }
    /**
     * 保存Txt导入任务明细
     */
    public DBTaskSel saveTxtTaskSel(Request request, DBTaskSel dbTaskSel)
			throws DataAccessException {
		dataImportDao.saveDBTaskSel(dbTaskSel);
		return dbTaskSel;
}  
    /**
     * 返回字段对照表表设置通过id
     */
    public ClassList findbyid(Request request, String id) {
        return dataImportDao.findbyid(id);
    }

    /**
     * 保存对象
     */
    public void saveObject(Request request, Object obj)
            throws DataAccessException {
        dataImportDao.saveObject(obj);
    }

    /**
     * 连接数据源
     */
    public String isConnect(Request request, String driverClassName,
            String url, String userName, String password) {
        return DataImportLogic.isConnect(driverClassName, url, userName,
                password);
    }

    /**
     * 检验是否为空数据表
     */
    public Vector isNulltable(Request request, DBDataRoot db)
            throws SQLException {
        return dataImportLogic.isNulltable(db);
    }

    /**
     * 验证 SQL 语句
     */
    public String textSql(Request request, DBDataRoot db, String sql) {
        return dataImportLogic.testSql(db, sql);
    }

    public String checkDB(Request request, DBDataRoot db) {
        return dataImportLogic.checkDB(db);
    }

    public int getFieldsPart(Request request, DBDataRoot dbroot, String sql)
            throws SQLException {
        return dataImportLogic.getFieldsPart(dbroot, sql);
    }

    public Vector getFieldName2(Request request,DBDataRoot dbroot, String sql, int columnCount)  throws SQLException {
        return dataImportLogic.getFieldName2(dbroot, sql, columnCount);
    }

    public Vector getTableColumn(Request request, DBDataRoot db, String sql,
            int columnCount) throws SQLException {
        return dataImportLogic.getTableColumn(db, sql, columnCount);
    }

    public Vector getFName(Request request, DBDataRoot db, String tableName)
            throws SQLException {
        return dataImportLogic.getFName(db, tableName);
    }

    public List getSourceFields(Request request, DBFormat dbFormat) {
        return dataImportLogic.getSourceFields(dbFormat);
    }

    /**
     * 得到序号
     */
    public Integer getNumToDriverList(Request request, String className,
            String seqNum) {
        return this.dataImportDao.getNumToDriverList(className, seqNum);
    }

    /**
     * 新增驱动
     */
    public DriverList saveDriverList(Request request, DriverList driverList)
            throws DataAccessException {
        this.dataImportDao.saveDriverList(driverList);
        return driverList;
    }

    /**
     * 返回驱动
     */
    public List findDriverList(Request request) {
        return this.dataImportDao.findDriverList();
    }

    /**
     * 返回对象
     */
    public DriverList findDriverListObj(Request request, Integer sortNo) {
        return this.dataImportDao.findDriverListObj(sortNo);
    }

    public void exportBackUp(Request request, String fileName, String tableName)
            throws SQLException {
        this.dataBackupLogic.exportBackUp(fileName, tableName);
    }

    public String execuSql(Request request, DBDataExecuSql obj)
            throws SQLException {
    	if(CommonUtils.isImportDataLock()) {
    		throw new RuntimeException("有用户正在导入数据库！请稍后在试。。。");
    	} else {
    		CommonUtils.setImportDataLock(true);
    	}
    	try {
    		return this.dataImportLogic.execuSql(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			CommonUtils.setImportDataLock(false);
		}
        
    }
    
    /**
     * 执行文件
     * @param dbFormat
     * @return
     */
    public void execuFileSql(Request request,String sql) throws SQLException {
    	this.dataImportLogic.execuFileSql(sql);
    }

    public void deleteDBDataExecuSql(Request request, DBDataExecuSql obj) {
        this.dataImportDao.deleteDBDataExecuSql(obj);
    }

    /**
     * 检查视图参数设置权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "DB导入管理--视图参数设置", index = 1)
    public void checkParameterListAuthority(Request request) {

    }
    /**
     * 检查数据源操作权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "DB导入管理--数据源操作", index = 2)
    public void checkDataRootAuthority(Request request) {

    }
    /**
     * 检查事件设置权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "DB导入管理--事件设置", index = 3)
    public void checkDataExecuSqlAuthority(Request request) {

    }
    /**
     * 检查视图操作权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "DB导入管理--视图操作", index = 4)
    public void checkViewAuthority(Request request) {

    }
    /**
     * 检查域定义管理权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "DB导入管理--域定义管理", index = 5)
    public void checkFormatAuthority(Request request) {

    }
    /**
     * 检查任务计划设置权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "DB导入管理--任务计划设置", index = 6)
    public void checkDBTaskAuthority(Request request) {

    }
    /**
     * 检查执行DB导入权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "DB导入管理--执行DB导入", index = 7)
    public void checkDBImportAuthority(Request request) {

    }

    /**
     * 检查系统字段对照表权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "文本导入管理--系统字段对照表", index = 8)
    public void checkFieldCollateAuthority(Request request) {

    }
    /**
     * 检查导入路径设置权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "文本导入管理--导入路径设置", index = 9)
    public void checkBcusPathSetAuthority(Request request) {

    }
    /**
     * 检查导入格式设置权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "文本导入管理--导入格式设置", index = 10)
    public void checkTxtFormatFieldSetupAuthority(Request request) {

    }
    /**
     * 检查导入任务设置权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "文本导入管理--导入任务设置", index = 11)
    public void checkTxtTaskAuthority(Request request) {

    }
    /**
     * 检查执行文本导入权限
     * 
     * @param request
     */
    @AuthorityFunctionAnnotation(caption = "文本导入管理--执行文本导入", index = 12)
    public void checkDataToolsAuthority(Request request) {

    }

    /**
     * @return Returns the dataBackupLogic.
     */
    public DataBackupLogic getDataBackupLogic() {
        return dataBackupLogic;
    }

    /**
     * @param dataBackupLogic
     *            The dataBackupLogic to set.
     */
    public void setDataBackupLogic(DataBackupLogic dataBackupLogic) {
        this.dataBackupLogic = dataBackupLogic;
    }

    // 对照表转抄
    public ClassList emsEdiChange(Request request, ClassList obj) {
        return this.dataImportLogic.emsEdiChange(obj);
    }

    // 还原
    public void insertTable(Request request, String fileName) {
        this.dataBackupLogic.insertTable(fileName);
    }

    // 域转抄
    public DBFormat formatChange(Request request, DBFormat obj) {
        return this.dataImportLogic.formatChange(obj);
    }

    public DBFormatSetup getisExistAimname(Request request, DBFormat dbFormat,
            String aimName) {
        return this.dataImportDao.getisExistAimname(dbFormat, aimName);
    }

    public DBDataExecuSql saveDBDataExecuSql(Request request, DBDataExecuSql db)
            throws DataAccessException {
        this.dataImportDao.saveDBDataExecuSql(db);
        return db;
    }

    /**
     * 得到序号DB导入
     */
    public Integer getNumToTaskSel(Request request, String className,
            String seqNum, DBTaskEx dbtaskEx) {
        return this.dataImportDao.getNumToTaskSel(className, seqNum, dbtaskEx);
    }

    /**
     * 得到序号Txt导入
     */
    public Integer getNumToTxtTaskSell(Request request, String className,
            String seqNum, TxtTaskEx txtTaskEx) {
        return this.dataImportDao.getNumToTxtTaskSell(className, seqNum, txtTaskEx);
    }
    
    /**
     * @return Returns the dbBcsLogic.
     */
    public DbBcsLogic getDbBcsLogic() {
        return dbBcsLogic;
    }

    /**
     * @param dbBcsLogic
     *            The dbBcsLogic to set.
     */
    public void setDbBcsLogic(DbBcsLogic dbBcsLogic) {
        this.dbBcsLogic = dbBcsLogic;
    }

    // 导入BCS数据
    @AuthorityFunctionAnnotation(caption = "老系统切换接口", index = 13)
    public void importData(Request request, List list, String serverName,
            String dbName, String userName, String password,String hccode,String emsNo) {
        this.dbBcsLogic
                .importData(list, serverName, dbName, userName, password,hccode,emsNo);
    }

    @AuthorityFunctionAnnotation(caption = "老系统切换接口", index = 13)
    public void copyComplex(Request request) {
        this.dataImportLogic.copyComplex();
    }

    public List findParameterList(Request request) {
        return this.dataImportDao.findParameterList();
    }

    public ParameterList saveParameterList(Request request, ParameterList obj) {
        this.dataImportDao.saveParameterList(obj);
        return obj;
    }

    public String formatSql(Request request, String sql) {
        return this.dataImportLogic.formatSql(sql, null);
    }

    public void deleteParameterList(Request request, ParameterList obj) {
        this.dataImportDao.deleteParameterList(obj);
    }

    public void startThread(Request request) {
        this.dataDBAutoImportLogic.startThread();
    }

    public void shutDownThread(Request request) {
        this.dataDBAutoImportLogic.shutDownThread();
    }

    public void tempChange(Request request){
    	this.dataImportLogic.tempChange();
    }
    public void saveTxtList(Request request,List list){
    	this.dataImportLogic.saveTxtList(list);
    }
    public DBQuerySql saveDBQuerySql(Request request,DBQuerySql db) throws DataAccessException {
    	this.dataImportDao.saveDBQuerySql(db);
    	return db;
    }
    public List findDBQuerySql(Request request){
    	return this.dataImportDao.findDBQuerySql();
    }

	public DataSuperImportLogic getDataSuperImportLogic() {
		return dataSuperImportLogic;
	}

	public void setDataSuperImportLogic(DataSuperImportLogic dataSuperImportLogic) {
		this.dataSuperImportLogic = dataSuperImportLogic;
	}

	public SuperClassList saveSuperFieldList(Request request,List fieldList,SuperClassList obj,boolean isAdd){
		return dataSuperImportLogic.saveSuperFieldList(fieldList,obj,isAdd);
	}
	public List findSuperClassList(Request request){
	    return dataImportDao.findSuperClassList();
	}
	
	public void deleteSuperClassList(Request request,List ls){
		this.dataSuperImportLogic.deleteSuperClassList(ls);
	}
	public List findSuperFieldListByClass(Request request,SuperClassList obj){
		return this.dataImportDao.findSuperFieldListByClass(obj);
	}
	
	public List importData(Request request,List<List> lines, SuperClassList classList){
		return this.dataSuperImportLogic.importData(lines,classList);
	}
	
	public void saveImportData(Request request,List objList){
		this.dataSuperImportLogic.saveImportData(objList);
	}
	
	public void deleteDBQuerySql(Request request,DBQuerySql db)  {
		this.dataImportDao.deleteDBQuerySql(db);
		}
	
}



