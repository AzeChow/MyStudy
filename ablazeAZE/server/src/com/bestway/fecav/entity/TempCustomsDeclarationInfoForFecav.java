/*
 * Created on 2005-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fecav.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseEntity;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempCustomsDeclarationInfoForFecav implements Serializable {

	/**
	 * 报关单来源 ：bcus;bcs;dzba
	 */
	private Integer	projectType;				
	/**
	 * 报关单ID
	 */
	private String	customsDeclarationId;		
	/**
	 * 报关单号
	 */
	private String	customsDeclarationCode;		
	/**
	 * 出口日期
	 */
	private Date	exportDate;					
	/**
	 * 申报日期
	 */
	private Date	declareDate;				
	/**
	 * 合同号码
	 */
	private String	contractNo;					
	/**
	 * 手册号码
	 */
	private String	emsNo;						
	/**
	 * 币别
	 */
	private Curr	curr;						
	/**
	 * 报关单总金额
	 */
	private Double	totalPrice;					
	/**
	 * 是否打印白单
	 */
	private Boolean	isPrintWhite;				
	/**
	 * 是否打印黄单
	 */
	private Boolean	isPrintYellow;				
	/**
	 * 冲销金额
	 */
	private Double	strikeMoney;				
	/**
	 * 剩余金额
	 */
	private Double	remainMoney;				
	/**
	 * 预录入号
	 */
	private String	preCustomsDeclarationCode;	
	/**
	 * 贸易方式
	 */
	private String	tradeMode;					

	/**
	 * 取得预录入号
	 * @return 预录入号
	 */
	public String getPreCustomsDeclarationCode() {
		return preCustomsDeclarationCode;
	}

	/**
	 * 设置预录入号
	 * @param preCustomsDeclarationCode 预录入号
	 */
	public void setPreCustomsDeclarationCode(String preCustomsDeclarationCode) {
		this.preCustomsDeclarationCode = preCustomsDeclarationCode;
	}

	/**
	 * 取得贸易方式
	 * @return 贸易方式
	 */
	public String getTradeMode() {
		return tradeMode;
	}

	/**
	 * 设置贸易方式
	 * @param tradeMode 贸易方式
	 */
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	/**
	 * 获得报关单来源 ：bcus;bcs;dzba
	 * @return 报关单来源 ：bcus;bcs;dzba
	 */
	public Integer getProjectType() {
		return projectType;
	}

	/**
	 * 设置报关单来源 ：bcus;bcs;dzba
	 * @param projectType 报关单来源 ：bcus;bcs;dzba
	 */
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	/**
	 * 获得合同号码
	 * @return 合同号码
	 */
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * 设置合同号码
	 * @param contractNo 合同号码
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 获得币别
	 * @return 币别
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * 设置币别
	 * @param curr 币别
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 获得报关单号
	 * @return 报关单号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	/**
	 * 设置报关单号
	 * @param customsDeclarationCode 报关单号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	/**
	 * 获得报关单ID
	 * @return 报关单ID
	 */
	public String getCustomsDeclarationId() {
		return customsDeclarationId;
	}

	/**
	 * 设置报关单ID
	 * @param customsDeclarationId 报关单ID
	 */
	public void setCustomsDeclarationId(String customsDeclarationId) {
		this.customsDeclarationId = customsDeclarationId;
	}

	/**
	 * 获得申报日期
	 * @return 申报日期
	 */
	public Date getDeclareDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (declareDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(declareDate));
		}
		return declareDate;
	}

	/**
	 * 设置申报日期
	 * @param declareDate 申报日期
	 */
	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	/**
	 * 获得手册号码
	 * @return 手册号码
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置手册号码
	 * @param emsNo 手册号码
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获得出口日期
	 * @return 出口日期
	 */
	public Date getExportDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (exportDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(exportDate));
		}
		return exportDate;
	}

	/**
	 * 设置出口日期
	 * @param exportDate 出口日期
	 */
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * 判断是否打印白单
	 * @return  是否打印白单
	 */
	public Boolean getIsPrintWhite() {
		return isPrintWhite;
	}

	/**
	 * 设置是否打印白单标志
	 * @param isPrintWhite 是否打印白单
	 */
	public void setIsPrintWhite(Boolean isPrintWhite) {
		this.isPrintWhite = isPrintWhite;
	}

	/**
	 * 判断是否打印黄单
	 * @return 是否打印黄单
	 */
	public Boolean getIsPrintYellow() {
		return isPrintYellow;
	}

	/**
	 * 设置是否打印黄单标志
	 * @param isPrintYellow 是否打印黄单
	 */
	public void setIsPrintYellow(Boolean isPrintYellow) {
		this.isPrintYellow = isPrintYellow;
	}

	/**
	 * 获得报关单总金额
	 * @return 报关单总金额
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 设置报关单总金额
	 * @param totalPrice 报关单总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 获得剩余金额
	 * @return 剩余金额
	 */
	public Double getRemainMoney() {
		return remainMoney;
	}

	/**
	 * 设置剩余金额
	 * @param remainMoney 剩余金额
	 */
	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	/**
	 * 获得冲销金额
	 * @return 冲销金额
	 */
	public Double getStrikeMoney() {
		return strikeMoney;
	}

	/**
	 * 设置冲销金额
	 * @param strikeMoney 冲销金额
	 */
	public void setStrikeMoney(Double strikeMoney) {
		this.strikeMoney = strikeMoney;
	}

	/**
	 * 比较两个对象是否相等
	 */
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other.getClass().equals(this.getClass())))
			return false;
		TempCustomsDeclarationInfoForFecav castOther = (TempCustomsDeclarationInfoForFecav) other;
		if (this.getCustomsDeclarationId() == null
				&& castOther.getCustomsDeclarationId() == null) {
			return super.equals(other);
		} else {
			return new EqualsBuilder().append(this.getCustomsDeclarationId(),
					castOther.getCustomsDeclarationId()).isEquals();
		}
	}
}
