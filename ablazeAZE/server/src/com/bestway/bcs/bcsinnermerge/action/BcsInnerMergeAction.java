/*
 * Created on 2005-3-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.bcsinnermerge.action;

import java.util.Hashtable;
import java.util.List;

import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomExg;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeDataOrder;
import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMergeFileData;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.Materiel;

/**
 * 
 * com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction
 * 
 * @author ls checked by 陈井彬 date 2008.11.29 内部归并接口
 */

public interface BcsInnerMergeAction {
	/**
	 * 查找物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * 
	 */
	List<BcsInnerMerge> findBcsInnerMerge(Request request);
	
	/**
	 * 查找物料与报关对应表keys
	 * key=materiel.ptNo/bcsTenInnerMerge.seqNum
	 * @param request
	 * @param meterialType
	 * @return 
	 */
	List<String> findBcsInnerMergeKeys(Request request, String meterialType);


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
	List<BcsInnerMerge> findBcsTenInnerMergeByInnerMerge(Request request, String type,
			String tenCode, Integer seqNum);

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List findBcsTenInnerMergeByInnerMerge(Request request,
			String materielType);

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List<BcsInnerMerge> findBcsInnerMergeByTenInnerMerge(Request request,
			BcsTenInnerMerge bcsTenInnerMerge);

	/**
	 * 查找物料与报关对应表来自不同物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	List<BcsInnerMerge> findBcsInnerMerge(Request request, String materielType);
	
	/**
	 * 查找物料与报关对应表来自不同物料类型(物料一对多的对应关系)
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	List<BcsInnerMerge> findBcsInnerMergeOneToMany(Request request, String materielType);

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param bcsInnerMerge
	 *            物料与报关对应表
	 */
	BcsInnerMerge saveBcsInnerMerge(Request request, BcsInnerMerge bcsInnerMerge);

