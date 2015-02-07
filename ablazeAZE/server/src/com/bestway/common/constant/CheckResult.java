/*
 * Created on 2004-7-24
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckResult implements Serializable {
	/**
	 * 入库成功
	 */
	public static final int IN_STOCK_SUCCEED = 1;
	
	/**
	 * 审批通过
	 */
	public static final int CHECK_PASS = 2;  
	
	/**
	 * 退单
	 */
	public static final int QUIT_BILL= 3;             
	
	/**
	 * 重复申报(退单)
	 */
	public static final int RE_DECLARE = 4;      
	
	/**
	 * 入库成功(自动审核检查通过)
	 */
	public static final int IN_STOCK_SUCCEED_AUTO = 5; 
	
}
