package com.bestway.bls.action;

import java.util.List;

import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.MaterielImportApply;
import com.bestway.common.Request;

/**
 * 保税物料进口申请Action
 */
public interface MaterielImportApplyAction {

	/**
	 * 保存,修改保税物料进口申请表头
	 */
	MaterielImportApply saveMaterielImportApply(Request request,MaterielImportApply materielImportApply);
	
	/**
	 * 删除保税物料进口申请表头
	 */
	void deleteMaterielImportApply(Request request,MaterielImportApply materielImportApply);
	
	/**
	 * 查询所有MaterielImportApply
	 * @return
	 */
	List findMaterielImportApplyAll(Request request);
	
	/**
	 * 保存保税物料进口申请商品信息
	 */
	void saveMaterielImportApplyList(Request request,List list);
	
	/**
	 * 删除保税物料进口申请商品信息
	 */
	void deleteMaterielImportApplyList(Request request,List list);
	
	/**
	 * 根据表头查询商品
	 * @return
	 */
	List findMaterielImportApplyListByHead(Request request,MaterielImportApply materielImportApply);
	
	/**
	 * 查询自用商品库商品
	 */
	List findComplex(Request request,String sFields, String sValue,MaterielImportApply materielImportApply);
	
	/**
	 * 查询仓库编码
	 * @param request
	 * @return
	 */
	List findDepotNo(Request request);
	
	/**
	 * 仓单特殊申请海关申报
	 */
	public MaterielImportApply applyMaterielImportApply(Request request, MaterielImportApply materielImportApply);

	/**
	 * 仓单特殊申请回执处理
	 */
	public MaterielImportApply processMaterielImportApply(Request request, MaterielImportApply materielImportApply,BlsReceiptResult blsReceiptResult);
}
