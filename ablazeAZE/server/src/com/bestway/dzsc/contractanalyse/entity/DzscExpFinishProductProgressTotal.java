/*
 * Created on 2005-6-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractanalyse.entity;

import java.io.Serializable;

import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;

/**
 * 存放报关分析－－成品执行情况分析－－出口成品执行进度总表资料
 * 
 * @author ls
 */
public class DzscExpFinishProductProgressTotal implements Serializable{
	
	/**
	 * 合同成品
	 */
    private DzscEmsExgBill contractExg = null;
	
	/**
	 * 出口合计
	 */
    private Double  expTotal               = null;
	
	/**
	 * 成品出口数量
	 */
    private Double  finishProductExpAmount = null;
	
	/**
	 * 转厂出口数量
	 */
    private Double  transferExpAmount      = null; 
	
	/**
	 * 退厂返工总量
	 */
    private Double  backFactoryRework      = null;
	
	/**
	 * 退料复出(返工复出)总量
	 */
    private Double  reworkExpAmount        = null; 
	
	/**
	 * 可出口总量
	 */
    private Double  canExpAmount           = null; 
    
  
    
    
    /**
     * 获取退厂返工总量
     * 
     * @return backFactoryRework 退厂返工总量
     */
    public Double getBackFactoryRework() {
        return backFactoryRework;
    }
    
    /**
     * 获取可出口总量
     * 
     * @return canExpAmount 可出口总量
     */
    public Double getCanExpAmount() {
        return canExpAmount;
    }
    
    /**
     * 获取合同成品
     * 
     * @return contractExg 合同成品
     */
    public DzscEmsExgBill getContractExg() {
        return contractExg;
    }
    
    /**
     * 获取出口合计
     * 
     * @return expTotal 出口合计
     */
    public Double getExpTotal() {
        return expTotal;
    }
    
    /**
     * 获取成品出口数量
     * 
     * @return  finishProductExpAmoun 成品出口数量
     */
    public Double getFinishProductExpAmount() {
        return finishProductExpAmount;
    }
    
    /**
     * 获取退料复出(返工复出)总量
     * 
     * @return reworkExpAmount 退料复出(返工复出)总量
     */
    public Double getReworkExpAmount() {
        return reworkExpAmount;
    }
    
    /**
     * 获取转厂出口数量
     * 
     * @return transferExpAmount 转厂出口数量
     */
    public Double getTransferExpAmount() {
        return transferExpAmount;
    }
    
    /**
     * 设置退厂返工总量
     * 
     * @param backFactoryRework 退厂返工总量
     */
    public void setBackFactoryRework(Double backFactoryRework) {
        this.backFactoryRework = backFactoryRework;
    }
    
    /**
     * 设置可出口总量
     * 
     * @param canExpAmount 可出口总量
     */
    public void setCanExpAmount(Double canExpAmount) {
        this.canExpAmount = canExpAmount;
    }
    
    /**
     * 设置合同成品
     * 
     * @param contractExg 合同成品
     */
    public void setContractExg(DzscEmsExgBill contractExg) {
        this.contractExg = contractExg;
    }
    
    /**
     * 设置出口合计
     * 
     * @param expTotal 出口合计
     */
    public void setExpTotal(Double expTotal) {
        this.expTotal = expTotal;
    }
    
    /**
     * 设置成品出口数量
     * 
     * @param finishProductExpAmount 成品出口数量
     */
    public void setFinishProductExpAmount(Double finishProductExpAmount) {
        this.finishProductExpAmount = finishProductExpAmount;
    }
    
    /**
     * 设置退料复出(返工复出)总量
     * 
     * @param reworkExpAmount 退料复出(返工复出)总量
     */
    public void setReworkExpAmount(Double reworkExpAmount) {
        this.reworkExpAmount = reworkExpAmount;
    }
    
    /**
     * 设置转厂出口数量
     * 
     * @param transferExpAmount 转厂出口数量
     */
    public void setTransferExpAmount(Double transferExpAmount) {
        this.transferExpAmount = transferExpAmount;
    }
}
