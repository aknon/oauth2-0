package com.goraksh.rest.auth.request;

import com.goraksh.rest.RandomString;
import com.goraksh.rest.auth.map.AuthorisationCodeTable;
import com.goraksh.rest.auth.map.ClientIdTokenControlTable;
import com.goraksh.rest.auth.map.TokenControl;

public class TokenResponseGenerator {
	
	private TokenRequest tokenRequest;
	private String scope; // this scope can be overrideen by auth server. In that case, scope will be different from the scope requested by the client
	private String code;
	private RandomString random;
	private AuthorisationRequest implicitTokenRequest;
	
	private TokenResponseGenerator( TokenRequest tokenRequest ) {
		this.tokenRequest = tokenRequest;
		this.random = RandomString.getInstance();
	}
	
	private TokenResponseGenerator( AuthorisationRequest implicitTokenRequest ) {
		this.implicitTokenRequest = implicitTokenRequest;
		this.random = RandomString.getInstance();
	}
	
	public TokenResponseGenerator( TokenRequest tokenRequest, String code ) {
		this(tokenRequest);
		this.scope = AuthorisationCodeTable.getInstance().get(code).getAuthRequest().getScope();	
		// no need of saving code. This is useful only for Implicit Grant type
	}
	
	public TokenResponseGenerator( AuthorisationRequest implicitTokenRequest, String accessToken ) {
		this(implicitTokenRequest);
		this.scope = AuthorisationCodeTable.getInstance().get(accessToken).getAuthRequest().getScope();		
		this.code = accessToken;
	}
	
	private TokenResponse generate(String accessToken, TokenControl tokenControl) {
		System.out.println("In Token Response Generator. Generated access_token : " + accessToken);
		return new TokenResponse(accessToken, tokenControl.getTokenType(), tokenControl.getExpiresIn(), this.scope, null);
	}
	
	public TokenResponse authorisationGrantTokenResponse() {
		String accessToken = random.nextString();
		TokenControl tokenControl = ClientIdTokenControlTable.getInstance().get( tokenRequest.getClientId() );
		System.out.println("In TokenResponseGenerator End Point. Generated access_token for Authorisation Grant, 'Response_type=code' : " + accessToken);
		return generate(accessToken, tokenControl);
	}
	
	public TokenResponse implicitGrantTokenResponse() {
		String accessToken = this.code;
		TokenControl tokenControl = ClientIdTokenControlTable.getInstance().get( implicitTokenRequest.getClientId() );
		System.out.println("In Token End Point. Generated access_token for Implicit Grant, 'Response_type=token'  : " + accessToken);
		return generate(accessToken, tokenControl);
	}

}
