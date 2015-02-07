package com.bestway.bcus.cas.entity;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.common.BaseEntity;

public class BillInput extends BaseEntity {
	private BillMaster master = null;
	private BillDetail detail = null;
	private String error = "";

	public BillMaster getMaster() {
		return master;
	}

	public void setMaster(BillMaster master) {
		this.master = master;
	}

	public BillDetail getDetail() {
		return detail;
	}

	public void setDetail(BillDetail detail) {
		this.detail = detail;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
