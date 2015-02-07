package com.bestway.common.tools.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PluginTreeNode implements Serializable {
	private static final long	serialVersionUID	= 1L;
	
	public static final String	PLUGIN_FOLDER		= "clientplugins";
	
	String						targetForm			= null;
	String						caption				= null;
	String						isshow				= "0";

	List<PluginTreeNode>		list				= new ArrayList<PluginTreeNode>();

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}

	public List<PluginTreeNode> getList() {
		return list;
	}

	public void setList(List<PluginTreeNode> list) {
		this.list = list;
	}

	public String getTargetForm() {
		return targetForm;
	}

	public void setTargetForm(String targetForm) {
		this.targetForm = targetForm;
	}

}
