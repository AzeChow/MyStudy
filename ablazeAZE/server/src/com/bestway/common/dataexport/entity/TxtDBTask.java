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
public class TxtDBTask extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 * 任务名称
	 */
	private String				taskname			= null;						
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

	// 导入数据方式

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
}
