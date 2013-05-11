package com.goraksh.rest.auth.request;

public class AuthorisationCodeTableAttribute {
	
	
	private AuthorisationResponse authResponse;
	private AuthorisationRequest authRequest;
	private boolean isCodeValid;
	
	public AuthorisationCodeTableAttribute( AuthorisationResponse authResponse, AuthorisationRequest authRequest ) {
		this.authRequest = authRequest;
		this.authResponse = authResponse;
		 isCodeValid = true;
	}

	public AuthorisationResponse getAuthResponse() {
		return authResponse;
	}
	public void setAuthResponse(AuthorisationResponse authResponse) {
		this.authResponse = authResponse;
	}
	public AuthorisationRequest getAuthRequest() {
		return authRequest;
	}
	public void setAuthRequest(AuthorisationRequest authRequest) {
		this.authRequest = authRequest;
	}
	public boolean isCodeValid() {
		return isCodeValid;
	}
	public void setCodeValid(boolean isCodeValid) {
		this.isCodeValid = isCodeValid;
	}

}
