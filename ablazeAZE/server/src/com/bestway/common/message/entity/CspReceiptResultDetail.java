package com.bestway.common.message.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 报文信息接收明细
 * 
 * @author Administrator
 * 
 */
public class CspReceiptResultDetail extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 接收结果
	 */
	private CspReceiptResult cspReceiptResult = null;

	/**
	 * 结果信息
	 */
	private String resultInfo;

	public CspReceiptResult getCspReceiptResult() {
		return cspReceiptResult;
	}

	public void setCspReceiptResult(CspReceiptResult cspReceiptResult) {
		this.cspReceiptResult = cspReceiptResult;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = this.getFieldValueByLen(resultInfo, 255);
	}

}
