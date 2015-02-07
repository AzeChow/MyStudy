package com.bestway.common.transferfactory.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

public class TempCustomsEnvelopRequestCommodityInfo implements Serializable
{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    private CustomsEnvelopRequestCommodityInfo c = null;
    private Boolean isSelected = false;
    
    /**
     * @return Returns the customsEnvelopRequestCommodityInfo.
     */
    public CustomsEnvelopRequestCommodityInfo getC() {
        return c;
    }
    /**
     * @param customsEnvelopRequestCommodityInfo The customsEnvelopRequestCommodityInfo to set.
     */
    public void setC(
            CustomsEnvelopRequestCommodityInfo customsEnvelopRequestCommodityInfo) {
        this.c = customsEnvelopRequestCommodityInfo;
    }
    /**
     * @return Returns the isSelected.
     */
    public Boolean getIsSelected() {
        return isSelected;
    }
    /**
     * @param isSelected The isSelected to set.
     */
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
}