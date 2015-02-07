package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * 存放报关分析－－进、出口报关单列表资料
 */
public class ImpExpCustomsDeclarationList implements Serializable{
	
	/**
	 * 合同号
	 */
	private String contractNo;
	
	/**
	 * 手册号
	 */
	private String emsNo;
	
	/**
	 * 日期
	 */
	private Date customsDeclarationDate;
	
	/**
	 * 报关单号
	 */
	private String customsDeclarationCode;
	
	/**
	 * 核销单号
	 */
	private String fecavBillCode;
	
	/**
	 * 集装箱号
	 */
	private String containerCode;
	
	/**
	 * 金额
	 */
	private Double totalMoney;
	
	/**
	 * 件数
	 */
	private Integer commodityNum;
	
	/**
	 * 毛重
	 */
	private Double grossWeight; 
	
	/**
	 * 净重
	 */
	private Double netWeight; 
	
	/**
	 * 页数
	 */
	private Integer pageCount;
	
	/**
	 * 包装种类
	 */
	private Wrap wrapType; 
	
	/**
	 * 贸易方式
	 */
	private Trade tradeMode; 
	
	/**
	 * 客户
	 */
	private ScmCoc customer; 
	
	/**
	 * 是否生效
	 */
	private Boolean iseffective;
	
	/**
	 * 币制
	 */
	private Curr curr ;
	
	
	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 获取合同号
	 * 
	 * @return contractNo 合同号
	 */
	public String getContractNo() {
		return contractNo;
	}
	
	/**
	 * 设置合同号
	 * 
	 * @param contractNo 合同号
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	/**
	 * 获取手册号
	 * 
	 * @return emsNo 手册号
	 */
	public String getEmsNo() {
		return emsNo;
	}
	
	/**
	 * 设置手册号
	 * 
	 * @param emsNo 手册号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	
	/**
	 * 获取是否生效
	 * 
	 * @return iseffective 是否生效
	 */
	public boolean isIseffective() {
		return iseffective;
	}
	
	/**
	 * 设置是否生效
	 * 
	 * @param iseffective 是否生效
	 */
	public void setIseffective(boolean iseffective) {
		this.iseffective = iseffective;
	}
	
	/**
	 * 获取件数
	 * 
	 * @return commodityNum 件数
	 */
	public Integer getCommodityNum() {
		return commodityNum;
	}
	
	/**
	 * 设置件数
	 * 
	 * @param commodityNum 件数
	 */
	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}
	
	/**
	 * 获取客户
	 * 
	 * @return customer 客户
	 */
	public ScmCoc getCustomer() {
		return customer;
	}
	
	/**
	 * 设置客户
	 * 
	 * @param customer 客户
	 */
	public void setCustomer(ScmCoc customer) {
		this.customer = customer;
	}
	
	/**
	 * 获取报关单号
	 * 
	 * @return customsDeclarationCode 报关单号
	 */
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}
	
	/**
	 * 设置报关单号
	 * 
	 * @param customsDeclarationCode 报关单号
	 */
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}
	
	/**
	 * 获取日期
	 * 
	 * @return customsDeclarationDate 日期
	 */
	public Date getCustomsDeclarationDate() {
		return customsDeclarationDate;
	}
	
	/**
	 * 设置日期
	 * 
	 * @param customsDeclarationDate 日期
	 */
	public void setCustomsDeclarationDate(Date customsDeclarationDate) {
		this.customsDeclarationDate = customsDeclarationDate;
	}
	
	/**
	 * 获取毛重
	 * 
	 * @return grossWeight 毛重
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}
	
	/**
	 * 设置毛重
	 * 
	 * @param grossWeight 毛重
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}
	
	/**
	 * 获取净重
	 * 
	 * @return netWeight 净重
	 */
	public Double getNetWeight() {
		return netWeight;
	}
	
	/**
	 * 设置净重
	 * 
	 * @param netWeight 净重
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}
	
	/**
	 * 获取页数
	 * 
	 * @return pageCount 页数
	 */
	public Integer getPageCount() {
		return pageCount;
	}
	
	/**
	 * 设置页数
	 * 
	 * @param pageCount 页数
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	
	/**
	 * 获取金额
	 * 
	 * @return totalMoney 金额
	 */
	public Double getTotalMoney() {
		return totalMoney;
	}
	
	/**
	 * 设置金额
	 * 
	 * @param totalMoney 金额
	 */
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	/**
	 * 获取贸易方式
	 * 
	 * @return tradeMode 贸易方式
	 */
	public Trade getTradeMode() {
		return tradeMode;
	}
	
	/**
	 * 设置贸易方式
	 * 
	 * @param tradeMode 贸易方式
	 */
	public void setTradeMode(Trade tradeMode) {
		this.tradeMode = tradeMode;
	}
	
	/**
	 * 获取包装种类
	 * 
	 * @return wrapType 包装种类
	 */
	public Wrap getWrapType() {
		return wrapType;
	}
	
	/**
	 * 设置包装种类
	 * 
	 * @param wrapType 包装种类
	 */
	public void setWrapType(Wrap wrapType) {
		this.wrapType = wrapType;
	}
	
	/**
	 * 获取集装箱号
	 * 
	 * @return containerCode 集装箱号
	 */
	public String getContainerCode() {
		return containerCode;
	}
	
	/**
	 * 设置集装箱号
	 * 
	 * @param containerCode 集装箱号
	 */
	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}
	
	/**
	 * 获取核销单号
	 * 
	 * @return fecavBillCode 核销单号
	 */
	public String getFecavBillCode() {
		return fecavBillCode;
	}
	
	/**
	 * 设置核销单号
	 * 
	 * @param fecavBillCode 核销单号
	 */
	public void setFecavBillCode(String fecavBillCode) {
		this.fecavBillCode = fecavBillCode;
	}
}
