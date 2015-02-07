package com.bestway.common.dataexport.dao;

import java.util.List;

import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.dataexport.entity.JDBCDataSource;
import com.bestway.common.dataexport.entity.JDBCParameter;
import com.bestway.common.dataexport.entity.JDBCRegion;
import com.bestway.common.dataexport.entity.JDBCRegionSetup;
import com.bestway.common.dataexport.entity.JDBCSqlEvent;
import com.bestway.common.dataexport.entity.JDBCTask;
import com.bestway.common.dataexport.entity.JDBCTaskDetail;
import com.bestway.common.dataexport.entity.JDBCView;
import com.bestway.common.dataexport.entity.ThreadData;

public class DBExportDao extends BaseDao {

	// JDBCDataSource
	// JDBCParameter
	// JDBCRegion
	// JDBCRegionSetup
	// JDBCRegionSetupPara
	// JDBCSqlEvent
	// JDBCTask
	// JDBCTaskDetail
	// JDBCView
	// ThreadData

	/** 保存 JDBCDataSource */
	public JDBCDataSource saveJDBCDataSource(JDBCDataSource jdbcDataSource) {
		super.saveOrUpdate(jdbcDataSource);
		return jdbcDataSource;
	}

	/** 删除 JDBCDataSource */
	public void deleteJDBCDataSource(JDBCDataSource jdbcDataSource) {
		super.delete(jdbcDataSource);
	}

