/*
 * Created on 2004-7-7
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.owp.entity;

import java.util.Date;

import com.bestway.common.CommonUtils;
import com.bestway.common.message.entity.CspMessageSend;

/**
 * 报文发送
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class OwpMessageSend extends CspMessageSend {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 转入，转出标志
	 */
	private String inOutFlag;

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}
}
