/*
 * Created on 2004-10-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.innermerge.action;

import java.util.Hashtable;
import java.util.List;

import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.dzsc.innermerge.entity.DzscCustomInnerMergeCondition;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomDetail;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomExg;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeDataOrder;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.dzsc.innermerge.entity.TempDzscAutoInnerMergeParam;
import com.bestway.dzsc.innermerge.entity.TempInnerMergeApplySelectParam;

/**
 * com.bestway.dzsc.dzscmanage.action.DzscAction
 * 
 * @author yp
 * 
 */
public interface DzscInnerMergeAction {
	/**
	 * 抓取全部归并记录（不包含变更的）
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	List findInnerMergeDataByType(Request request, String type, int index,
			int length, String property, Object value, boolean isLike);

	List findInnerMergedTenSeqNum(Request request, String meterialType);

	List findInnerMergedTenSeqNum(Request request, String meterialYype,
			String sFields, Object obj);

	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	List findExistedMaterialPtNoInInnerMerge(Request request,
			String materielType);

	/**
	 * 根据10码抓取归并前的资料
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	List findDzscInnerMergeData(Request request, DzscTenInnerMerge tenInnerMerge);

	/**
	 * 抓取变更的归并记录
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	List findChangedInnerMergeData(Request request, String type, int index,
			int length, String property, Object value, boolean isLike);

	/**
	 * 取得要备案的物料数据
	 * 
	 * @param materialType
	 * @return
	 */
	List[] findNotApplyInnerMergeData(Request request, String materialType);

	/**
	 * 抓取10码归并记录（不包含变更的）
	 * 
	 * @param type
	 *            物料类型
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	List findDzscExgTenInnerMerge(Request request, int index, int length,
			String property, Object value, boolean isLike);

	/**
	 * 保存手册归并数据变更记录
	 * 
	 * @param request
	 *            请求控制
	 * @param data
	 *            存手册归并数据
	 * @return data 存手册归并数据
	 */
	DzscInnerMergeData saveDzscInnerMergeData(Request request,
			DzscInnerMergeData data);

	/**
	 * 保存内部归并10码资料
	 * 
	 * @param list
	 */
	void saveDzscTenInnerMerge(Request request, List list);

	/**
	 * 检查所选择的数据能否进行10位归并 如果数据有效则并且归并后的10位商品编码全部为空返回0， 归并后的10位商品编码只要有一不为空返回1。；否则，
	 * 如果有编码不同的数据返回-1； 申报计 量单位不同返回-2； 商品名称不同返回-3； 如果全部归并的话 返回-4； 如果选择的数据的备
	 * 案序号不同返回-5。
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return int
	 */
	int checkDataForTenMerge(Request request, List list);

	/**
	 * 抓取10码的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	List findAllDzscTenInnerMerge(Request request, String imrType,
			boolean isChange);

	/**
	 * 抓取10码的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	List findAllDzscFourInnerMerge(Request request, String imrType,
			boolean isChange);

	/**
	 * 10位商品归并
	 * 
	 * @param list
	 * @param tenInnerMerge
	 * @param isChange
	 */
	List tenInnerMerge(Request request, List list,
			DzscTenInnerMerge tenInnerMerge, boolean isChange);

	/**
	 * 10位商品归并
	 * 
	 * @param list
	 * @param tenInnerMerge
	 * @param isChange
	 */
	List editTenInnerMerge(Request request, DzscTenInnerMerge tenInnerMerge,
			boolean isChange);

	// /**
	// * 新增10位商品归并。
	// *
	// * @param request
	// * 请求控制
	// * @param list
	// * 归并的数据（一笔或多笔）
	// * @param tenSeqNum
	// * 十位编码序号
	// * @param afterComplex
	// * 归并后的10位商品
	// * @param afterTenUnit
	// * 归并后的备案单位
	// * @param afterTenPtName
	// * 归并后的商品名称
	// * @param afterTenPtSpec
	// * 归并后的商品规格
	// * @param isNew
	// * 如果为true代表list中的数据重新归并到一新类型中， 如果为false，list中的数据归并到
	// * list中数据已有的类型中。
	// */
	// List tenInnerMerge(Request request, List list, Integer tenSeqNum,
	// Complex afterComplex, Unit afterTenUnit, String afterTenPtName,
	// String afterTenPtSpec, boolean isNew);

	/**
	 * 4位商品归并 10位商品编码的前4位相同，并且属于同一类商品。
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @param isNew
	 *            如果为true代表list中的数据重新归并到一新类型中， 如果为false，list中的数据归并到
	 *            list中数据已有的类型中。
	 */
	List fourInnerMerge(Request request, List list);

	/**
	 * 补充4码归并
	 * 
	 * @param list
	 * @param fourInnerMerge
	 * @return
	 */
	List addFourInnerMerge(Request request, List list,
			DzscFourInnerMerge fourInnerMerge);

