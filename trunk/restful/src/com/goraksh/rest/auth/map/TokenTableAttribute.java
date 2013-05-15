package com.goraksh.rest.auth.map;

import com.goraksh.rest.auth.Constants;
import com.goraksh.rest.auth.request.AuthorisationRequest;
import com.goraksh.rest.auth.request.TokenRequest;
import com.goraksh.rest.auth.request.TokenResponse;

public class TokenTableAttribute {
	
	
	private TokenResponse tokenResponse;
	private TokenRequest tokenRequest;
	private AuthorisationRequest implicitTokenRequest;
	private String responseType;
	
	private boolean isValid;
	private boolean forcedInvalidated;
	private long generationTimeInMillis;
	
	public TokenTableAttribute( TokenRequest tokenRequest, TokenResponse tokenResponse ) {
	this.tokenRequest = tokenRequest;
	this.tokenResponse =  tokenResponse;
	generationTimeInMillis = System.currentTimeMillis();
	isValid = true;
	forcedInvalidated = false;
	responseType = Constants.AUTHORISATION_GRANT_TYPE;
	}
	
	public TokenTableAttribute( AuthorisationRequest implicitTokenRequest, TokenResponse tokenResponse ) {
	this.implicitTokenRequest = implicitTokenRequest;
	this.tokenResponse =  tokenResponse;
	generationTimeInMillis = System.currentTimeMillis();
	isValid = true;
	forcedInvalidated = false;
	responseType = Constants.IMPLICIT_GRANT_TYPE;
	}
	
	public String getResponseType() {
		return this.responseType;
	}

	public TokenResponse getTokenResponse() {
		return tokenResponse;
	}

	public TokenRequest getTokenRequest() {
		return tokenRequest;
	}
	
	public AuthorisationRequest getImplicitTokenRequest() {
		return this.implicitTokenRequest;
	}

	public boolean isExpired() {
		if ( forcedInvalidated ) return isValid;
		boolean isExpired =  (System.currentTimeMillis() - generationTimeInMillis) > ( tokenResponse.getExpiresIn() * 1000);
		System.out.println("Validating Token Expiry Is Expired ? " + isExpired+ " .Time Expired(millis) : " + (System.currentTimeMillis() - generationTimeInMillis ) + "Allowed Time(millis): " + (tokenResponse.getExpiresIn() * 1000));;
		return isExpired;
	}	
	
	public void forceInvalidate() {
		isValid = false;
		forcedInvalidated = true;
	}

}
