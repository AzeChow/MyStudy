package com.bestway.customs.common.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.common.BaseScmEntity;

public class TempLoadBGDFromQPXmlErrorInfo implements java.io.Serializable {
	private String fileName;

	private Date date;

	private Integer impExpFlag;

	private String errorInfo;

	public Date getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			return java.sql.Date.valueOf(dateFormat.format(date));
		}
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(Integer impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String customsDeclarationCode) {
		this.errorInfo = customsDeclarationCode;
	}
}
