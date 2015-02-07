package com.bestway.client.windows.form;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;

public class JModuleMenu extends JMenu {
	private String moduleId;

	private String targetForm;

	private String isshow;

	private String iconName;

	private Map<String, Object> parameters = new HashMap<String, Object>();

	public JModuleMenu(String moduleId, String caption, String isshow,
			String targetForm, Map<String, Object> parameters) {
		super(caption);
		this.targetForm = targetForm;
		this.isshow = isshow;
		this.moduleId = moduleId;
		this.parameters = parameters;
	}

	public String getIsshow() {
		return isshow;
	}

	public void setIsshow(String isshow) {
		this.isshow = isshow;
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
