package com.goraksh.rest.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author niteshk
 *
 */
public class ZipFileWriter {

	private String zipDir;
	
	private String zipFilename; // ends in .zip
	
	private static String FS = System.getProperty("file.separator" );
	
	public ZipFileWriter( String zipDir, String zipFilename ) {
		this.zipDir = zipDir;
		this.zipFilename = zipFilename;
	}
	
	/**
	}
	 * 
	 * @return
	 * @throws IOException
	 */
	public void writeAsZip( byte[] bytesRead, String fileEntry ) throws IOException {

		String absoluteZipFIleName = zipDir + FS + zipFilename;
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(absoluteZipFIleName));
	
		ZipEntry entry = new ZipEntry( fileEntry );
		zos.putNextEntry( entry );
		
		zos.write(bytesRead);
		
		zos.closeEntry();		
		zos.close();
	}
	
	public void writeAsText( String filename, byte[] bytesRead ) throws IOException{ 
		FileOutputStream fos = new FileOutputStream( new File( zipDir +  FS + filename ));
		fos.write( bytesRead );
		fos.close();
	}
	
}
