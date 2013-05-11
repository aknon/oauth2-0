package com.goraksh.rest.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.google.gson.Gson;

public class JsonUtil {
	
	public static String toJson( Map<String, String> map ) {
		Gson gson = new Gson();
		String toJson = gson.toJson(map);
		System.out.println("To: " + toJson);
		return toJson;
	}
	
	public static String toJsonFro(Object obj) {
		Gson gson = new Gson();
		String toJson = gson.toJson(obj);
		System.out.println("Obj To: " + toJson);
		return toJson;
	}
	
	public static <T> T fromJson(InputStream in, Class<T> clz) {
		InputStreamReader reader = new InputStreamReader(in);
		Gson gson = new Gson();
		return gson.fromJson(reader, clz);
	}
	
	public static <T> T fromJson(String json, Class<T> clz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clz);
	}
	
	
	public static String fromJson(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader( new InputStreamReader(in) );
		String read = null;
		StringBuilder sb = new StringBuilder();
		while ( (read = reader.readLine()) != null )
		sb.append(read).append("\\n");
		
		return sb.toString();
	}
}
