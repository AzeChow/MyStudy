package com.bestway.bcus.cas.entity;

import java.io.Serializable;

/**
 * 临时对象,不会跟数据库进行映射
 * 
 * @author ls
 * 
 */
public class TempMonthStorage implements Serializable {
	
	/**
	 * 工厂料号 || 报关单位
	 */
	private String	ptPart					= null;
	/**
	 * 商品名称
	 */
	private String	ptName					= null;
	/**
	 * 规格型号
	 */
	private String	ptSpec					= null;	
	/**
	 * 上月结存数量
	 */
	private Double	frontMonthPtAmount		= 0.0;
	/**
	 * 上月结存重量
	 */
	private Double	frontMonthNetWeight		= 0.0;
	/**
	 * 本月进仓数量
	 */
	private Double	importPtAmount			= 0.0;
	/**
	 * 本月进仓重量
	 */
	private Double	importNetWeight			= 0.0;
	/**
	 * 本月出仓数量
	 */
	private Double	exportPtAmount			= 0.0;
	/**
	 * 本月出仓重量
	 */
	private Double	exportNetWeight			= 0.0;
	/**
	 * 当月结存数量
	 */
	private Double	currentMonthPtAmount	= 0.0;
	/**
	 * 当月结存重量
	 */
	private Double	currentMonthNetWeight	= 0.0;

	
	
	/**
	 * 当月结存重量 进的-出的+上月结存
	 * @return 本月进仓重量-本月出仓重量+上月结存重量
	 */
	public Double getCurrentMonthNetWeight() {
		return this.importNetWeight - this.exportNetWeight + this.frontMonthNetWeight ;
	}

	/**
	 * 设置当月结存重量
	 * @param currentMonthNetWeight 当月结存重量
	 */
	public void setCurrentMonthNetWeight(Double currentMonthNetWeight) {
		this.currentMonthNetWeight = currentMonthNetWeight;
	}

	/**
	 * 当月结存数量=进的-出的+上月结存
	 * @return  本月进仓数量-本月出仓数量+上月结存数量
	 */
	public Double getCurrentMonthPtAmount() {
		return this.importPtAmount - this.exportPtAmount + this.frontMonthPtAmount ;		
	}

	/**
	 * 设置当月结存数量
	 * @param currentMonthPtAmount 当月结存数量
	 */
	public void setCurrentMonthPtAmount(Double currentMonthPtAmount) {
		this.currentMonthPtAmount = currentMonthPtAmount;
	}

	/**
	 * 取得本月出仓重量
	 * @return 本月出仓重量
	 */
	public Double getExportNetWeight() {
		return exportNetWeight;
	}

	/**
	 * 设置本月出仓重量
	 * @param exportNetWeight 本月出仓重量
	 */
	public void setExportNetWeight(Double exportNetWeight) {
		this.exportNetWeight = exportNetWeight;
	}

	/**
	 * 取得本月出仓数量
	 * @return 本月出仓数量
	 */
	public Double getExportPtAmount() {
		return exportPtAmount;
	}

	/**
	 * 设置出仓数量
	 * @param exportPtAmount 出仓数量
	 */
	public void setExportPtAmount(Double exportPtAmount) {
		this.exportPtAmount = exportPtAmount;
	}

	/**
	 * 取得上月结存重量
	 * @return 上月结存重量
	 */
	public Double getFrontMonthNetWeight() {
		return frontMonthNetWeight;
	}

	/**
	 * 设置上月结存重量
	 * @param frontMonthNetWeight 上月结存重量
	 */
	public void setFrontMonthNetWeight(Double frontMonthNetWeight) {
		this.frontMonthNetWeight = frontMonthNetWeight;
	}

	/**
	 * 取得上月结存数量
	 * @return 上月结存数量
	 */
	public Double getFrontMonthPtAmount() {
		return frontMonthPtAmount;
	}

	/**
	 * 设置上月结存数量
	 * @param frontMonthPtAmount 上月结存数量
	 */
	public void setFrontMonthPtAmount(Double frontMonthPtAmount) {
		this.frontMonthPtAmount = frontMonthPtAmount;
	}

	

	/**
	 * 取得本月进仓重量
	 * @return 本月进仓重量
	 */
	public Double getImportNetWeight() {
		return importNetWeight;
	}

	/**
	 * 设置本月进仓重量
	 * @param importNetWeight 本月进仓重量
	 */
	public void setImportNetWeight(Double importNetWeight) {
		this.importNetWeight = importNetWeight;
	}

	/**
	 * 取得本月进仓数量
	 * @return 本月进仓数量
	 */
	public Double getImportPtAmount() {
		return importPtAmount;
	}

	/**
	 * 设置本月进仓数量
	 * @param importPtAmount 本月进仓数量
	 */
	public void setImportPtAmount(Double importPtAmount) {
		this.importPtAmount = importPtAmount;
	}

	/**
	 * 取得商品名称
	 * @return 商品名称
	 */
	public String getPtName() {
		return ptName;
	}

	/**
	 * 设置商品名称
	 * @param ptName 商品名称
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	/**
	 * 取得工厂料号
	 * @return 工厂料号
	 */
	public String getPtPart() {
		return ptPart;
	}

	/**
	 * 设置工厂料号
	 * @param ptPart 工厂料号
	 */
	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	/**
	 * 取得规格
	 * @return 规格
	 */
	public String getPtSpec() {
		return ptSpec;
	}

	/**
	 * 设置规格
	 * @param ptSpec 规格
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	

}
