package com.rise.vdr.api.utils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ri.se.commonentities.EvidenceData;
import com.ri.se.commonentities.transporter.TransportedToSlaughter;
import com.rise.vdr.api.aggregation.DownloadAggregatedData;
import com.rise.vdr.api.persistance.SCEvidenceService;
import com.rise.vdr.api.persistance.SCSecretKeyService;
import com.rise.vdr.api.persistance.SCTransaction;
import com.rise.vdr.api.persistance.SCTransactionList;
import com.rise.vdr.api.persistance.SCTransactionService;
import com.rise.vdr.api.transaction.EvidenceContainer;
import com.rise.vdr.api.transaction.EvidenceObject;
import com.rise.vdr.api.transaction.TransactionManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Path("/v1/sctransaction")
@Api(value = "SCTransaction by RRB", description = "API used to provide CRUD operations for SCTransaction service ")
public class SCTransactionResource {

	private SCTransactionService sctransactionService;

	private SCEvidenceService scevidenceService;
	private SCSecretKeyService scsecretkeyService;

	public SCTransactionResource(SCTransactionService sctransactionService, SCEvidenceService scevidenceService,
			SCSecretKeyService scsecretkeyService) {
		this.sctransactionService = sctransactionService;
		this.scevidenceService = scevidenceService;
		this.scsecretkeyService = scsecretkeyService;

	}

