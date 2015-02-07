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
public class JDBCTaskDetail extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/** 
	 * 事件类型
	 */
	public static final int		JDBC_SQL_EVENT_TYPE	= 0;
	/**
	 *  对应域类型
	 */
	public static final int		JDBC_REGION_TYPE	= 1;
	/**
	 *  子任务类型
	 */
	public static final int		SUB_JDBC_TASK_TYPE	= 2;
	/**
	 * DB任务
	 */
	private JDBCTask			jdbcTask			= null;						
	/**
	 * 执行顺序
	 */
	private Integer				seqNum				= null;						
	/**
	 * 任务类型
	 */
	private Integer				taskType			= -1;							
	/**
	 * 域
	 */
	private JDBCRegion			jdbcRegion			= null;						
	/**
	 * 事件执行
	 */
	private JDBCSqlEvent		jdbcSqlEvent		= null;						
	/**
	 * 子任务
	 */
	private JDBCTask			subJDBCTask			= null;						

	public JDBCRegion getJdbcRegion() {
		return jdbcRegion;
	}

	public void setJdbcRegion(JDBCRegion jdbcRegion) {
		this.jdbcRegion = jdbcRegion;
	}

	public JDBCSqlEvent getJdbcSqlEvent() {
		return jdbcSqlEvent;
	}

	public void setJdbcSqlEvent(JDBCSqlEvent jdbcSqlEvent) {
		this.jdbcSqlEvent = jdbcSqlEvent;
	}

	public JDBCTask getJdbcTask() {
		return jdbcTask;
	}

	public void setJdbcTask(JDBCTask jdbcTask) {
		this.jdbcTask = jdbcTask;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public JDBCTask getSubJDBCTask() {
		return subJDBCTask;
	}

	public void setSubJDBCTask(JDBCTask subJDBCTask) {
		this.subJDBCTask = subJDBCTask;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

}
