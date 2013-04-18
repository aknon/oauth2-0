package com.goraksh.rest;

public class AuthParams {
	
	private String clientId;
	private String state;
	private String accessToken;
	private long issueTime;
	
	private int errorcode;
	
	private String errorMessage;
		
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public AuthParams() {
		issueTime = System.currentTimeMillis();
	}
	public long getIssueTime() {
		return issueTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}	
	
}
