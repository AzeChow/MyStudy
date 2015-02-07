package com.bestway.common.message.entity;

import java.io.Serializable;

public class TempCspMessageTreeInfo implements Serializable {
	private String parentDesc;

	private String childDesc;
	
	private String sectionFlag;

	public String getChildDesc() {
		return childDesc;
	}

	public void setChildDesc(String childDesc) {
		this.childDesc = childDesc;
	}

	public String getParentDesc() {
		return parentDesc;
	}

	public void setParentDesc(String parentDesc) {
		this.parentDesc = parentDesc;
	}

	public String getSectionFlag() {
		return sectionFlag;
	}

	public void setSectionFlag(String sectionFlag) {
		this.sectionFlag = sectionFlag;
	}
}
