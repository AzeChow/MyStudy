package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;
/**
 * 工厂库存统计分析总表
 * @author chl
 */
public class VFStockAnalyse extends BaseScmEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 序号 
     */
    private Integer serialNumber; 
	/**
	 * 归并序号
	 */
	private Integer mergerNo;
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
	private Double stockAmountOutSend;
	
	/**
	 * 厂外存放库存
	 */
	private Double stockAmountOutFactory;
	
	/**
	 * 内购库存
	 */
	private Double stockAmountBuy;
	
	/**
	 * 在途库存料件
	 */
	private Double stockAmountTraveling;
	/**
	 * 在途库存成品
	 */
	private Double stockAmountTravelingExg;
	
	/**
	 * 半成品库存（已入库）
	 */
	private Double stockAmountSemiExgHadStore;
	
	
	/**
	 * 残次品库存
	 */
	private Double stockAmountBad;
	
	/**
	 * 在制品库存
	 */
	private Double stockAmountFinishing;
	
	/**
	 * 库存总量
	 */
	private Double stockTotalAmount;
	/**
	 * 批次号
	 */
	private VFSection section;
	
	public Integer getMergerNo() {
		return mergerNo;
	}
	public void setMergerNo(Integer mergerNo) {
		this.mergerNo = mergerNo;
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
	public Double getStockAmountOutSend() {
		return stockAmountOutSend;
	}
	public void setStockAmountOutSend(Double stockAmountOutSend) {
		this.stockAmountOutSend = stockAmountOutSend;
	}
	public Double getStockAmountOutFactory() {
		return stockAmountOutFactory;
	}
	public void setStockAmountOutFactory(Double stockAmountOutFactory) {
		this.stockAmountOutFactory = stockAmountOutFactory;
	}
	public Double getStockAmountBuy() {
		return stockAmountBuy;
	}
	public void setStockAmountBuy(Double stockAmountBuy) {
		this.stockAmountBuy = stockAmountBuy;
	}
	public Double getStockAmountTraveling() {
		return stockAmountTraveling;
	}
	public void setStockAmountTraveling(Double stockAmountTraveling) {
		this.stockAmountTraveling = stockAmountTraveling;
	}
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
	}
	
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
	public Double getStockTotalAmount() {
		return stockTotalAmount;
	}
	public void setStockTotalAmount(Double stockTotalAmount) {
		this.stockTotalAmount = stockTotalAmount;
	}
	
	
	public Double getStockAmountBad() {
		return stockAmountBad;
	}
	public void setStockAmountBad(Double stockAmountBad) {
		this.stockAmountBad = stockAmountBad;
	}
	public Double getStockAmountFinishing() {
		return stockAmountFinishing;
	}
	public void setStockAmountFinishing(Double stockAmountFinishing) {
		this.stockAmountFinishing = stockAmountFinishing;
	}
	public void init(){
		stockAmountImg = 0d;
		stockAmountProcessImg = 0d;
		stockAmountExg = 0d;
		stockAmountOutSend = 0d;
		stockAmountOutFactory= 0d;
		stockAmountBuy= 0d;
		stockAmountTraveling= 0d;
		stockAmountTravelingExg = 0d;
		stockTotalAmount= 0d;
		stockAmountSemiExgHadStore = 0d;
		stockAmountBad = 0d;
		stockAmountFinishing = 0d;
	}
	public Double getStockAmountSemiExgHadStore() {
		return stockAmountSemiExgHadStore;
	}
	public void setStockAmountSemiExgHadStore(Double stockAmountSemiExgHadStore) {
		this.stockAmountSemiExgHadStore = stockAmountSemiExgHadStore;
	}
	public Double getStockAmountTravelingExg() {
		return stockAmountTravelingExg;
	}
	public void setStockAmountTravelingExg(Double stockAmountTravelingExg) {
		this.stockAmountTravelingExg = stockAmountTravelingExg;
	}
}
