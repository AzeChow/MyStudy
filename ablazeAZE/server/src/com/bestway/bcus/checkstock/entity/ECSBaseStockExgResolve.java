package com.bestway.bcus.checkstock.entity;


/**
 * 库存成品基本折料表
 * @author lyh
 */
public class ECSBaseStockExgResolve extends ECSBaseConvert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private ECSBaseStockExg baseStockExg;
	
	/**
	 * 单耗
	 */
	private Double unitWaste;  
	
	/**
	 * 损耗
	 */
	private Double waste;  
	
	/**
	 * 单项用量
	 */
	private Double unitUsed;
	
	/**
	 * 成品耗用量
	 */
	private Double usedAmount;
	
	/**
	 * @return the stockExg
	 */
	public ECSBaseStockExg getBaseStockExg() {
		return baseStockExg;
	}

	/**
	 * @param stockExg the stockExg to set
	 */
	public void setBaseStockExg(ECSBaseStockExg badExg) {
		this.baseStockExg = badExg;
	}

	/**
	 * @return the unitWaste
	 */
	public Double getUnitWaste() {
		return unitWaste;
	}

	/**
	 * @param unitWaste the unitWaste to set
	 */
	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	/**
	 * @return the waste
	 */
	public Double getWaste() {
		return waste;
	}

	/**
	 * @param waste the waste to set
	 */
	public void setWaste(Double waste) {
		this.waste = waste;
	}

	/**
	 * @return the unitUsed
	 */
	public Double getUnitUsed() {
		return unitUsed;
	}

	/**
	 * @param unitUsed the unitUsed to set
	 */
	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}

	/**
	 * @return the usedAmount
	 */
	public Double getUsedAmount() {
		return usedAmount;
	}

	/**
	 * @param usedAmount the usedAmount to set
	 */
	public void setUsedAmount(Double usedAmount) {
		this.usedAmount = usedAmount;
	}	
	
	
	
}
