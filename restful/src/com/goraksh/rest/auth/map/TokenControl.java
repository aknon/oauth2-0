package com.goraksh.rest.auth.map;

import com.goraksh.rest.auth.Constants;


public class TokenControl {

	private String tokenType;
	private long expiresIn;
	
	public TokenControl( TokenTypeAttribute.TokenType tokenType) {
		this.tokenType = tokenType.toString().toLowerCase();
		expiresIn = Constants.INVALID_TOKEN_EXPIRY; // expiry in secs; this denotes that expiresIn in not valid
	}

	public TokenControl(TokenTypeAttribute.TokenType tokenType, long expiresIn) {
		this.tokenType = tokenType.toString().toLowerCase();
		this.expiresIn = expiresIn;
	}

	public void setTokenType(TokenTypeAttribute.TokenType tokenType) {
		this.tokenType = tokenType.toString().toLowerCase();
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public long getExpiresIn() {
		return this.expiresIn;
	}
}
