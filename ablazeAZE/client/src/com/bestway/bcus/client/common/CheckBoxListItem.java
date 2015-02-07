package com.bestway.bcus.client.common;

public class CheckBoxListItem {
    private String  code       = null;
    private String  name       = null;
    private Boolean isSelected = false;
    
	public CheckBoxListItem(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public CheckBoxListItem() {

	}
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return (code == null ? "" : code) +"   "+ (name == null ? "" : name);
    }

}
