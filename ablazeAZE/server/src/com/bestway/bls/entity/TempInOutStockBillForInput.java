package com.bestway.bls.entity;

import java.io.Serializable;
/**
 * 倒入单据所需临时实体类
 * @author hw
 *
 */
public class TempInOutStockBillForInput implements Serializable{
    /**
     * 进出仓单据表头
     */
	private BlsInOutStockBill bsb=null;
	
	/**
	 * 进出仓单据表体
	 */
	private BlsInOutStockBillDetail bsd=null;
	
	public BlsInOutStockBill getBsb() {
		return bsb;
	}
	public void setBsb(BlsInOutStockBill bsb) {
		this.bsb = bsb;
	}
	public BlsInOutStockBillDetail getBsd() {
		return bsd;
	}
	public void setBsd(BlsInOutStockBillDetail bsd) {
		this.bsd = bsd;
	}
	public String getErrinfo() {
		return errinfo;
	}
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	
	/**
	 * 错误原因
	 */
	private String errinfo = null;
}
