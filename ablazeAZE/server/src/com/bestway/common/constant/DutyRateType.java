package com.bestway.common.constant;
/**
 * 非保税料件 申报状态
 * @author Administrator
 *
 */
public class DutyRateType {
	/**
	 * 企业不申报
	 */
	public static final String NO_DECLARE = "1";

	/**
	 * 企业申报
	 */
	public static final String DECLARE = "2";

	/**
	 * 已核定
	 */
	public static final String CHECK_AND_RATIFY = "9";

	public static String getEquipModeDesc(String type) {
		if (DutyRateType.NO_DECLARE.equals(type)) {
			return "企业不申报";
		} else if (DutyRateType.DECLARE.equals(type)) {
			return "企业申报";
		} else if (DutyRateType.CHECK_AND_RATIFY.equals(type)) {
			return "已核定";
		}
		return "";
	}
	
	public static Double getDutyRateStrint(String str){
		if(str==null || "".equals(str)){
			return 0d;
		}
		str=str.trim();
		if("企业不申报".equals(str)){
			return 1d;
		}else if("企业申报".equals(str)){
			return 2d;
		}else if("已核定".equals(str)){
			return 9d;
		}
		return 0d;
	}
}
