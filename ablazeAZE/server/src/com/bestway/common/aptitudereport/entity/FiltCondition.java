package com.bestway.common.aptitudereport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 自定义报表查询过滤条件
 * @author Administrator
 *
 */
public class FiltCondition extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 表头
	 */
	private SelectCondition selectCondition = null;

	/**
	 * 逻辑符号
	 */
	private String logic = null;

	/**
	 * 前括符
	 */
	private String beginBracket = null;

	/**
	 * 中文字段名称
	 */
	private String fieldName = null;

	/**
	 * 判断符号
	 */
	private String operator = null;

	/**
	 * 判断值
	 */
	private String value = null;

	/**
	 * 回括号
	 */
	private String endBracket = null;

	/**
	 * 字段名类型
	 */
	private String dataType = null;
	
	/**
	 * 条件类型
	 */
	private String conditionType = null;//１－过滤条件；　２－统计条件
	
	/**
	 * ＳＱＬ语句
	 */
	private String sqlSentence = null;
	
	/**
	 * 英文字段名（表字段）
	 */
	private String enFieldName = null;
	
	/**
	 * 英文ＳＱＬ语句
	 */
	private String enSqlSentence = null;

	public String getBeginBracket() {
		return beginBracket;
	}

	public void setBeginBracket(String beginBracket) {
		this.beginBracket = beginBracket;
	}

	public String getEndBracket() {
		return endBracket;
	}

	public void setEndBracket(String endBracket) {
		this.endBracket = endBracket;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public SelectCondition getSelectCondition() {
		return selectCondition;
	}

	public void setSelectCondition(SelectCondition selectCondition) {
		this.selectCondition = selectCondition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSqlSentence() {
		return sqlSentence;
	}

	public void setSqlSentence(String sqlSentence) {
		this.sqlSentence = sqlSentence;
	}

	public String getEnFieldName() {
		return enFieldName;
	}

	public void setEnFieldName(String enFieldName) {
		this.enFieldName = enFieldName;
	}

	public String getEnSqlSentence() {
		return enSqlSentence;
	}

	public void setEnSqlSentence(String enSqlSentence) {
		this.enSqlSentence = enSqlSentence;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

}
