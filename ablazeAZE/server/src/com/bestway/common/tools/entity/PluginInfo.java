package com.bestway.common.tools.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 插件信息
 * @author Administrator
 *
 */
public class PluginInfo implements Serializable {

	private static final long				serialVersionUID	= 1L;
	/**
	 * ＩＤ
	 * 
	 */
	private String							id					= null;
	/**
	 * 运行插件类名
	 */
	private String							className			= null;
	/**
	 * 插件中文名
	 */
	private String							cnName				= null;
	/**
	 * 文件路径
	 */
	private String							filePath			= null;
	/**
	 * 文件名
	 */
	private String							fileName			= null;
	/**
	 * 最后修改时间
	 */
	private String							modifiyTime			= null;
	/**
	 * 描述
	 */
	private String							description			= null;
	/**
	 * 是否自动运行
	 */
	private Boolean							isAutoRun			= false;

	private Map<String, PluginParameter>	parameter			= new LinkedHashMap<String, PluginParameter>();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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

	public Boolean getIsAutoRun() {
		return isAutoRun;
	}

	public void setIsAutoRun(Boolean isAutoRun) {
		this.isAutoRun = isAutoRun;
	}

	public Map<String, PluginParameter> getParameter() {
		return parameter;
	}

	public void setParameter(Map<String, PluginParameter> parameter) {
		this.parameter = parameter;
	}

}
