package com.goraksh.rest.clientapp;

import javax.servlet.http.HttpServletRequest;

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

}
