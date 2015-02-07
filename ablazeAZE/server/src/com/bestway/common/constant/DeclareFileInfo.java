package com.bestway.common.constant;

import java.math.BigDecimal;

import com.bestway.common.CommonUtils;

public class DeclareFileInfo implements java.io.Serializable {
	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 文件大小
	 */
	private long fileSize;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileInfoSpec() {
		String result = "<html>系统申报成功<br>" + "报文名称为:" + fileName + "<br>";
		double mbSize = 0L;
		if (fileSize > 0) {
			mbSize = CommonUtils.getDoubleByDigit(Double.valueOf(String.valueOf(fileSize)) / (1024.0 * 1024.0),6);
			result += "文件大小：" + formatDecimal(mbSize) + "MB" + "<b>";
		}
		if (mbSize >= 1.2) {
			result += "警告！已经超出海关默认的1.2MB,有可能海关入库失败";
		}
		result += "</html>";
		return result;
	}
	
	private String formatDecimal(double f) {
		BigDecimal b = new BigDecimal(f);
		double df = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(df);
	}

}
