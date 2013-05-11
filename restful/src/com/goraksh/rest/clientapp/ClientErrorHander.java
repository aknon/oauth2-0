package com.goraksh.rest.clientapp;

import com.goraksh.rest.clientapp.request.ClientTokenResponse;


/**
 * 
 * @author niteshk
 *
 */
public class ClientErrorHander {
	
	public static void cancelToken( AuthParams params ) {
		params.cancel();
	}
	
	public static void addAccessExpiredErrorCode( ClientTokenResponse tokenResponse ) {
		tokenResponse.setError("token_expired");
		tokenResponse.setError_description("Access Token :" + tokenResponse.getAccess_token() +  " Expired !!" + " .Expired In Secs :" + tokenResponse.getExpires_in() );
	}
	
	public static void addInvalidAccessTokenErrorCode( AuthParams params ) {
		params.setErrorcode( 304 );
		params.setErrorMessage("Access Token :" + params.getAccessToken() + " Invalid !!" );
	}
	
	public static void addInvalidUsernameOrPasswordErrorCode( AuthParams params ) {
		params.setErrorcode( 301 );
		params.setErrorMessage("Invalid Username or Password");
	}
	
	public static boolean hasError(AuthParams params ) {
		return params.isCancelled() || ( params.getErrorcode() > 0 );
	}
	
	private static boolean hasAccessTokenIssed( AuthParams params ) {
		return params.getAccessToken() != null;
	}
	

}
