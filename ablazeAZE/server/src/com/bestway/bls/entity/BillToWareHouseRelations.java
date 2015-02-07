package com.bestway.bls.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 转仓单临时关系表
 * 
 * @author hw
 * 
 */
public class BillToWareHouseRelations extends BaseScmEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 单据商品信息
	 */
	private BlsInOutStockBillDetail bsd;

	/**
	 * 仓单归并后商品
	 */
	private StorageBillBefore sbb;

	/**
	 * 获取单据商品信息
	 * 
	 * @return
	 */
	public BlsInOutStockBillDetail getBsd() {
		return bsd;
	}

	/**
	 * 设置单据商品信息
	 * 
	 * @param bsd
	 */
	public void setBsd(BlsInOutStockBillDetail bsd) {
		this.bsd = bsd;
	}

	/**
	 * 获取仓单表体(归并前)
	 * 
	 * @return
	 */
	public StorageBillBefore getSbb() {
		return sbb;
	}

	/**
	 * 设置仓单表体(归并前)
	 * 
	 * @param sbb
	 */
	public void setSbb(StorageBillBefore sbb) {
		this.sbb = sbb;
	}

	/**
	 * 仓单表头
	 */
	private StorageBill sb;

	/**
	 * 获取仓单表头
	 * 
	 * @return
	 */
	public StorageBill getSb() {
		return sb;
	}

	/**
	 * 设置仓单表头
	 * 
	 * @param sb
	 */
	public void setSb(StorageBill sb) {
		this.sb = sb;
	}

	/**
	 * 入仓单归并后表体
	 */
	private StorageBillAfter sbAfter;

	/**
	 * 获取入仓单归并后表体
	 * 
	 * @return
	 */
	public StorageBillAfter getSbAfter() {
		return sbAfter;
	}

	/**
	 * 设置入仓单归并后表体
	 * 
	 * @param sbAfter
	 */
	public void setSbAfter(StorageBillAfter sbAfter) {
		this.sbAfter = sbAfter;
	}
}
