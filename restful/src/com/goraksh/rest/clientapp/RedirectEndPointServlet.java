package com.goraksh.rest.clientapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goraksh.rest.auth.JsonUtil;
import com.goraksh.rest.clientapp.map.ClientAuthHandler;
import com.goraksh.rest.clientapp.map.ClientAuthParams;
import com.goraksh.rest.clientapp.map.ClientGrantAttribute;
import com.goraksh.rest.clientapp.map.ClientGrantTable;
import com.goraksh.rest.clientapp.request.ClientTokenResponse;
import com.goraksh.rest.clientapp.request.RedirectionRequest;

/**
 * 
 * @author niteshk
 *
 */
public class RedirectEndPointServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2218291982614286050L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out
				.println("Into client redirect end point servlet for sessionId. This EndPoint shall accept the code ( or error ) and make a request for a access_token" + request.getSession().getId());

		RedirectionRequest redirectRequest = Util.extractRedirectionParams(request);
		
		if ( hasError( redirectRequest )) {
			handleError(request, response, redirectRequest);
			return;
		}
		
		System.out.println("Into Client RedirectEndPoint. Will now post to the Auth token end POint to fetch access token.");
		ClientAuthParams authParams = ClientAuthHandler.getInstance().get(redirectRequest.getState());
		String tokenEndPointUrl =  Util.constructBaseUri(request)  + ClientConstants.TOKEN_END_POINT ;
		
	
		String urlEncodedTokenRequest = constructTokenPostRequest( redirectRequest, authParams );
		HttpResponse tokenHttpResponse = postToTokenEndPoint(tokenEndPointUrl, urlEncodedTokenRequest);
		
		ClientTokenResponse clientTokenResponse = handleTokenResponse(request, response, tokenHttpResponse );
		saveGrantToTable( authParams, redirectRequest, clientTokenResponse);
		setAttributesAndForwardToClientApp(request, response, authParams.getState() );
	}
	
	private void saveGrantToTable( ClientAuthParams authParams, RedirectionRequest redirectionRequest, ClientTokenResponse clientTokenResponse ) {
		ClientGrantTable.getInstance().save( authParams.getKey(), new ClientGrantAttribute(redirectionRequest, clientTokenResponse) );
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
	
	private void setAttributesAndForwardToClientApp(HttpServletRequest request, HttpServletResponse response, String state) throws ServletException, IOException {
		request.setAttribute("state", state);
		 forwardTokenResponseToClientApp(request, response);
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
	
 private boolean hasError( RedirectionRequest redReq ) {
		return  redReq.hasError();
	}

	private void handleError(HttpServletRequest request,
			HttpServletResponse response, RedirectionRequest redirectRequest) throws IOException, ServletException {
		
		System.out.println("Into client redirection_end_point. Has error. Will Respond with /errorapp.Error: " + redirectRequest.getError().getErrorMessage() );
		request.setAttribute("error",   redirectRequest.getError().getError() );
		request.setAttribute( "error_description", redirectRequest.getError().getErrorMessage() );
		request.getRequestDispatcher("/errorapp").forward(request,  response);
		
	}

}
