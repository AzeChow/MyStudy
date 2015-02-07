/*
 * Created on 2005-3-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractexe.entity;

import java.util.Date;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 存放合同备案料件资料
 */
public class ImpExpGoodsBill extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
    
    //-------------------料件----------------------------------  
    /**
     * KR#  
     */
    private String krNo; 
    /**
     * 报关单号码    
     */
    private String customsNo; 
    //-------------------成品----------------------------------
    /**
     * 件数
     */
	private Integer commodityNum;
	
	/**
	 * 包装种类
	 */
	private String wrapType;
	
	/**
	 * 毛重
	 */
	private Double grossWeight;
	
	/**
	 * 净重
	 */
	private Double netWeight; 
	
	/**
	 * 贸易方式
	 */
	private String tradeMode;
	
	/**
	 * 备注1
	 */
	private String memo1; 
	
	/**
	 * 备注2
	 */
	private String memo2; 
	
	/**
	 * 出口口岸
	 */
	private String customs;
	
	/**
	 * 指运港
	 */
	private String portLoad;
	
	/**
	 * 批准文号
	 */
	private String authorizeFile;
	
	/**
	 * 集装箱号
	 */
	private String containerNum;
	
	/**
	 * 运输工具名称
	 */
	private String conveyance;
	
	/**
	 * 运输方式
	 */
	private String transferMode;
	
	/**
	 * 境内运输工具编号
	 */
	private String domeConveyance;
    //------------------公共----------------------------------
	/**
	 * 导入时间
	 */
    private Date importDate; 
    
    /**
     * 原产国
     */
    private String country; 
    
    /**
     * 车牌号码
     */
    private String catNo; 
    /**
     * 数量
     */
    private Double num; 
    /**
     * 手册号
     */
    private String contractNo; 
    /**
     * 序号 
     */
    private Integer seqNum; 
    /**
     * 是否转报关单    
     */
    private Boolean isTcustoms; 
    /**
     * 是否料件进口
     */
    private Boolean isLj;
    /**
     * 已转报关单流水号
     */
    private Integer serialNumber;
    
    
	/**
	 * 合同定量=合同数量
	 */
	private double contractAmount; 

	/**
	 * 合同余量=合同定量-已生效报关单数量
	 */
	private double contractRemain;// 应该当前余量
	
	/**
	 * 料件合同
	 */
	private ContractImg img;
	
	/**
	 * 成品合同
	 */
	private ContractExg exg;
	
	/**
	 * 起运国（出口表头）
	 */
	private String countryOfLoadingOrUnloading; 
	/**
	 * 报送海关（出口表头）
	 */
    private String declarationCustoms; 
    
    
    
	public String getCatNo() {
		return catNo;
	}
	public void setCatNo(String catNo) {
		this.catNo = catNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCustomsNo() {
		return customsNo;
	}
	public void setCustomsNo(String customsNo) {
		this.customsNo = customsNo;
	}
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	public Boolean getIsTcustoms() {
		return isTcustoms;
	}
	public void setIsTcustoms(Boolean isTcustoms) {
		this.isTcustoms = isTcustoms;
	}
	public String getKrNo() {
		return krNo;
	}
	public void setKrNo(String krNo) {
		this.krNo = krNo;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public double getContractRemain() {
		return contractRemain;
	}
	public void setContractRemain(double contractRemain) {
		this.contractRemain = contractRemain;
	}
	public ContractImg getImg() {
		return img;
	}
	public void setImg(ContractImg img) {
		this.img = img;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public ContractExg getExg() {
		return exg;
	}
	public void setExg(ContractExg exg) {
		this.exg = exg;
	}
	public Boolean getIsLj() {
		return isLj;
	}
	public void setIsLj(Boolean isLj) {
		this.isLj = isLj;
	}
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
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
	public String getCustoms() {
		return customs;
	}
	public void setCustoms(String customs) {
		this.customs = customs;
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
	public String getPortLoad() {
		return portLoad;
	}
	public void setPortLoad(String portLoad) {
		this.portLoad = portLoad;
	}
	public String getDomeConveyance() {
		return domeConveyance;
	}
	public void setDomeConveyance(String domeConveyance) {
		this.domeConveyance = domeConveyance;
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