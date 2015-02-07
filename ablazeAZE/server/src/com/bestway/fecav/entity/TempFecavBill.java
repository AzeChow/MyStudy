/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fecav.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempFecavBill implements Serializable {
	/**
	 * 外汇核销单1
	 */
	private FecavBill fecavBill1 = null;
	/**
	 * 外汇核销单2
	 */
	private FecavBill fecavBill2 = null;

	/**
	 * 取得外汇核销单1内容
	 * @return  外汇核销单1
	 */
	public FecavBill getFecavBill1() {
		return fecavBill1;
	}

	/**
	 * 设置外汇核销单1内容
	 * @param fecavBill1 外汇核销单1 
	 */
	public void setFecavBill1(FecavBill fecavBill1) {
		this.fecavBill1 = fecavBill1;
	}

	/**
	 * 取得外汇核销单2内容
	 * @return 外汇核销单2
	 */
	public FecavBill getFecavBill2() {
		return fecavBill2;
	}

	/**
	 * 设置外汇核销单2内容
	 * @param fecavBill2 外汇核销单2
	 */
	public void setFecavBill2(FecavBill fecavBill2) {
		this.fecavBill2 = fecavBill2;
	}

	

}
