package com.bestway.bcus.cas.invoice.entity;

import java.io.Serializable;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 单据对应盘点信息
 * @author chensir
 *
 */
public class CurrentBillDetailPanDian implements Serializable{
	
	
	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 69066885080585223L;
	
	/**
	 * 工厂料号
	 */
	private String ptNo;
	
	/**
	 * 工厂名称规格
	 */
	private String ptNameSpec;
	
	/**
	 * 工厂单位
	 */
	 private String ptUnitName;
	
	/**
	 * 工厂帐存数量
	 */
	 private Double ptAmount;
	 
	/**
	 * 工厂盘点数量
	 */
	 
	 private Double ptCheckAmount;
	
	/**
	 * 工厂数量差异
	 */
	 private Double ptBalanceAmount;
	 
	/**
	 * 报关编码
	 */
	 private Complex complex;
	
	/**
	 * 报关名称规格
	 */
	 private String hsNameSpec;
	 
	/**
	 * 报关单位
	 */
	 private String hsUnitName;
	 
	/**
	 * 报关帐存数量
	 */
	 private Double hsAmount;
	 
	/**
	 * 报关盘点数量
	 */
	 private Double hsCheckAmount; 
	 
	 
	/**
	 * 报关数量差异
	 */
	 private Double hsBalanceAmount;
	 
	 
	/**
	 * 物料类别
	 */
	 private String materialType;
	 
	 
	/**
	 * 物料仓位
	 */
	 private WareSet wareSet;
	 
	
	/**
	 * 制单号
	 */
	 private String productNo;
	 
	 /**
	  * 单据类型代码
	  * 
	  */
	 private String billTypeCode = null;


	public String getPtNo() {
		return ptNo;
	}


	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}


	public String getPtNameSpec() {
		return ptNameSpec;
	}


	public void setPtNameSpec(String ptNameSpec) {
		this.ptNameSpec = ptNameSpec;
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


	public Double getPtCheckAmount() {
		return ptCheckAmount;
	}


	public void setPtCheckAmount(Double ptCheckAmount) {
		this.ptCheckAmount = ptCheckAmount;
	}


	public Double getPtBalanceAmount() {
		return ptBalanceAmount;
	}


	public void setPtBalanceAmount(Double ptBalanceAmount) {
		this.ptBalanceAmount = ptBalanceAmount;
	}


	public Complex getComplex() {
		return complex;
	}


	public void setComplex(Complex complex) {
		this.complex = complex;
	}


	public String getHsNameSpec() {
		return hsNameSpec;
	}


	public void setHsNameSpec(String hsNameSpec) {
		this.hsNameSpec = hsNameSpec;
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


	public Double getHsCheckAmount() {
		return hsCheckAmount;
	}


	public void setHsCheckAmount(Double hsCheckAmount) {
		this.hsCheckAmount = hsCheckAmount;
	}


	public Double getHsBalanceAmount() {
		return hsBalanceAmount;
	}


	public void setHsBalanceAmount(Double hsBalanceAmount) {
		this.hsBalanceAmount = hsBalanceAmount;
	}


	public String getMaterialType() {
		return materialType;
	}


	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}


	public WareSet getWareSet() {
		return wareSet;
	}


	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}


	public String getProductNo() {
		return productNo;
	}


	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}


	public String getBillTypeCode() {
		return billTypeCode;
	}


	public void setBillTypeCode(String billTypeCode) {
		this.billTypeCode = billTypeCode;
	}
	
	



	 
	 
	 
	 
	 
	
	/**
	 * 对应单据
	 
	private BillDetail billDetail;

	public BillDetail getBillDetail() {
		return billDetail;
	}

	public void setBillDetail(BillDetail billDetail) {
		this.billDetail = billDetail;
	}
	*/
}
