package com.goraksh.rest.auth.request;

public class TokenRequest {
	
	private String code;
	private String clientKey;
	private String clientId;
	private String redirectUri;
	private String grantType;
	
	public TokenRequest( String code, String clientId, String clientKey, String redirectUri, String grantType ) {
		this.clientId = clientId;
		this.clientKey = clientKey;
		this.code = code;
		this.redirectUri = redirectUri;
		this.grantType = grantType;				
	}

	public String getClientKey() {
		return this.clientKey;
	}
	
	public String getCode() {
			return code;
	}

	public String getClientId() {
		return clientId;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public String getGrantType() {
		return grantType;
	}	

}
