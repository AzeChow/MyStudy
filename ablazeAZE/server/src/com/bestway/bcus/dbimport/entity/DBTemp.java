/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DBTemp extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	private String temp1;
	private String temp2;
	/**
	 * @return Returns the temp1.
	 */
	public String getTemp1() {
		return temp1;
	}
	/**
	 * @param temp1 The temp1 to set.
	 */
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	/**
	 * @return Returns the temp2.
	 */
	public String getTemp2() {
		return temp2;
	}
	/**
	 * @param temp2 The temp2 to set.
	 */
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
}
