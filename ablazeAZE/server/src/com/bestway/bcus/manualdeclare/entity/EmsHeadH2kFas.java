/*
 * Created on 2004-7-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.ApplicationMode;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 电子帐册分册
 * @author 001
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsHeadH2kFas extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 电子帐册编号
	 */
	private String emsHeadH2kNo;  
	/**
	 * 分册类型
	 */
	private String emsType;       
	/**
	 * 帐册状态
	 */
	private String declareState; 
	/**
	 * 申报类型
	 * APPLY_POR = "1";	申请备案
	 * WAIT_EAA = "2";	等待审批
	 * PROCESS_EXE = "3";	正在执行
	 * CHANGING_EXE = "4";	正在变更
	 * CHANGING_CANCEL = "5";	已经核销
	 */
	private String declareType;   
	/**
	 * 企业内部编码
	 */
	private String copEmsNo;      
	/**
	 * 分册号
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
	 * 申报单位代码
	 */
	private String declareCode;   
	/**
	 * 申报单位名称
	 */
	private String declareName;   
	/**
	 * 分册期限
	 */
	private Date limitDate;       
	/**
	 * 进口口岸
	 */
	private Customs iePort;           
	/**
	 * 申报标志
	 */
	private ApplicationMode declareMark;  
	/**
	 * 录入员代号
	 */
	private String inputEr;      
	/**
	 * 录入日期
	 */
	private Date inputDate;     
	/**
	 * 申报日期
	 */
	private Date declareDate;   
	/**
	 * 申报时间
	 */
	private Date declareTime;     
	/**
	 * 备案批准日期
	 */
	private Date newApprDate;    
	/**
	 * 变更批准日期
	 */
	private Date changeApprDate;  
	/**
	 * 备注
	 */
	private String note;         
	/**
	 * 历史记录
	 */
	private Boolean historyState=false; 
	/**
	 * 变更次数
	 */
	private Integer modifyTimes;   
    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark; 
	/**
	 * 审批标志
	 * 1 ，申请备案 2，等待审批 3，正在执行
	 */
	private String checkMark;     
	/**
	 * 执行标志
	 */
	private String exeMark;        
	
	/**
	 * @return Returns the changeApprDate.
	 */
	public Date getChangeApprDate() {
		return changeApprDate;
	}
	/**
	 * @param changeApprDate The changeApprDate to set.
	 */
	public void setChangeApprDate(Date changeApprDate) {
		this.changeApprDate = changeApprDate;
	}
	/**
	 * @return Returns the checkMark.
	 */
	public String getCheckMark() {
		return checkMark;
	}
	/**
	 * @param checkMark The checkMark to set.
	 */
	public void setCheckMark(String checkMark) {
		this.checkMark = checkMark;
	}
	/**
	 * @return Returns the copEmsNo.
	 */
	public String getCopEmsNo() {
		return copEmsNo;
	}
	/**
	 * @param copEmsNo The copEmsNo to set.
	 */
	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}
	/**
	 * @return Returns the declareCode.
	 */
	public String getDeclareCode() {
		return declareCode;
	}
	/**
	 * @param declareCode The declareCode to set.
	 */
	public void setDeclareCode(String declareCode) {
		this.declareCode = declareCode;
	}
	/**
	 * @return Returns the declareDate.
	 */
	public Date getDeclareDate() {
		return declareDate;
	}
	/**
	 * @param declareDate The declareDate to set.
	 */
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}
	/**
	 * @return Returns the declareName.
	 */
	public String getDeclareName() {
		return declareName;
	}
	/**
	 * @param declareName The declareName to set.
	 */
	public void setDeclareName(String declareName) {
		this.declareName = declareName;
	}
	/**
	 * @return Returns the declareState.
	 */
	public String getDeclareState() {
		return declareState;
	}
	/**
	 * @param declareState The declareState to set.
	 */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}
	/**
	 * @return Returns the declareTime.
	 */
	public Date getDeclareTime() {
		return declareTime;
	}
	/**
	 * @param declareTime The declareTime to set.
	 */
	public void setDeclareTime(Date declareTime) {
		this.declareTime = declareTime;
	}
	/**
	 * @return Returns the declareType.
	 */
	public String getDeclareType() {
		return declareType;
	}
	/**
	 * @param declareType The declareType to set.
	 */
	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}
	/**
	 * @return Returns the emsNo.
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * @param emsNo The emsNo to set.
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	/**
	 * @return Returns the emsType.
	 */
	public String getEmsType() {
		return emsType;
	}
	/**
	 * @param emsType The emsType to set.
	 */
	public void setEmsType(String emsType) {
		this.emsType = emsType;
	}
	/**
	 * @return Returns the exeMark.
	 */
	public String getExeMark() {
		return exeMark;
	}
	/**
	 * @param exeMark The exeMark to set.
	 */
	public void setExeMark(String exeMark) {
		this.exeMark = exeMark;
	}
	/**
	 * @return Returns the historyState.
	 */
	public Boolean getHistoryState() {
		return historyState;
	}
	/**
	 * @param historyState The historyState to set.
	 */
	public void setHistoryState(Boolean historyState) {
		this.historyState = historyState;
	}

	/**
	 * @return Returns the inputDate.
	 */
	public Date getInputDate() {
		return inputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	/**
	 * @return Returns the inputEr.
	 */
	public String getInputEr() {
		return inputEr;
	}
	/**
	 * @param inputEr The inputEr to set.
	 */
	public void setInputEr(String inputEr) {
		this.inputEr = inputEr;
	}
	/**
	 * @return Returns the limitDate.
	 */
	public Date getLimitDate() {
		return limitDate;
	}
	/**
	 * @param limitDate The limitDate to set.
	 */
	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}
	/**
	 * @return Returns the modifyMark.
	 */
	public String getModifyMark() {
		return modifyMark;
	}
	/**
	 * @param modifyMark The modifyMark to set.
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}
	/**
	 * @return Returns the modifyTimes.
	 */
	public Integer getModifyTimes() {
		return modifyTimes;
	}
	/**
	 * @param modifyTimes The modifyTimes to set.
	 */
	public void setModifyTimes(Integer modifyTimes) {
		this.modifyTimes = modifyTimes;
	}
	/**
	 * 返回更改次数（海关申报用，固定为0）
	 */
	public Integer getModifyTimesMark() {
		return 0;
	}
	/**
	 * @return Returns the newApprDate.
	 */
	public Date getNewApprDate() {
		return newApprDate;
	}
	/**
	 * @param newApprDate The newApprDate to set.
	 */
	public void setNewApprDate(Date newApprDate) {
		this.newApprDate = newApprDate;
	}
	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note The note to set.
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return Returns the tradeCode.
	 */
	public String getTradeCode() {
		return tradeCode;
	}
	/**
	 * @param tradeCode The tradeCode to set.
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	/**
	 * @return Returns the tradeName.
	 */
	public String getTradeName() {
		return tradeName;
	}
	/**
	 * @param tradeName The tradeName to set.
	 */
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	/**
	 * @return Returns the emsHeadH2kNo.
	 */
	public String getEmsHeadH2kNo() {
		return emsHeadH2kNo;
	}
	/**
	 * @param emsHeadH2kNo The emsHeadH2kNo to set.
	 */
	public void setEmsHeadH2kNo(String emsHeadH2kNo) {
		this.emsHeadH2kNo = emsHeadH2kNo;
	}
	/**
	 * @return Returns the declareMark.
	 */
	public ApplicationMode getDeclareMark() {
		return declareMark;
	}
	/**
	 * @param declareMark The declareMark to set.
	 */
	public void setDeclareMark(ApplicationMode declareMark) {
		this.declareMark = declareMark;
	}
	/**
	 * @return Returns the iePort.
	 */
	public Customs getIePort() {
		return iePort;
	}
	/**
	 * @param iePort The iePort to set.
	 */
	public void setIePort(Customs iePort) {
		this.iePort = iePort;
	}
}
