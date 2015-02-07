package com.bestway.bcus.cas.invoice.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.ScmCoc;
/**
 * 存储海关帐的发票
 * @author ower
 *
 */
public class CasInvoice extends BaseScmEntity {

	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 3873439713644045495L;

	/**
	 * 发票号
	 */
	private String invoiceNo = null;

	/**
	 * 发票日期
	 */
	private Date validDate = null;

	/**
	 * 客户供应商
	 */
	private ScmCoc customer = null;

	/**
	 * 手册号
	 */
	private String emsNo = null;

	/**
	 * 是否核销
	 */
	private boolean canceled = false;
	
	/**
	 * 导入时临时存放字段
	 * @return
	 */
	private String tempNo;

	public ScmCoc getCustomer() {
		return customer;
	}

	public void setCustomer(ScmCoc customer) {
		this.customer = customer;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * @return the tempNo
	 */
	public String getTempNo() {
		return tempNo;
	}

	/**
	 * @param tempNo the tempNo to set
	 */
	public void setTempNo(String tempNo) {
		this.tempNo = tempNo;
	}
}
