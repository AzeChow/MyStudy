/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.customslist.entity;

import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 申请单转清单的中间表
 * 
 * @author yp
 */
public class DzscMakeApplyToCustoms extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 报关清单归并前商品信息
     */
    private DzscBillListBeforeCommInfo atcMergeBeforeComInfo = null;
    
    /**
     * 进出口申请单商品信息
     */
    private ImpExpCommodityInfo   impExpCommodityInfo   = null;

    /**
     * 获取报关清单归并前商品信息
     * 
     * @return atcMergeBeforeComInfo 报关清单归并前商品信息
     */
    public DzscBillListBeforeCommInfo getAtcMergeBeforeComInfo() {
        return atcMergeBeforeComInfo;
    }

    /**
     * 获取进出口申请单商品信息
     * 
     * @return impExpCommodityInfo 进出口申请单商品信息
     */
    public ImpExpCommodityInfo getImpExpCommodityInfo() {
        return impExpCommodityInfo;
    }

    /**
     * 设置报关清单归并前商品信息
     * 
     * @param atcMergeBeforeComInfo 报关清单归并前商品信息
     */
    public void setAtcMergeBeforeComInfo(
    		DzscBillListBeforeCommInfo atcMergeBeforeComInfo) {
        this.atcMergeBeforeComInfo = atcMergeBeforeComInfo;
    }

    /**
     * 设置进出口申请单商品信息
     * 
     * @param impExpCommodityInfo 进出口申请单商品信息
     */
    public void setImpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
        this.impExpCommodityInfo = impExpCommodityInfo;
    }
}