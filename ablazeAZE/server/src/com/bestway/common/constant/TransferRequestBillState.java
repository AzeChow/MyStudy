/*
 * Created on 2004-7-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * 0：原始状态，1：等待审批，2：正在执行，3：变更，4：退单
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TransferRequestBillState implements Serializable {
	/**原始状态*/
	public static final Integer ORIGINAL = 0;
	/**等待审批*/
	public static final Integer APPLY = 1; 
	/**正在执行*/
	public static final Integer  EXECUTE= 2;
	/**变更*/
	public static final Integer CHANGE= 3; 
	/**退单*/
	public static final Integer BACK_BILL = 4;
	
	
	public static final String getNote(int value) {
		switch (value) {
		case 0:
			return "原始状态";
		case 1:
			return "等待审批";
		case 2:
			return "正在执行";
		case 3:
			return "变更";
		case 4:
			return "退单";
		}
		return "";
	}
	
	
	
}
