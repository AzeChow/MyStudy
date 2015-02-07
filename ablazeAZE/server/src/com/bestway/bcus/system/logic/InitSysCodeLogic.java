/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.logic;

import java.util.Calendar;
import java.util.Date;

import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.system.dao.SysCodeDao;
import com.bestway.bcus.system.entity.SysCode;
import com.bestway.common.materialbase.entity.ProjectDept;

/**
 * 不在使用状态
 * 
 * @author 001
 *
 */
public class InitSysCodeLogic {
	public SysCodeDao sysCodeDao = null;
	

	public void init(){
		SysCode sysCode = null;
		if(sysCodeDao.getSysCodeEntityByName(EmsEdiTrHead.class.getName())==null){
			newRecord(EmsEdiTrHead.class,"TR",4,2,2,12);
		}
		if(sysCodeDao.getSysCodeEntityByName(EmsEdiMergerHead.class.getName())==null){
			newRecord(EmsEdiMergerHead.class,"EH",4,2,2,12);
		}
		if(sysCodeDao.getSysCodeEntityByName(EmsHeadH2k.class.getName())==null){
			newRecord(EmsHeadH2k.class,"EM",2,2,2,12);
		}
		if(sysCodeDao.getSysCodeEntityByName(EmsHeadH2kFas.class.getName())==null){
			newRecord(EmsHeadH2kFas.class,"FAS",2,2,2,12);
		}
	}
	
	
	private void newRecord(Class entityClass,String prefix, int yearLength,int monthLength, 
			int dayLength, int maxLength) {
		SysCode sysCode;
		sysCode = new SysCode();
		sysCode.setEntityClassName(entityClass.getName());
		
		Date tempDate = new Date();

		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		tempDate = c.getTime();
		
		sysCode.setCurrDate(tempDate);
		sysCode.setYearLength(new Integer(yearLength));
		sysCode.setMonthLength(new Integer(monthLength));
		sysCode.setDayLength(new Integer(dayLength));
		sysCode.setMaxLength(new Integer(maxLength));
		sysCode.setPrefix(prefix);
		sysCode.setSeq(new Integer(0));
		sysCodeDao.save(sysCode);
	}
	
	public SysCodeDao getSysCodeDao() {
		return sysCodeDao;
	}
	public void setSysCodeDao(SysCodeDao sysCodeDao) {
		this.sysCodeDao = sysCodeDao;
	}
}
