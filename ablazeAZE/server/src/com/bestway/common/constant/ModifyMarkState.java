/*
 * Created on 2004-8-11
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ModifyMarkState implements Serializable {
	/**
	 * 未修改
	 */
	public static final String UNCHANGE = "0"; 

	/**
	 * 已修改
	 */
	public static final String MODIFIED = "1"; 

	/**
	 * 已删除
	 */
	public static final String DELETED = "2";  
 
	/**
	 * 新增
	 */
	public static final String ADDED = "3";    
	
	public static final String getModifyMarkSpec(String value) {
		
		String returnValue = "";
		
		if (ModifyMarkState.UNCHANGE.equals(value)) {
			
			returnValue = "未修改";
			
		} else if (ModifyMarkState.MODIFIED.equals(value)) {
			
			returnValue = "已修改";
			
		} else if (ModifyMarkState.DELETED.equals(value)) {
			
			returnValue = "已删除";
			
		} else if (ModifyMarkState.ADDED.equals(value)) {
			
			returnValue = "新增";
		} 
		return returnValue;
	}
	
	public static final String getToolTipText(){
		return "<html>"+ModifyMarkState.UNCHANGE+": 未修改<br>"+
		ModifyMarkState.MODIFIED+": 已修改<br>"+
		ModifyMarkState.DELETED+": 已删除<br>"+
		ModifyMarkState.ADDED+": 新增</html>";
	}
}