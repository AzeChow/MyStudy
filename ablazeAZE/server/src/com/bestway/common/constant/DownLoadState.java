package com.bestway.common.constant;

import java.io.Serializable;

public class DownLoadState implements Serializable 
{
	/**
	 *  A申请表
	 */
	public static final String FPT_APP = "A";
	
	/**
	 *  B退货单
	 */
	public static final String FPT_BILL_BACK = "B";
	
	/**
	 * 收发货单
	 * 
	 */
	public static final String FPT_BILL = "C";
	
	public static final String getDownLoadStateSpec(String value) 
	{
		String returnValue = "";
		if(DownLoadState.FPT_APP.equals(value))
		{
			returnValue = "结转申请表";
		} 
		else if(DownLoadState.FPT_BILL_BACK.equals(value)) 
		{
			returnValue = "退货单";
		} 
		else if(DownLoadState.FPT_BILL.equals(value)) 
		{
			returnValue = "收发货单";
		} 
		return returnValue;
	}
}
