package org.ri.se.trace.api.resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.ri.se.ipfsj.lsc.AttachedResourceHandler;
import org.ri.se.ipfsj.lsc.DataResourceProps;
import org.ri.se.ipfsj.lsc.DataResourceProps.SecurityLevel;
import org.ri.se.trace.api.main.TAPIConfig;
import org.ri.se.trace.api.resource.AttachedEvidenceResource.AttachedResourceStatus;
import org.ri.se.trace.api.trace.Edge;
import org.ri.se.trace.api.trace.EdgesList;
import org.ri.se.trace.api.trace.Node;
import org.ri.se.trace.api.trace.NodeList;
import org.ri.se.trace.api.trace.TraceGraph;
import org.ri.se.trace.api.utils.IOUtils;
import org.ri.se.trace.api.utils.ResourceDelegator;
import org.ri.se.trace.api.utils.Transaction;
import org.ri.se.vt.blockchain.AccountsManager;
import org.ri.se.vt.blockchain.exceptions.CredentialException;
import org.ri.se.vt.blockchain.exceptions.EtherGenericException;
import org.ri.se.vt.blockchain.exceptions.TraceabilityException;
import org.ri.se.vt.v1.Record;
import org.ri.se.vt.v1.RecordList;
import org.ri.se.vt.v1.RecordStatus;
import org.ri.se.vt.v1.RecordStatusList;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ri.se.trace.persistant.AttachedResource;
import ri.se.trace.persistant.AttachedResourceService;

@Path("/tapi/v2")
@Api(value = "/tapi", description = "Traceability API")
public class TraceResource {

	private static final Logger logger_ = Logger.getLogger(TraceResource.class);
	private TAPIConfig tapiConfig;
	private AttachedResourceService attachedResourceService;

	public TraceResource(TAPIConfig tapiConfig, AttachedResourceService attachedResourceService) {
		this.tapiConfig = tapiConfig;
		this.attachedResourceService = attachedResourceService;
	}

	@Context
	protected HttpServletResponse response;
	@Context
	protected HttpHeaders headers;

	@GET
	@Path("/{id}/{no}")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the Record of a given identity and number !", notes = "Return a Record !", response = String.class, responseContainer = "Record")
	public Record get(@PathParam("id") final String id, @PathParam("no") final long no) {
		logger_.info("===> Request received at /{id}/{no} : /"+id+"/"+no);
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			return resourceDelegator.get(id, no - 1);
		} catch (EtherGenericException e) {
			logger_.error("===> Problem when extracting records.");
			throw new WebApplicationException("Problem when extracting records. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);

		}
	}

	@GET
	@Path("/{id}/last")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the last Record of a given identity and number !", notes = "Return a last Record !", response = String.class, responseContainer = "Record")
	public Record getLast(@PathParam("id") final String id) {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			long size = resourceDelegator.size(id);
			if (size != 0) {
				Record rec = resourceDelegator.get(id, size - 1);
				if (size == 1) {
					return rec;
				} else {
					Record temp = resourceDelegator.get(id, 0);
					rec.setParents(temp.getParents());
					return rec;
				}
			}
			logger_.error("===> No record found .");
			throw new WebApplicationException("No record found !", Status.NOT_FOUND);

		} catch (EtherGenericException e) {
			logger_.error("===> Problem when extracting records.");
			throw new WebApplicationException("Problem when extracting records. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);

		}
	}

	@GET
	@Path("/{id}")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the list of Records stored against an identity !", notes = "Return a RecordList !", response = String.class, responseContainer = "RecordList")
	public RecordList get(@PathParam("id") final String id) {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			return resourceDelegator.get(id);
		} catch (EtherGenericException e) {
			logger_.error("===> Problem when extracting records.");
			throw new WebApplicationException("Problem when extracting records. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);

		}
	}

