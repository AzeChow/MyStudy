package com.bestway.common.message.logic;

import com.bestway.common.message.entity.TempCspReceiptResultInfo;

public interface CspProcessMessage {
	/**
	 * 申报成功处理
	 * 
	 */
	void successHandling(TempCspReceiptResultInfo tempReceiptResult);

	/**
	 * 申报失败处理
	 * 
	 */
	void failureHandling(TempCspReceiptResultInfo tempReceiptResult);
}
