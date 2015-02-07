package com.bestway.fecav.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.fecav.entity.BillOfExchange;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.fecav.entity.FecavParameters;
import com.bestway.fecav.entity.ImpCustomsDeclaration;
import com.bestway.fecav.entity.StrikeBillOfExchange;
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;
import com.bestway.fecav.entity.TempBillOfExchangeForFecav;
import com.bestway.fecav.entity.TempCancelContractStat;
import com.bestway.fecav.entity.TempCancelSignInStat;
import com.bestway.fecav.entity.TempCustomsDeclarationInfoForFecav;

public interface FecavAction {
	/**
	 * 查询外汇核销单参数
	 * 
	 * @return
	 */
	FecavParameters findFecavParameters(Request request);

	/**
	 * 保存外汇核销单参数
	 * 
	 * @param fecavParameters
	 */
	FecavParameters saveFecavParameters(Request request,
			FecavParameters fecavParameters);

	/**
	 * 根据核销单开始号码和份数取得核销单结束号码
	 * 
	 * @param request
	 *            发送请求
	 * @param beginNo
	 *            开始号码
	 * @param num
	 *            份数
	 * @return 核销单结束号码
	 */
	String getEndFecavBillNo(Request request, String beginNo, int num);

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            进口报关单冲销
	 * @return 进口报关单冲销
	 */
	StrikeImpCustomsDeclaration saveStrikeImpCustomsDeclaration(
			Request request, StrikeImpCustomsDeclaration imp);

	/**
	 * 根据状态查询外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 与指定的状态匹配没有遗失作废的外汇核销单
	 */
	List findFecavBillByState(Request request, int fecavState);

	/**
	 * 根据状态查询外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 与指定条件匹配的没有遗失作废的外汇核销单
	 */
	List findFecavBillByState(Request request, int fecavState,
			String condition, List<Object> lsParam);

	/**
	 * 根据ID查询外汇核销单
	 * 
	 * @param id
	 *            外汇核销单id
	 * @return 与指定id匹配的外汇核销单
	 */
	FecavBill findFecavBillById(String id);

	/**
	 * 保存外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBill
	 *            外汇核销单
	 * @return 外汇核销单
	 */
	FecavBill saveFecavBill(Request request, FecavBill fecavBill);

	/**
	 * 删除外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBill
	 *            外汇核销单
	 */
	void deleteFecavBill(Request request, FecavBill fecavBill);

	/**
	 * 删除外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            要批量删除的外汇核销单
	 */
	void deleteFecavBill(Request request, List list);

	/**
	 * 批量外部领用外汇核销单据
	 * 
	 * @param request
	 *            发送请求
	 * @param beginNo
	 *            外部领用核销单开始号
	 * @param num
	 *            核销单份数
	 * @param outerObtain
	 *            外部核销单领用人
	 * @param outerObtainDate
	 *            领用日期
	 * @return 外部领用外汇核销单据
	 */
	List batchOuterObtainFecavBill(Request request, String beginNo, int num,
			String outerObtain, Date outerObtainDate);

	/**
	 * 批量内部领用外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param beginNo
	 *            内部领用核销单开始号
	 * @param num
	 *            核销单份数
	 * @param innerObtain
	 *            内部领用人
	 * @param innerObtainDate
	 *            内部领用日期
	 * @return 内部领用外汇核销单
	 */
	List batchInnerObtainFecavBill(Request request, String beginNo, int num,
			String innerObtain, Date innerObtainDate);

	/**
	 * 批量内部领用外汇核销单
	 * 
	 * @param beginNo
	 *            内部领用核销单开始号
	 * @param num
	 *            核销单份数
	 * @param innerObtain
	 *            内部领用人
	 * @param innerObtainDate
	 *            内部领用日期
	 * @return 内部领用外汇核销单
	 */
	List batchInnerObtainFecavBill(Request request, List list,
			String innerObtain, Date innerObtainDate);

	/**
	 * 批量取消内部领用
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            要取消的内部领用
	 * @return 取消后的内部领用 状态改为外部领用
	 */
	List batchUndoInnerObtainFecavBill(Request request, List list);

	/**
	 * 取得未与外汇核销单建立联系的报关单信息
	 * 
	 * @param request
	 *            发送请求
	 * @return 未与外汇核销单建立联系的报关单(联网监管 电子手册 纸质手册)信息
	 */
	List findCustomsDeclarationInfoForFecav(Request request);

