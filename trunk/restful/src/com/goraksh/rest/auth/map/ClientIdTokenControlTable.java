package com.goraksh.rest.auth.map;

import java.util.concurrent.ConcurrentHashMap;

import com.goraksh.rest.auth.Constants;

public class ClientIdTokenControlTable {
	
	private static ClientIdTokenControlTable instance;
	private ConcurrentHashMap<String, TokenControl> clientIdTokenControlMap;
	
	private ClientIdTokenControlTable() {
		this.clientIdTokenControlMap = new ConcurrentHashMap<>();
	}
	
	public static synchronized ClientIdTokenControlTable getInstance() {
		if( instance == null )
			instance = new ClientIdTokenControlTable();
		return instance;
	}

	public void update( String clientId ) {
		System.out.println("Client Registration. Updating CliendId: " + clientId + " ,with TokenControlParams: tokentype and tokenExpirytime");
		update0( clientId, defaultControl() );
	}
	
	private void update0( String clientId, TokenControl control) {
		clientIdTokenControlMap.put( clientId, control);
	}
	
	public TokenControl get( String clientId ) {
		return clientIdTokenControlMap.get(clientId);
	}
	
	private TokenControl defaultControl() {
		return  new TokenControl(TokenTypeAttribute.TokenType.BEARER, Constants.DEFAULT_TOKEN_EXPIRY_IN_SECS );
	}

	
}
