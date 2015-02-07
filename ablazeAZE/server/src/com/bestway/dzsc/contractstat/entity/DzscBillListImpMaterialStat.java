/*
 * Created on 2005-5-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractstat.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;

/**
 * 存放统计报表中的进口料件清单情况统计表资料
 * 
 * @author Administrator
 */
public class DzscBillListImpMaterialStat implements Serializable{
	/**
	 * 料号
	 */
	private String commCode;
	
	/**
	 * 品名
	 */
	private String commName;
	
	/**
	 * 规格
	 */
	private String commSpec;
	
	/**
	 * 单位
	 */
	private Unit unit;
	
	/**
	 * 单价
	 */
	private Double unitPrice;
	
	/**
	 * 合同定量
	 */
	private Double contractAmount;
		
	/**
	 * 总进口量
	 */
	private Double impTotalAmont;
		
	/**
	 * 报关单进口量
	 */
	private Double impCDAmount;
		
	/**
	 * 料件进口量
	 */
	private Double directImport;
		
	/**
	 * 转厂进口量
	 */
	private Double transferFactoryImport;
		
	/**
	 * 余料进口
	 */
	private Double remainImport;
		
	/**
	 * 余料转出
	 */
	private Double remainForward;
		
	/**
	 * 退料出口量
	 */
	private Double backMaterialExport;
		
	/**
	 * 退料复出量
	 */
	private Double backMaterialReturn;
		
	/**
	 * 退料退换量
	 */
	private Double backMaterialExchange;
		
	/**
	 * 内销数量
	 */
	private Double internalAmount;
		
	/**
	 * 料件退换进口数
	 */
	private Double exchangeImport;
		
	/**
	 * 料件退换出口数
	 */
	private Double exchangeExport;
		
	/**
	 * 成品使用量
	 */
	private Double productUse;
		
	/**
	 * 成品使用金额
	 */
	private Double productUseMoney;
		
	/**
	 * 余量
	 */
	private Double remainAmount;
		
	/**
	 * 缺量
	 */
	private Double ullage;
		
	/**
	 * 可进口量
	 */
	private Double canImportAmount;
		
	/**
	 * 比例
	 */
	private Double scale;
		
	/**
	 * 是主料还是辅料
	 */
	private String materialType;
		
	/**
	 * 原产国
	 */
	private Country country;
		
	/**
	 * 序号
	 */
	private String serialNo;
		
	/**
	 * 余料金额
	 */
	private Double remainMoney;
		
	/**
	 * 关封余量
	 */
	private Double customsEnvelopRemain;
		
	/**
	 * 可直接进口量
	 */
	private Double canDirectImportAmount;
	
	/**
	 * 获取退料退换量
	 * 
	 * @return backMaterialExchange 退料退换量
	 */
	public Double getBackMaterialExchange() {
		return backMaterialExchange;
	}
	
	/**
	 * 设置退料退换量
	 * 
	 * @param backMaterialExchange 退料退换量
	 */
	public void setBackMaterialExchange(Double backMaterialExchange) {
		this.backMaterialExchange = backMaterialExchange;
	}
	
	/**
	 * 获取退料出口量 
	 * 
	 * @return backMaterialExport 退料出口量
	 */
	public Double getBackMaterialExport() {
		return backMaterialExport;
	}
	
	/**
	 * 设置退料出口量
	 * 
	 * @param backMaterialExport 退料出口量
	 */
	public void setBackMaterialExport(Double backMaterialExport) {
		this.backMaterialExport = backMaterialExport;
	}
	
	/**
	 * 获取退料复出量
	 * 
	 * @return backMaterialReturn 退料复出量
	 */
	public Double getBackMaterialReturn() {
		return backMaterialReturn;
	}
	
	/**
	 * 设置退料复出量
	 * 
	 * @param backMaterialReturn 退料复出量
	 */
	public void setBackMaterialReturn(Double backMaterialReturn) {
		this.backMaterialReturn = backMaterialReturn;
	}
	
	/**
	 * 获取可直接进口量
	 * 
	 * @return canDirectImportAmount 可直接进口量
	 */
	public Double getCanDirectImportAmount() {
		return canDirectImportAmount;
	}
	
