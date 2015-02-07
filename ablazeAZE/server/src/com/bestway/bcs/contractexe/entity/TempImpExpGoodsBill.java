/*
 * Created on 2005-3-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractexe.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 存放合同备案料件资料
 */
public class TempImpExpGoodsBill extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    /**
     * 是否选中
     */
    private Boolean           isSelected        = null;
    /**
     * 报关单号
     */
    private String            customsNo         = null;
    /**
     * 合同号
     * @return
     */
    private String            contractNo        = null;
    
    /**
     * KR#
     * @return
     */
    
    private String            kr = null;
    
    private String            catNo = null;
    
    
    
    private Integer commodityNum;//件数
	private String wrapType;//包装种类
	private Double grossWeight;//毛重
	private Double netWeight; //净重
	private String tradeMode;//贸易方式
	private String memo1; //备注1
	private String memo2; //备注2
	private String customs;//出口口岸
	private String portLoad;//指运港
	private String authorizeFile;//批准文号
	private String containerNum;//集装箱号
	private String conveyance;//运输工具名称
	private String transferMode;//运输方式
	private String domeConveyance;//境内运输工具编号
	private String country; //原产国

	private String countryOfLoadingOrUnloading; //起运国（出口表头）
    private String declarationCustoms; //报送海关（出口表头）
	
	
	public String getCustomsNo() {
		return customsNo;
	}
	public void setCustomsNo(String customsNo) {
		this.customsNo = customsNo;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getCatNo() {
		return catNo;
	}
	public void setCatNo(String catNo) {
		this.catNo = catNo;
	}
	public String getKr() {
		return kr;
	}
	public void setKr(String kr) {
		this.kr = kr;
	}
	public String getAuthorizeFile() {
		return authorizeFile;
	}
	public void setAuthorizeFile(String authorizeFile) {
		this.authorizeFile = authorizeFile;
	}
	public Integer getCommodityNum() {
		return commodityNum;
	}
	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}
	public String getContainerNum() {
		return containerNum;
	}
	public void setContainerNum(String containerNum) {
		this.containerNum = containerNum;
	}
	public String getConveyance() {
		return conveyance;
	}
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCustoms() {
		return customs;
	}
	public void setCustoms(String customs) {
		this.customs = customs;
	}
	public String getDomeConveyance() {
		return domeConveyance;
	}
	public void setDomeConveyance(String domeConveyance) {
		this.domeConveyance = domeConveyance;
	}
	public Double getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	public String getMemo2() {
		return memo2;
	}
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	public Double getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}
	public String getPortLoad() {
		return portLoad;
	}
	public void setPortLoad(String portLoad) {
		this.portLoad = portLoad;
	}
	public String getTradeMode() {
		return tradeMode;
	}
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}
	public String getTransferMode() {
		return transferMode;
	}
	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}
	public String getWrapType() {
		return wrapType;
	}
	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}
	public String getCountryOfLoadingOrUnloading() {
		return countryOfLoadingOrUnloading;
	}
	public void setCountryOfLoadingOrUnloading(String countryOfLoadingOrUnloading) {
		this.countryOfLoadingOrUnloading = countryOfLoadingOrUnloading;
	}
	public String getDeclarationCustoms() {
		return declarationCustoms;
	}
	public void setDeclarationCustoms(String declarationCustoms) {
		this.declarationCustoms = declarationCustoms;
	}
    
    
    
}