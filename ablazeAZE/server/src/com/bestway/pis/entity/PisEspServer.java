package com.bestway.pis.entity;

import com.bestway.common.BaseScmEntity;

/**
 * Esp服务器配置
 *
 */
public class PisEspServer extends BaseScmEntity {

    /**
     * Esp企业编码
     */
    private String espCompanyCode;
    /**
     * Esp企业名称
     */
    private String espCompanyName;
    /**
     * 平台归属者 0 进出口企业 ； 1 代理公司
     */
    private Integer ownership;

    /**
     * 服务器地址
     */
    private String serverAddress;
    /**
     * 端口号
     */
    private String portNumber;

    public String getEspCompanyCode() {
        return espCompanyCode;
    }

    public void setEspCompanyCode(String espCompanyCode) {
        this.espCompanyCode = espCompanyCode;
    }

    public String getEspCompanyName() {
        return espCompanyName;
    }

    public void setEspCompanyName(String espCompanyName) {
        this.espCompanyName = espCompanyName;
    }

    public Integer getOwnership() {
        return ownership;
    }

    public void setOwnership(Integer ownership) {
        this.ownership = ownership;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

}
