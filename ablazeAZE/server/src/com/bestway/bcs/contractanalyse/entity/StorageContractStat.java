package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;
import java.util.List;

import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 用于报关分析－－库存分析 纵向显示
 */
public class StorageContractStat implements Serializable {
	/**
	 * 记录号
	 */
	private Integer credenceNo = null; 
	/**
	 * 进口合同号
	 */
	private String impContractNo = null; 
	
	/**
	 * 出口合同号
	 */
	private String expContractNo = null; 
	
	/**
	 * 商品编码
	 */
	private String complexCode = null; 
	
	/**
	 * 品名
	 */
	private String name = null;
	
	/**
	 * 规格
	 */
	private String spec = null;
	
	/**
	 * 单位
	 */
	private Unit unit = null; 
	
	/**
	 * 合同定量
	 */
	private Double contractRation = null; 
	
	/**
	 * 总出口量
	 */
	private Double totalExpAmount = null; 
	
	/**
	 * 返工数量
	 */
	private Double returnAmount = null; 
	
	/**
	 * 大单出口量
	 */
	private Double orderExpAmount = null; 
	
	/**
	 * 可以出口数量
	 */
	private Double canExpRemain = null; 
	
	/**
	 * 现在出口数量
	 */
	private Double nowExpAmount = null; 
	
	/**
	 * 总进口量
	 */
	private Double totalImpAmount = null; 
	
	/**
	 * 大单进口量
	 */
	private Double orderImpAmount = null; 
	
	/**
	 * 退料出口量
	 */
	private Double backMaterielExpAmount = null; 
	
	/**
	 * 成品使用量
	 */
	private Double finishProductDosageAmount = null; 
	
	/**
	 * 余料库存
	 */
	private Double remainStorageAmount = null; 
	
	/**
	 * 现进口量
	 */
	private Double nowImpAmount = null; 



	/**
	 * 获取可以出口数量
	 * 
	 * @return canExpRemain 可以出口数量
	 */
	public Double getCanExpRemain() {
		return canExpRemain;
	}

	/**
	 * 设置可以出口数量
	 * 
	 * @param canExpRemain 可以出口数量
	 */
	public void setCanExpRemain(Double canExpRemain) {
		this.canExpRemain = canExpRemain;
	}

	/**
	 * 获取商品编码
	 * 
	 * @return complexCode 商品编码
	 */
	public String getComplexCode() {
		return complexCode;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param complexCode 商品编码
	 */ 
	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	/**
	 * 获取合同定量
	 * 
	 * @return contractRation 合同定量
	 */
	public Double getContractRation() {
		return contractRation;
	}

	/**
	 * 设置合同定量
	 * 
	 * @param contractRation 合同定量
	 */
	public void setContractRation(Double contractRation) {
		this.contractRation = contractRation;
	}

	/**
	 * 获取进口合同号
	 * 
	 * @return impContractNo 进口合同号
	 */ 
	public String getImpContractNo() {
		return impContractNo;
	}

	/**
	 * 设置进口合同号
	 * 
	 * @param impContractNo 进口合同号
	 */
	public void setImpContractNo(String impContractNo) {
		this.impContractNo = impContractNo;
	}




	/**
	 * 获取现在出口数量
	 * 
	 * @return nowExpAmount 现在出口数量
	 */
	public Double getNowExpAmount() {
		return nowExpAmount;
	}

	/**
	 * 设置现在出口数量
	 * 
	 * @param nowExpAmount 现在出口数量
	 */
	public void setNowExpAmount(Double nowExpAmount) {
		this.nowExpAmount = nowExpAmount;
	}

	/**
	 * 获取大单出口量
	 * 
	 * @return orderExpAmount 大单出口量
	 */ 
	public Double getOrderExpAmount() {
		return orderExpAmount;
	}

	/**
	 * 设置大单出口量 
	 * 
	 * @param orderExpAmount 大单出口量 
	 */
	public void setOrderExpAmount(Double orderExpAmount) {
		this.orderExpAmount = orderExpAmount;
	}

	/**
	 * 获取返工数量
	 * 
	 * @return returnAmount 返工数量
	 */
	public Double getReturnAmount() {
		return returnAmount;
	}

	/**
	 * 设置返工数量
	 * 
	 * @param returnAmount 返工数量
	 */
	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}

