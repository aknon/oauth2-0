package com.goraksh.rest.auth.map;

import java.util.concurrent.ConcurrentHashMap;

import com.goraksh.rest.auth.request.AuthorisationRequest;
import com.goraksh.rest.auth.request.TokenRequest;
import com.goraksh.rest.auth.request.TokenResponse;

public class TokenTable {
	
	private static TokenTable instance;
	private ConcurrentHashMap<String, TokenTableAttribute> tokenTable;
	
	public static synchronized TokenTable getInstance() {
		if( instance == null )
			instance = new TokenTable();
		return instance;
	}

	private TokenTable() {
		tokenTable = new ConcurrentHashMap<>();
}
	
	public void saveAuthorisationGrantToken( TokenRequest tokenRequest, TokenResponse tokenResponse ) {
		System.out.println("Saving successfull generated Token to TokenTable");
		tokenTable.put( tokenResponse.getAccessToken() , new TokenTableAttribute(tokenRequest, tokenResponse) );
	}
	
	public void saveImplicitGrantToken( AuthorisationRequest implicitTokenRequest, TokenResponse tokenResponse ) {
		System.out.println("Saving successfull generated Token to TokenTable");
		tokenTable.put( tokenResponse.getAccessToken() , new TokenTableAttribute(implicitTokenRequest, tokenResponse) );
	}
	
	public TokenTableAttribute get( String accessToken ) {
		return tokenTable.get(accessToken);
	}
	
	public void  forceInvalidateToken(String accessTokne) {
		tokenTable.get(accessTokne).forceInvalidate();
	}	

}
