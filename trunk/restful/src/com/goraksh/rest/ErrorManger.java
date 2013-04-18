package com.goraksh.rest;

public class ErrorManger {
	
	private static long EXPIRY_IN_SECS = 10l;
	
	public static void addAccessExpiredErrorCode( AuthParams params ) {
		params.setErrorcode( 303 );
		params.setErrorMessage("Access Token :" + params.getAccessToken() + " Expired !!" + " .Expired In Secs :" + EXPIRY_IN_SECS);
	}
	
	public static void addInvalidClientErrorCode( AuthParams params ) {
		params.setErrorcode( 305 );
		params.setErrorMessage("Client Id :" + params.getClientId() + " Invalid !!" );
	}
	
	public static void addInvalidAccessTokenErrorCode( AuthParams params ) {
		params.setErrorcode( 304 );
		params.setErrorMessage("Access Token :" + params.getAccessToken() + " Invalid !!" );
	}
	
	public static boolean isAccessTokenExpiry( String sessionId, AuthParams params ) {
		boolean expired = (System.currentTimeMillis() - params.getIssueTime()) > ( EXPIRY_IN_SECS * 1000 );
		if ( expired )
			System.out.println("EXPIRED. SessionId: " + sessionId + " ,client_id :" + params.getClientId() + " ,access_token :" + params.getAccessToken());
		return expired;
	}
	

}
