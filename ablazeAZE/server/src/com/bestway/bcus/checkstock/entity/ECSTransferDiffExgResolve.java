package com.bestway.bcus.checkstock.entity;


/**
 * 结转成品差额折料转换报关数据
 * @author chl
 *
 */
public class ECSTransferDiffExgResolve extends ECSBaseResolve {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 已送货未转厂（报关）
	 */
	private Double hsUnTransferNum;
	/**
	 * 已转厂未送货（报关）
	 */
	private Double hsUnSendferNum;
	/**
	 * @return the hsUnTransferNum
	 */
	public Double getHsUnTransferNum() {
		return hsUnTransferNum;
	}
	/**
	 * @param hsUnTransferNum the hsUnTransferNum to set
	 */
	public void setHsUnTransferNum(Double hsUnTransferNum) {
		this.hsUnTransferNum = hsUnTransferNum;
	}
	/**
	 * @return the hsUnSendferNum
	 */
	public Double getHsUnSendferNum() {
		return hsUnSendferNum;
	}
	/**
	 * @param hsUnSendferNum the hsUnSendferNum to set
	 */
	public void setHsUnSendferNum(Double hsUnSendferNum) {
		this.hsUnSendferNum = hsUnSendferNum;
	}
}
