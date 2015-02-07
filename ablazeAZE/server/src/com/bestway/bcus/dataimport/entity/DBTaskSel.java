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
 * 任务明细
 * @author lin
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DBTaskSel extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * DB任务
     */
    private DBTaskEx dbtaskEx;   
    /**
     * 执行顺序
     */
    private Integer seqNum;     
	/**
	 * 域
	 */
    private DBFormat dbFormat;  
	/**
	 * 事件执行
	 */
    private DBDataExecuSql execuSql; 
	/**
	 * 子任务
	 */
    private DBTaskEx subDBTaskEx; 
	
	/**
	 * @return Returns the dbFormat.
	 */
	public DBFormat getDbFormat() {
		return dbFormat;
	}
	/**
	 * @param dbFormat The dbFormat to set.
	 */
	public void setDbFormat(DBFormat dbFormat) {
		this.dbFormat = dbFormat;
	}
	/**
	 * @return Returns the dbtaskEx.
	 */
	public DBTaskEx getDbtaskEx() {
		return dbtaskEx;
	}
	/**
	 * @param dbtaskEx The dbtaskEx to set.
	 */
	public void setDbtaskEx(DBTaskEx dbtaskEx) {
		this.dbtaskEx = dbtaskEx;
	}
	/**
	 * @return Returns the execuSql.
	 */
	public DBDataExecuSql getExecuSql() {
		return execuSql;
	}
	/**
	 * @param execuSql The execuSql to set.
	 */
	public void setExecuSql(DBDataExecuSql execuSql) {
		this.execuSql = execuSql;
	}
	/**
	 * @return Returns the seqNum.
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	/**
	 * @param seqNum The seqNum to set.
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public DBTaskEx getSubDBTaskEx() {
		return subDBTaskEx;
	}
	public void setSubDBTaskEx(DBTaskEx subDBTaskEx) {
		this.subDBTaskEx = subDBTaskEx;
	}
}
