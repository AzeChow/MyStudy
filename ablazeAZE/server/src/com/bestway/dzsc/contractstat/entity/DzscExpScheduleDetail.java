/*
 * Created on 2005-5-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractstat.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 存放统计报表中的成品执行进度明细资料
 * 
 * @author Administrator
 */
public class DzscExpScheduleDetail implements Serializable{

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
	 * 商品编号
	 */
	private String commCode;
	
	/**
	 * 商品名称
	 */
	private String commName;
	
	/**
	 * 商品规格
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
	 * 成品出口量
	 */
	private Double directExport;

	
	/**
	 * 转厂出口
	 */
	private Double transferFactoryExport;
	
	/**
	 * 可出口量
	 */
	private Double canExportAmount;
	
	
	/**
	 * 退厂返工数
	 */
	private Double backFactoryRework; 
	
	/**
	 * 返工复出数
	 */
	private Double reworkExport;
	
	/**
	 * 获取退厂返工数
	 * 
	 * @return backFactoryRework 退厂返工数
	 */
	public Double getBackFactoryRework() {
		return backFactoryRework;
	}
	
	/**
	 * 设置退厂返工数
	 * 
	 * @param backFactoryRework 退厂返工数
	 */
	public void setBackFactoryRework(Double backFactoryRework) {
		this.backFactoryRework = backFactoryRework;
	}
	
	
	/**
	 * 获取可出口量
	 * 
	 * @return canExportAmount 可出口量
	 */
	public Double getCanExportAmount() {
		return canExportAmount;
	}
	
	/**
	 * 设置可出口量
	 * 
	 * @param canExportAmount 可出口量
	 */
	public void setCanExportAmount(Double canExportAmount) {
		this.canExportAmount = canExportAmount;
	}
	
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
	 * 获取商品名称
	 * 
	 * @return commName 商品名称
	 */
	public String getCommName() {
		return commName;
	}
	
	/**
	 * 设置商品名称
	 * 
	 * @param commName 商品名称
	 */
	public void setCommName(String commName) {
		this.commName = commName;
	}
	
	/**
	 * 获取商品规格
	 * 
	 * @return commSpec 商品规格
	 */
	public String getCommSpec() {
		return commSpec;
	}
	
	/**
	 * 设置商品规格
	 * 
	 * @param commSpec 商品规格 
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
	 * 获取成品出口量
	 * 
	 * @return directExport 成品出口量
	 */
	public Double getDirectExport() {
		return directExport;
	}
	
	/**
	 * 设置成品出口量
	 * 
	 * @param directExport 成品出口量
	 */
	public void setDirectExport(Double directExport) {
		this.directExport = directExport;
	}
	
	/**
	 * 获取返工复出数
	 * 
	 * @return reworkExport 返工复出数
	 */
	public Double getReworkExport() {
		return reworkExport;
	}
	
	/**
	 * 设置返工复出数
	 * 
	 * @param reworkExport 返工复出数
	 */
	public void setReworkExport(Double reworkExport) {
		this.reworkExport = reworkExport;
	}
	
	/**
	 * 获取转厂出口
	 * 
	 * @return transferFactoryExport 转厂出口
	 */ 
	public Double getTransferFactoryExport() {
		return transferFactoryExport;
	}
	
	/**
	 * 设置转厂出口
	 * 
	 * @param transferFactoryExport 转厂出口
	 */
	public void setTransferFactoryExport(Double transferFactoryExport) {
		this.transferFactoryExport = transferFactoryExport;
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
}
