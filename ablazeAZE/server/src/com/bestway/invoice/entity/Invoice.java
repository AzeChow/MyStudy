/*
 * Created on 2005-7-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.invoice.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 发票管理
 * 
 * @author Administrator table="invoice" 发票管理
 */
public class Invoice extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 版次
	 */
	private String versionCode;

	/**
	 * 发票号
	 */
	private String invoiceCode;

	/**
	 * 报关单号
	 */
	private String customsDeclaredCode;

	/**
	 * 金额
	 */
	private Double money;

	/**
	 * 录入日期
	 */
	private Date beginDate;

	/**
	 * 截止日期
	 */
	private Date endDate;

	/**
	 * 状态
	 * 
	 * DRAFT=0;//领用 USED=1;//已使用 CANCELED=2;//作废
	 * CANCEL_AFTER_VERIFICATION=3;//核销
	 * 
	 * 
	 */
	private Integer state;

	/**
	 * 备注 比如输入作废原因等资料
	 */
	private String memo;

	/**
	 * 报关单来源 bcus bcs dzba
	 */
	private Integer projectType;

	/**
	 * 报关单ID号
	 */
	private String customsDeclarationId;

	/**
	 * 领用日期
	 */
	private Date draftDate;

	/**
	 * 使用日期
	 */
	private Date usedDate;

	/**
	 * 作废日期
	 */
	private Date canceledDate;

	/**
	 * 核销日期
	 */
	private Date cavDate;

	/**
	 * @return serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * 取得录入日期
	 * 
	 * @return beginDate 录入日期
	 */
	public Date getBeginDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(beginDate));
		}
		return beginDate;
	}

	/**
	 * 设置录入日期
	 * 
	 * @param beginDate
	 *            录入日期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 取得报关单号
	 * 
	 * @return customsDeclaredCode 报关单号
	 */
	public String getCustomsDeclaredCode() {
		return customsDeclaredCode;
	}

	/**
	 * 设置报关单号
	 * 
	 * @param customsDeclaredCode
	 *            报关单号
	 */
	public void setCustomsDeclaredCode(String customsDeclaredCode) {
		this.customsDeclaredCode = customsDeclaredCode;
	}

	/**
	 * 取得截止日期
	 * 
	 * @return endDate 截止日期
	 */
	public Date getEndDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (endDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(endDate));
		}
		return endDate;
	}

	/**
	 * 设置截止日期
	 * 
	 * @param endDate
	 *            截止日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 取得发票号
	 * 
	 * @return invoiceCode 发票号
	 */
	public String getInvoiceCode() {
		return invoiceCode;
	}

	/**
	 * 设置发票号
	 * 
	 * @param invoiceCode
	 *            发票号
	 */
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	/**
	 * 取得金额
	 * 
	 * @return money 金额
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置金额
	 * 
	 * @param money
	 *            金额
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * 取得状态
	 * 
	 * @return state 状态
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * 设置状态
	 * 
	 * @param state
	 *            状态
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * 取得版次
	 * 
	 * @return versionCode 版次
	 */
	public String getVersionCode() {
		return versionCode;
	}

	/**
	 * 设置版次
	 * 
	 * @param versionCode
	 *            版次
	 */
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	/**
	 * 取得备注
	 * 
	 * @return memo 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 取得报关单来源
	 * 
	 * @return projectType 报关单来源
	 */
	public Integer getProjectType() {
		return projectType;
	}

	/**
	 * 设置报关单来源
	 * 
	 * @param projectType
	 *            报关单来源
	 */
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	/**
	 * 取得报关单id
	 * 
	 * @return customsDeclarationId 报关单id
	 */
	public String getCustomsDeclarationId() {
		return customsDeclarationId;
	}

	/**
	 * 设置报关单id
	 * 
	 * @param customsDeclarationId
	 *            报关单id
	 */
	public void setCustomsDeclarationId(String customsDeclarationId) {
		this.customsDeclarationId = customsDeclarationId;
	}

	/**
	 * 取得作废日期
	 * 
	 * @return canceledDate 作废日期
	 */
	public Date getCanceledDate() {
		return canceledDate;
	}

	/**
	 * 设置作废日期
	 * 
	 * @param canceledDate
	 *            作废日期
	 */
	public void setCanceledDate(Date canceledDate) {
		this.canceledDate = canceledDate;
	}

	/**
	 * 取得核销日期
	 * 
	 * @return cavDate 核销日期
	 */
	public Date getCavDate() {
		return cavDate;
	}

	/**
	 * 设置核销日期
	 * 
	 * @param cavDate
	 *            核销日期
	 */
	public void setCavDate(Date cavDate) {
		this.cavDate = cavDate;
	}

	/**
	 * 取得领用日期
	 * 
	 * @return draftDate 领用日期
	 */
	public Date getDraftDate() {
		return draftDate;
	}

	/**
	 * 设置领用日期
	 * 
	 * @param draftDate
	 *            领用日期
	 */
	public void setDraftDate(Date draftDate) {
		this.draftDate = draftDate;
	}

	/**
	 * 取得使用日期
	 * 
	 * @return usedDate 使用日期
	 */
	public Date getUsedDate() {
		return usedDate;
	}

	/**
	 * 设置使用日期
	 * 
	 * @param usedDate
	 *            使用日期
	 */
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	@Override
	public String toString() {

		ToStringBuilder toStringBuilder = new ToStringBuilder(this);

		toStringBuilder.append("id", getId());

		toStringBuilder.append("customsDeclaredCode", getCustomsDeclaredCode());

		toStringBuilder.append("state", getState());

		return toStringBuilder.toString();
	}

}
