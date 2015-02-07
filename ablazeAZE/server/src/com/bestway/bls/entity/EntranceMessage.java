package com.bestway.bls.entity;

import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.common.BaseEntity;
import com.bestway.common.BaseScmEntity;
/**
 * 货到报告（卡口方放行）实体类
 * @author hw
 *
 */
public class EntranceMessage extends BaseScmEntity{

	
	/**
	 * 企业编码
	 */
	private Brief tradeCo=null;
	/**
	 * 业务类型
	 */
	private String operType="MnlEpz";
	/**
	 * 车次
	 */
	private String deliveryNo=null;
	/**
	 * 进出口标志
	 */
	private String ioFlag=null;
	
	/**
	 * 车牌号
	 */
	private String vehicleLicense=null;
	/**
	 * 承运商代码
	 */
	private String carrierCode=null;
	/**
	 * 承运商名称
	 */
	private String carrierName=null;
	/**
	 * 仓单数
	 */
	private Integer billCount=null;
	/**
	 * 净重
	 */
	private Double grossWeight=null;
	/**
	 * 毛重
	 */
	private Double netWeight=null;
	/**
	 * 第1个集装箱号
	 */
	private String containerNo1=null;
	/**
	 * 第2个集装箱号
	 */
	private String containerNo2=null;
	/**
	 * 第1关锁号
	 */
	private String sealNo1=null;
	/**
	 * 第2关锁号
	 */
	private String sealNo2=null;
	/**
	 * 申报状态
	 */
	private String declareState=null;
	/**
	 * 得到企业编码
	 */
	public Brief getTradeCo() {
		return tradeCo;
	}
	/**
	 * 设置企业编码
	 */
	public void setTradeCo(Brief tradeCo) {
		this.tradeCo = tradeCo;
	}
	/**
	 * 得到业务类型
	 */
	public String getOperType() {
		return operType;
	}
	/**
	 * 设置业务类型
	 */
	public void setOperType(String operType) {
		this.operType = operType;
	}
	/**
	 * 得到车次
	 */
	public String getDeliveryNo() {
		return deliveryNo;
	}
	/**
	 * 设置车次
	 */
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	/**
	 * 得到车牌号
	 */
	public String getVehicleLicense() {
		return vehicleLicense;
	}
	/**
	 * 设置车牌号
	 */
	public void setVehicleLicense(String vehicleLicense) {
		this.vehicleLicense = vehicleLicense;
	}
	/**
	 * 得到承运商代码
	 * @return 承运商代码
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * 设置承运商代码
	 * @param carrierCode
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * 得到承运商名称
	 * @return 承运商名称
	 */
	public String getCarrierName() {
		return carrierName;
	}
	/**
	 * 设置承运商名称
	 * @param carrierName
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	/**
	 * 得到仓单数
	 * @return 仓单数
	 */
	public Integer getBillCount() {
		return billCount;
	}
	/**
	 * 设置仓单数
	 * @param billCount
	 */
	public void setBillCount(Integer billCount) {
		this.billCount = billCount;
	}
	/**
	 * 得到净重
	 * @return 净重
	 */
	public Double getGrossWeight() {
		return grossWeight;
	}
	/**
	 * 设置净重
	 * @param grossWeight
	 */
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}
	/**
	 * 得到毛重
	 * @return 毛重
	 */
	public Double getNetWeight() {
		return netWeight;
	}
	/**
	 * 设置毛重
	 * @param netWeight
	 */
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}
	/**
	 * 得到第1个集装箱号
	 * @return 第1个集装箱号
	 */
	public String getContainerNo1() {
		return containerNo1;
	}
	/**
	 * 设置第1个集装箱号
	 * @param containerNo1
	 */
	public void setContainerNo1(String containerNo1) {
		this.containerNo1 = containerNo1;
	}
	/**
	 * 得到第2个集装箱号
	 * @return 第2个集装箱号
	 */
	public String getContainerNo2() {
		return containerNo2;
	}
	/**
	 * 设置第2个集装箱号
	 * @param containerNo2
	 */
	public void setContainerNo2(String containerNo2) {
		this.containerNo2 = containerNo2;
	}
	/**
	 * 得到第1关锁号
	 * @return 第1关锁号
	 */
	public String getSealNo1() {
		return sealNo1;
	}
	/**
	 * 设置第1关锁号
	 * @param sealNo1
	 */
	public void setSealNo1(String sealNo1) {
		this.sealNo1 = sealNo1;
	}
	/**
	 * 得到第2关锁号
	 * @return 得到第2关锁号
	 */
	public String getSealNo2() {
		return sealNo2;
	}
	/**
	 * 设置第2关锁号
	 * @param sealNo2
	 */
	public void setSealNo2(String sealNo2) {
		this.sealNo2 = sealNo2;
	}
	/**
	 * 得到申报状态
	 * @return 申报状态
	 */
	public String getDeclareState() {
		return declareState;
	}
	/**
	 * 设置申报状态
	 * @param declareState
	 */
	public void setDeclareState(String declareState) {
		this.declareState = declareState;
	}
	
	/**
	 * 得到进出口标志
	 * @return 进出口标志
	 */
	public String getIoFlag() {
		return ioFlag;
	}
	
	/**
	 * 设置进出口标志
	 * @param flag
	 */
	public void setIoFlag(String ioFlag) {
		this.ioFlag = ioFlag;
	}
}
