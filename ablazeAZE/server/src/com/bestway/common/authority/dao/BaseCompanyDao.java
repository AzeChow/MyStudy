/*
 * Created on 2004-7-1
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.authority.dao;

import java.util.List;

import com.bestway.common.BaseDao;
import com.bestway.common.authority.entity.BaseCompany;

/**
 * @author 001
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BaseCompanyDao extends BaseDao {
	
	  public List findCompanies() {
	    return this.find("select a from BaseCompany a");
	  }
	  
	  public void saveCompany(BaseCompany abstractCompany){
	  	this.saveOrUpdate(abstractCompany);
	  }
}
