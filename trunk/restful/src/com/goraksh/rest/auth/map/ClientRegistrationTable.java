package com.goraksh.rest.auth.map;

import java.util.concurrent.ConcurrentHashMap;

import com.goraksh.rest.RandomString;
import com.goraksh.rest.auth.request.ClientRegister;

public class ClientRegistrationTable {

	private ConcurrentHashMap<String, ClientRegister> store;
	
	private static ClientRegistrationTable instance;
	
	private static RandomString random;
	
	private ClientRegistrationTable() {
		store = new ConcurrentHashMap<>();
		random =  RandomString.getInstance();
    }
	
	public static synchronized ClientRegistrationTable getInstance() {
		if( instance == null )
			instance = new ClientRegistrationTable();
		
		return instance;
	}

	public ClientRegister registerNewClient(String appname, String homeUrl, String redirectUri ) {
		ClientRegister clientRegister = new ClientRegister(appname,  homeUrl, redirectUri);
		String clientId = random.nextString();
		String clientKey = random.nextString() + random.nextString().substring( clientId.length() / 2 );
		
		clientRegister.setClientID( clientId );
		clientRegister.setClientKey(clientKey);
		
		System.out.println("Generated new ClientId: " + clientId +  " ,ClientKey: " + clientKey + " for App name: " + appname + " and RedirectUri: " + redirectUri );
		store.put(clientId,  clientRegister);
		return clientRegister;
	}
	
	public boolean isRegisteredClient(String clientId) {
		return store.containsKey( clientId );
	}
	
	public ClientRegister get( String clientId ) {
		return store.get( clientId );
	}
	
}
