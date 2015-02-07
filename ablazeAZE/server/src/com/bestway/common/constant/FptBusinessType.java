package com.bestway.common.constant;

public class FptBusinessType {
	/**
	 * 结转申请表
	 */
	public static final String FPT_APP = "1";

	/**
	 * 收发货单
	 */
	public static final String FPT_BILL = "2";

	/**
	 * 退货单
	 */
	public static final String FPT_BILL_BACK = "3";

	/**
	 * 撤消报文
	 */
	public static final String FPT_CANCEL_BILL = "4";

	/**
	 * 下载报文
	 */
	public static final String FPT_DOWN_DATA = "5";
	/**
	 * 其它
	 * @param type
	 * @return
	 */
	public static final String FPT_OTHER = "6";
	
	public static String getFptBusinessTypeDesc(String type) {
		if (FptBusinessType.FPT_APP.equals(type)) {
			return "结转申请表";
		} else if (FptBusinessType.FPT_BILL.equals(type)) {
			return "收发货单";
		} else if (FptBusinessType.FPT_BILL_BACK.equals(type)) {
			return "退货单";
		} else if (FptBusinessType.FPT_CANCEL_BILL.equals(type)) {
			return "撤消报文";
		} else if (FptBusinessType.FPT_DOWN_DATA.equals(type)) {
			return "下载报文";
		}else if (FptBusinessType.FPT_OTHER.equals(type)) {
			return "其它";	
		}
		return "";
	}
}
