/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 数据视图
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JDBCView extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
										.getSerialVersionUID();
	/**
	 * 数据源
	 */				
	private JDBCDataSource		jdbcDataSource		= null;						
	/**
	 * 视图名称
	 */
	private String				name				= null;						
	/**
	 * 执行语句1
	 */
	private String				sqlStr1				= null;
	/**
	 * 执行语句2
	 */
	private String				sqlStr2				= null;
	/**
	 * 执行语句3
	 */
	private String				sqlStr3				= null;
	/**
	 * 执行语句4
	 */
	private String				sqlStr4				= null;
	/**
	 * 执行语句5
	 */
	private String				sqlStr5				= null;
	/**
	 * SQL脚本
	 */
	private String				sqlScript			= "";							

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the db.
	 */
	public JDBCDataSource getJdbcDataSource() {
		return jdbcDataSource;
	}

	/**
	 * @param db
	 *            The db to set.
	 */
	public void setJdbcDataSource(JDBCDataSource db) {
		this.jdbcDataSource = db;
	}

	public String getSqlScript() {
		if (sqlStr1 == null) {
			sqlStr1 = "";
		}
		if (sqlStr2 == null) {
			sqlStr2 = "";
		}
		if (sqlStr3 == null) {
			sqlStr3 = "";
		}
		if (sqlStr4 == null) {
			sqlStr4 = "";
		}
		if (sqlStr5 == null) {
			sqlStr5 = "";
		}
		return this.sqlStr1 + this.sqlStr2 + this.sqlStr3 + this.sqlStr4
				+ this.sqlStr5;
	}

	public void setSqlScript(String sqlStr) {
		if (sqlStr == null || "".equals(sqlStr.trim())) {
			return;
		}
		int length = sqlStr.length();
		if (length <= 225) {
			this.setSqlStr1(sqlStr.substring(0, length));
		} else if (length <= 450) {
			this.setSqlStr1(sqlStr.substring(0, 225));
			this.setSqlStr2(sqlStr.substring(225, length));
			this.setSqlStr3("");
			this.setSqlStr4("");
			this.setSqlStr5("");
		} else if (length <= 675) {
			this.setSqlStr1(sqlStr.substring(0, 225));
			this.setSqlStr2(sqlStr.substring(225, 450));
			this.setSqlStr3(sqlStr.substring(450, length));
			this.setSqlStr4("");
			this.setSqlStr5("");
		} else if (length <= 900) {
			this.setSqlStr1(sqlStr.substring(0, 225));
			this.setSqlStr2(sqlStr.substring(225, 450));
			this.setSqlStr3(sqlStr.substring(450, 675));
			this.setSqlStr4(sqlStr.substring(675, length));
			this.setSqlStr5("");
		} else if (length <= 1125) {
			this.setSqlStr1(sqlStr.substring(0, 225));
			this.setSqlStr2(sqlStr.substring(225, 450));
			this.setSqlStr3(sqlStr.substring(450, 675));
			this.setSqlStr4(sqlStr.substring(675, 900));
			this.setSqlStr5(sqlStr.substring(900, length));
		}
	}

	public String getSqlStr1() {
		return sqlStr1;
	}

	public void setSqlStr1(String sqlStr1) {
		this.sqlStr1 = sqlStr1;
	}

	public String getSqlStr2() {
		return sqlStr2;
	}

	public void setSqlStr2(String sqlStr2) {
		this.sqlStr2 = sqlStr2;
	}

	public String getSqlStr3() {
		return sqlStr3;
	}

	public void setSqlStr3(String sqlStr3) {
		this.sqlStr3 = sqlStr3;
	}

	public String getSqlStr4() {
		return sqlStr4;
	}

	public void setSqlStr4(String sqlStr4) {
		this.sqlStr4 = sqlStr4;
	}

	public String getSqlStr5() {
		return sqlStr5;
	}

	public void setSqlStr5(String sqlStr5) {
		this.sqlStr5 = sqlStr5;
	}
}