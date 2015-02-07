package com.bestway.bls.entity;

/**
 * 回执信息代码 
 * @author Administrator
 *
 */
public class BlsReceiptResultType implements java.io.Serializable{
	/**
	 * 成功
	 */
	public static final String SUCCESS = "0";
	/**
	 * 失败
	 */
	public static final String FAILURE = "1";
	/**
	 * 未完成
	 */
	public static final String NOT_COMPLETE = "2";
	/**
	 * 根据回执结果代码返回文字描述信息 
	 * @param receiptResultType
	 * @return
	 */
	public static final String getReceiptResultSpecByType(
			String receiptResultType) {
		if (BlsReceiptResultType.SUCCESS.equals(receiptResultType)) {
			return "申报成功";
		} else if (BlsReceiptResultType.FAILURE.equals(receiptResultType)) {
			return "申报失败";
		} else if (BlsReceiptResultType.NOT_COMPLETE.equals(receiptResultType)) {
			return "等待审批";
		} else {
			return "";
		}
	}
	/**
	 * 判断回执结果是否申报成功 
	 * @param blsReceiptResult
	 * @return
	 */
	public static final boolean checkReceiptResultIsSuccess(BlsReceiptResult blsReceiptResult){
		if(BlsReceiptResultType.SUCCESS.equals(blsReceiptResult.getServiceStatus())){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断回执结果是否申报失败 
	 * @param blsReceiptResult
	 * @return
	 */
	public static final boolean checkReceiptResultIsFail(BlsReceiptResult blsReceiptResult){
		if(BlsReceiptResultType.FAILURE.equals(blsReceiptResult.getServiceStatus())){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断回执结果是否等待审批
	 * @param blsReceiptResult
	 * @return
	 */
	public static final boolean checkReceiptResultIsWaitfor(BlsReceiptResult blsReceiptResult){
		if(BlsReceiptResultType.NOT_COMPLETE.equals(blsReceiptResult.getServiceStatus())){
			return true;
		}else{
			return false;
		}
	}
}
