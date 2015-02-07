/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fecav.entity;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempCancelSignInStat implements Serializable {

	/**
	 * 批号
	 */
	private String	batchNo;		
	/**
	 * 份数
	 */
	private Integer	count;			
	/**
	 * 金额
	 */
	private Double	money;			
	/**
	 * 本批号的开始核销号
	 */
	private String	beginBillNo;	
	/**
	 * 本批号的结束核销号
	 */
	private String	endBillNo;		
	/**
	 * 核销单据号(一段单据号)
	 */
	private String	billNoBetween;	

	/**
	 * 获得批号
	 * @return 批号
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * 设置批号
	 * @param batchNo 批号
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * 获得核销单据号(一段单据号)
	 * @return 核销单据号(一段单据号)
	 */
	public String getBillNoBetween() {
		if (beginBillNo != null && endBillNo != null) {
			return beginBillNo + "-" + endBillNo;
		} else if (beginBillNo == null && endBillNo != null) {
			return endBillNo + "-" + endBillNo;
		} else if (beginBillNo != null && endBillNo == null) {
			return beginBillNo + "-" + beginBillNo;
		} else {
			return "";
		}
	}
	

	/**
	 * 获得份数
	 * @return 份数
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * 设置份数
	 * @param count 份数
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 获得金额
	 * @return 金额
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置金额
	 * @param money 金额
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * 获得本批号的开始核销号
	 * @return 本批号的开始核销号
	 */
	public String getBeginBillNo() {
		return beginBillNo;
	}

	/**
	 * 填写本批号的开始核销号
	 * @param beginBillNo 本批号的开始核销号
	 */
	public void setBeginBillNo(String beginBillNo) {
		this.beginBillNo = beginBillNo;
	}

	/**
	 * 获得本批号的结束核销号
	 * @return 本批号的结束核销号
	 */
	public String getEndBillNo() {
		return endBillNo;
	}

	/**
	 * 填写本批号的结束核销号
	 * @param endBillNo 本批号的结束核销号
	 */
	public void setEndBillNo(String endBillNo) {
		this.endBillNo = endBillNo;
	}

}
