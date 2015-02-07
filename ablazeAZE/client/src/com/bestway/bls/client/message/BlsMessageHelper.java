package com.bestway.bls.client.message;

import java.util.ArrayList;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.bls.entity.BlsReceiptResult;

public final class BlsMessageHelper {
	private static BlsMessageHelper common = null;

	private static BlsMessageAction blsMessageAction = null;

	public static BlsMessageHelper getInstance() {
		if (common == null) {
			common = new BlsMessageHelper();
			blsMessageAction = (BlsMessageAction) CommonVars
					.getApplicationContext().getBean("blsMessageAction");
		}
		return common;
	}

	private BlsMessageHelper() {

	}

	/**
	 * 查看回执内容
	 * 
	 * @param sysType
	 * @param copEmsNo
	 * @return
	 */
	public BlsReceiptResult showBlsReceiptFile(String messageType,
			String relateID) {
		DgBlsReceiptResult dg = new DgBlsReceiptResult();
		dg.setMessageType(messageType);
		dg.setRelateID(relateID);
		dg.setVisible(true);
		if (dg.isOK()) {
			return dg.getBlsReceiptResult();
		} else {
			return null;
		}
	}
}
