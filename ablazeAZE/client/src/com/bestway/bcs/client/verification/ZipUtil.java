package com.bestway.bcs.client.verification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	/**
	 * 压缩目录下的文件
	 * 
	 * @param zipFileName
	 * @param inputFile
	 *            目录
	 * @throws java.lang.Exception
	 */
	public void zip(String zipFileName, String inputFile) throws Exception {
		zip(zipFileName, new File(inputFile));
	}

	/**
	 * 压缩文件
	 * 
	 * @param zipFileName
	 * @param inputFile
	 *            文件
	 * @throws java.lang.Exception
	 */
	public void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		zip(out, inputFile, "");
		System.out.println("压缩成功!");
		out.close();
	}

	public void zip(ZipOutputStream out, File f, String base) throws Exception {
		System.out.println("正在压缩  " + f.getName());
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1)
				out.write(b);
			in.close();
		}

	}

	/**
	 * 解压缩
	 * 
	 * @param zipFileName
	 * @param outputDirectory
	 *            输出目录
	 * @throws java.lang.Exception
	 */
	public void unzip(String zipFileName, String outputDirectory)
			throws Exception {
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
		ZipEntry z;
		while ((z = in.getNextEntry()) != null) {
			System.out.println("正在解压 " + z.getName());
			if (z.isDirectory()) {
				String name = z.getName();
				name = name.substring(0, name.length() - 1);
				File f = new File(outputDirectory + File.separator + name);
				f.mkdir();
				System.out.println("创建目录 " + outputDirectory + File.separator
						+ name);
			} else {
				File f = new File(outputDirectory + File.separator
						+ z.getName());
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				int b;
				while ((b = in.read()) != -1)
					out.write(b);
				out.flush();
				out.close();
			}
		}

		in.close();
	}

	public static void main(String[] args) {
		try {
			ZipUtil t = new ZipUtil();
//			 t.zip("D:\\py\\新建文件夹\\新建a.zip", "D:\\py\\新建文件夹\\新建a.docx");
//			 t.unzip("D:\\py\\新建文件夹\\新建a.zip", "D:\\py\\新建文件夹\\新建a.docx");
			 t.unzip("D:\\py\\新建文件夹\\111\\011.zip", "D:\\py\\新建文件夹\\111\\011.docx");
			
//			bytesToFile(fileToBytes("D:\\py\\新建文件夹\\新建a.zip"),"D:\\py\\新建文件夹\\111\\011.zip");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	public byte[] fileToBytes(String path){
		
		File file = new File(path);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] b = new byte[(int)file.length()];
			int len = 0;
				while ((len = in.read(b))!=-1) {
					in.read(b);
				}
			return b;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new byte[]{};
	}
	
	public void bytesToFile(byte[] stu,String file){
		OutputStream out = null;
		try {
			out = new FileOutputStream(new File(file));
			out.write(stu);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}