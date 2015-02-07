package com.bestway.common.aptitudereport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 统计条件信息
 * @author Administrator
 *
 */
public class StatTypeValue extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 表头
	 */
	private SelectCondition selectCondition = null;

	/**
	 * 统计方式英文名
	 */
	private String code = null;

	/**
	 * 统计方式中文名
	 */
	private String name = null;

	/**
	 * 是否选择
	 */
	private Integer isSelect = 0;

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

	public Integer getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Integer isSelect) {
		this.isSelect = isSelect;
	}

}
