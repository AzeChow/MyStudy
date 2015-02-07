package com.bestway.common.aptitudereport.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 报表信息
 * @author Administrator
 *
 */
public class SelectCondition extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 报表名称
	 */
	private String reportName=null;

	/**
	 * 数据来源
	 */
	private String aimObject=null;
	/**
	 * 数据来源中文名
	 */
	private String aimObjectValue=null;

	/**
	 * 统计栏位
	 */
	private String statColumn=null;
	/**
	 * 统计栏位中文名
	 */
	private String statColumnValue=null;

	/**
	 * 分组栏位
	 */
	private String groupingColumnValue=null;
	/**
	 * 报表类型
	 */
	private Integer reportType = 0;


	public String getStatColumn() {
		return statColumn;
	}

	public void setStatColumn(String statColumn) {
		this.statColumn = statColumn;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getAimObject() {
		return aimObject;
	}

	public void setAimObject(String aimObject) {
		this.aimObject = aimObject;
	}


	public String getStatColumnValue() {
		return statColumnValue;
	}

	public void setStatColumnValue(String statColumnValue) {
		this.statColumnValue = statColumnValue;
	}

	public String getAimObjectValue() {
		return aimObjectValue;
	}

	public void setAimObjectValue(String aimObjectValue) {
		this.aimObjectValue = aimObjectValue;
	}


	public String getGroupingColumnValue() {
		return groupingColumnValue;
	}

	public void setGroupingColumnValue(String groupingColumnValue) {
		this.groupingColumnValue = groupingColumnValue;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}








}