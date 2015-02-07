package com.bestway.common;

import java.util.Hashtable;

public class Context
{

	private Context()
	{
	}



	private static Context	context	= new Context();



	public Context getInstance()
	{
		return context;
	}



	private Hashtable	extendedProperties	= null;

	private UserInfo	userInfo			= null;



	public void setUserInfo(UserInfo userInfo)
	{
		this.userInfo = userInfo;
	}



	public UserInfo getUserInfo()
	{
		return userInfo;
	}

}