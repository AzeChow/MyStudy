/*
 * Created on 2004-8-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.entity;

import java.util.Date;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 回执处理
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReceiptResult extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 经营单位代码
	 */
	String tradeCode;
	/**
	 * 企业内部编号
	 */
	String copEmsNo; 
	/**
	 * 数据中心统一编号
	 */
	String seqNo;    
	/**
	 * 海关账册分册编号
	 */
	String emsNo;    
	/**
	 * 数据类型
	 */
	String applType; 
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
	 * 处理结果
	 */
	String chkMark;  
	/**
	 * 通知日期
	 */
	Date noticeDate; 
	/**
	 * 通知时间
	 */
	Date noticeTime; 
	/**
	 * 备注
	 */
	String note;
	/**
	 * 处理者
	 */
	String userName; 
	/**
	 * 文件名称
	 */
	String fileName; 
	
	/**
	 * @return Returns the applType.
	 */
	public String getApplType() {
		return applType;
	}
	/**
	 * @param applType The applType to set.
	 */
	public void setApplType(String applType) {
		this.applType = applType;
	}
	/**
	 * @return Returns the chkMark.
	 */
	public String getChkMark() {
		return chkMark;
	}
	/**
	 * @param chkMark The chkMark to set.
	 */
	public void setChkMark(String chkMark) {
		this.chkMark = chkMark;
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
	 * @return Returns the noticeDate.
	 */
	public Date getNoticeDate() {
		return noticeDate;
	}
	/**
	 * @param noticeDate The noticeDate to set.
	 */
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	/**
	 * @return Returns the noticeTime.
	 */
	public Date getNoticeTime() {
		return noticeTime;
	}
	/**
	 * @param noticeTime The noticeTime to set.
	 */
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}
	/**
	 * @return Returns the seqNo.
	 */
	public String getSeqNo() {
		return seqNo;
	}
	/**
	 * @param seqNo The seqNo to set.
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
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
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
