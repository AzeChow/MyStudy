/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.fpt.entity;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 获得转厂的单据商品信息
 * 
 * @author
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MakeFptBillFromCasBill extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 海关帐工厂单据明细ID
	 */
	private String billDetailId = null;

	/**
	 * 结转单据明细ID
	 */
	private String fptBillItemId = null;

	/**
	 * 海关帐工厂单据明细，查询显示所用，不保存在数据库
	 */
	private BillDetail tempBillDetail = null;

	/**
	 * 结转单据明细，查询显示所用，不保存在数据库
	 */
	private FptBillItem tempFptBillItem = null;

	public String getBillDetailId() {
		return billDetailId;
	}

	public void setBillDetailId(String billDetailId) {
		this.billDetailId = billDetailId;
	}

	public String getFptBillItemId() {
		return fptBillItemId;
	}

	public void setFptBillItemId(String fptBillItemId) {
		this.fptBillItemId = fptBillItemId;
	}

	public BillDetail getTempBillDetail() {
		return tempBillDetail;
	}

	public void setTempBillDetail(BillDetail tempBillDetail) {
		this.tempBillDetail = tempBillDetail;
	}

	public FptBillItem getTempFptBillItem() {
		return tempFptBillItem;
	}

	public void setTempFptBillItem(FptBillItem tempFptBillItem) {
		this.tempFptBillItem = tempFptBillItem;
	}

}