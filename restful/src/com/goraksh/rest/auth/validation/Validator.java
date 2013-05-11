package com.goraksh.rest.auth.validation;

import java.net.MalformedURLException;

import com.goraksh.rest.auth.AuthorisationErrorHandler;
import com.goraksh.rest.auth.request.AuthorisationError;

public class Validator {

	protected AuthorisationError error;

	public Validator(AuthorisationError error) {
		this.error = error;
	}

	public Validator() {
		this.error = new AuthorisationError();
	}

	protected boolean validateRedirectUri(String redirectUri) {
		boolean valid = ValidationLib.validateUri(redirectUri);
	
		if (!valid)
			AuthorisationErrorHandler.addInvalidRedirectErrorCode(redirectUri,
					this.error);
		return valid;
	}

	protected boolean validateResponseTypeCode(String responseType) {
		boolean valid = ValidationLib.validateResponseTypeCode(responseType);
		if (!valid)
			AuthorisationErrorHandler.addInvalidResponseTypeErrorCode(
					responseType, this.error);
		return valid;
	}
	
	protected boolean validateGrantTypeCode(String grantType) {
		boolean valid = ValidationLib.validateGrantTypeCode(grantType);
		if (!valid)
			AuthorisationErrorHandler.addInvalidTokenGrantTypeErrorCode(grantType,  this.error );
		return valid;
	}

	protected boolean validateClientAsRegistered(String clientId) {
		boolean valid = ValidationLib.validateClientAsRegistered(clientId);
		if (!valid)
			AuthorisationErrorHandler.addInvalidClientErrorCode(clientId,
					this.error);
		return valid;
	}

	protected boolean validateRedirectUriAsRegistered(String clientId,
			String redirectUri) {
		try {
			boolean valid = ValidationLib.validateRedirectUriAsRegistered(
					clientId, redirectUri);
			if (!valid)
				AuthorisationErrorHandler.addUnregisteredRedirectErrorCode(
						redirectUri, this.error);
			return valid;
		} catch (MalformedURLException e) {
			System.out.println("Malformed exception");
			e.printStackTrace();
			AuthorisationErrorHandler.addInternalServerErrorCode(this.error);
			return false;
		}
	}
}
