package com.bestway.customs.common.entity;

public class EntityShowCustomerError {
	private int errid;
	private String error;
	private String customsDeclarationCode;//报关单号
	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}
	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}
	public int getErrid() {
		return errid;
	}
	public void setErrid(int errid) {
		this.errid = errid;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
