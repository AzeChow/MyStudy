package com.bestway.bls.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;

/**
 * 车次基本信息（Delivery）
 * 
 * @author kangbo
 */
public class Delivery extends BaseScmEntity {
	/**
	 * 企业编码 按照海关编制的十位编码（调用Brief表）
	 * 
	 * @uml.property name="tradeCo"
	 * @uml.associationEnd
	 */
	private Brief tradeCo;
	/**
	 * 业务类型 本系统统一为MnlEpz (报文需要，界面不需要)
	 * 
	 * @uml.property name="operType"
	 */
	private String operType = "MnlEpz";
	/**
	 * 车次号,一次送货车次号，企业自编并保持唯一性（文本） 企业编码＋企业自编号
	 * 
	 * @uml.property name="deliveryNo"
	 */
	private String deliveryNo;
	/**
	 * 车牌号 如粤A12345，没有可不填（文本）
	 * 
	 * @uml.property name="vehicleLicense"
	 */
	private String vehicleLicense;
	/**
	 * 承运单位名称(String)
	 * 
	 * @uml.property name="carrierCode"
	 */
	private String carrierCode;
	/**
	 * 承运单位名称(String)
	 * 
	 * @uml.property name="carrierCode"
	 */
	private String carrierName;

	/**
	 * 仓单数(Integer) 由仓单基本信息数汇总
	 * 
	 * @uml.property name="billCount"
	 */
	private Integer billCount;
	/**
	 * 预计到达日期(Date) 供输入，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @uml.property name="schedularArrivalDate"
	 */
	private Date schedularArrivalDate;
	/**
	 * 该车载货物件数(Integer) 由仓单基本信息件数汇总
	 * 
	 * @uml.property name="packNo"
	 */
	private Integer packNo;
	/**
	 * 毛重(Double) 没有可不填
	 * 
	 * @uml.property name="grossWeight"
	 */
	private Double grossWeight;
	/**
	 * 净重(Double) 没有可不填
	 * 
	 * @uml.property name="netWeight"
	 */
	private Double netWeight;
	/**
	 * 第1个集装箱号(String) 最多只有两个
	 * 
	 * @uml.property name="containerNo1"
	 */
	private String containerNo1;
	/**
	 * 第2个集装箱号(String) 最多只有两个
	 * 
	 * @uml.property name="containerNo2"
	 */
	private String containerNo2;
	/**
	 * 第1关锁号(String) 最多只有两个
	 * 
	 * @uml.property name="sealNo1"
	 */
	private String sealNo1;
	/**
	 * 第2关锁号(String) 最多只有两个
	 * 
	 * @uml.property name="sealNo2"
	 */
	private String sealNo2;
	/**
	 * ModifyMark 修改标志(Integer) 预留，默认为0 (报文需要，界面不需要)
	 * 
	 * @uml.property name="modifyMark"
	 */
	private Integer modifyMark = 0;
	/**
	 * 报文发送时间(Date) 申报时添加此时间, 格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @uml.property name="messTimeStamp"
	 */
	private Date messTimeStamp;
	/**
	 * 申报状态(与电子化手册一致) 初始状态、等待审批、正在执行、退单
	 * 
	 * @uml.property name="declareState"
	 */
	private String declareState="1";
	/**
	 * 是否生效
	 */
	private Boolean effective = false;
	/**
	 * 进出口标志 I--进 ； O--出品
	 */
	private String inOut = "I";

	public Boolean getEffective() {
		return effective;
	}

	public void setEffective(Boolean effective) {
		this.effective = effective;
	}

	/**
	 * @return
	 * @uml.property name="tradeCo"
	 */
	public Brief getTradeCo() {
		return tradeCo;
	}

	/**
	 * @param tradeCo
	 * @uml.property name="tradeCo"
	 */
	public void setTradeCo(Brief tradeCo) {
		this.tradeCo = tradeCo;
	}

	/**
	 * @return
	 * @uml.property name="operType"
	 */
	public String getOperType() {
		return operType;
	}

	/**
	 * @param operType
	 * @uml.property name="operType"
	 */
	public void setOperType(String operType) {
		this.operType = operType;
	}

