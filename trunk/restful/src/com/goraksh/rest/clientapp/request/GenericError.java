package com.goraksh.rest.clientapp.request;


public class GenericError {
	
	private String error;
	private String errorMessage;
	private String errorUri;
	private boolean hasError;
	
	public GenericError() {
		// hasError may be true or false in this case
	}
	
	public GenericError(String error, String errorMessage ) {
		this.error = error;
		this.errorMessage = errorMessage;
		hasError = true;
	}
	
	public GenericError(String error, String errorMessage, String errorUri ) {
		this( error, errorMessage  );
		this.errorUri = errorUri;
		hasError = true;
	}
	
	public boolean hasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
 public String getError() {
		return error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorUri() {
		return errorUri;
	}

}
