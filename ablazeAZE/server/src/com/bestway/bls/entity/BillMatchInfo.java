package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;

public class BillMatchInfo extends BaseScmEntity {
	
	/**
	 * 期初单单据编号
	 */
	private String inBillNo = null;
	
	/**
	 * 出仓单单据编号
	 */
	private String outBillNo = null;
	
	/**
	 * 入仓商品序号
	 */
	private Integer matchNo = null;
	
	/**
	 * 出仓商品序号
	 */
	private Integer outMatchNo = null;
	
	/**
	 * 料号
	 */
	private String ptNo = null;
	
	/**
	 * 对应商品名称
	 */
	private String matchName = null;
	
	/**
	 * 对应商品规格
	 */
	private String matchSpec = null;
	
	/**
	 * 对应商品单位
	 */
	private String unitName = null;
	
	/**
	 * 对应客户名称
	 */
	private String customerName = null;
	
	/**
	 * 旧出仓单据对应的入仓单据编号
	 */
	private String oldInBillNo = null;
	
	/**
	 * 旧出仓单据对应的入仓单据商品项号
	 */
	private Integer oldInBillGoodNo = null;
	
	/**
	 * 对应数量
	 */
	private Double detailQty = null;
	
	/**
	 * 对应日期
	 * @return
	 */
	private Date matchDate = null;
	
	
	
	

	public String getInBillNo() {
		return inBillNo;
	}

	public void setInBillNo(String inBillNo) {
		this.inBillNo = inBillNo;
	}

	public String getOutBillNo() {
		return outBillNo;
	}

	public void setOutBillNo(String outBillNo) {
		this.outBillNo = outBillNo;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public String getMatchSpec() {
		return matchSpec;
	}

	public void setMatchSpec(String matchSpec) {
		this.matchSpec = matchSpec;
	}

	public Double getDetailQty() {
		return detailQty;
	}

	public void setDetailQty(Double detailQty) {
		this.detailQty = detailQty;
	}

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	public String getOldInBillNo() {
		return oldInBillNo;
	}

	public void setOldInBillNo(String oldInBillNo) {
		this.oldInBillNo = oldInBillNo;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getMatchNo() {
		return matchNo;
	}

	public void setMatchNo(Integer matchNo) {
		this.matchNo = matchNo;
	}

	public Integer getOldInBillGoodNo() {
		return oldInBillGoodNo;
	}

	public void setOldInBillGoodNo(Integer oldInBillGoodNo) {
		this.oldInBillGoodNo = oldInBillGoodNo;
	}

	public Integer getOutMatchNo() {
		return outMatchNo;
	}

	public void setOutMatchNo(Integer outMatchNo) {
		this.outMatchNo = outMatchNo;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}
	

}
