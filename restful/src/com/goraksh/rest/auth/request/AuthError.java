package com.goraksh.rest.auth.request;

public class AuthError {
	
	private String errorcode;
	private String errorMessage;
	
	public AuthError(){
		
	}
	
	public AuthError(String errorcode, String errorMessage ) {
		this.errorcode = errorcode;
		this.errorMessage = errorMessage;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
