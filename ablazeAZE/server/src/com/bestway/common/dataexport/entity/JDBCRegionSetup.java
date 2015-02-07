/*
 * Created on 2004-9-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * JDBC 导入导出域设置
 * @author
 */
public class JDBCRegionSetup extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 * 导入导出域
	 */
	private JDBCRegion			jdbcRegion			= null;
	/**
	 * 目的表字段名称
	 */
	private String				destFieldName		= null;						// 目的表字段名称
	/**
	 * 目的表字段类型
	 */
	private Integer				destFieldType		= null;						// 目的表字段类型
	/**
	 * 目的表字段类型
	 */
	private String				destFieldTypeDesc	= null;						// 目的表字段类型
	/**
	 * 源表字段名称
	 */
	private String				srcFieldName		= null;						// 源表字段名称
	/**
	 * 源表字段类型
	 */
	private Integer				srcFieldType		= null;						// 源表字段类型
	/**
	 * 源表字段类型
	 */
	private String				srcFieldTypeDesc	= null;						// 源表字段类型
	/**
	 * 执行的语句1
	 */
	private String				sqlStr1				= null;
	/**
	 * 执行的语句2
	 */
	private String				sqlStr2				= null;
	/**
	 * 执行的语句3
	 */
	private String				sqlStr3				= null;
	/**
	 * 执行的语句4
	 */
	private String				sqlStr4				= null;
	
	/**
	 * 执行的语句5
	 */
	private String				sqlStr5				= null;
	/**
	 * 执行的语句
	 */
	private String				sqlStr				= null;						// 执行的语句
	/**
	 * 参数数据源
	 */
	private JDBCDataSource		paraJDBCDataSource	= null;						// 参数数据源
	/**
	 * 数值转换是否使用Cache
	 */
	private Boolean				isCache				= false;						// 数值转换是否使用Cache
	/**
	 * 是否是繁体数据库
	 */
	private Boolean				isBig5DataBase		= false;						// 是否是繁体数据库
	/**
	 * 是否是判定唯一性的关键列
	 */
	private Boolean				isKey				= false;						// 是否是判定唯一性的关键列
	/**
	 * 是数据库主键用于数据库的更新,如果是主键,在更新时不会修改主键
	 */
	private Boolean				isPrimaryKey		= false;	 
	/**
	 * 转换标识
	 */
	private String				jdbcFlag			= null;						// 转换标识

	// JDBCFlag
	// 类

	public String getDestFieldTypeDesc() {
		return destFieldTypeDesc;
	}

	public void setDestFieldTypeDesc(String destFieldTypeDesc) {
		this.destFieldTypeDesc = destFieldTypeDesc;
	}

	public String getSrcFieldTypeDesc() {
		return srcFieldTypeDesc;
	}

	public void setSrcFieldTypeDesc(String srcFieldTypeDesc) {
		this.srcFieldTypeDesc = srcFieldTypeDesc;
	}

	public String getDestFieldName() {
		return destFieldName;
	}

	public void setDestFieldName(String destFieldName) {
		this.destFieldName = destFieldName;
	}

	public Integer getDestFieldType() {
		return destFieldType;
	}

	public void setDestFieldType(Integer destFieldType) {
		this.destFieldType = destFieldType;
	}

	public JDBCRegion getJdbcRegion() {
		return jdbcRegion;
	}

	public void setJdbcRegion(JDBCRegion jdbcRegion) {
		this.jdbcRegion = jdbcRegion;
	}

	public String getSqlStr() {
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

	public void setSqlStr(String sqlStr) {
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

	public String getSrcFieldName() {
		return srcFieldName;
	}

	public void setSrcFieldName(String srcFieldName) {
		this.srcFieldName = srcFieldName;
	}

	public Integer getSrcFieldType() {
		return srcFieldType;
	}

	public void setSrcFieldType(Integer srcFieldType) {
		this.srcFieldType = srcFieldType;
	}

	public Boolean getIsCache() {
		return isCache;
	}

	public void setIsCache(Boolean isCache) {
		this.isCache = isCache;
	}

	public Boolean getIsKey() {
		return isKey;
	}

	public void setIsKey(Boolean isKey) {
		this.isKey = isKey;
	}

	public String getJdbcFlag() {
		return jdbcFlag;
	}

	public void setJdbcFlag(String jdbcFlag) {
		this.jdbcFlag = jdbcFlag;
	}

	public JDBCDataSource getParaJDBCDataSource() {
		return paraJDBCDataSource;
	}

	public void setParaJDBCDataSource(JDBCDataSource paraJDBCDataSource) {
		this.paraJDBCDataSource = paraJDBCDataSource;
	}

	public Boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public Boolean getIsBig5DataBase() {
		return isBig5DataBase;
	}

	public void setIsBig5DataBase(Boolean isBig5DataBase) {
		this.isBig5DataBase = isBig5DataBase;
	}

}
