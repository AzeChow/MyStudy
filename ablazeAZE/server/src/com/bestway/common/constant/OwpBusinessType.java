package com.bestway.common.constant;

public class OwpBusinessType {
	/**
	 * 外发加工 申请表
	 */
	public static final String OWP_APP = "5";

	/**
	 * 外发加工 发货单
	 */
	public static final String OWP_BILL_SEND = "6";
	
	/**
	 * 外发加工 收货单
	 */
	public static final String OWP_BILL_RECV = "7";
	/**
	 * 外发加工 撤销申请表
	 */
	public static final String CANCEL_BILL = "8";

	public static String getOwpBusinessTypeDesc(String type) {
		if (OwpBusinessType.OWP_APP.equals(type)) {
			return "申请表";
		} else if (OwpBusinessType.OWP_BILL_SEND.equals(type)) {
			return "发货单";
			
		} else if (OwpBusinessType.OWP_BILL_RECV.equals(type)) {
			return "收货单";
			
		}else if (OwpBusinessType.CANCEL_BILL.equals(type)) {
			return "撤销申请表";
		}
		return "";
	}
}
