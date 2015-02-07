package com.bestway.bls.action;

import java.util.Date;
import java.util.List;

import javax.swing.JDialog;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.CollateBindDetail;
import com.bestway.bls.entity.CollateBindDetailList;
import com.bestway.bls.entity.StorageBill;
import com.bestway.bls.entity.StorageBillAfter;
import com.bestway.common.Request;



public interface BlsCheckCancelAction {
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
	public List<CollateBind> findCollateBind(Request request, int index,
			int length, String property, Object value, boolean isLike,Integer type);

	/**
	 * 保存核销捆绑关系基本信息（表头）
	 * 
	 * @param obj
	 *            核销捆绑关系基本信息（表头）
	 */
	public CollateBind saveCollateBind(Request request, CollateBind obj);

	/**
	 * 获得核销捆绑关系基本信息（表头）
	 * 
	 * @return 核销捆绑关系基本信息（表头）
	 */
	public List<CollateBind> findCollateBind(Request request);
	
	/**
	 * 获得核销捆绑关系基本信息（表头）
	 * 
	 * @return 核销捆绑关系基本信息（表头）
	 */
	public List<CollateBind> findCollateBind(Request request,Date start,Date end,Integer type);

	/**
	 * 删除核销捆绑关系基本信息（表头）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表头）
	 */
	public void deleteCollateBind(Request request, List<CollateBind> list);

	/**
	 * 删除核销捆绑关系基本信息（表头）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表头）
	 */
	public void deleteCollateBind(Request request, CollateBind collateBind);

	/**
	 * 保存核销捆绑关系基本信息（表体）
	 * 
	 * @param obj
	 *            核销捆绑关系基本信息（表体）
	 */
	public CollateBindDetail saveCollateBindDetail(Request request,
			CollateBindDetail obj);

	/**
	 * 获得核销捆绑关系基本信息（表体）
	 * 
	 * @return 核销捆绑关系基本信息（表体）
	 */
	public List<CollateBindDetail> findCollateBindDetailByHead(Request request,
			String collateBindId);

	/**
	 * 删除核销捆绑关系基本信息（表体）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表体）
	 */
	public void deleteCollateBindDetail(Request request,
			List<CollateBindDetail> list);

	/**
	 * 删除核销捆绑关系基本信息（表体）内容
	 * 
	 * @param list
	 *            核销捆绑关系基本信息（表体）
	 */
	public void deleteCollateBindDetail(Request request,
			CollateBindDetail collateBindDetail);

	/** save 核销捆绑关系核销项信息 */
	public CollateBindDetailList saveCollateBindDetailList(Request request,
			CollateBindDetailList obj);

	/** find 核销捆绑关系核销项信息 */
	public List<CollateBindDetailList> findCollateBindDetailListByDetail(
			Request request, String collateBindDetailId);

	/** delete 核销捆绑关系核销项信息 */
	public void deleteCollateBindDetailList(Request request,
			List<CollateBindDetailList> list);

	/** delete 核销捆绑关系核销项信息 */
	public void deleteCollateBindDetailList(Request request,
			CollateBindDetailList collateBindDetailList);

	// ////////////////////////////////////////////////////////////////
	//
	// 每次新增不用弹出对话框是好的操作模式 要常用,这样用户方便性加强,
	// window状态也好控制,好的经验要学习
	//
	// //////////////////////////////////////////////////////////////////

	/**
	 * 每次新增不用弹出对话框是好的操作模式, 核销捆绑关系基本信息 CollateBind
	 * */
	public CollateBind newCollateBind(Request request,CollateBind bind);

	/**
	 * 每次新增不用弹出对话框是好的操作模式, 核销捆绑关系基本信息明细 CollateBindDetail
	 * */
	public CollateBindDetail newCollateBindDetail(Request request,
			CollateBind collateBind);

	/**
	 * 每次新增不用弹出对话框是好的操作模式, 核销捆绑关系基本信息明细列表 CollateBindDetailList
	 * */
	public CollateBindDetailList newCollateBindDetailList(Request request,
			CollateBindDetail collateBindDetail);

	/** 导入数据来自仓单 */
	public List<CollateBind> saveImportDataByBill(Request request,
			String taskId, List<StorageBill> storageBillList);

	/** 获得正在执行的 StorageBill */
	public List<StorageBill> findStorageBillByProcessExe(Request request,Integer flag);

	/**
	 * 单证核销海关申报
	 */
	public CollateBind applyCollatebind(Request request, CollateBind collateBind);

	/**
	 * 单证核销申报回执处理
	 * 
	 * @param delivery
	 * @param blsReceiptResult
	 * @return
	 */
	public CollateBind processCollatebind(Request request, CollateBind collateBind,
			BlsReceiptResult blsReceiptResult);
	
	/**
	 * 获取当前公司
	 * 
	 * @return Company 当前公司，返回符合条件的第一条数据
	 */
	public Company findCompany(Request request);
	
	/**
	 * 查询海关公司注册
	 * @param request
	 * @param code
	 * @return
	 */
	public Brief findBrief(Request request,String code);
	
	/**
	 * 查询未核销进，出仓单
	 */
	public List<StorageBillAfter> findNoCheckBill(Request request,String ioFlag,String formType,String collateFormType);
	
	/**
	 * 自动核销
	 * @return
	 */
	public String checkBill(Request request,List<StorageBillAfter> storageBills,String formType,String collateFormType);

}
