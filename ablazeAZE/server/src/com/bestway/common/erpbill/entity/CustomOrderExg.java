package com.bestway.common.erpbill.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseEmsExg;
/**
 * 客户订单成品表头
 * @author ower
 *
 */
public class CustomOrderExg extends BaseEmsExg {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	
//	/**
//	 * 定单表头
//	 */
//	private CustomOrder customOrder;
	/**
	 * 定单明细
	 */
	private CustomOrderDetail customOrderDetail = null;

	/**
	 * 出口数量
	 */
	private Double amount = null;

	/**
	 * 单价
	 */
	private Double unitPrice = null; 

	/**
	 * 总金额
	 */
	private Double totalPrice = null; 

	/**
	 * 法定单位数量
	 */
	private Double legalAmount = null; 
	/**
	 * 第二法定单位数量
	 */
	private Double   secondAmount = null;


	/**
	 * 归并序号
	 */
	private Integer credenceNo = null;

	/**
	 * 原料费用
	 */
	private Double materialFee = null; 

	/**
	 * 消费国
	 */
	private Country country = null; 

	/**
	 * 加工费单价
	 */
	private Double processUnitPrice = null; 

	/**
	 * 加工费总价
	 */
	private Double processTotalPrice = null; 

	/**
	 * 单位毛重
	 */
	private Double unitGrossWeight = null; 

	/**
	 * 单位净重
	 */
	private Double unitNetWeight = null; 
    
    /**
	 * 征减免税方式
	 */
	private LevyMode levyMode = null;
	
    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark=null;
	/**
	 * 币别
	 */
	private Curr curr = null;
	
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
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Integer getCredenceNo() {
		return credenceNo;
	}

	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
	}

	public Double getLegalAmount() {
		return legalAmount;
	}

	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	public LevyMode getLevyMode() {
		return levyMode;
	}

	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	public Double getMaterialFee() {
		return materialFee;
	}

	public void setMaterialFee(Double materialFee) {
		this.materialFee = materialFee;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Double getProcessTotalPrice() {
		return processTotalPrice;
	}

	public void setProcessTotalPrice(Double processTotalPrice) {
		this.processTotalPrice = processTotalPrice;
	}

	public Double getProcessUnitPrice() {
		return processUnitPrice;
	}

	public void setProcessUnitPrice(Double processUnitPrice) {
		this.processUnitPrice = processUnitPrice;
	}

	public Double getSecondAmount() {
		return secondAmount;
	}

	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getUnitGrossWeight() {
		return unitGrossWeight;
	}

	public void setUnitGrossWeight(Double unitGrossWeight) {
		this.unitGrossWeight = unitGrossWeight;
	}

	public Double getUnitNetWeight() {
		return unitNetWeight;
	}

	public void setUnitNetWeight(Double unitNetWeight) {
		this.unitNetWeight = unitNetWeight;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

//	public CustomOrder getCustomOrder() {
//		return customOrder;
//	}
//
//	public void setCustomOrder(CustomOrder customOrder) {
//		this.customOrder = customOrder;
//	}


	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
