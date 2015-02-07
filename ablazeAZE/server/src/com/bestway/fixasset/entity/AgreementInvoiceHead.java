package com.bestway.fixasset.entity;

import java.util.Date;

import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 批文协议设备发票表头
 * @author Administrator
 *
 */
public class AgreementInvoiceHead extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
	.getSerialVersionUID();
	/**
	 * 协议批文号
	 */
	private String sancEmsNo;
	/**
	 * 发票号码
	 */
	private String invoiceCode;

	/**
	 * 发送者
	 */
	private String sender;

	/**
	 * 发送方地址
	 */
	private String senderAddr;

	/**
	 * 发送方联系人
	 */
	private String senderContactPerson;

	/**
	 * 发送方电话
	 */
	private String senderTel;

	/**
	 * 接收者
	 */
	private String receiver;

	/**
	 * 接收方地址
	 */
	private String receiverAddr;

	/**
	 * 接收方联系人
	 */
	private String receiverContactPerson;

	/**
	 * 接收方电话
	 */
	private String receiverTel;

	/**
	 * 承运者
	 */
	private String consigner;

	/**
	 * 承运方地址
	 */
	private String consignerAddr;

	/**
	 * 承运方联系人
	 */
	private String consignerContactPerson;

	/**
	 * 承运方电话
	 */
	private String consignerTel;

	/**
	 * 出发地
	 */
	private String fromPlace;

	/**
	 * 目的地
	 */
	private String toPlace;

	/**
	 * 运输方
	 */
	private String transporter;

	/**
	 * 运输时间
	 */
	private Date transTime;

	/**
	 * 发票日期：
	 */
	private Date invoiceDate;

	/**
	 * 运输方式
	 */
	private Transf transMode;

	/**
	 * 信用证
	 */
	private String lc;

	/**
	 * 开户银行
	 */
	private String accBank;

	/**
	 * 备注
	 */
	private String note;

	public String getSancEmsNo() {
		return sancEmsNo;
	}

	public void setSancEmsNo(String sancEmsNo) {
		this.sancEmsNo = sancEmsNo;
	}

	public String getAccBank() {
		return accBank;
	}

	public void setAccBank(String accBank) {
		this.accBank = accBank;
	}

	public String getConsigner() {
		return consigner;
	}

	public void setConsigner(String consigner) {
		this.consigner = consigner;
	}

	public String getConsignerAddr() {
		return consignerAddr;
	}

	public void setConsignerAddr(String consignerAddr) {
		this.consignerAddr = consignerAddr;
	}

	public String getConsignerContactPerson() {
		return consignerContactPerson;
	}

	public void setConsignerContactPerson(String consignerContactPerson) {
		this.consignerContactPerson = consignerContactPerson;
	}

	public String getConsignerTel() {
		return consignerTel;
	}

	public void setConsignerTel(String consignerTel) {
		this.consignerTel = consignerTel;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverAddr() {
		return receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	public String getReceiverContactPerson() {
		return receiverContactPerson;
	}

	public void setReceiverContactPerson(String receiverContactPerson) {
		this.receiverContactPerson = receiverContactPerson;
	}

	public String getReceiverTel() {
		return receiverTel;
	}

	public void setReceiverTel(String receiverTel) {
		this.receiverTel = receiverTel;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderAddr() {
		return senderAddr;
	}

	public void setSenderAddr(String senderAddr) {
		this.senderAddr = senderAddr;
	}

	public String getSenderContactPerson() {
		return senderContactPerson;
	}

	public void setSenderContactPerson(String senderContactPerson) {
		this.senderContactPerson = senderContactPerson;
	}

	public String getSenderTel() {
		return senderTel;
	}

	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public Transf getTransMode() {
		return transMode;
	}

	public void setTransMode(Transf transMode) {
		this.transMode = transMode;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

}
