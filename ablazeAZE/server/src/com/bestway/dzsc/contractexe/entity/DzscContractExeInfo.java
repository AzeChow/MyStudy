package com.bestway.dzsc.contractexe.entity;

import java.io.Serializable;

/**
 * 存放报关单商品的合同定量、合同余量、当前余量
 * 
 * @author yp
 */
public class DzscContractExeInfo implements Serializable {
	/**
	 * 合同定额=合同商品金额
	 */
	private double contractAmount; 

	/**
	 * 合同余额=合同定额-已生效报关单金额
	 */
	private double contractRemain;

	/**
	 * 当前余额
	 */
	private double currentRemain;

	
	/**
	 * 合同定额=合同商品金额
	 * 料件 当前余额=合同定额-((已生效+为生效)进口金额-退料出口（退换）+复出（退换）)
	 * 成品 当前余额=合同定额-((已生效+为生效)出口金额-退厂返工+返工复出）
	 */
	
	
	
	/**
	 * 获取合同定额
	 * 
	 * @return 合同定额
	 */
	public double getContractAmount() {
		return contractAmount;
	}

	/**
	 * 设置合同定额
	 * 
	 * @param contractAmount 合同定额
	 */
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}

	/**
	 * 获取合同余额
	 * 
	 * @return contractRemain 合同余额
	 */
	public double getContractRemain() {
		return contractRemain;
	}

	/**
	 * 设置合同余额
	 * 
	 * @param contractRemain 合同余额
	 */
	public void setContractRemain(double contractRemain) {
		this.contractRemain = contractRemain;
	}

	/**
	 * 获取当前余额
	 * 
	 * @return currentRemain 当前余额
	 */
	public double getCurrentRemain() {
		return currentRemain;
	}

	/**
	 * 设置当前余额
	 * 
	 * @param currentRemain 当前余额
	 */
	public void setCurrentRemain(double currentRemain) {
		this.currentRemain = currentRemain;
	}
}
