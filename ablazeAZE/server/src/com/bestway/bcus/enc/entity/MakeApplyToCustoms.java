/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 进出口类型为直接出口的申请单商品信息
 * @author ls
 * 
 * table="makeapplytocustoms"
 */
public class MakeApplyToCustoms extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 报关清单归并前商品信息Id
     */
    private AtcMergeBeforeComInfo atcMergeBeforeComInfo = null; 
    /**
     * 进出口商品信息
     */
    private ImpExpCommodityInfo   impExpCommodityInfo   = null; 

    /**
     * 获得报关清单归并前商品信息Id
     * @return 报关清单归并前商品信息Id
     */
    public AtcMergeBeforeComInfo getAtcMergeBeforeComInfo() {
        return atcMergeBeforeComInfo;
    }

    /**
     * 获得进出口商品信息
     * @return 进出口商品信息
     */
    public ImpExpCommodityInfo getImpExpCommodityInfo() {
        return impExpCommodityInfo;
    }

    /**
     * 设置报关清单归并前商品信息Id
     * @param atcMergeBeforeComInfo 报关清单归并前商品信息Id
     */
    public void setAtcMergeBeforeComInfo(
            AtcMergeBeforeComInfo atcMergeBeforeComInfo) {
        this.atcMergeBeforeComInfo = atcMergeBeforeComInfo;
    }

    /**
     * 设置进出口商品信息
     * @param impExpCommodityInfo 进出口商品信息
     */
    public void setImpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
        this.impExpCommodityInfo = impExpCommodityInfo;
    }
}