package com.bestway.common.transferfactory.entity;

import java.math.BigDecimal;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 结转单据商品信息
 * 
 * @author Administrator
 * 
 */
public class TransferFactoryCommodityInfo extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 转厂进出货单表头
	 */
	private TransferFactoryBill transferFactoryBill = null;

	/**
	 * 手册号
	 */
	private String emsNo = null;

	/**
	 * 帐册序号
	 */
	private Integer seqNum = null;

	/**
	 * 海关商品编码
	 */
	private Complex complex = null;

	/**
	 * 商品名称
	 */
	private String commName = null;

	/**
	 * 商品规格
	 */
	private String commSpec = null;

	/**
	 * 单位
	 */
	private Unit unit = null;

	/**
	 * 关封数量
	 */
	private Double quantity = null;

	/**
	 * 送货数量
	 */
	private Double transFactAmount = null;

	/**
	 * 单价
	 */
	private Double unitPrice = null;

	/**
	 * 总价
	 */
	private Double totalPrice = null;

	/**
	 * 汇率
	 */
	private Double exchangeRate;

	/**
	 * 币制
	 */
	private Curr curr = null;

	/**
	 * 毛重
	 */
	private Double grossWeight = null;

	/**
	 * 净重(单位重量)
	 */
	private Double netWeight = null;

	/**
	 * 产销国
	 */
	private Country country = null;

	/**
	 * 版本号
	 */
	private String version = null;

	/**
	 * 来源单据号
	 */
	private String sourceBill = null;

	/**
	 * 体积
	 */
	private Double cubage = null;

	/**
	 * 备注
	 */
	private String memo = null;

	/**
	 * 是否转报关清单
	 */
	private Boolean isTransferATC = false;

	/**
	 * 是否转关封申请单
	 */
	private Boolean isTransferCustomsEnvelopReqeust = false;

	/**
	 * 在归并关系是否备案
	 */
	private Boolean isPutOnRecord = false;

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommSpec() {
		return commSpec;
	}

	public void setCommSpec(String commSpec) {
		this.commSpec = commSpec;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * @return Returns the isTransferATC.
	 */
	public Boolean getIsTransferATC() {
		return isTransferATC;
	}

	/**
	 * @param isTransferATC
	 *            The isTransferATC to set.
	 */
	public void setIsTransferATC(Boolean isTransferATC) {
		this.isTransferATC = isTransferATC;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Double getTransFactAmount() {
		return transFactAmount;
	}

	public void setTransFactAmount(Double transFactAmount) {
		this.transFactAmount = transFactAmount;
	}

	public Double getTotalPrice() {
		if (this.totalPrice != null && this.totalPrice != 0.0) {
			return this.totalPrice;
		}
		if (this.transFactAmount == null || this.unitPrice == null) {
			return Double.valueOf(0);
		}
		BigDecimal b = new BigDecimal(this.transFactAmount.doubleValue()
				* this.unitPrice.doubleValue());
		return Double.valueOf(b.setScale(4, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return Returns the country.
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            The country to set.
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**体积
	 * @return Returns the cubage.
	 */
	public Double getCubage() {
		return cubage;
	}

	/**体积
	 * @param cubage
	 *            The cubage to set.
	 */
	public void setCubage(Double cubage) {
		this.cubage = cubage;
	}

	/**
	 * @return Returns the curr.
	 */
	public Curr getCurr() {
		return curr;
	}

	/**
	 * @param curr
	 *            The curr to set.
	 */
	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**毛重
	 * @return Returns the grossWeight.
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}

	/**毛重
	 * @param grossWeight
	 *            The grossWeight to set.
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	// /**
	// * @return Returns the materiel.
	// */
	// public Materiel getMateriel() {
	// return materiel;
	// }
	//
	// /**
	// * @param materiel
	// * The materiel to set.
	// */
	// public void setMateriel(Materiel materiel) {
	// this.materiel = materiel;
	// }

	/**
	 * @return Returns the memo.
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            The memo to set.
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return Returns the netWeight.
	 */
	public Double getNetWeight() {
		return netWeight;
	}

	/**
	 * @param netWeight
	 *            The netWeight to set.
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * @return Returns the sourceBill.
	 */
	public String getSourceBill() {
		return sourceBill;
	}

	/**
	 * @param sourceBill
	 *            The sourceBill to set.
	 */
	public void setSourceBill(String sourceBill) {
		this.sourceBill = sourceBill;
	}

	/**
	 * @return Returns the transferFactoryBill.
	 */
	public TransferFactoryBill getTransferFactoryBill() {
		return transferFactoryBill;
	}

	/**
	 * @param transferFactoryBill
	 *            The transferFactoryBill to set.
	 */
	public void setTransferFactoryBill(TransferFactoryBill transferFactoryBill) {
		this.transferFactoryBill = transferFactoryBill;
	}

	/**
	 * @return Returns the version.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            The version to set.
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @param quantity
	 *            The quantity to set.
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return Returns the isTransferCustomsEnvelopReqeust.
	 */
	public Boolean getIsTransferCustomsEnvelopReqeust() {
		return isTransferCustomsEnvelopReqeust;
	}

	/**
	 * @param isTransferCustomsEnvelopReqeust
	 *            The isTransferCustomsEnvelopReqeust to set.
	 */
	public void setIsTransferCustomsEnvelopReqeust(
			Boolean isTransferCustomsEnvelopReqeust) {
		this.isTransferCustomsEnvelopReqeust = isTransferCustomsEnvelopReqeust;
	}

	/**
	 * @return Returns the isPutOnRecord.
	 */
	public Boolean getIsPutOnRecord() {
		return isPutOnRecord;
	}

	/**
	 * @param isPutOnRecord
	 *            The isPutOnRecord to set.
	 */
	public void setIsPutOnRecord(Boolean isPutOnRecord) {
		this.isPutOnRecord = isPutOnRecord;
	}

	/**
	 * @param totalPrice
	 *            The totalPrice to set.
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
}