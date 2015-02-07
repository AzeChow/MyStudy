package com.bestway.dzsc.materialapply.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.dzsc.materialapply.entity.DzscMaterielHead;
import com.bestway.dzsc.materialapply.entity.MaterialApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomMasterApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;
import com.bestway.dzsc.materialapply.entity.MaterialChange;
import com.bestway.dzsc.materialapply.entity.TempExpQPBomMsgSelectParam;
import com.bestway.dzsc.materialapply.entity.TempExpQPMaterialMsgSelectParam;
import com.bestway.dzsc.materialapply.entity.TempMaterialApplySelectParam;

/**
 * com.bestway.dzsc.materialapply.action.MaterialApplyAction
 * 
 * @author yp
 * 
 */
public interface MaterialApplyAction {
	/**
	 * 抓取物料表头
	 * 
	 * @return
	 */
	DzscMaterielHead findDzscMaterielHead(Request request);

	/**
	 * 抓取还没有备案的物料
	 * 
	 * @param request
	 *            请求控制
	 * @param materialTypeCode
	 *            物料类别编码
	 * @return List 是Materiel型，报关常用物料
	 */
	List findMaterialNotInApply(Request request, String materialTypeCode);

	/**
	 * 查找电子手册物料基础资料
	 * 
	 * @return List 是MaterialApply型，电子手册物料基础资料
	 */
	List findMaterialApply(Request request, String materialType);

	/**
	 * 查找电子手册物料基础资料
	 * 
	 * @param request
	 *            请求控制
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
	List findMaterialApply(Request request, String materialTypeCode, int index,
			int length, String property, Object value, Boolean isLike);

	/**
	 * 查找电子手册物料基础资料
	 * 
	 * @param materialTypeCode
	 *            物料类别编码
	 * @return List 是MaterialApply型，电子手册物料基础资料
	 */
	List findMaterialApplied(Request request, String materialType);

	/**
	 * 查询电子手册物料基础资料（未申请）
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是MaterialApply类型，电子手册物料基础资料（未申请）
	 */
	List findOriginalMaterial(Request request, String materialType);

	/**
	 * 查询未申报的物料（已变更未生效）
	 * 
	 * @param scmCoiCode
	 *            料件类别编码
	 * @return List 是MaterialChange型，电子手册物料基础资料变更数据
	 */
	List findNotApplyMaterialChange(Request request, String materialType);

	/**
	 * 保存电子手册物料基础资料 来自电子手册物料基础资料
	 * 
	 * @param request
	 *            请求控制
	 * @param materialApply
	 *            电子手册物料基础资料
	 * @return materialApply 电子手册物料基础资料
	 */
	MaterialApply saveMaterialApply(Request request, MaterialApply materialApply);

	/**
	 * 删除电子手册物料基础资料 来自电子手册物料基础资料
	 * 
	 * @param request
	 *            请求控制
	 * @param materialApply
	 *            电子手册物料基础资料
	 */
	void deleteMaterialApply(Request request, MaterialApply materialApply);

	/**
	 * 删除已申报和已执行的物料备案记录
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是MaterialApply型，物料备案记录
	 * @return List 是MaterialApply型，物料备案记录，返回移除了符合条件的List
	 */
	List deleteMaterialApply(Request request, List list);

	/**
	 * 查询电子手册物料基础资料变更数据
	 * 
	 * @param request
	 *            请求控制
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
	List findMaterialChange(Request request, String materialTypeCode,
			int index, int length, String property, Object value, Boolean isLike);

	/**
	 * 保存电子手册物料基础资料变更数据
	 * 
	 * @param request
	 *            请求控制
	 * @param materialChange
	 *            电子手册物料基础资料变更数据
	 */
	MaterialChange saveMaterialChange(Request request,
			MaterialChange materialChange);

	/**
	 * 删除电子手册物料基础资料变更数据
	 * 
	 * @param request
	 *            请求控制
	 * @param materialChange
	 *            电子手册物料基础资料变更数据
	 */
	void deleteMaterialChange(Request request, MaterialChange materialChange);

	/**
	 * 删除物料变更记录
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是MaterialChange型，物料变更记录
	 * @return List 是MaterialChange型，物料变更记录
	 */
	List deleteMaterialChange(Request request, List list);

	/**
	 * 删除物料变更记录
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是MaterialChange型，物料变更记录
	 * @return List 是MaterialChange型，物料变更记录
	 */
	List markDeleteMaterialChange(Request request, List list);

