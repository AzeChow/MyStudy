package com.bestway.common.message.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * BCS参数设置
 */
public class CspParameterSet extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 发送路径
	 */
	private String sendDir = "";

	/**
	 * 接收路径
	 */
	private String recvDir = "";

	/**
	 * 回执报文存放路径
	 */
	private String finallyDir = "";

	/**
	 * 日志路径
	 */
	private String logDir = "";

	/**
	 * 是否读卡
	 */
	private Boolean readFromCard = false;

	/**
	 * 唯一号(加签读卡唯一号)
	 */
	private String seqNo = "";

	/**
	 * 密码(加签读卡密码)
	 */
	private String pwd = "";

	/**
	 * 是否远程加签
	 */
	private Boolean remoteSignData = false;

	/**
	 * 是否是远程邮箱
	 */
	private Boolean remoteDxpMail = false;

	/**
	 * 9097端口是否影射开通
	 */
	private Boolean port9097IsOpen = false;

	/**
	 * 远程服务地址
	 */
	private String remoteHostAddress = "";
	/**
	 * 远程服务端口
	 */
	private String remoteHostPort = "6666";
	/**
	 * 远程服务卡密码
	 */
	private String remoteHostICPwd="";
	
	/**
	 * 签名人IC卡ID
	 */
	private String idCard = "";

	public String getFinallyDir() {
		return finallyDir;
	}

	public void setFinallyDir(String finallyDir) {
		this.finallyDir = finallyDir;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Boolean getReadFromCard() {
		return readFromCard;
	}

	public void setReadFromCard(Boolean readFromCard) {
		this.readFromCard = readFromCard;
	}

	public String getRecvDir() {
		return recvDir;
	}

	public void setRecvDir(String recvDir) {
		this.recvDir = recvDir;
	}

	public String getSendDir() {
		return sendDir;
	}

	public void setSendDir(String sendDir) {
		this.sendDir = sendDir;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getRemoteHostAddress() {
		return remoteHostAddress;
	}

	public void setRemoteHostAddress(String remoteHostAddress) {
		this.remoteHostAddress = remoteHostAddress;
	}

	public Boolean getRemoteSignData() {
		return remoteSignData;
	}

	public void setRemoteSignData(Boolean remoteSignData) {
		this.remoteSignData = remoteSignData;
	}

	public Boolean getRemoteDxpMail() {
		return remoteDxpMail;
	}

	public void setRemoteDxpMail(Boolean remoteDxpMail) {
		this.remoteDxpMail = remoteDxpMail;
	}

	public Boolean getPort9097IsOpen() {
		return port9097IsOpen;
	}

	public void setPort9097IsOpen(Boolean port9097IsOpen) {
		this.port9097IsOpen = port9097IsOpen;
	}

	public String getRemoteHostPort() {
		if(remoteHostPort==null||"".equals(remoteHostPort.trim())){
			return "6666";
		}
		return remoteHostPort;
	}

	public void setRemoteHostPort(String remoteHostPort) {
		this.remoteHostPort = remoteHostPort;
	}

	public String getRemoteHostICPwd() {
		return remoteHostICPwd;
	}

	public void setRemoteHostICPwd(String remoteHostICPwd) {
		this.remoteHostICPwd = remoteHostICPwd;
	}

}
