/*
 * Created on 2004-9-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 现金流量表表头
 */
public class MoneyMaster extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 * 现金流量表表头流水号
	 */
	private Integer num;       
	/**
	 * 报表时间
	 */
	private Date reportDate;  
	/**
	 * 备注
	 */
	private String note;      
	/**
	 * 是否作废
	 */
	private Boolean isDelete=false; 
	/**
	 * 取得是否作废标志
	 * @return isDelete 是否作废.
	 */
	public Boolean getIsDelete() {
		return isDelete;
	}
	/**
	 * 设置是否作废
	 * @param isDelete 是否作废.
	 */
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * 取得备注内容
	 * @return note 备注.
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置备注内容
	 * @param note 备注.
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 取得现金流量表表头流水号
	 * @return num 流水号.
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * 设置现金流量表表头流水号
	 * @param num 流水号.
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * 取得报表生成日期
	 * @return reportDate 报表日期.
	 */
	public Date getReportDate() {
		return reportDate;
	}
	/**
	 * 设置报表生成日期
	 * @param reportDate 报表日期.
	 */
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
}