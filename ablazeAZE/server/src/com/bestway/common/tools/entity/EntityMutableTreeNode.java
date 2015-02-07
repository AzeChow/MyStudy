package com.bestway.common.tools.entity;

import javax.swing.tree.DefaultMutableTreeNode;

public class EntityMutableTreeNode extends DefaultMutableTreeNode {
	private String className = null;

	private String enytityName = null;

	private String cnName = null;

	private String propertyName = null;

	private boolean isSelected = false;

	private Object begin = null;

	private Object end = null;
	
	private Class  cls=null;

	public EntityMutableTreeNode() {
	}

	public Object getBegin() {
		return begin;
	}

	public void setBegin(Object begin) {
		this.begin = begin;
	}

	public Object getEnd() {
		return end;
	}

	public void setEnd(Object end) {
		this.end = end;
	}

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

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getEnytityName() {
		return enytityName;
	}

	public void setEnytityName(String enytityName) {
		this.enytityName = enytityName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Class getCls() {
		return cls;
	}

	public void setCls(Class cls) {
		this.cls = cls;
	}

}
