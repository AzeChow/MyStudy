package com.bestway.customs.common.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Trade;

/**
 * 从QP下载导入报关单的过滤条件
 * @author lenovo
 *
 */
public class ImportBGDCondition implements java.io.Serializable{
	/**
	 * 是否是进口报关单
	 */
	private boolean isImportBGD;
	/**
	 * 开始日期
	 */
	private Date beginDate;
	/**
	 * 结束日期
	 */
	private Date endDate;
	/**
	 * 申报海关
	 */
	private String customs;
	/**
	 * 贸易方式
	 */
	private String trade;
	/**
	 * 手册号
	 */
	private String emsNo;
	/**
	 * 报关单号
	 */
	private String entryId;
	
	
	public boolean isImportBGD() {
		return isImportBGD;
	}
	public void setImportBGD(boolean isImportBGD) {
		this.isImportBGD = isImportBGD;
	}
	public String getCustoms() {
		return customs;
	}
	public void setCustoms(String customs) {
		this.customs = customs;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getEmsNo() {
		return emsNo;
	}
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	public String getEntryId() {
		return entryId;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
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
	

}
