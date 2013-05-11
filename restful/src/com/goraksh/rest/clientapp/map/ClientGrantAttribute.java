package com.goraksh.rest.clientapp.map;

import com.goraksh.rest.clientapp.request.ClientTokenResponse;
import com.goraksh.rest.clientapp.request.RedirectionRequest;

public class ClientGrantAttribute {
	
	private RedirectionRequest codeGrant;
	private ClientTokenResponse token;
	
	public ClientGrantAttribute( RedirectionRequest codeGrant, ClientTokenResponse token ) {
		this.codeGrant = codeGrant;
		this.token = token;
	}

	public RedirectionRequest getCodeGrant() {
		return codeGrant;
	}

	public void setCodeGrant(RedirectionRequest codeGrant) {
		this.codeGrant = codeGrant;
	}

	public ClientTokenResponse getToken() {
		return token;
	}

	public void setToken(ClientTokenResponse token) {
		this.token = token;
	}
}
