package com.bestway.common.message.entity;

import java.io.File;
import java.io.Serializable;

public class TempCspReceiptFile implements Serializable, Comparable {

	private File file;

	private String fileName;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int compareTo(Object o) {
		TempCspReceiptFile temp = (TempCspReceiptFile) o;
		return this.fileName.compareTo(temp.getFileName());
	}
}
