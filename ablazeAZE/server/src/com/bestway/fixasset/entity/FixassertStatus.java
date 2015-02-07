package com.bestway.fixasset.entity;

import java.util.Date;
/**
 * 设备状态
 * @author Administrator
 *
 */
public class FixassertStatus implements java.io.Serializable{

	/**
	 * 组号
	 */
	private String groupNo;

	/**
	 * 发票号
	 */
	private String invoiceNo;

	/**
	 * 征免税号
	 */
	private String certNo;

	/**
	 * 保证书号(报检号)
	 */
	private String warNo;

	/**
	 * 状态
	 */
	private Integer stateMark;

	/**
	 * 收件日期
	 */
	private Date createDate;

	/**
	 * 征免税证递单日期
	 */
	private Date dutyCertApplyDate;

	/**
	 * 征免税证出单日期
	 */
	private Date dutyCertApproveDate;

	/**
	 * 待出货通知日期
	 */
	private Date prepExportNoteDate;

	/**
	 * 设备到港日期
	 */
	private Date arriveHKDate;

	/**
	 * 设备到莞日期
	 */
	private Date arriveDGDate;

	/**
	 * 商检日期
	 */
	private Date inspectDate;

	/**
	 * 报关日期
	 */
	private Date declareDate;

	/**
	 * 进厂日期
	 */
	private Date inFactDate;
}
