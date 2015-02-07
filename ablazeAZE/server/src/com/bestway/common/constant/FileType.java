/*
 * Created on 2004-7-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FileType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 文本类型
	 */
	public static final Integer	TXT	= Integer.valueOf(1);
	
	/**
	 * 文本类型 后缀
	 */
	public static final String TXT_SUFFIX	= ".txt";
	
	/**
	 * excel 文件类型
	 */
	public static final Integer	XLS	= Integer.valueOf(2);
	
	/**
	 * excel 文件类型 后缀
	 */
	public static final String XLS_SUFFIX	= ".xls";
	
	/**
	 * word 文档
	 */
	public static final Integer	DOC	= Integer.valueOf(3);
	
	/**
	 * word 文档 后缀
	 */
	public static final String DOC_SUFFIX	= ".doc";
	
	/**
	 * pdf 阅读文档
	 */
	public static final Integer	PDF	= Integer.valueOf(4);
	
	/**
	 * pdf 阅读文档 后缀
	 */
	public static final String PDF_SUFFIX	= ".pdf";
	
	
	public static final String getSuffix(Integer fileType) {
		String suffix = null;
		if(fileType == TXT) {
			suffix = TXT_SUFFIX;
		} else if(fileType == XLS) {
			suffix = XLS_SUFFIX;
		} else if(fileType == DOC) {
			suffix = DOC_SUFFIX;
		} else if(fileType == PDF) {
			suffix = PDF_SUFFIX;
		} else {
			suffix = TXT_SUFFIX;
		}
			
		return suffix;
	}
}
