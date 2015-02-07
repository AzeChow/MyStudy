/*
 * Created on 2004-11-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 浏览委外加工单 1
 保存委外加工单 2
 删除委外加工单 3
 
 浏览海关委外申请加工单 4
 委外明细 5

 浏览委外受托发料收货退料管理 6
 保存委外受托发料收货退料管理 7
 删除委外受托发料收货退料管理 8

 浏览受托加工单 9
 保存受托加工单 10
 删除受托加工单 11


 委外受托余量表 12
 委外受托汇总表 13
 */
package com.bestway.common.outsource.action;

import com.bestway.common.outsource.dao.OutsourceDao;
import com.bestway.common.outsource.logic.OutsourceLogic;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class OutsourceActionImpl implements OutsourceAction {
	/**
	 * 委外Dao
	 */
	private OutsourceDao	outsourceDao	= null;
	/**
	 * 委外Logic
	 */
	private OutsourceLogic	outsourceLogic	= null;

	/**
	 * 取得委外Dao内容
	 * 
	 * @return 委外Dao
	 */
	public OutsourceDao getOutsourceDao() {
		return outsourceDao;
	}

	/**
	 * 设计委外Dao内容
	 * 
	 * @param outsourceDao
	 *            委外Dao
	 */
	public void setOutsourceDao(OutsourceDao outsourceDao) {
		this.outsourceDao = outsourceDao;
	}

	/**
	 * 取得委外Logic内容
	 * 
	 * @return 委外Logic
	 */
	public OutsourceLogic getOutsourceLogic() {
		return outsourceLogic;
	}

	/**
	 * 设计委外Logic内容
	 * 
	 * @param outsourceLogic
	 *            委外Logic
	 */
	public void setOutsourceLogic(OutsourceLogic outsourceLogic) {
		this.outsourceLogic = outsourceLogic;
	}
}