package com.goraksh.rest.clientapp.map;

import java.util.concurrent.ConcurrentHashMap;

public class ClientGrantTable {

	private static ConcurrentHashMap<String, ClientGrantAttribute> store;
	private static ClientGrantTable instance;
	
	public static synchronized  ClientGrantTable getInstance() {
		if ( instance == null )
			instance = new ClientGrantTable();
		return instance;
	}
	
	private ClientGrantTable() {
	store = new ConcurrentHashMap<>();
	}

	public void save( ClientAuthParams params, ClientGrantAttribute grantAttribute) {
		store.put( params.getKey(), grantAttribute );
	}
	
	public void save( String key, ClientGrantAttribute grantAttribute) {
		store.put( key, grantAttribute );
	}
	
	public ClientGrantAttribute get( String key) {
		return store.get( key );
	}
	
}
