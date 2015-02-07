package com.bestway.client.windows.form;

import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

public class ModuleTreeNode extends DefaultMutableTreeNode {
	private String moduleId;

	private String targetForm;

	private String isshow;

	private String caption;

	private String iconName;

	private Map<String, Object> parameters = new HashMap<String, Object>();

	public ModuleTreeNode(String moduleId, String caption, String isshow,
			String targetForm, Map<String, Object> parameters) {
		super(caption);
		this.targetForm = targetForm;
		this.isshow = isshow;
		this.moduleId = moduleId;
		this.caption = caption;
		this.parameters=parameters;
	}

	public ModuleTreeNode(String caption, String isshow, String targetForm) {
		super(caption);
		this.targetForm = targetForm;
		this.isshow = isshow;
		this.caption = caption;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getTargetForm() {
		return targetForm;
	}

	public String getIsshow() {
		return isshow;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setTargetForm(String targetForm) {
		this.targetForm = targetForm;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

}
