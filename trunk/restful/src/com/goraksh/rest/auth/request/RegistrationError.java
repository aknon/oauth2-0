package com.goraksh.rest.auth.request;

public class RegistrationError extends AuthError {
	
private boolean hasError;
	
	public RegistrationError() {
		super();
	}
	
	public RegistrationError(String errorcode, String errorMessage ) {
		super(errorcode, errorMessage);
	}
	
	public boolean hasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
}
