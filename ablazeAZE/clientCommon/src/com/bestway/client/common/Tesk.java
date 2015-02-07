package com.bestway.client.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Tesk {
	private double dd = 0.0000002;

	private double dd1 = 0.0000001;

	public static void main(String[] args) {
		// Integer.valueOf(null);
		// System.out.println("");
		Tesk tesk = new Tesk();
		tesk.ltest();
		// tesk.test();
		// tesk.createTable();/
		// Integer.valueOf(null);
		// tesk.test001();
		// JOptionPane.showInternalOptionDialog(null,"eee","fff",);
	}

	private void ltest() {

		NumberFormat formatter = NumberFormat.getInstance();

		// Object[] message = new Object[4];
		// message[0] = "OptionPaneDemo.componentmessage";
		// message[1] = new JTextField("OptionPaneDemo.componenttextfield");
		//
		// JComboBox cb = new JComboBox();
		// cb.addItem("OptionPaneDemo.component_cb1");
		// cb.addItem("OptionPaneDemo.component_cb2");
		// cb.addItem("OptionPaneDemo.component_cb3");
		// message[2] = cb;
		//
		// message[3] = "OptionPaneDemo.componentmessage2";
		// int ms= JOptionPane.showInternalOptionDialog(new
		// JFrame(),message,"fff", JOptionPane.DEFAULT_OPTION, // option type
		// JOptionPane.INFORMATION_MESSAGE, null,new Object[]{"1","2"},"");
		// System.out.println(ms);
	}

	public void test001() {
		String str = "k244开发部KPI计划";
		String tstr;
		try {
			FileOutputStream fw = new FileOutputStream("C:\\aa.txt");
			tstr = new String(str.getBytes("GB2312"), "GB2312");
			fw.write(tstr.getBytes("GB2312"));
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createTable() {
		File f = new File("C://test.html");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			f.delete();
		}
		FileWriter fos = null;
		try {
			fos = new FileWriter(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringBuffer bf = new StringBuffer();
		for (int m = 0; m < 3000; m++) {
			bf.append(" <tr> ");
			for (int k = 0; k < 14; k++) {
				bf
						.append("  <td width=\"6%\" align=\"center\">" + k
								+ "</td> ");
			}
			bf.append(" <tr> ");
		}
		// BufferedReader br=new BufferedReader(new Reader(bf.));
		String str = bf.toString();
		try {
			fos.write(str, 0, str.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void test() {

		Double dou = new Double(8888888888.0111);
		DecimalFormat format = new DecimalFormat();
		format.setGroupingSize(999);
		format.setMaximumFractionDigits(999);
		// DecimalFormat df = new DecimalFormat("####.*");
		System.out.println(format.format(dou) + " fffff");
		String kcvb = new String("柯常柏哦");
		try {
			System.out.println(kcvb.getBytes("GB2312").length);
			System.out.println(kcvb.getBytes("GBK").length);
			System.out.println(kcvb.getBytes("ISO-8859-1").length);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(kcvb.getBytes().length);
		// List list=new ArrayList();
		// list.add("a");
		// String ss=new String ("a");
		// if(list.contains(ss)){
		// System.out.println("ggg");
		// }else{
		// System.out.println("fff");
		// }
		// List<List> lines = new ArrayList<List>();
		// File file = new File("c:/aa.xls");
		// //
		// // 导入xls
		// //
		// // lines = FileReading.readExcel(file, 0, null);
		// for (int i = 0; i < lines.size(); i++) {
		// List dlist = lines.get(i);
		// for (int j = 0; j < dlist.size(); j++) {
		// System.out.println(dlist.get(j));
		// }
		// }
		// Double dous = Double.valueOf("0.3333568533356898985");
		// System.out.println(dous);
		// if ("".equals(null)) {
		// System.out.println("aaaaaaa");
		// } else {
		// System.out.println("cccccccccc");
		// }
		// Double value = Double.valueOf("0.88888");
		// // value.
		// // String test = String.valueOf(value);
		// BigDecimal bd = new BigDecimal(String.valueOf(value.doubleValue()));
		// // int count1 = test.length();
		// // int count2 = test.indexOf(".") + 1;
		// // int margin = count1 - count2;
		// int margin = bd.scale();
		// if (margin > 5) {
		// System.out.println(margin);
		// System.out.println("kkkkkkkkkkkkk");
		// } else {
		// System.out.println("mmmmmmmmmmmmm");
		// }
		// Double values = Double.valueOf("2");
		// String test = String.valueOf(values);
		// System.out.println(test);
		// String[] strs = test.split("\\.");
		// System.out.println(strs.length);
		// System.out.println(strs[0]);
		// System.out.println(strs[1]);

	}

	private void tests() {
		Double d = 7.0;
		for (int i = 0; i < 60; i++) {
			System.out.println(getDoubleByDigit(i / d, 5));
		}
		// System.out.println(new Date().toGMTString());
		// System.out.println(new Long(new Date().getTime()).toString());
	}

	public static Double getDoubleByDigit(Double sourceDouble, int n) {
		if (sourceDouble == null) {
			return 0.0;
		}
		BigDecimal b = new BigDecimal(sourceDouble);
		return b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
