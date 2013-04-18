package com.goraksh.rest;

import java.util.concurrent.ConcurrentHashMap;

public class Store {

	private static String CLIENT_ID;
	private static RandomString random;
	private static ConcurrentHashMap<String, AuthParams> store;
	

	static {
		random = new RandomString(20);
		CLIENT_ID = random.nextString();

		store = new ConcurrentHashMap<>();
	}

	public static AuthParams create(String sessionId) {
		System.out.println("Creating new Access Tokens and ClientId for Session Id:" + sessionId + " .Length :"
				+ sessionId.length());
		
		AuthParams params = createNew(sessionId);
		System.out.println("New. SessionId: " + sessionId + " ,client_id :" + params.getClientId() + " ,access_token :" + params.getAccessToken());
		return params;
	   
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
	
	
	
	private static AuthParams createNew(String sessionId) {
		String state = random.nextString();
		String accessToken = random.nextString();
		AuthParams params = new AuthParams();
		params.setClientId(CLIENT_ID);
		params.setAccessToken(accessToken);
		params.setState(state);

		store.put(sessionId, params);
		return params;
	}

	
}
