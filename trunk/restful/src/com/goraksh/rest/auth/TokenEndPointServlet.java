package com.goraksh.rest.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;

import com.google.gson.JsonObject;
import com.goraksh.rest.auth.map.TokenTable;
import com.goraksh.rest.auth.request.AuthorisationError;
import com.goraksh.rest.auth.request.TokenError;
import com.goraksh.rest.auth.request.TokenRequest;
import com.goraksh.rest.auth.request.TokenResponse;
import com.goraksh.rest.auth.request.TokenResponseGenerator;
import com.goraksh.rest.auth.validation.TokenValidator;
import com.goraksh.rest.clientapp.HttpClient;

/**
 * 
 * @author niteshk
 * 
 */
public class TokenEndPointServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473961001930308879L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		System.out
				.println("Into Token End Point : POST. Extracting token request params by explicit parsing the request string");

		TokenRequest tokenRequest = AuthUtil.extractTokenParams(request);
		if (tokenRequest == null)
			throw new NullPointerException(
					"Token Request Params map cannot be NULL at this point of Token End Point : POST !!!");
		
		String code = tokenRequest.getCode();
		System.out
				.println("Into Token End Point : POST. Tooken Request Exracted for Authorisation code: "	+ code);
		
		TokenValidator validator = new TokenValidator(tokenRequest);
		TokenError error = validator.validate();
		boolean hasError = error.hasError();

		if (hasError) {
			try {
				errorJsonTokenResponseToClient(request, response, error);
			} catch (Exception e) {
				handleInternalServerError(request, response, e);
			}
			return;
		}

	System.out
				.println("In Token End Point. Scuccessfull Token request validation, moving forward to Access Token genration");

		TokenResponseGenerator generator = new TokenResponseGenerator(
				tokenRequest, code);
		TokenResponse tokenResponse = generator.generate();

		TokenTable.getInstance().save(tokenRequest, tokenResponse);

		try {
			okJsonTokenResponseToClient(request, response, tokenResponse);
			return;
		} catch (Exception e) {
			handleInternalServerError(request, response, e);
		}
	}

	private void errorJsonTokenResponseToClient(HttpServletRequest request,
			HttpServletResponse response, TokenError error) throws IOException,
			JSONException {

		JsonObject jsonObject = AuthUtil.toJsonObject(error);

		System.out.println("Writing Error Response as Json object: "
				+ jsonObject);

		writeJSonResponse(response, jsonObject);
		// postBack(request, jsonObject);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param redirectEndPoint
	 * @throws IOException
	 * @throws JSONException
	 */
	private void okJsonTokenResponseToClient(HttpServletRequest request,
			HttpServletResponse response, TokenResponse tokenResponse)
			throws IOException, JSONException {

		JsonObject jsonObject = AuthUtil.toJsonObject(tokenResponse);

		System.out.println("Writing Ok Response as Json object: " + jsonObject);

		writeJSonResponse(response, jsonObject);
		// postBack(request, jsonObject);

	}

	private void writeJSonResponse(HttpServletResponse response,
			JsonObject jsonObject) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
}

	private void handleInternalServerError(HttpServletRequest request,
			HttpServletResponse response, Exception e) throws ServletException,
			IOException {
		AuthorisationError error = new AuthorisationError();
		AuthorisationErrorHandler.addInternalServerErrorCode(error);

		JsonObject jsonObject = AuthUtil.toJsonObject(error);

		System.out
				.println("Writing Internal Server Error Response as Json object: "
						+ jsonObject);

		writeJSonResponse(response, jsonObject);
		// postBack(request, jsonObject);
	}

	private void postBack(HttpServletRequest request, JsonObject jsonObject)
			throws IOException {
		String url = AuthUtil.constructBaseUri(request)
				+ Constants.REGISTERED_REDIRECT_URI; // since this request has
														// come from the
														// registered uri
		postBackToReferer(url, jsonObject.toString());
	}

	private void postBackToReferer(String url, String urlEncodedPostParams)
			throws IOException {
		HttpClient client = new HttpClient();
		System.out
				.println("Token End point. Posting successful json string to back to referer client app  "
						+ url
						+ " ,with Token Request params: "
						+ urlEncodedPostParams);
		client.executePost(url, urlEncodedPostParams);
	}

}
