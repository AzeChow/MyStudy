package com.bestway.common.erpbill.entity;

import java.io.Serializable;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;

public class TempOrderBom implements Serializable {
	/**
	 * 电子手册内部归并
	 */
	private DzscInnerMergeData dzscInnerMergeData = null;
	/**
	 * 纸质手册内部归并
	 */
	
	private BcsInnerMerge bcsInnerMerge = null;
	/**
	 * 定单明细
	 */
	private CustomOrderDetail customOrderDetail = null;

	/**
	 * 料号
	 */
	private String ptNo = null;
	
	/**
	 * 单耗
	 */
	private Double unitWaste = null;  
	
	/**
	 * 损耗
	 */
	private Double waste = null;  
	
	/**
	 * 单项用量
	 */
	private Double unitUsed = null;
	
	/**
	 * 单价
	 */
	private Double ptPrice = null;
	/**
	 * 数量
	 */
	private Double amount = null;
	/**
	 * 币别
	 */
	private Curr curr = null;
	/**
	 * 物料与报关商品的折算系数
	 */
	private Double				unitConvert = null;
	
	/**
	 * 已转厂数量
	 */
	private Double transNum = null;
	/**
	 * 已转合同数量
	 */
	private Double contractNum = null;
	/**
	 * 未转厂数量
	 */
	private Double notTransNum = null;
	/**
	 * 未转合同数量
	 */
	private Double notContractNum = null;
	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public Double getPtPrice() {
		return ptPrice;
	}

	public void setPtPrice(Double ptPrice) {
		this.ptPrice = ptPrice;
	}

	public Double getUnitUsed() {
		return unitUsed;
	}

	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}

	public Double getUnitWaste() {
		return unitWaste;
	}

	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	public Double getWaste() {
		return waste;
	}

	public void setWaste(Double waste) {
		this.waste = waste;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public BcsInnerMerge getBcsInnerMerge() {
		return bcsInnerMerge;
	}

	public void setBcsInnerMerge(BcsInnerMerge bcsInnerMerge) {
		this.bcsInnerMerge = bcsInnerMerge;
	}

	public DzscInnerMergeData getDzscInnerMergeData() {
		return dzscInnerMergeData;
	}

	public void setDzscInnerMergeData(DzscInnerMergeData dzscInnerMergeData) {
		this.dzscInnerMergeData = dzscInnerMergeData;
	}

	public CustomOrderDetail getCustomOrderDetail() {
		return customOrderDetail;
	}

	public void setCustomOrderDetail(CustomOrderDetail customOrderDetail) {
		this.customOrderDetail = customOrderDetail;
	}

	public Double getContractNum() {
		return contractNum;
	}

	public void setContractNum(Double contractNum) {
		this.contractNum = contractNum;
	}

	public Double getNotContractNum() {
		return notContractNum;
	}

	public void setNotContractNum(Double notContractNum) {
		this.notContractNum = notContractNum;
	}

	public Double getNotTransNum() {
		return notTransNum;
	}

	public void setNotTransNum(Double notTransNum) {
		this.notTransNum = notTransNum;
	}

	public Double getTransNum() {
		return transNum;
	}

	public void setTransNum(Double transNum) {
		this.transNum = transNum;
	} 
}
