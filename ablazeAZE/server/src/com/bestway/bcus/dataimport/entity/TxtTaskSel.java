/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 文本任务选择
 * @author lin
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TxtTaskSel extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 文本任务表
	 */
    private TxtTask txttable;
    /**
     * 执行顺序
     */
    private Integer seqNum;   
    /**
     * 事件执行
     */
    private DBDataExecuSql execuSql; 
	/**
	 * 任务说明
	 */
    private TxtTaskEx txttask;
    /**
     * 子任务说明
     */
	private TxtTaskEx subDBTaskEx;

	/**
	 * @return Returns the txttable.
	 */
	public TxtTask getTxttable() {
		return txttable;
	}
	/**
	 * @param txttable The txttable to set.
	 */
	public void setTxttable(TxtTask txttable) {
		this.txttable = txttable;
	}
	/**
	 * @return Returns the txttask.
	 */
	public TxtTaskEx getTxttask() {
		return txttask;
	}
	/**
	 * @param txttask The txttask to set.
	 */
	public void setTxttask(TxtTaskEx txttask) {
		this.txttask = txttask;
	}
	public TxtTaskEx getSubDBTaskEx() {
		return subDBTaskEx;
	}
	public void setSubDBTaskEx(TxtTaskEx subDBTaskEx) {
		this.subDBTaskEx = subDBTaskEx;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public DBDataExecuSql getExecuSql() {
		return execuSql;
	}
	public void setExecuSql(DBDataExecuSql execuSql) {
		this.execuSql = execuSql;
	}
}
