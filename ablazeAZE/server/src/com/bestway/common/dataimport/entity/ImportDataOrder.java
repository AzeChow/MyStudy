package com.bestway.common.dataimport.entity;

import com.bestway.common.BaseEntity;
/**
 * 倒入数据顺序
 * @author ower
 *
 */
public class ImportDataOrder extends BaseEntity {
	/**
	 * 表名,统一用类名
	 */
	String tableName;
	/**
	 * 字段名，统一用属性名
	 */
	String columnName;
	/**
	 * 存放位置
	 */
	Integer position;
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

}