	/**
	 * 取消和出口报关单的对应
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            外汇核销单信息
	 * @return 取消和出口报关单对应后的报关单信息
	 */
	List undoParallelFecavBill(Request request, List list);

	/**
	 * 核销单使用
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单
	 * @return 使用状态的核销单
	 */
	List useFecavBill(Request request, List list);

	/**
	 * 取消核销单使用
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单
	 * @return 取消使用的外部核销单 改状态为内部领用
	 */
	List undoUseFecavBill(Request request, List list);

	/**
	 * 核销单退税联签收
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单
	 * @param man
	 *            退税联签收人
	 * @param date
	 *            退税联签收日期
	 * @return 核销单退税联签收内容
	 */
	List debentureSignInFecavBill(Request request, List list, String man,
			Date date);

	/**
	 * 取消核销单退税联签收
	 * 
	 * @param list
	 *            核销单
	 * @return 取消后的核销单退税联签收单据 该状态为使用
	 */
	List undoDebentureSignInFecavBill(Request request, List list);

	/**
	 * 核销单交单明细
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            外汇核销单
	 * @param man
	 *            核销单交单人
	 * @param date
	 *            核销单交单日期
	 * @return 核销单交单明细
	 */
	List handBillFecavBill(Request request, List list, String man, Date date);

	/**
	 * 取消核销单交单明细
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            外汇核销单
	 * @return 取消的核销单交单 改其状态为退税签收
	 */
	List undoHandBillFecavBill(Request request, List list);

	// /**
	// * 核销单核销联管制
	// *
	// * @param list
	// * @return
	// */
	// List cavSignInFecavBill(Request request, List list);
	//
	// /**
	// * 取消核销单核销联管制
	// *
	// * @param list
	// * @return
	// */
	// List undoCavSignInfecavBill(Request request, List list);
	/**
	 * 核销单核销
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @param signInMan
	 *            核销人
	 * @param signInDate
	 *            核销日期
	 * @return 核销后的核销单
	 */
	List cancelAfterVerificationFecavBill(Request request, List list,
			String signInMan, Date signInDate);

	/**
	 * 取消核销单核销
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @return 取消核销单核销 改状态为冲销
	 */
	List undoCancelAfterVerificationFecavBill(Request request, List list);

	/**
	 * 核销单财务签收
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @param signInMan
	 *            财务签收者
	 * @param signInDate
	 *            财务签收日期
	 * @return 财务签收的核销单
	 */
	List financeSignInFecavBill(Request request, List list, String signInMan,
			Date signInDate);

	/**
	 * 取消核销单财务签收
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @return 取消要签收的核销单 改状态为核销
	 */
	List undoFinanceSignInFecavBill(Request request, List list);

	/**
	 * 核销单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @param strikeMan
	 *            冲销者
	 * @param strikeDate
	 *            冲销期
	 * @return 核销单的冲销单据
	 */
	List strikeBalanceFecavBill(Request request, List list, String strikeMan,
			Date strikeDate);

	/**
	 * 取消核销单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @return 取消核销单冲销 改状态为管制
	 */
	List undoStrikeBalanceFecavBill(Request request, List list);

	/**
	 * 核销单关帐
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            外汇核销单冲销
	 * @param closeAccountMan
	 *            关帐人
	 * @param closeAccountDate
	 *            关帐日期
	 * @return 核销单关帐的单据
	 */
	List closeAccountFecavBill(Request request, List list,
			String closeAccountMan, Date closeAccountDate);

	/**
	 * 取消核销单关帐
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @return 取消要关帐的核销单 改状态为签收
	 */
	List undoCloseAccountFecavBill(Request request, List list);

	/**
	 * 查询银行汇票
	 * 
	 * @param request
	 *            发送请求
	 * @return 银行汇票
	 */
	List findBillOfExchange(Request request);

	/**
	 * 根据编号查询银行汇票数目
	 * 
	 * @param request
	 *            发送请求
	 * @param code
	 *            汇票号码
	 * @return 与指定汇票号码匹配的银行汇票
	 */
	int findBillOfExchangeCountByCode(Request request, String code);

	/**
	 * 保存汇票
	 * 
	 * @param request
	 *            发送请求
	 * @param bill
	 *            银行汇票
	 * @return 银行汇票
	 */
	BillOfExchange saveBillOfExchange(Request request, BillOfExchange bill);

