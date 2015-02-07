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
 * db任务主表
 * @author db任务主表
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JDBCTask extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 * 任务名称
	 */
	private String				taskname			= null;						
	/**
	 * 运行方式
	 */
	private Integer				executekind			= null;					
	/**
	 * 运行日期
	 */
	private String				excuteday			= null;						
	/**
	 * 运行时间
	 */
	private String				excutetime			= null;					
	/**
	 * 任务说明
	 */
	private String				note				= null;						
	/**
	 * 是否是父任务
	 */
	private Boolean				isParentTask		= false;						
	/**
	 * 是否正在执行
	 */
	private Boolean				isExecute			= false;						
	/**
	 * 导入数据方式
	 */
	private Integer				importDataMode		= null;						
																				

	/**
	 * @return Returns the excuteday.
	 */
	public String getExcuteday() {
		return excuteday;
	}

	/**
	 * @param excuteday
	 *            The excuteday to set.
	 */
	public void setExcuteday(String excuteday) {
		this.excuteday = excuteday;
	}

	/**
	 * @return Returns the excutetime.
	 */
	public String getExcutetime() {
		return excutetime;
	}

	/**
	 * @param excutetime
	 *            The excutetime to set.
	 */
	public void setExcutetime(String excutetime) {
		this.excutetime = excutetime;
	}

	/**
	 * @return Returns the taskname.
	 */
	public String getTaskname() {
		return taskname;
	}

	/**
	 * @param taskname
	 *            The taskname to set.
	 */
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            The note to set.
	 */
	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getIsParentTask() {
		return isParentTask;
	}

	public void setIsParentTask(Boolean isParentTask) {
		this.isParentTask = isParentTask;
	}

	public Boolean getIsExecute() {
		return isExecute;
	}

	public void setIsExecute(Boolean isExecute) {
		this.isExecute = isExecute;
	}

	public Integer getExecutekind() {
		return executekind;
	}

	public void setExecutekind(Integer executekind) {
		this.executekind = executekind;
	}

	public Integer getImportDataMode() {
		return importDataMode;
	}

	public void setImportDataMode(Integer importDataMode) {
		this.importDataMode = importDataMode;
	}
}
