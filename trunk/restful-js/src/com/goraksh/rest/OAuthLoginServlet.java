package com.goraksh.rest;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author niteshk
 *
 */
public class OAuthLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473961001930308879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String requestId = request.getParameter("request_id");
		System.out.println("Into OAuth Login Servlet. Processing Request-Id : " + requestId);
		
		Map<String, String[]> requestParams = (Map<String, String[]>) SessionMap.getInstance().get( requestId );
		if ( requestParams == null ) throw new NullPointerException( "Request Params map cannot be NULL !!!" );
		
		AuthParams auth = Store.get(requestId);
		
		String access_allowed = request.getParameter("access_allowed");
		if ( "true".equals(access_allowed))
			auth = validateLoginCredentials( request.getParameter("user_name"), request.getParameter("password"), auth, request, requestId);
		
		requestParams.put( "access_allowed",  new String[] {access_allowed });
		
		boolean hasError = auth.getErrorcode() > 0;
		if ( hasError ) {
		requestParams.put( "oauth_login_error_code",  new String[] { String.valueOf( auth.getErrorcode() ) });
		requestParams.put( "oauth_login_error_message", new String[] { auth.getErrorMessage() });
		}
			
	
		//request.setAttribute("login", "logged_in");

		System.out.println("Login Successfull. Forwarding it to Authorisation Servlet for redirection");
		if ( hasError )
			handleUserError( auth, request, response );
		else {
			refreshIssueTime( auth );
		getServletContext().getRequestDispatcher("/authorise").forward(request, response);
		}
	}
	
	/**
	 * As login is successful, so this becomes it latest issue time
	 * 
	 * @param auth
	 */
	private void refreshIssueTime( AuthParams auth ) {
		auth.refreshIssueTime();
	}
	
	private void handleUserError( AuthParams auth, HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		System.out.println("Handling Error in OAuthLogin Servlet. Error Message: " + auth.getErrorMessage());
		request.setAttribute("error_message",  auth.getErrorMessage() );
		//getServletContext().getRequestDispatcher("/errorapp").forward(request , response );
		response.getWriter().print( "error="+auth.getErrorcode()+"&error_description="+auth.getErrorMessage());
		ErrorManger.refreshError(auth);
	}
	
	private AuthParams validateLoginCredentials( String username, String password, AuthParams auth, HttpServletRequest request, String requestId ) {
		
		if ( !"nk".equals(username) || !"nk".equals(password) ) {
			//request.setAttribute("oauth_login_error_code",  String.valueOf( 301));
			//request.setAttribute( "oauth_login_error_message", "Invalid Username or Password");
			
			ErrorManger.addInvalidUsernameOrPasswordErrorCode(auth);
			ErrorManger.cancelToken(auth);
			System.out.println("Loign Failure for RequesstId :" + requestId + " . For Username :" + username + " .Enter Test username :" + "nk");
			
		}
		return auth;
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
}
