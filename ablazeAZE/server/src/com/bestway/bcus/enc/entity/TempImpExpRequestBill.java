/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.io.Serializable;

import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 申请单数据
 */
public class TempImpExpRequestBill implements Serializable {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 是否选中
     */
    private Boolean           isSelected        = null;
    /**
     * 进出口申请单
     */
    private ImpExpRequestBill impExpRequestBill = null;

    /**
     * 取得进出口申请单内容
     * @return 进出口申请单
     */
    public ImpExpRequestBill getImpExpRequestBill() {
        return impExpRequestBill;
    }
    /**
     * 判断是否选中
     * @return 是否选中
     */
    public Boolean getIsSelected() {
        return isSelected;
    }
    /**
     * 设计进出口申请单内容
     * @param impExpRequestBill 进出口申请单
     */
    public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
        this.impExpRequestBill = impExpRequestBill;
    }
    /**
     * 设置是否选中标志
     * @param isSelected 是否选中
     */
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }
}