package com.bestway.common.tools.entity;

import java.io.Serializable;
import java.lang.reflect.Type;

public class TempNodeItem implements Serializable ,Comparable {
	private String enName        = null;
	private String	name		= null;
	private String	className	= null;
	private String	cnName		= null;
	private String  remark      ="";

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return name==null?"":name;
	}
	
	public int compareTo(Object o) {
		TempNodeItem a = (TempNodeItem) o;
		if (a == null) {
			return 1;
		}
		String tempName = name.toLowerCase();
		//
		// ASC
		//
		return tempName.compareTo(a.getName().toLowerCase());
		//return 0;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}



}
