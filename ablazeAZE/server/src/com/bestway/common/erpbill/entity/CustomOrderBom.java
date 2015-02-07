package com.bestway.common.erpbill.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseEmsImg;

/**
 * 客户订单单耗表头
 * 
 * @author ower
 * 
 */
public class CustomOrderBom extends BaseEmsImg {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 成品
	 */
	private CustomOrderExg customOrderExg = null;
	/**
	 * 料号，只作显示用
	 */
	private String ptNo = null;

	// /**
	// * 定单表头
	// */
	// private CustomOrder customOrder;
	// /**
	// * 定单明细
	// */
	// private CustomOrderDetail customOrderDetail;

	/**
	 * 数量
	 */
	private Double amount = 0.0;

	/**
	 * 单耗
	 */
	private Double unitWaste = null;

	/**
	 * 损耗率
	 */
	private Double waste = null;

	/**
	 * 单价
	 */
	private Double unitPrice = null;

	/**
	 * 总金额
	 */
	private Double totalPrice = null;

	/**
	 * 法定数量
	 */
	private Double legalAmount = null;
	/**
	 * 单位重量
	 */
	private Double unitWeight = null;

	/**
	 * 是否主料
	 */
	private Boolean isMainImg = false;

	/**
	 * 原产地
	 */
	private Country country = null;

	/**
	 * 单项用量
	 */
	private Double unitDosage = null;

	/**
	 * 料件总表序号
	 */
	private Integer contractImgSeqNum = null;
	/**
	 * 料件备案序号
	 */
	private Integer imgCredenceNo = null;
	/**
	 * 料件总表Id
	 */
	private String contractImgId = "";
	/**
	 * 修改标志 UNCHANGE = "0"; 未修改 MODIFIED = "1"; 已修改 DELETED = "2"; 已删除 ADDED =
	 * "3"; 新增
	 */
	private String modifyMark = null;
	/**
	 * 为界面选择而用
	 */
	private Boolean isSelected = true;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getContractImgId() {
		return contractImgId;
	}

	public void setContractImgId(String contractImgId) {
		this.contractImgId = contractImgId;
	}

	public Integer getContractImgSeqNum() {
		return contractImgSeqNum;
	}

	public void setContractImgSeqNum(Integer contractImgSeqNum) {
		this.contractImgSeqNum = contractImgSeqNum;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Integer getImgCredenceNo() {
		return imgCredenceNo;
	}

	public void setImgCredenceNo(Integer imgCredenceNo) {
		this.imgCredenceNo = imgCredenceNo;
	}

	public Boolean getIsMainImg() {
		return isMainImg;
	}

	public void setIsMainImg(Boolean isMainImg) {
		this.isMainImg = isMainImg;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Double getLegalAmount() {
		return legalAmount;
	}

	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getUnitDosage() {
		return unitDosage;
	}

	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}

	public Double getUnitWaste() {
		return unitWaste;
	}

	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public Double getWaste() {
		return waste;
	}

	public void setWaste(Double waste) {
		this.waste = waste;
	}

	// public CustomOrder getCustomOrder() {
	// return customOrder;
	// }
	// public void setCustomOrder(CustomOrder customOrder) {
	// this.customOrder = customOrder;
	// }

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public CustomOrderExg getCustomOrderExg() {
		return customOrderExg;
	}

	public void setCustomOrderExg(CustomOrderExg customOrderExg) {
		this.customOrderExg = customOrderExg;
	}

	// public CustomOrderDetail getCustomOrderDetail() {
	// return customOrderDetail;
	// }
	// public void setCustomOrderDetail(CustomOrderDetail customOrderDetail) {
	// this.customOrderDetail = customOrderDetail;
	// }
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

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

}
