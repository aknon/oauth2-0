package com.goraksh.rest.auth.request;


public class AuthorisationError extends AuthError {
	
	private boolean hasError;
	
	public AuthorisationError() {
		super();
		hasError = false;
	}
	
	public enum ErrorType {
		userOriented,
		redirectionOriented;
	}
	
	private ErrorType errorType = ErrorType.redirectionOriented;
	
	public void setErrorType( ErrorType type ) {
		this.errorType = type;
	}
	
	public ErrorType getErrorType() {
		return this.errorType;
	}
	
	public boolean isUserOriented() {
		return this.errorType == ErrorType.userOriented;
	}
	
	public boolean isRedirectionOriented() {
		return this.errorType == ErrorType.redirectionOriented;
	}
	
	public boolean hasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
		
}
