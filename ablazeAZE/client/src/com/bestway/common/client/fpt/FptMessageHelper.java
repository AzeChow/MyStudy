package com.bestway.common.client.fpt;

import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.fpt.action.FptMessageAction;

public final class FptMessageHelper {
	private static FptMessageHelper common = null;

	private static FptMessageAction fptMessageAction = null;

	public static FptMessageHelper getInstance() {
		if (common == null) {
			common = new FptMessageHelper();
			fptMessageAction = (FptMessageAction) CommonVars
					.getApplicationContext().getBean("fptMessageAction");
		}
		return common;
	}

	private FptMessageHelper() {

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
		DgFptReceiptFile dg = new DgFptReceiptFile();
		dg.setSysType(sysType);
		dg.setCopEmsNo(copEmsNo);
		dg.setCspMessageAction(fptMessageAction);
		dg.setVisible(true);
		if(dg.isOK()){
			return dg.getReturnFile();
		}else{
			return new ArrayList();
		}
	}
	
	public void showFptReceiptResult(String sysType, String inOutFlag,String copEmsNo) {
		DgFptReceiptResult dg = new DgFptReceiptResult();
		dg.setSysType(sysType);
		dg.setCopEmsNo(copEmsNo);
		dg.setInOutFlag(inOutFlag);
		dg.setVisible(true);		
	}
}
