/*
 * Created on 2004-7-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeclareState implements Serializable 
{
	/**
	 * 申请备案[草稿状态]
	 */
	public static final String APPLY_POR = "1"; // 申请备案

	/**
	 * 等待审批
	 */
	public static final String WAIT_EAA = "2"; // 等待审批

	/**
	 * 正在执行
	 */
	public static final String PROCESS_EXE = "3"; // 正在执行

	/**
	 * 正在变更
	 */
	public static final String CHANGING_EXE = "4"; // 正在变更
	
	/**
	 * 正在变更
	 */
//	public static final String PROCESS_ALL = "6"; // 全部

	/**
	 * 已经核销
	 */
	public static final String CHANGING_CANCEL = "5"; // 已经核销

	/**
	 * 已经退单
	 */
	public static final String BACK_BILL = "6"; // 退单状态
	
	/**
	 * 已撤销（结转收发货单需要）
	 */
	public static final String IS_CANCELED="S";//已撤销
	
	/**
	 * 已撤销（结转收发货单需要）
	 */
	public static final String IS_CAN="C";//作废

	public static final String getDeclareStateSpec(String value) {
		String returnValue = "";
		if (DeclareState.APPLY_POR.equals(value)) {
			returnValue = "初始状态";
		} else if (DeclareState.WAIT_EAA.equals(value)) {
			returnValue = "等待审批";
		} else if (DeclareState.PROCESS_EXE.equals(value)) {
			returnValue = "正在执行";
		} else if (DeclareState.CHANGING_EXE.equals(value)) {
			returnValue = "正在变更";
		} else if (DeclareState.CHANGING_CANCEL.equals(value)) {
			returnValue = "已经核销";
		}else if (DeclareState.IS_CANCELED.equals(value)) {
			returnValue = "已撤销";
		}else if (DeclareState.IS_CAN.equals(value)) {
			returnValue = "作废";
		}
		
//		else if (DeclareState.PROCESS_ALL.equals(value)) {
//			returnValue = "全部";
//		}
//		else if (DeclareState.BACK_BILL.equals(value)) {
//			returnValue = "退单";
//		}
		return returnValue;
	}

}
