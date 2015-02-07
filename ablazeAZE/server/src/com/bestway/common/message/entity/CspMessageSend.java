/*
 * Created on 2004-7-7
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.message.entity;


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
public class CspMessageSend extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 类型
	 * EMS_POR_WJ = "1";	经营范围/合同备案/备案资料库备案 
	 * EMS_POR_BILL = "2";	电子帐册备案商品/通关备案
	 * CANCEL_AFTER_VERIFICA = "4"; 	 数据报核
	 */
	private String sysType; 
	/**
	 * 发送电脑名
	 */
	private String computerName; 
	/**
	 * 收发时间
	 */
	private Date sendRecvTime; 
	/**
	 * 报文类型
	 */
	private String messageType; 
	/**
	 * 文件名称
	 */
	private String fileName;   
	/**
	 * 企业内部编码
	 */
	private String copEmsNo;     
	/**
	 * 报文代码
	 */
	private String messageCode; 
	/**
	 * 发送或接收者
	 */
	private String sendRecvEr;  
	/**
	 * 审核结果
	 * IN_STOCK_SUCCEED = 1;	入库成功
	 * CHECK_PASS = 2	审批通过
	 * QUIT_BILL= 3;	退单
	 * RE_DECLARE = 4;	重复申报(退单)
	 * IN_STOCK_SUCCEED_AUTO = 5;	入库成功(自动审核检查通过)
	 */
	private Integer checkResult;  
	
	/**
	 * @return Returns the computerName.
	 */
	public String getComputerName() {
		return computerName;
	}
	/**
	 * @param computerName The computerName to set.
	 */
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	/**
	 * @return Returns the copEmsNo.
	 */
	public String getCopEmsNo() {
		return copEmsNo;
	}
	/**
	 * @param copEmsNo The copEmsNo to set.
	 */
	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}
	/**
	 * @return Returns the ediType.
	 */
	public String getSysType() {
		return sysType;
	}
	/**
	 * @param ediType The ediType to set.
	 */
	public void setSysType(String sysType) {
		this.sysType = sysType;
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
	 * @return Returns the messageCode.
	 */
	public String getMessageCode() {
		return messageCode;
	}
	/**
	 * @param messageCode The messageCode to set.
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
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
	 * @return Returns the sendRecvEr.
	 */
	public String getSendRecvEr() {
		return sendRecvEr;
	}
	/**
	 * @param sendRecvEr The sendRecvEr to set.
	 */
	public void setSendRecvEr(String sendRecvEr) {
		this.sendRecvEr = sendRecvEr;
	}

	/**
	 * @return Returns the sendRecvTime.
	 */
	public Date getSendRecvTime() {
		return sendRecvTime;
	}
	/**
	 * @param sendRecvTime The sendRecvTime to set.
	 */
	public void setSendRecvTime(Date sendRecvTime) {
		this.sendRecvTime = sendRecvTime;
	}

	/**
	 * @return Returns the checkResult.
	 */
	public Integer getCheckResult() {
		return checkResult;
	}
	/**
	 * @param checkResult The checkResult to set.
	 */
	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}
}
