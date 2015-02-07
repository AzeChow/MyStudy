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
public class CustomSendMessage extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();

	/**
	 * 集成通任务ID
	 */
	private String tcsTaskId;
	/**
	 * 报关单类型
	 */
	private Integer projectType; 
 
	/**
	 * 内部报关单编号
	 */
	private String seqNo;
	/**
	 * 发送时间
	 */
	private Date sendDate;
	
	/**
	 * 文件名称
	 */
	private String fileName;  
	/**
	 * 手册编号
	 */
	private String emsNo;  
//	/**
//	 * 报文内容
//	 */
//	private String context;  
	/**
	 * 发送者
	 */
	private String sendUser;   
	
	/**
	 * 回执处理结果
	 */
	private String bgdcheckResult;
	/**
	 * 报关单预录入号
	 */
	private String ylrh;
	/**
	 * 发送状态
	 */
	private String success;
	
	
	
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getEmsNo() {
		return emsNo;
	}
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
//	public String getContext() {
//		return context;
//	}
//	public void setContext(String context) {
//		this.context = context;
//	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getBgdcheckResult() {
		return bgdcheckResult;
	}
	public void setBgdcheckResult(String bgdcheckResult) {
		this.bgdcheckResult = bgdcheckResult;
	}
	public String getYlrh() {
		return ylrh;
	}
	public void setYlrh(String ylrh) {
		this.ylrh = ylrh;
	}
	public String getTcsTaskId() {
		return tcsTaskId;
	}
	public void setTcsTaskId(String tcsTaskId) {
		this.tcsTaskId = tcsTaskId;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}         
	
}
