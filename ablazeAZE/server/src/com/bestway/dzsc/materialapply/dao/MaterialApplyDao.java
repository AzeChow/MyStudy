package com.bestway.dzsc.materialapply.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.dzsc.materialapply.entity.BomDetailHistory;
import com.bestway.dzsc.materialapply.entity.BomVersionHistory;
import com.bestway.dzsc.materialapply.entity.MaterialApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomDetailApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomMasterApply;
import com.bestway.dzsc.materialapply.entity.MaterialBomVersionApply;
import com.bestway.dzsc.materialapply.entity.MaterialChange;
import com.bestway.dzsc.materialapply.entity.MaterialHistory;

/**
 * com.bestway.dzsc.materialapply.dao.MaterialApplyDao
 * 
 * @author yp
 * 
 */
public class MaterialApplyDao extends BaseDao {
	/**
	 * 抓取物料表头
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findDzscMaterialHead() {
		return this.find(
				"select a from DzscMaterielHead as a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓取还没有备案的物料
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findMaterialNotInApply(String scmCoiCode) {
		return this.find(
				"select a from Materiel as a left join fetch a.complex "
						+ " where a.id not in (select b.materiel.id"
						+ " from MaterialApply as b where b.company.id=? "
						+ " and b.materiel.scmCoi.code=? )"
						+ " and a.id not in (select b.customMaterialId "
						+ " from MaterialChange as b where b.company.id=? "
						+ " and b.scmCoi.code=? )"
						+ " and a.company.id=? and a.scmCoi.code=? ",
				new Object[] { CommonUtils.getCompany().getId(), scmCoiCode,
						CommonUtils.getCompany().getId(), scmCoiCode,
						CommonUtils.getCompany().getId(), scmCoiCode });
	}

	// /**
	// * 抓取还没有备案的物料
	// *
	// * @param scmCoiCode
	// * 物料类别编码
	// * @return List 是Materiel型，报关常用物料
	// */
	// public List findMaterial(String scmCoiCode) {
	// return this.find(
	// "select a from Materiel as a left join fetch a.complex "
	// + " where a.company.id=? and a.scmCoi.code=? ",
	// new Object[] { CommonUtils.getCompany().getId(), scmCoiCode });
	// }

