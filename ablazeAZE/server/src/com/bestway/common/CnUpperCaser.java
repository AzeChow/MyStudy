package com.bestway.common;

import java.util.ArrayList;
import java.util.List;

public class CnUpperCaser {
	// 整数部分
	  private String integerPart;
	  // 小数部分
	  private String floatPart;
	  
	  // 将数字转化为汉字的数组,因为各个实例都要使用所以设为静态
	  private static final char[] cnNumbers={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};
	  
	  // 供分级转化的数组,因为各个实例都要使用所以设为静态
	  private static final char[] series={'亿','万'};
	  
	  private static final char[] fourSeries = {' ','拾','佰','仟'};
	  
	  private static final char[] floatSeries = {'角','分'};
	  
	  /**
	   * 构造函数,通过它将阿拉伯数字形式的字符串传入
	   * @param original
	   */
	  public CnUpperCaser(String original){ 
	    // 成员变量初始化
	    integerPart="";
	    floatPart="";
	    
	    if(original.contains(".")){
	      // 如果包含小数点
	      int dotIndex=original.indexOf(".");
	      integerPart=original.substring(0,dotIndex);
	      floatPart=original.substring(dotIndex+1);
	    }
	    else{
	      // 不包含小数点
	      integerPart=original;
	    }
	  }
	  
	  /**
	   * 取得大写形式的字符串
	   * @return
	   */
	  public String getCnString(){
	    // 因为是累加所以用StringBuffer
	    StringBuffer sb=new StringBuffer();
	    
	    // 整数部分处理
	    //总长度
	    int len = integerPart.length();
	    //分组
	    int group = len / 4 +(len%4==0?0:1);
	    
	    int firstSize = len %4;
	    
	    String[] nums  = new String[group];
	    
	    for(int g=0;g<group;g++){
	    	if(firstSize==0){
	    		nums[g] = integerPart.substring(g*4, (g+1)*4);
	    	}else{
	    		if(g==0){
	    			nums[g] = integerPart.substring(0, firstSize);
	    		}else{
	    			nums[g] = integerPart.substring((g-1)*4+firstSize, g*4+firstSize);
	    		}
	    	}
	    }
	    
//	    for(int i=0;i<group;i++){
//	    	System.out.println(nums[i]);
//	    }
	    //解析
	    String temp = "";
	    int num = 0;
	    boolean addZero=false;
	    boolean firstZero = false;
	    boolean addGroup = false;
	    for(int g=0;g<group;g++){
	    	temp = nums[g];
	    	addGroup = false;
	    	addZero=false;
	    	if(g==0){
	    		firstZero = false;
	    	}else{
	    		firstZero = true;
	    	}
	    	for(int i=0;i<temp.length();i++){
	    		num = getNumber(temp.charAt(i));
	    		if(num==0){
	    			if(firstZero){
	    				addZero = true;
	    			}else{
	    				addZero = true;
	    			}
	    		}else{
	    			if(addZero){
		    			sb.append("零");
		    		}
		    		sb.append(""+cnNumbers[num]+((temp.length()-i-1)==0?"":fourSeries[temp.length()-i-1]));
		    		firstZero = false;
		    		addZero = false;
		    		addGroup = true;
	    		}
	    	}
	    	//加万，亿
	    	if(g==group-1 || !addGroup)
	    		break;
	    	if(group%2==0){
	    		if(g%2==0){
	    			sb.append(series[1]);
	    		}else{
	    			sb.append(series[0]);
	    		}
	    	}else{
	    		if(g%2==0){
	    			sb.append(series[0]);
	    		}else{
	    			sb.append(series[1]);
	    		}
	    	}
	    }
	    
//	    for(int i=0;i<integerPart.length();i++){
//	      int number=getNumber(integerPart.charAt(i));
//	      if(number!=0){
//	    	  sb.append(cnNumbers[number]);
//	    	  if(i!=integerPart.length()-1){
//		      sb.append(series[integerPart.length()-1-i]);
//	    	  }
//	      }else if((i==integerPart.length()-2) && (getNumber(integerPart.charAt(i+1))!=0)){
//	    	  sb.append("零");
//	      }
//	    
//	    }
	    
	    // 小数部分处理
	    if(floatPart.length()>0){
	      sb.append("元");
	      for(int i=0;i<floatPart.length()&&i<2;i++){
	        int number=getNumber(floatPart.charAt(i));
	        if(number!=0){
	          sb.append(cnNumbers[number]);
	          sb.append(floatSeries[i]);
	        }
	      }
	    }else{
	    	sb.append("元整");
	    }
//	    
	    // 返回拼接好的字符串
	    return sb.toString();
	  }
	  
