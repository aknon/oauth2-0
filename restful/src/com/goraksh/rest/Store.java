package com.goraksh.rest;

import java.util.concurrent.ConcurrentHashMap;

import com.goraksh.rest.clientapp.ErrorManger;
import com.goraksh.rest.clientapp.AuthParams;

/**
 * 
 * @author niteshk
 *
 */
public class Store {

	private static String CLIENT_ID;
	private static String CLIENT_NAME;
	
	private static RandomString random;
	private static ConcurrentHashMap<String, AuthParams> store;
	

	static {
		random = RandomString.getInstance();
		CLIENT_ID = random.nextString();
		CLIENT_NAME = "Test App";

		store = new ConcurrentHashMap<>();
	}

	public static AuthParams createWithAccessToken(String sessionId) {
		System.out.println("Creating new Access Tokens and ClientId for Session Id:" + sessionId + " .Length :"
				+ sessionId.length());
		
		AuthParams params = createNew(sessionId, true);
		System.out.println("New. SessionId: " + sessionId + " ,client_id :" + params.getClientId() + " ,access_token :" + params.getAccessToken());
		return params;
	   
		}
	
	public static AuthParams createWithoutAccessToken(String sessionId) {
		System.out.println("Creating new Access Tokens and ClientId for Session Id:" + sessionId);
		
		AuthParams params = createNew(sessionId, false);
		System.out.println("New. SessionId: " + sessionId + " ,client_id :" + params.getClientId() + " ,access_token :" + params.getAccessToken());
		return params;
	   
		}
	
	public static AuthParams createWithoutAccessToken(String sessionId, String clientId) {
		System.out.println("Creating new Access Tokens and ClientId for Session Id:" + "  and Client Id: " + clientId);
		
		AuthParams params = createNew(sessionId, clientId, false);
		System.out.println("New. SessionId: " + sessionId + " ,client_id :" + params.getClientId() + " ,access_token :" + params.getAccessToken());
		return params;
	   
		}
	
	/**
	 * Returns without checking the Access Token Expiry.
	 * Can return null;
	 * 
	 * @param sessionId
	 * @return
	 */
	public static AuthParams get(String sessionId) {
		System.out.println("Loooking for Access Tokens and ClientId in Store for Session Id:" + sessionId );
		
		return store.get(sessionId);
	}
		
	public static AuthParams update(String sessionId) {
		System.out.println("Loooking for Access Tokens and ClientId in Store for Session Id:" + sessionId + " .Length :"
				+ sessionId.length());
		
		AuthParams params = null;
		if (store.containsKey(sessionId)) {
			System.out.println("Session Id:" + sessionId + " already present");
			params = store.get(sessionId);
			if (ErrorManger.isAccessTokenExpiry(sessionId, params))
				ErrorManger.addAccessExpiredErrorCode( params );
		}
		return params;
	}
	
	public  static AuthParams generateAndAddAccessToken( AuthParams params ) {
		String accessToken = random.nextString();
		params.setAccessToken(accessToken);
		return params;
	}
	
	private static AuthParams createNew(String sessionId, boolean withAccessToken) {
		String state = random.nextString();
		
		AuthParams params = new AuthParams();
		params.setClientId(CLIENT_ID);
		params.setClientName( CLIENT_NAME );
		
		if ( withAccessToken ) {
			params = generateAndAddAccessToken( params );
		}
		params.setState(state);

		store.put(sessionId, params);
		return params;
	}
	
	private static AuthParams createNew(String sessionId, String clientId, boolean withAccessToken) {
		String state = random.nextString();
		
		AuthParams params = new AuthParams();
		
		if ( clientId == null || "".equals(clientId))
			clientId = CLIENT_ID;
		
		params.setClientId(clientId);
		params.setClientName( CLIENT_NAME );
		
		if ( withAccessToken ) {
			params = generateAndAddAccessToken( params );
		}
		params.setState(state);

		//store.put(sessionId, params);
		store.put(state, params);
		return params;
	}

	
}
