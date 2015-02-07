package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;

/**
 * 仓单表体信息（StorageBillAfter） -- 相当于报关单体
 * 
 * @author kangbo
 */
public class StorageBillAfter extends BaseScmEntity {
	/**
	 * 仓单表头信息
	 * 
	 * @uml.property name="storageBill"
	 * @uml.associationEnd
	 */
	private StorageBill storageBill;
	/**
	 * GNo 表体序号(Integer) 流水号
	 * 
	 * @uml.property name="gNo"
	 */
	private Integer seqNo;
	/**
	 * GNo 归并序号(Integer)
	 * 
	 * @uml.property name="gNo"
	 */
	private Integer seqNum;

	/**
	 * CopGNo 基本号(String) 企业内部编号。没有填空。
	 * 
	 * @uml.property name="copGNo"
	 */
	private String copGNo;
	/**
	 * 载货清单(String) 非监管车辆没有载货清单
	 */
	private String tafName;

	/**
	 * GName 商品名称(String)
	 * 
	 * @uml.property name="gName"
	 */
	private String name;
	/**
	 * CodeTS 商品编码(Complex) 来自自用商品编码表
	 * 
	 * @uml.property name="codeTS"
	 * @uml.associationEnd
	 */
	private Complex codeTS;
	/**
	 * GModel 商品规格(String)
	 * 
	 * @uml.property name="gModel"
	 */
	private String model;
	/**
	 * QTY1 法定第一数量(Double) 当申报单位=第一法定单位，该数量=申报数量
	 * 
	 * @uml.property name="qTY1"
	 */
	private Double qty1;
	/**
	 * QTY2 法定第二数量(Double) 当申报单位=第二法定单位，该数量=申报数量
	 * 
	 * @uml.property name="qTY2"
	 */
	private Double qty2;
	/**
	 * GUnit 申报单位编码(Unit) 来自关务基础资料Unit表
	 * 
	 * @uml.property name="gUnit"
	 * @uml.associationEnd
	 */
	private Unit unit;
	/**
	 * Qty 申报数量(Double)
	 * 
	 * @uml.property name="qty"
	 */
	private Double qty;
	/**
	 * UnitPrice 申报单价(Double)
	 * 
	 * @uml.property name="unitPrice"
	 */
	private Double unitPrice;
	/**
	 * TotalPrice 申报总价(Double)
	 * 
	 * @uml.property name="totalP"
	 */
	private Double totalP;
	/**
	 * Curr 币制(Curr)
	 * 
	 * @uml.property name="curr"
	 * @uml.associationEnd
	 */
	private Curr curr;
	/**
	 * ContrItem 在电子帐册中的商品序号(Integer) 没有帐册则填0
	 * 
	 * @uml.property name="contrItem"
	 */
	private Integer contrItem;
	/**
	 * OriginCountry 原产国(Country) 没有可不填
	 * 
	 * @uml.property name="originCountry"
	 * @uml.associationEnd
	 */
	private Country originCountry;
	/**
	 * TradeMode 贸易方式(trade)
	 * 
	 * @uml.property name="tradeModel"
	 * @uml.associationEnd
	 */
	private Trade tradeModel;
	/**
	 * TransMode 成交方式(transac)
	 * 
	 * @uml.property name="transModel"
	 * @uml.associationEnd
	 */
	private Transac transModel;
	/**
	 * ApprTime 报关单申报时间(Date)
	 * 
	 * @uml.property name="apprTime"
	 */
	private Date apprTime;
	/**
	 * EntryID 报关单号(String) 没有可不填
	 * 
	 * @uml.property name="entryID"
	 */
	private String entryID;
	/**
	 * EntryGNo 在报关单中的商品序号(Integer) 没有可不填
	 * 
	 * @uml.property name="entryGNo"
	 */
	private Integer entryGNo;
	/**
	 * CorrBillNo 该出仓单对应的入仓单号(String) 出仓单中填写
	 * 
	 * @uml.property name="corrBillNo"
	 */
	private String corrBillNo;
	/**
	 * 该出仓单商品项对应入仓单中的商品项号(Integer) 出仓单中填写
	 */
	private Integer corrBillGNo;
	/**
	 * 是否出仓 进仓使用
	 */
	private Boolean isOut = false;

	/**
	 * remainingQuantity 对应的进仓单中的剩余数量(Double)
	 * 
	 * @uml.property name="remainingQuantity"
	 */
	private Double remainingQuantity;

	/**
	 * 获取剩余数量
	 * 
	 * @return
	 */
	public Double getRemainingQuantity() {
		return remainingQuantity;
	}

	/**
	 * 设置剩余数量
	 */
	public void setRemainingQuantity(Double remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}

