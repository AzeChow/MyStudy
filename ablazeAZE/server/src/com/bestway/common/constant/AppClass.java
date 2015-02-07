package com.bestway.common.constant;

import java.io.Serializable;

/**
 * Z 加工贸易企业保税货物的深加工结转； H 加工区的深加工结转
 * 
 * @author ower
 * 
 */
public class AppClass implements Serializable{

	public static final String Z = "Z";// 一般保税货物深加工结转 （原来是“加工区的深加工结转”)
	public static final String H = "H";//加工贸易企业保税货物的深加工结转
	public static final String G = "G";//wss2010.09.26新增外发加工申请类型
	public static final String M = "M";//2013.10.15 HH 一般保税货物深加工结转
	
	/**获得其备注*/
	public static final String getNote(String value) {
		if(AppClass.H.equals(value)){
			return "加工区的深加工结转";
		}else if(AppClass.Z.equals(value)){
			return "一般保税货物深加工结转";
		}else if(AppClass.G.equals(value)){
			return "外发加工申请";
		}else if (AppClass.M.equals(value)) {
			return "一般保税货物深加工结转";
		}
		return "";
	}

	
	/**获得其备注 所对应的 代码 HH 2013.10.15*/
	public static final String getCode(String value) {
		if("加工区的深加工结转".equals(value)){
			return "H";
		}else if("一般保税货物深加工结转".equals(value)){
			return "Z";
		}else if("外发加工申请".equals(value)){
			return "G";
		}else if ("一般保税货物深加工结转".equals(value)) {
			return "M";
		}
		return "";
	}
//	Z("加工贸易企业保税货物的深加工结转"), H("加工区的深加工结转");

//	AppClass(String value) {
//		this.value = value;		
//	}
//
//	private final String value;
//
//	public String getValue() {
//		return value;
//	}
	
	
}