	/**
	 * 查询异常的物料备案资料
	 * 
	 * @return
	 */
	List findExceptionMaterialApply(Request request);

	/**
	 * 删除异常的物料备案资料
	 * 
	 * @return
	 */
	void deleteExceptionMaterialApply(Request request, List list);

	/**
	 * 将物料增加到物料备案中
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Materiel型，物料
	 * @return List 是MaterialApply型，物料备案记录
	 */
	List addMaterialApply(Request request, List list);

	/**
	 * 将物料增加到物料备案变更中
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Materiel型，物料
	 * @return List 是MaterialApply型，物料备案记录
	 */
	List addMaterialChange(Request request, List list);

	/**
	 * 变更物料主档
	 * 
	 * @param request
	 *            请求控制
	 * @param materialApply
	 *            物料备案记录
	 */
	MaterialApply changeMaterial(Request request, MaterialApply materialApply);

	/**
	 * 申报物料主档
	 * 
	 * @param request
	 *            请求控制
	 * @param scmCoiCode
	 *            物料类别编码
	 */
	DeclareFileInfo applyMaterial(Request request, TempMaterialApplySelectParam param);

	/**
	 * 申报物料主档
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 */
	// void applyMaterial(Request request,List list);
	/**
	 * 生效物料主档
	 * 
	 * @param request
	 *            请求控制
	 * @param scmCoiCode
	 *            物料类别编码
	 */
	String processMaterialApplyResult(Request request,List lsReturnFile);

	/**
	 * 生效物料主档
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 */
	// void effectiveMaterial(Request request,List list) ;
	/**
	 * 抓取还没有备案的单耗
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是MaterialBomMaster型，报关常用BOM
	 */
	List findMaterialBomNotInApply(Request request);

	/**
	 * 读取BOM备案记录
	 * 
	 * @param request
	 *            请求控制
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
	List findMaterialBomApply(Request request, int index, int length,
			String property, Object value, Boolean isLike, Boolean isChanged);

	/**
	 * 读取BOM版本备案记录
	 * 
	 * @param request
	 *            请求控制
	 * @param bomMaster
	 *            BOM备案记录
	 * @param isChanged
	 *            判断是否改变，true表示已改变了
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	List findMaterialBomVersionApply(Request request,
			MaterialBomMasterApply bomMaster);

	/**
	 * 将物料单耗增加到物料单耗备案中
	 * 
	 * @param list
	 *            是MaterialBomMaster型，报关常用BOM里的成品
	 * @return List 是MaterialBomMasterApply型，BOM备案记录
	 */
	List changeMaterialBomApply(Request request, List list);

	/**
	 * 将物料单耗增加到物料单耗备案中
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是MaterialBomMaster型，报关常用BOM里的成品
	 * @return List 是MaterialBomMasterApply型，BOM备案记录
	 */
	List addMaterialBomApply(Request request, List list, boolean isChange);

	/**
	 * 删除物料单耗备案
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是MaterialBomMasterApply型，BOM备案记录
	 */
	void deleteMaterialBomApply(Request request, List list);

	/**
	 * 将变更的BOM显示为删除状态
	 * 
	 * @param list
	 *            是MaterialBomMasterApply型，BOM备案记录
	 */
	void markDeleteMaterialBomApply(Request request, List list);

	/**
	 * 变更BOM版本备案记录
	 * 
	 * @param request
	 *            请求控制
	 * @param bomVersionApply
	 *            BOM版本备案记录
	 * @return bomVersionApply BOM版本备案记录
	 */
	MaterialBomVersionApply changeBomVersion(Request request,
			MaterialBomVersionApply bomVersionApply);

	// /**
	// * 变更BOM版本
	// *
	// * @param list
	// * @return
	// */
	// MaterialBomDetail changeBomDetail(Request request,
	// MaterialBomVersionApply bomVersionApply,
	// MaterialBomDetail bomDetail) ;

	/**
	 * 申报物料单耗
	 * 
	 * @param request
	 *            请求控制
	 */
	DeclareFileInfo applyMaterialBom(Request request);

	/**
	 * 生效物料单耗
	 * 
	 * @param request
	 *            请求控制
	 */
	String processMaterialBomResult(Request request, List lsReturnFile);

	/**
	 * 读取BOM版本备案记录
	 * 
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	List findMaterialBomVersionApply(Request request, String bomMasterPtNo);

	/**
	 * 保存BOM版本备案记录
	 * 
	 * @param request
	 *            请求控制
	 * @param materialBomVersionApply
	 *            BOM版本备案记录
	 * @return materialBomVersionApply BOM版本备案记录
	 */
	MaterialBomVersionApply saveMaterialBomVersionApply(Request request,
			MaterialBomVersionApply materialBomVersionApply, boolean isChange);

