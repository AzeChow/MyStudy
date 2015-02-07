package com.bestway.bcs.contract.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存储回执资料
 */
public class ProcessedContractData extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 获取文件名称
	 * 
	 * @return fileName 文件名称
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置文件名称
	 * 
	 * @param fileName 文件名称
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
