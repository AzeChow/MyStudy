package com.bestway.common.dataexport.dao;

import java.util.List;

import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.dataexport.entity.DBToTxtRegion;
import com.bestway.common.dataexport.entity.JDBCView;
import com.bestway.common.dataexport.entity.TxtDBTask;
import com.bestway.common.dataexport.entity.TxtDBTaskDetail;
import com.bestway.common.dataexport.entity.TxtToDBRegion;
import com.bestway.common.dataexport.entity.TxtToDBRegionSetup;

public class TextExportDao extends BaseDao {
	// DBToTxtRegion
	// TxtToDBRegion
	// TxtToDBRegionSetup
	// TxtDBTask
	// TxtDBTaskDetail

	/** 保存 DBToTxtRegion */
	public DBToTxtRegion saveDBToTxtRegion(DBToTxtRegion dbToTxtRegion) {
		super.saveOrUpdate(dbToTxtRegion);
		return dbToTxtRegion;
	}

	/** 删除 DBToTxtRegion */
	public void deleteDBToTxtRegion(DBToTxtRegion dbToTxtRegion) {
		super.delete(dbToTxtRegion);
	}

	/** 查找 DBToTxtRegion */
	public List<DBToTxtRegion> findDBToTxtRegion() {
		String hsql = "from DBToTxtRegion a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}
	
	/** 查找 DBToTxtRegion */
	public List<DBToTxtRegion> findDBToTxtRegion(JDBCView jdbcView){
		String hsql = "from DBToTxtRegion a where a.company = ? and a.srcJDBCView.id = ? ";
		List list = super.find(hsql, new Object[]{CommonUtils.getCompany(),jdbcView.getId()});
		return list;
	}

	
	/** 保存 TxtToDBRegion */
	public TxtToDBRegion saveTxtToDBRegion(TxtToDBRegion txtToDBRegion) {
		super.saveOrUpdate(txtToDBRegion);
		return txtToDBRegion;
	}

	/** 删除 TxtToDBRegion */
	public void deleteTxtToDBRegion(TxtToDBRegion txtToDBRegion) {
		super.delete(txtToDBRegion);
	}

	/** 查找 TxtToDBRegion */
	public List<TxtToDBRegion> findTxtToDBRegion() {
		String hsql = "from TxtToDBRegion a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}

	/** 保存 TxtToDBRegionSetup */
	public TxtToDBRegionSetup saveTxtToDBRegionSetup(
			TxtToDBRegionSetup txtToDBRegionSetup) {
		super.saveOrUpdate(txtToDBRegionSetup);
		return txtToDBRegionSetup;
	}

	/** 删除 TxtToDBRegionSetup */
	public void deleteTxtToDBRegionSetup(TxtToDBRegionSetup txtToDBRegionSetup) {
		super.delete(txtToDBRegionSetup);
	}

	/** 查找 TxtToDBRegionSetup */
	public List<TxtToDBRegionSetup> findTxtToDBRegionSetup() {
		String hsql = "from TxtToDBRegionSetup a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}
	
	/** 查找 TxtToDBRegionSetup */
	public List<TxtToDBRegionSetup> findTxtToDBRegionSetup(String txtToDBRegionId) {		
		String hsql = "from TxtToDBRegionSetup a where a.company = ? and a.txtToDBRegion.id = ? ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(), txtToDBRegionId });
		return list;
	}	
	

	/** 保存 TxtDBTask */
	public TxtDBTask saveTxtDBTask(TxtDBTask txtDBTask) {
		super.saveOrUpdate(txtDBTask);
		return txtDBTask;
	}

	/** 删除 TxtDBTask */
	public void deleteTxtDBTask(TxtDBTask txtDBTask) {
		super.delete(txtDBTask);
	}

	/** 查找 TxtDBTask */
	public List<TxtDBTask> findTxtDBTask() {
		String hsql = "from TxtDBTask a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}

	/** 保存 TxtDBTaskDetail */
	public TxtDBTaskDetail saveTxtDBTaskDetail(TxtDBTaskDetail txtDBTaskDetail) {
		super.saveOrUpdate(txtDBTaskDetail);
		return txtDBTaskDetail;
	}

	/** 删除 TxtDBTaskDetail */
	public void deleteTxtDBTaskDetail(TxtDBTaskDetail txtDBTaskDetail) {
		super.delete(txtDBTaskDetail);
	}

	/** 查找 TxtDBTaskDetail */
	public List<TxtDBTaskDetail> findTxtDBTaskDetail() {
		String hsql = "from TxtDBTaskDetail a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}
	
	/** 查找 TxtDBTaskDetail */
	public List<TxtDBTaskDetail> findTxtDBTaskDetail(String txtDBTaskId) {		
		String hsql = "from TxtDBTaskDetail a where a.company = ? and a.txtDBTask.id = ? ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(), txtDBTaskId });
		return list;		
	}
	
		
	/** 查找 TxtDBTaskDetail */
	public List<TxtDBTaskDetail> findTxtDBTaskDetail(TxtDBTask txtDBTask) {		
		String hsql = "";
		boolean isParentTask = txtDBTask.getIsParentTask() == null ? false
				: txtDBTask.getIsParentTask();
		if (isParentTask) {
			hsql = "from TxtDBTaskDetail a where a.txtDBTask = ? and a.taskType = ? order by a.seqNum ";
		} else {
			hsql = "from TxtDBTaskDetail a where a.txtDBTask = ? and a.taskType != ? order by a.seqNum ";
		}
		List list = super.find(hsql, new Object[] { txtDBTask,
				TxtDBTaskDetail.SUB_JDBC_TASK_TYPE });
		return list;			
	}

	
	/** 查找 TxtDBTask */
	public boolean isExecute(TxtDBTask txtDBTask) {
		String hsql = "from TxtDBTask a where a.id = ? ";
		List list = super.find(hsql, txtDBTask.getId());
		if (list.size() > 0) {
			TxtDBTask temp = (TxtDBTask) list.get(0);
			return temp.getIsExecute() == null ? false : temp.getIsExecute();
		}
		return true;
	}
	
	/** 查找 JDBCRegion */
	public int findTxtDBTaskDetailMaxSeqNum(TxtDBTask txtDBTask) {
		int num = 0;
		String hsql = "select max(a.seqNum) from TxtDBTaskDetail a where a.company = ? and "
				+ " a.txtDBTask = ? ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				txtDBTask });
		if (list.get(0) != null) {
			return (Integer) list.get(0);
		}
		return num;
	}
	

	
	/** 查找 DBToTxtRegion */
	public List<DBToTxtRegion> findDBToTxtRegionNotInTxtDBTask(TxtDBTask txtDBTask) {
		String hsql = "from DBToTxtRegion a where a.company = ? and "
				+ " a.id not in ( select dbToTxtRegion.id from TxtDBTaskDetail b "
				+ " left outer join b.dbToTxtRegion dbToTxtRegion "
				+ " left outer join b.txtToDBRegion txtToDBRegion "
				+ " left outer join b.subTxtDBTask subTxtDBTask "
				+ " left outer join b.jdbcSqlEvent where b.txtDBTask = ? and b.dbToTxtRegion is not null ) ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				txtDBTask });
		return list;
	}
	
	
	/** 查找 TxtToDBRegion */
	public List<TxtToDBRegion> findTxtToDBRegionNotInTxtDBTask(TxtDBTask txtDBTask) {
		String hsql = "from TxtToDBRegion a where a.company = ? and "
				+ " a.id not in ( select txtToDBRegion.id from TxtDBTaskDetail b "
				+ " left outer join b.dbToTxtRegion dbToTxtRegion "
				+ " left outer join b.txtToDBRegion txtToDBRegion "
				+ " left outer join b.subTxtDBTask subTxtDBTask "
				+ " left outer join b.jdbcSqlEvent where b.txtDBTask = ? and b.txtToDBRegion is not null ) ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				txtDBTask });
		return list;
	}
	
	

	/** 查找 TxtDBTask 的子任务 */
	public List<TxtDBTask> findSubTxtDBTaskNotInTxtDBTask(TxtDBTask txtDBTask) {
		String hsql = "from TxtDBTask a where a.company = ? and "
				+ " a.id != ? and a.id not in ( select subTxtDBTask.id from TxtDBTaskDetail b "
				+ " left outer join b.dbToTxtRegion dbToTxtRegion "
				+ " left outer join b.txtToDBRegion txtToDBRegion "
				+ " left outer join b.subTxtDBTask subTxtDBTask "
				+ " left outer join b.jdbcSqlEvent "
				+ "  where b.txtDBTask = ? "
				+ " and b.subTxtDBTask is not null ) "
				+ "and ( a.isParentTask = ? or a.isParentTask is null ) ) ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				txtDBTask.getId(), txtDBTask, false });
		return list;
	}
	
	
	
}