	/**
	 * 删除BOM版本备案记录
	 * 
	 * @param request
	 *            请求控制
	 * @param materialBomVersionApply
	 *            BOM版本备案记录
	 */
	void deleteMaterialBomVersionApply(Request request,
			MaterialBomVersionApply materialBomVersionApply, boolean isChange);

	/**
	 * 查询BOM料件备案记录
	 * 
	 * @param request
	 *            请求控制
	 * @param versionApply
	 *            BOM版本备案记录
	 * @return List 是MaterialBomDetailApply，BOM料件备案记录
	 */
	List findMaterialBomDetailApply(Request request,
			MaterialBomVersionApply versionApply);

	/**
	 * 保存BOM料件备案记录
	 * 
	 * @param request
	 *            请求控制
	 * @param materialBomDetailApply
	 *            BOM料件备案记录
	 * @return materialBomDetailApply BOM料件备案记录
	 */
	MaterialBomDetailApply saveMaterialBomDetailApply(Request request,
			MaterialBomDetailApply materialBomDetailApply);

	/**
	 * 删除BOM料件备案记录
	 * 
	 * @param request
	 *            请求控制
	 * @param materialBomDetailApply
	 *            BOM料件备案记录
	 */
	void deleteMaterialBomDetailApply(Request request,
			MaterialBomDetailApply materialBomDetailApply, boolean isChange);

	/**
	 * 查询没有在BOM备案中的Bom版本
	 * 
	 * @param request
	 *            请求控制
	 * @param master
	 *            BOM成品备案记录
	 * @return List 是MaterialBomVersion型，报关常用BOM里的版本号
	 */
	List findMaterialBomVersionNotInApply(Request request,
			MaterialBomMasterApply master);

	/**
	 * 查询没有在BOM备案的BOM料件
	 * 
	 * @param request
	 *            请求控制
	 * @param version
	 *            BOM版本备案记录
	 * @return List 是MaterialBomDetail型，报关常用BOM里的料件
	 */
	List findMaterialBomDetailNotInApply(Request request,
			MaterialBomVersionApply version);

	/**
	 * 新增ＢＯＭ版本
	 * 
	 * @param request
	 *            请求控制
	 * @param bomMasterApply
	 *            BOM备案记录
	 * @param list
	 *            是MaterialBomVersion型，报关常用BOM里的版本号
	 */
	void addMaterialBomVersionApply(Request request,
			MaterialBomMasterApply bomMasterApply, List<MaterialBomVersion> list);

	/**
	 * 新增ＢＯＭ料件
	 * 
	 * @param request
	 *            请求控制
	 * @param bomVersionApply
	 *            BOM备案记录
	 * @param list
	 *            是MaterialBomDetail型，报关常用BOM里的料件
	 */
	List addMaterialBomDetailApply(Request request,
			MaterialBomVersionApply bomVersionApply,
			List<MaterialBomDetail> list);

	/**
	 * 保存电子手册物料表头
	 * 
	 * @param request
	 *            请求控制
	 * @param dzscMaterielHead
	 *            电子手册物料表头
	 * 
	 */
	void saveDzscMaterielHead(Request request, DzscMaterielHead dzscMaterielHead);

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
	List findMaterialBomApplyMateriel(Request request, boolean isChange,
			int index, int length, String property, Object value, Boolean isLike);

	/**
	 * 根据料件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	List findMaterialBomApplyByDetail(Request request, boolean isChange,
			Materiel materiel);

	/**
	 * 查询被归并禁用的物料
	 * 
	 * @param materialType
	 * @return
	 */
	List findForbidMergeMaterialApply(Request request, String materialType);

	/**
	 * 取消物料被归并禁用
	 * 
	 * @param list
	 */
	List cancelForbidMergeMaterialApply(Request request, List list);

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	void exportQPMaterialMessage(Request request, String messageFileName,
			TempExpQPMaterialMsgSelectParam param);

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	void exportQPBomMessage(Request request, String messageFileName,
			TempExpQPBomMsgSelectParam param);
	/**
	 * 构造BOM备案版本
	 * 
	 * @param parentNo
	 *            料号
	 * @return List 是TempEntBomVersion型，临时的BOM备案版本
	 */
	public List findMaterialBomVersionApplyByPtNo(Request request,String parentNo);
}
