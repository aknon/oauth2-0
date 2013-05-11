package com.goraksh.rest.auth.request;

import com.goraksh.rest.RandomString;

public class AuthorisationResponseGenerator {
	
	private AuthorisationRequest authRequest;
	private String state;
	private RandomString random;
	
	public AuthorisationResponseGenerator( AuthorisationRequest authReqest ) {
		this.authRequest = authReqest;
		this.random = RandomString.getInstance();
	}
	
	public AuthorisationResponseGenerator( AuthorisationRequest authReqest, String state ) {
		this(authReqest);
		this.state = state;			
	}
	
	public AuthorisationResponse generate() {
		String code = random.nextString();
		String state = this.state != null ? this.state :  this.authRequest.getState();
		AuthorisationResponse authResponse = new AuthorisationResponse( code, state );
		System.out.println("New Authorisation response Generated for State: " + authResponse.getState() + " .Authorisation Response: " + authResponse.toString() );
		return authResponse;		
	}

}
