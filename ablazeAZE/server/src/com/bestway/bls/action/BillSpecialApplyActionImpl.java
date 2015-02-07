package com.bestway.bls.action;

import java.util.List;

import com.bestway.bls.dao.BillSpecialApplyDao;
import com.bestway.bls.entity.BillSpecialApplyApplyList;
import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.bls.logic.BillSpecialApplyLogic;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * 仓单特殊申请action实现
 * @author Administrator
 *
 */
// -仓单特殊申请
@AuthorityClassAnnotation(caption = "保税仓管理", index = 17)
public class BillSpecialApplyActionImpl implements BillSpecialApplyAction{

	/**
	 * 仓单特殊申请logic
	 */
	private BillSpecialApplyLogic billSpecialApplyLogic;	
	
	/**
	 * 仓单特殊申请dao
	 */
	private BillSpecialApplyDao billSpecialApplyDao;

	/*******************************特殊申请单表头*****************************************/
	/**
	 * 删除仓单特殊申请单表头
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请单--删除表头", index = 10)
	public void deleteBillSpecialApplyHead(
			Request request,BillSpecialApplyHead billSpecialApplyHead) {
		// TODO Auto-generated method stub
		billSpecialApplyLogic.deleteBillSpecialApplyHead(billSpecialApplyHead);
	}

	/**
	 * 查询所有仓单特殊申请单表头
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请单--查询表头", index = 10.1)
	public List<BillSpecialApplyHead> findBillSpecialApplyHead(Request request) {
		// TODO Auto-generated method stub
		return billSpecialApplyDao.findBillSpecialApplyHead();
	}

	/**
	 * 保存仓单特殊申请单表头
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请单--保存表头", index = 10.2)
	public BillSpecialApplyHead saveBillSpecialApplyHead(
			Request request,BillSpecialApplyHead billSpecialApplyHead) {
		// TODO Auto-generated method stub
		billSpecialApplyDao.saveBillSpecialApplyHead(billSpecialApplyHead);
		return billSpecialApplyHead;
	}
	
	/*******************************特殊申请单表体*****************************************/
	/**
	 * 删除仓单特殊申请单表体
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请单--删除表头", index = 10)
	public void deleteApplyList(Request request,List<BillSpecialApplyApplyList> applyList) {
		// TODO Auto-generated method stub
		 billSpecialApplyLogic.deleteApplyList(applyList);
	}

	/**
	 * 根据仓单号查询商品
	 * @param billNo 仓单号
	 */
	@AuthorityFunctionAnnotation(caption = "查询商品-根据仓单号", index = 5)
	public List<BillSpecialApplyApplyList> findApplyListByBillNo(Request request,BillSpecialApplyHead billSpecialApplyHead){
		return billSpecialApplyDao.findApplyList(billSpecialApplyHead);
	}
	/**
	 * 保存仓单特殊申请单表体
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请单--批量保存表体", index = 10.6)
	public void saveApplyList(Request request,List<BillSpecialApplyApplyList> applyList) {
		// TODO Auto-generated method stub
		 billSpecialApplyLogic.saveApplyList(applyList);
	}
	
	/**
	 * 保存仓单特殊申请单表体
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请单--保存表体", index = 10.7)
	public void saveApplyList(Request request,BillSpecialApplyApplyList billApply){
		billSpecialApplyDao.saveApplyList(billApply);
	}
	
	/*******************************仓单里的商品*****************************************/
	/**
	 * 查询仓单里的商品
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请单--查询表头", index = 10.1)
	public List<StorageBillAfter> findBillDepot(Request request,String billNo,boolean isFilt){
		return billSpecialApplyLogic.findBillDepot(billNo, isFilt);
	}

	/**
	 * 仓单特殊申请logic
	 */
	public BillSpecialApplyLogic getBillSpecialApplyLogic() {
		return billSpecialApplyLogic;
	}

	/**
	 * 仓单特殊申请logic
	 */
	public void setBillSpecialApplyLogic(BillSpecialApplyLogic billSpecialApplyLogic) {
		this.billSpecialApplyLogic = billSpecialApplyLogic;
	}

	/**
	 * 仓单特殊申请dao
	 */
	public BillSpecialApplyDao getBillSpecialApplyDao() {
		return billSpecialApplyDao;
	}

	/**
	 * 仓单特殊申请dao
	 */
	public void setBillSpecialApplyDao(BillSpecialApplyDao billSpecialApplyDao) {
		this.billSpecialApplyDao = billSpecialApplyDao;
	}
	
	/**
	 * 查询仓单
	 * @param request
	 * @return
	 */
	public List<StorageBill> findStorageBill(Request request){
		return this.billSpecialApplyDao.findStorageBill();
	}
	
	/**
	 * 仓单特殊申请海关申报
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请--海关申报", index = 10.5)
	public BillSpecialApplyHead applyBillSpecialApply(Request request, BillSpecialApplyHead billSpecialApplyHead){
		return this.billSpecialApplyLogic.applyBillSpecialApply(billSpecialApplyHead);
	}
	
	/**
	 * 仓单特殊申请回执处理
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请--回执处理", index = 10.4)
	public BillSpecialApplyHead processBillSpecialApply(Request request, BillSpecialApplyHead billSpecialApplyHead,BlsReceiptResult blsReceiptResult){
		return this.billSpecialApplyLogic.processBillSpecialApply(billSpecialApplyHead,blsReceiptResult);
	}
}
