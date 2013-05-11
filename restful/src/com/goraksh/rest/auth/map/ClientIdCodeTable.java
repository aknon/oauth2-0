package com.goraksh.rest.auth.map;

import java.util.concurrent.ConcurrentHashMap;

import com.goraksh.rest.auth.request.AuthorisationRequest;
import com.goraksh.rest.auth.request.AuthorisationResponse;

public class ClientIdCodeTable {
	
	private static ClientIdCodeTable instance;
	private ConcurrentHashMap<String, String> clientIdCodeMap;
	
	private ClientIdCodeTable() {
		this.clientIdCodeMap = new ConcurrentHashMap<>();
	}
	
	public static synchronized ClientIdCodeTable getInstance() {
		if( instance == null )
			instance = new ClientIdCodeTable();
		return instance;
	}

	public void save( AuthorisationRequest authRequest, AuthorisationResponse authResponse ) {
		String authCode = authResponse.getCode();
		String clientId = authRequest.getClientId();
		
		System.out.println("Saving ClientId: " + clientId + " , Cliend Code: " + authCode + " to ClientIdCodeTable");
		clientIdCodeMap.put( clientId, authCode);
	}
	
	public String get( String clientId ) {
		return clientIdCodeMap.get(clientId);
	}

	
}
