package com.bestway.bcus.system.entity;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

public class TcsParameters extends BaseEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 报文发送者编号
	 */
	private String senderId;
	/**
	 * 报文发送者地址
	 */
	private String senderAddress;
	/**
	 * 报文接收者编号
	 */
	private String receiverId;
	/**
	 * 报文接收者地址
	 */
	private String receiverAddress;
	/**
	 * 集成通平台用户编号 
	 */
	private String userId;
	/**
	 * 集成通平台用户私密 
	 */
	private String userPrivateKey;
	/**
	 * 集成通企业业务流程编号
	 */
	private String bpNo;
	/**
	 * 集成通企业业务流程活动的活动编号
	 */
	private String actionId;
	/**
	 * 录入员IC卡号
	 */
	private String icCardNo;
	/**
	 * 操作员卡的证书号
	 */
	private String certificateNo;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	/**
	 * 组织机构代码
	 */
	private String oganizationCode;
//	/**
//	 * ftp ip地址
//	 */
//	private String ftpAddress;
//	/**
//	 * ftp用户名
//	 */
//	private String ftpName;
//	/**
//	 * ftp密码
//	 */
//	private String ftpPwd;
	/**
	 * 是否发送报关单附页
	 */
	private Boolean sendNote;
	
	/**
	 * 平台地址
	 */
	private String btplsAddress;
	/**
	 * 平台端口
	 */
	private Integer btplsPort;
	
	
	public Boolean getSendNote() {
		return sendNote;
	}
	public void setSendNote(Boolean sendNote) {
		this.sendNote = sendNote;
	}
//	public String getFtpAddress() {
//		return ftpAddress;
//	}
//	public void setFtpAddress(String ftpAddress) {
//		this.ftpAddress = ftpAddress;
//	}
//	public String getFtpName() {
//		return ftpName;
//	}
//	public void setFtpName(String ftpName) {
//		this.ftpName = ftpName;
//	}
//	public String getFtpPwd() {
//		return ftpPwd;
//	}
//	public void setFtpPwd(String ftpPwd) {
//		this.ftpPwd = ftpPwd;
//	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOganizationCode() {
		return oganizationCode;
	}
	public void setOganizationCode(String oganizationCode) {
		this.oganizationCode = oganizationCode;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getIcCardNo() {
		return icCardNo;
	}
	public void setIcCardNo(String icCardNo) {
		this.icCardNo = icCardNo;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPrivateKey() {
		return userPrivateKey;
	}
	public void setUserPrivateKey(String userPrivateKey) {
		this.userPrivateKey = userPrivateKey;
	}
	public String getBpNo() {
		return bpNo;
	}
	public void setBpNo(String bpNo) {
		this.bpNo = bpNo;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getBtplsAddress() {
		return btplsAddress;
	}
	public void setBtplsAddress(String btplsAddress) {
		this.btplsAddress = btplsAddress;
	}
	public Integer getBtplsPort() {
		return btplsPort;
	}
	public void setBtplsPort(Integer btplsPort) {
		this.btplsPort = btplsPort;
	}
	
	
}
