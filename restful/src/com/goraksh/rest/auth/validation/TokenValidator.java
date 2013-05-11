package com.goraksh.rest.auth.validation;

import com.goraksh.rest.auth.AuthorisationErrorHandler;
import com.goraksh.rest.auth.map.AuthorisationCodeTable;
import com.goraksh.rest.auth.map.ClientIdCodeTable;
import com.goraksh.rest.auth.map.ClientRegistrationTable;
import com.goraksh.rest.auth.request.AuthorisationCodeTableAttribute;
import com.goraksh.rest.auth.request.ClientRegister;
import com.goraksh.rest.auth.request.TokenError;
import com.goraksh.rest.auth.request.TokenRequest;

public class TokenValidator extends Validator {

	private TokenRequest params;
	private TokenError error;

	public TokenValidator(TokenRequest params) {
		this.params = params;
		error = new TokenError();
	}

	public TokenError validate() {
		if (!validateClient())
			return this.error;

		if (!validateRedirectUri())
			return this.error;

		validateCode();

		System.out.println("Successful Token Reques Validation. Has Error ? "
				+ this.error.hasError());

		return this.error;
	}

	private boolean validateRedirectUri() {
		boolean valid = validateRedirectUri(params.getRedirectUri());
		if (valid)
			validateRedirectUriAsRegistered(params.getClientId(),
					params.getRedirectUri());

		System.out
				.println("Over with Redirect validation. Is Valid ? " + valid);
		return valid;
	}

	private boolean validateGrantType() {
		return validateGrantTypeCode(params.getGrantType());
	}

	private boolean validateClient() {
		boolean valid = validateClientAsRegistered(params.getClientId());

		// if registered then check authenticate, if a client_key was issued
		if (valid)
			valid = validateIfClientWasIssuedAnyCode();
		if (valid)
			valid = authenticateClient();
		return valid;
	}

	private boolean validateIfClientWasIssuedAnyCode() {
		String issuedCode = ClientIdCodeTable.getInstance().get(
				params.getClientId());
		boolean valid = issuedCode != null;
		System.out.println("Was this ClientId: " + params.getClientId()
				+ " issued any code at all ? " + valid);

		if (!valid)
			AuthorisationErrorHandler.addInvalidClientErrorCode(
					params.getClientId(), this.error);
		return valid;
	}

	/**
	 * Precondition : must be 1..) Valid registered client 2.) authenticated
	 * client 3) an code has been issued to this client, some time in past.
	 * Cannot be really authenticated if this particular 'code' was issued to
	 * this client 4.) have right grant type
	 * 
	 * must check: 1.) check if this code is present in table 2.) check if this
	 * code is valid. possible this code was invalidated or cancelled for some
	 * reason 3.) match the client_id fetched from table ( for this input code )
	 * with the client_id in the input request
	 * 
	 * 
	 * @return
	 */
	private boolean validateCode() {
		AuthorisationCodeTableAttribute codeAttributes = AuthorisationCodeTable
				.getInstance().get(params.getCode());
		boolean valid = codeAttributes != null;

		if (valid) {
			valid = codeAttributes.isCodeValid();
			System.out.println(valid == false ? "Invalid Code: "
					+ params.getCode() + " .Cannot authenticate Client" : "");
		}

		if (valid)
			valid = codeAttributes.getAuthRequest().getClientId()
					.equals(params.getClientId());

		if (!valid)
			AuthorisationErrorHandler.addInvalidGrantErrorCode(
					params.getCode(), this.error);
		System.out.println("Validating Authorisation Code. Valid ? " + valid);
		return valid;
	}

	/**
	 * precondition: 1.) must be a registered client 2.) must have been issued
	 * atleast one token ( valid or invalid )
	 * 
	 * @return
	 */
	private boolean authenticateClient() {
		String clientId = params.getClientId();

		ClientRegister register = ClientRegistrationTable.getInstance().get(
				clientId);
		boolean valid = true;
		if (register.getClientKey() != null)
			valid = register.getClientKey().equals(params.getClientKey());

		if (!valid)
			AuthorisationErrorHandler.addUnAuthenticatedClientErrorCode(
					clientId, this.error);

		System.out.println("Authenticating Client. Valid ? " + valid);
		return valid;
	}
}
