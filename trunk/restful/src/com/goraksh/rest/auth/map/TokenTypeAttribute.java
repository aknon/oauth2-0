package com.goraksh.rest.auth.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenTypeAttribute {
	
	public enum TokenType {
		BEARER, MAC;
	}
	private Map<TokenType, List<String>> attributeMap;
	
	public TokenTypeAttribute() {
		attributeMap = new HashMap<>();
	init();
	}
	
	private void init() {
		attributeMap.put( TokenType.MAC,  Arrays.asList( new String[]{"id", "nonce", "mac"}));
	}	

}
