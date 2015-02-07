/*
 * Created on 2005-3-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.bcsinnermerge.action;

import java.util.Hashtable;
import java.util.List;

import com.bestway.bcs.bcsinnermerge.dao.BcsInnerMergeDao;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomExg;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeDataOrder;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeFileData;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.bcsinnermerge.logic.BcsImportFromOther;
import com.bestway.bcs.bcsinnermerge.logic.BcsInnerMergeLogic;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeActionImpl
 * 
 * @author ls checked by 陈井彬 date 2008.11.29 内部归并接口
 */

@AuthorityClassAnnotation(caption = "电子化手册", index = 5)
public class BcsInnerMergeActionImpl extends BaseActionImpl implements
		BcsInnerMergeAction {

	/**
	 * 内部归并DAO接口
	 */
	private BcsInnerMergeDao bcsInnerMergeDao = null;

	/**
	 * 内部归并LOGIC
	 */
	private BcsInnerMergeLogic bcsInnerMergeLogic = null;

	/**
	 * 内部归并导入接口
	 */
	private BcsImportFromOther bcsImportFromOther = null;

	/**
	 * 获取bcsInnerMergeDao
	 * 
	 * @return bcsInnerMergeDao
	 */
	public BcsInnerMergeDao getBcsInnerMergeDao() {
		return bcsInnerMergeDao;
	}

	/**
	 * 设置bcsInnerMergeDao
	 * 
	 * @param bcsInnerMergeDao
	 */
	public void setBcsInnerMergeDao(BcsInnerMergeDao bcsInnerMergeDao) {
		this.bcsInnerMergeDao = bcsInnerMergeDao;
	}

	/**
	 * 获取bcsImportFromOther
	 * 
	 * @return
	 */
	public BcsImportFromOther getBcsImportFromOther() {
		return bcsImportFromOther;
	}

	/**
	 * 获取bcsImportFromOther
	 * 
	 * @param bcsImportFromOther
	 */
	public void setBcsImportFromOther(BcsImportFromOther bcsImportFromOther) {
		this.bcsImportFromOther = bcsImportFromOther;
	}

	/**
	 * 返回模块名
	 */
	public String getName() {
		return "";
	}

	/**
	 * 获取bcsInnerMergeLogic
	 * 
	 * @return Returns the BcsInnerMergeLogic.
	 */
	public BcsInnerMergeLogic getBcsInnerMergeLogic() {
		return bcsInnerMergeLogic;
	}

	/**
	 * 设置bcsInnerMergeLogic
	 * 
	 * @param BcsInnerMergeLogic
	 *            The BcsInnerMergeLogic to set.
	 */
	public void setBcsInnerMergeLogic(BcsInnerMergeLogic BcsInnerMergeLogic) {
		this.bcsInnerMergeLogic = BcsInnerMergeLogic;
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
	public List findComplexOutOfBcsTenInnerMerge(Request request, int index,
			int length, String property, Object value, boolean isLike) {
		return this.bcsInnerMergeDao.findComplexOutOfBcsTenInnerMerge(index,
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
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "工厂与报关对比--浏览", index = 4)
	public List findBcsTenInnerMergeByInnerMerge(Request request, String type,
			String tenCode, Integer seqNum) {
		return this.bcsInnerMergeDao.findBcsTenInnerMergeByInnerMerge(type,
				tenCode, seqNum);
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "工厂与报关对比--浏览", index = 4)
	public List findBcsTenInnerMergeByInnerMerge(Request request,
			String materielType) {
		return this.bcsInnerMergeDao
				.findBcsTenInnerMergeByInnerMerge(materielType);
	}

	/**
	 * 查找物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--浏览", index = 3)
	public List<BcsInnerMerge> findBcsInnerMerge(Request request) {
		return bcsInnerMergeDao.findBcsInnerMerge();
	}
	
	
	/**
	 * 查找物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--浏览", index = 3)
	public List<String> findBcsInnerMergeKeys(Request request, String materialType) {
		return bcsInnerMergeDao.findBcsInnerMergeKeys(materialType);
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--浏览", index = 3)
	public List<BcsInnerMerge> findBcsInnerMerge(Request request, String materielType) {
		return bcsInnerMergeDao.findBcsInnerMerge(materielType);
	}
	
	/**
	 * 查找物料与报关对应表来自不同物料类型(物料一对多的对应关系)
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--浏览", index = 3)
	public List<BcsInnerMerge> findBcsInnerMergeOneToMany(Request request, String materielType) {
		return bcsInnerMergeDao.findBcsInnerMergeOneToMany(materielType);
	}
	
	

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMergeByTenInnerMerge(Request request,
			BcsTenInnerMerge bcsTenInnerMerge) {
		return this.bcsInnerMergeDao
				.findBcsInnerMergeByTenInnerMerge(bcsTenInnerMerge);
	}

	
	/**
	 * 通过归并序号抓取内部归并表
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findBcsInnerMergeDataByTenSeqNum(Request request,Integer tenSeqNum,
			String materielType) {
		return this.bcsInnerMergeDao.findBcsInnerMergeDataByTenSeqNum(tenSeqNum, materielType);
	}
	
	/**
	 * 通过归并序号抓取报关常用资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findBcsTenInnerMergeDataBySeqNum(Request request,Integer tenSeqNum,
			String materielType){
		return this.bcsInnerMergeDao.findBcsTenInnerMergeDataBySeqNum(tenSeqNum, materielType);
	}
	/**
	 * 查找物料与报关对应表来自不同物料类型,并且满足指定的报关商品编码。
	 * 
	 * @param materielType
	 *            物料类型
	 * @param tenCode
	 *            报关商品编码
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List findBcsInnerMerge(Request request, String type, String tenCode,
			Integer seqNum) {
		return bcsInnerMergeDao.findBcsInnerMerge(type, tenCode, seqNum);
	}

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param BcsInnerMerge
	 *            物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--归并关系编辑删除", index = 3.2)
	public BcsInnerMerge saveBcsInnerMerge(Request request,
			BcsInnerMerge BcsInnerMerge) {
		bcsInnerMergeDao.saveBcsInnerMerge(BcsInnerMerge);
		return BcsInnerMerge;
	}

	/**
	 * 删除物料与报关对应表里的数据
	 * 
	 * @param request
	 *            请求控制
	 * @param bcsInnerMerge
	 *            物料与报关对应表里的数据
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--归并关系编辑删除", index = 3.2)
	public void deleteBcsInnerMerge(Request request, BcsInnerMerge bcsInnerMerge) {
		this.bcsInnerMergeLogic.deleteBcsInnerMerge(bcsInnerMerge);
	}

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--归并关系编辑删除", index = 3.2)
	public List saveBcsInnerMerge(Request request, List list) {
		this.bcsInnerMergeDao.saveBcsInnerMerge(list);
		return list;
	}

	/**
	 * 删除物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是bcsInnerMergeList型，物料与报关对应表里的数据
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--归并关系编辑删除", index = 3.2)
	public void deleteBcsInnerMerge(Request request, List list) {
		this.bcsInnerMergeLogic.deleteBcsInnerMerge(list);
	}

//	/**
//	 * 自动从报关常用物料中导入资料
//	 * 
//	 * @param request
//	 *            请求控制
//	 * @param materielType
//	 *            物料类型
//	 */
//	@AuthorityFunctionAnnotation(caption = "物料与报关对应表-新增物料", index = 3.3)
//	public void importInnerMergeDataFromMateriel(Request request,
//			String materielType) {
//		this.bcsInnerMergeLogic.importInnerMergeDataFromMateriel(materielType);
//	}

	/**
	 * 导入物料与报关对应表来自报关常用物料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielList
	 *            是Materiel型，报关常用物料
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--新增物料", index = 3.3)
	public List importInnerMergeDataFromMateriel(Request request,
			List materielList, String materielType) {
		return this.bcsInnerMergeLogic.importInnerMergeDataFromMateriel(
				materielList, materielType);
	}
	
	/**
	 * 导入物料与报关对应表来自通关手册料件与成品
	 *  商品编码 + 商品名称 + 商品规格 + 计量单位 为Key进行重复导入判断
	 * @param request 请求控制
	 * @param materielType 物料类型
	 */
	public Integer importInnerMergeDataFromContract(Request request,String materielType,BcsDictPorHead bcsDictPorHead){
		Integer size = this.bcsInnerMergeLogic.importInnerMergeDataFromContract(materielType,bcsDictPorHead);
		return size;
	}

	/**
	 * 自动归并,名称，规格，编码，单位相同
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--归并关系编辑删除", index = 3.2)
	public void bcsAutoMerge(Request request, String materielType) {
		this.bcsInnerMergeLogic.bcsAutoMerge(materielType);
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
	 * @param includeIsUsed
	 * 			包含已使用的物料
	 * @return 是Materiel型，报关常用物料
	 */
	@SuppressWarnings("rawtypes")
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--浏览", index = 3)
	public List findMaterielForBcsInnerMerge(Request request,
			String materielType, int index, int length, String property,
			Object value, Boolean isLike, Boolean includeIsUsed) {
		return this.bcsInnerMergeDao.findMaterielForBcsInnerMerge(materielType,
				index, length, property, value, isLike, includeIsUsed);
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
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--浏览", index = 3)
	public List findBcsInnerMerge(Request request, String materielType,
			int index, int length, String property, Object value, boolean isLike,boolean isNoMerge) {
		return bcsInnerMergeDao.findBcsInnerMerge(materielType, index, length,
				property, value, isLike,isNoMerge);
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
	public int getBcsInnerMergeCount(Request request, String materielType) {
		return bcsInnerMergeDao.getBcsInnerMergeCount(materielType);
	}

	/**
	 * 保存报关商品资料，并反写到物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BcsInnerMerge型，物料与报关对应表
	 * @param tempB
	 *            报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--归并关系编辑删除", index = 3.2)
	public List saveBcsInnerMerge(Request request, List list,
			BcsTenInnerMerge tempB) {
		bcsInnerMergeLogic.saveBcsInnerMerge(list, tempB);
		return list;
	}

	/**
	 * 取消物料与报关对应表里的10码归并
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BcsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--归并关系编辑删除", index = 3.2)
	public List<BcsInnerMerge> unDoTenInnerMerge(Request request, List<BcsInnerMerge> list) {
		bcsInnerMergeLogic.unDoTenInnerMerge(list);
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
	// @AuthorityFunctionAnnotation(caption = "报关商品资料-编辑", index = 2.1)
	// public List findBcsInnerMergeInContract(Request request, BcsTenInnerMerge
	// b) {
	// return bcsInnerMergeDao.findBcsInnerMergeInContract(b);
	// }

	/**
	 * 物料与报关对应表里十码重排
	 * 
	 * @param request
	 *            请求控制
	 * @param selectedRows
	 *            是BcsInnerMerge型，物料与报关对应表
	 * @param toNo
	 *            重排到的行号
	 */
	public void reSortMergeTenNo(Request request, List selectedRows, int toNo) {
		bcsInnerMergeLogic.reSortMergeTenNo2(selectedRows, toNo);
	}

	/**
	 * 保存报关商品资料，并反写到物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BcsInnerMerge型，物料与报关对应表
	 * @param tempB
	 *            报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--归并关系编辑删除", index = 3.2)
	public List updateBcsInnerMerge(Request request, List list,
			BcsTenInnerMerge tempB) {
		bcsInnerMergeLogic.updateBcsInnerMerge(list, tempB);
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
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料--浏览", index = 2)
	public List<BcsTenInnerMerge> findBcsTenInnerMerge(Request request, String materielType,
			int index, int length, String property, Object value, boolean isLike) {
		return this.bcsInnerMergeDao.findBcsTenInnerMerge(materielType, index,
				length, property, value, isLike);
	}
	
	
	/**
	 * 查找报关商品资料来自不同物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料--浏览", index = 2)
	public List<BcsTenInnerMerge> findBcsTenInnerMerge(Request request, String materielType) {
		return this.bcsInnerMergeDao.findBcsTenInnerMerge(materielType);
	}

	/**
	 * 保存报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bcsTenInnerMerge
	 *            报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料--编辑", index = 2.1)
	public BcsTenInnerMerge saveBcsTenInnerMerge(Request request,
			BcsTenInnerMerge bcsTenInnerMerge) {
		this.bcsInnerMergeDao.saveBcsTenInnerMerge2(bcsTenInnerMerge);
		return bcsTenInnerMerge;
	}

	/**
	 * 删除报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bcsTenInnerMerge
	 *            报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料--删除", index = 2.2)
	public void deleteBcsTenInnerMerge(Request request,
			BcsTenInnerMerge bcsTenInnerMerge) {
		this.bcsInnerMergeDao.deleteBcsTenInnerMerge(bcsTenInnerMerge);
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
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料--编辑", index = 2.1)
	public List saveBcsTenInnerMerge(Request request, List list,
			String materielType) {
		return this.bcsInnerMergeLogic.saveBcsTenInnerMerge(list, materielType);
	}

	/**
	 * 添加报关商品资料来自于商品编码
	 * 
	 * @param list
	 *            是Complex型，自用商品编码
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List importBcsTenInnerMergeFromComplex(Request request,List list, String materielType){
		return this.bcsInnerMergeLogic.importBcsTenInnerMergeFromComplex(list, materielType);
	}
	
	/**
	 * 保存工厂与物料对应表 同时反写物料物档与单据中心对应表中的单价，单重，单位折算系统
	 * 
	 * @param dim
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--归并关系编辑删除", index = 3.2)
	public Materiel saveBcsFactoryCustom(Request request, BcsInnerMerge dim) {
		return this.bcsInnerMergeLogic.saveBcsFactoryCustom(dim);
	}

	/**
	 * 删除报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BcsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料--删除", index = 2.2)
	public void deleteBcsTenInnerMerge(Request request, List list) {
		this.bcsInnerMergeLogic.deleteBcsTenInnerMerge(list);
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
	@AuthorityFunctionAnnotation(caption = "报关商品资料--浏览", index = 2)
	public int getMaxTenBcsInnerMergeNo(Request request, String materielType) {
		return this.bcsInnerMergeDao.getMaxTenBcsInnerMergeNo(materielType);
	}

	// /**
	// * 查找物料与报关对应表的十位编码是否被合同引用
	// *
	// * @param request
	// * 请求控制
	// * @param ls
	// * 是BcsInnerMerge型，物料与报关对应表
	// * @return List 是ContractImg型，合同备案料件
	// */
	// @AuthorityFunctionAnnotation(caption = "物料与报关对应表--浏览", index = 3)
	// public List findBcsInnerMergeInContract(Request request,
	// List<BcsInnerMerge> ls) {
	// return this.bcsInnerMergeDao.findBcsInnerMergeInContract(ls);
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
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--浏览", index = 3)
	public int getMaxBcsInnerMergeNo(Request request, String materielType) {
		return this.bcsInnerMergeDao.getMaxBcsInnerMergeNo(materielType);
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
	 * @return List 是bcsTenInnerMerge型，报关商品资料
	 */
	@AuthorityFunctionAnnotation(caption = "报关商品资料--浏览", index = 2)
	public List findBcsTenInnerMergeInMerge(Request request,
			String materielType, int index, int length, String property,
			Object value, boolean isLike) {
		return this.bcsInnerMergeDao.findBcsTenInnerMergeInMerge(materielType,
				index, length, property, value, isLike);
	}

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	@AuthorityFunctionAnnotation(caption = "物料与报关对应表--浏览", index = 3)
	public List findBcsInnerMergeByMerge(Request request, String materielType) {
		return this.bcsInnerMergeDao.findBcsInnerMergeByMerge(materielType);
	}

	/**
	 * 删除不在合同备案和物料与报关对应表里的报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BcsTenInnerMerge型，报关商品资料
	 * @param items
	 *            物料类型
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List deleteBcsTenInnerMergeForContract(Request request, List list,
			String items) {
		return this.bcsInnerMergeLogic.deleteBcsTenInnerMergeForContract(list,
				items);
	}

	/**
	 * 使内部归并所有商品都改为默认系数
	 */
	public void initBcsUnitDedault(Request request, String materielType) {
		this.bcsInnerMergeLogic.initUnitDedault(materielType);
	}

	/**
	 * 检查内部归并文档导入数据时,文本数据的正确性
	 * 
	 * @param request
	 * @param list
	 * @param ht
	 * @return
	 */
	public List checkFileData(Request request, List list, Hashtable ht) {
		return this.bcsImportFromOther.checkFileData(list, ht, request
				.getTaskId());
	}

	/**
	 * 导入文件来自文件
	 * 
	 * @param request 
	 * @param list 数据
	 * @param materielType 物料类型
	 * @param importMerge 导入对应关系或物料主档（是 导入对应关系，否 导入物料主档）
	 */
//	public void importDataFromTxtFile(Request request, List<BcsInnerMergeFileData> list, String materielType, Boolean importMerge) {
//		this.bcsImportFromOther
//				.importDataFromTxtFile(list, request.getTaskId(), materielType, importMerge);
	public void importDataFromTxtFile(Request request, List list, String materielType, Boolean importMerge,Boolean isUpdateMaterial) {
		this.bcsImportFromOther
					.importDataFromTxtFile(list, request.getTaskId(), materielType, importMerge,isUpdateMaterial);
	}

	/**
	 * 选择导入对应关系(old)或者导入工厂资料(new)
	 * 
	 * @param request 
	 * @param list 有多个list
	 * @param materielType 物料类型
	 * @param importMerge 导入对应关系或物料主档（是 导入对应关系，否 导入物料主档）
	 */
//	public void chooseOldImportOrNewImport(Request request, List list, String materielType, Boolean importMerge) {
//		this.bcsImportFromOther
//				.chooseOldImportOrNewImport(list, request.getTaskId(), materielType, importMerge);
//	}
	
	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	public List checkExistedMaterialPtNoInInnerMerge(Request request,
			String materielType,String ptNo) {
		return this.bcsInnerMergeDao
				.checkExistedMaterialPtNoInInnerMerge(materielType,ptNo);
	}

	/**
	 * 查询内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	public BcsInnerMergeDataOrder findBcsInnerMergeDataOrder(Request request) {
		return this.bcsInnerMergeDao.findBcsInnerMergeDataOrder();
	}

	/**
	 * 内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	public BcsInnerMergeDataOrder saveBcsInnerMergeDataOrder(Request request,
			BcsInnerMergeDataOrder bcsInnerMergeDataOrder) {
		this.bcsInnerMergeDao
				.saveBcsInnerMergeDataOrder(bcsInnerMergeDataOrder);
		return bcsInnerMergeDataOrder;
	}

	/**
	 * 根据成品查找报关单耗资料
	 */
	public List findCustomsBomDetailByExg(Request request, ContractExg exg) {
		return this.bcsInnerMergeDao.findCustomsBomDetailByExg(exg);
	}

	/**
	 * 转化报关Bom
	 */
	public List converBom(Request request, ContractExg exg, List list) {
		return this.bcsInnerMergeLogic.converBom(exg, list);
	}

	/**
	 * 查询不在报关单耗成品里的归并资料
	 * 
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomExg(Request request) {
		return this.bcsInnerMergeLogic.findInnerMergeNotInCustomsBomExg();
	}

	/**
	 * 根据归并增加报关单耗成品
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	public List addBcsCustomsBomExgFromInnerMerge(Request request,
			List lsTenInnerMerge) {
		return this.bcsInnerMergeLogic
				.addBcsCustomsBomExgFromInnerMerge(lsTenInnerMerge);
	}

	/**
	 * 删除报关单耗成品资料
	 * 
	 * @param dzscCustomsBomExg
	 */
	public void deleteBcsCustomsBomExg(Request request, List list) {
		// TODO Auto-generated method stub
		this.bcsInnerMergeLogic.deleteBcsCustomsBomExg(list);
	}

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "报关单耗--浏览", index = 5)
	public List findBcsCustomsBomExg(Request request) {
		return this.bcsInnerMergeDao.findBcsCustomsBomExg();
	}

	/**
	 * 查询报关单耗明细资料
	 * 
	 * @param dzscCustomsBomExg
	 * @return
	 */
	public List findBcsCustomsBomDetail(Request request,
			BcsCustomsBomExg bcsCustomsBomExg) {
		return this.bcsInnerMergeDao.findBcsCustomsBomDetail(bcsCustomsBomExg);
	}

	/**
	 * 根据报关单耗的成品资料抓取归并前的BOM成品以及版本资料
	 * 
	 * @param exg
	 * @return
	 */
	public List findMaterialExgByCustomsBomExg(Request request,
			BcsCustomsBomExg exg) {
		return this.bcsInnerMergeLogic.findMaterialExgByCustomsBomExg(exg);
	}

	/**
	 * 自动计算报关单耗
	 * 
	 * @param list
	 * @return
	 */
	public List autoCalDzscCustomsBom(Request request, List list,
			BcsCustomsBomExg exg, int bomStructureType) {
		return this.bcsInnerMergeLogic.autoCalBcsCustomsBom(list, exg, bomStructureType);
	}

	/**
	 * 保存报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	public BcsCustomsBomDetail saveBcsCustomsBomDetail(Request request,
			BcsCustomsBomDetail bom) {
		this.bcsInnerMergeDao.saveOrUpdate(bom);
		return bom;
	}
	/**
	 * 批量保存报关单耗明细资料
	 * 
	 * @param list 
	 */
	public void btachSaveBcsCustomsBomDetail(Request request,
			List list){
		this.bcsInnerMergeDao.batchSaveOrUpdate(list);
	}

	/**
	 * 删除报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	public void deleteBcsCustomsBomDetail(Request request, List list) {
		this.bcsInnerMergeLogic.deleteBcsCustomsBomDetail(list);
	}

//	/**
//	 * 根据物料号码,抓取未变更的归并数据
//	 * 
//	 * @param materialCode
//	 *            料号
//	 * @return DzscInnerMergeData 手册商品归并资料，返回符合突条件的第一条数据
//	 */
//	public List findInnerMergeDataPtNo(Request request) {
//		return this.bcsInnerMergeDao.findInnerMergeDataPtNo();
//	}

	/**
	 * 通过code查询海光商品编码
	 * 
	 * @param code商品编码代码
	 * @return 商品编码
	 */
	public Complex findComplexByCode(Request request, String sValue) {
		return this.bcsImportFromOther.findComplexByCode(sValue);
	}

	/**
	 * 根据归并增加报关单耗明细
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	public List addBcsCustomsBomDetailFromInnerMerge(Request request,
			BcsCustomsBomExg bcsCustomsBomExg, List lsTenInnerMerge) {
		return this.bcsInnerMergeLogic.addBcsCustomsBomDetailFromInnerMerge(
				bcsCustomsBomExg, lsTenInnerMerge);
	}

	/**
	 * 查询不在报关单耗明细里的归并资料
	 * 
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomDetail(Request request,
			BcsCustomsBomExg bcsCustomsBomExg) {
		return this.bcsInnerMergeLogic
				.findInnerMergeNotInCustomsBomDetail(bcsCustomsBomExg);
	}

	/**
	 * 保存自动计算的报关单耗
	 * 
	 * @param list
	 * @param exg
	 */
	public void saveAutoCalBcsCustomsBomDetail(Request request, List list,
			BcsCustomsBomExg exg) {
		this.bcsInnerMergeLogic.saveAutoCalBcsCustomsBomDetail(list, exg);
	}

	@Override
	public List<BcsInnerMerge> saveBcsInnerMergeIsCounts(Request request,
			List<BcsInnerMerge> bcsInnerMerges) {
		return bcsInnerMergeLogic.saveBcsInnerMergeIsCounts(bcsInnerMerges);
	}
	/**
	 * 根据料号、备案序号查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	public List checkExistedMaterialPtNoInInnerMerge(Request request,
			String materielType,String ptNo,Integer seqNum){
		return this.bcsInnerMergeDao
				.checkExistedMaterialPtNoInInnerMerge(materielType,ptNo,seqNum);
	}
	/**
	 * 查找BCS内部归并对应表对象
	 * @param request
	 * @param materialType
	 * @param strBcsInnerMergeKeys
	 * @return
	 */
	public List<Materiel> findBcsInnerMergeObject(Request request,String materialType,String strBcsInnerMergeKeys){
		return this.bcsInnerMergeDao.findBcsInnerMergeObject(materialType, strBcsInnerMergeKeys);
	}
	/**
	 * 保存根据料件新增和替换的数据
	 * @param request
	 * @param list
	 * @return
	 */
	public List saveBcsInnerMergeAddOrUpdate(Request request, List list) {
		this.bcsInnerMergeDao.saveBcsInnerMergeAddOrUpdate(list);
		return list;
	}
	
	/**
	 * 查找工厂物料主档对象
	 * @param request
	 * @param materialType
	 * @param strBcsInnerMergeKeys
	 * @return
	 */
	public List findBcsInnerMergeEnterpriseObject(Request request,String strBcsInnerMergeKeys){
		return this.bcsInnerMergeDao.findBcsInnerMergeEnterpriseObject(strBcsInnerMergeKeys);
	}



	@Override
	public void chooseOldImportOrNewImport(Request request, List list,
			String materielType, Boolean importMerge) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 查询备案资料库数量，用于“报关商品资料中的导入”
	 * @param request
	 * @return
	 */
	public List<BcsDictPorHead> findCountBcsDictPorHead(Request request){
		return this.bcsInnerMergeDao.findCountBcsDictPorHead();
	}
}