package com.bestway.common.fpt.entity;

import java.io.Serializable;

public class TempFptAppItem implements Serializable{
	/**
     * 是否选中
     */
    private Boolean    isSelected = null;
	private FptAppItem fptAppItem = null;
	private String errinfo = null;
	/**
	 * @return the isSelected
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}
	/**
	 * @param isSelected the isSelected to set
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	/**
	 * @return the fptAppItem
	 */
	public FptAppItem getFptAppItem() {
		return fptAppItem;
	}
	/**
	 * @param fptAppItem the fptAppItem to set
	 */
	public void setFptAppItem(FptAppItem fptAppItem) {
		this.fptAppItem = fptAppItem;
	}
	/**
	 * @return the errinfo
	 */
	public String getErrinfo() {
		return errinfo;
	}
	/**
	 * @param errinfo the errinfo to set
	 */
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	
	
	
}
