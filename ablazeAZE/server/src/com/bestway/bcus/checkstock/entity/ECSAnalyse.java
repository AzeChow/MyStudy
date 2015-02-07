package com.bestway.bcus.checkstock.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 盘点核查（核查分析）
 * 工厂盘点总库存 - 合同分析【结余】 + 结转数据差额 = 短溢分析
 * @author chl
 *
 */
public class ECSAnalyse extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 序号 
     */
    private Integer serialNumber; 	
    /**
	 * 帐册编号
	 */
	private String emsNo;
	/**
	 * 批次号
	 */
	private ECSSection section;
	/**
	 * 料件备案序号
	 */
	private Integer seqNum;
	/**
	 * 报关商品名称
	 */
	private String hsName;
	/**
	 * 报关商品规格
	 */
	private String hsSpec;
	/**
	 * 报关单位
	 */
	private String hsUnit;
	/**
	 * 单价
	 */
	private Double price;
	 /**
     * 帐面结余(进口数量,成品耗用)
     */
    private Double emsBalance = null; 
    
	/**
	 * 进口数量
	 */
	private Double importAmount;
	
	/**
	 * 成品耗用
	 */
	private Double exgWasteAmount;
	
    /**
     * 实物库存
     * (料件库存,在产品库存,成品库存,外发加工库存, 厂外存放库存,内购库存,
     * 料件在途库存,成品在途库存,残次品库存,半成品（已入库）】库存,
     * 外发库存,在制品库存)
     */
    private Double stockBalance = null;
    
    /**
	 * 料件库存
	 */
	private Double stockAmountImg;
	
	/**
	 * 在产品库存
	 */
	private Double stockAmountProcessImg;
	
	/**
	 * 成品库存
	 */
	private Double stockAmountExg;
	
	/**
	 * 外发加工库存
	 */
	private Double stockAmountOutSendExg;
	
	/**
	 * 厂外存放库存
	 */
	private Double stockAmountOutFactoryImg;
	
	/**
	 * 内购库存
	 */
	private Double stockAmountBuyImg;
	
	/**
	 * 料件在途库存
	 */
	private Double stockAmountTravelingImg;
	/**
	 * 成品在途库存
	 */
	private Double stockAmountTravelingExg;
	/**
	 *残次品库存
	 */
	private Double stockAmountBadImg;
	/**
	 *半成品（已入库）】库存
	 */
	private Double stockAmountSemiExgHStore;
	/**
	 *在制品库存
	 */
	private Double stockAmountFinishingExg;
	/**
	 *外发库存在制品库存
	 */
	private Double stockAmountOutSendExgPt;
    
    /**
     * 结转差额(已送货未转厂数 ,已转厂未送货数,已收货未转厂数,已转厂未收货数)
     */
    private Double transferBalance = null; 
    
	/**
	 * 已送货未转厂数 
	 */
	private Double unTransHadSendNum;
	
	/**
	 * 已转厂未送货数
	 */
	private Double unSendHadTransNum;
	
	/**
	 * 已收货未转厂数
	 */
	private Double unTransHadReceiveNum;
	
	/**
	 * 已转厂未收货数
	 */
	private Double unReceiveHadTransNum;
    
	/**
	 * 差异数
	 */
	private Double diffAmount;
	 /**
     * USD 关税
     */
    private Double usd = null;
    
    /**
     * USD 增值税
     */
    private Double usdAdd = null;
	
	
	/**
	 * @return the serialNumber
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the section
	 */
	public ECSSection getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(ECSSection section) {
		this.section = section;
	}
	/**
	 * @return the seqNum
	 */
	public Integer getSeqNum() {
		return seqNum;
	}
	/**
	 * @param seqNum the seqNum to set
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	/**
	 * @return the hsName
	 */
	public String getHsName() {
		return hsName;
	}
	/**
	 * @param hsName the hsName to set
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}
	/**
	 * @return the hsSpec
	 */
	public String getHsSpec() {
		return hsSpec;
	}
	/**
	 * @param hsSpec the hsSpec to set
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}
	/**
	 * @return the hsUnit
	 */
	public String getHsUnit() {
		return hsUnit;
	}
	/**
	 * @param hsUnit the hsUnit to set
	 */
	public void setHsUnit(String hsUnit) {
		this.hsUnit = hsUnit;
	}
	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}
	/**
	 * @return the emsBalance
	 */
	public Double getEmsBalance() {
		return emsBalance;
	}
	/**
	 * @param emsBalance the emsBalance to set
	 */
	public void setEmsBalance(Double emsBalance) {
		this.emsBalance = emsBalance;
	}
	/**
	 * @return the stockBalance
	 */
	public Double getStockBalance() {
		return stockBalance;
	}
	/**
	 * @param stockBalance the stockBalance to set
	 */
	public void setStockBalance(Double stockBalance) {
		this.stockBalance = stockBalance;
	}
	/**
	 * @return the transferBalance
	 */
	public Double getTransferBalance() {
		return transferBalance;
	}
	/**
	 * @param transferBalance the transferBalance to set
	 */
	public void setTransferBalance(Double transferBalance) {
		this.transferBalance = transferBalance;
	}
	/**
	 * @return the diffAmount
	 */
	public Double getDiffAmount() {
		return diffAmount;
	}
	/**
	 * @param diffAmount the diffAmount to set
	 */
	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}
	/**
	 * @return the usd
	 */
	public Double getUsd() {
		return usd;
	}
	/**
	 * @param usd the usd to set
	 */
	public void setUsd(Double usd) {
		this.usd = usd;
	}
	/**
	 * @return the usdAdd
	 */
	public Double getUsdAdd() {
		return usdAdd;
	}
	/**
	 * @param usdAdd the usdAdd to set
	 */
	public void setUsdAdd(Double usdAdd) {
		this.usdAdd = usdAdd;
	}
	/**
	 * @return the emsNo
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * @param emsNo the emsNo to set
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	
	
	public Double getImportAmount() {
		return importAmount;
	}
	public void setImportAmount(Double importAmount) {
		this.importAmount = importAmount;
	}
	
	public Double getExgWasteAmount() {
		return exgWasteAmount;
	}
	public void setExgWasteAmount(Double exgWasteAmount) {
		this.exgWasteAmount = exgWasteAmount;
	}
	public Double getStockAmountImg() {
		return stockAmountImg;
	}
	public void setStockAmountImg(Double stockAmountImg) {
		this.stockAmountImg = stockAmountImg;
	}
	public Double getStockAmountProcessImg() {
		return stockAmountProcessImg;
	}
	public void setStockAmountProcessImg(Double stockAmountProcessImg) {
		this.stockAmountProcessImg = stockAmountProcessImg;
	}
	public Double getStockAmountExg() {
		return stockAmountExg;
	}
	public void setStockAmountExg(Double stockAmountExg) {
		this.stockAmountExg = stockAmountExg;
	}
	public Double getStockAmountOutSendExg() {
		return stockAmountOutSendExg;
	}
	public void setStockAmountOutSendExg(Double stockAmountOutSendExg) {
		this.stockAmountOutSendExg = stockAmountOutSendExg;
	}
	public Double getStockAmountOutFactoryImg() {
		return stockAmountOutFactoryImg;
	}
	public void setStockAmountOutFactoryImg(Double stockAmountOutFactoryImg) {
		this.stockAmountOutFactoryImg = stockAmountOutFactoryImg;
	}
	public Double getStockAmountBuyImg() {
		return stockAmountBuyImg;
	}
	public void setStockAmountBuyImg(Double stockAmountBuyImg) {
		this.stockAmountBuyImg = stockAmountBuyImg;
	}
	public Double getStockAmountTravelingImg() {
		return stockAmountTravelingImg;
	}
	public void setStockAmountTravelingImg(Double stockAmountTravelingImg) {
		this.stockAmountTravelingImg = stockAmountTravelingImg;
	}
	public Double getStockAmountTravelingExg() {
		return stockAmountTravelingExg;
	}
	public void setStockAmountTravelingExg(Double stockAmountTravelingExg) {
		this.stockAmountTravelingExg = stockAmountTravelingExg;
	}
	public Double getStockAmountBadImg() {
		return stockAmountBadImg;
	}
	public void setStockAmountBadImg(Double stockAmountBadImg) {
		this.stockAmountBadImg = stockAmountBadImg;
	}

	public Double getStockAmountSemiExgHStore() {
		return stockAmountSemiExgHStore;
	}
	public void setStockAmountSemiExgHStore(Double stockAmountSemiExgHStore) {
		this.stockAmountSemiExgHStore = stockAmountSemiExgHStore;
	}
	public Double getStockAmountFinishingExg() {
		return stockAmountFinishingExg;
	}
	public void setStockAmountFinishingExg(Double stockAmountFinishingExg) {
		this.stockAmountFinishingExg = stockAmountFinishingExg;
	}
	public Double getStockAmountOutSendExgPt() {
		return stockAmountOutSendExgPt;
	}
	public void setStockAmountOutSendExgPt(Double stockAmountOutSendExgPt) {
		this.stockAmountOutSendExgPt = stockAmountOutSendExgPt;
	}
	public Double getUnTransHadSendNum() {
		return unTransHadSendNum;
	}
	public void setUnTransHadSendNum(Double unTransHadSendNum) {
		this.unTransHadSendNum = unTransHadSendNum;
	}
	public Double getUnSendHadTransNum() {
		return unSendHadTransNum;
	}
	public void setUnSendHadTransNum(Double unSendHadTransNum) {
		this.unSendHadTransNum = unSendHadTransNum;
	}
	public Double getUnTransHadReceiveNum() {
		return unTransHadReceiveNum;
	}
	public void setUnTransHadReceiveNum(Double unTransHadReceiveNum) {
		this.unTransHadReceiveNum = unTransHadReceiveNum;
	}
	public Double getUnReceiveHadTransNum() {
		return unReceiveHadTransNum;
	}
	public void setUnReceiveHadTransNum(Double unReceiveHadTransNum) {
		this.unReceiveHadTransNum = unReceiveHadTransNum;
	}
	public void init() {
		price = 0.0;
	    emsBalance = 0.0; 
	    stockBalance = 0.0;
	    transferBalance = 0.0; 
	    unReceiveHadTransNum= 0.0;
	    unTransHadReceiveNum= 0.0;
	    unSendHadTransNum= 0.0;
	    unTransHadSendNum= 0.0;
	    stockAmountOutSendExgPt= 0.0;
	    stockAmountFinishingExg= 0.0;
	    stockAmountSemiExgHStore= 0.0;
	    stockAmountBadImg= 0.0;
	    stockAmountTravelingExg= 0.0;
	    stockAmountTravelingImg= 0.0;
	    stockAmountBuyImg= 0.0;
	    stockAmountOutFactoryImg= 0.0;
	    stockAmountOutSendExg= 0.0;
	    stockAmountExg= 0.0;
	    stockAmountProcessImg= 0.0;
	    stockAmountImg= 0.0;
	    exgWasteAmount= 0.0;
	    importAmount= 0.0;
		diffAmount = 0.0;
	    usd = 0.0;
	    usdAdd = 0.0;
	}
}
