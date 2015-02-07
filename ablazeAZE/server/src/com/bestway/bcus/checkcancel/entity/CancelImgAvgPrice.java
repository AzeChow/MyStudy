/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 报核耗用金额的单价取于每月折算美元金额的累加
 */
public class CancelImgAvgPrice extends BaseScmEntity {

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 核销表头
	 */
	private CancelHead cancelHead;

	/**
	 * 帐册序号
	 */
	private Integer emsSeqNum = null;
	/**
	 * 开始日期
	 */
	private Date beginDate = null;
	/**
	 * 每月次数
	 */
	private Integer cancelTimes = null;
	/**
	 * 结束日期
	 */
	private Date endDate = null;
	/**
	 * 本月美元总价
	 */
	private Double dollarTotalPrice = 0.0;
	/**
	 * 本月数量
	 */
	private Double commAmount = 0.0;
	/**
	 * 单价
	 */
	private Double avgPrice = 0.0;
	/**
	 * 上一期核销的期初数量
	 * 
	 */
	private Double beginLeaveNum = 0.0;
	/**
	 * 上一期核销的期初金
	 * 
	 */
	private Double beginLeaveMoney = 0.0;
	/**
	 * 本月边角料数量
	 */
	private Double leftOverImgNum = 0.0;
	/**
	 * 本月边角料金额
	 */
	private Double leftOverImgMoney = 0.0;
	/**
	 * 本月出口耗用数量
	 */
	private Double useNum = 0.0;
	/**
	 * 本月出口耗用金额
	 */
	private Double useMoney = 0.0;
	/**
	 * 这里总的耗用数量。总耗用金额，总进口量，总进口金额 都指的是（1－2,3....）月份来汇总
	 */
	private Double impNumTotal = 0.0;
	private Double impMoneyTotal = 0.0;
	private Double useNumTotal = 0.0;
	private Double useMoneyTotal = 0.0;
	/**
	 * 总结余数量
	 */
	private Double leaveNum = 0.0;
	/**
	 * 总结余金额
	 */
	private Double leaveMoney = 0.0;

	public CancelHead getCancelHead() {
		return cancelHead;
	}

	public void setCancelHead(CancelHead cancelHead) {
		this.cancelHead = cancelHead;
	}

	public Integer getEmsSeqNum() {
		return emsSeqNum;
	}

	public void setEmsSeqNum(Integer emsSeqNum) {
		this.emsSeqNum = emsSeqNum;
	}

	public Double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}

	public Double getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(Double useMoney) {
		this.useMoney = useMoney;
	}

	/**
	 * 结余数量
	 */
	public Double getLeaveNum() {
		return leaveNum;
	}

	/**
	 * 结余数量
	 */
	public void setLeaveNum(Double leaveNum) {
		this.leaveNum = leaveNum;
	}

	/**
	 * 结余金额
	 */
	public Double getLeaveMoney() {
		return leaveMoney;
	}

	/**
	 * 结余金额
	 */
	public void setLeaveMoney(Double leaveMoney) {
		this.leaveMoney = leaveMoney;
	}

	public Double getUseNum() {
		return useNum;
	}

	public void setUseNum(Double useNum) {
		this.useNum = useNum;
	}

	public Double getDollarTotalPrice() {
		return dollarTotalPrice;
	}

	public void setDollarTotalPrice(Double dollarTotalPrice) {
		this.dollarTotalPrice = dollarTotalPrice;
	}

	public Double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

	/**
	 * 边角料数量
	 */

	/**
	 * 边角料数量
	 */

	/**
	 * 边角料金额
	 */

	/**
	 * 边角料金额
	 */

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

	public Integer getCancelTimes() {
		return cancelTimes;
	}

	public void setCancelTimes(Integer cancelTimes) {
		this.cancelTimes = cancelTimes;
	}

	public Double getImpNumTotal() {
		return impNumTotal;
	}

	public void setImpNumTotal(Double impNumTotal) {
		this.impNumTotal = impNumTotal;
	}

	public Double getImpMoneyTotal() {
		return impMoneyTotal;
	}

	public void setImpMoneyTotal(Double impMoneyTotal) {
		this.impMoneyTotal = impMoneyTotal;
	}

	public Double getUseNumTotal() {
		return useNumTotal;
	}

	public void setUseNumTotal(Double useNumTotal) {
		this.useNumTotal = useNumTotal;
	}

	public Double getUseMoneyTotal() {
		return useMoneyTotal;
	}

	public void setUseMoneyTotal(Double useMoneyTotal) {
		this.useMoneyTotal = useMoneyTotal;
	}

	public Double getLeftOverImgNum() {
		return leftOverImgNum;
	}

	public void setLeftOverImgNum(Double leftOverImgNum) {
		this.leftOverImgNum = leftOverImgNum;
	}

	public Double getLeftOverImgMoney() {
		return leftOverImgMoney;
	}

	public void setLeftOverImgMoney(Double leftOverImgMoney) {
		this.leftOverImgMoney = leftOverImgMoney;
	}

	public Double getBeginLeaveNum() {
		return beginLeaveNum;
	}

	public void setBeginLeaveNum(Double beginLeaveNum) {
		this.beginLeaveNum = beginLeaveNum;
	}

	public Double getBeginLeaveMoney() {
		return beginLeaveMoney;
	}

	public void setBeginLeaveMoney(Double beginLeaveMoney) {
		this.beginLeaveMoney = beginLeaveMoney;
	}

}
