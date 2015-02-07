/*
 * Created on 2004-11-10
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckableItem
{
	private String	code;
	private String  tableName;
	boolean	isSelected;
	
	public CheckableItem(String str,String tableName)
	{
		this.code = str;
		this.tableName = tableName;
		isSelected = false;
	}
	public void setSelected(boolean b)
	{
		isSelected = b;
	}
	public boolean isSelected()
	{
		return isSelected;
	}	
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
	