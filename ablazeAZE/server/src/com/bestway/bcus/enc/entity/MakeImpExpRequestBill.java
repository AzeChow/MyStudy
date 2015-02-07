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
 * 与指定的进出口商品信息id匹配的进出口申请单的中间表
 * @author ls
 * table="makeimpexprequestbill"
 */
public class MakeImpExpRequestBill extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    /**
     * 海关帐工厂商品信息id
     */
    private String cfcId = null; 
    /**
     * 进出口商品信息Id
     */
    private String iecId = null; 

    /**
     * 获得进出口商品信息Id
     * 
     * @return 进出口商品信息Id
     */
    public String getIecId() {
        return iecId;
    }

    /**
     * 设置进出口商品信息Id
     * 
     * @param iecId 进出口商品信息Id
     */
    public void setIecId(String iecId) {
        this.iecId = iecId;
    }

    /**
     * 获得海关帐工厂商品信息id
     * 
     * @return 海关帐工厂商品信息id
     */
    public String getCfcId() {
        return cfcId;
    }

    /**
     * 设置海关帐工厂商品信息id
     * 
     * @param cfcId 海关帐工厂商品信息id
     */
    public void setCfcId(String cfcId) {
        this.cfcId = cfcId;
    }
}