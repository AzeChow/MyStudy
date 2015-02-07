/*
 * Created on 2004-9-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 盘点表表头
 * @author luosheng  checked 2008-11-29
 */
public class CheckMaster extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
	 * 盘点表表头流水号
	 */
	private Integer num;     
	/**
	 * 物料类型
	 * FINISHED_PRODUCT="0";	成品
	 * SEMI_FINISHED_PRODUCT="1";	半成品
	 * MATERIEL="2";	材料--主料
	 * MACHINE="3";	设备
	 * REMAIN_MATERIEL="4";	边角料
	 * BAD_PRODUCT="5";	残次品
	 * ASSISTANT_MATERIAL = "6";	辅料
	 * WASTE_MATERIAL = "7";	消耗料
	 */
	private Integer masterielType;   
	/**
	 * 盘点时间
	 */
	private Date checkDate;  
	/**
	 * 截止时间
	 */
	private Date endDate;   
	/**
	 * 仓库
	 */
	private WareSet wareSet; 
	/**
	 * 备注
	 */
	private String note;     
	/**
	 *是否作废
	 */
	private Boolean isDelete=false; 
	
	
	
	/**
	 * 取得盘点日期
	 * @return checkDate 盘点日期
	 */
	public Date getCheckDate() {
		return checkDate;
	}
	/**
	 * 设置盘点日期
	 * @param checkDate 盘点日期
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	/**取得物料类别(产成品原材料......)
	 * @return masterielType 物料类别(产成品 原材料......)
	 */
	public Integer getMasterielType() {
		return masterielType;
	}
	/**
	 * 设置物料类别(产成品 原材料.......)
	 * @param masterielType 物料类别(产成品 原材料......)
	 */
	public void setMasterielType(Integer masterielType) {
		this.masterielType = masterielType;
	}
	/**
	 * 取得备注
	 * @return note 备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置备注
	 * @param note 备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 取得盘点表表头流水号
	 * @return num 盘点表表头流水号
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * 设置盘点表表头流水号
	 * @param num 盘点表表头流水号
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * 取得仓库
	 * @return wareSet 仓库
	 */
	public WareSet getWareSet() {
		return wareSet;
	}
	/**
	 * 设置仓库
	 * @param wareSet 仓库
	 */
	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}
	/**判断是否作废  
	 * @return isDelete 是否作废
	 */
	public Boolean getIsDelete() {
		return isDelete;
	}
	/**
	 * 设置是否作废
	 * @param isDelete 是否作废
	 */
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	/**
	 * 取得截止日期
	 * @return endDate 截止日期
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * 设置截止日期
	 * @param endDate 截止日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}