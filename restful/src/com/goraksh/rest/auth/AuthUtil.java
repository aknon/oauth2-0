package com.goraksh.rest.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;
import com.goraksh.rest.auth.request.AuthError;
import com.goraksh.rest.auth.request.AuthorisationRequest;
import com.goraksh.rest.auth.request.TokenRequest;
import com.goraksh.rest.auth.request.TokenResponse;

public class AuthUtil {
	
	public enum GrantType {
		AUTHORISATION_CODE,
		IMPLICIT,
		CLIENT_CREDETIAL,
		OTHER		
	}
	
public static GrantType getGrantType( String type ) {
	if ( "implicit".equals(type))
		return GrantType.IMPLICIT;
	
	if ( "authorisation".equals(type))
		return GrantType.AUTHORISATION_CODE;
	
	return GrantType.OTHER;
}
	public static String constructBaseUri(HttpServletRequest request) {
		String scheme = request.getScheme();
		String ipaddress = request.getServerName();// InetAddress.getLocalHost().getHostAddress();
		String port = String.valueOf(request.getServerPort());// "8080";
		String contextPath = request.getContextPath();
		StringBuilder sb = new StringBuilder();
		sb.append(scheme).append("://").append(ipaddress).append(":")
				.append(port).append(contextPath);
		return sb.toString();
	}
	
	public static String constructBaseUri(HttpServletRequest request, boolean useIpAddress) {
		String scheme = request.getScheme();
		String ipaddress = null;
		if ( !useIpAddress ) {
			ipaddress = request.getServerName();// InetAddress.getLocalHost().getHostAddress();
		}else {
			try {
				ipaddress = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		String port = String.valueOf(request.getServerPort());// "8080";
		String contextPath = request.getContextPath();
		StringBuilder sb = new StringBuilder();
		sb.append(scheme).append("://").append(ipaddress).append(":")
				.append(port).append(contextPath);
		return sb.toString();
	}

	public static String constructBaseUri(URL url) {
		StringBuilder sb = new StringBuilder(url.getProtocol()).append("://")
				.append(url.getAuthority()).append(url.getPath());
		return sb.toString();
	}

	public static AuthorisationRequest extractAuthorisationParams(
			HttpServletRequest request) {
		String redirectUri = request.getParameter("redirect_uri");
		String scope = request.getParameter("scope");
		String clientId = request.getParameter("client_id");
		String responseType = request.getParameter("response_type");
		String state = request.getParameter("state");

		AuthorisationRequest params = new AuthorisationRequest(clientId,
				responseType, redirectUri, scope);
		params.setState(state);
		return params;
	}

	public static JsonObject toJsonObject(TokenResponse tokenResponse ) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("access_token", tokenResponse.getAccessToken());
		jsonObject.addProperty("token_type", tokenResponse.getTokenType());

		jsonObject.addProperty("expires_in", tokenResponse.getExpiresIn());

		String scope = tokenResponse.getScope();
		String refreshToken = tokenResponse.getRefreshToken();

		if (scope != null)
			jsonObject.addProperty("scope", scope);

		if (refreshToken != null)
			jsonObject.addProperty("refresh_token", refreshToken);
		return jsonObject;
	}
	
	public static JsonObject toJsonObject(AuthError error ) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("error", error.getErrorcode());
		jsonObject.addProperty("error_description", error.getErrorMessage());
		return jsonObject;
	}
	
	public static TokenRequest extractTokenParams(HttpServletRequest request0)
			throws IOException {
		
		Map<String, String> request = buildMap(request0);
		
		String redirectUri = decode(request, "redirect_uri");
		String code = decode(request, "code");
		String clientId = decode(request, "client_id");
		String clientKey = decode(request, "client_key");
		String grantType = decode(request, "grant_type");

		return new TokenRequest(code, clientId, clientKey, redirectUri,
				grantType);
	}

	public static String decode(String val) throws UnsupportedEncodingException {
		return URLDecoder.decode(val, "UTF-8");
	}

	private static String decode(HttpServletRequest request, String key)
			throws UnsupportedEncodingException {
		return decode(request.getParameter(key));
	}
	
	private static String decode(Map<String, String> request, String key)
			throws UnsupportedEncodingException {
		return decode(request.get(key));
	}
	
	public static Map<String, String> buildMap(HttpServletRequest request) throws IOException {
		InputStream in = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String read = null;
		Map<String, String> map = null;
				while ( (read = reader.readLine()) != null ) {
					System.out.println("Into Token End Point. Reading Request Input stream : " + read);
				map = parse( read );
				break;
				}
				return map;
		
	}
	
	private static Map<String, String> parse( String line ) {
		Map<String, String> map = new HashMap<>();
		String[] params = line.split("&");
		for ( String param : params ) {
			String[] aa = param.split("=");
			map.put(aa[0], aa[1] );			
		}
		return map;		
	}

}
