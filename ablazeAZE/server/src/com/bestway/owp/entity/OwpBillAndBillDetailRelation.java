package com.bestway.owp.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 外发加工与单据中心关联表、用于判断单据引用后不能修改、删除。
 * @author sxk
 *
 */
public class OwpBillAndBillDetailRelation extends BaseScmEntity {
	private static final long serialVersionUID = -157886393309954960L;

	/**
	 * 外发加工登记表ID
	 */
	private String OwpBillId;
	
	/**
	 * 单据中心单据ID
	 */
	private String BillDetailId;

	/**
	 * 获取外发加工登记表ID
	 * @return
	 */
	public String getOwpBillId() {
		return OwpBillId;
	}

	/**
	 * 设置外发加工登记表ID
	 * @param owpBillId
	 */
	public void setOwpBillId(String owpBillId) {
		OwpBillId = owpBillId;
	}

	/**
	 * 获取单据中心单据ID
	 * @return
	 */
	public String getBillDetailId() {
		return BillDetailId;
	}

	/**
	 * 设置单据中心单据ID
	 * @param billDetailId
	 */
	public void setBillDetailId(String billDetailId) {
		BillDetailId = billDetailId;
	}
	
	
}
