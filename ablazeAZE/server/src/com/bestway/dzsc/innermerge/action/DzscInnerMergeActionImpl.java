/*
 * Created on 2004-6-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.innermerge.action;

import java.util.Hashtable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.dzsc.innermerge.dao.DzscInnerMergeDao;
import com.bestway.dzsc.innermerge.entity.DzscCustomInnerMergeCondition;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomDetail;
import com.bestway.dzsc.innermerge.entity.DzscCustomsBomExg;
import com.bestway.dzsc.innermerge.entity.DzscFourInnerMerge;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeDataOrder;
import com.bestway.dzsc.innermerge.entity.DzscTenInnerMerge;
import com.bestway.dzsc.innermerge.entity.TempDzscAutoInnerMergeParam;
import com.bestway.dzsc.innermerge.entity.TempInnerMergeApplySelectParam;
import com.bestway.dzsc.innermerge.logic.DzscAutoInnerMerge;
import com.bestway.dzsc.innermerge.logic.DzscCustomInnerMerge;
import com.bestway.dzsc.innermerge.logic.DzscHandInnerLogic;
import com.bestway.dzsc.innermerge.logic.DzscImportFromOther;
import com.bestway.dzsc.innermerge.logic.DzscInnerMergeLogic;

/**
 * com.bestway.dzsc.dzscmanage.action.DzscActionImpl
 * 
 * @author bsway
 * 
 */
