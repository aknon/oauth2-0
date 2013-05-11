package com.goraksh.rest.auth.map;

public class UserCredentials {
	
	private String username;
	private String password;
	
	public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public int hashCode() {
		return username.hashCode() + password.hashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj == null ||  ! (obj instanceof UserCredentials ))
			return false;
		return this.hashCode() == obj.hashCode();
	}
	
	public String getUsername() {
		return this.username;
	}

}
