package com.goraksh.rest.clientapp.request;

public class ClientAuthError extends GenericError {
	
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
}
