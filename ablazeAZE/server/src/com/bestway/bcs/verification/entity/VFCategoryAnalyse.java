package com.bestway.bcs.verification.entity;

import com.bestway.common.BaseScmEntity;

/**
 * 大类别分析表
 * @author xc 
 */
public class VFCategoryAnalyse extends BaseScmEntity{

	/**
     * 序号 
     */
    private Integer serialNumber;
    /**
     * 大类序号以*开头，或归并序号
     */
    private String seqNum;
    /**
	 * 归并序号
	 */
	private Integer mergerNo;
    /**
	 * 海关料件名称
	 */
	private String hsName;
	/**
	 * 海关料件规格
	 */
	private String hsSpec;
	/**
	 * 海关料件单位
	 */
	private String hsUnit;
	/**
	 * 进口数量
	 */
	private Double impAmount;
	
	/**
	 * 出口总耗用
	 */
	private Double expAmount;
	/**
	 * 合同应剩余量
	 */
	private Double contractLeaveNum;
	/**
	 * 料件库存数量
	 */
	private Double stockAmountImg;
	/**
	 * 在产品库存数量
	 */
	private Double stockAmountProcessImg;
	/**
	 * 成品库存
	 */
	private Double stockAmountExg;
	/**
	 * 外发库存
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
	 * 在途库存
	 */
	private Double stockAmountTraveling;
	/**
	 * 库存数量
	 */
	private Double stockTotalAmount;
	/**
	 * 已转厂未收货
	 */
	private Double unReceiveHadTransNum;
	
	/**
	 * 已送货未转厂
	 */
	private Double unTransHadSendNum;
	/**
	 * 已转厂未送货
	 */
	private Double unSendHadTransNum;
	/**
	 * 已收货未转厂
	 */
	private Double unTransHadReceiveNum;
	/**
	 * 结转差额
	 */
	private Double transferDiffNum;
	/**
	 * 短溢数量
	 */
	private Double overOrshortNum;
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
	public Double getImpAmount() {
		return impAmount;
	}
	public void setImpAmount(Double impAmount) {
		this.impAmount = impAmount;
	}
	public Double getExpAmount() {
		return expAmount;
	}
	public void setExpAmount(Double expAmount) {
		this.expAmount = expAmount;
	}
	public Double getContractLeaveNum() {
		return contractLeaveNum;
	}
	public void setContractLeaveNum(Double contractLeaveNum) {
		this.contractLeaveNum = contractLeaveNum;
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
	public Double getStockTotalAmount() {
		return stockTotalAmount;
	}
	public void setStockTotalAmount(Double stockTotalAmount) {
		this.stockTotalAmount = stockTotalAmount;
	}
	public Double getUnReceiveHadTransNum() {
		return unReceiveHadTransNum;
	}
	public void setUnReceiveHadTransNum(Double unReceiveHadTransNum) {
		this.unReceiveHadTransNum = unReceiveHadTransNum;
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
	public Double getTransferDiffNum() {
		return transferDiffNum;
	}
	public void setTransferDiffNum(Double transferDiffNum) {
		this.transferDiffNum = transferDiffNum;
	}
	public Double getOverOrshortNum() {
		return overOrshortNum;
	}
	public void setOverOrshortNum(Double overOrshortNum) {
		this.overOrshortNum = overOrshortNum;
	}
	public VFSection getSection() {
		return section;
	}
	public void setSection(VFSection section) {
		this.section = section;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public void init(){
		this.impAmount = 0.0;
		this.expAmount = 0.0;
		this.contractLeaveNum = 0.0;
		this.stockAmountImg = 0.0;
		this.stockAmountProcessImg = 0.0;
		this.stockAmountExg = 0.0;
		this.stockAmountOutSend = 0.0;
		this.stockAmountOutFactory = 0.0;
		this.stockAmountBuy = 0.0;
		this.stockAmountTraveling = 0.0;
		this.stockTotalAmount = 0.0;
		this.unReceiveHadTransNum = 0.0;
		this.unTransHadSendNum = 0.0;
		this.unSendHadTransNum = 0.0;
		this.unTransHadReceiveNum = 0.0;
		this.transferDiffNum = 0.0;
		this.overOrshortNum = 0.0;
	}
}
