/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractexe.entity;

import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 存储申请单转报关单中间表资料
 */
public class MakeBcsCustomsDeclaration extends BaseScmEntity {
    private static final long             serialVersionUID              = CommonUtils
                                                                                .getSerialVersionUID();
    /**
     * 报关单商品信息
     */
    private BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = null;
    
    /**
     * 申请单商品信息
     */
    private ImpExpCommodityInfo           impExpCommodityInfo           = null;

    /**
     * 获取申请单商品信息
     * 
     * @return impExpCommodityInfo 申请单商品信息
     */
    public ImpExpCommodityInfo getImpExpCommodityInfo() {
        return impExpCommodityInfo;
    }

    /**
     * 设置申请单商品信息
     * 
     * @param impExpCommodityInfo 申请单商品信息
     */
    public void setImpExpCommodityInfo(ImpExpCommodityInfo impExpCommodityInfo) {
        this.impExpCommodityInfo = impExpCommodityInfo;
    }

    /**
     * 获取报关单商品信息
     * 
     * @return bcsCustomsDeclarationCommInfo 报关单商品信息
     */
    public BcsCustomsDeclarationCommInfo getBcsCustomsDeclarationCommInfo() {
        return bcsCustomsDeclarationCommInfo;
    }

    /**
     * 设置报关单商品信息
     * 
     * @param bcsCustomsDeclarationCommInfo 报关单商品信息
     */
    public void setBcsCustomsDeclarationCommInfo(
            BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo) {
        this.bcsCustomsDeclarationCommInfo = bcsCustomsDeclarationCommInfo;
    }
}