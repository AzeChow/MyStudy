/*
 * Created on 2004-6-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.custombase.dao;

import java.util.List;

import com.bestway.bcus.custombase.entity.CustomBaseVersion;
import com.bestway.common.BaseDao;

/**
 * @author 001
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class CustomBaseVersionDao extends BaseDao {
	public CustomBaseVersion findCustomBaseVersion(String tableName) {

		List list = this.find(
				"select a from CustomBaseVersion a where a.tableName = ?",
				tableName);
		if (list.size() > 0)
			return (CustomBaseVersion) list.get(0);
		else
			return null;
	}

	public List findCustomBaseVersion() {
		List list = this
				.find("select a from CustomBaseVersion a order by a.version");
		return list;
	}

	/**
	 * 根据{fieldName}和{fieldValue}抓取 实体 {entityClassName}的对象。此实体对象不用公司区分
	 *
	 * @param entityClassName
	 * @param fieldName
	 * @param fieldValue
	 * @param isLike
	 * @return
	 */
	public List findCustomBaseEntity(String entityClassName, String fieldName,
			Object fieldValue, boolean isLike) {

		StringBuilder jpql = new StringBuilder("select a from "
				+ entityClassName + " as a");

		if (fieldName == null || "".equals(fieldValue)) {

			return this.findPageList(jpql.toString(), -1, -1);

		} else {

			if (isLike) {

				jpql.append(" where a.").append(fieldName).append(" like ?");

				fieldValue = "%" + fieldValue.toString() + "%";

			} else {

				jpql.append(" where a.").append(fieldName).append(" = ?");

			}

			return this.findPageList(jpql.toString(), fieldValue, -1, -1);
		}
	}
}