	/**
	 * 删除汇票
	 * 
	 * @param request
	 *            发送请求
	 * @param bill
	 *            银行汇票
	 */
	void deleteBillOfExchange(Request request, BillOfExchange bill);

	/**
	 * 删除实体对象 防止传递过来的对象和缓存中的对象的版本不一致
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            Collection类型
	 */
	void deleteBillOfExchange(Request request, List list);

	/**
	 * 查询已经作废的外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 已经作废的外汇核销单
	 */
	List findIsBlankOutFecavBill(Request request, String condition,
			List<Object> lsParam);

	/**
	 * 查询没有作废的外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有作废的外汇核销单
	 */
	List findNotBlankOutFecavBill(Request request);

	/**
	 * 核销单作废
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            外汇核销单
	 * @param reason
	 *            作废原因
	 * @param date
	 *            作废日期
	 * @return 作废后的外汇核销单
	 */
	List blankOutFecavBill(Request request, List list, String reason, Date date);

	/**
	 * 取消核销单作废
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单
	 * @return 取消作废的外汇核销单
	 */
	List undoBlankOutFecavBill(Request request, List list);

	/**
	 * 根据外汇核销单抓取已冲销的进口报关单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 与指定的外汇核销单冲销id匹配的进出口报关单
	 */
	List findBrikeImpCustomsDeclaration(Request request,
			FecavBillStrike fecavBillStrike);

	/**
	 * 判断白单号是否重复
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            进口报关单冲销
	 * @param whiteBillNo
	 *            白单号
	 * @return false 白单号不重复
	 */
	boolean checkImpWhiteBillNoIsDuple(Request request,
			ImpCustomsDeclaration imp, String whiteBillNo);

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            进口报关单冲销
	 * @return 进口报关单冲销
	 */
	StrikeImpCustomsDeclaration saveBrikeImpCustomsDeclaration(Request request,
			StrikeImpCustomsDeclaration imp);

	/**
	 * 删除进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param lsImp
	 *            进口报关单冲销
	 */
	void deleteBrikeImpCustomsDeclaration(Request request, List lsImp);

	/**
	 * 根据外汇核销单抓取已冲销的汇票
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单
	 * @return 已冲销的汇票
	 */
	List findBrikeBillOfExchange(Request request,
			FecavBillStrike fecavBillStrike);

	/**
	 * 保存汇票冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param exchange
	 *            汇票冲销
	 * @return 汇票冲销
	 */
	StrikeBillOfExchange saveBrikeBillOfExchange(Request request,
			StrikeBillOfExchange exchange);

	/**
	 * 删除汇票冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param lsExchange
	 *            汇票冲销
	 */
	void deleteBrikeBillOfExchange(Request request, List lsExchange);

	/**
	 * 查询可以冲销的进口报关单
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有冲销的进口报关单
	 */
	List findNotUseImpCustomsDeclaration(Request request);

	/**
	 * 查询可以冲销的白单
	 * 
	 * @return 没有冲销的进口报关单
	 */
	List findNotStrikeImpCustomsDeclaration(Request request,
			FecavBillStrike fecavBillStrike);

	/**
	 * 查询可以冲销的汇票
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有冲销的汇票
	 */
	List findNotBrikeBillOfExchange(Request request,
			FecavBillStrike fecavBillStrike);

	// /**
	// * 新增进口报关单冲销
	// *
	// * @param request
	// * 发送请求
	// * @param fecavBillStrike
	// * 外汇核销单冲销
	// * @param list
	// * 临时的外汇核销单的报关单信息
	// * @return 进口报关单冲销
	// */
	// List addBrikeImpCustomsDeclaration(Request request,
	// FecavBillStrike fecavBillStrike, List list);

	/**
	 * 新增汇票冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @param list
	 *            临时汇票单据
	 * @return 汇票冲销
	 */
	List addBrikeBillOfExchange(Request request,
			FecavBillStrike fecavBillStrike, List list);

	/**
	 * 取得汇率
	 * 
	 * @param sourCurr
	 *            本地汇率
	 * @param destCurr
	 *            外地汇率
	 * @param createDate
	 *            创建日期
	 * @return 汇率
	 */
	double findExchangeRate(Request request, Curr sourCurr, Curr destCurr,
			Date createDate);

	/**
	 * 取得外汇核销单已经冲销的进口报关单金额
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 已冲销的进口报关单金额
	 */
	double findFecavStrikeImpMoney(Request request,
			FecavBillStrike fecavBillStrike);

