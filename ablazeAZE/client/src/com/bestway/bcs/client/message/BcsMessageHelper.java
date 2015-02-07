package com.bestway.bcs.client.message;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcs.message.action.BcsMessageAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.client.message.DgCspReceiptFile;

public final class BcsMessageHelper {
	private static BcsMessageHelper common = null;

	private static BcsMessageAction bcsMessageAction = null;

	public static BcsMessageHelper getInstance() {
		if (common == null) {
			common = new BcsMessageHelper();
			bcsMessageAction = (BcsMessageAction) CommonVars
					.getApplicationContext().getBean("bcsMessageAction");
		}
		return common;
	}

	private BcsMessageHelper() {

	}

//	/**
//	 * 获取电子手册监管模式
//	 * 
//	 * @return
//	 */
//	public Integer getBcsManageType() {
//		BcsParameterSet parameter = bcsMessageAction
//				.findBcsMessageDirSet(new Request(CommonVars.getCurrUser(),
//						true));
//		if (parameter != null) {
//			return parameter.getBcsManageType();
//		}
//		return null;
//	}
	/**
	 * 查看回执内容
	 * @param sysType
	 * @param copEmsNo
	 * @return
	 */
	public List showBcsReceiptFile(String sysType, String copEmsNo) {
		DgCspReceiptFile dg = new DgCspReceiptFile();
		dg.setSysType(sysType);
		dg.setCopEmsNo(copEmsNo);
		dg.setCspMessageAction(bcsMessageAction);
		dg.setVisible(true);
		if(dg.isOK()){
			return dg.getReturnFile();
		}else{
			return new ArrayList();
		}
	}
}