//内部归并管理
@AuthorityClassAnnotation(caption = "电子手册", index = 7)
public class DzscInnerMergeActionImpl extends BaseActionImpl implements
		DzscInnerMergeAction {
	private DzscInnerMergeDao dzscInnerMergeDao = null;

	private DzscInnerMergeLogic dzscInnerMergeLogic = null;

	private DzscHandInnerLogic dzscHandInnerLogic = null;

	private DzscImportFromOther dzscImportFromOther = null;

	private DzscCustomInnerMerge dzscCustomInnerMerge = null;

	private DzscAutoInnerMerge dzscAutoInnerMerge = null;

	/**
	 * 获取dzscHandInnerLogic
	 * 
	 * @return dzscHandInnerLogic
	 */
	public DzscHandInnerLogic getDzscHandInnerLogic() {
		return dzscHandInnerLogic;
	}

	/**
	 * 设置dzscHandInnerLogic
	 * 
	 * @param dzscHandInnerLogic
	 */
	public void setDzscHandInnerLogic(DzscHandInnerLogic dzscHandInnerLogic) {
		this.dzscHandInnerLogic = dzscHandInnerLogic;
	}

	public DzscImportFromOther getDzscImportFromOther() {
		return dzscImportFromOther;
	}

	public void setDzscImportFromOther(DzscImportFromOther dzscImportFromOther) {
		this.dzscImportFromOther = dzscImportFromOther;
	}

	public DzscAutoInnerMerge getDzscAutoInnerMerge() {
		return dzscAutoInnerMerge;
	}

	public void setDzscAutoInnerMerge(DzscAutoInnerMerge dzscAutoInnerMerge) {
		this.dzscAutoInnerMerge = dzscAutoInnerMerge;
	}

	public DzscCustomInnerMerge getDzscCustomInnerMerge() {
		return dzscCustomInnerMerge;
	}

	public void setDzscCustomInnerMerge(
			DzscCustomInnerMerge dzscCustomInnerMerge) {
		this.dzscCustomInnerMerge = dzscCustomInnerMerge;
	}

	public DzscInnerMergeDao getDzscInnerMergeDao() {
		return dzscInnerMergeDao;
	}

	public void setDzscInnerMergeDao(DzscInnerMergeDao dzscInnerMergeDao) {
		this.dzscInnerMergeDao = dzscInnerMergeDao;
	}

	public DzscInnerMergeLogic getDzscInnerMergeLogic() {
		return dzscInnerMergeLogic;
	}

	public void setDzscInnerMergeLogic(DzscInnerMergeLogic dzscInnerMergeLogic) {
		this.dzscInnerMergeLogic = dzscInnerMergeLogic;
	}

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
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-浏览", index = 2.1)
	public List findInnerMergeDataByType(Request request, String type,
			int index, int length, String property, Object value, boolean isLike) {
		return this.dzscInnerMergeLogic.findInnerMergeDataByType(type, index,
				length, property, value, isLike);
	}

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
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-浏览", index = 2.1)
	public List findChangedInnerMergeData(Request request, String type,
			int index, int length, String property, Object value, boolean isLike) {
		return this.dzscInnerMergeDao.findChangedInnerMergeData(type, index,
				length, property, value, isLike);
	}

	/**
	 * 查询已经存在归并的物料号码
	 * 
	 * @param materielType
	 * @return
	 */
	public List findExistedMaterialPtNoInInnerMerge(Request request,
			String materielType) {
		return this.dzscInnerMergeDao
				.findExistedMaterialPtNoInInnerMerge(materielType);
	}

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
	public List findDzscExgTenInnerMerge(Request request, int index,
			int length, String property, Object value, boolean isLike) {
		return this.dzscInnerMergeDao.findDzscExgTenInnerMerge(index, length,
				property, value, isLike);
	}

	/**
	 * 取得要备案的物料数据
	 * 
	 * @param materialType
	 * @return
	 */
	public List[] findNotApplyInnerMergeData(Request request,
			String materialType) {
		return this.dzscInnerMergeLogic
				.findNotApplyInnerMergeData(materialType);
	}

	/**
	 * 保存内部归并10码资料
	 * 
	 * @param list
	 */
	public void saveDzscTenInnerMerge(Request request, List list) {
		this.dzscInnerMergeLogic.saveDzscTenInnerMerge(list);
	}

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
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-归并操作", index = 2.1)
	public int checkDataForTenMerge(Request request, List list) {
		return this.dzscHandInnerLogic.checkDataForTenMerge(list);
	}

	/**
	 * 抓取10码的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findAllDzscTenInnerMerge(Request request, String imrType,
			boolean isChange) {
		return this.dzscHandInnerLogic.findAllDzscTenInnerMerge(imrType,
				isChange);
	}

	/**
	 * 抓取4码的资料
	 * 
	 * @param fourInnerMerge
	 * @return
	 */
	public List findAllDzscFourInnerMerge(Request request, String imrType,
			boolean isChange) {
		return this.dzscHandInnerLogic.findAllDzscFourInnerMerge(imrType,
				isChange);
	}

	/**
	 * 10位商品归并
	 * 
	 * @param list
	 * @param tenInnerMerge
	 * @param isChange
	 */
	public List tenInnerMerge(Request request, List list,
			DzscTenInnerMerge tenInnerMerge, boolean isChange) {
		this.dzscHandInnerLogic.tenInnerMerge(list, tenInnerMerge, isChange);
		return list;
	}

	/**
	 * 10位商品归并
	 * 
	 * @param list
	 * @param tenInnerMerge
	 * @param isChange
	 */
	public List editTenInnerMerge(Request request,
			DzscTenInnerMerge tenInnerMerge, boolean isChange) {
		return this.dzscHandInnerLogic.editTenInnerMerge(tenInnerMerge,
				isChange);
	}

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
	// @AuthorityFunctionAnnotation(caption = "手册商品归并-归并操作", index = 1.3)
	// public List tenInnerMerge(Request request, List list, Integer tenSeqNum,
	// Complex afterComplex, Unit afterTenUnit, String afterTenPtName,
	// String afterTenPtSpec, boolean isNew) {
	// this.dzscHandInnerLogic.tenInnerMerge(list, tenSeqNum, afterComplex,
	// afterTenUnit, afterTenPtName, afterTenPtSpec, isNew);
	// return list;
	// }

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
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-归并操作", index = 2.1)
	public List fourInnerMerge(Request request, List list) {
		this.dzscHandInnerLogic.fourInnerMerge(list);
		return list;
	}

	/**
	 * 补充4码归并
	 * 
	 * @param list
	 * @param fourInnerMerge
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-归并操作", index = 2.1)
	public List addFourInnerMerge(Request request, List list,
			DzscFourInnerMerge fourInnerMerge) {
		this.dzscHandInnerLogic.addFourInnerMerge(list, fourInnerMerge);
		return list;
	}

	/**
	 * 保存手册归并数据变更记录
	 * 
	 * @param request
	 *            请求控制
	 * @param data
	 *            存手册归并数据
	 * @return data 存手册归并数据
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-编辑", index = 2.1)
	public DzscInnerMergeData saveDzscInnerMergeData(Request request,
			DzscInnerMergeData data) throws DataAccessException {
		dzscInnerMergeDao.saveDzscInnerMergeData(data);
		return data;
	}

	public void saveDzscInnerMergeDataInFactoryAndCustoms(Request request,
			DzscInnerMergeData dzscInnerMergeData) {
		this.dzscInnerMergeLogic
				.saveDzscInnerMergeDataInFactoryAndCustoms(dzscInnerMergeData);

	}

	/**
	 * 申报商品归并资料
	 * 
	 * @param request
	 *            请求控制
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-申报", index = 2.1)
	public DeclareFileInfo applyInnerMerge(Request request,
			TempInnerMergeApplySelectParam param) {
		return this.dzscInnerMergeLogic.applyInnerMerge(request.getTaskId(),
				param);
	}

	/**
	 * 生效商品归并资料
	 * 
	 * @param request
	 *            请求控制
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-生效", index = 2.1)
	public String proccessDzscInnerMerge(Request request, List lsReturnFile) {
		return this.dzscInnerMergeLogic.proccessDzscInnerMerge(lsReturnFile);
	}

	/**
	 * 变更商品归并资料
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，商品归并资料
	 * @return List 是DzscInnerMergeData型，商品归并资料(未变更的）
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-编辑", index = 2.1)
	public List changeInnerMerge(Request request, List list) { // 归并变更
		return this.dzscInnerMergeLogic.changeInnerMerge(list);
	}

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
	public int findMaxInnerMergeNo(Request request, String type, String seqNum) {
		return this.dzscInnerMergeDao.findMaxInnerMergeNo(type, seqNum);
	}

	/**
	 * 判断是否已经归并
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return boolean true表示已经归并
	 */
	public boolean isInnerMerger(Request request, List list) {
		return this.dzscHandInnerLogic.isInnerMerger(list);
	}

	// /**
	// * 抓取通关备案料件基本资料（不在电子手册归并存在的）
	// *
	// * @param request
	// * 请求控制
	// * @param type
	// * 物料类型
	// * @return List 是materiel型，电子手册的物料基本资料
	// */
	// public List findMaterielForDzscInnerMerge(Request request, String type) {
	// return this.dzscInnerMergeDao.findMaterielForDzscInnerMerge(type);
	// }

	/**
	 * 增加归并数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是materiel型，电子手册的物料基本资料
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-编辑", index = 2.1)
	public List addInnerMergeData(Request request, List list, boolean flag) {
		return this.dzscInnerMergeLogic.addInnerMergeData(list, flag);
	}

	/**
	 * 自动从物料主档中导入资料
	 * 
	 * @param request
	 *            请求控制
	 * @param imrType
	 *            物料类型
	 * @return 返回空的ArrayList()
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-编辑", index = 2.1)
	public void importInnerMergeDataFromMateriel(Request request,
			List lsMaterialType, boolean flag) {
		this.dzscImportFromOther.importDataFromMaterial(lsMaterialType, flag);
	}

	/**
	 * 删除手册归并数据
	 * 
	 * @param request
	 *            请求控制
	 * @param data
	 *            归并数据
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-删除", index = 2.1)
	public void deleteDzscInnerMergeData(Request request, List list) {
		this.dzscInnerMergeLogic.deleteDzscInnerMergeData(list);
	}

	/**
	 * 删除手册归并数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	public void deleInnerMergerForMateriel(Request request, List list) {
		this.dzscHandInnerLogic.deleInnerMergerForMateriel(list);
	}

	/**
	 * 删除物料的内部归并，并且设置这些物料不再用于内部归并
	 * 
	 * @param list
	 */
	public void forbidInnerMergeForMateriel(Request request, List list) {
		this.dzscHandInnerLogic.forbidInnerMergeForMateriel(list);
	}

	/**
	 * 转抄归并数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	public void copyInnerMergerForMateriel(Request request, List list) {
		this.dzscHandInnerLogic.copyInnerMergerForMateriel(list);
	}

	/**
	 * 判断序号是否重复是否重复
	 * 
	 * @param request
	 *            请求控制
	 */
	/*
	 * public boolean isReMerger(Request request, DzscInnerMergeHead head,
	 * DzscInnerMergeData data) { return this.dzbaDao.isReMerger(head, data); }
	 */

	/**
	 * 撤消10位归并
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-归并操作", index = 2.1)
	public List unDoTenInnerMerge(Request request, List list) {
		this.dzscHandInnerLogic.unDoTenInnerMerge(list);
		return list;
	}

	/**
	 * 检查能否进行撤消10位归并动作。 如果允许撤消返回0，否则如果数据已做过四位归并则返回-1，不能撤消。
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return int 允许撤消返回0，否则如果数据已做过四位归并则返回-1，不能撤消
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-归并操作", index = 2.1)
	public int checkDataForUndoTenInnerMerge(Request request, List list) {
		return this.dzscHandInnerLogic.checkDataForUndoTenInnerMerge(list);
	}

	// /**
	// * 抓取归并后的DzscInnerMergeData；根据物料类型，及归并序号来查。
	// *
	// * @param request
	// * 请求控制
	// * @param type
	// * 物料类型
	// * @return List 是DzscInnerMergeData型，手册商品归并资料
	// */
	// public List findInnerMergedMeterialBytenSeqNum(Request request,
	// String type, Integer seqNum) {
	// return this.dzscInnerMergeDao.findInnerMergedMeterialBytenSeqNum(type,
	// seqNum);
	// }

	/**
	 * 根据10码抓取归并前的资料
	 * 
	 * @param tenInnerMerge
	 * @return
	 */
	public List findDzscInnerMergeData(Request request,
			DzscTenInnerMerge tenInnerMerge) {
		return this.dzscInnerMergeDao.findDzscInnerMergeData(tenInnerMerge);
	}

	/**
	 * 撤消4位商品归并。
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是DzscInnerMergeData型，归并数据
	 * @return list 是DzscInnerMergeData型，归并数据
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--手册商品归并-归并操作", index = 2.1)
	public List undoFourInnerMerge(Request request, List list) {
		this.dzscHandInnerLogic.undoFourInnerMerge(list);
		return list;
	}

	// /**
	// * 补充四码
	// *
	// * @param request
	// * 请求控制
	// * @param list
	// * 是DzscInnerMergeData型，归并数据
	// * @return list 是DzscInnerMergeData型，归并数据
	// */
	// @AuthorityFunctionAnnotation(caption = "手册商品归并-归并操作", index = 1.3)
	// public List addfourInner(Request request, List list) {
	// this.dzscHandInnerLogic.addfourInner(list);
	// return list;
	// }

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
	public List editFourInnerMerge(Request request,
			DzscFourInnerMerge fourInnerMerge) {
		return this.dzscHandInnerLogic.editFourInnerMerge(fourInnerMerge);
	}

	/**
	 * 抓取不在正在执行的归并记录
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findInnerMergeDataAndChangeByType(Request request, String type) {
		return this.dzscInnerMergeDao.findInnerMergeDataAndChangeByType(type);
	}

	/**
	 * 抓取未变更的归并记录 根据物料类型
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findInnerMergeDataByTypeForPrint(Request request, String type) {
		return this.dzscInnerMergeDao.findInnerMergeDataByTypeForPrint(type);
	}

	/**
	 * 抓取归并后的DzscInnerMergeData；
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--报关与工厂对照", index = 2.2)
	public List findInnerMergedTenSeqNum(Request request, String type) {
		return this.dzscHandInnerLogic.findInnerMergedTenSeqNum(type);
	}
	
	@AuthorityFunctionAnnotation(caption = "归并关系--报关与工厂对照", index = 2.2)
	public  void checkfindInnerMergedTenSeqNum(Request request) {
	}

	/**
	 * 抓取归并后的DzscInnerMergeData；根据序号，及商品名称来查。
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 是DzscInnerMergeData型，手册商品归并资料
	 */
	public List findInnerMergedTenSeqNum(Request request, String meterialYype,
			String sFields, Object obj) {
		return this.dzscHandInnerLogic.findInnerMergedTenSeqNum(meterialYype,
				sFields, obj);
	}

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
	public List findMaterielForDzscInnerMerge(Request request,
			String materielType, int index, int length, String property,
			Object value, Boolean isLike) {
		return this.dzscInnerMergeLogic.findMaterielForDzscInnerMerge(
				materielType, index, length, property, value, isLike);
	}

	/**
	 * 查找电子手册自定义归并条件
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类型
	 * @return List 是DzscCustomInnerMergeCondition型，电子手册自定义归并条件
	 */
	public List findDzscCustomInnerMergeConditionByType(Request request,
			String type) {
		return this.dzscInnerMergeDao
				.findDzscCustomInnerMergeConditionByType(type);
	}

	/**
	 * 查找电子手册自定义归并条件
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是DzscCustomInnerMergeCondition型，电子手册自定义归并条件
	 */
	public List findDzscCustomInnerMergeCondition(Request request) {
		return this.dzscInnerMergeDao.findDzscCustomInnerMergeCondition();
	}

	/**
	 * 手册电子手册自定义归并条件
	 * 
	 * @param request
	 *            请求控制
	 * @param condition
	 *            电子手册自定义归并条件
	 */
	public void deleteDzscCustomInnerMergeCondition(Request request,
			DzscCustomInnerMergeCondition condition) {
		this.dzscInnerMergeDao.deleteDzscCustomInnerMergeCondition(condition);
	}

	/**
	 * 归并全部资料
	 * 
	 * @param request
	 *            请求控制
	 */
	public void dzscCustomInnerMerge(Request request) {
		this.dzscCustomInnerMerge.customInnerMergeAll();
	}

	/**
	 * 保存电子手册自定义归并条件
	 * 
	 * @param request
	 *            请求控制
	 * @param condition
	 *            电子手册自定义归并条件
	 */
	public void saveDzscCustomInnerMergeCondition(Request request,
			DzscCustomInnerMergeCondition condition) {
		this.dzscInnerMergeDao.saveDzscCustomInnerMergeCondition(condition);
	}

	/**
	 * 归并数据
	 * 
	 * @param request
	 *            请求控制
	 */
	public void dzscAutoInnerMergeData(Request request,
			TempDzscAutoInnerMergeParam param) {
		this.dzscAutoInnerMerge.dzscInnerMergeData(param);
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
		return this.dzscImportFromOther.checkFileData(list, ht, request
				.getTaskId());
	}

	/**
	 * 导入文件来自文件
	 * 
	 * @param list
	 */
	public void importDataFromTxtFile(Request request, List list) {
		this.dzscImportFromOther.importDataFromTxtFile(list, request
				.getTaskId());
	}

	/**
	 * 使内部归并所有商品都改为默认系数
	 */
	public void initDzscUnitDedault(Request request) {
		this.dzscInnerMergeLogic.initUnitDedault();
	}

	public List findDzscReverseMergeFourData(Request request,
			String materielType) {
		return this.dzscInnerMergeDao
				.findDzscReverseMergeFourData(materielType);
	}

	public void convertOldDataToNewData(Request request) {
		this.dzscInnerMergeLogic.convertOldDataToNewData();
	}

	/**
	 * 查询所有未归并的资料
	 * 
	 * @param materielType
	 * @param isChange
	 * @return
	 */
	public List findDzscNotMergeInnerMergeData(Request request,
			String materielType, boolean isChange) {
		return this.dzscInnerMergeDao.findDzscNotMergeInnerMergeData(
				materielType, isChange);
	}

	/**
	 * 删除所有未归并的资料
	 * 
	 * @param materielType
	 * @param isChange
	 * @return
	 */
	public void deleteDzscNotMergeInnerMergeData(Request request,
			String materielType, boolean isChange) {
		this.dzscInnerMergeDao.deleteDzscNotMergeInnerMergeData(materielType,
				isChange);
	}

	/**
	 * 查询内部归并导入文件栏位对应次序
	 * 
	 * @return
	 */
	public DzscInnerMergeDataOrder findDzscInnerMergeDataOrder(Request request) {
		return this.dzscInnerMergeDao.findDzscInnerMergeDataOrder();
	}

	/**
	 * 保存内部归并导入文件栏位对应次序
	 * 
	 * @param order
	 */
	public DzscInnerMergeDataOrder saveDzscInnerMergeDataOrder(Request request,
			DzscInnerMergeDataOrder order) {
		this.dzscInnerMergeDao.saveDzscInnerMergeDataOrder(order);
		return order;
	}

	/**
	 * 特殊处理，将归并关系从正在生效状态回滚到原始状态
	 * 
	 * @param list
	 */
	public List resetExecuteToOriginal(Request request, List list) {
		this.dzscInnerMergeLogic.resetExecuteToOriginal(list);
		return list;
	}

	/**
	 * 查询报关单耗表头成品资料
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "归并关系--报关单耗", index = 2.4)
	public List findDzscCustomsBomExg(Request request) {
		return this.dzscInnerMergeDao.findDzscCustomsBomExg();
	}

	/**
	 * 查询报关单耗明细资料
	 * 
	 * @param dzscCustomsBomExg
	 * @return
	 */
	public List findDzscCustomsBomDetail(Request request,
			DzscCustomsBomExg dzscCustomsBomExg) {
		return this.dzscInnerMergeDao
				.findDzscCustomsBomDetail(dzscCustomsBomExg);
	}

	/**
	 * 查询不在报关单耗成品里的归并资料
	 * 
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomExg(Request request) {
		return this.dzscInnerMergeLogic.findInnerMergeNotInCustomsBomExg();
	}

	/**
	 * 查询不在报关单耗明细里的归并资料
	 * 
	 * @return
	 */
	public List findInnerMergeNotInCustomsBomDetail(Request request,
			DzscCustomsBomExg dzscCustomsBomExg) {
		return this.dzscInnerMergeLogic
				.findInnerMergeNotInCustomsBomDetail(dzscCustomsBomExg);
	}

	/**
	 * 根据归并增加报关单耗成品
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	public List addDzscCustomsBomExgFromInnerMerge(Request request,
			List lsTenInnerMerge) {
		return this.dzscInnerMergeLogic
				.addDzscCustomsBomExgFromInnerMerge(lsTenInnerMerge);
	}

	/**
	 * 根据归并增加报关单耗明细
	 * 
	 * @param lsTenInnerMerge
	 * @return
	 */
	public List addDzscCustomsBomDetailFromInnerMerge(Request request,
			DzscCustomsBomExg dzscCustomsBomExg, List lsTenInnerMerge) {
		return this.dzscInnerMergeLogic.addDzscCustomsBomDetailFromInnerMerge(
				dzscCustomsBomExg, lsTenInnerMerge);
	}

	/**
	 * 保存报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	public DzscCustomsBomDetail saveDzscCustomsBomDetail(Request request,
			DzscCustomsBomDetail dzscCustomsBomDetail) {
		this.dzscInnerMergeDao.saveOrUpdate(dzscCustomsBomDetail);
		return dzscCustomsBomDetail;
	}

	/**
	 * 删除报关单耗成品资料
	 * 
	 * @param dzscCustomsBomExg
	 */
	public void deleteDzscCustomsBomExg(Request request, List list) {
		this.dzscInnerMergeLogic.deleteDzscCustomsBomExg(list);
	}

	/**
	 * 删除报关单耗明细资料
	 * 
	 * @param dzscCustomsBomDetail
	 */
	public void deleteDzscCustomsBomDetail(Request request, List list) {
		this.dzscInnerMergeLogic.deleteDzscCustomsBomDetail(list);
	}

	/**
	 * 根据报关单耗的成品资料抓取归并前的BOM成品以及版本资料
	 * 
	 * @param exg
	 * @return
	 */
	public List findMaterialExgByCustomsBomExg(Request request,
			DzscCustomsBomExg exg) {
		return this.dzscInnerMergeLogic.findMaterialExgByCustomsBomExg(exg);
	}

	/**
	 * 自动计算报关单耗
	 * 
	 * @param list
	 * @return
	 */
	public List autoCalDzscCustomsBom(Request request, List list,
			DzscCustomsBomExg exg) {
		return this.dzscInnerMergeLogic.autoCalDzscCustomsBom(list, exg);
	}

	/**
	 * 保存自动计算的报关单耗
	 * 
	 * @param list
	 * @param exg
	 */
	public void saveAutoCalDzscCustomsBomDetail(Request request, List list,
			DzscCustomsBomExg exg) {
		this.dzscInnerMergeLogic.saveAutoCalDzscCustomsBomDetail(list, exg);
	}
	@AuthorityFunctionAnnotation(caption = "归并关系--成品加工费预设", index = 2.3)
	public void checkFmDzscExgTenInnerMerge(Request request) {
		// TODO Auto-generated method stub
		
	}

}