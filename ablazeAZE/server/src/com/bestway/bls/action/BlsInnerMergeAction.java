/*
 * Created on 2005-3-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.action;

import java.util.Hashtable;
import java.util.List;

import com.bestway.bls.entity.BlsInnerMerge;
import com.bestway.bls.entity.BlsInnerMergeDataOrder;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.common.Request;

/**
 * com.bestway.bls.blsinnermerge.action.BlsInnerMergeAction
 * 
 * 内部归并接口
 */

public interface BlsInnerMergeAction {
	/**
	 * 查找物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * 
	 */
	List findBlsInnerMerge(Request request);

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
	public List findBlsTenInnerMergeByInnerMerge(Request request, // String
																	// type,
			String tenCode, Integer seqNum);

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List findBlsTenInnerMergeByInnerMerge(Request request);

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List findBlsInnerMergeByTenInnerMerge(Request request,
			BlsTenInnerMerge blsTenInnerMerge);

	// /**
	// * 查找物料与报关对应表来自不同物料类型
	// *
	// * @param request
	// * 请求控制
	// * @param materielType
	// * 物料类型
	// * @return List 是BlsInnerMerge型，物料与报关对应表
	// */
	// List findBlsInnerMerge(Request request);

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param blsInnerMerge
	 *            物料与报关对应表
	 */
	BlsInnerMerge saveBlsInnerMerge(Request request, BlsInnerMerge blsInnerMerge);

	/**
	 * 删除物料与报关对应表里的数据
	 * 
	 * @param request
	 *            请求控制
	 * @param BlsInnerMerge
	 *            物料与报关对应表里的数据
	 */
	void deleteBlsInnerMerge(Request request, BlsInnerMerge BlsInnerMerge);

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
	List findComplexOutOfBlsTenInnerMerge(Request request, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            物料与报关对应表
	 */
	List saveBlsInnerMerge(Request request, List list);

	/**
	 * 删除物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是blsInnerMergeList型，物料与报关对应表里的数据
	 */
	void deleteBlsInnerMerge(Request request, List list);

	/**
	 * 自动从报关常用物料中导入资料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 */
	void importInnerMergeDataFromMateriel(Request request);// , String
															// materielType

	/**
	 * 自动归并,名称，规格，编码，单位相同
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 */
	void blsAutoMerge(Request request);// , String materielType

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
	List findMaterielForBlsInnerMerge(Request request, // String materielType,
			int index, int length, String property, Object value, Boolean isLike);

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
	List findBlsInnerMerge(Request request, int index, int length,
			String property, Object value, boolean isLike);// String
															// materielType,

	/**
	 * 获得物料与报关对应表里的个数
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return int 个数
	 */
	int getBlsInnerMergeCount(Request request, String materielType);

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
	List saveBlsInnerMerge(Request request, List list, BlsTenInnerMerge tempB);

	/**
	 * 取消物料与报关对应表里的10码归并
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BlsInnerMerge型，物料与报关对应表
	 */
	List unDoTenInnerMerge(Request request, List list,
			boolean isDeleteTenInnerMerge);

	// /**
	// * 查找物料与报关对应表的十位编码是否被合同引用
	// *
	// * @param request
	// * 请求控制
	// * @param b
	// * 报关商品资料
	// * @return List 是ContractImg型，合同备案料件
	// */
	// List findBlsInnerMergeInContract(Request request, BlsTenInnerMerge b);

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
	void reSortMergeTenNo(Request request, List selectedRows, int toNo);

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
	List importInnerMergeDataFromMateriel(Request request, List materielList);//

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
	List updateBlsInnerMerge(Request request, List list, BlsTenInnerMerge tempB);

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
	List findBlsTenInnerMerge(Request request, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 保存报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param blsTenInnerMerge
	 *            报关商品资料
	 */
	BlsTenInnerMerge saveBlsTenInnerMerge(Request request,
			BlsTenInnerMerge blsTenInnerMerge);

	/**
	 * 删除报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param blsTenInnerMerge
	 *            报关商品资料
	 */
	void deleteBlsTenInnerMerge(Request request,
			BlsTenInnerMerge blsTenInnerMerge);

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
	List saveBlsTenInnerMerge(Request request, List list);// , String
															// materielType

	/**
	 * 删除报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BlsTenInnerMerge型，报关商品资料
	 */
	void deleteBlsTenInnerMerge(Request request, List list);

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
	List deleteBlsTenInnerMergeForContract(Request request, List list);

	/**
	 * 查找物料与报关对应表来自不同物料类型,并且满足指定的报关商品编码。
	 * 
	 * @param materielType
	 *            物料类型
	 * @param tenCode
	 *            报关商品编码
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	List findBlsInnerMerge(Request request, String tenCode,// String type,
			Integer seqNum);

	/**
	 * 获得最大的报关商品资料序号
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return int 最大序号
	 */
	int getMaxTenBlsInnerMergeNo(Request request);// , String materielType

	// /**
	// * 查找物料与报关对应表的十位编码是否被合同引用
	// *
	// * @param request
	// * 请求控制
	// * @param ls
	// * 是BlsInnerMerge型，物料与报关对应表
	// * @return List 是ContractImg型，合同备案料件
	// */
	// List findBlsInnerMergeInContract(Request request, List<BlsInnerMerge>
	// ls);

	/**
	 * 获取最大报关商品资料的凭证序号，但是是物料与报关对应表里对应的报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return int 最大的凭证序号
	 */
	int getMaxBlsInnerMergeNo(Request request);// , String materielType

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
	List findBlsTenInnerMergeInMerge(Request request, String materielType,
			int index, int length, String property, Object value, boolean isLike);

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BlsInnerMerge型，物料与报关对应表
	 */
	public List findBlsInnerMergeByMerge(Request request, String materielType);

	/**
	 * 使内部归并所有商品都改为默认系数
	 */
	void initBlsUnitDedault(Request request);

	/**
	 * 检查内部归并文档导入数据时,文本数据的正确性
	 * 
	 * @param request
	 * @param list
	 * @param ht
	 * @return
	 */
	List checkFileData(Request request, List list, Hashtable ht);

	/**
	 * 导入文件来自文件
	 * 
	 * @param list
	 */
	void importDataFromTxtFile(Request request, List list);

	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	List findExistedMaterialPtNoInInnerMerge(Request request);

	/**
	 * 查询内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	BlsInnerMergeDataOrder findBlsInnerMergeDataOrder(Request request);

	/**
	 * 内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	BlsInnerMergeDataOrder saveBlsInnerMergeDataOrder(Request request,
			BlsInnerMergeDataOrder blsInnerMergeDataOrder);
}
