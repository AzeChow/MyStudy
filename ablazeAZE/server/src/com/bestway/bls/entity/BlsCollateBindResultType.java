package com.bestway.bls.entity;

/**
 * 核销捆绑申报结果
 * 
 * @author Administrator
 * 
 */
public class BlsCollateBindResultType implements java.io.Serializable {
	/**
	 * 出入仓关系申报【出入仓关系核销报文已接收，正在进行核销报文审核!】
	 */
	public static final int R0 = 0;
	/**
	 * 出入仓关系【申报成功】
	 */
	public static final int R1 = 1;
	/**
	 * 报关单申报【报关单核销报文已接收，正在进行核销报文审核!】
	 */
	public static final int R2 = 2;
	/**
	 * 报关单申报成功【申报成功!】
	 */
	public static final int R3 = 3;
	/**
	 * 【申报失败！】
	 */
	public static final int RF1 = -1;

	/**
	 * 根据回执结果代码返回文字描述信息
	 * 
	 * @param receiptResultType
	 * @return
	 */
	public static final String getReceiptResultSpecByType(int resultType) {
		if (BlsCollateBindResultType.R0 == resultType) {
			return "出入仓关系申报【出入仓关系核销报文已接收，正在进行核销报文审核!】";
		} else if (BlsCollateBindResultType.R1 == resultType) {
			return "出入仓关系【申报成功】";
		} else if (BlsCollateBindResultType.R2 == resultType) {
			return "报关单申报【报关单核销报文已接收，正在进行核销报文审核!】";
		} else if (BlsCollateBindResultType.R3 == resultType) {
			return "报关单申报成功【申报成功!】";
		} else if (BlsCollateBindResultType.RF1 == resultType) {
			return "【申报失败】";
		} else {
			return "";
		}
	}

	/**
	 * 判断回执结果是否申报成功
	 * 
	 * @param blsReceiptResult
	 * @return
	 */
	public static final boolean checkReceiptResultIsSuccess(
			BlsReceiptResult blsReceiptResult) {
		if (String.valueOf(BlsCollateBindResultType.R1).equals(
				blsReceiptResult.getServiceStatus())
				|| String.valueOf(BlsCollateBindResultType.R3).equals(
						blsReceiptResult.getServiceStatus())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断回执结果是否申报失败
	 * 
	 * @param blsReceiptResult
	 * @return
	 */
	public static final boolean checkReceiptResultIsFail(
			BlsReceiptResult blsReceiptResult) {
		if (String.valueOf(BlsCollateBindResultType.RF1).equals(
				blsReceiptResult.getServiceStatus())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断回执结果是否正在审批
	 * 
	 * @param blsReceiptResult
	 * @return
	 */
	public static final boolean checkReceiptResultIsWaitfor(
			BlsReceiptResult blsReceiptResult) {
		if (!String.valueOf(BlsCollateBindResultType.R1).equals(
				blsReceiptResult.getServiceStatus())
				&& !String.valueOf(BlsCollateBindResultType.R3).equals(
						blsReceiptResult.getServiceStatus())
				&& !String.valueOf(BlsCollateBindResultType.RF1).equals(
						blsReceiptResult.getServiceStatus())) {
			return true;
		} else {
			return false;
		}
	}
}
