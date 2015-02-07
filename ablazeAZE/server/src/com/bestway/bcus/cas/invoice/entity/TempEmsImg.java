package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.constant.ProjectType;

public class TempEmsImg implements Serializable {

	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 69066885080585223L;

	private Complex complex = null;

	private String cusName = null;

	private String cusSpec = null;

	private Unit unit = null;
	/**
	 * 	BCUS = 0;
	 *	BCS = 1;
	 *	DZSC = 3;
	 */
	private Integer projectType = null;
	private Integer seqNum = null;
	private String emsNo = "";

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusSpec() {
		return cusSpec;
	}

	public void setCusSpec(String cusSpec) {
		this.cusSpec = cusSpec;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getProjectName() {
		Integer project = this.getProjectType();
		if (project == null) {
			return "";
		}
		if (project == ProjectType.BCS) {
			return "电子化手册";
		} else if (project == ProjectType.BCUS) {
			return "电子帐册";
		} else if (project == ProjectType.DZSC) {
			return "电子手册";
		} else {
			return "";
		}

	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
}
