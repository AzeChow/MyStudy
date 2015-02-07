package com.bestway.bcs.verification.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
/**
 * 成品报关明细数据
 * @author chl
 */
public class VFCustomsExg extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 手册号
     */
    private String emsNo; 
    /**
     * 序号 
     */
    private Integer serialNumber; 
    /**
     * 企业名称
     */
    private String  companyName;
    /**
	 * 报关单号
	 */
	private String customsDeclarationCode;
	
	/**
	 * 申报日期
	 */
	private Date declarationDate;
	/**
	 * 备案资料库备案序号
	 */
	private Integer seqNum;
	/**
	 * 商品名称
	 */
	private String commName;
	
	/**
	 * 商品规格
	 */
	private String commSpec;

	/**
	 * 商品数量
	 */
	private Double commAmount;
	
	/**
	 * 单位
	 */
	private String  unit;
	/**
	 * 商品单价
	 */
	private Double commUnitPrice;
	/**
	 * 总金额
	 */
	private Double totalMoney;
	/**
	 * 进出口类型
	 */
	private Integer impExpType;
	/**
	 * 贸易方式
	 */
	private String tradeMode;
	/**
	 * 批次号
	 */
	private VFSection section;
	/**
	 * 归并序号
	 */
	private Integer mergerNo;
	
	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * @param emsNo the emsNo to set
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
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
	public Double getCommAmount() {
		return commAmount;
	}
	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getCommUnitPrice() {
		return commUnitPrice;
	}
	public void setCommUnitPrice(Double commUnitPrice) {
		this.commUnitPrice = commUnitPrice;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Integer getImpExpType() {
		return impExpType;
	}
	public void setImpExpType(Integer impExpType) {
		this.impExpType = impExpType;
	}
	public String getTradeMode() {
		return tradeMode;
	}
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
	}
	public Integer getMergerNo() {
		return mergerNo;
	}
	public void setMergerNo(Integer mergerNo) {
		this.mergerNo = mergerNo;
	}	
	
}
