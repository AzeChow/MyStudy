package com.bestway.bcus.cas.entity;

import java.util.Date;

import com.bestway.common.BaseEntity;

public class TempEmsImgExg extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String emsNo = null;

	private Date beginDate = null;

	private Date endDate = null;

	private Integer secNo = null;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getSecNo() {
		return secNo;
	}

	public void setSecNo(Integer secNo) {
		this.secNo = secNo;
	}

}
