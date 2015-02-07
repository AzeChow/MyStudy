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
 * 由转厂单据生成关封申请单的中间表
 * @author ls 由转厂单据生成关封申请单的中间表 
 *         type comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class MakeCustomsEnvelopRequest extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 关封申请单商品信息Id
	 */
	private CustomsEnvelopRequestCommodityInfo cerCommodityInfo = null; 
    /**
     * 转厂商品信息Id
     */
	private TransferFactoryCommodityInfo       tfCommodityInfo  = null; 

   
    /**
     * @return Returns the cerCommodityInfo.
     */
    public CustomsEnvelopRequestCommodityInfo getCerCommodityInfo() {
        return cerCommodityInfo;
    }
    /**
     * @return Returns the tfCommodityInfo.
     */
    public TransferFactoryCommodityInfo getTfCommodityInfo() {
        return tfCommodityInfo;
    }
    /**
     * @param cerCommodityInfo The cerCommodityInfo to set.
     */
    public void setCerCommodityInfo(
            CustomsEnvelopRequestCommodityInfo cerCommodityInfo) {
        this.cerCommodityInfo = cerCommodityInfo;
    }
    /**
     * @param tfCommodityInfo The tfCommodityInfo to set.
     */
    public void setTfCommodityInfo(TransferFactoryCommodityInfo tfCommodityInfo) {
        this.tfCommodityInfo = tfCommodityInfo;
    }
}