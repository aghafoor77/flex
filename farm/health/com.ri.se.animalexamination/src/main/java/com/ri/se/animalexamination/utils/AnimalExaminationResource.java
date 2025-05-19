package com.ri.se.animalexamination.utils;

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

import com.ri.se.animalexamination.dto.AnimalExaminationEntityConverter;
import com.ri.se.animalexamination.dto.DTOAnimalExamination;
import com.ri.se.animalexamination.dto.DTOAnimalExaminationList;
import com.ri.se.animalexamination.dto.Message;
import com.ri.se.animalexamination.persistance.AnimalExamination;
import com.ri.se.animalexamination.persistance.AnimalExaminationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/animalexamination")
@Api(value = "AnimalExamination by RRB", description = "API used to provide CRUD operations for AnimalExamination service ")
public class AnimalExaminationResource {

	private AnimalExaminationService animalexaminationService;

	private AnimalExaminationEntityConverter animalexaminationEntityConverter = new AnimalExaminationEntityConverter();

	public AnimalExaminationResource(AnimalExaminationService animalexaminationService) {
		this.animalexaminationService = animalexaminationService;
	}

	/*
	 * @GET
	 * 
	 * @Path("/health")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @ApiOperation(value = "Returns OK report if service is running !", notes =
	 * "Returns OK report if service is running !", response = String.class)
	 * 
	 * @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
	 * 
	 * @ApiResponse(code = 500, message = "Internal Server error !") }) public
	 * Response health() {
	 * 
	 * return Response.status(Response.Status.OK).entity(new
	 * Message("OK health report !")).build();
	 * 
	 * }
	 * 
	 * @GET
	 * 
	 * @Path("/deephealth")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @ApiOperation(value = "Returns OK report if service is running !", notes =
	 * "Returns OK report if service is running !", response = String.class)
	 * 
	 * @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
	 * 
	 * @ApiResponse(code = 500, message = "Internal Server error !") }) public
	 * Response deephealth() {
	 * 
	 * String status = animalexaminationService.performHealthCheck();
	 * if(!Objects.isNull(status)) { return
	 * Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new
	 * Message(status)).build();
	 * 
	 * } return Response.status(Response.Status.OK).entity(new
	 * Message("OK deep health report !")).build();
	 * 
	 * }
	 */

	@GET
	@Path("/{aeid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [AnimalExamination]!", notes = "Returns instance of AnimalExamination stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("aeid") final String aeid) {
		AnimalExamination animalexamination = this.animalexaminationService.get(aeid);
		if (java.util.Objects.isNull(animalexamination)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(animalexaminationEntityConverter.getDTOAnimalExamination(animalexamination)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored AnimalExamination against animal ID !", notes = "Get list of all stored AnimalExamination animal ID!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByAnimalID(@PathParam("animalID") final String animalID) {
		List<AnimalExamination> list = this.animalexaminationService.getByAnimalID(animalID);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAnimalExaminationList animalexaminationListDTO = new DTOAnimalExaminationList();
		for (AnimalExamination animalexamination : list) {
			try {
				animalexaminationListDTO
						.add(animalexaminationEntityConverter.getDTOAnimalExamination(animalexamination));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(animalexaminationListDTO).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored AnimalExamination!", notes = "Get list of all stored AnimalExamination!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<AnimalExamination> list = this.animalexaminationService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAnimalExaminationList animalexaminationListDTO = new DTOAnimalExaminationList();
		for (AnimalExamination animalexamination : list) {
			try {
				animalexaminationListDTO
						.add(animalexaminationEntityConverter.getDTOAnimalExamination(animalexamination));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(animalexaminationListDTO).build();
	}

	@DELETE
	@Path("/{aeid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("aeid") final String aeid) {
		int result = this.animalexaminationService.delete(aeid);
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
	@ApiOperation(value = "Create AnimalExamination!", notes = "Creating an instance of AnimalExamination!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOAnimalExamination dtoanimalexamination) {
		dtoanimalexamination.setAeid("AE-" + System.currentTimeMillis());
		AnimalExamination animalexamination = null;
		try {
			animalexamination = animalexaminationEntityConverter.getAnimalExamination(dtoanimalexamination);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.animalexaminationService.post(animalexamination);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(dtoanimalexamination).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{aeid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update AnimalExamination!", notes = "Updating an instance of AnimalExamination !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("aeid") final String aeid, @Valid DTOAnimalExamination dtoanimalexamination) {
		dtoanimalexamination.setAeid(aeid);
		AnimalExamination animalexamination = null;
		try {
			animalexamination = animalexaminationEntityConverter.getAnimalExamination(dtoanimalexamination);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.animalexaminationService.put(animalexamination);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@GET
	@Path("/status/{status}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all Conceived Animals!", notes = "Get list of all Conceived Animals!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getListByStatus(@PathParam("status") final String status) {
		List<AnimalExamination> list = this.animalexaminationService.getListByStatus(status);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAnimalExaminationList animalexaminationListDTO = new DTOAnimalExaminationList();
		for (AnimalExamination animalexamination : list) {
			try {
				animalexaminationListDTO
						.add(animalexaminationEntityConverter.getDTOAnimalExamination(animalexamination));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(animalexaminationListDTO).build();
	}

	@GET
	@Path("/animal/{animal}/status/{status}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of Conceived status with Animal ID!", notes = "Get list of all examination reports which have Conceived status with Animal ID!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getListByAnimalAndStatus(@PathParam("animal") final String animal,
			@PathParam("status") String status) {
		List<AnimalExamination> list = this.animalexaminationService.getListByAnimalAndStatus(animal, status);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOAnimalExaminationList animalexaminationListDTO = new DTOAnimalExaminationList();
		for (AnimalExamination animalexamination : list) {
			try {
				animalexaminationListDTO
						.add(animalexaminationEntityConverter.getDTOAnimalExamination(animalexamination));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(animalexaminationListDTO).build();
	}
	// public List<AnimalExamination> getListByAnimalAndStatus(String animalID,
	// String status){
	// return dao().getListByAnimalAndStatus(animalID, status);
}
