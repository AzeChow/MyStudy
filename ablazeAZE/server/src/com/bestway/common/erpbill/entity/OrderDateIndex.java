package com.bestway.common.erpbill.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 存放从文本导入的定单资料
 * @author Administrator
 *
 */
public class OrderDateIndex extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
	/**
	 * 客户
	 */
	private Integer scmCoc;
	/**
	 * 定单号
	 */
	private Integer billCode;
	/**
	 * 是否转厂
	 */
	private Integer ifzc;
	/**
	 * 订单日期
	 */
	private Integer orderDate;
	/**
	 * 料号
	 */
	private Integer ptNo;
	/**
	 * 报关数量
	 */
	private Integer bgAmount;
	/**
	 * 报关单位
	 */
	private Integer bgUnit;
	/**
	 * 送货日期
	 */
	private Integer salesDate;
	/**
	 *订单状态: 1:有效,2:作废
	 */
	private Integer state = null;
	
	/**
	 *订单在ＥＲＰ中的ＩＤ
	 */
	private Integer orderERPId = null;
	public Integer getBgAmount() {
		return bgAmount;
	}
	public void setBgAmount(Integer bgAmount) {
		this.bgAmount = bgAmount;
	}
	public Integer getBgUnit() {
		return bgUnit;
	}
	public void setBgUnit(Integer bgUnit) {
		this.bgUnit = bgUnit;
	}
	public Integer getBillCode() {
		return billCode;
	}
	public void setBillCode(Integer billCode) {
		this.billCode = billCode;
	}
	public Integer getIfzc() {
		return ifzc;
	}
	public void setIfzc(Integer ifzc) {
		this.ifzc = ifzc;
	}
	public Integer getPtNo() {
		return ptNo;
	}
	public void setPtNo(Integer ptNo) {
		this.ptNo = ptNo;
	}
	public Integer getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(Integer salesDate) {
		this.salesDate = salesDate;
	}
	public Integer getScmCoc() {
		return scmCoc;
	}
	public void setScmCoc(Integer scmCoc) {
		this.scmCoc = scmCoc;
	}
	public Integer getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Integer orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getOrderERPId() {
		return orderERPId;
	}
	public void setOrderERPId(Integer orderERPId) {
		this.orderERPId = orderERPId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
