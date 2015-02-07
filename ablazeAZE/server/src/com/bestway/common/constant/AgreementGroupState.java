package com.bestway.common.constant;

public class AgreementGroupState {

	public static final int RECEIVE = 0;// 收件

	public static final int CERT_HANDIN = 1;// 征免税证递单

	public static final int CERT_OUTBILL = 2;// 征免税证出单

	public static final int ON_OUT_NOTICE = 3;// 待出货通知

	public static final int ARRIVE_HK = 4;// 设备到港

	public static final int ARRIVE_DG = 5;// 设备到莞

	public static final int WAR = 6;// 商检

	public static final int APPLY_TO_CUSTOMS = 7;// 报关

	public static final int IN_FACTORY = 8;// 进厂

	public static final String getStateDesc(int state) {
		String str = "";
		switch (state) {
		case RECEIVE:
			str = "收件";
			break;
		case CERT_HANDIN:
			str = "征免税证递单";
			break;
		case CERT_OUTBILL:
			str = "征免税证出单";
			break;
		case ON_OUT_NOTICE:
			str = "待出货通知";
			break;
		case ARRIVE_HK:
			str = "设备到港";
			break;
		case ARRIVE_DG:
			str = "设备到莞";
			break;
		case WAR:
			str = "商检";
			break;
		case APPLY_TO_CUSTOMS:
			str = "报关";
			break;
		case IN_FACTORY:
			str = "进厂";
			break;
		}
		return str;
	}
}
