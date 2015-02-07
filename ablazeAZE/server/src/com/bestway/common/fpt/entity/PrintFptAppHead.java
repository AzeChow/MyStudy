package com.bestway.common.fpt.entity;
/**
 * 用于申请表每五笔打印
 * @author lyh
 *
 */
public class PrintFptAppHead extends FptAppHead{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 流水号（用于分笔）
	 */
	private Integer serialNumber;

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

}
