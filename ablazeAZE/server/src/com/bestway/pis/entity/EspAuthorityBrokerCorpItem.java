package com.bestway.pis.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 授权代理公司明细
 *
 * @author sjl
 */
public class EspAuthorityBrokerCorpItem extends BaseScmEntity {

    /**
     * 授权代理公司
     *
     */
    private EspAuthorityBrokerCorp espAuthorityBrokerCorp;

    /**
     * 主营业务
     *
     */
    private String mainBusiness;

    /**
     * IC卡号
     *
     */
    private String icCard;

    private String optionStatus;

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getIcCard() {
        return icCard;
    }

    public void setIcCard(String icCard) {
        this.icCard = icCard;
    }

    public EspAuthorityBrokerCorp getEspAuthorityBrokerCorp() {
        return espAuthorityBrokerCorp;
    }

    public void setEspAuthorityBrokerCorp(EspAuthorityBrokerCorp espAuthorityBrokerCorp) {
        this.espAuthorityBrokerCorp = espAuthorityBrokerCorp;
    }

    public String getOptionStatus() {
        return optionStatus;
    }

    public void setOptionStatus(String optionStatus) {
        this.optionStatus = optionStatus;
    }

}
