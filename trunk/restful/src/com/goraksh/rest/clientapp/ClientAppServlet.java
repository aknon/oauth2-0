package com.goraksh.rest.clientapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goraksh.rest.auth.AuthUtil.GrantType;
import com.goraksh.rest.auth.JsonUtil;
import com.goraksh.rest.auth.map.TokenTable;
import com.goraksh.rest.clientapp.map.ClientAuthHandler;
import com.goraksh.rest.clientapp.map.ClientAuthParams;
import com.goraksh.rest.clientapp.map.ClientGrantAttribute;
import com.goraksh.rest.clientapp.map.ClientGrantTable;
import com.goraksh.rest.clientapp.map.ClientRedirectRequestTable;
import com.goraksh.rest.clientapp.request.ClientTokenResponse;
import com.goraksh.rest.clientapp.request.RedirectionRequest;

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

		String state = (String) request.getParameter("state");
		System.out
				.println("Into Client App Servlet. Processing for User state: "
						+ state);
	
		ClientAuthParams param = ClientAuthHandler.getInstance().get(state);
		ClientTokenResponse tokenResponse = processForAccessTokens(request, response, state);
		processApp(request, response, tokenResponse, state, param);
	}
	
	public ClientTokenResponse processForAccessTokens(HttpServletRequest request, HttpServletResponse response, String state) throws IOException, ServletException {

			RedirectionRequest redirectRequest = ClientRedirectRequestTable.getInstance().get(state);		
				
		System.out.println("Into Client RedirectEndPoint. Processing for Tokens for state: " + state );
		ClientAuthParams authParams = ClientAuthHandler.getInstance().get(redirectRequest.getState());
		
		String tokenEndPointUrl =  Util.constructBaseUri(request)  + ClientConstants.TOKEN_END_POINT ;
		
	
		String urlEncodedTokenRequest = constructTokenPostRequest( redirectRequest, authParams );
		// POST to Token End Point and Get access_Tokens as TOkenResponse
		HttpResponse tokenHttpResponse = postToTokenEndPoint(tokenEndPointUrl, urlEncodedTokenRequest);
		
		ClientTokenResponse clientTokenResponse = handleTokenResponse(request, response, tokenHttpResponse );
		saveGrantToTable( authParams, redirectRequest, clientTokenResponse);
		//setAttributesAndForwardToClientApp(request, response, authParams.getState() );
		return clientTokenResponse;
	}
	
	private HttpResponse postToTokenEndPoint( String url, String urlEncodedPostParams ) throws IOException {
		HttpClient client = new HttpClient();
		System.out.println("RedirectEndPoint posting to Token point: " + url + " ,with Token Request params: " + urlEncodedPostParams);
		return client.executePost(url, urlEncodedPostParams );
	}
	
	/**
	 * 
	 */
	public ClientTokenResponse handleTokenResponse(HttpServletRequest request, HttpServletResponse response, HttpResponse tokenEndPointResponse)
			throws IOException, ServletException {
		System.out.println("Got Token Response from Token End Point ");
		
		ClientTokenResponse tokenResponse  = JsonUtil.fromJson(tokenEndPointResponse.getResponseStr(), ClientTokenResponse.class);
		System.out.println("Converting Json TokenRespone to TokenResponse.class Object. JsonTokenResponse: " + tokenResponse );
		return tokenResponse;
		}
	
	private void saveGrantToTable( ClientAuthParams authParams, RedirectionRequest redirectionRequest, ClientTokenResponse clientTokenResponse ) {
		ClientGrantTable.getInstance().save( authParams.getKey(), new ClientGrantAttribute(redirectionRequest, clientTokenResponse) );
	}
	
	private void setAttributesAndForwardToClientApp(HttpServletRequest request, HttpServletResponse response, String state) throws ServletException, IOException {
		request.setAttribute("state", state);
		 //forwardTokenResponseToClientApp(request, response);
	}
	
	private void forwardTokenResponseToClientApp( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		getServletContext().getRequestDispatcher(ClientConstants.APP_START_WITH_TOKEN ).forward(request, response);
	}
	
	
	private String constructTokenPostRequest( RedirectionRequest redirectRequest,ClientAuthParams authParams  ) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("grant_type=").append("authorisation_code")
		.append("&code=").append( encode( redirectRequest.getCode() ))
		.append("&redirect_uri=").append(  encode(authParams.getRedirectUri()) )
		.append("&client_id=").append( encode(authParams.getClientId()))
		.append("&client_key=").append( encode(authParams.getClientKey()));
		return sb.toString();
	}
	
	private String encode(String val) throws UnsupportedEncodingException {
		return URLEncoder.encode(val, "UTF-8");
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
						+ tokenResponse.getScope()
						+ " ,Access_token: "
						+ tokenResponse.getAccess_token() + " ,State: " + state);
		request.setAttribute("scope", tokenResponse.getScope());
		request.setAttribute("access_token", tokenResponse.getAccess_token());
		request.setAttribute("state", state);
	}

	private void forwardToClientAppView(HttpServletRequest request,
			HttpServletResponse response, GrantType grantType)
			throws ServletException, IOException {

		if ( grantType == GrantType.AUTHORISATION_CODE) {
			getServletContext().getRequestDispatcher("/clientappview").forward(
					request, response);
			return;
		}

		if ( grantType == GrantType.IMPLICIT ) {
			getServletContext().getRequestDispatcher("/clientappimplicitview")
					.forward(request, response);
			return;
		}

	}

	/**
	 * 
	 * @param tokenResponse
	 * @return
	 */
	private boolean hasError(ClientTokenResponse tokenResponse) {
		boolean hasError = tokenResponse.getError() != null;
		System.out
				.println("Client App Servlet checking for Error.Checking for Error in token Resposne. Has Error ? "
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

		String state = (String) request.getParameter("state"); // this is
																// parameter and
																// NOT
																// getAttribute
																// !!

		System.out
				.println("Into client app servlet: POST. Will validate the Access token expiry for state: "
						+ state);

		ClientAuthParams clientParams = ClientAuthHandler.getInstance().get(
				state);
		ClientTokenResponse tokenResponse = getClientTokenResponse(request,
				response, clientParams);
		System.out
				.println("Into Client App Servlet POST. Processing for User state: "
						+ state);

		valiateClientAppRequest(tokenResponse);
		request.setAttribute("state", state);
		processApp(request, response, tokenResponse, state, clientParams);
	}

	private ClientTokenResponse getClientTokenResponse(
			HttpServletRequest request, HttpServletResponse response,
			ClientAuthParams params) throws IOException, ServletException {

		if (params.grantType() == GrantType.AUTHORISATION_CODE)
			return ClientGrantTable.getInstance().get(params.getState())
					.getToken();

		return processImplicitRequest(request, response, params);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public ClientTokenResponse processImplicitRequest(
			HttpServletRequest request, HttpServletResponse response,
			ClientAuthParams params) throws IOException, ServletException {

		System.out
				.println("Into client app servlet: POST. Processing Implicit Request");

		String state = (String) request.getParameter("state"); // this is
																// parameter and
																// NOT
																// getAttribute
																// !!
		String access_token = (String) request.getParameter("access_token");
		String scope = (String) request.getParameter("scope");
		String expires_in = (String) request.getParameter("expires_in");
		String error = (String) request.getParameter("error");
		String errorDescription = (String) request
				.getParameter("error_description");

		System.out
				.println("Into Client App Servlet POST. Processing Implicit request. User state: "
						+ state + " ,access_token: " + access_token);

		ClientTokenResponse clientTokenResponse = new ClientTokenResponse();

		if (access_token != null && !"undefined".equals(access_token))
			clientTokenResponse.setAccess_token( decode( access_token) );

		if (expires_in != null && !"undefined".equals(expires_in))
			clientTokenResponse.setExpires_in( decode(expires_in));

		if (error != null && !"undefined".equals(error))
			clientTokenResponse.setError( decode(error));

		if (errorDescription != null && !"undefined".equals(errorDescription))
			clientTokenResponse.setError_description( decode(errorDescription));

		if (scope != null && !"undefined".equals(scope))
			clientTokenResponse.setScope( decode(scope));

	System.out.println("Into Client App Servlet. Extracted all the TokenRespone params received from the javascript client : " + clientTokenResponse.toString());
		return clientTokenResponse;
	}
	
	private String decode( String s ) {
		return Util.decode(s, "UTF-8" );
	}

	/**
	 * 
	 * @param tokenResponse
	 */
	private void valiateClientAppRequest(ClientTokenResponse tokenResponse) {
		// must access the Authorisation server validation end point

		boolean valid = validateHasRedirectionErrors(tokenResponse);
		
			if ( valid )
				validateTokenExpiry( tokenResponse );	
	}
		
		private void validateTokenExpiry( ClientTokenResponse tokenResponse ) {
			boolean invalid = TokenTable.getInstance()
					.get(tokenResponse.getAccess_token()).isExpired();
			if (invalid)
				ClientErrorHander.addAccessExpiredErrorCode(tokenResponse);
		}

	/**
	 * For any errors in Toekn Response, error=some error ( otherwise it can be
	 * null or undefined )
	 * 
	 * both error and access_token cannot be null !!
	 * 
	 * @param tokenResponse
	 */
	private boolean validateHasRedirectionErrors(ClientTokenResponse tokenResponse) {
	
		boolean valid = true;
		
		if (tokenResponse.getError() != null) {
			System.out
					.println("Into Client App servlet. Has carried forward some Redirection Errors from the Implicit Auth servlet");
			valid =  false; // clientResponse already contains error and
					// error_description
		}

		if ( valid && TokenTable.getInstance().get(tokenResponse.getAccess_token()) == null) {
			System.out
					.println("Into Client App Servlet. Access_Token recived: "
							+ tokenResponse.getAccess_token()
							+ " not found the Token Table. Something weired !!. Shall throw internal server error");
			ClientErrorHander.addInternalServerErrorCode(tokenResponse);
			valid = false;

		}
		System.out.println("Validation for Any Redirection errors. Any Error ? " + (!valid));
		return valid;
		
	}

	public void processApp(HttpServletRequest request,
			HttpServletResponse response,
			ClientTokenResponse clientTokenResponse, String state,
			ClientAuthParams clientAuthParams) throws IOException, ServletException {

		System.out
				.println("Into client app servlet. All Passes. Will process the Actual app and return /clientappview");

		if (hasError(clientTokenResponse)) {
			handleError(request, response, clientTokenResponse);
			return;
		}

		setAttributes(request, clientTokenResponse, state);
		forwardToClientAppView(request, response, clientAuthParams.grantType());
		
	}

}