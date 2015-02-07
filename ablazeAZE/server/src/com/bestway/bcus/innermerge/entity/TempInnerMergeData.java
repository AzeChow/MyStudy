/*
 * Created on 2007-07-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.innermerge.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * 临时实体类，存放临时的内部归并
 * 
 * @author fhz
 */
public class TempInnerMergeData implements Serializable{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * isChoose
	 */
	private Boolean isSelected=false;
	
	/**
	 * 关联的物料基本资料
	 */
	private InnerMergeData innerMergeData=null;

	/**
	 * @return the choose
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * @param choose the choose to set
	 */
	public void setIsSelected(Boolean choose) {
		this.isSelected = choose;
	}

	/**
	 * @return the innerMergeData
	 */
	public InnerMergeData getInnerMergeData() {
		return innerMergeData;
	}

	/**
	 * @param innerMergeData the innerMergeData to set
	 */
	public void setInnerMergeData(InnerMergeData innerMergeData) {
		this.innerMergeData = innerMergeData;
	}

	
}
