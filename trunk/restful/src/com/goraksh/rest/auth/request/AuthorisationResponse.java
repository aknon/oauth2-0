package com.goraksh.rest.auth.request;

/**
 * 
 * @author niteshk
 *
 */
public class AuthorisationResponse {
	
	private String code; // this code can also be access_token in case response_type="token"
	private String state;
	private String responseType; // = code, = token
	private String toString;
	
	public AuthorisationResponse( String code, String state, String responseType ) {
		this.code = code;
		this.state  = state;
		this.responseType = responseType;
}
	
	public String getResponseType() {
		return this.responseType;			
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
