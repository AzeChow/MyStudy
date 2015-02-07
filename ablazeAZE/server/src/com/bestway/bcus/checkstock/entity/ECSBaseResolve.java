package com.bestway.bcus.checkstock.entity;


/**
 * 基础bom分解表(成品耗用数据)
 * 
 * @author chl
 * @version 1.0
 * @createDate 2013-9-16
 */
public class ECSBaseResolve extends ECSBaseStockItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    /**
     * 折算成品
     */
    private ECSBaseConvert baseExg;
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
	 * @return the baseExg
	 */
	public ECSBaseConvert getBaseExg() {
		return baseExg;
	}
	/**
	 * @param baseExg the baseExg to set
	 */
	public void setBaseExg(ECSBaseConvert baseExg) {
		this.baseExg = baseExg;
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
