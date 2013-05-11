package com.goraksh.rest.auth.validation;

import java.net.MalformedURLException;
import java.net.URL;

import com.goraksh.rest.auth.AuthUtil;
import com.goraksh.rest.auth.map.ClientRegistrationTable;
import com.goraksh.rest.auth.request.ClientRegister;

public class ValidationLib {

	public static boolean validateUri(String uri) {
		System.out.println("Validating redirect_uri for basic non-null,hash chars: " + uri);
		if (uri == null || "".trim().equals(uri))
			return false;
		if (!(uri.startsWith("http")) || uri.contains("#"))
			return false;
		return true;
	}

	public static boolean validateResponseTypeCode(String responseType) {
		boolean valid = "code".equals(responseType);
		System.out.println("Validating response_type: " + responseType
				+ " .valid: " + valid);
		return valid;
	}

	public static boolean validateGrantTypeCode(String grantType) {
		boolean valid = "authorization_code".equals(grantType);
		System.out.println("Validating Token Grant Type for Authorisation code grant. GrantType : " + grantType
				+ " . Is Valid ? " + valid);
		return valid;
	} 
	public static boolean validateClientAsRegistered(String clientId) {
		boolean valid = ClientRegistrationTable.getInstance()
				.isRegisteredClient(clientId);

		System.out.println("Validating Client Id. Client Id in request: "
				+ clientId + " .Is valid/registered_client_id: " + valid);
		return valid;
	}

	/**
	 * pre-condition:
	 * 1.) must be a valid registered client
	 * 
	 * @param clientId
	 * @param redirectUri
	 * @return
	 * @throws MalformedURLException
	 */
	public static boolean validateRedirectUriAsRegistered(String clientId,
			String redirectUri) throws MalformedURLException {
	
		ClientRegister registerInfo = ClientRegistrationTable.getInstance()
				.get(clientId);
		boolean valid = registerInfo.getRedirectUri() != null;

		if (valid) {
			URL url = new URL(redirectUri); // this uri may have some query
											// components. Registered URI may
											// never have a query component
			String redirectBaseUri = AuthUtil.constructBaseUri(url);
			valid = redirectBaseUri.equals(registerInfo.getRedirectUri());
		}

		System.out.println("Validating RedirectUri as Registerd Client Id: "
				+ clientId + "  , and inputRedirectUri: " + redirectUri
				+ " ,registered redirectUri: " + registerInfo.getRedirectUri()
				+ " .Is valid/registered_client_id: " + valid);
		return valid;
	}

}
