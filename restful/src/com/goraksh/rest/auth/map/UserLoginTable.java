package com.goraksh.rest.auth.map;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class UserLoginTable {
	
	private static UserLoginTable instance;
	private ConcurrentHashMap<UserCredentials, Integer> userLoginTable;
	
	public static synchronized UserLoginTable getInstance() {
		if( instance == null )
			instance = new UserLoginTable();
		return instance;
	}

	private UserLoginTable() {
		init();
	}
	
	private void init() {
		userLoginTable = new ConcurrentHashMap<>();
		userLoginTable.put( new UserCredentials("nk", "nk"), new Random().nextInt(10));
	}
	
	public Integer getLoginId(UserCredentials cred) {
		return userLoginTable.get(cred);
	}
	
	public boolean contains( UserCredentials cred ) {
		return userLoginTable.containsKey( cred );
	}
	
}
