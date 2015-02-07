package com.bestway.common.fpt.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

public class TempCustomsEnvelopRequestCommodityInfo implements Serializable
{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    private FptAppItem c = null;
    private Boolean isSelected = false;
    
    /**
     * @return Returns the customsEnvelopRequestCommodityInfo.
     */
    public FptAppItem getC() {
        return c;
    }
    /**
     * @param fptAppItem The customsEnvelopRequestCommodityInfo to set.
     */
    public void setC(
            FptAppItem fptAppItem) {
        this.c = fptAppItem;
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