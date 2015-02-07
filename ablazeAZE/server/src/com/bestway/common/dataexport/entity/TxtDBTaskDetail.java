/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 任务明细
 * @author lin 任务明细 // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class TxtDBTaskDetail extends BaseScmEntity {
	private static final long	serialVersionUID		= CommonUtils
																.getSerialVersionUID();
	/** 事件类型 */
	public static final int		JDBC_SQL_EVENT_TYPE		= 0;
	/** 对应域类型导入 Txt To DB */
	public static final int		TXT_TO_DB_REGION_TYPE	= 1;
	/** 对应域类型导出 DB To Txt */
	public static final int		DB_TO_TXT_REGION_TYPE	= 5;
	/** 子任务类型 */
	public static final int		SUB_JDBC_TASK_TYPE		= 2;

	/**
	 *  Txt任务
	 */
	private TxtDBTask			txtDBTask				= null;
	/**
	 *  执行顺序
	 */
	private Integer				seqNum					= null;
	/**
	 *  任务类型
	 *  JDBC_SQL_EVENT_TYPE		= 0;	事件类型
	 *  TXT_TO_DB_REGION_TYPE	= 1;	对应域类型导入 Txt To DB
	 *  DB_TO_TXT_REGION_TYPE	= 5;	对应域类型导出 DB To Txt
	 *  SUB_JDBC_TASK_TYPE		= 2;	子任务类型
	 */
	private Integer				taskType				= -1;
	/**
	 *  对应域类型导入 Txt To DB
	 */
	private TxtToDBRegion		txtToDBRegion			= null;
	/**
	 *  对应域类型导出 DB To Txt
	 */
	private DBToTxtRegion		dbToTxtRegion			= null;
	/**
	 *  事件执行
	 */
	private JDBCSqlEvent		jdbcSqlEvent			= null;
	/**
	 *  子任务
	 */
	private TxtDBTask			subTxtDBTask			= null;

	public TxtDBTask getTxtDBTask() {
		return txtDBTask;
	}

	public void setTxtDBTask(TxtDBTask txtTask) {
		this.txtDBTask = txtTask;
	}

	public TxtToDBRegion getTxtToDBRegion() {
		return txtToDBRegion;
	}

	public void setTxtToDBRegion(TxtToDBRegion txtToDBRegion) {
		this.txtToDBRegion = txtToDBRegion;
	}

	public JDBCSqlEvent getJdbcSqlEvent() {
		return jdbcSqlEvent;
	}

	public void setJdbcSqlEvent(JDBCSqlEvent jdbcSqlEvent) {
		this.jdbcSqlEvent = jdbcSqlEvent;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	

	public TxtDBTask getSubTxtDBTask() {
		return subTxtDBTask;
	}

	public void setSubTxtDBTask(TxtDBTask subTxtDBTask) {
		this.subTxtDBTask = subTxtDBTask;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public DBToTxtRegion getDbToTxtRegion() {
		return dbToTxtRegion;
	}

	public void setDbToTxtRegion(DBToTxtRegion dbToTxtRegion) {
		this.dbToTxtRegion = dbToTxtRegion;
	}

}
