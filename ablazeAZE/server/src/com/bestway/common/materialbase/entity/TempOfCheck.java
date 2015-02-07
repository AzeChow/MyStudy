package com.bestway.common.materialbase.entity;

import java.io.Serializable;

public class TempOfCheck implements Serializable{

	/**
	 * 父级料号
	 */
	private String parentNo = null;

	/**
	 * 子级料号
	 */
	private String compentNo = null;
	/**
	 * 父件版本号
	 */
	private String versionNo = null;
	/**
	 * 子件版本号
	 */
	private String childVersionNo = null;
	/**
	 * 错误信息
	 */
	private String erro = null;
	public String getParentNo() {
		return parentNo;
	}
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	public String getCompentNo() {
		return compentNo;
	}
	public void setCompentNo(String compentNo) {
		this.compentNo = compentNo;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getChildVersionNo() {
		return childVersionNo;
	}
	public void setChildVersionNo(String childVersionNo) {
		this.childVersionNo = childVersionNo;
	}
	public String getErro() {
		return erro;
	}
	public void setErro(String erro) {
		this.erro = erro;
	}
	
}
