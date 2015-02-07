package com.bestway.common.constant;

import java.io.Serializable;

/**
 * 外汇核销单状态
 * @author Administrator
 *
 */
public class FecavState implements Serializable {
	/**
	 * 外部领用
	 */
	public static final int OUTER_OBTAIN = 1; 
	
	/**
	 * 内部领用
	 */
	public static final int INNER_OBTAIN = 2;     
	
	/**
	 * 核销单使用
	 */
	public static final int USED = 3;      
	
	/**
	 * 退税联签收
	 */
	public static final int DEBENTURE_SIGN_IN=4;
	
	/**
	 * 核销员交单
	 */
	public static final int HAND_IN_BILL=5;
	
	/**
	 * 管制	
	 */
	public static final int CONTROL=6;
	
	/**
	 * 冲销
	 */
	public static final int STRIKE_BALANCE=7;
	
	/**
	 * 核销
	 */
	public static final int CANCEL=8;
	
	/**
	 * 财务签收
	 */
	public static final int FINANCE_SIGN_IN=9;
	
	/**
	 * 关帐
	 */
	public static final int CLOSE_ACCOUNT=10;	
}
