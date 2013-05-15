package com.goraksh.rest.clientapp;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goraksh.rest.auth.map.ClientRegistrationTable;
import com.goraksh.rest.clientapp.map.ClientAuthHandler;
import com.goraksh.rest.clientapp.map.ClientAuthParams;

/**
 * 
 * @author niteshk
 * 
 */
public class OAuthStartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473961001930308879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String sessionId = request.getSession().getId();
		System.out
				.println("Into OAuth App Start Servlet. Will Create Authoration End Point for Session Id :"
						+ sessionId);
		System.out.println("Host name:" + request.getServerName());

		/* This is shared by both Client and Authorisation server */
		ClientAuthParams auth = generateParams( request );
		auth = ClientAuthHandler.getInstance().generateKeyandSave(auth);

		System.out.println("Created Auth Params without Access Tokens: "
				+ auth.toString());

		String authCodeEndPoint = Util.getAuthorisationGrantAuthEndPoint(request, auth);
		String implicitGrantEndPoint = Util.getImplicitGrantAuthEndPoint(request, auth);

		System.out
				.println("Starting MyApp. Setting Authorisation End Point as : "
						+ authCodeEndPoint + " , ImplictAuthEndPoint: " + implicitGrantEndPoint);

		request.setAttribute("authorisation_uri", authCodeEndPoint+ "&type=authorisation");
		request.setAttribute("authorisation_uri_implicit", implicitGrantEndPoint + "&type=implicit");
		request.setAttribute("scope", auth.getScope());

		getServletContext().getRequestDispatcher("/games").forward(request,
				response);

	}
	
	private ClientAuthParams generateParams(HttpServletRequest request ) {
		String redirectUri =  getRedirectEndPoint(request);		
		String scope =  ClientConstants.DEFAULT_SCOPE;
		String clientId = request.getParameter("client_id");
		String responseType = "code";
		String clientKey = ClientRegistrationTable.getInstance().get(clientId).getClientKey();
		
		return new ClientAuthParams(clientId, responseType, redirectUri, scope, clientKey);
	}

	private String getRedirectEndPoint(HttpServletRequest request) {
		 String preUri = Util.constructBaseUri(request);
		 String redirectUrl = preUri + ClientConstants.REDIRECTION_END_POINT_URI;
			return redirectUrl;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
