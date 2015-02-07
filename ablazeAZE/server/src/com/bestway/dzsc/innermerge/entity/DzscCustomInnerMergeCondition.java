package com.bestway.dzsc.innermerge.entity;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 
 * 存放电子手册自定义归并条件
 * 
 * @author yp
 */
public class DzscCustomInnerMergeCondition extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 物料编号－归并条件
	 */
	private String conMaterielCode; 
	
	/**
	 * 物料名称－归并条件
	 */
	private String conMaterielName;
	
	/**
	 * 物料规格－归并条件
	 */
	private String conMaterielSpec;
	
	/**
	 * 物料最低价格－归并条件
	 */
	private Double conLowestPrice;
	
	/**
	 * 物料价格最高上浮－归并条件
	 */
	private Double conPriceUpExceed;
	
	/**
	 * 归并后的商品编码
	 */
	private Complex afterComplex; 
	
	/**
	 * 归并后的商品名称
	 */
	private String afterMaterielName;
	
	/**
	 * 归并后的法定单位
	 */
	private Unit afterLegalUnit;
	
	/**
	 * 归并后的备案单位
	 */
	private Unit afterMemoUnit;

	
	/**
	 * 物料类型
	 */
	private String materielType;
	
	/**
	 * 获取物料类型
	 * 
	 * @return materielType 物料类型
	 */
	public String getMaterielType() {
		return materielType;
	}
	
	/**
	 * 设置物料类型
	 * 
	 * @param materielType 物料类型
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
	
	/**
	 * 获取物料类型
	 * 
	 * @return afterComplex 物料类型
	 */
	public Complex getAfterComplex() {
		return afterComplex;
	}
	
	/**
	 * 设置物料类型
	 * 
	 * @param afterComplex 物料类型
	 */
	public void setAfterComplex(Complex afterComplex) {
		this.afterComplex = afterComplex;
	}
	
	/**
	 * 获取归并后的法定单位
	 * 
	 * @return afterLegalUnit 归并后的法定单位
	 */
	public Unit getAfterLegalUnit() {
		return afterLegalUnit;
	}
	
	/**
	 * 设置归并后的法定单位
	 * 
	 * @param afterLegalUnit 归并后的法定单位
	 */
	public void setAfterLegalUnit(Unit afterLegalUnit) {
		this.afterLegalUnit = afterLegalUnit;
	}
	
	/**
	 * 获取归并后的商品名称
	 * 
	 * @return afterMaterielName 归并后的商品名称
	 */
	public String getAfterMaterielName() {
		return afterMaterielName;
	}
	
	/**
	 * 设置归并后的商品名称
	 * 
	 * @param afterMaterielName 归并后的商品名称
	 */
	public void setAfterMaterielName(String afterMaterielName) {
		this.afterMaterielName = afterMaterielName;
	}
	
	/**
	 * 获取归并后的备案单位
	 * 
	 * @return afterMemoUnit 归并后的备案单位
	 */
	public Unit getAfterMemoUnit() {
		return afterMemoUnit;
	}
	
	/**
	 * 设置归并后的备案单位
	 * 
	 * @param afterMemoUnit 归并后的备案单位
	 */
	public void setAfterMemoUnit(Unit afterMemoUnit) {
		this.afterMemoUnit = afterMemoUnit;
	}
	
	/**
	 * 获取物料编号－归并条件
	 * 
	 * @return conMaterielCode 物料编号－归并条件
	 */
	public String getConMaterielCode() {
		return conMaterielCode;
	}
	
	/**
	 * 设置物料编号－归并条件
	 * 
	 * @param conMaterielCode 物料编号－归并条件
	 */
	public void setConMaterielCode(String conMaterielCode) {
		this.conMaterielCode = conMaterielCode;
	}
	
	/**
	 * 获取物料名称－归并条件
	 * 
	 * @return conMaterielName 物料名称－归并条件
	 */
	public String getConMaterielName() {
		return conMaterielName;
	}
	
	/**
	 * 设置物料名称－归并条件
	 * 
	 * @param conMaterielName 物料名称－归并条件
	 */
	public void setConMaterielName(String conMaterielName) {
		this.conMaterielName = conMaterielName;
	}
	
	/**
	 * 获取物料规格－归并条件
	 * 
	 * @return conMaterielSpec 物料规格－归并条件
	 */
	public String getConMaterielSpec() {
		return conMaterielSpec;
	}
	
	/**
	 * 设置物料规格－归并条件
	 * 
	 * @param conMaterielSpec 物料规格－归并条件
	 */
	public void setConMaterielSpec(String conMaterielSpec) {
		this.conMaterielSpec = conMaterielSpec;
	}
	
	/**
	 * 获取物料最低价格－归并条件
	 * 
	 * @return conPrice 物料最低价格－归并条件
	 */
	public Double getConLowestPrice() {
		return conLowestPrice;
	}
	
	/**
	 * 设置物料最低价格－归并条件
	 * 
	 * @param conPrice 物料最低价格－归并条件
	 */
	public void setConLowestPrice(Double conPrice) {
		this.conLowestPrice = conPrice;
	}
	
	/**
	 * 获取物料价格最高上浮－归并条件
	 * 
	 * @return conPriceUpExceed 物料价格最高上浮－归并条件
	 */
	public Double getConPriceUpExceed() {
		return conPriceUpExceed;
	}
	
	/**
	 * 设置物料价格最高上浮－归并条件
	 * 
	 * @param conPriceUpExceed 物料价格最高上浮－归并条件
	 */
	public void setConPriceUpExceed(Double conPriceUpExceed) {
		this.conPriceUpExceed = conPriceUpExceed;
	}
}

