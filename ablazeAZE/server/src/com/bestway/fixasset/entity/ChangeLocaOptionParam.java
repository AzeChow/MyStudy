package com.bestway.fixasset.entity;
/**
 * 设备异动表
 * @author Administrator
 *
 */
public class ChangeLocaOptionParam implements java.io.Serializable {
	/**
	 * 设备报关进厂
	 */
	public final static int CUSTOMS_IN_FACT = 0;

	/**
	 * 设备厂内位移
	 */
	public final static int FACT_CHANGE_LOCATION = 1;

	/**
	 * 设备厂内增加
	 */
	public final static int FACT_ADD = 2;

	/**
	 * 设备厂内减少
	 */
	public final static int FACT_SUBTRACT = 3;
}
