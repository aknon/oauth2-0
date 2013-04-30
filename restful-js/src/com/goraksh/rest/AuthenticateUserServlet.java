package com.goraksh.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author niteshk
 *
 */
public class AuthenticateUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473961001930308879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String username = request.getParameter("user_name");
		String password = request.getParameter("password");
		
		if ( !"nk".equals(username) || !"nk".equals(password) ) {
			request.setAttribute("error_code",  String.valueOf( 301));
			request.setAttribute( "error_message", "Invalid Username or Password");
			System.out.println("Loign Failure of Username :" + username + " .Enter Test username :" + "nk");
		}
		
		//request.setAttribute("login", "logged_in");

		System.out.println("Login Successfull. Forwarding it to Authorisation Servlet for redirection");
		getServletContext().getRequestDispatcher("/authorise").forward(request, response);  
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
}