	/**
	 * 取得外汇核销单已经冲销的汇票金额
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 已冲销的汇票金额
	 */
	double findFecavStrikeExchangeMoney(Request request,
			FecavBillStrike fecavBillStrike);

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	List findFecavBillStrikeByState(Request request, int fecavState);

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	List findFecavBillStrikeByCancel(Request request);

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	List findFecavBillStrikeByStateSignIn(Request request);

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	List findFecavBillStrikeControl(Request request);

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	List findFecavBillStrikeCancel(Request request);

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	public List findFecavBillStrikeSignIn(Request request);

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	List findFecavBillStrikeBlance(Request request);

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	List findFecavBillStrikeByBlance(Request request);

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	List findFecavBillStrikeByState(Request request, int fecavState,
			String condition, List<Object> lsParam);

	/**
	 * 根据ID查询外汇核销单领用单头
	 * 
	 * @param request
	 *            发送请求
	 * @param id
	 *            外汇核销单id
	 * @return 外汇核销单单头
	 */
	FecavBillStrike findFecavBillStrikeById(Request request, String id);

	/**
	 * 根据状态查询外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 外汇核销单
	 */
	List findFecavBillByStrike(Request request, FecavBillStrike fecavBillStrike);

	/**
	 * 保存外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单
	 * @return 外汇核销单
	 */
	FecavBillStrike saveFecavBillStrike(Request request,
			FecavBillStrike fecavBillStrike);

	/**
	 * 删除签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 */
	void deleteFecavBillStrike(Request request, FecavBillStrike fecavBillStrike);

	/**
	 * 根据状态查询外汇核销单没有被核销的
	 * 
	 * @param request
	 *            发送请求
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 没有被核销的外汇核销单
	 */
	List findFecavBillNotStrike(Request request, String condition,
			List<Object> lsParam);

	/**
	 * 根据状态查询外汇核销单没有被核销的
	 * 
	 * @param request
	 *            发送请求
	 * @param trade
	 *            贸易方式
	 * @param itemCount
	 *            没有被核销和项数
	 * @return 没有被核销的外汇核销单
	 */
	List findFecavBillNotStrike(Request request,
			FecavBillStrike fecavBillStrike, Integer itemCount);

	/**
	 * 新增外汇核销单（核销签收）
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @param list
	 *            外汇核销单
	 * @return 增加后的外汇核销单内容
	 */
	List addFecavBillForBrike(Request request, FecavBillStrike fecavBillStrike,
			List list);

	/**
	 * 新增外汇核销单（核销签收）
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @param num
	 *            外汇核销单份数
	 * @return 外汇核销单
	 */
	List addFecavBillForBrike(Request request, FecavBillStrike fecavBillStrike,
			Integer num);

	/**
	 * 移除外汇核销单（核销签收）
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @param list
	 *            外汇核销单
	 */
	void removeFecavBillForBrike(Request request,
			FecavBillStrike fecavBillStrike, List list);

	/**
	 * 查找外汇核消单来自核销单号 内部领单
	 * 
	 * @param request
	 *            发送请求
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 内部领单没有遗失作废的核销单号
	 */
	List findFecavBillNotCustomsDeclaration(Request request, int index,
			int length, String property, Object value, Boolean isLike);

	// /**
	// * 保存外汇核消单
	// *
	// * @param projectType
	// * 工程类型
	// * @param baseCustomsDeclaration
	// * 修改过的报关单
	// * @param oldAuthorizeFile
	// * 修改前的核消单号 null
	// */
	// void saveFacavBill(Request request, int projectType, int actionState,
	// BaseCustomsDeclaration baseCustomsDeclaration,
	// String oldAuthorizeFile);
	/**
	 * 查找出口日期为空的使用单据
	 * 
	 * @param request
	 *            发送请求
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 正在使用的出口日期为空没有作废的外汇核销单
	 */
	List findFecavBillNotUsed(Request request, String condition,
			List<Object> lsParam);

	/**
	 * 查找已使用未输入出口日期的核销单
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 正在使用的出口日期不为空的没有遗失作废的按外汇核销单号升序排列的外汇核销单
	 */
	List findFecavBillByUsedBefore(Request request, String condition,
			List<Object> lsParam);

	/**
	 * 查找出口日期不为空的使用单据
	 * 
	 * @param request
	 *            发送请求
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 正在使用的出口日期不为空的没有遗失作废的按外汇核销单号升序排列的外汇核销单
	 */
	List findFecavBillByUsedAfter(Request request, String condition,
			List<Object> lsParam);

