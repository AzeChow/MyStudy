 package com.bestway.bcus.cas.invoice.entity;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.ScmCoc;
/**
 * 发票和单据对应表
 * @author ower
 *
 */
public class InvoiceAndBillCorresponding extends BaseScmEntity {

	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 2727658581873989611L;

	/**
	 * 发票体ID
	 */
	private String invoiceInfokey = null;

	/**
	 * 单据体ID
	 */

	private String billDetailkey = null;
	
	
	/**
	 * 单据号码
	 */

	private String billNo = null;
	
	/**
	 * 单据类型
	 */
	private String typeCode = null;

	/**
	 * 对应发票数(不为该对应发票的总数量，只存该发票对应的发票数量)
	 */
	private Double invoiceNum = null;

	/**
	 * 对应单据数
	 */
	private Double billDetailNum = null;

	/**
	 * 报关名称
	 */
	private String cusName = null;

	/**
	 * 报关规格
	 */
	private String cusSpce = null;

	/**
	 * 报关单位
	 */
	private Unit cusUnit = null;

	/**
	 * 客户供应商
	 */
	private ScmCoc customer = null;
	/**
	 * 发票商品单价
	 */
	private Double invoicePrice = null;
	/**
	 * 发票重量(不为该对应发票的总重量，只存该发票对应的发票重量)
	 */
	private Double invoiceWeight = null;
	/**
	 * 对应单据重量
	 */
	private Double billWeight = null;
	/**
	 * 发票中的手册号
	 */
	private String emsNo=null;

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusSpce() {
		return cusSpce;
	}

	public void setCusSpce(String cusSpce) {
		this.cusSpce = cusSpce;
	}

	/**
	 * 获取对应发票数量
	 * @return
	 */
	public Double getInvoiceNum() {
		return invoiceNum;
	}

	/**
	 * 设置对应发票数量
	 * @param invoiceNum
	 */
	public void setInvoiceNum(Double invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public ScmCoc getCustomer() {
		return customer;
	}

	public void setCustomer(ScmCoc customer) {
		this.customer = customer;
	}

	public Unit getCusUnit() {
		return cusUnit;
	}

	public void setCusUnit(Unit cusUnit) {
		this.cusUnit = cusUnit;
	}

	public Double getBillDetailNum() {
		return billDetailNum;
	}

	public void setBillDetailNum(Double billDetailNum) {
		this.billDetailNum = billDetailNum;
	}

	/**
	 * 获取对应单据重量
	 * @return
	 */
	public Double getBillWeight() {
		return billWeight;
	}

	/**
	 * 设置对应单据重量
	 * @param billWeight
	 */
	public void setBillWeight(Double billWeight) {
		this.billWeight = billWeight;
	}

	public Double getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(Double invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	/**
	 * 获取发票重量
	 * @return
	 */
	public Double getInvoiceWeight() {
		return invoiceWeight;
	}

	/**
	 * 设置发票重量
	 * @param invoiceWeight
	 */
	public void setInvoiceWeight(Double invoiceWeight) {
		this.invoiceWeight = invoiceWeight;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public String getInvoiceInfokey() {
		return invoiceInfokey;
	}

	public void setInvoiceInfokey(String invoiceInfokey) {
		this.invoiceInfokey = invoiceInfokey;
	}

	public String getBillDetailkey() {
		return billDetailkey;
	}

	public void setBillDetailkey(String billDetailkey) {
		this.billDetailkey = billDetailkey;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	
}
