package com.goraksh.rest.clientapp;

import java.util.HashMap;
import java.util.List;

public class HttpResponse {
	
	private String responseStr;
	private HashMap<String, List<String>> responseHeadermap;
	
	public HttpResponse( String responseStr ) {
		this.responseStr = responseStr;
	}

	public String getResponseStr() {
		return responseStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

	public HashMap<String, List<String>> getResponseHeadermap() {
		return responseHeadermap;
	}

	public void setResponseHeadermap(HashMap<String, List<String>> responseHeadermap) {
		this.responseHeadermap = responseHeadermap;
	}
}
