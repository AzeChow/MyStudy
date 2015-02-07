package com.bestway.common.constant;
/**
 * 单耗申报环节
 * @author Administrator
 *
 */
public class EquipMode {
	/**
	 * 备案
	 */
	public static final String PUT_ON_RECORD = "1";

	/**
	 * 出口前
	 */
	public static final String BEFORE_EXPORT = "2";

	/**
	 * 报核前
	 */
	public static final String BEFORE_CANCEL = "3";

	public static String getEquipModeDesc(String type) {
		if (EquipMode.PUT_ON_RECORD.equals(type)) {
			return "备案";
		} else if (EquipMode.BEFORE_EXPORT.equals(type)) {
			return "出口前";
		} else if (EquipMode.BEFORE_CANCEL.equals(type)) {
			return "报核前";
		}
		return "";
	}
}
