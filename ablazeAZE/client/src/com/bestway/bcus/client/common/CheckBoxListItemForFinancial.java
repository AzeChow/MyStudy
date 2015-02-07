package com.bestway.bcus.client.common;
/**
 * 用于财务分析报表 主要是为了把编码、名称修改为名称、编码
 * @author Administrator
 *
 */
public class CheckBoxListItemForFinancial {
    private String  code       = null;
    private String  name       = null;
    private Boolean isSelected = false;
    
	public CheckBoxListItemForFinancial(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public CheckBoxListItemForFinancial() {

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
        return (name == null ? "" : name) + "   " + (code == null ? "" : code);
    }

}
