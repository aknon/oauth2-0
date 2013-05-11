package com.goraksh.rest.auth.request;

import com.goraksh.rest.RandomString;
import com.goraksh.rest.auth.map.AuthorisationCodeTable;
import com.goraksh.rest.auth.map.ClientIdTokenControlTable;
import com.goraksh.rest.auth.map.TokenControl;

public class TokenResponseGenerator {
	
	private TokenRequest tokenRequest;
	private String scope; // this scope can be overrideen by auth server. In that case, scope will be different from the scope requested by the client
	private RandomString random;
	
	public TokenResponseGenerator( TokenRequest tokenRequest ) {
		this.tokenRequest = tokenRequest;
		this.random = RandomString.getInstance();
	}
	
	public TokenResponseGenerator( TokenRequest tokenRequest, String code ) {
		this(tokenRequest);
		this.scope = AuthorisationCodeTable.getInstance().get(code).getAuthRequest().getScope();		
	}
	
	public TokenResponse generate() {
		String accessToken = random.nextString();
		TokenControl control = ClientIdTokenControlTable.getInstance().get( tokenRequest.getClientId() );
		System.out.println("In Token End Point. Generated access_token : " + accessToken);
		return new TokenResponse(accessToken, control.getTokenType(), control.getExpiresIn(), this.scope, null);
	}

}
