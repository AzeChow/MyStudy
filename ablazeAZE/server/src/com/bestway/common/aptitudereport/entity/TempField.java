package com.bestway.common.aptitudereport.entity;

import java.io.Serializable;

import com.bestway.common.tools.entity.TempNodeItem;

public class TempField implements Serializable ,Comparable {

	private String	enName		= null;
	private String	className	= null;
	private String	cnName		= null;




	
	public int compareTo(Object o) {
		TempField a = (TempField) o;
		if (a == null) {
			return 1;
		}
		String tempName = enName.toLowerCase();
		//
		// ASC
		//
		return tempName.compareTo(a.getEnName().toLowerCase());
		//return 0;
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





	public String getEnName() {
		return enName;
	}





	public void setEnName(String enName) {
		this.enName = enName;
	}



}

