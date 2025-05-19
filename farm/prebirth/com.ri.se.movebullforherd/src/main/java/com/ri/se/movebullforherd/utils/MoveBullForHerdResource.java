package com.ri.se.movebullforherd.utils;

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

import com.ri.se.movebullforherd.dto.DTOMoveBullForHerd;
import com.ri.se.movebullforherd.dto.DTOMoveBullForHerdList;
import com.ri.se.movebullforherd.dto.Message;
import com.ri.se.movebullforherd.dto.MoveBullForHerdEntityConverter;
import com.ri.se.movebullforherd.persistance.MoveBullForHerd;
import com.ri.se.movebullforherd.persistance.MoveBullForHerdService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/v1/movebullforherd")
@Api(value = "MoveBullForHerd by RRB", description = "API used to provide CRUD operations for MoveBullForHerd service ")
public class MoveBullForHerdResource {

	private MoveBullForHerdService movebullforherdService;

	private MoveBullForHerdEntityConverter movebullforherdEntityConverter = new MoveBullForHerdEntityConverter();

	public MoveBullForHerdResource(MoveBullForHerdService movebullforherdService) {
		this.movebullforherdService = movebullforherdService;
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
	 * String status = movebullforherdService.performHealthCheck();
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
	@Path("/{mb4hid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity [MoveBullForHerd]!", notes = "Returns instance of MoveBullForHerd stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response get(@PathParam("mb4hid") final String mb4hid) {
		MoveBullForHerd movebullforherd = this.movebullforherdService.get(mb4hid);
		if (java.util.Objects.isNull(movebullforherd)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching entry or requested entry does not exist !")).build();
		}
		try {
			return Response.status(Response.Status.OK)
					.entity(movebullforherdEntityConverter.getDTOMoveBullForHerd(movebullforherd)).build();

		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored MoveBullForHerd!", notes = "Get list of all stored MoveBullForHerd!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response list() {
		List<MoveBullForHerd> list = this.movebullforherdService.list();
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOMoveBullForHerdList movebullforherdListDTO = new DTOMoveBullForHerdList();
		for (MoveBullForHerd movebullforherd : list) {
			try {
				movebullforherdListDTO.add(movebullforherdEntityConverter.getDTOMoveBullForHerd(movebullforherd));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(movebullforherdListDTO).build();
	}
	
	@GET
	@Path("/animal/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all stored MoveBullForHerd against animalID!", notes = "Get list of all stored MoveBullForHerd against animalID!", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getByAnimalID(@PathParam("animalID") final String animalID) {
		List<MoveBullForHerd> list = this.movebullforherdService.getAnimalMovement(animalID);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOMoveBullForHerdList movebullforherdListDTO = new DTOMoveBullForHerdList();
		for (MoveBullForHerd movebullforherd : list) {
			try {
				movebullforherdListDTO.add(movebullforherdEntityConverter.getDTOMoveBullForHerd(movebullforherd));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(movebullforherdListDTO).build();
	}
	

	@DELETE
	@Path("/{mb4hid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns instance stored against given identity !", notes = "Returns instance stored against given identity !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response delete(@PathParam("mb4hid") final String mb4hid) {
		int result = this.movebullforherdService.delete(mb4hid);
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
	@ApiOperation(value = "Create MoveBullForHerd!", notes = "Creating an instance of MoveBullForHerd!", response = Response.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response post(@Valid DTOMoveBullForHerd dtomovebullforherd) {
		dtomovebullforherd.setMb4hid("MB-" + System.currentTimeMillis());
		MoveBullForHerd movebullforherd = null;
		try {
			movebullforherd = movebullforherdEntityConverter.getMoveBullForHerd(dtomovebullforherd);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.movebullforherdService.post(movebullforherd);
		if (i > 0) {
			return Response.status(Response.Status.OK).entity(movebullforherd).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/{mb4hid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update MoveBullForHerd!", notes = "Updating an instance of MoveBullForHerd !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response put(@PathParam("mb4hid") final String mb4hid, @Valid DTOMoveBullForHerd dtomovebullforherd) {
		dtomovebullforherd.setMb4hid(mb4hid);
		MoveBullForHerd movebullforherd = null;
		try {
			movebullforherd = movebullforherdEntityConverter.getMoveBullForHerd(dtomovebullforherd);
		} catch (Exception exp) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage())).build();
		}
		int i = this.movebullforherdService.put(movebullforherd);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
					new Message("Problems when updating entry. Please contact system admin for more information!"))
					.build();
		}
	}

	@PUT
	@Path("/exit/{mb4hid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update MoveBullForHerd!", notes = "Updating an instance of MoveBullForHerd !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response exit(@PathParam("mb4hid") final String mb4hid, @Valid MoveBullForHerd _movebullforherd) {
		MoveBullForHerd movebullforherd = this.movebullforherdService.get(mb4hid);
		if (java.util.Objects.isNull(movebullforherd)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("problems when fetching record for setting exit status !")).build();
		}
		System.out.println(_movebullforherd.getExitDate());
		movebullforherd.setExitDate(_movebullforherd.getExitDate());

		int i = this.movebullforherdService.put(movebullforherd);
		if (i > 0) {
			return Response.status(Response.Status.OK).build();
		} else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("problems when updating exit status !")).build();
		}
	}

	/*
	 * @GET
	 * 
	 * @Path("/animal/{animalID}")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @ApiOperation(value =
	 * "Returns list of all stored MoveBullForHerd against an animal!", notes =
	 * "Get list of all stored MoveBullForHerd against an animal!", response =
	 * String.class)
	 * 
	 * @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
	 * 
	 * @ApiResponse(code = 500, message = "Internal Server error !") }) public
	 * Response getDatabyAnimalID(@PathParam("animalID") final String animalID) {
	 * List<MoveBullForHerd> list = this.movebullforherdService.list(); if
	 * (java.util.Objects.isNull(list)) { return
	 * Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new
	 * Message("Problems when fetching animal movement record !")).build(); }
	 * MoveBullForHerdList movebullforherdList = new MoveBullForHerdList(); for
	 * (MoveBullForHerd movebullforherd : list) {
	 * movebullforherdList.add(movebullforherd); } return
	 * Response.status(Response.Status.OK).entity(movebullforherdList).build(); }
	 */

	@GET
	@Path("/move/{animalID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Returns list of all movements of an animal (Does not mater, its a cow or bull).!", notes = "Returns list of all movement of an animal (Does not mater, its a cow or bull). !", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Internal Server error !") })
	public Response getAnimalMovement(@PathParam("animalID") final String animalID) {
		List<MoveBullForHerd> list = this.movebullforherdService.getAnimalMovement(animalID);
		if (java.util.Objects.isNull(list)) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Message("Problems when fetching list or requested data list does not exist !")).build();
		}
		DTOMoveBullForHerdList movebullforherdListDTO = new DTOMoveBullForHerdList();
		for (MoveBullForHerd movebullforherd : list) {
			try {
				movebullforherdListDTO.add(movebullforherdEntityConverter.getDTOMoveBullForHerd(movebullforherd));
			} catch (Exception exp) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Message(exp.getMessage()))
						.build();
			}
		}
		return Response.status(Response.Status.OK).entity(movebullforherdListDTO).build();
	}
}
