package com.bestway.common.transferfactory.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

public class TempTransferFactoryCommodityInfo implements Serializable {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	private Boolean                      isSelected = false;
    private TransferFactoryCommodityInfo t          = null;
    private String                       seqNum     = null;

    /**
     * @return Returns the seqNum.
     */
    public String getSeqNum() {
        return seqNum;
    }
    /**
     * @param seqNum The seqNum to set.
     */
    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }
    /**
     * @return Returns the isSelected.
     */
    public Boolean getIsSelected() {
        return isSelected;
    }

    /**
     * @param isSelected
     *            The isSelected to set.
     */
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * @return Returns the transferFactoryCommodityInfo.
     */
    public TransferFactoryCommodityInfo getT() {
        return t;
    }

    /**
     * @param transferFactoryCommodityInfo
     *            The transferFactoryCommodityInfo to set.
     */
    public void setT(TransferFactoryCommodityInfo transferFactoryCommodityInfo) {
        this.t = transferFactoryCommodityInfo;
    }
}