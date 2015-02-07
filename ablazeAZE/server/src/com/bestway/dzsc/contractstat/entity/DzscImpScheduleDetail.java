/*
 * Created on 2005-5-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractstat.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 存放统计报表中的料件执行进度明细资料
 * 
 * @author Administrator
 */
public class DzscImpScheduleDetail implements Serializable{
	
	/**
	 * 可进口量
	 */
	private Double canImpAmount;
	
	/**
	 * 料件进口数量
	 */
	private Double directImport;
	
	/**
	 * 料件转厂
	 */
	private Double transferFactoryImport;
	
	/**
	 * 退料出口
	 */
	private Double backMaterialExport;
	
	/**
	 * 申报日期
	 */
	private Date declarationDate; 
	
	/**
	 * 运输工具
	 */
	private String conveyance; 
	
	/**
	 * 商品总价
	 */
	private Double commTotalPrice; 
	
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode; 
	
	/**
	 * 客户
	 */
	private ScmCoc customer; 
	
	/**
	 * 第二法定数量
	 */
	private Double secondAmount;  
	
	/**
	 * 第二法定单位
	 */
	private Unit secondLegalUnit; 
	
	/**
	 * 单位重量
	 */
	private Double unitWeight; 
	
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
	 * 合同定量
	 */
	private Double contractAmount;
	
	/**
	 * 获取商品编号
	 * 
	 * @return commCode 商品编号
	 */
	public String getCommCode() {
		return commCode;
	}
	
	/**
	 * 获取商品编号
	 * 
	 * @param commCode 商品编号
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
	 * 获取单重
	 * 
	 * @return unitWeight 单重
	 */
	public Double getUnitWeight() {
		return unitWeight;
	}
	
	/**
	 * 设置单重
	 * 
	 * @param unitWeight 单重
	 */
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	/**
	 * 获取商品总价
	 * 
	 * @return commTotalPrice 商品总价
	 */
	public Double getCommTotalPrice() {
		return commTotalPrice;
	}

	/**
	 * 设置商品总价
	 * 
	 * @param commTotalPrice 商品总价
	 */
	public void setCommTotalPrice(Double commTotalPrice) {
		this.commTotalPrice = commTotalPrice;
	}

	/**
	 * 获取运输工具
	 * 
	 * @return conveyance 运输工具
	 */
	public String getConveyance() {
		return conveyance;
	}

	/**
	 * 设置运输工具
	 * 
	 * @param conveyance 运输工具
	 */
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	/**
	 * 获取客户
	 * 
	 * @return customer 客户
	 */
	public ScmCoc getCustomer() {
		return customer;
	}

	/**
	 * 设置客户
	 * 
	 * @param customer 客户
	 */
	public void setCustomer(ScmCoc customer) {
		this.customer = customer;
	}

	/**
	 * 获取报关单号
	 * 
	 * @return customsDeclarationCode 报关单号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	/**
	 * 设置报关单号
	 * 
	 * @param customsDeclarationCode 报关单号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	/**
	 * 获取申报日期
	 * 
	 * @return declarationDate 申报日期
	 */
	public Date getDeclarationDate() {
		return declarationDate;
	}

	/**
	 * 设置申报日期
	 * 
	 * @param declarationDate 申报日期
	 */
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}

	/**
	 * 获取第二法定数量
	 * 
	 * @return secondAmount 第二法定数量
	 */
	public Double getSecondAmount() {
		return secondAmount;
	}

	/**
	 * 设置第二法定数量
	 * 
	 * @param secondAmount 第二法定数量
	 */
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}

	/**
	 * 获取第二法定单位
	 * 
	 * @return secondLegalUnit 第二法定单位
	 */
	public Unit getSecondLegalUnit() {
		return secondLegalUnit;
	}

	/**
	 * 设置第二法定单位
	 * 
	 * @param secondLegalUnit 第二法定单位
	 */
	public void setSecondLegalUnit(Unit secondLegalUnit) {
		this.secondLegalUnit = secondLegalUnit;
	}

	/**
	 * 获取退料出口
	 * 
	 * @return backMaterialExport 退料出口
	 */
	public Double getBackMaterialExport() {
		return backMaterialExport;
	}

	/**
	 * 设置退料出口
	 * 
	 * @param backMaterialExport 退料出口
	 */
	public void setBackMaterialExport(Double backMaterialExport) {
		this.backMaterialExport = backMaterialExport;
	}

	/**
	 * 获取可进口量
	 * 
	 * @return canImpAmount 可进口量
	 */
	public Double getCanImpAmount() {
		return canImpAmount;
	}

	/**
	 * 设置可进口量
	 * 
	 * @param canImpAmount 可进口量
	 */
	public void setCanImpAmount(Double canImpAmount) {
		this.canImpAmount = canImpAmount;
	}

	/**
	 * 获取料件进口数量
	 * 
	 * @return 料件进口数量
	 */
	public Double getDirectImport() {
		return directImport;
	}

	/**
	 * 设置料件进口数量
	 * 
	 * @param directImport 料件进口数量
	 */
	public void setDirectImport(Double directImport) {
		this.directImport = directImport;
	}

	/**
	 * 获取料件转厂
	 * 
	 * @return transferFactoryImport 料件转厂
	 */
	public Double getTransferFactoryImport() {
		return transferFactoryImport;
	}

	/**
	 * 设置料件转厂
	 * 
	 * @param transferFactoryImport 料件转厂
	 */
	public void setTransferFactoryImport(Double transferFactoryImport) {
		this.transferFactoryImport = transferFactoryImport;
	}
}
