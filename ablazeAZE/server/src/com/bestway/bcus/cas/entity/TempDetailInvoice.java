package com.bestway.bcus.cas.entity;

import com.bestway.bcus.cas.bill.entity.BillDetailMateriel;
import com.bestway.common.BaseEntity;

public class TempDetailInvoice extends BaseEntity {

	private BillDetailMateriel billDetail;
	private boolean iscancel;
	
	
	public BillDetailMateriel getBillDetail() {
		return billDetail;
	}
	public void setBillDetail(BillDetailMateriel billDetail) {
		this.billDetail = billDetail;
	}
	public boolean isIscancel() {
		return iscancel;
	}
	public void setIscancel(boolean iscancel) {
		this.iscancel = iscancel;
	}

}
