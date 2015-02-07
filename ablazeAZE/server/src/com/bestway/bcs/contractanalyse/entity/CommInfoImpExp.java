package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.materialbase.entity.ScmCoc;
/**
 * 报关分析--料件,成品分析表
 */
public class CommInfoImpExp implements Serializable{
	
	/**
	 * 商品编码
	 */
	private Complex complex = null;
	/**
	 * 品名
	 */
	private String commName= null;
	/**
	 * 规格
	 */
	private String commSpec= null;
	/**
	 * 合同号
	 */
	private String contract= null;
	/**
	 * 进出口类型
	 */
	private Integer impExpType= null;
	/**
	 * 进出口类型String型
	 */
	private String impExpTypeString= null;
	/**
	 * 报关日期
	 */
	private Date declarationDate= null;
	/**
	 * 数量
	 */
	private Double commAmount= null;
	/**
	 * 单位
	 */
	private Unit unitName= null;
	/**
	 * 价值
	 */
	private Double commTotalPrice= null;
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode= null;
	/**
	 * 客户名称
	 */
	private ScmCoc scmCoc= null;
	/**
	 * 发票号
	 */
	private String invoiceCode = null;
	/**
	 * 运输工具名称
	 */
	private String conveyance= null;
	/**
	 * 单位重
	 */
	private Double unitWeight= null;
	/**
	 * 第一数量
	 */
	private Double firstAmount = null;
	/**
	 * 第一单位
	 */
	private Unit firstLegalUnit = null;
	/**
	 * 第二数量
	 */
	private Double secondAmount= null;
	/**
	 * 第二单位
	 */
	private Unit secondLegalUnit= null;
	/**
	 * 料件序号
	 */
	private Integer commSerialNo= null;
	/**
	 * 包装
	 */
	private Wrap wrapType= null;
	/**
	 * 毛重
	 */
	private Double grossWeight= null;
	/**
	 * 净重
	 */
	private Double netWeight= null;
	
	/**
	 * 加工费单价
	 * @return
	 */
	private Double processUnitPrice = null;
	
	/**
	 * 加工费总价
	 * @return
	 */
	private Double processTotalPrice = null;
	
	/**
	 * 核销单号--报关单表头外汇核销单号
	 * @return
	 */
	private String authorizeFile = null;
	
	/**
	 * 报关单币制
	 */
	private String currency = null;
	
	/**
	 * 折算汇率后的价值
	 */
	private Double currencyCommTotalPrice = null;
	
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getCurrencyCommTotalPrice() {
		return currencyCommTotalPrice;
	}
	public void setCurrencyCommTotalPrice(Double currencyCommTotalPrice) {
		this.currencyCommTotalPrice = currencyCommTotalPrice;
	}
	public Double getCommAmount() {
		return commAmount;
	}
	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public String getCommSpec() {
		return commSpec;
	}
	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}
	public Double getCommTotalPrice() {
		return commTotalPrice;
	}
	public void setCommTotalPrice(Double commTotalPrice) {
		this.commTotalPrice = commTotalPrice;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getConveyance() {
		return conveyance;
	}
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}
	public Date getDeclarationDate() {
		return declarationDate;
	}
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
	public Double getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}
	public Integer getImpExpType() {
		return impExpType;
	}
	public void setImpExpType(Integer impExpType) {
		this.impExpType = impExpType;
	}
	public Double getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}
	public Double getSecondAmount() {
		return secondAmount;
	}
	public void setSecondAmount(Double secondAmount) {
		this.secondAmount = secondAmount;
	}


	public void setCommSerialNo(Integer commSerialNo) {
		this.commSerialNo = commSerialNo;
	}
	public Double getUnitWeight() {
		return unitWeight;
	}
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}
	public Integer getCommSerialNo() {
		return commSerialNo;
	}
	public Complex getComplex() {
		return complex;
	}
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	public ScmCoc getScmCoc() {
		return scmCoc;
	}
	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}
	public Unit getSecondLegalUnit() {
		return secondLegalUnit;
	}
	public void setSecondLegalUnit(Unit secondLegalUnit) {
		this.secondLegalUnit = secondLegalUnit;
	}
	public Unit getUnitName() {
		return unitName;
	}
	public void setUnitName(Unit unitName) {
		this.unitName = unitName;
	}
	public Wrap getWrapType() {
		return wrapType;
	}
	public void setWrapType(Wrap wrapType) {
		this.wrapType = wrapType;
	}
	public Double getProcessUnitPrice() {
		return processUnitPrice;
	}
	public void setProcessUnitPrice(Double processUnitPrice) {
		this.processUnitPrice = processUnitPrice;
	}
	public Double getProcessTotalPrice() {
		return processTotalPrice;
	}
	public void setProcessTotalPrice(Double processTotalPrice) {
		this.processTotalPrice = processTotalPrice;
	}
	public String getAuthorizeFile() {
		return authorizeFile;
	}
	public void setAuthorizeFile(String authorizeFile) {
		this.authorizeFile = authorizeFile;
	}
	public Double getFirstAmount() {
		return firstAmount;
	}
	public void setFirstAmount(Double firstAmount) {
		this.firstAmount = firstAmount;
	}
	public Unit getFirstLegalUnit() {
		return firstLegalUnit;
	}
	public void setFirstLegalUnit(Unit firstLegalUnit) {
		this.firstLegalUnit = firstLegalUnit;
	}
	public String getImpExpTypeString() {
		return impExpTypeString;
	}
	public void setImpExpTypeString(String impExpTypeString) {
		this.impExpTypeString = impExpTypeString;
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

}
