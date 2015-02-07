/*
 * Created on 2004-11-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.outsource.logic;

import com.bestway.common.outsource.dao.OutsourceDao;

/**
 * @author Administrator
 * 委外
 */
public class OutsourceLogic {
	/**
	 *委外Dao 
	 */
	private OutsourceDao outsourceDao = null;

	/**
	 * 取得委外Dao内容
	 * @return 委外Dao
	 */
	public OutsourceDao getOutsourceDao() {
		return outsourceDao;
	}

	/**
	 * 设计委外Dao内容
	 * @param outsourceDao 委外Dao
	 */
	public void setOutsourceDao(OutsourceDao outsourceDao) {
		this.outsourceDao = outsourceDao;
	}

}