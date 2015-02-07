/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

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
import com.bestway.bcus.dataimport.entity.SuperFieldList;
import com.bestway.bcus.dataimport.entity.TempBillMasterKey;
import com.bestway.bcus.dataimport.entity.TempImpExpRequestBillKey;
import com.bestway.bcus.dataimport.entity.ThreadList;
import com.bestway.bcus.dataimport.entity.TxtFormat;
import com.bestway.bcus.dataimport.entity.TxtLog;
import com.bestway.bcus.dataimport.entity.TxtTask;
import com.bestway.bcus.dataimport.entity.TxtTaskEx;
import com.bestway.bcus.dataimport.entity.TxtTaskSel;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.constant.Gbflag;

/**
 * @author lin
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DataImportDao extends BaseDao {
	/**
	 * 得到序号
	 */
	public Integer getNumToDriverList(String className, String seqNum) {
		int num = 1;
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a ");
		if (list.get(0) != null) {
			num = (Integer) list.get(0);
			num = Integer.valueOf(num) + 1;
		}
		return num;
	}

	/**
	 * 得到序号DB导入
	 */
	public Integer getNumToTaskSel(String className, String seqNum,
			DBTaskEx dbtaskEx) {
		int num = 1;
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a where a.company.id = ? and a.dbtaskEx.id = ?",
				new Object[] { CommonUtils.getCompany().getId(),
						dbtaskEx.getId() });
		if (list.get(0) != null) {
			num = (Integer) list.get(0);
			num = Integer.valueOf(num) + 1;
		}
		return num;
	}

	/**
	 * 得到序号TXT导入
	 */
	public Integer getNumToTxtTaskSell(String className, String seqNum,
			TxtTaskEx txtTaskEx) {
		int num = 1;
		List list = this.find("select max(a." + seqNum + ") from " + className
				+ " as a where a.company.id = ? and a.txttask.id = ?",
				new Object[] { CommonUtils.getCompany().getId(),
						txtTaskEx.getId() });
		if (list.get(0) != null) {
			num = (Integer) list.get(0);
			num = Integer.valueOf(num) + 1;
		}
		return num;
	}

	/**
	 * 新增驱动
	 */
	public void saveDriverList(DriverList driverList)
			throws DataAccessException {
		this.saveOrUpdate(driverList);
	}

	/**
	 * 返回驱动
	 */
	public List findDriverList() {
		return this.find("select a from DriverList a");
	}

	/**
	 * 返回对象
	 */
	public DriverList findDriverListObj(Integer sortNo) {
		List list = this.find("select a from DriverList a where a.sortno=?",
				new Object[] { sortNo });
		if (list != null && list.size() > 0) {
			return (DriverList) list.get(0);
		}
		return null;
	}

	/**
	 * 显示字段设置表
	 * 
	 * @param txtTask
	 * @return
	 */
	// 手工
	public List findTxtFormat(TxtTask txtTask) {
		return this
				.find(
						"select a from TxtFormat a where a.company.id= ? and a.txttask.id= ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								txtTask.getId() });
	}

	// 自动导入
	public List findTxtFormatCompany(TxtTask txtTask, BaseCompany company) {
		return this
				.find(
						"select a from TxtFormat a where a.company.id= ? and a.txttask.id= ? ",
						new Object[] { company.getId(), txtTask.getId() });
	}

	/**
	 * 保存字段设置
	 * 
	 * @param txtFormat
	 * @throws DataAccessException
	 */
	public void saveTxtFormat(TxtFormat txtFormat) throws DataAccessException {
		this.saveOrUpdate(txtFormat);
	}

	public void deleteDBDataExecuSql(DBDataExecuSql obj) {
		this.delete(obj);
	}

	// 保存视图参数设置
	public void saveDBDataExecuSql(DBDataExecuSql db)
			throws DataAccessException {
		this.saveOrUpdate(db);
	}

	/**
	 * 删除字段设置
	 * 
	 * @param txtFormat
	 */
	public void deleteTxtFormat(TxtFormat txtFormat) {
		this.delete(txtFormat);
	}

	/**
	 * 显示格式表
	 * 
	 * @return
	 */
	public List findTxtTask() {
		return this.find("select a from TxtTask a where a.company.id= ? ",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 获得格式表通过筛选
	 * 
	 * @return
	 */
	public List findTxtTaskFromTxtTask(TxtTaskEx txtTaskEx) {
		if (txtTaskEx.getIsParentTask() == null
				|| (!txtTaskEx.getIsParentTask())) {
			return this
					.find(
							"select a from TxtTask a where a.company.id= ? and a.taskname "
									+ "not in (select b.txttable.taskname from TxtTaskSel b where b.company.id=? and b.txttask.id=?)",
							new Object[] { CommonUtils.getCompany().getId(),
									CommonUtils.getCompany().getId(),
									txtTaskEx.getId() });
		} else {
			List list = this
					.find(
							"select a from TxtTaskEx a where a.company.id= ? and a.id "
									+ "not in (select b.subDBTaskEx.id from TxtTaskSel b where b.company.id=? and b.txttask.id=?)",
							new Object[] { CommonUtils.getCompany().getId(),
									CommonUtils.getCompany().getId(),
									txtTaskEx.getId() });
			list.remove(txtTaskEx);
			return list;
		}
	}

	/**
	 * 保存格式
	 * 
	 * @param txtTask
	 * @throws DataAccessException
	 */
	public void saveTxtTask(TxtTask txtTask) throws DataAccessException {
		this.saveOrUpdate(txtTask);
	}

	/**
	 * 删除格式
	 * 
	 * @param txtTask
	 */
	public void deleteTxtTask(TxtTask txtTask) {
		this.delete(txtTask);
	}

	/**
	 * 删除所有字段设置
	 * 
	 * @param txtTask
	 */
	public void deleteAllFormat(TxtTask txtTask) {
		this.deleteAll(findTxtFormat(txtTask));
	}

	/**
	 * 返回任务主表
	 * 
	 * @return
	 */
	public List findTxtTaskEx() {
		return this.find("select a from TxtTaskEx a where a.company.id= ? ",
				CommonUtils.getCompany().getId());
	}

	public List findDBTaskEx() {
		return this.find("select a from DBTaskEx a where a.company.id= ? order by taskname",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 删除任务主表
	 * 
	 * @param txtTaskEx
	 */
	public void deleteTxTaskEx(TxtTaskEx txtTaskEx) {
		this.delete(txtTaskEx);
	}

	public void deleteDBTaskEx(DBTaskEx dbTaskEx) {
		this.delete(dbTaskEx);
	}

	/**
	 * 删除任务明细所有
	 * 
	 * @param txtTaskEx
	 */
	public void deleteAllTxtTaskSel(TxtTaskEx txtTaskEx) {
		this.deleteAll(findTxtTaskSel(txtTaskEx));
	}

	public void deleteAllDBTaskSel(DBTaskEx dbTaskEx) {
		this.deleteAll(findDBTaskSel(dbTaskEx));
	}

	/**
	 * 保存任务主表
	 * 
	 * @param txtTaskEx
	 * @throws DataAccessException
	 */
	public void saveTxtTaskEx(TxtTaskEx txtTaskEx) throws DataAccessException {
		this.saveOrUpdate(txtTaskEx);
	}

	public void saveDBTaskEx(DBTaskEx dbTaskEx) throws DataAccessException {
		this.saveOrUpdate(dbTaskEx);
	}

	/**
	 * 返回任务明细
	 * 
	 * @param txtTaskEx
	 * @return
	 */
	public List findTxtTaskSel(TxtTaskEx txtTaskEx) {
		return this
				.find(
						"select a from TxtTaskSel a where a.company.id= ?  and a.txttask.id= ? order by a.seqNum ",
						new Object[] { CommonUtils.getCompany().getId(),
								txtTaskEx.getId() });
	}

	public List findDBTaskSel(DBTaskEx dbTaskEx) {
		return this
				.find(
						"select a from DBTaskSel a where a.company.id= ?  and a.dbtaskEx.id= ? order by a.seqNum",
						new Object[] { CommonUtils.getCompany().getId(),
								dbTaskEx.getId() });
	}

	/**
	 * 返回文本导入日志
	 * 
	 * @return
	 */
	public List findTxtLog() {
		return this.find("select a from TxtLog a where a.company.id= ? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存日志
	 * 
	 * @param txtLog
	 * @throws DataAccessException
	 */
	public void saveTxtLog(TxtLog txtLog) throws DataAccessException {
		this.saveOrUpdate(txtLog);
	}

	/**
	 * 清空日志
	 * 
	 */
	public void deleteTxtLog() {
		this.deleteAll(findTxtLog());
	}

	/**
	 * 删除任务明细
	 * 
	 * @param txtTaskSel
	 */
	public void deleteTxtTaskSel(TxtTaskSel txtTaskSel) {
		this.delete(txtTaskSel);
	}

	public void deleteDBTaskSel(DBTaskSel dbTaskSel) {
		this.delete(dbTaskSel);
	}

	/**
	 * 保存任务明细
	 * 
	 * @param txtTaskSel
	 * @throws DataAccessException
	 */
	public void saveTxtTaskSel(TxtTaskSel txtTaskSel)
			throws DataAccessException {
		this.saveOrUpdate(txtTaskSel);
	}

	public void saveDBTaskSel(DBTaskSel dbTaskSel) throws DataAccessException {
		this.saveOrUpdate(dbTaskSel);
	}

	public List findTxtTaskSelTaskTaskEx(TxtTaskEx txtTaskEx) {
		List list = null;
		list = this
				.find(
						"select "
								+ " a.id,b.taskname,b.tablecnname,b.gbflag,b.creator from TxtTaskSel a ,TxtTask b where "
								+ " a.company.id= ?  and a.txttask.id= ? and a.txttable=b.id ",
						new Object[] { CommonUtils.getCompany().getId(),
								txtTaskEx.getId() });
		return list;
	}

	public List findTxtTaskTaskTaskSel() {
		List list = null;
		list = this
				.find(
						"select "
								+ " a.id,b.taskname,b.tablecnname,b.gbflag,b.creator  from  TxtTaskSel a right outer join a.txttable b  where "
								+ " b.company.id= ?  ",
						new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 字段对照表
	 * 
	 * @return
	 */
	public List findClassList() {
		return this.find("select a from ClassList a order by a.name");
	}

	public void saveClassList(ClassList classList) throws DataAccessException {
		this.saveOrUpdate(classList);
	}

	public List findFieldList(ClassList classList) {
		return this.find("select a from FieldList a where a.classList.id= ? ",
				new Object[] { classList.getId() });
	}

	public void deleteFieldList(FieldList obj) {
		this.delete(obj);
	}

	public void deleteClassList(ClassList obj) {
		this.delete(obj);
	}

	public void deleteAllField(ClassList obj) {
		this.deleteAll(findFieldList(obj));
	}

	/**
	 * 获得字段通过字段设置筛选
	 * 
	 * @param classList
	 * @param txtTask
	 * @return
	 */
	public List findFieldListFromTxtFormat(ClassList classList, TxtTask txtTask) {
		return this
				.find(
						"select a from FieldList a where a.classList.id= ? and"
								+ " a.fieldname not in (select b.fieldname from TxtFormat b where b.txttask.id=?) ",
						new Object[] { classList.getId(), txtTask.getId() });
	}

	/**
	 * 保存FieldList
	 * 
	 * @param fieldList
	 * @throws DataAccessException
	 */
	public void saveFieldList(FieldList fieldList) throws DataAccessException {
		this.saveOrUpdate(fieldList);
	}

	/**
	 * 返回序号
	 * 
	 * @param className
	 * @return
	 */
	public String getNum(String className, ClassList classList) {
		String num = "1";
		List list = null;
		if (classList != null) {
			list = this.find("select max(a.sortno) from " + className
					+ " as a where a.classList.id=?", new Object[] { classList
					.getId() });
		} else {
			list = this
					.find("select max(a.sortno) from " + className + " as a");
		}
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 字段设置得到序号
	 * 
	 * @param className
	 * @param txtTask
	 * @return
	 */
	public String getFieldFormatNum(String className, TxtTask txtTask) {
		String num = "1";
		List list = null;
		list = this.find("select max(a.sortno) from " + className
				+ " as a where a.company.id= ? and a.txttask.id=?",
				new Object[] { CommonUtils.getCompany().getId(),
						txtTask.getId() });
		if (list.get(0) != null) {
			num = list.get(0).toString();
			num = String.valueOf(Integer.parseInt(num) + 1);
		}
		return num;
	}

	/**
	 * 检查是否重复
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param code
	 * @return
	 */
	public TxtTask findTxtTaskBytaskname(String taskname) {
		List result = this
				.find(
						"select a from TxtTask a where a.taskname=? and a.company.id= ?",
						new Object[] { taskname,
								CommonUtils.getCompany().getId() });
		if (result.size() > 0)
			return (TxtTask) result.get(0);
		else
			return null;
	}

	public void saveTxtInput(Object obj) throws DataAccessException {
		 //this.saveOrUpdate(obj);
		this.getHibernateTemplate().save(obj);
	}
	
	public void saveSuperObj(Object obj) throws DataAccessException {
		this.getHibernateTemplate().saveOrUpdate(obj);
	}
	

	public void saveTxt(Object obj) throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	public void saveObject(Object obj) throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	public void deleteTxtInput(Object obj) {
		this.delete(obj);
	}

	// 手工导入
	public List findRepeatList(String obj, String key, String value,
			Boolean isCompanyId) {
		if (isCompanyId.equals(new Boolean(true))) {
			return this.find("select a from " + obj + " a where a." + key
					+ "=? and a.company.id= ?", new Object[] { value,
					CommonUtils.getCompany().getId() });
		} else {
			return this.find("select a from " + obj + " a where a." + key
					+ "=?", new Object[] { value });
		}
	}

	// 自动导入-检查是否有重复值
	public List findRepeatListCompany(String obj, String key, Object value,
			Boolean isCompanyId, BaseCompany company) {
		if (isCompanyId.equals(new Boolean(true))) {
			return this.find("select count(a." + key + ") from " + obj
					+ " a where a." + key + "=? and a.company.id= ?",
					new Object[] { value, company, company.getId() });
		} else {
			return this.find("select count(a." + key + ") from " + obj
					+ " a where a." + key + "=?", new Object[] { value });
		}
	}

	public List findKey(String obj, String key, Boolean isCompanyId,
			BaseCompany company) {
		if (isCompanyId.equals(new Boolean(true))) {
			return this.find("select a." + key + " from " + obj
					+ " a where a.company.id= ?", new Object[] { company
					.getId() });
		} else {
			return this.find("select a." + key + " from " + obj + " a");
		}
	}

	// 手工导入
	public List findObjList(String obj, String field, String value,
			Boolean isCompanyId) {
		if (isCompanyId.equals(new Boolean(true))) {
			return this.find("select a from " + obj + " a where a." + field
					+ "=? and a.company.id= ?", new Object[] { value,
					CommonUtils.getCompany().getId() });
		} else {
			return this.find("select a from " + obj + " a where a." + field
					+ "=?", new Object[] { value });
		}
	}

	// 自动导入
	public List findObjListCompany(String obj, String field, String value,
			Boolean isCompanyId, BaseCompany company) {
		if (isCompanyId.equals(new Boolean(true))) {
			return this.find("select a from " + obj + " a where a." + field
					+ "=? and a.company.id= ?", new Object[] { value,
					company.getId() });
		} else {
			return this.find("select a from " + obj + " a where a." + field
					+ "=?", new Object[] { value });
		}
	}

	public Object findObjectByPara(String sql, List fieldsList) {
		List list = this.find(sql, fieldsList.toArray());
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// 自动导入
	public List findTxtTaskSelRun(TxtTaskEx obj, BaseCompany company) {
		return this
				.find(
						"select a from TxtTaskSel a where a.txttask.excutekind<>? and a.txttask.executeState=? and a.company.id=?",
						new Object[] { "0", Integer.valueOf("1"),
								company.getId() });
	}
	
	public List findTxtTaskExForRun(BaseCompany company){
		return this.find(
				"select a from TxtTaskEx a where a.excutekind<>? and a.company.id=?",
				new Object[] { "0",
						company.getId() });
	}
	
	public List findTxtTaskSelForRun(TxtTaskEx head,BaseCompany company) {
		return this
				.find(
						"select a from TxtTaskSel a where a.txttask.id = ? and a.company.id=? order by a.seqNum",
						new Object[] { head.getId(),
								company.getId() });
	}

	public List findDBTaskSelRun(DBTaskEx obj, BaseCompany company) {
		return this
				.find(
						"select a from DBTaskSel a where a.dbtaskEx.id = ? order by a.seqNum",
						new Object[] { obj.getId() });// 0：未运行
	}

	public List findDBTaskEx(BaseCompany company) {
		return this
				.find(
						"select a from DBTaskEx a where a.excutekind <> ? and a.company.id=?",
						new Object[] { "0",company.getId() });
	}
	
	public List findTxtTaskEx(BaseCompany company) {
		return this
				.find(
						"select a from TxtTaskEx a where a.excutekind<>? and a.company.id=?",
						new Object[] { "0", 
								company.getId() });
	}

	/**
	 * 线程
	 * 
	 * @return
	 */
	public List findThread(String type) {
		return this.find("select a from ThreadList a where a.type=?",
				new Object[] { type });
	}

	public List findThreadList(String type) {
		return this.find(
				"select a from ThreadList a where a.company.id=? and a.type=?",
				new Object[] { CommonUtils.getCompany().getId(), type });
	}

	public void saveThreadList(ThreadList obj) throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	public void deleteThread(String type) {
		this.deleteAll(findThreadList(type));
	}

	public void deleteAllThread(String type) {
		this.deleteAll(findThread(type));
	}

	// DB导入
	/**
	 * 数据源
	 */
	public List findDBDataRoot() {
		return this.find("select a from DBDataRoot a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
	}
	

	/**
	 * 视图参数设置
	 * 
	 * @param obj
	 * @throws DataAccessException
	 */
	public List findDbDataExecuSql() {
		return this.find(
				"select a from DBDataExecuSql a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });

	}

	public void saveDBDataRoot(DBDataRoot obj) throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	public void deleteDBDataRoot(DBDataRoot obj) {
		this.delete(obj);
	}

	/**
	 * 视图
	 * 
	 */
	public List findView() {
		return this.find("select a from DBView a where a.company.id= ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public List findDBView(DBDataRoot db) {
		return this.find(
				"select a from DBView a where a.company.id= ? and a.db= ? ",
				new Object[] { CommonUtils.getCompany().getId(), db });
	}

	public void saveDBView(DBView obj) throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	public void deleteDBView(DBView obj) {
		this.delete(obj);
	}

	/**
	 * 域
	 * 
	 * @param dbView
	 * @return
	 */
	public List findDBForByDBView(DBView dbView) {
		return this
				.find(
						"select a from DBFormat a where a.company.id= ? and a.dbView.id= ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								dbView.getId() });
	}

	public List findDBFormatSetup(DBFormat dbFormat) {
		return this
				.find(
						"select a from DBFormatSetup a where a.company.id= ? and a.dbFormat.id= ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								dbFormat.getId() });
	}

	public List findDBFormatSetup(DBFormat dbFormat, BaseCompany company) {
		return this
				.find(
						"select a from DBFormatSetup a where a.company.id= ? and a.dbFormat.id= ? ",
						new Object[] { company.getId(), dbFormat.getId() });
	}

	public List findIsReAimName(DBFormat dbFormat, String aimName,
			BaseCompany company) {
		return this
				.find(
						"select a from DBFormatSetup a where a.company.id= ? and a.dbFormat.id= ? and a.aimFieldName= ? and a.gbflag = ?",
						new Object[] { company.getId(), dbFormat.getId(),
								aimName, Gbflag.OBJ });
	}

	/**
	 * 查找是否存在相同的目标名
	 * 
	 * @param dbFormat
	 * @param aimName
	 * @return
	 */
	public DBFormatSetup getisExistAimname(DBFormat dbFormat, String aimName) {
		List list = this
				.find(
						"select a from DBFormatSetup a where a.company.id= ? and a.dbFormat.id= ? and a.aimName= ?",
						new Object[] { CommonUtils.getCompany().getId(),
								dbFormat.getId(), aimName });
		if (list != null && list.size() > 0) {
			return (DBFormatSetup) list.get(0);
		}
		return null;
	}

	/**
	 * 得到主键行
	 * 
	 * @return
	 */
	public DBFormatSetup getIsKeySetup(DBFormat dbFormat) {
		List list = this
				.find(
						"select a from DBFormatSetup a where a.company.id= ? and a.dbFormat.id= ? and a.iskey = ? ",
						new Object[] { CommonUtils.getCompany().getId(),
								dbFormat.getId(), new Boolean(true) });
		if (list != null && list.size() > 0) {
			return (DBFormatSetup) list.get(0);
		}
		return null;
	}

	public DBFormatSetup getIsKeySetup(DBFormat dbFormat, BaseCompany company) {
		List list = this
				.find(
						"select a from DBFormatSetup a where a.company.id= ? and a.dbFormat.id= ? and a.iskey = ? ",
						new Object[] { company.getId(), dbFormat.getId(),
								new Boolean(true) });
		if (list != null && list.size() > 0) {
			return (DBFormatSetup) list.get(0);
		}
		return null;
	}

	public void saveDBFormat(DBFormat obj) throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	public void saveDBFormatSetup(DBFormatSetup obj) throws DataAccessException {
		this.saveOrUpdate(obj);
	}

	public void deleteDBFormat(DBFormat obj) {
		this.delete(obj);
	}

	public void deleteDBFormatSetup(DBFormatSetup obj) {
		this.delete(obj);
	}

	public void deleteAllSetup(DBFormat dbFormat) {
		this.deleteAll(findDBFormatSetup(dbFormat));
	}

	/**
	 * 获得域表通过筛选
	 * 
	 * @return
	 */
	public List findDBTaskFromDBTask(DBTaskEx dbTaskEx) {
		if (dbTaskEx.getIsParentTask() == null || (!dbTaskEx.getIsParentTask())) {
			return this
					.find(
							"select a from DBFormat a where a.company.id= ? and a.regionName "
									+ "not in (select b.dbFormat.regionName from DBTaskSel b where b.company.id=? and b.dbtaskEx.id=?)",
							new Object[] { CommonUtils.getCompany().getId(),
									CommonUtils.getCompany().getId(),
									dbTaskEx.getId() });
		} else {
			List list = this
					.find(
							"select a from DBTaskEx a where a.company.id= ? and a.id "
									+ "not in (select b.subDBTaskEx.id from DBTaskSel b where b.company.id=? and b.dbtaskEx.id=?)",
							new Object[] { CommonUtils.getCompany().getId(),
									CommonUtils.getCompany().getId(),
									dbTaskEx.getId() });
			list.remove(dbTaskEx);
			return list;
		}
	}

	public ClassList findbyid(String id) {
		List list = this.find("select a from ClassList a where a.id=?",
				new Object[] { id });
		return (ClassList) list.get(0);
	}

	public List findCustomsComplex() {
		return this.find("from CustomsComplex a");
	}

	public List findParameterList() {
		return this.find("from ParameterList a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}
	

	public List findParameterListByCompany(BaseCompany company) {
		return this.find("from ParameterList a where a.company.id = ?",
				new Object[] { company.getId() });
	}

	public void saveParameterList(ParameterList obj) {
		this.saveOrUpdate(obj);
	}

	public void deleteParameterList(ParameterList obj) {
		this.delete(obj);
	}

	/**
	 * 获得所有条件单据头的记录
	 * 
	 * @param billMasterClassName
	 * @param whereList
	 * @param company
	 * @return
	 */
	public List findBillMasterByWhere(String billMasterClassName,
			List<TempBillMasterKey> whereList, Company company) {
		List<Object> parameters = new ArrayList<Object>();

		String hsql = "select a from " + billMasterClassName
				+ " a left join fetch a.billType b "
				+ " where a.company.id = ? ";
		parameters.add(company.getId());

		for (int i = 0; i < whereList.size(); i++) {
			TempBillMasterKey temp = whereList.get(i);
			if (i == 0) {
				hsql += " and ( ( b.code = ? and a.billNo=? and  a.validDate=? ) ";
			} else {
				hsql += " or ( b.code = ? and a.billNo=? and  a.validDate=? ) ";
			}
			if (i == whereList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(temp.getBillTypeCode());
			parameters.add(temp.getBillNo());
			parameters.add(temp.getValidDate());
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 获得所有条件单据头的记录
	 * 
	 * @param billMasterClassName
	 * @param whereList
	 * @param company
	 * @return
	 */
	public void batchDeleteBillDetailByWhere(String billDetailClassName,
			List<TempBillMasterKey> whereList,
			List<DBFormatSetup> billMasterAimNameReList,
			String billMasterClassName, Company company) {

		List<Object> parameters = new ArrayList<Object>();
		String hsql = "delete from " + billDetailClassName
				+ " m where m.company.id = ? ";
		parameters.add(company.getId());

		boolean billTypeHas = false;
		boolean billNoHas = false;
		boolean validDateHas = false;
		for (int j = 0; j < billMasterAimNameReList.size(); j++) { // 修改
			DBFormatSetup dbFormatSetup = (DBFormatSetup) billMasterAimNameReList
					.get(j);// 字段对照
			String destFiledName = dbFormatSetup.getGbStr().getFieldname()
					.trim();
			if ("billType".equals(destFiledName)) {// 以字段 billType
				billTypeHas = true;
			} else if ("billNo".equals(destFiledName)) {// 以字段 billNo
				billNoHas = true;
			} else if ("validDate".equals(destFiledName)) {// 以字段
				validDateHas = true;
			}
		}
		if (billNoHas == false) {
			System.out.println("你太大意了,BillMaster 单据号 都没有对应");
			return;
		}

		String hsqlMaster = "select a.id from " + billMasterClassName + " a "
				+ (billTypeHas == true ? " left join a.billType b " : "")
				+ " where a.company.id = ? ";

		parameters.add(company.getId());

		if (billTypeHas == true && billNoHas == true && validDateHas == true) {
			for (int i = 0; i < whereList.size(); i++) {
				TempBillMasterKey temp = whereList.get(i);
				if (i == 0) {
					hsqlMaster += " and ( ( b.code = ? and a.billNo=? and  a.validDate=? ) ";
				} else {
					hsqlMaster += " or ( b.code = ? and a.billNo=? and  a.validDate=? ) ";
				}
				if (i == whereList.size() - 1) {
					hsqlMaster += " ) ";
				}
				parameters.add(temp.getBillTypeCode());
				parameters.add(temp.getBillNo());
				parameters.add(temp.getValidDate());
			}
		} else if (billTypeHas == false && billNoHas == true
				&& validDateHas == true) {
			for (int i = 0; i < whereList.size(); i++) {
				TempBillMasterKey temp = whereList.get(i);
				if (i == 0) {
					hsqlMaster += " and ( (  a.billNo=? and  a.validDate=? ) ";
				} else {
					hsqlMaster += " or ( a.billNo=? and  a.validDate=? ) ";
				}
				if (i == whereList.size() - 1) {
					hsqlMaster += " ) ";
				}
				parameters.add(temp.getBillNo());
				parameters.add(temp.getValidDate());
			}
		} else if (billTypeHas == true && billNoHas == true
				&& validDateHas == false) {
			for (int i = 0; i < whereList.size(); i++) {
				TempBillMasterKey temp = whereList.get(i);
				if (i == 0) {
					hsqlMaster += " and ( ( b.code = ? and a.billNo=? ) ";
				} else {
					hsqlMaster += " or ( b.code = ? and a.billNo=? ) ";
				}
				if (i == whereList.size() - 1) {
					hsqlMaster += " ) ";
				}
				parameters.add(temp.getBillTypeCode());
				parameters.add(temp.getBillNo());
			}
		} else if (billTypeHas == false && billNoHas == true
				&& validDateHas == false) {
			for (int i = 0; i < whereList.size(); i++) {
				TempBillMasterKey temp = whereList.get(i);
				if (i == 0) {
					hsqlMaster += " and ( ( a.billNo=? ) ";
				} else {
					hsqlMaster += " or ( a.billNo=? ) ";
				}
				if (i == whereList.size() - 1) {
					hsqlMaster += " ) ";
				}
				parameters.add(temp.getBillNo());
			}
		}

		hsql += " and m.billMaster.id  in ( " + hsqlMaster + " )";
		this.batchUpdateOrDelete(hsql, parameters.toArray());
	}

	/**
	 * 获得所有条件申请单据头的记录
	 * 
	 * @param billMasterClassName
	 * @param whereList
	 * @param company
	 * @return
	 */
	public List findImpExpRequestBillByWhere(String billMasterClassName,
			List<String> whereList, Company company) {
		List<Object> parameters = new ArrayList<Object>();

		String hsql = "select a from " + billMasterClassName
				+ " a left join fetch a.scmCoc b left join fetch a.wareSet  "
				+ " where a.company.id = ? ";
		parameters.add(company.getId());

		for (int i = 0; i < whereList.size(); i++) {
			String billNo = whereList.get(i);
			if (i == 0) {
				hsql += " and ( a.billNo = ?  ";
			} else {
				hsql += " or  a.billNo = ?  ";
			}
			if (i == whereList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(billNo);
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 获得所有条件申请单据头的记录
	 * 
	 * @param billMasterClassName
	 * @param whereList
	 * @param company
	 * @return
	 */
	public List findImpExpCommodityInfoByWhere(String billMasterClassName,
			List<TempImpExpRequestBillKey> whereList, Company company) {
		List<Object> parameters = new ArrayList<Object>();

		String hsql = "select a from "
				+ billMasterClassName
				+ " a left join fetch a.impExpRequestBill b left join fetch a.materiel m  "
				+ " where a.company.id = ? ";
		parameters.add(company.getId());

		for (int i = 0; i < whereList.size(); i++) {
			TempImpExpRequestBillKey temp = whereList.get(i);
			if (i == 0) {
				hsql += " and ( ( b.billNo = ?  and m.ptNo = ? ) ";
			} else {
				hsql += " or  ( b.billNo = ?  and m.ptNo = ? )  ";
			}
			if (i == whereList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(temp.getBillNo());
			parameters.add(temp.getPtNo());
		}
		return this.findNoCache(hsql, parameters.toArray());
	}

	
	/**
	 * 删除企业物料主档
	 * @param enterpriseMaterial
	 * @param ptNoList
	 * @param company
	 */
	public void batchDeleteEnterpriseMatereiel(String enterpriseMaterial,
			List<String> ptNoList, Company company) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "delete from " + enterpriseMaterial
				+ " m where m.company.id = ? ";
		parameters.add(company.getId());
		for (int i = 0; i < ptNoList.size(); i++) {
			String ptNo = ptNoList.get(i);
			if (i == 0) {
				hsql += " and ( m.ptNo = ?   ";
			} else {
				hsql += " or   m.ptNo = ?   ";
			}
			if (i == ptNoList.size() - 1) {
				hsql += " ) ";
			}
			parameters.add(ptNo);
		}
		this.batchUpdateOrDelete(hsql, parameters.toArray());
	}
		
		/**
		 * 删除内部归并
		 * @param enterpriseMaterial
		 * @param ptNoList
		 * @param company
		 */
		public void batchDeleteInnerMergeData(String innerMergeData,
				List<String> materielList, Company company) {
			List<Object> parameters = new ArrayList<Object>();
			String hsql = "delete from InnerMergeData m where m.company.id = ? ";
			parameters.add(company.getId());
			for (int i = 0; i < materielList.size(); i++) {
				String materiel = materielList.get(i);
				if (i == 0) {
					hsql += " and ( m.emsSerialNo = ?   ";
				} else {
					hsql += " or m.emsSerialNo = ?   ";
				}
				if (i == materielList.size() - 1) {
					hsql += " ) ";
				}
				parameters.add(materiel);
			}
			this.batchUpdateOrDelete(hsql, parameters.toArray());
		}
	


		public void saveDBQuerySql(DBQuerySql db) throws DataAccessException {
		       this.saveOrUpdate(db);
			}
			public List findDBQuerySql(){
				return this.find("select a from DBQuerySql a where a.company.id = ?",
						new Object[]{CommonUtils.getCompany().getId()});
			}
			
			public void deleteDBQuerySql(DBQuerySql db) throws DataAccessException {
			       this.delete(db);
				}
			

		public void saveSuperClassList(SuperClassList obj){
		      this.saveOrUpdate(obj);
		}
		public void saveSuperFieldList(SuperFieldList obj){
			  this.saveOrUpdate(obj);
		}
		public List findMaxSuperClassList(){
			return this.find("select max(a.seqNum) from SuperClassList a where a.company.id = ?",
					new Object[]{CommonUtils.getCompany().getId()});
		}
		public List findSuperClassList(){
			return this.find("select a from SuperClassList a where a.company.id = ?",
					new Object[]{CommonUtils.getCompany().getId()});
		}
		
		public List findSuperFieldListByClass(SuperClassList obj){
			return this.find("select a from SuperFieldList a where a.superClassList.id = ? and a.company.id = ?",
					new Object[]{obj.getId(),CommonUtils.getCompany().getId()});
		}
		
		public List findMaxSuperFieldList(SuperClassList obj){
			return this.find("select max(a.seqNum) from SuperFieldList a where a.superClassList.id = ? and a.company.id = ?",
					new Object[]{obj.getId(),CommonUtils.getCompany().getId()});
		}
		
		public List findCommonObj(String childClassName){
			try {
				Class cls = Class.forName(childClassName);
				Object obj = cls.newInstance();
				if (obj instanceof BaseScmEntity){
					return this.find("select a from "+childClassName+" a where a.company.id = ?",
							new Object[]{CommonUtils.getCompany().getId()});
				} else {
					return this.find("select a from "+childClassName+" a ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
			
		}
		public SuperFieldList getSuperField(SuperClassList classList){
			List ls = this.find("select a from SuperFieldList a where a.superClassList.id = ? and a.company.id = ? and a.isOnlyValue = ?",
					new Object[]{classList.getId(),CommonUtils.getCompany().getId(),Boolean.valueOf(true)});
			if (ls != null && ls.size()>0){
				return (SuperFieldList) ls.get(0);
			}
			return null;
		}

}