package com.goraksh.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorisationServlet_1 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473961001930308879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
	if ( loginFailed(request, response)) {
			handleError(request, response);
		return;
		}
		
		String location = "http://restful-fundamentals.blogspot.in/2013/04/oauth-20-authorisation-grant-different.html#access_token=aaga";
		//response.setHeader("Location",   "http://restful-fundamentals.blogspot.in/2013/04/oauth-20-authorisation-grant-different.html#http");
		//response.setHeader("Location",   "/restful/hellojsp#access_token=aagaga");
		//response.setHeader("Location",   "http://restful-fundamentals.blogspot.in/2013/04/oauth-20-authorisation-grant-different.html#access_token=aaga");
		
		//response.setStatus(302);

		System.out.println( "Saving session for first time, Authorisation :" + request.getSession().getId());
		System.out.println("Host name:" + request.getServerName());
		AuthParams auth = Store.createWithAccessToken( request.getSession().getId() );
		String redirectUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()  + "/restful/redirect?client_id="+auth.getClientId()+"&state="+auth.getState()+"#access_token="+auth.getAccessToken();	
		
		System.out.println("Redirect URl : " + redirectUrl);
		request.setAttribute("redirect_url",  redirectUrl);

		getServletContext().getRequestDispatcher("/authjsp").forward(request, response);  
		
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
