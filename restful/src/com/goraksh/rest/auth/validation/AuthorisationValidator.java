package com.goraksh.rest.auth.validation;

import com.goraksh.rest.auth.AuthorisationErrorHandler;
import com.goraksh.rest.auth.map.UserCredentials;
import com.goraksh.rest.auth.map.UserLoginTable;
import com.goraksh.rest.auth.request.AuthorisationError;
import com.goraksh.rest.auth.request.AuthorisationRequest;

public class AuthorisationValidator  extends Validator {

	private AuthorisationRequest params;
	//private AuthorisationError error;

	public AuthorisationValidator(AuthorisationRequest params) {
		super();
		this.params = params;
		//error = new AuthorisationError();
	}

	public AuthorisationError validateAuthorisationGrantRequest() {
		if (!validateClient())
			return this.error;
		if (!validateAuthorisationGrantResponseType())
			return this.error;
		if (!validateRedirectUri())
			return this.error;
		return this.error;
	}
	
	public AuthorisationError validateImplicitGrantAuthorisationRequest() {
		if (!validateClient())
			return this.error;
		if (!validateImplicitGrantResponseType())
			return this.error;
		if (!validateRedirectUri())
			return this.error;
		return this.error;
	}
	
	public AuthorisationError validateUserAuthentication(UserCredentials loginInfo, String tmpRequestId, boolean accessAllowed ) {
		if( accessAllowed )
			return validateLoginCredentials(loginInfo, tmpRequestId);
		return processAccessDenied();		
	}

	private boolean validateRedirectUri() {
		boolean valid = validateRedirectUri(params.getRedirectUri());
		if ( valid )
			validateRedirectUriAsRegistered(params.getClientId(),  params.getRedirectUri() );
		return valid;
	}
	
	private AuthorisationError processAccessDenied() {
			AuthorisationErrorHandler.addAccessDeniedByUser(error);
			return this.error;
		}

	private boolean validateAuthorisationGrantResponseType() {
		boolean  valid = validateResponseTypeCode(params.getResponseType() );
		System.out.println("Validating Response Type: " + params.getResponseType() + " . Is Valid ? " + valid );
		return valid;
	}
	
	private boolean validateImplicitGrantResponseType() {
		boolean  valid = validateResponseTypeToken( params.getResponseType() );
		System.out.println("Validating Response Type: " + params.getResponseType() + " . Is Valid ? " + valid );
		return valid;
	}

	private boolean validateClient() {
		System.out.println("Validation Client Id: " + params.getClientId() );
		return validateClientAsRegistered(params.getClientId() );
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param tmpRequestId
	 * @param authRequest
	 * @return
	 */
	private AuthorisationError validateLoginCredentials(UserCredentials loginInfo, String tmpRequestId) {

		boolean validLogin = UserLoginTable.getInstance().contains(loginInfo);
		
		if (!validLogin) {
			AuthorisationErrorHandler
					.addInvalidUsernameOrPasswordErrorCode(error);
			System.out.println("Login Failure for Temp RequesstId :"
					+ tmpRequestId + " , and state: " + this.params.getState()
					+ " . For Username :" + loginInfo.getUsername()
					+ " .Enter Test username :" + "nk");
		}
		return this.error;
	}
}
