/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.logic;

/**
 * @author 陈海鹏
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NotFoundSectionFlagException extends RuntimeException {	
	
	public NotFoundSectionFlagException(String sSectionFlag){
		super("未能找到指定的SectionFlag："+sSectionFlag);
	}
}
