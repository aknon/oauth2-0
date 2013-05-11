package com.goraksh.rest.auth.request;

/**
 * 
 * @author niteshk
 *
 */
public class AuthorisationResponse {
	
	private String code;
	private String state;
	private String toString;
	
	public AuthorisationResponse( String code, String state ) {
		this.code = code;
		this.state  = state;
}
	public String getCode() {
		return code;
	}

	public String getState() {
		return state;
	}
	public String toString() {
		if ( toString == null)
		toString =  "Code: " + code + " ,State: " + state;
		return toString;
	}

}
