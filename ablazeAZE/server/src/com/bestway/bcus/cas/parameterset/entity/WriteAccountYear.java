/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.parameterset.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 记帐年度
 */
public class WriteAccountYear extends BaseScmEntity {
    private static final long serialVersionUID    = CommonUtils
                                                          .getSerialVersionUID();
    /**
     * 海关帐年度控制 
     */
    private Integer           year                = null;
    /**
     * 海关帐分客户统计
     */
    private Boolean           isCleftCustomerStat = false;
    
    

    /**
     * 取得海关帐分客户统计的标志 是为true 否则为false
     * @return isCleftCustomerStat 海关帐分客户统计
     */
    public Boolean getIsCleftCustomerStat() {
        return isCleftCustomerStat;
    }
    
    /**
     * 取得海关帐年度控制
     * @return  year  海关帐年度控制
     */
    public Integer getYear() {
        return year;
    }
    /**
     * 设置海关帐是否分客户统计标志
     * @param  isCleftCustomerStat  海关帐是否分客户统计
     */
    public void setIsCleftCustomerStat(Boolean isCleftCustomerStat) {
        this.isCleftCustomerStat = isCleftCustomerStat;
    }
    /**
     *设置海关帐年度控制
     * @param year 海关帐年度控制
     */
    public void setYear(Integer year) {
        this.year = year;
    }
}
