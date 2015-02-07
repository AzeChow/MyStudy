package com.bestway.client.custom.common.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.bestway.bcus.client.manualdeclare.report.ManualdeclareReportVars;
import com.bestway.client.custom.common.DgPrintInfo;

public class ReportUtil {
	
	private static ReportUtil	c						= null;
	
	public static ReportUtil getInstance() {
		if (c == null) {
			c = new ReportUtil();
		}
		return c;
	}

	public String formatDouble(String str,int num){
		if(str==null || "".equals(str)){
			return "";
		}
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		format.setMaximumFractionDigits(num);
		try {
			Double myD = Double.parseDouble(str);
			return format.format(myD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
	
	public Properties read() throws Exception{
		File file = new File("../commercialInvoiceOfOut.properties");
		JOptionPane.showMessageDialog(null, file.getAbsoluteFile());
		InputStream in = ReportUtil.class.getResourceAsStream("../commercialInvoiceOfOut.properties");
		Properties pro = new Properties();
		pro.load(in);
		return pro;
	}
	
	public void write(Properties pro) throws Exception{
		File file = new File(ReportUtil.class.getResource("../commercialInvoiceOfOut.properties").toURI());
		JOptionPane.showMessageDialog(null, file.getAbsoluteFile());
		FileOutputStream out = new FileOutputStream(file);
		pro.store(out, "");
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ReportUtil u = ReportUtil.getInstance();
		Properties pro = u.read();
		System.out.println(pro.get("invoiceNo"));
		
		pro.put("invoiceNo", "chensir");
		u.write(pro);
	}

}
