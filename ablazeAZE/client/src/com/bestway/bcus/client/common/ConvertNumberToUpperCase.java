/*
 * Created on 2004-9-2
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;


public class ConvertNumberToUpperCase
{
//	加到类的定义部分
	private static String[] cstr={"零","壹","贰","叁","肆", "伍", "陆","柒","捌","玖"};
	private  static String[] wstr={"","","拾","佰","仟","萬","拾","佰","仟","億","拾","佰","仟"};
	private  static String[] zstr={"角","分"};
//	数字必须在12位整数以内的字符串
//	调用方式如：Label1.Text=ConvertInt("数字字符串");

	public static String convert(String str){
		if(str.trim().equals("")){
			return "";
		}
		if(str.lastIndexOf(".") != -1){
			str = str.substring(0,str.lastIndexOf("."));
		}		 
		int len=str.length();
		int i;
		String tmpstr = "";
		String rstr="";
		for(i=1;i<=len;i++){
	   		tmpstr=str.substring(len-i,len-i+1);
	   		rstr= cstr[Integer.parseInt(tmpstr)]+wstr[i] + rstr;
		}
		rstr=rstr.replace("拾零","拾");
		rstr=rstr.replace("零拾","零");
	    rstr=rstr.replace("零佰","零");
	    rstr=rstr.replace("零仟","零");
	    rstr=rstr.replace("零萬","萬");
	    for(i=1;i<=6;i++){
	    	rstr=rstr.replace("零零","零");
	    }
	    rstr=rstr.replace("零萬","零");
	    rstr=rstr.replace("零億","億");
	    rstr=rstr.replace("零零","零"); 
	    rstr+="圆整";  
	    return rstr;
	}
	
	
	
	public static String convertInteger(String str){
		if(str.trim().equals("")){
			return "";
		}
		if(str.lastIndexOf(".") != -1){
			str = str.substring(0,str.lastIndexOf("."));
		}		 
		int len=str.length();
		int i;
		String tmpstr = "";
		String rstr="";
		for(i=1;i<=len;i++){
	   		tmpstr=str.substring(len-i,len-i+1);
	   		rstr= cstr[Integer.parseInt(tmpstr)]+wstr[i] + rstr;
		}
		rstr=rstr.replace("拾零","拾");
		rstr=rstr.replace("零拾","零");
	    rstr=rstr.replace("零佰","零");
	    rstr=rstr.replace("零仟","零");
	    rstr=rstr.replace("零萬","萬");
	    for(i=1;i<=6;i++){
	    	rstr=rstr.replace("零零","零");
	    }
	    rstr=rstr.replace("零萬","零");
	    rstr=rstr.replace("零億","億");
	    rstr=rstr.replace("零零","零"); 
	    return rstr;
	}
	
	public static String convertd(String str){
		if(str.trim().equals("")){
			return "";
		}
		String digstr = null;
		if(str.lastIndexOf(".") != -1){
			digstr = str.substring(str.lastIndexOf(".")+1,str.length());
			str = str.substring(0,str.lastIndexOf("."));			
		}		 
		int len=str.length();
		int i;
		String tmpstr = "";
		String rstr="";
		for(i=1;i<=len;i++){
	   		tmpstr=str.substring(len-i,len-i+1);
	   		rstr= cstr[Integer.parseInt(tmpstr)]+wstr[i] + rstr;
		}
		rstr=rstr.replace("拾零","拾");
		rstr=rstr.replace("零拾","零");
	    rstr=rstr.replace("零佰","零");
	    rstr=rstr.replace("零仟","零");
	    rstr=rstr.replace("零萬","萬");
	    for(i=1;i<=6;i++){
	    	rstr=rstr.replace("零零","零");
	    }
	    rstr=rstr.replace("零萬","零");
	    rstr=rstr.replace("零億","億");
	    rstr=rstr.replace("零零","零"); 
	    rstr+="圆"; 
	    
	    String tempstr = "";
	    String rstrs = "";
	    if (digstr == null){
	    	return rstr+"整";
	    }
	    int len1=digstr.length();
		for(int j=0;j<=len1-1;j++){
			tempstr=digstr.substring(j,j+1);
	   		rstrs = rstrs + cstr[Integer.parseInt(tempstr)]+zstr[j];
		}
	    return rstr + rstrs + "整";
	}
	
	public static void main(String[] args) {
		System.out.println(convertd("123.23"));
	}
}
