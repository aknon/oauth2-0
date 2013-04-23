package com.goraksh.rest;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorisationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473961001930308879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String requestId= request.getParameter("request_id");
		AuthParams auth = Store.get(requestId);
		
	System.out.println( "Into Authorisation Server. This request brings in a redirect_uri_new from the AuthorisationEndPoint via the OAuth Login page. Request Id :" + requestId );
	Map<String, String[]> requestParamsMap = (Map<String, String[]>) SessionMap.getInstance().get(requestId);
	
	String accessAllowed  = requestParamsMap.get("access_allowed")[0];
	if ( "false".equals(accessAllowed)) {
		requestParamsMap.put("error", new String[] { String.valueOf(310) } );
		requestParamsMap.put( "error_description", new String[] { "Access to the Requested Resource Denied By User" } );
		
		auth.setErrorcode( 310 );
		auth.setErrorMessage( "Access to the Requested Resource Denied By User" );
		ErrorManger.cancelToken(auth);
		
		// THis is not an Error Condition. As this must be propagated to the ClientApp Servlet
		/**
		 * Only the Ones that need to be directly shown to the User, shoould be handled by Authorisation Server as Error.
		 * So Redirection to Error only 
		 * a.)  Invalid login credentials
		 * b.) Invalid Redirect Uri
		 * 
		 * for others :
		 * a.) Access Denied
		 * b.) Request params validation
		 * 
		 * Just send the right Auth object
		 */
	}
			
	String redirectUri = appendToRedirectUri(requestParamsMap) ;
		requestParamsMap.put("redirect_uri_new", new String[] { redirectUri } );
		
		request.setAttribute("redirect_uri",  redirectUri);
		
		// This Jsp has Scripts for Redirecting. Prior to Redirecting it saves Url hash Fragment
		getServletContext().getRequestDispatcher("/authjsp").forward(request, response);  
		
		
	}
	
	/**
	 * All attributes must be added and not just the below ones.
	 * 
	 * Some attributes can be given by the user, that are not a part of Oauth
	 * specs. They must be added too !!
	 * 
	 * @param requestParamsMap
	 * @return
	 */
	private String appendToRedirectUri(Map<String, String[]> requestParamsMap) {

		StringBuilder sb = new StringBuilder(
				requestParamsMap.get("redirect_uri")[0]);

		String client_id = requestParamsMap.get("client_id")[0];
		String access_token = requestParamsMap.get("access_token")[0];
		String response_type = requestParamsMap.get("response_type")[0];
		String state = requestParamsMap.get("state") != null ? requestParamsMap
				.get("state")[0] : null;
		String scope = requestParamsMap.get("scope") != null ? requestParamsMap
				.get("scope")[0] : null;

		String error = requestParamsMap.get("error") != null ? requestParamsMap
				.get("error")[0] : null;
		String error_description = requestParamsMap.get("error_description") != null ? requestParamsMap
				.get("error_description")[0] : null;
		String error_uri = requestParamsMap.get("error_uri") != null ? requestParamsMap
				.get("error_uri")[0] : null;

		sb.append("?");
		append("client_id", client_id, sb);

		if (state != null)
			append("&state", state, sb);

		if (scope != null)
			append("&scope", scope, sb);

		if (error != null)
			append("&error", error, sb);

		if (error_description != null)
			append("&error_description", error_description, sb);

		if (error_uri != null)
			append("&error_uri", error_uri, sb);

		if (access_token != null && response_type.equals("code"))
			append("&access_token", access_token, sb);
		else if (access_token != null && response_type.equals("token"))
			append("#access_token", access_token, sb);
		append("&response_type", response_type, sb);

		String redirect_url = sb.toString();
		System.out.println("New Redirect URi constructed : " + redirect_url);
		return redirect_url;

	}
	private StringBuilder append(String key, String value, StringBuilder sb) {
		sb.append(key).append("=").append(URLEncoder.encode(value));
		return sb;
	}
	
	private boolean loginRequest( HttpServletRequest request, HttpServletResponse response ) {
		return "login".equals(request.getParameter("login") );
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
	
	private void handleError(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
	
		System.out.println("Authorisation Servlet forwarding to /loginfailedjsp");
		request.getRequestDispatcher("/loginfailedjsp").forward(request,  response);
		
	}
	
	private boolean loginFailed(HttpServletRequest request,
			HttpServletResponse response) {
		boolean loginFailed =  request.getAttribute("error_message")!= null;
		if ( loginFailed )
			System.out.println("Authorisation Server verified that login has filed at Resource server");
		return loginFailed;
	}
}
