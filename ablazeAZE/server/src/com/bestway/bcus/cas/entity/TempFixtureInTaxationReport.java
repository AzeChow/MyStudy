package com.bestway.bcus.cas.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * @author fhz
 * 
 * 非保税设备报表
 */
public class TempFixtureInTaxationReport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 流水号
	 */
	private Integer serialNumber; 
	
	/**
	 * 设备料号(企业编号)
	 */
	private String ptPart=null;
	
	/**
	 * 工厂商品名称
	 */
	private String ptName=null;
    
    /**
	 * 报关商品名称
	 */
	private String commName=null;
	
	/**
	 * 商品数量
	 */
	private Double commAmount=null;

	/**
	 * 商品单价
	 */
	private Double commUnitPrice=null;

	/**
	 * 商品总价
	 */
	private Double commTotalPrice=null;

	/**
	 * 单位
	 */
	private String unitName=null;
	
	/**
	 * 合同协议号
	 */
	private String contractNo=null;
	
	/**
	 * 进出口日期
	 */
	private Date impExpDate=null;
	
	/**
	 * 解除监管日期
	 */
	private Date outImpExpDate=null;
	
	/**
	 * 贸易方式
	 */
	private String tradeModeName=null;
	
	/**
	 * 报关单号/发票号
	 */
	private String customInvoiceNo=null;
	
	/**
	 * 存放地点
	 */

	private String location =null;

	/**
	 * 保管人
	 */
	private String holdMan =null;
	
	/**
	 * 备注
	 */

	private String note=null;

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

	public Double getCommTotalPrice() {
		return commTotalPrice;
	}

	public void setCommTotalPrice(Double commTotalPrice) {
		this.commTotalPrice = commTotalPrice;
	}

	public Double getCommUnitPrice() {
		return commUnitPrice;
	}

	public void setCommUnitPrice(Double commUnitPrice) {
		this.commUnitPrice = commUnitPrice;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustomInvoiceNo() {
		return customInvoiceNo;
	}

	public void setCustomInvoiceNo(String customInvoiceNo) {
		this.customInvoiceNo = customInvoiceNo;
	}

	public String getHoldMan() {
		return holdMan;
	}

	public void setHoldMan(String holdMan) {
		this.holdMan = holdMan;
	}

	public Date getImpExpDate() {
		return impExpDate;
	}

	public void setImpExpDate(Date impExpDate) {
		this.impExpDate = impExpDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getOutImpExpDate() {
		return outImpExpDate;
	}

	public void setOutImpExpDate(Date outImpExpDate) {
		this.outImpExpDate = outImpExpDate;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtPart() {
		return ptPart;
	}

	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	public String getTradeModeName() {
		return tradeModeName;
	}

	public void setTradeModeName(String tradeModeName) {
		this.tradeModeName = tradeModeName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
}
