package com.bestway.common.tools.entity;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 索引信息
 * 
 * @author yp
 * 
 */
public class DBIndex implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 索引名字
	 */
	private String indexName;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 字段名
	 */
	private String fieldNames;
	/**
	 * 创建索引语句
	 */
	private String indexSql;
	/**
	 * 生成索引日志信息
	 */
	private String logInfo;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
	}

	public String getIndexSql() {
		return indexSql;
	}

	public void setIndexSql(String indexSql) {
		this.indexSql = indexSql;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj.getClass().equals(this.getClass())))
			return false;
		DBIndex castOther = (DBIndex) obj;
		if (this.getIndexName() == null && castOther.getIndexName() == null) {
			return super.equals(obj);
		} else {
			return new EqualsBuilder().append(this.getIndexName(),
					castOther.getIndexName()).isEquals();
		}
	}
}
