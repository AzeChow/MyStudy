/*
 * Created on 2004-10-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.fpt.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author bestway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempCustomsEnvelopRequetBill implements Serializable {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	private Boolean                   isSelected                = false;
    private FptAppHead fptAppHead = null;
    /**
     * @return Returns the fptAppHead.
     */
    public FptAppHead getCustomsEnvelopRequestBill() {
        return fptAppHead;
    }
    /**
     * @param fptAppHead The fptAppHead to set.
     */
    public void setCustomsEnvelopRequestBill(
            FptAppHead fptAppHead) {
        this.fptAppHead = fptAppHead;
    }
   
    public Boolean getIsSelected(){
        return this.isSelected;
    }
    /**
     * @param isSelected The isSelected to set.
     */
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
}