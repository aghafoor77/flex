package com.ri.se.semenutilization.utils;

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

import com.ri.se.semenutilization.dto.DTOSemenUtilization;
import com.ri.se.semenutilization.dto.DTOSemenUtilizationList;
import com.ri.se.semenutilization.dto.Message;
import com.ri.se.semenutilization.dto.SemenUtilizationEntityConverter;
import com.ri.se.semenutilization.persistance.SemenUtilization;
import com.ri.se.semenutilization.persistance.SemenUtilizationList;
import com.ri.se.semenutilization.persistance.SemenUtilizationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/semenutilization")
@Api(value = "SemenUtilization by RRB", description = "API used to provide CRUD operations for SemenUtilization service ")
public class SemenUtilizationResource {

	private SemenUtilizationService semenutilizationService;

	private SemenUtilizationEntityConverter semenutilizationEntityConverter = new SemenUtilizationEntityConverter();

	public SemenUtilizationResource(SemenUtilizationService semenutilizationService) {
		this.semenutilizationService = semenutilizationService;
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
	 * String status = semenutilizationService.performHealthCheck(); if
	 * (!Objects.isNull(status)) { return
	 * Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new
	 * Message(status)).build();
	 * 
	 * } return Response.status(Response.Status.OK).entity(new
	 * Message("OK deep health report !")).build();
	 * 
	 * }
	 */

	@GET
	@Path("/{suid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [SemenUtilization]!", notes = "Returns instance of SemenUtilization stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("suid") final String suid) {
		SemenUtilization semenutilization = this.semenutilizationService.get(suid);
		if (java.util.Objects.isNull(semenutilization)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(semenutilizationEntityConverter.getDTOSemenUtilization(semenutilization)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored SemenUtilization!", notes = "Get list of all stored SemenUtilization!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<SemenUtilization> list = this.semenutilizationService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOSemenUtilizationList semenutilizationListDTO = new DTOSemenUtilizationList();
		for (SemenUtilization semenutilization : list) {
			try {
				semenutilizationListDTO.add(semenutilizationEntityConverter.getDTOSemenUtilization(semenutilization));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(semenutilizationListDTO).build();
	}

	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored SemenUtilization!", notes = "Get list of all stored SemenUtilization!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByAnimalID(@PathParam("animalID") final String animalID) {
		List<SemenUtilization> list = this.semenutilizationService.getByAnimal(animalID);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOSemenUtilizationList semenutilizationListDTO = new DTOSemenUtilizationList();
		for (SemenUtilization semenutilization : list) {
			try {
				semenutilizationListDTO.add(semenutilizationEntityConverter.getDTOSemenUtilization(semenutilization));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(semenutilizationListDTO).build();
	}
	
	@DELETE
	@Path("/{suid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("suid") final String suid) {
		int result = this.semenutilizationService.delete(suid);
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
	@ApiOperation(value = "Create SemenUtilization!", notes = "Creating an instance of SemenUtilization!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOSemenUtilization dtosemenutilization) {
		dtosemenutilization.setSuid("SU-" + System.currentTimeMillis());
		SemenUtilization semenutilization = null;
		try {
			semenutilization = semenutilizationEntityConverter.getSemenUtilization(dtosemenutilization);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.semenutilizationService.post(semenutilization);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{suid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update SemenUtilization!", notes = "Updating an instance of SemenUtilization !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("suid") final String suid, @Valid DTOSemenUtilization dtosemenutilization) {
		dtosemenutilization.setSuid(suid);
		SemenUtilization semenutilization = null;
		try {
			semenutilization = semenutilizationEntityConverter.getSemenUtilization(dtosemenutilization);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.semenutilizationService.put(semenutilization);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@GET
	@Path("/os/{osid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given order number [SemenUtilization]!", notes = "Returns instance of SemenUtilization stored against given order number !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByOrder(@PathParam("osid") final String osid) {
		List<SemenUtilization> list = this.semenutilizationService.getByOrder(osid);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SemenUtilizationList semenutilizationList = new SemenUtilizationList();
		for (SemenUtilization semenutilization : list) {
			semenutilizationList.add(semenutilization);
		}
		return Response.status(Response.Status.OK).entity(semenutilizationList).build();
	}

	@GET
	@Path("/su/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given animal [SemenUtilization]!", notes = "Returns instance of SemenUtilization stored against given order number !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAnimalRecord(@PathParam("animalID") final String animalID) {
		List<SemenUtilization> list = this.semenutilizationService.getByAnimal(animalID);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		SemenUtilizationList semenutilizationList = new SemenUtilizationList();
		for (SemenUtilization semenutilization : list) {
			semenutilizationList.add(semenutilization);
		}
		return Response.status(Response.Status.OK).entity(semenutilizationList).build();
	}
}
