package com.bestway.common.fpt.qp.action;

import java.util.List;

public interface FptQpAction {
	/**
	 * <summary> 获得出口明细 </summary> <param name="outTradeCode"></param> <param
	 * name="outCopAppNo"></param> <returns></returns>
	 */
	List findFptAppItemOutByQp(String outTradeCode, String outCopAppNo);

	/**
	 * <summary> 获得进口明细 </summary> <param name="outTradeCode"></param> <param
	 * name="outCopAppNo"></param> <returns></returns>
	 */
	List findFptAppItemInByQp(String outTradeCode, String outCopAppNo);

	/**
	 * <summary> 获得 正在处理 的转出申请表 来自海关编码没有明细 </summary> <param
	 * name="outTradeCode"></param> <returns></returns>
	 */
	List findFptAppHeadByQpNoList(String outTradeCode);

	/**
	 * <summary> 获得 转出申请表 来自海关编码 有明细 </summary> <param name="outTradeCode"></param>
	 * <returns></returns>
	 */
	List findFptAppHeadByQpHasList(String outTradeCode, List outCopAppNos);

	    // /////////////////////////以下是转厂收退货单///////////////////////////

	/**
	 * <summary> 获得收发货单出口明细 </summary> <param name="outTradeCode"></param>
	 * <param name="outCopAppNo"></param> <returns></returns>
	 */
	List findFptBillItemOutByQp(String issueTradeCod,String receiveTradeCod);

	/**
	 * <summary> 获得收发货单进口明细 </summary> <param name="outTradeCode"></param>
	 * <param name="outCopAppNo"></param> <returns></returns>
	 */
	List findFptBillItemInByQp(String issueTradeCod,String receiveTradeCod);
}