	/**
	 * 抓取还没有备案的物料
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findMaterialIdInApply(String scmCoiCode) {
		return this.find("select b.materiel.id"
				+ " from MaterialApply as b where b.company.id=? "
				+ " and b.materiel.scmCoi.code=? ", new Object[] {
				CommonUtils.getCompany().getId(), scmCoiCode });
	}

	/**
	 * 抓取还没有备案的物料
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是Materiel型，报关常用物料
	 */
	public List findMaterialIdInChange(String scmCoiCode) {
		return this.find("select b.customMaterialId "
				+ " from MaterialChange as b where b.company.id=? "
				+ " and b.scmCoi.code=? ", new Object[] {
				CommonUtils.getCompany().getId(), scmCoiCode });
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
	public List findMaterialApply(String materialTypeCode, int index,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from MaterialApply as a"
				+ " left join fetch a.materiel "
				+ " where a.materiel.scmCoi.code=? and a.company.id=? ";
		paramters.add(materialTypeCode);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.no";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找电子手册物料基础资料
	 * 
	 * @return List 是MaterialApply型，电子手册物料基础资料
	 */
	public List findMaterialApply(String materialType) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from MaterialApply as a"
				+ " left join fetch a.materiel "
				+ " where a.materiel.scmCoi.coiProperty=? and a.company.id=? ";
		paramters.add(materialType);
		paramters.add(CommonUtils.getCompany().getId());
		hsql += " order by a.no";
		return this.find(hsql, paramters.toArray());
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
	public List findMaterialApplied(String materialType) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from MaterialApply as a"
				+ " left join fetch a.materiel "
				+ " where a.materiel.scmCoi.coiProperty=? and a.stateMark=?"
				+ " and a.company.id=? ";
		paramters.add(materialType);
		paramters.add(DzscState.EXECUTE);
		paramters.add(CommonUtils.getCompany().getId());
		hsql += " order by a.no";
		return this.find(hsql, paramters.toArray());
	}

	/**
	 * 抓取物料备案最大流水号
	 * 
	 * @param materialTypeCode
	 * @return
	 */
	public int findMaterielApplyMaxNo(String materialTypeCode) {
		List list = this.find("select max(a.no) from MaterialApply a "
				+ " where a.materiel.scmCoi.code=? and a.company.id = ?",
				new Object[] { materialTypeCode,
						CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 查询异常的物料备案资料
	 * 
	 * @return
	 */
	public List findExceptionMaterialApply() {
		return this.find("select a.materiel from MaterialApply a "
				+ " where (a.materiel.complex is null"
				+ " or a.materiel.ptUnit is null) "
				+ " and a.stateMark=? and a.company.id = ?", new Object[] {
				DzscState.ORIGINAL, CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询异常的物料备案变更资料
	 * 
	 * @return
	 */
	public List findExceptionMaterialChange() {
		return this.find("select a from MaterialChange a "
				+ " where (a.complex is null or a.ptUnit is null) "
				+ " and a.stateMark=?  and a.company.id = ?", new Object[] {
				DzscState.CHANGE, CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓取物料备案最大流水号
	 * 
	 * @param materialTypeCode
	 * @return
	 */
	public List findMaterielApplyPtNo(ScmCoi scmCoi) {
		return this
				.find("select a.materiel.ptNo from MaterialApply a "
						+ " where a.materiel.scmCoi.id=? and a.company.id = ?",
						new Object[] { scmCoi.getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓取物料备案变更最大流水号
	 * 
	 * @param materialTypeCode
	 * @return
	 */
	public int findMaterielChangeMaxNo(String materialTypeCode) {
		List list = this.find("select max(a.no) from MaterialChange a "
				+ " where a.scmCoi.code=? and a.company.id = ?", new Object[] {
				materialTypeCode, CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 查找电子手册物料基础资料 来自报关常用物料
	 * 
	 * @param materialId
	 *            报关常用物料
	 * @return MaterialApply 返回符合统计的第一条数据
	 */
	public MaterialApply findMaterialApplyByMaterialId(String materialId) {
		List list = this.find("select a from MaterialApply a "
				+ " left join fetch a.materiel "
				+ " where a.materiel.id = ? and a.company.id = ?",
				new Object[] { materialId, CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (MaterialApply) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 抓取物料备案已生效个数
	 * 
	 * @param materialTypeCode
	 * @return
	 */
	public int findMaterielEffectiveCount() {
		List list = this.find("select count(a.id) from MaterialApply a "
				+ " where a.stateMark=? and a.company.id = ?", new Object[] {
				DzscState.EXECUTE, CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 保存电子手册物料基础资料 来自电子手册物料基础资料
	 * 
	 * @param materialApply
	 *            电子手册物料基础资料
	 */
	public void saveMaterialApply(MaterialApply materialApply) {
		this.saveOrUpdate(materialApply);
	}

	/**
	 * 删除电子手册物料基础资料 来自电子手册物料基础资料
	 * 
	 * @param materialApply
	 *            电子手册物料基础资料
	 */
	public void deleteMaterialApply(MaterialApply materialApply) {
		this.delete(materialApply);
	}

	/**
	 * 查询电子手册物料基础资料历史纪录
	 * 
	 * @param materialId
	 *            报关常用物料ID
	 * @return List 是MaterialHistory类型，电子手册物料基础资料历史纪录
	 */
	public List findMaterialHistory(String materialId) {
		List list = this.find("select a from MaterialHistory a "
				+ " left join fetch a.complex " + " left join fetch a.scmCoc "
				+ " where a.customMaterialId = ? and a.company.id = ?",
				new Object[] { materialId, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 保存电子手册物料基础资料历史纪录
	 * 
	 * @param materielHistory
	 *            电子手册物料基础资料历史纪录
	 */
	public void saveMaterialHistory(MaterialHistory materielHistory) {
		this.saveOrUpdate(materielHistory); // 物料主档保存
	}

	/**
	 * 删除电子手册物料基础资料历史纪录
	 * 
	 * @param materielHistory
	 *            电子手册物料基础资料历史纪录
	 */
	public void deleteMaterialHistory(MaterialHistory materielHistory) { // 物料主档删除
		this.delete(materielHistory);
	}

	/**
	 * 查询电子手册物料基础资料（未申请）
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是MaterialApply类型，电子手册物料基础资料（未申请）
	 */
	public List findOriginalMaterial(String materialType) {
		List list = this
				.find(
						"select b  from MaterialApply b left join fetch b.materiel "
								+ " where  b.materiel.scmCoi.coiProperty=? and b.stateMark=? "
								+ " and b.company.id=?", new Object[] {
								materialType, DzscState.ORIGINAL,
								CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询未生效的电子手册物料基础资料（已申请未生效）
	 * 
	 * @param scmCoiCode
	 *            物料类别编码
	 * @return List 是MaterialApply类型，电子手册物料基础资料（已申请未生效）
	 */
	public List findNotEffectiveMaterial() {
		List list = this.find(
				" select b from MaterialApply b left join fetch b.materiel "
						+ " where b.stateMark=? " + " and b.company.id=?",
				new Object[] { DzscState.APPLY,
						CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询已生效的电子手册物料基础资料
	 * 
	 * @return List 是MaterialApply类型，电子手册物料基础资料
	 */
	public List findMaterialApplyExingPtNo(String materielType) {
		List list = this.find(" select b.materiel.ptNo from MaterialApply b "
				+ " where b.materiel.scmCoi.coiProperty=? and b.stateMark=? "
				+ " and b.company.id=?", new Object[] { materielType,
				DzscState.EXECUTE, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询已生效的电子手册物料基础资料
	 * 
	 * @return List
	 */
	public List findEffectivedMaterialApplyPtNo() {
		List list = this.find(" select b.materiel.ptNo from MaterialApply b "
				+ " where b.stateMark=? " + " and b.company.id=?",
				new Object[] { DzscState.EXECUTE,
						CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 保存电子手册物料基础资料变更数据
	 * 
	 * @param materialChange
	 *            电子手册物料基础资料变更数据
	 */
	public void saveMaterialChange(MaterialChange materialChange) {
		this.saveOrUpdate(materialChange);
	}

	/**
	 * 删除电子手册物料基础资料变更数据
	 * 
	 * @param materialChange
	 *            电子手册物料基础资料变更数据
	 */
	public void deleteMaterialChange(MaterialChange materialChange) {
		this.delete(materialChange);
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
	public List findMaterialChange(String materialTypeCode, int index,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = " select a from MaterialChange as a"
				+ " left join fetch a.complex " + " left join fetch a.scmCoc "
				+ " where a.scmCoi.code=? and a.company.id=? ";
		paramters.add(materialTypeCode);
		paramters.add(CommonUtils.getCompany().getId());
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.no";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 查找电子手册物料基础资料变更数据 来自报关常用物料Id
	 * 
	 * @param materialId
	 *            报关常用物料Id
	 * @return List 是MaterialChange型，电子手册物料基础资料变更数据
	 */
	public List findMaterialChangeByMaterialId(String materialId) {
		List list = this.find(" select b from MaterialChange b "
				+ " where  b.customMaterialId=?  " + " and b.company.id=? ",
				new Object[] { materialId, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询未申报的物料（已变更未生效）
	 * 
	 * @param scmCoiCode
	 *            料件类别编码
	 * @return List 是MaterialChange型，电子手册物料基础资料变更数据
	 */
	public List findNotApplyMaterialChange(String materialType) {
		List list = this.find(" select b from MaterialChange b "
				+ " where b.scmCoi.coiProperty=? and b.stateMark=? "
				+ " and b.modifyMark<>? and b.company.id=? ", new Object[] {
				materialType, DzscState.CHANGE, ModifyMarkState.UNCHANGE,
				CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询未生效的物料（变更已申请未生效）
	 * 
	 * @param scmCoiCode
	 *            料件类别编码
	 * @return List 是MaterialChange型，电子手册物料基础资料变更数据
	 */
	public List findNotEffectiveMaterialChange() {
		List list = this.find(" select b from MaterialChange b "
				+ " where b.stateMark=? and b.company.id=?", new Object[] {
				DzscState.APPLY, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查找物料主档来自类别-->进出口报关申请单
	 * 
	 * @param materielType
	 *            物料类别属性
	 * @return List 是MaterialApply型，电子手册物料基础资料
	 */
	public List findMaterialByType(String materielType) {
		List list = this
				.find(
						"select b from MaterialApply b left join fetch b.materiel"
								+ " where ( b.materiel.scmCoi.coiProperty=? or b.materiel.scmCoi.coiProperty=? ) "
								+ " and b.company.id=?", new Object[] {
								materielType,
								MaterielType.SEMI_FINISHED_PRODUCT,
								CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 抓取还没有备案的单耗
	 * 
	 * @return List 是MaterialBomMaster型，报关常用BOM
	 */
	public List findMaterialBomNotInApply() {
		return this
				.find(
						"select a from MaterialBomMaster as a left join fetch a.materiel "
								+ " where a.materiel.id not in (select b.materiel.id "
								+ " from MaterialBomMasterApply as b where b.company.id=? )"
								+ " and a.company.id=? ", new Object[] {
								CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId() });
	}

	/**
	 * 抓取报关常用BOM
	 * 
	 * @return List 是MaterialBomMaster型，报关常用BOM
	 */
	public List findMaterialBomMaster() {
		return this.find(
				"select a from MaterialBomMaster as a left join fetch a.materiel "
						+ " where  a.company.id=? ", new Object[] { CommonUtils
						.getCompany().getId() });
	}

	/**
	 * 抓取报关常用BOM
	 * 
	 * @return List 是MaterialBomMaster型，报关常用BOM
	 */
	public List findMaterialBomMasterApply() {
		return this.find(
				"select a from MaterialBomMasterApply as a left join fetch a.materiel "
						+ " where  a.company.id=? ", new Object[] { CommonUtils
						.getCompany().getId() });
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
	public List findMaterialBomApply(int index, int length, String property,
			Object value, Boolean isLike, Boolean isChanged) {
		String hql = "select a from MaterialBomMasterApply a "
				+ " left join fetch a.materiel where a.company.id=? "
				+ " and a.isChanged=? ";
		List<Object> lsPrarm = new ArrayList<Object>();
		lsPrarm.add(CommonUtils.getCompany().getId());
		lsPrarm.add(isChanged);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hql += " and  a." + property + " like ?  ";
				lsPrarm.add(value + "%");
			} else {
				hql += " and  a." + property + " = ?  ";
				lsPrarm.add(value);
			}
		}
		List list = this.findPageList(hql, lsPrarm.toArray(), index, length);
		return list;
	}

	public MaterialBomMasterApply findMaterialBomApply(String ptNo,
			boolean isChanged) {
		String hql = "select a from MaterialBomMasterApply a "
				+ " left join fetch a.materiel where a.company.id=? "
				+ " and a.isChanged=? and a.materiel.ptNo=? ";
		List<Object> lsPrarm = new ArrayList<Object>();
		lsPrarm.add(CommonUtils.getCompany().getId());
		lsPrarm.add(isChanged);
		lsPrarm.add(ptNo);
		List list = this.find(hql, lsPrarm.toArray());
		if (list.size() > 0) {
			return (MaterialBomMasterApply) list.get(0);
		}
		return null;
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
	public List findMaterialBomVersionApply(String bomMasterPtNo) {
		String hql = "select a from MaterialBomVersionApply a "
				+ " where a.company.id=? ";
		List<Object> lsPrarm = new ArrayList<Object>();
		lsPrarm.add(CommonUtils.getCompany().getId());
		if (bomMasterPtNo != null && !"".equals(bomMasterPtNo.trim())) {
			hql += " and a.bomMasterApply.materiel.ptNo like ? ";
			lsPrarm.add("%" + bomMasterPtNo.trim() + "%");
		}
		hql += " order by a.bomMasterApply.materiel.ptNo,a.version ";
		List list = this.find(hql, lsPrarm.toArray());
		return list;
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
	public List findMaterialBomVersionApply(
			MaterialBomMasterApply bomMasterApply) {
		List list = this.find("select a from MaterialBomVersionApply a "
				+ " where a.company.id=? and a.bomMasterApply.id=? "
				+ " order by a.version ", new Object[] {
				CommonUtils.getCompany().getId(), bomMasterApply.getId() });
		return list;
	}

	/**
	 * 读取BOM版本备案记录
	 * 
	 * @param bomMasterApply
	 *            BOM备案记录
	 * @param version
	 *            版本号
	 * @param isChanged
	 *            判断是否改变，true表示已改变了
	 * @return MaterialBomVersionApply BOM版本备案记录,返回符合条件的第一条
	 */
	public MaterialBomVersionApply findMaterialBomVersionApply(
			MaterialBomMasterApply bomMasterApply, Integer version,
			Boolean isChanged) {
		List list = this
				.find(
						"select a from MaterialBomVersionApply a "
								+ " where a.company.id=? and a.isChanged=? and a.bomMasterApply.id=? "
								+ " and a.version=? ", new Object[] {
								CommonUtils.getCompany().getId(), isChanged,
								bomMasterApply.getId(), version });
		if (list.isEmpty()) {
			return null;
		} else {
			return (MaterialBomVersionApply) list.get(0);
		}
	}

	/**
	 * 读取BOM版本备案记录
	 * 
	 * @param materiel
	 *            物料
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	public List findMaterialBomVersionApply(Materiel materiel) {
		List list = this.find("select a from MaterialBomVersionApply a "
				+ " where a.company.id=? and a.bomMasterApply.isChanged=? "
				+ " and a.bomMasterApply.materiel.id=? "
				+ " and a.bomMasterApply.stateMark=? order by a.version",
				new Object[] { CommonUtils.getCompany().getId(), false,
						materiel.getId(), DzscState.EXECUTE });
		return list;
	}

	/**
	 * 读取BOM备案记录
	 * 
	 * @return List 是MaterialBomMasterApply型，BOM备案记录
	 */
	public List findMaterialBomMasterFromApply() {
		List list = this.find(
				"select a.bomMaster from MaterialBomMasterApply a "
						+ " left join fetch a.materiel"
						+ " where a.company.id=?", new Object[] { CommonUtils
						.getCompany().getId() });
		return list;
	}

	/**
	 * 保存BOM备案记录
	 * 
	 * @param materialBomApply
	 *            BOM备案记录
	 */
	public void saveMaterialBomApply(MaterialBomMasterApply materialBomApply) {
		this.saveOrUpdate(materialBomApply);
	}

	/**
	 * 删除BOM备案记录
	 * 
	 * @param materialBomApply
	 *            BOM备案记录
	 */
	public void deleteMaterialBomApply(MaterialBomMasterApply materialBomApply) {
		this.delete(materialBomApply);
	}

	/**
	 * 保存BOM版本备案记录
	 * 
	 * @param materialBomVersionApply
	 *            BOM版本备案记录
	 */
	public void saveMaterialBomVersionApply(
			MaterialBomVersionApply materialBomVersionApply) {
		this.saveOrUpdate(materialBomVersionApply);
	}

	/**
	 * 删除BOM版本备案记录
	 * 
	 * @param materialBomVersionApply
	 *            BOM版本备案记录
	 */
	public void deleteMaterialBomVersionApply(
			MaterialBomVersionApply materialBomVersionApply) {
		this.delete(materialBomVersionApply);
	}

	/**
	 * 查询BOM料件备案记录
	 * 
	 * @param versionApply
	 *            BOM版本备案记录
	 * @return List 是MaterialBomDetailApply，BOM料件备案记录
	 */
	public List findMaterialBomDetailApply(MaterialBomVersionApply versionApply) {
		String hql = "select a from MaterialBomDetailApply a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.versionApply "
				+ " left join fetch a.versionApply.bomMasterApply "
				+ " left join fetch a.versionApply.bomMasterApply.materiel "
				+ " where a.versionApply.id=? and a.company.id=? ";
		return this.find(hql, new Object[] { versionApply.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 查询BOM料件备案记录
	 * 
	 * @param versionApply
	 *            BOM版本备案记录
	 * @return List 是MaterialBomDetailApply，BOM料件备案记录
	 */
	public List findMaterialBomDetailApply(String ptNo, Integer versionNo) {
		String hql = "select a from MaterialBomDetailApply a "
				+ " left join fetch a.materiel "
				+ " left join fetch a.versionApply "
				+ " left join fetch a.versionApply.bomMasterApply "
				+ " left join fetch a.versionApply.bomMasterApply.materiel "
				+ " where a.versionApply.bomMasterApply.materiel.ptNo=? "
				+ " and a.versionApply.version=? and a.company.id=? ";
		return this.find(hql, new Object[] { ptNo, versionNo,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 保存BOM料件备案记录
	 * 
	 * @param materialBomDetailApply
	 *            BOM料件备案记录
	 */
	public void saveMaterialBomDetailApply(
			MaterialBomDetailApply materialBomDetailApply) {
		this.saveOrUpdate(materialBomDetailApply);
	}

	/**
	 * 删除BOM料件备案记录
	 * 
	 * @param materialBomDetailApply
	 *            BOM料件备案记录
	 */
	public void deleteMaterialBomDetailApply(
			MaterialBomDetailApply materialBomDetailApply) {
		this.delete(materialBomDetailApply);
	}

	/**
	 * 查询BOM备案中正在执行的个数
	 * 
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	public int findMaterialBomApplyEffectiveCount() {
		List list = this.find(
				" select count(b.id) from MaterialBomDetailApply b "
						+ " where b.versionApply.bomMasterApply.stateMark=? "
						+ " and b.versionApply.bomMasterApply.isChanged=? "
						+ " and b.company.id=?", new Object[] {
						DzscState.EXECUTE, false,
						CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 查询原始的物料单耗（未申请）
	 * 
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	public List findOriginalMaterialBomApply() {
		List list = this.find(" select b from MaterialBomDetailApply b "
				+ " where b.versionApply.bomMasterApply.stateMark=? "
				+ " and b.versionApply.bomMasterApply.isChanged=? "
				+ " and b.company.id=?", new Object[] { DzscState.ORIGINAL,
				false, CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询原始的物料单耗（未申请）
	 * 
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	public List findChangedMaterialBomApply() {
		List list = this.find(" select b from MaterialBomDetailApply b "
				+ " where b.versionApply.bomMasterApply.stateMark=? "
				+ " and b.versionApply.bomMasterApply.isChanged=? "
				+ " and b.company.id=?", new Object[] { DzscState.CHANGE, true,
				CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询未生效的物料单耗（已申请未生效）
	 * 
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	public List findNotEffectiveMaterialBomApply() {
		// List list = this.find(" select b from MaterialBomDetailApply b "
		// + " where b.stateMark=? and b.isChanged=? "
		// + " and b.company.id=?", new Object[] { DzscState.APPLY, false,
		// CommonUtils.getCompany().getId() });
		// return list;
		List list = this.find(" select b from MaterialBomDetailApply b "
				+ " where b.versionApply.bomMasterApply.stateMark=? "
				+ " and b.versionApply.bomMasterApply.isChanged=? "
				+ " and b.company.id=?", new Object[] { DzscState.APPLY, false,
				CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询物料单耗（已存在）
	 * 
	 * @return List 是String型，物料的料号
	 */
	public List findMaterialBomMasterApplyPtNo() {
		List list = this.find(" select distinct b.materiel.ptNo "
				+ " from MaterialBomMasterApply b where b.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询物料单耗
	 * 
	 * @return List 是String型，物料的料号
	 */
	public List findMaterialBomDetailApply() {
		List list = this.find(" select a from MaterialBomDetailApply a"
				+ "	left join fetch a.materiel "
				+ " left join fetch a.versionApply "
				+ " left join fetch a.versionApply.bomMasterApply "
				+ " left join fetch a.versionApply.bomMasterApply.materiel "
				+ " where a.company.id=?", new Object[] { CommonUtils
				.getCompany().getId() });
		return list;
	}

	/**
	 * 查询物料单耗（已存在）
	 * 
	 * @return List 是String型，物料的料号
	 */
	public List findMaterialBomDetailApplyPtNo() {
		List list = this.find(" select  distinct b.materiel.ptNo "
				+ " from MaterialBomDetailApply b where b.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询未生效的物料单耗（已变更申请未生效）
	 * 
	 * @return List 是MaterialBomVersionApply型，BOM版本备案记录
	 */
	public List findChangedNotEffectiveMaterialBomApply() {
		// List list = this.find(" select b from MaterialBomVersionApply b "
		// + " where b.stateMark=? and b.isChanged=? "
		// + " and b.company.id=?", new Object[] { DzscState.APPLY, true,
		// CommonUtils.getCompany().getId() });
		// return list;
		List list = this.find(" select b from MaterialBomDetailApply b "
				+ " where b.versionApply.bomMasterApply.stateMark=? "
				+ " and b.versionApply.bomMasterApply.isChanged=? "
				+ " and b.company.id=?", new Object[] { DzscState.APPLY, true,
				CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询BOM料件备案记录
	 * 
	 * @param versionApply
	 *            BOM版本备案记录
	 * @param materiel
	 *            物料
	 * @return MaterialBomDetailApply BOM料件备案记录，返回符合条件的第一条数据
	 */
	public MaterialBomDetailApply findMaterialBomDetailApply(
			MaterialBomVersionApply versionApply, Materiel materiel) {
		List list = this.find(" select b from MaterialBomDetailApply b "
				+ " left join fetch b.versionApply "
				+ " where b.versionApply.id=? and b.materiel.id=? ",
				new Object[] { versionApply.getId(), materiel.getId() });
		if (list.size() <= 0 || list.get(0) == null) {
			return null;
		} else {
			return (MaterialBomDetailApply) list.get(0);
		}
	}

	/**
	 * 保存BomVersionHistory
	 * 
	 * @param history
	 */
	public void saveBomVersionHistory(BomVersionHistory history) {
		this.saveOrUpdate(history);
	}

	/**
	 * 保存BomDetailHistory
	 * 
	 * @param history
	 */
	public void saveBomDetailHistory(BomDetailHistory history) {
		this.saveOrUpdate(history);
	}

	/**
	 * 查询没有在BOM备案中的Bom版本
	 * 
	 * @param master
	 *            BOM成品备案记录
	 * @return List 是MaterialBomVersion型，报关常用BOM里的版本号
	 */
	public List findMaterialBomVersionNotInApply(MaterialBomMasterApply master) {
		String hql = "select a from MaterialBomVersion a where a.version not in "
				+ " (select b.version from MaterialBomVersionApply b "
				+ " where b.bomMasterApply.materiel.id=?"
				+ " and b.company.id=? ) and a.company.id=? "
				+ " and a.master.materiel=? ";
		return this.find(hql, new Object[] { master.getMateriel().getId(),
				CommonUtils.getCompany().getId(),
				CommonUtils.getCompany().getId(), master.getMateriel() });
	}

	/**
	 * 查询没有在BOM备案的BOM料件
	 * 
	 * @param version
	 *            BOM版本备案记录
	 * @return List 是MaterialBomDetail型，报关常用BOM里的料件
	 */
	public List findMaterialBomDetailNotInApply(MaterialBomVersionApply version) {
		String hql = "select a from MaterialBomDetail a where a.materiel.id not in "
				+ " (select b.materiel.id from MaterialBomDetailApply b "
				+ " where b.versionApply.id=?"
				// + " and b.versionApply.bomMasterApply.id=? "
				+ " and b.company.id=? ) and a.company.id=? "
				+ " and a.version.version=? "
				+ " and a.version.master.materiel.id=? ";
		return this.find(hql, new Object[] {
				version.getId(),
				// version.getBomMasterApply().getId(),
				CommonUtils.getCompany().getId(),
				CommonUtils.getCompany().getId(), version.getVersion(),
				version.getBomMasterApply().getMateriel().getId() });
	}

	/**
	 * 查询已经存在内部归并的料号
	 * 
	 * @param materialType
	 * @return
	 */
	public List findExistingInnerMergePtNo(String materialType) {
		List list = this
				.find(" select a.materiel.ptNo from DzscInnerMergeData a "
						+ " where a.imrType=? " + " and a.company.id=?",
						new Object[] { materialType,
								CommonUtils.getCompany().getId() });
		return list;
	}

	/**
	 * 查询物料的单位换算率
	 * 
	 * @return
	 */
	public Map<String, Double> findInnerMergeUnitConvert() {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = this.find(
				" select distinct a.materiel.ptNo,a.unitConvert from DzscInnerMergeData a "
						+ " where a.company.id=?", new Object[] { CommonUtils
						.getCompany().getId() });
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] == null || objs[1] == null) {
				continue;
			}
			String ptNo = objs[0].toString().trim();
			Double unitConvert = Double.valueOf(objs[1].toString().trim());
			map.put(ptNo, unitConvert);
		}
		return map;
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
	public List findMaterialBomApplyMateriel(boolean isChange, int index,
			int length, String property, Object value, Boolean isLike) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select distinct a.materiel from MaterialBomDetailApply as a"
				+ " left join fetch a.materiel.complex "
				+ " left join fetch a.materiel.scmCoc"
				+ " left join fetch a.materiel.calUnit"
				+ " where a.materiel.company.id=? "
				+ " and a.versionApply.bomMasterApply.isChanged=? ";
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(isChange);
		if (property != null && !"".equals(property) && value != null) {
			if (isLike) {
				hsql += " and  a.materiel." + property + " like ?  ";
				paramters.add(value + "%");
			} else {
				hsql += " and  a.materiel." + property + " = ?  ";
				paramters.add(value);
			}
		}
		hsql += " order by a.materiel.ptNo";
		return this.findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 根据料件查询BOM
	 * 
	 * @param materiel
	 * @return
	 */
	public List findMaterialBomApplyByDetail(boolean isChange, Materiel materiel) {
		return this.find(
				"select a from MaterialBomDetailApply a where a.company.id = ?"
						+ " and a.materiel.id=? "
						+ " and a.versionApply.bomMasterApply.isChanged=? ",
				new Object[] { CommonUtils.getCompany().getId(),
						materiel.getId(), isChange });
	}

	/**
	 * 查询被归并禁用的物料
	 * 
	 * @param materialType
	 * @return
	 */
	public List findForbidMergeMaterialApply(String materialType) {
		return this.find("select a from MaterialApply a where a.company.id = ?"
				+ " and a.isForbidMerge=? ", new Object[] {
				CommonUtils.getCompany().getId(), true });
	}
	/**
	 * 查询申报成品的申报单耗版本（电子手册）
	 * 
	 * @param parentNo
	 *            料号
	 * @return List 是TempEntBomVersion型，临时的BOM备案版本
	 */
	public List findMaterialBomVersionApplyByPtNo(String parentNo) {
		return this
				.find("select distinct a.version from MaterialBomVersionApply a where a.company.id = ?"
						+ " and a.bomMasterApply.materiel.ptNo=? order by a.version ",
						new Object[]{CommonUtils.getCompany().getId(),parentNo});
	}
}