	  /**
	   * 将字符形式的数字转化为整形数字
	   * 因为所有实例都要用到所以用静态修饰
	   * @param c
	   * @return
	   */
	  private static int getNumber(char c){
	    String str=String.valueOf(c);   
	    return Integer.parseInt(str);
	  }
	  
	  /**
	   * @param args
	   */
	  public static void main(String[] args) {
		  //小于四位
//	     System.out.println("小于四位");
	     String str = new CnUpperCaser("1234").getCnString();
//	     System.out.println("1234  测试结果："+"壹仟贰佰叁拾肆元整".equals(str)+"---------"+str);
//	     
//	     str = new CnUpperCaser("1234.23").getCnString();
//	     System.out.println("1234.23  测试结果："+"壹仟贰佰叁拾肆元贰角叁分".equals(str)+"---------"+str);
//	     
//	     str = new CnUpperCaser("1004.23").getCnString();
//	     System.out.println("1004.23  测试结果："+"壹仟零肆元贰角叁分".equals(str)+"---------"+str);
//	     
//	     str=new CnUpperCaser("204.1").getCnString(); 
//	     System.out.println("204.1  测试结果："+"贰佰零肆元壹角".equals(str)+"---------"+str);
//		  //四位到八位
//	     System.out.println("四位到八位");
//	     str=new CnUpperCaser("10204.1").getCnString(); 
//	     System.out.println("10204.1  测试结果："+"壹万零贰佰零肆元壹角".equals(str)+"---------"+str);
//	     
//	     str=new CnUpperCaser("101001").getCnString(); 
//	     System.out.println("101001  测试结果："+"壹拾万壹仟零壹元整".equals(str)+"---------"+str);
//	     
//	     str=new CnUpperCaser("12341204.1").getCnString(); 
//	     System.out.println("12341204.1  测试结果："+"壹仟贰佰叁拾肆万壹仟贰佰零肆元壹角".equals(str)+"---------"+str);
//	     
//	     str=new CnUpperCaser("12340204.1").getCnString(); 
//	     System.out.println("12340204.1  测试结果："+"壹仟贰佰叁拾肆万零贰佰零肆元壹角".equals(str)+"---------"+str);
//		  //八位到十二位
//	     System.out.println("八位到十二位");
//	     str=new CnUpperCaser("123412340204.1").getCnString(); 
//	     System.out.println("123412340204.1  测试结果："+"壹仟贰佰叁拾肆亿壹仟贰佰叁拾肆万零贰佰零肆元壹角".equals(str)+"---------"+str);
//	     
//	     str=new CnUpperCaser("100412340204.1").getCnString(); 
//	     System.out.println("100412340204.1  测试结果："+"壹仟零肆亿壹仟贰佰叁拾肆万零贰佰零肆元壹角".equals(str)+"---------"+str);
//	     
//	     str=new CnUpperCaser("100412341204.1").getCnString(); 
//	     System.out.println("100412341204.1  测试结果："+"壹仟零肆亿壹仟贰佰叁拾肆万壹仟贰佰零肆元壹角".equals(str)+"---------"+str);
//	     
//	     str=new CnUpperCaser("100000000.1").getCnString(); 
//	     System.out.println("100000000.1  测试结果："+"壹亿元壹角".equals(str)+"---------"+str);
//		  //十二位到十六位
//	     str=new CnUpperCaser("10100100000000.1").getCnString(); 
//	     System.out.println("10100100000000.1  测试结果："+"壹拾万壹仟零壹亿元壹角".equals(str)+"---------"+str);
//	     
//	     str=new CnUpperCaser("50054285100100000000.1").getCnString(); 
//	     System.out.println("50054285100100000000.1"+str);
		  
		  str=new CnUpperCaser("8036050").getCnString(); 
		     System.out.println("8036050"+str);
	  }
}
