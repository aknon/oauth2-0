package com.goraksh.rest.clientapp.request;

/**
 * 
 * @author niteshk
 *
 */
public class RedirectionError extends GenericError {
	private String state;

	public RedirectionError( String error, String errorMessage, String errorUri, String state ) {
		super(error, errorMessage, errorUri);
		this.state = state;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}	
}
