package com.goraksh.rest.auth.request;


public class ClientRegister {

	private String appname;
	private String clientID;
	private String homeUrl;
	private String redirectUri;
	private String clientKey;


	public ClientRegister(String appname, String homeUrl, String redirectUri) {
		this.appname = appname;
		this.homeUrl = homeUrl;
		this.redirectUri = redirectUri;
	}

	public String getClientKey() {
		return this.clientKey;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

public void setClientID(String clientID) {
		this.clientID = clientID;
	}
 
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getAppname() {
		return this.appname;
	}

	public String getClientID() {
		return clientID;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public String getRedirectUri() {
		return redirectUri;
	}
}
