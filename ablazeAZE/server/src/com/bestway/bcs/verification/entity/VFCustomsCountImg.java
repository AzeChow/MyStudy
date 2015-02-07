package com.bestway.bcs.verification.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;

/**
 * 料件报关数据统计表
 * 
 * @author chl
 */
public class VFCustomsCountImg extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	private Integer serialNumber;
	/**
	 * 备案资料库备案序号
	 */
	private Integer seqNum;
	
	/**
	 * 归并序号
	 */
	private Integer mergerNo;
	/**
	 * 申报日期
	 */
	private Date declarationDate;
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
	private String unit;
	/**
	 * 手册号
	 */
	private String emsNo;
	/**
	 * 企业名称
	 */
	private String companyName;
	/**
	 * 本期总进口量
	 */
	private Double impTotalAmont;

	/**
	 * 本期总进口金额
	 */
	private Double impTotalMoney;
	/**
	 * 本期平均单价
	 */
	private Double impPrice;

	/**
	 * 本期余料结转量
	 */
	private Double remainForwordAmount;
	/**
	 * 批次号
	 */
	private VFSection section;
	
	
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public Date getDeclarationDate() {
		return declarationDate;
	}
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
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
		public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}	
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Double getImpTotalAmont() {
		return impTotalAmont;
	}
	public void setImpTotalAmont(Double impTotalAmont) {
		this.impTotalAmont = impTotalAmont;
	}
	public Double getImpTotalMoney() {
		return impTotalMoney;
	}
	public void setImpTotalMoney(Double impTotalMoney) {
		this.impTotalMoney = impTotalMoney;
	}
	public Double getRemainForwordAmount() {
		return remainForwordAmount;
	}
	public void setRemainForwordAmount(Double remainForwordAmount) {
		this.remainForwordAmount = remainForwordAmount;
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
	public Double getImpPrice() {
		return impPrice;
	}
	public void setImpPrice(Double impPrice) {
		this.impPrice = impPrice;
	}	
}
