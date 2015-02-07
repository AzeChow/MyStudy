package com.bestway.owp.entity;

import java.io.Serializable;

public class TempOwpAppRecvItem implements Serializable{
	/**
     * 是否选中
     */
    private Boolean    isSelected = null;
	private OwpAppRecvItem owpAppRecvItem = null;
	private String errinfo = null;
	private Integer row = null;


	public OwpAppRecvItem getOwpAppRecvItem() {
		return owpAppRecvItem;
	}
	public void setOwpAppRecvItem(OwpAppRecvItem owpAppRecvItem) {
		this.owpAppRecvItem = owpAppRecvItem;
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
