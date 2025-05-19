package org.ri.se.trace.api.resource;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.ri.se.ipfsj.lsc.AttachedResourceHandler;
import org.ri.se.ipfsj.lsc.DataResourceProps;
import org.ri.se.ipfsj.lsc.DataResourceProps.SecurityLevel;
import org.ri.se.trace.api.main.TAPIConfig;
import org.ri.se.trace.api.utils.IOUtils;
import org.ri.se.trace.api.utils.ResourceDelegator;
import org.ri.se.vt.blockchain.AccountsManager;
import org.ri.se.vt.blockchain.exceptions.CredentialException;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.web3j.crypto.Credentials;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ri.se.trace.persistant.AttachedResource;
import ri.se.trace.persistant.AttachedResourceService;
import ri.se.trace.persistant.AttachedResourceTransform;
import ri.se.trace.persistant.common.AttachedResourceCO;
import ri.se.trace.persistant.common.AttachedResourceCOList;

@Path("/tapi/v1/local/attres")
@Api(value = "/tapi", description = "Traceability API")
public class AttachedEvidenceResource {

	private static final Logger logger_ = Logger.getLogger(AttachedEvidenceResource.class);
	private TAPIConfig tapiConfig;
	private AttachedResourceService attachedResourceService;

	public enum AttachedResourceStatus {
		UPLOADED("Uploaded"), LOCAL("Local");

		String value;

		AttachedResourceStatus(String _val) {
			value = _val;
		}

		String value() {
			return value;
		}
	}

	public AttachedEvidenceResource(TAPIConfig tapiConfig, AttachedResourceService attachedResourceService) {
		this.tapiConfig = tapiConfig;
		this.attachedResourceService = attachedResourceService;
	}

	@Context
	protected HttpServletResponse response;
	@Context
	protected HttpHeaders headers;

	@GET
	@Path("/{ipfshash}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the Attached Resource based on ipfshash !", notes = "Return an AttachedResource !", response = AttachedResource.class, responseContainer = "AttachedResource")
	public AttachedResourceCO getAttachedResource(@PathParam("ipfshash") final String ipfshash) {
		return new AttachedResourceTransform().to(this.attachedResourceService.getAttachedResource(ipfshash));
	}

