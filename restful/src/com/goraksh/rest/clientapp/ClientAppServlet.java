package com.goraksh.rest.clientapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goraksh.rest.auth.map.TokenTable;
import com.goraksh.rest.clientapp.map.ClientGrantTable;
import com.goraksh.rest.clientapp.request.ClientTokenResponse;

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

		System.out
				.println("Into client app servlet. Will convert the JsonString into ClientTokenResponse.class object");

		String state = (String) request.getAttribute("state"); 
		System.out
				.println("Into Client App Servlet. Processing for User state: "
						+ state);
		ClientTokenResponse tokenResponse = ClientGrantTable.getInstance()
				.get(state).getToken();

		if (hasError(tokenResponse)) {
			handleError(request, response, tokenResponse);
			return;
		}

		boolean match = true;

		setAttributes(request, tokenResponse, state);

		if (match) {
			forwardToClientAppView(request, response);

		} else {
			handleError(request, response, tokenResponse);
		}
	}

	/**
	 * 
	 * @param request
	 * @param tokenResponse
	 */
	private void setAttributes(HttpServletRequest request,
			ClientTokenResponse tokenResponse, String state) {

		System.out
				.println("Setting Attributes before forwading it to the Clientappview/errorapp. Scope: "
						+ tokenResponse.getError()
						+ " ,Access_token: "
						+ tokenResponse.getAccess_token() + " ,State: " + state);
		request.setAttribute("scope", tokenResponse.getScope());
		request.setAttribute("access_token", tokenResponse.getAccess_token());
		request.setAttribute("state", state);
	}

	private void forwardToClientAppView(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/clientappview").forward(
				request, response);
		return;
	}

	/**
	 * 
	 * @param tokenResponse
	 * @return
	 */
	private boolean hasError(ClientTokenResponse tokenResponse) {
		boolean hasError = tokenResponse.getError() != null;
		System.out.println("Checking for Error in token Resposne. Has Error ? "
				+ hasError);
		return hasError;
	}

	private void handleError(HttpServletRequest request,
			HttpServletResponse response, ClientTokenResponse tokenResponse)
			throws IOException, ServletException {

		System.out
				.println("Into Client App Servlet. Token Response contains error Will forward it to /errorapp. Error: "
						+ tokenResponse.getError());
		request.setAttribute("error", tokenResponse.getError());
		request.setAttribute("error_description",
				tokenResponse.getError_description());
		request.getRequestDispatcher("/errorapp").forward(request, response);

	}

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out
				.println("Into client app servlet: POST. Will validate the Access token expiry");

		String state = (String) request.getParameter("state"); // this is parameter and NOT  getAttribute !!
		System.out
				.println("Into Client App Servlet POST. Processing for User state: "
						+ state);
		ClientTokenResponse tokenResponse = ClientGrantTable.getInstance()
				.get(state).getToken();
		
		valiateClientAppRequest( tokenResponse );
		request.setAttribute("state", state );
		doGet(request, response);
	}
	
	/**
	 * 
	 * @param tokenResponse
	 */
	private void valiateClientAppRequest( ClientTokenResponse tokenResponse ) {
		// must access the Authorisation server validation end point
		boolean invalid = TokenTable.getInstance().get(tokenResponse.getAccess_token()).isExpired();
		if ( invalid )
			ClientErrorHander.addAccessExpiredErrorCode(tokenResponse);
	}
}