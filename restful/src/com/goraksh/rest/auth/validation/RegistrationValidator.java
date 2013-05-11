package com.goraksh.rest.auth.validation;

import com.goraksh.rest.auth.request.RegistrationError;

public class RegistrationValidator {

	private String appName;
	private String redirectUri;
	private String homeUri;

	public RegistrationValidator(String appName, String redirectUri,
			String homeUri) {
		this.appName = appName;
		this.redirectUri = redirectUri;
		this.homeUri = homeUri;
	}

	public RegistrationError validate() {

		if (appName == null || "".equals(appName)) {
			return error("invalid_app_name", "Invalid App Name");
		}

		if (!validateUri(homeUri, false)) {
			return error("invalid_home_uri", "Invalid Home Uri: " + homeUri);
		}

		if (!validateUri(redirectUri, true)) {
			return error("invalid_redirect_uri", "Invalid Redirect Uri");
		}
		return new RegistrationError();
	}

	private boolean validateUri(String uri, boolean checkhash) {
		System.out.println("Validating redirect_uri : " + uri);
		if (uri == null || "".trim().equals(uri) || (!uri.startsWith("http")))
			return false;
		if (checkhash) {
			if (uri.contains("#"))
				return false;
		}
		return true;
	}

	private RegistrationError error(String code, String msg) {
		RegistrationError error = new RegistrationError();
		error.setHasError(true);
		error.setErrorcode(code);
		error.setErrorMessage(msg);
		return error;
	}

}
