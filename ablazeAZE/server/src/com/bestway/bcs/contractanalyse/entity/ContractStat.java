package com.bestway.bcs.contractanalyse.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 存放报关分析－－各合同执行状况表资料
 */
public class ContractStat implements Serializable {
	
	/**
	 * 合同号
	 */
	private String contractNo; 
	
	/**
	 * 手册号
	 */
	private String emsNo; 
	
	/**
	 * 进口总金额
	 */
	private Double imgAmount = null; 
	
	/**
	 * 出口总金额
	 */
	private Double exgAmount = null; 
	
	/**
	 * 已进口总金额
	 */
	private Double impTotalMoney; 
	
	/**
	 * 已出口总金额
	 */
	private Double expTotalMoney; 
	
	/**
	 * 已完成进口比例
	 */
	private Double impScale; 
	
	/**
	 * 已完成出口比例
	 */
	private Double expScale; 
	
	/**
	 * 有效日期
	 */
	private Date availabilityDate; 
	
	/**
	 * 变更次数
	 */
	private Integer changeTimes; 
	
	/**
	 * 延期次数
	 */
	private Integer delayTimes; 
	
	/**
	 * 延期时间
	 */
	private Integer delayTerm; 
	
	/**
	 * 核销顺序
	 */
	private String cavOrder; 
	
	/**
	 * 核销状态
	 */
	private String cavState; 
	
	/**
	 * 备注
	 */
	private String memo; 

	/**
	 * 获取有效日期
	 * 
	 * @return availabilityDate 有效日期
	 */
	public Date getAvailabilityDate() {
		if (availabilityDate == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(availabilityDate));
	}

	/**
	 *  设置有效日期
	 * 
	 * @param availabilityDate 有效日期
	 */
	public void setAvailabilityDate(Date availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	/**
	 * 获取核销顺序
	 * 
	 * @return cavOrder 核销顺序
	 */
	public String getCavOrder() {
		return cavOrder;
	}

	/**
	 *  设置核销顺序
	 * 
	 * @param cavOrder 核销顺序
	 */
	public void setCavOrder(String cavOrder) {
		this.cavOrder = cavOrder;
	}

	/**
	 * 获取核销状态
	 * 
	 * @return cavState 核销状态
	 */
	public String getCavState() {
		return cavState;
	}

	/**
	 *  设置核销状态
	 * 
	 * @param cavState 核销状态
	 */
	public void setCavState(String cavState) {
		this.cavState = cavState;
	}

	/**
	 * 获取变更次数
	 * 
	 * @return changeTimes 变更次数
	 */
	public Integer getChangeTimes() {
		return changeTimes;
	}

	/**
	 *  设置变更次数
	 * 
	 * @param changeTimes 变更次数
	 */
	public void setChangeTimes(Integer changeTimes) {
		this.changeTimes = changeTimes;
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
	 *  设置合同号
	 * 
	 * @param contractNo 合同号
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 获取延期时间
	 * 
	 * @return delayTerm 延期时间
	 */
	public Integer getDelayTerm() {
		return delayTerm;
	}

	/**
	 *  设置延期时间
	 * 
	 * @param delayTerm 延期时间
	 */
	public void setDelayTerm(Integer delayTerm) {
		this.delayTerm = delayTerm;
	}

	/**
	 * 获取延期次数
	 * 
	 * @return delayTimes 延期次数
	 */
	public Integer getDelayTimes() {
		return delayTimes;
	}

	/**
	 *  设置延期次数
	 * 
	 * @param delayTimes 延期次数
	 */
	public void setDelayTimes(Integer delayTimes) {
		this.delayTimes = delayTimes;
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
	 *  设置手册号
	 * 
	 * @param emsNo 手册号
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 获取出口总金额
	 * 
	 * @return exgAmount 出口总金额
	 */
	public Double getExgAmount() {
		return exgAmount;
	}

	/**
	 *  设置出口总金额
	 *  
	 * @param exgAmount 出口总金额
	 */
	public void setExgAmount(Double exgAmount) {
		this.exgAmount = exgAmount;
	}

	/**
	 * 获取已完成出口比例
	 * 
	 * @return expScale 已完成出口比例
	 */
	public Double getExpScale() {
		return expScale;
	}

	/**
	 *  设置已完成出口比例
	 * 
	 * @param expScale 已完成出口比例
	 */
	public void setExpScale(Double expScale) {
		this.expScale = expScale;
	}

	/**
	 * 获取已出口总金额
	 * 
	 * @return expTotalMoney 已出口总金额
	 */
	public Double getExpTotalMoney() {
		return expTotalMoney;
	}

	/**
	 *  设置已出口总金额
	 * 
	 * @param expTotalMoney 已出口总金额
	 */
	public void setExpTotalMoney(Double expTotalMoney) {
		this.expTotalMoney = expTotalMoney;
	}

	/**
	 * 获取进口总金额
	 * 
	 * @return imgAmount 进口总金额
	 */
	public Double getImgAmount() {
		return imgAmount;
	}

	/**
	 *  设置进口总金额
	 * 
	 * @param imgAmount 进口总金额
	 */
	public void setImgAmount(Double imgAmount) {
		this.imgAmount = imgAmount;
	}

	/**
	 * 获取已完成进口比例
	 * 
	 * @return impScale 已完成进口比例
	 */
	public Double getImpScale() {
		return impScale;
	}

	/**
	 *  设置已完成进口比例
	 * 
	 * @param impScale 已完成进口比例
	 */
	public void setImpScale(Double impScale) {
		this.impScale = impScale;
	}

	/**
	 * 获取已进口总金额
	 * 
	 * @return impTotalMoney 已进口总金额
	 */
	public Double getImpTotalMoney() {
		return impTotalMoney;
	}

	/**
	 *  设置已进口总金额
	 * 
	 * @param impTotalMoney 已进口总金额
	 */
	public void setImpTotalMoney(Double impTotalMoney) {
		this.impTotalMoney = impTotalMoney;
	}

	/**
	 * 获取备注
	 * 
	 * @return memo 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 *  设置备注
	 * 
	 * @param memo 备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
