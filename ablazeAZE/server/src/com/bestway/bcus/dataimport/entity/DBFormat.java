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
 * 域定义对应表主表
 * @author 
 */
public class DBFormat extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 目标对象
     */
    private ClassList classList;  
    /**
     * 视图对象
     */
    private DBView dbView;        
	/**
	 * 域名称
	 */
    private String regionName;    
	/**
	 * 域描述
	 */
    private String memo;       
    /**
     * 编码转换
     */
    private Boolean isBigToBgk;
	/**
	 * 转换标致
	 */
    private String gbflag;        
	/**
	 * 域创建者
	 */
    private String creator;     
	/**
	 * 域修改者
	 */
    private String editor;        
	/**
	 * 建立日期
	 */
    private Date createdate;      
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
	 * @return Returns the dbView.
	 */
	public DBView getDbView() {
		return dbView;
	}
	/**
	 * @param dbView The dbView to set.
	 */
	public void setDbView(DBView dbView) {
		this.dbView = dbView;
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
	 * @return Returns the regionName.
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * @param regionName The regionName to set.
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public Boolean getIsBigToBgk() {
		return isBigToBgk;
	}
	public void setIsBigToBgk(Boolean isBigToBgk) {
		this.isBigToBgk = isBigToBgk;
	}
}
