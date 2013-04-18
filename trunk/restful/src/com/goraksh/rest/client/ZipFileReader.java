package com.goraksh.rest.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * @author niteshk
 *
 */
public class ZipFileReader {

	private File zipFile;
	
	public ZipFileReader( File zipFile ) {
		this.zipFile = zipFile;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public Map<String, byte[]> read( ) throws IOException {

		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		Map<String, byte[]> byteMap = new HashMap<String, byte[]>();
		
		byte[] buffer = new byte[4096];
		ZipEntry entry = null;
	
		while ((entry = zis.getNextEntry()) != null) {
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			System.out.println("Extracting: " + entry);
			int numBytes;
	
			while ((numBytes = zis.read(buffer, 0, buffer.length)) != -1)
				bos.write(buffer, 0, numBytes);

			zis.closeEntry();
			bos.close();
						
			byteMap.put( entry.getName(), bos.toByteArray() );
			}
		return byteMap;
	}
	
	public void simpleWrite( String filename, byte[] bytesRead ) throws IOException{ 
		FileOutputStream fos = new FileOutputStream( new File( filename ));
		fos.write( bytesRead );
	}
	
	public void writeAsZip( String filename , byte[] bytesRead ) {
		
	}

}
