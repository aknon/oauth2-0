package com.goraksh.rest;

import java.util.concurrent.ConcurrentHashMap;

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
		random = new RandomString(20);
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
		System.out.println("Creating new Access Tokens and ClientId for Session Id:" + sessionId + " .Length :"
				+ sessionId.length());
		
		AuthParams params = createNew( false);
		System.out.println("New Request. Creating new AuthParams Object. Key stored is state: " + params.getState() + " ,client_id :" + params.getClientId() + " ,access_token :" + params.getAccessToken());
		return params;
	   
		}
	
	public static AuthParams createWithoutAccessToken() {
		
		AuthParams params = createNew( false);
		System.out.println("New Request. Creating new AuthParams Object. Key Stored is 'State': " + params.getState() + " ,client_id :" + params.getClientId() + " ,access_token :" + params.getAccessToken());
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
		
	public static AuthParams update(String requestId) {
		System.out.println("Loooking for Access Tokens and ClientId in Store for Session Id:" + requestId + " .Length :"
				+ requestId.length());
		
		AuthParams params = null;
		if (store.containsKey(requestId)) {
			System.out.println("RequestId Id:" + requestId + " already present in Store");
			params = store.get(requestId);
			if (ErrorManger.isAccessTokenExpiry(requestId, params))
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
		params.setRequestId(sessionId);

		store.put(sessionId, params);
		return params;
	}
	
	private static AuthParams createNew(boolean withAccessToken) {
		String state = random.nextString();
		
		AuthParams params = new AuthParams();
		params.setClientId(CLIENT_ID);
		params.setClientName( CLIENT_NAME );
		
		if ( withAccessToken ) {
			params = generateAndAddAccessToken( params );
		}
		params.setState(state);
		params.setRequestId( state );

		store.put(state, params);
		return params;
	}

	
}
