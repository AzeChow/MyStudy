/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.checkcancel.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放电子手册中期核查表头
 * 
 * @author Administrator
 */
public class DzscCheckHead extends BaseScmEntity implements Cloneable {
	public static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 帐册编号
	 */
	private String emsNo;

	/**
	 * 经营单位代码
	 */
	private String tradeCode;

	/**
	 * 经营单位名称
	 */
	private String tradeName;

	/**
	 * 加工单位代码
	 */
	private String machCode;

	/**
	 * 加工单位名称
	 */
	private String machName;

	/**
	 * 本期起始日期
	 */
	private Date beginDate;

	/**
	 * 主管海关
	 */
	private Customs masterCustoms;

	/**
	 * 申报标志
	 */
	private Boolean isDeclare = false;

	// 以下字段都是为接口而新加的

	/**
	 * 对应归并关系企业内部编号
	 */
	private String copEmsNo;

	/**
	 * 核查范围
	 */
	private String colRange;

	/**
	 * 核查期数
	 */
	private Integer colShdulTime;

	/**
	 * 库存截止日期
	 */
	private Date ctockData;

	/**
	 * 申报日期
	 */
	private Date declareDate;

	/**
	 * 申报类型
	 */
	private String flagHG;
	/**
	 * 申报状态
	 * APPLY_POR = "1";	申请备案
	 * WAIT_EAA = "2";	等待审批
	 * PROCESS_EXE = "3";	正在执行
	 * CHANGING_EXE = "4";	正在变更
	 * CHANGING_CANCEL = "5";	已经核销
	 */
	private String declareState;  

//	/**
//	 * 预录入号,对应列名preRecordNo
//	 */
//	private String copTrNo = null;
	
	/**
	 * 管理对象
	 */
	private String manageObject;

	public String getColRange() {
		return colRange;
	}

	public void setColRange(String colRange) {
		this.colRange = colRange;
	}

	/**
	 * 获取本期起始日期
	 * 
	 * @return beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置本期起始日期
	 * 
	 * @param beginDate
	 *            本期起始日期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取帐册编号
	 * 
	 * @return emsNo 帐册编号
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置帐册编号
	 * 
	 * @param emsNo
	 *            帐册编号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获取加工单位代码
	 * 
	 * @return machCode 加工单位代码
	 */
	public String getMachCode() {
		return machCode;
	}

	/**
	 * 设置加工单位代码
	 * 
	 * @param machCode
	 *            加工单位代码
	 */
	public void setMachCode(String machCode) {
		this.machCode = machCode;
	}

	/**
	 * 获取加工单位名称
	 * 
	 * @return machName 加工单位名称
	 */
	public String getMachName() {
		return machName;
	}

	/**
	 * 设置加工单位名称
	 * 
	 * @param machName
	 *            加工单位名称
	 */
	public void setMachName(String machName) {
		this.machName = machName;
	}

	/**
	 * 获取主管海关
	 * 
	 * @return masterCustoms 主管海关
	 */
	public Customs getMasterCustoms() {
		return masterCustoms;
	}

	/**
	 * 设置主管海关
	 * 
	 * @param masterCustoms
	 *            主管海关
	 */
	public void setMasterCustoms(Customs masterCustoms) {
		this.masterCustoms = masterCustoms;
	}

	/**
	 * 获取经营单位代码
	 * 
	 * @return tradeCode 经营单位代码
	 */
	public String getTradeCode() {
		return tradeCode;
	}

	/**
	 * 设置经营单位代码
	 * 
	 * @param tradeCode
	 *            经营单位代码
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	/**
	 * 获取经营单位名称
	 * 
	 * @return tradeName 经营单位名称
	 */
	public String getTradeName() {
		return tradeName;
	}

	/**
	 * 设置经营单位名称
	 * 
	 * @param tradeName
	 *            经营单位名称
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	/**
	 * 获取申报标志
	 * 
	 * @return isDeclare 申报标志，true为已申报
	 */
	public Boolean getIsDeclare() {
		return isDeclare;
	}

	/**
	 * 设置申报标志
	 * 
	 * @param isDeclare
	 *            申报标志，true为已申报
	 */
	public void setIsDeclare(Boolean isDeclare) {
		this.isDeclare = isDeclare;
	}

	public String getCopEmsNo() {
		return copEmsNo;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	public Date getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public Integer getColShdulTime() {
		return colShdulTime;
	}

	public void setColShdulTime(Integer colShdulTime) {
		this.colShdulTime = colShdulTime;
	}

	public Date getCtockData() {
		return ctockData;
	}

	public void setCtockData(Date ctockData) {
		this.ctockData = ctockData;
	}

	public String getFlagHG() {
		return flagHG;
	}

	public void setFlagHG(String flagHG) {
		this.flagHG = flagHG;
	}

//	public String getCopTrNo() {
//		return copTrNo;
//	}
//
//	public void setCopTrNo(String preRecordNo) {
//		this.copTrNo = preRecordNo;
//	}

	public String getDeclareState() {
		return declareState;
	}

	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	public String getManageObject() {
		return manageObject;
	}

	public void setManageObject(String manageObject) {
		this.manageObject = manageObject;
	}
	
	
	public String getCustomsCopCode(){
		if("0".equals(manageObject)){
			return this.tradeCode;
		}else{
			return this.machCode;
		}
	}
	
	public String getCustomsCopName(){
		if("0".equals(manageObject)){
			return this.tradeName;
		}else{
			return this.machName;
		}
	}

}
