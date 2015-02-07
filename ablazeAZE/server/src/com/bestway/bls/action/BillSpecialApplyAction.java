package com.bestway.bls.action;

import java.util.List;

import com.bestway.bls.entity.BillSpecialApplyApplyList;
import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.common.Request;

/**
 * 仓单特殊申请action
 * @author Administrator
 *
 */
public interface BillSpecialApplyAction {

	/*******************************特殊申请单表头*****************************************/
	/**
	 * 保存仓单特殊申请单表头
	 */
	BillSpecialApplyHead saveBillSpecialApplyHead(Request request,BillSpecialApplyHead billSpecialApplyHead);
	
	/**
	 * 删除仓单特殊申请单表头
	 */
	void deleteBillSpecialApplyHead(Request request,BillSpecialApplyHead billSpecialApplyHead);
	
	/**
	 * 查询所有仓单特殊申请单表头
	 * @return
	 */
	List<BillSpecialApplyHead> findBillSpecialApplyHead(Request request);
	
	/*******************************特殊申请单表体*****************************************/
	/**
	 * 保存仓单特殊申请单表体
	 */
	void saveApplyList(Request request,List<BillSpecialApplyApplyList> applyList);
	
	/**
	 * 保存仓单特殊申请单表体
	 */
	void saveApplyList(Request request,BillSpecialApplyApplyList billApply);
	
	/**
	 * 删除仓单特殊申请单表体
	 */
	void deleteApplyList(Request request,List<BillSpecialApplyApplyList> applyList);
	
	/**
	 * 根据仓单号查询商品
	 */
	List<BillSpecialApplyApplyList> findApplyListByBillNo(Request request,BillSpecialApplyHead billSpecialApplyHead);
	
	
	/*******************************仓单里的商品*****************************************/
	/**
	 * 查询仓单里的商品
	 */
	public List<StorageBillAfter> findBillDepot(Request request,String billNo,boolean isFilt);
	
	/**
	 * 查询仓单
	 * @param request
	 * @return
	 */
	public List<StorageBill> findStorageBill(Request request);
	
	/**
	 * 仓单特殊申请海关申报
	 */
	public BillSpecialApplyHead applyBillSpecialApply(Request request, BillSpecialApplyHead billSpecialApplyHead);
	
	/**
	 * 仓单特殊申请回执处理
	 */
	public BillSpecialApplyHead processBillSpecialApply(Request request, BillSpecialApplyHead billSpecialApplyHead,BlsReceiptResult blsReceiptResult);
}
