package com.bestway.bcs.client.verification.factoryanalyse;

public class ErrorMessage {
	private String title;
	private String errorMessage;
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getTitle() {
		return title;
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