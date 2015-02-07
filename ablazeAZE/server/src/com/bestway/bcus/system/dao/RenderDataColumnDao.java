/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.dao;

import java.util.List;

import com.bestway.bcus.system.entity.RenderDataColumn;
import com.bestway.common.BaseDao;

/**
 * 显示数据列Dao
 * 
 */
public class RenderDataColumnDao extends BaseDao {

	/**
	 * 查找所有存在的 Render Data Column
	 * 
	 * @return
	 */
	public List<RenderDataColumn> findRenderDataColumn() {
		return super.findNoCache("from RenderDataColumn", null);
	}

	/**
	 * 保存呈现数据列表
	 * 
	 * @param list
	 */
	public void saveOrUpdate(List<RenderDataColumn> list) {
		for (RenderDataColumn rdc : list) {
			this.saveOrUpdate(rdc);
		}
	}

	/**
	 * 保存呈现数据列表
	 * 
	 * @param list
	 */
	public void deleteByClassName(String className, String companyId) {
		super
				.batchUpdateOrDelete(
						"delete from RenderDataColumn a where  a.company.id = ? and a.className = ? ",
						new Object[] { companyId, className });
	}
}