	@GET
	@Path("/{transactionID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SCTransaction]!", notes = "Returns instance of SCTransaction stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("transactionID") final String transactionID) {
		SCTransaction sctransaction = this.sctransactionService.get(transactionID);
		if (java.util.Objects.isNull(sctransaction)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(sctransaction).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored SCTransaction!", notes = "Get list of all stored SCTransaction!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<SCTransaction> list = this.sctransactionService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SCTransactionList sctransactionList = new SCTransactionList();
		for (SCTransaction sctransaction : list) {
			sctransactionList.add(sctransaction);
		}
		return Response.status(Response.Status.OK).entity(sctransactionList).build();
	}

	@DELETE
	@Path("/{transactionID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("transactionID") final String transactionID) {
		int result = this.sctransactionService.delete(transactionID);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create SCTransaction!", notes = "Creating an instance of SCTransaction!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid SCTransaction sctransaction) {
		int i = this.sctransactionService.post(sctransaction);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Path("/{transactionID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update SCTransaction!", notes = "Updating an instance of SCTransaction !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("transactionID") final String transactionID, @Valid SCTransaction sctransaction) {
		sctransaction.setTransactionID(transactionID);
		int i = this.sctransactionService.put(sctransaction);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	// ============================================
	@POST
	@Path("/submit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Transfer animal !", notes = "Transfer animals to submit transaction !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response submit(@Valid SubmitTransaction submitTransaction) {
		DownloadAggregatedData downloadAggregatedData = new DownloadAggregatedData();
		String ip = "http://localhost";

		for (String animalID : submitTransaction.getAnimals()) {
			EvidenceContainer ec;
			try {
				ec = downloadAggregatedData.fetchRecord(ip, animalID);
				Map<String, EvidenceContainer> map = new HashMap<String, EvidenceContainer>();
				map.put(animalID, ec);
				new TransactionManager(sctransactionService, scevidenceService, scsecretkeyService)
						.manage("/ip4/127.0.0.1/tcp/5001", submitTransaction.getTo(), submitTransaction.getFrom(), map);
				String resource = "/v1/registeranimal/status/" + animalID;
				downloadAggregatedData.updateAnimalStatus(ip, 9002, resource);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TRANSFERED

		}
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Path("/submitwithal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Transfer animal !", notes = "Transfer animals to submit transaction !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response submitWithAccessLevel(@Valid AccessLevelEntity accessLevelEntity) {

		DownloadAggregatedData downloadAggregatedData = new DownloadAggregatedData();
		String ip = "http://localhost";
		Map<String, Object> animals = accessLevelEntity.getAnimals();

		Map<String, ALEvidenceObjectContainer> finalEvidence = new HashMap<String, ALEvidenceObjectContainer>();

		animals.forEach((key, value) -> {
			ALEvidenceObjectContainer alec = new ALEvidenceObjectContainer();
			EvidenceContainer ec = null;
			try {
				ec = downloadAggregatedData.fetchRecord(ip, key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			Map<String, EvidenceObject> objectHolder = new HashMap<String, EvidenceObject>();
			ec.forEach((evd) -> {
				objectHolder.put(evd.getUniqueNo(), evd);
			});
			Map<String, String> evidences = (Map<String, String>) value;
			evidences.put("DATA", "ALL");
			evidences.forEach((keye, valuee) -> {
				// key
				System.out.println(keye + " " + valuee);
				EvidenceObject temp = objectHolder.get(keye);
				ALEvidenceObject fe = new ALEvidenceObject();
				fe.setEvidence(temp);
				fe.setAccessLevel(valuee);
				alec.add(fe);

			});
			finalEvidence.put(key, alec);
		});
		new TransactionManager(sctransactionService, scevidenceService, scsecretkeyService).manageWithAccess(
				"/ip4/127.0.0.1/tcp/5001", accessLevelEntity.getTransporter(), accessLevelEntity.getSlaughterhouse(),
				accessLevelEntity.getFrom(), finalEvidence);
		finalEvidence.forEach((k, v) -> {
			String resource = "/v1/registeranimal/status/" + k;
			downloadAggregatedData.updateAnimalStatus(ip, 9002, resource);
		});

		try {

			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@POST
	@Path("/submit/to/slaughter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Transfer animal !", notes = "Transfer animals to submit transaction !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response submitToSlaughter(@Valid EvidenceData ed) {

		TransportedToSlaughter transportedToSlaughter = null;
		System.out.println(ed.getContents().toString());

		String temp = new String(Base64.getDecoder().decode(ed.getContents().toString()));
		transportedToSlaughter = new T2SParser().parser(ed);
		if (Objects.isNull(transportedToSlaughter)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		EvidenceContainer ec = new EvidenceContainer();
		EvidenceObject evidenceObject = new EvidenceObject();
		evidenceObject.setEvidenceType("DATA");
		evidenceObject.setEvidence(transportedToSlaughter);
		ec.add(evidenceObject);
		String ip = "http://localhost";
		try {
			Map<String, EvidenceContainer> map = new HashMap<String, EvidenceContainer>();
			map.put(transportedToSlaughter.getTransferedAnimal().getAnimalID(), ec);
			new TransactionManager(sctransactionService, scevidenceService, scsecretkeyService).manage(
					"/ip4/127.0.0.1/tcp/5001", transportedToSlaughter.getTo(), transportedToSlaughter.getFrom(), map);

			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TRANSFERED

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}

	// SELECT e.department, e.employee_name, e.salary

	@GET
	@Path("/byreceiver/{transactionReceiver}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance transfered to the given identity  [SCTransaction]!", notes = "!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getMyAnimals(@PathParam("transactionReceiver") final String transactionReceiver) {
		List<SCTransaction> sctransactions = this.sctransactionService.getOwnedAnimals(transactionReceiver);
		if (java.util.Objects.isNull(sctransactions)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(sctransactions).build();
	}

	@GET
	@Path("/updated/byreceiver/{timeinmilli}/{transactionReceiver}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance transfered to the given identity  [SCTransaction]!", notes = "!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getMyUpdatedAnimals(@PathParam("timeinmilli") final String timeinmilli,
			@PathParam("transactionReceiver") final String transactionReceiver) {

		if (timeinmilli == null || timeinmilli.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		try {

			List<SCTransaction> sctransactions = this.sctransactionService.getOwnedUpdatedAnimals(transactionReceiver,
					Long.parseLong(timeinmilli));
			if (java.util.Objects.isNull(sctransactions)) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			return Response.status(Response.Status.OK).entity(sctransactions).build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	@GET
	@Path("/trace/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance transfered to the given identity  [SCTransaction]!", notes = "!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response trace(@PathParam("animalID") final String animalID) {

		AnimalTraceability animalTraceability = new AnimalTraceability(sctransactionService, scevidenceService,scsecretkeyService);
		ArrayList<String> tace = new ArrayList<String>();
		animalTraceability.trace(animalID, tace);
		return Response.status(Response.Status.OK).entity(tace).build();
	}

	@GET
	@Path("/trace/transaction/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance transfered to the given identity  [SCTransaction]!", notes = "!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response traceTrasaction(@PathParam("animalID") final String timeinmilli,
			@PathParam("transactionReceiver") final String transactionReceiver) {

		if (timeinmilli == null || timeinmilli.isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		try {

			List<SCTransaction> sctransactions = this.sctransactionService.getOwnedUpdatedAnimals(transactionReceiver,
					Long.parseLong(timeinmilli));
			if (java.util.Objects.isNull(sctransactions)) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
			return Response.status(Response.Status.OK).entity(sctransactions).build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

	}

	// ============================================================================
	@GET
	@Path("/fetch/records/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Transfer animal !", notes = "Transfer animals to submit transaction !", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response fetchBuild(@PathParam("animalID") final String animalID) {
		DownloadAggregatedData downloadAggregatedData = new DownloadAggregatedData();
		String ip = "http://localhost";
		try {
			EvidenceContainer ec = downloadAggregatedData.fetchRecord(ip, animalID);
			return Response.status(Response.Status.OK).entity(ec).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
