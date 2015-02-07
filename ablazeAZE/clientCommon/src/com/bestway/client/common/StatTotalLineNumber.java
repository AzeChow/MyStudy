package com.bestway.client.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StatTotalLineNumber {
	int codeLines = 0; // 代码行数

	int commLines = 0; // 注释行数

	int otherLines = 0; // 其它文件行数

	int nullLines = 0; // 空行

	int javaFiles = 0; // java文件个数

	int otherFiles = 0; // 其它文件个数

	public File srcDir;

	/** Creates a new instance of Main */
	public StatTotalLineNumber() {

	}

	public void print() {
		System.out.println("代码行数: " + codeLines);
		System.out.println("注释行数: " + commLines);
		System.out.println("其它文件行数: " + otherLines);
		System.out.println("空行: " + nullLines);
		System.out.println("java文件个数: " + javaFiles);
		System.out.println("其它文件个数: " + otherFiles);
	}

	public void calcLines() throws FileNotFoundException, IOException {
		read(srcDir);
	}

	private void read(File file) throws FileNotFoundException, IOException {
		if (file.isFile())
			return;
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				if (files[i].toString().startsWith("."))
					continue;
				read(files[i]);
			} else {
				String str = files[i].toString();
				if (str.endsWith(".java") || str.endsWith(".xml")) {
					readFileLines(files[i]);
				}
			}
		}
	}

	private void readFileLines(File file) throws FileNotFoundException,
			IOException {
		System.out.println(file + " ...");
		BufferedReader read = new BufferedReader(new FileReader(file));
		if (file.toString().endsWith(".java")) {
			javaFiles++;
		} else {
			otherFiles++;
		}
		String line;
		while (true) {
			line = read.readLine();
			if (line == null)
				break;
			line = line.trim();
			if ("".equals(line)) {
				nullLines++;
			} else if (line.startsWith("/*") || line.startsWith("*")) {
				commLines++;
			} else if (file.toString().endsWith(".java")) {
				codeLines++;
			} else {
				otherLines++;
			}
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// 
//		if (args == null || args.length < 1) {
//			System.out.println("请输入程序源代码目录。");
//			return;
//		}
		StatTotalLineNumber main = new StatTotalLineNumber();
		String workspaceDir="D:/eclipse/workspace8.10/";
		String clientCommonDir=workspaceDir+"bestwayclientcommon/src";
		String jbcusDir=workspaceDir+"jbcus3/src";
		String jbcusClientDir=workspaceDir+"jbcusclient/src";
		
		try {
			main.srcDir = new File(clientCommonDir);
			main.calcLines();
			main.srcDir = new File(jbcusDir);
			main.calcLines();
			main.srcDir = new File(jbcusClientDir);
			main.calcLines();
			main.print();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
