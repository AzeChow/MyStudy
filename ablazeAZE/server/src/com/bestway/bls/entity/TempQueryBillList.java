package com.bestway.bls.entity;

import java.io.Serializable;

/**
 * QueryBillList临时表
 * 
 * @author hw
 * 
 */
public class TempQueryBillList implements Serializable {

	/**
	 * 表体序号
	 */
	private String no = null;

	/**
	 * 获取表体序号
	 * 
	 * @return
	 */
	public String getNo() {
		return no;
	}

	/**
	 * 设置表体序号
	 * 
	 * @param no
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * 获取商品名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取商品规格
	 * 
	 * @param model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * 设置商品规格
	 * 
	 * @param model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 获取申报单位编码
	 * 
	 * @return
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * 设置申报单位编码
	 * 
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * 基本号
	 */
	private String copGNo = null;
	/**
	 * 在电子帐册中的商品序号
	 */
	private int contrItem;
	/**
	 * 商品编码
	 */
	private String codeTS = null;
	/**
	 * 商品名称
	 */
	private String name = null;
	/**
	 * 商品规格
	 */
	private String model = null;
	/**
	 * 法定第一数量
	 */
	private Double qty1 = null;
	/**
	 * 法定第二数量
	 */
	private Double qty2 = null;
	/**
	 * 法定第一单位
	 */
	private String unit1=null;
	/**
	 * 法定第二单位
	 */
	private String unit2=null;
	/**
	 * 申报单位编码
	 */
	private String unit = null;
	/**
	 * 申报数量
	 */
	private Double qty = null;
	/**
	 * 申报单价
	 */
	private Double unitPrice = null;
	/**
	 * 申报总价
	 */
	private Double totalPrice=null;

	/**
	 * 币值
	 */
	private String curr = null;
	/**
	 * 原产国
	 */
	private String originCountry = null;
	/**
	 * 贸易方式
	 */
	private String tradeMode = null;
	/**
	 * 成交方式
	 */
	private String transMode = null;
	/**
	 * 库存核扣明细
	 */
	private TempCollateEntity collateMftStock = null;
	/**
	 * 出入仓核销明细
	 */
	private TempCollateEntity collateMftInOut = null;
	/**
	 * 仓单报关单明细
	 */
	private TempCollateEntity collateMftEnt = null;

	


	


	/**
	 * 获取基本号
	 * 
	 * @return
	 */
	public String getCopGNo() {
		return copGNo;
	}

	/**
	 * 设置基本号
	 * 
	 * @param copGNo
	 */
	public void setCopGNo(String copGNo) {
		this.copGNo = copGNo;
	}

	/**
	 * 获取在电子账册中的商品序号
	 * 
	 * @return
	 */
	public int getContrItem() {
		return contrItem;
	}

	/**
	 * 设置在电子账册中的商品序号
	 * 
	 * @param contrItem
	 */
	public void setContrItem(int contrItem) {
		this.contrItem = contrItem;
	}

	/**
	 * 获取商品编码
	 * 
	 * @return
	 */
	public String getCodeTS() {
		return codeTS;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param codeTS
	 */
	public void setCodeTS(String codeTS) {
		this.codeTS = codeTS;
	}

	/**
	 * 获取法定第一数量
	 * 
	 * @return
	 */
	public Double getQty1() {
		return qty1;
	}

	/**
	 * 设置法定第一数量
	 * 
	 * @param qty1
	 */
	public void setQty1(Double qty1) {
		this.qty1 = qty1;
	}

	/**
	 * 获取法定第二数量
	 * 
	 * @return
	 */
	public Double getQty2() {
		return qty2;
	}

	/**
	 * 设置法定第二数量
	 * 
	 * @param qty2
	 */
	public void setQty2(Double qty2) {
		this.qty2 = qty2;
	}

	/**
	 * 获取申报数量
	 * 
	 * @return
	 */
	public Double getQty() {
		return qty;
	}

	/**
	 * 设置申报数量
	 * 
	 * @param qty
	 */
	public void setQty(Double qty) {
		this.qty = qty;
	}

	/**
	 * 获取申报单价
	 * 
	 * @return
	 */
	public Double getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 设置申报单价
	 * 
	 * @param unitPrice
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 获取币制
	 * 
	 * @return
	 */
	public String getCurr() {
		return curr;
	}

	/**
	 * 设置币制
	 * 
	 * @param curr
	 */
	public void setCurr(String curr) {
		this.curr = curr;
	}

	/**
	 * 获取原产国
	 * 
	 * @return
	 */
	public String getOriginCountry() {
		return originCountry;
	}

	/**
	 * 设置原产国
	 * 
	 * @param originCountry
	 */
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	/**
	 * 获取贸易方式
	 * 
	 * @return
	 */
	public String getTradeMode() {
		return tradeMode;
	}

	/**
	 * 设置贸易方式
	 * 
	 * @param tradeMode
	 */
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}

	/**
	 * 获取成交方式
	 * 
	 * @return
	 */
	public String getTransMode() {
		return transMode;
	}

	/**
	 * 设置成交方式
	 * 
	 * @param transMode
	 */
	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	/**
	 * 获取出入仓核销明细
	 * 
	 * @return
	 */
	public TempCollateEntity getCollateMftInOut() {
		return collateMftInOut;
	}

	/**
	 * 设置出入仓核销明细
	 * 
	 * @param collateMftInOut
	 */
	public void setCollateMftInOut(TempCollateEntity collateMftInOut) {
		this.collateMftInOut = collateMftInOut;
	}

	/**
	 * 获取仓单报关单明细
	 * 
	 * @return
	 */
	public TempCollateEntity getCollateMftEnt() {
		return collateMftEnt;
	}

	/**
	 * 设置仓单报关单明细
	 * 
	 * @param collateMftEnt
	 */
	public void setCollateMftEnt(TempCollateEntity collateMftEnt) {
		this.collateMftEnt = collateMftEnt;
	}
	
	/**
	 * 获取申报总价
	 * @return
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}
    /**
     * 设置申报总价
     * @param totalPrice
     */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUnit1() {
		return unit1;
	}

	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}

	public String getUnit2() {
		return unit2;
	}

	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	public TempCollateEntity getCollateMftStock() {
		return collateMftStock;
	}

	public void setCollateMftStock(TempCollateEntity collateMftStock) {
		this.collateMftStock = collateMftStock;
	}
}
