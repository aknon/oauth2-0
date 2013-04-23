package com.goraksh.rest.auth;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goraksh.rest.AuthParams;
import com.goraksh.rest.ErrorManger;
import com.goraksh.rest.SessionMap;
import com.goraksh.rest.Store;

public class AuthorisationEndPointServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473961001930308879L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String sessionId = request.getSession().getId();
		System.out
				.println("Into Authorisation Server. Validating Authorisation Request. Will Issue Access Tokens on Success :"
						+ sessionId);
		System.out.println("Host name:" + request.getServerName());

		AuthParams auth = Store.get(sessionId);

		boolean match = false;

		Map<String, String[]> requestParamMap = clonee(request
				.getParameterMap());

		match = validateRedirectUri(request.getParameter("redirect_uri"), auth);

		System.out.println("Accessing redirect Uri : "
				+ request.getParameter("redirect_uri"));

		if (!match) {
			cancelTokens(auth);
			handleError(request, response, auth);
			return;
		}

		match = validateResponseType(request.getParameter("response_type"),
				auth);
		if (match) {
			match = validateClient(request.getParameter("client_id"),
					auth.getClientId(), auth);
		}

		if (!match)
			requestParamMap = constructErrorMap(
					String.valueOf(auth.getErrorcode()),
					auth.getErrorMessage(), requestParamMap);
		else {
			auth = Store.generateAndAddAccessToken(auth);
			requestParamMap = constructOkResponseMap(auth.getAccessToken(),
					requestParamMap);
		}
		// requestParamMap.put("redirect_uri_new",
		// new String[] { appendToRedirectUri(requestParamMap) });
		requestParamMap.put("from_auth_Server", new String[] { "true" });

		System.out
				.println("Forwarding to Resource Server for any Error or OK redirections. Set new Attribute 'redirect_uri_new' ");

		/*
		 * set required attributes that should be accessible in the Forwarded
		 * page
		 */
		String requestId = request.getSession().getId();
		System.out.println("Setting Request Id to Store and SessionMap :" + requestId );
		setRequestId("request_id", requestId , request);
		setRequestScope("scope", "ALL", request);
		setClientName("client_name", auth.getClientName(), request);
		store( requestId, requestParamMap);
		forward(match, auth, request, response);
	}
	
	private void cancelTokens( AuthParams auth ) {
		ErrorManger.cancelToken( auth );
	}

	private void setClientName(String id, String value,
			HttpServletRequest request) {
		request.setAttribute(id, value);
	}

	private void setRequestScope(String id, String defScope,
			HttpServletRequest request) {
		String scope = request.getParameter(id);
		if (scope == null)
			scope = defScope;
		request.setAttribute(id, scope);
	}

	private void setRequestId(String id, String value,
			HttpServletRequest request) {
		request.setAttribute(id, value);
	}

	private void store(String key, Object value) {
		SessionMap.getInstance().put(key, value);
	}

	private void forward(boolean match, AuthParams params,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * If request input validations has failed then no need to ask user for
		 * OAuth Login, directly forward it to AuthorisatinServelt, that is the
		 * Resource to handle requests after login
		 */
		if (!match) {
			getServletContext().getRequestDispatcher("/authorise").forward(
					request, response);
		} else {
			getServletContext().getRequestDispatcher("/authenticate").forward(
					request, response);
		}
	}

	private Map<String, String[]> constructOkResponseMap(String acces_token,
			Map<String, String[]> requestAttributes) {
		requestAttributes.put("access_token", new String[] { acces_token });
		return requestAttributes;
	}

	private Map<String, String[]> clonee(Map<String, String[]> requestMap) {
		Map<String, String[]> paramsMap = new HashMap<>();
		Set<String> set = requestMap.keySet();
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			paramsMap.put(key, getVal(requestMap.get(key)));
		}
		return paramsMap;

	}

	private String[] getVal(String[] val) {
		String[] s = new String[1];
		// for ( int i = 0; i < val.length; i++ ) {
		s[0] = new String(URLDecoder.decode(val[0]));
		// }
		return s;
	}

	/**
	 * Here the OAuth server directly redirects to the user-agent. It does not
	 * redirect to the Client. Redirect to the client is done via
	 * {@link #processOkRedirect(HttpServletRequest, HttpServletResponse, Map)}
	 * 
	 * @param request
	 * @param response
	 * @param requestParamsMap
	 * @throws IOException
	 */
	private void processErrorRedirect(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> requestParamsMap)
			throws IOException {
		String error = requestParamsMap.get("error")[0];
		String redirect_uri = appendToRedirectUri(requestParamsMap);

		if (error != null) {
			System.out
					.println("Error Response. Redirecting user-agent to Redirect URi. No need to go to resource server");
			response.sendRedirect(redirect_uri);
			return;
		}

	}

	private void processOkRedirect(HttpServletRequest request,
			HttpServletResponse response, Map<String, String[]> requestParamsMap)
			throws IOException {
		String error = requestParamsMap.get("error")[0];
		String redirect_uri = appendToRedirectUri(requestParamsMap);

		if (error != null) {
			System.out
					.println("Error Response. Redirecting user-agent to Redirect URi. No need to go to resource server");
			response.sendRedirect(redirect_uri);
			return;
		}

	}

	/**
	 * All attributes must be added and not just the below ones.
	 * 
	 * Some attributes can be given by the user, that are not a part of Oauth
	 * specs. They must be added too !!
	 * 
	 * @param requestParamsMap
	 * @return
	 */
	private String appendToRedirectUri(Map<String, String[]> requestParamsMap) {

		StringBuilder sb = new StringBuilder(
				requestParamsMap.get("redirect_uri")[0]);

		String client_id = requestParamsMap.get("client_id")[0];
		String access_token = requestParamsMap.get("access_token")[0];
		String response_type = requestParamsMap.get("response_type")[0];
		String state = requestParamsMap.get("state") != null ? requestParamsMap
				.get("state")[0] : null;
		String scope = requestParamsMap.get("scope") != null ? requestParamsMap
				.get("scope")[0] : null;

		String error = requestParamsMap.get("error") != null ? requestParamsMap
				.get("error")[0] : null;
		String error_description = requestParamsMap.get("error_description") != null ? requestParamsMap
				.get("error_description")[0] : null;
		String error_uri = requestParamsMap.get("error_uri") != null ? requestParamsMap
				.get("error_uri")[0] : null;

		sb.append("?");
		append("client_id", client_id, sb);

		if (state != null)
			append("&state", state, sb);

		if (scope != null)
			append("&scope", scope, sb);

		if (error != null)
			append("&error", error, sb);

		if (error_description != null)
			append("&error_description", error, sb);

		if (error_uri != null)
			append("&error_uri", error_uri, sb);

		if (access_token != null && response_type.equals("code"))
			append("&access_token", access_token, sb);
		else if (access_token != null && response_type.equals("token"))
			append("#access_token", access_token, sb);
		append("&response_type", response_type, sb);

		String redirect_url = sb.toString();
		System.out.println("New Redirect URi constructed : " + redirect_url);
		return redirect_url;

	}

	private StringBuilder append(String key, String value, StringBuilder sb) {
		sb.append(key).append("=").append(URLEncoder.encode(value));
		return sb;
	}

	private Map<String, String[]> constructErrorMap(String errorCode,
			String errorMsg, Map<String, String[]> requestParamMap) {

		requestParamMap.put("error", new String[] { errorCode });
		requestParamMap.put("error_description", new String[] { errorMsg });
		return requestParamMap;
	}

	private boolean validateResponseType(String responseType, AuthParams params) {
		boolean valid = "token".equals(responseType);
		if (!valid)
			ErrorManger.addInvalidResponseTypeErrorCode(responseType, params);
		return valid;
	}

	private boolean loginRequest(HttpServletRequest request,
			HttpServletResponse response) {
		return "login".equals(request.getParameter("login"));
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
	private void handleError(HttpServletRequest request,
			HttpServletResponse response, AuthParams params)
			throws IOException, ServletException {

		request.setAttribute("error_code",
				String.valueOf(params.getErrorcode()));
		request.setAttribute("error_message", params.getErrorMessage());
		request.getRequestDispatcher("/errorapp").forward(request, response);

	}

	/*
	 * 
	 */
	private boolean validateClient(String reqId, String storedId,
			AuthParams params) {
		boolean valid = match(reqId, storedId);
		if (!valid)
			ErrorManger.addInvalidClientErrorCode(params);
		return valid;
	}

	private boolean validateRedirectUri(String redirectUri, AuthParams params) {
		boolean valid = validateUri(redirectUri);
		if (!valid)
			ErrorManger.addInvalidRedirectErrorCode(redirectUri, params);
		return valid;
	}

	private boolean validateUri(String uri) {
		if (uri == null || "".trim().equals(uri))
			return false;
		if (uri.contains("#"))
			return false;
		return true;
	}

	private boolean match(String reqId, String storedId) {
		return storedId.equals(reqId);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
