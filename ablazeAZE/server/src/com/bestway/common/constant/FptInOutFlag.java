package com.bestway.common.constant;

import java.io.Serializable;

/** 转入转出标记 */
public class FptInOutFlag implements Serializable {
	/**
	 * 转出方
	 */
	public static final String OUT = "0";
	/**
	 * 转入方
	 */
	public static final String IN = "1";
//	/**
//	 * 转出
//	 */
//	public static final int rollOUT = 0;
//	/**
//	 * 转入
//	 */
//	public static final int rollIN = 1;

	public static String getInOutFlagSpec(String flag) {
		if (OUT.equals(flag)) {
			return "转出单据";
		} else if (IN.equals(flag)) {
			return "转入单据";
		}
		return "";
	}

	public static String getNote(String flag) {
		if (OUT.equals(flag)) {
			return "转出";
		} else if (IN.equals(flag)) {
			return "转入";
		}
		return "";
	}

//	public static String getInOrOutFlag(int flag) {
//		String str = "";
//		switch (flag) {
//
//		case FptInOutFlag.rollOUT: // 0
//			str = "转出";
//			break;
//		case FptInOutFlag.rollIN: // 1
//			str = "转入";
//			break;
//		}
//		return str;
//	}
}
