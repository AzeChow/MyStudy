/*
 * Created on 2004-7-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.parameterset.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 单据对应控制对象
 */
public class BillCorrespondingControl extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils
                                                       .getSerialVersionUID();
    /**
     * 是否是系统控制
     */
    private Boolean           isSystemControl  = false;
    /**
     * 是否是特殊控制
     */
    private Boolean           isSpecialControl = false;
    /**
     * 是否是手工控制
     */
    private Boolean           isHandContrl     = false;
    /**
     * 结转退货单参与对应
     */
    private Boolean           isTransferBack   = true;

    /**
     * 是否是系统控制
     * @return  isHandContrl  是否手工控制
     */
    public Boolean getIsHandContrl() {
        return isHandContrl;
    }

    /**
     * 是否特殊控制
     * @return  isSpecialControl 
     */
    public Boolean getIsSpecialControl() {
        return isSpecialControl;
    }

    /**
     * 是否是手工控制
     * @return  isSystemControl 是否系统控制
     */
    public Boolean getIsSystemControl() {
        return isSystemControl;
    }

    /**
     * 是否是手工控制
     * @return  isTransferBack 是否结转退货单参与对应
     */
    public Boolean getIsTransferBack() {
        return isTransferBack;
    }

    /**
     * 设置是否手工控制
     * @param  isHandContrl 是否手工控制
     */
    public void setIsHandContrl(Boolean isHandContrl) {
        this.isHandContrl = isHandContrl;
    }

    /**
     * 设置是否特殊控制
     * @param  isSpecialControl 是否特殊控制
     */
    public void setIsSpecialControl(Boolean isSpecialControl) {
        this.isSpecialControl = isSpecialControl;
    }

    /**
     * 设置是否系统控制
     * @param  isSystemControl 是否系统控制
     */
    public void setIsSystemControl(Boolean isSystemControl) {
        this.isSystemControl = isSystemControl;
    }

    /**
     * 结转退货单参与对应
     * @param isTransferBack 是否结转退货单参与对应
     */
    public void setIsTransferBack(Boolean isTransferBack) {
        this.isTransferBack = isTransferBack;
    }
}
