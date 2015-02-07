/*
 * Created on 2004-7-24
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CspProcessResult implements Serializable {
	public static final String IN_STOCK_SUCCEED = "1"; // 入库成功

	public static final String CHECK_PASS = "2"; // 审批通过

	public static final String BACK_BILL = "3"; // 退单

	public static final String RE_DECLARE = "4"; // 重复申报(退单)

	public static final String IN_STOCK_SUCCEED_AUTO = "7"; // 成功入海关库(自动审核检查通过)

	public static final String IMP_CENTDB_SUCCESSD = "a"; // 成功入数据中心库

	public static final String IMP_CENTDB_FAIL = "b"; // 入数据中心库失败

	public static final String SEND_CUSTOMS_SUCCESSD = "c"; // 成功发往海关

	public static final String SEND_CUSTOMS_FAIL = "d"; // 发往海关失败

	// public static final String AA="e"; // QP服务器解析签名成功
	// public static final String AA="f"; // 成功入QP服务器数据库
	public static final String IMP_QPDB_FAIL = "g"; // 入QP服务器数据库失败

	// public static final String AA="P"; // 合同到期催报
	// public static final String AA="S"; // 手册暂停
	// public static final String AA="U"; // 手册恢复
	// public static final String AA="H"; // 手册挂账
	// public static final String AA="T"; // 手册核算完成
	// public static final String AA="O"; // 手册结案
	// public static final String AA="A"; // 手册取消报核申请
	// public static final String AA="B"; // 手册取消核算
	// public static final String AA="C"; // 手册取消结案
	// public static final String AA="D"; // 保函展期通知
	// public static final String AA="F"; // 索赔函发出通知
	public static final String QP_REC_SUCCESSD = "0"; // QP接收成功

	public static final String QP_IMPDB_SUCCESSD = "y"; // QP入库成功

	public static final String QP_FAIL = "z"; // QP处理失败

	public static String getResultDesc(String chkMark) {
		String resultDesc = "";
		if (IN_STOCK_SUCCEED.equals(chkMark)) {
			resultDesc = "入库成功";
		} else if (CHECK_PASS.equals(chkMark)) {
			resultDesc = "审批通过";
		} else if (BACK_BILL.equals(chkMark)) {
			resultDesc = "退单";
		} else if (RE_DECLARE.equals(chkMark)) {
			resultDesc = "重复申报(退单)";
		} else if (IN_STOCK_SUCCEED_AUTO.equals(chkMark)) {
			resultDesc = "成功入海关库(自动审核检查通过)";
		} else if (IMP_CENTDB_SUCCESSD.equals(chkMark)) {
			resultDesc = "成功入数据中心库";
		} else if (IMP_CENTDB_FAIL.equals(chkMark)) {
			resultDesc = "入数据中心库失败";
		} else if (SEND_CUSTOMS_SUCCESSD.equals(chkMark)) {
			resultDesc = "成功发往海关";
		} else if (SEND_CUSTOMS_FAIL.equals(chkMark)) {
			resultDesc = "发往海关失败";
		} else if (IMP_QPDB_FAIL.equals(chkMark)) {
			resultDesc = "入QP服务器数据库失败";
		} else if (QP_REC_SUCCESSD.equals(chkMark)) {
			resultDesc = "QP接收成功";
		} else if (QP_IMPDB_SUCCESSD.equals(chkMark)) {
			resultDesc = "QP入库成功";
		} else if (QP_FAIL.equals(chkMark)) {
			resultDesc = "QP处理失败";
		}
		return resultDesc;
	}
}
