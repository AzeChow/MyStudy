package com.bestway.pis.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 同步账号esp
 *
 */
public class PisSyncAgentCorp extends BaseScmEntity {

    /**
     * 同步错误信息
     */
    private String info;

    /**
     * 同步状态
     */
    private String syncState;

    /**
     * 海关注册编码 来源【海关注册公司】
     *
     */
    private String briefCode;
    /**
     * 海关企业名称 依据“海关注册编码”从【海关注册公司】关联其值
     *
     */
    private String briefName;
    /**
     * 联系人
     *
     */
    private String linkMan;
    /**
     * 联系电话
     *
     */
    private String linkTel;
    /**
     * 地址
     *
     */
    private String address;

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

    public String getBriefCode() {
        return briefCode;
    }

    public void setBriefCode(String briefCode) {
        this.briefCode = briefCode;
    }

    public String getBriefName() {
        return briefName;
    }

    public void setBriefName(String briefName) {
        this.briefName = briefName;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
