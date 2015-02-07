package com.bestway.bls.entity;

/**
 * 仓单申报结果
 * 
 * @author Administrator
 * 
 */
public class BlsDeliveryResultType implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4590282136632353718L;
	/**
	 * 初始库存申报【初始库存已接收，正在进行初始库存审核!】
	 */
	public static final int R0 = 0;
	/**
	 * 初始库存申报成功【建立初始库存成功!】
	 */
	public static final int R1 = 1;
	/**
	 * 入仓单申报【入仓单已接收,正在进行入仓单审核!】
	 */
	public static final int R2 = 2;
	/**
	 * 入仓单审核通过【入仓单已通过审核,未收到入仓单对应的货物入仓报文!】
	 */
	public static final int R3 = 3;
	/**
	 * 货到报文申报【入仓单已通过审核,未接收到入仓单对应的报关单申报!】
	 */
	public static final int R4 = 4;
	/**
	 * 入仓对应报关单申报 【入仓单已通过审核,入仓报关单已接收,正在进行入仓单－入仓报关单关系审核!】
	 */
	public static final int R5 = 5;
	/**
	 * 入仓对应报关单审核通过【入仓单申报成功!】
	 */
	public static final int R6 = 6;
	/**
	 * 出仓单申报【出仓单已接收,正在进行出仓单审核!】
	 */
	public static final int R7 = 7;
	/**
	 * 出仓单审核通过【出仓单已通过审核,未收到出仓单对应的货物出仓报文!】
	 */
	public static final int R8 = 8;
	/**
	 * 货到报文申报【出仓单已通过审核,未接收到对应的入仓单-出仓单关系申报!】
	 */
	public static final int R9 = 9;
	/**
	 * 出入仓单关系申报【出仓单已通过审核, 未接收到出仓单对应的报关单申报!】
	 */
	public static final int R10 = 10;
	/**
	 * 出仓对应报关单申报 【出仓单已通过审核,出仓报关单已接收,正在进行出仓单－出仓报关单关系审核!】
	 */
	public static final int R11 = 11;
	/**
	 * 出仓对应报关单审核通过 【出仓单申报成功!】
	 */
	public static final int R12 = 12;
	/**
	 * 【申报失败】
	 */
	public static final int RF1 = -1;

	/**
	 * 根据回执结果代码返回文字描述信息
	 * 
	 * @param receiptResultType
	 * @return
	 */
	public static final String getReceiptResultSpecByType(int resultType) {
		if (BlsDeliveryResultType.R0 == resultType) {
			return "初始库存申报【初始库存已接收，正在进行初始库存审核!】";
		} else if (BlsDeliveryResultType.R1 == resultType) {
			return "初始库存申报成功【建立初始库存成功!】";
		} else if (BlsDeliveryResultType.R2 == resultType) {
			return "入仓单申报【入仓单已接收,正在进行入仓单审核!】";
		} else if (BlsDeliveryResultType.R3 == resultType) {
			return "入仓单审核通过【入仓单已通过审核,未收到入仓单对应的货物入仓报文!】";
		} else if (BlsDeliveryResultType.R4 == resultType) {
			return "货到报文申报【入仓单已通过审核,未接收到入仓单对应的报关单申报!】";
		} else if (BlsDeliveryResultType.R5 == resultType) {
			return "入仓对应报关单申报【入仓单已通过审核,入仓报关单已接收,正在进行入仓单－入仓报关单关系审核!】";
		} else if (BlsDeliveryResultType.R6 == resultType) {
			return "入仓对应报关单审核通过【入仓单申报成功!】";
		} else if (BlsDeliveryResultType.R7 == resultType) {
			return "出仓单申报【出仓单已接收,正在进行出仓单审核!】";
		} else if (BlsDeliveryResultType.R8 == resultType) {
			return "出仓单审核通过【出仓单已通过审核,未收到出仓单对应的货物出仓报文!】";
		} else if (BlsDeliveryResultType.R9 == resultType) {
			return "货到报文申报【出仓单已通过审核,未接收到对应的入仓单-出仓单关系申报!】";
		} else if (BlsDeliveryResultType.R10 == resultType) {
			return "出入仓单关系申报【出仓单已通过审核, 未接收到出仓单对应的报关单申报!】";
		} else if (BlsDeliveryResultType.R11 == resultType) {
			return "出仓对应报关单申报【出仓单已通过审核,出仓报关单已接收,正在进行出仓单－出仓报关单关系审核!】";
		} else if (BlsDeliveryResultType.R12 == resultType) {
			return "出仓对应报关单审核通过【出仓单申报成功!】";
		} else if (BlsDeliveryResultType.RF1 == resultType) {
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
		if (String.valueOf(BlsDeliveryResultType.R1).equals(
				blsReceiptResult.getServiceStatus())
				|| String.valueOf(BlsDeliveryResultType.R6).equals(
						blsReceiptResult.getServiceStatus())
				|| String.valueOf(BlsDeliveryResultType.R12).equals(
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
		if (String.valueOf(BlsDeliveryResultType.RF1).equals(
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
		if (!String.valueOf(BlsDeliveryResultType.RF1).equals(
				blsReceiptResult.getServiceStatus())
				&& !String.valueOf(BlsDeliveryResultType.R1).equals(
						blsReceiptResult.getServiceStatus())
				&& !String.valueOf(BlsDeliveryResultType.R6).equals(
						blsReceiptResult.getServiceStatus())
				&& !String.valueOf(BlsDeliveryResultType.R12).equals(
						blsReceiptResult.getServiceStatus())) {
			return true;
		} else {
			return false;
		}
	}
}
