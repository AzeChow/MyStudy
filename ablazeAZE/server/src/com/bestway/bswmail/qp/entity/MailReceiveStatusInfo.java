package com.bestway.bswmail.qp.entity;

import java.util.ArrayList;
import java.util.List;

public class MailReceiveStatusInfo implements java.io.Serializable {
	private int fileTotalNum = 0;

	private int fileSuccessNum = 0;

	private int fileFailNum = 0;

	private String statusInfo = "";

	private List successList = new ArrayList();

	private List failList = new ArrayList();

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
}
