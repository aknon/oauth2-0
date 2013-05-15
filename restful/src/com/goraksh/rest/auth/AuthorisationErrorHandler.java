package com.goraksh.rest.auth;

import com.goraksh.rest.auth.request.AuthorisationError;

public class AuthorisationErrorHandler {
	
	private static long EXPIRY_IN_SECS = 10l;
	
	public static void addInvalidClientErrorCode( String clientId, AuthorisationError params ) {
		params.setHasError(true);
		params.setErrorcode( "unauthorized_client" );
		params.setErrorMessage("Client Id :" + clientId + " Invalid !!" );
	}
	
	public static void addUnAuthenticatedClientErrorCode( String clientId, AuthorisationError params ) {
		params.setHasError(true);
		params.setErrorcode( "invalid_client" ); // invalid_client refers to HTTP 401
		params.setErrorMessage("Client Id :" + clientId + " cannot be Authenticated against the Registered !!" );
	}
	
	public static void addInvalidResponseTypeErrorCode( String responseType, AuthorisationError error ) {
		error.setHasError(true);
		error.setErrorcode( "unsupported_response_type" );
		error.setErrorMessage("Response Type :" + responseType + " Invalid. Not supported !!" );
		System.out.println("Adding error: " + error.getErrorcode() + " for response Type: " + responseType);
	}
	
	public static void addInvalidTokenGrantTypeErrorCode( String grantType, AuthorisationError error ) {
		error.setHasError(true);
		error.setErrorcode( "unsupported_grant_type" );
		error.setErrorMessage("Grant Type :" + grantType + " Invalid. Not supported !!" );
	}
	
	public static void addInvalidGrantErrorCode( String code, AuthorisationError error ) {
		error.setHasError(true);
		error.setErrorcode( "invalid_grant" ); // wrong authorisation code
		error.setErrorMessage("Invalid Grant :" + code + " Invalid Authorisation Code!!" );
	}
	
	
	public static void addInvalidRedirectErrorCode( String redirectUri, AuthorisationError error ) {
		error.setHasError(true);
		error.setErrorType( AuthorisationError.ErrorType.userOriented );
		error.setErrorcode( "malformed_or_missing_redirect_uri" );
		error.setErrorMessage("Redirect URI :" + redirectUri + " Invalid !!" );
	}	
	
	public static void addUnregisteredRedirectErrorCode( String redirectUri, AuthorisationError error ) {
		error.setHasError(true);
		error.setErrorType( AuthorisationError.ErrorType.userOriented );
		error.setErrorcode( "unregistered_redirect_uri" );
		error.setErrorMessage("Redirect URI :" + redirectUri + " Not Registered !!" );
	}
	
	public static void addInternalServerErrorCode( AuthorisationError error ) {
		error.setHasError(true);
		error.setErrorcode( "server_error" );
		error.setErrorMessage("Unable to server the request. Trg again shortly" );
	}
	
	public static void addInvalidUsernameOrPasswordErrorCode( AuthorisationError error ) {
		error.setHasError( true );
		error.setErrorType( AuthorisationError.ErrorType.userOriented );
		error.setErrorcode( "invalid_login_credentials" );
		error.setErrorMessage("Invalid Username or Password");
	}
	
	public static void addAccessDeniedByUser( AuthorisationError error ) {
		error.setHasError( true );
		error.setErrorcode( "access_denied" );
		error.setErrorMessage("Access Denied by User");
	}

}
