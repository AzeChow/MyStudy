package com.bestway.bls.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;
/**
 * 倒入信息所用到的临时实体类
 * @author hw
 *
 */
public class TempInOutStockBills implements Serializable{
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 出入仓单表体
	 */
	private BlsInOutStockBillDetail bsd=null;
	
	/**
	 * 错误原因
	 */
	private String errinfo = null;
	/**
	 * 出入仓单表头
	 */
	private BlsInOutStockBill bsb=null;

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
    /**
     * 获取错误信息
     * @return
     */
	public String getErrinfo() {
		return errinfo;
	}
    /**
     * 设置错误信息
     * @param errinfo
     */
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}

	
}
