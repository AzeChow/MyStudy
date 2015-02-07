/*
 * Created on 2004-9-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 盘点表明细
 * @author luosheng  checked 2008-11-29
 */
public class CheckDetail extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 * 盘点表表头
	 */
	private CheckMaster checkMaster;
	/**
	 * 商品编号
	 */
	private String ptPart;   
	/**
	 *  品名
	 */
	private String ptName;      
	/**
	 * 规格型号
	 */
	private String ptSpec;     
	/**
	 *单位
	 */
	private String ptUnit;     
	/**
	 * 库存数量
	 */
	private Double ptAmount;   
	/**
	 * 实际库存数量
	 */
	private Double factPtAmount; 
	/**
	 * 差值
	 */
	private Double removeAmount;
	
	/**
	 * 	取得盘点表表头  自定义CheckMaster型
	 * @return checkMaster 盘点表表头.
	 */
	public CheckMaster getCheckMaster() {
		return checkMaster;
	}
	/**
	 * 设置盘点表表头   自定义CheckMaster型
	 * @param checkMaster 盘点表表头.
	 */
	public void setCheckMaster(CheckMaster checkMaster) {
		this.checkMaster = checkMaster;
	}
	/**
	 * 取得实际库存数量
	 * @return factPtAmount 实际库存数量.
	 */
	public Double getFactPtAmount() {
		return factPtAmount;
	}
	/**
	 * 设置实际库存数量
	 * @param factPtAmount 实际库存数量.
	 */
	public void setFactPtAmount(Double factPtAmount) {
		this.factPtAmount = factPtAmount;
	}
	/**
	 * 取得库存数量
	 * @return ptAmount 库存数量.
	 */
	public Double getPtAmount() {
		return ptAmount;
	}
	/**
	 * 设置库存数量
	 * @param ptAmount 库存数量.
	 */
	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}
	/**
	 * 取得商品名称
	 * @return  ptName 商品名称.
	 */
	public String getPtName() {
		return ptName;
	}
	/**
	 * 设置商品名称
	 * @param ptName 商品名称.
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}
	/**
	 * 取得商品编号
	 * @return ptPart 商品编号.
	 */
	public String getPtPart() {
		return ptPart;
	}
	/**
	 * 设置商品编号
	 * @param ptPart 商品编号.
	 */
	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}
	/**
	 * 取得规格
	 * @return ptSpec 规格.
	 */
	public String getPtSpec() {
		return ptSpec;
	}
	/**
	 * 设置规格
	 * @param ptSpec 规格.
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}
	/**
	 * 取得单位
	 * @return  ptUnit 单位.
	 */
	public String getPtUnit() {
		return ptUnit;
	}
	/**
	 * 设置单位
	 * @param ptUnit 单位.
	 */
	public void setPtUnit(String ptUnit) {
		this.ptUnit = ptUnit;
	}
	/**
	 * 取得差值
	 * @return removeAmount 差值.
	 */
	public Double getRemoveAmount() {
		return removeAmount;
	}
	/**
	 * 设置差值
	 * @param removeAmount 差值.
	 */
	public void setRemoveAmount(Double removeAmount) {
		this.removeAmount = removeAmount;
	}
}