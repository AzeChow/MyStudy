
package com.bestway.bcs.contractexe.entity;

import java.io.Serializable;

/**
 * 存放合同成品的合同定量、合同余量、当前余量资料
 */
public class BcsContractExeInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 合同定量=合同数量
	 */
	private double contractAmount; 

	/**
	 * 合同余量=合同定量-已生效报关单数量
	 */
	private double contractRemain;// 

	/**
	 * 当前余量
	 * 料件 当前余量=合同定量-((已生效+为生效)进口数量-退料出口（退换）+复出（退换）)
	 * 成品 当前余量=合同定量-((已生效+为生效)出口数量-退厂返工+返工复出）
	 */
	private double currentRemain;
	
	/**
	 * 可内销量=总进口量-余料结转-料件复出-料件内销-成品使用量；
	 */
	private double canInSell; 
	
	/**
	 * 关封余量 = 关封数量 - 转厂数量
	 */
	private double customsRemain;
	
	/**
	 * 关封数量
	 */
	private double customsNum;
	
	/**
	 * 可申请申请表数量=合同定量-全部报关单数量(除去转厂)-全部收发送货数(含收退货)
	 */
	private double applyRemain;
	
	/**
	 * 获取合同定量=合同数量
	 * 
	 * @return contractAmount 合同定量=合同数量
	 */
	public double getContractAmount() {
		return contractAmount;
	}

	/**
	 * 设置合同定量=合同数量
	 * 
	 * @param contractAmount 合同定量=合同数量
	 */
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}

	/**
	 * 获取合同余量=合同定量-已生效报关单数量
	 * 
	 * @return contractRemain 合同余量=合同定量-已生效报关单数量
	 */
	public double getContractRemain() {
		return contractRemain;
	}

	/**
	 * 设置合同余量=合同定量-已生效报关单数量
	 * 
	 * @param contractRemain 合同余量=合同定量-已生效报关单数量
	 */
	public void setContractRemain(double contractRemain) {
		this.contractRemain = contractRemain;
	}

	/**
	 * 获取当前余量
	 * 
	 * @return currentRemain 当前余量
	 */
	public double getCurrentRemain() {
		return currentRemain;
	}

	/**
	 * 设置当前余量
	 * 
	 * @param currentRemain 当前余量
	 */
	public void setCurrentRemain(double currentRemain) {
		this.currentRemain = currentRemain;
	}

	public double getCanInSell() {
		return canInSell;
	}

	public void setCanInSell(double canInSell) {
		this.canInSell = canInSell;
	}

	public double getCustomsRemain() {
		return customsRemain;
	}

	public void setCustomsRemain(double customsRemain) {
		this.customsRemain = customsRemain;
	}

	public double getCustomsNum() {
		return customsNum;
	}

	public void setCustomsNum(double customsNum) {
		this.customsNum = customsNum;
	}

	public double getApplyRemain() {
		return applyRemain;
	}

	public void setApplyRemain(double applyRemain) {
		this.applyRemain = applyRemain;
	}
	
	
}
