package com.bestway.bls.entity;

/**
 * 货到报告申报申报结果
 * 
 * @author Administrator
 * 
 */
public class BlsEntranceMessageResultType implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 货到报告申报成功!
	 */
	public static final int SUCCESS = 1;
	/**
	 * 货到报告申报失败!
	 */
	public static final int FAILURE = -1;

	/**
	 * 根据回执结果代码返回文字描述信息
	 * 
	 * @param receiptResultType
	 * @return
	 */
	public static final String getReceiptResultSpecByType(int resultType) {
		if (BlsEntranceMessageResultType.SUCCESS == resultType) {
			return "申报成功";
		} else if (BlsEntranceMessageResultType.FAILURE == resultType) {
			return "申报失败";
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
		if (String.valueOf(BlsEntranceMessageResultType.SUCCESS).equals(
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
		if (String.valueOf(BlsEntranceMessageResultType.FAILURE).equals(
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
		if (!String.valueOf(BlsEntranceMessageResultType.SUCCESS).equals(
				blsReceiptResult.getServiceStatus())
				&& !String.valueOf(BlsEntranceMessageResultType.FAILURE)
						.equals(blsReceiptResult.getServiceStatus())) {
			return true;
		} else {
			return false;
		}
	}
}
