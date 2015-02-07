package com.bestway.client.owp;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.client.message.DgCspReceiptFile;
import com.bestway.owp.action.OwpMessageAction;

public final class OwpMessageHelper {
	private static OwpMessageHelper common = null;

	private static OwpMessageAction owpMessageAction = null;

	public static OwpMessageHelper getInstance() {
		if (common == null) {
			common = new OwpMessageHelper();
			owpMessageAction = (OwpMessageAction) CommonVars
					.getApplicationContext().getBean("owpMessageAction");
		}
		return common;
	}

	private OwpMessageHelper() {

	}

	/**
	 * 查看回执内容
	 * @param sysType
	 * @param copEmsNo
	 * @return
	 */
	public List showOwpReceiptFile(String sysType, String copEmsNo) {
		DgCspReceiptFile dg = new DgCspReceiptFile();
		dg.setSysType(sysType);
		dg.setCopEmsNo(copEmsNo);
		dg.setCspMessageAction(owpMessageAction);
		dg.setVisible(true);
		if(dg.isOK()){
			return dg.getReturnFile();
		}else{
			return new ArrayList();
		}
	}
}
