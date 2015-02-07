/*
 * Created on 2004-7-24
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.constant;

import java.io.Serializable;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class OwpProcessResult implements Serializable {
	/**
	 * 数据申报成功
	 */
	public static final String CHECK_PASS = "2";

	/**
	 * 成功入数据中心库
	 */
	public static final String IMP_CENTDB_SUCCESSD = "4";

	/**
	 * 入数据中心库失败
	 */
	public static final String IMP_CENTDB_FAIL = "5";

	/**
	 * 成功发往海关
	 */
	public static final String SEND_CUSTOMS_SUCCESSD = "6";

	/**
	 * 成功入海关库
	 */
	public static final String IN_STOCK_SUCCEED = "8";

	/**
	 * 入海关库失败
	 */
	public static final String IN_STOCK_FAIL = "9";

	/**
	 * 成功入海关库(自动审核检查通过)
	 */
	public static final String IN_STOCK_SUCCEED_AUTO = "A";

	/**
	 * 审批通过（包括收发/退货单撤消失败）
	 */
	public static final String CHECK_PASS_ALL = "B";

	/**
	 * 审核退单
	 */
	public static final String CHECK_BACK = "C";

	/**
	 * 撤消成功(加工区转出方已出区撤消发货)
	 */
	public static final String CANCEL_SUCCESSED = "P";

	/**
	 * 审核通过(发货待出区审核通过、退货待入区审核通过)
	 */
	public static final String Q = "Q";

	/**
	 * 撤消成功
	 */
	public static final String S = "S";

	/**
	 * 下载成功
	 */
	public static final String X = "X";

	/**
	 * QP接收成功
	 */
	public static final String QP_REC_SUCCESSD = "0";

	/**
	 * QP入库成功
	 */
	public static final String QP_IMPDB_SUCCESSD = "Y";

	/**
	 * QP处理失败
	 */
	public static final String QP_FAIL = "Z";

	public static String getResultDesc(String chkMark) {
		String resultDesc = "";
		if (IN_STOCK_SUCCEED.equals(chkMark)) {
			resultDesc = "成功入海关库";
		} else if (CHECK_PASS.equals(chkMark)) {
			resultDesc = "数据申报成功";
		} else if (IN_STOCK_FAIL.equals(chkMark)) {
			resultDesc = "入海关库失败";
		} else if (CHECK_PASS_ALL.equals(chkMark)) {
			resultDesc = "审批通过（包括收发/退货单撤消失败）";
		} else if (IN_STOCK_SUCCEED_AUTO.equals(chkMark)) {
			resultDesc = "成功入海关库(自动审核检查通过)";
		} else if (IMP_CENTDB_SUCCESSD.equals(chkMark)) {
			resultDesc = "成功入数据中心库";
		} else if (IMP_CENTDB_FAIL.equals(chkMark)) {
			resultDesc = "入数据中心库失败";
		} else if (SEND_CUSTOMS_SUCCESSD.equals(chkMark)) {
			resultDesc = "成功发往海关";
		} else if (CHECK_BACK.equals(chkMark)) {
			resultDesc = "审核退单";
		} else if (CANCEL_SUCCESSED.equals(chkMark)) {
			resultDesc = "撤消成功(加工区转出方已出区撤消发货)";
		} else if (QP_REC_SUCCESSD.equals(chkMark)) {
			resultDesc = "QP接收成功";
		} else if (QP_IMPDB_SUCCESSD.equals(chkMark)) {
			resultDesc = "QP入库成功";
		} else if (QP_FAIL.equals(chkMark)) {
			resultDesc = "QP处理失败";
		} else if (Q.equals(chkMark)) {
			resultDesc = "审核通过(发货待出区审核通过、退货待入区审核通过)";
		} else if (S.equals(chkMark)) {
			resultDesc = "撤消成功";
		} else if (X.equals(chkMark)) {
			resultDesc = "下载成功";
		}
		return resultDesc;
	}
}
