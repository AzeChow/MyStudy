package com.bestway.bcus.client.checkstock.factoryanalyse;

public class ErrorMessage {
	private String title;
	private String ptNo;
	private String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getPtNo() {
		return ptNo;
	}

	public void setPtNo(String ptNo) {
		this.ptNo = ptNo;
	}

	public String getTitle() {
		return title;
	}
	
	public ErrorMessage(){
	}
	public ErrorMessage(String title, String errorMessage){
		this.errorMessage = errorMessage;
		this.title = title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}