/*
 * Created on 2004-12-3
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.license.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.bestway.common.CommonUtils;

/**
 * 客户信息
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClientInfo implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 客户登陆公司
	 */
	private String companyName;
	/**
	 * 客户端电脑名称
	 */
	private String clientName;
	/**
	 * 客户端IP
	 */
	private String clientIP;
	/**
	 * 在同一个客户端电脑上打开几个客户端程序
	 */
	private Integer commonClientNo;
	/**
	 * 客户端登陆时间
	 */
	private Date clientLoginTime;
	/**
	 * 客户端最后一次呼叫时间
	 */
	private Date lastCallTime;
	/**
	 * 客户端正在执行的任务
	 */
	private String clientRunTask;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return Returns the clientIP.
	 */
	public String getClientIP() {
		return clientIP;
	}

	/**
	 * @param clientIP
	 *            The clientIP to set.
	 */
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	/**
	 * @return Returns the clientLoginTime.
	 */
	public Date getClientLoginTime() {
		return clientLoginTime;
	}

	/**
	 * @param clientLoginTime
	 *            The clientLoginTime to set.
	 */
	public void setClientLoginTime(Date clientLoginTime) {
		this.clientLoginTime = clientLoginTime;
	}

	/**
	 * @return Returns the clientName.
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clientName
	 *            The clientName to set.
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return Returns the clientRunTask.
	 */
	public String getClientRunTask() {
		return clientRunTask;
	}

	/**
	 * @param clientRunTask
	 *            The clientRunTask to set.
	 */
	public void setClientRunTask(String clientRunTask) {
		this.clientRunTask = clientRunTask;
	}

	/**
	 * @return Returns the commonClientNo.
	 */
	public Integer getCommonClientNo() {
		return commonClientNo;
	}

	/**
	 * @param commonClientNo
	 *            The commonClientNo to set.
	 */
	public void setCommonClientNo(Integer commonClientNo) {
		this.commonClientNo = commonClientNo;
	}

	/**
	 * @return Returns the lastCallTime.
	 */
	public Date getLastCallTime() {
		return lastCallTime;
	}

	/**
	 * @param lastCallTime
	 *            The lastCallTime to set.
	 */
	public void setLastCallTime(Date lastCallTime) {
		this.lastCallTime = lastCallTime;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null) {
			return false;
		}
		if (!(arg0 instanceof ClientInfo)) {
			return false;
		}
		ClientInfo info = (ClientInfo) arg0;
		if (this.getClientIP() == null || info.getClientIP() == null
				|| this.getCompanyName() == null
				|| info.getCompanyName() == null) {
			return false;
		}
		if (info.getClientIP().trim().equals(this.getClientIP().trim())
				&& info.getCompanyName().trim().equals(
						this.getCompanyName().trim())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (this.getClientIP() != null && this.getCompanyName() != null) {
			HashCodeBuilder hcb = new HashCodeBuilder();
			hcb
					.append(this.getClientIP().trim()
							+ this.getCompanyName().trim());
			return hcb.toHashCode();
		} else {
			return super.hashCode();
		}
	}
}