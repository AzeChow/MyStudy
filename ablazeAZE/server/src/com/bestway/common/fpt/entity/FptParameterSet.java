package com.bestway.common.fpt.entity;

import com.bestway.common.CommonUtils;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.message.entity.CspParameterSet;

/**
 * 关封参数设置表头
 * @author ower
 *
 */
public class FptParameterSet extends CspParameterSet {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	private Integer projectType = ProjectType.BCUS;

	/**
	 * 一个关封是否可以用多次
	 */
	private Boolean customsEnvelopUsedMultiple = false;
	
	
//	// ftp地址
//	private String ftpAddress;
//	
//	// 端口
//	private Integer ftpPort;
//	
//	// 用户名
//	private String ftpUserName;
//	
//	// 密码
//	private String ftpPassword;
	
	/**
	 * 控制申请表当前余量控制 ，true 控制 false 不控制
	 */
	private Boolean 	controlAppCurrentAmount = false;
	/**
	 * 控制收发货当前余量控制 ，true 控制 false 不控制
	 */
	private Boolean 	controlBillCurrentAmount = false;
	
//	public String getFtpAddress() {
//		return ftpAddress;
//	}
//
//	public void setFtpAddress(String ftpAddress) {
//		this.ftpAddress = ftpAddress;
//	}
//
//	public Integer getFtpPort() {
//		return ftpPort;
//	}
//
//	public void setFtpPort(Integer ftpPort) {
//		this.ftpPort = ftpPort;
//	}
//
//	public String getFtpUserName() {
//		return ftpUserName;
//	}
//
//	public void setFtpUserName(String ftpUserName) {
//		this.ftpUserName = ftpUserName;
//	}
//
//	public String getFtpPassword() {
//		return ftpPassword;
//	}
//
//	public void setFtpPassword(String ftpPassword) {
//		this.ftpPassword = ftpPassword;
//	}

	public Boolean getCustomsEnvelopUsedMultiple() {
		return customsEnvelopUsedMultiple;
	}

	public void setCustomsEnvelopUsedMultiple(Boolean customsEnvelopUsedMultiple) {
		this.customsEnvelopUsedMultiple = customsEnvelopUsedMultiple;
	}

	/**
	 * @return the projectType
	 */
	public Integer getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType the projectType to set
	 */
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	/**
	 * @return the controlAppCurrentAmount
	 */
	public Boolean getControlAppCurrentAmount() {
		return controlAppCurrentAmount;
	}

	/**
	 * @param controlAppCurrentAmount the controlAppCurrentAmount to set
	 */
	public void setControlAppCurrentAmount(Boolean controlAppCurrentAmount) {
		this.controlAppCurrentAmount = controlAppCurrentAmount;
	}

	/**
	 * @return the controlBillCurrentAmount
	 */
	public Boolean getControlBillCurrentAmount() {
		return controlBillCurrentAmount;
	}

	/**
	 * @param controlBillCurrentAmount the controlBillCurrentAmount to set
	 */
	public void setControlBillCurrentAmount(Boolean controlBillCurrentAmount) {
		this.controlBillCurrentAmount = controlBillCurrentAmount;
	}

}
