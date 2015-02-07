package com.bestway.bcus.checkstock.entity;


/**
 * 结转成品差额
 * @author chl
 *
 */
public class ECSTransferBaseConvert extends ECSBaseConvert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 已收/送货未转厂（工厂）
	 */
	private Double ptUnTransferNum;
	/**
	 * 已转厂未收/送货（工厂）
	 */
	private Double ptUnSendferNum;		
	/**
	 * 已收/送货未转厂（报关）
	 */
	private Double hsUnTransferNum;
	/**
	 * 已转厂未收/送货（报关）
	 */
	private Double hsUnSendferNum;
	/**
	 * 收/发货数量(编码级)
	 */
	private Double hsBillNum;
	/**
	 * 报关数量(编码级)
	 */
	private Double hsCustomsNum;
	
	/**
	 * @return the ptUnTransferNum
	 */
	public Double getPtUnTransferNum() {
		return ptUnTransferNum;
	}
	/**
	 * @param ptUnTransferNum the ptUnTransferNum to set
	 */
	public void setPtUnTransferNum(Double ptUnTransferNum) {
		this.ptUnTransferNum = ptUnTransferNum;
	}
	/**
	 * @return the ptUnSendferNum
	 */
	public Double getPtUnSendferNum() {
		return ptUnSendferNum;
	}
	/**
	 * @param ptUnSendferNum the ptUnSendferNum to set
	 */
	public void setPtUnSendferNum(Double ptUnSendferNum) {
		this.ptUnSendferNum = ptUnSendferNum;
	}
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
	public Double getHsBillNum() {
		return hsBillNum;
	}
	public void setHsBillNum(Double hsBillNum) {
		this.hsBillNum = hsBillNum;
	}
	public Double getHsCustomsNum() {
		return hsCustomsNum;
	}
	public void setHsCustomsNum(Double hsCustomsNum) {
		this.hsCustomsNum = hsCustomsNum;
	}
	
	public void init(){
		ptUnTransferNum = 0d;
		ptUnSendferNum = 0d;		
		hsUnTransferNum = 0d;
		hsUnSendferNum = 0d;
		hsBillNum = 0d;
		hsCustomsNum = 0d;
	}
}
