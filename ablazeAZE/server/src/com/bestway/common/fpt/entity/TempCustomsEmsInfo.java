package com.bestway.common.fpt.entity;

import java.io.Serializable;

public class TempCustomsEmsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer projectType = null;

	private String emsNo = null;

	private Boolean isSelect = false;

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	@Override
	public String toString() {
		// 
		return emsNo == null ? "" : emsNo;
	}

	public Boolean getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Boolean isSelect) {
		this.isSelect = isSelect;
	}
}
