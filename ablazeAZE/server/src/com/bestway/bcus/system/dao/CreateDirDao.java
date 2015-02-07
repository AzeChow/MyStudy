/*
 * Created on 2004-7-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.dao;

import java.util.List;

import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.ProjectDept;

/**
 * com.bestway.bcus.system.dao.CreateDirDao 统参数设置Dao
 * 
 * @author 001
 */
public class CreateDirDao extends BaseDao {

	/**
	 * 根据选项查找系统参数设置资料
	 * 
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	public List findnameValues(int type) {

		String hql = "select a from ParameterSet a "
				+ "left join fetch a.company "
				+ "where a.company= ? and a.type=?";

		return find(hql, new Object[] { CommonUtils.getCompany(), type });
	}

	/**
	 * 查询BCS参数
	 * 
	 * @param parameter
	 */
	public BcsParameterSet findBcsParameterSet() {
		List list = find(
				"select a from BcsParameterSet a where a.company.id=? ",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return (BcsParameterSet) list.get(0);
		} else {
			BcsParameterSet parameterSet = new BcsParameterSet();
			this.saveOrUpdate(parameterSet);
			return parameterSet;
		}
	}

	/**
	 * 查询bcus参数
	 * 
	 * @param obj
	 */
	public List findBcusnameValues(int type) {

		return find(
				"select a from BcusParameter a left join fetch a.company where a.company= ? and a.type=?",
				new Object[] { CommonUtils.getCompany(), type });
	}

	public void saveDept(ProjectDept obj) {
		this.saveOrUpdate(obj);
	}

	public ProjectDept getProjectDept() {

		List list = find(
				"select a from ProjectDept a where a.code  = ? and a.company = ?",
				new Object[] { "", CommonUtils.getCompany() });

		if (list != null && list.size() > 0) {

			return (ProjectDept) list.get(0);

		}
		return null;
	}

	/**
	 * 保存系统参数设置资料
	 * 
	 * @param parameterSet
	 *            系统参数设置资料
	 */
	public void saveValues(ParameterSet parameterSet) {

		saveOrUpdate(parameterSet);

	}

	/**
	 * 保存BCS参数
	 * 
	 * @param parameter
	 */
	public void saveBcsParameterSet(BcsParameterSet parameter) {

		saveOrUpdate(parameter);

	}

	/**
	 * 保存BCUS参数
	 * 
	 * @param parameter
	 */
	public void saveBcusParameterSet(BcusParameter parameter) {

		saveOrUpdate(parameter);

	}

	/**
	 * 取得BCuS参数
	 * 
	 * @param parameter
	 */
	public List getBparaList(int type) {

		String hql = "select a from BcusParameter a where a.type = ? and a.company.id = ?";

		return find(hql,
				new Object[] { type, CommonUtils.getCompany().getId() });

	}

	/**
	 * 删除系统参数设置资料
	 * 
	 * @param parameterSet
	 *            系统参数设置资料
	 */
	public void deleteParameterSet(ParameterSet parameterSet) {

		delete(parameterSet);

	}
}
