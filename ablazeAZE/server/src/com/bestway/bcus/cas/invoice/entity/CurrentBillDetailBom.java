package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

/**
 * 单据对应折算信息
 * @author chensir
 *
 */
public class CurrentBillDetailBom implements Serializable{

	/**
	 * 单据日期
	 */
	private Date validDate;
	/**
	 * 单据类型
	 */
	private String billTypeName="";
	/**
	 * 单据号码
	 */
	private String billNo = "" ;
	/**
	 * 工厂商品编码
	 */
	private String ptPart ="";
	/**
	 * 工厂商品名称
	 */
	private String ptName = "";
	/**
	 * 工厂商品规格
	 */
	private String ptSpec = "";
	/**
	 * 工厂商品单位
	 */
	private String ptUnitName = "";
	/**
	 * 工厂商品数量
	 */
	private Double ptAmount = 0.0;

	/**
	 * 报关商品编码
	 */
	private String complexCode= "";
	/**
	 * 报关商品名称
	 */
	private String hsName = "";
	/**
	 * 报关商品规格
	 */
	private String hsSpec = "";
	/**
	 * 报关商品单位
	 */
	private String hsUnitName = "";
	/**
	 * 报关商品数量
	 */
	private Double hsAmount = 0.0;
	/**
	 * 工厂料件编码
	 */
	private String ptNo = "";
	/**
	 * 工厂料件名称
	 */
	private String factoryName = "";
	/**
	 * 工厂料件规格
	 */
	private String factorySpec = "";
	/**
	 * 工厂料件单位
	 */
	private String calUnitName = "";
	/**
	 * 报关料件编码
	 */
	private String mcomplexCode = "";
	/**
	 * 报关料件名称
	 */
	private String mptName = "";
	/**
	 * 报关料件规格
	 */
	private String mptDeSpec = "";
	/**
	 * 报关料件单位
	 */
	private String mptUnitName = "";
	/**
	 * 单耗
	 */
	private Double unitWaste = 0.0;
	/**
	 * 损耗
	 */
	private Double waste = 0.0;
	/**
	 * 单项用量
	 */
	private Double unitUsed = 0.0;
	/**
	 * 折算系数
	 */
	private Double unitConvert = 0.0;
	/**
	 * 制单号
	 */
	private String productNo = "";
	/**
	 * 报关数量
	 */
	private Double ptTotal =null;

	/**
	 * 总用量
	 */
	private Double totalDosage = null;
	
	public Double getPtTotal() {
		return ptTotal;
	}

	public void setPtTotal(Double ptTotal) {
		this.ptTotal = ptTotal;
	}


	public Double getTotalDosage() {
		return totalDosage;
	}

	public void setTotalDosage(Double totalDosage) {
		this.totalDosage = totalDosage;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public String getPtUnitName() {
		return ptUnitName;
	}

	public void setPtUnitName(String ptUnitName) {
		this.ptUnitName = ptUnitName;
	}

	public Double getPtAmount() {
		return ptAmount;
	}

	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public String getHsName() {
		return hsName;
	}

	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	public String getHsSpec() {
		return hsSpec;
	}

	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	public String getHsUnitName() {
		return hsUnitName;
	}

	public void setHsUnitName(String hsUnitName) {
		this.hsUnitName = hsUnitName;
	}

	public Double getHsAmount() {
		return hsAmount;
	}

	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getFactorySpec() {
		return factorySpec;
	}

	public void setFactorySpec(String factorySpec) {
		this.factorySpec = factorySpec;
	}

	public String getCalUnitName() {
		return calUnitName;
	}

	public void setCalUnitName(String calUnitName) {
		this.calUnitName = calUnitName;
	}

	public String getMcomplexCode() {
		return mcomplexCode;
	}

	public void setMcomplexCode(String mcomplexCode) {
		this.mcomplexCode = mcomplexCode;
	}

	public String getMptName() {
		return mptName;
	}

	public void setMptName(String mptName) {
		this.mptName = mptName;
	}

	public String getMptDeSpec() {
		return mptDeSpec;
	}

	public void setMptDeSpec(String mptDeSpec) {
		this.mptDeSpec = mptDeSpec;
	}

	public String getMptUnitName() {
		return mptUnitName;
	}

	public void setMptUnitName(String mptUnitName) {
		this.mptUnitName = mptUnitName;
	}

	public Double getUnitWaste() {
		return unitWaste;
	}

	public void setUnitWaste(Double unitWaste) {
		this.unitWaste = unitWaste;
	}

	public Double getWaste() {
		return waste;
	}

	public void setWaste(Double waste) {
		this.waste = waste;
	}

	public Double getUnitUsed() {
		return unitUsed;
	}

	public void setUnitUsed(Double unitUsed) {
		this.unitUsed = unitUsed;
	}

	public Double getUnitConvert() {
		return unitConvert;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	
}
