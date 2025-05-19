package com.ri.se.temporarymovementgroup.utils;

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

import com.ri.se.temporarymovementgroup.dto.DTOTemporaryMovementGroup;
import com.ri.se.temporarymovementgroup.dto.DTOTemporaryMovementGroupList;
import com.ri.se.temporarymovementgroup.dto.Message;
import com.ri.se.temporarymovementgroup.dto.TemporaryMovementGroupEntityConverter;
import com.ri.se.temporarymovementgroup.persistance.TemporaryMovementGroup;
import com.ri.se.temporarymovementgroup.persistance.TemporaryMovementGroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/temporarymovementgroup")
@Api(value = "TemporaryMovementGroup by RRB", description = "API used to provide CRUD operations for TemporaryMovementGroup service ")
public class TemporaryMovementGroupResource {

	private TemporaryMovementGroupService temporarymovementgroupService;

	private TemporaryMovementGroupEntityConverter temporarymovementgroupEntityConverter = new TemporaryMovementGroupEntityConverter();

	public TemporaryMovementGroupResource(TemporaryMovementGroupService temporarymovementgroupService) {
		this.temporarymovementgroupService = temporarymovementgroupService;
	}

	@GET
	@Path("/health")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns OK report if service is running !", notes = "Returns OK report if service is running !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response health() {

		return Response.status(Response.Status.OK).entity(new Message("OK health report !")).build();

	}

	@GET
	@Path("/deephealth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns OK report if service is running !", notes = "Returns OK report if service is running !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response deephealth() {

		String status = temporarymovementgroupService.performHealthCheck();
		if (!Objects.isNull(status)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(status)).build();

		}
		return Response.status(Response.Status.OK).entity(new Message("OK deep health report !")).build();

	}

	@GET
	@Path("/{tmgid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [TemporaryMovementGroup]!", notes = "Returns instance of TemporaryMovementGroup stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("tmgid") final String tmgid) {
		TemporaryMovementGroup temporarymovementgroup = this.temporarymovementgroupService.get(tmgid);
		if (java.util.Objects.isNull(temporarymovementgroup)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(temporarymovementgroupEntityConverter.getDTOTemporaryMovementGroup(temporarymovementgroup))
					.build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Path("/group/{tmid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored TemporaryMovementGroup!", notes = "Get list of all stored TemporaryMovementGroup!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getGroupByTM(@PathParam("tmid") final String tmid) {
		List<TemporaryMovementGroup> list = this.temporarymovementgroupService.getAnimals(tmid);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOTemporaryMovementGroupList temporarymovementgroupListDTO = new DTOTemporaryMovementGroupList();
		for (TemporaryMovementGroup temporarymovementgroup : list) {
			try {
				temporarymovementgroupListDTO.add(
						temporarymovementgroupEntityConverter.getDTOTemporaryMovementGroup(temporarymovementgroup));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(temporarymovementgroupListDTO).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored TemporaryMovementGroup!", notes = "Get list of all stored TemporaryMovementGroup!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<TemporaryMovementGroup> list = this.temporarymovementgroupService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOTemporaryMovementGroupList temporarymovementgroupListDTO = new DTOTemporaryMovementGroupList();
		for (TemporaryMovementGroup temporarymovementgroup : list) {
			try {
				temporarymovementgroupListDTO.add(
						temporarymovementgroupEntityConverter.getDTOTemporaryMovementGroup(temporarymovementgroup));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(temporarymovementgroupListDTO).build();
	}

	
	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored TemporaryMovementGroup by animal ID!", notes = "Get list of all stored TemporaryMovementGroup by animal ID!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByAnimalID(@PathParam("animalID") final String animalID) {
		List<TemporaryMovementGroup> list = this.temporarymovementgroupService.getByAnimalID(animalID);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOTemporaryMovementGroupList temporarymovementgroupListDTO = new DTOTemporaryMovementGroupList();
		for (TemporaryMovementGroup temporarymovementgroup : list) {
			try {
				temporarymovementgroupListDTO.add(
						temporarymovementgroupEntityConverter.getDTOTemporaryMovementGroup(temporarymovementgroup));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(temporarymovementgroupListDTO).build();
	}	
	
	@DELETE
	@Path("/{tmgid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("tmgid") final String tmgid) {
		int result = this.temporarymovementgroupService.delete(tmgid);
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
	@ApiOperation(value = "Create TemporaryMovementGroup!", notes = "Creating an instance of TemporaryMovementGroup!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOTemporaryMovementGroup dtotemporarymovementgroup) {
		TemporaryMovementGroup temporarymovementgroup = null;
		try {
			temporarymovementgroup = temporarymovementgroupEntityConverter
					.getTemporaryMovementGroup(dtotemporarymovementgroup);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.temporarymovementgroupService.post(temporarymovementgroup);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{tmgid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update TemporaryMovementGroup!", notes = "Updating an instance of TemporaryMovementGroup !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("tmgid") final String tmgid,
			@Valid DTOTemporaryMovementGroup dtotemporarymovementgroup) {
		dtotemporarymovementgroup.setTmgid(tmgid);
		TemporaryMovementGroup temporarymovementgroup = null;
		try {
			temporarymovementgroup = temporarymovementgroupEntityConverter
					.getTemporaryMovementGroup(dtotemporarymovementgroup);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.temporarymovementgroupService.put(temporarymovementgroup);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}
}
