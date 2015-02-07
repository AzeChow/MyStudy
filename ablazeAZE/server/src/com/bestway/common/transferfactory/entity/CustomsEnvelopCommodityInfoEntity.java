package com.bestway.common.transferfactory.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
/**
 * 关封明细中间类
 * 
 * @author 石小凯
 *
 */
public class CustomsEnvelopCommodityInfoEntity extends BaseScmEntity {

	/**
	 * 关封日期
	 */
	private String  beginAvailability = null;
	/**
	 * 关封号
	 */
	private String customsEnvelopBillNo="";
	/**
	 * 客户名称
	 * 
	 */
	private String scmCocName="";
	/**
	 * 合同号
	 */
	private String purchaseAndSaleContractNo="";
	/**
	 * 报关单号
	 */
	private String carryForwardApplyToCustomsBillNo = "";
	/**
	 * 商品编码
	 */
	private String complexCode="";
	/**
	 * 商品名称
	 */
	private String ptName="";
	/**
	 * 商品规格
	 */
	private String ptSpec="";
	/**
	 * 商品单位
	 */
	private String unitName="";

	/**
	 * 关封数量
	 */
	private Double ownerQuantity=0.0;

	

	public String getBeginAvailability() {
		return beginAvailability;
	}

	public void setBeginAvailability(String beginAvailability) {
		this.beginAvailability = beginAvailability;
	}

	public String getCustomsEnvelopBillNo() {
		return customsEnvelopBillNo;
	}

	public void setCustomsEnvelopBillNo(String customsEnvelopBillNo) {
		this.customsEnvelopBillNo = customsEnvelopBillNo;
	}

	public String getScmCocName() {
		return scmCocName;
	}

	public void setScmCocName(String scmCocName) {
		this.scmCocName = scmCocName;
	}

	public String getPurchaseAndSaleContractNo() {
		return purchaseAndSaleContractNo;
	}

	public void setPurchaseAndSaleContractNo(String purchaseAndSaleContractNo) {
		this.purchaseAndSaleContractNo = purchaseAndSaleContractNo;
	}

	public String getCarryForwardApplyToCustomsBillNo() {
		return carryForwardApplyToCustomsBillNo;
	}

	public void setCarryForwardApplyToCustomsBillNo(
			String carryForwardApplyToCustomsBillNo) {
		this.carryForwardApplyToCustomsBillNo = carryForwardApplyToCustomsBillNo;
	}

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getOwnerQuantity() {
		return ownerQuantity;
	}

	public void setOwnerQuantity(Double ownerQuantity) {
		this.ownerQuantity = ownerQuantity;
	}
	
	
	
	
	
	
	
	
}
