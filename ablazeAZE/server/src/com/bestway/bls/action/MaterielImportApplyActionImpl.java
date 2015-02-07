package com.bestway.bls.action;

import java.util.List;

import com.bestway.bls.dao.MaterielImportApplyDao;
import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.MaterielImportApply;
import com.bestway.bls.logic.MaterielImportApplyLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
//保税物流-保税物料进口申请
@AuthorityClassAnnotation(caption = "保税仓管理", index = 17)
public class MaterielImportApplyActionImpl extends BaseActionImpl implements MaterielImportApplyAction {

	/**
	 * 保税物料进口申请DAO
	 */
	private MaterielImportApplyDao materielImportApplyDao;

	/**
	 * 保税物料进口申请LOGIC
	 */
	private MaterielImportApplyLogic materielImportApplyLogic;

	
	/**
	 * 保存,修改保税物料进口申请表头
	 */
	@AuthorityFunctionAnnotation(caption = "保税物料进口申请保存", index = 2)
	public MaterielImportApply saveMaterielImportApply(Request request,MaterielImportApply materielImportApply) {
		// TODO Auto-generated method stub
		materielImportApplyDao.saveMaterielImportApply(materielImportApply);
		return materielImportApply;
	}

	/**
	 * 保存保税物料进口申请商品信息
	 */
	@AuthorityFunctionAnnotation(caption = "保税物料进口申请保存", index = 2)
	public void saveMaterielImportApplyList(Request request,List list) {
		// TODO Auto-generated method stub
		materielImportApplyLogic.saveMaterielImportApplyList(list);
	}

	/**
	 * 查询所有MaterielImportApply
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物料进口申请-查询", index = 1)
	public List findMaterielImportApplyAll(Request request) {
		// TODO Auto-generated method stub
		return materielImportApplyDao.findMaterielImportApplyAll();
	}
	/**
	 * 根据表头查询商品
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物料进口申请-查询", index = 1)
	public List findMaterielImportApplyListByHead(
			Request request,MaterielImportApply materielImportApply) {
		// TODO Auto-generated method stub
		return materielImportApplyDao.findMaterielImportApplyListByHead(materielImportApply);
	}
	/**
	 * 查询自用商品库商品
	 */
	@AuthorityFunctionAnnotation(caption = "保税物料进口申请-查询", index = 1)
	public List findComplex(Request request,String sFields, String sValue,MaterielImportApply materielImportApply){
		return materielImportApplyLogic.findComplex(sFields, sValue,materielImportApply);	
	}
	/**
	 * 查询仓库编码
	 * @param request
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物料进口申请-查询", index = 1)
    public List findDepotNo(Request request){
    	return materielImportApplyDao.findDepotNo();
	}
    
    /**
	 * 仓单特殊申请海关申报
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请-海关申报", index = 7)
	public MaterielImportApply applyMaterielImportApply(Request request, MaterielImportApply materielImportApply){
		return materielImportApplyLogic.applyMaterielImportApply(materielImportApply);
	}

	/**
	 * 仓单特殊申请回执处理
	 */
	@AuthorityFunctionAnnotation(caption = "仓单特殊申请-回执处理", index = 8)
	public MaterielImportApply processMaterielImportApply(Request request, MaterielImportApply materielImportApply,BlsReceiptResult blsReceiptResult){
		return materielImportApplyLogic.processMaterielImportApply(materielImportApply , blsReceiptResult);
	}
	
	public MaterielImportApplyDao getMaterielImportApplyDao() {
		return materielImportApplyDao;
	}

	public void setMaterielImportApplyDao(
			MaterielImportApplyDao materielImportApplyDao) {
		this.materielImportApplyDao = materielImportApplyDao;
	}

	public MaterielImportApplyLogic getMaterielImportApplyLogic() {
		return materielImportApplyLogic;
	}

	public void setMaterielImportApplyLogic(
			MaterielImportApplyLogic materielImportApplyLogic) {
		this.materielImportApplyLogic = materielImportApplyLogic;
	}
	/**
	 * 删除保税物料进口申请表头
	 */
	@AuthorityFunctionAnnotation(caption = "保税物料进口申请-删除", index = 9)
	public void deleteMaterielImportApply(Request request,MaterielImportApply materielImportApply) {
		// TODO Auto-generated method stub
		materielImportApplyLogic.deleteMaterielImportApply(materielImportApply);
	}

	/**
	 * 删除保税物料进口申请商品信息
	 */
	@AuthorityFunctionAnnotation(caption = "保税物料进口申请-删除", index = 9)
	public void deleteMaterielImportApplyList(Request request,List list) {
		// TODO Auto-generated method stub
		materielImportApplyLogic.deleteMaterielImportApplyList(list);
	}
}
