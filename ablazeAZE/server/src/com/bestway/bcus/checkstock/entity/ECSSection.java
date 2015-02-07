package com.bestway.bcus.checkstock.entity;

import java.util.Date;

import com.bestway.bcus.checkcancel.entity.CancelOwnerHead;
import com.bestway.common.BaseScmEntity;

/**
 * 盘点核查批次
 * @author chl
 */
public class ECSSection extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 关联自我核查表头
	 */
	private CancelOwnerHead cancelOwnerHead;
	/**
	 * 核查批次号
	 */
	private Integer verificationNo;
	/**
	 * 盘点核查开始时间	
	 */
	private Date beginDate;
	/**
	 * 盘点核查结束时间	
	 */
	private Date endDate;
	/**
	 * 工厂盘点数据导入方式（false代表料号级，true代表编码级）
	 */
	private Boolean stockImportIsHs;
	/**
	 * 结转数据导入方式（false代表料号级，true代表编码级）
	 */
	private Boolean transImportIsHs;
	/**
	 * 短溢分析单价来源方式
	 * 1： 本报核周期平均单价　2：上一期报核周期平均单价  3： 报关单单价"
	 */
	private Integer priceFromType;
	/**
	 * 内购库存是否参加短溢分析
	 */
	private Boolean buyIsCount;
	/**
	 * 备注	
	 */
	private String memo;
	
	/**
	 * 是否已经在附件管理中存在
	 */
	private Boolean isExist;
	/**
	 * @return the cancelOwnerHead
	 */
	public CancelOwnerHead getCancelOwnerHead() {
		return cancelOwnerHead;
	}
	/**
	 * @param cancelOwnerHead the cancelOwnerHead to set
	 */
	public void setCancelOwnerHead(CancelOwnerHead cancelOwnerHead) {
		this.cancelOwnerHead = cancelOwnerHead;
	}
	/**
	 * 核查批次号
	 * @return the verificationNo
	 */
	public Integer getVerificationNo() {
		return verificationNo;
	}
	/**
	 * @param verificationNo the verificationNo to set
	 */
	public void setVerificationNo(Integer verificationNo) {
		this.verificationNo = verificationNo;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the stockImportIsHs
	 */
	public Boolean getStockImportIsHs() {
		return stockImportIsHs;
	}
	/**
	 * @param stockImportIsHs the stockImportIsHs to set
	 */
	public void setStockImportIsHs(Boolean stockImportIsHs) {
		this.stockImportIsHs = stockImportIsHs;
	}
	/**
	 * @return the transImportIsHs
	 */
	public Boolean getTransImportIsHs() {
		return transImportIsHs;
	}
	/**
	 * @param transImportIsHs the transImportIsHs to set
	 */
	public void setTransImportIsHs(Boolean transImportIsHs) {
		this.transImportIsHs = transImportIsHs;
	}
	/**
	 * @return the priceFromType
	 */
	public Integer getPriceFromType() {
		return priceFromType;
	}
	/**
	 * @param priceFromType the priceFromType to set
	 */
	public void setPriceFromType(Integer priceFromType) {
		this.priceFromType = priceFromType;
	}
	/**
	 * @return the buyIsCount
	 */
	public Boolean getBuyIsCount() {
		return buyIsCount;
	}
	/**
	 * @param buyIsCount the buyIsCount to set
	 */
	public void setBuyIsCount(Boolean buyIsCount) {
		this.buyIsCount = buyIsCount;
	}
	public Boolean getIsExist() {
		return isExist;
	}
	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}
}
