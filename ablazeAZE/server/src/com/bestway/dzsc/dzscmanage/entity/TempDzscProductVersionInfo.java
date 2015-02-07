package com.bestway.dzsc.dzscmanage.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;

public class TempDzscProductVersionInfo extends BaseScmEntity {
	/**
	 * 成品料号
	 */
	private String parentNo;

	/**
	 * 版本号
	 */
	private String versionNo;

	/**
	 * 版本开始日期
	 */
	private Date beginDate;

	/**
	 * 版本结束日期
	 */
	private Date endDate;

	/**
	 * 分配数量
	 */
	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
}