	/**
	 * 获得最大的核销单编号
	 * 
	 * @param request
	 *            发送请求
	 * @return 不为空则为最大的核销单编号 为空返回0001
	 */
	String getMaxSignInNo(Request request);

	/**
	 * 批量保存外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单
	 * @return 外汇核销单
	 */
	List saveFecavBillStrike(Request request, List fecavBillStrike);

	/**
	 * 查询外汇核销单的份数
	 * 
	 * @param request
	 *            发送请求
	 * @param code
	 *            外汇核销单号
	 * @return 出口日期不为空的外汇核销单的份数
	 */
	int findFecavBillByCodeCount(Request request, String code);

	/**
	 * 保存外汇核销单并且反写报关单的出口日期
	 * 
	 * @param request
	 *            发送请求
	 * @param b
	 *            外汇核销单
	 */
	void saveFecavBillAndUpdateCustomsDeclaration(Request request, FecavBill b);

	/**
	 * 更新外汇核销单进口报关单号
	 * 
	 * @param b
	 *            外汇核销单
	 */
	void updateFecavBillCustomsDeclarationCode(Request request);

	/**
	 * 查询外汇核销单的进口报关单冲销信息
	 * 
	 * @param request
	 *            发送请求
	 * @return 外汇核销单为空白单号不为空的进口报关单冲销信息
	 */
	List<StrikeImpCustomsDeclaration> findBrikeImpCustomsDeclarationToStrike(
			Request request);

	/**
	 * 根据条件查询外汇核销单的进口报关单冲销信息
	 * 
	 * @param request
	 *            发送请求
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 外汇核销单为空的进口报关单冲销信息
	 */
	List<ImpCustomsDeclaration> findImpCustomsDeclarationNotCancel(
			Request request, String condition, List<Object> lsParam);

	/**
	 * 查询进口报关单的冲销信息
	 * 
	 * @param request
	 *            发送请求
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 外汇核销单不为空的进口报关单冲销信息
	 */
	List<StrikeImpCustomsDeclaration> findImpCustomsDeclarationIsCancel(
			Request request, String condition, List<Object> lsParam);

	/**
	 * 保存进口白单
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            进口报关单冲销
	 * @return 进口报关单冲销
	 */
	ImpCustomsDeclaration saveImpCustomsDeclaration(Request request,
			ImpCustomsDeclaration imp);

	/**
	 * 更新旧数据
	 * 
	 */
	void deleteOldImpCustomsDeclaration(Request request);

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            外汇核销单冲销
	 * @return 外汇核销单冲销
	 */
	List<StrikeImpCustomsDeclaration> addStrikeImpCustomsDeclaration(
			Request request, FecavBillStrike fecavBillStrike,
			List<ImpCustomsDeclaration> imp);

	/**
	 * 新增进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            进口报关单冲销
	 * @return 进口报关单冲销信息
	 */
	List<ImpCustomsDeclaration> addImpCustomsDeclaration(Request request,
			List<TempCustomsDeclarationInfoForFecav> list);

	/**
	 * 查询已冲销的进口报关单的数量
	 * 
	 * @param request
	 *            发送请求
	 * @param customsDeclarationId
	 *            报关单id
	 * @return 白单号不为空的冲销的进出口报关单的数量
	 */
	int findStrikeImpCustomsDeclarationCount(Request request,
			String customsDeclarationId);

	/**
	 * 删除进口报关单冲销信息
	 * 
	 * @param request
	 *            发送请求
	 * @param customsDeclarationId
	 *            报关单id
	 */
	void deleteImpCustomsDeclaration(Request request,
			String customsDeclarationId);

	/**
	 * 外汇核销单映射内容
	 * 
	 * @param request
	 *            发送请求
	 */
	Map<String, List<FecavBill>> getFecavBillMap(Request request);

	/**
	 * 保存外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillList
	 *            批量的外汇核销单
	 * @return 批量外汇核销单
	 */
	List<FecavBill> saveFecavBill(Request request, List<FecavBill> fecavBillList);

	/**
	 * 分页查询核销单明细
	 * 
	 * @param request
	 *            发送请求
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 没有遗失作废的单据明细
	 */
	public List findFecavBillDetail(Request request, int index, int length,
			String property, Object value, Boolean isLike);