	@GET
	@Path("/tracegraph/{id}")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the list of Records stored against an identity !", notes = "Return a RecordList !", response = String.class, responseContainer = "RecordList")
	public TraceGraph tracegraphGet(@PathParam("id") final String id) {
		Credentials cred = manageCredentials();
		try {

			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			RecordList rl = resourceDelegator.get(id);
			NodeList nodeList = new NodeList();
			NodeList recMgt = new NodeList();
			EdgesList edgesList = new EdgesList();
			for (int i = 0; i < rl.size(); i++) {
				int currentSize = nodeList.size();
				Record r = rl.get(i);
				Node node = new Node();
				currentSize += 1;
				node.setId(currentSize);
				node.setLabel("" + (i + 1));
				if (cred.getAddress().equalsIgnoreCase(r.getReceiver())) {
					node.setGroup(3);
				} else {
					node.setGroup(1);
				}
				recMgt.add(node);
				nodeList.add(node);
				String data = r.getData();
				String[] evidence = data.split(";");

				for (int j = 0; j < evidence.length; j++) {
					// System.out.println("'"+evidence[j] +"'"+evidence[j].length());
					evidence[j] = evidence[j].trim();
					if (evidence[j] != null && evidence[j].length() != 0) {
						Node nodeEv = new Node();
						currentSize += 1;
						nodeEv.setId(currentSize);
						nodeEv.setLabel(evidence[j]);
						nodeEv.setGroup(2);
						nodeList.add(nodeEv);
						Edge edge = new Edge();
						edge.setFrom(currentSize);
						edge.setTo(node.getId());
						edgesList.add(edge);
					}
				}
			}
			for (int i = 1; i < recMgt.size(); i++) {
				Edge edge = new Edge();
				edge.setFrom(recMgt.get(i - 1).getId());
				edge.setTo(recMgt.get(i).getId());
				edgesList.add(edge);
			}

			TraceGraph traceGraph = new TraceGraph();
			traceGraph.setNodes(nodeList);
			traceGraph.setEdges(edgesList);
			return traceGraph;
		} catch (Exception e) {
			logger_.error("===> Problem when creating graph data .");
			throw new WebApplicationException("Problem when creating graph data . \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);

		}
	}

	@GET
	@Path("/size/{id}")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the size of record list stored against an identity ", notes = "Return a long value wchih gives size of the records stored against an identity !", response = Long.class, responseContainer = "long")
	public long size(@PathParam("id") final String id) {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			return resourceDelegator.size(id);
		} catch (EtherGenericException e) {
			logger_.error("===> Problem when finding size of records against in given id.");
			throw new WebApplicationException(
					"Problem when finding size of records against in given id. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/status")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the status of all identities and their records !", notes = "Return the list of RecordStatus !", response = RecordStatusList.class, responseContainer = "RescordStatusList")
	public RecordStatusList getRescordStatus() {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			return resourceDelegator.getRescordStatusOfAllIds();
		} catch (TraceabilityException e) {
			logger_.error("===> Problem when extracting status of records !");
			throw new WebApplicationException("Problem when extracting status of records. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		} catch (EtherGenericException e) {
			logger_.error("===> Problem when extracting records.");
			throw new WebApplicationException("Problem when extracting records. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/status/{id}/{no}")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find status of the Record stored against an identity with specific number !", notes = "Return a RecordStatus of an identity with specific number !", response = RecordStatus.class, responseContainer = "RecordStatus")
	public RecordStatus getRescordStatus(@PathParam("id") final String id, @PathParam("no") final long no) {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			return resourceDelegator.getRescordStatus(id, no);
		} catch (EtherGenericException e) {
			logger_.error("===> Problem when extracting records.");
			throw new WebApplicationException("Problem when extracting records. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/status/{id}")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the status of all Records stored against an identity !", notes = "Return the status of all Records stored against an identity !", response = RecordStatusList.class, responseContainer = "RecordStatusList")
	public RecordStatusList getRescordStatus(@PathParam("id") final String id) {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			return resourceDelegator.getRescordStatus(id);
		} catch (EtherGenericException e) {
			logger_.error("===> Problem when extracting records.");
			throw new WebApplicationException("Problem when extracting records. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/all/size")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the size of all identities !", notes = "Return a long value which represents the size of all identties !", response = Long.class, responseContainer = "Long")
	public long totalIds() {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			return resourceDelegator.totalIds();
		} catch (EtherGenericException e) {
			logger_.error("===> Problem when extracting size of ids.");
			throw new WebApplicationException("Problem when extracting size of ids. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	private String getUser() {
		String owner = headers.getHeaderString("owner");
		if (Objects.isNull(owner)) {
			logger_.error("===> Unauthorized user cannot execute this function !");
			java.net.URI location = null;
			try {
				location = new java.net.URI(tapiConfig.getUrlWeb() + "/lscdash/forbidden.html");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(location);

			throw new WebApplicationException(Response.temporaryRedirect(location).build());
		}
		return owner;
	}

	private String getPassword() {
		String pass = headers.getHeaderString("pass");
		if (Objects.isNull(pass)) {
			logger_.error("===> Unauthorized user cannot execute this function !");

			java.net.URI location = null;
			try {
				location = new java.net.URI(tapiConfig.getUrlWeb() + "/lscdash/forbidden.html");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(location);

			throw new WebApplicationException(Response.temporaryRedirect(location).build());
		}
		return pass;
	}

	private Credentials manageCredentials() {
		try {
			return new AccountsManager().getCredentials(this.tapiConfig.getWalletDir(), getUser(), getPassword());
		} catch (CredentialException e) {
			logger_.error("===> Invalid credentials !");
			throw new WebApplicationException("Invalid credentials !", Status.UNAUTHORIZED);
		}
	}

	// =============================================================
	@GET
	@Path("/status/all")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the status of all Records stored against an identity !", notes = "Return the status of all Records stored against an identity !", response = RecordStatusList.class, responseContainer = "RecordStatusList")
	public RecordStatusList getRescordStatusOfAllIds() {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			RecordStatusList recordStatusList = resourceDelegator.getRescordStatusOfAllIds();
			// System.out.println(recordStatusList.size());
			return recordStatusList;
		} catch (EtherGenericException | TraceabilityException e) {
			logger_.error("===> Problem when extracting records.");
			throw new WebApplicationException("Problem when extracting records.  \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/status/own")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the status of all Records stored against an identity !", notes = "Return the status of all Records stored against an identity !", response = RecordStatusList.class, responseContainer = "RecordStatusList")
	public RecordStatusList getRescordStatusOfMyOwnedIds() {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			RecordStatusList recordStatusList = resourceDelegator.getRescordStatusOwnedByMe();
			System.out.println(recordStatusList.size());
			return recordStatusList;
		} catch (EtherGenericException | TraceabilityException e) {
			logger_.error("===> Problem when extracting records.");
			throw new WebApplicationException("Problem when extracting records.  \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("/own/size")
	@RolesAllowed("")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the size of all identities !", notes = "Return a long value which represents the size of all identties !", response = Long.class, responseContainer = "Long")
	public long ownIds() {
		Credentials cred = manageCredentials();
		try {
			ResourceDelegator resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
			RecordStatusList recordStatusList = resourceDelegator.getRescordStatusOwnedByMe();
			System.out.println(recordStatusList.size());
			return recordStatusList.size();
		} catch (EtherGenericException | TraceabilityException e) {
			logger_.error("===> Problem when extracting records for geting owner total ids.");
			throw new WebApplicationException(
					"Problem when extracting records for geting owner total ids. \n[" + e.getMessage() + "] !",
					Status.INTERNAL_SERVER_ERROR);
		}
	}

	// ===================
	@POST
	@RolesAllowed("")
	@Path("/transact")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Transaction of record  ! ", notes = "Transaction of record  !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response manage(@Valid Transaction transaction) {
		Credentials credentials = manageCredentials();
		List<AttachedResource> evidences = this.attachedResourceService.getAttachedResourceByIdentityStatusAndAddress(
				transaction.getIdentity(), credentials.getAddress(), AttachedResourceStatus.LOCAL.value);
		String ipfsEvidence = "";
		if (Objects.isNull(evidences) || evidences.size() == 0) {
			ipfsEvidence = "";
		} else {

			for (AttachedResource ar : evidences) {
				ipfsEvidence += ar.getIpfshash() + ";";
			}
		}
		Record record = new Record();
		record.setId(transaction.getIdentity());
		record.setData(ipfsEvidence);
		record.setOther(transaction.getOther());
		record.setParents("NONE");
		record.setReceiver(transaction.getReceiver());
		ResourceDelegator resourceDelegator = null;
		try {
			resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), credentials,
					this.tapiConfig.getUrlEther());
		} catch (EtherGenericException e2) {
			System.err.println("Ether connection problem !");
			Response.status(Response.Status.NOT_FOUND).build();
		}
		try {

			System.out.println(resourceDelegator.size(transaction.getIdentity()));
			RecordList list = resourceDelegator.get(transaction.getIdentity());
			TransactionReceipt result = resourceDelegator.set(record);

		} catch (EtherGenericException e) {
			logger_.error("===> Problems when processing uploaded AttachedResource !");
			e.printStackTrace();
			logger_.error(e);
			throw new WebApplicationException(
					"Problems when processing uploaded AttachedResource. May be IPFS is not running ! !");
		}
		return Response.ok().build();
	}

	// =============================================================
	@POST
	@Path("/identity")
	@RolesAllowed("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Provide a list of key values to create identity. 'parents' key should be part of these values !", notes = "Create a new identity based on the provided key-value list. 'parents' key should be part of these values !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !"),
			@ApiResponse(code = 401, message = "Unauthorized access !") })
	public Response identity(@Valid Hashtable<String, String> identity) {
		if (!identity.containsKey("parents")) {
			logger_.error("===> 'parents' key not found !");
			return Response.status(Response.Status.NOT_FOUND).entity("'parents' key not found !").build();
		}
		String email = getUser();
		AccountsManager accountsManager = new AccountsManager();
		try {
			if (!accountsManager.exists(tapiConfig.getWalletDir(), email)) {
				logger_.error("===> Account does not exist !");
				return Response.status(Response.Status.UNAUTHORIZED).entity("Account does not exist !").build();
			}
		} catch (CredentialException e3) {
			System.err.println("Authorization problem !");
			logger_.error("===> Account does not exist !");
			return Response.status(Response.Status.UNAUTHORIZED).entity("Account does not exist !").build();
		}
		Credentials cred = manageCredentials();

		ResourceDelegator resourceDelegator = null;
		try {
			resourceDelegator = new ResourceDelegator(tapiConfig.getTraceContract(), cred,
					this.tapiConfig.getUrlEther());
		} catch (EtherGenericException e2) {
			System.err.println("Ether connection problem !");
			Response.status(Response.Status.NOT_FOUND).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		String jonIdentity = null;
		try {
			jonIdentity = mapper.writeValueAsString(identity);
		} catch (JsonProcessingException e) {
			System.err.println("JSON problem !");
			Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		DataResourceProps file = null;

		IOUtils ioUtils = new IOUtils(email);
		String storedFilePath = null;
		try {
			storedFilePath = ioUtils.writeToFile(jonIdentity);
		} catch (IOException e1) {
			throw new WebApplicationException("Problems when writing identity file !");
		}

		file = new DataResourceProps();
		file.setPath(storedFilePath);
		file.setSecurityLevel(SecurityLevel.NONE);
		AttachedResourceHandler attachedResourceHandler = null;
		try {
			attachedResourceHandler = new AttachedResourceHandler(tapiConfig.getUrlIPFS());
			file = attachedResourceHandler.upload(file);
			System.out.println(file.getIpfsCID());
			// System.err.println("Delete folloiwng line of code !");
			// file.setIpfsCID("gereisCID" + System.currentTimeMillis());
		} catch (Exception ipfsExp) {
			logger_.error("===> Problems when processing uploaded AttachedResource !");
			ipfsExp.printStackTrace();
			logger_.error(ipfsExp);
			throw new WebApplicationException(
					"Problems when processing uploaded AttachedResource. May be IPFS is not running ! !");
		}
		// file.setIpfsCID(""+System.currentTimeMillis());

		Record record = new Record();
		record.setData("");
		record.setId(file.getIpfsCID());
		record.setOther("");
		record.setSender(cred.getAddress());
		record.setParents(identity.get("parents"));
		record.setReceiver(cred.getAddress());
		try {
			TransactionReceipt result = resourceDelegator.set(record);
			System.out.println(resourceDelegator.totalIds());
		} catch (EtherGenericException e) {
			logger_.error("===> Problems when processing uploaded Identity !");
			e.printStackTrace();
			logger_.error(e);
			throw new WebApplicationException(
					"Problems when processing uploaded Identity. May be IPFS is not running ! !");
		}
		return Response.ok().build();
	}
}
