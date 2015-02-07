/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.transferfactory.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 由关封申请单生成关封单据的中间表
 * @author ls 由关封申请单生成关封单据的中间表 
 *         type comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class MakeCustomsEnvelop extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 关封申请单商品信息Id
	 */
	private CustomsEnvelopRequestCommodityInfo cerCommodityInfo = null;
    /**
     * 关封商品信息Id
     */
	private CustomsEnvelopCommodityInfo        ceCommodityInfo  = null; 

    
    /**
     * @return Returns the ceCommodityInfo.
     */
    public CustomsEnvelopCommodityInfo getCeCommodityInfo() {
        return ceCommodityInfo;
    }
    /**
     * @return Returns the cerCommodityInfo.
     */
    public CustomsEnvelopRequestCommodityInfo getCerCommodityInfo() {
        return cerCommodityInfo;
    }
    /**
     * @param ceCommodityInfo The ceCommodityInfo to set.
     */
    public void setCeCommodityInfo(CustomsEnvelopCommodityInfo ceCommodityInfo) {
        this.ceCommodityInfo = ceCommodityInfo;
    }
    /**
     * @param cerCommodityInfo The cerCommodityInfo to set.
     */
    public void setCerCommodityInfo(
            CustomsEnvelopRequestCommodityInfo cerCommodityInfo) {
        this.cerCommodityInfo = cerCommodityInfo;
    }
}