package com.bestway.common.fpt.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.common.CommonUtils;

/**
 * HYQ
 * 深加工结转的报表
 */

public class TempFptApplySurplus implements Serializable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	
	//-----
	/** 转入转出标记 0：转出，1：转入 */
	private String inOutFlag = null;
	/** 客户供应商海关代码 */
	private String tradeCode = null;
	/** 客户供应商 */
	private String tradeName = null;
	/** 商品编码 */
	private String complex = null;

	
	//--------------------------------
	/**手册号*/
	private String emsNo;
	
	/**申请表编号*/
	private String appNo;
	
	/**申请表有效期*/
	private Date endDate;
	
	/**合同效期*/
	private Date contractEndDate;
	
	/**是否结案*/
	private Boolean isCollated;
	
	/**申请表序号*/
	private int listNo;
	
	/**合同序号*/
	private int trNo;
	
	/**归并序号,记录号（备案资料库序号）*/
	private Integer credenceNo;
	
	/**申请表商品名称*/
	private String name;
	
	/**申请表商品规格*/
	private String spec;
	
	/**申请表商品单位*/
	private String unitName;
	
	/**申请表定量*/
	private Double fptHeadQty;
	
	/**合同定量*/
	private Double contractQty;
	
	/**转厂报关定量*/
	private Double declarationTransferFactoryQty;
	
	/**收发货数量*/
	private Double sendReceiveQty;
	
	/**收发退货数量*/
	private Double returnQty;
	
	/**可收发货数量*/
	private Double remainQty;
	
	/**转厂差额*/
	private Double fptDifferenceQty;
	
	

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsCollated() {
		return isCollated;
	}

	public void setIsCollated(Boolean isCollated) {
		this.isCollated = isCollated;
	}

	public int getListNo() {
		return listNo;
	}

	public void setListNo(int listNo) {
		this.listNo = listNo;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public Double getFptHeadQty() {
		return fptHeadQty;
	}

	public void setFptHeadQty(Double fptHeadQty) {
		this.fptHeadQty = fptHeadQty;
	}

	public Double getSendReceiveQty() {
		return sendReceiveQty;
	}

	public void setSendReceiveQty(Double sendReceiveQty) {
		this.sendReceiveQty = sendReceiveQty;
	}

	public Double getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(Double returnQty) {
		this.returnQty = returnQty;
	}

	public Double getRemainQty() {
		return remainQty;
	}

	public void setRemainQty(Double remainQty) {
		this.remainQty = remainQty;
	}

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getComplex() {
		return complex;
	}

	public void setComplex(String complex) {
		this.complex = complex;
	}

	public int getTrNo() {
		return trNo;
	}

	public void setTrNo(int trNo) {
		this.trNo = trNo;
	}

	public Integer getCredenceNo() {
		return credenceNo;
	}

	public void setCredenceNo(Integer credenceNo) {
		this.credenceNo = credenceNo;
	}

	public Double getContractQty() {
		return contractQty;
	}

	public void setContractQty(Double contractQty) {
		this.contractQty = contractQty;
	}

	public Double getFptDifferenceQty() {
		return fptDifferenceQty;
	}

	public void setFptDifferenceQty(Double fptDifferenceQty) {
		this.fptDifferenceQty = fptDifferenceQty;
	}

	public Double getDeclarationTransferFactoryQty() {
		return declarationTransferFactoryQty;
	}

	public void setDeclarationTransferFactoryQty(
			Double declarationTransferFactoryQty) {
		this.declarationTransferFactoryQty = declarationTransferFactoryQty;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	

	
	
	

	
	
}