	@GET
	@Path("/owner/ownslocal")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the Attached Resource owned by owner !", notes = "Return an AttachedResource against given owner !", response = AttachedResourceCOList.class, responseContainer = "AttachedResourceCOList")
	public AttachedResourceCOList getResourceByOwner(@PathParam("owner") final String owner) {
		Credentials credentials = manageCredentials();
		return new AttachedResourceTransform()
				.to(this.attachedResourceService.getResourceByOwner(credentials.getAddress()));
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete evidence !", notes = "Delete evidence !", response = AttachedResourceCOList.class, responseContainer = "AttachedResourceCOList")
	public AttachedResourceCO delete(@PathParam("id") final String id) {
		Credentials credentials = manageCredentials();
		return new AttachedResourceTransform().to(this.attachedResourceService.delete(id));
	}

	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the Attached Resource based on the given name !", notes = "Return a Record !", response = AttachedResourceCOList.class, responseContainer = "AttachedResourceCOList")
	public AttachedResourceCOList getAttachedResourceByName(@PathParam("name") final String name) {
		return new AttachedResourceTransform().to(this.attachedResourceService.getAttachedResourceByName(name));
	}

	@GET
	@Path("/identity/{identity}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the Attached Resources registered against an identity !", notes = "Return Attached Resources registered against an identity !", response = AttachedResourceCOList.class, responseContainer = "AttachedResourceCOList")
	public AttachedResourceCOList getAttachedResourceByIdentity(@PathParam("identity") final String identity) {
		return new AttachedResourceTransform().to(this.attachedResourceService.getAttachedResourceByIdentity(identity));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the list of all AttachedResources stored in the local storage !", notes = "Return all AttachedResources stored in the local storage !", response = AttachedResourceCOList.class, responseContainer = "AttachedResourceCOList")
	public AttachedResourceCOList list() {
		return new AttachedResourceTransform().to(this.attachedResourceService.list());
	}

	@POST
	@Path("/min")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Upload an Attached Resource/Document/Evidence ", notes = "Uppload an Attached Resource/Document/Evidence", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response uploadEvidenceMin(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("identity") String identity,
			@FormDataParam("token") String token, @FormDataParam("typeRes") String typeRes,
			@FormDataParam("securityLevel") String securityLevel, @FormDataParam("description") String description) {
		
		String[] up = process(token);
		Credentials cred = manageCredentials(up);
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			System.out.println(resourceDelegator.owner(identity));
			System.out.println("" + cred.getAddress());
			if (!resourceDelegator.owner(identity).equalsIgnoreCase(cred.getAddress())) {
				logger_.error("You are not authorized to attach resource with the specified identity !");
				throw new WebApplicationException(
						"You are not authorized to attach resource with the specified identity ! !",
						Status.UNAUTHORIZED);
			}
		} catch (EtherGenericException e) {
			logger_.error("Problem when checking the ownership of identity !");
			throw new WebApplicationException(
					"Problem when checking the ownership of identity. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		}
		IOUtils ioUtils = new IOUtils(cred.getAddress());
		DataResourceProps file = null;
		// System.err.println("ENABLE FOLLOWING COMMENTS !" + this.getClass());

		// ============================================================
		try {
			String storedFilePath = ioUtils.write(uploadedInputStream, fileDetail);
			AttachedResourceHandler attachedResourceHandler = new AttachedResourceHandler(tapiConfig.getUrlIPFS());
			file = new DataResourceProps();
			file.setPath(storedFilePath);
			file.setSecurityLevel(SecurityLevel.NONE);
			file = attachedResourceHandler.upload(file);
		} catch (Exception e) {
			logger_.error("Problems when processing uploaded AttachedResource !");
			logger_.error(e);
			throw new WebApplicationException("Problems when processing uploaded AttachedResource !");
		}

		if (!Objects.isNull(attachedResourceService.getAttachedResource(file.getIpfsCID()))) {
			logger_.info("AttachedResource uploaded successfully !");
			java.net.URI location;
			try {
				location = new java.net.URI(tapiConfig.getUrlWeb() + "/lscweb/index.html");
				System.out.println(location);
				throw new WebApplicationException(Response.temporaryRedirect(location).build());
			} catch (URISyntaxException e) {
				logger_.error(" Problems when uploading an AttachedResource !");
				throw new WebApplicationException("Problems when uploading an AttachedResource !");
			}
		} else {
			// ============================================================
			AttachedResource attachedResource = new AttachedResource();
			attachedResource.setDescription(description);
			attachedResource.setIdentity(identity);
			// System.err.println("CHECK FOLLOWING CODE !" + this.getClass());
			attachedResource.setIpfshash(file.getIpfsCID());
			attachedResource.setSecurityLevel(SecurityLevel.NONE.value());
			attachedResource.setName(fileDetail.getFileName());
			attachedResource.setOwner(cred.getAddress());
			attachedResource.setType(typeRes);
			attachedResource.setUploadTime(new Date());
			attachedResource.setStatus(AttachedResourceStatus.LOCAL.value());
			int result = attachedResourceService.post(attachedResource);
			if (result > 0) {
				logger_.info("AttachedResource uploaded successfully !");
				java.net.URI location;
				try {
					;
					location = new java.net.URI(tapiConfig.getUrlWeb() + "/lscweb/index.html");
					System.out.println(location);
					throw new WebApplicationException(Response.temporaryRedirect(location).build());
				} catch (URISyntaxException e) {
					logger_.error(" Problems when uploading an AttachedResource !");
					throw new WebApplicationException("Problems when uploading an AttachedResource !");
				}
			} else {
				logger_.error(" Problems when uploading an AttachedResource !");
				throw new WebApplicationException("Problems when uploading an AttachedResource !");
			}
		}
	}

	private String getUser() {
		String owner = headers.getHeaderString("owner");
		if (Objects.isNull(owner)) {
			logger_.error("Unauthorized user cannot execute this function !");
			java.net.URI location = null;
			try {
				location = new java.net.URI(tapiConfig.getUrlWeb() + "/lscdash/forbidden.html");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(location);

			throw new WebApplicationException(Response.temporaryRedirect(location).build());
		}
		return owner;
	}

	private String getPassword() {
		String pass = headers.getHeaderString("pass");
		if (Objects.isNull(pass)) {
			logger_.error("Unauthorized user cannot execute this function !");

			java.net.URI location = null;
			try {
				location = new java.net.URI(tapiConfig.getUrlWeb() + "/lscdash/forbidden.html");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(location);

			throw new WebApplicationException(Response.temporaryRedirect(location).build());
		}
		return pass;
	}

	private Credentials manageCredentials() {
		try {
			return new AccountsManager().getCredentials(this.tapiConfig.getWalletDir(), getUser(), getPassword());
		} catch (CredentialException e) {
			logger_.error("Invalid credentials !");
			throw new WebApplicationException("Invalid credentials !", Status.UNAUTHORIZED);
		}
	}

	private Credentials manageCredentials(String up[]) {
		try {
			return new AccountsManager().getCredentials(this.tapiConfig.getWalletDir(), up[0], up[1]);
		} catch (CredentialException e) {
			logger_.error("Invalid credentials !");
			throw new WebApplicationException("Invalid credentials !", Status.UNAUTHORIZED);
		}
	}

	@OPTIONS
	@Path("/hello")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	public Response downloadFileWithGetOption() {
		System.out.println("DID : ");
		System.out.println("====================");

		ResponseBuilder response = Response.ok("");
		response.header("Access-Control-Allow-Origin", "*");
		response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		return response.build();
	}

	private String[] process(String token) {
		String[] up = new String[2];
		String value = token;
		if (Objects.isNull(value)) {
			throw new WebApplicationException(
					Response.status(Response.Status.UNAUTHORIZED).entity("You are not authorized to access !").build());
		}
		value = value.trim();
		String temp = value.substring(0, value.indexOf(" "));
		temp = temp.trim().toLowerCase();

		if (temp.equalsIgnoreCase("basic")) {
			temp = value.substring(value.indexOf(" "));
			byte ss[] = org.bouncycastle.util.encoders.Base64.decode(temp);

			temp = new String(ss);
			if (temp.contains(":")) {
				String username = temp.substring(0, temp.indexOf(":"));
				String password = temp.substring(temp.indexOf(":") + 1);
				up[0] = username;
				up[1] = password;
				return up;
			} else {
				throw new WebApplicationException(
						Response.status(Response.Status.UNAUTHORIZED).entity("Bad request !").build());
			}
		} else {
			throw new WebApplicationException(
					Response.status(Response.Status.UNAUTHORIZED).entity("Bad request !").build());
		}
	}
}
