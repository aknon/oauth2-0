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
public class ClientAppServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2218291982614286050L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String requestId = request.getParameter("state");
		System.out
				.println("Into client app servlet. This requestId must match already stored servlet :"
						+ requestId);
		
		
		AuthParams auth = Store.update( requestId );
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

		request.setAttribute("scope", auth.getScope());
		if (match) {
				request.getRequestDispatcher("/clientappview").forward(request,response);
			//response.getWriter().print("<br>hello into div");
		}
		else {
			ErrorManger.cancelToken(auth);
			handleError(request, response, auth);
		}
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
		return  ErrorManger.hasError(params);
	}

	private void handleError(HttpServletRequest request,
			HttpServletResponse response, AuthParams params) throws IOException, ServletException {
		
		System.out.println("Errro in Client App Servlet. Error message: " + params.getErrorMessage() );
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