	/**
	 * 根据外汇核销单抓取已冲销的汇票(所有)
	 * 
	 * @param request
	 *            发送请求
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 所有已冲销的汇票
	 */
	List<TempBillOfExchangeForFecav> findBrikeBillOfExchange(Request request,
			Date beginDate, Date endDate);

	/**
	 * 查询银行汇票没有被使用的记录(所有)
	 * 
	 * @param request
	 *            发送请求
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内结汇金额 冲销金额 剩余金额的统计情况
	 */
	List<TempBillOfExchangeForFecav> findBillOfExchangeNotUse(Request request,
			Date beginDate, Date endDate);

	/**
	 * 根据外汇核销单来源与已核销签收(核销,财务签收,关帐)统计表
	 * 
	 * @param request
	 *            发送请求
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 已核销签收(核销,财务签收,关帐)统计表
	 */
	List<TempCancelSignInStat> findTempCancelSignInStat(Request request,
			Date beginDate, Date endDate);

	/**
	 * 根据外汇核销单来自源于[交单末核销]的单据(合同汇总表)
	 * 
	 * @param request
	 *            发送请求
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内[交单末核销]的单据(合同汇总表)
	 */
	List<TempCancelContractStat> findTempCancelContractStat(Request request,
			Date beginDate, Date endDate);

	/**
	 * 出口核销单使用状况表
	 * 
	 * @param request
	 *            发送请求
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内出口核销单使用情况
	 */
	List findFecavBillForUsedStat(Request request, Date beginDate, Date endDate);

	/**
	 * 查询外汇核销单(所有)
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有遗失作废的所有外汇核销单
	 */
	List findFecavBill(Request request);

	/**
	 * 查询外汇核销单(所有)
	 * 
	 * @param request
	 *            发送请求
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 与查询条件匹配按核销单号升序排列的没有遗失作废的外汇核销单
	 */
	List findFecavBill(Request request, String condition, List<Object> lsParam);

	/**
	 * 查询外汇核销单不包括外部领用
	 * 
	 * @param request
	 *            发送请求
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 与条件匹配不包括外部领用的没有遗失作废的外汇核销单
	 */
	List findFecavBillNotOuterObtain(Request request, String condition,
			List<Object> lsParam);

	/**
	 * 根据编号,操作员,汇日期查询银行汇票
	 * 
	 * @param request
	 *            发送请求
	 * @param code
	 *            汇票号码
	 * @param operator
	 *            操作员
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 与指定条件匹配的银行汇票
	 */
	List findBillOfExchange(Request request, String code, String operator,
			Date beginDate, Date endDate);

	/**
	 * 根据系统类型抓取正在执行的账册号或手册号
	 */
	List findEmsHeadByProjectType(Request request, Integer projectType);

	/**
	 * 根据系统类型抓取正在执行的报关单
	 */
	List findCustomsDeclarationByProjectType(Request request, List contracts,
			Date beginDate, Date endDate, Integer projectType);
	
	boolean findCustomsInofFecavBill(Request request,Customs customs);

	/**
	 * 删除进口白单，如果白单的进口报关单号在外汇核销单报关单号中存在，则不删除。
	 * 
	 * @param icd
	 *            ImpCustomsDeclaration 进口白单
	 * 
	 * @return 如果删除则为true 反之为false
	 */
	boolean deleteImpCustomsDeclaration(Request request,
			ImpCustomsDeclaration icd);

	/**
	 * 查找正在执行的帐册
	 */
	public List findEmsHeadH2kByProcessExe(Request request);

	/**
	 * 只作权限用
	 */
	public void controlRepotByCancel(Request request);

	/**
	 * 更新进口白单的余额
	 * 
	 * @param imp
	 */
	ImpCustomsDeclaration updateRemainImpCustomsDecMoney(Request request,
			ImpCustomsDeclaration imp);
	
	/**
	 * 查找是否有汇票已被冲消
	 * 
	 * @param List
	 *            已冲销的汇票List
	 * @return Boolean
	 */
	public Boolean isBillOfExchangeIsUer(List list ) ;
	/**
	 * 根据外汇核销单的领用类型和是否作废状态获取核销单号最小的外汇核销单
	 * @param request
	 * @param obtain
	 * @param billState
	 * @return
	 */
	public FecavBill  getMinFecavBill(Request request,Integer billState,Boolean isBlankOut);
	/**
	 * 从内部领单中新增核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单
	 * @return 新增核销单信息
	 */
	List<FecavBill> addFecavBill(Request request,
			List<FecavBill> list);

}
