package com.bestway.common.aptitudereport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 报表显示字段信息
 * @author Administrator
 *
 */
public class ReportField extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 报表信息
	 */
	private SelectCondition selectCondition;

	/**
	 * 英文字段名
	 */
	private String enName;

	/**
	 * 中文字段名
	 */
	private String cnName;
	
	/**
	 * 字段所属的类名
	 */
	private String classname;
	
	/**
	 * 字段存储分类
	 * 0:待选择的字段;　1:显示的字段;　2:分组的字段;　3:统计的字段;
	 */
	private Integer fieldType = 0;  

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public SelectCondition getSelectCondition() {
		return selectCondition;
	}

	public void setSelectCondition(SelectCondition selectCondition) {
		this.selectCondition = selectCondition;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
}
