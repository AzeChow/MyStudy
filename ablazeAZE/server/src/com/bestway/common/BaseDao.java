/*
 * Created on 2004-6-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.NullableType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;

/**
 * @author bsway // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */

public class BaseDao extends HibernateDaoSupport {

	protected void initDao() throws Exception {
		super.initDao();

		this.getHibernateTemplate().setCacheQueries(false);
	}

	/**
	 * 获得分页 List 来自带多个参数的 hsql 语句
	 */
	public List findPageList(String hsql, Object[] objParams, int index,
			int length) {
		return this.findList(hsql, objParams, index, length);
	}

	/**
	 * 获得分页 List 来自带多一个参数的 hsql 语句
	 */
	public List findPageList(String hsql, Object objParam, int index, int length) {
		Object[] objParams = { objParam };
		return this.findList(hsql, objParams, index, length);
	}

	/**
	 * 获得分页 List 来自带没有参数的 hsql 语句
	 */
	public List findPageList(String hsql, int index, int length) {
		return this.findList(hsql, null, index, length);
	}

	/**
	 * 获得分页 List 来自带多个?来代替名字参数的 hsql 语句
	 */
	public List find(String hsql, Object[] objParams) {
		return this.findList(hsql, objParams, -1, -1);
	}

	/**
	 * 获得分页 List 来自带一个?来代替名字参数的 hsql 语句
	 */
	public List find(String hsql, Object objParam) {
		Object[] objParams = { objParam };
		return this.findList(hsql, objParams, -1, -1);
	}

	/**
	 * 获得所有的数据来自无参数的 hsql
	 */
	public List find(String hsql) {
		return this.findList(hsql, null, -1, -1);
	}

	public List find(String tableName, String sFields, String sValue) {
		String hsql = null;
		List list = null;
		if (sFields == null || sFields.equals("")) {

			hsql = "select a from " + tableName + " a";
			list = this.getHibernateTemplate().find(hsql);
		} else {
			hsql = "select a from " + tableName + " a where a." + sFields
					+ " like ? ";
			list = this.getHibernateTemplate().find(hsql,
					new Object[] { "%" + sValue + "%" });
		}
		return list;
	}

	/**
	 * 查询海关基础资料的数据
	 * 
	 * @param tableName
	 *            海关基础资料 类的名称
	 * @param sFields
	 *            字段名
	 * @param sValue
	 *            字段值
	 * @return
	 */
	public List findCustoms(String tableName, String sFields, String sValue) {
		String hsql = null;
		List list = null;
		if (sFields == null || sFields.equals("")) {
			hsql = "select a from " + tableName
					+ " a where (a.isOut <> '1' or a.isOut is null)";
			list = this.getHibernateTemplate().find(hsql);
		} else {
			hsql = "select a from " + tableName
					+ " a where (a.isOut <> '1' or a.isOut is null) and a."
					+ sFields + " like ? ";
			list = this.getHibernateTemplate().find(hsql,
					new Object[] { "%" + sValue + "%" });
		}
		return list;
	}

	/**
	 * 获得 List 来自带多个?来代替名字参数的 hsql 语句
	 */
	public List findBeginToEndList(String hsql, Object[] objParams) {
		return this.findList(hsql, objParams, -1, -1);
	}

	/**
	 * 获得 List 来自带一个?来代替名字参数的 hsql 语句
	 */
	public List findBeginToEndList(String hsql, Object objParam) {
		Object[] objParams = { objParam };
		return this.findList(hsql, objParams, -1, -1);
	}

	/**
	 * 获得从某行开始到最后的数据来自无参数的 hsql
	 */
	public List findBeginToEndList(String hsql) {
		return this.findList(hsql, null, -1, -1);
	}

	/**
	 * 查询(没用缓存)
	 * 
	 * @param hsql
	 * @param objParams
	 * @return
	 */
	public List findListNoCache(final String hsql, final Object[] objParams) {
		return this.findListNoCache(hsql, objParams, -1, -1);
	}

	/**
	 * 查询(没用缓存)
	 * 
	 * @param hsql
	 * @return
	 */
	public List findListNoCache(final String hsql) {
		return this.findListNoCache(hsql, -1, -1);
	}

