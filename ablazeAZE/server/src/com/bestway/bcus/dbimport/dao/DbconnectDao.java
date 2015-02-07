/*
 * Created on 2004-12-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bestway.bcus.dbimport.entity.DBDataConnect;


/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class DbconnectDao extends HibernateDaoSupport {
	
	/**
	 * 
	 */
	public DbconnectDao() {
		super();
	}
	
	protected  void initDao() throws Exception{
		super.initDao();
		this.getHibernateTemplate().setCacheQueries(true);
	}
	public List findDbConnect() {
		return super.getHibernateTemplate().find("select a from DBDataConnect a ");
	}
    public void saveDBDataConnect(DBDataConnect db) {
        getHibernateTemplate().saveOrUpdate(db);
    }

}
