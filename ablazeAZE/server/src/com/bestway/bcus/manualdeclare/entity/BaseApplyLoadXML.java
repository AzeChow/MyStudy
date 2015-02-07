package com.bestway.bcus.manualdeclare.entity;

import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.common.BaseScmEntity;

public class BaseApplyLoadXML extends BaseScmEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 报关清单
	 */
	private ApplyToCustomsBillList apply=null;
	/**
	 * 导入XML的文件名
	 */
	private String fileName;
	/**
	 * 错误信息
	 */
	private String errorInfo;
	public ApplyToCustomsBillList getApply() {
		return apply;
	}
	public void setApply(ApplyToCustomsBillList apply) {
		this.apply = apply;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	
	
}
