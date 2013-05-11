package com.goraksh.rest.clientapp.request;

public class RedirectionRequest {
	
	private String code;
	private String state;
	private boolean hasError;
	private RedirectionError error;
	
	public RedirectionRequest(String code, String state) {
		this.code = code;
		this.state = state;
	}
	
	public RedirectionRequest(RedirectionError error) {
		hasError = true;
		this.error = error;
	}

	public boolean hasError() {
		return hasError;
	}

	public GenericError getError() {
		return error;
	}

	public String getCode() {
		return code;
	}

	public String getState() {
		return state;
	}	

}
