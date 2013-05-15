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
		System.out.println("Error mesasge: " + tokenResponse.getError_description());
	}
	
	public static void addInternalServerErrorCode( ClientTokenResponse tokenResponse ) {
		tokenResponse.setError("server_error");
		tokenResponse.setError_description("Requested  Token not foudn in our database:" + tokenResponse.getAccess_token()  );
		System.out.println("Error mesasge: " + tokenResponse.getError_description());
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
	

}
