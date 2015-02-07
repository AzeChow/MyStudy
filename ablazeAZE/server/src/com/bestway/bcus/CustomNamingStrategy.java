/*
 * Created on 2005-4-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus;

import org.apache.poi.util.SystemOutLogger;
import org.hibernate.cfg.DefaultNamingStrategy;

import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class CustomNamingStrategy extends DefaultNamingStrategy {
	private String tableOwner = "";

	private String dialect = ""; // 数据库的方言

	/**
	 * @return Returns the tableOwner.
	 */
	public String getTableOwner() {
		return tableOwner;
	}

	/**
	 * @param tableOwner
	 *            The tableOwner to set.
	 */
	public void setTableOwner(String tableOwner) {
		this.tableOwner = tableOwner;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	@Override
	public String tableName(String tableName) {
		tableName=super.tableName(tableName);
		//
		// 海关帐年度表名
		//
		tableName = CommonUtils.tableNamingStrategyByCasYear(tableName);

//		if (dialect.indexOf("Informix") >= 0) {
//			if (tableName.length() > 18) {
//				tableName = tableName.substring(0, 10)
//						+ tableName.substring(tableName.length() - 8);
//			}
//		}
		System.out.println("--------------tableName:"+tableName);
//		if (tableOwner != null && !"".equals(tableOwner.trim())) {
//			if (tableName.indexOf(tableOwner + ".") >= 0) {
//				return tableName;
//			} else {
//				return tableOwner + "." + tableName;
//			}
//		} else {
//			return super.tableName(tableName);
//		}
		return tableName;
		// return super.tableName(tableName);
	}

	@Override
	public String classToTableName(String tableName) {
		tableName = super.classToTableName(tableName);
		if (dialect.indexOf("Informix") >= 0) {
			if (tableName.length() > 18) {
				tableName = tableName.substring(0, 10)
						+ tableName.substring(tableName.length() - 8);
			}
		}
		System.out.println("--------------classToTableName:"+tableName);
		return tableName;
	}

	@Override
	public String columnName(String colName) {
		colName=super.columnName(colName);
		if (dialect.indexOf("Informix") >= 0) {
			if (colName.length() > 18) {
				colName = colName.substring(0, 10)
						+ colName.substring(colName.length() - 8);
			}
		}
		return super.columnName(colName);
	}

	@Override
	public String propertyToColumnName(String colName) {
		if (dialect.indexOf("Informix") >= 0) {
			if (colName.length() > 18) {
				colName = colName.substring(0, 10)
						+ colName.substring(colName.length() - 8);
			}
		}
		return super.propertyToColumnName(colName);
	}

}
