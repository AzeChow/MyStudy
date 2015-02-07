/*
 * Created on 2005-5-15
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.authority;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class AuthorityLog {
	private static String logLocation;

	public static String getLogLocation() {
		return logLocation;
	}

	public static void setLogLocation(String logLocation) {
		AuthorityLog.logLocation = logLocation;
	}

	/**
	 * 
	 */
	public AuthorityLog() {
		super();
	}

	/**
	 * 调用方法日志
	 * 
	 * @param info
	 * @param log
	 */
	public synchronized static void outinfo(String info) {
		if (info == null || "".equals(info.trim())) {
			return;
		}
		loginfo(info);
	}

	private static void loginfo(String info) {
		if (logLocation == null || "".equals(logLocation.trim())) {
			return;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = logLocation + File.separator
				+ dateFormat.format(new Date()) + ".log";
		File file = new File(fileName);
		Document doc = null;
		Element root =null;
		if (!file.exists()) {
			try {
				file.createNewFile();
				root=new Element("AuthorityLog");
				doc = new Document(root);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			SAXBuilder sb = new SAXBuilder();
			FileReader fileReader = null;
			try {
				fileReader = new FileReader(file);
				doc = sb.build(fileReader);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fileReader != null) {
					try {
						fileReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			root = doc.getRootElement();
		}
		Element logInfo = new Element("LogInfo");
		String[] infos = info.split("\n");
		for (int i = 0; i < infos.length; i++) {
			Pattern pattern = Pattern.compile("<(.*?)>(.*?)<");
			Matcher matcher = pattern.matcher(infos[i]);
			if (matcher.find()) {
				Element element = new Element(replaceSpecialChar(matcher.group(
						1).trim())).setText(replaceSpecialChar(matcher.group(2)
						.trim()));
				logInfo.addContent(element);
			}
		}
		root.addContent(logInfo);
		XMLOutputter fmt = new XMLOutputter("",false,"GBK");//" ",false,"UTF-8"
		try {
			FileWriter writer = new FileWriter(file);
			// Format f = Format.getPrettyFormat();
			// f.setEncoding("GB2312");
			// fmt.setFormat(f);
			fmt.output(doc, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String replaceSpecialChar(String str) {
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
				">", "&gt;").replaceAll("\"", "&quot;");
	}

}
