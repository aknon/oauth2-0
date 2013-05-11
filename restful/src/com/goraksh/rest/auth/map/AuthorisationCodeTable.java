package com.goraksh.rest.auth.map;

import java.util.concurrent.ConcurrentHashMap;

import com.goraksh.rest.auth.request.AuthorisationCodeTableAttribute;
import com.goraksh.rest.auth.request.AuthorisationRequest;
import com.goraksh.rest.auth.request.AuthorisationResponse;

public class AuthorisationCodeTable {
	
	private static AuthorisationCodeTable instance;
	private ConcurrentHashMap<String, AuthorisationCodeTableAttribute> authCodeTable;
	
	public static synchronized AuthorisationCodeTable getInstance() {
		if( instance == null )
			instance = new AuthorisationCodeTable();
		return instance;
	}

	private AuthorisationCodeTable() {
		authCodeTable = new ConcurrentHashMap<>();
}
	
	public void save( AuthorisationRequest authRequest, AuthorisationResponse authResponse ) {
		authCodeTable.put( authResponse.getCode(), new AuthorisationCodeTableAttribute(authResponse, authRequest));
	}
	
	public AuthorisationCodeTableAttribute get( String code ) {
		return authCodeTable.get( code );
	}
	
	public void  updateIsCodeValid( String code, boolean isValid ) {
		authCodeTable.get( code ).setCodeValid( isValid );
	}
	
	

}
