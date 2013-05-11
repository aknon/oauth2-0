package com.goraksh.rest.auth;

import java.net.URLEncoder;

import com.goraksh.rest.auth.request.AuthorisationError;
import com.goraksh.rest.auth.request.AuthorisationResponse;

public class RedirectUriBuilder {

	private String baseUri;
	
	public RedirectUriBuilder(String baseUri) {
		this.baseUri = baseUri;
	}
	
	public String buildErrorUri(AuthorisationError authError, String state) {
		StringBuilder sb = new StringBuilder( baseUri );
		String error = authError.getErrorcode();
		String error_description = authError.getErrorMessage();
		String error_uri = null;
		
		sb.append("?");
		append( "error", error, sb );

		if (error_description != null)
			append("&error_description", error_description, sb);

		if (error_uri != null)
			append("&error_uri", error_uri, sb);
		
		if ( state != null )
			append( "&state", state, sb);
		return sb.toString();
	}
	
	private StringBuilder append(String key, String value, StringBuilder sb) {
		sb.append(key).append("=").append(URLEncoder.encode(value));
		return sb;
	}
	
	public String buildRedirectUri(AuthorisationResponse authResponse ) {
		StringBuilder sb = new StringBuilder( baseUri );
		String code = authResponse.getCode();
			String state = authResponse.getState();
			
			sb.append("?");
			append("code", code, sb);
			if (state != null)
				append("&state", state, sb);	
			return sb.toString();
		}
}
