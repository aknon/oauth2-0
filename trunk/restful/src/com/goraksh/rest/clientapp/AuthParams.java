package com.goraksh.rest.clientapp;

/**
 * 
 * @author niteshk
 *
 */
public class AuthParams {
	
	private boolean cancel;
	private String clientId;
	private String clientName;
	private String state;
	private String accessToken;
	private long issueTime;
	private String scope;
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Client Id: ").append( clientId )
		.append( ", Client Name: ").append( clientName)
		.append(", Access Token: ").append( accessToken != null ? accessToken : "")
		.append(", Issue Time: ").append( issueTime );
		return sb.toString();
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	private int errorcode;
	
	private String errorMessage;
		
	public void cancel() {
		this.cancel = true;
	}
	
	public boolean isCancelled() {
		return cancel;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
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
	public void refreshIssueTime() {
		issueTime = System.currentTimeMillis();
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
