/*
 * Created on 2004-9-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.tools.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.stat.EntityStatistics;

import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.tools.entity.EntityInfo;
import com.bestway.common.tools.entity.FtpConfig;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates SQL,HSQL操作DAO
 *         checked by 陈井彬 2008-12.6
 */
public class ToolsDao extends BaseDao {
	/**
	 * 运行HSQL
	 * 
	 * @param hql
	 * @return
	 */
	public List execHql(String hql) {
		return this.find(hql);
	}

	/**
	 * 执行SQL
	 * 
	 * @param hql
	 * @return
	 */
	public int delete(String hql) {
		return this.delete(hql);
	}

	/**
	 * 批处理运行HSQL更新或删除
	 * 
	 * @param hql
	 * @return
	 */
	public int execute(String hql) {
		return this.batchUpdateOrDelete(hql);
	}

	/**
	 * 获得实体信息
	 * 
	 * @return
	 */
	public List getEntityInfo() {
		List<ClassMetadata> list = new ArrayList<ClassMetadata>();
		List<EntityInfo> entityInfolist = new ArrayList<EntityInfo>();
		try {
			list.addAll(getHibernateTemplate().getSessionFactory()
					.getAllClassMetadata().values());
		} catch (HibernateException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			ClassMetadata classMetadata = (ClassMetadata) list.get(i);
			EntityInfo ei = new EntityInfo();
			EntityStatistics entityStatistics = getHibernateTemplate()
					.getSessionFactory().getStatistics().getEntityStatistics(
							classMetadata.getEntityName());
			ei.setName(classMetadata.getEntityName());
			ei.setDeleteCount(entityStatistics.getDeleteCount());
			ei.setFetchCount(entityStatistics.getFetchCount());
			ei.setInsertCount(entityStatistics.getInsertCount());
			ei.setLoadCount(entityStatistics.getLoadCount());
			ei.setOptimisticFailureCount(entityStatistics
					.getOptimisticFailureCount());
			ei.setUpdateCount(entityStatistics.getUpdateCount());
			entityInfolist.add(ei);
		}
		return entityInfolist;
	}

	/**
	 * 查找FTP配置
	 * 
	 * @param request
	 * @return
	 */
	public FtpConfig findFtpConfig() {
		List list = this.find(
				"select a from FtpConfig a where a.company.id= ? ", CommonUtils
						.getCompany().getId());
		if (list.size() > 0) {
			return (FtpConfig) list.get(0);
		}
		return null;
	}

	/**
	 * 保存FTP配置
	 * 
	 * @param request
	 * @return
	 */
	public void saveFtpConfig(FtpConfig ftpConfig) {
		super.saveOrUpdate(ftpConfig);
	}

	/**
	 * 获取类元数据类型
	 * 
	 * @param cls
	 * @return
	 */
	public ClassMetadata getClassMetadata(Class cls) {
		return getHibernateTemplate().getSessionFactory().getClassMetadata(cls);

	}
}
