package com.bestway.common.fpt.entity;

import java.io.Serializable;

public class TempFptExeInfo implements Serializable{
	/**
	 * 合同定量=合同数量
	 */
	private double contractAmount = 0.0; 

	/**
	 * 合同余量=合同定量-已正在执行转厂数量
	 */
	private double contractRemain= 0.0;// 

	/**
	 * 当前余量=合同定量-已所有转厂数量(如果有正在执行和变更的去掉正在执行的)
	 */
	private double currentRemain= 0.0;
	
	/**
	 * 可申请申请表数量=合同定量-全部报关单数量(除去转厂)-全部收发送货数(含收退货)
	 */
	 private double applyRemain = 0.0;

	/**
	 * @return the contractAmount
	 */
	public double getContractAmount() {
		return contractAmount;
	}

	/**
	 * @param contractAmount the contractAmount to set
	 */
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}

	/**
	 * @return the contractRemain
	 */
	public double getContractRemain() {
		return contractRemain;
	}

	/**
	 * @param contractRemain the contractRemain to set
	 */
	public void setContractRemain(double contractRemain) {
		this.contractRemain = contractRemain;
	}

	/**
	 * @return the currentRemain
	 */
	public double getCurrentRemain() {
		return currentRemain;
	}

	/**
	 * @param currentRemain the currentRemain to set
	 */
	public void setCurrentRemain(double currentRemain) {
		this.currentRemain = currentRemain;
	}

	public double getApplyRemain() {
		return applyRemain;
	}

	public void setApplyRemain(double applyRemain) {
		this.applyRemain = applyRemain;
	}
	
	
	
}
