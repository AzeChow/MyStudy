/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 报关单商品信息
 * @author ls
 * table="makelisttocustoms"
 */
public class MakeListToCustoms extends BaseScmEntity {
    private static final long          serialVersionUID     = CommonUtils
                                                                    .getSerialVersionUID();
    /**
     * 归并后商品信息
     */
    private AtcMergeAfterComInfo       atcMergeAfterComInfo = null;                       
    /**
     * 报关单商品信息
     */
    private CustomsDeclarationCommInfo customsInfo          = null;                       
    /**
     * 更新日期
     */
    private Date                       updateDate           = null;                        // 修改日期

    /**
     * 获得归并后商品信息
     * @return 归并后商品信息
     */
    public AtcMergeAfterComInfo getAtcMergeAfterComInfo() {
        return atcMergeAfterComInfo;
    }

    /**
     * 设计归并后商品信息
     * @param atcMergeAfterComInfo 归并后商品信息
     */
    public void setAtcMergeAfterComInfo(
            AtcMergeAfterComInfo atcMergeAfterComInfo) {
        this.atcMergeAfterComInfo = atcMergeAfterComInfo;
    }

    /**
     * 获得报关单商品信息
     * @return 报关单商品信息
     */
    public CustomsDeclarationCommInfo getCustomsInfo() {
        return customsInfo;
    }

    /**
     * 设计报关单商品信息
     * @param customsInfo 报关单商品信息
     */
    public void setCustomsInfo(CustomsDeclarationCommInfo customsInfo) {
        this.customsInfo = customsInfo;
    }

    /**
     * 获得更新日期
     * @return 更新日期
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新日期
     * @param updateDate 更新日期
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    
}