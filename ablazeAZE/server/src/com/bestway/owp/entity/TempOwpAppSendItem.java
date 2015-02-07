package com.bestway.owp.entity;

import java.io.Serializable;

public class TempOwpAppSendItem implements Serializable{
	/**
     * 是否选中
     */
    private Boolean    isSelected = null;
	private OwpAppSendItem owpAppSendItem = null;
	private String errinfo = null;
	private Integer row = null;

	
	public OwpAppSendItem getOwpAppSendItem() {
		return owpAppSendItem;
	}
	public void setOwpAppSendItem(OwpAppSendItem owpAppSendItem) {
		this.owpAppSendItem = owpAppSendItem;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getErrinfo() {
		return errinfo;
	}
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	
	
}
