package com.bestway.common.fpt.entity;

import java.io.Serializable;

public class TempFptBillExeInfo implements Serializable{
	/**
	 * 转出方的公式：
       发货单商品数量控制：1。申请数量=申请表的数量
	            2。已发货交易数量=所有发货单（所有状态，除去已撤消的发货单）的数量-收退货的数量（所有状态）	
	            3。可发货交易数量=申请数量-已发货数量(2)
	   收退货的数量控制：
	            4。已收退货的数量：收退货的数量（所有状态）
	            5。可收退货交易的数量=已发货数量（2）
      转入方的公式：
      收货单商品数量控制：
	           1。申请数量=申请表的数量
	           2。已收货交易数量=所有收货单的数量-发退货的数量（所有状态）	
	           3。可收货交易数量=申请数量-已收货数量(2)
	  发退货的数量控制：
	           4。已发退货的数量：发退货的数量（所有状态，除去已撤消的发退货单）
	           5。可发退货交易的数量=已收货数量（2）
	 */
	private double fptBillAmount = 0.0; //申报数量

	private double fptbillRemain= 0.0;// 已交易数量

	private double fptbillcurrentRemain= 0.0;//当前可交易数量

	public double getFptBillAmount() {
		return fptBillAmount;
	}

	public void setFptBillAmount(double fptBillAmount) {
		this.fptBillAmount = fptBillAmount;
	}

	public double getFptbillRemain() {
		return fptbillRemain;
	}

	public void setFptbillRemain(double fptbillRemain) {
		this.fptbillRemain = fptbillRemain;
	}

	public double getFptbillcurrentRemain() {
		return fptbillcurrentRemain;
	}

	public void setFptbillcurrentRemain(double fptbillcurrentRemain) {
		this.fptbillcurrentRemain = fptbillcurrentRemain;
	}

	
}
