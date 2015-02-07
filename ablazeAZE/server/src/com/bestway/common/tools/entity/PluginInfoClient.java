package com.bestway.common.tools.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bestway.common.CommonUtils;

/**
 * 客户端插件信息
 * 
 * @author Administrator
 * 
 */
public class PluginInfoClient implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String ROOT_NODE = "根路径";
	/**
	 * 包名
	 */
	private String id = null;
	/**
	 * 中文名
	 */
	private String cnName = null;
	/**
	 * 最后修改时间
	 */
	private String modifiyTime = null;
	/**
	 * 文件大小
	 */
	private Long fileLength = null;
	/**
	 * 文件路径
	 */
	private String filePath = null;
	/**
	 * 文件名
	 */
	private String fileName = null;
	/**
	 * 描述
	 */
	private String description = null;
	/**
	 * 插件结点的父路径
	 */
	private String parentPath = ROOT_NODE;
	/**
	 * 当前的结点的位置
	 */
	private int childIndex = -1;
	private String replacePath = "";
	private String replaceform = "";
	/**
	 * 是否替换
	 */
	private String replace = "";
	/**
	 * 详细插件结点集合
	 */
	List<PluginTreeNode> list = new ArrayList<PluginTreeNode>();
	/** 要替换的list */
	private ReplaceIntreeList replaceIntreeList = null;

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

	public int getChildIndex() {
		return childIndex;
	}

	public void setChildIndex(int childIndex) {
		this.childIndex = childIndex;
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

	public List<PluginTreeNode> getList() {
		return list;
	}

	public void setList(List<PluginTreeNode> list) {
		this.list = list;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
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

	/** 路径数组 */
	public String[] getPaths() {
		if (parentPath == null || parentPath.equals(ROOT_NODE)) {
			return new String[0];
		}
		return CommonUtils.split(parentPath, ".");
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public String getReplacePath() {
		return replacePath;
	}

	public void setReplacePath(String replacePath) {
		this.replacePath = replacePath;
	}

	public String getReplaceform() {
		return replaceform;
	}

	public void setReplaceform(String replaceform) {
		this.replaceform = replaceform;
	}

	public ReplaceIntreeList getReplaceIntreeList() {
		return replaceIntreeList;
	}

	public void setReplaceIntreeList(ReplaceIntreeList replaceIntreeList) {
		this.replaceIntreeList = replaceIntreeList;
	}

}
