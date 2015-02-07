/*
 * Created on 2005-7-19
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.backup.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BackupDao extends BaseDao {
	// public Object load(final Class entityClass, final Serializable id) {
	// return this.getHibernateTemplate().load(entityClass, id);
	// }

	/**
	 * 取得实体类名称来自公司名称
	 * 
	 * @param className
	 *            类名称
	 * @param company
	 *            公司名称
	 * @return 实体类名称
	 */
	public List findEntityByCompany(String className, Company company) {
		return this.findListNoCache("select a from " + className
				+ " as a where a.company.id=? ",
				new Object[] { company.getId() });
	}

	/**
	 * 取得实体类来自id
	 * 
	 * @param className
	 *            实体类名称
	 * @param id
	 *            实体类id
	 * @return 实体类名称
	 */
	public List findEntityById(String className, String id) {
		return this.find("select a from " + className + " as a where a.id=? ",
				new Object[] { id });
	}

	/**
	 * 查询已经存在的实体CODE
	 * 
	 * @param className
	 * @return
	 */
	public List findCustomBaseEntityExistCode(String className) {
		return this.find("select a.code from " + className + " as a ");
	}

	/**
	 * 查询已经存在的实体ID
	 * 
	 * @param className
	 * @return
	 */
	public List findBaseEntityExistId(String className) {
		return this.find("select a.id from " + className + " as a ");
	}

	// /**
	// * 取得实体类个数来自代号
	// * @param className 实体类名称
	// * @param code 代号
	// * @return 模块个数
	// */
	// public int findEntityCountByCode(String className, String code) {
	// return ((Integer) this.find(
	// "select count(*) from " + className + " as a where a.code=? ",
	// new Object[] { code }).iterator().next()).intValue();
	// }

	// /**
	// * 取得实体类个数来自id
	// * @param className 实体类名称
	// * @param id 实体类id
	// * @return 模块实体类个数
	// */
	// public int findEntityCountById(String className, String id) {
	// return ((Integer) this.find(
	// "select count(*) from " + className + " as a where a.id=? ",
	// new Object[] { id }).iterator().next()).intValue();
	// }

	/**
	 * 取得所有的模块
	 * 
	 * @param className
	 *            模块实体类名称
	 * @return 所有模块
	 */
	public List findAllEntity(String className) {
		return this.findListNoCache("select a from " + className + " as a");
	}

	/**
	 * 删除所有模块实体类
	 * 
	 * @param className
	 *            模块实体类
	 */
	public void deleteAllEntity(String className) {
		// this.getHibernateTemplate().deleteAll(
		// this.getHibernateTemplate().find("select a from " + className+"as
		// a"));
		this.batchUpdateOrDelete("delete from " + className);
	}

	/**
	 * 删除所有模块实体类来自id
	 * 
	 * @param className
	 *            模块实体类
	 * @param id
	 *            实体类id
	 */
	public void deleteEntityById(String className, String id) {
		// this.getHibernateTemplate().deleteAll(
		// this.getHibernateTemplate().find(
		// "select a from " + className + " as a where a.id=?",
		// new Object[] { id }));
		this.batchUpdateOrDelete("delete from " + className + "  where id=?",
				new Object[] { id });
	}

	/**
	 * 删除所有模块实体类
	 * 
	 * @param className
	 *            模块所有实体类
	 * @param company
	 *            公司名称
	 */
	public void deleteEntityByCompany(String className, Company company) {
		// this.getHibernateTemplate().deleteAll(
		// this.getHibernateTemplate().find(
		// "select a from " + className + " as a where a.company.id=?",
		// new Object[] { company.getId() }));
		this.batchUpdateOrDelete("delete from " + className
				+ " where company=?", new Object[] { company });
	}

	/**
	 * 保存所有模块
	 * 
	 * @param entity
	 *            模块实体类
	 * @param id
	 *            模块实体类id
	 */
	public void save(final Object entity, final Serializable id) {
		// this.getHibernateTemplate()
		// .replicate(entity, ReplicationMode.OVERWRITE);
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.setCacheMode(CacheMode.IGNORE);
				session.replicate(entity, ReplicationMode.OVERWRITE);
				session.flush();
				session.clear();
				return entity;
			}
		});
	}
}
