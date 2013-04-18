package com.goraksh.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.MultiPart;

@Path("/import")
public class ImportResource {
	/**
	 * Class Name for the current class.
	 */
	private static final String CLASS_NAME = "egain.ws.v11.authoring.resources.CasebaseImport";

	/**
	 * File Name of the current file.
	 */
	private static final String FILE_NAME = "CasebaseImport.java";

	/**
	 * {@link Logger} for the current package.
	 */
	private static Logger logger = Logger
			.getLogger("com.goraksh.rest");

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML,
			MediaType.TEXT_PLAIN })
	public Response createCasebaseImport(MultiPart multipart) {
		
		String message = null;
		String name = null;
		try {
			BodyPartEntity importEntity = (BodyPartEntity) multipart
					.getBodyParts().get(0).getEntity();
			InputStream in = importEntity.getInputStream();

			System.out.println("starting import read");
			name = getLine(in);
			// String name = csim.getCasebaseName() ;
			// System.out.println( ":get cassebase name :" +
			// csim.getCasebaseName() );

			// get the second part which is the project logo
			BodyPartEntity bpe = (BodyPartEntity) multipart.getBodyParts()
					.get(1).getEntity();
			String id = UUID.randomUUID().toString();
			boolean isProcessed = false;

			InputStream source = bpe.getInputStream();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(source));
			String line = null;

			while ((line = br.readLine()) != null) {
				System.out.println("printing :" + line);
			}
		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
			
			return Response
			.status(Response.Status.NOT_FOUND)
			.entity  ( "Failure Message : "+ e.getMessage() + "Get casebase name :"
					+ name).type(MediaType.TEXT_PLAIN).build();
		}
		return Response
				.status(Response.Status.ACCEPTED)
				.entity("Attachements processed successfully. Get casebase name :"
						+ name).type(MediaType.TEXT_PLAIN).build();

	}

	@GET
	@Produces({ MediaType.TEXT_XML, MediaType.TEXT_PLAIN })
	public Response getCasebaseImport() {
		return Response.status(Response.Status.ACCEPTED)
				.entity("Attachements processed successfully.")
				.type(MediaType.TEXT_PLAIN).build();
	}

	@DELETE
	@Produces({ MediaType.TEXT_XML, MediaType.TEXT_PLAIN })
	public Response delCasebaseImport() {
		return Response.status(Response.Status.ACCEPTED)
				.entity("Attachements processed successfully.")
				.type(MediaType.TEXT_PLAIN).build();
	}

	/**
	 * 
	 * @param in
	 * @return
	 */
	String getLine(InputStream in) throws IOException {
		BufferedReader bf = null;
		bf = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = bf.readLine()) != null)
			System.out.println(line);

		return "casebase-1";

	}

}
