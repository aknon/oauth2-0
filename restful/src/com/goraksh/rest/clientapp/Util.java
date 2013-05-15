package com.goraksh.rest.clientapp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.goraksh.rest.clientapp.map.ClientAuthParams;
import com.goraksh.rest.clientapp.request.GenericError;
import com.goraksh.rest.clientapp.request.RedirectionError;
import com.goraksh.rest.clientapp.request.RedirectionRequest;

public class Util {
	
	public static String constructBaseUri( HttpServletRequest request ) {
		String scheme = request.getScheme();
		String ipaddress = request.getServerName();//InetAddress.getLocalHost().getHostAddress();
		String port = String.valueOf(request.getServerPort() );//"8080";
		String contextPath = request.getContextPath();
		StringBuilder sb = new StringBuilder();
		sb.append( scheme ).append("://")
		.append(ipaddress)
		.append(":").append(port)
		.append(contextPath);
		return sb.toString();
	}
	
	public static RedirectionRequest extractRedirectionParams(HttpServletRequest request ) {
		
		String state = request.getParameter("state");
		String error = request.getParameter("error");
		if ( error != null ) {
			String errorMessage = request.getParameter("error_description");
			String errorUri  = request.getParameter("error_uri");
			return new RedirectionRequest( new RedirectionError( error, errorMessage, errorUri , state) );
		}
		
		String code = request.getParameter("code");	
		return new RedirectionRequest(code, state);
	}
	

	public static String getAuthorisationGrantAuthEndPoint(HttpServletRequest request, ClientAuthParams auth) {
		StringBuilder sb = new StringBuilder(Util.constructBaseUri(request) + ClientConstants.AUTHORISATION_END_POINT);

		sb.append("?response_type=code").append("&client_id=")
				.append(auth.getClientId()).append("&scope=").append(auth.getScope())
				.append("&state=").append(auth.getState());

		sb.append("&redirect_uri=").append(URLEncoder.encode(auth.getRedirectUri()));
		return sb.toString();
	}
	
	public static String getImplicitGrantAuthEndPoint(HttpServletRequest request, ClientAuthParams auth) {
		StringBuilder sb = new StringBuilder(Util.constructBaseUri(request) + ClientConstants.AUTHORISATION_END_POINT);

		sb.append("?response_type=token").append("&client_id=")
				.append(auth.getClientId()).append("&scope=").append(auth.getScope())
				.append("&state=").append(auth.getState());

		sb.append("&redirect_uri=").append(URLEncoder.encode(auth.getRedirectUri()));
		return sb.toString();
	}
	
	public static String decode( String toDecode, String enc ) {
		try {
			return URLDecoder.decode(toDecode, enc );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println( "UnsupportedEncodingException while decoding: " + toDecode + " in UTF-8" );
			e.printStackTrace();
			return null;
		}
	}
	
	}
