package com.bestway.bls.entity;

import java.io.Serializable;

/**
 * 单证类型	formID 所指示单证的类型
 * 入仓单-ImMft, 出仓单-ExMft，入仓报关单-ImEnt，出仓报关单-ExEnt
 * @author ower
 *
 */
public class FormType implements Serializable{
	/**入仓单*/
	public static String IM_MFT = "ImMft";
	/**出仓单*/
	public static String EX_MFT = "ExMft";
	/**入仓报关单*/
	public static String IM_ENT = "ImEnt";
	/**出仓报关单*/
	public static String EX_ENT = "ExEnt";
	
	
	/**获得中文显示*/
	public static String getNote(String formType){
		String note = "";
		if(IM_MFT.equals(formType)){
			note = "入仓单";
		}else if(EX_MFT.equals(formType)){
			note = "出仓单";
		}else if(IM_ENT.equals(formType)){
			note = "入仓报关单";
		}else if(EX_ENT.equals(formType)){
			note = "出仓报关单";
		}
		return note;
	}
}
