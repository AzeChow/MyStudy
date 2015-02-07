package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;

/**
 * 仓单基本信息（StorageBill）--相当于报关单头
 * 
 * @author kangbo
 */
public class StorageBill extends BaseScmEntity {
	/**
	 * 车次基本信息
	 * 
	 * @uml.property name="delivery"
	 * @uml.associationEnd
	 */
	private Delivery delivery;
	/**
	 * 仓单号（String） 企业自编 （仓单编号不能重复） 企业编码＋企业自编号
	 * 
	 * @uml.property name="billNo"
	 */
	private String billNo;
	/**
	 * 仓库编码(WareSet) 海关对该仓库的编码（来自仓库基本资料）
	 * 
	 * @uml.property name="wareHouseCode"
	 * @uml.associationEnd
	 */
	private WareSet wareHouseCode;
	/**
	 * 帐册编号(String) 电子账册编号或底账编号
	 * 
	 * @uml.property name="emsNo"
	 */
	private String emsNo;
	/**
	 * 海关代码(Customs) 申报主管海关编码
	 * 
	 * @uml.property name="customsCode"
	 * @uml.associationEnd
	 */
	private Customs customsCode;
	/**
	 * 仓单有效期(Date)
	 * 
	 * @uml.property name="validDate"
	 */
	private Date validDate;
	/**
	 * 仓单类型(String) （00-申报初始库存，01-后报关方式 02-先报关分批送货方式03-特殊审核）
	 * 
	 * @uml.property name="billType"
	 */
	private String billType;
	/**
	 * IOFlag 进出仓标志(Integer) 0: I 入仓,1: O出仓
	 * 
	 * @uml.property name="iOFlag"
	 */
	private String ioFlag;

	/**
	 * OrderNo 订单号(String) 没有可不填
	 * 
	 * @uml.property name="orderNo"
	 */
	private String orderNo;
	/**
	 * PlanNo 计划编号(String)
	 * 
	 * @uml.property name="planNo"
	 */
	private String planNo;
	/**
	 * PackCount 该仓单对应的件数(Integer) 仓单对应件数。总和要等于车次表里的件数
	 * 
	 * @uml.property name="packCount"
	 */
	private Integer packCount;
	/**
	 * WrapType 包装种类，按海关代码表(wrap) 来自关务基础资料包装种类表
	 * 
	 * @uml.property name="wrap"
	 * @uml.associationEnd
	 */
	private Wrap wrap;
	/**
	 * SupplierCd 供货商编码(Scmcoc) 来自客户/供应商表
	 * 
	 * @uml.property name="supplierCd"
	 * @uml.associationEnd
	 */
	private ScmCoc supplierCd;
	/**
	 * 供货方企业编码 仅国内入区的单需要填报（客户/供应商对应海关编码）
	 */
	private ScmCoc corrOwnerCode;

	/**
	 * GrossWeight 毛重(Double) 暂可不填
	 * 
	 * @uml.property name="grossWeight"
	 */
	private Double grossWeight;
	/**
	 * NetWeight 净重(Double) 暂可不填
	 * 
	 * @uml.property name="netWeight"
	 */
	private Double netWeight;
	/**
	 * ManualNo 手册号(String) 电子帐册或手册号
	 * 
	 * @uml.property name="manualNo"
	 */
	private String manualNo;
	/**
	 * ItemsCount 仓单表体数量(Integer) <=20 （汇总仓单表体树木）
	 * 
	 * @uml.property name="itemsCount"
	 */
	private Integer itemsCount;
	/**
	 * IEPort 进出口岸(Custom) 4位海关代码
	 * 
	 * @uml.property name="iEPort"
	 * @uml.associationEnd
	 */
	private Customs iePort;
	/**
	 * OutName 转出方名称(String) 当仓单类型为出仓单时填写
	 * 
	 * @uml.property name="outName"
	 */
	private String outName;
	/**
	 * InName 转入方名称(String) 当仓单类型为入仓单时填写
	 * 
	 * @uml.property name="inName"
	 */
	private String inName;
	/**
	 * 是否生效
	 */
	private Boolean effective = false;

	public Boolean getEffective() {
		return effective;
	}

	public void setEffective(Boolean effective) {
		this.effective = effective;
	}

	/**
	 * @return
	 * @uml.property name="delivery"
	 */
	public Delivery getDelivery() {
		return delivery;
	}

