/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.entity;


import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 消息查询
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MessageQuery extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 发送
	 */
	public static final int SENDTYPE = 1;
	/**
	 * 接收
	 */
	public static final int RECVTYPE = 2;
	/**
	 * 发送接收类型
	 */
	private Integer sendRecvType; 
	/**
	 * 帐册类型
	 * EMS_EDI_TR = 1; 	经营范围
	 * EMS_EDI_MERGER_BEFORE = 2;	归并关系（归并前部分）
	 * EMS_EDI_MERGER_AFTER = 3;	归并关系（归并后部分）
	 * EMS_EDI_H2K = 4;	电子帐册（不包括单损耗）
	 * EMS_EDI_H2K_CLEFT = 5;	电子帐册分册
	 * CHECK = 6;	电子帐册联网核查
	 * CANCEL = 7;	电子帐册报核（不包括单损耗）
	 * CustomsDeclare = 8;	报关单
	 * CUSTOMS_BILL = 9;	报关清单
	 */
	private Integer ediType;     
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
	 * 企业编码
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
	 * 报关单预录入号
	 */
	private String bgdcheckResult;
	/**
	 * 报关单预录入号
	 */
	private String ylrh;         
	/**
	 * 报关单统一编号
	 */
	private String uniteCode;     
	/**
	 * 报关单号
	 */
	private String customs;       
	/**
	 * 报关单回执内容
	 */
	private String returnNote;    
	/**
	 * 大单序号
	 */
	private String bigSeqnum;    
	
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
	public Integer getEdiType() {
		return ediType;
	}
	/**
	 * @param ediType The ediType to set.
	 */
	public void setEdiType(Integer ediType) {
		this.ediType = ediType;
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
	 * @return Returns the sendRecvType.
	 */
	public Integer getSendRecvType() {
		return sendRecvType;
	}
	/**
	 * @param sendRecvType The sendRecvType to set.
	 */
	public void setSendRecvType(Integer sendRecvType) {
		this.sendRecvType = sendRecvType;
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
	 * @return Returns the returnNote.
	 */
	public String getReturnNote() {
		return returnNote;
	}
	/**
	 * @param returnNote The returnNote to set.
	 */
	public void setReturnNote(String returnNote) {
		this.returnNote = returnNote;
	}
	/**
	 * @return Returns the uniteCode.
	 */
	public String getUniteCode() {
		return uniteCode;
	}
	/**
	 * @param uniteCode The uniteCode to set.
	 */
	public void setUniteCode(String uniteCode) {
		this.uniteCode = uniteCode;
	}
	/**
	 * @return Returns the ylrh.
	 */
	public String getYlrh() {
		return ylrh;
	}
	/**
	 * @param ylrh The ylrh to set.
	 */
	public void setYlrh(String ylrh) {
		this.ylrh = ylrh;
	}
	/**
	 * @return Returns the customs.
	 */
	public String getCustoms() {
		return customs;
	}
	/**
	 * @param customs The customs to set.
	 */
	public void setCustoms(String customs) {
		this.customs = customs;
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
	/**
	 * @return Returns the bgdcheckResult.
	 */
	public String getBgdcheckResult() {
		return bgdcheckResult;
	}
	/**
	 * @param bgdcheckResult The bgdcheckResult to set.
	 */
	public void setBgdcheckResult(String bgdcheckResult) {
		this.bgdcheckResult = bgdcheckResult;
	}
	/**
	 * @return Returns the bigSeqnum.
	 */
	public String getBigSeqnum() {
		return bigSeqnum;
	}
	/**
	 * @param bigSeqnum The bigSeqnum to set.
	 */
	public void setBigSeqnum(String bigSeqnum) {
		this.bigSeqnum = bigSeqnum;
	}
}
