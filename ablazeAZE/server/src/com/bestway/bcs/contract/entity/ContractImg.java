/*
 * Created on 2005-3-22
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contract.entity;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseEmsImg;

/**
 * 存放合同备案料件资料
 */
public class ContractImg extends BaseEmsImg {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 合同表头
	 */
	private Contract contract;

	/**
	 * 备案资料库备案序号 /  记录号
	 */
	private Integer credenceNo;

	/**
	 * 数量
	 */
	private Double amount;

	/**
	 * 损耗数量
	 */
	private Double wasteAmount;

	/**
	 * 总金额
	 */
	private Double totalPrice;

	/**
	 * 法定单位总量
	 */
	private Double legalTotalAmount;

	/**
	 * 法定单位数量
	 */
	private Double legalAmount;

	/**
	 * 第一法定比例因子
	 */
	private Double legalUnitGene;

	/**
	 * 第二法定单位数量
	 */
	private Double secondAmount;

	/**
	 * 第二法定比例因子
	 * 
	 */
	private Double legalUnit2Gene;

	/**
	 * 单位重量
	 */
	private Double unitWeight;

	/**
	 * 总重量
	 */
	private Double totalWeight;

	/**
	 * 是否主料
	 */
	private Boolean isMainImg = false;

	/**
	 * 原产地
	 */
	private Country country;

	/**
	 * 废料是否报关
	 */
	private Boolean isApplyToCustoms = false;

	/**
	 * 征减免税方式
	 */
	private LevyMode levyMode = null;

	/**
	 * 修改标志 UNCHANGE = "0"; 未修改 MODIFIED = "1"; 已修改 DELETED = "2"; 已删除 ADDED =
	 * "3"; 新增
	 */
	private String modifyMark = null;

	/**
	 * 方式（我也不知道是什么方式，是客户填的）
	 */
	private String mannerNote = null;

	/**
	 * 征税比例
	 * 
	 */
	private Double dutyRate = null;

	public String getMannerNote() {
		return mannerNote;
	}

	public void setMannerNote(String mannerNote) {
		this.mannerNote = mannerNote;
	}

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
	 * 获取合同表头
	 * 
	 * @return contract 合同表头
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * 设置合同表头
	 * 
	 * @param contract
	 *            合同表头
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
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
	 * 获取凭证序号(记录号)
	 * 
	 * @return credenceNo 凭证序号(记录号)
	 */
	public Integer getCredenceNo() {
		return credenceNo;
	}

	/**
	 * 设置凭证序号(记录号)
	 * 
	 * @param credenceNo
	 *            凭证序号(记录号)
	 */
	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
	}

	/**
	 * 获取废料是否报关
	 * 
	 * @return isApplyToCustoms 废料是否报关
	 */
	public Boolean getIsApplyToCustoms() {
		return isApplyToCustoms;
	}

	/**
	 * 设置废料是否报关
	 * 
	 * @param isApplyToCustoms
	 *            废料是否报关
	 */
	public void setIsApplyToCustoms(Boolean isApplyToCustoms) {
		this.isApplyToCustoms = isApplyToCustoms;
	}

	/**
	 * 获取法定单位数量
	 * 
	 * @return legalAmount 法定单位数量
	 */
	public Double getLegalAmount() {
		return legalAmount;
	}

	/**
	 * 设置法定单位数量
	 * 
	 * @param legalAmount
	 *            法定单位数量
	 */
	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	/**
	 * 获取法定单位总量
	 * 
	 * @return legalTotalAmount 法定单位总量
	 */
	public Double getLegalTotalAmount() {
		return legalTotalAmount;
	}

	/**
	 * 设置法定单位总量
	 * 
	 * @param legalTotalAmount
	 *            法定单位总量
	 */
	public void setLegalTotalAmount(Double legalTotalAmount) {
		this.legalTotalAmount = legalTotalAmount;
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
	 * 获取 总金额 = 单价 * 申报数量
	 * 
	 * @return totalPrice 总金额
	 */
	public Double getTotalPrice() {
		return totalPrice;
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
	 * 获取总重量
	 * 
	 * @return totalWeight 总重量
	 */
	public Double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * 设置总重量
	 * 
	 * @param totalWeight
	 *            总重量
	 */
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
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
	 * 获取损耗数量
	 * 
	 * @return wasteAmount 损耗数量
	 */
	public Double getWasteAmount() {
		return wasteAmount;
	}

	/**
	 * 设置损耗数量
	 * 
	 * @param wasteAmount
	 *            损耗数量
	 */
	public void setWasteAmount(Double wasteAmount) {
		this.wasteAmount = wasteAmount;
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
	 * 获取征减免税方式
	 * 
	 * @return levyMode 征减免税方式
	 */
	public LevyMode getLevyMode() {
		return levyMode;
	}

	/**
	 * 设置征减免税方式
	 * 
	 * @param levyMode
	 *            征减免税方式
	 */
	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	// /**
	// * 获取合同状态，1：申请变更
	// *
	// * @return declareState 合同状态，1：申请变更
	// */
	// public String getDeclareState() {
	// return declareState;
	// }
	// /**
	// * 设置合同状态，1：申请变更
	// *
	// * @param declareState 合同状态，1：申请变更
	// */
	// public void setDeclareState(String declareState) {
	// this.declareState = declareState;
	// }
	/**
	 * 获取第二法定单位数量
	 * 
	 * @return secondAmount 第二法定单位数量
	 */
	public Double getSecondAmount() {
		return secondAmount;
	}

	/**
	 * 设置第二法定单位数量
	 * 
	 * @param secondAmount
	 *            第二法定单位数量
	 */
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}

	// /**
	// * 获取第二法定单位
	// *
	// * @return secondUnit 第二法定单位
	// */
	// public Unit getSecondUnit() {
	// return secondUnit;
	// }
	// /**
	// * 设置第二法定单位
	// *
	// * @param secondUnit 第二法定单位
	// */
	// public void setSecondUnit(Unit secondUnit) {
	// this.secondUnit = secondUnit;
	// }
	/**
	 * 商品编号
	 * 
	 * @return
	 */
	public String getComplexTCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() < 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(0,8);
		}
	}
	/**
	 * 附加编码
	 * 
	 * @return
	 */
	public String getComplexSCode() {
		if (this.getComplex() == null
				|| this.getComplex().getCode().length() <= 8) {
			return "";
		} else {
			return this.getComplex().getCode().substring(8,
					this.getComplex().getCode().length());
		}
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

	public String getMainImgMark() {
		if (isMainImg != null && isMainImg) {
			return "1";
		} else {
			return "2";
		}
	}

	public Double getLegalUnitGene() {
		return legalUnitGene;
	}

	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	public Double getDutyRate() {
		if(dutyRate==null){
			return 0.0;
		}
		return dutyRate;
	}

	public void setDutyRate(Double dutyRate) {
		this.dutyRate = dutyRate;
	}
}
