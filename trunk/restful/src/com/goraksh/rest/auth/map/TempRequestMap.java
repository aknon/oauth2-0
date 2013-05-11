package com.goraksh.rest.auth.map;

import java.util.concurrent.ConcurrentHashMap;

import com.goraksh.rest.RandomString;
import com.goraksh.rest.auth.request.AuthorisationError;
import com.goraksh.rest.auth.request.AuthorisationRequest;

public class TempRequestMap {
	
	private static TempRequestMap instance;
	private ConcurrentHashMap<String, TempStore> tmpMap;
	private RandomString random;
	
	private TempRequestMap() {
		tmpMap = new ConcurrentHashMap<>();
		random = RandomString.getInstance();
	}
	
	public static synchronized TempRequestMap getInstance() {
		if ( instance == null )
			instance = new TempRequestMap();
		return instance;
	}
	
	public String saveAndGetTmpRequestId( AuthorisationRequest authRequest) {
		String tempRequestId = random.nextString();
		tmpMap.put(tempRequestId,  new TempStore(authRequest, null));
		System.out.println("Generating new Temp Request Id: " + tempRequestId + " request state: " + authRequest.getState() + " .Saving it Temporarily to TempRequestMap" );
		return tempRequestId;
	}
	
	public String saveAndGetTmpRequestId( AuthorisationRequest authRequest, AuthorisationError error) {
		String tempRequestId = random.nextString();
		tmpMap.put(tempRequestId,  new TempStore(authRequest, error));
		System.out.println("Generating new Temp Request Id: " + tempRequestId + " request state: " + authRequest.getState() + " .Saving it Temporarily to TempRequestMap" );
		return tempRequestId;
	}
	
	public String saveAndGetTmpRequestId( TempStore tmpStore) {
		String tempRequestId = random.nextString();
		tmpMap.put(tempRequestId,  tmpStore );
		System.out.println("Generating new Temp Request Id: " + tempRequestId + " request state: " + tmpStore.getAuth().getState() + " .Saving it Temporarily to TempRequestMap" );
		return tempRequestId;
	}
	
	public void update( String tmpRequestId, TempStore inputTmpStore) {
	 TempStore tmpStore = tmpMap.get(tmpRequestId );
	 if ( tmpStore == null ) {
		 System.out.println("Temp Store cannot be updated. RequestId: " + tmpRequestId + " not present");
		 return;
	 }
	 tmpMap.put( tmpRequestId, inputTmpStore);
	}
	
	public AuthorisationRequest getAuthRequest( String tmpRequestId ) {
		return tmpMap.get( tmpRequestId ).getAuth();
	}
	
	public AuthorisationError getAuthError( String tmpRequestId ) {
		return tmpMap.get( tmpRequestId ).getError();
	}
	
	public TempStore get( String tmpRequestId ) {
		return tmpMap.get( tmpRequestId );
	}
	
	public String inValidateTmpId( String tmpId ) {
		System.out.println("Invalidating Tmp Request Id: " + tmpId  + " .Deleting it from TmpRequestMap");
		tmpMap.remove(tmpId);
		return tmpId;
		
	}

}