	/**
	 * 获取总出口量
	 * 
	 * @return totalExpAmount 总出口量
	 */
	public Double getTotalExpAmount() {
		return totalExpAmount;
	}

	/**
	 * 设置总出口量
	 * 
	 * @param totalExpAmount 总出口量
	 */
	public void setTotalExpAmount(Double totalExpAmount) {
		this.totalExpAmount = totalExpAmount;
	}

	/**
	 * 获取单位
	 * 
	 * @return unit 单位
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * 设置单位
	 * 
	 * @param unit 单位
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	/**
	 * 获取退料出口量
	 * 
	 * @return backMaterielExpAmount 退料出口量
	 */
	public Double getBackMaterielExpAmount() {
		return backMaterielExpAmount;
	}

	/**
	 * 设置退料出口量
	 *  
	 * @param backMaterielExpAmount 退料出口量
	 */
	public void setBackMaterielExpAmount(Double backMaterielExpAmount) {
		this.backMaterielExpAmount = backMaterielExpAmount;
	}

	/**
	 * 获取成品使用量
	 * 
	 * @return finishProductDosageAmount 成品使用量
	 */
	public Double getFinishProductDosageAmount() {
		return finishProductDosageAmount;
	}

	/**
	 * 设置成品使用量
	 * 
	 * @param finishProductDosageAmount 成品使用量
	 */
	public void setFinishProductDosageAmount(Double finishProductDosageAmount) {
		this.finishProductDosageAmount = finishProductDosageAmount;
	}

	/**
	 * 获取现进口量
	 * 
	 * @return nowImpAmount 现进口量
	 */
	public Double getNowImpAmount() {
		return nowImpAmount;
	}

	/**
	 * 设置现进口量
	 * 
	 * @param nowImpAmount 现进口量
	 */
	public void setNowImpAmount(Double nowImpAmount) {
		this.nowImpAmount = nowImpAmount;
	}

	/**
	 * 获取大单进口量
	 * 
	 * @return orderImpAmount 大单进口量
	 */
	public Double getOrderImpAmount() {
		return orderImpAmount;
	}

	/**
	 * 设置大单进口量
	 * 
	 * @param orderImpAmount 大单进口量
	 */
	public void setOrderImpAmount(Double orderImpAmount) {
		this.orderImpAmount = orderImpAmount;
	}

	/**
	 * 获取余料库存
	 * 
	 * @return remainStorageAmount 余料库存
	 */
	public Double getRemainStorageAmount() {
		return remainStorageAmount;
	}

	/**
	 * 设置余料库存
	 * 
	 * @param remainStorageAmount 余料库存
	 */
	public void setRemainStorageAmount(Double remainStorageAmount) {
		this.remainStorageAmount = remainStorageAmount;
	}

	/**
	 * 获取总进口量
	 * 
	 * @return totalImpAmount 总进口量
	 */
	public Double getTotalImpAmount() {
		return totalImpAmount;
	}

	/**
	 * 设置总进口量
	 * 
	 * @param totalImpAmount 总进口量
	 */
	public void setTotalImpAmount(Double totalImpAmount) {
		this.totalImpAmount = totalImpAmount;
	}

	/**
	 * 获取出口合同号
	 * 
	 * @return expContractNo 出口合同号
	 */
	public String getExpContractNo() {
		return expContractNo;
	}

	/**
	 * 设置出口合同号
	 * 
	 * @param expContractNo 出口合同号
	 */
	public void setExpContractNo(String expContractNo) {
		this.expContractNo = expContractNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Integer getCredenceNo() {
		return credenceNo;
	}

	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
	}

}
