package com.bestway.common.constant;

public class LimitFlag {
	/**
	 * 调整前旧手册
	 */
	public static final String ADJUST_BEFORE_EMS = "1";

	/**
	 * 调整后新手册
	 */
	public static final String ADJUST_AFTER_EMS = "2";

	/**
	 * 台账专用手册
	 */
	public static final String ACOUNT_BOOK_USE = "3";

	public static String getLimitFlagDesc(String type) {
		if (LimitFlag.ADJUST_BEFORE_EMS.equals(type)) {
			return "调整前旧手册";
		} else if (LimitFlag.ADJUST_AFTER_EMS.equals(type)) {
			return "调整后新手册";
		} else if (LimitFlag.ACOUNT_BOOK_USE.equals(type)) {
			return "台账专用手册";
		}
		return "";
	}
}
