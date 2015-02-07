package com.bestway.common.erpbill.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * 存放文本导入定单的资料
 * 
 * @author Administrator
 * 
 */
public class OrderDate implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	public static final int order_scmCoc = 1;
	public static final int order_billCode = 2;
	public static final int order_ifzc = 3;
	public static final int order_orderDate = 4;
	public static final int order_ptNo = 5;
	public static final int order_bgAmount = 6;
	public static final int order_bgUnit = 7;
	public static final int order_salesDate = 8;
	public static final int order_state = 9;
	public static final int order_erpId = 10;
	public static final int order_orderType = 11;
	public static final int order_unitPrice = 12;
	public static final int order_curr = 13;
	public static final int order_totalPrice = 14;
	/**
	 * 单价
	 */
	private String unitPrice = null;
	/**
	 * 币别
	 */
	private String curr = null;
	/**
	 * 订单类型
	 */
	private String orderType =null;
	/**
	 * 总价
	 */
	private String totalPrice = null;

	/**
	 * 客户
	 */
	private String scmCoc = null;
	/**
	 * 定单号
	 */
	private String billCode = null;
	/**
	 * 是否转厂
	 */
	private String ifzc = null;
	/**
	 * 订单日期
	 */
	private String orderDate = null;
	/**
	 * 料号
	 */
	private String ptNo = null;
	/**
	 * 数量
	 */
	private String bgAmount = null;
	/**
	 * 单位
	 */
	private String bgUnit = null;
	/**
	 * 送货日期
	 */
	private String salesDate = null;
	/**
	 * 
	 */
	private int[] invalidationColNo;

	/**
	 * 错误原因
	 */
	private String errinfo = "";
	/**
	 * 行记录
	 */
	private Integer recordRow = null;
	/**
	 * 订单状态: 1:有效,2:作废
	 */
	private String state = null;

	/**
	 * 订单在ＥＲＰ中的ＩＤ
	 */
	private String orderERPId = null;

	public String getBgAmount() {
		return bgAmount;
	}

	public void setBgAmount(String bgAmount) {
		this.bgAmount = bgAmount;
	}

	public String getBgUnit() {
		return bgUnit;
	}

	public void setBgUnit(String bgUnit) {
		this.bgUnit = bgUnit;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getIfzc() {
		return ifzc;
	}

	public void setIfzc(String ifzc) {
		this.ifzc = ifzc;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}

	public String getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(String scmCoc) {
		this.scmCoc = scmCoc;
	}


	public String getErrinfo() {
		return errinfo;
	}

	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	public int[] getInvalidationColNo() {
		return invalidationColNo;
	}

	public void setInvalidationColNo(int[] invalidationColNo) {
		this.invalidationColNo = invalidationColNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getRecordRow() {
		return recordRow;
	}

	public void setRecordRow(Integer recordRow) {
		this.recordRow = recordRow;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrderERPId() {
		return orderERPId;
	}

	public void setOrderERPId(String orderERPId) {
		this.orderERPId = orderERPId;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
