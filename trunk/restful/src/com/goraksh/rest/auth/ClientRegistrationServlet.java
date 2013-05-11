package com.goraksh.rest.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goraksh.rest.auth.map.ClientIdTokenControlTable;
import com.goraksh.rest.auth.map.ClientRegistrationTable;
import com.goraksh.rest.auth.request.ClientRegister;
import com.goraksh.rest.auth.request.RegistrationError;
import com.goraksh.rest.auth.validation.RegistrationValidator;

public class ClientRegistrationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4110550471574258655L;
	

	
	private  String getDefaultAppName() {
		return Constants.DEFAULT_APP_NAME;
	}
	
	private String getDefaultHomeUri() {
		return Constants.DEFAULT_HOME_URI;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String sessionId = request.getSession().getId();
		

		System.out.println("Into Client Registration for HTTP Get. Going to Return /register with prepopulated Client Registration values for sessionId: " + sessionId );

		request.setAttribute("app_name", getDefaultAppName());
		request.setAttribute("home_uri", getDefaultHomeUri() );
		request.setAttribute("redirect_uri",  constructRedirectUri(request, response, Constants.REGISTERED_REDIRECT_URI));

	 forwardRegistrationPage(request, response);
	}
	
	private String constructRedirectUri( HttpServletRequest request, HttpServletResponse response, String defaultUri ) {
		String pre = AuthUtil.constructBaseUri(request);
		String completeUri = pre + defaultUri;
		System.out.println("Redirect Uri constructed: " + completeUri);
		return completeUri;
		
		
	}
	
	private void forwardError( HttpServletRequest request, HttpServletResponse response, RegistrationError error ) throws ServletException, IOException {
		request.setAttribute("error_description", error.getErrorMessage() );
		request.setAttribute("error",  error.getErrorcode() );
		getServletContext().getRequestDispatcher("/clienterror").forward(request, response );
	}

	private void forwardResponse(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			System.out
					.println("Forwarding it to Successful Sending Client Response.");
			getServletContext().getRequestDispatcher("/clientresponse").forward(
					request, response);
		
	}
	
	private void forwardRegistrationPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			System.out
					.println("Forwarding it to Successful Sending Client Response.");
			getServletContext().getRequestDispatcher("/registerform").forward(
					request, response);
		
	}

public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String sessionId = request.getSession().getId();

		String appname = request.getParameter("app_name");
		String homeUri = request.getParameter("home_uri");
		String redirectUri = request.getParameter("redirect_uri");

		System.out.println("Into Client Registration for Session Id:"
				+ sessionId + " ,app_name: " + appname + " ,home_uri: "
				+ homeUri + " ,redirect_uri: " + redirectUri);

		RegistrationError error = validate(appname, homeUri, redirectUri);
		if ( error.hasError() ) {
			forwardError(request, response, error);
			return;
		}
		
		
		ClientRegister clientRegister = ClientRegistrationTable.getInstance()
				.registerNewClient(appname, homeUri, redirectUri);
		ClientIdTokenControlTable.getInstance().update(clientRegister.getClientID()); // updates token control params

		System.out.println("Setting ClientId, AppName and Client Key in response for successful client registration of sessionI:"	+ sessionId);
		request.setAttribute( "client_id", clientRegister.getClientID() );
		request.setAttribute("client_key", clientRegister.getClientKey() );
		request.setAttribute("app_name", clientRegister.getAppname() );
		request.setAttribute("start_uri", AuthUtil.constructBaseUri(request) + Constants.APP_START_URI + "?client_id="+ clientRegister.getClientID() );
		forwardResponse(request, response);
	}

  private RegistrationError validate( String appName, String redirectUri, String homeUri ) {
	  RegistrationValidator validator = new RegistrationValidator(appName, redirectUri, homeUri);
	  return validator.validate();
  }
}
