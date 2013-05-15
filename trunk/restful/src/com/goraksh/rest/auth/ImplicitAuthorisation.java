package com.goraksh.rest.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goraksh.rest.auth.map.AuthorisationCodeTable;
import com.goraksh.rest.auth.map.ClientIdCodeTable;
import com.goraksh.rest.auth.map.ClientRegistrationTable;
import com.goraksh.rest.auth.map.TempRequestMap;
import com.goraksh.rest.auth.map.TempStore;
import com.goraksh.rest.auth.map.TokenTable;
import com.goraksh.rest.auth.request.AuthorisationError;
import com.goraksh.rest.auth.request.AuthorisationRequest;
import com.goraksh.rest.auth.request.AuthorisationResponse;
import com.goraksh.rest.auth.request.AuthorisationResponseGenerator;
import com.goraksh.rest.auth.request.TokenResponse;
import com.goraksh.rest.auth.request.TokenResponseGenerator;
import com.goraksh.rest.auth.validation.AuthorisationValidator;

public class ImplicitAuthorisation extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473961001930308879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		boolean loggedIn = request.getAttribute("login") != null ? true : false;
		System.out
				.println("Into ImplicitAuthorisation End Point Server. Processing Auth Request for Implicit Grant Flow "
						+ (loggedIn ? "User Login Crdentials have been Validated. Implicit Auth End Point will now proceed with constructing a Redirection End Point"
								: " User not Authenticated., hence proceed to call login page"));

		if (loggedIn) {
			doPost(request, response);
			return;
		}
		System.out
				.println("Into ImplicitAuthorisation End Point Server. Processing Auth Request for Implicit Grant Flow ");

		AuthorisationRequest authRequest = AuthUtil
				.extractAuthorisationParams(request);

		System.out
				.println("State: "
						+ authRequest.getState()
						+ " .Extracted Authorisation parameters from Implicit Auth EndPoint Request : "
						+ authRequest.toString());

		AuthorisationError error = validateAuthRequest(authRequest);
		boolean hasError = error.hasError();
		boolean userOriented = hasError && error.isUserOriented();
		boolean redirectionOriented = hasError && error.isRedirectionOriented();

		if (userOriented) {
			showErrroToUser(request, response, error);
			return;
		}

		/***************/

		if (redirectionOriented) {
			String redirectEndPoint = getRedirectEndPointOnError(authRequest,
					error);
			redirectToClientWithErrorInHashFragments(request, response,
					redirectEndPoint);
			return;
		}

		System.out
				.println("Implicit Authorisation EndPoint Servlet generating tem Request id ");

		/*
		 * set required attributes that should be accessible in the Forwarded
		 * page
		 */
		String requestId = TempRequestMap.getInstance().saveAndGetTmpRequestId(
				authRequest);
		String appName = getAppName(authRequest);

		setRequestId("request_id", requestId, request);
		setRequestScope("scope", authRequest.getScope(), "ALL", request);
		setClientName("client_name", appName, request);

		/*
		 * AuthorisationResponseGenerator responseGenerator = new
		 * AuthorisationResponseGenerator(authRequest); AuthorisationResponse
		 * authResponse = responseGenerator.implicitGrantResponse();
		 * AuthorisationCodeTable.getInstance().save(authRequest, authResponse);
		 * ClientIdCodeTable.getInstance().save(authRequest, authResponse);
		 * 
		 * TokenResponseGenerator generator = new TokenResponseGenerator(
		 * authRequest, authResponse.getCode()); TokenResponse
		 * implicitTokenResponse = generator.implicitGrantTokenResponse();
		 * 
		 * TokenTable.getInstance().saveImplicitGrantToken(authRequest,
		 * implicitTokenResponse);
		 */

		// TempRequestMap.getInstance().inValidateTmpId( tmpRequestId );
		setRequestId("request_id", requestId, request);
		forwardForLoginAuthentication(request, response);
	}

	/**
	 * Is called from OOuth login form jsp.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String tmpRequestId = request.getParameter("request_id");
		System.out
				.println("Into Implicit Authorisation End Point : POST. Fetching Authorisation Request Params for Tmp Request Id: "
						+ tmpRequestId);

		TempStore tmpStore = TempRequestMap.getInstance().get(tmpRequestId);

		AuthorisationRequest authRequest = tmpStore.getAuth();
		AuthorisationError error = tmpStore.getError();
		boolean redirectionOriented = error != null && error.hasError()
				&& error.isRedirectionOriented();

		System.out
				.println("State: "
						+ authRequest.getState()
						+ " .Extracted Authorisation parameters from Implicit Auth EndPoint POST Request : "
						+ authRequest.toString());

		/***************/

		if (redirectionOriented) {
			String redirectEndPoint = getRedirectEndPointOnError(authRequest,
					error);
			redirectToClientWithHashFragments(request, response,
					redirectEndPoint);
			return;
		}

		System.out
				.println("Implcit Authorisation EndPoint Servlet POST after successfull login. Generating TOken Response for response_type=token ");

		AuthorisationResponseGenerator responseGenerator = new AuthorisationResponseGenerator(
				authRequest);
		AuthorisationResponse authResponse = responseGenerator
				.implicitGrantResponse();
		AuthorisationCodeTable.getInstance().save(authRequest, authResponse);
		ClientIdCodeTable.getInstance().save(authRequest, authResponse);

		TokenResponseGenerator generator = new TokenResponseGenerator(
				authRequest, authResponse.getCode());
		TokenResponse implicitTokenResponse = generator
				.implicitGrantTokenResponse();

		TokenTable.getInstance().saveImplicitGrantToken(authRequest,
				implicitTokenResponse);

		TempRequestMap.getInstance().inValidateTmpId(tmpRequestId);

		String redirectEndPoint = getRedirectEndPointImplicitGrant(authRequest,
				implicitTokenResponse);
		setRequestAttributes(request, response, redirectEndPoint);
		redirectToClientWithHashFragments(request, response, redirectEndPoint);

	}

	private void redirectToClientWithHashFragments(HttpServletRequest request,
			HttpServletResponse response, String redirectEndPoint)
			throws IOException, ServletException {
		// response.sendRedirect(redirectEndPoint);

		// getServletContext().getRequestDispatcher("/implicitauthorise").forward(request,
		// response );

		// This Jsp has Scripts for Redirecting. Prior to Redirecting it saves
		// Url hash Fragment
		System.out
				.println("Into ImplicitAuthorisation servlet. Ok Redirection., Throwing /authjsp to the user-agent for redirection. Set redirect_uri in the as request attribue: "
						+ redirectEndPoint);
		// getServletContext().getRequestDispatcher("/authjsp").forward(request,
		// response);
		System.out
				.println("Since its Implicit Flow, so writing redirect_uri ( as simple text) with hash fragments to response: "
						+ redirectEndPoint);

		writeRedirectUriWithFragmentsToResponse(request, response,
				redirectEndPoint);
		return;
	}

	private void redirectToClientWithErrorInHashFragments(
			HttpServletRequest request, HttpServletResponse response,
			String redirectEndPoint) throws IOException, ServletException {
		// response.sendRedirect(redirectEndPoint);

		// getServletContext().getRequestDispatcher("/implicitauthorise").forward(request,
		// response );

		// This Jsp has Scripts for Redirecting. Prior to Redirecting it saves
		// Url hash Fragment
		System.out
				.println("Into ImplicitAuthorisation servlet. has error. Throwing /authjsp to the user-agent for redirection. Set redirect_uri in the as request attribue: "
						+ redirectEndPoint);
		// getServletContext().getRequestDispatcher("/authjsp").forward(request,
		// response);
		System.out
				.println("Since its Implicit Flow, so writing redirect_uri ( as simple text) with hash fragments to response: "
						+ redirectEndPoint);

		// writeRedirectUriWithFragmentsToResponse(request, response,
		// redirectEndPoint);
		request.setAttribute("redirect_uri", redirectEndPoint);
		getServletContext().getRequestDispatcher("/authjsp").forward(request,
				response);
		return;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param redirectUri
	 * @throws IOException
	 */
	private void writeRedirectUriWithFragmentsToResponse(
			HttpServletRequest request, HttpServletResponse response,
			String redirectUri) throws IOException {
		PrintWriter out = response.getWriter();
		out.write(redirectUri);
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param redirectEndPoint
	 * @throws IOException
	 * @throws ServletException
	 */
	private void forwardForLoginAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// response.sendRedirect(redirectEndPoint);
		// request.setAttribute("redirect_uri", redirectEndPoint);
		System.out
				.println("Implicit AuthEndPoint forwading to /implictiauthenticate, ImplicitAuthenticationjsp");
		getServletContext().getRequestDispatcher("/implicitauthenticate")
				.forward(request, response);
	}

	/**
	 * 
	 * @param authRequest
	 * @return
	 */
	private String getAppName(AuthorisationRequest authRequest) {
		return ClientRegistrationTable.getInstance()
				.get(authRequest.getClientId()).getAppname();
	}

	/**
	 * 
	 * @param authRequest
	 * @param authError
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String getRedirectEndPointOnError(AuthorisationRequest authRequest,
			AuthorisationError authError) throws UnsupportedEncodingException {
		System.out
				.println("Into ImplicitAuthorisation servlet:POST. Has ClientRedirection Error. Error: "
						+ authError.getErrorcode());
		String baseUri = authRequest.getRedirectUri();
		RedirectUriBuilder errorBuilder = new RedirectUriBuilder(baseUri);
		return errorBuilder.buildErrorUriImplicitGrant(authError,
				authRequest.getState());
	}

	private String getRedirectEndPointImplicitGrant(
			AuthorisationRequest authRequest,
			TokenResponse implicitTokenResponse) throws UnsupportedEncodingException {
		String baseUri = authRequest.getRedirectUri();
		RedirectUriBuilder okBuilder = new RedirectUriBuilder(baseUri);
		return okBuilder.buildRedirectUriForImplicitGrant(authRequest,
				implicitTokenResponse);
	}

	private void setRequestAttributes(HttpServletRequest request,
			HttpServletResponse response, String redirectEndPoint) {
		request.setAttribute("redirect_uri", redirectEndPoint);
	}

	private AuthorisationError validateAuthRequest(AuthorisationRequest params) {
		AuthorisationValidator validator = new AuthorisationValidator(params);
		return validator.validateImplicitGrantAuthorisationRequest();
	}

	private void setRequestScope(String id, String scope, String defScope,
			HttpServletRequest request) {

		if (scope == null)
			scope = defScope;
		request.setAttribute(id, scope);
	}

	/**
	 * This Error goes to the User and not the Client.
	 * 
	 * Hence no redirection
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @throws IOException
	 * @throws ServletException
	 */
	private void showErrroToUser(HttpServletRequest request,
			HttpServletResponse response, AuthorisationError error)
			throws IOException, ServletException {

		System.out
				.println("User oriented error. Setting error and error_description and forwarding it to /errorapp");
		request.setAttribute("error", error.getErrorcode());
		request.setAttribute("error_description", error.getErrorMessage());
		request.getRequestDispatcher("/errorapp").forward(request, response);

	}

	private void setClientName(String id, String value,
			HttpServletRequest request) {
		request.setAttribute(id, value);
	}

	private void setRequestId(String id, String value,
			HttpServletRequest request) {
		request.setAttribute(id, value);
	}
}
