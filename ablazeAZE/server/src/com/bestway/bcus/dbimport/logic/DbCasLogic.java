/*
 * Created on 2005-4-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dbimport.logic;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.dbimport.dao.DbCasDao;

/**
 * @author ls
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DbCasLogic {
	private DbCasDao dbCasDao  = null;
	private CasDao casDao = null;

	/**
	 * @return Returns the casDao.
	 */
	public CasDao getCasDao() {
		return casDao;
	}
	/**
	 * @return Returns the dbCasDao.
	 */
	public DbCasDao getDbCasDao() {
		return dbCasDao;
	}
	/**
	 * @param casDao The casDao to set.
	 */
	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}
	/**
	 * @param dbCasDao The dbCasDao to set.
	 */
	public void setDbCasDao(DbCasDao dbCasDao) {
		this.dbCasDao = dbCasDao;
	}
	
	
	
	
	
	private long billNo = 0;
	/*
	 * 保存海关帐单据
	 */	
	public void saveBillMaster(BillMaster billMaster){
		if(billNo == 0){
			billNo = Long.parseLong(casDao.getBillNo(billMaster.getBillType().getBillType()));
		}else{
			billNo ++;
		}
		billMaster.setBillNo(String.valueOf( billNo));
		this.dbCasDao.saveBillMaster(billMaster);
	}	
	
	
	
	
	/*
	 * 保存海关帐单据
	 */	
	public void saveBillMaster(List billMasterList){
		for(int i=0;i<billMasterList.size();i++){
			this.saveBillMaster((BillMaster)billMasterList.get(i));
		}		
	}
	
	
	
	
}
