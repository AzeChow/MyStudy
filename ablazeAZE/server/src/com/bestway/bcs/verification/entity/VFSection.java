package com.bestway.bcs.verification.entity;

import java.util.Date;

import javax.persistence.Transient;

import com.bestway.common.BaseScmEntity;
import com.jnetdirect.a.b;

/**
 * 核查分析批次
 * @author chl
 */
public class VFSection extends BaseScmEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 核查批次号
	 */
	private Integer verificationNo;
	/**
	 *  结转差额分析文本导入方式
	 */
	private Boolean isImportHs = Boolean.FALSE;
	/**
	 * 截止时间	
	 */
	private Date endDate;
	/**
	 * 备注	
	 */
	private String memo;
	
	/**
	 * 是否已经在附件管理中存在
	 */
	private Boolean isExist;
	
	/**
	 * 内购库存是否参加短溢分析
	 */
	private Boolean buyIsCount;
	
	
	/**
	 * @return the verificationNo
	 */
	public Integer getVerificationNo() {
		return verificationNo;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	
	/**
	 * @return the isImportHs
	 */
	public Boolean getIsImportHs() {
		return isImportHs;
	}
	/**
	 * @param isImportHs the isImportHs to set
	 */
	public void setIsImportHs(Boolean isImportHs) {
		this.isImportHs = isImportHs;
	}
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}
	public void setVerificationNo(Integer verificationNo) {
		this.verificationNo = verificationNo;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Boolean getBuyIsCount() {
		return buyIsCount;
	}
	public void setBuyIsCount(Boolean buyIsCount) {
		this.buyIsCount = buyIsCount;
	}
	@Transient
	public String getVerificationNoView(){
		if(Boolean.TRUE.equals(isImportHs)){
			return verificationNo+"【编码级】";
		}
		return verificationNo+"【料号级】";
	}
	public Boolean getIsExist() {
		return isExist;
	}
	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}
}
