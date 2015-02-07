/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractexe.entity;

import java.io.Serializable;

import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.common.CommonUtils;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;

/**
 * 临时实体类,TempDzscImpExpCommodityInfo,存放临时的报关单商品信息
 * 
 * @author Administrator
 */
public class TempDzscImpExpCommodityInfo implements Serializable {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	
	/**
	 * 判断是否给选中，true为选中
	 * 
	 */
	private Boolean				isSelected			= false;
	
	/**
	 * 报关申请单物料
	 * 
	 */
	private ImpExpCommodityInfo impExpCommodityInfo = null;
	
	/**
	 * 手册通关备案成品
	 * 
	 */
	private DzscEmsExgBill		dzscEmsExgBill		= null;
	
	/**
	 * 手册通关备案料件
	 * 
	 */
	private DzscEmsImgBill		dzscEmsImgBill		= null;

	/**
	 * 获取手册通关备案成品
	 * 
	 * @return dzscEmsExgBill 手册通关备案成品
	 */
	public DzscEmsExgBill getDzscEmsExgBill() {
		return dzscEmsExgBill;
	}

	/**
	 * 设置手册通关备案成品
	 * 
	 * @param dzscEmsExgBill 手册通关备案成品
	 */
	public void setDzscEmsExgBill(DzscEmsExgBill dzscEmsExgBill) {
		this.dzscEmsExgBill = dzscEmsExgBill;
	}

	/**
	 * 获取手册通关备案料件
	 * 
	 * @return dzscEmsImgBill 手册通关备案料件
	 */
	public DzscEmsImgBill getDzscEmsImgBill() {
		return dzscEmsImgBill;
	}

	/**
	 * 设置手册通关备案料件
	 * 
	 * @param dzscEmsImgBill 手册通关备案料件
	 */
	public void setDzscEmsImgBill(DzscEmsImgBill dzscEmsImgBill) {
		this.dzscEmsImgBill = dzscEmsImgBill;
	}

	/**
	 * 获取判断是否给选中，true为选中
	 * 
	 * @return isSelected 判断是否给选中，true为选中
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * 设置判断是否给选中，true为选中
	 * 
	 * @param isSelected 判断是否给选中，true为选中
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * 获取报关申请单物料
	 * 
	 * @return impExpCommodityInfo 报关申请单物料
	 */
	public ImpExpCommodityInfo getImpExpCommodityInfo() {
		return impExpCommodityInfo;
	}

	/**
	 * 设置报关申请单物料
	 * 
	 * @param impExpCommodityInfo 报关申请单物料
	 */
	public void setImpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
		this.impExpCommodityInfo = impExpCommodityInfo;
	}
}