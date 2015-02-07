package com.bestway.bcs.contract.entity;

public class TempBankMode {
	
	/**
	 * 纸质台帐
	 */
	public static String PAPER = "0";
	
	/**
	 * 工商银行
	 */
	public static String ICBC = "1";
	
	/**
	 * 中国银行
	 */
	public static String BANKOFCHINA = "2";
	
	public static String getBankModeDesc(String type) {
		if(type.equals("0")){
			return "纸质台帐";
		}else if(type.equals("1")){
			return "中国银行";
		}else if(type.equals("2")){
			return "工商银行";
		}
		return "";
	}

}
