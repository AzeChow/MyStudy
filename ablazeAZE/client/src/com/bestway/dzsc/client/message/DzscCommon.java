package com.bestway.dzsc.client.message;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.client.message.DgCspReceiptFile;
import com.bestway.dzsc.message.action.DzscMessageAction;

public final class DzscCommon {
	private static DzscCommon common = null;

	private static DzscMessageAction dzscMessageAction = null;

	public static DzscCommon getInstance() {
		if (common == null) {
			common = new DzscCommon();
			dzscMessageAction = (DzscMessageAction) CommonVars
					.getApplicationContext().getBean("dzscMessageAction");
		}
		return common;
	}

	private DzscCommon() {

	}

//	/**
//	 * 获取电子手册监管模式
//	 * 
//	 * @return
//	 */
//	public Integer getDzscManageType() {
//		DzscParameterSet parameter = dzscMessageAction
//				.findDzscMessageDirSet(new Request(CommonVars.getCurrUser(),
//						true));
//		if (parameter != null) {
//			return parameter.getDzscManageType();
//		}
//		return null;
//	}
	/**
	 * 查看回执内容
	 * @param sysType
	 * @param copEmsNo
	 * @return
	 */
	public List showDzscReceiptFile(String sysType, String copEmsNo) {
		DgCspReceiptFile dg = new DgCspReceiptFile();
		dg.setSysType(sysType);
		dg.setCopEmsNo(copEmsNo);
		dg.setCspMessageAction(dzscMessageAction);
		dg.setVisible(true);
		if(dg.isOK()){
			return dg.getReturnFile();
		}else{
			return new ArrayList();
		}
	}
}
