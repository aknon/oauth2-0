package com.goraksh.rest.auth.map;

import com.goraksh.rest.auth.request.AuthorisationError;
import com.goraksh.rest.auth.request.AuthorisationRequest;

public class TempStore {
	
	public void setAuth(AuthorisationRequest auth) {
		this.auth = auth;
	}

	public void setError(AuthorisationError error) {
		this.error = error;
	}

	private AuthorisationRequest auth;
	private AuthorisationError error;
	
	public TempStore( AuthorisationRequest auth, AuthorisationError error) {
		this.auth = auth;
		this.error = error;
	}

	public AuthorisationRequest getAuth() {
		return auth;
	}

	public AuthorisationError getError() {
		return error;
	}

}
