package com.bestway.fecav.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.FecavState;
import com.bestway.common.fpt.logic.FptManageLogic;
import com.bestway.fecav.dao.FecavDao;
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
import com.bestway.fecav.logic.FecavLogic;

@AuthorityClassAnnotation(caption = "外汇核销管理", index = 9)
public class FecavActionImpl extends BaseActionImpl implements FecavAction {
	/**
	 * 外汇核销管理Dao
	 */
	private FecavDao fecavDao = null;

	/**
	 * 外汇核销管理Logic
	 */
	private FecavLogic fecavLogic = null;

	private FptManageLogic fptManageLogic = null;

	/**
	 * 取得外汇核销管理Dao
	 * 
	 * @return 外汇核销管理Dao
	 */
	public FecavDao getFecavDao() {
		return fecavDao;
	}

	/**
	 * 设计外汇核销管理Dao
	 * 
	 * @param fecavDao
	 *            外汇核销管理Dao
	 */
	public void setFecavDao(FecavDao fecavDao) {
		this.fecavDao = fecavDao;
	}

	/**
	 * 取得外汇核销管理Logic
	 * 
	 * @return 外汇核销管理Logic
	 */
	public FecavLogic getFecavLogic() {
		return fecavLogic;
	}

	/**
	 * 设计外汇核销管理Logic
	 * 
	 * @param fecavLogic
	 *            外汇核销管理Logic
	 */
	public void setFecavLogic(FecavLogic fecavLogic) {
		this.fecavLogic = fecavLogic;
	}

	public FptManageLogic getFptManageLogic() {
		return fptManageLogic;
	}

	public void setFptManageLogic(
			FptManageLogic transferFactoryManageLogic) {
		this.fptManageLogic = transferFactoryManageLogic;
	}

