package com.goraksh.rest.clientapp.map;

import java.util.concurrent.ConcurrentHashMap;

import com.goraksh.rest.RandomString;
import com.goraksh.rest.clientapp.request.RedirectionRequest;

public class ClientRedirectRequestTable {

	private static ConcurrentHashMap<String, RedirectionRequest> store;
	private static ClientRedirectRequestTable handler;
	
	public static synchronized  ClientRedirectRequestTable getInstance() {
		if ( handler == null )
			handler = new ClientRedirectRequestTable();
		return handler;
	}
	
	private ClientRedirectRequestTable() {
		store = new ConcurrentHashMap<>();
	}

	public RedirectionRequest save( String state, RedirectionRequest redirectionRequest ) {
		store.put( state, redirectionRequest );
		return redirectionRequest;
	}
	
	public RedirectionRequest get( String key) {
		return store.get( key );
	}
	
}
