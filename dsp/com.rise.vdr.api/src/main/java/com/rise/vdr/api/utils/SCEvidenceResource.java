package com.rise.vdr.api.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ri.se.ipfsj.v2.DocumentManager;
import org.ri.se.selectivedisclosure.vc.VerifiableCredential;
import org.ri.se.selectivedisclosure.vc.VerifiableCredentialManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.ri.se.commonentities.EvidenceData;
import com.rise.vdr.api.persistance.SCEvidence;
import com.rise.vdr.api.persistance.SCEvidenceList;
import com.rise.vdr.api.persistance.SCEvidenceService;

import io.ipfs.api.IPFS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/scevidence")
@Api(value = "SCEvidence by RRB", description = "API used to provide CRUD operations for SCEvidence service ")
public class SCEvidenceResource {

	private SCEvidenceService scevidenceService;

	public SCEvidenceResource(SCEvidenceService scevidenceService) {
		this.scevidenceService = scevidenceService;
	}

	@GET
	@Path("/{eid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCEvidence]!", notes = "Returns instance of SCEvidence stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("eid") final String eid) {
		SCEvidence scevidence = this.scevidenceService.get(eid);
		if (java.util.Objects.isNull(scevidence)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(scevidence).build();
	}

	@GET
	@Path("/fetchby/transaction/{transactionID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCEvidence]!", notes = "Returns instance of SCEvidence stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByTransactionID(@PathParam("transactionID") final String transactionID) {
		List<SCEvidence> scevidence = this.scevidenceService.getByTransactionID(transactionID);
		if (java.util.Objects.isNull(scevidence)) {
			System.out.println(" Error - Not found !");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(scevidence).build();
	}

	@GET
	@Path("/fetchby/transaction/data/{transactionID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCEvidence]!", notes = "Returns instance of SCEvidence stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByTransactionIDByData(@PathParam("transactionID") final String transactionID) {
		List<SCEvidence> scevidence = this.scevidenceService.getByTransactionIDData(transactionID);
		if (java.util.Objects.isNull(scevidence)) {
			System.out.println(" Error - Not found !");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(scevidence).build();
	}

	@GET
	@Path("/download/evidence/{link}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCEvidence]!", notes = "Returns instance of SCEvidence stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getDownloadByLink(@PathParam("link") final String link) {

		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
		DocumentManager documentManager = new DocumentManager(ipfs);
		String data = new String(documentManager.download(link));
		
		
		if (Objects.isNull(data)) {
			System.out.println(" Error -  Not found !");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} else {

			// Verify and extract contents
			VerifiableCredentialManager credentialManager = new VerifiableCredentialManager();
			VerifiableCredential jsonVC = new VerifiableCredential(data);
			try {
				Map<String, Object> claims = jsonVC.getClaims();
				StringBuffer stringBuffer = new StringBuffer();
				
				Object evidence = claims.get("evidence");
				
				Map<String, Object> evdMap = (Map<String, Object> )evidence;
				
				stringBuffer.append("{");
				Iterator<String> keySet = evdMap .keySet().iterator();
				while(keySet.hasNext() ){
					Object value = evdMap .get(keySet.next());
					if(value instanceof Map) {
						Map<String, Object> valueSalted = (Map<String, Object>)value;
						stringBuffer.append("\""+ valueSalted.get("name")+"\" : ");
						stringBuffer.append("\""+ valueSalted.get("value")+"\", ");
					}					
				}
				String contents = stringBuffer.toString();
				contents = contents.substring(0, contents .length()-2);
				contents =contents +"}";
				DTOTransferedAnimal ta = new ObjectMapper().readValue(contents, DTOTransferedAnimal.class);
				EvidenceData dvidenceData = new EvidenceData();
				dvidenceData.setContents(ta);
				return Response.status(Response.Status.OK).entity(dvidenceData).build();
			} catch (Exception exp) {
				System.out.println(" Error - Not found !" + exp.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	@GET
	@Path("/download/evidence/slaughter/{link}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCEvidence]!", notes = "Returns instance of SCEvidence stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getDownloadSlaughterhouseByLink(@PathParam("link") final String link) {

		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
		DocumentManager documentManager = new DocumentManager(ipfs);
		String data = new String(documentManager.download(link));
		if (Objects.isNull(data)) {
			System.out.println(" Error - Not found !");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} else {

			// Verify and extract contents
			VerifiableCredentialManager credentialManager = new VerifiableCredentialManager();
			VerifiableCredential jsonVC = new VerifiableCredential(data);
			try {
				Map<String, Object> claims = jsonVC.getClaims();
				String json = new ObjectMapper().writeValueAsString(claims);
				byte [] b64 = Base64.getEncoder().encode(json.getBytes());
				EvidenceData value = new EvidenceData();
				value.setContents(new String(b64));
				return Response.status(Response.Status.OK).entity(value).build();
			} catch (Exception exp) {
				System.out.println(" Error - Not found !" + exp.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	@GET
	@Path("/download/evidence/opendata/{link}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCEvidence]!", notes = "Returns instance of SCEvidence stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getDownloadOpenByLink(@PathParam("link") final String link) {

		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
		DocumentManager documentManager = new DocumentManager(ipfs);
		String data = new String(documentManager.download(link));
		if (Objects.isNull(data)) {
			System.out.println(" Error - Not found !");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} else {

			// Verify and extract contents
			VerifiableCredentialManager credentialManager = new VerifiableCredentialManager();
			VerifiableCredential jsonVC = new VerifiableCredential(data);
			try {
				Map<String, Object> claims = jsonVC.getClaims();
				String json = new ObjectMapper().writeValueAsString(claims);
				Files.write(json.getBytes(), new File("temp.json"));
				try {
		            File file = new File("temp.json");
		            if (!file.exists()) {
		                throw new WebApplicationException("File not found", Response.Status.NOT_FOUND);
		            }

		            return Response.ok(file)
		                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
		                    .build();
		        } catch (Exception e) {
		            return Response.serverError().entity("Error downloading file: " + e.getMessage()).build();
		        }				
			} catch (Exception exp) {
				System.out.println(" Error - Not found !" + exp.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored SCEvidence!", notes = "Get list of all stored SCEvidence!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<SCEvidence> list = this.scevidenceService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SCEvidenceList scevidenceList = new SCEvidenceList();
		for (SCEvidence scevidence : list) {
			scevidenceList.add(scevidence);
		}
		return Response.status(Response.Status.OK).entity(scevidenceList).build();
	}

	@DELETE
	@Path("/{eid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("eid") final String eid) {
		int result = this.scevidenceService.delete(eid);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create SCEvidence!", notes = "Creating an instance of SCEvidence!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid SCEvidence scevidence) {
		int i = this.scevidenceService.post(scevidence);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{eid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update SCEvidence!", notes = "Updating an instance of SCEvidence !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("eid") final String eid, @Valid SCEvidence scevidence) {
		scevidence.setEid(eid);
		int i = this.scevidenceService.put(scevidence);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			System.out.println(" Error - Not found !");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
