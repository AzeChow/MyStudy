package com.bestway.bcus.client.cas.otherreport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test {
	public static void main(String[] args) throws ParseException{
		String name = "SBC";
		String Spec = "";
		String Unit = "个";
		String NSU = (name==null?"":name) +
				     (Spec==null?"":Spec) +
				     (Unit==null?"":Unit);
		System.out.println(NSU);
		String d = "7-30-07";
		int pos = d.indexOf("-");
		if(pos==2){
			
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new Date();
		
		//d = sdf.format(d);
		
		System.out.println("date is "+sdf.parse(d).toString());
		try{
			Integer.valueOf(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH));
	}
}