	/**
	 * @return
	 * @uml.property name="deliveryNo"
	 */
	public String getDeliveryNo() {
		return deliveryNo;
	}

	/**
	 * @param deliveryNo
	 * @uml.property name="deliveryNo"
	 */
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	/**
	 * @return
	 * @uml.property name="vehicleLicense"
	 */
	public String getVehicleLicense() {
		return vehicleLicense;
	}

	/**
	 * @param vehicleLicense
	 * @uml.property name="vehicleLicense"
	 */
	public void setVehicleLicense(String vehicleLicense) {
		this.vehicleLicense = vehicleLicense;
	}

	/**
	 * @return
	 * @uml.property name="carrierCode"
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 * @uml.property name="carrierCode"
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return
	 * @uml.property name="billCount"
	 */
	public Integer getBillCount() {
		return billCount;
	}

	/**
	 * @param billCount
	 * @uml.property name="billCount"
	 */
	public void setBillCount(Integer billCount) {
		this.billCount = billCount;
	}

	/**
	 * @return
	 * @uml.property name="schedularArrivalDate"
	 */
	public Date getSchedularArrivalDate() {
		return schedularArrivalDate;
	}

	/**
	 * @param schedularArrivalDate
	 * @uml.property name="schedularArrivalDate"
	 */
	public void setSchedularArrivalDate(Date schedularArrivalDate) {
		this.schedularArrivalDate = schedularArrivalDate;
	}

	/**
	 * @return
	 * @uml.property name="packNo"
	 */
	public Integer getPackNo() {
		return packNo;
	}

	/**
	 * @param packNo
	 * @uml.property name="packNo"
	 */
	public void setPackNo(Integer packNo) {
		this.packNo = packNo;
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
	 * @uml.property name="containerNo1"
	 */
	public String getContainerNo1() {
		return containerNo1;
	}

	/**
	 * @param containerNo1
	 * @uml.property name="containerNo1"
	 */
	public void setContainerNo1(String containerNo1) {
		this.containerNo1 = containerNo1;
	}

	/**
	 * @return
	 * @uml.property name="containerNo2"
	 */
	public String getContainerNo2() {
		return containerNo2;
	}

	/**
	 * @param containerNo2
	 * @uml.property name="containerNo2"
	 */
	public void setContainerNo2(String containerNo2) {
		this.containerNo2 = containerNo2;
	}

	/**
	 * @return
	 * @uml.property name="sealNo1"
	 */
	public String getSealNo1() {
		return sealNo1;
	}

	/**
	 * @param sealNo1
	 * @uml.property name="sealNo1"
	 */
	public void setSealNo1(String sealNo1) {
		this.sealNo1 = sealNo1;
	}

	/**
	 * @return
	 * @uml.property name="sealNo2"
	 */
	public String getSealNo2() {
		return sealNo2;
	}

	/**
	 * @param sealNo2
	 * @uml.property name="sealNo2"
	 */
	public void setSealNo2(String sealNo2) {
		this.sealNo2 = sealNo2;
	}

	/**
	 * @return
	 * @uml.property name="modifyMark"
	 */
	public Integer getModifyMark() {
		return modifyMark;
	}

	/**
	 * @param modifyMark
	 * @uml.property name="modifyMark"
	 */
	public void setModifyMark(Integer modifyMark) {
		this.modifyMark = modifyMark;
	}

	/**
	 * @return
	 * @uml.property name="messTimeStamp"
	 */
	public Date getMessTimeStamp() {
		return messTimeStamp;
	}

	/**
	 * @param messTimeStamp
	 * @uml.property name="messTimeStamp"
	 */
	public void setMessTimeStamp(Date messTimeStamp) {
		this.messTimeStamp = messTimeStamp;
	}

	/**
	 * @return
	 * @uml.property name="declareState"
	 */
	public String getDeclareState() {
		return declareState;
	}

	/**
	 * @param declareState
	 * @uml.property name="declareState"
	 */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getInOut() {
		return inOut;
	}

	public void setInOut(String inOut) {
		this.inOut = inOut;
	}
}
