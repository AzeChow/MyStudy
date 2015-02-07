/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.entity;


import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.BalanceMode;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.pis.entity.BrokerCorp;

/**
 * 存放系统参数设置－其他参数设置资料
 * 
 * @author 001
 */
public class CustomsDeclarationSet extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();

    /**
     * 报送海关
     */
    private Customs declarationCustoms; 
    
    /**
     * 征免性质
     */
    private LevyKind customlevyKind;  
    
    /**
     * 提运单号
     */
    private String billOfLading; 
    
    /**
     * 结汇方式
     */
    private BalanceMode balanceMode; 
	
	/**
	 * 贸易方式
	 */
    private Trade tradeMode; 
	
	/**
	 * 运输工具
	 */
    private String conveyance; 
	
	
	/**
	 * 减免方式
	 */
    private LevyMode levyMode; 
	
	/**
	 * 用途
	 */
    private Uses uses; 
	
	/**
	 * 成交方式
	 */
    private Transac transac;
	
	/**
	 * 原产国
	 */
    private Country country;
	
	/**
	 * 起运国
	 */
    private Country coun;
	
	/**
	 * 码头
	 */
    private PreDock predock; 
	
	/**
	 * 装货港
	 */
    private PortLin port; 
	
	/**
	 * 包装种类
	 */
    private Wrap wrapType; 
	
	/**
	 *  进、出口岸
	 */
    private Customs customs;
	
	/**
	 * 运输方式
	 */
    private Transf transferMode; 
	
	/**
	 * 境内目的地
	 */
    private District district;
	
	/**
	 * 进出口类型
	 */
    private Integer impType;
    
    /**
     * 币制
     * @return
     */
    private Curr curr;

    /**
     * 商品包装种类
     */
    private Wrap commWrapType;
    
    /**
     * 申报单位
     * @return
     */
    private BrokerCorp brokerCorp;
    
	public BalanceMode getBalanceMode() {
		return balanceMode;
	}

	public void setBalanceMode(BalanceMode balanceMode) {
		this.balanceMode = balanceMode;
	}

	public String getBillOfLading() {
		return billOfLading;
	}

	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}

	public String getConveyance() {
		return conveyance;
	}

	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}

	public Country getCoun() {
		return coun;
	}

	public void setCoun(Country coun) {
		this.coun = coun;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public LevyKind getCustomlevyKind() {
		return customlevyKind;
	}

	public void setCustomlevyKind(LevyKind customlevyKind) {
		this.customlevyKind = customlevyKind;
	}

	public Customs getCustoms() {
		return customs;
	}

	public void setCustoms(Customs customs) {
		this.customs = customs;
	}

	public Customs getDeclarationCustoms() {
		return declarationCustoms;
	}

	public void setDeclarationCustoms(Customs declarationCustoms) {
		this.declarationCustoms = declarationCustoms;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Integer getImpType() {
		return impType;
	}

	public void setImpType(Integer impType) {
		this.impType = impType;
	}

	public Transac getTransac() {
		return transac;
	}

	public LevyMode getLevyMode() {
		return levyMode;
	}

	public void setLevyMode(LevyMode levyMode) {
		this.levyMode = levyMode;
	}

	public PortLin getPort() {
		return port;
	}

	public void setPort(PortLin port) {
		this.port = port;
	}

	public PreDock getPredock() {
		return predock;
	}

	public void setPredock(PreDock predock) {
		this.predock = predock;
	}

	public Trade getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Trade tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Transf getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(Transf transferMode) {
		this.transferMode = transferMode;
	}

	public Uses getUses() {
		return uses;
	}

	public void setUses(Uses uses) {
		this.uses = uses;
	}

	public Wrap getWrapType() {
		return wrapType;
	}

	public void setWrapType(Wrap wrapType) {
		this.wrapType = wrapType;
	}

	public void setTransac(Transac transac) {
		this.transac = transac;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	public Wrap getCommWrapType() {
		return commWrapType;
	}

	public void setCommWrapType(Wrap commWrapType) {
		this.commWrapType = commWrapType;
	}

	public BrokerCorp getBrokerCorp() {
		return brokerCorp;
	}

	public void setBrokerCorp(BrokerCorp brokerCorp) {
		this.brokerCorp = brokerCorp;
	}  
    
}