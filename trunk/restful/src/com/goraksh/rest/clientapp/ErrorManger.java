package com.goraksh.rest.clientapp;


/**
 * 
 * @author niteshk
 *
 */
public class ErrorManger {
	
	private static long EXPIRY_IN_SECS = 10l;
	
	public static void cancelToken( AuthParams params ) {
		params.cancel();
	}
	
	public static void addAccessExpiredErrorCode( AuthParams params ) {
		params.setErrorcode( 303 );
		params.setErrorMessage("Access Token :" + params.getAccessToken() + " Expired !!" + " .Expired In Secs :" + EXPIRY_IN_SECS);
	}
	
	public static void addInvalidAccessTokenErrorCode( AuthParams params ) {
		params.setErrorcode( 304 );
		params.setErrorMessage("Access Token :" + params.getAccessToken() + " Invalid !!" );
	}
	
	public static void addInvalidUsernameOrPasswordErrorCode( AuthParams params ) {
		params.setErrorcode( 301 );
		params.setErrorMessage("Invalid Username or Password");
	}
	
	public static boolean isAccessTokenExpiry( String sessionId, AuthParams params ) {
		if ( !hasAccessTokenIssed( params))
			return false;
		
		if ( params.isCancelled() )
			return true;
		
		boolean expired = (System.currentTimeMillis() - params.getIssueTime()) > ( EXPIRY_IN_SECS * 1000 );
		if ( expired )
			System.out.println("EXPIRED. SessionId: " + sessionId + " ,client_id :" + params.getClientId() + " ,access_token :" + params.getAccessToken());
		return expired;
	}
	
	public static boolean hasError(AuthParams params ) {
		return params.isCancelled() || ( params.getErrorcode() > 0 );
	}
	
	private static boolean hasAccessTokenIssed( AuthParams params ) {
		return params.getAccessToken() != null;
	}
	

}
