package com.bestway.pis.entity;

import com.bestway.common.BaseScmEntity;


/**
 * 授权代理公司
 *
 * @author sjl
 */
public class EspAuthorityBrokerCorp extends BaseScmEntity {

    /**
     * 代理公司
     *
     */
    private BrokerCorp brokerCorp;

    /**
     * 同步错误信息
     */
    private String info;

    /**
     * 同步状态
     */
    private String syncState;

    public BrokerCorp getBrokerCorp() {
        return brokerCorp;
    }

    public void setBrokerCorp(BrokerCorp brokerCorp) {
        this.brokerCorp = brokerCorp;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSyncState() {
        return syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

}
