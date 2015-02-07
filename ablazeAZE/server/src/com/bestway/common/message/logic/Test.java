package com.bestway.common.message.logic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// File file=new File("F:/3.FPT");
		File file = new File("F:/1.FPT");
		// File file=new
		// File("D:/集成通/回执/FILE_SEND_20130529_114343_875(QTSA.SEND.REMOTE.DB)报关单放行.xml");
		String fileEncode = EncodingDetect.getJavaEncode(file.getPath());
		String fileContent = null;
		try {
			fileContent = FileUtils.readFileToString(file, fileEncode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(fileContent);
		
		byte[] b;
		try {
			b = fileContent.getBytes(fileEncode);
			// System.out.println(b[0] == (byte) 0xEF);
			// System.out.println(b[1] == (byte) 0xBB);
			// System.out.println(b[2] == (byte) 0xBF);
			if (b[0] == (byte) 0xEF && b[1] == (byte) 0xBB
					&& b[2] == (byte) 0xBF) {
				fileContent = new String(b, 3, b.length - 3, fileEncode);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String regEx = "encoding=\"(.*)\"";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(fileContent);
		String xmlEncode = "GB2312";
		boolean rs = mat.find();
		if (rs) {
			String temp = (mat.group(1) == null ? "" : mat.group(1).toString()
					.trim());
			if (!"".equals(temp)) {
				xmlEncode = temp;
			}
			System.out.println(xmlEncode);
		}
		
		InputStream messageStream;
		try {
			messageStream = new ByteArrayInputStream(
					fileContent.getBytes(xmlEncode));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		SAXBuilder sax = new SAXBuilder();
		Document doc;
		try {
			doc = sax.build(messageStream);
			Element root = doc.getRootElement();
			// System.out.println(root.getChild("FPT_DOWN_DATA").getChild("FPT_BILL_HEAD").getChild("OUT_TRADE_NAME").getTextTrim());
			// CspReceiptResult result = this.readReceiptResultHead(root,
			// businessType, copEmsNo);
			// if (result == null) {
			// continue;
			// }
			// result.setNoticeDate(new Date(files[i].lastModified()));
			// result.setFileName(files[i].getName());
			// result.setFile(files[i]);
			// result.setIsSelected(true);
			// result.setReceiptResultDetailList(this
			// .readReceiptResultDetail(root));
			// lsResult.add(result);
		} catch (Exception e) {
			// throw new RuntimeException(e.getMessage());
			e.printStackTrace();
			System.out.println(file.getName() + "不是一个有效的XML格式文件！");
		} finally {
			try {
				messageStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
