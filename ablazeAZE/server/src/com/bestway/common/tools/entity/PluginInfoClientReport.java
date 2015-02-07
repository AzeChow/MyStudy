package com.bestway.common.tools.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 客户端报表插件信息
 * 
 * @author Administrator
 * 
 */
public class PluginInfoClientReport implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 报表插件文件夹 */
	public static final String REPORT_PLUGIN_FOLDER = "reportplugins";

	/** 包名 */
	private String id = null;

	/** 中文名 */
	private String cnName = null;

	/** 最后修改时间 */
	private String modifiyTime = null;

	/** 文件大小 */
	private Long fileLength = null;

	/** 文件路径 */
	private String filePath = null;

	/** 文件名 */
	private String fileName = null;

	/** 描述 */
	private String description = null;

//	<tragetClassName window="com.bestway.bcs.client.contract.FmContract">
//		<className>com.bestway.client.plugin.report.ClientReportPluginImpl</className>
//		<className>com.bestway.client.plugin.report.ClientReportPluginImpl2</className>
//	</tragetClassName>

	/** 
	 * key = 作用的目标类名 JDialog Or JInnerFrame
	 * value = 客户端插件类名集合
	 *  
	 **/
	private Map<String,List<String>> map =null;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModifiyTime() {
		return modifiyTime;
	}

	public void setModifiyTime(String modifiyTime) {
		this.modifiyTime = modifiyTime;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public Map<String, List<String>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}

}
