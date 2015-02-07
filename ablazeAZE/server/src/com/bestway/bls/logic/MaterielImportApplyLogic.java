package com.bestway.bls.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.MaterielImportApplyDao;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsReceiptResultType;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.GoodsList;
import com.bestway.bls.entity.MaterielImportApply;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;

/**
 * 保税物料进口申请LOGIC
 * 
 * @author chen
 * 
 */
public class MaterielImportApplyLogic {

	/**
	 * 保税物料进口申请DAO
	 */
	private MaterielImportApplyDao materielImportApplyDao;

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

	public MaterielImportApplyDao getMaterielImportApplyDao() {
		return materielImportApplyDao;
	}

	public void setMaterielImportApplyDao(
			MaterielImportApplyDao materielImportApplyDao) {
		this.materielImportApplyDao = materielImportApplyDao;
	}

	/**
	 * 删除保税物料进口申请表头
	 * 
	 * @param materielImportApply
	 */
	public void deleteMaterielImportApply(
			MaterielImportApply materielImportApply) {
		// TODO Auto-generated method stub
		List<GoodsList> lists = materielImportApplyDao
				.findMaterielImportApplyListByHead(materielImportApply);
		this.deleteMaterielImportApplyList(lists);
		materielImportApplyDao.deleteMaterielImportApply(materielImportApply);
	}

	/**
	 * 保存保税物料进口申请商品信息
	 */
	public void saveMaterielImportApplyList(List list) {
		for (int i = 0, zise = list.size(); i < zise; i++) {
			GoodsList goodsList = (GoodsList) list.get(i);
			materielImportApplyDao.saveGoodsList(goodsList);
		}
	}

	/**
	 * 删除保税物料进口申请商品信息
	 */
	public void deleteMaterielImportApplyList(List list) {
		for (int i = 0, zise = list.size(); i < zise; i++) {
			GoodsList goodsList = (GoodsList) list.get(i);
			materielImportApplyDao.deleteGoodsList(goodsList);
		}
	}

	/**
	 * 
	 * @param sFields
	 * @param sValue
	 * @param materielImportApply
	 * @return
	 */
	public List findComplex(String sFields, String sValue,
			MaterielImportApply materielImportApply) {
		List<Complex> complexs = materielImportApplyDao.findComplex(sFields,
				sValue);
		List<GoodsList> goods = materielImportApplyDao
				.findMaterielImportApplyListByHead(materielImportApply);
		for (GoodsList good : goods) {
			complexs.remove(good.getCodeTS());
		}
		return complexs;
	}

	/**
	 * 仓单特殊申请海关申报
	 */
	public MaterielImportApply applyMaterielImportApply(
			MaterielImportApply materielImportApply) {
		String serviceType = BlsServiceType.IMPORT_APPLY;
		// 关键值
		String keyCode = materielImportApply.getWarehouseCode().getCode();
		// 生成报文查询值参数
		Map<String, String> queryValues = new HashMap<String, String>();
		queryValues.put("id", materielImportApply.getId());
		// 海关申报
		BlsReceiptResult blsReceiptResult = this.blsMessageLogic
				.declareMessage(serviceType, materielImportApply.getId(),
						keyCode, queryValues, null, null,(Company) materielImportApply.getCompany());
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
		materielImportApply.setDeclareState(declareState);
		this.materielImportApplyDao.saveOrUpdate(materielImportApply);
		return materielImportApply;
	}

	/**
	 * 仓单特殊申请回执处理
	 */
	public MaterielImportApply processMaterielImportApply(
			MaterielImportApply materielImportApply,
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
		materielImportApply.setDeclareState(declareState);
		this.materielImportApplyDao.saveOrUpdate(materielImportApply);
		return materielImportApply;
	}
}
