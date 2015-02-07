package com.bestway.bcus.checkstock.dao;

import java.util.List;

import com.bestway.bcus.checkstock.entity.ECSBaseResolve;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

/**
 * 工厂库存数据分析 DAO
 * 
 * @author chl
 */
public class ECSFactoryAnalyseDao extends BaseDao {

	// -------------------------------查询方法 开始-----------------------------//
	public <T extends ECSBaseResolve> List<T> findECSBaseResolveByECSSection(
			ECSSection section, Class<T> clazz) {
		return this.findECSBaseResolveByECSSection(section, clazz, null);
	}

	@SuppressWarnings("unchecked")
	public <T extends ECSBaseResolve> List<T> findECSBaseResolveByECSSection(
			ECSSection section, Class<T> clazz, Integer seqNum) {
		String hql = "select e from " + clazz.getName() + " e" +
				" join fetch e.section" +
				" where e.section.id = ?";
		if (seqNum != null) {
			hql += " and e.seqNum = " + seqNum;
		}
		return super
				.find(hql + " order by e.serialNumber ", section.getId());
	}
	/**
	 * 根据公司，查询工厂 成品BOM 表,并加载对应的料件
	 * 
	 * @return
	 */
	public List<MaterialBomVersion> findMaterialBomVersions() {
		return find(
				"SELECT m FROM MaterialBomVersion m join fetch m.master join fetch m.master.materiel WHERE m.company.id = ? order by m.version desc",
				CommonUtils.getCompany().getId());
	}
	// -------------------------------查询方法 结束-----------------------------//

	// -------------------------------保存方法 开始-----------------------------//

	// -------------------------------保存方法 结束-----------------------------//

	// -------------------------------删除方法 开始-----------------------------//

	// -------------------------------删除方法 结束-----------------------------//
}