	/**
	 * 删除物料与报关对应表里的数据
	 * 
	 * @param request
	 *            请求控制
	 * @param BcsInnerMerge
	 *            物料与报关对应表里的数据
	 */
	void deleteBcsInnerMerge(Request request, BcsInnerMerge BcsInnerMerge);

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
	List findComplexOutOfBcsTenInnerMerge(Request request, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 保存物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            物料与报关对应表
	 */
	List saveBcsInnerMerge(Request request, List list);

	/**
	 * 删除物料与报关对应表
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是bcsInnerMergeList型，物料与报关对应表里的数据
	 */
	void deleteBcsInnerMerge(Request request, List list);

//	/**
//	 * 自动从报关常用物料中导入资料
//	 * 
//	 * @param request
//	 *            请求控制
//	 * @param materielType
//	 *            物料类型
//	 */
//	void importInnerMergeDataFromMateriel(Request request, String materielType);

	/**
	 * 自动归并,名称，规格，编码，单位相同
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 */
	void bcsAutoMerge(Request request, String materielType);

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
	List findMaterielForBcsInnerMerge(Request request, String materielType,
			int index, int length, String property, Object value, Boolean isLike, Boolean includeIsUsed);

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
	List findBcsInnerMerge(Request request, String materielType, int index,
			int length, String property, Object value, boolean isLike,boolean isNoMerge);

	/**
	 * 获得物料与报关对应表里的个数
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return int 个数
	 */
	int getBcsInnerMergeCount(Request request, String materielType);

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
	List saveBcsInnerMerge(Request request, List list, BcsTenInnerMerge tempB);

	/**
	 * 取消物料与报关对应表里的10码归并
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BcsInnerMerge型，物料与报关对应表
	 */
	List<BcsInnerMerge> unDoTenInnerMerge(Request request, List<BcsInnerMerge> list);

	// /**
	// * 查找物料与报关对应表的十位编码是否被合同引用
	// *
	// * @param request
	// * 请求控制
	// * @param b
	// * 报关商品资料
	// * @return List 是ContractImg型，合同备案料件
	// */
	// List findBcsInnerMergeInContract(Request request, BcsTenInnerMerge b);

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
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	List importInnerMergeDataFromMateriel(Request request, List materielList,
			String materielType);

	
	/**
	 * 导入物料与报关对应表来自通关手册料件与成品
	 *  商品编码 + 商品名称 + 商品规格 + 计量单位 为Key进行重复导入判断
	 * @param request 请求控制
	 * @param materielType 物料类型
	 */
	Integer importInnerMergeDataFromContract(Request request,String materielType,BcsDictPorHead bcsDictPorHead);
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
	List updateBcsInnerMerge(Request request, List list, BcsTenInnerMerge tempB);

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
	List<BcsTenInnerMerge> findBcsTenInnerMerge(Request request, String materielType, int index,
			int length, String property, Object value, boolean isLike);
	/**
	 * 查找报关商品资料来自不同物料类型
	 * 
	 * @param request
	 * @param materielType
	 * @return
	 */
	List<BcsTenInnerMerge> findBcsTenInnerMerge(Request request, String materielType);

	/**
	 * 保存报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bcsTenInnerMerge
	 *            报关商品资料
	 */
	BcsTenInnerMerge saveBcsTenInnerMerge(Request request,
			BcsTenInnerMerge bcsTenInnerMerge);

	/**
	 * 删除报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bcsTenInnerMerge
	 *            报关商品资料
	 */
	void deleteBcsTenInnerMerge(Request request,
			BcsTenInnerMerge bcsTenInnerMerge);

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
	List saveBcsTenInnerMerge(Request request, List list, String materielType);
	
	/**
	 * 添加报关商品资料来自于商品编码
	 * 
	 * @param list
	 *            是Complex型，自用商品编码
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List importBcsTenInnerMergeFromComplex(Request request,List list, String materielType);

	/**
	 * 删除报关商品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是BcsTenInnerMerge型，报关商品资料
	 */
	void deleteBcsTenInnerMerge(Request request, List list);

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
	List deleteBcsTenInnerMergeForContract(Request request, List list,
			String items);

	/**
	 * 查找物料与报关对应表来自不同物料类型,并且满足指定的报关商品编码。
	 * 
	 * @param materielType
	 *            物料类型
	 * @param tenCode
	 *            报关商品编码
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	List findBcsInnerMerge(Request request, String type, String tenCode,
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
	int getMaxTenBcsInnerMergeNo(Request request, String materielType);

	// /**
	// * 查找物料与报关对应表的十位编码是否被合同引用
	// *
	// * @param request
	// * 请求控制
	// * @param ls
	// * 是BcsInnerMerge型，物料与报关对应表
	// * @return List 是ContractImg型，合同备案料件
	// */
	// List findBcsInnerMergeInContract(Request request, List<BcsInnerMerge>
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
	int getMaxBcsInnerMergeNo(Request request, String materielType);

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
	List findBcsTenInnerMergeInMerge(Request request, String materielType,
			int index, int length, String property, Object value, boolean isLike);

	/**
	 * 查找物料与报关对应表来自不同物料类型(并且有归并的数据)
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类型
	 * @return List 是BcsInnerMerge型，物料与报关对应表
	 */
	public List findBcsInnerMergeByMerge(Request request, String materielType);

	/**
	 * 使内部归并所有商品都改为默认系数
	 */
	void initBcsUnitDedault(Request request, String materielType);

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
	 * @param request 
	 * @param list 数据
	 * @param materielType 物料类型
	 * @param importMerge 导入对应关系或物料主档（是 导入对应关系，否 导入物料主档）
	 */
//	void importDataFromTxtFile(Request request, List<BcsInnerMergeFileData> list, String materielType, Boolean importMerge);
	void importDataFromTxtFile(Request request, List list, String materielType, Boolean importMerge,Boolean isUpdateMaterial);
	
	/**
	 *选择导入对应关系(old)或者导入工厂资料(new)
	 * 
	 * @param request 
	 * @param list 有多个list
	 * @param materielType 物料类型
	 * @param importMerge 导入对应关系或物料主档（是 导入对应关系，否 导入物料主档）
	 */
	void chooseOldImportOrNewImport(Request request, List list, String materielType, Boolean importMerge);


	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	List checkExistedMaterialPtNoInInnerMerge(Request request,
			String materielType,String ptNo);
	/**
	 * 根据料号、备案序号查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	List checkExistedMaterialPtNoInInnerMerge(Request request,
			String materielType,String ptNo,Integer seqNum);

//	/**
//	 * 根据物料号码,抓取未变更的归并数据
//	 * 
//	 * @param materialCode
//	 *            料号
//	 * @return DzscInnerMergeData 手册商品归并资料，返回符合突条件的第一条数据
//	 */
//	List findInnerMergeDataPtNo(Request request);

	/**
	 * 查询内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	BcsInnerMergeDataOrder findBcsInnerMergeDataOrder(Request request);

	/**
	 * 内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	BcsInnerMergeDataOrder saveBcsInnerMergeDataOrder(Request request,
			BcsInnerMergeDataOrder bcsInnerMergeDataOrder);

	/**
	 * 根据成品查找报关单耗资料
	 */
	public List findCustomsBomDetailByExg(Request request, ContractExg exg);

	/**
	 * 转化报关Bom
	 */
	public List converBom(Request request, ContractExg exg, List list);

	/**
	 * 查询不在报关单耗成品里的归并资料
	 * 
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomExg(Request request);

	/**
	 * 根据归并增加报关单耗成品
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	List addBcsCustomsBomExgFromInnerMerge(Request request, List list);

	/**
	 * 删除报关单耗成品资料
	 * 
	 * @param dzscCustomsBomExg
	 */
	void deleteBcsCustomsBomExg(Request request, List list);

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	public List findBcsCustomsBomExg(Request request);

	/**
	 * 查询报关单耗明细资料
	 * 
	 * @param dzscCustomsBomExg
	 * @return
	 */
	public List findBcsCustomsBomDetail(Request request, BcsCustomsBomExg exg);

	/**
	 * 根据报关单耗的成品资料抓取归并前的BOM成品以及版本资料
	 * 
	 * @param exg
	 * @return
	 */
	public List findMaterialExgByCustomsBomExg(Request request,
			BcsCustomsBomExg exg);

	/**
	 * 自动计算报关单耗
	 * 
	 * @param list
	 * @return
	 */
	public List autoCalDzscCustomsBom(Request request, List lsExg,
			BcsCustomsBomExg exgBill, int bomStructureType);

	/**
	 * 保存报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	public BcsCustomsBomDetail saveBcsCustomsBomDetail(Request request,
			BcsCustomsBomDetail bom);
	/**
	 * 批量保存报关单耗明细资料
	 * 
	 * @param list 
	 */
	public void btachSaveBcsCustomsBomDetail(Request request,
			List list);

	/**
	 * 删除报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	public void deleteBcsCustomsBomDetail(Request request, List list);

	/**
	 * 根据归并增加报关单耗明细
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	List addBcsCustomsBomDetailFromInnerMerge(Request request,
			BcsCustomsBomExg exg, List list);

	/**
	 * 查询不在报关单耗明细里的归并资料
	 * 
	 * @return
	 */
	List findInnerMergeNotInCustomsBomDetail(Request request,
			BcsCustomsBomExg exg);

	/**
	 * 保存自动计算的报关单耗
	 * 
	 * @param list
	 * @param exg
	 */
	void saveAutoCalBcsCustomsBomDetail(Request request, List list,
			BcsCustomsBomExg exg);

	/**
	 * 保存工厂与物料对应表 同时反写物料物档与单据中心对应表中的单价，单重，单位折算系统
	 * 
	 * @param dim
	 */
	Materiel saveBcsFactoryCustom(Request request, BcsInnerMerge dim);

	/**
	 * 通过code查询海光商品编码
	 * 
	 * @param code商品编码代码
	 * @return 商品编码
	 */
	Complex findComplexByCode(Request request, String sValue);
	/**
	 * 通过归并序号抓取内部归并表
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findBcsInnerMergeDataByTenSeqNum(Request request,Integer tenSeqNum,
			String materielType);
	/**
	 * 通过归并序号抓取报关常用资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findBcsTenInnerMergeDataBySeqNum(Request request,Integer tenSeqNum,
			String materielType);
	
	/**
	 * 设置 bcsInnerMerges 为统计使用
	 * @param request
	 * @param bcsInnerMerges
	 * @return
	 */
	List<BcsInnerMerge> saveBcsInnerMergeIsCounts(Request request, List<BcsInnerMerge> bcsInnerMerges);
	
	/**
	 * 查找BCS内部归并对应表对象
	 * @param request
	 * @param materialType
	 * @param strBcsInnerMergeKeys
	 * @return
	 */
	List<Materiel> findBcsInnerMergeObject(Request request,String materialType,String strBcsInnerMergeKeys);
	
	/**
	 * 保存料件新增或替换的数据
	 * @param request
	 * @param list
	 * @return
	 */
	List saveBcsInnerMergeAddOrUpdate(Request request, List list);
	
	/**
	 * 查找工厂物料主档对象
	 * @param request
	 * @param materialType
	 * @param strBcsInnerMergeKeys
	 * @return
	 */
	List<EnterpriseMaterial> findBcsInnerMergeEnterpriseObject(Request request,String strBcsInnerMergeKeys);

	/**
	 * 查询备案资料库数量，用于“报关商品资料中的导入”
	 * @param request
	 * @return
	 */
	List<BcsDictPorHead> findCountBcsDictPorHead(Request request);
}
