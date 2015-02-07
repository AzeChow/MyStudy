/*
 * Created on 2005-3-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.action;

import java.util.Hashtable;
import java.util.List;

import com.bestway.bls.dao.BlsInnerMergeDao;
import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsInnerMergeDataOrder;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.bls.logic.BlsImportFromOther;
import com.bestway.bls.logic.BlsInnerMergeLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;

/**
 * com.bestway.bls.blsinnermerge.action.BlsInnerMergeActionImpl
 * 
 * 内部归并接口
 */
//保税物流-报关商品资料和物料与报关对应表管理
@AuthorityClassAnnotation(caption = "保税仓管理", index = 17)
public class BlsInnerMergeActionImpl extends BaseActionImpl implements
		BlsInnerMergeAction {

	/**
	 * 内部归并DAO接口
	 */
	private BlsInnerMergeDao blsInnerMergeDao = null;

	/**
	 * 内部归并LOGIC
	 */
	private BlsInnerMergeLogic blsInnerMergeLogic = null;

	/**
	 * 内部归并导入接口
	 */
	private BlsImportFromOther blsImportFromOther = null;

	/**
	 * 获取blsInnerMergeDao
	 * 
	 * @return blsInnerMergeDao
	 */
	public BlsInnerMergeDao getBlsInnerMergeDao() {
		return blsInnerMergeDao;
	}

	/**
	 * 设置blsInnerMergeDao
	 * 
	 * @param blsInnerMergeDao
	 */
	public void setBlsInnerMergeDao(BlsInnerMergeDao blsInnerMergeDao) {
		this.blsInnerMergeDao = blsInnerMergeDao;
	}

	/**
	 * 获取blsImportFromOther
	 * 
	 * @return
	 */
	public BlsImportFromOther getBlsImportFromOther() {
		return blsImportFromOther;
	}

	/**
	 * 获取blsImportFromOther
	 * 
	 * @param blsImportFromOther
	 */
	public void setBlsImportFromOther(BlsImportFromOther blsImportFromOther) {
		this.blsImportFromOther = blsImportFromOther;
	}

	/**
	 * 返回模块名
	 */
	public String getName() {
		return "";
	}

	/**
	 * 获取blsInnerMergeLogic
	 * 
	 * @return Returns the BlsInnerMergeLogic.
	 */
	public BlsInnerMergeLogic getBlsInnerMergeLogic() {
		return blsInnerMergeLogic;
	}

	/**
	 * 设置blsInnerMergeLogic
	 * 
	 * @param BlsInnerMergeLogic
	 *            The BlsInnerMergeLogic to set.
	 */
	public void setBlsInnerMergeLogic(BlsInnerMergeLogic BlsInnerMergeLogic) {
		this.blsInnerMergeLogic = BlsInnerMergeLogic;
	}

	/**
	 * 过滤已经在 报关商品资料 中存在的 自用商品编码
	 * 
	 * @param request
	 *            请求控制
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是Complex型，自用商品编码
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public List findComplexOutOfBlsTenInnerMerge(Request request, int index,
			int length, String property, Object value, boolean isLike) {
		return this.blsInnerMergeDao.findComplexOutOfBlsTenInnerMerge(index,
				length, property, value, isLike);
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型,并且满足指定的报关商品编码。
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param tenCode
	 *            报关商品编码
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public List findBlsTenInnerMergeByInnerMerge(Request request,
			String tenCode, Integer seqNum) {
		return this.blsInnerMergeDao.findBlsTenInnerMergeByInnerMerge(tenCode,
				seqNum);
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-浏览权限", index = 3)
	public List findBlsTenInnerMergeByInnerMerge(Request request) {
		return this.blsInnerMergeDao.findBlsTenInnerMergeByInnerMerge();
	}

	/**
	 * 查找物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-浏览权限", index = 3)
	public List findBlsInnerMerge(Request request) {
		return blsInnerMergeDao.findBlsInnerMerge();
	}

	// /**
	// * 查找物料与报关对应表来自不同物料类型
	// *
	// * @param request
	// * 请求控制
	// * @param materielType
	// * 物料类型
	// * @return List 是BlsInnerMerge型，物料与报关对应表
	// */
	// @AuthorityFunctionAnnotation(caption = "浏览物料与报关对应表", index = 4)
	// public List findBlsInnerMerge(Request request) {//, String materielType
	// return blsInnerMergeDao.findBlsInnerMerge();
	// }

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public List findBlsInnerMergeByTenInnerMerge(Request request,
			BlsTenInnerMerge blsTenInnerMerge) {
		return this.blsInnerMergeDao
				.findBlsInnerMergeByTenInnerMerge(blsTenInnerMerge);
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型,并且满足指定的报关商品编码。
	 * 
	 * @param materielType
	 *            物料类型
	 * @param tenCode
	 *            报关商品编码
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public List findBlsInnerMerge(Request request, String tenCode,// String
																	// type,
			Integer seqNum) {
		return blsInnerMergeDao.findBlsInnerMerge(tenCode, seqNum);
	}

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param BlsInnerMerge
	 *            物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public BlsInnerMerge saveBlsInnerMerge(Request request,
			BlsInnerMerge BlsInnerMerge) {
		blsInnerMergeDao.saveBlsInnerMerge(BlsInnerMerge);
		return BlsInnerMerge;
	}

	/**
	 * 删除物料与报关对应表里的数据
	 * 
	 * @param request
	 *            请求控制
	 * @param blsInnerMerge
	 *            物料与报关对应表里的数据
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public void deleteBlsInnerMerge(Request request, BlsInnerMerge blsInnerMerge) {
		this.blsInnerMergeLogic.deleteBlsInnerMerge(blsInnerMerge);
	}

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public List saveBlsInnerMerge(Request request, List list) {
		this.blsInnerMergeDao.saveBlsInnerMerge(list);
		return list;
	}

	/**
	 * 删除物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是blsInnerMergeList型，物料与报关对应表里的数据
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public void deleteBlsInnerMerge(Request request, List list) {
		this.blsInnerMergeLogic.deleteBlsInnerMerge(list);
	}

	/**
	 * 自动从报关常用物料中导入资料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-新增权限", index = 15)
	public void importInnerMergeDataFromMateriel(Request request) {
		this.blsInnerMergeLogic.importInnerMergeDataFromMateriel();
	}

	/**
	 * 导入物料与报关对应表来自报关常用物料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielList
	 *            是Materiel型，报关常用物料
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-新增权限", index = 15)
	public List importInnerMergeDataFromMateriel(Request request,
			List materielList) {// , String materielType
		return this.blsInnerMergeLogic
				.importInnerMergeDataFromMateriel(materielList);
	}

	/**
	 * 自动归并,名称，规格，编码，单位相同
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 */
	@AuthorityFunctionAnnotation(caption = "自动归并名称规格编码-单位相同", index = 17)
	public void blsAutoMerge(Request request) {// , String materielType
		this.blsInnerMergeLogic.blsAutoMerge();
	}

	/**
	 * 查找不存在物料与报关对应表的物料来自料件类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return 是Materiel型，报关常用物料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public List findMaterielForBlsInnerMerge(Request request, int index,
			int length, String property, Object value, Boolean isLike) {// String
																		// materielType,
		return this.blsInnerMergeDao.findMaterielForBlsInnerMerge(index,
				length, property, value, isLike);
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public List findBlsInnerMerge(Request request, // String materielType,
			int index, int length, String property, Object value, boolean isLike) {
		return blsInnerMergeDao.findBlsInnerMerge(index, length,// materielType,
				property, value, isLike);
	}

	/**
	 * 获得物料与报关对应表里的个数
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return int 个数
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public int getBlsInnerMergeCount(Request request, String materielType) {
		return blsInnerMergeDao.getBlsInnerMergeCount(materielType);
	}

	/**
	 * 保存报关商品资料，并反写到物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BlsInnerMerge型，物料与报关对应表
	 * @param tempB
	 *            报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public List saveBlsInnerMerge(Request request, List list,
			BlsTenInnerMerge tempB) {
		blsInnerMergeLogic.saveBlsInnerMerge(list, tempB);
		return list;
	}

	/**
	 * 取消物料与报关对应表里的10码归并
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BlsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public List unDoTenInnerMerge(Request request, List list,
			boolean isDeleteTenInnerMerge) {
		blsInnerMergeLogic.unDoTenInnerMerge(list, isDeleteTenInnerMerge);
		return list;
	}

	// /**
	// * 查找物料与报关对应表的十位编码是否被合同引用
	// *
	// * @param request
	// * 请求控制
	// * @param b
	// * 报关商品资料
	// * @return List 是ContractImg型，合同备案料件
	// */
	// @AuthorityFunctionAnnotation(caption = "报关商品资料-编辑", index = 2)
	// public List findBlsInnerMergeInContract(Request request, BlsTenInnerMerge
	// b) {
	// return blsInnerMergeDao.findBlsInnerMergeInContract(b);
	// }

	/**
	 * 物料与报关对应表里十码重排
	 * 
	 * @param request
	 *            请求控制
	 * @param selectedRows
	 *            是BlsInnerMerge型，物料与报关对应表
	 * @param toNo
	 *            重排到的行号
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表里十码重排", index = 23)
	public void reSortMergeTenNo(Request request, List selectedRows, int toNo) {
		blsInnerMergeLogic.reSortMergeTenNo2(selectedRows, toNo);
	}

	/**
	 * 保存报关商品资料，并反写到物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BlsInnerMerge型，物料与报关对应表
	 * @param tempB
	 *            报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public List updateBlsInnerMerge(Request request, List list,
			BlsTenInnerMerge tempB) {
		blsInnerMergeLogic.updateBlsInnerMerge(list, tempB);
		return list;
	}

	/**
	 * 查找报关商品资料来自不同物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是BlsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public List findBlsTenInnerMerge(Request request,// String materielType,
			int index, int length, String property, Object value, boolean isLike) {
		return this.blsInnerMergeDao.findBlsTenInnerMerge(index, length,
				property, value, isLike);
	}

	/**
	 * 保存报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param blsTenInnerMerge
	 *            报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-编辑", index = 26)
	public BlsTenInnerMerge saveBlsTenInnerMerge(Request request,
			BlsTenInnerMerge blsTenInnerMerge) {
		this.blsInnerMergeDao.saveBlsTenInnerMerge2(blsTenInnerMerge);
		return blsTenInnerMerge;
	}

	/**
	 * 删除报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param blsTenInnerMerge
	 *            报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public void deleteBlsTenInnerMerge(Request request,
			BlsTenInnerMerge blsTenInnerMerge) {
		this.blsInnerMergeDao.deleteBlsTenInnerMerge(blsTenInnerMerge);
	}

	/**
	 * 保存报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Complex型，自用商品编码
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-编辑", index = 28)
	public List saveBlsTenInnerMerge(Request request, List list) {
		return this.blsInnerMergeLogic.saveBlsTenInnerMerge(list);
	}

	/**
	 * 删除报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BlsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public void deleteBlsTenInnerMerge(Request request, List list) {
		this.blsInnerMergeLogic.deleteBlsTenInnerMerge(list);
	}

	/**
	 * 获得最大的报关商品资料序号
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return int 最大序号
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public int getMaxTenBlsInnerMergeNo(Request request) {// , String
															// materielType
		return this.blsInnerMergeDao.getMaxTenBlsInnerMergeNo();
	}

	// /**
	// * 查找物料与报关对应表的十位编码是否被合同引用
	// *
	// * @param request
	// * 请求控制
	// * @param ls
	// * 是BlsInnerMerge型，物料与报关对应表
	// * @return List 是ContractImg型，合同备案料件
	// */
	// @AuthorityFunctionAnnotation(caption = "浏览物料与报关对应表", index = 4)
	// public List findBlsInnerMergeInContract(Request request,
	// List<BlsInnerMerge> ls) {
	// return this.blsInnerMergeDao.findBlsInnerMergeInContract(ls);
	// }

	/**
	 * 获取最大报关商品资料的凭证序号，但是是物料与报关对应表里对应的报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return int 最大的凭证序号
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public int getMaxBlsInnerMergeNo(Request request) {// , String materielType
		return this.blsInnerMergeDao.getMaxBlsInnerMergeNo();
	}

	/**
	 * 查找报关商品资料来自不同物料类型并且在物料与报关对应表中存在的
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            要查的表对应的属性
	 * @param value
	 *            属性对应的值
	 * @param isLike
	 *            属性的条件是使用"like"还是"="，true表示用"like"
	 * @return List 是blsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-浏览权限", index = 3)
	public List findBlsTenInnerMergeInMerge(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		return this.blsInnerMergeDao.findBlsTenInnerMergeInMerge(materielType,
				index, length, property, value, isLike);
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-浏览权限", index = 3)
	public List findBlsInnerMergeByMerge(Request request, String materielType) {
		return this.blsInnerMergeDao.findBlsInnerMergeByMerge(materielType);
	}

	/**
	 * 删除不在合同备案和物料与报关对应表里的报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BlsTenInnerMerge型，报关商品资料
	 * @param items
	 *            物料类型
	 * @return List 是BlsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-删除权限", index = 12)
	public List deleteBlsTenInnerMergeForContract(Request request, List list) {
		return this.blsInnerMergeLogic.deleteBlsTenInnerMergeForContract(list);
	}

	/**
	 * 使内部归并所有商品都改为默认系数
	 */
	@AuthorityFunctionAnnotation(caption = "内部归并所有商品都改为默认系数", index = 35)
	public void initBlsUnitDedault(Request request) {
		this.blsInnerMergeLogic.initUnitDedault();
	}

	/**
	 * 检查内部归并文档导入数据时,文本数据的正确性
	 * 
	 * @param request
	 * @param list
	 * @param ht
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "检查内部归并文档导入数据时,文本数据的正确性", index = 36)
	public List checkFileData(Request request, List list, Hashtable ht) {
		return this.blsImportFromOther.checkFileData(list, ht, request
				.getTaskId());
	}

	/**
	 * 导入文件来自文件
	 * 
	 * @param list
	 */
	@AuthorityFunctionAnnotation(caption = "导入文件", index = 10)
	public void importDataFromTxtFile(Request request, List list) {
		this.blsImportFromOther
				.importDataFromTxtFile(list, request.getTaskId());
	}

	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public List findExistedMaterialPtNoInInnerMerge(Request request) {
		return this.blsInnerMergeDao.findExistedMaterialPtNoInInnerMerge();
	}

	/**
	 * 查询内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料-查询", index = 1)
	public BlsInnerMergeDataOrder findBlsInnerMergeDataOrder(Request request) {
		return this.blsInnerMergeDao.findBlsInnerMergeDataOrder();
	}

	/**
	 * 内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "导入文件", index = 10)
	public BlsInnerMergeDataOrder saveBlsInnerMergeDataOrder(Request request,
			BlsInnerMergeDataOrder blsInnerMergeDataOrder) {
		this.blsInnerMergeDao
				.saveBlsInnerMergeDataOrder(blsInnerMergeDataOrder);
		return blsInnerMergeDataOrder;
	}
}