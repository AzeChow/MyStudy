/*
 * Created on 2004-8-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.message.entity;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 报文信息接收
 * 
 * @author yp // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CspReceiptResult extends BaseScmEntity implements Comparable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 经营单位代码
	 */
	private String tradeCode;

	/**
	 * 企业内部编号
	 */
	private String copEmsNo;

	/**
	 * 核查/报核次数
	 */
	private String colDcrTime;

	/**
	 * 数据中心统一编号
	 */
	private String seqNo;

	/**
	 * 海关账册分册编号(单据海关编号)
	 */
	private String emsNo;

	/**
	 * 数据类型
	 */
	private String applType;

	/**
	 * 申报类型
	 */
	private String declareType;

	/**
	 * 处理结果
	 */
	private String chkMark;

	/**
	 * 通知日期
	 */
	private Date noticeDate;

	/**
	 * 通知时间
	 */
	private Date noticeTime;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 处理者
	 */
	private String userName;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 帐册编号
	 */
	private String corrEmsNo;

	/**
	 * 发送的报文名称
	 */
	private String sendFileName;

	/**
	 * 回执明细信息
	 */
	private List receiptResultDetailList;

	/**
	 * 接收到的回执报文
	 */
	private File file;

	/**
	 * @return Returns the applType.
	 */
	public String getApplType() {
		return applType;
	}

	/**
	 * @param applType
	 *            The applType to set.
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
	 * @param chkMark
	 *            The chkMark to set.
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
	 * @param copEmsNo
	 *            The copEmsNo to set.
	 */
	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	public String getColDcrTime() {
		return colDcrTime;
	}

	public void setColDcrTime(String colDcrTime) {
		this.colDcrTime = colDcrTime;
	}

	/**
	 * @return Returns the declareType.
	 */
	public String getDeclareType() {
		return declareType;
	}

	/**
	 * @param declareType
	 *            The declareType to set.
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
	 * @param emsNo
	 *            The emsNo to set.
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
	 * @param note
	 *            The note to set.
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
	 * @param noticeDate
	 *            The noticeDate to set.
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
	 * @param noticeTime
	 *            The noticeTime to set.
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
	 * @param seqNo
	 *            The seqNo to set.
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
	 * @param tradeCode
	 *            The tradeCode to set.
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
	 * @param userName
	 *            The userName to set.
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
	 * @param fileName
	 *            The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCorrEmsNo() {
		return corrEmsNo;
	}

	public void setCorrEmsNo(String corrEmsNo) {
		this.corrEmsNo = corrEmsNo;
	}

	public String getSendFileName() {
		return sendFileName;
	}

	public void setSendFileName(String sendFileName) {
		this.sendFileName = sendFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 重compareTo方法，用noticeDate（通知日期）进行比较
	 * 
	 * @param o
	 *            要比较的
	 * @return int 比o小时返回-1，相等时返回0，大于时返回1
	 */
	public int compareTo(Object o) {
		if (o == null) {
			return 1;
		}
		CspReceiptResult result = (CspReceiptResult) o;
		if (this.getNoticeDate() == null && result.getNoticeDate() != null) {
			return -1;
		}
		if (this.getNoticeDate() != null && result.getNoticeDate() == null) {
			return 1;
		}
		long sub = this.getNoticeDate().getTime()
				- result.getNoticeDate().getTime();
		if ((sub) > 0) {
			return 1;
		} else if ((sub) == 0) {
			return 0;
		} else if ((sub) < 0) {
			return -1;
		}
		return 0;
	}

	public String getFormatedNoticeDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS");
		if (this.noticeDate != null) {
			return dateFormat.format(noticeDate);
		} else {
			return "";
		}
	}

	public List getReceiptResultDetailList() {
		return receiptResultDetailList;
	}

	public void setReceiptResultDetailList(List listReceiptResultDetail) {
		this.receiptResultDetailList = listReceiptResultDetail;
	}
}
