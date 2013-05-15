package com.goraksh.rest.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goraksh.rest.auth.map.TempRequestMap;
import com.goraksh.rest.auth.map.TempStore;
import com.goraksh.rest.auth.map.UserCredentials;
import com.goraksh.rest.auth.map.UserLoginTable;
import com.goraksh.rest.auth.request.AuthorisationError;
import com.goraksh.rest.auth.request.AuthorisationRequest;
import com.goraksh.rest.auth.validation.AuthorisationValidator;

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

		String tmpRequestId = request.getParameter("request_id");
		System.out.println("Into OAuth Login Servlet. Processing Request-Id : "
				+ tmpRequestId);

		TempStore tmpStore = TempRequestMap.getInstance().get(
				tmpRequestId);
		AuthorisationRequest auth = tmpStore.getAuth();
		if (auth == null)
			throw new NullPointerException(
					"Authorisation Request Params map cannot be NULL at this point of OAuth Login !!!");

		boolean accessAllowed = request.getParameter("access_allowed") != null ? Boolean.valueOf(request.getParameter("access_allowed")) : false;
		AuthorisationValidator validator = new AuthorisationValidator(auth);
		AuthorisationError error = validator.validateUserAuthentication(new UserCredentials(request.getParameter("user_name"), request.getParameter("password")), tmpRequestId, accessAllowed);

		boolean userOrientedError = error.hasError() && error.isUserOriented();

		System.out.println( error.hasError() ? ( "Error during login. Is user Oriented Error ? " + userOrientedError + ( userOrientedError ? error.getErrorMessage() +  " .Will resposnd to user-agent" : error.getErrorMessage() + " .Will Redirect this Error to Client") ) : "Login and User authentication successfull. Will set request attributes and forward it to Auth/ImplcitAUht end points");
		if (userOrientedError) {
			handleUserError(request, response, error);
			return;
		}
		
		setAttributes(request );
		updateTmpStore(tmpStore, error);
		// refreshIssueTime( auth );
		forwardToAuthEndPoint(request, response, auth);

	}
	
	private void updateTmpStore( TempStore tmpStore, AuthorisationError error ) {
		tmpStore.setError(error);
	}
	
	private void setAttributes( HttpServletRequest request ) {
		request.setAttribute("login", "logged_in");
}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardToAuthEndPoint(HttpServletRequest request,
			HttpServletResponse response, AuthorisationRequest authRequest) throws ServletException, IOException {
		
		if  ( authRequest.getResponseType().equals(Constants.AUTHORISATION_GRANT_TYPE) ) {
		//getServletContext().getRequestDispatcher("/authorise").forward(request,
		 //response);
			System.out.println("Response type="+authRequest.getResponseType() + " , OAuthLogin servlet forwarding it to /authendpoint, AuthorissationEndPoint");
		getServletContext().getRequestDispatcher("/authendpoint").forward(request, response);
		}
				
		else if ( authRequest.getResponseType().equals( Constants.IMPLICIT_GRANT_TYPE ) ) {
			System.out.println("Response type="+authRequest.getResponseType() + " , OAuthLogin servlet forwarding it to /implicitauthendpoint, ImplicitAuthorissationEndPoint");
			getServletContext().getRequestDispatcher("/implicitauthendpoint").forward(request, response);
			
		}
	}

	/**
	 * As login is successful, so this becomes it latest issue time
	 * 
	 * @param auth
	 */
	//private void refreshIssueTime(AuthParams auth) {
		//auth.refreshIssueTime();
	//}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param error
	 * @throws ServletException
	 * @throws IOException
	 */
	private void handleUserError(HttpServletRequest request,
			HttpServletResponse response, AuthorisationError error)
			throws ServletException, IOException {
		System.out.println("User oriented error. Error_description: " + error.getErrorMessage() + " .Forwarding it to /errorapp");
		request.setAttribute("error_description", error.getErrorMessage());
		request.setAttribute("error", error.getErrorcode());
		getServletContext().getRequestDispatcher("/errorapp").forward(request,
				response);
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param tmpRequestId
	 * @param authRequest
	 * @return
	 */
	private AuthorisationError validateLoginCredentials(String username,
			String password, String tmpRequestId,
			AuthorisationRequest authRequest) {

		UserCredentials loginInfo = new UserCredentials(username, password);
		boolean validLogin = UserLoginTable.getInstance().contains(loginInfo);
		AuthorisationError error = new AuthorisationError();

		if (!validLogin) {
			AuthorisationErrorHandler
					.addInvalidUsernameOrPasswordErrorCode(error);
			System.out.println("Loign Failure for Temp RequesstId :"
					+ tmpRequestId + " , and state: " + authRequest.getState()
					+ " . For Username :" + username
					+ " .Enter Test username :" + "nk");
		}
		return error;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
}
