/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.qp.entity;


/**
 * 存放电子手册通关备案里的料件资料
 * 
 * @author yp
 */
public class DzscQPEmsImgBill implements java.io.Serializable{

	/**
	 * 备案序号
	 */
	private Integer seqNum = null;

	/**
	 * 归并序号
	 */
	private Integer tenSeqNum = null;

	/**
	 * 海关编码
	 */
	private String complexCode = null;

	/**
	 * 料件名称
	 */
	private String name = null;

	/**
	 * 型号规格
	 */
	private String spec = null;

	/**
	 * 单价
	 */
	private Double price = null;

	/**
	 * 数量
	 */
	private Double amount = null;

	/**
	 * 金额
	 */
	private Double money = null;

	/**
	 * 单位
	 */
	private String unitCode = null;

	/**
	 * 单位重量
	 */
	private Double unitWeight = null;

	/**
	 * 总重量
	 */
	private Double weight = null;

	/**
	 * 原产国
	 */
	private String countryCode = null;

	/**
	 * 增减免税方式
	 */
	private String levyModeCode = null;

	/**
	 * 说明
	 */
	private String note = null;

	/**
	 * 法定单位比例因子
	 */
	private Double legalUnitGene;

	/**
	 * 第二法定单位比例因子
	 */
	private Double legalUnit2Gene;

	/**
	 * 重量单位比例因子
	 */
	private Double weigthUnitGene;

	/**
	 * 出口征税比例
	 */
	private Double dutyRatio;

	/**
	 * 详细型号规格
	 */
	private String detailNote;

	/**
	 * 非保税料件比例
	 * 
	 */
	private Double dutyRate = null;

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 获取数量
	 * 
	 * @return amount 数量
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置数量
	 * 
	 * @param amount
	 *            数量
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 获取金额
	 * 
	 * @return money 金额
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置金额
	 * 
	 * @param money
	 *            金额
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * 获取料件名称
	 * 
	 * @return name 料件名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置料件名称
	 * 
	 * @param name
	 *            料件名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取单价
	 * 
	 * @return price 单价
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 设置单价
	 * 
	 * @param price
	 *            单价
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * 获取型号规格
	 * 
	 * @return spec 型号规格
	 */
	public String getSpec() {
		return spec;
	}

	/**
	 * 设置型号规格
	 * 
	 * @param spec
	 *            型号规格
	 */
	public void setSpec(String spec) {
		this.spec = spec;
	}


	/**
	 * 获取单位重量
	 * 
	 * @return unitWeight 单位重量
	 */
	public Double getUnitWeight() {
		return unitWeight;
	}

	/**
	 * 设置单位重量
	 * 
	 * @param unitWeight
	 *            单位重量
	 */
	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	/**
	 * 获取总重量
	 * 
	 * @return weight 总重量
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * 设置总重量
	 * 
	 * @param weight
	 *            总重量
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * 获取说明
	 * 
	 * @return note 说明
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置说明
	 * 
	 * @param note
	 *            说明
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取出口征税比例
	 * 
	 * @return dutyRatio 出口征税比例
	 */
	public Double getDutyRatio() {
		return dutyRatio;
	}

	/**
	 * 设置出口征税比例
	 * 
	 * @param dutyRatio
	 *            出口征税比例
	 */
	public void setDutyRatio(Double dutyRatio) {
		this.dutyRatio = dutyRatio;
	}

	/**
	 * 获取序号
	 * 
	 * @return tenSeqNum 序号
	 */
	public Integer getTenSeqNum() {
		return tenSeqNum;
	}

	/**
	 * 设置序号
	 * 
	 * @param tenSeqNum
	 *            序号
	 */
	public void setTenSeqNum(Integer tenSeqNum) {
		this.tenSeqNum = tenSeqNum;
	}

	public String getDetailNote() {
		return detailNote;
	}

	public void setDetailNote(String detailNote) {
		this.detailNote = detailNote;
	}

	public Double getLegalUnit2Gene() {
		return legalUnit2Gene;
	}

	public void setLegalUnit2Gene(Double legalUnit2Gene) {
		this.legalUnit2Gene = legalUnit2Gene;
	}

	public Double getLegalUnitGene() {
		return legalUnitGene;
	}

	public void setLegalUnitGene(Double legalUnitGene) {
		this.legalUnitGene = legalUnitGene;
	}

	public Double getWeigthUnitGene() {
		return weigthUnitGene;
	}

	public void setWeigthUnitGene(Double weigthUnitGene) {
		this.weigthUnitGene = weigthUnitGene;
	}

	public Double getDutyRate() {
		return dutyRate;
	}

	public void setDutyRate(Double dutyRate) {
		this.dutyRate = dutyRate;
	}

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLevyModeCode() {
		return levyModeCode;
	}

	public void setLevyModeCode(String levyModeCode) {
		this.levyModeCode = levyModeCode;
	}

}