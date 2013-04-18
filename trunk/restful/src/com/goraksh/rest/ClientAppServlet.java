package com.goraksh.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientAppServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2218291982614286050L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out
				.println("Into client app servlet. This session must match already stored servlet :"
						+ request.getSession().getId());
		
		AuthParams auth = Store.update(request.getSession().getId());
		request.setAttribute("access_token", auth.getAccessToken() );
		
		if ( hasError( auth )) {
			handleError(request, response, auth);
			return;
		}
		
		boolean match = false;
		match = validateClient(request.getParameter("client_id"), auth.getClientId(), auth);
		System.out.println("client_id in Request :"
				+ request.getParameter("client_id")
				+ " :: Client Id in store :" + auth.getClientId());

		if (match) {
			match = validateAccessToken(request.getParameter("access_token"),
					auth.getAccessToken(), auth);
			System.out.println("Access Token in Request :"
					+ request.getParameter("access_token")
					+ " :: Access Token in store :" + auth.getAccessToken());
		}

		if (match) {
				request.getRequestDispatcher("/clientappview").forward(request,
					response);
		}
		else
			handleError(request, response, auth);
	}

	private boolean match(String reqId, String storedId) {
		return storedId.equals(reqId);
	}
	
	/*
	 * 
	 */
	private boolean validateClient(String reqId, String storedId, AuthParams params) {
		boolean  valid = match(reqId, storedId);
		if ( !valid)
			ErrorManger.addInvalidClientErrorCode(params);
		return valid;
	}
	
	private boolean validateAccessToken(String reqId, String storedId, AuthParams params) {
		boolean  valid = match(reqId, storedId);
		if ( !valid)
			ErrorManger.addInvalidAccessTokenErrorCode(params);
		return valid;
	}
	
	
	private boolean hasError( AuthParams params ) {
		return params.getErrorcode() > 0;
	}

	private void handleError(HttpServletRequest request,
			HttpServletResponse response, AuthParams params) throws IOException, ServletException {
		
		request.setAttribute("error_code",  String.valueOf(params.getErrorcode()));
		request.setAttribute( "error_message", params.getErrorMessage() );
		request.getRequestDispatcher("/errorapp").forward(request,  response);
		
	}

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
