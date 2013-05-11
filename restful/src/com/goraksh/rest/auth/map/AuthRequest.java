package com.goraksh.rest.auth.map;

/**
 * All the Authorisation paramters received by the Authorisation server for the Authorisation Request
 * 
 * @author niteshk
 *
 */
public class AuthRequest {
	
	private String clientId;
	private String authCode;
	private Integer userloginId;
	private String redirectUri;
	private String requestType;
	private String scope;
	//private String state; // this is not required
	
	public AuthRequest(String clientId, String requestType, Integer userLoginId) {
		this.clientId = clientId;
		this.requestType = requestType;
		this.userloginId  = userLoginId;
	}
	
	public AuthRequest redirectUri(String uri) {
		this.redirectUri = uri;
		return this;
	}
	
	public AuthRequest scope( String scope ) {
		this.scope = scope;
		return this;
	}
	
	public AuthRequest code(String authCode ) {
		this.authCode = authCode;
		return this;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public Integer getUserloginId() {
		return userloginId;
	}
	public void setUserloginId(Integer userloginId) {
		this.userloginId = userloginId;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	
	

}
