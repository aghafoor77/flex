package com.ri.se.transferedanimal.utils;

import java.sql.SQLIntegrityConstraintViolationException;
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

import com.ri.se.commonentities.transporter.DTOTransferedAnimal;
import com.ri.se.commonentities.transporter.DTOTransferedAnimalList;
import com.ri.se.transferedanimal.downloader.DownloadVDRData;
import com.ri.se.transferedanimal.dto.Message;
import com.ri.se.transferedanimal.dto.TransferedAnimalEntityConverter;
import com.ri.se.transferedanimal.persistance.TransferedAnimal;
import com.ri.se.transferedanimal.persistance.TransferedAnimalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/transferedanimal")
@Api(value = "TransferedAnimal by RRB", description = "API used to provide CRUD operations for TransferedAnimal service ")
public class TransferedAnimalResource {

	private TransferedAnimalService transferedanimalService;

	private TransferedAnimalEntityConverter transferedanimalEntityConverter = new TransferedAnimalEntityConverter();

	public TransferedAnimalResource(TransferedAnimalService transferedanimalService) {
		this.transferedanimalService = transferedanimalService;
	}

	@GET
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [TransferedAnimal]!", notes = "Returns instance of TransferedAnimal stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("animalID") final String animalID) {
		TransferedAnimal transferedanimal = this.transferedanimalService.get(animalID);
		if (java.util.Objects.isNull(transferedanimal)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(transferedanimalEntityConverter.getDTOTransferedAnimal(transferedanimal)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored TransferedAnimal!", notes = "Get list of all stored TransferedAnimal!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<TransferedAnimal> list = this.transferedanimalService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOTransferedAnimalList transferedanimalListDTO = new DTOTransferedAnimalList();
		for (TransferedAnimal transferedanimal : list) {
			try {
				transferedanimalListDTO.add(transferedanimalEntityConverter.getDTOTransferedAnimal(transferedanimal));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(transferedanimalListDTO).build();
	}

	@DELETE
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("animalID") final String animalID) {
		int result = this.transferedanimalService.delete(animalID);
		if (result < 1) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create TransferedAnimal!", notes = "Creating an instance of TransferedAnimal!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOTransferedAnimal dtotransferedanimal) {
		TransferedAnimal transferedanimal = null;
		try {
			transferedanimal = transferedanimalEntityConverter.getTransferedAnimal(dtotransferedanimal);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.transferedanimalService.post(transferedanimal);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(transferedanimal).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update TransferedAnimal!", notes = "Updating an instance of TransferedAnimal !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("animalID") final String animalID, @Valid DTOTransferedAnimal dtotransferedanimal) {
		dtotransferedanimal.setAnimalID(animalID);
		TransferedAnimal transferedanimal = null;
		try {
			transferedanimal = transferedanimalEntityConverter.getTransferedAnimal(dtotransferedanimal);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.transferedanimalService.put(transferedanimal);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	//
	@GET
	@Path("/list/totransfer/{veid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [TransferedAnimal]!", notes = "Returns instance of TransferedAnimal stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response listTotransfer(@PathParam("veid") final String veid) {
		DownloadVDRData downloadVDRData = new DownloadVDRData();
		String ipfs = "";
		String ip = "http://localhost";
		int port = 9030;
		long timeInMilli = 0;
		try {
			long updated = this.transferedanimalService.getLatest();
			timeInMilli = updated;			
		} catch (Exception exp) {

		}
		System.err.println("================================================================");
		System.err.println("========================= HOW TO CHECK =========================");
		System.err.println("========================= USER ADDRESS =========================");
		System.err.println("================================================================");
		String resource = "/v1/sctransaction//updated/byreceiver/" + timeInMilli+"/"+ veid;
		DTOTransferedAnimalList resources;

		try {
			resources = downloadVDRData.downloadFromVDRData(ip, port, resource);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		for (DTOTransferedAnimal ta : resources) {
			try {
				this.transferedanimalService.post(new TransferedAnimalEntityConverter().getTransferedAnimal(ta));
			} catch (SQLIntegrityConstraintViolationException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(transferedanimalEntityConverter.toDTOTransferedAnimalList(this.transferedanimalService.listAvaiable()))
					.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.OK).entity("Error convertor handling !").build();
		}

	}

	@GET
	@Path("/current/status/{status}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [TransportedCattle]!", notes = "Returns instance of TransportedCattle stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAnimalAsCurrentStatus(@PathParam("status") final String status) {
		List<TransferedAnimal> transportedcattle = this.transferedanimalService.getAnimalAsCurrentStatus(status);
		if (java.util.Objects.isNull(transportedcattle)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try{
			return Response.status(Response.Status.OK)
					.entity(transferedanimalEntityConverter.toDTOTransferedAnimalList(this.transferedanimalService.list()))
					.build();

		}catch(Exception exp){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	
	
	
	@GET
	@Path("/download/{identity}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [TransferedAnimal]!", notes = "Returns instance of TransferedAnimal stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response download(@PathParam("identity") final String animalID) {
		TransferedAnimal transferedanimal = this.transferedanimalService.get(animalID);
		if (java.util.Objects.isNull(transferedanimal)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(transferedanimalEntityConverter.getDTOTransferedAnimal(transferedanimal)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}
}