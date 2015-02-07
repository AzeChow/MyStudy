package com.bestway.common.constant;

public class BcsBusinessType {
	/**
	 *  经营范围/合同备案/备案资料库备案 
	 */
	public static final String EMS_POR_WJ = "1"; 

	/**
	 * 电子帐册备案商品/通关备案
	 */
	public static final String EMS_POR_BILL = "2"; 
	
	/**
	 * 数据报核cancel after
	 */
	public static final String CANCEL_AFTER_VERIFICA = "4"; 

	public static String getBcsBusinessTypeDesc(String type) {
		if (BcsBusinessType.EMS_POR_WJ.equals(type)) {
			return "备案资料库备案";
		} else if (BcsBusinessType.EMS_POR_BILL.equals(type)) {
			return "通关手册备案";
		}  else if (BcsBusinessType.CANCEL_AFTER_VERIFICA.equals(type)) {
			return "数据核销";
		} 
		return "";
	}
}