	/**
	 * 设置可直接进口量
	 * 
	 * @param canDirectImportAmount 可直接进口量
	 */
	public void setCanDirectImportAmount(Double canDirectImportAmount) {
		this.canDirectImportAmount = canDirectImportAmount;
	}
	
	/**
	 * 获取可进口量
	 * 
	 * @return canImportAmount 可进口量
	 */
	public Double getCanImportAmount() {
		return canImportAmount;
	}
	
	/**
	 * 设置可进口量
	 * 
	 * @param canImportAmount 可进口量
	 */
	public void setCanImportAmount(Double canImportAmount) {
		this.canImportAmount = canImportAmount;
	}
	
	/**
	 * 获取料号
	 * 
	 * @return commCode 料号
	 */
	public String getCommCode() {
		return commCode;
	}
	
	/**
	 * 获取料号
	 * 
	 * @param commCode 料号
	 */
	public void setCommCode(String commCode) {
		this.commCode = commCode;
	}
	
	/**
	 * 获取品名
	 * 
	 * @return commName 品名
	 */
	public String getCommName() {
		return commName;
	}
	
	/**
	 * 设置品名
	 * 
	 * @param commName 品名
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}
	
	/**
	 * 获取规格
	 * 
	 * @return commSpec 规格
	 */
	public String getCommSpec() {
		return commSpec;
	}
	
