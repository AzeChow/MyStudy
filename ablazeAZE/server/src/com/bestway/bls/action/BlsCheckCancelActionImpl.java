package com.bestway.bls.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.BlsCheckCancelDao;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.CollateBindDetail;
import com.bestway.bls.entity.CollateBindDetailList;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.bls.logic.BlsCheckCancelLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareState;
//保税物流-核销管理
@AuthorityClassAnnotation(caption = "保税仓管理", index = 17)
public class BlsCheckCancelActionImpl extends BaseActionImpl implements BlsCheckCancelAction {

	private BlsCheckCancelDao blsCheckCancelDao = null;
	private BlsCheckCancelLogic blsCheckCancelLogic = null;

	/**
	 * @return the blsCheckCancelDao
	 */
	public BlsCheckCancelDao getBlsCheckCancelDao() {
		return blsCheckCancelDao;
	}

	/**
	 * @return the blsCheckCancelLogic
	 */
	public BlsCheckCancelLogic getBlsCheckCancelLogic() {
		return blsCheckCancelLogic;
	}

	/**
	 * @param blsCheckCancelDao
	 *            the blsCheckCancelDao to set
	 */
	public void setBlsCheckCancelDao(BlsCheckCancelDao blsCheckCancelDao) {
		this.blsCheckCancelDao = blsCheckCancelDao;
	}

	/**
	 * @param blsCheckCancelLogic
	 *            the blsCheckCancelLogic to set
	 */
	public void setBlsCheckCancelLogic(BlsCheckCancelLogic blsCheckCancelLogic) {
		this.blsCheckCancelLogic = blsCheckCancelLogic;
	}

	/**
	 * 获得核销捆绑关系基本信息（表头）所有的数据
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return 所有核销捆绑关系基本信息（表头）数据
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--查询捆绑关系", index = 12.3)
	public List<CollateBind> findCollateBind(Request request, int index,
			int length, String property, Object value, boolean isLike,
			Integer type) {
		return this.blsCheckCancelDao.findCollateBind(index, length, property,
				value, isLike, type);
	}

	/**
	 * 保存核销捆绑关系基本信息（表头）
	 * 
	 * @param obj
	 *            核销捆绑关系基本信息（表头）
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--保存捆绑关系", index = 12.4)
	public CollateBind saveCollateBind(Request request, CollateBind obj) {
		this.blsCheckCancelDao.saveOrUpdate(obj);
		return obj;
	}

	/**
	 * 获得核销捆绑关系基本信息（表头）
	 * 
	 * @return 核销捆绑关系基本信息（表头）
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--查询捆绑关系", index = 12.3)
	public List<CollateBind> findCollateBind(Request request) {
		return this.blsCheckCancelDao.findCollateBind();
	}

	/**
	 * 获得核销捆绑关系基本信息（表头）
	 * 
	 * @return 核销捆绑关系基本信息（表头）
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--查询捆绑关系", index = 12.3)
	public List<CollateBind> findCollateBind(Request request, Date start,
			Date end, Integer type) {
		return this.blsCheckCancelDao.findCollateBind(start, end, type);
	}

	/**
	 * 删除核销捆绑关系基本信息（表头）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表头）
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--删除捆绑关系", index = 12.1)
	public void deleteCollateBind(Request request, List<CollateBind> list) {
		this.blsCheckCancelLogic.deleteCollateBind(list);
	}

	/**
	 * 删除核销捆绑关系基本信息（表头）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表头）
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--删除捆绑关系", index = 12.1)
	public void deleteCollateBind(Request request, CollateBind collateBind) {
		this.blsCheckCancelLogic.deleteCollateBind(collateBind);
	}

	/**
	 * 保存核销捆绑关系基本信息（表体）
	 * 
	 * @param obj
	 *            核销捆绑关系基本信息（表体）
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--保存捆绑关系", index = 12.4)
	public CollateBindDetail saveCollateBindDetail(Request request,
			CollateBindDetail obj) {
		this.blsCheckCancelDao.saveCollateBindDetail(obj);
		return obj;
	}

	/**
	 * 获得核销捆绑关系基本信息（表体）
	 * 
	 * @return 核销捆绑关系基本信息（表体）
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--查询捆绑关系", index = 12.3)
	public List<CollateBindDetail> findCollateBindDetailByHead(Request request,
			String collateBindId) {
		return this.blsCheckCancelDao
				.findCollateBindDetailByHead(collateBindId);
	}

	/**
	 * 删除核销捆绑关系基本信息（表体）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表体）
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--删除捆绑关系", index = 12.1)
	public void deleteCollateBindDetail(Request request,
			List<CollateBindDetail> list) {
		this.blsCheckCancelLogic.deleteCollateBindDetail(list);
	}

	/**
	 * 删除核销捆绑关系基本信息（表体）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表体）
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--删除捆绑关系", index = 12.1)
	public void deleteCollateBindDetail(Request request,
			CollateBindDetail collateBindDetail) {
		this.blsCheckCancelLogic.deleteCollateBindDetail(collateBindDetail);
	}

	/** save 核销捆绑关系核销项信息 */
	@AuthorityFunctionAnnotation(caption = "核销管理--保存捆绑关系", index = 12.4)
	public CollateBindDetailList saveCollateBindDetailList(Request request,
			CollateBindDetailList obj) {
		this.blsCheckCancelDao.saveCollateBindDetailList(obj);
		return obj;
	}

