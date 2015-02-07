package com.bestway.bls.entity;

import java.io.Serializable;
/**
 * DeliveryEntity临时表
 * @author hw
 *
 */
public class TempDeliveryEntity implements Serializable{
    /**
     * 车次号
     */
	private String deliveryNo=null;

	/**
	 * 车牌号码
	 */
	private String vehicleLicense=null;
	/**
	 * 申报货到报文时间
	 */
	private String deliveryOperTime=null;
	/**
	 * 获取车次号
	 * @return
	 */
	public String getDeliveryNo() {
		return deliveryNo;
	}
	/**
	 * 设置车次号
	 * @param deliveryNo
	 */
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	/**
	 * 获取车牌号
	 * @return
	 */
	public String getVehicleLicense() {
		return vehicleLicense;
	}
	/**
	 * 设置车牌号
	 * @param vehicleLicense
	 */
	public void setVehicleLicense(String vehicleLicense) {
		this.vehicleLicense = vehicleLicense;
	}
	/**
	 * 获取申报货到报文时间
	 * @return
	 */
	public String getDeliveryOperTime() {
		return deliveryOperTime;
	}
	/**
	 * 设置申报货到报文时间
	 * @param deliveryOperTime
	 */
	public void setDeliveryOperTime(String deliveryOperTime) {
		this.deliveryOperTime = deliveryOperTime;
	}
}
