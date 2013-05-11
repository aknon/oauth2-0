package com.goraksh.rest.clientapp.map;

public class ClientAuthParams {
	
   private String clientId;
	private String state;
	private String redirectUri;
	private String scope; // can be null
	private String responseType;
	private String clientKey;
	
	public ClientAuthParams( String clientId, String responseType, String redirectUri, String scope )  {
		this(clientId, responseType, redirectUri);
		this.scope  = scope;
	}
	
	public ClientAuthParams( String clientId, String responseType, String redirectUri, String scope, String clientKey )  {
		this(clientId, responseType, redirectUri, scope);
		this.clientKey  = clientKey;
	}
	
	public ClientAuthParams( String clientId, String responseType, String redirectUri )  {
		this.clientId = clientId;
		this.redirectUri = redirectUri;
		this.responseType = responseType;		
	}
	
		
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	
	public String getClientKey() {
		return this.clientKey;
	}

	public String getClientId() {
		return clientId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public String getScope() {
		return scope;
	}
	
	public void setKey( String key ) {
		setState(key);
	}
	public String getKey() {
		return getState();
	}

	public String getResponseType() {
		return responseType;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( "Client Id: ").append( clientId )
		.append( ", Redirect_uri: ").append( redirectUri )
		.append( ", State: ").append( state != null ? state : "")
		.append(", Scope: ").append( scope != null ? scope :  "" )
		.append(", responseType: ").append( responseType );
		return sb.toString();
	}	
	
}
