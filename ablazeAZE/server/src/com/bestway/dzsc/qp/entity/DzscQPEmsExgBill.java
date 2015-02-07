/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.qp.entity;


/**
 * 存放手册通过备案的成品资料
 * 
 * @author yp
 */
public class DzscQPEmsExgBill implements java.io.Serializable {

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
	 * 商品名称
	 */
	private String name = null;

	/**
	 * 型号规格
	 */
	private String spec = null;

	/**
	 * 出口数量
	 */
	private Double amount = null;

	/**
	 * 单位
	 */
	private String unitCode = null;

	/**
	 * 单价
	 */
	private Double price = null;

	/**
	 * 金额
	 */
	private Double money = null;

	/**
	 * 原料费
	 */
	private Double imgMoney = null;

	/**
	 * 消费国
	 */
	private String countryCode = null;

	/**
	 * 加工费单价
	 */
	private Double machinPrice = null;

	/**
	 * 加工费总价
	 */
	private Double machinMoney = null;

	/**
	 * 补充说明
	 */
	private String note = null;

	/**
	 * 单位净重
	 */
	private Double unitNetWeight = null;

	/**
	 * 单位毛重
	 */
	private Double unitGrossWeight = null;

	/**
	 * 增减免税方式
	 */
	private String levyModeCode = null;

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
	private Double dutyRatio = null;

	/**
	 * 物料号码(发送报文时需要，不做保存)
	 */
	private String materialPtNo = null;

	/**
	 * 报关助记码
	 */
	private String customsNo = null;

	/**
	 * 申报状态 1-企业不申报； 2-企业申报； 9-已核定
	 * 
	 */
	private Double dutyRate = null;
	

	/**
	 * 获取出口数量
	 * 
	 * @return amount 出口数量
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 设置出口数量
	 * 
	 * @param amount
	 *            出口数量
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 获取原料费
	 * 
	 * @return imgMoney 原料费
	 */
	public Double getImgMoney() {
		return imgMoney;
	}

	/**
	 * 设置原料费
	 * 
	 * @param imgMoney
	 *            原料费
	 */
	public void setImgMoney(Double imgMoney) {
		this.imgMoney = imgMoney;
	}

	/**
	 * 获取加工费总价
	 * 
	 * @return machinMoney 加工费总价
	 */
	public Double getMachinMoney() {
		return machinMoney;
	}

	/**
	 * 设置加工费总价
	 * 
	 * @param machinMoney
	 *            加工费总价
	 */
	public void setMachinMoney(Double machinMoney) {
		this.machinMoney = machinMoney;
	}

	/**
	 * 获取加工费单价
	 * 
	 * @return machinPrice 加工费单价
	 */
	public Double getMachinPrice() {
		return machinPrice;
	}

	/**
	 * 设置加工费单价
	 * 
	 * @param machinPrice
	 *            加工费单价
	 */
	public void setMachinPrice(Double machinPrice) {
		this.machinPrice = machinPrice;
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
	 * 获取商品名称
	 * 
	 * @return name 商品名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param name
	 *            商品名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取补充说明
	 * 
	 * @return note 补充说明
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置补充说明
	 * 
	 * @param note
	 *            补充说明
	 */
	public void setNote(String note) {
		this.note = note;
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
	 * 获取单位毛重
	 * 
	 * @return unitGrossWeight 单位毛重
	 */
	public Double getUnitGrossWeight() {
		return unitGrossWeight;
	}

	/**
	 * 设置单位毛重
	 * 
	 * @param unitGrossWeight
	 *            单位毛重
	 */
	public void setUnitGrossWeight(Double unitGrossWeight) {
		this.unitGrossWeight = unitGrossWeight;
	}

	/**
	 * 获取单位净重
	 * 
	 * @return unitNetWeight 单位净重
	 */
	public Double getUnitNetWeight() {
		return unitNetWeight;
	}

	/**
	 * 设置单位净重
	 * 
	 * @param unitNetWeight
	 *            单位净重
	 */
	public void setUnitNetWeight(Double unitNetWeight) {
		this.unitNetWeight = unitNetWeight;
	}

	/**
	 * @param seqNum
	 *            The seqNum to set.
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * @return Returns the seqNum.
	 */
	public Integer getSeqNum() {
		return seqNum;
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
	 * 获取凭证序号
	 * 
	 * @return tenSeqNum 凭证序号
	 */
	public Integer getTenSeqNum() {
		return tenSeqNum;
	}

	/**
	 * 设置凭证序号
	 * 
	 * @param tenSeqNum
	 *            凭证序号
	 */
	public void setTenSeqNum(Integer tenSeqNum) {
		this.tenSeqNum = tenSeqNum;
	}

	public String getMaterialPtNo() {
		return materialPtNo;
	}

	public void setMaterialPtNo(String materialPtNo) {
		this.materialPtNo = materialPtNo;
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

	public String getCustomsNo() {
		return customsNo;
	}

	public void setCustomsNo(String customsNo) {
		this.customsNo = customsNo;
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