package com.goraksh.rest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URLClassLoader;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
	   
		System.out.println( "Into OAuth Start Servlet. Will Create Authoration End Point for Session Id :" + request.getSession().getId());
		System.out.println("Host name:" + request.getServerName());
		
		String scheme = "http"; // should be https
		String ipaddress = request.getServerName();//InetAddress.getLocalHost().getHostAddress();
		String port = String.valueOf(request.getServerPort() );//"8080";
		String scope = "HurrayPhotos";
		
		
		
		AuthParams auth = Store.createWithoutAccessToken( );
		auth.setScope( scope );
		
		System.out.println("Created Auth Params without Access Tokens: " + auth.toString());
		
	
		StringBuilder sb = new StringBuilder( scheme + "://" + ipaddress + ":" + port  + "/restful-js/authendpoint?" );
		
		sb.append( "response_type=token").append("&client_id=").append(auth.getClientId())
		.append("&scope=").append( scope)
		.append("&state=").append( auth.getState());
		
		String redirectUri = getAuthEndPoint(request, scheme, ipaddress, port);
		sb.append( "&redirect_uri=").append( URLEncoder.encode(redirectUri ));
		
		String authEndPoint = sb.toString();
		
		System.out.println("Starting MyApp. Setting Authorisation End Point as : " + authEndPoint);
		
		
		request.setAttribute("authorisation_uri",  authEndPoint);
		request.setAttribute("scope",  scope);
		
		
		//getServletContext().getRequestDispatcher("/games").forward(request, response);
		response.getWriter().print(authEndPoint);
		
			}
	
	private String getAuthEndPoint( HttpServletRequest request, String scheme, String servername, String port ) {
		AuthParams auth = Store.get( request.getSession().getId() );
		String redirectUrl = scheme + "://" + servername + ":" + request.getServerPort()  +
				"/restful-js/redirect";
		return redirectUrl;
	}
	
	private boolean loginRequest( HttpServletRequest request, HttpServletResponse response ) {
		return "login".equals(request.getParameter("login") );
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
	
	private void handleError(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
	
		System.out.println("Authorisation Servlet forwarding to /loginfailedjsp");
		request.getRequestDispatcher("/loginfailedjsp").forward(request,  response);
		
	}
	
	private boolean loginFailed(HttpServletRequest request,
			HttpServletResponse response) {
		boolean loginFailed =  request.getAttribute("error_message")!= null;
		if ( loginFailed )
			System.out.println("Authorisation Server verified that login has filed at Resource server");
		return loginFailed;
	}
}
