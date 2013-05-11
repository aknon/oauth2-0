package com.goraksh.rest.clientapp.request;

public class ClientTokenResponse {
	
	private String access_token;
	private String token_type;
	private String expires_in;
	private String scope;
	private String refresh_token;
	private String error;
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}
	private String error_description;
	
	public String toString() {
		StringBuilder sb = new StringBuilder( "{");
		
		if ( access_token != null )
		sb.append("access_token:").append("\"").append( access_token).append("\"");
		
		if ( error != null)
			sb.append("error:").append("\"").append( error).append("\"");
		
		if ( token_type != null )
		sb.append(",token_type:").append("\"").append( token_type).append("\"");
		
		if ( expires_in != null )
		sb.append(",expires_in:").append("\"").append( expires_in).append("\"");
		
		if ( scope != null )
		sb.append(",scope:").append("\"").append( scope).append("\"");
		
		if ( refresh_token != null)
		sb.append(",refresh_token:").append("\"").append( refresh_token).append("\"");
		
	
		if ( error_description != null)
			sb.append(",error_description:").append("\"").append( error_description).append("\"");
		
		sb.append("}");
		
		return sb.toString();
	}
	
	
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

}

