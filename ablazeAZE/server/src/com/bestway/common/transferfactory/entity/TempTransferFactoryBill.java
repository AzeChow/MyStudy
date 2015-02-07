/*
 * Created on 2004-10-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author bestway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TempTransferFactoryBill implements Serializable {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	private Boolean             isSelected          = false;
    private TransferFactoryBill t = null;
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
    /**
     * @return Returns the transferFactoryBill.
     */
    public TransferFactoryBill getT() {
        return t;
    }
    /**
     * @param transferFactoryBill The transferFactoryBill to set.
     */
    public void setT(TransferFactoryBill transferFactoryBill) {
        this.t = transferFactoryBill;
    }
}