	/**
	 * @param delivery
	 * @uml.property name="delivery"
	 */
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	/**
	 * @return
	 * @uml.property name="billNo"
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * @param billNo
	 * @uml.property name="billNo"
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * @return
	 * @uml.property name="wareHouseCode"
	 */
	public WareSet getWareHouseCode() {
		return wareHouseCode;
	}

	/**
	 * @param wareHouseCode
	 * @uml.property name="wareHouseCode"
	 */
	public void setWareHouseCode(WareSet wareHouseCode) {
		this.wareHouseCode = wareHouseCode;
	}

	/**
	 * @return
	 * @uml.property name="emsNo"
	 */
	public String getEmsNo() {
		return emsNo;
	}

	/**
	 * @param emsNo
	 * @uml.property name="emsNo"
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}

	/**
	 * @return
	 * @uml.property name="customsCode"
	 */
	public Customs getCustomsCode() {
		return customsCode;
	}

	/**
	 * @param customsCode
	 * @uml.property name="customsCode"
	 */
	public void setCustomsCode(Customs customsCode) {
		this.customsCode = customsCode;
	}

	/**
	 * @return
	 * @uml.property name="validDate"
	 */
	public Date getValidDate() {
		return validDate;
	}

	/**
	 * @param validDate
	 * @uml.property name="validDate"
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * @return
	 * @uml.property name="billType"
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 * @uml.property name="billType"
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return
	 * @uml.property name="orderNo"
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 * @uml.property name="orderNo"
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return
	 * @uml.property name="planNo"
	 */
	public String getPlanNo() {
		return planNo;
	}

	/**
	 * @param planNo
	 * @uml.property name="planNo"
	 */
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	/**
	 * @return
	 * @uml.property name="packCount"
	 */
	public Integer getPackCount() {
		return packCount;
	}

	/**
	 * @param packCount
	 * @uml.property name="packCount"
	 */
	public void setPackCount(Integer packCount) {
		this.packCount = packCount;
	}

	/**
	 * @return
	 * @uml.property name="wrap"
	 */
	public Wrap getWrap() {
		return wrap;
	}

	/**
	 * @param wrap
	 * @uml.property name="wrap"
	 */
	public void setWrap(Wrap wrap) {
		this.wrap = wrap;
	}

	/**
	 * @return
	 * @uml.property name="supplierCd"
	 */
	public ScmCoc getSupplierCd() {
		return supplierCd;
	}

	/**
	 * @param supplierCd
	 * @uml.property name="supplierCd"
	 */
	public void setSupplierCd(ScmCoc supplierCd) {
		this.supplierCd = supplierCd;
	}

	/**
	 * @return
	 * @uml.property name="grossWeight"
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * @param grossWeight
	 * @uml.property name="grossWeight"
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * @return
	 * @uml.property name="netWeight"
	 */
	public Double getNetWeight() {
		return netWeight;
	}

	/**
	 * @param netWeight
	 * @uml.property name="netWeight"
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	/**
	 * @return
	 * @uml.property name="manualNo"
	 */
	public String getManualNo() {
		return manualNo;
	}

	/**
	 * @param manualNo
	 * @uml.property name="manualNo"
	 */
	public void setManualNo(String manualNo) {
		this.manualNo = manualNo;
	}

	/**
	 * @return
	 * @uml.property name="itemsCount"
	 */
	public Integer getItemsCount() {
		return itemsCount;
	}

	/**
	 * @param itemsCount
	 * @uml.property name="itemsCount"
	 */
	public void setItemsCount(Integer itemsCount) {
		this.itemsCount = itemsCount;
	}

	/**
	 * @return
	 * @uml.property name="outName"
	 */
	public String getOutName() {
		return outName;
	}

	/**
	 * @param outName
	 * @uml.property name="outName"
	 */
	public void setOutName(String outName) {
		this.outName = outName;
	}

	/**
	 * @return
	 * @uml.property name="inName"
	 */
	public String getInName() {
		return inName;
	}

	/**
	 * @param inName
	 * @uml.property name="inName"
	 */
	public void setInName(String inName) {
		this.inName = inName;
	}

	public Customs getIePort() {
		return iePort;
	}

	public void setIePort(Customs iePort) {
		this.iePort = iePort;
	}

	public ScmCoc getCorrOwnerCode() {
		return corrOwnerCode;
	}

	public void setCorrOwnerCode(ScmCoc corrOwnerCode) {
		this.corrOwnerCode = corrOwnerCode;
	}

	public String getIoFlag() {
		return ioFlag;
	}

	public void setIoFlag(String ioFlag) {
		this.ioFlag = ioFlag;
	}


}
