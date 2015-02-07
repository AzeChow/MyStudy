package com.bestway.bls.entity;

import java.io.Serializable;

public class BillSpecialApplyType implements Serializable{

	/**
	 * 增值性加工
	 */
	public static final String PRC ="PRC";
	
	/**
	 * 弃货
	 */
	public static final String ABD ="ABD";
	
	/**
	 * 换货
	 */
	public static final String RPL ="RPL";
	
	/**
	 * 仓单作废
	 */
	public static final String CAN ="CAN";
	
	/**
	 * 货物延期存放
	 */
	public static final String DLA ="DLA";
	
}