	/**
	 * 申报商品归并资料
	 * 
	 * @param request
	 *            请求控制
	 */
	DeclareFileInfo applyInnerMerge(Request request, TempInnerMergeApplySelectParam param);

	/**
	 * 生效商品归并资料
	 * 
	 * @param request
	 *            请求控制
	 */
	String proccessDzscInnerMerge(Request request, List lsReturnFile);

	/**
	 * 变更商品归并资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，商品归并资料
	 * @return List 是DzscInnerMergeData型，商品归并资料(未变更的）
	 */
	List changeInnerMerge(Request request, List list);

	// /**
	// * 抓取通关备案料件基本资料（不在电子手册归并存在的）
	// *
	// * @param request
	// * 请求控制
	// * @param type
	// * 物料类型
	// * @return List 是materiel型，电子手册的物料基本资料
	// */
	// List findMaterielForDzscInnerMerge(Request request, String type);

	/**
	 * 增加归并数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是materiel型，电子手册的物料基本资料
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	List addInnerMergeData(Request request, List list, boolean flag);

	/**
	 * 自动从物料主档中导入资料
	 * 
	 * @param request
	 *            请求控制
	 * @param imrType
	 *            物料类型
	 * @return 返回空的ArrayList()
	 */
	void importInnerMergeDataFromMateriel(Request request, List lsMaterialType,
			boolean flag);

	// void deleteDzscInnerMergeHead(Request request);
	//
	// void inure(Request request);

	/**
	 * 查找归并数据中的最大的seqNum
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @param seqNum
	 *            所要查找的编码序号
	 * @return int 最大十位编码序号
	 */
	int findMaxInnerMergeNo(Request request, String type, String seqNum);

	/**
	 * 判断是否已经归并
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return boolean true表示已经归并
	 */
	boolean isInnerMerger(Request request, List list);

	/**
	 * 删除手册归并数据
	 * 
	 * @param request
	 *            请求控制
	 * @param data
	 *            归并数据
	 */
	void deleteDzscInnerMergeData(Request request, List list);

	/**
	 * 删除手册归并数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	void deleInnerMergerForMateriel(Request request, List list);

	/**
	 * 删除物料的内部归并，并且设置这些物料不再用于内部归并
	 * 
	 * @param list
	 */
	void forbidInnerMergeForMateriel(Request request, List list);

	/**
	 * 转抄归并数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	void copyInnerMergerForMateriel(Request request, List list);

	/**
	 * 撤消10位归并
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	List unDoTenInnerMerge(Request request, List list);

	/**
	 * 检查能否进行撤消10位归并动作。 如果允许撤消返回0，否则如果数据已做过四位归并则返回-1，不能撤消。
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return int 允许撤消返回0，否则如果数据已做过四位归并则返回-1，不能撤消
	 */
	int checkDataForUndoTenInnerMerge(Request request, List list);

	/**
	 * 撤消4位商品归并。
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return list 是DzscInnerMergeData型，归并数据
	 */
	List undoFourInnerMerge(Request request, List list);

	// /**
	// * 补充四码
	// *
	// * @param request
	// * 请求控制
	// * @param list
	// * 是DzscInnerMergeData型，归并数据
	// * @return list 是DzscInnerMergeData型，归并数据
	// */
	// List addfourInner(Request request, List list);

	/**
	 * 四码修改
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @param fourSeqNum
	 *            四位编码序号<第三层>
	 * @param afterMemoUnit
	 *            是Unit型，四位商品单位
	 * @param fourName
	 *            四位商品名称
	 * @param fourSpec
	 *            四位商品规格
	 */
	List editFourInnerMerge(Request request, DzscFourInnerMerge fourInnerMerge);

	/**
	 * 抓取不在正在执行的归并记录
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	List findInnerMergeDataAndChangeByType(Request request, String type);

	/**
	 * 抓取未变更的归并记录 根据物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	List findInnerMergeDataByTypeForPrint(Request request, String type);

	/**
	 * 抓取电子手册的物料基本资料（不在电子手册归并存在的）
	 * 
	 * @param request
	 *            请求控制
	 * @param materielType
	 *            物料类别属性
	 * @param index
	 *            数据的开始下表
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            查找时用“Like”还是“＝”
	 * @return List 是materiel型，电子手册的物料基本资料
	 */
	List findMaterielForDzscInnerMerge(Request request, String materielType,
			int index, int length, String property, Object value, Boolean isLike);

	/**
	 * 查找电子手册自定义归并条件
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 是DzscCustomInnerMergeCondition型，电子手册自定义归并条件
	 */
	List findDzscCustomInnerMergeConditionByType(Request request, String type);

