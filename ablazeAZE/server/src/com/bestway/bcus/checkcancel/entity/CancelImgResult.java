/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.checkcancel.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 给别核销料件的中间过程继承
 */
public class CancelImgResult extends BaseScmEntity implements Cloneable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 期初数量
	 */
	private Double beginNum; // 

	/**
	 * 期初金额
	 */
	private Double beginMoney; // 

	/**
	 * 核增数量--单价表，进口总数量
	 */
	private Double cancelAddNum; // 

	/**
	 * 核减数量--单价表，进口总数量
	 */
	private Double cancelMinNum; // 
	/**
	 * 单价表--本期进口总数量
	 */
	private Double cancelNum;
	/**
	 * 应剩余--数量
	 */
	private Double leaveNum; //

	/**
	 * 应剩余--总价值
	 */
	private Double leaveSumPrice; // 

	/**
	 * 应剩余--总重量
	 */
	private Double leaveSumWeight; // 

	/**
	 * 核销表头
	 */
	private CancelHead cancelHead;

	/**
	 * 帐册序号
	 */
	private Integer emsSeqNum;

	/**
	 * 料件名称
	 */
	private String name;

	/**
	 * 规格型号
	 */
	private String spec;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 消耗--总数量
	 */
	private Double useSumNum;

	/**
	 * 消耗--总价值
	 */
	private Double useSumPrice;

	/**
	 * 消耗--总重量
	 */
	private Double useSumWeight;

	/**
	 * 内销--总数量
	 */
	private Double innerUseSumNum;

	/**
	 * 内销--总价值
	 */
	private Double innerUseSumPrice;

	/**
	 * 残次品--数量
	 */
	private Double leftOverImgNum;

	/**
	 * 残次品--总价值
	 */
	private Double leftOverImgSumPrice;

	/**
	 * 残次品--总重量
	 */
	private Double leftOverImgSumWeight;

	/**
	 * 实际剩余--数量
	 */
	private Double factLeaveNum;

	/**
	 * 实际剩余--总价值
	 */
	private Double factLeaveSumPrice;

	/**
	 * 实际剩余--总重量
	 */
	private Double factLeaveSumWeight;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 结余数量
	 */
	private Double resultNum;

	/**
	 * 结余金额
	 */
	private Double resultSumPrice;

	/**
	 * 国内购买
	 */
	private Double inChinaBuyNum;
	/**
	 * 其他来源数量
	 */
	private Double otherSourceNum;
	/**
	 * 其他来源金额
	 */
	private Double otherSourcePrice;
	/**
	 * 平均单价
	 */
	private Double averagePrice;	
	
	/**
	 * 核增总金额--单价表--进口总金额
	 */
	private Double cancelAddMoney;
	/**
	 * 单价表--本期进口总金额
	 */
	private Double cancelMoney;
	/**
	 * 国内购买金额
	 */
	private Double inChinaBuyMoney;
	
	/**
	 * 系统单价--单价表
	 */
	private Double sysPrice;
	
	/**
	 * 手调单价--单价表
	 */
	private Double selfPrice;

	/**
	 * 获取核销表头
	 * 
	 * @return cancelHead 核销表头
	 */
	public CancelHead getCancelHead() {
		return cancelHead;
	}

	/**
	 * 设置核销表头
	 * 
	 * @param cancelHead
	 *            核销表头
	 */
	public void setCancelHead(CancelHead cancelHead) {
		this.cancelHead = cancelHead;
	}

	public Double getOtherSourcePrice() {
		return otherSourcePrice;
	}

	public void setOtherSourcePrice(Double otherSourcePrice) {
		this.otherSourcePrice = otherSourcePrice;
	}

	/**
	 * 获取帐册序号
	 * 
	 * @return emsSeqNum 帐册序号
	 */
	public Integer getEmsSeqNum() {
		return emsSeqNum;
	}

	/**
	 * 设置帐册序号
	 * 
	 * @param emsSeqNum
	 *            帐册序号
	 */
	public void setEmsSeqNum(Integer emsSeqNum) {
		this.emsSeqNum = emsSeqNum;
	}

	/**
	 * 获取际剩余--数量
	 * 
	 * @return factLeaveNum 际剩余--数量
	 */
	public Double getFactLeaveNum() {
		return factLeaveNum;
	}

	/**
	 * 设置际剩余--数量
	 * 
	 * @param factLeaveNum
	 *            际剩余--数量
	 */
	public void setFactLeaveNum(Double factLeaveNum) {
		this.factLeaveNum = factLeaveNum;
	}

	/**
	 * 获取实际剩余--总价值
	 * 
	 * @return factLeaveSumPrice 实际剩余--总价值
	 */
	public Double getFactLeaveSumPrice() {
		return factLeaveSumPrice;
	}

	/**
	 * 设置实际剩余--总价值
	 * 
	 * @param factLeaveSumPrice
	 *            实际剩余--总价值
	 */
	public void setFactLeaveSumPrice(Double factLeaveSumPrice) {
		this.factLeaveSumPrice = factLeaveSumPrice;
	}

	/**
	 * 获取实际剩余--总重量
	 * 
	 * @return factLeaveSumWeight 实际剩余--总重量
	 */
	public Double getFactLeaveSumWeight() {
		return factLeaveSumWeight;
	}

	/**
	 * 设置实际剩余--总重量
	 * 
	 * @param factLeaveSumWeight
	 *            实际剩余--总重量
	 */
	public void setFactLeaveSumWeight(Double factLeaveSumWeight) {
		this.factLeaveSumWeight = factLeaveSumWeight;
	}

	/**
	 * 获取内销--总数量
	 * 
	 * @return innerUseSumNum 内销--总数量
	 */
	public Double getInnerUseSumNum() {
		return innerUseSumNum;
	}

	/**
	 * 设置内销--总数量
	 * 
	 * @param innerUseSumNum
	 *            内销--总数量
	 */
	public void setInnerUseSumNum(Double innerUseSumNum) {
		this.innerUseSumNum = innerUseSumNum;
	}

	/**
	 * 获取内销--总价值
	 * 
	 * @return innerUseSumPrice 内销--总价值
	 */
	public Double getInnerUseSumPrice() {
		return innerUseSumPrice;
	}

	/**
	 * 设置内销--总价值
	 * 
	 * @param innerUseSumPrice
	 *            内销--总价值
	 */
	public void setInnerUseSumPrice(Double innerUseSumPrice) {
		this.innerUseSumPrice = innerUseSumPrice;
	}

	/**
	 * 获取残次品--数量
	 * 
	 * @return leftOverImgNum 残次品--数量
	 */
	public Double getLeftOverImgNum() {
		return leftOverImgNum;
	}

	/**
	 * 设置残次品--数量
	 * 
	 * @param leftOverImgNum
	 *            残次品--数量
	 */
	public void setLeftOverImgNum(Double leftOverImgNum) {
		this.leftOverImgNum = leftOverImgNum;
	}

	/**
	 * 获取残次品--总价值
	 * 
	 * @return leftOverImgSumPrice 残次品--总价值
	 */
	public Double getLeftOverImgSumPrice() {
		return leftOverImgSumPrice;
	}

	/**
	 * 设置残次品--总价值
	 * 
	 * @param leftOverImgSumPrice
	 *            残次品--总价值
	 */
	public void setLeftOverImgSumPrice(Double leftOverImgSumPrice) {
		this.leftOverImgSumPrice = leftOverImgSumPrice;
	}

	/**
	 * 获取残次品--总重量
	 * 
	 * @return leftOverImgSumWeight 残次品--总重量
	 */
	public Double getLeftOverImgSumWeight() {
		return leftOverImgSumWeight;
	}

	/**
	 * 设置残次品--总重量
	 * 
	 * @param leftOverImgSumWeight
	 *            残次品--总重量
	 */
	public void setLeftOverImgSumWeight(Double leftOverImgSumWeight) {
		this.leftOverImgSumWeight = leftOverImgSumWeight;
	}

	/**
	 * 获取料件名称
	 * 
	 * @return name 料件名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置料件名称
	 * 
	 * @param name
	 *            料件名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取备注
	 * 
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置备注
	 * 
	 * @param note
	 *            备注
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取规格型号
	 * 
	 * @return spec 规格型号
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置规格型号
	 * 
	 * @param spec
	 *            规格型号
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}

	/**
	 * 获取消耗--总数量
	 * 
	 * @return useSumNum 消耗--总数量
	 */
	public Double getUseSumNum() {
		return useSumNum;
	}

	/**
	 * 设置消耗--总数量
	 * 
	 * @param useSumNum
	 *            消耗--总数量
	 */
	public void setUseSumNum(Double useSumNum) {
		this.useSumNum = useSumNum;
	}

	/**
	 * 获取消耗--总价值
	 * 
	 * @return useSumPrice 消耗--总价值
	 */
	public Double getUseSumPrice() {
		return useSumPrice;
	}

	/**
	 * 设置消耗--总价值
	 * 
	 * @param useSumPrice
	 *            消耗--总价值
	 */
	public void setUseSumPrice(Double useSumPrice) {
		this.useSumPrice = useSumPrice;
	}

	/**
	 * 获取消耗--总重量
	 * 
	 * @return useSumWeight 消耗--总重量
	 */
	public Double getUseSumWeight() {
		return useSumWeight;
	}

	/**
	 * 设置消耗--总重量
	 * 
	 * @param useSumWeight
	 *            消耗--总重量
	 */
	public void setUseSumWeight(Double useSumWeight) {
		this.useSumWeight = useSumWeight;
	}

	/**
	 * 获取单位
	 * 
	 * @return unit 单位
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit
	 *            单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 获取结余数量
	 * 
	 * @return resultNum 结余数量
	 */
	public Double getResultNum() {
		return resultNum;
	}

	/**
	 * 设置结余数量
	 * 
	 * @param resultNum
	 *            结余数量
	 */
	public void setResultNum(Double resultNum) {
		this.resultNum = resultNum;
	}

	/**
	 * 获取期初金额
	 * 
	 * @return beginMoney 期初金额
	 */
	public Double getBeginMoney() {
		return beginMoney;
	}

	/**
	 * 设置期初金额
	 * 
	 * @param beginMoney
	 *            期初金额
	 */
	public void setBeginMoney(Double beginMoney) {
		this.beginMoney = beginMoney;
	}

	/**
	 * 获取期初数量
	 * 
	 * @return beginNum 期初数量
	 */
	public Double getBeginNum() {
		return beginNum;
	}

	/**
	 * 设置期初数量
	 * 
	 * @param beginNum
	 *            期初数量
	 */
	public void setBeginNum(Double beginNum) {
		this.beginNum = beginNum;
	}

	/**
	 * 获取核增数量
	 * 
	 * @return cancelAddNum 核增数量
	 */
	public Double getCancelAddNum() {
		return cancelAddNum;
	}

	/**
	 * 设置核增数量
	 * 
	 * @param cancelAddNum
	 *            核增数量
	 */
	public void setCancelAddNum(Double cancelAddNum) {
		this.cancelAddNum = cancelAddNum;
	}

	/**
	 * 获取核减数量
	 * 
	 * @return cancelMinNum 核减数量
	 */
	public Double getCancelMinNum() {
		return cancelMinNum;
	}

	/**
	 * 设置核减数量
	 * 
	 * @param cancelMinNum
	 *            核减数量
	 */
	public void setCancelMinNum(Double cancelMinNum) {
		this.cancelMinNum = cancelMinNum;
	}

	/**
	 * 获取 应剩余--数量
	 * 
	 * @return leaveNum 应剩余--数量
	 */
	public Double getLeaveNum() {
		return leaveNum;
	}

	/**
	 * 设置 应剩余--数量
	 * 
	 * @param leaveNum
	 *            应剩余--数量
	 */
	public void setLeaveNum(Double leaveNum) {
		this.leaveNum = leaveNum;
	}

	/**
	 * 获取应剩余--总价值
	 * 
	 * @return leaveSumPrice 应剩余--总价值
	 */
	public Double getLeaveSumPrice() {
		return leaveSumPrice;
	}

	/**
	 * 设置应剩余--总价值
	 * 
	 * @param leaveSumPrice
	 *            应剩余--总价值
	 */
	public void setLeaveSumPrice(Double leaveSumPrice) {
		this.leaveSumPrice = leaveSumPrice;
	}

	/**
	 * 获取应剩余--总重量
	 * 
	 * @return leaveSumWeight 应剩余--总重量
	 */
	public Double getLeaveSumWeight() {
		return leaveSumWeight;
	}

	/**
	 * 设置应剩余--总重量
	 * 
	 * @param leaveSumWeight
	 *            应剩余--总重量
	 */
	public void setLeaveSumWeight(Double leaveSumWeight) {
		this.leaveSumWeight = leaveSumWeight;
	}

	public Double getInChinaBuyNum() {
		return inChinaBuyNum;
	}

	public void setInChinaBuyNum(Double inChinaBuyNum) {
		this.inChinaBuyNum = inChinaBuyNum;
	}

	public Double getOtherSourceNum() {
		return otherSourceNum;
	}

	public void setOtherSourceNum(Double otherSourceNum) {
		this.otherSourceNum = otherSourceNum;
	}

	public Double getResultSumPrice() {
		return resultSumPrice;
	}

	public void setResultSumPrice(Double resultSumPrice) {
		this.resultSumPrice = resultSumPrice;
	}

	public Double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public Double getSysPrice() {
		return sysPrice;
	}

	public void setSysPrice(Double sysPrice) {
		this.sysPrice = sysPrice;
	}

	public Double getSelfPrice() {
		return selfPrice;
	}

	public void setSelfPrice(Double selfPrice) {
		this.selfPrice = selfPrice;
	}

	public Double getCancelAddMoney() {
		return cancelAddMoney;
	}

	public void setCancelAddMoney(Double cancelAddMoney) {
		this.cancelAddMoney = cancelAddMoney;
	}

	public Double getInChinaBuyMoney() {
		return inChinaBuyMoney;
	}

	public void setInChinaBuyMoney(Double inChinaBuyMoney) {
		this.inChinaBuyMoney = inChinaBuyMoney;
	}
	/**
	 * 单价表--进口总数量
	 * @return
	 */
	public Double getCancelNum() {
		return cancelNum;
	}
	/**
	 * 单价表--进口总数量
	 * @return
	 */
	public void setCancelNum(Double cancelNum) {
		this.cancelNum = cancelNum;
	}
	/**
	 * 核增总金额--单价表--进口总金额
	 * @return
	 */
	public Double getCancelMoney() {
		return cancelMoney;
	}
	/**
	 * 核增总金额--单价表--进口总金额
	 * @return
	 */
	public void setCancelMoney(Double cancelMoney) {
		this.cancelMoney = cancelMoney;
	}
	
	
}
