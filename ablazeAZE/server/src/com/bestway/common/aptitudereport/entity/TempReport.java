package com.bestway.common.aptitudereport.entity;

import java.io.Serializable;
import java.util.List;

public class TempReport implements Serializable{
	String groupingColumn = "";
	Double statColumn = 0.0;
//	String reportName = null;
	
	public String getGroupingColumn() {
		return groupingColumn;
	}
	public void setGroupingColumn(String groupingColumn) {
		this.groupingColumn = groupingColumn;
	}
	public Double getStatColumn() {
		return statColumn;
	}
	public void setStatColumn(Double statColumn) {
		this.statColumn = statColumn;
	}
//	public String getReportName() {
//		return reportName;
//	}
//	public void setReportName(String reportName) {
//		this.reportName = reportName;
//	}


}
