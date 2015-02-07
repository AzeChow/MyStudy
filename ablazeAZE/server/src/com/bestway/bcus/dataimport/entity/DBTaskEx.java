/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * db任务主表
 * @author db任务主表
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DBTaskEx extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 任务名称
	 */
    private String taskname;    
	/**
	 * 运行方式
	 */
    private String excutekind;  
	/**
	 * 执行状态
	 * 0:未执行
	 * 1:正在执行
	 * 2:执行结束
	 */
    private Integer executeState;
	/**
	 * 运行日期
	 */
    private String excuteday;    
	/**
	 * 运行时间
	 */
    private String excutetime;   
	/**
	 * 导入选项
	 */
    private String inputSelect;  
	/**
	 * 任务创建日期
	 */
    private Date createdate;     
	/**
	 * 任务创建者
	 */
    private String createuser;   
	/**
	 * 是否生效
	 */
    private Boolean iseffice=false;  
	/**
	 * 生效日期
	 */
    private Date efficeDate;     
	/**
	 * 任务说明
	 */
    private String note;        
	/**
	 * 处理方式
	 */
    private String deal;        
	
	/**
	 * 是否是父任务
	 */
    private Boolean isParentTask =false;
	
	/**
	 * @return Returns the createdate.
	 */
	public Date getCreatedate() {
		return createdate;
	}
	/**
	 * @param createdate The createdate to set.
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	/**
	 * @return Returns the createuser.
	 */
	public String getCreateuser() {
		return createuser;
	}
	/**
	 * @param createuser The createuser to set.
	 */
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	/**
	 * @return Returns the efficeDate.
	 */
	public Date getEfficeDate() {
		return efficeDate;
	}
	/**
	 * @param efficeDate The efficeDate to set.
	 */
	public void setEfficeDate(Date efficeDate) {
		this.efficeDate = efficeDate;
	}
	/**
	 * @return Returns the excuteday.
	 */
	public String getExcuteday() {
		return excuteday;
	}
	/**
	 * @param excuteday The excuteday to set.
	 */
	public void setExcuteday(String excuteday) {
		this.excuteday = excuteday;
	}
	/**
	 * @return Returns the excutekind.
	 */
	public String getExcutekind() {
		return excutekind;
	}
	/**
	 * @param excutekind The excutekind to set.
	 */
	public void setExcutekind(String excutekind) {
		this.excutekind = excutekind;
	}
	/**
	 * @return Returns the excutetime.
	 */
	public String getExcutetime() {
		return excutetime;
	}
	/**
	 * @param excutetime The excutetime to set.
	 */
	public void setExcutetime(String excutetime) {
		this.excutetime = excutetime;
	}
	/**
	 * @return Returns the inputSelect.
	 */
	public String getInputSelect() {
		return inputSelect;
	}
	/**
	 * @param inputSelect The inputSelect to set.
	 */
	public void setInputSelect(String inputSelect) {
		this.inputSelect = inputSelect;
	}
	/**
	 * @return Returns the iseffice.
	 */
	public Boolean getIseffice() {
		return iseffice;
	}
	/**
	 * @param iseffice The iseffice to set.
	 */
	public void setIseffice(Boolean iseffice) {
		this.iseffice = iseffice;
	}
	/**
	 * @return Returns the taskname.
	 */
	public String getTaskname() {
		return taskname;
	}
	/**
	 * @param taskname The taskname to set.
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
	 * @param note The note to set.
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return Returns the executeState.
	 */
	public Integer getExecuteState() {
		return executeState;
	}
	/**
	 * @param executeState The executeState to set.
	 */
	public void setExecuteState(Integer executeState) {
		this.executeState = executeState;
	}
	/**
	 * @return Returns the deal.
	 */
	public String getDeal() {
		return deal;
	}
	/**
	 * @param deal The deal to set.
	 */
	public void setDeal(String deal) {
		this.deal = deal;
	}
	public Boolean getIsParentTask() {
		return isParentTask;
	}
	public void setIsParentTask(Boolean isParentTask) {
		this.isParentTask = isParentTask;
	}
}
