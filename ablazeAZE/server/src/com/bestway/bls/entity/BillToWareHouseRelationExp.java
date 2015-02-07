package com.bestway.bls.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 保存出仓单据与出仓单之间的对应关系
 * @author hw
 */
public class BillToWareHouseRelationExp extends BaseScmEntity {
	/**
	 * 单据明细
	 */
	private BlsInOutStockBillDetail bsd;

	/**
	 * 仓单表头
	 */
	private StorageBill sb;
	
	/**
	 * 获取出仓单据表体明细
	 * @return
	 */
	public BlsInOutStockBillDetail getBsd() {
		return bsd;
	}

	/**
	 * 设置出仓单据表体明细
	 * @param bsd
	 */
	public void setBsd(BlsInOutStockBillDetail bsd) {
		this.bsd = bsd;
	}

	/**
	 * 获取出仓单表头
	 * @return
	 */
	public StorageBill getSb() {
		return sb;
	}

	/**
	 * 设置出仓单表头
	 * @param sb
	 */
	public void setSb(StorageBill sb) {
		this.sb = sb;
	}
}
