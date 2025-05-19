package com.rise.carrier.slaughterhouse.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ri.se.commonentities.EvidenceData;
import com.ri.se.commonentities.transporter.TransportedToSlaughter;
import com.ri.se.transferedanimal.persistance.TransferedAnimal;
import com.ri.se.transferedanimal.persistance.TransferedAnimalService;
import com.ri.se.transporter.uploader.Representation;
import com.ri.se.transporter.uploader.RestClient;
import com.rise.carrier.slaughterhouse.persistance.CarrierSlaughterhouse;
import com.rise.carrier.slaughterhouse.persistance.CarrierSlaughterhouseList;
import com.rise.carrier.slaughterhouse.persistance.CarrierSlaughterhouseService;
import com.rise.carrier.slaughterhouse.persistance.SHCarrier;
import com.rise.carrier.slaughterhouse.persistance.SHCarrierService;

import carrier.persistance.Carrier;
import carrier.persistance.CarrierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import transportedcattle.dto.CarrierAssignment;

@Path("/v1/carrierslaughterhouse")
@Api(value = "CarrierSlaughterhouse by RRB", description = "API used to provide CRUD operations for CarrierSlaughterhouse service ")
public class CarrierSlaughterhouseResource {

	private CarrierSlaughterhouseService carrierslaughterhouseService;
	private SHCarrierService shCarrierService;
	private CarrierService carrierService;
	private TransferedAnimalService transferedAnimalService;

	public CarrierSlaughterhouseResource(CarrierSlaughterhouseService carrierslaughterhouseService,
			SHCarrierService shCarrierService, CarrierService carrierService,
			TransferedAnimalService transferedAnimalService) {
		this.carrierslaughterhouseService = carrierslaughterhouseService;
		this.shCarrierService = shCarrierService;
		this.carrierService = carrierService;
		this.transferedAnimalService = transferedAnimalService;
	}

	@GET
	@Path("/{tripNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [CarrierSlaughterhouse]!", notes = "Returns instance of CarrierSlaughterhouse stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("tripNo") final String tripNo) {
		CarrierSlaughterhouse carrierslaughterhouse = this.carrierslaughterhouseService.get(tripNo);
		if (java.util.Objects.isNull(carrierslaughterhouse)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).entity(carrierslaughterhouse).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored CarrierSlaughterhouse!", notes = "Get list of all stored CarrierSlaughterhouse!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<CarrierSlaughterhouse> list = this.carrierslaughterhouseService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		CarrierSlaughterhouseList carrierslaughterhouseList = new CarrierSlaughterhouseList();
		for (CarrierSlaughterhouse carrierslaughterhouse : list) {
			carrierslaughterhouseList.add(carrierslaughterhouse);
		}
		return Response.status(Response.Status.OK).entity(carrierslaughterhouseList).build();
	}

