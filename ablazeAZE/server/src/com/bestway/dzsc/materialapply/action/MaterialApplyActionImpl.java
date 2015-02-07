package com.bestway.dzsc.materialapply.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.materialbase.entity.BomStructureType;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.dzsc.materialapply.dao.MaterialApplyDao;
import com.bestway.dzsc.materialapply.entity.DzscMaterielHead;
import com.bestway.dzsc.materialapply.entity.MaterialApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomMasterApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;
import com.bestway.dzsc.materialapply.entity.MaterialChange;
import com.bestway.dzsc.materialapply.entity.TempExpQPBomMsgSelectParam;
import com.bestway.dzsc.materialapply.entity.TempExpQPMaterialMsgSelectParam;
import com.bestway.dzsc.materialapply.entity.TempMaterialApplySelectParam;
import com.bestway.dzsc.materialapply.logic.MaterialApplyLogic;

/**
 * com.bestway.dzsc.materialapply.action.MaterialApplyActionImpl
 * 
 * @author yp
 * 
 */
//-物料和单耗申报
@AuthorityClassAnnotation(caption = "电子手册", index = 7)
public class MaterialApplyActionImpl extends BaseActionImpl implements
		MaterialApplyAction {
	private MaterialApplyDao materialApplyDao = null;

	private MaterialApplyLogic materialApplyLogic = null;

	/**
	 * 获取materialApplyLogic
	 * 
	 * @return materialApplyLogic
	 */
	public MaterialApplyLogic getMaterialApplyLogic() {
		return materialApplyLogic;
	}

	/**
	 * 设置materialApplyLogic
	 * 
	 * @param materialApplyLogic
	 */
	public void setMaterialApplyLogic(MaterialApplyLogic materialApplyLogic) {
		this.materialApplyLogic = materialApplyLogic;
	}

	/**
	 * 获取materialApplyDao
	 * 
	 * @return materialApplyDao
	 */
	public MaterialApplyDao getMaterialApplyDao() {
		return materialApplyDao;
	}

	/**
	 * 设置materialApplyDao
	 * 
	 * @param materialApplyDao
	 */
	public void setMaterialApplyDao(MaterialApplyDao materialApplyDao) {
		this.materialApplyDao = materialApplyDao;
	}

	/**
	 * 抓取物料表头
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料备案-浏览", index = 1.1)
	public DzscMaterielHead findDzscMaterielHead(Request request) {
		return this.materialApplyLogic.findDzscMaterielHead();
	}

	/**
	 * 抓取还没有备案的物料
	 * 
	 * @param materialTypeCode
	 *            物料类别编码
	 * @return List 是Materiel型，报关常用物料
	 */

	public List findMaterialNotInApply(Request request, String materialTypeCode) {
		// long begin =System.currentTimeMillis();
		// this.materialApplyDao.findMaterialNotInApply(materialTypeCode);
		// long one =System.currentTimeMillis();
		List list = this.materialApplyLogic
				.findMaterialNotInApply(materialTypeCode);
		// long two =System.currentTimeMillis();
		// System.out.println("---------:"+(one-begin)/1000.0+"--------"+(two-one)/1000.0);
		return list;
	}

	/**
	 * 查找电子手册物料基础资料
	 * 
	 * @param materialTypeCode
	 *            物料类别编码
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 * @return List 是MaterialApply型，电子手册物料基础资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料备案-申报", index = 1.1)
	public List findMaterialApply(Request request, String materialTypeCode,
			int index, int length, String property, Object value, Boolean isLike) {
		return this.materialApplyDao.findMaterialApply(materialTypeCode, index,
				length, property, value, isLike);
	}

	/**
	 * 查找电子手册物料基础资料
	 * 
	 * @return List 是MaterialApply型，电子手册物料基础资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料备案-申报", index = 1.1)
	public List findMaterialApply(Request request, String materialType) {
		return this.materialApplyDao.findMaterialApply(materialType);
	}

	/**
	 * 查找电子手册物料基础资料
	 * 
	 * @param materialTypeCode
	 *            物料类别编码
	 * @return List 是MaterialApply型，电子手册物料基础资料
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料备案-申报", index = 1.1)
	public List findMaterialApplied(Request request, String materialType) {
		return this.materialApplyDao.findMaterialApplied(materialType);
	}

	/**
	 * 将物料增加到物料备案中
	 * 
	 * @param list
	 *            是Materiel型，物料
	 * @return List 是MaterialApply型，物料备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料申报-编辑", index = 1.1)
	public List addMaterialApply(Request request, List list) {
		return this.materialApplyLogic.addMaterialApply(list);
	}

	/**
	 * 将物料增加到物料备案记录中
	 * 
	 * @param list
	 *            是Materiel型，物料
	 * @return List 是MaterialApply型，物料备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料申报-编辑", index = 1.1)
	public List addMaterialChange(Request request, List list) {
		return this.materialApplyLogic.addMaterialChange(list);
	}

	/**
	 * 保存电子手册物料基础资料 来自电子手册物料基础资料
	 * 
	 * @param materialApply
	 *            电子手册物料基础资料
	 * @return materialApply 电子手册物料基础资料
	 */
	public MaterialApply saveMaterialApply(Request request,
			MaterialApply materialApply) {
		this.materialApplyDao.saveMaterialApply(materialApply);
		return materialApply;
	}

	/**
	 * 删除电子手册物料基础资料 来自电子手册物料基础资料
	 * 
	 * @param materialApply
	 *            电子手册物料基础资料
	 */
	public void deleteMaterialApply(Request request, MaterialApply materialApply) {
		this.materialApplyDao.deleteMaterialApply(materialApply);
	}

	/**
	 * 删除已申报和已执行的物料备案记录
	 * 
	 * @param list
	 *            是MaterialApply型，物料备案记录
	 * @return List 是MaterialApply型，物料备案记录，返回移除了符合条件的List
	 */
	public List deleteMaterialApply(Request request, List list) {
		return this.materialApplyLogic.deleteMaterialApply(list);
	}

	/**
	 * 查询电子手册物料基础资料变更数据
	 * 
	 * @param materialTypeCode
	 *            物料类别编码
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            判断查找时where中的property是用“＝”还是用“like”
	 * @return List 是MaterialChange型，电子手册物料基础资料变更数据
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料备案-申报", index = 1.1)
	public List findMaterialChange(Request request, String materialTypeCode,
			int index, int length, String property, Object value, Boolean isLike) {
		return this.materialApplyDao.findMaterialChange(materialTypeCode,
				index, length, property, value, isLike);
	}

	/**
	 * 查询电子手册物料基础资料（未申请）
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是MaterialApply类型，电子手册物料基础资料（未申请）
	 */
	public List findOriginalMaterial(Request request, String materialType) {
		return this.materialApplyDao.findOriginalMaterial(materialType);
	}

	/**
	 * 查询未申报的物料（已变更未生效）
	 * 
	 * @param scmCoiCode
	 *            料件类别编码
	 * @return List 是MaterialChange型，电子手册物料基础资料变更数据
	 */
	public List findNotApplyMaterialChange(Request request, String materialType) {
		return this.materialApplyDao.findNotApplyMaterialChange(materialType);
	}

	/**
	 * 保存电子手册物料基础资料变更数据
	 * 
	 * @param materialChange
	 *            电子手册物料基础资料变更数据
	 */
	public MaterialChange saveMaterialChange(Request request,
			MaterialChange materialChange) {
		this.materialApplyDao.saveMaterialChange(materialChange);
		return materialChange;
	}

	/**
	 * 删除电子手册物料基础资料变更数据
	 * 
	 * @param materialChange
	 *            电子手册物料基础资料变更数据
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料申报-删除", index = 1.1)
	public void deleteMaterialChange(Request request,
			MaterialChange materialChange) {
		this.materialApplyDao.deleteMaterialChange(materialChange);
	}

	/**
	 * 删除物料变更记录
	 * 
	 * @param list
	 *            是MaterialChange型，物料变更记录
	 * @return List 是MaterialChange型，物料变更记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料申报-删除", index = 1.1)
	public List deleteMaterialChange(Request request, List list) {
		return this.materialApplyLogic.deleteMaterialChange(list);
	}

	/**
	 * 删除物料变更记录
	 * 
	 * @param list
	 *            是MaterialChange型，物料变更记录
	 * @return List 是MaterialChange型，物料变更记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料申报-删除", index = 1.1)
	public List markDeleteMaterialChange(Request request, List list) {
		return this.materialApplyLogic.markDeleteMaterialChange(list);
	}

	// /**
	// * 查询电子手册物料基础资料（未申请）
	// *
	// * @param scmCoiCode
	// * 物料类别编码
	// * @return List 是MaterialApply类型，电子手册物料基础资料（未申请）
	// */
	//
	// public List findOriginalMaterial(Request request, String scmCoiCode) {
	// return this.materialApplyDao.findOriginalMaterial(scmCoiCode);
	// }

	// /**
	// * 查询未生效的电子手册物料基础资料（已申请未生效）
	// *
	// * @param scmCoiCode
	// * 物料类别编码
	// * @return List 是MaterialApply类型，电子手册物料基础资料（已申请未生效）
	// */
	//
	// public List findNotEffectiveMaterial(Request request, String scmCoiCode)
	// {
	// return this.materialApplyDao.findNotEffectiveMaterial(scmCoiCode);
	// }

	/**
	 * 变更物料主档
	 * 
	 * @param materialApply
	 *            物料备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料申报-编辑", index = 1.1)
	public MaterialApply changeMaterial(Request request,
			MaterialApply materialApply) {
		this.materialApplyLogic.changeMaterial(materialApply);
		return materialApply;
	}

	/**
	 * 申报物料主档
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料申报-申报", index = 1.1)
	public DeclareFileInfo applyMaterial(Request request,
			TempMaterialApplySelectParam param) {
		return this.materialApplyLogic
				.applyMaterial(request.getTaskId(), param);
	}

	/**
	 * 查询异常的物料备案资料
	 * 
	 * @return
	 */
	public List findExceptionMaterialApply(Request request) {
		return this.materialApplyLogic.findExceptionMaterialApply();
	}

	/**
	 * 删除异常的物料备案资料
	 * 
	 * @return
	 */
	public void deleteExceptionMaterialApply(Request request, List list) {
		this.materialApplyLogic.deleteExceptionMaterialApply(list);
	}

	/**
	 * 生效物料主档
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料申报-生效", index = 1.1)
	public String processMaterialApplyResult(Request request,List lsReturnFile) {
		return this.materialApplyLogic.processMaterialApplyResult(lsReturnFile);
	}

	// /**
	// * 生效物料主档
	// *
	// * @param list
	// */
	// public void effectiveMaterial(Request request, List list) {
	// this.materialApplyLogic.effectiveMaterial(list);
	// }

	/**
	 * 抓取还没有备案的单耗
	 * 
	 * @return List 是MaterialBomMaster型，报关常用BOM
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-浏览", index = 1.2)
	public List findMaterialBomNotInApply(Request request) {
		return this.materialApplyLogic.findMaterialBomNotInApply();
	}

	/**
	 * 读取BOM备案记录
	 * 
	 * @param index
	 *            数据的开始下标
	 * @param length
	 *            数据的长度
	 * @param property
	 *            属性
	 * @param value
	 *            property的值
	 * @param isLike
	 *            判断查找时where中的property是用“＝”还是用“like”
	 * @return List 是MaterialBomMasterApply型，BOM备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-浏览", index = 1.2)
	public List findMaterialBomApply(Request request, int index, int length,
			String property, Object value, Boolean isLike, Boolean isChanged) {
		return this.materialApplyDao.findMaterialBomApply(index, length,
				property, value, isLike, isChanged);
	}

	/**
	 * 读取BOM版本备案记录
	 * 
	 * @param bomMaster
	 *            BOM备案记录
	 * @param isChanged
	 *            判断是否改变，true表示已改变了
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-浏览", index = 1.2)
	public List findMaterialBomVersionApply(Request request,
			MaterialBomMasterApply bomMaster) {
		return this.materialApplyDao.findMaterialBomVersionApply(bomMaster);
	}

	/**
	 * 将物料单耗增加到物料单耗备案中
	 * 
	 * @param list
	 *            是MaterialBomMaster型，报关常用BOM里的成品
	 * @return List 是MaterialBomMasterApply型，BOM备案记录
	 */
	public List changeMaterialBomApply(Request request, List list) {
		return this.materialApplyLogic.changeMaterialBomApply(list);
	}

	/**
	 * 保存BOM备案记录
	 * 
	 * @param materialBomApply
	 *            BOM备案记录
	 */

	public MaterialBomMasterApply saveMaterialBomApply(Request request,
			MaterialBomMasterApply materialBomApply) {
		this.materialApplyDao.saveMaterialBomApply(materialBomApply);
		return materialBomApply;
	}

	/**
	 * 删除BOM备案记录
	 * 
	 * @param materialBomApply
	 *            BOM备案记录
	 */
	public void deleteMaterialBomApply(Request request,
			MaterialBomMasterApply materialBomApply) {
		this.materialApplyDao.deleteMaterialBomApply(materialBomApply);
	}

	/**
	 * 将物料单耗增加到物料单耗备案中
	 * 
	 * @param list
	 *            是MaterialBomMaster型，报关常用BOM里的成品
	 * @return List 是MaterialBomMasterApply型，BOM备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-新增成品", index = 1.2)
	public List addMaterialBomApply(Request request, List list, boolean isChange) {
		return this.materialApplyLogic.addMaterialBomApply(list, isChange);
	}

	/**
	 * 删除物料单耗备案
	 * 
	 * @param list
	 *            是MaterialBomMasterApply型，BOM备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-删除成品", index = 1.2)
	public void deleteMaterialBomApply(Request request, List list) {
		this.materialApplyLogic.deleteMaterialBomApply(list);
	}

	/**
	 * 将变更的BOM显示为删除状态
	 * 
	 * @param list
	 *            是MaterialBomMasterApply型，BOM备案记录
	 */
	public void markDeleteMaterialBomApply(Request request, List list) {
		this.materialApplyLogic.markDeleteMaterialBomApply(list);
	}

	/**
	 * 变更BOM版本备案记录
	 * 
	 * @param bomVersionApply
	 *            BOM版本备案记录
	 * @return bomVersionApply BOM版本备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-编辑版本号", index = 1.2)
	public MaterialBomVersionApply changeBomVersion(Request request,
			MaterialBomVersionApply bomVersionApply) {
		this.materialApplyLogic.changeBomVersion(bomVersionApply);
		return bomVersionApply;
	}

	// /**
	// * 变更BOM版本
	// *
	// * @param list
	// * @return
	// */
	// public MaterialBomDetail changeBomDetail(Request request,
	// MaterialBomVersionApply bomVersionApply, MaterialBomDetail bomDetail) {
	// this.materialApplyLogic.changeBomDetail(bomVersionApply, bomDetail);
	// return bomDetail;
	// }

	/**
	 * 申报物料单耗
	 * 
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-申报", index = 1.2)
	public DeclareFileInfo applyMaterialBom(Request request) {
		return this.materialApplyLogic.applyMaterialBom(request.getTaskId());
	}

	/**
	 * 生效物料单耗
	 * 
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-生效", index = 1.2)
	public String processMaterialBomResult(Request request, List lsReturnFile) {
		return this.materialApplyLogic.processMaterialBomResult(lsReturnFile);
	}

	/**
	 * 读取BOM版本备案记录
	 * 
	 * @param bomMasterApply
	 *            BOM备案记录
	 * @param isChanged
	 *            判断是否改变，true表示已改变了
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	public List findMaterialBomVersionApply(Request request,
			String bomMasterPtNo) {
		return this.materialApplyDao.findMaterialBomVersionApply(bomMasterPtNo);
	}

	/**
	 * 保存BOM版本备案记录
	 * 
	 * @param materialBomVersionApply
	 *            BOM版本备案记录
	 * @return materialBomVersionApply BOM版本备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-编辑版本号", index = 1.2)
	public MaterialBomVersionApply saveMaterialBomVersionApply(Request request,
			MaterialBomVersionApply materialBomVersionApply, boolean isChange) {
		return this.materialApplyLogic.saveMaterialBomVersionApply(
				materialBomVersionApply, isChange);
	}

	/**
	 * 删除BOM版本备案记录
	 * 
	 * @param materialBomVersionApply
	 *            BOM版本备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-删除版本号", index = 1.2)
	public void deleteMaterialBomVersionApply(Request request,
			MaterialBomVersionApply materialBomVersionApply, boolean isChange) {
		this.materialApplyLogic.deleteMaterialBomVersionApply(
				materialBomVersionApply, isChange);
	}

	/**
	 * 查询BOM料件备案记录
	 * 
	 * @param versionApply
	 *            BOM版本备案记录
	 * @return List 是MaterialBomDetailApply，BOM料件备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-浏览", index = 1.2)
	public List findMaterialBomDetailApply(Request request,
			MaterialBomVersionApply versionApply) {
		return this.materialApplyDao.findMaterialBomDetailApply(versionApply);
	}

	/**
	 * 保存BOM料件备案记录
	 * 
	 * @param materialBomDetailApply
	 *            BOM料件备案记录
	 * @return materialBomDetailApply BOM料件备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-编辑料件", index = 1.2)
	public MaterialBomDetailApply saveMaterialBomDetailApply(Request request,
			MaterialBomDetailApply materialBomDetailApply) {
		this.materialApplyLogic
				.saveMaterialBomDetailApply(materialBomDetailApply);
		return materialBomDetailApply;
	}

	/**
	 * 删除BOM料件备案记录
	 * 
	 * @param materialBomDetailApply
	 *            BOM料件备案记录
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-删除料件", index = 1.2)
	public void deleteMaterialBomDetailApply(Request request,
			MaterialBomDetailApply materialBomDetailApply, boolean isChange) {
		this.materialApplyLogic.deleteMaterialBomDetailApply(
				materialBomDetailApply, isChange);
	}

	/**
	 * 查询没有在BOM备案中的Bom版本
	 * 
	 * @param master
	 *            BOM成品备案记录
	 * @return List 是MaterialBomVersion型，报关常用BOM里的版本号
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-浏览", index = 1.2)
	public List findMaterialBomVersionNotInApply(Request request,
			MaterialBomMasterApply master) {
		return this.materialApplyDao.findMaterialBomVersionNotInApply(master);
	}

	/**
	 * 查询没有在BOM备案的BOM料件
	 * 
	 * @param version
	 *            BOM版本备案记录
	 * @return List 是MaterialBomDetail型，报关常用BOM里的料件
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-浏览", index = 1.2)
	public List findMaterialBomDetailNotInApply(Request request,
			MaterialBomVersionApply version) {
		return this.materialApplyDao.findMaterialBomDetailNotInApply(version);
	}

	/**
	 * 新增ＢＯＭ版本
	 * 
	 * @param bomMasterApply
	 *            BOM备案记录
	 * @param list
	 *            是MaterialBomVersion型，报关常用BOM里的版本号
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-编辑版本号", index = 1.2)
	public void addMaterialBomVersionApply(Request request,
			MaterialBomMasterApply bomMasterApply, List<MaterialBomVersion> list) {
		this.materialApplyLogic
				.addMaterialBomVersionApply(bomMasterApply, list);
	}

	/**
	 * 新增ＢＯＭ料件
	 * 
	 * @param bomVersionApply
	 *            BOM备案记录
	 * @param list
	 *            是MaterialBomDetail型，报关常用BOM里的料件
	 */
	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--BOM备案-编辑料件", index = 1.2)
	public List addMaterialBomDetailApply(Request request,
			MaterialBomVersionApply bomVersionApply,
			List<MaterialBomDetail> list) {
		return this.materialApplyLogic.addMaterialBomDetailApply(
				bomVersionApply, list);
	}

	@AuthorityFunctionAnnotation(caption = "物料与BOM备案--物料备案-修改企业信息", index = 1.1)
	public void saveDzscMaterielHead(Request request,
			DzscMaterielHead dzscMaterielHead) {
		this.materialApplyDao.saveOrUpdate(dzscMaterielHead);
	}

	/**
	 * 查询BOM中的料件
	 * 
	 * @param materielType
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List findMaterialBomApplyMateriel(Request request, boolean isChange,
			int index, int length, String property, Object value, Boolean isLike) {
		return this.materialApplyDao.findMaterialBomApplyMateriel(isChange,
				index, length, property, value, isLike);
	}

	/**
	 * 根据料件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	public List findMaterialBomApplyByDetail(Request request, boolean isChange,
			Materiel materiel) {
		return this.materialApplyDao.findMaterialBomApplyByDetail(isChange,
				materiel);
	}

	/**
	 * 查询被归并禁用的物料
	 * 
	 * @param materialType
	 * @return
	 */
	public List findForbidMergeMaterialApply(Request request,
			String materialType) {
		return this.materialApplyDao.findForbidMergeMaterialApply(materialType);
	}

	/**
	 * 取消物料被归并禁用
	 * 
	 * @param list
	 */
	public List cancelForbidMergeMaterialApply(Request request, List list) {
		this.materialApplyLogic.cancelForbidMergeMaterialApply(list);
		return list;
	}

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPMaterialMessage(Request request,
			String messageFileName, TempExpQPMaterialMsgSelectParam param) {
		this.materialApplyLogic.exportQPMaterialMessage(request.getTaskId(),
				messageFileName, param);
	}

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPBomMessage(Request request, String messageFileName,
			TempExpQPBomMsgSelectParam param) {
		this.materialApplyLogic.exportQPBomMessage(request.getTaskId(),
				messageFileName, param);
	}
	/**
	 * 构造申报成品的申报单耗版本（电子手册）
	 * 
	 * @param parentNo
	 *            料号
	 * @return List 是TempEntBomVersion型，临时的BOM备案版本
	 */
	public List findMaterialBomVersionApplyByPtNo(Request request,String parentNo) {
		Set<Object> hsTemp = new HashSet<Object>();
		List list = materialApplyDao.findMaterialBomVersionApplyByPtNo(parentNo);
		for (int i = 0; i < list.size(); i++) {
			TempEntBomVersion temp = new TempEntBomVersion();
			Integer obj = (Integer) list.get(i);
				temp.setVersion(obj);
				temp.setBeginDate(null);
				temp.setEndDate(null);
				hsTemp.add(temp);
		}
		return new ArrayList(hsTemp);
	}
}
