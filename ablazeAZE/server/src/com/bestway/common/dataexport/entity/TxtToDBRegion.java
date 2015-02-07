/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 文本 导出DB 到的对应域定义
 * @author
 */
public class TxtToDBRegion extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 *  域名称
	 */
	private String				regionName			= null;
	/**
	 *  源文件路径
	 */
	private String				srcFilePath			= null;
	/**
	 *  导入文件类型
	 */
	private Integer				fileType			= null;
	/**
	 *  目的表
	 */
	private String				destTableName		= null;
	/**
	 *  目的数据源
	 */
	private JDBCDataSource		destJDBCDataSource	= null;
	/**
	 *  从第几行导入
	 */
	private Integer				importRowNumber		= 1;
	/**
	 *  简繁转换标致
	 */
	private String				gbkToBig5Flag		= null;						// GbkToBig5Flag
	/**
	 *  导入方式
	 */
	private Integer				importDataMode		= null;						// ImportDataMode.java

	/**
	 *  域描述
	 */
	private String				note				= null;
	/**
	 *  导入文件编码
	 */
	private String				encoding			= null;

	/**
	 * @return Returns the regionName.
	 */
	public String getRegionName() {
		return regionName;
	}

	public Integer getImportDataMode() {
		return importDataMode;
	}

	public void setImportDataMode(Integer importDataMode) {
		this.importDataMode = importDataMode;
	}

	/**
	 * @param regionName
	 *            The regionName to set.
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getSrcFilePath() {
		return srcFilePath;
	}

	public void setSrcFilePath(String destTableName) {
		this.srcFilePath = destTableName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getGbkToBig5Flag() {
		return gbkToBig5Flag;
	}

	public void setGbkToBig5Flag(String gbkToBig5Flag) {
		this.gbkToBig5Flag = gbkToBig5Flag;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public JDBCDataSource getDestJDBCDataSource() {
		return destJDBCDataSource;
	}

	public void setDestJDBCDataSource(JDBCDataSource destJDBCDataSource) {
		this.destJDBCDataSource = destJDBCDataSource;
	}

	public String getDestTableName() {
		return destTableName;
	}

	public void setDestTableName(String destTableName) {
		this.destTableName = destTableName;
	}

	public Integer getImportRowNumber() {
		return importRowNumber;
	}

	public void setImportRowNumber(Integer importRowNumber) {
		this.importRowNumber = importRowNumber;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
