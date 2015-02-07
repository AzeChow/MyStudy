package com.bestway.bcus.system.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

public class JarFile implements Serializable {

	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 * 文件名称
	 */
	private String				fileName			= null;
	/**
	 * 修改时间
	 */
	private String				modifiyTime			= null;
	/**
	 * 
	 */
	private String				hashCode			= null;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public String getModifiyTime() {
		return modifiyTime;
	}

	public void setModifiyTime(String modifiyTime) {
		this.modifiyTime = modifiyTime;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

}
