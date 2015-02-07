package com.bestway.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class CspZIPUtil {

	public static void createZIPFile(String fileName, String fileContent) {
		String tager = "";
		int index = fileName.lastIndexOf(File.separator);
		if (index >= 0) {
			tager = fileName.substring(index + 1);
		} else {
			index = fileName.lastIndexOf("/");
			if (index >= 0) {
				tager = fileName.substring(index + 1);
			}
		}
		index = tager.lastIndexOf(".");
		if (index > 0) {
			tager = tager.substring(0, index);
		}
		ZipOutputStream zos;
		try {
			zos = new ZipOutputStream(new FileOutputStream(new File(fileName)));
			ZipEntry ze = new ZipEntry(tager);
			zos.putNextEntry(ze);
			zos.write(fileContent.getBytes("utf-8"));
			zos.flush();
			zos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String readZIPFile(String fileName) {
		String fileContent = readZIPFile(new File(fileName));
		// System.out.println("fileContent:" + fileContent);
		return fileContent;
	}

	public static String readZIPFile(File file) {

		// 创建压缩文件对象
		ZipFile zf;

		try {
			zf = new ZipFile(file);
			// 获取压缩文件中的文件枚举
			Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zf.entries();

			int length = 0;

			byte[] b = new byte[2048];

			// 提取压缩文件夹中的所有压缩实例对象
			if (en.hasMoreElements()) {

				ZipEntry ze = en.nextElement();

				ByteArrayOutputStream out = new ByteArrayOutputStream();

				InputStream inputStream = zf.getInputStream(ze);

				while ((length = inputStream.read(b)) > 0)

					out.write(b, 0, length);

				String str = new String(out.toByteArray(), "utf-8");//

				inputStream.close();

				out.close();

				return str;
			}
			zf.close();
		} catch (ZipException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		String fileName = "D:/Projects/jbcus/workspace/jbcus3/CustomsBaseData/Unit.zip";
		// String fileName = "D:/11111111.zip";
		StringBuffer stem = new StringBuffer();
		stem.append("<Students>");
		for (int i = 0; i < 10; i++) {
			stem.append("  <Student>\n");
			stem.append("    <code>" + i + "</code>\n");
			stem.append("    <name>" + "东莞市百思维软件有限公司" + i + "</name>\n");
			stem.append("  </Student>\n");
		}
		stem.append("</Students>\n");
		CspZIPUtil.createZIPFile(fileName, stem.toString());
		System.out.println(CspZIPUtil.readZIPFile(new File(fileName)));

		String content = CspZIPUtil.readZIPFile(fileName);
		// System.out.println("fileContent:" + content);
		// try {
		// Element root =
		// DocumentHelper.parseText(content.trim()).getRootElement();//.replaceAll("[^\\x20-\\x7e]",
		// "")
		// } catch (DocumentException e) {
		// e.printStackTrace();
		// }
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(
					content.getBytes("utf-8"));
			doc = sb.build(inputStream);
			Element root = doc.getRootElement();
			System.out.println("11111111:" + root.getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// SAXBuilder sb = new SAXBuilder();
		// Document doc = null;
		// try {
		// // doc = sb.build(new FileInputStream(new File(fileName)));
		// doc=sb.bu
		// Element root = doc.getRootElement();
		// List listRow = root.getChildren("row");
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
	}

	/**
	 * 第一个参数是压缩文件路径，第二个参数是要解压的文件夹路径，文件夹可以不存在会自动生成(由调用此方法的类产生)
	 * 
	 * 解压文件
	 */
	public static void zipDecompress(String frompath, String topath)
			throws IOException {

		File from = new File(frompath);

		ZipFile zf = new ZipFile(from);

		InputStream inputStream;

		Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zf.entries();

		while (en.hasMoreElements()) {

			ZipEntry zn = (ZipEntry) en.nextElement();

			if (!zn.isDirectory()) {

				inputStream = zf.getInputStream(zn);

				File f = new File(topath + zn.getName());

				File file = f.getParentFile();

				file.mkdirs();

				System.out.println(zn.getName() + "---" + zn.getSize());

				// 解压到....
				FileOutputStream outputStream = new FileOutputStream(topath
						+ zn.getName());

				int len = 0;

				byte bufer[] = new byte[1024];

				while (-1 != (len = inputStream.read(bufer))) {

					outputStream.write(bufer, 0, len);

				}

				outputStream.close();

			}
		}
	}

}
