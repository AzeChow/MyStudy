/*
 * Created on 2004-7-7
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.entity;


import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 报文发送
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BlsMessageSend extends BaseScmEntity {
	
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 发送时间
	 */
	private Date sendTime; 
	/**
	 * 报文类型
	 */
	private String messageType; 
	/**
	 * 文件名称
	 */
	private String fileName;   
	/**
	 * 单证号码
	 */
	private String keyCode;
	/**
	 * 发送者
	 */
	private String sendEr;  
	/**
	 * @return Returns the copEmsNo.
	 */
	public String getKeyCode() {
		return keyCode;
	}
	/**
	 * @param copEmsNo The copEmsNo to set.
	 */
	public void setKeyCode(String copEmsNo) {
		this.keyCode = copEmsNo;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return Returns the messageType.
	 */
	public String getMessageType() {
		return messageType;
	}
	/**
	 * @param messageType The messageType to set.
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return Returns the sendRecvTime.
	 */
	public Date getSendTime() {
		return sendTime;
	}
	/**
	 * @param sendRecvTime The sendRecvTime to set.
	 */
	public void setSendTime(Date sendRecvTime) {
		this.sendTime = sendRecvTime;
	}
	public String getSendEr() {
		return sendEr;
	}
	public void setSendEr(String sendEr) {
		this.sendEr = sendEr;
	}

}