	/**
	 * 查询外汇核销单参数
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "参数设置", index = 0.01)
	public FecavParameters findFecavParameters(Request request) {
		return this.fecavDao.findFecavParameters();
	}

	/**
	 * 保存外汇核销单参数
	 * 
	 * @param fecavParameters
	 */
	public FecavParameters saveFecavParameters(Request request,
			FecavParameters fecavParameters) {
		this.fecavDao.saveFecavParameters(fecavParameters);
		return fecavParameters;
	}

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
	public String getEndFecavBillNo(Request request, String beginNo, int num) {
		return this.fecavLogic.getEndFecavBillNo(beginNo, num);
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览核销单使用", index = 3)
	public List findFecavBillByState(Request request, int fecavState,
			String condition, List<Object> lsParam) {
		return this.fecavDao.findFecavBillByState(fecavState, condition,
				lsParam);
	}

	/**
	 * 根据状态查询外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 与指定的状态匹配没有遗失作废的外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "浏览核销单使用", index = 3)
	public List findFecavBillByState(Request request, int fecavState) {
		return this.fecavDao.findFecavBillByState(fecavState);
	}

	/**
	 * 根据ID查询外汇核销单
	 * 
	 * @param id
	 *            外汇核销单id
	 * @return 与指定id匹配的外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "浏览外部领单", index = 1)
	public FecavBill findFecavBillById(String id) {
		return this.fecavDao.findFecavBillById(id);
	}

	/**
	 * 保存外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBill
	 *            外汇核销单
	 * @return 外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "核销单使用-修改", index = 3.2)
	public FecavBill saveFecavBill(Request request, FecavBill fecavBill) {
		this.fecavDao.saveFecavBill(fecavBill);
		return fecavBill;
	}

	/**
	 * 删除外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBill
	 *            外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "外部领单-删除", index = 1.201)
	public void deleteFecavBill(Request request, FecavBill fecavBill) {
		this.fecavDao.deleteFecavBill(fecavBill);
	}

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
	@AuthorityFunctionAnnotation(caption = "外部领单-新建", index = 1.2)
	public List batchOuterObtainFecavBill(Request request, String beginNo,
			int num, String outerObtain, Date outerObtainDate) {
		return this.fecavLogic.batchOuterObtainFecavBill(beginNo, num,
				outerObtain, outerObtainDate);
	}

	/**
	 * 删除外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            要批量删除的外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "外部领单-删除", index = 1.3)
	public void deleteFecavBill(Request request, List list) {
		this.fecavLogic.deleteFecavBill(list);
	}

	/**
	 * 取消和出口报关单的对应
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            外汇核销单信息
	 * @return 取消和出口报关单对应后的报关单信息
	 */
	public List undoParallelFecavBill(Request request, List list) {
		return this.fecavLogic.undoParallelFecavBill(list);
	}

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
	@AuthorityFunctionAnnotation(caption = "内部领单-内部领用", index = 2.2)
	public List batchInnerObtainFecavBill(Request request, String beginNo,
			int num, String innerObtain, Date innerObtainDate) {
		return this.fecavLogic.batchInnerObtainFecavBill(beginNo, num,
				innerObtain, innerObtainDate);
	}

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
	@AuthorityFunctionAnnotation(caption = "内部领单-内部领用", index = 2.2)
	public List batchInnerObtainFecavBill(Request request, List list,
			String innerObtain, Date innerObtainDate) {
		return this.fecavLogic.batchInnerObtainFecavBill(list, innerObtain,
				innerObtainDate);
	}

	/**
	 * 批量取消内部领用
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            要取消的内部领用
	 * @return 取消后的内部领用 状态改为外部领用
	 */
	@AuthorityFunctionAnnotation(caption = "内部领单-取消内部领用", index = 2.3)
	public List batchUndoInnerObtainFecavBill(Request request, List list) {
		return this.fecavLogic.batchUndoInnerObtainFecavBill(list);
	}

	/**
	 * 取得未与外汇核销单建立联系的报关单信息
	 * 
	 * @param request
	 *            发送请求
	 * @return 未与外汇核销单建立联系的报关单(联网监管 电子手册 纸质手册)信息
	 */
	public List findCustomsDeclarationInfoForFecav(Request request) {
		return this.fecavLogic.findCustomsDeclarationInfoForFecav();
	}

	/**
	 * 核销单使用
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单
	 * @return 使用状态的核销单
	 */
	public List useFecavBill(Request request, List list) {
		return this.fecavLogic.useFecavBill(list);
	}

	/**
	 * 取消核销单使用
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单
	 * @return 取消使用的外部核销单 改状态为内部领用
	 */
	public List undoUseFecavBill(Request request, List list) {
		return this.fecavLogic.undoUseFecavBill(list);
	}

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
	@AuthorityFunctionAnnotation(caption = "核销单退税连签收-退税签收", index = 5.2)
	public List debentureSignInFecavBill(Request request, List list,
			String man, Date date) {
		return this.fecavLogic.debentureSignInFecavBill(list, man, date);
	}

	/**
	 * 取消核销单退税联签收
	 * 
	 * @param list
	 *            核销单
	 * @return 取消后的核销单退税联签收单据 该状态为使用
	 */
	@AuthorityFunctionAnnotation(caption = "核销单退税连签收-取消退税签收", index = 5.3)
	public List undoDebentureSignInFecavBill(Request request, List list) {
		return this.fecavLogic.undoDebentureSignInFecavBill(list);
	}

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
	@AuthorityFunctionAnnotation(caption = "核销单交单明细-交单", index = 6.1)
	public List handBillFecavBill(Request request, List list, String man,
			Date date) {
		return this.fecavLogic.handBillFecavBill(list, man, date);
	}

	/**
	 * 取消核销单交单明细
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            外汇核销单
	 * @return 取消的核销单交单 改其状态为退税签收
	 */
	@AuthorityFunctionAnnotation(caption = "核销单交单明细-取消交单", index = 6.2)
	public List undoHandBillFecavBill(Request request, List list) {
		return this.fecavLogic.undoHandBillFecavBill(list);
	}

	// /**
	// * 核销单核销管制
	// *
	// * @param list
	// * @return
	// */
	// public List cavSignInFecavBill(Request request, List list) {
	// return this.fecavLogic.cavSignInFecavBill(list);
	// }
	//
	// /**
	// * 取消核销单核销管制
	// *
	// * @param list
	// * @return
	// */
	// public List undoCavSignInFecavBill(Request request, List list) {
	// return this.fecavLogic.undoCavSignInFecavBill(list);
	// }
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
	@AuthorityFunctionAnnotation(caption = "核销单核销-核销", index = 10.1)
	public List cancelAfterVerificationFecavBill(Request request, List list,
			String signInMan, Date signInDate) {
		return this.fecavLogic.cancelAfterVerificationFecavBill(list,
				signInMan, signInDate);
	}

	/**
	 * 取消核销单核销
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @return 取消核销单核销 改状态为冲销
	 */
	@AuthorityFunctionAnnotation(caption = "核销单核销-取消核销", index = 10.2)
	public List undoCancelAfterVerificationFecavBill(Request request, List list) {
		return this.fecavLogic.undoCancelAfterVerificationFecavBill(list);
	}

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
	@AuthorityFunctionAnnotation(caption = "核销单财务签收-签收", index = 11.1)
	public List financeSignInFecavBill(Request request, List list,
			String signInMan, Date signInDate) {
		return this.fecavLogic.financeSignInFecavBill(list, signInMan,
				signInDate);
	}

	/**
	 * 取消核销单财务签收
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @return 取消要签收的核销单 改状态为核销
	 */
	@AuthorityFunctionAnnotation(caption = "核销单财务签收-取消签收", index = 11.2)
	public List undoFinanceSignInFecavBill(Request request, List list) {
		return this.fecavLogic.undoFinanceSignInFecavBill(list);
	}

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
	@AuthorityFunctionAnnotation(caption = "核销单冲消-冲消", index = 9.1)
	public List strikeBalanceFecavBill(Request request, List list,
			String strikeMan, Date strikeDate) {
		return this.fecavLogic.strikeBalanceFecavBill(list, strikeMan,
				strikeDate);
	}

	/**
	 * 取消核销单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @return 取消核销单冲销 改状态为管制
	 */
	@AuthorityFunctionAnnotation(caption = "核销单冲消-取消冲消", index = 9.2)
	public List undoStrikeBalanceFecavBill(Request request, List list) {
		return this.fecavLogic.undoStrikeBalanceFecavBill(list);
	}

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
	@AuthorityFunctionAnnotation(caption = "核销单关帐-关帐", index = 12.1)
	public List closeAccountFecavBill(Request request, List list,
			String closeAccountMan, Date closeAccountDate) {
		return this.fecavLogic.closeAccountFecavBill(list, closeAccountMan,
				closeAccountDate);
	}

	/**
	 * 取消核销单关帐
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单冲销
	 * @return 取消要关帐的核销单 改状态为签收
	 */
	@AuthorityFunctionAnnotation(caption = "核销单关帐-取消关帐", index = 12.2)
	public List undoCloseAccountFecavBill(Request request, List list) {
		return this.fecavLogic.undoCloseAccountFecavBill(list);
	}

	/**
	 * 查询银行汇票
	 * 
	 * @param request
	 *            发送请求
	 * @return 银行汇票
	 */
	@AuthorityFunctionAnnotation(caption = "浏览银行汇票录入", index = 8)
	public List findBillOfExchange(Request request) {
		return this.fecavDao.findBillOfExchange();
	}

	/**
	 * 根据编号查询银行汇票数目
	 * 
	 * @param request
	 *            发送请求
	 * @param code
	 *            汇票号码
	 * @return 与指定汇票号码匹配的银行汇票
	 */
	public int findBillOfExchangeCountByCode(Request request, String code) {
		return this.fecavDao.findBillOfExchangeCountByCode(code);
	}

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
	public List findBillOfExchange(Request request, String code,
			String operator, Date beginDate, Date endDate) {
		return this.fecavDao.findBillOfExchange(code, operator, beginDate,
				endDate);
	}

	/**
	 * 保存汇票
	 * 
	 * @param request
	 *            发送请求
	 * @param bill
	 *            银行汇票
	 * @return 银行汇票
	 */
	@AuthorityFunctionAnnotation(caption = "银行汇票录入-编辑", index = 8.2)
	public BillOfExchange saveBillOfExchange(Request request,
			BillOfExchange bill) {
		this.fecavDao.saveBillOfExchange(bill);
		return bill;
	}

	/**
	 * 删除汇票
	 * 
	 * @param request
	 *            发送请求
	 * @param bill
	 *            银行汇票
	 */
	@AuthorityFunctionAnnotation(caption = "银行汇票录入-删除", index = 8.3)
	public void deleteBillOfExchange(Request request, BillOfExchange bill) {
		this.fecavDao.deleteBillOfExchange(bill);
	}

	/**
	 * 删除实体对象 防止传递过来的对象和缓存中的对象的版本不一致
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            Collection类型
	 */
	@AuthorityFunctionAnnotation(caption = "银行汇票录入-删除", index = 8.3)
	public void deleteBillOfExchange(Request request, List list) {
		this.fecavDao.deleteAll(list);
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览核销单遗失作废管理", index = 13)
	public List findIsBlankOutFecavBill(Request request, String condition,
			List<Object> lsParam) {
		return this.fecavDao.findIsBlankOutFecavBill(condition, lsParam);
	}

	/**
	 * 查询没有作废的外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有作废的外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "浏览核销单遗失作废管理", index = 13)
	public List findNotBlankOutFecavBill(Request request) {
		return this.fecavDao.findNotBlankOutFecavBill();
	}

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
	@AuthorityFunctionAnnotation(caption = "核销单遗失作废管理-作废", index = 13.1)
	public List blankOutFecavBill(Request request, List list, String reason,
			Date date) {
		return this.fecavLogic.blankOutFecavBill(list, reason, date);
	}

	/**
	 * 取消核销单作废
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单
	 * @return 取消作废的外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "核销单遗失作废管理-取消作废", index = 13.2)
	public List undoBlankOutFecavBill(Request request, List list) {
		return this.fecavLogic.undoBlankOutFecavBill(list);
	}

	/**
	 * 根据外汇核销单抓取已冲销的进口报关单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 与指定的外汇核销单冲销id匹配的进出口报关单
	 */
	public List findBrikeImpCustomsDeclaration(Request request,
			FecavBillStrike fecavBillStrike) {
		return this.fecavDao.findBrikeImpCustomsDeclaration(fecavBillStrike);
	}

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
	public boolean checkImpWhiteBillNoIsDuple(Request request,
			ImpCustomsDeclaration imp, String whiteBillNo) {
		return this.fecavLogic.checkImpWhiteBillNoIsDuple(imp, whiteBillNo);
	}

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            进口报关单冲销
	 * @return 进口报关单冲销
	 */
	@AuthorityFunctionAnnotation(caption = "进口白单管制-编辑", index = 4.2)
	public StrikeImpCustomsDeclaration saveBrikeImpCustomsDeclaration(
			Request request, StrikeImpCustomsDeclaration imp) {
		this.fecavLogic.saveBrikeImpCustomsDeclaration(imp);
		return imp;
	}

	/**
	 * 保存进口白单
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            进口报关单冲销
	 * @return 进口报关单冲销
	 */
	public ImpCustomsDeclaration saveImpCustomsDeclaration(Request request,
			ImpCustomsDeclaration imp) {
		this.fecavDao.saveImpCustomsDeclaration(imp);
		return imp;
	}

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            进口报关单冲销
	 * @return 进口报关单冲销
	 */
	public StrikeImpCustomsDeclaration saveStrikeImpCustomsDeclaration(
			Request request, StrikeImpCustomsDeclaration imp) {
		this.fecavDao.saveBrikeImpCustomsDeclaration(imp);
		return imp;
	}

	/**
	 * 删除进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            进口报关单冲销
	 */
	@AuthorityFunctionAnnotation(caption = "核销单冲消-进口报关单冲消删除", index = 9.4)
	public void deleteBrikeImpCustomsDeclaration(Request request, List imp) {
		this.fecavLogic.deleteBrikeImpCustomsDeclaration(imp);
	}

	/**
	 * 根据外汇核销单抓取已冲销的汇票
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单
	 * @return 已冲销的汇票
	 */
	public List findBrikeBillOfExchange(Request request,
			FecavBillStrike fecavBillStrike) {
		return this.fecavDao.findBrikeBillOfExchange(fecavBillStrike);
	}

	/**
	 * 保存汇票冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param exchange
	 *            汇票冲销
	 * @return 汇票冲销
	 */
	@AuthorityFunctionAnnotation(caption = "核销单冲消-汇票冲消编辑", index = 9.3)
	public StrikeBillOfExchange saveBrikeBillOfExchange(Request request,
			StrikeBillOfExchange exchange) {
		this.fecavLogic.saveBrikeBillOfExchange(exchange);
		return exchange;
	}

	/**
	 * 删除汇票冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param exchange
	 *            汇票冲销
	 */
	@AuthorityFunctionAnnotation(caption = "核销单冲消-汇票冲消删除", index = 9.5)
	public void deleteBrikeBillOfExchange(Request request, List exchange) {
		this.fecavLogic.deleteBrikeBillOfExchange(exchange);
	}

	/**
	 * 查询可以冲销的进口报关单
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有冲销的进口报关单
	 */
	public List findNotUseImpCustomsDeclaration(Request request) {
		return this.fecavLogic.findNotUseImpCustomsDeclaration();
	}
	/**
	 * 查询可以冲销的白单
	 * 
	 * @return 没有冲销的进口报关单
	 */
	public List findNotStrikeImpCustomsDeclaration(Request request,
			FecavBillStrike fecavBillStrike) {
		return this.fecavLogic
				.findNotStrikeImpCustomsDeclaration(fecavBillStrike);
	}

	/**
	 * 查询可以冲销的汇票
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有冲销的汇票
	 */
	public List findNotBrikeBillOfExchange(Request request,
			FecavBillStrike fecavBillStrike) {
		return this.fecavLogic.findNotBrikeBillOfExchange(fecavBillStrike);
	}

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
	// @AuthorityFunctionAnnotation(caption = "进口白单管制-编辑", index = 4.2)
	// public List addBrikeImpCustomsDeclaration(Request request,
	// FecavBillStrike fecavBillStrike, List list) {
	// return this.fecavLogic.addBrikeImpCustomsDeclaration(fecavBillStrike,
	// list);
	// }

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
	@AuthorityFunctionAnnotation(caption = "核销单冲消-汇票冲消编辑", index = 9.3)
	public List addBrikeBillOfExchange(Request request,
			FecavBillStrike fecavBillStrike, List list) {
		return this.fecavLogic.addBrikeBillOfExchange(fecavBillStrike, list);
	}

	/**
	 * 取得外汇核销单已经冲销的进口报关单金额
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 已冲销的进口报关单金额
	 */
	public double findFecavStrikeImpMoney(Request request,
			FecavBillStrike fecavBillStrike) {
		return this.fecavLogic.findFecavStrikeImpMoney(fecavBillStrike);
	}

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
	public double findExchangeRate(Request request, Curr sourCurr,
			Curr destCurr, Date createDate) {
		return this.fecavLogic.findExchangeRate(sourCurr, destCurr, createDate);
	}

	/**
	 * 取得外汇核销单已经冲销的汇票金额
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 已冲销的汇票金额
	 */
	public double findFecavStrikeExchangeMoney(Request request,
			FecavBillStrike fecavBillStrike) {
		return this.fecavLogic.findFecavStrikeExchangeMoney(fecavBillStrike);
	}

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	@AuthorityFunctionAnnotation(caption = "浏览出口收汇管制", index = 7)
	public List findFecavBillStrikeByState(Request request, int fecavState) {
		return this.fecavDao.findFecavBillStrikeByState(fecavState);
	}

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	@AuthorityFunctionAnnotation(caption = "浏览核销单关帐", index = 11.2)
	public List findFecavBillStrikeByStateSignIn(Request request) {
		return this.fecavDao
				.findFecavBillStrikeByState(FecavState.FINANCE_SIGN_IN);
	}

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	@AuthorityFunctionAnnotation(caption = "浏览核销单交单明细", index = 13.2)
	public List findFecavBillStrikeSignIn(Request request) {
		return this.fecavDao
				.findFecavBillStrikeByState(FecavState.DEBENTURE_SIGN_IN);
	}

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	@AuthorityFunctionAnnotation(caption = "浏览核销单财务签收", index = 18.2)
	public List findFecavBillStrikeByCancel(Request request) {
		return this.fecavDao.findFecavBillStrikeByState(FecavState.CANCEL);
	}

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	@AuthorityFunctionAnnotation(caption = "浏览核销单冲销", index = 7)
	public List findFecavBillStrikeControl(Request request) {
		return this.fecavDao.findFecavBillStrikeByState(FecavState.CONTROL);
	}

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	@AuthorityFunctionAnnotation(caption = "浏览核销单核销", index = 10.01)
	public List findFecavBillStrikeCancel(Request request) {
		return this.fecavDao.findFecavBillStrikeByState(FecavState.CANCEL);
	}

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	@AuthorityFunctionAnnotation(caption = "浏览核销单核销", index = 10.01)
	public List findFecavBillStrikeBlance(Request request) {
		return this.fecavDao
				.findFecavBillStrikeByState(FecavState.STRIKE_BALANCE);
	}

	/**
	 * 根据状态查询外汇核销单签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavState
	 *            单据状态
	 * @return 符合指定状态的外汇核销单签收单头
	 */
	@AuthorityFunctionAnnotation(caption = "浏览核销单核销", index = 10.01)
	public List findFecavBillStrikeByBlance(Request request) {
		return this.fecavDao
				.findFecavBillStrikeByState(FecavState.STRIKE_BALANCE);
	}

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
	public List findFecavBillStrikeByState(Request request, int fecavState,
			String condition, List<Object> lsParam) {
		return this.fecavDao.findFecavBillStrikeByState(fecavState, condition,
				lsParam);
	}

	/**
	 * 根据状态查询外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 外汇核销单
	 */
	public List findFecavBillByStrike(Request request,
			FecavBillStrike fecavBillStrike) {
		return this.fecavDao.findFecavBillByStrike(fecavBillStrike);
	}

	/**
	 * 根据ID查询外汇核销单领用单头
	 * 
	 * @param request
	 *            发送请求
	 * @param id
	 *            外汇核销单id
	 * @return 外汇核销单单头
	 */
	public FecavBillStrike findFecavBillStrikeById(Request request, String id) {
		return this.fecavDao.findFecavBillStrikeById(id);
	}

	/**
	 * 保存外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单
	 * @return 外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "出口收汇管制-编辑", index = 7.2)
	public FecavBillStrike saveFecavBillStrike(Request request,
			FecavBillStrike fecavBillStrike) {
		this.fecavDao.saveFecavBillStrike(fecavBillStrike);
		return fecavBillStrike;
	}

	/**
	 * 删除签收单头
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 */
	@AuthorityFunctionAnnotation(caption = "出口收汇管制-删除", index = 7.3)
	public void deleteFecavBillStrike(Request request,
			FecavBillStrike fecavBillStrike) {
		this.fecavLogic.deleteFecavBillStrike(fecavBillStrike);
	}

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
	public List findFecavBillNotStrike(Request request, String condition,
			List<Object> lsParam) {
		return this.fecavDao.findFecavBillNotStrike(condition, lsParam);
	}

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
	public List findFecavBillNotStrike(Request request,
			FecavBillStrike fecavBillStrike, Integer itemCount) {
		return this.fecavLogic.findFecavBillNotStrike(fecavBillStrike,
				itemCount);
	}

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
	@AuthorityFunctionAnnotation(caption = "出口收汇管制-编辑", index = 7.2)
	public List addFecavBillForBrike(Request request,
			FecavBillStrike fecavBillStrike, List list) {
		return this.fecavLogic.addFecavBillForBrike(fecavBillStrike, list);
	}

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
	@AuthorityFunctionAnnotation(caption = "出口收汇管制-编辑", index = 7.2)
	public List addFecavBillForBrike(Request request,
			FecavBillStrike fecavBillStrike, Integer num) {
		return this.fecavLogic.addFecavBillForBrike(fecavBillStrike, num);
	}

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
	@AuthorityFunctionAnnotation(caption = "出口收汇管制-删除", index = 7.3)
	public void removeFecavBillForBrike(Request request,
			FecavBillStrike fecavBillStrike, List list) {
		this.fecavLogic.removeFecavBillForBrike(fecavBillStrike, list);
	}

	// public List undoCavSignInfecavBill(Request request, List list) {
	// return this.fecavLogic.undoCavSignInFecavBill(list);
	// }

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
	public List findFecavBillNotCustomsDeclaration(Request request, int index,
			int length, String property, Object value, Boolean isLike) {
		return this.fecavDao.findFecavBillNotCustomsDeclaration(index, length,
				property, value, isLike);
	}

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
	// public void saveFacavBill(Request request, int projectType,
	// int actionState, BaseCustomsDeclaration baseCustomsDeclaration,
	// String oldAuthorizeFile) {
	// this.fecavLogic.saveFacavBill(projectType, actionState,
	// baseCustomsDeclaration, oldAuthorizeFile);
	// }
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
	@AuthorityFunctionAnnotation(caption = "浏览核销单使用", index = 3)
	public List findFecavBillNotUsed(Request request, String condition,
			List<Object> lsParam) {
		return this.fecavDao.findFecavBillNotUsed(condition, lsParam);
	}

	/**
	 * 查找已使用未输入出口日期的核销单
	 * 
	 * @param condition
	 *            查询条件
	 * @param lsParam
	 *            是否参数
	 * @return 正在使用的出口日期不为空的没有遗失作废的按外汇核销单号升序排列的外汇核销单
	 */
	public List findFecavBillByUsedBefore(Request request, String condition,
			List<Object> lsParam) {
		return this.fecavDao.findFecavBillByUsedBefore(condition, lsParam);
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览核销单退税连签收", index = 5)
	public List findFecavBillByUsedAfter(Request request, String condition,
			List<Object> lsParam) {
		return this.fecavDao.findFecavBillByUsedAfter(condition, lsParam);
	}

	/**
	 * 获得最大的核销单编号
	 * 
	 * @param request
	 *            发送请求
	 * @return 不为空则为最大的核销单编号 为空返回0001
	 */
	public String getMaxSignInNo(Request request) {
		return this.fecavLogic.getMaxSignInNo();
	}

	/**
	 * 批量保存外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillStrike
	 *            外汇核销单
	 * @return 外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "出口收汇管制-编辑", index = 7.2)
	public List saveFecavBillStrike(Request request, List fecavBillStrike) {
		this.fecavDao.saveFecavBillStrike(fecavBillStrike);
		return fecavBillStrike;
	}

	/**
	 * 查询外汇核销单的份数
	 * 
	 * @param request
	 *            发送请求
	 * @param code
	 *            外汇核销单号
	 * @return 出口日期不为空的外汇核销单的份数
	 */
	public int findFecavBillByCodeCount(Request request, String code) {
		return this.fecavDao.findFecavBillByCodeCount(code);
	}

	/**
	 * 保存外汇核销单并且反写报关单的出口日期
	 * 
	 * @param request
	 *            发送请求
	 * @param b
	 *            外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "核销单使用-修改", index = 3.2)
	public void saveFecavBillAndUpdateCustomsDeclaration(Request request,
			FecavBill b) {
		this.fecavLogic.saveFecavBillAndUpdateCustomsDeclaration(b);
	}

	/**
	 * 更新外汇核销单进口报关单号
	 * 
	 * @param b
	 *            外汇核销单
	 */
	public void updateFecavBillCustomsDeclarationCode(Request request) {
		this.fecavLogic.updateFecavBillCustomsDeclarationCode();
	}

	/**
	 * 查询外汇核销单的进口报关单冲销信息
	 * 
	 * @param request
	 *            发送请求
	 * @return 外汇核销单为空白单号不为空的进口报关单冲销信息
	 */
	public List<StrikeImpCustomsDeclaration> findBrikeImpCustomsDeclarationToStrike(
			Request request) {
		return this.fecavDao.findBrikeImpCustomsDeclarationToStrike();
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览进口白单管制", index = 4)
	public List<ImpCustomsDeclaration> findImpCustomsDeclarationNotCancel(
			Request request, String condition, List<Object> lsParam) {
		return this.fecavDao.findImpCustomsDeclarationNotCancel(condition,
				lsParam);
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览进口白单管制", index = 4)
	public List<StrikeImpCustomsDeclaration> findImpCustomsDeclarationIsCancel(
			Request request, String condition, List<Object> lsParam) {
		return this.fecavDao.findImpCustomsDeclarationIsCancel(condition,
				lsParam);
	}

	/**
	 * 更新旧数据
	 * 
	 */
	public void deleteOldImpCustomsDeclaration(Request request) {
		this.fecavLogic.deleteOldImpCustomsDeclaration();
	}

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param imp
	 *            外汇核销单冲销
	 * @return 外汇核销单冲销
	 */
	@AuthorityFunctionAnnotation(caption = "进口白单管制-编辑", index = 4.2)
	public List<StrikeImpCustomsDeclaration> addStrikeImpCustomsDeclaration(
			Request request, FecavBillStrike fecavBillStrike,
			List<ImpCustomsDeclaration> imp) {
		List<StrikeImpCustomsDeclaration> lsResult = this.fecavLogic
				.addStrikeImpCustomsDeclaration(fecavBillStrike, imp);
		return lsResult;
	}

	/**
	 * 新增进口报关单冲销
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            进口报关单冲销
	 * @return 进口报关单冲销信息
	 */
	@AuthorityFunctionAnnotation(caption = "进口白单管制-编辑", index = 4.2)
	public List<ImpCustomsDeclaration> addImpCustomsDeclaration(
			Request request, List<TempCustomsDeclarationInfoForFecav> list) {
		return this.fecavLogic.addImpCustomsDeclaration(list);
	}

	/**
	 * 查询已冲销的进口报关单的数量
	 * 
	 * @param request
	 *            发送请求
	 * @param customsDeclarationId
	 *            报关单id
	 * @return 白单号不为空的冲销的进出口报关单的数量
	 */
	public int findStrikeImpCustomsDeclarationCount(Request request,
			String customsDeclarationId) {
		return this.fecavDao
				.findStrikeImpCustomsDeclarationCount(customsDeclarationId);
	}

	/**
	 * 删除进口报关单冲销信息
	 * 
	 * @param request
	 *            发送请求
	 * @param customsDeclarationId
	 *            报关单id
	 */
	public void deleteImpCustomsDeclaration(Request request,
			String customsDeclarationId) {
		this.fecavLogic.deleteImpCustomsDeclaration(customsDeclarationId);
	}

	/**
	 * 外汇核销单映射内容
	 * 
	 * @param request
	 *            发送请求
	 */
	public Map<String, List<FecavBill>> getFecavBillMap(Request request) {
		return this.fecavLogic.getFecavBillMap();
	}

	/**
	 * 保存外汇核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param fecavBillList
	 *            批量的外汇核销单
	 * @return 批量外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "核销单使用-修改", index = 3.2)
	public List<FecavBill> saveFecavBill(Request request,
			List<FecavBill> fecavBillList) {
		this.fecavDao.saveFecavBill(fecavBillList);
		return fecavBillList;
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览统计报表", index = 15)
	public List findFecavBillDetail(Request request, int index, int length,
			String property, Object value, Boolean isLike) {
		return this.fecavDao.findFecavBillDetail(index, length, property,
				value, isLike);

	}

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
	@AuthorityFunctionAnnotation(caption = "浏览统计报表", index = 15)
	public List<TempBillOfExchangeForFecav> findBrikeBillOfExchange(
			Request request, Date beginDate, Date endDate) {
		return this.fecavLogic.findBrikeBillOfExchange(beginDate, endDate);
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览统计报表", index = 15)
	public List<TempBillOfExchangeForFecav> findBillOfExchangeNotUse(
			Request request, Date beginDate, Date endDate) {
		return this.fecavLogic.findBillOfExchangeNotUse(beginDate, endDate);
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览统计报表", index = 15)
	public List<TempCancelSignInStat> findTempCancelSignInStat(Request request,
			Date beginDate, Date endDate) {
		return this.fecavLogic.findTempCancelSignInStat(beginDate, endDate);

	}

	/**
	 * 只作权限用
	 */
	@AuthorityFunctionAnnotation(caption = "浏览统计报表", index = 15)
	public void controlRepotByCancel(Request request) {

	}

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
	@AuthorityFunctionAnnotation(caption = "浏览统计报表", index = 15)
	public List<TempCancelContractStat> findTempCancelContractStat(
			Request request, Date beginDate, Date endDate) {
		return this.fecavLogic.findTempCancelContractStat(beginDate, endDate);
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览统计报表", index = 15)
	public List findFecavBillForUsedStat(Request request, Date beginDate,
			Date endDate) {
		return this.fecavLogic.findFecavBillForUsedStat(beginDate, endDate);
	}

	/**
	 * 查询外汇核销单(所有)
	 * 
	 * @param request
	 *            发送请求
	 * @return 没有遗失作废的所有外汇核销单
	 */
	@AuthorityFunctionAnnotation(caption = "浏览外部领单", index = 1)
	public List findFecavBill(Request request) {
		return this.fecavDao.findFecavBill();
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览外部领单", index = 1)
	public List findFecavBill(Request request, String condition,
			List<Object> lsParam) {
		return this.fecavDao.findFecavBill(condition, lsParam);
	}

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
	@AuthorityFunctionAnnotation(caption = "浏览内部领单", index = 2)
	public List findFecavBillNotOuterObtain(Request request, String condition,
			List<Object> lsParam) {
		return this.fecavDao.findFecavBillNotOuterObtain(condition, lsParam);
	}

	/**
	 * 根据系统类型抓取正在执行的账册号或手册号
	 */
	public List findEmsHeadByProjectType(Request request, Integer projectType) {
		return this.fptManageLogic
				.findEmsHeadByProjectType(projectType);
	}

	/**
	 * 删除进口白单，如果白单的进口报关单号在外汇核销单报关单号中存在，则不删除。
	 * 
	 * @param icd
	 *            ImpCustomsDeclaration 进口白单
	 * 
	 * @return 如果删除则为true 反之为false
	 */
	public boolean deleteImpCustomsDeclaration(Request request,
			ImpCustomsDeclaration icd) {
		return this.fecavLogic.deleteImpCustomsDeclaration(icd);
	}

	/**
	 * 根据系统类型抓取正在执行的报关单
	 */
	public List findCustomsDeclarationByProjectType(Request request,
			List contracts, Date beginDate, Date endDate, Integer projectType) {
		return this.fecavLogic.findCustomsDeclarationByProjectType(contracts,
				beginDate, endDate, projectType);
	}

	public List findEmsHeadH2kByProcessExe(Request request) {
		return this.fecavDao.findEmsHeadH2kByProcessExe();
	}

	/**
	 * 更新进口白单的余额
	 * 
	 * @param imp
	 */
	public ImpCustomsDeclaration updateRemainImpCustomsDecMoney(
			Request request, ImpCustomsDeclaration imp) {
		this.fecavLogic.updateRemainImpCustomsDecMoney(imp);
		return imp;
	}
	
	/**
	 * 查找是否有汇票已被冲消
	 * 
	 * @param List
	 *            已冲销的汇票List
	 * @return Boolean
	 */
	public Boolean isBillOfExchangeIsUer(List list ) {
		return this.fecavLogic.isBillOfExchangeIsUer(list);
	}

	public boolean findCustomsInofFecavBill(Request request, Customs customs) {
		return this.fecavLogic.findCustomsInofFecavBill(customs);
	}
	/**
	 * 根据外汇核销单的领用类型和是否作废状态获取核销单号最小的外汇核销单
	 * @param request
	 * @param obtain
	 * @param billState
	 * @return
	 */
	public FecavBill getMinFecavBill(Request request,Integer billState,Boolean isBlankOut){
		return this.fecavDao.getMinFecavBill(billState, isBlankOut);
	}
	/**
	 * 从内部领单中新增核销单
	 * 
	 * @param request
	 *            发送请求
	 * @param list
	 *            核销单
	 * @return 新增核销单信息
	 */
	public List<FecavBill> addFecavBill(Request request,
			List<FecavBill> list){
		return this.fecavLogic.addFecavBill(list);
	}
}
