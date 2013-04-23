package com.goraksh.rest;

import java.util.concurrent.ConcurrentHashMap;

public class SessionMap {

	private ConcurrentHashMap<String, Object> store;
	
	private static SessionMap instance;
	
	
	private SessionMap() {
		store = new ConcurrentHashMap<>();
    }
	
	public static synchronized SessionMap getInstance() {
		if( instance == null )
			instance = new SessionMap();
		
		return instance;
	}

	/**
	 * Returns without checking the Access Token Expiry.
	 * Can return null;
	 * 
	 * @param sessionId
	 * @return
	 */
	public Object get(String id) {
	System.out.println("Fetching Object from SessionMap for Id :" + id);
		
		return store.get(id);
	}
		
	public void put(String id, Object value) {
		store.put( id, value);
	}
	
}
