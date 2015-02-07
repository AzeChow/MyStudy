/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 文本日志
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TxtLog extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 类型
	 */
    private String type;        
	/**
	 * 任务名称
	 */
    private String taskName;     
	/**
	 * 执行结果
	 */
    private String result;      
	/**
	 * 其他说明
	 */
    private String otherNote;   
	/**
	 * 开始执行时间
	 */
    private String beginDate;   
	/**
	 * 完成结束时间
	 */
    private String endDate;       
	/**
	 * 执行机器
	 */
    private String computer;    
	/**
	 * 执行人
	 */
    private String excuter;      

	/**
	 * @return Returns the beginDate.
	 */
	public String getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate The beginDate to set.
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return Returns the computer.
	 */
	public String getComputer() {
		return computer;
	}
	/**
	 * @param computer The computer to set.
	 */
	public void setComputer(String computer) {
		this.computer = computer;
	}
	/**
	 * @return Returns the endDate.
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the excuter.
	 */
	public String getExcuter() {
		return excuter;
	}
	/**
	 * @param excuter The excuter to set.
	 */
	public void setExcuter(String excuter) {
		this.excuter = excuter;
	}
	/**
	 * @return Returns the otherNote.
	 */
	public String getOtherNote() {
		return otherNote;
	}
	/**
	 * @param otherNote The otherNote to set.
	 */
	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}
	/**
	 * @return Returns the result.
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result The result to set.
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return Returns the taskName.
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName The taskName to set.
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}
}
