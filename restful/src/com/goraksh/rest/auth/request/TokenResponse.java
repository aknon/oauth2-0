package com.goraksh.rest.auth.request;

import com.goraksh.rest.auth.JsonUtil;

public class TokenResponse {

	private String accessToken;
	private String tokenType;
	private long expiresIn;
	private String refreshToken; //
	private String scope; //
	private String error;
	
	public TokenResponse( String accessToken, String tokenType, long expiresIn ) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiresIn = expiresIn;
	}
	
	public TokenResponse( String accessToken, String tokenType, long expiresIn, String scope, String refreshToken ) {
	this(accessToken, tokenType, expiresIn);
	this.scope = scope;
	this.refreshToken = refreshToken;
	}
	
	public TokenResponse( String accessToken, String tokenType, long expiresIn, String scope, String refreshToken, String error ) {
		this(accessToken, tokenType, expiresIn);
		this.scope = scope;
		this.refreshToken = refreshToken;
		this.error = error;
		}

	public String getAccessToken() {
		return accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getScope() {
		return scope;
	}	
	
	public String toString() {
		System.out.println("Converting Token Response class to Json string : " + JsonUtil.toJsonFro(this));
		return JsonUtil.toJsonFro(this);
	}
	
}
