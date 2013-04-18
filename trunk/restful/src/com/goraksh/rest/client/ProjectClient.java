package com.goraksh.rest.client;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.MultiPart;
 
/**
 * 
 * @author nitesh
 *
 */
public class ProjectClient {
	
	private static String getZipFileName( String userInputZipfileName0, String casebaseName ) {
		
		String userInputZipfileName = userInputZipfileName0;
		if ( userInputZipfileName == null || userInputZipfileName.length() == 0 )
			return casebaseName;
		userInputZipfileName = userInputZipfileName.toLowerCase();
		int ZIP_INDEX = userInputZipfileName.indexOf(".");
		if ( ZIP_INDEX == -1 )
			ZIP_INDEX = userInputZipfileName.length();
		return userInputZipfileName.substring(0, ZIP_INDEX) + ".zip";
	}
	
	private static byte[] getByteArrayFromInputStream(InputStream in) throws IOException
	{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[4096];

		while ((nRead = in.read(data, 0, data.length)) != -1)
				buffer.write(data, 0, nRead);

		buffer.flush();
		buffer.close();
		return buffer.toByteArray();
	}
	
	private static String getString( byte[] bytes ) {
		String s = new String( bytes );
		System.out.println(":sss =" + s);
		return s;
	}
	
	private static InputStream getInput( String filename ) throws FileNotFoundException {
		FileInputStream in = new FileInputStream( new File( filename ));
		return in;
	}
	
	private static void testCallImport() {

		  
		  String filename = "D:\\svn_tree\\testfile.txt";
	    final String BASE_URI = "http://localhost:8080/restful/rest";
	 
	    Client c = Client.create();
	    WebResource service = c.resource(BASE_URI); 
	  
	    byte[] logo = getAttachmentBytes(filename);
	 
	    // Construct a MultiPart with two body parts
	    CasebaseImport csim = new CasebaseImport();
	    csim.setCasebaseName( "hello casebase");
	    
	    MultiPart multiPart = new MultiPart().
	     bodyPart(new BodyPart(csim, MediaType.APPLICATION_XML_TYPE)).
	      bodyPart(new BodyPart(logo, MediaType.APPLICATION_XML_TYPE));
	 
	    // POST the request
	    ClientResponse response = service.path("/import").
	      type("multipart/mixed").post(ClientResponse.class, multiPart);
	    System.out.println("Response Status : " + response.getEntity(String.class));
	  	}
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
  public static void main(String[] args) throws Exception {
	  String user = "cb-1-zip.dfgdf.zip";
	  System.out.println( getZipFileName( user, "default" ) );
	  getString( getByteArrayFromInputStream( getInput("D:\\svn_tree\\testfile.txt" )) );
	  //testCallImport();
  }
  
  private static void testZipReadWrite() throws IOException {

		 String FS = System.getProperty("file.separator" );
		  String zipDirRead = "D:\\test";
		  String zipDirWrite = "D:\\test\\out";
		  String zipFilename = "cb-1-zip.zip";
		  
		   
		  File zipFile = new File ( zipDirRead + FS + zipFilename );
		  
		  ZipFileReader reader = new ZipFileReader( zipFile );
		  Map<String, byte[]> map = reader.read();
		 
		  ZipFileWriter writer = new ZipFileWriter(zipDirWrite, zipFilename);
		  Set<String> set = map.keySet();
		  Iterator<String> iter = set.iterator();
		  while ( iter.hasNext() ) {
			  
			 String key = iter.next();
			  System.out.println("Writing file Entry :" + key );
			  byte[] bytesread = map.get(key);
			
			  writer.writeAsZip( bytesread, key );
			 // writer.writeAsText( key, bytesread );
		  }
	  
  }
  
  /**
   * 
   * @return
   */
  private static byte[] getAttachmentBytes(String filename)
  {
	  
      File file = new File(filename);
      BufferedReader bf = null;
      StringBuilder sb = null;
      try
      {
          bf = new BufferedReader(new FileReader(file));
          String line = null;
          sb = new StringBuilder();
          while ((line = bf.readLine()) != null)
          {
              sb.append(line);
          }
      }
      catch (Exception e)
      {
          e.printStackTrace();
          return null;
      }
      return sb.toString().getBytes();

  }
}