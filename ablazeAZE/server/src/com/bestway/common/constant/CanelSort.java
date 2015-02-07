package com.bestway.common.constant;

import java.io.Serializable;

public class CanelSort implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 *  撤消发货单
	 */
	public static final String canelInvoice = "0";
	
	/**
	 *  撤消退货单
	 */
	public static final String returnForm = "1";
	
	public static final String getCanelSortState(String value) 
	{
		String returnValue = "";
		if(CanelSort.canelInvoice.equals(value))
		{
			returnValue = "撤消发货单";
		} 
		else if(CanelSort.returnForm.equals(value)) 
		{
			returnValue = "撤消退货单";
		} 
		return returnValue;
	}
        
}
