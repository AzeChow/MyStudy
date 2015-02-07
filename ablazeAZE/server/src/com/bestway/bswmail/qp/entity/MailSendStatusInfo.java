package com.bestway.bswmail.qp.entity;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class MailSendStatusInfo implements java.io.Serializable {

	private int fileTotalNum = 0;

	private int fileSendNum = 0;

	private int fileLeftNum = 0;

	private int fileSuccessNum = 0;

	private int fileFailNum = 0;

	private String statusInfo = "";

	private List sendList;

	private List successList;

	private List failList;

//	private Hashtable hashtable;

//	private Date currentDate = null;

	public int getFileTotalNum() {
		return fileTotalNum;
	}

	public void setFileTotalNum(int fileTotalNum) {
		this.fileTotalNum = fileTotalNum;
	}

	public int getFileFailNum() {
		return fileFailNum;
	}

	public void setFileFailNum(int fileFailNum) {
		this.fileFailNum = fileFailNum;
	}

	public int getFileLeftNum() {
		return fileLeftNum;
	}

	public void setFileLeftNum(int fileLeftNum) {
		this.fileLeftNum = fileLeftNum;
	}

	public int getFileSendNum() {
		return fileSendNum;
	}

	public void setFileSendNum(int fileSendNum) {
		this.fileSendNum = fileSendNum;
	}

	public int getFileSuccessNum() {
		return fileSuccessNum;
	}

	public void setFileSuccessNum(int fileSuccessNum) {
		this.fileSuccessNum = fileSuccessNum;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public List getSendList() {
		return sendList;
	}

	public void setSendList(List arrayList) {
		this.sendList = arrayList;
	}

	public List getFailList() {
		return failList;
	}

	public void setFailList(List failList) {
		this.failList = failList;
	}

	public List getSuccessList() {
		return successList;
	}

	public void setSuccessList(List successList) {
		this.successList = successList;
	}

//	public Hashtable getHashtable() {
//		return hashtable;
//	}
//
//	public void setHashtable(Hashtable hashtable) {
//		this.hashtable = hashtable;
//	}

//	public Date getCurrentDate() {
//		return currentDate;
//	}
//
//	public void setCurrentDate(Date currentDate) {
//		this.currentDate = currentDate;
//	}
}
