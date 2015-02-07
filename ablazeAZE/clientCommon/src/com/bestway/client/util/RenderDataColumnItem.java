package com.bestway.client.util;

public class RenderDataColumnItem {
	private String	code;

	private String	name;

	private boolean	isSelected;

	public RenderDataColumnItem() {	
	}
	
	public RenderDataColumnItem(String str, String name) {
		this.code = str;
		this.name = name;
		isSelected = false;
	}

	public void setSelected(boolean b) {
		isSelected = b;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
