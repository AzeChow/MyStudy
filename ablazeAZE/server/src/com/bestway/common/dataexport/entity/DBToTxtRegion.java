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
 * 导出到文本的对应域定义
 * @author
 */
public class DBToTxtRegion extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 *  域名称
	 */
	private String				regionName			= null;
	/**
	 *  目的文件路径
	 */
	private String				destFilePath		= null;
	/**
	 *  导出文件类型
	 *  TXT	= 1;	文本类型
	 *  XLS	= 2;	excel 文件类型
	 */
	private Integer				fileType			= null;
	/**
	 *  视图对象(数据来源信息)
	 */
	private JDBCView			srcJDBCView			= null;
	/**
	 *  是否有标题栏导出
	 */
	private Boolean				isExistCaption		= false;
	/**
	 *  从第几行导出
	 */
	private Integer				exportRowNumber		= 1;
	/**
	 *  简繁转换标致
	 */
	private String				gbkToBig5Flag		= null;					
	/**
	 *  域描述
	 */
	private String				note				= null;
	/**
	 *  导出文件编码
	 */
	private String				encoding			= null;

	/**
	 * @return Returns the dbView.
	 */
	public JDBCView getSrcJDBCView() {
		return srcJDBCView;
	}

	/**
	 * @param dbView
	 *            The dbView to set.
	 */
	public void setSrcJDBCView(JDBCView dbView) {
		this.srcJDBCView = dbView;
	}

	/**
	 * @return Returns the regionName.
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName
	 *            The regionName to set.
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getDestFilePath() {
		return destFilePath;
	}

	public void setDestFilePath(String destTableName) {
		this.destFilePath = destTableName;
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

	public Integer getExportRowNumber() {
		return exportRowNumber;
	}

	public void setExportRowNumber(Integer exportRowNumber) {
		this.exportRowNumber = exportRowNumber;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Boolean getIsExistCaption() {
		return isExistCaption;
	}

	public void setIsExistCaption(Boolean isExistCaption) {
		this.isExistCaption = isExistCaption;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