	/**
	 * 查找电子手册自定义归并条件
	 * 
	 * @param request
	 *            请求控制
	 */
	List findDzscCustomInnerMergeCondition(Request request);

	/**
	 * 手册电子手册自定义归并条件
	 * 
	 * @param request
	 *            请求控制
	 * @param condition
	 *            电子手册自定义归并条件
	 */
	void deleteDzscCustomInnerMergeCondition(Request request,
			DzscCustomInnerMergeCondition condition);

	/**
	 * 归并全部资料
	 * 
	 * @param request
	 *            请求控制
	 */
	void dzscCustomInnerMerge(Request request);

	/**
	 * 保存电子手册自定义归并条件
	 * 
	 * @param request
	 *            请求控制
	 * @param condition
	 *            电子手册自定义归并条件
	 */
	void saveDzscCustomInnerMergeCondition(Request request,
			DzscCustomInnerMergeCondition condition);

	/**
	 * 归并数据
	 * 
	 * @param request
	 *            请求控制
	 */
	void dzscAutoInnerMergeData(Request request,
			TempDzscAutoInnerMergeParam param);

	void saveDzscInnerMergeDataInFactoryAndCustoms(Request request,
			DzscInnerMergeData dzscInnerMergeData);

	/**
	 * 使内部归并所有商品都改为默认系数
	 */
	void initDzscUnitDedault(Request request);

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

	List findDzscReverseMergeFourData(Request request, String materielType);

	void convertOldDataToNewData(Request request);

	/**
	 * 查询所有未归并的资料
	 * 
	 * @param materielType
	 * @param isChange
	 * @return
	 */
	List findDzscNotMergeInnerMergeData(Request request, String materielType,
			boolean isChange);

	/**
	 * 删除所有未归并的资料
	 * 
	 * @param materielType
	 * @param isChange
	 * @return
	 */
	void deleteDzscNotMergeInnerMergeData(Request request, String materielType,
			boolean isChange);

	/**
	 * 查询内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	DzscInnerMergeDataOrder findDzscInnerMergeDataOrder(Request request);

	/**
	 * 保存内部归并导入文件栏位对应次序
	 * 
	 * @param order
	 */
	DzscInnerMergeDataOrder saveDzscInnerMergeDataOrder(Request request,
			DzscInnerMergeDataOrder order);

	/**
	 * 特殊处理，将归并关系从正在生效状态回滚到原始状态
	 * 
	 * @param list
	 */
	List resetExecuteToOriginal(Request request, List list);

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	List findDzscCustomsBomExg(Request request);

	/**
	 * 查询报关单耗明细资料
	 * 
	 * @param dzscCustomsBomExg
	 * @return
	 */
	List findDzscCustomsBomDetail(Request request,
			DzscCustomsBomExg dzscCustomsBomExg);

	/**
	 * 查询不在报关单耗成品里的归并资料
	 * 
	 * @return
	 */
	List findInnerMergeNotInCustomsBomExg(Request request);

	/**
	 * 查询不在报关单耗明细里的归并资料
	 * 
	 * @return
	 */
	List findInnerMergeNotInCustomsBomDetail(Request request,
			DzscCustomsBomExg dzscCustomsBomExg);

	/**
	 * 根据归并增加报关单耗成品
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	List addDzscCustomsBomExgFromInnerMerge(Request request,
			List lsTenInnerMerge);

	/**
	 * 根据归并增加报关单耗明细
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	List addDzscCustomsBomDetailFromInnerMerge(Request request,
			DzscCustomsBomExg dzscCustomsBomExg, List lsTenInnerMerge);

	/**
	 * 保存报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	DzscCustomsBomDetail saveDzscCustomsBomDetail(Request request,
			DzscCustomsBomDetail dzscCustomsBomDetail);

	/**
	 * 删除报关单耗成品资料
	 * 
	 * @param dzscCustomsBomExg
	 */
	void deleteDzscCustomsBomExg(Request request, List list);

	/**
	 * 删除报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	void deleteDzscCustomsBomDetail(Request request, List list);

	/**
	 * 根据报关单耗的成品资料抓取归并前的BOM成品以及版本资料
	 * 
	 * @param exg
	 * @return
	 */
	List findMaterialExgByCustomsBomExg(Request request, DzscCustomsBomExg exg);

	/**
	 * 自动计算报关单耗
	 * 
	 * @param list
	 * @return
	 */
	List autoCalDzscCustomsBom(Request request, List list, DzscCustomsBomExg exg);

	/**
	 * 保存自动计算的报关单耗
	 * 
	 * @param list
	 * @param exg
	 */
	void saveAutoCalDzscCustomsBomDetail(Request request, List list,
			DzscCustomsBomExg exg);

	void checkfindInnerMergedTenSeqNum(Request request);

	void checkFmDzscExgTenInnerMerge(Request request);
}