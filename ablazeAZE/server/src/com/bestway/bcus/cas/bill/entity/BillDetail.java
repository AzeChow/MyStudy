/*
 * Created on 2004-9-14
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.cas.bill.entity;

import java.math.BigDecimal;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 工厂单据明细
 */
public class BillDetail extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 工厂单据表表头
	 */
	private BillMaster billMaster;

	/**
	 * 工厂料号
	 */
	private String ptPart;

	/**
	 * 商品名称
	 */
	private String ptName;

	/**
	 * 规格型号
	 */
	private String ptSpec;

	/**
	 * 单位
	 */
	private CalUnit ptUnit;

	/**
	 * 数量
	 */
	private Double ptAmount;

	/**
	 * 单价
	 */
	private Double ptPrice;
	/**
	 * 总金额
	 */
	private Double totalPrice = null;
	/**
	 * 制单号
	 */

	private String productNo;

	/**
	 * 海关商品编码
	 */

	private Complex complex;

	/**
	 * 海关商品名称
	 */
	private String hsName;

	/**
	 * 海关商品规格型号
	 */
	private String hsSpec;

	/**
	 * 海关单价
	 */
	private Double hsPrice;

	/**
	 * 金额
	 */
	private Double money;

	/**
	 * 序号
	 */
	private Integer seqNum;

	/**
	 * 折算报关数量
	 */
	private Double hsAmount;

	/**
	 * 海关单位
	 */
	private Unit hsUnit;

	/**
	 * 版本
	 */
	private Integer version = 0;

	 /**
	 * 单净重
	 * lyh 2013-01-4因为工厂单据表需要现在这个栏位
	 */
	
	 private Double inNetWeight = null;

	/**
	 * 净重
	 */
	private Double netWeight;

	/**
	 * 重量单位
	 */
	private Unit weightUnit;

	/**
	 * 报关单的格式 == 报关单号(手册号),报关单号(手册号)...
	 */

	/**
	 * 对应报关单号
	 */
	private String customNo;

	/**
	 * 对应报关数量
	 */
	private Double customNum;


	/**
	 * 备注
	 */

	private String note;

	/**
	 * 是否转结转单据
	 */
	private Boolean isTransferFactoryBill = false;

	/**
	 * 是否转进出口申请单据
	 */
	private Boolean isImpExpRequestBill = false;

	/**
	 * 仓库
	 */

	private WareSet wareSet;

	/**
	 * 折算报关单位比率
	 */

	private Double unitConvert = null;

	/**
	 * 单据号
	 */
	private String billNo = null;

	// 以下字段用于临时存放数据
	/**
	 * 临时存放数据
	 */
	private String tempDetailNo = null;

	/**
	 * 临时存放数据1
	 */
	private String tempDetailNo1 = null;

	/**
	 * 临时存放数据2
	 */
	private String tempDetailNo2 = null;

	/**
	 * 临时存放数据3
	 */
	private String tempDetailNo3 = null;

	// 以下字段为新海关帐业务需求加的字段 add by sls 2007/07/10
	/**
	 * 手册号
	 */
	private String emsNo = null;

	/**
	 * 版本号
	 */
	private Integer hsVersion = 0;

	/**
	 * 发票编号
	 */
	private String invoiceNo = null;

	// 以下两个字段为新海关帐设备用到
	/**
	 * 存放地点
	 */

	private String location = null;

	/**
	 * 保管人
	 */
	private String holdMan = null;

	/**
	 * 订单号
	 */
	private String oderBillNo = null;

	/**
	 * 送货单号
	 */
	private String takeBillNo = null;

	/**
	 * 订单号
	 */
	private String orderNo=null;
	
	/**
	 * 获取订单号
	 * @return
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * 设置订单号
	 * @param orderNo
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * 判断是否转结转单据
	 * 
	 * @return isTransferCustomsEnvelopRequest 是否转结转单据.
	 */
	public Boolean getIsTransferFactoryBill() {
		return isTransferFactoryBill;
	}

	/**
	 * 设置是否转结转单据标志
	 * 
	 * @param isTransferCustomsEnvelopRequest
	 *            是否转结转单据.
	 */
	public void setIsTransferFactoryBill(Boolean isTransferCustomsEnvelopRequest) {
		this.isTransferFactoryBill = isTransferCustomsEnvelopRequest;
	}

	/**
	 * 取得工厂单据表头
	 * 
	 * @return billMaster 工厂单据表头.
	 */
	public BillMaster getBillMaster() {
		return billMaster;
	}

	/**
	 * 设置工厂单据表头
	 * 
	 * @param billMaster
	 *            工厂单据表头
	 */
	public void setBillMaster(BillMaster billMaster) {
		this.billMaster = billMaster;
	}

	/**
	 * 取得商品编码
	 * 
	 * @return complex 商品编码.
	 */
	public Complex getComplex() {
		return complex;
	}

	/**
	 * 设置商品编码
	 * 
	 * @param complex
	 *            商品编码.
	 */
	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	/**
	 * 取得对应的报关单号
	 * 
	 * @return customNo 对应的报关单号.
	 */
	public String getCustomNo() {
		return customNo;
	}

	/**
	 * 设置对应的报关单号
	 * 
	 * @param customNo
	 *            对应的报关单号.
	 */
	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}

	/**
	 * 取得对应的报关数量
	 * 
	 * @return customNum 对应的报关数量.
	 */
	public Double getCustomNum() {
		return customNum;
	}

	/**
	 * 设置对应的报关数量
	 * 
	 * @param customNum
	 *            对应的报关数量 .
	 */
	public void setCustomNum(Double customNum) {
		this.customNum = customNum;
	}

	/**
	 * 取得折算报关数量
	 * 
	 * @return hsAmount 折算海关数量.
	 */
	public Double getHsAmount() {
		return hsAmount;
	}

	/**
	 * 设置折算报关数量
	 * 
	 * @param hsAmount
	 *            折算海关数量.
	 */
	public void setHsAmount(Double hsAmount) {
		this.hsAmount = hsAmount;
	}

	/**
	 * 取得海关商品名称
	 * 
	 * @return hsName 海关商品名称.
	 */
	public String getHsName() {
		return hsName;
	}

	/**
	 * 设置海关商品名称
	 * 
	 * @param hsName
	 *            海关商品名称
	 */
	public void setHsName(String hsName) {
		this.hsName = hsName;
	}

	/**
	 * 取得海关单价
	 * 
	 * @return hsPrice 海关单价.
	 */
	public Double getHsPrice() {
		return hsPrice;
	}

	/**
	 * 设置海关单价
	 * 
	 * @param hsPrice
	 *            海关单价
	 */
	public void setHsPrice(Double hsPrice) {
		this.hsPrice = hsPrice;
	}

	/**
	 * 取得海关单位
	 * 
	 * @return hsUnit 海关单位.
	 */
	public Unit getHsUnit() {
		return hsUnit;
	}

	/**
	 * 设置海关单位
	 * 
	 * @param hsUnit
	 *            海关单位
	 */
	public void setHsUnit(Unit hsUnit) {
		this.hsUnit = hsUnit;
	}

	/**
	 * 取得报关的规格型号
	 * 
	 * @return htSpec 报关规格型号.
	 */
	public String getHsSpec() {
		return hsSpec;
	}

	/**
	 * 设置报关规格型号
	 * 
	 * @param hsSpec
	 *            报关规格型号.
	 */
	public void setHsSpec(String hsSpec) {
		this.hsSpec = hsSpec;
	}

	/**
	 * 取得金额
	 * 
	 * @return money 金额.
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置金额
	 * 
	 * @param money
	 *            金额.
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * 取得净重
	 * 
	 * @return netWeight 净重.
	 */
	public Double getNetWeight() {
		return netWeight;
	}

	/**
	 * 设置净重
	 * 
	 * @param netWeight
	 *            净重.
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}
	/**
	 * 取得单净重
	 * lyh	2013-01-4因为工厂单据表需要现在这个栏位
	 * @return inNetWeight 单净重.
	 */
	public Double getInNetWeight() {
		return inNetWeight;
	}
	/**
	 * 设置单净重
	 * 
	 * @param inNetWeight
	 *           单净重.
	 */
	public void setInNetWeight(Double inNetWeight) {
		this.inNetWeight = inNetWeight;
	}

	/**
	 * 取得制单号
	 * 
	 * @return productNo 制单号.
	 */
	public String getProductNo() {
		return productNo;
	}

	/**
	 * 设置制单号
	 * 
	 * @param productNo
	 *            制单号.
	 */
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	/**
	 * 取得工厂数量
	 * 
	 * @return ptAmount 数量.
	 */
	public Double getPtAmount() {
		return ptAmount;
	}

	/**
	 * 设置工厂数量
	 * 
	 * @param ptAmount
	 *            数量.
	 */
	public void setPtAmount(Double ptAmount) {
		this.ptAmount = ptAmount;
	}

	/**
	 * 取得工厂商品名称
	 * 
	 * @return ptName 工厂商品名称.
	 */
	public String getPtName() {
		if(ptName==null){
			return "";
		}
		return ptName;
	}

	/**
	 * 设置工厂商品名称
	 * 
	 * @param ptName
	 *            工厂商品名称.
	 */
	public void setPtName(String ptName) {
		this.ptName = ptName;
	}

	/**
	 * 取得工厂料号
	 * 
	 * @return ptPart 工厂料号.
	 */
	public String getPtPart() {
		if(ptPart==null){
			return "";
		}
		return ptPart;
	}

	/**
	 * 设置工厂料号
	 * 
	 * @param ptPart
	 *            工厂料号.
	 */
	public void setPtPart(String ptPart) {
		this.ptPart = ptPart;
	}

	/**
	 * 取得工厂单价
	 * 
	 * @return ptPrice 工厂单价.
	 */
	public Double getPtPrice() {
		return ptPrice;
	}

	/**
	 * 设置工厂单价
	 * 
	 * @param ptPrice
	 *            工厂单价.
	 */
	public void setPtPrice(Double ptPrice) {
		this.ptPrice = ptPrice;
	}

	/**
	 * 获取 总金额=单价*数量
	 * 
	 * @return the totalPrice
	 */
	public Double getTotalPrice() {
		if (this.totalPrice != null) {
			return this.totalPrice;
		}
		double ptAmount = 0;
		double ptPrice = 0;
		if (this.ptAmount != null) {
			ptAmount = this.ptAmount.doubleValue();
		}
		if (this.getPtPrice() != null) {
			ptPrice = this.getPtPrice().doubleValue();
		}
		return new Double(ptAmount * ptPrice);
	}

	/**
	 * 设置总金额
	 * 
	 * @param totalPrice
	 *            总金额
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * 取得工厂规格型号
	 * 
	 * @return ptSpec 工厂规格型号.
	 */
	public String getPtSpec() {
		if(ptSpec==null){
			return "";
		}
		return ptSpec;
	}

	/**
	 * 设置工厂规格型号
	 * 
	 * @param ptSpec
	 *            工厂规格型号.
	 */
	public void setPtSpec(String ptSpec) {
		this.ptSpec = ptSpec;
	}

	/**
	 * 取得工厂单位
	 * 
	 * @return ptUnit 工厂单位.
	 */
	public CalUnit getPtUnit() {
		return ptUnit;
	}

	/**
	 * 设置工厂单位
	 * 
	 * @param ptUnit
	 *            工厂单位.
	 */
	public void setPtUnit(CalUnit ptUnit) {
		this.ptUnit = ptUnit;
	}

	/**
	 * 取得序号
	 * 
	 * @return seqNum 序号.
	 */
	public Integer getSeqNum() {
		return seqNum;
	}

	/**
	 * 设置序号
	 * 
	 * @param seqNum
	 *            序号.
	 */
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	/**
	 * 取得备注内容
	 * 
	 * @return note 备注.
	 */
	public String getNote() {
		return note;
	}

	/**
	 * 设置备注内容
	 * 
	 * @param note
	 *            备注.
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 取得重量单位
	 * 
	 * @return weightUnit 重量单位.
	 */
	public Unit getWeightUnit() {
		return weightUnit;
	}

	/**
	 * 设置重量单位
	 * 
	 * @param weightUnit
	 *            重量单位.
	 */
	public void setWeightUnit(Unit weightUnit) {
		this.weightUnit = weightUnit;
	}

	/**
	 * 取得版本
	 * 
	 * @return version 版本.
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * 设置版本
	 * 
	 * @param version
	 *            版本
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 判断是否转进出口申请单据
	 * 
	 * @return isImpExpRequestBill 是否转进出口申请单据.
	 */
	public Boolean getIsImpExpRequestBill() {
		return isImpExpRequestBill;
	}

	/**
	 * 设置是否转进出口申请单据标志
	 * 
	 * @param isImpExpRequestBill
	 *            进出口申请单据标志
	 */
	public void setIsImpExpRequestBill(Boolean isImpExpRequestBill) {
		this.isImpExpRequestBill = isImpExpRequestBill;
	}

	/**
	 * 取得未和报关单对应的数量
	 * 
	 * @return noCustomNum =hsAmount - customNum 未对应报关单数量 = 工厂数量 - 对应报关单数量.
	 */
	public Double getNoCustomNum() {
		if (this.hsAmount != null && this.customNum != null) {
			return this.hsAmount - this.customNum;
		} else if(this.hsAmount != null && this.customNum == null){
			return this.hsAmount;
		} else if(this.hsAmount == null && this.customNum != null){
			return - customNum;
		} 
		return 0.0;
	}

	public Double getNoCustomNum2() {
		double customNum = 0.0;
		if (this.customNum != null) {
			customNum = this.customNum;
		}
		double hsAmount = 0.0;
		if (this.hsAmount != null) {
			hsAmount = this.hsAmount;
		}
		return hsAmount - customNum;
	}

	/**
	 * 取得仓库内容
	 * 
	 * @return wareSet 仓库.
	 */
	public WareSet getWareSet() {
		return wareSet;
	}

	/**
	 * 设置仓库
	 * 
	 * @param wareSet
	 *            仓库
	 */
	public void setWareSet(WareSet wareSet) {
		this.wareSet = wareSet;
	}

	/**
	 * 取得折算报关单位比率
	 * 
	 * @return unitConvert 折算报关单位比率.
	 */
	public Double getUnitConvert() {
		return unitConvert;
	}

	/**
	 * 取得单据号
	 * 
	 * @return billNo 单据号.
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * 设置单据号
	 * 
	 * @param billNo
	 *            单据号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public void setUnitConvert(Double unitConvert) {
		this.unitConvert = unitConvert;
	}

	public String getTempDetailNo() {
		return tempDetailNo;
	}

	public void setTempDetailNo(String tempDetailNo) {
		this.tempDetailNo = tempDetailNo;
	}

	public String getTempDetailNo1() {
		return tempDetailNo1;
	}

	public void setTempDetailNo1(String tempDetailNo1) {
		this.tempDetailNo1 = tempDetailNo1;
	}

	public String getTempDetailNo2() {
		return tempDetailNo2;
	}

	public void setTempDetailNo2(String tempDetailNo2) {
		this.tempDetailNo2 = tempDetailNo2;
	}

	public String getTempDetailNo3() {
		return tempDetailNo3;
	}

	public void setTempDetailNo3(String tempDetailNo3) {
		this.tempDetailNo3 = tempDetailNo3;
	}

	/**
	 * 得到手册号
	 * 
	 * @return
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * 设置手册号
	 * 
	 * @param emsNo
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * 得到报关商品版本号
	 * 
	 * @return
	 */
	public Integer getHsVersion() {
		return hsVersion;
	}

	/**
	 * 设置报关商品版本号
	 * 
	 * @param hsVersion
	 */
	public void setHsVersion(Integer hsVersion) {
		this.hsVersion = hsVersion;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getPtNameptSpec() {
		return (this.getPtName() == null ? "" : this.getPtName()) + "/"
				+ (this.getPtSpec() == null ? "" : this.getPtSpec());
	}

	public Double getUnitWeight() {
		if (this.getPtAmount() == null
				|| this.getPtAmount().equals(Double.valueOf(0))) {
			return Double.valueOf(0);
		}
		return (this.netWeight == null ? Double.valueOf(0) : this.netWeight)
				/ this.getPtAmount();
	}

	public String getHoldMan() {
		return holdMan;
	}

	public void setHoldMan(String holdMan) {
		this.holdMan = holdMan;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOderBillNo() {
		return oderBillNo;
	}

	public void setOderBillNo(String oderBillNo) {
		this.oderBillNo = oderBillNo;
	}

	public String getTakeBillNo() {
		return takeBillNo;
	}

	public void setTakeBillNo(String takeBillNo) {
		this.takeBillNo = takeBillNo;
	}

	// public Double getInNetWeight() {
	// return inNetWeight;
	// }
	//
	// public void setInNetWeight(Double inNetWeight) {
	// this.inNetWeight = inNetWeight;
	// }

}