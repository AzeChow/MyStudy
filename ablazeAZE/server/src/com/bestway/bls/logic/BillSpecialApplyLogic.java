package com.bestway.bls.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.BillSpecialApplyDao;
import com.bestway.bls.dao.StorageBillDao;
import com.bestway.bls.entity.BillSpecialApplyApplyList;
import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.BillSpecialApplyType;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsReceiptResultType;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.EntranceMessage;
import com.bestway.bls.entity.BillSpecialApplyType;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsReceiptResultType;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;

/**
 * 仓单特殊申请logic
 * 
 * @author Administrator
 * 
 */
public class BillSpecialApplyLogic {
	/**
	 * 仓单特殊申请dao
	 */
	private BillSpecialApplyDao billSpecialApplyDao;

	/**
	 * 仓单商品DAO
	 */
	private StorageBillDao storageBillDao;
	/**
	 * 报文发送，回执处理
	 */
	private BlsMessageLogic blsMessageLogic;

	public BlsMessageLogic getBlsMessageLogic() {
		return blsMessageLogic;
	}

	public void setBlsMessageLogic(BlsMessageLogic blsMessageLogic) {
		this.blsMessageLogic = blsMessageLogic;
	}

	/**
	 * 删除仓单特殊申请单表头
	 */
	public void deleteBillSpecialApplyHead(
			BillSpecialApplyHead billSpecialApplyHead) {
		// 查询仓单表头对应的商品
		List<BillSpecialApplyApplyList> applyLists = billSpecialApplyDao
				.findApplyList(billSpecialApplyHead);
		this.deleteApplyList(applyLists);
		billSpecialApplyDao.deleteBillSpecialApplyHead(billSpecialApplyHead);
	}

	/**
	 * 删除仓单特殊申请单表体
	 */
	public void deleteApplyList(List<BillSpecialApplyApplyList> applyList) {
		if (applyList == null) {
			return;
		}
		for (BillSpecialApplyApplyList billSpecialApplyApplyList : applyList) {
			billSpecialApplyDao.deleteApplyList(billSpecialApplyApplyList);
		}
	}

	/**
	 * 保存仓单特殊申请单表体
	 */
	public void saveApplyList(List<BillSpecialApplyApplyList> applyList) {
		if (applyList == null) {
			return;
		}
		for (BillSpecialApplyApplyList villSpecialApplyApplyList : applyList) {
			billSpecialApplyDao.saveOrUpdate(villSpecialApplyApplyList);
		}
	}

	/**
	 * 查询仓单商品
	 */
	public List<StorageBillAfter> findBillDepot(String billNo, boolean isFilt) {
		List<StorageBillAfter> storeList = billSpecialApplyDao
				.findStorageBillByBillNo(billNo);

		return storeList;// TODO edit
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
	 * 仓单商品DAO
	 */
	public StorageBillDao getStorageBillDao() {
		return storageBillDao;
	}

	/**
	 * 仓单商品DAO
	 */
	public void setStorageBillDao(StorageBillDao storageBillDao) {
		this.storageBillDao = storageBillDao;
	}

	/**
	 * 车到报告海关申报
	 */
	public BillSpecialApplyHead applyBillSpecialApply(
			BillSpecialApplyHead billSpecialApplyHead) {
		// 系统类型，比如车次，车到，或者核销
		String serviceType = "";
		if (BillSpecialApplyType.PRC
				.equals(billSpecialApplyHead.getApplyType())) {
			serviceType = BlsServiceType.PROCESS_APPLY;
		} else if (BillSpecialApplyType.ABD.equals(billSpecialApplyHead
				.getApplyType())) {
			serviceType = BlsServiceType.CARGOABANDON_APPLY;
		} else if (BillSpecialApplyType.RPL.equals(billSpecialApplyHead
				.getApplyType())) {
			serviceType = BlsServiceType.CARGOREPLACE_APPLY;
		} else if (BillSpecialApplyType.CAN.equals(billSpecialApplyHead
				.getApplyType())) {
			serviceType = BlsServiceType.BILLCANCEL_APPLY;
		} else if (BillSpecialApplyType.DLA.equals(billSpecialApplyHead
				.getApplyType())) {
			serviceType = BlsServiceType.TRN_APPLY;// 此处没有正确对应
		} else {
			throw new RuntimeException("无法识别的特殊申请类型"
					+ billSpecialApplyHead.getApplyType());
		}
		// 关键值
		String keyCode = billSpecialApplyHead.getBillNo();
		// 生成报文查询值参数
		Map<String, String> queryValues = new HashMap<String, String>();
		queryValues.put("id", billSpecialApplyHead.getId());
		// 海关申报
		BlsReceiptResult blsReceiptResult = this.blsMessageLogic
				.declareMessage(serviceType, billSpecialApplyHead.getId(),
						keyCode, queryValues, null, null,(Company)billSpecialApplyHead.getCompany());
		String declareState = DeclareState.WAIT_EAA;
		if (BlsReceiptResultType.checkReceiptResultIsSuccess(blsReceiptResult)) {
			declareState = DeclareState.PROCESS_EXE;
		} else if (BlsReceiptResultType
				.checkReceiptResultIsFail(blsReceiptResult)) {
			declareState = DeclareState.APPLY_POR;
		} else if (BlsReceiptResultType
				.checkReceiptResultIsWaitfor(blsReceiptResult)) {
			declareState = DeclareState.WAIT_EAA;
		}
		billSpecialApplyHead.setDeclareState(declareState);
		this.billSpecialApplyDao.saveOrUpdate(billSpecialApplyHead);
		return billSpecialApplyHead;
	}

	/**
	 * 单证核销申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	public BillSpecialApplyHead processBillSpecialApply(
			BillSpecialApplyHead billSpecialApplyHead,
			BlsReceiptResult blsReceiptResult) {
		String declareState = DeclareState.WAIT_EAA;
		if (BlsReceiptResultType.checkReceiptResultIsSuccess(blsReceiptResult)) {
			declareState = DeclareState.PROCESS_EXE;
		} else if (BlsReceiptResultType
				.checkReceiptResultIsFail(blsReceiptResult)) {
			declareState = DeclareState.APPLY_POR;
		} else if (BlsReceiptResultType
				.checkReceiptResultIsWaitfor(blsReceiptResult)) {
			declareState = DeclareState.WAIT_EAA;
		}
		billSpecialApplyHead.setDeclareState(declareState);
		this.billSpecialApplyDao.saveOrUpdate(billSpecialApplyHead);
		return billSpecialApplyHead;
	}

}
