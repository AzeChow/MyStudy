package com.bestway.common.fpt.entity;

import java.io.Serializable;
import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.materialbase.entity.ScmCoc;

public class TempCustomsEnvelopBillList implements Serializable {
	private ScmCoc scmCoc = null;// 厂商

//	private Customs customs = null;// 海关

	private String emsNo = null;// 账册号（联网监管）；手册号（纸质手册和电子手册）

	private Date endAvailability = null; // 有效截止日期

	private Date beginAvailability = null; // 生效日期

	private Date endCaseDate = null; // 结案日期

	private String purchaseAndSaleContractNo = null; // 购销合同编号

	private Integer seqNum = null;// 帐册序号

	private String complex = null;// 海关商品编码

	private String ptName = null; // 商品名称

	private String ptSpec = null; // 商品规格

	private Double ownerQuantity = null; // 本厂数量(申请数量)

	private String oppositeEmsNo = null; // 对方帐册号

	private String oppositeEmsSerialNo = null; // 对方帐册序号

	private Double oppositeQuantity = null; // 对方数量

	private String oppositeName = null; // 对方商品名称

	private String oppositeComplexCode = null; // 对方商品代码

	private String oppositeSpec = null; // 对方商品型号

	private Unit oppositeUnit = null; // 对方商品单位

	private Double unitPrice = null; // 单价

	private Date transferFactoryDate;// 转厂日期

	private Double realTransferFactoryAmount;// 实际转厂数量

	public Date getBeginAvailability() {
		return beginAvailability;
	}

	public void setBeginAvailability(Date beginAvailability) {
		this.beginAvailability = beginAvailability;
	}

//	public Customs getCustoms() {
//		return customs;
//	}
//
//	public void setCustoms(Customs brief) {
//		this.customs = brief;
//	}

	public String getComplex() {
		return complex;
	}

	public void setComplex(String complex) {
		this.complex = complex;
	}

	public String getEmsNo() {
		return emsNo;
	}

	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	public Date getEndAvailability() {
		return endAvailability;
	}

	public void setEndAvailability(Date endAvailability) {
		this.endAvailability = endAvailability;
	}

	public Date getEndCaseDate() {
		return endCaseDate;
	}

	public void setEndCaseDate(Date endCaseDate) {
		this.endCaseDate = endCaseDate;
	}

	public String getOppositeComplexCode() {
		return oppositeComplexCode;
	}

	public void setOppositeComplexCode(String oppositeComplexCode) {
		this.oppositeComplexCode = oppositeComplexCode;
	}

	public String getOppositeEmsNo() {
		return oppositeEmsNo;
	}

	public void setOppositeEmsNo(String oppositeEmsNo) {
		this.oppositeEmsNo = oppositeEmsNo;
	}

	public String getOppositeEmsSerialNo() {
		return oppositeEmsSerialNo;
	}

	public void setOppositeEmsSerialNo(String oppositeEmsSerialNo) {
		this.oppositeEmsSerialNo = oppositeEmsSerialNo;
	}

	public String getOppositeName() {
		return oppositeName;
	}

	public void setOppositeName(String oppositeName) {
		this.oppositeName = oppositeName;
	}

	public Double getOppositeQuantity() {
		return oppositeQuantity;
	}

	public void setOppositeQuantity(Double oppositeQuantity) {
		this.oppositeQuantity = oppositeQuantity;
	}

	public String getOppositeSpec() {
		return oppositeSpec;
	}

	public void setOppositeSpec(String oppositeSpec) {
		this.oppositeSpec = oppositeSpec;
	}

	public Unit getOppositeUnit() {
		return oppositeUnit;
	}

	public void setOppositeUnit(Unit oppositeUnit) {
		this.oppositeUnit = oppositeUnit;
	}

	public Double getOwnerQuantity() {
		return ownerQuantity;
	}

	public void setOwnerQuantity(Double ownerQuantity) {
		this.ownerQuantity = ownerQuantity;
	}

	public String getPtName() {
		return ptName;
	}

	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	public String getPtSpec() {
		return ptSpec;
	}

	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	public String getPurchaseAndSaleContractNo() {
		return purchaseAndSaleContractNo;
	}

	public void setPurchaseAndSaleContractNo(String purchaseAndSaleContractNo) {
		this.purchaseAndSaleContractNo = purchaseAndSaleContractNo;
	}

	public Double getRealTransferFactoryAmount() {
		return realTransferFactoryAmount;
	}

	public void setRealTransferFactoryAmount(Double realTransferFactoryAmount) {
		this.realTransferFactoryAmount = realTransferFactoryAmount;
	}

	public ScmCoc getScmCoc() {
		return scmCoc;
	}

	public void setScmCoc(ScmCoc scmCoc) {
		this.scmCoc = scmCoc;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Date getTransferFactoryDate() {
		return transferFactoryDate;
	}

	public void setTransferFactoryDate(Date transferFactoryDate) {
		this.transferFactoryDate = transferFactoryDate;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
