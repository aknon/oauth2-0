package com.goraksh.rest.clientapp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.goraksh.rest.auth.WebConnector;

public class HttpClient {

	public HttpClient() {

	}

	/**
	 * 
	 * @param urlStr
	 * @param urlFormEncodedString
	 * @throws IOException
	 */
	public void executePost0(String urlStr, String urlFormEncodedString)
			throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Length", ""
				+ urlFormEncodedString.length());

		OutputStreamWriter outputWriter = new OutputStreamWriter(
				connection.getOutputStream());
		outputWriter.write(urlFormEncodedString);
		outputWriter.flush();
		outputWriter.close();
	}

	public HttpResponse executePost(String urlStr, String urlFormEncodedString)
			throws IOException {
		WebConnector webConnector = new WebConnector(urlStr);
		String responseStr = webConnector.doPost(urlFormEncodedString);
		HttpResponse httpResponse = new HttpResponse(responseStr);
		httpResponse.setResponseHeadermap(webConnector.getResponseHeadermap());
		return httpResponse;
	}

}
