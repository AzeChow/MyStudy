package com.bestway.common.constant;

public class AgreementState {
	public static final int ADDED = 0;// 新增
	public static final int PUT_ON_RECORD = 1;// 备案
	public static final int EXECUTING = 2;// 执行
	
	public static final String getStateDesc(int state) {
		String str = "";
		switch (state) {
		case ADDED:
			str = "新增";
			break;
		case PUT_ON_RECORD:
			str = "备案";
			break;
		case EXECUTING:
			str = "执行";
			break;		
		}
		return str;
	}
}
