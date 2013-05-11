package com.goraksh.rest.clientapp.map;

import java.util.concurrent.ConcurrentHashMap;

import com.goraksh.rest.RandomString;

public class ClientAuthHandler {

	private static RandomString random;
	private static ConcurrentHashMap<String, ClientAuthParams> store;
	private static ClientAuthHandler handler;
	
	public static synchronized  ClientAuthHandler getInstance() {
		if ( handler == null )
			handler = new ClientAuthHandler();
		return handler;
	}
	
	private ClientAuthHandler() {
		random = RandomString.getInstance();
		store = new ConcurrentHashMap<>();
	}

	public ClientAuthParams generateKeyandSave( ClientAuthParams params) {
		String key = random.nextString();
		params.setKey(key);
		store.put( key, params );
		return params;
	}
	
	public ClientAuthParams get( String key) {
		return store.get( key );
	}
	
}
