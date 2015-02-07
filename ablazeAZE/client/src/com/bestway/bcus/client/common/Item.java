package com.bestway.bcus.client.common;

public class Item {
	private String	name		= null;
	private String	property	= null;
	private int		dataType	= -1;
	private Integer		sort	= null; //数据字典用于排序

	public Item(String name, String property, int dataType) {
		this.name = name;
		this.property = property;
		this.dataType = dataType;
	}

	public Item(String name, String property, Integer sort) {
		this.name = name;
		this.property = property;
		this.sort = sort;
	}
	
	
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String toString() {
		return name;
	}
}

