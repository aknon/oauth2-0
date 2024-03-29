package com.goraksh.rest.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goraksh.rest.auth.AuthUtil.GrantType;
import com.goraksh.rest.auth.map.AuthorisationCodeTable;
import com.goraksh.rest.auth.map.ClientIdCodeTable;
import com.goraksh.rest.auth.map.ClientRegistrationTable;
import com.goraksh.rest.auth.map.TempRequestMap;
import com.goraksh.rest.auth.map.TempStore;
import com.goraksh.rest.auth.request.AuthorisationError;
import com.goraksh.rest.auth.request.AuthorisationRequest;
import com.goraksh.rest.auth.request.AuthorisationResponse;
import com.goraksh.rest.auth.request.AuthorisationResponseGenerator;
import com.goraksh.rest.auth.validation.AuthorisationValidator;
import com.goraksh.rest.clientapp.map.ClientAuthHandler;
import com.goraksh.rest.clientapp.map.ClientAuthParams;

/**
 * 
 * @author niteshk
 * 
 */
public class AuthorisationEndPointServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473961001930308879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		boolean loggedIn = request.getAttribute("login") != null ? true : false;
		System.out
				.println("Into Authorisation End Point Server. "
						+ (loggedIn ? "User Login Crdentials have been Validated. Auth End Point will now proceed with constructing a Redirection End Point"
								: " User not Authenticated., hence proceed to call login page"));
		if (loggedIn) {
			doPost(request, response);
			return;
		}

		String type = request.getParameter("type");
		GrantType grantType = AuthUtil.getGrantType(type);
		
		AuthorisationRequest authRequest = AuthUtil.extractAuthorisationParams(request);
		
		/** Just required because the way UI is constructed */
		ClientAuthParams params = ClientAuthHandler.getInstance().get( authRequest.getState() ); // just a hack
		params.setGrantType(grantType); // just a hack
		/*** Above two lines are a special hack !!! */
		
		
		System.out.println("Received Response_type=" + authRequest.getResponseType() + " , Grant Type of request " + grantType.toString() );
		if (  grantType == GrantType.IMPLICIT ) {
			
			forwardToImplicitAuthEndPoint( request, response, authRequest );
			return;
		}
		
		System.out
				.println("State: "
						+ authRequest.getState()
						+ " .Extracted Authorisatin parameters from Auth EndPoint Request : "
						+ authRequest.toString());

		AuthorisationError error = validateAuthRequest(authRequest);
		boolean hasError = error.hasError();
		boolean userOriented = hasError && error.isUserOriented();
		boolean redirectionOriented = hasError && error.isRedirectionOriented();

		if (userOriented) {
			showErrroToUser(request, response, error);
			return;
		}
		
		if (redirectionOriented) {
			String redirectEndPoint = getRedirectEndPoint(authRequest, error);
			errorRedirectToClient(request, response, redirectEndPoint);
			return;
		}

		System.out
				.println("Authorisation EndPoint Servlet generating tem Request id ");

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
		forwardForLoginAuthentication(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {


		String tmpRequestId = request.getParameter("request_id");
		System.out.println("Into Authorisation End Point : POST. Fetching Authorisation Request Params for Tmp Request Id: "
				+ tmpRequestId);

		TempStore tmpStore = TempRequestMap.getInstance().get(
				tmpRequestId);
		
		AuthorisationRequest authRequest = tmpStore.getAuth();
		AuthorisationError error = tmpStore.getError();
		boolean redirectionOriented = error!= null && error.hasError() && error.isRedirectionOriented();	
		
		if (redirectionOriented) {
			System.out.println("Redirection Oriented Error from the Rest forwarded by the OAuthLoginServlet: " + error.getErrorMessage() );
				String redirectEndPoint = getRedirectEndPoint(authRequest, error);
				errorRedirectToClient(request, response, redirectEndPoint);
				return;
			}
		
		if (authRequest == null)
			throw new NullPointerException(
					"Authorisation Request Params map cannot be NULL at this point of Authorisation End Point : POST !!!");

		System.out.println("In AuthnEndPoint. Scuccessfull validattion, Authentication and User access Allowed. Moving forward to Authorisation code generation");
   AuthorisationResponseGenerator responseGenerator = new AuthorisationResponseGenerator(authRequest);
   AuthorisationResponse authResponse = responseGenerator.authorisationGrantReponse();
   AuthorisationCodeTable.getInstance().save(authRequest, authResponse);
   ClientIdCodeTable.getInstance().save(authRequest, authResponse);
   
   TempRequestMap.getInstance().inValidateTmpId( tmpRequestId );
   
   String redirectEndPoint = getRedirectEndPoint(authRequest, authResponse);
   okRedirectToClient(request, response, redirectEndPoint);	
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param authRequest
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardToImplicitAuthEndPoint( HttpServletRequest request, HttpServletResponse response, AuthorisationRequest authRequest ) throws ServletException, IOException {
		request.setAttribute("auth_request", authRequest );
		System.out.println("Received A Implcit End Point Request. Will forward to /impliciteauthendpoint i.e ImplicitAuthorisationServlet");
		getServletContext().getRequestDispatcher("/implicitauthendpoint").forward(request, response );
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
	private String getRedirectEndPoint(AuthorisationRequest authRequest,
			AuthorisationError authError) throws UnsupportedEncodingException {
		String baseUri = authRequest.getRedirectUri();
		RedirectUriBuilder errorBuilder = new RedirectUriBuilder(baseUri);
		return errorBuilder.buildErrorUriAuthorisationGrant(authError, authRequest.getState());
	}
	
	/**
	 * 
	 * @param authRequest
	 * @param authResponse
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String getRedirectEndPoint(AuthorisationRequest authRequest, AuthorisationResponse authResponse) throws UnsupportedEncodingException {
		String baseUri = authRequest.getRedirectUri();
		RedirectUriBuilder okBuilder = new RedirectUriBuilder(baseUri);
		return okBuilder.buildRedirectUriForAuthorisationGrant(authResponse);
	}

	private void errorRedirectToClient(HttpServletRequest request,
			HttpServletResponse response, String redirectEndPoint)
			throws IOException {
		response.sendRedirect(redirectEndPoint);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param redirectEndPoint
	 * @throws IOException
	 */
	private void okRedirectToClient(HttpServletRequest request,
			HttpServletResponse response, String redirectEndPoint)
			throws IOException {
		response.sendRedirect(redirectEndPoint);
	}

	private AuthorisationError validateAuthRequest(AuthorisationRequest params) {
		AuthorisationValidator validator = new AuthorisationValidator(params);
		return validator.validateAuthorisationGrantRequest();
	}

	private void setClientName(String id, String value,
			HttpServletRequest request) {
		request.setAttribute(id, value);
	}

	private void setRequestScope(String id, String scope, String defScope,
			HttpServletRequest request) {

		if (scope == null)
			scope = defScope;
		request.setAttribute(id, scope);
	}

	private void setRequestId(String id, String value,
			HttpServletRequest request) {
		request.setAttribute(id, value);
	}

	private void forwardForLoginAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out
				.println("Authorisatin Request Paramters to Authentication End Point validated successfully, so sending it to /authenticate for a login page");
		getServletContext().getRequestDispatcher("/authenticate").forward(
				request, response);
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

		System.out.println("User oriented error. Setting error and error_description and forwarding it to /errorapp");
		request.setAttribute("error", error.getErrorcode());
		request.setAttribute("error_description", error.getErrorMessage());
		request.getRequestDispatcher("/errorapp").forward(request, response);

	}
}
