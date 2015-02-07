package com.bestway.dzsc.client.innermerge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import com.bestway.common.CommonUtils;

public class Test {
	public static void main(String[] args){
//		File file = new File("C:\\test\\test.txt");
//		try {
//			BufferedReader in = new BufferedReader(new FileReader(file));
//			String s = "";
//			while((s = in.readLine())!= null){
//				String[] strs = s.split(String.valueOf((char) 9));
//				for(int i = 0;i<strs.length;i++){
//					System.out.println(strs[i]);
//				}
//			}
//			double a = 199.0001;
//			double b = 200;
//			BigDecimal biga = new BigDecimal(a*Math.pow(10.0,16));
//			BigDecimal bigb = new BigDecimal(b*Math.pow(10.0,16));
//			System.out.println(biga);
//			System.out.println(bigb.subtract(biga).movePointLeft(16).doubleValue());
//			//System.out.println(new String("修改".getBytes("GBK"),"UTF-8"));
//			System.out.println(formatDouble(new Double(100000)));
//		} catch (IOException e3) {
//			e3.printStackTrace();
//		}
//		String str = "dasf期初sdaf sdafas";
//		if(str.contains("期初")){
//			System.out.println("包含期初");
//		}
//		Calendar cal = Calendar.getInstance();
//		String curDate = ""+cal.get(Calendar.YEAR)+"-";
//		if((cal.get(Calendar.MONTH)+1)<10){
//			curDate += "0"+(cal.get(Calendar.MONTH)+1)+"-";
//		}else{
//			curDate += ""+(cal.get(Calendar.MONTH)+1)+"-";
//		}
//		if((cal.get(Calendar.DATE))<10){
//			curDate += "0"+cal.get(Calendar.DATE);
//		}else{
//			curDate += ""+cal.get(Calendar.DATE);
//		}
//		Date date = new Date();
//		date.setMonth(0);
//		date.setDate(1);
//		System.out.println(date);
//		
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.YEAR, Integer
//				.valueOf(CommonUtils.getYear()));
//		calendar.set(Calendar.MONTH, 0);
//		calendar.set(Calendar.DATE, 1);
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		System.out.println("enddate:"+calendar.getTime());
		List<List> list = new ArrayList<List>();
		Workbook workbook = null;
		try {

			WorkbookSettings wbs = new WorkbookSettings();
			//
			// excel is default ISO-8859-1 encoding
			// setEncoding("UTF-8"); utf-8 no support in excel
			//
			workbook = Workbook.getWorkbook(new File("C:\\test\\test.xls"), wbs);
			//
			// ��ȡ�Sheet�� default 0
			//
			Sheet sheet = workbook.getSheet(0);
			Cell cell = sheet.getCell(0, 0);
			if(cell.getType()==CellType.LABEL){
				System.out.println(cell.getContents().toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private static Double formatDouble(Double value){
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String tmp = "";
		try{
			if(value!=null){
				tmp = df.format(value.doubleValue());
				return new Double(tmp);
			}else{
				return new Double(0.00);
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Double(0.00);
		}
	}
	
	private void connectDb(){
		try {
			Class.forName("");
			try {
				Connection con = DriverManager.getConnection("", "", "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