	/** 查找 JDBCDataSource */
	public List<JDBCDataSource> findJDBCDataSource() {
		String hsql = "from JDBCDataSource a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}

	/** 保存 JDBCParameter */
	public JDBCParameter saveJDBCParameter(JDBCParameter jdbcParameter) {
		super.saveOrUpdate(jdbcParameter);
		return jdbcParameter;
	}

	/** 删除 JDBCParameter */
	public void deleteJDBCParameter(JDBCParameter jdbcParameter) {
		super.delete(jdbcParameter);
	}

	/** 查找 JDBCParameter */
	public List<JDBCParameter> findJDBCParameter() {
		return this.findJDBCParameter(CommonUtils.getCompany());
	}

	/** 查找 JDBCParameter */
	public List<JDBCParameter> findJDBCParameter(BaseCompany company) {
		String hsql = "from JDBCParameter a where a.company = ? ";
		List list = super.find(hsql, company);
		return list;
	}

	/** 保存 JDBCRegion */
	public JDBCRegion saveJDBCRegion(JDBCRegion jdbcRegion) {
		super.saveOrUpdate(jdbcRegion);
		return jdbcRegion;
	}

	/** 删除 JDBCRegion */
	public void deleteJDBCRegion(JDBCRegion jdbcRegion) {
		super.delete(jdbcRegion);
	}

	/** 查找 JDBCRegion */
	public List<JDBCRegion> findJDBCRegion() {
		String hsql = "from JDBCRegion a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}

	/** 查找 JDBCRegion */
	public List<JDBCRegion> findJDBCRegion(JDBCView jdbcView) {
		String hsql = "from JDBCRegion a where a.company = ? and a.srcJDBCView = ? ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				jdbcView });
		return list;
	}

	/** 保存 JDBCRegionSetup */
	public JDBCRegionSetup saveJDBCRegionSetup(JDBCRegionSetup jdbcRegionSetup) {
		super.saveOrUpdate(jdbcRegionSetup);
		return jdbcRegionSetup;
	}

	/** 删除 JDBCRegionSetup */
	public void deleteJDBCRegionSetup(JDBCRegionSetup jdbcRegionSetup) {
		super.delete(jdbcRegionSetup);
	}

	/** 查找 JDBCRegionSetup */
	public List<JDBCRegionSetup> findJDBCRegionSetup() {
		String hsql = "from JDBCRegionSetup a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}

	/** 查找 JDBCRegionSetup */
	public List<JDBCRegionSetup> findJDBCRegionSetup(String jdbcRegionId) {
		return this.findJDBCRegionSetup(jdbcRegionId, (Company) CommonUtils
				.getCompany());
	}

	/** 查找 JDBCRegionSetup */
	public List<JDBCRegionSetup> findJDBCRegionSetup(String jdbcRegionId,
			Company company) {
		String hsql = "from JDBCRegionSetup a where a.company = ? and a.jdbcRegion.id = ? ";
		List list = super.find(hsql, new Object[] { company, jdbcRegionId });
		return list;
	}

	/** 保存 JDBCSqlEvent */
	public void saveJDBCSqlEvent(JDBCSqlEvent jdbcSqlEvent) {
		this.saveOrUpdate(jdbcSqlEvent);
	}

	/** 删除 JDBCSqlEvent */
	public void deleteJDBCSqlEvent(JDBCSqlEvent jdbcSqlEvent) {
		super.delete(jdbcSqlEvent);
	}

	/** 查找 JDBCSqlEvent */
	public List<JDBCSqlEvent> findJDBCSqlEvent() {
		String hsql = "from JDBCSqlEvent a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}

	/** 查找 JDBCSqlEvent */
	public List<JDBCSqlEvent> findJDBCSqlEvent(JDBCDataSource jdbcDataSource) {
		String hsql = "from JDBCSqlEvent a where a.company = ?  and a.jdbcDataSource = ? ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				jdbcDataSource });
		return list;
	}

	/** 保存 JDBCTask */
	public JDBCTask saveJDBCTask(JDBCTask jdbcTask) {
		super.saveOrUpdate(jdbcTask);
		return jdbcTask;
	}

	/** 删除 JDBCTask */
	public void deleteJDBCTask(JDBCTask jdbcTask) {
		super.delete(jdbcTask);
	}

	/** 查找 JDBCTask */
	public List<JDBCTask> findJDBCTask() {
		String hsql = "from JDBCTask a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}
	
	/** 查找 JDBCTask */
	public List<JDBCTask> findJDBCTask(Company company) {
		String hsql = "from JDBCTask a where a.company = ? ";
		List list = super.findNoCache(hsql, new Object[]{company});
		return list;
	}

	/** 查找 JDBCTask */
	public boolean isExecute(JDBCTask jdbcTask) {
		String hsql = "from JDBCTask a where a.id = ? ";
		List list = super.find(hsql, jdbcTask.getId());
		if (list.size() > 0) {
			JDBCTask temp = (JDBCTask) list.get(0);
			return temp.getIsExecute() == null ? false : temp.getIsExecute();
		}
		return true;
	}

	/** 保存 JDBCTaskDetail */
	public JDBCTaskDetail saveJDBCTaskDetail(JDBCTaskDetail jdbcTaskDetail) {
		super.saveOrUpdate(jdbcTaskDetail);
		return jdbcTaskDetail;
	}

	/** 删除 JDBCTaskDetail */
	public void deleteJDBCTaskDetail(JDBCTaskDetail jdbcTaskDetail) {
		super.delete(jdbcTaskDetail);
	}

	/** 查找 JDBCTaskDetail */
	public List<JDBCTaskDetail> findJDBCTaskDetail() {
		String hsql = "from JDBCTaskDetail a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}

	/** 查找 JDBCTaskDetail */
	public List<JDBCTaskDetail> findJDBCTaskDetail(JDBCTask jdbcTask) {
		String hsql = "";
		boolean isParentTask = jdbcTask.getIsParentTask() == null ? false
				: jdbcTask.getIsParentTask();
		if (isParentTask) {
			hsql = "from JDBCTaskDetail a where a.jdbcTask = ? and a.taskType = ? order by a.seqNum ";
		} else {
			hsql = "from JDBCTaskDetail a where a.jdbcTask = ? and a.taskType != ? order by a.seqNum ";
		}
		List list = super.find(hsql, new Object[] { jdbcTask,
				JDBCTaskDetail.SUB_JDBC_TASK_TYPE });
		return list;
	}

	/** 保存 JDBCView */
	public JDBCView saveJDBCView(JDBCView jdbcView) {
		super.saveOrUpdate(jdbcView);
		return jdbcView;
	}

	/** 删除 JDBCView */
	public void deleteJDBCView(JDBCView jdbcView) {
		super.delete(jdbcView);
	}

	/** 查找 JDBCView */
	public List<JDBCView> findJDBCView() {
		String hsql = "from JDBCView a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}

	/** 查找 JDBCView */
	public List<JDBCView> findJDBCView(JDBCDataSource jdbcDataSource) {
		String hsql = "from JDBCView a where a.company = ?  and a.jdbcDataSource = ?  ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				jdbcDataSource });
		return list;
	}

	/** 保存 ThreadData */
	public ThreadData saveThreadData(ThreadData threadData) {
		super.saveOrUpdate(threadData);
		return threadData;
	}

	/** 删除 ThreadData */
	public void deleteThreadData(ThreadData threadData) {
		super.delete(threadData);
	}

	/** 查找 ThreadData */
	public List<ThreadData> findAllDBThreadData() {
		String hsql = "from ThreadData a where a.type = ? ";
		List list = super.find(hsql, ThreadData.DB_TYPE);
		return list;
	}

	/** 查找 ThreadData */
	public List<ThreadData> findThreadData() {
		String hsql = "from ThreadData a where a.company = ? ";
		List list = super.find(hsql, CommonUtils.getCompany());
		return list;
	}

	/** 查找 ThreadData */
	public ThreadData findThreadData(int type) {
		String hsql = "from ThreadData a where a.company = ? and a.type = ? ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				type });
		if(list != null && list.size()>0){
			return (ThreadData)list.get(0);
		}
		return null;
	}

	/** 查找 JDBCRegion */
	public List<JDBCRegion> findJDBCRegionNotInJDBCTask(JDBCTask jdbcTask) {
		String hsql = "from JDBCRegion a where a.company = ? and "
				+ " a.id not in ( select jdbcRegion.id from JDBCTaskDetail b "
				+ " left outer join b.jdbcRegion jdbcRegion "
				+ " left outer join b.subJDBCTask subJdbcTask "
				+ " left outer join b.jdbcSqlEvent where b.jdbcTask = ? and b.jdbcRegion is not null ) ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				jdbcTask });
		return list;
	}

	/** 查找 JDBCTask 的子任务 */
	public List<JDBCTask> findSubJDBCTaskNotInJDBCTask(JDBCTask jdbcTask) {
		String hsql = "from JDBCTask a where a.company = ? and "
				+ " a.id != ? and a.id not in ( select subJdbcTask.id from JDBCTaskDetail b "
				+ " left outer join b.jdbcRegion jdbcRegion "
				+ " left outer join b.subJDBCTask subJdbcTask "
				+ " left outer join b.jdbcSqlEvent jdbcSqlEvent"
				+ "  where b.jdbcTask = ? "
				+ " and b.subJDBCTask is not null ) "
				+ "and ( a.isParentTask = ? or a.isParentTask is null ) ) ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				jdbcTask.getId(), jdbcTask, false });
		return list;
	}

	/** 查找 JDBCSqlEvent */
	public List<JDBCSqlEvent> findJDBCSqlEventNotInJDBCTask(JDBCTask jdbcTask) {
		String hsql = "from JDBCSqlEvent a where a.company = ? and "
				+ " a.id not in ( select jdbcSqlEvent.id from JDBCTaskDetail b "
				+ " left outer join b.jdbcRegion jdbcRegion "
				+ " left outer join b.subJDBCTask subJdbcTask "
				+ " left outer join b.jdbcSqlEvent jdbcSqlEvent where b.jdbcTask = ? and b.jdbcSqlEvent is not null) ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				jdbcTask });
		return list;
	}

	/** 查找 JDBCRegion */
	public int findJDBCTaskDetailMaxSeqNum(JDBCTask jdbcTask) {
		int num = 0;
		String hsql = "select max(a.seqNum) from JDBCTaskDetail a where a.company = ? and "
				+ " a.jdbcTask = ? ";
		List list = super.find(hsql, new Object[] { CommonUtils.getCompany(),
				jdbcTask });
		if (list.get(0) != null) {
			return (Integer) list.get(0);
		}
		return num;
	}

	public List<Gbtobig> findGbtobig() {
		return find("from Gbtobig");
	}

}
