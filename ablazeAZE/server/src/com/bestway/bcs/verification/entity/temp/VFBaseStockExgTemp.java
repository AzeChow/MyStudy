package com.bestway.bcs.verification.entity.temp;

import com.bestway.bcs.verification.entity.VFBaseStockExg;

public class VFBaseStockExgTemp extends VFBaseStockExg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 错误信息
	 */
	private String errorInfo;

	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
}
