package com.bestway.bcs.verification.entity;


/**
 * 库存成品基本折料表
 * @author chl
 */
public class VFBaseStockExgConvert extends VFBaseStockImg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  库存成品
	 */
	private VFBaseStockExg stockExg;
	
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
	 * @return the stockExg
	 */
	public VFBaseStockExg getStockExg() {
		return stockExg;
	}

	/**
	 * @param stockExg the stockExg to set
	 */
	public void setStockExg(VFBaseStockExg stockExg) {
		this.stockExg = stockExg;
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
}
