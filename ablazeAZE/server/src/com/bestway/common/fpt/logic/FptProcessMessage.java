package com.bestway.common.fpt.logic;

import java.util.List;

import com.bestway.common.message.entity.CspReceiptResult;

public interface FptProcessMessage {
	/**
	 * 回执处理
	 * 
	 */
	void processHandling(CspReceiptResult receiptResult);

}
