package com.bestway.pis.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.common.BaseScmEntity;

public class BrokerCorp extends BaseScmEntity {

    /**
     * Esp服务器配置
     *
     */
    private PisEspServer pisEspServer;

    /**
     * 运维平台是否已停用 1.运维平台授权esp模块中停用当前企业 2.运维平台授权esp模块中停用当前代理公司
     */
    private Boolean isBcgsStop;

    /**
     * 同步状态
     */
    private String syncState;

    /**
     * 邮箱
     *
     */
    private String email;

    /**
     * 代理公司名称
     *
     */
    private String branchName;

    /**
     * 公司类型
     *
     */
    private String corpType;

    /**
     * 企业法人
     *
     */
    private String corporate;

    /**
     * 组织机构代码
     *
     */
    private String orgaCode;

    /**
     * 组织机构名称
     *
     */
    private String orgaName;

    /**
     * 公司地址
     *
     */
    private String addrees;
    /**
     * 公司电话
     *
     */
    private String tel;

    /**
     * 注册资本
     *
     */
    private Double regisCapital;

    /**
     * 成立日期
     *
     */
    private Date foundedDate;

    /**
     * 联系人
     *
     */
    private String linkMan;

    /**
     * 手机号码
     *
     */
    private String phoneNumber;

    /**
     * 主营业务
     *
     */
    private String mainBusiness;

    /**
     * 公司介绍
     *
     */
    private String introduction;

    /**
     * 公司图标
     *
     */
    private String logo;

    /**
     * 公司营业照
     *
     */
    private String license;

    /**
     * 关区代码
     *
     */
    private Customs customs;

    /**
     * 公司网址
     *
     */
    private String corpUrl;
    /**
     * 是否试用
     */
    private Boolean isTry;

    /**
     * 试用截至日期
     */
    private Date tryEndDate;

    /**
     * 是否停用
     */
    private Boolean isStoped;

    /**
     * 停用原因
     */
    private String stopReason;

    /**
     * 停用日期
     */
    private Date stopDate;

    public BrokerCorp() {
        super();
    }

    public BrokerCorp(String _id) {
        super();
        setId(_id);
    }

    /**
     * 获取公司类型
     *
     * @return 公司类型
     */
    public String getCorpType() {
        return this.corpType;
    }

    /**
     * 设置公司类型
     *
     * @param corpType 公司类型
     */
    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }

    /**
     * 获取企业法人
     *
     * @return 企业法人
     */
    public String getCorporate() {
        return this.corporate;
    }

    /**
     * 设置企业法人
     *
     * @param corporate 企业法人
     */
    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    /**
     * 获取组织机构代码
     *
     * @return 组织机构代码
     */
    public String getOrgaCode() {
        return this.orgaCode;
    }

    /**
     * 设置组织机构代码
     *
     * @param orgaCode 组织机构代码
     */
    public void setOrgaCode(String orgaCode) {
        this.orgaCode = orgaCode;
    }

    /**
     * 获取组织机构名称
     *
     * @return 组织机构名称
     */
    public String getOrgaName() {
        return this.orgaName;
    }

    /**
     * 设置组织机构名称
     *
     * @param orgaName 组织机构名称
     */
    public void setOrgaName(String orgaName) {
        this.orgaName = orgaName;
    }

    /**
     * 获取公司地址
     *
     * @return 公司地址
     */
    public String getAddrees() {
        return this.addrees;
    }

    /**
     * 设置公司地址
     *
     * @param addrees 公司地址
     */
    public void setAddrees(String addrees) {
        this.addrees = addrees;
    }

    /**
     * 获取注册资本
     *
     * @return 注册资本
     */
    public Double getRegisCapital() {
        return this.regisCapital;
    }

    /**
     * 设置注册资本
     *
     * @param regisCapital 注册资本
     */
    public void setRegisCapital(Double regisCapital) {
        this.regisCapital = regisCapital;
    }

    /**
     * 获取成立日期
     *
     * @return 成立日期
     */
    public Date getFoundedDate() {
        return this.foundedDate;
    }

    /**
     * 设置成立日期
     *
     * @param foundedDate 成立日期
     */
    public void setFoundedDate(Date foundedDate) {
        this.foundedDate = foundedDate;
    }

    /**
     * 获取联系人
     *
     * @return 联系人
     */
    public String getLinkMan() {
        return this.linkMan;
    }

    /**
     * 设置联系人
     *
     * @param linkMan 联系人
     */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    /**
     * 获取主营业务
     *
     * @return 主营业务
     */
    public String getMainBusiness() {
        return this.mainBusiness;
    }

    /**
     * 设置主营业务
     *
     * @param mainBusiness 主营业务
     */
    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    /**
     * 获取公司介绍
     *
     * @return 公司介绍
     */
    public String getIntroduction() {
        return this.introduction;
    }

    /**
     * 设置公司介绍
     *
     * @param introduction 公司介绍
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取公司图标
     *
     * @return 公司图标
     */
    public String getLogo() {
        return this.logo;
    }

    /**
     * 设置公司图标
     *
     * @param logo 公司图标
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 获取公司营业照
     *
     * @return 公司营业照
     */
    public String getLicense() {
        return this.license;
    }

    /**
     * 设置公司营业照
     *
     * @param license 公司营业照
     */
    public void setLicense(String license) {
        this.license = license;
    }

    public Customs getCustoms() {
        return customs;
    }

    public void setCustoms(Customs customs) {
        this.customs = customs;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCorpUrl() {
        return corpUrl;
    }

    public void setCorpUrl(String corpUrl) {
        this.corpUrl = corpUrl;
    }

    public Boolean getIsTry() {
        return isTry;
    }

    public void setIsTry(Boolean isTry) {
        this.isTry = isTry;
    }

    public Date getTryEndDate() {
        return tryEndDate;
    }

    public void setTryEndDate(Date tryEndDate) {
        this.tryEndDate = tryEndDate;
    }

    public Boolean getIsStoped() {
        return isStoped;
    }

    public void setIsStoped(Boolean isStoped) {
        this.isStoped = isStoped;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public PisEspServer getPisEspServer() {
        return pisEspServer;
    }

    public void setPisEspServer(PisEspServer pisEspServer) {
        this.pisEspServer = pisEspServer;
    }

    public String getSyncState() {
        return syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public Boolean isIsBcgsStop() {
        return isBcgsStop;
    }

    public void setIsBcgsStop(Boolean isBcgsStop) {
        this.isBcgsStop = isBcgsStop;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