	/**
	 * 进仓单据单据编号
	 */
	private String blsDocuments;

	/**
	 * 获取进仓单据单据编号
	 * 
	 * @return
	 */
	public String getBlsDocuments() {
		return blsDocuments;
	}

	/**
	 * 设置进仓单据单据编号
	 * 
	 * @param blsDocuments
	 */
	public void setBlsDocuments(String blsDocuments) {
		this.blsDocuments = blsDocuments;
	}

	/**
	 * 入仓单据商品明细流水号
	 */
	private Integer inBillGoodNo;

	/**
	 * 获取入仓单据商品明细流水号
	 * 
	 * @return
	 */
	public Integer getInBillGoodNo() {
		return inBillGoodNo;
	}

	/**
	 * 设置入仓单据商品明细流水号
	 * 
	 * @param inBillGoodNo
	 */
	public void setInBillGoodNo(Integer inBillGoodNo) {
		this.inBillGoodNo = inBillGoodNo;
	}

	/**
	 * 备注1
	 */
	private String remarks1;

	/**
	 * 获取备注1
	 * 
	 * @return
	 */
	public String getRemarks1() {
		return remarks1;
	}

	/**
	 * 设置备注1
	 * 
	 * @param remarks1
	 */
	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}

	/**
	 * 备注2
	 */
	private String remarks2;

	/**
	 * 获取备注2
	 * 
	 * @return
	 */
	public String getRemarks2() {
		return remarks2;
	}

	/**
	 * 设置备注2
	 * 
	 * @param remarks2
	 */
	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}

	public Boolean getIsOut() {
		return isOut;
	}

	public void setIsOut(Boolean isOut) {
		this.isOut = isOut;
	}

	public Integer getCorrBillGNo() {
		if (corrBillGNo == null) {
			return 0;
		}
		return corrBillGNo;
	}

	public void setCorrBillGNo(Integer corrBillGNo) {
		this.corrBillGNo = corrBillGNo;
	}

	public StorageBill getStorageBill() {
		return storageBill;
	}

	public void setStorageBill(StorageBill storageBill) {
		this.storageBill = storageBill;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getCopGNo() {
		return copGNo;
	}

	public void setCopGNo(String copGNo) {
		this.copGNo = copGNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Complex getCodeTS() {
		return codeTS;
	}

	public void setCodeTS(Complex codeTS) {
		this.codeTS = codeTS;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Double getQty1() {
		if (qty1 == null) {
			return 0.0;
		}
		return qty1;
	}

	public void setQty1(Double qty1) {
		this.qty1 = qty1;
	}

	public Double getQty2() {
		if (qty2 == null) {
			return 0.0;
		}
		return qty2;
	}

	public void setQty2(Double qty2) {
		this.qty2 = qty2;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Double getQty() {
		if (qty == null) {
			return 0.0;
		}
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalP() {
		return totalP;
	}

	public void setTotalP(Double totalP) {
		this.totalP = totalP;
	}

	public Curr getCurr() {
		return curr;
	}

	public void setCurr(Curr curr) {
		this.curr = curr;
	}

	/**
	 * 获取 ContrItem 在电子帐册中的商品序号
	 * 
	 * @return
	 */
	public Integer getContrItem() {
		if (contrItem == null) {
			return 0;
		}
		return contrItem;
	}

	/**
	 * 设置 ContrItem 在电子帐册中的商品序号
	 * 
	 * @param contrItem
	 */
	public void setContrItem(Integer contrItem) {
		this.contrItem = contrItem;
	}

	public Country getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(Country originCountry) {
		this.originCountry = originCountry;
	}

	public Trade getTradeModel() {
		return tradeModel;
	}

	public void setTradeModel(Trade tradeModel) {
		this.tradeModel = tradeModel;
	}

	public Transac getTransModel() {
		return transModel;
	}

	public void setTransModel(Transac transModel) {
		this.transModel = transModel;
	}

	public Date getApprTime() {
		if (apprTime == null) {
			return new Date();
		}
		return apprTime;
	}

	public void setApprTime(Date apprTime) {
		this.apprTime = apprTime;
	}

	public String getEntryID() {
		return entryID;
	}

	public void setEntryID(String entryID) {
		this.entryID = entryID;
	}

	public Integer getEntryGNo() {
		if (entryGNo == null) {
			return 0;
		}
		return entryGNo;
	}

	public void setEntryGNo(Integer entryGNo) {
		this.entryGNo = entryGNo;
	}

	public String getCorrBillNo() {
		return corrBillNo;
	}

	public void setCorrBillNo(String corrBillNo) {
		this.corrBillNo = corrBillNo;
	}

	public String getTafName() {
		return tafName;
	}

	public void setTafName(String tafName) {
		this.tafName = tafName;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
}
