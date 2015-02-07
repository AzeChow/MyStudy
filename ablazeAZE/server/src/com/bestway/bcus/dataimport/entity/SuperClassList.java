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
 * 任务列表
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SuperClassList extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 序号
	 */
    private Integer seqNum;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务备注
     */    
    private String memo;
    /**
     * 导入选项（更新还是递增）
     */
    private Integer importSelect;
    /**
     * 是否繁转简
     */
    private Boolean isFtoJ;
    /**
     * 导入的类
     */
    private String className;
    
    
    
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getImportSelect() {
		return importSelect;
	}
	public void setImportSelect(Integer importSelect) {
		this.importSelect = importSelect;
	}
	public Boolean getIsFtoJ() {
		return isFtoJ;
	}
	public void setIsFtoJ(Boolean isFtoJ) {
		this.isFtoJ = isFtoJ;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
    
    
   
}
