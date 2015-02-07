/*
 * Created on 2004-6-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.materialbase.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放物料通用代码－－汇率设置
 * 
 * @author Administrator
 */
public class CurrRate extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 本地
	 */
	private Curr curr;  

	/**
	 * 外地
	 */
	private Curr curr1; 

	/**
	 * 汇率
	 */
	private Double rate; 

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 设置创建日期
	 * 
	 * @return createDate 创建日期
	 */
	public Date getCreateDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (createDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(createDate));
		}
		return createDate;
	}

	/**
	 * 设置创建日期
	 * 
	 * @param createDate 创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 设置本地
	 * 
	 * @return curr 本地
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置本地
	 * 
	 * @param curr 本地
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 设置外地
	 * 
	 * @return curr1 外地
	 */
	public Curr getCurr1() {
		return curr1;
	}

	/**
	 * 设置外地
	 * 
	 * @param curr1 外地
	 */
	public void setCurr1(Curr curr1) {
		this.curr1 = curr1;
	}

	/**
	 * 设置汇率
	 * 
	 * @return rate 汇率
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * 设置汇率
	 * 
	 * @param rate 汇率
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}
}
