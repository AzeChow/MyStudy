/*
 * Created on 2005-5-13
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractcav.entity;

import java.io.Serializable;

/**
 * 存放纸质手册合同核销的进出口方面的总和
 */
public class ContractCavTotalValue implements Serializable {

	/**
	 * 实际进口总值
	 */
	private Double impAmount = null;

	/**
	 * 出口总金额
	 */
	private Double expAmount = null;

	/**
	 * 加工费总价
	 */
	private Double processTotalPrice; 

	/**
	 * 出口总件数
	 */
	private Double expTotalPieces; 

	/**
	 * 出口总毛重
	 */
	private Double expTotalGrossWeight; 

	/**
	 * 出口总净重
	 */
	private Double expTotalNetWeight; 

	/**
	 * 进口总毛重
	 */
	private Double impTotalGrossWeight; 

	/**
	 * 进口总净重
	 */
	private Double impTotalNetWeight; 
	
	/**
	 * 获取出口总金额
	 * 
	 * @return expAmount 出口总金额
	 */
	public Double getExpAmount() {
		return expAmount;
	}
	
	/**
	 * 获取出口总毛重
	 * 
	 * @return expTotalGrossWeight 出口总毛重
	 */
	public Double getExpTotalGrossWeight() {
		return expTotalGrossWeight;
	}
	
	/**
	 * 获取出口总净重
	 * 
	 * @return expTotalNetWeight 出口总净重
	 */
	public Double getExpTotalNetWeight() {
		return expTotalNetWeight;
	}
	
	/**
	 * 获取出口总件数
	 * 
	 * @return expTotalPieces 出口总件数
	 */
	public Double getExpTotalPieces() {
		return expTotalPieces;
	}
	
	/**
	 * 获取进口总金额
	 * 
	 * @return impAmount 进口总金额
	 */
	public Double getImpAmount() {
		return impAmount;
	}
	
	/**
	 * 获取进口总毛重
	 * 
	 * @return impTotalGrossWeight 进口总毛重
	 */
	public Double getImpTotalGrossWeight() {
		return impTotalGrossWeight;
	}
	
	/**
	 * 获取进口总净重
	 * 
	 * @return impTotalNetWeight 进口总净重
	 */
	public Double getImpTotalNetWeight() {
		return impTotalNetWeight;
	}
	
	/**
	 * 获取加工费总价
	 * 
	 * @return processTotalPrice 加工费总价
	 */
	public Double getProcessTotalPrice() {
		return processTotalPrice;
	}
	
	/**
	 * 设置出口总金额
	 * 
	 * @param expAmount 出口总金额
	 */
	public void setExpAmount(Double expAmount) {
		this.expAmount = expAmount;
	}
	
	/**
	 * 设置出口总毛重
	 * 
	 * @param expTotalGrossWeight 出口总毛重
	 */
	public void setExpTotalGrossWeight(Double expTotalGrossWeight) {
		this.expTotalGrossWeight = expTotalGrossWeight;
	}
	
	/**
	 * 设置出口总净重
	 * 
	 * @param expTotalNetWeight 出口总净重
	 */
	public void setExpTotalNetWeight(Double expTotalNetWeight) {
		this.expTotalNetWeight = expTotalNetWeight;
	}
	
	/**
	 * 设置出口总件数
	 * 
	 * @param expTotalPieces 出口总件数
	 */
	public void setExpTotalPieces(Double expTotalPieces) {
		this.expTotalPieces = expTotalPieces;
	}
	
	/**
	 * 设置进口总金额
	 * 
	 * @param impAmount 进口总金额
	 */
	public void setImpAmount(Double impAmount) {
		this.impAmount = impAmount;
	}
	
	/**
	 * 设置进口总毛重
	 * 
	 * @param impTotalGrossWeight 进口总毛重
	 */
	public void setImpTotalGrossWeight(Double impTotalGrossWeight) {
		this.impTotalGrossWeight = impTotalGrossWeight;
	}
	
	/**
	 * 设置进口总净重
	 * 
	 * @param impTotalNetWeight 进口总净重
	 */
	public void setImpTotalNetWeight(Double impTotalNetWeight) {
		this.impTotalNetWeight = impTotalNetWeight;
	}
	
	/**
	 * 设置加工费总价
	 * 
	 * @param processTotalPrice 加工费总价
	 */
	public void setProcessTotalPrice(Double processTotalPrice) {
		this.processTotalPrice = processTotalPrice;
	}
}
