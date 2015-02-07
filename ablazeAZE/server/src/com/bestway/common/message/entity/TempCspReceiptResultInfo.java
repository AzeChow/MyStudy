package com.bestway.common.message.entity;

import java.io.Serializable;
import java.util.List;

public class TempCspReceiptResultInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private CspReceiptResult receiptResult = null;

	private List<CspReceiptResult> lsREceiptResult;

	public CspReceiptResult getReceiptResult() {
		return receiptResult;
	}

	public void setReceiptResult(CspReceiptResult receiptResult) {
		this.receiptResult = receiptResult;
	}

	public List<CspReceiptResult> getLsREceiptResult() {
		return lsREceiptResult;
	}

	public void setLsREceiptResult(List<CspReceiptResult> lsREceiptResult) {
		this.lsREceiptResult = lsREceiptResult;
	}
}