	@DELETE
	@Path("/{tripNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("tripNo") final String tripNo) {
		int result = this.carrierslaughterhouseService.delete(tripNo);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create CarrierSlaughterhouse!", notes = "Creating an instance of CarrierSlaughterhouse!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid CarrierAssignment carrierAssignment) {
		//
		CarrierSlaughterhouse carrierslaughterhouse_ = new CarrierSlaughterhouse();
		carrierslaughterhouse_.setCurrentStatus("SLAGUTER-BOUND");
		// Convert to milliseconds since epoch
		long startmilliseconds = LocalDate.parse(carrierAssignment.getStartDate()).atStartOfDay(ZoneId.of("UTC"))
				.toInstant().toEpochMilli();
		long arrivaltmilliseconds = LocalDate.parse(carrierAssignment.getEndDate()).atStartOfDay(ZoneId.of("UTC"))
				.toInstant().toEpochMilli();
		carrierslaughterhouse_.setExpectedArrivalDate(arrivaltmilliseconds);
		carrierslaughterhouse_.setLocation(carrierAssignment.getLocation());
		carrierslaughterhouse_.setSlaughterhousename(carrierAssignment.getSalughterhousename());
		carrierslaughterhouse_.setStartDate(startmilliseconds);
		carrierslaughterhouse_.setTripNo("TR-" + System.currentTimeMillis());
		try {
			if (Objects.isNull(this.carrierslaughterhouseService.get(carrierslaughterhouse_.getTripNo()))) {

				this.carrierslaughterhouseService.post(carrierslaughterhouse_);
			}
		} catch (Exception exp) {
			this.carrierslaughterhouseService.post(carrierslaughterhouse_);
		}
		Carrier carrier = carrierService.get(carrierAssignment.getCid());
		for (String animal : carrierAssignment.getAnimals()) {
			SHCarrier shCarrier = new SHCarrier();
			shCarrier.setTripNo(carrierslaughterhouse_.getTripNo());
			shCarrier.setCarrierNumber(carrier.getCarrierNumber());
			shCarrier.setCID(carrierAssignment.getCid());
			shCarrier.setCarrierId("SHC-" + System.currentTimeMillis());
			try {
				if (Objects.isNull(shCarrierService.get(shCarrier.getTripNo()))) {
					shCarrierService.post(shCarrier);
				}
			} catch (Exception exp) {
				shCarrierService.post(shCarrier);
			}
			TransferedAnimal transferedAnimal = transferedAnimalService.get(animal);
			transferedAnimal.setCurrentStatus("TRANSFERED");
			transferedAnimalService.put(transferedAnimal);
			TransportedToSlaughter transportedToSlaughter = new TransportedToSlaughter();

			ObjectMapper mapper = new ObjectMapper();
			try {
				com.ri.se.commonentities.transporter.SHCarrier carrierEntity = mapper.readValue(
						mapper.writeValueAsString(shCarrier), com.ri.se.commonentities.transporter.SHCarrier.class);
				com.ri.se.commonentities.transporter.CarrierSlaughterhouse carrierSlaughterEntity = mapper.readValue(
						mapper.writeValueAsString(carrierslaughterhouse_),
						com.ri.se.commonentities.transporter.CarrierSlaughterhouse.class);
				com.ri.se.commonentities.transporter.TransferedAnimal transferedAnimalEntity = mapper.readValue(
						mapper.writeValueAsString(transferedAnimal),
						com.ri.se.commonentities.transporter.TransferedAnimal.class);
				transportedToSlaughter.setCarrier(carrierEntity);
				transportedToSlaughter.setCarrierSlaughterhouse(carrierSlaughterEntity);
				transportedToSlaughter.setTransferedAnimal(transferedAnimalEntity);
				transportedToSlaughter.setTo(carrierAssignment.getTo());
				transportedToSlaughter.setFrom(carrierAssignment.getFrom());
				String temp = mapper.writeValueAsString(transportedToSlaughter);
				// TransportedToSlaughter transportedToSlaughter1 = mapper.readValue(temp,
				// TransportedToSlaughter.class);
				EvidenceData data = new EvidenceData();
				data.setContents(new String(Base64.getEncoder().encodeToString(temp.getBytes())));
				try {
					String ip = "http://localhost";
					int port = 9030;
					String resource = "/v1/sctransaction/submit/to/slaughter";
					RestClient rc = RestClient.builder().baseUrl(ip + ":" + port).build();
					Representation rep = rc.post("/application" + resource, data, null);
					System.out.println(rep.getCode());
					System.out.println(rep.getBody().toString());

				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception exp) {

			}
		}
		// TransferedAnimalService
		return Response.status(Response.Status.OK).build();

	}

	@PUT
	@Path("/{tripNo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update CarrierSlaughterhouse!", notes = "Updating an instance of CarrierSlaughterhouse !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("tripNo") final String tripNo, @Valid CarrierSlaughterhouse carrierslaughterhouse) {
		carrierslaughterhouse.setTripNo(tripNo);
		int i = this.carrierslaughterhouseService.put(carrierslaughterhouse);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
