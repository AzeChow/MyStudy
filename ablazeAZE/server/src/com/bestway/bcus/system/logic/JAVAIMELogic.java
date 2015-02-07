package com.bestway.bcus.system.logic;

import com.bestway.common.CommonUtils;
import com.bestway.common.WebUtils;

public class JAVAIMELogic {
	private static String		LIB_FOLDER			= WebUtils.getWebAppRoot()
															+ "WEB-INF/lib";
	private static final String	JAVA_CHINESE_IME	= "javachineseime.jar";

	/**
	 * 下载 java chinese ime jar
	 * 
	 */
	public byte[] downloadJAVAIME() {
		String filePath = LIB_FOLDER + "/" + JAVA_CHINESE_IME;
		return CommonUtils.getBytesByFile(filePath);
	}
}
