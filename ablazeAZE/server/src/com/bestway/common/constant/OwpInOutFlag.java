package com.bestway.common.constant;

import java.io.Serializable;

/** 转入转出标志 */
public class OwpInOutFlag implements Serializable {
	/**
	 * 外发申请表
	 */
	public static final String APP = "2";
	/**
	 * 外发发货
	 */
	public static final String SEND = "3";
	/**
	 * 外发收货
	 */
	public static final String RECV = "4";


	public static String getInOutFlagSpec(String flag) {
		if (SEND.equals(flag)) {
			return "外发发货";
		} else if (RECV.equals(flag)) {
			return "外发收货";
		}else if (APP.equals(flag)) {
			return "外发申请表";
		}
		return "";
	}

}
