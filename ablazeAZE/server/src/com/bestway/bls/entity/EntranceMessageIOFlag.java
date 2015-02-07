package com.bestway.bls.entity;

/**
 * 货到报告进出口标识位类
 * @author hw
 *
 */
public class EntranceMessageIOFlag {
	/**
	 * 进口
	 */
	public static final String IMPORT = "I"; // 

	/**
	 * 出口
	 */
	public static final String EXPORT = "O"; // 

	/**
	 * 转化标志位
	 * @param value
	 * @return
	 */
	public static final String getImpExpFlagSpec(String value) {
		String returnValue = "";
		if (EntranceMessageIOFlag.IMPORT.equals(value)) {
			returnValue = "进口";
		} else if (EntranceMessageIOFlag.EXPORT.equals(value)) {
			returnValue = "出口";
		} 
		return returnValue;
	}
}
