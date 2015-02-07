package com.bestway.bcus.enc.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 客户月结信息表
 * @author ower
 *
 */
public class CustomsMonthStatInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 帐册号
	 */
	private String emsNo;

	/**
	 * 月结期间
	 */
	private String statTerm;

	/**
	 * 料件序号
	 */
	private Integer seqNum;

	/**
	 * 商品编码
	 */
	private Complex complex;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品规格
	 */
	private String spec;

	/**
	 * 单位
	 */
	private Unit unit;

	/**
	 * 本期期初数量
	 */
	private Double preAmount;

	/**
	 * 本期期初金额
	 */
	private Double preMoney;

	/**
	 * 本期进口数量
	 */
	private Double currentImpAmount;

	/**
	 * 本期耗用数量
	 */
	private Double currentUsedAmount;

	/**
	 * 本期数量
	 */
	private Double currentAmount;

	/**
	 * 本期进口金额
	 */
	private Double currentImpMoney;
	/**
	 * 本期料件耗用金额 =期初金额＋本期金额－期末结余金额
	 */
	private Double currentUsedTotalMoney;

	// /**
	// * 本期耗用数量
	// */
	// private Double currentUsedMoney;

	// /**
	// * 本期金额
	// */
	// private Double currentMoney;

	/**
	 * 本期结存数量
	 */
	private Double endAmount;

	/**
	 * 本期结存金额
	 */
	private Double endMoney;

	/**
	 * 平局单价
	 */
	private Double averageUnitPrice;

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public Double getAverageUnitPrice() {
		return averageUnitPrice;
	}

	public void setAverageUnitPrice(Double averageUnitPrice) {
		this.averageUnitPrice = averageUnitPrice;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Double currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Double getCurrentImpAmount() {
		return currentImpAmount;
	}

	public void setCurrentImpAmount(Double currentImpAmount) {
		this.currentImpAmount = currentImpAmount;
	}

	// public Double getCurrentMoney() {
	// return currentMoney;
	// }
	//
	// public void setCurrentMoney(Double currentMoney) {
	// this.currentMoney = currentMoney;
	// }

	public Double getCurrentUsedAmount() {
		return currentUsedAmount;
	}

	public void setCurrentUsedAmount(Double currentUsedAmount) {
		this.currentUsedAmount = currentUsedAmount;
	}

	public Double getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(Double endAmount) {
		this.endAmount = endAmount;
	}

	public Double getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(Double endMoney) {
		this.endMoney = endMoney;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPreAmount() {
		return preAmount;
	}

	public void setPreAmount(Double preAmount) {
		this.preAmount = preAmount;
	}

	public Double getPreMoney() {
		return preMoney;
	}

	public void setPreMoney(Double preMoney) {
		this.preMoney = preMoney;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getStatTerm() {
		return statTerm;
	}

	public void setStatTerm(String statTerm) {
		this.statTerm = statTerm;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Double getCurrentImpMoney() {
		return currentImpMoney;
	}

	public void setCurrentImpMoney(Double currentImpMoney) {
		this.currentImpMoney = currentImpMoney;
	}

	public String getNameSpec() {
		return this.name + "/" + this.spec;
	}

	public Double getCurrentUsedTotalMoney() {
		Double preMoney = this.getPreMoney() == null ? 0.0 : this.getPreMoney();
		Double impMoney = this.getCurrentImpMoney() == null ? 0.0 : this
				.getCurrentImpMoney();
		Double endMoney = this.getEndMoney() == null ? 0.0 : this.getEndMoney();
		currentUsedTotalMoney = preMoney + impMoney - endMoney;
		return currentUsedTotalMoney;
	}

	public void setCurrentUsedTotalMoney(Double currentUsedTotalMoney) {
		this.currentUsedTotalMoney = currentUsedTotalMoney;
	}

	// public Double getCurrentUsedMoney() {
	// return currentUsedMoney;
	// }
	//
	// public void setCurrentUsedMoney(Double currentUsedMoney) {
	// this.currentUsedMoney = currentUsedMoney;
	// }
}
