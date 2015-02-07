/*
 * Created on 2004-12-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BillType;


/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class DbCasDao extends HibernateDaoSupport {	
	private CasDao casDao = null;
	
	protected void initDao() throws Exception {
		super.initDao();	
	}
	
	
	/*
	 * 保存海关帐单据类型
	 */	
	
	public void saveBillType(BillType billType){
		this.getHibernateTemplate().save(billType);
	
	}
	
	
	/*
	 * 保存海关帐单据类型
	 */	
	public void saveBillType(List billTypeList){
		for(int i=0;i<billTypeList.size();i++){
			this.getHibernateTemplate().save(billTypeList.get(i));
		}		
	}
		
	
	
	/*
	 * 保存海关帐单据
	 */	
	public void saveBillMaster(BillMaster billMaster){
		this.getHibernateTemplate().save(billMaster);
		
	}		
	
	

	
	
	
	
	/*
	 * 保存海关帐单据明细
	 */	
	public void saveBillDetail(BillDetail billDetail){
		this.getHibernateTemplate().save(billDetail);
		
	}
		
	
	
	/*
	 * 保存海关帐单据明细
	 */	
	public void saveBillDetail(List billDetailList){
		for(int i=0;i<billDetailList.size();i++){
			this.getHibernateTemplate().save(billDetailList.get(i));
		}		
	}
	
	
	
	
	/**
	 * @return Returns the casDao.
	 */
	public CasDao getCasDao() {
		return casDao;
	}
	/**
	 * @param casDao The casDao to set.
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}
}
