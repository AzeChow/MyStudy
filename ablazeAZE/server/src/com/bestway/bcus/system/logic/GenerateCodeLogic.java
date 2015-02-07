/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bestway.bcus.system.dao.SysCodeDao;

import com.bestway.bcus.system.entity.SysCode;

/**
 * 不在使用状态
 * 
 * @author 001
 */
public class GenerateCodeLogic {
	private SysCodeDao sysCodeDao = null;

	public String generateAutoNo(Class entityClass) {
		String entityClassName = entityClass.getName();
		Date todayDate = null;
		Date currDate = null;

		int iMaxLength;
		int iYearFormat;
		int iMonthFormat;
		int iDayFormat;
		int iSeqFormat;
		int iMaxNo;
		String sPrefix = null;

		String sYear = null;
		String sMonth = null;
		String sDay = null;
		String sNewNo = null;

		int iNewNo;

		todayDate = (new Date());
		Calendar c = Calendar.getInstance();
		c.setTime(todayDate);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		todayDate = c.getTime();

		SysCode sysCodeEntity = sysCodeDao
				.getSysCodeEntityByName(entityClassName);
		if (sysCodeEntity == null) {
			throw new RuntimeException("[JBUCS] SysCode实体对应的表没有初始化!");
		}
		iMaxLength = sysCodeEntity.getMaxLength().intValue();
		sPrefix = sysCodeEntity.getPrefix();
		currDate = sysCodeEntity.getCurrDate();
		iYearFormat = sysCodeEntity.getYearLength().intValue();
		iMonthFormat = sysCodeEntity.getMonthLength().intValue();
		iDayFormat = sysCodeEntity.getDayLength().intValue();
		iSeqFormat = iMaxLength - sPrefix.length() - iYearFormat - iMonthFormat
				- iDayFormat;
		iMaxNo = sysCodeEntity.getSeq().intValue();
		
		
		
		if (changeDate(currDate).equals(changeDate(todayDate))) {
			iNewNo = iMaxNo + 1;
		} else {
			iNewNo = 1;
		}
		sysCodeEntity.setSeq(new Integer(iNewNo));
		sysCodeEntity.setCurrDate(changeDate(todayDate));
		sysCodeDao.update(sysCodeEntity);
		if (iYearFormat == 2) {
			SimpleDateFormat sdf = new SimpleDateFormat("yy");
			sYear = sdf.format(todayDate);
		} else if (iYearFormat == 4) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			sYear = sdf.format(todayDate);
		}

		if (iMonthFormat == 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("M");
			sMonth = sdf.format(todayDate);
		} else if (iMonthFormat == 2) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM");
			sMonth = sdf.format(todayDate);
		}

		if (iDayFormat == 1) {
			SimpleDateFormat sdf = new SimpleDateFormat("d");
			sDay = sdf.format(todayDate);
		} else if (iDayFormat == 2) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd");
			sDay = sdf.format(todayDate);
		}

		sNewNo = new Integer(iNewNo).toString();
		for (int i = 0; i <= (iSeqFormat - sNewNo.length()); i++) {
			sNewNo = '0' + sNewNo;
		}
		return sPrefix + sYear + sMonth + sDay + sNewNo;
	}

	public SysCodeDao getSysCodeDao() {
		return sysCodeDao;
	}

	public void setSysCodeDao(SysCodeDao sysCodeDao) {
		this.sysCodeDao = sysCodeDao;
	}
    private Date changeDate(Date date){
    	SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String defaultDate = bartDateFormat.format(date);
		return java.sql.Date.valueOf(defaultDate);
    }
}