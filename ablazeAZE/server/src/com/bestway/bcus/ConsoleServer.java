/*
 * Created on 2004-6-18
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus;

import java.io.IOException;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Administrator
 * 
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConsoleServer
{

	public static void startServer()
	{
		byte[] r = new byte[50];
		String s = "";
		System.out.println("系统启动BCUS应用服务器");
		System.out.println();
		System.out.println("BCUS应用服务器启动时间为：" + (new Date()).toString());
		System.out.println("如果要退出服务器请输入：Quit,然后按Enter键");
		try
		{
			System.in.read(r);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		s = new String(r);
		while (!s.trim().toLowerCase().equals("quit"))
		{
			try
			{
				System.in.read(r);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			s = new String(r);
		}
		System.exit(0);
	}



	public static void main(String[] args)
	{
		String[] contexts = new String[]
		{"CommonContext.xml", "AuthorityContext.xml", "SystemContext.xml",
				"CustomBaseContext.xml"
		//		  "InnerMergeContext.xml"
		//		  "ManualDeclareContext.xml",
		//		  "OnlineCustomContext.xml",
		//		  "VerifyCancelContext.xml",
		//		  "TransFactoryContext.xml",
		//		  "ConsignProcessContext.xml",
		//		  "CustomAccountContext.xml",
		//		  "MaterialInfoContext.xml"
		};
		new ClassPathXmlApplicationContext(contexts);
		//CommonUtil.setContext(getContext());
		ConsoleServer.startServer();

	}

}