	/** find 核销捆绑关系核销项信息 */
	@AuthorityFunctionAnnotation(caption = "核销管理--查询捆绑关系", index = 12.3)
	public List<CollateBindDetailList> findCollateBindDetailListByDetail(
			Request request, String collateBindDetailId) {
		return this.blsCheckCancelDao
				.findCollateBindDetailListByDetail(collateBindDetailId);
	}

	/** delete 核销捆绑关系核销项信息 */
	@AuthorityFunctionAnnotation(caption = "核销管理--删除捆绑关系", index = 12.1)
	public void deleteCollateBindDetailList(Request request,
			List<CollateBindDetailList> list) {
		this.blsCheckCancelDao.deleteCollateBindDetailList(list);
	}

	/** delete 核销捆绑关系核销项信息 */
	@AuthorityFunctionAnnotation(caption = "核销管理--删除捆绑关系", index = 12.1)
	public void deleteCollateBindDetailList(Request request,
			CollateBindDetailList collateBindDetailList) {
		this.blsCheckCancelDao
				.deleteCollateBindDetailList(collateBindDetailList);
	}

	// ////////////////////////////////////////////////////////////////
	//
	// 每次新增不用弹出对话框是好的操作模式 要常用,这样用户方便性加强,
	// window状态也好控制
	//
	// //////////////////////////////////////////////////////////////////

	/**
	 * 每次新增不用弹出对话框是好的操作模式, 核销捆绑关系基本信息 CollateBind
	 * */
	@AuthorityFunctionAnnotation(caption = "核销管理--新增捆绑关系", index = 12)
	public CollateBind newCollateBind(Request request, CollateBind bind) {
		this.blsCheckCancelDao.saveCollateBind(bind);
		return bind;
	}

	/**
	 * 每次新增不用弹出对话框是好的操作模式, 核销捆绑关系基本信息明细 CollateBindDetail
	 * */
	@AuthorityFunctionAnnotation(caption = "核销管理--新增捆绑关系", index = 12)
	public CollateBindDetail newCollateBindDetail(Request request,
			CollateBind collateBind) {
		return this.blsCheckCancelLogic.newCollateBindDetail(collateBind);
	}

	/**
	 * 每次新增不用弹出对话框是好的操作模式, 核销捆绑关系基本信息明细列表 CollateBindDetailList
	 * */
	@AuthorityFunctionAnnotation(caption = "核销管理--新增捆绑关系", index = 12)
	public CollateBindDetailList newCollateBindDetailList(Request request,
			CollateBindDetail collateBindDetail) {
		return this.blsCheckCancelLogic
				.newCollateBindDetailList(collateBindDetail);
	}

	/** 导入数据来自创单 */
	@AuthorityFunctionAnnotation(caption = "核销管理--保存捆绑关系", index = 12.4)
	public List<CollateBind> saveImportDataByBill(Request request,
			String taskId, List<StorageBill> storageBillList) {
		return this.blsCheckCancelLogic.saveImportDataByBill(taskId,
				storageBillList);
	}

	/** 获得正在执行的 StorageBill */
	@AuthorityFunctionAnnotation(caption = "核销管理--查询捆绑关系", index = 12.3)
	public List<StorageBill> findStorageBillByProcessExe(Request request,Integer flag) {
		return this.blsCheckCancelDao.findStorageBillByProcessExe(flag);
	}

	/**
	 * 单证核销海关申报
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--海关申报", index = 12.5)
	public CollateBind applyCollatebind(Request request, CollateBind collateBind) {
		return this.blsCheckCancelLogic.applyCollatebind(collateBind);
	}

	/**
	 * 单证核销申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--回执处理", index = 12.6)
	public CollateBind processCollatebind(Request request,
			CollateBind collateBind, BlsReceiptResult blsReceiptResult) {
		return this.blsCheckCancelLogic.processCollatebind(collateBind,
				blsReceiptResult);
	}

	/**
	 * 获取当前公司
	 * 
	 * @return Company 当前公司，返回符合条件的第一条数据
	 */
	//@AuthorityFunctionAnnotation(caption = "获取当前公司", index = 22)
	public Company findCompany(Request request) {
		return this.blsCheckCancelDao.findCompany();
	}

	/**
	 * 查询海关公司注册
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--查询捆绑关系", index = 12.3)
	public Brief findBrief(Request request, String code) {
		return this.blsCheckCancelDao.findBrief(code);
	}

	/**
	 * 查询未核销进，出仓单
	 */
	@AuthorityFunctionAnnotation(caption = "核销管理--查询捆绑关系", index = 12.3)
	public List<StorageBillAfter> findNoCheckBill(Request request,
			String ioFlag, String formType, String collateFormType) {
		return this.blsCheckCancelLogic.findNoCheckBill(ioFlag, formType,
				collateFormType);
	}

	/**
	 * 自动核销
	 * 
	 * @return
	 */
	//@AuthorityFunctionAnnotation(caption = "自动核销", index = 25)
	public String checkBill(Request request,
			List<StorageBillAfter> storageBills, String formType,
			String collateFormType) {
		return this.blsCheckCancelLogic.checkBill(storageBills, formType,
				collateFormType);
	}
}
