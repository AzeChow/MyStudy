package com.bestway.common.aptitudereport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 分组字段信息
 * @author Administrator
 *
 */
public class GroupValue extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
	/**
	 * 表头
	 */
	private SelectCondition selectCondition = null;
	
	/**
	 * 表字段
	 */
	private String code = null;
	
	/**
	 * 表字段中文名
	 */
	private String name = null;
	
	/**
	 * 字段类型
	 * 0: 分组字段;
	 * 1：统计方式
	 */
	private Integer groupType = 0;// 0: 分组字段；　1：统计方式
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SelectCondition getSelectCondition() {
		return selectCondition;
	}

	public void setSelectCondition(SelectCondition selectCondition) {
		this.selectCondition = selectCondition;
	}



	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}



}
