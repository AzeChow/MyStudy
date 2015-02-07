/*
 * Created on 2004-9-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.owp.action;

import com.bestway.common.Request;
import com.bestway.common.message.action.CspMessageAction;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public interface OwpMessageAction extends CspMessageAction {
	/**
	 * 获取报文格式
	 * 
	 * @param sysType
	 * @param messageFileName
	 * @return
	 */
	String getFormatFileNameByType(Request request, String sysType);

//	/**
//	 * 获取申请单/收送货/退货报文的转入转出标志
//	 * 
//	 * @param messageFileName
//	 * @return
//	 */
//	String getInOutFlag(Request request, String businessType,
//			String messageFileName);
	
}