/*
 * Created on 2004-10-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.fpt.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.CommonUtils;


/**
 * 申请表余量分析报表参数 // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TempFptAppParam implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	private Integer projectType = null;
	/** 转入转出标记 0：转出，1：转入 */
	private String inOutFlag = null;
	/** 客户供应商海关代码 */
	private String tradeName = null;
	/** 商品编码 */
	private String complex = null;
	/** 商品名称 */
	private String name = null;
	/** 申请表申报状态 非空 0：原始状态，1：等待审批，2：正在执行，3：变更，5：退单 6,全部 */
	private String declareState = null;
	/** 开始时间 */
	private Date beginDate = null;
	/** 结束时间 */
	private Date endDate = null;
	// /** 规格型号 */
	// private String spec = null;
	// /** 计量单位 */
	// private Unit unit = null;
	/** 申请表编号 */
	private String appNo = null;
	
	/** 手册编号/帐册编号 */
	private String emsNo = null;
	
	/** 手册序号 */
	private Integer trGno = null;
	
	/** 是否结案 */
	private Boolean isCollated = null;
	
	/** 手册申报状态   */
	private String emsDeclareState = null;
	
	
	public Boolean getIsCollated() {
		return isCollated;
	}
	public void setIsCollated(Boolean isCollated) {
		this.isCollated = isCollated;
	}
	/**
	 * @return the inOutFlag
	 */
	public String getInOutFlag() {
		return inOutFlag;
	}
	/**
	 * @param inOutFlag the inOutFlag to set
	 */
	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}
	/**
	 * @return the tradeName
	 */
	public String getTradeName() {
		return tradeName;
	}
	/**
	 * @param tradeName the tradeName to set
	 */
	public void setTradeName(String tradeCode) {
		this.tradeName = tradeCode;
	}
	/**
	 * @return the complex
	 */
	public String getComplex() {
		return complex;
	}
	/**
	 * @param complex the complex to set
	 */
	public void setComplex(String complex) {
		this.complex = complex;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the declareState
	 */
	public String getDeclareState() {
		return declareState;
	}
	/**
	 * @param i the declareState to set
	 */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}
	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the appNo
	 */
	public String getAppNo() {
		return appNo;
	}
	/**
	 * @param appNo the appNo to set
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * @param emsNo the emsNo to set
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	/**
	 * @return the projectType
	 */
	public Integer getProjectType() {
		return projectType;
	}
	/**
	 * @param projectType the projectType to set
	 */
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public Integer getTrGno() {
		return trGno;
	}
	public void setTrGno(Integer trGno) {
		this.trGno = trGno;
	}
	/**
	 * @return the emsDeclareState
	 */
	public String getEmsDeclareState() {
		return emsDeclareState;
	}
	/**
	 * @param emsDeclareState the emsDeclareState to set
	 */
	public void setEmsDeclareState(String emsDeclareState) {
		this.emsDeclareState = emsDeclareState;
	}

	
	
	

}