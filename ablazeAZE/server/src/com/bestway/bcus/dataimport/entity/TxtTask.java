/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 文本任务
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TxtTask extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 目标对象
     */
    private ClassList classList;
	/**
	 * 格式名称
	 */
    private String taskname;     
	/**
	 * 其它说明
	 */
    private String memo;         
	/**
	 * 转换标致
	 * 0:不转换;
	 * 1:繁转简;
	 * 2:简转繁;
	 */
    private String gbflag;       
	/**
	 * 格式创建者
	 */
    private String creator;     
	/**
	 * 格式修改者
	 */
    private String editor;       
	/**
	 * 建立日期
	 */
    private Date createdate;    

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
	 * @return Returns the creator.
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator The creator to set.
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return Returns the editor.
	 */
	public String getEditor() {
		return editor;
	}
	/**
	 * @param editor The editor to set.
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	/**
	 * @return Returns the gbflag.
	 */
	public String getGbflag() {
		return gbflag;
	}
	/**
	 * @param gbflag The gbflag to set.
	 */
	public void setGbflag(String gbflag) {
		this.gbflag = gbflag;
	}
	/**
	 * @return Returns the memo.
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo The memo to set.
	 */
	public void setMemo(String memo) {
		this.memo = memo;
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
	 * @return Returns the classList.
	 */
	public ClassList getClassList() {
		return classList;
	}
	/**
	 * @param classList The classList to set.
	 */
	public void setClassList(ClassList classList) {
		this.classList = classList;
	}
}
