package com.bestway.cspcard.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "icCardInfo", propOrder = {
    "applier",
    "certSeqNo",
    "icCode",
    "signData",
    "tradeCode",
    "tradeName",
    "tradeType"
})
public class ICCardInfo implements java.io.Serializable {
	private String icCode;

	private String certSeqNo;

	private String applier;

	private String tradeName;

	private String tradeType;

	private String tradeCode;

	private String signData;

	public String getApplier() {
		return applier;
	}

	public void setApplier(String applier) {
		this.applier = applier;
	}

	public String getCertSeqNo() {
		return certSeqNo;
	}

	public void setCertSeqNo(String certSeqNo) {
		this.certSeqNo = certSeqNo;
	}

	public String getIcCode() {
		return icCode;
	}

	public void setIcCode(String icCode) {
		this.icCode = icCode;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

}