	/**
	 * 设置规格
	 * 
	 * @param commSpec 规格 
	 */
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}
	
	/**
	 * 获取合同定量
	 * 
	 * @return contractAmount 合同定量
	 */
	public Double getContractAmount() {
		return contractAmount;
	}
	
	/**
	 * 设置合同定量
	 * 
	 * @param contractAmount 合同定量
	 */
	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}
	
	/**
	 * 获取原产国
	 * 
	 * @return country 原产国
	 */
	public Country getCountry() {
		return country;
	}
	
	/**
	 * 设置原产国
	 * 
	 * @param country 原产国
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	
	/**
	 * 获取关封余量
	 * 
	 * @return customsEnvelopRemain 关封余量
	 */
	public Double getCustomsEnvelopRemain() {
		return customsEnvelopRemain;
	}
	
	/**
	 * 设置关封余量
	 * 
	 * @param customsEnvelopRemain 关封余量
	 */
	public void setCustomsEnvelopRemain(Double customsEnvelopRemain) {
		this.customsEnvelopRemain = customsEnvelopRemain;
	}
	
	/**
	 * 获取料件进口量
	 * 
	 * @return directImport 料件进口量
	 */
	public Double getDirectImport() {
		return directImport;
	}
	
	/**
	 * 设置料件进口量
	 * 
	 * @param directImport 料件进口量
	 */
	public void setDirectImport(Double directImport) {
		this.directImport = directImport;
	}
	
	/**
	 * 获取料件退换出口数
	 * 
	 * @return exchangeExport 料件退换出口数
	 */
	public Double getExchangeExport() {
		return exchangeExport;
	}
	
	/**
	 * 设置料件退换出口数
	 * 
	 * @param exchangeExport 料件退换出口数
	 */
	public void setExchangeExport(Double exchangeExport) {
		this.exchangeExport = exchangeExport;
	}
	
	/**
	 * 获取料件退换进口数
	 * 
	 * @return exchangeImport 料件退换进口数
	 */
	public Double getExchangeImport() {
		return exchangeImport;
	}
	
	/**
	 * 设置料件退换进口数
	 * 
	 * @param exchangeImport 料件退换进口数
	 */
	public void setExchangeImport(Double exchangeImport) {
		this.exchangeImport = exchangeImport;
	}
	
	/**
	 * 获取报关单进口量
	 * 
	 * @return impCDAmount 报关单进口量
	 */ 
	public Double getImpCDAmount() {
		return impCDAmount;
	}
	
	/**
	 * 设置报关单进口量
	 * 
	 * @param impCDAmount 报关单进口量
	 */
	public void setImpCDAmount(Double impCDAmount) {
		this.impCDAmount = impCDAmount;
	}
	
	/**
	 * 获取总进口量
	 * 
	 * @return impTotalAmont 总进口量
	 */
	public Double getImpTotalAmont() {
		return impTotalAmont;
	}
	
	/**
	 * 设置总进口量
	 * 
	 * @param impTotalAmont 总进口量
	 */
	public void setImpTotalAmont(Double impTotalAmont) {
		this.impTotalAmont = impTotalAmont;
	}
	
	/**
	 * 获取内销数量
	 * 
	 * @return internalAmount 内销数量
	 */
	public Double getInternalAmount() {
		return internalAmount;
	}
	
	/**
	 * 设置内销数量
	 * 
	 * @param internalAmount 内销数量
	 */
	public void setInternalAmount(Double internalAmount) {
		this.internalAmount = internalAmount;
	}
	
	/**
	 * 获取是主料还是辅料
	 * 
	 * @return materialType 是主料还是辅料
	 */
	public String getMaterialType() {
		return materialType;
	}
	
	/**
	 * 设置是主料还是辅料
	 * 
	 * @param materialType 是主料还是辅料
	 */
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	/**
	 * 获取成品使用量
	 * 
	 * @return productUse 成品使用量
	 */
	public Double getProductUse() {
		return productUse;
	}
	
	/**
	 * 设置成品使用量
	 * 
	 * @param productUse 成品使用量
	 */
	public void setProductUse(Double productUse) {
		this.productUse = productUse;
	}
	
	/**
	 * 获取成品使用金额
	 * 
	 * @return productUseMoney 成品使用金额
	 */
	public Double getProductUseMoney() {
		return productUseMoney;
	}
	
	/**
	 * 设置成品使用金额
	 * 
	 * @param productUseMoney 成品使用金额
	 */
	public void setProductUseMoney(Double productUseMoney) {
		this.productUseMoney = productUseMoney;
	}
	
	/**
	 * 获取余量
	 * 
	 * @return remainAmount 余量
	 */
	public Double getRemainAmount() {
		return remainAmount;
	}
	
	/**
	 * 设置余量
	 * 
	 * @param remainAmount 余量
	 */
	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}
	
	/**
	 * 获取余料转出
	 * 
	 * @return remainForward 余料转出
	 */
	public Double getRemainForward() {
		return remainForward;
	}
	
	/**
	 * 设置余料转出
	 * 
	 * @param remainForward 余料转出
	 */
	public void setRemainForward(Double remainForward) {
		this.remainForward = remainForward;
	}
	
	/**
	 * 获取余料进口
	 * 
	 * @return remainImport 余料进口
	 */
	public Double getRemainImport() {
		return remainImport;
	}
	
	/**
	 * 设置余料进口
	 *  
	 * @param remainImport 余料进口
	 */
	public void setRemainImport(Double remainImport) {
		this.remainImport = remainImport;
	}
	
	/**
	 * 获取余料金额 
	 * 
	 * @return remainMoney 余料金额 
	 */
	public Double getRemainMoney() {
		return remainMoney;
	}
	
	/**
	 * 设置余料金额
	 * 
	 * @param remainMoney 余料金额
	 */
	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}
	
	/**
	 * 获取比例
	 * 
	 * @return scale 比例
	 */ 
	public Double getScale() {
		return scale;
	}
	
	/**
	 * 设置比例
	 * 
	 * @param scale 比例
	 */
	public void setScale(Double scale) {
		this.scale = scale;
	}
	
	/**
	 * 获取序号
	 * 
	 * @return serialNo 序号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 设置序号
	 * 
	 * @param serialNo 序号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取转厂进口量
	 * 
	 * @return transferFactoryImport 转厂进口量
	 */
	public Double getTransferFactoryImport() {
		return transferFactoryImport;
	}
	
	/**
	 * 设置转厂进口量
	 * 
	 * @param transferFactoryImport 转厂进口量
	 */
	public void setTransferFactoryImport(Double transferFactoryImport) {
		this.transferFactoryImport = transferFactoryImport;
	}
	
	/**
	 * 获取缺量
	 * 
	 * @return ullage 缺量
	 */
	public Double getUllage() {
		return ullage;
	}
	
	/**
	 * 设置缺量
	 * 
	 * @param ullage 缺量
	 */
	public void setUllage(Double ullage) {
		this.ullage = ullage;
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
	 * 获取单价
	 * 
	 * @return unitPrice 单价
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}
	
	/**
	 * 设置单价
	 * 
	 * @param unitPrice 单价
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

}
