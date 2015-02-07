package com.bestway.bcs.verification.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
/**
 * 成品报关数据统计表
 * @author chl
 */
public class VFCustomsCountExg extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
     * 流水号
     */
    private Integer serialNumber; 
    /**
	 * 备案序号
	 */
	private Integer seqNum;
	/**
	 * 申报日期
	 */
	private Date declarationDate;
	/**
	 * 商品名称
	 */
	private String commName;
	
	 /**
     * 企业名称
     */
    private String  companyName;
	/**
	 * 商品规格
	 */
	private String commSpec;	
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 本期总出口量
	 */
	private Double expTotalAmont;
	/**
	 * 本期总出口金额
	 */
	private Double expTotalMoney;
	/**
	 * 手册号
	 */
	private String emsNo;
	
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
	 * @return the expTotalMoney
	 */
	public Double getExpTotalMoney() {
		return expTotalMoney;
	}
	/**
	 * @param expTotalMoney the expTotalMoney to set
	 */
	public void setExpTotalMoney(Double expTotalMoney) {
		this.expTotalMoney = expTotalMoney;
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
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
	}
	/**
	 * @return the expTotalAmont
	 */
	public Double getExpTotalAmont() {
		return expTotalAmont;
	}
	/**
	 * @param expTotalAmont the expTotalAmont to set
	 */
	public void setExpTotalAmont(Double expTotalAmont) {
		this.expTotalAmont = expTotalAmont;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
