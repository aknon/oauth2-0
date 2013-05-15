package com.goraksh.rest.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.goraksh.rest.auth.request.AuthorisationError;
import com.goraksh.rest.auth.request.AuthorisationRequest;
import com.goraksh.rest.auth.request.AuthorisationResponse;
import com.goraksh.rest.auth.request.TokenResponse;

public class RedirectUriBuilder {

	private String baseUri;

	public RedirectUriBuilder(String baseUri) {
		this.baseUri = baseUri;
	}

	public String buildErrorUriAuthorisationGrant(AuthorisationError authError,
			String state) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder(baseUri);
		String error = authError.getErrorcode();
		String error_description = authError.getErrorMessage();
		String error_uri = null;

		sb.append("?");
		append("error", error, sb);

		if (error_description != null)
			append("&error_description", error_description, sb);

		if (error_uri != null)
			append("&error_uri", error_uri, sb);

		if (state != null)
			append("&state", state, sb);
		return sb.toString();
	}

	public String buildErrorUriImplicitGrant(AuthorisationError authError,
			String state) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder(baseUri);
		sb.append("?");
		append( "state", state, sb );

		String error = authError.getErrorcode();
		String error_description = authError.getErrorMessage();
		String error_uri = null;

		sb.append("#");
		append("error", error, sb);

		if (error_description != null)
			append("&error_description", error_description, sb);

		if (error_uri != null)
			append("&error_uri", error_uri, sb);

		if (state != null)
			append("&state", state, sb);

		String redirect_uri = sb.toString();
		System.out.println("Redirect URI build by Redirection Builder: "
				+ redirect_uri);
		return redirect_uri;
	}

	private StringBuilder append(String key, String value, StringBuilder sb) throws UnsupportedEncodingException {
		sb.append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
		return sb;
	}

	public String buildRedirectUriForAuthorisationGrant(
			AuthorisationResponse authResponse) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder(baseUri);
		String code = authResponse.getCode();
		String state = authResponse.getState();

		sb.append("?");
		append("code", code, sb);
		if (state != null)
			append("&state", state, sb);
		return sb.toString();
	}

	public String buildRedirectUriForImplicitGrant(
			AuthorisationRequest authRequest,
			TokenResponse implicitTokenResponse) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder(baseUri);

		sb.append("?");
		append( "state", authRequest.getState(), sb);

		String access_token = implicitTokenResponse.getAccessToken();
		String scope = implicitTokenResponse.getScope();
		String state = authRequest.getState();
		String expiresIn = String.valueOf(implicitTokenResponse.getExpiresIn());

		sb.append("#");
		append("access_token", access_token, sb);
		append("&token_type", implicitTokenResponse.getTokenType(), sb);

		if (state != null)
			append("&state", state, sb);
		if (scope != null)
			append("&scope", scope, sb);
		if (expiresIn != null)
			append("&expires_in", expiresIn, sb);

		return sb.toString();
	}
}
