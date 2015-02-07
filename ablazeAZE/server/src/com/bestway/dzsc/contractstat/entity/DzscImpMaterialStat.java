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
 * 存放统计报表中的进口料件报关情况表资料
 * 
 * @author Administrator
 */
public class DzscImpMaterialStat implements Serializable,Comparable{
	/**
	 * 备案序号
	 */
	private Integer seqNum;
	/**
	 * 商品编码
	 */
	private String complex;
	
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
	 * 余料进口
	 */
	private Double remainImport;

	/**
	 * 余料转出
	 */
	private Double remainForward;
	/**
	 * 转厂进口量
	 */
	private Double transferFactoryImport;
	
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
	 * 成品使用量
	 */
	private Double productUse;
	/**
	 * 关封余量
	 */
	private Double customsEnvelopRemain;
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
	 * 凭证序号
	 */
	private Integer credenceSerialNo;
		
	/**
	 * 余料金额
	 */
	private Double remainMoney;
	
	/**
	 * 手册号
	 */
	private String emsNo = null;
	
	/**
	 * 合同号
	 */
	private String ieContractNo;
	
	/**
	 * 第一法定单位数量
	 */
	private Double legalAmount; 
	
	/**
	 * 第一法定单位
	 */
	private Unit legalUnit; 
	/**
	 * 第二法定单位
	 */
	private Unit secondLegalUnit;
	/**
	 * 第二法定数量
	 */
	private Double secondAmount;
	
	
	
	public Double getLegalAmount() {
		return legalAmount;
	}

	public void setLegalAmount(Double legalAmount) {
		this.legalAmount = legalAmount;
	}

	public Unit getLegalUnit() {
		return legalUnit;
	}

	public void setLegalUnit(Unit legalUnit) {
		this.legalUnit = legalUnit;
	}

	public Unit getSecondLegalUnit() {
		return secondLegalUnit;
	}

	public void setSecondLegalUnit(Unit secondLegalUnit) {
		this.secondLegalUnit = secondLegalUnit;
	}

	public Double getSecondAmount() {
		return secondAmount;
	}

	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

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

	/**
	 * 获取商品编码
	 * 
	 * @return complex 商品编码
	 */
	public String getComplex() {
		return complex;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param complex 商品编码
	 */
	public void setComplex(String complex) {
		this.complex = complex;
	}

	public Double getRemainForward() {
		return remainForward;
	}

	public void setRemainForward(Double remainForward) {
		this.remainForward = remainForward;
	}

	public Double getRemainImport() {
		return remainImport;
	}

	public void setRemainImport(Double remainImport) {
		this.remainImport = remainImport;
	}

	public Double getCustomsEnvelopRemain() {
		return customsEnvelopRemain;
	}

	public void setCustomsEnvelopRemain(Double customsEnvelopRemain) {
		this.customsEnvelopRemain = customsEnvelopRemain;
	}

	public Integer getCredenceSerialNo() {
		return credenceSerialNo;
	}

	public void setCredenceSerialNo(Integer credenceSerialNo) {
		this.credenceSerialNo = credenceSerialNo;
	}

	public int compareTo(Object o) {
		if (o == null) {
			return 1;
		}
		DzscImpMaterialStat stat = (DzscImpMaterialStat) o;
		if (this.getSeqNum()== null && stat.getSeqNum() == null) {
			return 0;
		}
		if (this.getSeqNum() == null && stat.getSeqNum() != null) {
			return -1;
		}
		if (this.getSeqNum() != null && stat.getSeqNum() == null) {
			return 1;
		}
		int serialNo1 = Integer.valueOf(this.getSeqNum());
		int serialNo2 = Integer.valueOf(stat.getSeqNum());
		if ((serialNo1 - serialNo2) > 0) {
			return 1;
		} else if ((serialNo1 - serialNo2) == 0) {
			return 0;
		} else if ((serialNo1 - serialNo2) < 0) {
			return -1;
		}
		return 0;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public String getIeContractNo() {
		return ieContractNo;
	}

	public void setIeContractNo(String ieContractNo) {
		this.ieContractNo = ieContractNo;
	}
	
	
}
