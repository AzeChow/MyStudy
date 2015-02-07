package com.bestway.common.constant;

public class DzscListType implements java.io.Serializable {
	public final static int NORMAL_DCR = 0;// 一般报关单

	public final static int PRE_TRANS_DCR = 1;// 提前转关报关单

	public final static int SECOND_TRANS_DCR = 2;// 二次转关报关单

	public final static int APANAGE_DCR = 3;// 属地报关

	public final static String getListTypeDesc(int type) {
		String str = "";
		switch (type) {
		case (NORMAL_DCR):
			str = "一般报关单";
			break;
		case (PRE_TRANS_DCR):
			str = "提前转关报关单";
			break;
		case (SECOND_TRANS_DCR):
			str = "二次转关报关单";
			break;
		case (APANAGE_DCR):
			str = "属地报关";
			break;
		}
		return str;
	}
}
