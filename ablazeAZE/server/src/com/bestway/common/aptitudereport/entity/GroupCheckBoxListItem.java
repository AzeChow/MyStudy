package com.bestway.common.aptitudereport.entity;

public class GroupCheckBoxListItem {
	
	/**
	 * 分组值对象
	 */
	private GroupValue groupValue;
	
	/**
	 * 是否勾选
	 */
    private Boolean isSelected = false;
    
	public GroupValue getGroupValue() {
		return groupValue;
	}
	
	public void setGroupValue(GroupValue groupValue) {
		this.groupValue = groupValue;
	}
	
	public Boolean getIsSelected() {
		return isSelected;
	}
	
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

}
