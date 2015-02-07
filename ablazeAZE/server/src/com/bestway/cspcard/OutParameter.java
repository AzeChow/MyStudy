package com.bestway.cspcard;

public class OutParameter {
	
	private String errMsg;
	
	private String signData;
	
	private Integer msgSignDatalen;
	
	private String icCode;
	
	private String unitInfo;
	
	private Integer unitInfoLen;
	
	private String serverRndNumSign;
	
	private Integer serverRndNumSignLen;
	
	private String clientRandomNum;

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Integer getMsgSignDatalen() {
		return msgSignDatalen;
	}

	public void setMsgSignDatalen(Integer msgSignDatalen) {
		this.msgSignDatalen = msgSignDatalen;
	}

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

	public String getClientRandomNum() {
		return clientRandomNum;
	}

	public void setClientRandomNum(String clientRandomNum) {
		this.clientRandomNum = clientRandomNum;
	}

	public String getIcCode() {
		return icCode;
	}

	public void setIcCode(String icCode) {
		this.icCode = icCode;
	}

	public String getServerRndNumSign() {
		return serverRndNumSign;
	}

	public void setServerRndNumSign(String serverRndNumSign) {
		this.serverRndNumSign = serverRndNumSign;
	}

	public Integer getServerRndNumSignLen() {
		return serverRndNumSignLen;
	}

	public void setServerRndNumSignLen(Integer serverRndNumSignLen) {
		this.serverRndNumSignLen = serverRndNumSignLen;
	}

	public String getUnitInfo() {
		return unitInfo;
	}

	public void setUnitInfo(String unitInfo) {
		this.unitInfo = unitInfo;
	}

	public Integer getUnitInfoLen() {
		return unitInfoLen;
	}

	public void setUnitInfoLen(Integer unitInfoLen) {
		this.unitInfoLen = unitInfoLen;
	}	
	

}
