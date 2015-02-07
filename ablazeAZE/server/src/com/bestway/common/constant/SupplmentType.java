package com.bestway.common.constant;

import java.io.Serializable;

public class SupplmentType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 非补充申报报关单           0
	 */
	public static final Integer  NO_SYPPLMENT = Integer.valueOf(0);
	/**
	 * 主动补充申报报关单         1
	 */
	public static final Integer  INITIATIVE_SYPPLMENT = Integer.valueOf(1);
	/**
	 * 被动补充申报报关单         2
	 */
	public static final Integer  PASSIVITY_SYPPLMENT = Integer.valueOf(2);
	
	
	
	public static String getSupplmentTypeDesc(int supplmentType) {
		if(supplmentType == NO_SYPPLMENT) {
			return "非补充申报报关单";
		} else if(supplmentType == INITIATIVE_SYPPLMENT) {
			return "主动补充申报报关单";
		} else if(supplmentType == PASSIVITY_SYPPLMENT) {
			return "被动补充申报报关单";
		} else {
			return "非补充申报报关单"; 
		}
	}
	/**
	 * 未发送         0
	 */
	public static final Integer  SEND_N = Integer.valueOf(0);
	/**
	 * 已发送         1
	 */
	public static final Integer  SEND_Y = Integer.valueOf(1);
	public static String getSupplmentSend(int sendType) {
		if(sendType == SEND_N) {
			return "未发送";
		} else if(sendType == SEND_Y) {
			return "已发送";
		} else {
			return "未发送"; 
		}
	}
	/**
	 * 未检查         0
	 */
	public static final Integer  CHECK_N = Integer.valueOf(0);
	/**
	 * 已检查         1
	 */
	public static final Integer  CHECK_Y = Integer.valueOf(1);
	public static String getSupplmentcheck(int sendType) {
		if(sendType == CHECK_N) {
			return "未检查";
		} else if(sendType == CHECK_Y) {
			return "已检查";
		} else {
			return "未检查"; 
		}
	}
}
