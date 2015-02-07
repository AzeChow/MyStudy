package com.bestway.client.util;

public class JarItem {

	private String	fileName		= null;
	private String	modifiyTime		= null;
	private String	hashCode		= null;
	private int		fileNameWidth	= 80;	// 字宽

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getModifiyTime() {
		return modifiyTime;
	}

	public void setModifiyTime(String modifiyTime) {
		this.modifiyTime = modifiyTime;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String toString() {
		String tempFileName = this.fileName;
		if (tempFileName.length() < fileNameWidth) {
			int blankNum = fileNameWidth - tempFileName.length();
			for (int i = 0; i < blankNum; i++) {
				tempFileName += " ";
			}
		}
		return tempFileName + "  " + modifiyTime;
	}

	public int getFileNameWidth() {
		return fileNameWidth;
	}

	public void setFileNameWidth(int fileNameWidth) {
		this.fileNameWidth = fileNameWidth;
	}
}
