package com.bestway.bcus.checkstock.entity;

import com.bestway.bcs.contractstat.entity.StockStatisticsBill;
import com.bestway.common.BaseScmEntity;
/**
 * 工厂库存统计分析总表
 * @author chl
 */
public class ECSStockAnalyse extends BaseScmEntity {

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
	 * 备案序号
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
	 *外发库存
	 */
	private Double stockAmountOutSendExgPt;
	/**
	 *  总库存
	 */
	private Double stockTotalAmount;
	/**
	 * 批次号
	 */
	private ECSSection section;
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getHsName() {
		return hsName;
	}
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}
	public String getHsSpec() {
		return hsSpec;
	}
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}
	public String getHsUnit() {
		return hsUnit;
	}
	public void setHsUnit(String hsUnit) {
		this.hsUnit = hsUnit;
	}
	public Double getStockAmountImg() {
		return stockAmountImg;
	}
	public void setStockAmountImg(Double stockAmountImg) {
		this.stockAmountImg = stockAmountImg;
	}
	public Double getStockTotalAmount() {
		return stockTotalAmount;
	}
	public void setStockTotalAmount(Double stockTotalAmount) {
		this.stockTotalAmount = stockTotalAmount;
	}
	public ECSSection getSection() {
		return section;
	}
	public void setSection(ECSSection section) {
		this.section = section;
	}
	/**
	 * @return the stockAmountExg
	 */
	public Double getStockAmountExg() {
		return stockAmountExg;
	}
	/**
	 * @param stockAmountExg the stockAmountExg to set
	 */
	public void setStockAmountExg(Double stockAmountExg) {
		this.stockAmountExg = stockAmountExg;
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
	 * @return the stockAmountProcessImg
	 */
	public Double getStockAmountProcessImg() {
		return stockAmountProcessImg;
	}
	/**
	 * @param stockAmountProcessImg the stockAmountProcessImg to set
	 */
	public void setStockAmountProcessImg(Double stockAmountProcessImg) {
		this.stockAmountProcessImg = stockAmountProcessImg;
	}
	/**
	 * @return the stockAmountOutSendExg
	 */
	public Double getStockAmountOutSendExg() {
		return stockAmountOutSendExg;
	}
	/**
	 * @param stockAmountOutSendExg the stockAmountOutSendExg to set
	 */
	public void setStockAmountOutSendExg(Double stockAmountOutSendExg) {
		this.stockAmountOutSendExg = stockAmountOutSendExg;
	}
	/**
	 * @return the stockAmountOutFactoryImg
	 */
	public Double getStockAmountOutFactoryImg() {
		return stockAmountOutFactoryImg;
	}
	/**
	 * @param stockAmountOutFactoryImg the stockAmountOutFactoryImg to set
	 */
	public void setStockAmountOutFactoryImg(Double stockAmountOutFactoryImg) {
		this.stockAmountOutFactoryImg = stockAmountOutFactoryImg;
	}
	/**
	 * @return the stockAmountBuyImg
	 */
	public Double getStockAmountBuyImg() {
		return stockAmountBuyImg;
	}
	/**
	 * @param stockAmountBuyImg the stockAmountBuyImg to set
	 */
	public void setStockAmountBuyImg(Double stockAmountBuyImg) {
		this.stockAmountBuyImg = stockAmountBuyImg;
	}
	/**
	 * @return the stockAmountTravelingImg
	 */
	public Double getStockAmountTravelingImg() {
		return stockAmountTravelingImg;
	}
	/**
	 * @param stockAmountTravelingImg the stockAmountTravelingImg to set
	 */
	public void setStockAmountTravelingImg(Double stockAmountTravelingImg) {
		this.stockAmountTravelingImg = stockAmountTravelingImg;
	}
	/**
	 * @return the stockAmountTravelingExg
	 */
	public Double getStockAmountTravelingExg() {
		return stockAmountTravelingExg;
	}
	/**
	 * @param stockAmountTravelingExg the stockAmountTravelingExg to set
	 */
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
	public void init() {
		// 料件库存数量
		stockAmountImg = 0.0;
		// 成品折算数量
		stockAmountExg = 0.0;
		// 总库存
		stockTotalAmount = 0.0;
		stockAmountBuyImg = 0.0;
		stockAmountOutFactoryImg = 0.0;
		stockAmountOutSendExg = 0.0;
		stockAmountProcessImg = 0.0;
		stockAmountTravelingExg = 0.0;
		stockAmountTravelingImg = 0.0;
		stockAmountBadImg = 0.0;
		stockAmountFinishingExg= 0.0;
		stockAmountSemiExgHStore = 0.0;
		stockAmountOutSendExgPt = 0.0;
	}
	
}