	/**
	 * 公共分页查询方法
	 */
	private List findList(final String hsql, final Object[] objParams,
			final int index, final int length) {
		if ((index == -1) & (length == -1)) {
			if (objParams == null) {
				return getHibernateTemplate().find(hsql);
			} else {
				return getHibernateTemplate().find(hsql, objParams);
			}
		}

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				// List list = new ArrayList();
				Query query = session.createQuery(hsql).setCacheable(false);
				if (objParams != null) {
					for (int i = 0; i < objParams.length; i++) {
						query.setParameter(i, objParams[i]);
					}
				}
				if (index != -1) {
					query.setFirstResult(index);
				}
				if (length != -1) {
					query.setMaxResults(length);
				}
				return query.list();
			}
		});
	}

	/**
	 * 公共分页查询方法
	 */
	public List findListNoCache(final String hsql, final Object[] objParams,
			final int index, final int length) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				// List list = new ArrayList();
				session.setCacheMode(CacheMode.IGNORE);
				Query query = session.createQuery(hsql).setCacheable(false);
				if (objParams != null) {
					for (int i = 0; i < objParams.length; i++) {
						query.setParameter(i, objParams[i]);
					}
				}
				if (index != -1) {
					query.setFirstResult(index);
				}
				if (length != -1) {
					query.setMaxResults(length);
				}
				return query.list();
			}
		});
	}

	/**
	 * 公共分页查询方法
	 */
	public List findListNoCache(final String hsql, final int index,
			final int length) {
		return findListNoCache(hsql, null, index, length);
	}

	/**
	 * 公共分页查询方法
	 */
	public List findNoCache(final String hsql, final Object[] objParams) {
		return findListNoCache(hsql, objParams, -1, -1);
	}

	/**
	 * 批量修改不用cache
	 */
	public int batchSaveOrUpdate(final List list) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						session.setCacheMode(CacheMode.IGNORE);
						int size = list.size();
						int m = 0;
						List tlsit = new ArrayList();
						for (int i = 0; i < size; i++, m++) {
							if (m == 1000) {
								session.flush();
								session.clear();
								for (int j = 0; j < tlsit.size(); j++) {
									session.evict(tlsit.get(j));// 从缓存中删除，为了节省内存
								}
								tlsit.clear();
								m = 0;
								System.gc();
							}
							Object obj = list.get(i);
							if (obj instanceof BaseScmEntity) {
								if (((BaseScmEntity) obj).getCompany() == null) {
									((BaseScmEntity) obj)
											.setCompany(CommonUtils
													.getCompany());
								}
							}
							session.saveOrUpdate(obj);
							tlsit.add(obj);

						}
						session.flush();
						session.clear();
						for (int j = 0; j < tlsit.size(); j++) {
							session.evict(tlsit.get(j));// 从缓存中删除，为了节省内存
						}
						tlsit.clear();
						m = 0;
						System.gc();
						return size;
					}
				});
	}

	/**
	 * 修改不用cache
	 */
	public Object saveOrUpdateNoCache(final Object obj) {
		if (obj instanceof BaseScmEntity) {
			if (((BaseScmEntity) obj).getCompany() == null) {
				((BaseScmEntity) obj).setCompany(CommonUtils.getCompany());
			}
		}
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				session.setCacheMode(CacheMode.IGNORE);

				if (((BaseEntity) obj).getId() == null) {
					session.persist(obj);
				} else {
					session.merge(obj);
					session.flush();
				}
				// session.saveOrUpdate(obj);
				// session.flush();
				// session.clear();
				return obj;
			}
		});
	}

	/**
	 * 批量修改或者批量删除
	 */
	public int batchUpdateOrDelete(final String hsql, final Object[] objParams) {

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = session.createQuery(hsql);
						if (objParams != null) {
							for (int i = 0; i < objParams.length; i++) {
								query.setParameter(i, objParams[i]);
							}
						}
						return query.executeUpdate();
					}
				});
	}

	/**
	 * 批量修改或者批量删除 一般情况下不要使用，只有特殊情况下才可使用！ 走的是JDBC，不经过hibernate API
	 */
	public void batchUpdateOrDeleteByJDBC(final String hsql,
			final Object[] objParams) {
		Transaction tx = this.getSessionFactory().openSession()
				.beginTransaction();
		Connection con = this.getSessionFactory().openSession().disconnect();
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(hsql);
			for (int i = 0; i < objParams.length; i++) {
				if (objParams[i] instanceof String) {
					stmt.setString(i, (String) objParams[i]);
				} else if (objParams[i] instanceof Double) {
					stmt.setDouble(i, (Double) objParams[i]);
				} else if (objParams[i] instanceof Date) {
					Date dt = (Date) objParams[i];
					java.sql.Date date = new java.sql.Date(dt.getTime());
					stmt.setDate(i, date);
				} else {
					throw new RuntimeException("使用JDBC过程中出现错误！");
				}
			}
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tx.commit();
	}

	/**
	 * 批量修改或者批量删除
	 */
	public int batchUpdateOrDeleteInSql(final String sql,
			final List<List<Object>> list, final int batchSize) {

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Connection connection = null;
						try {
							connection = session.disconnect();
							// connection.setAutoCommit(true);
							PreparedStatement stmt = connection
									.prepareStatement(sql);
							int size = list.size();
							if (size == 0) {
								stmt.executeBatch();
								stmt.clearBatch();
							} else {
								for (int i = 0; i < size; i++) {
									List objParams = list.get(i);
									for (int j = 0; j < objParams.size(); j++) {
										stmt.setObject(j + 1, objParams.get(j));
									}
									stmt.addBatch();
									if ((i % batchSize) == 0 || i == size - 1) {
										stmt.executeBatch();
										stmt.clearBatch();
									}
								}
							}
							stmt.close();
							return size;
						} catch (Exception ex) {
							throw new RuntimeException(ex);
						} finally {
							try {
								if (connection != null
										&& !connection.isClosed()) {
									connection.close();
								}
							} catch (Exception ex) {
								throw new RuntimeException(ex);
							}
						}
					}
				});
	}

	/**
	 * 批量修改或者批量删除
	 */
	public int batchUpdateOrDelete(String hsql) {
		return batchUpdateOrDelete(hsql, null);
	}

	/**
	 * 批量修改或者批量删除
	 */
	public int batchUpdateOrDelete(String hsql, Object objParam) {
		return batchUpdateOrDelete(hsql, new Object[] { objParam });
	}

	public Object load(final Class entityClass, final Serializable id) {
		// try {
		// return this.getHibernateTemplate().load(entityClass, id);
		// } catch (Exception e) {
		// }
		// return null;
		// 改成get解决load生成代理对象的问题，千万不要改回去了，
		return this.getHibernateTemplate().get(entityClass, id);
	}

	public Object get(final Class entityClass, final Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 为了提高性能，不用判断相关联栏位，直接保存
	 * 
	 * @param obj
	 */
	public void saveOrUpdateDirect(Object obj) {
		if (obj instanceof BaseScmEntity) {
			if (((BaseScmEntity) obj).getCompany() == null) {
				((BaseScmEntity) obj).setCompany(CommonUtils.getCompany());
			}
		}
		if (obj instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) obj;
			if (entity.getId() == null || "".equals(entity.getId().trim())) {
				if (entity.getCreateDate() == null
						|| "".equals(entity.getCreateDate())) {
					entity.setCreateDate(new Date());
					entity.setCreatePeople(CommonUtils.getAclUser() == null ? ""
							: CommonUtils.getAclUser().getUserName());
				}
			} else {
				entity.setModifyDate(new Date());
				entity.setModifyPeople(CommonUtils.getAclUser() == null ? ""
						: CommonUtils.getAclUser().getUserName());
			}
		}
		this.getHibernateTemplate().saveOrUpdate(obj);
	}

	/**
	 * 保存实体对象 防止传递过来的对象和缓存中的对象的版本不一致
	 * 
	 * @param obj
	 */
	public void saveOrUpdate(Object obj) {
		if (obj == null) {
			return;
		}
		if (obj instanceof BaseEntity) {
			BaseEntity entity = (BaseEntity) obj;
			if (entity.getId() == null || "".equals(entity.getId().trim())) {
				if (entity.getCreateDate() == null
						|| "".equals(entity.getCreateDate())) {
					entity.setCreateDate(new Date());
					entity.setCreatePeople(CommonUtils.getAclUser() == null ? ""
							: CommonUtils.getAclUser().getUserName());
				}
			} else {
				entity.setModifyDate(new Date());
				entity.setModifyPeople(CommonUtils.getAclUser() == null ? ""
						: CommonUtils.getAclUser().getUserName());
			}
		}
		if (obj instanceof Complex || obj instanceof EnterpriseMaterial) {
			saveAssignedEntity(obj);
			return;
		}
		if (obj instanceof CustomBaseEntity) {
			CustomBaseEntity oldVersionObj = (CustomBaseEntity) obj;
			CustomBaseEntity lastVersionObj = null;
			Class entityCls = oldVersionObj.getClass();
			if (oldVersionObj instanceof HibernateProxy) {
				entityCls = ((HibernateProxy) oldVersionObj)
						.getHibernateLazyInitializer().getPersistentClass();
			}
			try {
				lastVersionObj = (CustomBaseEntity) this.getHibernateTemplate()
						.get(entityCls, oldVersionObj.getCode());
			} catch (Exception e) {
				throw new NotFoundDataException(oldVersionObj.getClass()
						.getName());
			}
			if (lastVersionObj != null) {
				try {
					PropertyUtils.copyProperties(lastVersionObj, oldVersionObj);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				this.getHibernateTemplate().update(lastVersionObj);
			} else {
				lastVersionObj = oldVersionObj;
				this.getHibernateTemplate().save(lastVersionObj);
			}
			obj = lastVersionObj;
		} else if (obj instanceof BaseEntity) {
			BaseEntity oldVersionObj = (BaseEntity) obj;
			BaseEntity lastVersionObj = null;
			if (oldVersionObj.getId() == null
					|| oldVersionObj.getId().trim().equals("")) {
				if (obj instanceof BaseScmEntity) {
					if (((BaseScmEntity) obj).getCompany() == null) {
						((BaseScmEntity) obj).setCompany(CommonUtils
								.getCompany());
					}
				}
				this.getHibernateTemplate().saveOrUpdate(obj);
			} else {
				Class entityCls = oldVersionObj.getClass();
				if (oldVersionObj instanceof HibernateProxy) {
					entityCls = ((HibernateProxy) oldVersionObj)
							.getHibernateLazyInitializer().getPersistentClass();
				}
				try {
					lastVersionObj = (BaseEntity) this.getHibernateTemplate()
							.get(entityCls, oldVersionObj.getId());
				} catch (Exception e) {
					e.printStackTrace();
					// System.out.println("------------ssssssssssssssss------------"+oldVersionObj.getClass()
					// .getName());
					throw new NotFoundDataException(oldVersionObj.getClass()
							.getName());
				}
				if (lastVersionObj == null) {
					throw new NotFoundDataException(oldVersionObj.getClass()
							.getName());
				}
				try {
					PropertyUtils.copyProperties(lastVersionObj, oldVersionObj);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				if (lastVersionObj instanceof BaseScmEntity) {
					if (((BaseScmEntity) lastVersionObj).getCompany() == null) {
						((BaseScmEntity) lastVersionObj).setCompany(CommonUtils
								.getCompany());
					}
				}
				this.getHibernateTemplate().saveOrUpdate(lastVersionObj);
				obj = lastVersionObj;
			}
		}
	}

	/**
	 * 保存继承自BaseEntity但是id是自定义Assigned的
	 * 
	 * @param obj
	 */
	private void saveAssignedEntity(Object obj) {
		BaseEntity oldVersionObj = (BaseEntity) obj;
		BaseEntity lastVersionObj = null;
		try {
			lastVersionObj = (BaseEntity) this.getHibernateTemplate().get(
					oldVersionObj.getClass(), oldVersionObj.getId());
		} catch (Exception e) {
		}
		if (lastVersionObj != null) {
			try {
				PropertyUtils.copyProperties(lastVersionObj, oldVersionObj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			this.getHibernateTemplate().update(lastVersionObj);
		} else {
			lastVersionObj = oldVersionObj;
			this.getHibernateTemplate().save(lastVersionObj);
		}
		obj = lastVersionObj;
	}

	/**
	 * 删除实体对象 防止传递过来的对象和缓存中的对象的版本不一致
	 * 
	 * @param obj
	 */
	public void delete(Object obj) {
		if (obj == null) {
			return;
		}
		if (obj instanceof CustomBaseEntity) {
			CustomBaseEntity oldVersionObj = (CustomBaseEntity) obj;
			CustomBaseEntity lastVersionObj = null;
			try {
				lastVersionObj = (CustomBaseEntity) this.getHibernateTemplate()
						.get(oldVersionObj.getClass(), oldVersionObj.getCode());
			} catch (Exception e) {
			}
			if (lastVersionObj == null) {
				return;
			}
			this.getHibernateTemplate().delete(lastVersionObj);
		} else if (obj instanceof BaseEntity) {
			BaseEntity oldVersionObj = (BaseEntity) obj;
			BaseEntity lastVersionObj = null;
			try {
				lastVersionObj = (BaseEntity) this.getHibernateTemplate().get(
						oldVersionObj.getClass(), oldVersionObj.getId());
			} catch (Exception e) {
			}
			if (lastVersionObj == null) {
				return;
			}
			this.getHibernateTemplate().delete(lastVersionObj);
		}
	}

	/**
	 * 删除实体对象 防止传递过来的对象和缓存中的对象的版本不一致
	 * 
	 * @param entities
	 *            Collection类型
	 */
	public void deleteAll(Collection entities) {
		Iterator iterator = entities.iterator();
		while (iterator.hasNext()) {
			this.delete(iterator.next());
		}
	}

	/**
	 * 根据{fieldName}和{fieldValue}抓取 实体 {entityClassName}的对象。此实体对象不用公司区分
	 * 
	 * @param entityClassName
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Object findCustomBaseEntity(String entityClassName,
			String fieldName, Object fieldValue) {
		List list = this.find("select a from " + entityClassName + " as a "
				+ "where a." + fieldName + "= ?", new Object[] { fieldValue });
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List findCustomBaseEntityList(String entityClassName) {
		List list = this.find("select a from " + entityClassName + " a");
		return list;
	}

	/**
	 * 根据{fieldName}和{fieldValue}抓取 实体 {entityClassName}的对象。此实体对象用公司区分
	 * 
	 * @param entityClassName
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Object findBaseScmEntity(String entityClassName, String fieldName,
			Object fieldValue) {
		List list = this.find("select a from " + entityClassName + " as a "
				+ "where a.company= ?  and a." + fieldName + "= ?",
				new Object[] { CommonUtils.getCompany(), fieldValue });
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * wss:2010.04.22使用原生SQL查询方法(试用）
	 */
	public List findListBySQL(final String sql,
			final Map<String, NullableType> backTypeMap,
			final Object[] objParams, final int index, final int length) {
		System.out.println("wss测试: 有没有执行到findListBySQL 这个方法中？？？");
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(sql);// .setCacheable(false);

				// 返回列的类型
				if (backTypeMap != null) {
					for (String str : backTypeMap.keySet()) {
						sqlQuery = sqlQuery.addScalar(str, backTypeMap.get(str));
					}
				}

				// 接受日期参数
				if (objParams != null) {
					for (int i = 0; i < objParams.length; i++) {
						if (objParams[i] instanceof Date) {
							sqlQuery.setTimestamp(i, (Date) objParams[i]);// 这样子好不好呢？不好
							continue;
						}
						sqlQuery.setParameter(i, objParams[i]);
					}
				}

				if (index != -1) {
					sqlQuery.setFirstResult(index);
				}
				if (length != -1) {
					sqlQuery.setMaxResults(length);
				}
				System.out.println("wss 测试 构思sqlQuery.list().size() = "
						+ sqlQuery.list().size());
				return sqlQuery.list();
			}
		});
	}

	/**
	 * 根据查询函数与参数列表创建Query对象,后续可进行更多处理,辅助函数.
	 * 
	 * @author CHL 2011-6-10
	 */
	protected Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query queryObject = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}

	/**
	 * <<<<<<< .mine 分页查询查询来自类型
	 */
	public List findPageListByClazz(String clazz, int index, int length,
			String[] properties, Object[] values, Boolean[] isLikes) {
		if ((properties.length != values.length)
				|| (values.length != isLikes.length)) {
			throw new AppException("属性长度与值的长度与条件不匹配!!!");
		}
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from " + clazz + " as a where 1=1 ";
		for (int i = 0; i < properties.length; i++) {
			String property = properties[i];
			Object value = values[i];
			Boolean isLike = isLikes[i];
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hsql += " and  a." + property + " like ?  ";
					paramters.add("%" + value + "%");
				} else {
					hsql += " and  a." + property + " = ?  ";
					paramters.add(value);
				}
			}
		}
		return findPageList(hsql, paramters.toArray(), index, length);
	}

	/**
	 * 分页查询查询来自类型的总数
	 */
	public Long findPageListByClazzCount(String clazz, int index, int length,
			String[] properties, Object[] values, Boolean[] isLikes) {
		if ((properties.length != values.length)
				|| (values.length != isLikes.length)) {
			throw new AppException("属性长度与值的长度与条件不匹配!!!");
		}
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select count(a.id) from " + clazz + " as a where 1=1 ";
		for (int i = 0; i < properties.length; i++) {
			String property = properties[i];
			Object value = values[i];
			Boolean isLike = isLikes[i];
			if (property != null && !"".equals(property) && value != null) {
				if (isLike) {
					hsql += " and  a." + property + " like ?  ";
					paramters.add("%" + value + "%");
				} else {
					hsql += " and  a." + property + " = ?  ";
					paramters.add(value);
				}
			}
		}
		List list = find(hsql, paramters.toArray());
		if (list != null && !list.isEmpty()) {
			return list.get(0) == null ? 0L : (Long) list.get(0);
		}
		return 0L;
	}
}