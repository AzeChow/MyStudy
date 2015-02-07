package com.bestway.dzsc.dzscmanage.entity;

import java.io.Serializable;

/**
 * 临时实体类 TempDzscCommMoneyAmountInfo
 * 
 * @author bsway
 */
public class TempDzscCommMoneyAmountInfo implements Serializable {
	/**
	 * 手册金额
	 */
	public Double dzscEmsTotalMoney; 

	/**
	 * 可备案金额
	 */
	public Double canPutOnRecordsMoney;

	/**
	 * 报关单数量
	 */
	public Double customsDeclarationAmount;

	/**
	 * 获取可备案金额
	 * 
	 * @return canPutOnRecordsMoney 可备案金额
	 */
	public Double getCanPutOnRecordsMoney() {
		return canPutOnRecordsMoney;
	}

	/**
	 * 设置可备案金额
	 * 
	 * @param canPutOnRecordsMoney 可备案金额
	 */
	public void setCanPutOnRecordsMoney(Double canPutOnRecordsMoney) {
		this.canPutOnRecordsMoney = canPutOnRecordsMoney;
	}

	/**
	 * 获取报关单数量
	 * 
	 * @return customsDeclarationAmount 报关单数量
	 */
	public Double getCustomsDeclarationAmount() {
		return customsDeclarationAmount;
	}

	/**
	 * 设置报关单数量
	 * 
	 * @param customsDeclarationAmount 报关单数量
	 */
	public void setCustomsDeclarationAmount(Double customsDeclarationAmount) {
		this.customsDeclarationAmount = customsDeclarationAmount;
	}

	/**
	 * 获取手册金额
	 * 
	 * @return dzscEmsTotalMoney 手册金额
	 */
	public Double getDzscEmsTotalMoney() {
		return dzscEmsTotalMoney;
	}

	/**
	 * 设置手册金额
	 * 
	 * @param dzscEmsTotalMoney 手册金额
	 */
	public void setDzscEmsTotalMoney(Double dzscEmsTotalMoney) {
		this.dzscEmsTotalMoney = dzscEmsTotalMoney;
	}
}
