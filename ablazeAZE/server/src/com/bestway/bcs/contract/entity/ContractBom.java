/*
 * Created on 2005-3-22
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contract.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseEmsImg;

/**
 * 存放合同备案BOM资料
 */
public class ContractBom extends BaseEmsImg {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 合同成品
	 */
	private ContractExg contractExg;

	/**
	 * 数量
	 */
	private Double amount = 0.0;

	/**
	 * 单耗
	 */
	private Double unitWaste;

	/**
	 * 损耗率
	 */
	private Double waste;

	/**
	 * 总金额
	 */
	private Double totalPrice;

	/**
	 * 法定数量
	 */
	private Double legalAmount;

	// /**
	// * 法定单位,对应列名legalUnit,对应表Unit
	// */
	// private Unit legalUnit;

	/**
	 * 单位重量
	 */
	private Double unitWeight;

	/**
	 * 是否主料
	 */
	private Boolean isMainImg = false;

	/**
	 * 原产地
	 */
	private Country country;

	/**
	 * 单项用量 (总耗) 
	 */
	private Double unitDosage = null;

	// /**
	// * 合同状态
	// */
	// private String declareState = null;

	/**
	 * 料件总表序号
	 */
	private Integer contractImgSeqNum = null;
	/**
	 * 料件记录号 / 备案序号
	 */
	private Integer imgCredenceNo = null;
	
	/**
	 * 非保税料件比例%
	 */
	private Double nonMnlRatio;
	// /**
	// * 料件总表Id
	// */
	// private String contractImgId = "";
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
	 * 获取数量
	 * 
	 * @return amount 数量
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置数量
	 * 
	 * @param amount
	 *            数量
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	
	/**
	 * 获取非保税料件比例%
	 */
	public Double getNonMnlRatio() {
		return nonMnlRatio;
	}
	/**
	 * 设置非保税料件比例%
	 */
	public void setNonMnlRatio(Double nonMnlRatio) {
		this.nonMnlRatio = nonMnlRatio;
	}

	/**
	 * 获取原产地
	 * 
	 * @return country 原产地
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 设置原产地
	 * 
	 * @param country
	 *            原产地
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * 获取法定数量
	 * 
	 * @return legalAmount 法定数量
	 */
	public Double getLegalAmount() {
		return legalAmount;
	}

	/**
	 * 设置法定数量
	 * 
	 * @param legalAmount
	 *            法定数量
	 */
	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	// /**
	// * 获取法定单位
	// *
	// * @return legalUnit 法定单位
	// */
	// public Unit getLegalUnit() {
	// return legalUnit;
	// }
	//
	// /**
	// * 设置法定单位
	// *
	// * @param legalUnit 法定单位
	// */
	// public void setLegalUnit(Unit legalUnit) {
	// this.legalUnit = legalUnit;
	// }

	/**
	 * 获取 总金额=单价*数量
	 * 
	 * @return Returns the totalPrice.
	 */
	public Double getTotalPrice() {
		// if(this.totalPrice!=null){
		// return this.totalPrice;
		// }
		// double amount = 0;
		// double unitPrice = 0;
		// if (this.amount != null) {
		// amount = this.amount.doubleValue();
		// }
		// if (this.getDeclarePrice() != null) {
		// unitPrice = this.getDeclarePrice().doubleValue();
		// }
		// return new Double(amount * unitPrice);
		return this.totalPrice;
	}

	/**
	 * 设置总金额
	 * 
	 * @param totalPrice
	 *            总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 获取单位重量
	 * 
	 * @return unitWeight 单位重量
	 */
	public Double getUnitWeight() {
		return unitWeight;
	}

	/**
	 * 设置单位重量
	 * 
	 * @param unitWeight
	 *            单位重量
	 */
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	/**
	 * 获取损耗率
	 * 
	 * @return waste 损耗率
	 */
	public Double getWaste() {
		return waste;
	}

	/**
	 * 设置损耗率
	 * 
	 * @param waste
	 *            损耗率
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}

	/**
	 * 获取serialVersionUID
	 * 
	 * @return Returns the serialVersionUID.
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * 获取单耗
	 * 
	 * @return unitWaste 单耗
	 */
	public Double getUnitWaste() {
		return unitWaste;
	}

	/**
	 * 设置单耗
	 * 
	 * @param unitWaste
	 *            单耗
	 */
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	/**
	 * 获取合同成品
	 * 
	 * @return contractExg 合同成品
	 */
	public ContractExg getContractExg() {
		return contractExg;
	}

	/**
	 * 设置合同成品
	 * 
	 * @param contractExg
	 *            合同成品
	 */
	public void setContractExg(ContractExg contractExg) {
		this.contractExg = contractExg;
	}

	/**
	 * 获取 单项用量 = 单耗 / (1-损耗)
	 * 
	 * @return unitDosage 单项用量
	 */
	public Double getUnitDosage() {
		// if(this.unitDosage!=null){
		// return this.unitDosage;
		// }
		// /**
		// * 单项用量 = 单耗 / (1-损耗)
		// */
		// double _unitWaste = 0;
		// double _wasteAmount = 0;
		// if (getUnitWaste() != null) {
		// _unitWaste = unitWaste.doubleValue();
		// }
		// if (getWasteAmount() != null) {
		// _wasteAmount = waste.doubleValue();
		// }
		// return new Double(_unitWaste / (1 - _wasteAmount));
		return this.unitDosage;
	}

	/**
	 * 设置单项用量
	 * 
	 * @param unitDosage
	 *            单项用量
	 */
	public void setUnitDosage(Double unitDosage) {
		this.unitDosage = unitDosage;
	}

	// /**
	// * 获取合同状态，1：申请变更
	// *
	// * @return declareState 合同状态，1：申请变更
	// */
	// public String getDeclareState() {
	// return declareState;
	// }
	//
	// /**
	// * 设置合同状态，1：申请变更
	// *
	// * @param declareState 合同状态，1：申请变更
	// */
	// public void setDeclareState(String declareState) {
	// this.declareState = declareState;
	// }

	/**
	 * 获取料件总表序号
	 * 
	 * @return contractImgSeqNum 料件总表序号
	 */
	public Integer getContractImgSeqNum() {
		return contractImgSeqNum;
	}

	/**
	 * 设置料件总表序号
	 * 
	 * @param contractImgSeqNum
	 *            料件总表序号
	 */
	public void setContractImgSeqNum(Integer contractImgSeqNum) {
		this.contractImgSeqNum = contractImgSeqNum;
	}

	// /**
	// * 获取料件总表Id
	// *
	// * @return contractImgId 料件总表Id
	// */
	// public String getContractImgId() {
	// return contractImgId;
	// }
	//
	// /**
	// * 设置料件总表Id
	// *
	// * @param contractImgId 料件总表Id
	// */
	// public void setContractImgId(String contractImgId) {
	// this.contractImgId = contractImgId;
	// }

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Integer getImgCredenceNo() {
		return imgCredenceNo;
	}

	public void setImgCredenceNo(Integer imgCredenceNo) {
		this.imgCredenceNo = imgCredenceNo;
	}

	public String getModifyMark() {
		return modifyMark;
	}

	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}

	public Boolean getIsMainImg() {
		return isMainImg;
	}

	public void setIsMainImg(Boolean isMainImg) {
		this.isMainImg = isMainImg;
	}

	public Integer getBomVersion() {
		return 0;
	}
}
